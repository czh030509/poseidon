package com.zaihua.model;

import com.zaihua.service.XQUrlLib;
import com.zaihua.utils.base.PropertyUtils;
import org.apache.commons.lang.StringUtils;


public final class DefaultContant {
    private static String fileName = "default.properties";

    private static String xq_user = PropertyUtils.getValue("xq_user", fileName);
    private static String xq_psw = PropertyUtils.getValue("xq_psw", fileName);
    private static String cookie;

    public static String getCookie() {
        if (StringUtils.isBlank(cookie)) {
            cookie = XQUrlLib.login(xq_user, xq_psw);
        }

        return cookie;
    }
}
