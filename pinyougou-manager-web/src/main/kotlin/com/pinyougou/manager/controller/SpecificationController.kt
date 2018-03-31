package com.pinyougou.manager.controller

import com.alibaba.dubbo.config.annotation.Reference
import com.github.pagehelper.Page
import org.springframework.web.bind.annotation.RequestMapping
import com.pinyougou.pojo.TbSpecification
import com.pinyougou.pojogroup.Specification
import com.pinyougou.sellergoods.service.SpecificationService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import priv.zhong.bean.PageResult
import priv.zhong.bean.Result


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
    fun findPage(page: Int, rows: Int): PageResult {
        return service.findPage(page, rows)
    }

    @RequestMapping("/search")
    fun findPage(@RequestBody specification: TbSpecification, page: Int, rows: Int): PageResult {
        return service.findPage(specification, page, rows)
    }

    @RequestMapping("/add")
    fun save(@RequestBody specification: Specification): Result {
        return try {
            service.add(specification)
            Result(true, "添加成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "添加失败")
        }
    }

    @RequestMapping("/update")
    fun update(@RequestBody specification: Specification): Result {
        return try {
            service.update(specification)
            Result(true, "修改成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "修改失败")
        }
    }

    @RequestMapping("/delete")
    fun delete(ids: Array<Long>): Result {
        return try {
            service.delete(ids)
            Result(true, "删除成功")
        } catch (e: Exception) {
            e.printStackTrace()
            Result(false, "删除失败")
        }
    }

}
