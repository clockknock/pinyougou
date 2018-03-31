package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.sellergoods.service.CommonService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/3/28
 */

open class BaseController<T> {


    lateinit var commonService: CommonService<T>

     fun setService(deriveService: CommonService<T>){
         this.commonService = deriveService
     }

    @RequestMapping("/findAll")
    fun findAll(): List<T> {
        return commonService.findAll()
    }

    @RequestMapping("/findPage")
    fun findPage(pageNum: Int, pageSize: Int): PageResult {
        return commonService.findPage(pageNum, pageSize)
    }

    @RequestMapping("/add")
    fun add(@RequestBody obj: T): Result {
        return try {
            commonService.add(obj)
            Result(true, "添加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "添加失败")
        }
    }

    @RequestMapping("/update")
    fun update(@RequestBody obj: T): Result {
        return try {
            commonService.update(obj)
            Result(true, "修改成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "修改失败")
        }

    }

    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        return try {
            commonService.delete(ids)
            Result(true, "删除成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "删除失败")
        }
    }

    @RequestMapping("/search")
    fun search(@RequestBody obj: T, pageNum: Int, pageSize: Int): PageResult {
        return commonService.findPage(obj, pageNum, pageSize)
    }

}