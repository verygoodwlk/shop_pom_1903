package com.qf.aop;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/19 15:01
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {
    boolean mustLogin() default false;
}
