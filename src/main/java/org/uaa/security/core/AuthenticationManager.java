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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uaa.admin.pojo.User;
import org.uaa.common.ConfigUtil;
import org.uaa.common.Crypto;
import org.uaa.security.SecurityService;
import org.uaa.security.exception.AuthenticationException;
import org.uaa.security.exception.BadCredentialsException;
import org.uaa.security.exception.BadPrincipalsException;
import org.uaa.security.exception.NullAuthenticationException;


/**
 * @author wangjian
 * @create 2014年1月10日 下午5:09:48
 *
 */
@Component
public class AuthenticationManager {
	private static Logger log = LoggerFactory.getLogger(AuthenticationManager.class);
	
	@Autowired
	private SecurityService securityService;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	public UsernamePasswordToken authenticate(UsernamePasswordToken token, HttpRequestResponseHolder holder) throws AuthenticationException {
		UsernamePasswordToken newToken = null;
		
		if (token == null) {
			log.info("you haven't signed in or your session was timeout, please sign in again!");
			throw new NullAuthenticationException("you haven't signed in or your session was timeout, please sign in again!");
		}
		if (!token.isAuthenticated()) {
			String username = token.getUsername();
			String password = token.getPassword();
			
			User user = securityService.queryUserByUsername(username);
			// if the username is not exist
			if (user == null) {
				log.info("the username ["+username+"] does not exist");
				throw new BadPrincipalsException("the username ["+username+"] does not exist");
			}
			// if the password is not correct
			if (!user.getPassword().equals(Crypto.MD5Encrypt(password))) {
				log.info("password is not correct for the user ["+username+"]");
				throw new BadCredentialsException("password is not correct for the user ["+username+"]");
			}
			Integer uid = user.getUser_id();
			List<Integer> roles = securityService.queryUserRoles(uid);
			newToken = new UsernamePasswordToken(uid, username, password, roles);
			newToken.setAuthenticated(true);
			
			// login successfully
			loginSuccessHandler.handle(newToken, holder.getRequest(), holder.getResponse());
		} else {
			newToken = token;
		}
		
		return newToken;
	}

	public UsernamePasswordToken getToken(HttpServletRequest request) {
		UsernamePasswordToken token = null;

		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(ConfigUtil.getValue("SESSION_ID"))) {
				String content = cookie.getValue();
				String sessionInfo = Crypto.decrypt(content);

				try {
					JSONObject result = new JSONObject(sessionInfo);
					Integer uid = result.getInt("uid");
					String username = result.getString("username");
					String password = result.getString("password");
					JSONArray array = result.getJSONArray("roles");
					List<Integer> roles = new ArrayList<Integer>();
					for (int i=0; i<array.length(); i++) {
						roles.add(array.getInt(i));
					}

					token = new UsernamePasswordToken(uid, username, password, roles);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		return token;
	}

}
