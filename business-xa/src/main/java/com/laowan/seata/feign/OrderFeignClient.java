package com.laowan.seata.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:27 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@FeignClient(name = "order-xa", url = "127.0.0.1:8030")
public interface OrderFeignClient {

    @GetMapping("/create")
    String create(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode,
                  @RequestParam("orderCount") int orderCount);

}
