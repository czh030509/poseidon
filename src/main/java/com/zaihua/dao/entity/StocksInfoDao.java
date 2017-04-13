package com.zaihua.dao.entity;

import com.zaihua.dao.dal.mapper.StocksInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carl on 08/04/2017.
 */
@Repository
public class StocksInfoDao {
    @Autowired
    private StocksInfoMapper stocksInfoMapper;

    public StocksInfo selectStocksInfosBySymbol(String symbol) {
        StocksInfoExample stocksInfoExample = new StocksInfoExample();
        stocksInfoExample.createCriteria().andSymbolEqualTo(symbol);

        List<StocksInfo> stocksInfoList = stocksInfoMapper.selectByExample(stocksInfoExample);
        if (stocksInfoList == null || stocksInfoList.size() == 0) {
            return null;
        }

        return stocksInfoList.get(0);
    }

    public List<StocksInfo> selectStocksInfoByType(String type) {
        StocksInfoExample stocksInfoExample = new StocksInfoExample();
        stocksInfoExample.createCriteria().andTypeEqualTo(type);

        return stocksInfoMapper.selectByExample(stocksInfoExample);
    }

    public void insertStocksInfo(StocksInfo stocksInfo) {
        if (stocksInfo == null) {
            return;
        }

        stocksInfoMapper.insert(stocksInfo);
    }

    public void updateStocksInfo(StocksInfo stocksInfo) {
        if (stocksInfo == null) {
            return;
        }

        if (selectStocksInfosBySymbol(stocksInfo.getSymbol()) != null) {
            StocksInfoExample stocksInfoExample = new StocksInfoExample();
            stocksInfoExample.createCriteria().andSymbolEqualTo(stocksInfo.getSymbol());

            stocksInfoMapper.updateByExampleSelective(stocksInfo, stocksInfoExample);
        }
    }
}
