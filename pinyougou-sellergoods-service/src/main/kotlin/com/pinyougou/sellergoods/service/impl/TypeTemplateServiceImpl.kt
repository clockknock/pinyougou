package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.alibaba.fastjson.JSON
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.TbSpecificationOptionMapper
import com.pinyougou.mapper.TbTypeTemplateMapper
import com.pinyougou.pojo.TbSpecificationOptionExample
import com.pinyougou.pojo.TbTypeTemplate
import com.pinyougou.pojo.TbTypeTemplateExample
import com.pinyougou.sellergoods.service.TypeTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import priv.zhong.bean.PageResult
import utils.ApplicationConstants

/**
 * @author 钟未鸣
 * @date 2018/4/19
 */
@Service
class TypeTemplateServiceImpl : TypeTemplateService {

    @Autowired
    private lateinit var typeTemplateMapper: TbTypeTemplateMapper

    @Autowired
    private lateinit var specificationOptionMapper: TbSpecificationOptionMapper

    /**
     * 查询全部
     */
    override fun findAll(): List<TbTypeTemplate> {
        return typeTemplateMapper.selectByExample(null)
    }

    /**
     * 按分页查询
     */
    override fun findPage(pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)
        val page = typeTemplateMapper.selectByExample(null) as Page<TbTypeTemplate>

        return PageResult(page.total, page.result)
    }

    /**
     * 缓存brandIdList和specList
     */
    private fun saveToRedis() {
        val list = findAll()
        list.forEach {typeTemplate->
            //品牌id列表
            redisTemplate.boundHashOps<Long,List<Any>>(ApplicationConstants.REDIS_BRANDLIST)
                    .put(typeTemplate.id,JSON.parseArray(typeTemplate.brandIds))
            //规格列表
            redisTemplate.boundHashOps<Long,List<Any>>(ApplicationConstants.REDIS_SPECLIST)
                    .put(typeTemplate.id,findSpecList(typeTemplate.id))

        }
        println("缓存了brandList和specList")
    }

    /**
     * 增加
     */
    override fun add(typeTemplate: TbTypeTemplate) {
        typeTemplateMapper.insert(typeTemplate)
    }


    /**
     * 修改
     */
    override fun update(typeTemplate: TbTypeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate)
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    override fun findOne(id: Long?): TbTypeTemplate {
        return typeTemplateMapper.selectByPrimaryKey(id)
    }

    /**
     * 批量删除
     */
    override fun delete(ids: Array<Long>) {
        for (id in ids) {
            typeTemplateMapper.deleteByPrimaryKey(id)
        }
    }


    override fun findPage(typeTemplate: TbTypeTemplate?, pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)

        val example = TbTypeTemplateExample()
        val criteria = example.createCriteria()

        if (typeTemplate != null) {
            if (typeTemplate.name != null && typeTemplate.name.isNotEmpty()) {
                criteria.andNameLike("%" + typeTemplate.name + "%")
            }
            if (typeTemplate.specIds != null && typeTemplate.specIds.isNotEmpty()) {
                criteria.andSpecIdsLike("%" + typeTemplate.specIds + "%")
            }
            if (typeTemplate.brandIds != null && typeTemplate.brandIds.isNotEmpty()) {
                criteria.andBrandIdsLike("%" + typeTemplate.brandIds + "%")
            }
            if (typeTemplate.customAttributeItems != null && typeTemplate.customAttributeItems.isNotEmpty()) {
                criteria.andCustomAttributeItemsLike("%" + typeTemplate.customAttributeItems + "%")
            }

        }

        //调用findPage时将所有数据存入redis
        val page = typeTemplateMapper.selectByExample(example) as Page<TbTypeTemplate>
        saveToRedis()
        return PageResult(page.total, page.result)
    }

    override fun findTypeTemplateList(): List<Map<*, *>> {
        return typeTemplateMapper.findTypeTemplateList()
    }

    override fun findTypeIdJson(id: Long?): Map<*, *> {
        return typeTemplateMapper.findTypeIdJson(id)
    }

    override fun findSpecList(id: Long?): List<Map<*, *>> {
        //获取对应的typeTemplate
        val tbTypeTemplate = typeTemplateMapper.selectByPrimaryKey(id)
        //tbTypeTemplate.specIds: [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        val specIds = tbTypeTemplate.specIds
        //字符串转对象
        @Suppress("UNCHECKED_CAST")
        val specIdsMap = JSON.parseArray(specIds, MutableMap::class.java) as List<MutableMap<String, Any>>
        for (map in specIdsMap) {

            val example = TbSpecificationOptionExample()
            val criteria = example.createCriteria()
            val i = map["id"] as Int
            criteria.andSpecIdEqualTo(i.toLong())

            val options = specificationOptionMapper.selectByExample(example)
            map["options"] = options
        }
        return specIdsMap
    }

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<Any,Any>


}