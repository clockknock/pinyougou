package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.TbBrandMapper
import com.pinyougou.pojo.TbBrand
import com.pinyougou.sellergoods.service.BrandService
import org.springframework.beans.factory.annotation.Autowired
import priv.zhong.bean.PageResult

/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
@Service
class BrandServiceImpl : BrandService {
    override fun add(brand: TbBrand) {
        brandMapper.insert(brand)
    }

    override fun findPage(pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum,pageSize)
        val page = brandMapper.selectByExample(null) as Page
        val result = PageResult(page.total, page.result)
        return result

    }

    override fun findAll(): List<TbBrand> {
        return brandMapper.selectByExample(null)
    }


    @Autowired
    lateinit var brandMapper: TbBrandMapper
}