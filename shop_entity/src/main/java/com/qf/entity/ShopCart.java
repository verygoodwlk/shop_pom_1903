package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @version 1.0
 * @user ken
 * @date 2019/7/22 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCart implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private Integer gid;
    private Integer gnumber;
    private BigDecimal sprice;//小计
    private Date createtime;

    @TableField(exist = false)
    private Goods goods;
}
