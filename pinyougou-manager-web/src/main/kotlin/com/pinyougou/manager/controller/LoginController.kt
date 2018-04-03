package com.pinyougou.manager.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sun.plugin.liveconnect.SecurityContextHelper

/**
 * @author 钟未鸣
 * @date 2018/4/3
 */
@RestController
@RequestMapping("/login")
class LoginController {

    @RequestMapping("/name")
    fun name(): Map<*, *> {
        val map = HashMap<String, String>()
        map["loginName"] = SecurityContextHolder.getContext().authentication.name
        return map
    }

}