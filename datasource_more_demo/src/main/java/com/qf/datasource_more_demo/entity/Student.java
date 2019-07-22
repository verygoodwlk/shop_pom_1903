package com.qf.datasource_more_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/22 15:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    private Integer id;
    private String sname;
    private Integer sage;
    private String sex;
    private Date create_time;

}
