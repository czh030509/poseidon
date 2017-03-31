package com.zaihua.service;

import com.zaihua.model.DefaultContant;
import com.zaihua.model.stock.Stocks;
import com.zaihua.utils.KDayDbUtil;
import com.zaihua.utils.base.JacksonUtils;

/**
 * Created by carl on 28/03/2017.
 */
public class UpdateInfoService {
    public static Stocks getStocklist(String symbol, String cookie, String begin, String end) {
        String res = XQUrlLib.getStocklist("SH600036", DefaultContant.getCookie(),"1459059584314","1490595584314");
        Stocks stocks = JacksonUtils.unmarshalFromString(res, Stocks.class);
        return stocks;
    }


    public static void main(String[] args) throws InterruptedException {
        Stocks stocks = getStocklist("SH600036", DefaultContant.getCookie(),"1459059584314","1490595584314");

        KDayDbUtil.insertKDays(stocks);
    }
}
