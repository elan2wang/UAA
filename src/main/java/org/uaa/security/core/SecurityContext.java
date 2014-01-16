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

import java.io.Serializable;

/**
 * @author wangjian
 * @create 2014年1月8日 下午2:23:03
 *
 */
public class SecurityContext implements Serializable{

	private static final long serialVersionUID = 769502677595645574L;
	
	private UsernamePasswordToken authenticationToken;
	
	public boolean equals(Object obj) {
        if (obj instanceof SecurityContext) {
        	SecurityContext test = (SecurityContext) obj;

            if ((this.getAuthenticationToken() == null) && (test.getAuthenticationToken() == null)) {
                return true;
            }

            if ((this.getAuthenticationToken() != null) && (test.getAuthenticationToken() != null)
                && this.getAuthenticationToken().equals(test.getAuthenticationToken())) {
                return true;
            }
        }

        return false;
    }

    public UsernamePasswordToken getAuthenticationToken() {
    	return authenticationToken;
    }

    public void setAuthenticationToken(UsernamePasswordToken authenticationToken) {
    	this.authenticationToken = authenticationToken;
    }

}
