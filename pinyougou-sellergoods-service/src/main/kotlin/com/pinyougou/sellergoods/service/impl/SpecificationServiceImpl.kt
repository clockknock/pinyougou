package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.TbSpecificationMapper
import com.pinyougou.mapper.TbSpecificationOptionMapper
import com.pinyougou.pojo.TbSpecification
import com.pinyougou.pojo.TbSpecificationOptionExample
import com.pinyougou.pojogroup.Specification
import com.pinyougou.sellergoods.service.SpecificationService
import org.springframework.beans.factory.annotation.Autowired
import priv.zhong.bean.PageResult
import com.pinyougou.pojo.TbSpecificationExample
import java.util.ArrayList

/**
 * @author 钟未鸣
 * @date 2018/3/31
 */
@Service
class SpecificationServiceImpl : SpecificationService {
    override fun add(specification: Specification) {
        specificationMapper.insert(specification.specification)
        specification.specificationOptionList?.forEach {
            it.specId=specification.specification?.id
            specificationOptionMapper.insert(it)
        }
    }

    override fun findPage(specification: TbSpecification, pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)

        val example = TbSpecificationExample()
        val criteria = example.createCriteria()

        if (specification.specName !=null && specification.specName.isNotBlank()) {
            criteria.andSpecNameLike("%" + specification.specName + "%")
        }
        val page = specificationMapper.selectByExample(example) as Page<TbSpecification>
        val specList = ArrayList<Specification>()
        page.result.forEach {tbSpec->
            val spec = Specification()
            spec.specification = tbSpec
            val optionExample=TbSpecificationOptionExample()
            optionExample.createCriteria().andSpecIdEqualTo(tbSpec.id)
            spec.specificationOptionList = specificationOptionMapper.selectByExample(optionExample)

            specList.add(spec)
        }


        return PageResult(page.total, specList)
    }

    override fun findPage(pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)

        val page = specificationMapper.selectByExample(null) as Page<TbSpecification>
        return PageResult(page.total, page.result)
    }

    override fun findAll(): List<TbSpecification> {
        return specificationMapper.selectByExample(null)

    }


    @Autowired
    lateinit var specificationMapper: TbSpecificationMapper
    @Autowired
    lateinit var specificationOptionMapper: TbSpecificationOptionMapper
}