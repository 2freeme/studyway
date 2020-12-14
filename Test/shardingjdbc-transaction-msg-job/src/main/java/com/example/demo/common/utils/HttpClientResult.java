package com.example.demo.common.utils;

import java.io.Serializable;

/**
 * Description: 封装httpClient响应结果
 * 
 * @author JourWon
 * @date Created on 2018年4月19日
 */
public class HttpClientResult implements Serializable {

	/**
	 * 响应状态码
	 */
	private int code;

	public HttpClientResult(String content) {
		super();
		this.content = content;
	}

	public HttpClientResult(int code) {
		super();
		this.code = code;
	}

	public HttpClientResult(int code, String content) {
		super();
		this.code = code;
		this.content = content;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 响应数据
	 */
	private String content;

}
