package com.pinyougou.sellergoods.service

import com.pinyougou.pojo.TbBrand
import priv.zhong.bean.PageResult

/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
interface BrandService {
    fun findAll(): List<TbBrand>

    fun findPage(pageNum:Int,pageSize:Int): PageResult

    fun add(brand :TbBrand)
}