package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 14:46
 */
@Controller
public class LoginController {

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/tologin")
    public String tologin(){
        return "login";
    }

    /**
     * 进行登录
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "index";
    }
}
