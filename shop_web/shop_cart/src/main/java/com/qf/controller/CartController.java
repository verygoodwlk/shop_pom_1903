package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.aop.IsLogin;
import com.qf.entity.ShopCart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/19 14:32
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    private ICartService cartService;

    /**
     * 自定义注解 + AOP
     *
     * 添加购物车
     * @return
     */
    @IsLogin
    @RequestMapping("/insert")
    public String addCart(
            @CookieValue(value = "cartToken", required = false) String cartToken,
            ShopCart shopCart,
            User user,
            HttpServletResponse response){


//        System.out.println("添加购物车的商品：" + gid + "  " + gnumber);
        if(cartToken == null){
            cartToken = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("cartToken", cartToken);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        //添加购物车
        cartService.insertCart(shopCart, user, cartToken);

        return "succ";
    }

    /**
     * 查询购物车列表
     * @return
     */
    @IsLogin
    @RequestMapping("/list")
    @ResponseBody
    public String cartList(
            @CookieValue(value = "cartToken", required = false) String cartToken,
            User user,
            String callback
        ){

        List<ShopCart> shopCarts = cartService.queryCartList(user, cartToken);

        return callback != null ? callback + "(" + JSON.toJSONString(shopCarts) + ")" : JSON.toJSONString(shopCarts);
    }
}
