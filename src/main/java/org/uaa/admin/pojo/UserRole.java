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
package org.uaa.admin.pojo;

import java.io.Serializable;

/**
 * @author wangjian
 * @create 2014年1月16日 下午10:53:28
 * @update TODO
 * 
 * 
 */
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1812082412127454088L;

	private Integer id;
	private Integer user_id;
	private Integer role_id;
	
	public UserRole() {
		super();
	}

	public UserRole(Integer user_id, Integer role_id) {
		super();
		this.user_id = user_id;
		this.role_id = role_id;
	}
	
	public UserRole(Integer id, Integer user_id, Integer role_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.role_id = role_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", user_id=" + user_id
				+ ", role_id=" + role_id + "]";
	}
	
}
