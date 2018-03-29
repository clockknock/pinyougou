package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbBrand
import com.pinyougou.sellergoods.service.BrandService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
@RestController
@RequestMapping("/brand")
class BrandController {
    @Reference
    private lateinit var brandService: BrandService

    @RequestMapping("/findAll")
    fun findAll(): List<TbBrand> {
        return brandService.findAll()
    }

    @RequestMapping("/findPage")
    fun findPage(pageNum: Int, pageSize: Int): PageResult {
        return brandService.findPage(pageNum, pageSize)
    }

    @RequestMapping("/add")
    fun add(@RequestBody brand: TbBrand): Result{
        val result = Result()
        try {
            brandService.add(brand)
            result.isStatus=true
            result.msg="添加成功"
        } catch (e: Exception) {
            result.isStatus=false
            result.msg="添加失败"
            e.printStackTrace()
        }
        return result
    }
    @RequestMapping("/update")
    fun update(@RequestBody brand: TbBrand):Result{
        val result = Result()
        try {
            brandService.update(brand)
            result.isStatus=true
            result.msg="修改成功"
        } catch (e: Exception) {
            result.isStatus=false
            result.msg="修改失败"
            e.printStackTrace()
        }
        return result
    }

}