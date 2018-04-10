package com.pinyougou.manager

import org.junit.Test

/**
 * @author 钟未鸣
 * @date 2018/4/10
 */
class TestRows {
    private val target = mutableListOf<String>()
    @Test
    fun descartes() {
        val names = arrayOf("张三", "李四", "王五")
        val course = arrayOf("语文", "数学")

        val descartesArr = arrayListOf(names, course)

        for( i in 0 until descartesArr.size){
            addRow(descartesArr[i])
        }

        target.forEach { println(it) }
    }

    private fun addRow(descartesArr: Array<String>){

    }

}