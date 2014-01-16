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
 * @create 2014年1月16日 上午10:21:09
 * @update TODO
 * 
 * 
 */
public class RoleModule implements Serializable{

	private static final long serialVersionUID = 1477169762265459190L;

	private Integer id;
	private Integer role_id;
	private Integer mod_id;
	
	public RoleModule() {
		super();
	}

	public RoleModule(Integer role_id, Integer mod_id) {
		super();
		this.id = null;
		this.role_id = role_id;
		this.mod_id = mod_id;
	}
	
	public RoleModule(Integer id, Integer role_id, Integer mod_id) {
		super();
		this.id = id;
		this.role_id = role_id;
		this.mod_id = mod_id;
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

	public Integer getMod_id() {
		return mod_id;
	}

	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}

	@Override
	public String toString() {
		return "RoleModule [id=" + id + ", role_id=" + role_id + ", mod_id="
				+ mod_id + "]";
	}
	
}
