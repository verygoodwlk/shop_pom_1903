package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 表名 xxx_xxx -> XxxXxxx
 *
 * @version 1.0
 * @user ken
 * @date 2019/7/1 15:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer sex;
    private Date createtime = new Date();
    private Integer status;
}
