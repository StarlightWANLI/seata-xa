package com.laowan.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:33 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
//@Configuration
public class OrderXADataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean("dataSourceProxy")
    public DataSource dataSource(DruidDataSource druidDataSource) {
        // DataSourceProxy for AT mode
        // return new DataSourceProxy(druidDataSource);

        // DataSourceProxyXA for XA mode
        return new DataSourceProxyXA(druidDataSource);
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate jdbcTemplate(DataSource dataSourceProxy) {
        return new JdbcTemplate(dataSourceProxy);
    }
}
