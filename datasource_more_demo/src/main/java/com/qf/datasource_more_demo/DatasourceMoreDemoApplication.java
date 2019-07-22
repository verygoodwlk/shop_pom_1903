package com.qf.datasource_more_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.qf.datasource_more_demo.dao")
public class DatasourceMoreDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceMoreDemoApplication.class, args);
    }

}
