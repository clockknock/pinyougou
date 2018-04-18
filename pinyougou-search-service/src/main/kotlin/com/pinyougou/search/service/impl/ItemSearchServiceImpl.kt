package com.pinyougou.search.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.pojo.TbItem
import com.pinyougou.search.service.ItemSearchService
import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.solr.core.SolrTemplate
import org.springframework.data.solr.core.query.*

/**
 * @author 钟未鸣
 * @date 2018/4/17
 */
@Service(timeout = 5000)
class ItemSearchServiceImpl : ItemSearchService {
    @Autowired
    private lateinit var solrTemplate: SolrTemplate

    override fun search(searchMap: MutableMap<String, Any>): MutableMap<String, Any> {
        //声明返回的map
        val resultMap = HashMap<String, Any>()

        //关键字高亮查询
        resultMap.putAll(highLightQuery(searchMap))

        return resultMap
    }


    private fun highLightQuery(searchMap: MutableMap<String, Any>): MutableMap<String, Any> {
        val highlightQuery = SimpleHighlightQuery()
        //1.初始化高亮选项
        val options = HighlightOptions().apply {
            addField("item_title")
            simplePrefix = "<em style='color:red'>"
            simplePostfix = "</em>"
        }
        highlightQuery.setHighlightOptions<HighlightQuery>(options)

        highlightQuery.addCriteria<SimpleQuery>(Criteria("item_keywords").`is`
        (searchMap["keywords"]))

        val highlightPage = solrTemplate.queryForHighlightPage(highlightQuery, TbItem::class.java)
        val highlighted = highlightPage.highlighted
        highlighted.forEach { entry ->
            //获得高亮列表
            if (entry.highlights.isNotEmpty()) {
                //将高亮内容放入contents
                entry.entity.title = entry.highlights[0].snipplets[0]
            }
        }

        return mutableMapOf(Pair("rows", highlightPage.content))

    }

}