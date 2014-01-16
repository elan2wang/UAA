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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.uaa.security.SecuritySupport;
import org.uaa.security.exception.AccessDeniedException;

/**
 * @author wangjian
 * @create 2014年1月10日 下午5:10:26
 *
 */
@Component
public class AuthorizationManager {
	private static Logger log = LoggerFactory.getLogger(AuthorizationManager.class);
	
	public void decide(UsernamePasswordToken token, HttpServletRequest request) throws AccessDeniedException {
		String action = request.getMethod().toUpperCase();
		String uri = request.getRequestURI();
		String context = request.getContextPath();
		log.debug(uri.replace(context, ""));
		
		List<Integer> roles = token.getRoles();
		
		if (!SecuritySupport.isAllowed(action, uri.replace(context, ""), roles)) {
			log.info("you don't have needed authorities to access this resource");
			throw new AccessDeniedException("you don't have needed authorities to access this resource");
		}
	}
}
