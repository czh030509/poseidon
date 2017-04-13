package com.zaihua.controller;

import com.zaihua.dao.entity.StocksInfo;
import com.zaihua.service.pachong.StocksListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/28 18:03
 */
@RestController
@RequestMapping("/api/stockInfos")
public class StocksListController {
    @Autowired
    private StocksListService stocksListService;

    @ResponseBody
    @RequestMapping(value = "/updateStocksList")
    public String updateStocks() throws Exception {
        stocksListService.updateStocksList();

        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/getStocksInfo")
    public List<StocksInfo> getStocksInfo(String type) throws Exception {
        return stocksListService.getStocksInfo(type);
    }

    @ResponseBody
    @RequestMapping(value = "/updateStocksInfo")
    public String updateStocksInfo(List<StocksInfo> stocksInfoList) throws Exception {
        stocksListService.editStocksInfo(stocksInfoList);
        return "success";
    }
}
