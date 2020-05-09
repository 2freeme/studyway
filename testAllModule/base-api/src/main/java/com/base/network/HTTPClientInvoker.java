//package com.base.network;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.net.MediaType;
//import org.apache.commons.httpclient.HostConfiguration;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.*;
//import org.apache.commons.httpclient.params.HttpMethodParams;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//public class HTTPClientInvoker {
//	private static final Logger logger = LoggerFactory.getLogger(HTTPClientInvoker.class.getName());
//
//	public static final int DEF_CONN_TIMEOUT = 60000;
//    public static final int DEF_READ_TIMEOUT = 60000;
//	/**
//	 * 调用http get方法
//	 *
//	 * @param url
//	 * @return
//	 */
//	public static String invokeGetMethodByHttp(String url) throws Exception {
//		/*
//		 * String result = ""; GetMethod request = new GetMethod(url);
//		 * request.setRequestHeader("Charset", "utf-8"); HttpClient client = new
//		 * HttpClient(); client.executeMethod(request); result =
//		 * request.getResponseBodyAsString(); return result;
//		 */
//		return invokeGetMethodByHttp(url,DEF_CONN_TIMEOUT,DEF_READ_TIMEOUT);
//	}
//
//	/**
//	 * 调用http， conn_timeout默认写死60秒，read_timeout可以根据业务需求调整（2019.09.12）
//	 * 时间单位：million second
//	 * @param url
//	 * @return
//	 */
//	public static String invokeGetMethodByHttp(String url,int conn_timeout,int read_timeout) throws Exception {
//		String result = "";
//		GetMethod request = new GetMethod(url);
//		request.setRequestHeader("Charset", "utf-8");
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(DEF_CONN_TIMEOUT);
//		client.getHttpConnectionManager().getParams().setSoTimeout(read_timeout);
//		client.executeMethod(request);
//		result = request.getResponseBodyAsString();
//		return result;
//	}
//
//	/**
//	 * 调用http post方法
//	 * 默认contentType="application/json"
//	 * @param url
//	 * @param body
//	 * @return
//	 */
//	public static String invokePostMethodByHttp(String url, String body)
//			throws Exception {
//		return invokePostMethodByHttp(url, body, "application/json");
//	}
//
//	/**
//	 * 调用http post方法
//	 *
//	 * @param url
//	 * @param body
//	 * @param contentType
//	 * @return
//	 */
//	public static String invokePostMethodByHttp(String url, String body, String contentType)
//		throws Exception {
//	    String result = "";
//	    PostMethod request = new PostMethod(url);
//	    HttpClient client = new HttpClient();
//	    request.getParams().setContentCharset("UTF-8");
//	    request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//	    request.setRequestHeader("Content-Type", contentType);
//	    request.setRequestHeader("Charset", "UTF-8");
//	    request.setRequestBody(body);
//	    client.executeMethod(request);
//        result = request.getResponseBodyAsString();
//        return result;
//	}
//
//	/**
//	 * 调用http post方法
//	 *
//	 * @param url
//	 * @param body
//	 * @param contentType
//	 * @return
//	 */
//	public static String invokePostMethodByHttp(String url, String body, String contentType, HostConfiguration hostConfiguration)
//			throws Exception {
//		String result = "";
//		PostMethod request = new PostMethod(url);
//		HttpClient client = new HttpClient();
//		request.getParams().setContentCharset("UTF-8");
//		request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//		request.setRequestHeader("Content-Type", contentType);
//		request.setRequestHeader("Charset", "UTF-8");
//		request.setRequestBody(body);
//		if (hostConfiguration != null) {
//			client.setHostConfiguration(hostConfiguration);
//		}
//		client.executeMethod(request);
//		result = request.getResponseBodyAsString();
//		return result;
//	}
//
//	/**
//	 * 调用http post方法
//	 *
//	 * @param url
//	 * @param params
//	 * @param body
//	 * @param contentType
//	 * @return
//	 */
//	public static String invokePostMethodByHttp(String url, HashMap<String,String> params,String body, String contentType)
//		throws Exception {
//	    String result = "";
//	    PostMethod request = new PostMethod(url);
//	    HttpClient client = new HttpClient();
//	    request.getParams().setContentCharset("UTF-8");
//	    request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//	    request.setRequestHeader("Content-Type", contentType);
//	    request.setRequestHeader("Charset", "UTF-8");
//	    for (Entry<String, String> entry : params.entrySet()) {
//	    	request.setParameter(entry.getKey(), entry.getValue());
//	    }
//	    request.setRequestBody(body);
//	  /*  client.getHostConfiguration().setProxy("172.16.6.191", 8080);//设置代理，使finddler工具能抓取 httpclient发送的请求
//	  */
//	    client.executeMethod(request);
//        result = request.getResponseBodyAsString();
//        return result;
//	}
//	/**
//	 * 调用http post方法
//	 *
//	 * @param url
//	 * @param params
//	 * @return
//	 */
//	public static String invokePostMethodByHttp(String url, Map<String,String> params)
//		throws Exception {
//	    String result = "";
//	    PostMethod request = new PostMethod(url);
//	    HttpClient client = new HttpClient();
//	    for (Entry<String, String> entry : params.entrySet()) {
//	    	request.setParameter(entry.getKey(), entry.getValue());
//	    }
//	    request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//	    client.executeMethod(request);
//        result = request.getResponseBodyAsString();
//        return result;
//	}
//	/**
//	 * 调用http post方法
//	 *
//	 * @param url
//	 * @param params
//	 * @return
//	 */
//	public static String invokePostMethodByHttp(String url, NameValuePair[] data)
//		throws Exception {
//	    String result = "";
//	    PostMethod request = new PostMethod(url);
//	    HttpClient client = new HttpClient();
//	    request.getParams().setContentCharset("UTF-8");
//	    request.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//	    request.setRequestHeader("Charset", "UTF-8");
//	    request.setRequestBody(data);
//        client.executeMethod(request);
//        result = request.getResponseBodyAsString();
//        return result;
//	}
//
//	/**
//	 * 调用http post方法
//	 *
//	 * @param url
//	 * @param body
//	 * @param contentType
//	 * @return
//	 */
//	public static String invokePutMethodByHttp(String url, String body,
//			String contentType) throws Exception {
//		String result = "";
//		PutMethod request = new PutMethod(url);
//		HttpClient client = new HttpClient();
//		request.setRequestHeader("Content-Type", contentType);
//		request.setRequestHeader("Charset", "utf-8");
//		request.setRequestBody(body);
//		client.executeMethod(request);
//		result = request.getResponseBodyAsString();
//		return result;
//	}
//
//	/**
//	 * 调用http put方法
//	 *
//	 * @param url
//	 * @param body
//	 * @param contentType
//	 * @param headerMap
//	 * @return
//	 * @throws IOException
//	 */
//	public static String invokePutMethodByHttp(String url, String body,
//			String contentType, HashMap<String, String> headerMap) throws IOException{
//		String result = "";
//		PutMethod request = new PutMethod(url);
//		HttpClient client = new HttpClient();
//		request.setRequestHeader("Content-Type", contentType);
//		request.setRequestHeader("Charset", "utf-8");
//		for (Entry<String, String> entry : headerMap.entrySet()) {
//			request.setRequestHeader(entry.getKey(), entry.getValue());
//		}
//		request.setRequestBody(body);
//		//client.getHostConfiguration().setProxy("127.0.0.1", 8888);//设置代理，使finddler工具能抓取 httpclient发送的请求
//		client.executeMethod(request);
//		result = request.getResponseBodyAsString();
//		return result;
//	}
//
//	/**
//	 * 中转文件
//	 *
//	 * @param file
//	 *            上传的文件
//	 * @return 响应结果
//	 * @throws IOException
//	 */
//	public static int httpClientUploadFile(InputStream inputStream, String targetURL, HashMap<String, String> headerMap) throws IOException {
//		String result;
//		PutMethod request = new PutMethod(targetURL);
//		for (Entry<String, String> entry : headerMap.entrySet()) {
//			request.setRequestHeader(entry.getKey(), entry.getValue());
//		}
//		int status=0;
//		try {
//			request.setRequestEntity(new InputStreamRequestEntity(inputStream));
//			String contentLength=String.valueOf(request.getRequestEntity().getContentLength());
//			request.setRequestHeader("content-length",contentLength );//计算文件字节
//			HttpClient client = new HttpClient();
//			//client.getHostConfiguration().setProxy("127.0.0.1", 8888);//设置代理，使finddler工具能抓取 httpclient发送的请求
//			client.getHttpConnectionManager().getParams()
//					.setConnectionTimeout(5000);
//			status = client.executeMethod(request);
//			if (status == HttpStatus.SC_OK) {
//				logger.debug("上传成功");
//				// 上传成功
//			} else {
//				// 上传失败
//				logger.error("上传失败:"+status);
//			}
//
//		} catch (Exception ex) {
//			logger.error("上传失败:"+ex.getMessage(),ex);
//		} finally {
//			request.releaseConnection();
//		}
//		return status;
//	}
//
//	/**
//	 * 调用http get方法
//	 *
//	 * @param url
//	 * @return
//	 */
//	public static InputStream invokeGetMethodByInputStream(String url, String contentType) throws Exception {
//		GetMethod request = new GetMethod(url);
//		request.setRequestHeader("Charset", "utf-8");
//		if(contentType!=null){
//			request.setRequestHeader("Content-Type", contentType);
//		}
//		HttpClient client = new HttpClient();
//		client.executeMethod(request);
//		InputStream in = request.getResponseBodyAsStream();
//		return in;
//	}
//
//	public static InputStream invokeGetMethodByInputStream(String url, Map<String, String> headerMap)
//			throws Exception {
//		logger.debug("调用GET方法读取流-开始，url="+url+"，header="+JSON.toJSONString(headerMap));
//		GetMethod request = new GetMethod(url);
//		for (Entry<String, String> entry : headerMap.entrySet()) {
//			request.setRequestHeader(entry.getKey(), entry.getValue());
//		}
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(300000);//连接时间5分钟
//		client.getHttpConnectionManager().getParams().setSoTimeout(300000);//数据传输时间5分钟
//		client.executeMethod(request);
//        int state = request.getStatusLine().getStatusCode();
//        logger.debug("调用GET方法读取流-请求返回状态值为"+state);
//        if (state == HttpStatus.SC_OK) {
//        	return request.getResponseBodyAsStream();
//        }
//        throw new ApplicationException("调用GET方法读取流-失败");
//	}
//
//	public static String invokeGetMethodByHttp(String url, Map<String, String> paramMap ) throws Exception{
//		return invokeGetMethodByHttp(url,null,paramMap);
//	}
//
//	public static String invokeGetMethodByHttp(String url, Map<String, String> headerMap, Map<String, String> paramMap ) throws Exception{
//		logger.debug("invokeGetMethodByHttp begin. url:{} headerMap:{} paramMap:{}", url, headerMap, paramMap);
//		GetMethod getMethod = new GetMethod(url);
//		if(headerMap != null){
//			for (Entry<String, String> entry : headerMap.entrySet()) {
//				getMethod.setRequestHeader(entry.getKey(), entry.getValue());
//			}
//		}
//		if(paramMap != null){
//			NameValuePair[] nameValuePairArr = new NameValuePair[paramMap.size()];
//			int count = 0;
//			for (Entry<String, String> entry : paramMap.entrySet()) {
//				nameValuePairArr [count++] = new NameValuePair(entry.getKey(), entry.getValue());
//		    }
//			getMethod.setQueryString(nameValuePairArr);
//		}
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(300000);//连接超时
//		client.getHttpConnectionManager().getParams().setSoTimeout(300000);//读取超时
//		long startTime = System.currentTimeMillis();
//		try{
//			int statusCode = client.executeMethod(getMethod);
//			String responseBody = getMethod.getResponseBodyAsString();
//			long endTime = System.currentTimeMillis();
//			if(responseBody != null && responseBody.length() <= 200){
//				logger.debug("invokeGetMethodByHttp end. 耗时:{} ms statusCode:{} responseBody:{}",
//						(endTime-startTime), statusCode, responseBody);
//			}else{
//				logger.debug("invokeGetMethodByHttp end. 耗时:{} ms statusCode:{}", (endTime-startTime), statusCode);
//			}
//			if (statusCode != HttpStatus.SC_OK) {
//				throw new ApplicationException("invokeGetMethodByHttp 请求目标URL返回错误.statusCode:"+ statusCode);
//	        }
//			return responseBody;
//		}catch(Exception e){
//			logger.error("invokeGetMethodByHttp exception.", e);
//			throw e;
//		}finally{
//			getMethod.releaseConnection();
//		}
//	}
//
//	public static String invokePostMethodByHttp(String url, Map<String, String> paramMap, String requestBody) throws Exception{
//		return invokePostMethodByHttp(url,null,paramMap,requestBody);
//	}
//
//	public static String invokePostMethodByHttp(String url, Map<String, String> headerMap, Map<String, String> paramMap, String requestBody) throws Exception{
//		logger.debug("invokePostMethodByHttp begin. url:{} headerMap:{} paramMap:{} requestBody:{}", url, headerMap, paramMap, requestBody);
//		PostMethod postMethod = new PostMethod(url);
//		if(headerMap != null){
//			for (Entry<String, String> entry : headerMap.entrySet()) {
//				postMethod.setRequestHeader(entry.getKey(), entry.getValue());
//			}
//		}
//		if(paramMap != null){
//			NameValuePair[] nameValuePairArr = new NameValuePair[paramMap.size()];
//			int count = 0;
//			for (Entry<String, String> entry : paramMap.entrySet()) {
//				nameValuePairArr [count++] = new NameValuePair(entry.getKey(), entry.getValue());
//		    }
//			postMethod.setQueryString(nameValuePairArr);
//		}
//		if(requestBody != null){
//			RequestEntity requestEntity = new StringRequestEntity(requestBody, MediaType.APPLICATION_JSON_VALUE, "UTF-8");
//			postMethod.setRequestEntity(requestEntity );
//		}
//
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(300000);//连接超时
//		client.getHttpConnectionManager().getParams().setSoTimeout(300000);//读取超时
//		long startTime = System.currentTimeMillis();
//		try{
//			int statusCode = client.executeMethod(postMethod);
//			String responseBody = postMethod.getResponseBodyAsString();
//			long endTime = System.currentTimeMillis();
//			if(responseBody != null && responseBody.length() <= 200){
//				logger.debug("invokePostMethodByHttp end. 耗时:{} ms statusCode:{} responseBody:{}",
//						(endTime-startTime), statusCode, responseBody);
//			}else{
//				logger.debug("invokePostMethodByHttp end. 耗时:{} ms statusCode:{}", (endTime-startTime), statusCode);
//			}
//			if (statusCode != HttpStatus.SC_OK) {
//				throw new ApplicationException("invokePostMethodByHttp 请求目标URL返回错误.statusCode:"+ statusCode);
//	        }
//			return responseBody;
//		}catch(Exception e){
//			logger.error("invokePostMethodByHttp exception.", e);
//			throw e;
//		}finally{
//			postMethod.releaseConnection();
//		}
//	}
//
//}
