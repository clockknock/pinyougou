package com.pinyougou.manager

import org.junit.Test

/**
 * @author 钟未鸣
 * @date 2018/3/29
 */
class TestFunction{
    @Test
    fun testIsNotEmpty(){
        val s=" "
      println(s.isNotEmpty())
      println(s.isNotBlank())
    }
}