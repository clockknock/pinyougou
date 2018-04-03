package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbSeller
import com.pinyougou.sellergoods.service.SellerService
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
@RequestMapping("/seller")
class SellerController {

    @Reference
    private lateinit var service: SellerService

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbSeller> {
        return service.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return service.findPage(page, rows)
    }

    /**
     * 增加
     * @param typeTemplate
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody seller: TbSeller): Result {
        return try {
            service.add(seller)
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
    fun update(@RequestBody seller: TbSeller): Result {
        return try {
            service.update(seller)
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
    fun findOne(id: String): TbSeller {
        return service.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<String>): Result {
        return try {
            service.delete(ids)
            Result(true, "删除成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "删除失败")
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
    fun search(@RequestBody tbSeller: TbSeller, page: Int, rows: Int): PageResult {
        return service.findPage(tbSeller, page, rows)
    }

    @RequestMapping("/updateStatus")
    fun updateStatus(@RequestBody seller: TbSeller) : Result{
        return try {
            service.updateStatus(seller)
            Result(true,"更新成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false,"更新失败")
        }
    }


}