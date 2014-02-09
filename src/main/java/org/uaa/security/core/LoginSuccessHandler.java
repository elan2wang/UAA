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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.uaa.common.ConfigUtil;
import org.uaa.common.Crypto;
import org.uaa.common.json.JsonFactory;

/**
 * @author wangjian
 * @create 2014年1月16日 下午4:41:01
 *
 */
@Component
public class LoginSuccessHandler {
	private static Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);
	
	public void handle(UsernamePasswordToken token, HttpServletRequest request, HttpServletResponse response) {
		// set cookie
		Map<String, Object> session = new LinkedHashMap<String, Object>();
		session.put("uid", token.getUid());
		session.put("username", token.getUsername());
		session.put("password", token.getPassword());
		session.put("roles", token.getRoles());
		session.put("isAuthenticated", token.isAuthenticated());
		
		try {
			String sessionId = Crypto.encrypt(JsonFactory.toJson(session));
			log.debug(sessionId);
			Cookie cookie = new Cookie(ConfigUtil.getValue("SESSION_ID"), sessionId);
			cookie.setMaxAge(7*24*3600);
			response.addCookie(cookie);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
