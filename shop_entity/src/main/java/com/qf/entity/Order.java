package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 14:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Order implements Serializable {

    @TableId(type = IdType.INPUT)
    private String orderid;
    private String person;
    private String address;
    private String phone;
    private BigDecimal allprice;
    private Date createtime;
    private Integer status;//0 - 未支付 1 - 已支付/待发货  2 - 已发货 3 - 已收货
    private Integer uid;

    @TableField(exist = false)
    private List<OrderDetils> orderDetils;
}
