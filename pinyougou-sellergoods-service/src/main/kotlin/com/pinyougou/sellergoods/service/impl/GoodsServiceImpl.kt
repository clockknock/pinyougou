package com.pinyougou.sellergoods.service.impl

import com.alibaba.dubbo.config.annotation.Service
import com.alibaba.fastjson.JSON
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.pinyougou.mapper.*
import com.pinyougou.pojo.*
import com.pinyougou.pojogroup.Goods
import com.pinyougou.sellergoods.service.GoodsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.transaction.annotation.Transactional
import priv.zhong.bean.PageResult
import java.util.*

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
        val tbGoods = goods.goods
//        val name = SecurityContextHolder.getContext().authentication.name
        tbGoods?.sellerId = tbGoods?.sellerId
        goodsMapper.insert(tbGoods)
        goods.goodsDesc?.goodsId = tbGoods?.id
        goodsDescMapper.insert(goods.goodsDesc)

        updateItemList(goods, tbGoods)

    }

    private fun updateItemList(goods: Goods, tbGoods: TbGoods?) {
        goods.itemList?.forEach { item ->
            if ("1" == tbGoods?.isEnableSpec) {
                //允许规格
                //标题
                val specs = JSON.parseObject(item.spec) as Map<*, *>
                item.title = tbGoods.goodsName + specs.values.joinToString(separator = "")
                setItemValues(item, tbGoods, goods)

            } else {

                item.title = tbGoods?.goodsName
                item.price = tbGoods?.price
                item.isDefault = "1"
                item.status = "1"
                item.num = 99
                item.spec = "{}"
                setItemValues(item, tbGoods, goods)
            }

            itemMapper.insert(item)

        }
    }

    /**
     * 设置tbItem公有属性
     */
    private fun setItemValues(item: TbItem, tbGoods: TbGoods?, goods: Goods) {
        //设置对应的goodId
        item.goodsId = tbGoods?.id
        //品牌
        item.brand = brandMapper.selectByPrimaryKey(tbGoods?.brandId).name
        //分类id
        item.categoryid = tbGoods?.category3Id
        //分类名
        item.category = itemCatMapper.selectByPrimaryKey(tbGoods?.category3Id).name
        //创建日期
        item.createTime = Date()
        //卖家
        item.seller = sellerMapper.selectByPrimaryKey(tbGoods?.sellerId).name
        //卖家Id
        item.sellerId = tbGoods?.sellerId
        //修改日期
        item.updateTime = Date()

        //商品图片
        @Suppress("UNCHECKED_CAST")
        val imageList = JSON.parseArray(goods.goodsDesc?.itemImages) as List<Map<*, *>>
        if (imageList.isNotEmpty()) {
            val map = imageList[0]
            item.image = map["url"] as String?
        }
    }


    /**
     * 修改
     */
    override fun update(goods: Goods) {
        val tbGoods = goods.goods
        //更新tbGoods
        goodsMapper.updateByPrimaryKey(tbGoods)
        //更新goodsDesc
        goodsDescMapper.updateByPrimaryKey(goods.goodsDesc)
        //更新itemList
        //先删除旧的itemList
        val itemExample = TbItemExample()
        itemExample.createCriteria().andGoodsIdEqualTo(tbGoods?.id)
        itemMapper.deleteByExample(itemExample)

        updateItemList(goods, tbGoods)

    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    override fun findOne(id: Long?): Goods {
        val goods = Goods()
        //封装tbGoods
        goods.goods = goodsMapper.selectByPrimaryKey(id)
        //封装goodsDesc
        goods.goodsDesc = goodsDescMapper.selectByPrimaryKey(id)
        //封装tbItem
        val itemExample = TbItemExample()
        itemExample.createCriteria().andGoodsIdEqualTo(id)
        goods.itemList=itemMapper.selectByExample(itemExample)

        return goods
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
            if (goods.sellerId != null && goods.sellerId.isNotEmpty()) {
                criteria.andSellerIdEqualTo( goods.sellerId )
            }
            if (goods.goodsName != null && goods.goodsName.isNotEmpty()) {
                criteria.andGoodsNameLike("%" + goods.goodsName + "%")
            }
            if (goods.auditStatus != null && goods.auditStatus.isNotEmpty()) {
                criteria.andAuditStatusLike("%" + goods.auditStatus + "%")
            }
            if (goods.isMarketable != null && goods.isMarketable.isNotEmpty()) {
                criteria.andIsMarketableLike("%" + goods.isMarketable + "%")
            }
            if (goods.caption != null && goods.caption.isNotEmpty()) {
                criteria.andCaptionLike("%" + goods.caption + "%")
            }
            if (goods.smallPic != null && goods.smallPic.isNotEmpty()) {
                criteria.andSmallPicLike("%" + goods.smallPic + "%")
            }
            if (goods.isEnableSpec != null && goods.isEnableSpec.isNotEmpty()) {
                criteria.andIsEnableSpecLike("%" + goods.isEnableSpec + "%")
            }
            if (goods.isDelete != null && goods.isDelete.isNotEmpty()) {
                criteria.andIsDeleteLike("%" + goods.isDelete + "%")
            }

        }

        val page = goodsMapper.selectByExample(example) as Page<TbGoods>
        return PageResult(page.total, page.result)
    }

    @Autowired
    lateinit var itemMapper: TbItemMapper
    @Autowired
    lateinit var brandMapper: TbBrandMapper
    @Autowired
    lateinit var itemCatMapper: TbItemCatMapper
    @Autowired
    lateinit var sellerMapper: TbSellerMapper

}