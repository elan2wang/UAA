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

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.uaa.common.ConfigUtil;
import org.uaa.common.http.ResponseWithStatus;

/**
 * @author wangjian
 * @create 2014年1月10日 下午5:12:15
 *
 */
@Component
public class LogoutHandler {
	private static Logger log = LoggerFactory.getLogger(LogoutHandler.class);
	
	private String logout_url = "/1/logout";

	public LogoutHandler() { }

	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();

		// clear Cookies
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			log.debug(cookie.getName());
			
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		// set response message
		ResponseWithStatus res = new ResponseWithStatus(uri, "0", ConfigUtil.getValue("00000"));
		response.setContentType("application/json;charset=UTF-8");
		response.getOutputStream().write(res.toJson().getBytes());
		return;
	}

	public boolean isLogout(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything from the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		int queryParamIndex = uri.indexOf('?');

		if (queryParamIndex > 0) {
			// strip everything from the first question mark
			uri = uri.substring(0, queryParamIndex);
		}

		if ("".equals(request.getContextPath())) {
			return uri.endsWith(logout_url);
		}

		return uri.endsWith(request.getContextPath()+logout_url);
	}

	// ~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getLogout_url() {
		return logout_url;
	}

	public void setLogout_url(String logout_url) {
		this.logout_url = logout_url;
	}
}
