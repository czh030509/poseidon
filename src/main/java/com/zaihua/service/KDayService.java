package com.zaihua.service;

import com.zaihua.dao.entity.KDay;
import com.zaihua.dao.entity.KDayDao;
import com.zaihua.model.DefaultContant;
import com.zaihua.model.stock.Stocks;
import com.zaihua.utils.ConvertUtil;
import com.zaihua.utils.base.DateUtils;
import com.zaihua.utils.base.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by carl on 28/03/2017.
 */
@Service
public class KDayService {
    @Autowired
    private KDayDao kDayDao;

    public Stocks getStocksByHttp(String symbol, String begin, String end) {
        Date bd  = DateUtils.convertDate(begin, DateUtils.DATE_PATTERN);
        Date ed  = DateUtils.convertDate(end, DateUtils.DATE_PATTERN);

        long bl = bd.getTime();
        long el = ed.getTime();

        String res = XQUrlLib.getStocklist(symbol, DefaultContant.getCookie(),String.valueOf(bl), String.valueOf(el));
        Stocks stocks = JacksonUtils.unmarshalFromString(res, Stocks.class);
        return stocks;
    }


    public Stocks getStocks(String symbol, String begin, String end) {
        List<KDay> kDays = kDayDao.selectKDaysBySymbolAndTime(symbol, begin, end);
        Stocks stocks = null;

        if(kDays == null || kDays.size() == 0) {
            //没有，请求接口，并插入数据库
            stocks =  getStocksByHttp(symbol, begin, end);
            kDayDao.insertKDays(stocks);

            List<KDay> newKDays = kDayDao.selectKDaysBySymbolAndTime(symbol, begin, end);
            stocks = ConvertUtil.kDaysToStocks(newKDays);
        }  else {
            String time = kDays.get(0).getTime();
            Date dbTime = DateUtils.convertDate(time, DateUtils.DATE_PATTERN);
            Date reqTime = DateUtils.convertDate(end, DateUtils.DATE_PATTERN);

            if(!dbTime.before(reqTime)) {
                //有，且最新的时间大于请求时间，则在直接返回
                stocks = ConvertUtil.kDaysToStocks(kDays);
            } else {
                //有，但是没有最新的，则请求db最新 到 end区间数据，插库，后再次查询，返回

                dbTime = DateUtils.addDay(dbTime, 1);
                time = DateUtils.formatDate(dbTime, DateUtils.DATE_PATTERN);

                Stocks newStocks = getStocksByHttp(symbol, time, end);
                kDayDao.insertKDays(newStocks);

                List<KDay> newKDays = kDayDao.selectKDaysBySymbolAndTime(symbol, begin, end);
                stocks = ConvertUtil.kDaysToStocks(newKDays);
            }

        }

        return stocks;
    }


    public static void main(String[] args) throws InterruptedException {


    }
}
