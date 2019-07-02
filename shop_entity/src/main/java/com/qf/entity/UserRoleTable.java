package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/2 11:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleTable implements Serializable {

    private Integer uid;
    private Integer rid;
}
