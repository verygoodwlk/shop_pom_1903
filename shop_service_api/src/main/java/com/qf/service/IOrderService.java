package com.qf.service;

import com.qf.entity.Order;
import com.qf.entity.User;

import java.util.List;

public interface IOrderService  {

    int insertOrder(Integer aid, User user);

    List<Order> queryByUid(Integer uid);

    Order queryByOid(String orderid);
}
