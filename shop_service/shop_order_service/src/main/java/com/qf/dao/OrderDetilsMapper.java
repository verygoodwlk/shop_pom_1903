package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.OrderDetils;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 14:22
 */
public interface OrderDetilsMapper extends BaseMapper<OrderDetils> {

    int insertDetils(@Param("orderDetils") List<OrderDetils> orderDetils,
                     @Param("tableIndex")int tableIndex);
}
