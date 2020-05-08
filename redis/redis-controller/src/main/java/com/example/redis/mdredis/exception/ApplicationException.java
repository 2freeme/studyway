package com.example.redis.mdredis.exception;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * 
 * 应用异常
 * 
 */
public class ApplicationException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 7968767874288417432L;
    
    private ErrorCode errorCode;
    
    private String errorCodeStr;
    
    private JSONObject data;
    
    public ApplicationException() {
        super();
    }
    
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
        this.errorCodeStr = errorCode.getCode();
    }
    
    /**
     * 注意 此时 ApplicationException的message有2个，第一个参数rootMessage和第二参数errorCode.message
     * 如果需要获取不同的message
     * 请参考getMessage() 与 getRootMessage()的区别
     * 
     * @param message
     * @param errorCode
     */
    public ApplicationException(String rootMessage, ErrorCode errorCode) {
        super(rootMessage);
        this.errorCode = errorCode;
        this.errorCodeStr = errorCode.getCode();
    }
    
    public ApplicationException(String rootMessage, ErrorCode errorCode, JSONObject data) {
        super(rootMessage);
        this.errorCode = errorCode;
        this.errorCodeStr = errorCode.getCode();
        this.data=data;
    }
    
    public ApplicationException(String message, String errorCodeStr) {
        super(message);
        this.errorCodeStr = errorCodeStr;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ApplicationException(ErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.errorCodeStr = errorCode.getCode();
    }
    
    
    
    public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	/**
     * 获取errorCode String
     * @return String
     */
    public String getCode() {
        return this.errorCodeStr;
    }
    
    /**
     * 获取 error Code
     * @return ErrorCode
     */
    public ErrorCode getErrorCode() {
    	return errorCode;
    }
    
    /**
     * 默认返回errorCode的message
     * 没有则返回applicationException的message
     */
    @Override
    public String getMessage() {
        if (errorCode != null) {
            return errorCode.getMessage();
        } else {
            return super.getMessage();
        }
    }
    
    /**
     * 默认返回applicationException的message
     * 没有则返回errorCode的message
     */
    public String getRootMessage() {
    	if (super.getMessage() != null) {
            return super.getMessage();
        } else if (errorCode != null) {
            return errorCode.getMessage();
        } else {
        	return null;
        }
    }
}
