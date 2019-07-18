package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/18 11:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {

    private String to;//发送给谁
    private String subject;//标题
    private String content;//内容
}
