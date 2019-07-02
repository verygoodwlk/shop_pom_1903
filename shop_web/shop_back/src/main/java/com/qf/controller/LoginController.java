package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.BackUser;
import com.qf.service.IBackUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/1 14:46
 */
@Controller
@SessionAttributes("loginUser")
public class LoginController {

    @Reference
    private IBackUserService userService;

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
    public String login(String username, String password, Model model){
        BackUser user = userService.login(username, password);

        if(user != null){
            //登录成功 - 放入session中
            model.addAttribute("loginUser", user);
            return "index";
        }

        return "redirect:/tologin?error=1";
    }
}
