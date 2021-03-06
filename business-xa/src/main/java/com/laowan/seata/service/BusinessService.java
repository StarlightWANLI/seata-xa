package com.laowan.seata.service;

import com.laowan.seata.TestDatas;
import com.laowan.seata.feign.OrderFeignClient;
import com.laowan.seata.feign.StorageFeignClient;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:22 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@Service
@Slf4j
public class BusinessService {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    @Autowired
    private StorageFeignClient storageFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount, boolean rollback) {
        String xid = RootContext.getXID();
        log.info("New Transaction Begins: " + xid);

        String result = storageFeignClient.deduct(commodityCode, orderCount);

        if (!SUCCESS.equals(result)) {
            throw new RuntimeException("库存服务调用失败,事务回滚!");
        }

        result = orderFeignClient.create(userId, commodityCode, orderCount);

        if (!SUCCESS.equals(result)) {
            throw new RuntimeException("订单服务调用失败,事务回滚!");
        }

        if (rollback) {
            throw new RuntimeException("Force rollback ... ");
        }
    }

    @PostConstruct
    public void initData() {
        jdbcTemplate.update("delete from account_tbl");
        jdbcTemplate.update("delete from order_tbl");
        jdbcTemplate.update("delete from storage_tbl");
        jdbcTemplate.update("insert into account_tbl(user_id,money) values('" + TestDatas.USER_ID + "','10000') ");
        jdbcTemplate.update("insert into storage_tbl(commodity_code,count) values('" + TestDatas.COMMODITY_CODE + "','100') ");
    }

    public boolean validData(String userId, String commodityCode) {
        Map accountMap = jdbcTemplate.queryForMap("select * from account_tbl where user_id='" + userId + "'");
        if (Integer.parseInt(accountMap.get("money").toString()) < 0) {
            // 余额被扣减为负：余额不足
            return false;
        }

        Map storageMap = jdbcTemplate.queryForMap(
                "select * from storage_tbl where commodity_code='" + commodityCode + "'");
        if (Integer.parseInt(storageMap.get("count").toString()) < 0) {
            // 库存被扣减为负：库存不足
            return false;
        }

        return true;
    }
}
