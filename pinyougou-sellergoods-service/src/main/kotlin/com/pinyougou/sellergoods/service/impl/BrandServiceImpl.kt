package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.TbBrandMapper
import com.pinyougou.pojo.TbBrand
import com.pinyougou.pojo.TbBrandExample
import com.pinyougou.sellergoods.service.BrandService
import org.springframework.beans.factory.annotation.Autowired
import priv.zhong.bean.PageResult

/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
@Service
class BrandServiceImpl : BrandService<TbBrand> {
    override fun findPage(brand: TbBrand, pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)
        val example = TbBrandExample()
        val criteria = example.createCriteria()

        if (brand.name != null && brand.name.isNotBlank()) {
            criteria.andNameLike("%${brand.name}%")
            println("增加了name条件")
        }
        if (brand.firstChar != null && brand.firstChar.isNotBlank()) {
            criteria.andFirstCharLike("%${brand.firstChar}%")
            println("增加了firstChar条件")
        }


        val page = brandMapper.selectByExample(example) as Page
        return PageResult(page.total, page.result)

    }

    override fun delete(ids: Array<Long>) {
        for (id in ids) {
            brandMapper.deleteByPrimaryKey(id)
        }
    }

    override fun update(brand: TbBrand) {
        brandMapper.updateByPrimaryKey(brand)
    }

    override fun add(brand: TbBrand) {
        brandMapper.insert(brand)
    }

    override fun findPage(pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)
        val page = brandMapper.selectByExample(null) as Page
        return PageResult(page.total, page.result)
    }

    override fun findAll(): List<TbBrand> {
        return brandMapper.selectByExample(null)
    }


    @Autowired
    lateinit var brandMapper: TbBrandMapper
}