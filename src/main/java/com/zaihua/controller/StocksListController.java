package com.zaihua.controller;

import com.zaihua.pachong.StocksListService;
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
    private StocksListService stocksListService;

    @ResponseBody
    @RequestMapping(value = "/updateStocksList")
    public String updateStocks() throws Exception {
        stocksListService.updateStocksList();

        return "success";
    }

}
