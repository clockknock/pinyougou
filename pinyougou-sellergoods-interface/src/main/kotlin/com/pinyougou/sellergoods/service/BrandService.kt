package com.pinyougou.sellergoods.service

import com.pinyougou.pojo.TbBrand
import priv.zhong.bean.PageResult

/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
interface BrandService {
    fun findAll(): List<TbBrand>

    /**
     * @param pageNum 当前页数
     * @param pageSize 页面大小
     */
    fun findPage(pageNum:Int,pageSize:Int): PageResult

    fun add(brand :TbBrand)

    fun update(brand: TbBrand)

    fun delete(ids: Array<Long>)

    /**
     * @param brand 查询条件的实体类
     * @param pageNum 当前页数
     * @param pageSize 页面大小
     */
    fun findPage(brand: TbBrand, pageNum:Int,pageSize:Int): PageResult

    fun findBrandList(): List<Map<*,*>>
}