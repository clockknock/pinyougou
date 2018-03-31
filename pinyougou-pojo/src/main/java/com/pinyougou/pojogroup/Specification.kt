package com.pinyougou.pojogroup

import com.pinyougou.pojo.TbSpecificationOption
import com.pinyougou.pojo.TbSpecification
import java.io.Serializable



/**
 * @author 钟未鸣
 * @date 2018/3/31
 */
class Specification : Serializable {

    var specification: TbSpecification? = null

    var specificationOptionList: List<TbSpecificationOption>? = null


}
