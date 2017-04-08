package com.zaihua.pachong;

import com.google.common.collect.Maps;
import com.zaihua.dao.entity.StocksInfo;
import com.zaihua.dao.entity.StocksInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.Map;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/1 19:46
 */
@Service
public class StocksListService {
    @Autowired
    private StocksInfoDao stocksInfoDao;

    public static Map<String, String> shs = Maps.newHashMap();
    public static Map<String, String> szs = Maps.newHashMap();

    public void updateStocksList() {
        Spider.create(new StocksPageProcessor())
                .addUrl("http://quote.eastmoney.com/stocklist.html")
                .thread(5)
                .run();

        for(Map.Entry<String, String> item : shs.entrySet()) {
            List<StocksInfo> stocksInfoList = stocksInfoDao.selectStocksInfosBySymbol(item.getKey());

            if (stocksInfoList == null || stocksInfoList.size() == 0) {
                StocksInfo stocksInfo = new StocksInfo();
                stocksInfo.setSymbol(item.getKey());
                stocksInfo.setType("sh");
                stocksInfo.setName(item.getValue());

                stocksInfoDao.insertStocksInfo(stocksInfo);
            }
        }

        for(Map.Entry<String, String> item : szs.entrySet()) {
            List<StocksInfo> stocksInfoList = stocksInfoDao.selectStocksInfosBySymbol(item.getKey());

            if (stocksInfoList == null || stocksInfoList.size() == 0) {
                StocksInfo stocksInfo = new StocksInfo();
                stocksInfo.setSymbol(item.getKey());
                stocksInfo.setType("sz");
                stocksInfo.setName(item.getValue());

                stocksInfoDao.insertStocksInfo(stocksInfo);
            }
        }
    }

}
