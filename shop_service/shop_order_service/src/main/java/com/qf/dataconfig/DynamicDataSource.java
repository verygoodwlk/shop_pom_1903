package com.qf.dataconfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 15:59
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("获得动态数据源的关键字：" + threadLocal.get());
        return threadLocal.get();
    }

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String datasourceKeywork){
        threadLocal.set(datasourceKeywork);
    }
}
