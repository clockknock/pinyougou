package com.pinyougou.solr

import com.pinyougou.pojo.TbItem
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.solr.core.SolrTemplate
import org.springframework.data.solr.core.query.Criteria
import org.springframework.data.solr.core.query.GroupOptions
import org.springframework.data.solr.core.query.Query
import org.springframework.data.solr.core.query.SimpleQuery
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * @author 钟未鸣
 * @date 2018/4/18
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration("classpath*:spring/applicationContext*.xml")
class SolrTest{
    @Autowired
    private lateinit var solrTemplate: SolrTemplate

    @Test
    fun groupQuery(){
        val query = SimpleQuery("*:*")
        //设置groupOptions
        val options = GroupOptions().apply {
            addGroupByField("item_category")
        }
        query.setGroupOptions<Query>(options)

        val criteria = Criteria("item_keywords")
        criteria.`is`("索尼")

        query.addCriteria<Query>(criteria)
        val groupPage = solrTemplate.queryForGroupPage(query, TbItem::class.java)
        val groupResult = groupPage.getGroupResult("item_category")
        groupResult.groupEntries.content.forEach {
            println(it.groupValue)
        }
    }

}