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
 * @create 2014年1月16日 上午10:27:04
 * @update TODO
 * 
 * 
 */
public class AuthorityResource implements Serializable{

	private static final long serialVersionUID = -5658932247479046033L;

	private Integer id;
	private Integer auth_id;
	private Integer res_id;
	
	public AuthorityResource() {
		super();
	}

	public AuthorityResource(Integer auth_id, Integer res_id) {
		super();
		this.auth_id = auth_id;
		this.res_id = res_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAuth_id() {
		return auth_id;
	}

	public void setAuth_id(Integer auth_id) {
		this.auth_id = auth_id;
	}

	public Integer getRes_id() {
		return res_id;
	}

	public void setRes_id(Integer res_id) {
		this.res_id = res_id;
	}

	@Override
	public String toString() {
		return "AuthorityResource [id=" + id + ", auth_id=" + auth_id
				+ ", res_id=" + res_id + "]";
	}

}
