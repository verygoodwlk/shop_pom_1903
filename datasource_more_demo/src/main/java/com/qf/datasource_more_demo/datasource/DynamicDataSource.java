package com.qf.datasource_more_demo.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @version 1.0
 * @user ken
 * @date 2019/7/22 16:12
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    //切换数据源的核心方法，返回当前需要切换的数据源的标识
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceSelector.getLocal();
    }
}
