package com.pinyougou.sellergoods.service;

import java.util.List;

import com.pinyougou.pojo.TbGoods;

import com.pinyougou.pojogroup.Goods;
import priv.zhong.bean.PageResult;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface GoodsService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbGoods> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(Goods goods);


    /**
     * 修改
     */
    public void update(Goods goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public Goods findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    PageResult findPage(TbGoods goods, int pageNum, int pageSize);


    /**
     * 更新商品描述
     * @param ids    审核了哪些商品
     * @param status 0:未审核 1:审核通过 2:驳回 3:关闭
     */
    void updateStatus(Long[] ids,String status);

}
