package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/19 14:32
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    /**
     * 自定义注解 + AOP
     *
     * 添加购物车
     * @return
     */
    @IsLogin
    @RequestMapping("/insert")
    public String addCart(Integer gid, Integer gnumber, User user){

        System.out.println("是否已经登录：" + user);
        System.out.println("添加购物车的商品：" + gid + "  " + gnumber);

        return "succ";
    }

//    public static void main(String[] args) throws Exception {
//        Class<CartController> cClass = CartController.class;
//
//        Method method = cClass.getMethod("addCart", Integer.class, Integer.class);
//
//        IsLogin isLogin = method.getAnnotation(IsLogin.class);
//
//        int v = isLogin.value();
//        String[] ss = isLogin.abc();
//
//        System.out.println(v + "  " + Arrays.toString(ss));
//    }
}
