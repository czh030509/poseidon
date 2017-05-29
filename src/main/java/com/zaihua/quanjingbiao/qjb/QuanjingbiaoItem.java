package com.zaihua.quanjingbiao.qjb;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/21 19:42
 */
public class QuanjingbiaoItem {
    public String getCompcode() {
        return compcode;
    }

    public void setCompcode(String compcode) {
        this.compcode = compcode;
    }

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public double getWeightedroe() {
        return weightedroe;
    }

    public void setWeightedroe(double weightedroe) {
        this.weightedroe = weightedroe;
    }

    public double getSalegrossprofitrto() {
        return salegrossprofitrto;
    }

    public void setSalegrossprofitrto(double salegrossprofitrto) {
        this.salegrossprofitrto = salegrossprofitrto;
    }

    public double getMainbusiincome() {
        return mainbusiincome;
    }

    public void setMainbusiincome(double mainbusiincome) {
        this.mainbusiincome = mainbusiincome;
    }

    public double getNetprofit() {
        return netprofit;
    }

    public void setNetprofit(double netprofit) {
        this.netprofit = netprofit;
    }

    public double getTotsharequi() {
        return totsharequi;
    }

    public void setTotsharequi(double totsharequi) {
        this.totsharequi = totsharequi;
    }

    public double getOperrevenue() {
        return operrevenue;
    }

    public void setOperrevenue(double operrevenue) {
        this.operrevenue = operrevenue;
    }

    public double getMainbusiincomeRate() {
        return mainbusiincomeRate;
    }

    public void setMainbusiincomeRate(double mainbusiincomeRate) {
        this.mainbusiincomeRate = mainbusiincomeRate;
    }

    public double getNetprofitRate() {
        return netprofitRate;
    }

    public void setNetprofitRate(double netprofitRate) {
        this.netprofitRate = netprofitRate;
    }

    public double getSalenetprofitrto() {
        return salenetprofitrto;
    }

    public void setSalenetprofitrto(double salenetprofitrto) {
        this.salenetprofitrto = salenetprofitrto;
    }

    private String compcode;
    private String reportdate;  //报表时间
    private double weightedroe;  //ROE
    private double salegrossprofitrto;  //销售毛利率
    private double mainbusiincome; //营业收入
    private double netprofit; //净利润
    private double totsharequi; //股东权益
    private double operrevenue; //经营现金流净额

    private double mainbusiincomeRate; //营业收入 同比增长
    private double netprofitRate; //净利润 同比增长
    private double salenetprofitrto; //销售净利率
}
