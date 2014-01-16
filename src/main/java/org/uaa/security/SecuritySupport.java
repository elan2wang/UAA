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
package org.uaa.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uaa.common.AppContext;
import org.uaa.security.core.SecurityMetadataSource;


/**
 * @author wangjian
 * @create 2014年1月16日 下午3:12:30
 *
 */
public class SecuritySupport {
	private static Logger log = LoggerFactory.getLogger(SecuritySupport.class);

	public static boolean isAllowed(String action, String uri, List<Integer> roles) {

		SecurityMetadataSource securityMetadataSource = 
				(SecurityMetadataSource) AppContext.getBean("securityMetadataSource");

		List<Integer> neededRoles = securityMetadataSource.getNeededRoles(uri, action);
		if (neededRoles == null || neededRoles.size() == 0) {
			log.debug(action+"@"+uri+", haven't set needed roles");
			return true;
		}
		else {
			for (Integer role : roles) {
				if (neededRoles.contains(role)) return true;
			}
		}
		return false;
	}


	public static void reloadSecurityMetadataSource() {
		SecurityMetadataSource securityMetadataSource = 
				(SecurityMetadataSource) AppContext.getBean("securityMetadataSource");
		securityMetadataSource.reloadDefinedResource();
	}
}
