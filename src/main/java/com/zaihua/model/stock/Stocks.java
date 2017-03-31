package com.zaihua.model.stock;

import java.util.List;

/**
 * Created by carl on 28/03/2017.
 */
public class Stocks {
    private String success;
    private Stock stock;
    private List<CharItem> chartlist;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public List<CharItem> getChartlist() {
        return chartlist;
    }

    public void setChartlist(List<CharItem> chartlist) {
        this.chartlist = chartlist;
    }
}
