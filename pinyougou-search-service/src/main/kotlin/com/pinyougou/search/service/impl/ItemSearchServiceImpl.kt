package com.pinyougou.search.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.pinyougou.pojo.TbItem
import com.pinyougou.search.service.ItemSearchService
import org.apache.commons.collections.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.solr.core.SolrTemplate
import org.springframework.data.solr.core.query.*
import utils.ApplicationConstants

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

        //1.关键字高亮查询以及其他条件过滤
        resultMap.putAll(highLightQuery(searchMap))

        //2.返回分类列表
        val categoryList = searchCategoryList(searchMap)
        resultMap["categoryList"] = categoryList

        //3.从缓存查出specList,brandList
        val category = searchMap["category"] as String
        if (category != "") {
            resultMap.putAll(searchBrandListAndSpecList(category))
        } else {
            resultMap.putAll(searchBrandListAndSpecList(categoryList[0]))
        }

        return resultMap
    }


    private fun searchBrandListAndSpecList(category: String): HashMap<String, Any> {
        val resultMap = HashMap<String, Any>()

        val catId = redisTemplate.boundHashOps<String, Any>(ApplicationConstants
                .REDIS_ITEMCAT).get(category) as Long?

        if (catId != null) {
            val brandList = redisTemplate.boundHashOps<String, List<Any>>(ApplicationConstants
                    .REDIS_BRANDLIST).get(catId)
            resultMap["brandList"] = brandList

            val specList = redisTemplate.boundHashOps<String, List<Any>>(ApplicationConstants
                    .REDIS_SPECLIST).get(catId)
            resultMap["specList"] = specList

        }

        return resultMap
    }


    /**
     * 根据关键字高亮查询
     * @param searchMap MutableMap<String, Any>  searchMap["keywords"]取关键字
     * @return MutableMap<String, Any>
     */
    private fun highLightQuery(searchMap: MutableMap<String, Any>): MutableMap<String, Any> {
        val highlightQuery = SimpleHighlightQuery()
        //1.1 初始化高亮选项
        val options = HighlightOptions().apply {
            addField("item_title")
            simplePrefix = "<em style='color:red'>"
            simplePostfix = "</em>"
        }
        highlightQuery.setHighlightOptions<HighlightQuery>(options)

        //1.2 高亮关键字
        highlightQuery.addCriteria<SimpleQuery>(Criteria("item_keywords").`is`
        (searchMap["keywords"]))

        //1.3过滤搜索条件

        val brand = searchMap["brand"] as String?
        if (brand != null && brand.isNotBlank()) {
            val filterQuery = SimpleFilterQuery()
            filterQuery.addCriteria<SimpleQuery>(Criteria("item_brand").`is`
            (brand))
            highlightQuery.addFilterQuery<Query>(filterQuery)
        }
        val category = searchMap["category"] as String?
        if (category != null && category.isNotBlank()) {
            val filterQuery = SimpleFilterQuery()
            filterQuery.addCriteria<SimpleQuery>(Criteria("item_category").`is`
            (category))
            highlightQuery.addFilterQuery<Query>(filterQuery)
        }
        val spec = searchMap["spec"] as Map<String, String>?
        if (spec != null && spec.isNotEmpty()) {
            spec.forEach {
                val filterQuery = SimpleFilterQuery()
                filterQuery.addCriteria<SimpleQuery>(Criteria("item_spec_${it.key}").`is`
                (it.value))
                highlightQuery.addFilterQuery<Query>(filterQuery)
            }
        }

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

    private fun searchCategoryList(searchMap: MutableMap<String, Any>): ArrayList<String> {
        val categoryList = ArrayList<String>()
        val query = SimpleQuery("*:*")
        //设置groupOptions
        val options = GroupOptions().apply {
            addGroupByField("item_category")
        }
        query.setGroupOptions<Query>(options)

        val criteria = Criteria("item_keywords")
        criteria.`is`(searchMap["keywords"])

        query.addCriteria<Query>(criteria)
        val groupPage = solrTemplate.queryForGroupPage(query, TbItem::class.java)
        val groupResult = groupPage.getGroupResult("item_category")
        groupResult.groupEntries.content.forEach {
            categoryList.add(it.groupValue)
        }
        return categoryList
    }

    @Autowired
    lateinit var redisTemplate: RedisTemplate<Any, Any>
}