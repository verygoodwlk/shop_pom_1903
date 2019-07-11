package com.qf.serviceimpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/5 9:27
 */
@Service
public class GoodsServiceimpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Reference
    private ISearchService searchService;

    @Override
    public List<Goods> queryGoodsList() {
        return goodsMapper.selectList(null);
    }

    @Override
    public Goods insertGoods(Goods goods) {
        //将商品添加到数据
        goodsMapper.insert(goods);

        //将商品信息添加到索引库
        searchService.addGoods(goods);

        return goods;
    }
}
