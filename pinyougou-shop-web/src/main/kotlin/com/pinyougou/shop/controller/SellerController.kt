package com.pinyougou.shop.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.pinyougou.pojo.TbSeller
import com.pinyougou.sellergoods.service.SellerService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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
    private lateinit var sellerService: SellerService

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbSeller> {
        return sellerService.findAll()
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    fun findPage(page: Int, rows: Int): PageResult {
        return sellerService.findPage(page, rows)
    }

    /**
     * 增加
     * @param seller
     * @return
     */
    @RequestMapping("/add")
    fun add(@RequestBody seller: TbSeller): Result {
        //密码加密
        val passwordEncoder = BCryptPasswordEncoder()
        val password = passwordEncoder.encode(seller.password)//加密
        seller.password = password

        return try {
            sellerService.add(seller)
            Result(true, "增加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "增加失败")
        }

    }

    /**
     * 修改
     * @param seller
     * @return
     */
    @RequestMapping("/update")
    fun update(@RequestBody seller: TbSeller): Result {
        return try {
            sellerService.update(seller)
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
        return sellerService.findOne(id)
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    fun delete(ids: Array<String>): Result {
        return try {
            sellerService.delete(ids)
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
    fun search(@RequestBody seller: TbSeller, page: Int, rows: Int): PageResult {
        return sellerService.findPage(seller, page, rows)
    }

}
