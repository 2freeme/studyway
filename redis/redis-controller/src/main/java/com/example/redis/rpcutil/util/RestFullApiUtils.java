package com.example.redis.rpcutil.util;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhaolp on 2017/7/12 0012.
 * RestAPI工具类
 */

public class RestFullApiUtils {
    public static SimpleClientHttpRequestFactory getSimpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60*1000);//一分钟连接超时
        requestFactory.setReadTimeout(30*1000);//30秒读取数据超时 fixed by zhongnh (考虑异步化)
        return requestFactory;
    }
    public static RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate(getSimpleClientHttpRequestFactory());
        return restTemplate;
    }
}
