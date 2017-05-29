package com.zaihua.quanjingbiao;

import com.zaihua.quanjingbiao.qjb.*;
import com.zaihua.service.QuanjingbiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/28 18:03
 */
@RestController
@RequestMapping("/api/getInfos")
public class QuanjingbiaoController {
    @Autowired
    private QuanjingbiaoService quanjingbiaoService;

    @ResponseBody
    @RequestMapping("/getQuanjingbiaoItems/{symbol}")
    public List<QuanjingbiaoItem> getQuanjingbiaoItems(@PathVariable("symbol") String symbol) throws Exception {
        QuanjingbiaoItems quanjingbiaoItems = null;
        quanjingbiaoItems = quanjingbiaoService.getQuanjingbiao(symbol);

        if (quanjingbiaoItems != null) {
            return quanjingbiaoItems.getLists();
        }

        return null;
    }

    @ResponseBody
    @RequestMapping("/getCompanyInfo/{symbol}")
    public CompanyInfo getCompanyInfo(@PathVariable("symbol") String symbol) throws Exception {
        CompanyInfo companyInfo = null;
        companyInfo = quanjingbiaoService.getCompanyInfo(symbol);

        if (companyInfo != null) {
            return companyInfo;
        }

        return null;
    }

    @ResponseBody
    @RequestMapping("/searchCompany/{code}")
    public List<SearchInfo> searchCompany(@PathVariable("code") String code) throws Exception {
        SearchInfoList searchInfoList = null;
        searchInfoList = quanjingbiaoService.searchCompany(code);

        if (searchInfoList != null) {
            return searchInfoList.getStocks();
        }

        return null;
    }
}
