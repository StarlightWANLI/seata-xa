package com.laowan.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:31 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@SpringBootApplication
@EnableFeignClients
public class OrderXAApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderXAApplication.class, args);
    }

}
