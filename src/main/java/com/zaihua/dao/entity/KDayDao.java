package com.zaihua.dao.entity;

import com.zaihua.dao.dal.mapper.KDayMapper;
import com.zaihua.model.stock.CharItem;
import com.zaihua.model.stock.Stocks;
import com.zaihua.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by carl on 07/04/2017.
 */
@Repository
public class KDayDao {
    @Autowired
    private KDayMapper kDayMapper;

    public List<KDay> selectKDaysBySymbol(String symbol) {
        KDayExample kDayExample = new KDayExample();
        kDayExample.createCriteria().andSymbolEqualTo(symbol);
        kDayExample.setOrderByClause(" time desc");

        return kDayMapper.selectByExample(kDayExample);
    }

    public List<KDay> selectKDaysBySymbolAndTime(String symbol, String begin, String end) {
        KDayExample kDayExample = new KDayExample();
        kDayExample.createCriteria().andSymbolEqualTo(symbol).andTimeBetween(begin, end);
        kDayExample.setOrderByClause(" time desc");

        return kDayMapper.selectByExample(kDayExample);
    }

    public void insertKDays(Stocks stocks) {
        if (stocks == null || stocks.getChartlist() == null || stocks.getChartlist().size() == 0) {
            return;
        }

        String symbol = stocks.getStock().getSymbol();

        for (CharItem item : stocks.getChartlist()) {
            kDayMapper.insert(ConvertUtil.charItemToKDay(symbol,  item));
        }
    }

}
