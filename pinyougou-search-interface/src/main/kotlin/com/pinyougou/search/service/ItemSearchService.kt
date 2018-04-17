package com.pinyougou.search.service

/**
 * @author 钟未鸣
 * @date 2018/4/17
 */
interface ItemSearchService{
    /**
     *
     * @param searchMap MutableMap<*, *> keywords
     * @return MutableMap<*,*>
     */
    fun search(searchMap: MutableMap<String,Any>):MutableMap<String,Any>
}