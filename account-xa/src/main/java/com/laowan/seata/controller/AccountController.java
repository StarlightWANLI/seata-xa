package com.laowan.seata.controller;

import com.laowan.seata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.laowan.seata.service.AccountService.FAIL;
import static com.laowan.seata.service.AccountService.SUCCESS;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 11:34 上午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/reduce", method = RequestMethod.GET, produces = "application/json")
    public String reduce(String userId, int money) {
        try {
            accountService.reduce(userId, money);
        } catch (Exception exx) {
            exx.printStackTrace();
            return FAIL;
        }
        return SUCCESS;
    }
}
