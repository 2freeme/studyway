package com.base.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * 
 * <pre>
 * HttpClientUtils
 * </pre>
 * 
 * @author ZHONGNH
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HttpClientUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class.getName());
	
	private final static Logger msgLogger = LoggerFactory.getLogger("MSG." + HttpClientUtils.class.getName());
	
	
	private int connTimeout = 10000;
	
	private int readTimeout = 10000;
	
	private String proxyHost;
	
	private int proxyPort;
	
	private boolean needProxy;

	private HttpResponseHandler responseHandler = new HttpResponseHandler();
	
	private CloseableHttpClient httpclient;

	private CloseableHttpClient httpsclient;
	
	/**
	 * 提交form表单
	 * 
	 * @param url
	 * @param params
	 * @return HttpResponse
	 * @throws Exception
	 */
	public JSONObject postForm(String url, Map<String, Object> params) throws Exception {
		try {
			return this.postForm(url, params, null);
		} catch (Exception ex) {
			logger.error("发送支付平台POST请求失败：", ex);
			throw ex;
		}		
	}
	
	/**
	 * 提交form表单
	 * 
	 * @param url
	 * @param params
	 * @param headers
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject postForm(String url, Map<String, Object> params, Map<String, Object> headers) 
			throws Exception {
		msgLogger.info("Request: " + params.toString()); // 记录发送消息
		logger.debug("url: " + url + ", headers: " + headers + ", 发送请求：" + params.toString());
		
		HttpPost post = new HttpPost(url);
		
		try {
			// 设置body要传的参数
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				Set<Entry<String, Object>> entrySet = params.entrySet();
				
				// 放在HttpRequest root中，就是转动转化为 httpRequest.setParameter(key， value);			
				for (Entry<String, Object> entry : entrySet) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()==null?null:entry.getValue().toString()));
				}
				
				// 设置参数编码字符集
				Charset charset = null;
				String encoding = (String)params.get("charset");
				if (encoding != null) {
					charset = Charset.forName(encoding);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, charset!=null?charset:Consts.UTF_8);
				//StringEntity entity = new StringEntity(formParams.toString(), charset!=null?charset:Consts.UTF_8);
				post.setEntity(entity);
			}
			
			// 设置header要传的参数
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, Object> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue()==null?null:entry.getValue().toString());
				}
			}
			
			// 设置timeout参数
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout);
			customReqConf.setSocketTimeout(readTimeout);
			
			if (needProxy) {
				HttpHost proxy = new HttpHost(proxyHost, proxyPort);
				customReqConf.setProxy(proxy);
			}
			
			post.setConfig(customReqConf.build());
			
			JSONObject json = null;			
			if (url.startsWith("https")) {
				// 执行 Https 请求
				if(httpsclient == null) {// 先判断再加锁可以性能优化
					synchronized(this) {
						if (httpsclient == null) { // double lock checking
							httpsclient = this.createHttpsClient();
						}
					}					
				}
				
				json = httpsclient.execute(post, responseHandler);
			} else {
				// 执行 Http 请求
				if(httpclient == null) {// 先判断再加锁可以性能优化
					synchronized(this) {
						if (httpclient == null) { // double lock checking
							httpclient = this.createHttpClient();
						}
					}
				}
				json = httpclient.execute(post, responseHandler);
			}
			
			logger.debug("### Post:"+post);
			
			return json;
		} finally {
			post.releaseConnection();
		}
	}
	
	/**
	 * 提交JSON content-type=application/json
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public JSONObject postJson(String url,Map<String, Object> params, Map<String, Object> headers) 
			throws Exception {
		msgLogger.info("Request: " + params.toString()); // 记录发送消息
		logger.debug("url: " + url + ", headers: " + headers + ", 发送请求：" + params.toString());
		
		HttpPost post = new HttpPost(url);
		
		try {
			// 设置body要传的参数
			if (params != null && !params.isEmpty()) {
				// 设置参数编码字符集
				Charset charset = null;
				String encoding = (String)params.get("charset");
				if (encoding != null) {
					charset = Charset.forName(encoding);
				}
				
				StringEntity jsonParams = new StringEntity(params.get("json").toString(),charset!=null?charset:Consts.UTF_8);
				
				// 放在HttpRequest		
				post.setEntity(jsonParams);
			}
			
			// 设置header要传的参数
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, Object> entry : headers.entrySet()) {
					post.addHeader(entry.getKey(), entry.getValue()==null?null:entry.getValue().toString());
				}
			}
			
			// 设置timeout参数
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(60000);
			customReqConf.setSocketTimeout(60000);
			
			if (needProxy) {
				HttpHost proxy = new HttpHost(proxyHost, proxyPort);
				customReqConf.setProxy(proxy);
			}
			
			post.setConfig(customReqConf.build());
			
			JSONObject json = null;			
			if (url.startsWith("https")) {
				// 执行 Https 请求
				if(httpsclient == null) {// 先判断再加锁可以性能优化
					synchronized(this) {
						if (httpsclient == null) { // double lock checking
							httpsclient = this.createHttpsClient();
						}
					}					
				}
				
				json = httpsclient.execute(post, responseHandler);
			} else {
				// 执行 Http 请求
				if(httpclient == null) {// 先判断再加锁可以性能优化
					synchronized(this) {
						if (httpclient == null) { // double lock checking
							httpclient = this.createHttpClient();
						}
					}
				}
				json = httpclient.execute(post, responseHandler);
			}
			
			logger.debug("### Post:"+post);			
			return json;
		} finally {
			post.releaseConnection();
		}
	}


	/** 
	 * 方法名称:transMapToString 
	 * 传入参数:map 
	 * 返回值:String 
	*/  
	public static String transMapToString(Map map){  
	  Entry entry;
	  StringBuffer sb = new StringBuffer();
	  for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
	  {
	    entry = (Entry)iterator.next();
//	      sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"": 
	      sb.append(entry.getKey().toString()).append( "=" ).append(null==entry.getValue()?"":
	      entry.getValue().toString()).append (iterator.hasNext() ? "&" : "");  

	  }  
	  return sb.toString();  
	}  
	
	/**
	 * 提交form表单(PC或移动端请求)
	 * zhaoys1
	 * @param url
	 * @param params
	 * @param headers
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject postBerbonForm(String url, Map<String, Object> params) 
			throws Exception {
		String strParam ="";
		strParam=transMapToString(params);
//		strParam = params.toString().replace(" ", "");
//		strParam= strParam.substring(1,strParam.length()-1);	
		
		strParam = strParam.replaceAll("\\+","%2B");  

		HttpPost post = new HttpPost(url+strParam);
		
		try {
			// 设置timeout参数
			Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(connTimeout);
			customReqConf.setSocketTimeout(readTimeout);
			if (needProxy) {
				HttpHost proxy = new HttpHost(proxyHost, proxyPort);
				customReqConf.setProxy(proxy);
			}
			
			post.setConfig(customReqConf.build());
			
			JSONObject json = null;			
			if (url.startsWith("https")) {
				// 执行 Https 请求
				if(httpsclient == null) {// 先判断再加锁可以性能优化
					synchronized(this) {
						if (httpsclient == null) { // double lock checking
							httpsclient = this.createHttpsClient();
						}
					}					
				}
				
				json = httpsclient.execute(post, responseHandler);
			} else {
				// 执行 Http 请求
				if(httpclient == null) {// 先判断再加锁可以性能优化
					synchronized(this) {
						if (httpclient == null) { // double lock checking
							httpclient = this.createHttpClient();
						}
					}
				}
				json = httpclient.execute(post, responseHandler);
				
			}
			
			logger.debug("post:"+post);
			
			return json;	
		}finally {
			post.releaseConnection();
		}
	}
	
	/**
	 * 生成HTTP客户端
	 * 
	 * @return HttpClient
	 */
	private CloseableHttpClient createHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(128);
		cm.setDefaultMaxPerRoute(128);
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
		
		return client;
	}
	
	/**
	 * 生成HTTPS客户端
	 * 
	 * @return HttpClient
	 */
	private CloseableHttpClient createHttpsClient() throws GeneralSecurityException {
		//TODO 骗过SSL验证
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, 
				new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
						return true;
					}
				}).build();
		
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, 
				new X509HostnameVerifier() {
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}

					@Override
					public void verify(String host, SSLSocket ssl)
							throws IOException {
					}

					@Override
					public void verify(String host, X509Certificate cert)
							throws SSLException {
					}

					@Override
					public void verify(String host, String[] cns,
							String[] subjectAlts) throws SSLException {
					}
				});
		
		return HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}
	
	/**
	 * 关闭 HTTP client
	 */
	@PreDestroy
	public void closeHttpClient() {
		if (httpclient != null) {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		if (httpsclient != null) {
			try {
				httpsclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setNeedProxy(boolean needProxy) {
		this.needProxy = needProxy;
	}
	
	/**
	 * 通过GET从移动用户平台拿令牌
	 * @param getUrl
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public JSONObject getTokenAndRoleByGet(String getUrl) throws Exception{
		
		JSONObject returnJson = new JSONObject();
		HttpClient client =  new DefaultHttpClient();
		HttpGet getMethod = new HttpGet(getUrl);
		HttpResponse response = client.execute(getMethod);
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode == 200){
			HttpEntity entity = response.getEntity();
			String content = EntityUtils.toString(entity);
			returnJson = JSONObject.parseObject(content);			
		}else{
			throw new Exception("getTokenAndRoleByGet失败!"+response.getStatusLine().getReasonPhrase());     
		}
		return returnJson;
	}
	
	
}
