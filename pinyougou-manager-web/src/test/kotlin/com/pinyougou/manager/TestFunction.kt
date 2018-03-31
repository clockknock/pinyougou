package com.pinyougou.manager

import org.junit.Test

/**
 * @author 钟未鸣
 * @date 2018/3/29
 */
class TestFunction{
    @Test
    fun testList(){
        val list = ArrayList<Int>(10)
        list.add(1)
        list.add(2)
        list.add(3)
        println(list.size)
    }

    @Test
    fun testIsNotEmpty(){
        val s=" "
      println(s.isNotEmpty())
      println(s.isNotBlank())
    }
}