package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbItemCat
import com.pinyougou.pojo.TbTypeTemplate
import com.pinyougou.sellergoods.service.ItemCatService
import com.pinyougou.sellergoods.service.TypeTemplateService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/4/4
 */

@RestController
@RequestMapping("/itemCat")
class ItemCatController {

    @Reference
    private lateinit var itemCatService: ItemCatService

    @Reference
    private lateinit var typeTemplateService: TypeTemplateService

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbItemCat> {
        return itemCatService.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return itemCatService.findPage(page, rows)
    }

    /**
     * 增加
     * @param itemCat
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody itemCat: TbItemCat): Result {
        return try {
            itemCatService.add(itemCat)
            Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "增加失败")
        }

    }

    /**
     * 修改
     * @param itemCat
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody itemCat: TbItemCat): Result {
        try {
            itemCatService.update(itemCat)
            return Result(true, "修改成功")
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(false, "修改失败")
        }

    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    fun findOne(id: Long?): TbItemCat {
        return itemCatService.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        try {
            itemCatService.delete(ids)
            return Result(true, "删除成功")
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(false, "删除失败")
        }

    }

    /**
     * 查询+分页
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    fun search(@RequestBody itemCat: TbItemCat, page: Int, rows: Int): PageResult {
        return itemCatService.findPage(itemCat, page, rows)
    }

    @RequestMapping("/findByParentId")
    fun findByParentId(parentId: Long): List<TbItemCat> {
        return itemCatService.findByParentId(parentId)
    }

    @RequestMapping("/findTypeTemplateList")
    fun findTypeTemplateList(): MutableList<Map<*,*>>? {
        return typeTemplateService.findTypeTemplateList()

    }
}
