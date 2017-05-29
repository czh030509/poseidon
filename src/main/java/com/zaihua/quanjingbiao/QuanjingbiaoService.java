package com.zaihua.quanjingbiao;

import com.zaihua.model.*;
import com.zaihua.quanjingbiao.qjb.*;
import com.zaihua.service.XQUrlLib;
import com.zaihua.utils.base.JacksonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/27 15:07
 */
@Service
public class QuanjingbiaoService {
    private static FinmainindxComparator finmainindxComparator = new FinmainindxComparator();
    private static String COOKIE = null;

    public QuanjingbiaoService() {
        if (StringUtils.isBlank(COOKIE)) {
            COOKIE = DefaultContant.getCookie();
        }
    }

    public CompanyInfo getCompanyInfo(String symbol) {
        CompanyInfo result = null;
        String res = XQUrlLib.getCompanyInfo(symbol, COOKIE);
        if (StringUtils.isNotBlank(res)) {
            result = JacksonUtils.unmarshalFromString(res, CompanyInfo.class);
        }

        return result;
    }

    public SearchInfoList searchCompany(String code) throws Exception{
        SearchInfoList result = null;

        if (StringUtils.isBlank(COOKIE)) {
            throw new Exception("无法登录，cookie为空。");
        }

        String res = XQUrlLib.searchComps(code, COOKIE);

        if (StringUtils.isNotBlank(res)) {
            result = JacksonUtils.unmarshalFromString(res, SearchInfoList.class);
        }

        return result;
    }

    public QuanjingbiaoItems getQuanjingbiao(String symbol) throws Exception{
        QuanjingbiaoItems quanjingbiaoItems = null;
        FinmainIndexs finalIndex = new FinmainIndexs();

        //最多取十年的数据
        for (int i = 1; i<=5;i++) {
            String finmainIndex = XQUrlLib.getFinmainIndex(symbol, COOKIE, i);

            if (StringUtils.isNotBlank(finmainIndex)) {
                FinmainIndexs fis = JacksonUtils.unmarshalFromString(finmainIndex, FinmainIndexs.class);

                if (fis == null || fis.getList() ==null || fis.getList().size() < 1) {
                    break;
                }

                for(FinmainItem item : fis.getList()) {
                    finalIndex.getList().add(item);
                }
            } else {
                break;
            }
        }

        Collections.sort(finalIndex.getList(), finmainindxComparator);
        quanjingbiaoItems = calcQuanjingbiaoItems(finalIndex.getList());
        return quanjingbiaoItems;
    }

    private QuanjingbiaoItems calcQuanjingbiaoItems(List<FinmainItem> finmainItems) {
        QuanjingbiaoItems quanjingbiaoItems = new QuanjingbiaoItems();
        boolean first = true;
        double lastNetprofit = 0;
        double lastMainbusiincome = 0;

        for(FinmainItem item : finmainItems) {
            if (item.getReportdate().contains("1231")) {
                QuanjingbiaoItem quanjingbiaoItem = new QuanjingbiaoItem();

                quanjingbiaoItem.setCompcode(item.getCompcode());
                quanjingbiaoItem.setMainbusiincome(item.getMainbusiincome());
                quanjingbiaoItem.setNetprofit(item.getNetprofit());
                quanjingbiaoItem.setOperrevenue(item.getOperrevenue());
                quanjingbiaoItem.setReportdate(item.getReportdate());
                quanjingbiaoItem.setSalegrossprofitrto(item.getSalegrossprofitrto());
                quanjingbiaoItem.setTotsharequi(item.getTotsharequi());
                quanjingbiaoItem.setWeightedroe(item.getWeightedroe());

                double netprofit = item.getNetprofit();
                double mainbusiincome = item.getMainbusiincome();
                double saleRate =100.0 * netprofit / mainbusiincome;
                quanjingbiaoItem.setSalenetprofitrto(saleRate);

                if(first) {
                    first = false;
                } else {
                    double detalNetprofit = netprofit - lastNetprofit;
                    double detalMainbusiincome = mainbusiincome - lastMainbusiincome;

                    double netprofitRate = detalNetprofit / lastNetprofit * 100.0;
                    double mainbusiincomeRate = detalMainbusiincome / lastMainbusiincome * 100.0;

                    quanjingbiaoItem.setNetprofitRate(netprofitRate);
                    quanjingbiaoItem.setMainbusiincomeRate(mainbusiincomeRate);
                }

                lastNetprofit = netprofit;
                lastMainbusiincome = mainbusiincome;

                quanjingbiaoItems.getLists().add(quanjingbiaoItem);
            }
        }

        for(QuanjingbiaoItem item : quanjingbiaoItems.getLists()) {
            item.setReportdate(item.getReportdate().replace("1231", ""));

            item.setNetprofitRate(formatDouble2(item.getNetprofitRate()));
            item.setMainbusiincomeRate(formatDouble2(item.getMainbusiincomeRate()));
            item.setSalenetprofitrto(formatDouble2(item.getSalenetprofitrto()));
            item.setSalegrossprofitrto(formatDouble2(item.getSalegrossprofitrto()));
            item.setWeightedroe(formatDouble2(item.getWeightedroe()));

            item.setMainbusiincome(formatYi(item.getMainbusiincome()));
            item.setNetprofit(formatYi(item.getNetprofit()));
            item.setOperrevenue(formatYi(item.getOperrevenue()));
            item.setTotsharequi(formatYi(item.getTotsharequi()));
        }

        return quanjingbiaoItems;
    }

    private static double formatYi(double d) {
        double base = d / 100000000.0;
        BigDecimal bg = new BigDecimal(base).setScale(5, RoundingMode.UP);
        return bg.doubleValue();
    }

    private static double formatDouble2(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

/*    public static void main(String[] args) throws InterruptedException {

        try {
            QuanjingbiaoService quanjingbiaoService = new QuanjingbiaoService();

           quanjingbiaoService.getCompanyInfo("SZ002658");


            System.out.println();
        }catch (Exception e) {
            System.out.println();
        }
    }*/
}
