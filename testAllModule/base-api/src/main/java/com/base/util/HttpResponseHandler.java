package com.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpResponseHandler implements ResponseHandler<JSONObject> {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpResponseHandler.class.getName());
	
	private final static Logger msgLogger = LoggerFactory.getLogger("MSG." + HttpResponseHandler.class.getName());
	
	//处理httpResponse
	@Override
	public JSONObject handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		
		HttpEntity entity = response.getEntity();
		if (entity == null) {  
            throw new ClientProtocolException("http response contains no content.");  
        } else {
        	//把原Http实体的内容缓冲到了内存中,以便可以重复读取内容
        	entity = new BufferedHttpEntity(entity);
            String result = EntityUtils.toString(entity);
			logger.debug("Handler收到的同步返回结果: " + result);
        }
		
		//处理异常结果
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new HttpResponseException(  
            		response.getStatusLine().getStatusCode(),  
            		response.getStatusLine().getReasonPhrase());  
        }
		
		//同步返回结果正常(HttpStatus.SC_OK==200)
        String strResult = EntityUtils.toString(entity);
		JSONObject jsonObject = JSON.parseObject(strResult);
		msgLogger.info("Response: " + jsonObject.toString()); //记录返回消息
		
		return jsonObject;
	}

}
