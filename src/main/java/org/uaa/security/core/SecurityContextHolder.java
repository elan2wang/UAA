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

/**
 * @author wangjian
 * @create 2014年1月8日 下午2:45:22
 *
 */
public class SecurityContextHolder{

	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();
	
	public static void addSecurityContext(SecurityContext securityContext) {
		contextHolder.set(securityContext);
	}

	public static void removeSecurityContext() {
		contextHolder.remove();
	}

	public static SecurityContext getContext() {
		SecurityContext ctx = contextHolder.get();

		if(ctx == null) {
			ctx = new SecurityContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}
}
