/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.common.http;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.uaa.common.ConfigUtil;
import org.uaa.common.json.JsonFactory;

/**
 * @author wangjian
 * @create 2013年8月15日 下午2:08:36
 * @update TODO
 * 
 * 
 */
public class ResponseWithStatus implements Response{

	private String apiVersion = ConfigUtil.getValue("apiVersion");
	
	private String request;
	private String result_msg;
	private String result_code;
	
	
	public ResponseWithStatus() {
		super();
	}
	
	public ResponseWithStatus(String request, String result_code, String result_msg) {
		super();
		this.request = request;
		this.result_code = result_code;
		this.result_msg = result_msg;
	}
	
	public String toJson() {
		String res = null;
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("apiVersion", apiVersion);
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		data.put("request", request);
		data.put("result_code", result_code);
		data.put("result_msg", result_msg);
		attributes.put("data", data);
		try {
			res = JsonFactory.toJson(attributes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	// ~~~~ Getters and Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getResult_msg() {
		return result_msg;
	}

	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

}
