package com.laowan.seata.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:35 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@FeignClient(name = "account-xa", url = "127.0.0.1:8010")
public interface AccountFeignClient {
    @GetMapping("/reduce")
    String reduce(@RequestParam("userId") String userId, @RequestParam("money") int money);
}
