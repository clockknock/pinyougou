package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.TbGoodsDescMapper
import com.pinyougou.mapper.TbGoodsMapper
import com.pinyougou.pojo.TbGoods
import com.pinyougou.pojo.TbGoodsExample
import com.pinyougou.pojogroup.Goods
import com.pinyougou.sellergoods.service.GoodsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import priv.zhong.bean.PageResult

/**
 * @author 钟未鸣
 * @date 2018/4/6
 */

@Service
@Transactional
open class GoodsServiceImpl : GoodsService {

    @Autowired
    lateinit var goodsMapper: TbGoodsMapper
    @Autowired
    lateinit var goodsDescMapper: TbGoodsDescMapper

    /**
     * 查询全部
     */
    override fun findAll(): List<TbGoods> {
        return goodsMapper.selectByExample(null)
    }

    /**
     * 按分页查询
     */
    override fun findPage(pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)
        val page = goodsMapper.selectByExample(null) as Page<TbGoods>
        return PageResult(page.total, page.result)
    }

    /**
     * 增加
     */
    override fun add(goods: Goods) {
        goodsMapper.insert(goods.goods)
        goods.goodsDesc?.goodsId = goods.goods?.id
        goodsDescMapper.insert(goods.goodsDesc)

    }


    /**
     * 修改
     */
    override fun update(goods: TbGoods) {
        goodsMapper.updateByPrimaryKey(goods)
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    override fun findOne(id: Long?): TbGoods {
        return goodsMapper.selectByPrimaryKey(id)
    }

    /**
     * 批量删除
     */
    override fun delete(ids: Array<Long>) {
        for (id in ids) {
            goodsMapper.deleteByPrimaryKey(id)
        }
    }


    override fun findPage(goods: TbGoods?, pageNum: Int, pageSize: Int): PageResult {
        PageHelper.startPage(pageNum, pageSize)

        val example = TbGoodsExample()
        val criteria = example.createCriteria()

        if (goods != null) {
            if (goods.sellerId != null && goods.sellerId.length > 0) {
                criteria.andSellerIdLike("%" + goods.sellerId + "%")
            }
            if (goods.goodsName != null && goods.goodsName.length > 0) {
                criteria.andGoodsNameLike("%" + goods.goodsName + "%")
            }
            if (goods.auditStatus != null && goods.auditStatus.length > 0) {
                criteria.andAuditStatusLike("%" + goods.auditStatus + "%")
            }
            if (goods.isMarketable != null && goods.isMarketable.length > 0) {
                criteria.andIsMarketableLike("%" + goods.isMarketable + "%")
            }
            if (goods.caption != null && goods.caption.length > 0) {
                criteria.andCaptionLike("%" + goods.caption + "%")
            }
            if (goods.smallPic != null && goods.smallPic.length > 0) {
                criteria.andSmallPicLike("%" + goods.smallPic + "%")
            }
            if (goods.isEnableSpec != null && goods.isEnableSpec.length > 0) {
                criteria.andIsEnableSpecLike("%" + goods.isEnableSpec + "%")
            }
            if (goods.isDelete != null && goods.isDelete.length > 0) {
                criteria.andIsDeleteLike("%" + goods.isDelete + "%")
            }

        }

        val page = goodsMapper.selectByExample(example) as Page<TbGoods>
        return PageResult(page.total, page.result)
    }

}