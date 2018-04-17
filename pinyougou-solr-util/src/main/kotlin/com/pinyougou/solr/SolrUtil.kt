@file:Suppress("SpringKotlinAutowiring")

package com.pinyougou.solr

import com.alibaba.fastjson.JSON
import com.pinyougou.mapper.TbItemMapper
import com.pinyougou.pojo.TbItem
import com.pinyougou.pojo.TbItemExample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.data.solr.core.SolrTemplate
import org.springframework.data.solr.core.query.Criteria
import org.springframework.data.solr.core.query.Query
import org.springframework.data.solr.core.query.SimpleQuery
import org.springframework.stereotype.Component
import java.math.BigDecimal

/**
 * @author 钟未鸣
 * @date 2018/4/17
 */
@Component
class SolrUtil {

    fun testSave() {
        val mutableList = mutableListOf<TbItem>()
        for (i in 0 until 100) {
             TbItem().apply {
                id = i + 1L
                brand = "Sonia$i"
                price = BigDecimal(4399.99)
                title = "好货$i"
                goodsId = i + 1L
                seller = "卖家$i"
                category = "手机$i"
                mutableList.add(this)
            }

        }
        solrTemplate.saveBeans(mutableList)
        solrTemplate.commit()
    }

    fun testQuery() {
        val query = SimpleQuery()
        //设置搜索条件名
        val titleCriteria = Criteria("item_keywords")
        //该条件包含字段
        titleCriteria.contains("Sonia")
        //添加到query中
        query.addCriteria<SimpleQuery>(titleCriteria)
        //指定query对象和返回类字节码
        val page = solrTemplate.queryForPage(query, TbItem::class.java)
        println("totalPage:${page.totalPages}")
        println("maxScore:${page.maxScore}")
        for (tbItem in page.content) {
            println("title:${tbItem.title},category:${tbItem.category}")
        }
    }

    fun testQueryAll() {
        val query = SimpleQuery("*:*")
        //指定分页查找的每页数
        query.setRows<Query>(10)
        query.setOffset<Query>(10)

        //指定query对象和返回类字节码
        val page = solrTemplate.queryForPage(query, TbItem::class.java)
        println("totalPage:${page.totalPages}")
        println("maxScore:${page.number}")
        for (tbItem in page.content) {
            println("title:${tbItem.title},category:${tbItem.category}")
        }
    }

    fun testDelete() {
        val query = SimpleQuery("*:*")

        solrTemplate.delete(query)
        solrTemplate.commit()
    }


    fun importItemData() {
        val example = TbItemExample()
        example.createCriteria().andStatusEqualTo("1")
        val mutableList = mutableListOf<TbItem>()
        val list = itemMapper.selectByExample(example)
        for (tbItem in list) {
            tbItem.specMap = JSON.parseObject(tbItem.spec, Map::class.java) as MutableMap<String, String>?

            mutableList.add(tbItem)
        }

        solrTemplate.saveBeans(mutableList)
        solrTemplate.commit()
    }

    @SuppressWarnings("All")
    @Autowired
    private lateinit var itemMapper: TbItemMapper
    @Autowired
    private lateinit var solrTemplate: SolrTemplate

}

fun main(args: Array<String>) {
    val applicationContext = ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml")
    val before = System.currentTimeMillis()
    val solrUtil = applicationContext.getBean("solrUtil") as SolrUtil
    solrUtil.testQueryAll()
    val after = System.currentTimeMillis()
    println(after-before)
}