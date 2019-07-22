package com.qf.datasource_more_demo.datasource;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/22 16:14
 */
public class DataSourceSelector {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setLocal(String local){
        threadLocal.set(local);
    }

    public static String getLocal(){
        return threadLocal.get();
    }
}
