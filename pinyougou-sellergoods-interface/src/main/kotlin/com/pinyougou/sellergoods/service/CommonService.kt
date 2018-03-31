package com.pinyougou.sellergoods.service

import priv.zhong.bean.PageResult

/**
 * @author 钟未鸣
 * @date 2018/3/31
 * 用于自动注入时不确定子类Service时的声明
 */
interface CommonService<T>{
    fun findAll(): List<T>

    /**
     * @param pageNum 当前页数
     * @param pageSize 页面大小
     */
    fun findPage(pageNum:Int,pageSize:Int): PageResult

    fun add(obj : T)

    fun update(obj: T)

    fun delete(ids: Array<Long>)

    /**
     * @param brand 查询条件的实体类
     * @param pageNum 当前页数
     * @param pageSize 页面大小
     */
    fun findPage(obj: T, pageNum:Int, pageSize:Int): PageResult
}