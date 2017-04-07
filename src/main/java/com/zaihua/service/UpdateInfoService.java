package com.zaihua.service;

import com.zaihua.dao.entity.KDay;
import com.zaihua.dao.entity.KDayDao;
import com.zaihua.model.DefaultContant;
import com.zaihua.model.stock.Stocks;
import com.zaihua.utils.KDayDbUtil;
import com.zaihua.utils.base.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.event.KeyAdapter;

/**
 * Created by carl on 28/03/2017.
 */
@Service
public class UpdateInfoService {

    public static Stocks getStocklist(String symbol, String begin, String end) {
        String res = XQUrlLib.getStocklist(symbol, DefaultContant.getCookie(),begin,end);
        Stocks stocks = JacksonUtils.unmarshalFromString(res, Stocks.class);
        return stocks;
    }



    public static void main(String[] args) throws InterruptedException {
        //Stocks stocks = getStocklist("SH600036","1459059584314","1490595584314");

        //KDayDbUtil.insertKDays(stocks);



    }
}
