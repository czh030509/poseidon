package com.zaihua.controller;

import com.zaihua.model.stock.Stocks;
import com.zaihua.pachong.DFCFJob;
import com.zaihua.service.KDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/28 18:03
 */
@RestController
@RequestMapping("/api")
public class StocksListController {
    @Autowired
    private DFCFJob dfcfJob;

    @ResponseBody
    @RequestMapping(value = "/updateStocksList")
    public String updateStocks() throws Exception {
        dfcfJob.updateStocksList();

        return "success";
    }

}
