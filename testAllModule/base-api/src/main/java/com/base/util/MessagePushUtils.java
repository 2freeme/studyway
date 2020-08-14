//package com.base.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.midea.ccs.core.entity.ImgText;
//import com.midea.ccs.core.entity.ServiceNumber;
//import com.midea.ccs.core.exception.ApplicationException;
//import com.midea.ccs.core.network.HTTPClientInvoker;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.lang.builder.ToStringBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.*;
//import java.util.Map.Entry;
//
///**
//* 用于向mpm服务号推送消息
//* @author ex_denggj
//* @Time 2017-6-24
//*
//*/
//@Configuration
//public class MessagePushUtils {
//	//每次最大推送用户数量
//	public final static int EACH_MAX_SEND_COUNT=250;
//
//	@Value("${ccs.mpm.apidomain}")
//	private String apidomain;
//
//	@Value("${ccs.mpm.appKey}")
//    private String appKey;
//
//	@Value("${ccs.mpm.mcWidgetIdentifier}")
//    private String mcWidgetIdentifier;
//
//	@Value("${muc.sourceId}")
//	private String empSource;
//
//	private Map<Integer,ServiceNumber> serviceNumberMap;
//
//
//	public String getMcWidgetIdentifier() {
//		return mcWidgetIdentifier;
//	}
//	public Map<Integer, ServiceNumber> getServiceNumberMap() {
//		return serviceNumberMap;
//	}
//	public void setServiceNumberMap(Map<Integer, ServiceNumber> serviceNumberMap) {
//		this.serviceNumberMap = serviceNumberMap;
//	}
//	private static Logger logger = LoggerFactory.getLogger(MessagePushUtils.class.getName());
//
//
//    /*
//     //将对象里面的字符串NULL转为空字符串""
//     //String requestBody = JSON.toJSONString(imgText,filter);
//     private ValueFilter filter = new ValueFilter() {
//        @Override
//        public Object process(Object obj, String s, Object v) {
//        if(v==null)
//            return "";
//        return v;
//        }
//    };*/
//	/**
//     *  获取分组ID
//     *  @param tagName 分组标签名
//     *  @param userList 用户登录名List
//     *  @param serviceNumber 服务号配置信息
//     *  返回-1获取失败，大于0正常
//     */
//    public  int getTagByTagName(String tagName,List<String> userList,ServiceNumber serviceNumber ){
//    	String url=apidomain+"/api/bus/get_tag_by_tagName?sid={sid}&appKey={appKey}&tagName={tagName}&accessToken={accessToken}";
//    	url=url.replace("{sid}", String.valueOf(serviceNumber.getSid()));
//    	url=url.replace("{accessToken}", serviceNumber.getAccessToken());
//    	url=url.replace("{appKey}", appKey);
//    	try {
//			url=url.replace("{tagName}", URLEncoder.encode(tagName, "UTF-8"));
//		} catch (UnsupportedEncodingException e1) {
//			logger.error("获取粉丝分组ID失败:"+e1.getMessage(),e1);
//			return -1;
//		}
//    	StringBuffer sb=new StringBuffer("");
//    	for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
//			String loginId = (String) iterator.next();
//			sb.append(loginId);
//			if(iterator.hasNext()){
//				sb.append(",");
//			}
//		}
//    	//url=url.replace("{userIds}", sb.toString());
//    	int  tagId=-1;
//    	String result_str;
//    	try {
//			//result_str = HTTPClientInvoker.invokeGetMethodByHttp(requestUrl);
//    		NameValuePair[] param = { new NameValuePair("userIds",sb.toString())} ;
//			result_str = HTTPClientInvoker.invokePostMethodByHttp(url,param);
//		} catch (Exception e) {
//			logger.error("获取粉丝分组ID失败:"+e.getMessage());
//			return -1;
//		}
//
//    	JSONObject result_json= JSON.parseObject(result_str);
// 	    if("0".equals(result_json.getString("errcode"))){//获取成功
// 	    	tagId=result_json.getIntValue("tagId");
// 	    }else{
// 	    	logger.error("获取粉丝分组ID失败:"+result_json.getString("errmsg"));
// 	    }
//    	return tagId;
//    }
//    /**
//     * 根据分组ID发送图文消息
//     * @param mideaId 图文消息ID
//     * @param groupIds 要推送的分组
//     * @param serviceNumber 服务号配置信息
//     */
//
//    public JSONObject imgTextPushMsgByGroupIds(int mideaId,String groupIds,ServiceNumber serviceNumber){
//    	JSONObject filterObj=new JSONObject();
//    	filterObj.put("group_id", groupIds);
//    	return imgTextPushMsg(mideaId,filterObj,serviceNumber);
//    }
//    /**
//     * 根据用户ID发送图文消息
//     * @param mideaId 图文消息ID
//     * @param userids 要推送的用户IDS集合
//     * @param serviceNumber 服务号配置信息
//     */
//
//    public JSONObject imgTextPushMsgByUserIds(int mideaId,String userids,ServiceNumber serviceNumber){
//    	JSONObject filterObj=new JSONObject();
//    	filterObj.put("user_id", userids);
//    	return imgTextPushMsg(mideaId,filterObj,serviceNumber);
//    }
//    public JSONObject imgTextPushMsgByUserIdArray(int mideaId,String[] userids,ServiceNumber serviceNumber){
//    	JSONObject filterObj=new JSONObject();
//    	filterObj.put("user_id", userids);
//    	return imgTextPushMsg(mideaId,filterObj,serviceNumber);
//    }
//    public JSONObject imgTextPushMsg(int mideaId,JSONObject filterObj,ServiceNumber serviceNumber){
//    	String url=apidomain+"/api/bus/imgTextPushMsg?accessToken="+serviceNumber.getAccessToken()
//    			+"&empSource="+empSource;
//    	String result_str;
//    	JSONObject result_json=new JSONObject();
//    	JSONObject requestBody=new JSONObject();
//    	JSONObject newsObj=new JSONObject();
//    	newsObj.put("media_id", mideaId);
//    	requestBody.put("appKey",appKey);
//    	requestBody.put("filter",filterObj);
//    	requestBody.put("sid",serviceNumber.getSid());
//    	requestBody.put("news",newsObj);
//    	try {
//    		logger.debug("调用底座消息发送接口url="+url+",requestBody="+requestBody.toJSONString());
//			result_str = HTTPClientInvoker.invokePostMethodByHttp(url, requestBody.toJSONString(), "application/json");
//			logger.debug("调用MPM消息推送接口返回的结果"+result_str);
//		} catch (Exception e) {
//			logger.error("发送图文消息失败:"+e.getMessage(),e);
//			result_json.put("ccs_except", 1);
//			result_json.put("ccs_except_msg", e.getMessage());
//			return result_json;
//		}
//
//    	result_json= JSON.parseObject(result_str);
// 	    if(!"0".equals(result_json.getString("errcode"))){//获取成功
// 	    	result_json.put("ccs_except", 1);
// 	    	logger.error("发送图文消息失败:url="+url+",requestBody="+requestBody.toJSONString()+"errmsg="+result_json.getString("errmsg"));
// 	    }else{
// 	    	result_json.put("ccs_except", 0);
// 	    }
//    	return result_json;
//    }
//    /**
//     * 单图文上传
//     * @param imgText 图文消息实体
//     * @param serviceNumber 服务号配置信息
//     */
//
//    public int uploadImgText(ImgText imgText,ServiceNumber serviceNumber){
//    	//imgText.setH5_identifier(mcWidgetIdentifier);
//    	int mideaId=-1;
//    	try
//    	{
//    		String url=apidomain+"/api/bus/uploadImgText?accessToken="+serviceNumber.getAccessToken().trim();
//        	logger.debug("上传图文消息的访问地址是："+url);
//        	String result_str;
//        	JSONObject result_json=new JSONObject();
//        	imgText.createNotifytitle();
//        	String requestBody = JSON.toJSONString(imgText);
//        	logger.debug("请求上传图文消息的JSON字符串是:"+requestBody);
//
//        	try {
//    			result_str = HTTPClientInvoker.invokePostMethodByHttp(url, requestBody, "application/json");
//    		} catch (Exception e) {
//    			logger.error("上传图文消息失败:"+e.getMessage(),e);
//    			return -1;
//    		}
//        	try {
//        		result_json= JSON.parseObject(result_str);
//    		} catch (Exception e) {
//    			logger.error("上传图文消息失败:JSON转换失败:"+result_str,e);
//    			return -1;
//    		}
//
//        	if("0".equals(result_json.getString("errcode"))){
//        		mideaId=result_json.getIntValue("msg_data_id");
//        	}else{
//        		logger.error("上传图文消息失败:"+result_json.getString("errmsg"));
//    			return -1;
//        	}
//    	} catch (Exception e) {
//    		logger.error("上传图文消息失败:"+ToStringBuilder.reflectionToString(imgText));
//			return -1;
//    	}
//    	return mideaId;
//    }
//
//    /**
//     * 发送模板消息
//     * @param params url 参数
//     * @param requestBody 请求体参数
//     * @param serviceNumber 服务号配置信息
//     * 如果要推送的用户太多，使用batchToUsers
//     */
//
//    public JSONObject createTempMsgAndPush(HashMap<String,String> params,JSONObject requestBody,ServiceNumber serviceNumber) throws IOException{
//    	//四种方式推送
//    	//touser,toDeptNum，groupIds url参数          batchToUsers 请求体参数
//    	String url=apidomain+"/api/tempmsg/create_temp_msg_and_push?appKey={appKey}&accessToken={accessToken}";
//    	url=url.replace("{accessToken}", serviceNumber.getAccessToken());
//    	url=url.replace("{appKey}", appKey);
//    	for (Entry<String, String> entry : params.entrySet()) {
//    		url+="&"+entry.getKey()+"="+ entry.getValue();
// 	    }
//
//    	String result_str;
//    	//requestBody.put("mc_widget_identifier", mcWidgetIdentifier);
//    	JSONObject result_json=new JSONObject();
//    	try {
//    		logger.debug("调用底座消息发送接口url="+url+",requestBody="+requestBody.toJSONString());
//			result_str = HTTPClientInvoker.invokePostMethodByHttp(url, requestBody.toJSONString(), "application/json");
//			logger.debug("推送模板消息:createTempMsgAndPush:返回:"+result_str);
//		} catch (Exception e) {
//			logger.error("推送模板消息失败:"+e.getMessage(),e);
//			result_json.put("errcode", 2);
//			result_json.put("errmsg", "请求推送模板消息失败！");
//			return result_json;
//		}
//    	result_json= JSON.parseObject(result_str);
//    	return result_json;
//    }
//
//    /**
//     *  利用获取分组ID的接口  检测用户是否在MPM  将结果打到DEBUG日志中
//     *  @param tagName 分组标签名
//     *  @param userList 用户登录名List
//     *  @param serviceNumber 服务号配置信息
//     */
//    public  List<String> checkMPMUserIsExists(String tagName,List<String> userList,ServiceNumber serviceNumber ){
//    	logger.debug("MPM用户检测：CCS中已上线APP的用户数量为"+userList.size()+"个。");
//    	String url=apidomain+"/api/bus/get_tag_by_tagName?sid={sid}&appKey={appKey}&tagName={tagName}&accessToken={accessToken}";
//    	url=url.replace("{sid}", String.valueOf(serviceNumber.getSid()));
//    	url=url.replace("{accessToken}", serviceNumber.getAccessToken());
//    	url=url.replace("{appKey}", appKey);
//    	try {
//			url=url.replace("{tagName}", URLEncoder.encode(tagName, "UTF-8"));
//		} catch (UnsupportedEncodingException e1) {
//			logger.debug("MPM用户检测：分组名编码失败！"+e1.getMessage());
//			throw new ApplicationException("MPM用户检测：分组名编码失败！");
//		}
//    	int  tagId=-1;
//
//    	String unInDepartmentUser="";
//    	String requestUrl;
//
//    	StringBuffer sb=new StringBuffer("");
//    	for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
//			String loginId = (String) iterator.next();
//			sb.append(loginId);
//			if(iterator.hasNext()){
//				sb.append(",");
//			}
//		}
//    	String userIds=sb.toString();
//    	List<String> noInMpmUserList = new ArrayList<String> ();
//    	do {
//    		//requestUrl=url.replace("{userIds}", userIds);
//    		requestUrl=url;
//        	String result_str="";
//        	try {
//    			//result_str = HTTPClientInvoker.invokeGetMethodByHttp(requestUrl);
//        		NameValuePair[] param = { new NameValuePair("userIds",userIds)} ;
//    			result_str = HTTPClientInvoker.invokePostMethodByHttp(requestUrl,param);
//    		} catch (Exception e) {
//    			logger.debug("MPM用户检测：调用分组接口失败！"+e.getMessage());
//    			throw new ApplicationException("MPM用户检测：调用分组接口失败！");
//    		}
//        	JSONObject result_json=null;
//        	try{
//        		result_json= JSON.parseObject(result_str);
//        	}catch (Exception e){
//        		logger.error("MPM用户检测：获取粉丝分组ID失败:"+result_str,e);
//        		throw new ApplicationException("MPM用户检测：获取粉丝分组ID失败:"+result_str,e);
//        	}
//
//     	    if("0".equals(result_json.getString("errcode"))){//获取成功
//     	    	tagId=result_json.getIntValue("tagId");
//     	    }else{
//     	    	String errmsg=result_json.getString("errmsg");
//     	    	logger.error("MPM用户检测：获取粉丝分组ID失败:"+errmsg);
//     	    	if(errmsg.indexOf("不在组织架构中")!=-1){
//     	    		String userid = errmsg.split(" ")[0];
//     	    		unInDepartmentUser+=userid+",";
//     	    		noInMpmUserList.add(userid);
//     	    		userIds=(","+userIds+",").replace(userid+",", "");
//     	    		if(userIds.charAt(0)==','){
//     	    			userIds=userIds.substring(1);
//     	    		}
//     	    		if(userIds.length()>0){
//	     	    		if(userIds.charAt((userIds.length()-1))==','){
//	     	    			userIds=userIds.substring(0,userIds.length()-1);
//	     	    		}
//     	    		}else{
//     	    			break;
//     	    		}
//     	    	}
//     	    }
//		} while (tagId==-1);
//    	logger.debug("MPM用户检测：不在组织架构的用户有"+noInMpmUserList.size()+"个。");
//    	logger.debug("MPM用户检测：不在组织架构的LOGIN_ID集合是==="+unInDepartmentUser);
//    	return noInMpmUserList;
//    }
//
//}
