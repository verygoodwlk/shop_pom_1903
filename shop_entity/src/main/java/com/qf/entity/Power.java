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
 * @date 2019/7/2 9:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Power implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid = -1;
    private String powername;
    private String powerpath;
    private Date createtime = new Date();
    private Integer status;

    @TableField(exist = false)
    private String pname;

    @TableField(exist = false)
    private boolean checked;
}
