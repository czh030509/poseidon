package com.zaihua.model.qjb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/27 15:25
 */
public class QuanjingbiaoItems {
    public List<QuanjingbiaoItem> getLists() {
        return lists;
    }

    public void setLists(List<QuanjingbiaoItem> lists) {
        this.lists = lists;
    }

    private List<QuanjingbiaoItem> lists = new ArrayList<QuanjingbiaoItem>();
}
