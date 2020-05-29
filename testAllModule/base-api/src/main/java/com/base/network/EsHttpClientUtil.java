//package com.base.network;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.net.ConnectException;
//import java.util.*;
//
//public class EsHttpClientUtil extends HttpClientUtil implements InitializingBean{
//
//	private static final Logger logger = LoggerFactory.getLogger(EsHttpClientUtil.class.getName());
//
//	@Value("${elasticSearch.hostList}")
//	private String hostList;
//
//	private List<String> hosts;
//
//	public String sendPostRequest(String url, String sendData, Map<String,String> header, boolean isProxy,
//    		String proxyHost, int proxyPort){
//        String result = null;
//        int failFlag = 0;
//
//        for (int i = 0 ;i < hosts.size(); i++) {
//        	try {
//            	result = sendPostRequest(url, sendData, header, isProxy, proxyHost, proxyPort, i);
//            	break;
//            } catch(ConnectException e){
//            	 logger.error("http通讯失败:"+e.getMessage(),e);
//            	 failFlag ++;
//            	 if (i==hosts.size()-1) throw new ApplicationException(e.getMessage(), e);
//            } catch(Exception ex) {
//            	logger.error("其他http通讯失败:"+ex.getMessage(),ex);
//            	throw new ApplicationException(ex.getMessage());
//            }
//        	logger.debug("hosts调用:"+hosts.get(i));
//        }
//
//        if(failFlag > 0) {
//        	adjustHosts(failFlag);
//        }
//
//        return result;
//    }
//
//
//	private String sendPostRequest(String url, String sendData, Map<String,String> header, boolean isProxy,
//    		String proxyHost, int proxyPort, int retryCount) throws Exception{
//
//        String fullUrl = "http://" + hosts.get(retryCount) + "/" + url;
//
//        String result = sendAndRcvHttpPostInternal(fullUrl, sendData, header, isProxy, proxyHost, proxyPort);
//
//        return result;
//    }
//
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		if (hostList != null && hostList.length() > 0) {
//			hosts = new ArrayList<String>(Arrays.asList(hostList.split(",")));
//			Collections.shuffle(hosts);
//		}
//	}
//
//	private synchronized void adjustHosts(int failFlag){
//		List<String> adjustList = new ArrayList<String>();
//
//		String successHost = hosts.get(failFlag);
//		adjustList.add(successHost);
//		hosts.remove(failFlag);
//		adjustList.addAll(hosts);
//		hosts = adjustList;
//	}
//}
