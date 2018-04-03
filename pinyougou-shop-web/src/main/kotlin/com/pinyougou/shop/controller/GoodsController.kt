package com.pinyougou.shop.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbGoods
import com.pinyougou.sellergoods.service.GoodsService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


/**
 * @author 钟未鸣
 * @date 2018/4/3
 */

@RestController
@RequestMapping("/goods")
class GoodsController {

    @Reference
    private lateinit var goodsService: GoodsService

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbGoods> {
        return goodsService.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return goodsService.findPage(page, rows)
    }

    /**
     * 增加
     * @param goods
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody goods: TbGoods): Result {
        try {
            goodsService.add(goods)
            return Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(false, "增加失败")
        }

    }

    /**
     * 修改
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody goods: TbGoods): Result {
        try {
            goodsService.update(goods)
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
    fun findOne(id: Long?): TbGoods {
        return goodsService.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        try {
            goodsService.delete(ids)
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
    fun search(@RequestBody goods: TbGoods, page: Int, rows: Int): PageResult {
        return goodsService.findPage(goods, page, rows)
    }

}