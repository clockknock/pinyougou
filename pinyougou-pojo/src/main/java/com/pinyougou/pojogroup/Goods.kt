package com.pinyougou.pojogroup

import com.pinyougou.pojo.TbGoods
import com.pinyougou.pojo.TbGoodsDesc
import java.io.Serializable
import com.pinyougou.pojo.TbItem

/**
 * @author 钟未鸣
 * @date 2018/4/6
 */
class Goods : Serializable {
     var goods: TbGoods? = null
     var goodsDesc: TbGoodsDesc? = null
     var itemList: List<TbItem>? = null//商品SKU列表	'
    override fun toString(): String {
        return "Goods(goods=${goods?.id}, goodsDesc=$goodsDesc)"
    }

}