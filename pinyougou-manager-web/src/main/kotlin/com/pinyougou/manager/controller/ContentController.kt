package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbContent
import org.springframework.web.bind.annotation.RequestBody
import priv.zhong.bean.PageResult
import org.springframework.web.bind.annotation.RequestMapping
import com.sun.deploy.util.SearchPath.findOne
import com.oracle.util.Checksums.update
import com.pinyougou.content.service.ContentService
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/4/14
 */

@RestController
@RequestMapping("/content")
class ContentController {

    @Reference
    private val contentService: ContentService? = null

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbContent> {
        return contentService!!.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return contentService!!.findPage(page, rows)
    }

    /**
     * 增加
     * @param content
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody content: TbContent): Result {
        return try {
            contentService!!.add(content)
            Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "增加失败")
        }

    }

    /**
     * 修改
     * @param content
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody content: TbContent): Result {
        try {
            contentService!!.update(content)
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
    fun findOne(id: Long?): TbContent {
        return contentService!!.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        try {
            contentService!!.delete(ids)
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
    fun search(@RequestBody content: TbContent, page: Int, rows: Int): PageResult? {
        return contentService!!.findPage(content, page, rows)
    }

}
