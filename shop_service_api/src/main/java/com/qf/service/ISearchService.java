package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {

    List<Goods> searchByKey(String keyword);

    int addGoods(Goods goods);
}
