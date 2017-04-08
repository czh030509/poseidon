package com.zaihua.model.stock;

import java.math.BigInteger;

/**
 * Created by carl on 28/03/2017.
 */
public class CharItem {
    private Long volume;      //成交量
    private double open;        //开盘价
    private double high;        //最高价
    private double close;       //收盘价
    private double low;         //最低价
    private double chg;         //涨跌额
    private double percent;     //涨跌幅
    private double turnrate;    //换手率
    private double ma5;
    private double ma10;
    private double ma20;
    private double ma30;
    private double dif;
    private double dea;
    private double macd;
    private String time;

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getChg() {
        return chg;
    }

    public void setChg(double chg) {
        this.chg = chg;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getTurnrate() {
        return turnrate;
    }

    public void setTurnrate(double turnrate) {
        this.turnrate = turnrate;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getMa30() {
        return ma30;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }

    public double getDif() {
        return dif;
    }

    public void setDif(double dif) {
        this.dif = dif;
    }

    public double getDea() {
        return dea;
    }

    public void setDea(double dea) {
        this.dea = dea;
    }

    public double getMacd() {
        return macd;
    }

    public void setMacd(double macd) {
        this.macd = macd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
