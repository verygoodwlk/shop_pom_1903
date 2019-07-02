package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 19:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String rolename;
    private String rolealias;
    private Date createtime;
    private Integer status;

    @TableField(exist = false)
    private boolean checked;
}
