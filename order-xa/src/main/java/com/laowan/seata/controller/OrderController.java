package com.laowan.seata.controller;

import com.laowan.seata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.laowan.seata.service.OrderService.FAIL;
import static com.laowan.seata.service.OrderService.SUCCESS;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:33 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
    public String create(String userId, String commodityCode, int orderCount) {
        try {
            orderService.create(userId, commodityCode, orderCount);
        } catch (Exception exx) {
            exx.printStackTrace();
            return FAIL;
        }
        return SUCCESS;
    }

}