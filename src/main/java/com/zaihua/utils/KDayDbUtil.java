package com.zaihua.utils;

import com.zaihua.model.stock.CharItem;
import com.zaihua.model.stock.Stocks;
import com.zaihua.service.JdbcBase;
import com.zaihua.utils.base.DateUtils;

import java.util.Date;

/**
 * Created by carl on 29/03/2017.
 */
public class KDayDbUtil {
    public static void insertKDays(Stocks stocks) {
        if (stocks == null || stocks.getChartlist() == null || stocks.getChartlist().size() == 0) {
            return;
        }

        String symbol = stocks.getStock().getSymbol();
        for (CharItem item : stocks.getChartlist()) {
            Date d = new Date(item.getTime());
            String formatDate = DateUtils.formatDate(d, DateUtils.DATE_PATTERN);

            String sql = String.format("insert into k_day (symbol, volume, open, high, close, low, chg, percent, turnrate, ma5, ma10, ma20, ma30, dif, dea, macd, time) values (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\");",
                    symbol, item.getVolume(), item.getOpen(), item.getHigh(), item.getClose(), item.getLow(), item.getChg(), item.getPercent(), item.getTurnrate(), item.getMa5(), item.getMa10(), item.getMa20(),
                    item.getMa30(), item.getDif(), item.getDea(), item.getMacd(), formatDate);
            JdbcBase.getJdbcTemplate().execute(sql);
        }
    }

}
