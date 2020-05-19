package com.keminapera.constellation.leo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送http请求的工具类
 *
 * @author KeminaPera
 * @date 2019/10/2 9:40
 */
@Slf4j
public final class HttpUtil {

    private static CookieStore cookieStore = new BasicCookieStore();
    /**
     * 发送GET请求
     * @param url 请求的url
     * @param param 参数
     * @return string
     */
    public static String sendGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            log.error("请求的uri路径为{}", uri);
           /* BasicClientCookie cookie = new BasicClientCookie("BAIDUID", "5D60DCBB8E914EE6505AB45F080189E5:FG=1");
            cookie.setDomain(".baidu.com");
            cookie.setPath("/");
            cookie.setExpiryDate(new Date("Sun, 04 Oct 2020 12:34:17 GMT"));
            cookieStore.addCookie(cookie);*/
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                resultString = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
            }
        } catch (Exception e) {
            log.error("context", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                log.error("context", e);
            }
        }
        return resultString;
    }

    /**
     * 发送无参GET请求
     * @param url 请求的url
     * @return string
     */
    public static String sendGet(String url) {
        return sendGet(url, null);
    }

    /**
     * 发送POST请求
     * @param url 请求的url
     * @param param 参数
     * @return string
     */
    public static String sendPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                        paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            log.error("context", e);
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                log.error("context", e);
            }
        }
        return resultString;
    }
}
