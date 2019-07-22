package com.qf.datasource_more_demo.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * @version 1.0
 * @user ken
 * @date 2019/7/22 15:42
 */
@Configuration
public class MyBatisConfig {

    /**
     * 数据源1的配置
     */
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 数据源2的配置
     */
    @Value("${spring.datasource2.url}")
    private String jdbcUrl2;
    @Value("${spring.datasource2.username}")
    private String username2;
    @Value("${spring.datasource2.password}")
    private String password2;
    @Value("${spring.datasource2.driver-class-name}")
    private String driverClassName2;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;


    /**
     * 手动配置数据源
     * @return
     */
    @Bean
    public DataSource getDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcUrl);
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(driverClassName);
        return hikariDataSource;
    }

    @Bean
    public DataSource getDataSource2(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(jdbcUrl2);
        hikariDataSource.setUsername(username2);
        hikariDataSource.setPassword(password2);
        hikariDataSource.setDriverClassName(driverClassName2);
        return hikariDataSource;
    }

    /**
     * 返回动态数据源
     * @return
     */
    @Bean
    public DataSource getDynamicDataSource(DataSource getDataSource, DataSource getDataSource2){
        Map<Object, Object> map = new HashMap<>();
        map.put("db1", getDataSource);
        map.put("db2", getDataSource2);

        //创建一个动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //默认使用第一个数据源
        dynamicDataSource.setDefaultTargetDataSource(getDataSource);
        //给动态数据源管理所有的数据源
        dynamicDataSource.setTargetDataSources(map);

        return dynamicDataSource;
    }


    @Bean
    public SqlSessionFactoryBean getSqlSessionFactory(DataSource getDynamicDataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //注入数据源
        sqlSessionFactoryBean.setDataSource(getDynamicDataSource);
        //配置映射文件的扫描路径
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sqlSessionFactoryBean;
    }

}
