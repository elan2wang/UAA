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
 * @create 2013年7月31日 上午11:04:41
 * @update TODO
 * 
 * 
 */
public class ResponseWithData implements Response{

	private String apiVersion = ConfigUtil.getValue("apiVersion");
	private Map<String, Object> attrs;
	
	public ResponseWithData(Map<String, Object> attrs) {
		this.attrs = attrs;
	}
	
	public String toJson() {
		String res = null;
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		attributes.put("apiVersion", apiVersion);
		attributes.put("data", attrs);
		try {
			res = JsonFactory.toJson(attributes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	// ~~~~ Getters and Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getApiVersion() {
		return apiVersion;
	}
	
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	public Map<String, Object> getAttrs() {
		return attrs;
	}
	
	public void setAttrs(Map<String, Object> attrs) {
		this.attrs = attrs;
	}

}
