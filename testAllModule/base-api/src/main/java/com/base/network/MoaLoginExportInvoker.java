//package com.base.network;
//
//import com.alibaba.fastjson.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.net.URL;
//import java.net.URLEncoder;
//
//@Configuration
//public class MoaLoginExportInvoker {
//
//	private static final Logger logger = LoggerFactory.getLogger(MoaLoginExportInvoker.class.getName());
//
//	@Value("${moa.loginExport.url}")
//	private String loginExporturl;
//
//	@Value("${moa.loginExport.appKey}")
//	private String appKey;
//
//	@Value("${moa.loginExport.token}")
//	private String token;
//
//	public JSONObject getLoginDate(String startDate ,String endDate,int pageNum,int pageSize) {
//		String urlStr = loginExporturl;
//		String result = null;
//		try {
//			urlStr += "?appKey=" + appKey;
//			urlStr += "&token=" + token;
//			urlStr += "&startDate=" + URLEncoder.encode(startDate,"utf-8");
//			urlStr += "&endDate=" + URLEncoder.encode (endDate,"utf-8");
//			urlStr += "&pageNum=" + pageNum;
//			urlStr += "&pageSize=" + pageSize;
//
//			logger.debug("app登录日志url:"+urlStr);
//			URL url = new URL(urlStr);
//			if("https".equalsIgnoreCase(url.getProtocol())) {
//				result = HTTPSClientInvoker.invokeGetMethodByHttps(urlStr);
//			} else if("http".equalsIgnoreCase(url.getProtocol())){
//				result = HTTPClientInvoker.invokeGetMethodByHttp(urlStr,60000,300000);
//			} else {
//				throw new ApplicationException("配置文件错误，moa接口URL协议必须是https或http");
//			}
//		} catch (Exception e) {
//			logger.error("调用moa失败", e);
//			throw new ApplicationException("调用moa失败");
//		}
//		logger.debug("调用moa返回值：" + result);
//		if (result != null) {
//			JSONObject resultJSON = JSONObject.parseObject(result);
//			if (MoaResultCode.SUCCESS.getValue().equals(resultJSON.getString("code"))) {
//				return resultJSON.getJSONObject("data");
//			} else {
//				throw new ApplicationException("调用moa接口失败："
//						+resultJSON.getString("code")+","+resultJSON.getString("msg"));
//			}
//		}
//		return null;
//	}
//
//}
//
