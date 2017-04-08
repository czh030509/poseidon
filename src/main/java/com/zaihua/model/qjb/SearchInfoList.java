package com.zaihua.model.qjb;

import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/22 14:23
 */
public class SearchInfoList {
    public List<SearchInfo> getStocks() {
        return stocks;
    }

    public void setStocks(List<SearchInfo> stocks) {
        this.stocks = stocks;
    }

    private List<SearchInfo> stocks;
}
