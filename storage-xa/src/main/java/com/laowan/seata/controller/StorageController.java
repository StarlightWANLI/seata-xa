package com.laowan.seata.controller;

import com.laowan.seata.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.laowan.seata.service.StorageService.FAIL;
import static com.laowan.seata.service.StorageService.SUCCESS;

/**
 * @Description
 * @Author lao.wan
 * @Date 2021/3/30 2:45 下午
 * @Version 1.0
 * @Copyright 2019-2021
 */
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/deduct", method = RequestMethod.GET, produces = "application/json")
    public String deduct(String commodityCode, int count) {
        try {
            storageService.deduct(commodityCode, count);
        } catch (Exception exx) {
            exx.printStackTrace();
            return FAIL;
        }
        return SUCCESS;
    }
}
