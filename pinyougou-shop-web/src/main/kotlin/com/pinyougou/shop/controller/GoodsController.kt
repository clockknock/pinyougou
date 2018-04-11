package com.pinyougou.shop.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbGoods
import com.pinyougou.pojogroup.Goods
import com.pinyougou.sellergoods.service.GoodsService
import org.springframework.security.core.context.SecurityContextHolder
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
    fun add(@RequestBody goods: Goods): Result {
        return try {
            val name = SecurityContextHolder.getContext().authentication.name
            //绑定商品的卖家id
            goods.goods?.sellerId = name

            goodsService.add(goods)
            Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "增加失败")
        }
    }

    /**
     * 修改
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody goods: Goods): Result {
        val sellerId = SecurityContextHolder.getContext().authentication.name
        //首先判断商品是否是该商家的商品
        val goods2 = goodsService.findOne(goods.goods?.id)
        return if (goods2.goods!!.sellerId != sellerId || !goods.goods?.sellerId.equals
                (sellerId)) {
            Result(false, "非法操作")
        } else try {
            goodsService.update(goods)
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
    fun findOne(id: Long?): Goods {
        return goodsService.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        return try {
            goodsService.delete(ids)
            Result(true, "删除成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "删除失败")
        }
    }

    /**
     * 查询+分页
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    fun search(@RequestBody goods: TbGoods, page: Int, rows: Int): PageResult {
        goods.sellerId = SecurityContextHolder.getContext().authentication.name
        return goodsService.findPage(goods, page, rows)
    }

}