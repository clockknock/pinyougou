package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.github.pagehelper.Page
import org.springframework.web.bind.annotation.RequestMapping
import com.pinyougou.pojo.TbSpecification
import com.pinyougou.sellergoods.service.SpecificationService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult


/**
 * @author 钟未鸣
 * @date 2018/3/31
 */
/**
 * controller
 * @author Administrator
 */
@RestController
@RequestMapping("/specification")
class SpecificationController {

    @Reference
    lateinit var service: SpecificationService

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    fun findAll(): List<TbSpecification> {
        return service.findAll()
    }

    @RequestMapping("/findPage")
    fun findPage(page:Int,rows:Int):PageResult{
        return service.findPage(page,rows)
    }

    @RequestMapping("/search")
    fun findPage(@RequestBody specification: TbSpecification, page:Int,rows:Int):PageResult{
        return service.findPage(specification,page,rows)
    }

}
