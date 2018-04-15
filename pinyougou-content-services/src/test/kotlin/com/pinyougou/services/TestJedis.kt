package com.pinyougou.services

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * @author 钟未鸣
 * @date 2018/4/15
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
class TestJedis{

    @Test
    fun testGet(){
        val get = redisTemplate.boundValueOps("name").get()
        println(get)
    }

    @Autowired
    lateinit var redisTemplate: RedisTemplate<Any,Any>
}