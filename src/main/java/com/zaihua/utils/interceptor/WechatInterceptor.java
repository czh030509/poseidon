package com.zaihua.utils.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hubo on 2017/4/16.
 */
public class WechatInterceptor extends HandlerInterceptorAdapter {

    private static String TOKEN = "gFS3UYzlI5FP0Nn";
    private static String EncodingAESKey = "0jkCCyVX8auOOIiajEOMh4b3wJi4lHwcYl0nLjmDRI8";


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
