package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbBrand
import com.pinyougou.sellergoods.service.BrandService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult


/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
@RestController
@RequestMapping("/brand")
class BrandController {
    @Reference
    lateinit var brandService: BrandService

    @RequestMapping("/findAll")
    fun findAll(): List<TbBrand> {
        return brandService.findAll()
    }

    @RequestMapping("findPage")
    fun findPage(pageNum: Int, pageSize: Int): PageResult {
        return brandService.findPage(pageNum, pageSize)
    }

}