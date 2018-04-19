package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.TbItemCatMapper
import com.pinyougou.pojo.TbItemCat
import com.pinyougou.pojo.TbItemCatExample
import com.pinyougou.sellergoods.service.ItemCatService
import javafx.application.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.transaction.annotation.Transactional
import priv.zhong.bean.PageResult
import utils.ApplicationConstants


/**
 * @author 钟未鸣
 * @date 2018/4/18
 */

@Service
@Transactional
open class ItemCatServiceImpl : ItemCatService {

    @Autowired
    private lateinit var itemCatMapper: TbItemCatMapper

    /**
     * 查询全部
     */
    override fun findAll(): List<TbItemCat> {
        return itemCatMapper.selectByExample(null)
    }

    /**
     * 按分页查询
     */
    override fun findPage(pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)
        val page = itemCatMapper.selectByExample(null) as Page<TbItemCat>
        return PageResult(page.total, page.result)
    }

    /**
     * 增加
     */
    override fun add(itemCat: TbItemCat) {
        itemCatMapper.insert(itemCat)
    }


    /**
     * 修改
     */
    override fun update(itemCat: TbItemCat) {
        itemCatMapper.updateByPrimaryKey(itemCat)
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    override fun findOne(id: Long?): TbItemCat {
        return itemCatMapper.selectByPrimaryKey(id)
    }

    /**
     * 批量删除
     */
    override fun delete(ids: Array<Long>) {
        for (id in ids) {
            val example = TbItemCatExample()
            val criteria = example.createCriteria()
            criteria.andParentIdEqualTo(id)
            val tbItemCats = itemCatMapper.selectByExample(example)
            if (tbItemCats != null && tbItemCats.size > 0) {
                throw RuntimeException("该分类有子分类,不能删")
            }
            itemCatMapper.deleteByPrimaryKey(id)
        }
    }

    override fun findPage(itemCat: TbItemCat?, pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)

        val example = TbItemCatExample()
        val criteria = example.createCriteria()

        if (itemCat != null) {
            if (itemCat.name != null && itemCat.name.isNotEmpty()) {
                criteria.andNameLike("%" + itemCat.name + "%")
            }

        }

        val page = itemCatMapper.selectByExample(example) as Page<TbItemCat>
        val itemCats = page.result

        return PageResult(page.total, itemCats)
    }

    override fun findByParentId(parentId: Long?): List<TbItemCat> {
        val example = TbItemCatExample()
        val criteria = example.createCriteria()
        criteria.andParentIdEqualTo(parentId)

        val list = itemCatMapper.selectByExample(example)

        list.forEach {
            //存入Redis--itemCat.name:itemCat.typeId
            redisTemplate.boundHashOps<Any, Any>(ApplicationConstants.REDIS_ITEMCAT)
                    .put(it.name, it.typeId)
        }
        println("缓存了itemCat")

        return list
    }

    @Autowired
    private
    lateinit var redisTemplate: RedisTemplate<Any, Any>

}