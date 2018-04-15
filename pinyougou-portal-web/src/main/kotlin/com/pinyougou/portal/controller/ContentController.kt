package com.pinyougou.portal.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.content.service.ContentService
import com.pinyougou.pojo.TbContent
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 钟未鸣
 * @date 2018/4/15
 */
@RestController
@RequestMapping("/content")
class ContentController {
    @Reference
    lateinit var contentService: ContentService

    @RequestMapping("/findByCategoryId")
    fun findByCategoryId(categoryId: Long): List<TbContent> {
        return contentService.findByCategoryId(categoryId)
    }

}