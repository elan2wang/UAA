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
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjian
 * @create 2014年1月8日 下午2:19:15
 *
 */
public class UsernamePasswordToken implements Serializable{

	private static final long serialVersionUID = -8321835982476506127L;

	private final Integer uid;
	private final String username;
	private final String password;
	private final List<Integer> roles;
	
	private boolean isAuthenticated;
	
	public UsernamePasswordToken(String username, String password) {
		this.uid = null;
		this.username = username;
		this.password = password;
		this.roles = new ArrayList<Integer>();
		isAuthenticated = false;
	}
	
	public UsernamePasswordToken(Integer uid, String username, String password, 
			List<Integer> roles) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.roles = roles;
		isAuthenticated = true;
	}

	public boolean equals(UsernamePasswordToken token) {
		if (uid != token.getUid() || !username.equals(token.getUsername()) ||
			password.equals(token.getPassword()) || isAuthenticated != token.isAuthenticated()) {
			return false;
		}
		if (roles.size() != token.getRoles().size()) {
			return false;
		} else {
			for (Integer role : roles) {
				if (!token.getRoles().contains(role)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Integer getUid() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public List<Integer> getRoles() {
		return roles;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
}
