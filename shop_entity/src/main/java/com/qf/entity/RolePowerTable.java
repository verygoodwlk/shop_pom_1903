package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/2 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePowerTable implements Serializable {

    private Integer rid;
    private Integer pid;
}
