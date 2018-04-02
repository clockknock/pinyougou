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
    override fun findSpecList(): List<Map<*, *>> {
        return specificationMapper.findSpecList()
    }

    override fun delete(ids: Array<Long>) {
        for (id in ids) {
            specificationMapper.deleteByPrimaryKey(id)
            val example = TbSpecificationOptionExample()
            example.createCriteria().andSpecIdEqualTo(id)
            specificationOptionMapper.deleteByExample(example)
        }
    }

    override fun update(specification: Specification) {

        val tbSpecification = specification.specification
        specificationMapper.updateByPrimaryKey(tbSpecification)

        //tbSpec相关的option们修改起来麻烦,我们选择将之前的option先删了再添加
        val example = TbSpecificationOptionExample()
        example.createCriteria().andSpecIdEqualTo(tbSpecification?.id)
        specificationOptionMapper.deleteByExample(example)

        //再把option们加进去
        specification.specificationOptionList?.forEach {
            it.specId = tbSpecification?.id
            specificationOptionMapper.insert(it)
        }
    }

    override fun add(specification: Specification) {
        specificationMapper.insert(specification.specification)
        specification.specificationOptionList?.forEach {
            //specOption对象的tbSpec的id一开始没有,我们需要去
            // TbSpecificationMapper.xml的insert中增加一条查询自增id的语句让tbSpec有id
            it.specId = specification.specification?.id
            specificationOptionMapper.insert(it)
        }
    }

    override fun findPage(specification: TbSpecification, pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)

        val example = TbSpecificationExample()
        val criteria = example.createCriteria()

        if (specification.specName != null && specification.specName.isNotBlank()) {
            criteria.andSpecNameLike("%" + specification.specName + "%")
        }
        val page = specificationMapper.selectByExample(example) as Page<TbSpecification>
        val specList = ArrayList<Specification>()
        page.result.forEach { tbSpec ->
            val spec = Specification()
            spec.specification = tbSpec
            val optionExample = TbSpecificationOptionExample()
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