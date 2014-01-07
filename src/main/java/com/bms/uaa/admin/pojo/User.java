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
package com.bms.uaa.admin.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangjian
 * @create 2014年1月7日 下午3:47:29
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = -4163904242193757363L;

	private Integer user_id;
	
	private String username;
	private String password;
	private String email;
	private String mobile;
	
	private Integer creator;
	private Timestamp create_time;
	
	private Boolean user_enable;
	private String user_status;
	
	private String last_login_addr;
	private Timestamp last_login_time;
	
	// ~~~~ Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public User() {
		super();
	}

	public User(Integer user_id, String username, String password,
			String email, String mobile, Integer creator,
			Timestamp create_time, Boolean user_enable, String user_status,
			String last_login_addr, Timestamp last_login_time) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.mobile = mobile;
		this.creator = creator;
		this.create_time = create_time;
		this.user_enable = user_enable;
		this.user_status = user_status;
		this.last_login_addr = last_login_addr;
		this.last_login_time = last_login_time;
	}

	// ~~~~ toMap and toString ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Map<String, Object> toMap() {
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		json.put("user_id", user_id);
		json.put("username", username);
		json.put("password", "confidential");
		json.put("email", email);
		json.put("mobile", mobile);
		json.put("creator", creator);
		json.put("create_time", create_time);
		json.put("user_enable", user_enable);
		json.put("user_status", user_status);
		json.put("last_login_addr", last_login_time);
		json.put("last_login_time", last_login_time);
		
		return json;
	}
	
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", mobile="
				+ mobile + ", creator=" + creator + ", create_time="
				+ create_time + ", user_enable=" + user_enable
				+ ", user_status=" + user_status + ", last_login_addr="
				+ last_login_addr + ", last_login_time=" + last_login_time
				+ "]";
	}

	// ~~~~ Getters and Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Boolean getUser_enable() {
		return user_enable;
	}

	public void setUser_enable(Boolean user_enable) {
		this.user_enable = user_enable;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public String getLast_login_addr() {
		return last_login_addr;
	}

	public void setLast_login_addr(String last_login_addr) {
		this.last_login_addr = last_login_addr;
	}

	public Timestamp getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Timestamp last_login_time) {
		this.last_login_time = last_login_time;
	}
}
