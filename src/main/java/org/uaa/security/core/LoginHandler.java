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

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wangjian
 * @create 2014年1月10日 下午5:11:31
 *
 */
@Component
public class LoginHandler {
	private static Logger log = LoggerFactory.getLogger(LoginHandler.class);
	
	private String login_url = "/1/login";
	private String principal_name = "username";
	private String credential_name = "password";
	
	public LoginHandler() { }
	
	public UsernamePasswordToken handle(HttpServletRequest request) {
		String username = request.getParameter(principal_name);
		String password = request.getParameter(credential_name);
		
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setAuthenticated(false);
		
		return token;
	}
	
	public boolean isLogin(HttpServletRequest request) {
		String uri = request.getRequestURI();
		log.debug("context: " + request.getContextPath());
		log.debug("uri: " + request.getRequestURI());
		
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
            return uri.endsWith(login_url);
        }
        
        return uri.endsWith(request.getContextPath()+login_url);
	}
	
	// ~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public String getLogin_url() {
		return login_url;
	}

	public void setLogin_url(String login_url) {
		this.login_url = login_url;
	}

	public String getPrincipal_name() {
		return principal_name;
	}

	public void setPrincipal_name(String principal_name) {
		this.principal_name = principal_name;
	}

	public String getCredential_name() {
		return credential_name;
	}

	public void setCredential_name(String credential_name) {
		this.credential_name = credential_name;
	}
}
