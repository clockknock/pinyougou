package com.pinyougou.shop.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbTypeTemplate
import com.pinyougou.sellergoods.service.TypeTemplateService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/4/2
 */
@RestController
@RequestMapping("/typeTemplate")
class TypeTemplateController {
    @Reference
    lateinit var typeTemplateService: TypeTemplateService

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbTypeTemplate> {
        return typeTemplateService.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return typeTemplateService.findPage(page, rows)
    }

    /**
     * 增加
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody typeTemplate: TbTypeTemplate): Result {
        return try {
            typeTemplateService.add(typeTemplate)
            Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "增加失败")
        }

    }

    /**
     * 修改
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody typeTemplate: TbTypeTemplate): Result {
        return try {
            typeTemplateService.update(typeTemplate)
            Result(true, "修改成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "修改失败")
        }

    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    fun findOne(id: Long?): TbTypeTemplate {
        return typeTemplateService.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        try {
            typeTemplateService.delete(ids)
            return Result(true, "删除成功")
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(false, "删除失败")
        }

    }

    /**
     * 查询+分页
     * @param brand
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    fun search(@RequestBody typeTemplate: TbTypeTemplate, page: Int, rows: Int): PageResult {
        return typeTemplateService.findPage(typeTemplate, page, rows)
    }

    @RequestMapping("/findTypeIdJson")
    fun findTypeIdJson(id: Long): Map<*, *>? =
            typeTemplateService.findTypeIdJson(id)


}