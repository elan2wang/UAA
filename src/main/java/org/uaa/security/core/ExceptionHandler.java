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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.uaa.common.ConfigUtil;
import org.uaa.common.http.ResponseWithStatus;
import org.uaa.security.exception.AccessDeniedException;
import org.uaa.security.exception.BadCredentialsException;
import org.uaa.security.exception.BadPrincipalsException;
import org.uaa.security.exception.NullAuthenticationException;

/**
 * @author wangjian
 * @create 2014年1月10日 下午9:52:00
 *
 */
@Component
public class ExceptionHandler {

	public void handle(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		ResponseWithStatus res = null;
		
		if (e instanceof BadPrincipalsException) {
			res = new ResponseWithStatus(uri, "10004", ConfigUtil.getValue("10004"));
		}
		if (e instanceof BadCredentialsException) {
			res = new ResponseWithStatus(uri, "10005", ConfigUtil.getValue("10005"));
		}
		if (e instanceof NullAuthenticationException) {
			res = new ResponseWithStatus(uri, "10006", ConfigUtil.getValue("10006"));
		}
		if (e instanceof AccessDeniedException) {
			res = new ResponseWithStatus(uri, "10007", ConfigUtil.getValue("10007"));
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getOutputStream().write(res.toJson().getBytes());
		return;
	}
}
