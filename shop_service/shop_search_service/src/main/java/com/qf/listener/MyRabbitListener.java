package com.qf.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/12 16:59
 */
@Component
public class MyRabbitListener {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(queues = "search_queue")
    public void msgHander(Goods goods){

        //同步到索引库
        searchService.addGoods(goods);
    }
}
