package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 14:21
 */
public interface OrderMapper extends BaseMapper<Order> {

    int insertOrder(@Param("order") Order order, @Param("tableIndex") int tableIndex);
}
