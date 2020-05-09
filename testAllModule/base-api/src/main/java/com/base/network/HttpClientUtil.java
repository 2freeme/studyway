//package com.base.network;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.config.Registry;
//import org.apache.http.config.RegistryBuilder;
//import org.apache.http.conn.socket.ConnectionSocketFactory;
//import org.apache.http.conn.socket.PlainConnectionSocketFactory;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.net.ssl.*;
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.InetSocketAddress;
//import java.net.Proxy;
//import java.net.URL;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.util.Map;
//
//
///**
// * 利用HttpClient进行post请求的工具类
// * @ClassName: HttpClientUtil
// * @Description: TODO
// * @author Devin <xxx>
// * @date 2017年2月7日 下午1:43:38
// *
// */
//public class HttpClientUtil {
//
//	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class.getName());
//
//	private static final String DEFAULT_CHARSET = "UTF-8";
//    public static final int DEF_CONN_TIMEOUT = 60000;
//    public static final int DEF_READ_TIMEOUT = 60000;
//
//
//
//	@SuppressWarnings("resource")
//    public static String doPost(String url,String jsonstr,String charset,boolean isProxy){
//        HttpClient httpClient = null;
//        HttpPost httpPost = null;
//        String result = null;
//        try{
//            httpClient = new SSLClient();
//
//            HttpClientBuilder httpClientBuilder = HttpClients.custom();
//           /* if(isProxy){
//		  		  HttpHost proxy = new HttpHost(proxyHost, proxyPort);
//				  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//				  httpClientBuilder.setRoutePlanner(routePlanner);
//		  	 }*/
//			 httpClient = httpClientBuilder.build();
//
//            httpPost = new HttpPost(url);
//            httpPost.addHeader("Content-Type", "application/json");
//            StringEntity se = new StringEntity(jsonstr);
//            se.setContentType("text/json");
//            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
//            httpPost.setEntity(se);
//            HttpResponse response = httpClient.execute(httpPost);
//            if(response != null){
//                HttpEntity resEntity = response.getEntity();
//                if(resEntity != null){
//                    result = EntityUtils.toString(resEntity,charset);
//                }
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return result;
//    }
//
//	public static String do_Post(String url, String body ,boolean isProxy) throws Exception {
//		HttpPost httpPost = null;
//		String responseContent = null;
//		try {
//			HttpClient httpClient = new SSLClient();
//			httpPost = new HttpPost(url);
//			httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
//			httpPost.setEntity(new StringEntity(body, "UTF-8"));
//			HttpResponse response = httpClient.execute(httpPost);
//			HttpEntity entity = response.getEntity();
//			responseContent = EntityUtils.toString(entity, "UTF-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return responseContent;
//	}
//
//	////------------------
//
//	/**
//	* 绕过验证
//	*
//	* @return
//	* @throws NoSuchAlgorithmException
//	* @throws KeyManagementException
//	*/
//	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
//	        SSLContext sc = SSLContext.getInstance("SSLv3");
//
//	        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
//	        X509TrustManager trustManager = new X509TrustManager() {
//	            @Override
//	            public void checkClientTrusted(
//	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
//	                    String paramString) throws CertificateException {
//	            }
//
//	            @Override
//	            public void checkServerTrusted(
//	                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
//	                    String paramString) throws CertificateException {
//	            }
//
//	            @Override
//	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//	                return null;
//	            }
//	        };
//	        sc.init(null, new TrustManager[] { trustManager }, null);
//	        return sc;
//	    }
//
//	public static String dosslpost(String url,String body,boolean isProxy)throws Exception{
//		String rlt="";
//         try{
//     	    //采用绕过验证的方式处理https请求
//     	    SSLContext sslcontext = createIgnoreVerifySSL();
//     	    //设置协议http和https对应的处理socket链接工厂的对象
//     	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
//     	        .register("http", PlainConnectionSocketFactory.INSTANCE)
//     	        .register("https", new SSLConnectionSocketFactory(sslcontext))
//     	        .build();
//     	    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//     	    //创建自定义的httpclient对象
//     	    CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();
//
//     	    try{
//     	        //创建post方式请求对象
//     	        HttpPost httpPost = new HttpPost(url);
//     	        HttpClientBuilder httpClientBuilder = HttpClients.custom();
//
//			  	/* if(isProxy){
//			  		  HttpHost proxy = new HttpHost(proxyHost, proxyPort);
//					  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//					  httpClientBuilder.setRoutePlanner(routePlanner);
//			  	 }*/
//			  	client = httpClientBuilder.build();
//
//     	       httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
//   			   httpPost.setEntity(new StringEntity(body, DEFAULT_CHARSET));
//   		       CloseableHttpResponse response = client.execute(httpPost);
//   			   HttpEntity entity = response.getEntity();
//   			   rlt = EntityUtils.toString(entity, DEFAULT_CHARSET);
//
//     	    }finally{
//     	        client.close();
//     	    }
//
//         }catch(Exception e){
//        	 logger.error("dosslpost error :"+e.getMessage(),e);
//         }
//		return rlt;
//	}
//
//
//	/**
//     * 注意：###########
//     *       支持http与HTTPS，绕开证书请求,http代理暂时没有测试，建议使用原来的方法
//     *
//     * @param url   需要请求的网关路径
//     * @param sendData  请求时需要传入的参数
//     * @param urlencode url的编码格式
//     * @param connTimeOut   链接超时时间
//     * @param readTimeOut   读取超时时间
//     * @param contentType   请求头部  固定输入"application/x-www-form-urlencoded;charset="+urlencode
//     * @param header     输入null
//     * @return
//     */
//    public static String sendAndRcvHttpPostBase(String url,String sendData,Map<String,String> header,boolean isProxy,
//    		String proxyHost,int proxyPort){
//        String result = "";
//        try {
//        	result = sendAndRcvHttpPostInternal(url,sendData,header,isProxy,proxyHost,proxyPort);
//        } catch(IOException e){
//        	logger.error("http通讯失败 !",e);
//            result = null;
//        } catch(Exception e){
//        	 logger.error("http通讯失败 !",e);
//            result = null;
//        }
//        return result;
//    }
//    protected static String sendAndRcvHttpPostInternal(String url,String sendData,Map<String,String> header,boolean isProxy,
//    		String proxyHost,int proxyPort)throws Exception {
//        Long curTime = System.currentTimeMillis();
//        String result = "";
//        BufferedReader in = null;
//        DataOutputStream out = null;
//        int code = 999;
//        HttpsURLConnection httpsConn = null;
//        HttpURLConnection httpConn = null;
//
//        try{
//            URL myURL = new URL(url);
//            logger.debug("请求地址："+url);
//            if(url.startsWith("https://")){
//
//            	if(isProxy){
//            		// 初始化proxy对象
//     	    	   Proxy proxy=new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
//     	    	   httpsConn =    (HttpsURLConnection) myURL.openConnection(proxy);
//     		       // 设置域名校验
//     	    	    httpsConn.setHostnameVerifier(new HTTPSClientInvoker().new TrustAnyHostnameVerifier());
//     	       }else{
//     	    	  httpsConn =    (HttpsURLConnection) myURL.openConnection();
//     	       }
//
//                TrustManager[] trustAllCerts = new TrustManager[]{
//                        new X509TrustManager() {
//                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                                return null;
//                            }
//                            public void checkClientTrusted(
//                                java.security.cert.X509Certificate[] certs, String authType) {
//                            }
//                            public void checkServerTrusted(
//                                java.security.cert.X509Certificate[] certs, String authType) {
//                            }
//                        }
//                    };
//                SSLContext sc = SSLContext.getInstance("TLS");
//                sc.init(null, trustAllCerts, new java.security.SecureRandom());
//                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
//                HostnameVerifier hv = new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String urlHostName, SSLSession session) {
//                        return true;
//                    }
//                };
//                httpsConn.setHostnameVerifier(hv);
//
//                httpsConn.setRequestProperty("Accept-Charset", "UTF-8");
//                httpsConn.setRequestProperty("User-Agent","java HttpsURLConnection");
//                if(header!=null){
//                    for(String key:header.keySet()){
//                        httpsConn.setRequestProperty(key, (String)header.get(key));
//                    }
//                }
//                httpsConn.setRequestMethod("POST");
//                httpsConn.setUseCaches(false);
//               // httpsConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
//                httpsConn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
//                httpsConn.setConnectTimeout(DEF_CONN_TIMEOUT);
//                httpsConn.setReadTimeout(DEF_READ_TIMEOUT);
//                httpsConn.setDoInput(true);
//                httpsConn.setInstanceFollowRedirects(true);
//                if(sendData !=null){
//                    httpsConn.setDoOutput(true);
//                    // 获取URLConnection对象对应的输出流
//                    out = new DataOutputStream(httpsConn.getOutputStream());
//                    // 发送请求参数
//                    out.write(sendData.getBytes("UTF-8"));
//                    // flush输出流的缓冲
//                    out.flush();
//                    out.close();
//                }
//                // 取得该连接的输入流，以读取响应内容
//                in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(),"UTF-8"));
//                code = httpsConn.getResponseCode();
//            }else{
//
//                if(isProxy){
//                	  Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
//                	  httpConn =    (HttpURLConnection) myURL.openConnection(proxy);
//                }else{
//                	 httpConn =    (HttpURLConnection) myURL.openConnection();
//                }
//
//                httpConn.setRequestProperty("Accept-Charset", "UTF-8");
//                httpConn.setRequestProperty("user-agent","java HttpURLConnection");
//                if(header!=null){
//                    for(String key:header.keySet()){
//                        httpConn.setRequestProperty(key, (String)header.get(key));
//                    }
//                }
//                httpConn.setRequestMethod("POST");
//                httpConn.setUseCaches(false);
//                httpConn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
//                httpConn.setConnectTimeout(DEF_CONN_TIMEOUT);
//                httpConn.setReadTimeout(DEF_READ_TIMEOUT);
//                httpConn.setDoInput(true);
//                httpConn.setInstanceFollowRedirects(true);
//                if(sendData !=null){
//                    httpConn.setDoOutput(true);
//                    // 获取URLConnection对象对应的输出流
//                    out = new DataOutputStream(httpConn.getOutputStream());
//                    // 发送请求参数
//                    out.write(sendData.getBytes("UTF-8"));
//                    // flush输出流的缓冲
//                    out.flush();
//                    out.close();
//                }
//                // 取得该连接的输入流，以读取响应内容
//                in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
//                code = httpConn.getResponseCode();
//            }
//            if (HttpURLConnection.HTTP_OK == code){
//                String line;
//                while ((line = in.readLine()) != null) {
//                    result += line;
//                    logger.debug("请求数据返回结果=="+line);
//                }
//                if(result.length()>2000){
//                	logger.debug("http返回结果 !\n"+result.substring(0,2000)+"...");
//                }else{
//                	logger.debug( "http返回结果 !\n"+result);
//                }
//            }else{
//                result = null;
//                throw new Exception("请求失败,服务端响应码："+code);
//            }
//        }finally{
//        	 logger.debug("对方地址："+url);
//            if(out!=null){
//                try {
//                    out.close();
//                } catch (IOException e) {
//                }
//            }
//            if(httpConn!=null){
//                httpConn.disconnect();
//            }
//            if(httpsConn!=null){
//                httpsConn.disconnect();
//            }
//            if(in!=null){
//                try {
//                    in.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//        logger.debug("SimpleHttpConnUtil "+curTime+" end for "+(System.currentTimeMillis()-curTime)+"ms");
//        return result;
//    }
//
//
//}
//