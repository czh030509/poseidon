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

    public List<StocksInfo> selectStocksInfosBySymbol(String symbol) {
        StocksInfoExample stocksInfoExample = new StocksInfoExample();
        stocksInfoExample.createCriteria().andSymbolEqualTo(symbol);

        return stocksInfoMapper.selectByExample(stocksInfoExample);
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
}
