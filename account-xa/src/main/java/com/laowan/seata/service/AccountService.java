package com.laowan.seata.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.seata.core.context.RootContext;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 11:35 上午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@Service
@Slf4j
public class AccountService {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, int money) {
        String xid = RootContext.getXID();
        log.info("reduce account balance in transaction: " + xid);
        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", new Object[] {money, userId});
        int balance = jdbcTemplate.queryForObject("select money from account_tbl where user_id = ?", new Object[] {userId}, Integer.class);
        log.info("balance after transaction: " + balance);
        if (balance < 0) {
            throw new RuntimeException("Not Enough Money ...");
        }
    }
}
