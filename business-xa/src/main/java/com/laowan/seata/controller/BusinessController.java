package com.laowan.seata.controller;

import com.laowan.seata.TestDatas;
import com.laowan.seata.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.laowan.seata.service.BusinessService.SUCCESS;

/**
 * @Description 
 * @Author lao.wan
 * @Date 2021/3/30 2:23 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@RestController
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @RequestMapping(value = "/purchase", method = RequestMethod.GET, produces = "application/json")
    public String purchase(Boolean rollback, Integer count) {
        int orderCount = 30;
        if (count != null) {
            orderCount = count;
        }
        try {
            businessService.purchase(TestDatas.USER_ID, TestDatas.COMMODITY_CODE, orderCount,
                    rollback == null ? false : rollback.booleanValue());
        } catch (Exception exx) {
            return "Purchase Failed:" + exx.getMessage();
        }
        return SUCCESS;
    }
}
