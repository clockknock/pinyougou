package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.mapper.TbBrandMapper
import com.pinyougou.pojo.TbBrand
import com.pinyougou.sellergoods.service.BrandService
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author 钟未鸣
 * @date 2018/3/28
 */
@Service
class BrandServiceImpl : BrandService {

    override fun findAll(): List<TbBrand> {
        return brandMapper.selectByExample(null)
    }

    @Autowired
    lateinit var brandMapper: TbBrandMapper
}