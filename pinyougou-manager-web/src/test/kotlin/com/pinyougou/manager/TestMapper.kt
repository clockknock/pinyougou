package com.pinyougou.manager

import com.pinyougou.mapper.TbTypeTemplateMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * @author 钟未鸣
 * @date 2018/4/6
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
class TestMapper{
    @Autowired
    lateinit var mapper: TbTypeTemplateMapper

    @Test
    fun testFindTypeTemplateList(){
        val typeIdJson = mapper.findTypeIdJson(35)
        println(typeIdJson)
    }
}