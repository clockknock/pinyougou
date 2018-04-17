package com.pinyougou.search.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.pojo.TbItem
import com.pinyougou.search.service.ItemSearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.solr.core.SolrTemplate
import org.springframework.data.solr.core.query.Criteria
import org.springframework.data.solr.core.query.Query
import org.springframework.data.solr.core.query.SimpleQuery

/**
 * @author 钟未鸣
 * @date 2018/4/17
 */
@Service
class ItemSearchServiceImpl : ItemSearchService{
    @Autowired
    private lateinit var solrTemplate: SolrTemplate

    override fun search(searchMap: MutableMap<String, Any>): MutableMap<String, Any> {
        //声明返回的map
        val resultMap = mutableMapOf<String, Any>()

        val query = SimpleQuery()
        val criteria = Criteria("item_keywords").apply {
            `is`(searchMap["keywords"])
        }
        query.addCriteria<Query>(criteria)
        val scoredPage = solrTemplate.queryForPage(query, TbItem::class.java)
        resultMap["rows"]=scoredPage.content

        return resultMap
    }

}