package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 14:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetils implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String orderid;
    private Integer gid;
    private String gname;
    private BigDecimal gprice;
    private String gimage;
    private Integer gnumber;
    private BigDecimal sprice;
}
