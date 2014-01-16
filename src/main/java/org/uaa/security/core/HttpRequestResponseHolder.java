/**
 * 
 * Copyright 2013.  All rights reserved. 
 * 
 */
package org.uaa.security.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangjian
 * @create 2013年8月3日 下午10:18:59
 * @update TODO
 * 
 * 
 */
public class HttpRequestResponseHolder {

	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public HttpRequestResponseHolder(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

	public HttpServletRequest getRequest() {
		return request;
	}
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
		
}
