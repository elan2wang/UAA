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
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangjian
 * @create 2014年1月16日 下午2:33:35
 * @update TODO
 * 
 * 
 */
public class User implements Serializable{

	private static final long serialVersionUID = -6961988904632764220L;
	
	private Integer user_id;
	private String username;
	private String password;
	private String email;
	private String mobile;
	private Integer dep_id;
	private Integer creator;
	private String last_login_addr;
	private String user_status;
	private Boolean user_enable;
	private Timestamp create_time;
	private Timestamp last_login_time;
	
	// ~~~~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public User() {
		super();
	}

	public User(Integer user_id, String username, String password,
			String email, String mobile, Integer dep_id, Integer creator,
			String last_login_addr, String user_status, Boolean user_enable,
			Timestamp create_time, Timestamp last_login_time) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.dep_id = dep_id;
		this.creator = creator;
		this.user_status = user_status;
		this.user_enable = user_enable;
		this.create_time = create_time;
		this.last_login_addr = last_login_addr;
		this.last_login_time = last_login_time;
	}

	// ~~~~~~~ toMap and toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("user_id", user_id);
		map.put("username", username);
		map.put("password", "confidential");
		map.put("email", email);
		map.put("mobile", mobile);
		map.put("dep_id", dep_id);
		map.put("creator", creator);
		map.put("create_time", create_time);
		map.put("last_login_addr", last_login_addr);
		map.put("last_login_time", last_login_time);
		map.put("user_enable", user_enable);
		map.put("user_status", user_status);
		return map;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", mobile="
				+ mobile + ", dep_id=" + dep_id + ", creator=" + creator
				+ ", last_login_addr=" + last_login_addr + ", user_status="
				+ user_status + ", user_enable=" + user_enable
				+ ", create_time=" + create_time + ", last_login_time="
				+ last_login_time + "]";
	}

	// ~~~~~~~ Getters and Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getDep_id() {
		return dep_id;
	}

	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getLast_login_addr() {
		return last_login_addr;
	}

	public void setLast_login_addr(String last_login_addr) {
		this.last_login_addr = last_login_addr;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public Boolean getUser_enable() {
		return user_enable;
	}

	public void setUser_enable(Boolean user_enable) {
		this.user_enable = user_enable;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Timestamp last_login_time) {
		this.last_login_time = last_login_time;
	}
}
