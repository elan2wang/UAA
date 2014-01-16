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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangjian
 * @create 2011年1月16日 下午2:32:42
 * @update TODO
 * 
 * 
 */
public class Role implements Serializable{

	private static final long serialVersionUID = -4649000019502669010L;

	private Integer role_id;
	private String role_name;
	private Integer role_level;
	private String role_type;
	private String role_description;
	private Boolean role_enable;
	
	public Role() {
		super();
	}

	public Role(Integer role_id, String role_name, Integer role_level,
			String role_type, String role_description, Boolean role_enable) {
		super();
		this.role_id = role_id;
		this.role_name = role_name;
		this.role_level = role_level;
		this.role_type = role_type;
		this.role_description = role_description;
		this.role_enable = role_enable;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public Integer getRole_level() {
		return role_level;
	}

	public void setRole_level(Integer role_level) {
		this.role_level = role_level;
	}

	public String getRole_type() {
		return role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	public String getRole_description() {
		return role_description;
	}

	public void setRole_description(String role_description) {
		this.role_description = role_description;
	}

	public Boolean getRole_enable() {
		return role_enable;
	}

	public void setRole_enable(Boolean role_enable) {
		this.role_enable = role_enable;
	}

	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", role_name=" + role_name
				+ ", role_level=" + role_level + ", role_type=" + role_type
				+ ", role_description=" + role_description + ", role_enable="
				+ role_enable + "]";
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("role_id", role_id);
		map.put("role_name", role_name);
		map.put("role_level", role_level);
		map.put("role_type", role_type);
		map.put("role_description",role_description);
		map.put("role_enable", role_enable);
		return map;
	}
	
}
