/*
 * Copyright 2013 Qunar.com All right reserved. This software is the confidential and proprietary information of
 * Qunar.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Qunar.com.
 */
package com.zaihua.utils.base;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 实现描述：http访问客户端工具类
 *
 * @author simon
 * @version v1.0.0
 * @see
 * @since 2013-8-2 下午5:21:09
 */
public class HttpClientUtils {
    /**
     * 实现描述：返回请求的内容，支持默认的字符集，如果httpEntity没有指定的话
     *
     * @author simon
     * @version v1.0.0
     * @see
     * @since 2013-8-2 下午5:21:59
     */
    private static class CharsetableResponseHandler implements ResponseHandler<String> {

        private String defaultEncoding;

        public CharsetableResponseHandler(String defaultEncoding) {
            this.defaultEncoding = defaultEncoding;
        }

        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            StatusLine statusLine = response.getStatusLine();
            HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            return entity == null ? null : EntityUtils.toString(entity, defaultEncoding);
        }
    }

    private static HttpClient client;

    private static final int CONNECTION_TIMEOUT = 5000;

    private static PoolingClientConnectionManager connectionManager;
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final int READ_TIMEOUT = 10000;

    static {
        HttpClientUtils.connectionManager = new PoolingClientConnectionManager();
        HttpClientUtils.connectionManager.setDefaultMaxPerRoute(50);
        HttpClientUtils.connectionManager.setMaxTotal(200);
        HttpParams defaultParams = new SyncBasicHttpParams();

        defaultParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, HttpClientUtils.READ_TIMEOUT);
        defaultParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HttpClientUtils.CONNECTION_TIMEOUT);

        HttpProtocolParams.setContentCharset(defaultParams, Consts.UTF_8.name());
        HttpProtocolParams.setUseExpectContinue(defaultParams, false);
        DefaultHttpClient.setDefaultHttpParams(defaultParams);
        HttpClientUtils.client = new DefaultHttpClient(HttpClientUtils.connectionManager, defaultParams);
        HttpClientUtils.client = wrapHttpsClient(client);
    }

    private static String getDefaultEncoding(String defaultEncoding) {
        String encoding = StringUtils.isEmpty(defaultEncoding) ? Consts.UTF_8.name() : defaultEncoding;
        return encoding;
    }

    /**
     * 封装支持https
     *
     * @param base
     * @return
     * @author reeboo
     */
    private static HttpClient wrapHttpsClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
            registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
            ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(mgr, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 请求特定的url提交表单，使用post方法，返回响应的内容
     *
     * @param url
     * @param formData 表单的键值对
     * @return
     */
    public static String post(String url, Map<String, String> formData) {
        return HttpClientUtils.post(url, formData, null);
    }

    /**
     * 请求特定的url提交表单，使用post方法，返回响应的内容，超时重试指定次数
     *
     * @param url
     * @param formData
     * @param retryTimes
     * @return
     */
    public static String timeoutRetryPost(String url, Map<String, String> formData, int retryTimes) {
        if (retryTimes < 0) {
            throw new IllegalArgumentException("retry times must great than or equal 0");
        }
        SocketTimeoutException ste = null;
        for (int i = 0; i <= retryTimes; i++) {
            try {
                String result = postWithTimeoutException(url, formData, null);
                if (i > 0) {
                    logger.warn("post [%s] retry %d times", url, i);
                }
                return result;
            } catch (SocketTimeoutException e) {
                ste = e;
            }
        }
        logger.error("post [%s] timeout, retry %d times", url, retryTimes, ste);
        return null;
    }

    /**
     * 请求特定的url提交表单，使用post方法，返回响应的内容
     *
     * @param url
     * @param formData        表单的键值对
     * @param defaultEncoding 处理 form encode 的编码，以及作为 contentType 的默认编码
     * @return
     */
    public static String post(String url, Map<String, String> formData, String defaultEncoding) {
        try {
            return postWithTimeoutException(url, formData, defaultEncoding);
        } catch (SocketTimeoutException e) {
            HttpClientUtils.logger.error(String.format("post [%s] timeout", url), e);
            return null;
        }
    }

    private static String postWithTimeoutException(String url, Map<String, String> formData, String defaultEncoding)
            throws SocketTimeoutException {
        defaultEncoding = HttpClientUtils.getDefaultEncoding(defaultEncoding);

        HttpPost post = new HttpPost(url);
        String content = null;
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        if (formData != null && !formData.isEmpty()) {
            for (Entry<String, String> entry : formData.entrySet()) {
                nameValues.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValues, defaultEncoding);
            post.setEntity(formEntity);
            content = HttpClientUtils.client.execute(post, new CharsetableResponseHandler(defaultEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported Encoding " + defaultEncoding, e);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return content;
    }

    /**
     * 请求特定的url提交Json字符串，使用post方法，返回响应的内容
     *
     * @param url
     * @param jsonData
     * @return
     */
    public static String post(String url, String jsonData) {
        return HttpClientUtils.post(url, jsonData, null);
    }

    /**
     * 请求特定的url提交Json字符串，使用post方法，返回响应的内容
     *
     * @param url
     * @param jsonData
     * @return
     */
    public static String post(String url, String jsonData, String defaultEncoding) {
        defaultEncoding = HttpClientUtils.getDefaultEncoding(defaultEncoding);
        String content = null;
        try {
            HttpPost post = new HttpPost(url);
            StringEntity entity = new StringEntity(jsonData, defaultEncoding);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(entity);
            HttpResponse response = HttpClientUtils.client.execute(post);
            content = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return content;
    }

    /**
     * 请求特定的url提交表单，使用post方法，返回响应的内容
     *
     * @param url
     * @param formData        表单的键值对
     * @param defaultEncoding 处理 form encode 的编码，以及作为 contentType 的默认编码
     * @return
     */
    public static String post(String url, Map<String, String> formData, String defaultEncoding, Integer readTimeout) {
        try {
            return postWithTimeoutException(url, formData, defaultEncoding, readTimeout);
        } catch (SocketTimeoutException e) {
            HttpClientUtils.logger.error(String.format("post [%s] timeout, params: %s", url, formData), e);
            return null;
        }
    }

    private static String postWithTimeoutException(String url, Map<String, String> formData, String defaultEncoding, Integer readTimeout)
            throws SocketTimeoutException {
        defaultEncoding = HttpClientUtils.getDefaultEncoding(defaultEncoding);

        HttpPost post = new HttpPost(url);
        if (readTimeout != null && readTimeout > 0) {
            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);  // RequestConfig
        }

        String content = null;
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        if (formData != null && !formData.isEmpty()) {
            for (Entry<String, String> entry : formData.entrySet()) {
                nameValues.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValues, defaultEncoding);
            post.setEntity(formEntity);
            content = HttpClientUtils.client.execute(post, new CharsetableResponseHandler(defaultEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported Encoding " + defaultEncoding, e);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return content;
    }

    /**
     * 带cookie的post请求
     * @param url
     * @param formData
     * @param cookies
     * @return
     */
    public static String doPostWithCookie(String url, Map<String, String> formData, String cookies, Integer readTimeout) {
        return HttpClientUtils.postWithCookieHeader(url, formData, cookies, readTimeout);
    }

    /**
     * 请求特定的url提交Json字符串，使用post方法，返回响应的内容
     * @param url
     * @param formData
     * @param cookies
     * @return
     */
    public static String postWithCookieHeader(String url, Map<String, String> formData, String cookies, Integer readTimeout) {
        try {
            return postWithCookieTimeoutException(url, formData, null, cookies, readTimeout);
        } catch (SocketTimeoutException e) {
            HttpClientUtils.logger.error(String.format("post [%s] timeout", url), e);
            return null;
        }
    }

    /**
     * 带cookie请求
     * @param url
     * @param formData
     * @param defaultEncoding
     * @param cookies
     * @param readTimeout
     * @return
     * @throws SocketTimeoutException
     */
    private static String postWithCookieTimeoutException(String url, Map<String, String> formData, String defaultEncoding, String cookies, Integer readTimeout)
            throws SocketTimeoutException {
        if (StringUtils.isEmpty(cookies)) {
            return post(url, formData, defaultEncoding, readTimeout);
        }
        defaultEncoding = HttpClientUtils.getDefaultEncoding(defaultEncoding);

        HttpPost post = new HttpPost(url);
        if (readTimeout != null && readTimeout > 0) {
            post.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);  // RequestConfig
        }

        post.addHeader("Cookie", cookies);
        String content = null;
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        if (formData != null && !formData.isEmpty()) {
            for (Entry<String, String> entry : formData.entrySet()) {
                nameValues.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValues, defaultEncoding);
            post.setEntity(formEntity);
            content = HttpClientUtils.client.execute(post, new CharsetableResponseHandler(defaultEncoding));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported Encoding " + defaultEncoding, e);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return content;
    }

    /**
     * 请求特定的url，返回响应的内容
     *
     * @param url
     * @return
     */
    public static String request(String url) {
        return HttpClientUtils.request(url, null, null);
    }

    /**
     * 请求特定的url，返回响应的内容
     *
     * @param url
     * @param readTimeout
     * @return
     */
    public static String request(String url, Integer readTimeout) {
        return HttpClientUtils.request(url, null, readTimeout);
    }

    /**
     * 请求特定的url，返回响应的内容
     *
     * @param url
     * @param defaultEncoding 如果返回的 contentType 中没有指定编码，则使用默认编码
     * @param readTimeout:    socket timeout
     * @return
     */
    public static String request(String url, String defaultEncoding, Integer readTimeout) {
        defaultEncoding = HttpClientUtils.getDefaultEncoding(defaultEncoding);

        HttpGet get = new HttpGet(url);
        if (readTimeout != null && readTimeout > 0) {
            get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);
        }
        String content = null;
        try {
            content = HttpClientUtils.client.execute(get, new CharsetableResponseHandler(defaultEncoding));
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("request [%s] happens error ", url), e);
        }
        return content;
    }

    /**
     * 请求特定的url，返回响应的内容
     *
     * @param url
     * @param defaultEncoding 如果返回的 contentType 中没有指定编码，则使用默认编码
     * @param readTimeout:    socket timeout
     * @return
     * @throws SocketTimeoutException
     * @throws org.apache.http.conn.ConnectTimeoutException
     */
    public static String requestDealTimeOutException(String url, String defaultEncoding, Integer readTimeout) 
            throws SocketTimeoutException, ConnectTimeoutException {
        defaultEncoding = HttpClientUtils.getDefaultEncoding(defaultEncoding);

        HttpGet get = new HttpGet(url);
        if (readTimeout != null && readTimeout > 0) {
            get.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);
        }
        String content = null;
        try {
            content = HttpClientUtils.client.execute(get, new CharsetableResponseHandler(defaultEncoding));
        } catch(SocketTimeoutException e){
            HttpClientUtils.logger.error(String.format("requestDealTimeOutException [%s] happens error ", url), e);
            throw  e;
        } catch(ConnectTimeoutException e){
            HttpClientUtils.logger.error(String.format("requestDealTimeOutException [%s] happens error ", url), e);
            throw  e;
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("requestDealTimeOutException [%s] happens error ", url), e);
        }finally {
            if(get!=null){
                get.releaseConnection();
            }
        }
        return content;
    }

    /**
     * 对url解码
     *
     * @param str    需要解码的字符串
     * @param encode 编码，如 GBK, UTF-8 等
     * @return 如果解码出错，会传回 null；如果 str 为空，则也返回 null
     */
    public static String urlDecode(String str, String encode) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String result = null;
        try {
            result = URLDecoder.decode(str, encode);
        } catch (UnsupportedEncodingException e) {
            HttpClientUtils.logger.error("urldecode error for {} with encode {}", str, encode);
        }
        return result;
    }

    /**
     * 对url编码，
     *
     * @param str    需要编码的字符串
     * @param encode 编码，如 GBK, UTF-8 等
     * @return 如果编码出错，会传回 null；如果 str 为空，则也返回 null
     */
    public static String urlEncode(String str, String encode) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String result = null;
        try {
            result = URLEncoder.encode(str, encode);
        } catch (UnsupportedEncodingException e) {
            HttpClientUtils.logger.error("urlencode error for {} with encode {}", str, encode);
        }
        return result;
    }

    /**
     * 对特定的url的post请求，返回响应体
     *
     * @param url      请求的url
     * @param formData 请求参数,转化为json
     * @return
     */
    public static HttpResponse postMapToJsonForResponse(String url, Map<String, Object> formData) {
        String encoding = HttpClientUtils.getDefaultEncoding(null);

        HttpPost post = new HttpPost(url);
        String param = JacksonUtils.marshalToString(formData);
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        nameValues.add(new BasicNameValuePair("param", param));
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValues, encoding);
            post.setEntity(formEntity);
            return HttpClientUtils.client.execute(post);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported Encoding " + encoding, e);
        } catch (SocketTimeoutException e) {
            logger.error("error when postForResponse " + e);
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return null;
    }

    /**
     * 对特定的url的post请求，返回响应体
     *
     * @param url      请求的url
     * @param formData 请求参数
     * @return
     */
    public static HttpResponse postMapForResponse(String url, Map<String, String> formData) {
        String encoding = HttpClientUtils.getDefaultEncoding(null);

        HttpPost post = new HttpPost(url);
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        if (formData != null && !formData.isEmpty()) {
            for (Entry<String, String> entry : formData.entrySet()) {
                nameValues.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValues, encoding);
            post.setEntity(formEntity);
            return HttpClientUtils.client.execute(post);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported Encoding " + encoding, e);
        } catch (SocketTimeoutException e) {
            logger.error("error when postForResponse " + e);
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return null;
    }

    public static HttpResponse postMapAndHeaderForResponse(String url, Map<String, String> headers, Map<String, String> formData) {
        String encoding = HttpClientUtils.getDefaultEncoding(null);

        HttpPost post = new HttpPost(url);
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        if (formData != null && !formData.isEmpty()) {
            for (Entry<String, String> entry : formData.entrySet()) {
                nameValues.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValues, encoding);
            post.setEntity(formEntity);

            //添加header
            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }

            return HttpClientUtils.client.execute(post);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unsupported Encoding " + encoding, e);
        } catch (SocketTimeoutException e) {
            logger.error("error when postForResponse " + e);
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("post [%s] happens error ", url), e);
        }
        return null;
    }

    /**
     * 对特定的url的post请求，返回响应体
     *
     * @param url      请求的url
     * @return
     */
    public static HttpResponse get(String url) {
        HttpGet get = new HttpGet(url);
        try {
            return HttpClientUtils.client.execute(get);
        } catch (SocketTimeoutException e) {
            logger.error("error when get " + e);
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("get [%s] happens error ", url), e);
        }
        return null;
    }

    public static String getWithHeader(String url, Map<String, String> headers) {
        String content = null;
        HttpGet get = new HttpGet(url);

        try {
            //添加header
            if (headers != null && !headers.isEmpty()) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    get.addHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse response = HttpClientUtils.client.execute(get);
            content = EntityUtils.toString(response.getEntity());
        } catch (SocketTimeoutException e) {
            logger.error("error when get " + e);
        } catch (Exception e) {
            HttpClientUtils.logger.error(String.format("get [%s] happens error ", url), e);
        }

        return content;
    }

    private HttpClientUtils() {
    }
}
