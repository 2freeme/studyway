//package com.base.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.google.common.net.HttpHeaders;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.http.Consts;
//import org.apache.http.HttpEntity;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.client.methods.RequestBuilder;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.IOException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//public class IdmHttpClientUtil {
//
//	/**
//     * 设置请求超时
//     */
//    private static final int REQUEST_TIMEOUT = 30 * 1000;
//
//    private static final int SO_TIMEOUT = 60 * 1000;
//
//    /**
//     * 每个主机的最大并行链接数
//     */
//    private static final int MAX_PER_ROUTE = 1000;
//
//    /**
//     * 客户端总并行链接最大数
//     */
//    private static final int MAX_TOTAL = 15000;
//
//    private static HttpClientBuilder httpBuilder = null;
//
//    private static RequestConfig requestConfig;
//
//    private static PoolingHttpClientConnectionManager pccm = null;
//
//    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
//
//    static {
//        try {
//            SSLContext sslContext = SSLContexts.custom().useProtocol("TLS").build();
//            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
//                @Override
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//                @Override
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//                @Override
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//            }}, null);
//            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//                    .register("http", PlainConnectionSocketFactory.INSTANCE)
//                    .register("https", new SSLConnectionSocketFactory(sslContext)).build();
//            pccm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//        } catch (Exception e) {
//            logger.error("连接线程发生异常 PoolHttpClientUtils:{}", e);
//        }
//
//        requestConfig = RequestConfig.custom()
//                .setSocketTimeout(SO_TIMEOUT)
//                .setConnectTimeout(REQUEST_TIMEOUT)
//                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
//                .build();
//        // 多连接的线程安全的管理器
//        if (pccm != null) {
//            pccm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
//            pccm.setMaxTotal(MAX_TOTAL);
//            httpBuilder = HttpClients.custom();
//            httpBuilder.setConnectionManager(pccm);
//        }
//    }
//
//    private static CloseableHttpClient getConnection() {
//        return httpBuilder.build();
//    }
//
//    /**
//     * 通过Post请求以application/json的格式访问链接
//     *
//     * @param url       访问链接
//     * @param params    参数
//     * @param headerMap Header参数
//     * @return 访问结果
//     */
//    public static String postJsonMethod(String url, Map<String, Object> params, Map<String, String> headerMap) {
//        params = params == null || params.isEmpty() ? new HashMap<String, Object>() : params;
//        String jsonParamStr = JSONObject.toJSONString(params);
//        HttpUriRequest post = RequestBuilder.post().setUri(url)
//                .setEntity(new StringEntity(jsonParamStr, Consts.UTF_8))
//                .setConfig(requestConfig).build();
//        post.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
//        if (MapUtils.isNotEmpty(headerMap)) {
//            Set<String> headerKeys = headerMap.keySet();
//            for (String k : headerKeys) {
//                post.setHeader(k, headerMap.get(k));
//            }
//        }
//        return execute(post);
//    }
//    /**
//     * 通过Post请求以application/json的格式访问链接
//     * 保留Json串中的NULL
//     * @param url 访问链接
//     * @param params 参数
//     * @param headerMap
//     * @return Header参数
//     */
//    public static String postJsonMethodRetainNull(String url, Map<String, Object> params, Map<String, String> headerMap) {
//        params = params == null || params.isEmpty() ? new HashMap<String, Object>() : params;
//        logger.debug("推送4A接口参数："+JSONObject.toJSONString(params));
//        String jsonParamStr = JSONObject.toJSONString(params, SerializerFeature.WriteMapNullValue);
//        HttpUriRequest post = RequestBuilder.post().setUri(url)
//                .setEntity(new StringEntity(jsonParamStr, Consts.UTF_8))
//                .setConfig(requestConfig).build();
//        post.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
//        if (MapUtils.isNotEmpty(headerMap)) {
//            Set<String> headerKeys = headerMap.keySet();
//            for (String k : headerKeys) {
//                post.setHeader(k, headerMap.get(k));
//            }
//        }
//        return execute(post);
//    }
//
//    /**
//     * 执行链接操作
//     *
//     * @param request 链接操作
//     * @return 操作结果
//     */
//    private static String execute(HttpUriRequest request) {
//        final int success = 200;
//        CloseableHttpResponse response = null;
//        try {
//            response = getConnection().execute(request);
//            HttpEntity entity = response.getEntity();
//            if (response.getStatusLine().getStatusCode() != success) {
//                logger.error("[HttpUtils Post] API request failed -uri:"
//                        .concat(request.getURI().toString())
//                        .concat("-response status:")
//                        .concat(String.valueOf(response.getStatusLine().getStatusCode())));
//                request.abort();
//            }
//            if (null != entity) {
//                return EntityUtils.toString(entity, Consts.UTF_8);
//            }
//        } catch (IOException e) {
//            logger.error(e.getLocalizedMessage(), e);
//        } finally {
//            if(response != null) {
//                try {
//                    response.close();
//                } catch (IOException e) {
//                    logger.error("[HttpUtils Post] response close error.", e.getLocalizedMessage());
//                }
//            }
//            if (pccm != null) {
//                pccm.closeExpiredConnections();
//                pccm.closeIdleConnections(5, TimeUnit.SECONDS);
//            }
//        }
//        return null;
//    }
//}
