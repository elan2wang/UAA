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
 * @create 2014年1月16日 上午10:19:05
 * @update TODO
 * 
 * 
 */
public class RoleAuthority implements Serializable{

	private static final long serialVersionUID = -5277005590243285694L;

	private Integer id;
	private Integer role_id;
	private Integer auth_id;
	
	public RoleAuthority() {
		super();
	}

	public RoleAuthority(Integer role_id, Integer auth_id) {
		super();
		this.role_id = role_id;
		this.auth_id = auth_id;
	}
	
	public RoleAuthority(Integer id, Integer role_id, Integer auth_id) {
		super();
		this.id = id;
		this.role_id = role_id;
		this.auth_id = auth_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(Integer auth_id) {
		this.auth_id = auth_id;
	}

	@Override
	public String toString() {
		return "RoleAuthority [id=" + id + ", role_id=" + role_id
				+ ", auth_id=" + auth_id + "]";
	}

}
