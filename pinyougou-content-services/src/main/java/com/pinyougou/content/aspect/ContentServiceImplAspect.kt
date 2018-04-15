package com.pinyougou.content.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * @author 钟未鸣
 * @date 2018/4/15
 *
 */
@Component
@Aspect
class ContentServiceImplAspect {

    @After(value = "execution(* com.pinyougou.content.service.impl.ContentServiceImpl.add(..)) ||" +
            " execution(* com.pinyougou.content.service.impl.ContentServiceImpl" +
            ".update(..)) || execution(* com.pinyougou.content.service.impl.ContentServiceImpl.delete(..))")
            /**
             *contentServiceImpl的add/update/delete方法执行后将redis中content删除,让其重新查找
             * @param joinPoint JoinPoint
             */
    fun cleanRedis(joinPoint: JoinPoint) {
        redisTemplate.delete("content")
    }

    @Autowired
    lateinit var redisTemplate: RedisTemplate<Any, Any>

}