package com.zaihua.controller;

import com.zaihua.model.*;
import com.zaihua.model.stock.Stocks;
import com.zaihua.service.QuanjingbiaoService;
import com.zaihua.service.UpdateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/k_day")
public class KDayController {
    @Autowired
    private UpdateInfoService updateInfoService;

    @ResponseBody
    @RequestMapping(value = "/getKdays")
    public Stocks getQuanjingbiaoItems(String symbol, String begin, String end) throws Exception {
        Stocks stocks = null;
        stocks = updateInfoService.getStocklist(symbol, begin, end);

        if (stocks != null) {
            return stocks;
        }

        return null;
    }
}
