package com.pinyougou.portal

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.content.service.ContentService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration("classpath*:spring/springmvc.xml")
class TestAspect{

    @Reference
    lateinit var contentService: ContentService

    @Test
    fun testGet(){
        val list = contentService.findByCategoryId(1)
        list.forEach { println(it) }
    }
    
    

}