package com.laowan.seata.service;

import com.laowan.seata.feign.AccountFeignClient;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:35 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@Service
@Slf4j
public class OrderService {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(String userId, String commodityCode, Integer count) {
        String xid = RootContext.getXID();
        log.info("create order in transaction: " + xid);

        // 定单总价 = 订购数量(count) * 商品单价(100)
        int orderMoney = count * 100;
        // 生成订单
        jdbcTemplate.update("insert order_tbl(user_id,commodity_code,count,money) values(?,?,?,?)",
                new Object[] {userId, commodityCode, count, orderMoney});
        // 调用账户余额扣减
        String result = accountFeignClient.reduce(userId, orderMoney);
        if (!SUCCESS.equals(result)) {
            throw new RuntimeException("Failed to call Account Service. ");
        }

    }

}
