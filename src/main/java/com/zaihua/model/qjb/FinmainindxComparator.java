package com.zaihua.model.qjb;

import java.util.Comparator;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/11/28 17:29
 */
public class FinmainindxComparator implements Comparator<FinmainItem> {
    @Override
    public int compare(FinmainItem o1, FinmainItem o2) {
        long l1 = Long.parseLong(o1.getReportdate());
        long l2 = Long.parseLong(o2.getReportdate());

        if(l1 > l2)
            return 1;
        return -1;
    }
}
