package com.zaihua.controller;

import com.zaihua.model.stock.Stocks;
import com.zaihua.service.KDayService;
import com.zaihua.utils.base.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/28 18:03
 */
@RestController
@RequestMapping("/api/k_day")
public class KDayController {
    @Autowired
    private KDayService kDayService;

    @ResponseBody
    @RequestMapping(value = "/getKdays")
    public Stocks getQuanjingbiaoItems(String symbol, String begin, String end) throws Exception {
        Stocks stocks = null;
        stocks = kDayService.getStocks(symbol, begin, end);

        if (stocks != null) {
            return stocks;
        }

        return null;
    }


}
