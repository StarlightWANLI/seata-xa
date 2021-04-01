package com.laowan.seata.config;

import com.alibaba.druid.pool.DruidDataSource;
//import io.seata.rm.datasource.xa.DataSourceProxyXA;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 11:37 上午
 * @Version 1.0
 * @Copyright 2019-2021
 */
//@Configuration
public class AccountXADataSourceConfiguration {

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

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSourceProxy) {
        return new DataSourceTransactionManager(dataSourceProxy);
    }

}
