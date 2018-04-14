package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.content.service.ContentCategoryService
import com.pinyougou.pojo.TbContentCategory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/4/14
 */

@RestController
@RequestMapping("/contentCategory")
class ContentCategoryController {

    @Reference
    private val contentCategoryService: ContentCategoryService? = null

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbContentCategory> {
        return contentCategoryService!!.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return contentCategoryService!!.findPage(page, rows)
    }

    /**
     * 增加
     * @param contentCategory
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody contentCategory: TbContentCategory): Result {
        try {
            contentCategoryService!!.add(contentCategory)
            return Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(false, "增加失败")
        }

    }

    /**
     * 修改
     * @param contentCategory
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody contentCategory: TbContentCategory): Result {
        try {
            contentCategoryService!!.update(contentCategory)
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
    fun findOne(id: Long?): TbContentCategory {
        return contentCategoryService!!.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        try {
            contentCategoryService!!.delete(ids)
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
    fun search(@RequestBody contentCategory: TbContentCategory, page: Int, rows: Int): PageResult {
        return contentCategoryService!!.findPage(contentCategory, page, rows)
    }

}
