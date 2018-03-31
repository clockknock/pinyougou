package com.pinyougou.sellergoods.service

import com.pinyougou.pojo.TbSpecification
import com.pinyougou.pojogroup.Specification
import priv.zhong.bean.PageResult


/**
 * @author 钟未鸣
 * @date 2018/3/31
 */

interface SpecificationService {

    /**
     * 返回全部列表
     * @return
     */
    fun findAll(): List<TbSpecification>



    /**
     * 返回分页列表
     * @return
     */
    fun findPage(pageNum: Int, pageSize: Int): PageResult



    /**
     * 增加
     */
    fun add(specification: Specification)


    /**
     * 修改
     *//*
    fun update(specification: Specification)


    */
    /**
     * 根据ID获取实体
     * @param id
     * @return
     *//*
    fun findOne(id: Long?): Specification


    */
    /**
     * 批量删除
     * @param ids
     *//*
    fun delete(ids: Array<Long>)

    */
    /**
     * 分页
     * @param pageNum 当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    fun findPage(specification: TbSpecification, pageNum: Int, pageSize: Int): PageResult


//    fun selectOptionList(): List<Map<*, *>>

}
