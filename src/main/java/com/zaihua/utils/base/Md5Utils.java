package com.zaihua.utils.base;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/16 11:10
 */
public class Md5Utils {
    private static final String HEX_DIGITS[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

    private final static Logger logger = LoggerFactory.getLogger(Md5Utils.class);

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (byte element : b) {
            resultSb.append(Md5Utils.byteToHexString(element));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return Md5Utils.HEX_DIGITS[d1] + Md5Utils.HEX_DIGITS[d2];
    }

    public static String md5Encode(String origin) {
        return Md5Utils.md5Encode(origin, Consts.UTF_8.name());
    }

    public static String md5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (StringUtils.isBlank(charsetname)) {
                resultString = Md5Utils.byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = Md5Utils.byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
            Md5Utils.logger.error("ERROR ## md5 encode happend error, the trace is ", e);
        }
        return resultString;
    }
}
