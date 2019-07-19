package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.qf.entity.Email;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/18 10:58
 */
@Controller
@RequestMapping("/sso")
public class SSOController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private IUserService userService;

    /**
     * 去到登录页
     * @return
     */
    @RequestMapping("/tologin")
    public String toLogin(){
        return "login";
    }

    /**
     * 去到注册页面
     * @return
     */
    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }

    /**
     * 去到忘记密码的页面
     * @return
     */
    @RequestMapping("/toForgetPassword")
    public String toForgetPassword(){
        return "forgetPassword";
    }


    /**
     * 发送验证码到指定的邮箱
     * @return
     */
    @RequestMapping("/sendCode")
    @ResponseBody
    public String sendCode(String email){

        //设置邮件的内容
        String content = "注册的验证码为：%d, 如果不是本人操作，请忽略！";
        //生成验证码
        int code = (int)(Math.random() * 9000) + 1000;
        content = String.format(content, code);

        //发送邮件 - 封装邮件的实体类
        Email emailObj = new Email(email, "掏奋网注册验证码", content);

        //将验证码信息放入到redis中
        redisTemplate.opsForValue().set(email + "_code", code);

        //将邮件放入rabbitmq
        rabbitTemplate.convertAndSend("email_exchange", "", emailObj);

        return "succ";
    }

    /**
     * 开始注册
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, int code){

        //判断验证码是否正确
        Integer sendCode = (Integer) redisTemplate.opsForValue().get(user.getEmail() + "_code");

        //判断验证码是否正确
        if(sendCode == null || sendCode != code){
            return "redirect:/sso/toregister?error=-3";//验证码错误
        }

        //进行注册
        int result = userService.register(user);

        if(result > 0){
            //成功,跳转到登录也
            return "redirect:/sso/tologin";
        }

        return "redirect:/sso/toregister?error=" + result;
    }

    /**
     * 发送找回密码的邮件
     * @return
     */
    @RequestMapping("/sendPassMail")
    @ResponseBody
    public Map<String, Object> sendPassMail(String username){

        Map<String, Object> map = new HashMap<>();

        //根据用户名查询用户的邮箱
        User user = userService.queryByUserName(username);

        if(user == null){
            //用户不存在
            map.put("code", "1000");
            return map;
        }

        //用户存在，给用户的邮箱发送邮件

        //生成一个修改密码的凭证
        String token = UUID.randomUUID().toString();
        //将凭证放入redis中
        redisTemplate.opsForValue().set(username + "_token", token);
        redisTemplate.expire(username + "_token", 5, TimeUnit.MINUTES);

        //生成一个url
        String url = "http://localhost:8084/sso/toChangePassword?username=" + username + "&token=" + token;

        Email email = new Email(user.getEmail(), "掏奋网找回密码邮件",
                "找回密码请点击<a href='" + url + "'>这里</a>");

        //发送邮件
        rabbitTemplate.convertAndSend("email_exchange", "", email);

        //邮件发送成功
        String emailStr = user.getEmail();
        int index = emailStr.indexOf("@");

        //设置通知的邮件地址
        String emailStr2 = emailStr.replace(emailStr.substring(3, index), "********");

        //设置去邮箱的地址  12312312412@sina.com
        String gomail = "mail." + emailStr.substring(index + 1);

        map.put("code", "0000");
        map.put("emailStr", emailStr2);
        map.put("goEmail", gomail);
        return map;
    }


    /**
     * 去到修改密码的页面
     * @return
     */
    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "changePassword";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("/changePassword")
    public String changePassword(String password, String username, String token){

        //校验token是否合法
        String uToken = (String) redisTemplate.opsForValue().get(username + "_token");

        //判断token是否相同
        if(token.equals(uToken)){
            //说明可以修改密码
            userService.updatePassword(username, password);

            //删除token凭证
            redisTemplate.delete(username + "_token");

            return "redirect:/sso/tologin";
        }

        return "fail";
    }

    //------------------------------------------------------------------------------------------------------

    /**
     * 登录用户信息
     * @return
     */
    @RequestMapping("/login")
    public String login(User user, String returnUrl, HttpServletResponse response){

        user = userService.login(user);

        if(user == null){
            //登录失败
            return "redirect:/sso/tologin?error=1";
        }

        if(returnUrl == null){
            returnUrl = "http://localhost:8081";
        }

        //登录成功

        //生成令牌
        String token = UUID.randomUUID().toString();

        //将用户信息写入redis
        redisTemplate.opsForValue().set(token, user);
        redisTemplate.expire(token, 7, TimeUnit.DAYS);

        //将令牌写入浏览器的cookie中
        Cookie cookie = new Cookie("loginToken", token);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
//        cookie.setDomain("tf.com");
//        cookie.setSecure(true);
//        cookie.setHttpOnly();
        response.addCookie(cookie);

        return "redirect:" + returnUrl;
    }

    /**
     * 验证是否登录
     * @return
     */
    @RequestMapping("/checkLogin")
    @ResponseBody
//    @CrossOrigin
    public String checkLogin(
            @CookieValue(name = "loginToken", required = false) String loginToken,
            String callback){

        User user = null;

        if(loginToken != null){
            //验证是否登录
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        String userJson = user != null ? JSON.toJSONString(user) : null;

        return callback != null ? callback + "(" + userJson + ")" : userJson;
    }

    /**
     * 请求注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(name = "loginToken", required = false) String loginToken, HttpServletResponse response){

        //删除redis
        if(loginToken != null){
            redisTemplate.delete(loginToken);
        }

        //清空cookie
        Cookie cookie = new Cookie("loginToken", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/sso/tologin";
    }
}
