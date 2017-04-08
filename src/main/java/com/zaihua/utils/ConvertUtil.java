package com.zaihua.utils;

import com.zaihua.dao.entity.KDay;
import com.zaihua.model.stock.CharItem;
import com.zaihua.model.stock.Stock;
import com.zaihua.model.stock.Stocks;
import com.zaihua.utils.base.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carl on 07/04/2017.
 */
public class ConvertUtil {
    public static KDay charItemToKDay(String symbol, CharItem item) {
        Date d = new Date(item.getTime());
        String formatDate = DateUtils.formatDate(d, DateUtils.DATE_PATTERN);

        KDay kDay = new KDay();
        kDay.setSymbol(symbol);
        kDay.setVolume(item.getVolume());
        kDay.setOpen(item.getOpen());
        kDay.setHigh(item.getHigh());
        kDay.setClose(item.getClose());
        kDay.setLow(item.getLow());
        kDay.setChg(item.getChg());
        kDay.setPercent(item.getPercent());
        kDay.setTurnrate(item.getTurnrate());
        kDay.setMa5(item.getMa5());
        kDay.setMa10(item.getMa10());
        kDay.setMa20(item.getMa20());
        kDay.setMa30(item.getMa30());
        kDay.setDif(item.getDif());
        kDay.setDea(item.getDea());
        kDay.setMacd(item.getMacd());
        kDay.setTime(formatDate);

        return kDay;
    }

    public static Stocks kDaysToStocks(List<KDay> kDays) {
        if (kDays == null || kDays.size() == 0) {
            return null;
        }

        Stocks stocks = new Stocks();
        Stock stock = new Stock();
        stock.setSymbol(kDays.get(0).getSymbol());
        stocks.setStock(stock);

        List<CharItem> charItems = new ArrayList<CharItem>();
        for (KDay kDay : kDays) {
            charItems.add(kDayToCharItem(kDay));
        }

        stocks.setChartlist(charItems);
        return stocks;
    }

    public static CharItem kDayToCharItem(KDay kDay) {
        CharItem charItem = new CharItem();

        charItem.setVolume(kDay.getVolume());
        charItem.setOpen(kDay.getOpen());
        charItem.setHigh(kDay.getHigh());
        charItem.setClose(kDay.getClose());
        charItem.setLow(kDay.getLow());
        charItem.setChg(kDay.getChg());
        charItem.setPercent(kDay.getPercent());
        charItem.setTurnrate(kDay.getTurnrate());
        charItem.setMa5(kDay.getMa5());
        charItem.setMa10(kDay.getMa10());
        charItem.setMa20(kDay.getMa20());
        charItem.setMa30(kDay.getMa30());
        charItem.setDif(kDay.getDif());
        charItem.setDea(kDay.getDea());
        charItem.setMacd(kDay.getMacd());
        charItem.setTime(kDay.getTime());

        return charItem;
    }
}
