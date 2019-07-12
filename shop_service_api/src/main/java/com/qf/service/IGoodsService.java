package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface IGoodsService {

    List<Goods> queryGoodsList();

    Goods insertGoods(Goods goods);

    Goods queryById(Integer gid);
}
