package com.zaihua.model.qjb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/27 15:03
 */
public class FinmainIndexs {
    public List<FinmainItem> getList() {
        return list;
    }

    public void setList(List<FinmainItem> list) {
        this.list = list;
    }

    private List<FinmainItem> list = new ArrayList<FinmainItem>();
}
