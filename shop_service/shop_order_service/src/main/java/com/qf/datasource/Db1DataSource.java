package com.qf.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/23 15:53
 */
@Component
@ConfigurationProperties(prefix = "spring.orderdb1.datasource")
public class Db1DataSource extends BaseDataSource {
}
