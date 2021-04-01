package com.laowan.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 11:33 上午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@RefreshScope
public class AccountXAApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountXAApplication.class, args);
    }
}
