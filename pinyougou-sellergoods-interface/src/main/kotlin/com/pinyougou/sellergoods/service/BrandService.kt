package com.pinyougou.sellergoods.service

import com.pinyougou.pojo.TbBrand

/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
interface BrandService {
    fun findAll(): List<TbBrand>
}