package com.pinyougou.manager

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.pinyougou.pojo.TbItem
import org.apache.lucene.analysis.util.CharArrayMap
import org.junit.Test

/**
 * @author 钟未鸣
 * @date 2018/4/10
 */
class TestRows {
    private var target = mutableListOf<Array<String>>()


    @Test
    fun descartes() {
        val names = arrayOf("张三", "李四", "王五")
        val course = arrayOf("语文", "数学")

        val descartesArr = arrayListOf(names, course)

        for (i in 0 until descartesArr.size) {
            target = addRow(descartesArr[i])
        }

        target.forEach { println(it) }
    }

    private fun addRow(descartesArr: Array<String>): MutableList<Array<String>> {
        val newList = mutableListOf<Array<String>>()

        for (i in 0 until target.size) {
            val oldRow = target[i]
            for (j in 0 until descartesArr.size) {
                val arrayOf = arrayOf(descartesArr[j])
                newList.add(arrayOf)
            }
        }


        return newList
    }

    @Test
    fun testJsonMap() {
        var str = "[{\"spec\":{\"接口类型\":\"DVI\",\"电视屏幕尺寸\":\"40英寸\",\"能效级别\":\"二级能效\"},\"price\":\"6\",\"num\":\"19\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"DVI\",\"电视屏幕尺寸\":\"40英寸\",\"能效级别\":\"一级能效\"},\"price\":\"66\",\"num\":\"12\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"DVI\",\"电视屏幕尺寸\":\"41英寸\",\"能效级别\":\"二级能效\"},\"price\":\"13\",\"num\":\"123\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"DVI\",\"电视屏幕尺寸\":\"41英寸\",\"能效级别\":\"一级能效\"},\"price\":\"12\",\"num\":\"13\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"HDMI\",\"电视屏幕尺寸\":\"40英寸\",\"能效级别\":\"二级能效\"},\"price\":\"16\",\"num\":\"12\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"HDMI\",\"电视屏幕尺寸\":\"40英寸\",\"能效级别\":\"一级能效\"},\"price\":\"015\",\"num\":\"2\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"HDMI\",\"电视屏幕尺寸\":\"41英寸\",\"能效级别\":\"二级能效\"},\"price\":\"167\",\"num\":\"133\",\"status\":\"1\",\"isDefault\":\"0\"},{\"spec\":{\"接口类型\":\"HDMI\",\"电视屏幕尺寸\":\"41英寸\",\"能效级别\":\"一级能效\"},\"price\":\"133\",\"num\":\"33344\",\"status\":\"1\",\"isDefault\":\"0\"}]"
        val parseArray = JSON.parseArray(str, TbItem::class.java)
        parseArray.forEach { item ->
            val specs = JSON.parseObject(item.spec) as Map<String, String>
            val joinToString = specs.values.joinToString(separator = "")
            println(joinToString)
//            println(flatMap)

        }
    }

    @Test
    fun testJsonListMap() {
        val images = "[{\"color\":\"白色\",\"url\":\"http://193.112.157.22/group1/M00/00/00/rBAAC1rMrICAHCfNAAAQLSsbCsY645.png\"},{\"color\":\"黄色\",\"url\":\"http://193.112.157.22/group1/M00/00/00/rBAAC1rMrImAXtN1AAAToH2309I451.png\"}]"
        val imageList = JSON.parseArray(images) as List<Map<*,*>>
            imageList.forEach { println(it["url"]) }
    }

}