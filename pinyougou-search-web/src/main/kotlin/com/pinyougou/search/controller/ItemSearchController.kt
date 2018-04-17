package com.pinyougou.search.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.search.service.ItemSearchService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author 钟未鸣
 * @date 2018/4/17
 */
@RestController
@RequestMapping("itemsearch")
class ItemSearchController{

    @Reference
    private lateinit var searchService:ItemSearchService

    @RequestMapping("search")
    fun search(@RequestBody searchMap: MutableMap<String,Any>):MutableMap<String,Any>{
        return searchService.search(searchMap)
    }

}