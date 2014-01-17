/*
 * Copyright (c) Jian Wang.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.uaa.security.core;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uaa.admin.pojo.Apilog;
import org.uaa.security.SecurityService;

/**
 * @author wangjian
 * @create 2014年1月10日 下午5:11:10
 *
 */
@Component
public class LoggerManager {
	
	@Autowired
	private SecurityService securityService;
	
	private Apilog log = null;
	private String res_uri = null;
	private Timestamp request_time = null;
	private Timestamp response_time = null;
	
	public void logBefore(HttpServletRequest request) {
		res_uri = request.getPathInfo();
		if (res_uri.startsWith("/logs")) return;
		
		String res_action = request.getMethod();
		request_time = new Timestamp(System.currentTimeMillis());
		String addr = request.getRemoteAddr();
		String client = request.getHeader("User-Agent");
		
		log = new Apilog();
		log.setAddr(addr);
		log.setClient(client);
		log.setRequest_time(request_time.getTime());
		log.setRes_action(res_action);
		log.setRes_uri(res_uri);
		
		securityService.insertLog(log);
	}
	
	public void logAfter(HttpServletRequest request) {
		if (res_uri.startsWith("/logs")) return;
		
		response_time = new Timestamp(System.currentTimeMillis());
		log.setResponse_time(response_time.getTime());
		log.setTime(response_time.getTime()-request_time.getTime());
		
		if (SecurityContextHolder.getContext().getAuthenticationToken() != null) {
			Integer uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
			log.setUid(uid);
		}
		
		securityService.updateLog(log);
	}
}
