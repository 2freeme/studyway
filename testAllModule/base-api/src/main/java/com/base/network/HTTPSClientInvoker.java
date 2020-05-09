//package com.base.network;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
//import org.apache.http.util.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
//import javax.net.ssl.*;
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.*;
//import java.net.Proxy.Type;
//import java.nio.charset.Charset;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
///**
// *
// * 调用HTTPS的接口
// * <pre>
// * 。
// * </pre>
// *
// * @author 夏文斌 wenbin.xia@midea.com.cn
// * @version 1.00.00
// *
// *          <pre>
// * 修改记录
// *    修改后版本:     修改人：  修改日期:     修改内容:
// * </pre>
// */
//public class HTTPSClientInvoker {
//
//	private static final Logger logger = LoggerFactory.getLogger(HTTPSClientInvoker.class.getName());
//
//
//	private  static  String proxyHost;
//
//
//	private static int proxyPort;
//
//	private static final String DEFAULT_CHARSET = "UTF-8";
//
//	private static final String _GET = "GET"; // GET
//    private static final String _POST = "POST";// POST
//    public static final int DEF_CONN_TIMEOUT = 60000;
//    public static final int DEF_READ_TIMEOUT = 60000;
//
//	@Value("${proxyHost}")
//	public  void setProxyHost(String proxyHost) {
//		HTTPSClientInvoker.proxyHost = proxyHost;
//	}
//
//	@Value("${proxyPort}")
//	public  void setProxyPort(int proxyPort) {
//		HTTPSClientInvoker.proxyPort = proxyPort;
//	}
//
//	 /**
//	    * 初始化http请求参数
//	    *
//	    * @param url
//	    * @param method
//	    * @return
//	    * @throws Exception
//	    */
//	   private static HttpURLConnection initHttps(String url, String method,
//	           Map<String, String> headers,boolean isProxy,String contentType) throws Exception {
//	       TrustManager[] tm = { new MyX509TrustManager() };
//	       System.setProperty("https.protocols", "TLSv1");
//	       SSLContext sslContext = SSLContext.getInstance("TLS");
//	       sslContext.init(null, tm, new java.security.SecureRandom());
//	       // 从上述SSLContext对象中得到SSLSocketFactory对象
//	       URL _url = new URL(url);
//	       HttpURLConnection http=null;
//	       if(isProxy){
//	    	   Proxy proxy1=new Proxy(Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
//	    	   HttpsURLConnection https = (HttpsURLConnection) _url.openConnection(proxy1);
//		       // 设置域名校验
//		       https.setHostnameVerifier(new HTTPSClientInvoker().new TrustAnyHostnameVerifier());
//
//		       SSLSocketFactory ssf = sslContext.getSocketFactory();
//			   https.setSSLSocketFactory(ssf);
//		       http = https;
//	       }else{
//	    	   http = (HttpURLConnection) _url.openConnection();
//	       }
//	       // 连接超时
//	       http.setConnectTimeout(DEF_CONN_TIMEOUT);
//	       // 读取超时 --服务器响应比较慢，增大时间
//	       http.setReadTimeout(DEF_READ_TIMEOUT);
//
//	       if(contentType==null){
//		       http.setRequestProperty("Content-Type",
//		               "application/x-www-form-urlencoded");
//	       }else{
//	    	   http.setRequestProperty("Content-Type", contentType);
//	       }
//	       http.setRequestProperty("Charset", DEFAULT_CHARSET);
//	       http.setUseCaches(false);
//	       http.setRequestMethod(method);
//	       http.setRequestProperty(
//	               "User-Agent",
//	               "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
//	      if (null != headers && !headers.isEmpty()) {
//	          for (Entry<String, String> entry : headers.entrySet()) {
//	              http.setRequestProperty(entry.getKey(), entry.getValue());
//	          }
//	      }
//	      http.setDoOutput(true);
//	      http.setDoInput(true);
//	      http.connect();
//	      return http;
//	  }
//
//
//	/**
//	 * @zhongnh
//	 *
//	 * @description 功能描述: get 请求
//	 * @return 返回类型:
//	 * @throws Exception
//	 */
//	public static String invokeGetMethodByHttps(String url, Map<String, String> params,
//												Map<String, String> headers,boolean isProxy) throws Exception {
//		logger.debug("代理ip:{},端口{},是否启用：{}",proxyHost,proxyPort,isProxy);
//		HttpURLConnection http = null;
//		InputStream in = null;
//		StringBuffer bufferRes = null;
//
//		try {
//			http = initHttps(initParams(url, params), _GET, headers,isProxy,null);
//			in = http.getInputStream();
//			BufferedReader read = new BufferedReader(new InputStreamReader(in,
//					DEFAULT_CHARSET));
//			String valueString = null;
//			bufferRes = new StringBuffer();
//			while ((valueString = read.readLine()) != null) {
//				bufferRes.append(valueString);
//			}
//		} finally {
//			try {
//				if (in != null) in.close();
//			} catch (Exception ex) {
//				logger.error("关闭资源失败", ex);
//			}
//			if (http != null) http.disconnect();// 关闭连接
//		}
//		return bufferRes.toString();
//	}
//
//	  /**
//	  *
//	   * @description 功能描述: get 请求  默认不走代理
//	   * @return 返回类型:
//	   * @throws Exception
//	   */
//	  public static String invokeGetMethodByHttps(String url, Map<String, String> params,
//	          Map<String, String> headers) throws Exception {
//	     return invokeGetMethodByHttps(url, params, headers,false);
//	  }
//
//	  public static String invokeGetMethodByHttps(String url) throws Exception {
//	      return invokeGetMethodByHttps(url, null,false);
//	  }
//	  public static String invokeGetMethodByHttps(String url,boolean isProxy) throws Exception {
//	      return invokeGetMethodByHttps(url, null,isProxy);
//	  }
//
//	  public static String invokeGetMethodByHttps(String url, Map<String, String> params,boolean isProxy)
//	          throws Exception {
//	      return invokeGetMethodByHttps(url, params, null,isProxy);
//	  }
//	  public static String invokeGetMethodByHttps(String url, Map<String, String> params)
//	          throws Exception {
//	      return invokeGetMethodByHttps(url, params, null,false);
//	  }
//
//	public static String invokePostMethodByHttps(String url, String params,String contentType,boolean isProxy)
//			throws Exception {
//		logger.debug("代理ip:{},端口{},是否启用：{}",proxyHost,proxyPort,isProxy);
//
//		HttpURLConnection http = null;
//		OutputStream out = null;
//		InputStream in = null;
//		StringBuffer bufferRes = null;
//
//		try {
//			http = initHttps(url, _POST, null,isProxy,contentType);
//			if(params!=null){
//				out = http.getOutputStream();
//				out.write(params.getBytes(DEFAULT_CHARSET));
//				out.flush();
//				out.close();
//			}
//
//			in = http.getInputStream();
//			BufferedReader read = new BufferedReader(new InputStreamReader(in,
//					DEFAULT_CHARSET));
//			String valueString = null;
//			bufferRes = new StringBuffer();
//			while ((valueString = read.readLine()) != null) {
//				bufferRes.append(valueString);
//			}
//		} finally {
//			try {
//				if (out != null) out.close();
//				if (in != null) in.close();
//			} catch (Exception ex) {
//				logger.error("关闭资源失败", ex);
//			}
//			if (http != null) http.disconnect();// 关闭连接
//		}
//
//		return bufferRes.toString();
//	}
//	public static String invokePostMethodByHttps(String url, String params,String contentType)
//			throws Exception {
//		return invokePostMethodByHttps(url,params,contentType,false);
//	}
//
//
//		/**
//		 * 调用http post方法
//		 *
//		 * @param url
//		 * @param params
//		 * @return
//		 */
//		public static String invokePostMethodByHttp(String url, NameValuePair[] data,boolean isProxy)
//			throws Exception {
//
//		StringBuilder stringBuilder=new StringBuilder();
//		if (data != null && data.length > 0) {
//			for (int i = 0; i < data.length; i++) {
//				String key=data[i].getName();
//				stringBuilder.append(key);
//				stringBuilder.append("=");
//				String value=  URLEncoder.encode(data[i].getValue(), "UTF-8");
//				stringBuilder.append(value);
//				stringBuilder.append("&");
//
//			}
//		}
//		String paramsContent=stringBuilder.toString();
//
//		HttpURLConnection http = null;
//		OutputStream out = null;
//		StringBuffer bufferRes = null;
//
//		try {
//			http = initHttps(url, _POST, null,isProxy,null);
//			out = http.getOutputStream();
//			out.write(paramsContent.getBytes(DEFAULT_CHARSET));
//			out.flush();
//			out.close();
//			InputStream in = http.getInputStream();
//			BufferedReader read = new BufferedReader(new InputStreamReader(in,
//					DEFAULT_CHARSET));
//			String valueString = null;
//			bufferRes = new StringBuffer();
//			while ((valueString = read.readLine()) != null) {
//				bufferRes.append(valueString);
//			}
//		} finally {
//			try {
//				if (out != null) out.close();
//			} catch (Exception ex) {
//				logger.error("关闭资源失败", ex);
//			}
//			if (http != null) http.disconnect();// 关闭连接
//		}
//		return bufferRes.toString();
//	}
//	public static String invokePostMethodByHttp(String url, NameValuePair[] data)
//			throws Exception {
//		 return invokePostMethodByHttp(url,data,false);
//		}
//
//	  /**
//	   * 功能描述: 构造请求参数
//	   *
//	   * @return 返回类型:
//	   * @throws Exception
//	   */
//	  public static String initParams(String url, Map<String, String> params)
//	          throws Exception {
//	      if (null == params || params.isEmpty()) {
//	          return url;
//	      }
//	      StringBuilder sb = new StringBuilder(url);
//	      if (url.indexOf("?") == -1) {
//	          sb.append("?");
//	      }
//	      sb.append(map2Url(params));
//	      return sb.toString();
//	  }
//
//	  /**
//	  * map构造url
//	   *
//	   * @return 返回类型:
//	   * @throws Exception
//	   */
//	  public static String map2Url(Map<String, String> paramToMap)
//	          throws Exception {
//	      if (null == paramToMap || paramToMap.isEmpty()) {
//	          return null;
//	      }
//	      StringBuffer url = new StringBuffer();
//	      boolean isfist = true;
//	      for (Entry<String, String> entry : paramToMap.entrySet()) {
//	         if (isfist) {
//	             isfist = false;
//	          } else {
//	              url.append("&");
//	          }
//	          url.append(entry.getKey()).append("=");
//	          String value = entry.getValue();
//	          if (!StringUtils.isEmpty(value)) {
//	              url.append(URLEncoder.encode(value, DEFAULT_CHARSET));
//	          }
//	      }
//	      return url.toString();
//	  }
//
//	  /**
//	   * 检测是否https
//	   *
//	   * @param url
//	   */
//	 private static boolean isHttps(String url) {
//	      return url.startsWith("https");
//	  }
//
//	  /**
//	   * https 域名校验
//	   *
//	   * @param url
//	   * @param params
//	   * @return
//	   */
//	  public class TrustAnyHostnameVerifier implements HostnameVerifier {
//	      public boolean verify(String hostname, SSLSession session) {
//	          return true;// 直接返回true
//	      }
//	  }
//	  public static class MyX509TrustManager implements X509TrustManager {
//			// 检查客户端证书
//			public void checkClientTrusted(X509Certificate[] chain, String authType)
//					throws CertificateException {
//			}
//
//			// 检查服务器端证书
//			public void checkServerTrusted(X509Certificate[] chain, String authType)
//					throws CertificateException {
//			}
//
//			// 返回受信任的x509数组
//			public X509Certificate[] getAcceptedIssuers() {
//				return null;
//			}
//		}
//
//	  public static String invokePostMethodByStringEntity(JSONObject jsonObj,String url,boolean isProxy) throws Exception {
//		  return HTTPSClientInvoker.invokePostMethodByStringEntity(jsonObj, url, isProxy, DEF_READ_TIMEOUT);
//	  }
//
//	  /**
//	  * 调用http post方法
//	  *
//	  * @param url
//	  * @param params
//	  * @return
//	  */
//	  public static String invokePostMethodByStringEntity(JSONObject jsonObj,String url,boolean isProxy,int readTimeOut) {
//		 String res=null;
//		// HttpPost post=null;
//		 StringEntity entity=null;
//		 CloseableHttpClient httpClient=null;
//		 CloseableHttpResponse response=null;
//		  try{
//			  RequestConfig requestConfig = RequestConfig.custom()
//			  	        .setConnectTimeout(DEF_CONN_TIMEOUT).setConnectionRequestTimeout(DEF_CONN_TIMEOUT)
//			  	        .setSocketTimeout(readTimeOut).build();
//			  	 HttpClientBuilder httpClientBuilder = HttpClients.custom();
//
//			  	 if(isProxy){
//			  		  HttpHost proxy = new HttpHost(proxyHost, proxyPort);
//					  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
//					  httpClientBuilder.setRoutePlanner(routePlanner);
//			  	 }
//				  httpClient = httpClientBuilder.build();
//				  HttpPost post = new HttpPost(url);
//				  post.setConfig(requestConfig);
//				  // 构造消息头
//				  post.setHeader("Content-type", "application/json; charset=utf-8");
//				  post.setHeader("Connection", "Close");
//				  /*String sessionId = getSessionId();
//				  post.setHeader("SessionId", sessionId);
//				  post.setHeader("appid", appid);*/
//
//				  // 构建消息实体
//				  entity = new StringEntity(jsonObj.toString(), Charset.forName("UTF-8"));
//				  entity.setContentEncoding("UTF-8");
//				  // 发送Json格式的数据请求
//				  entity.setContentType("application/json");
//				  post.setEntity(entity);
//				  response= httpClient.execute(post);
//				  res= EntityUtils.toString(response.getEntity(), "UTF-8");
//				  EntityUtils.consume(entity);
//		  }catch(Exception e){
//			  logger.error("invokePostMethodByStringEntity httpClient error:"+e.getMessage(), e);
//			  throw new ApplicationException(e.getMessage(), e);
//		  }finally{
//			  if(httpClient != null){
//		            try {
//		            	  httpClient.close();
//		            } catch (Exception ex) {
//		              logger.error("invokePostMethodByStringEntity httpClient error:"+ex.getCause(),ex);
//		            }
//		        }
//			  if(response != null){
//		            try {
//		            	  response.close();
//		            } catch (Exception ex) {
//		              logger.error("invokePostMethodByStringEntity response error:"+ex.getCause(),ex);
//		            }
//		        }
//
//		  }
//		  return res;
//	  }
//
//
//		public static Map<String,Object> getProxyInfo(){
//			Map<String,Object> rmp=new HashMap<String,Object>();
//			try{
//				rmp.put("proxyHost", proxyHost);
//				rmp.put("proxyPort", proxyPort);
//			}catch(Exception e){
//				logger.error("getProxyInfo" + e.getMessage(),e);
//			}
//			return rmp;
//		};
//}
