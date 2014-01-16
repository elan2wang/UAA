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
 * @create 2014年1月16日 下午2:32:53
 * @update TODO
 * 
 * 
 */
public class Resource implements Serializable{

	private static final long serialVersionUID = 8892468477522295506L;
	
	private Integer res_id;
	private String res_uri;
	private String res_type;
	private String res_action;
	private String res_description;
	private Boolean res_enable;
	private Integer mod_id;
	
	public Resource() {
		super();
	}

	public Resource(Integer res_id, String res_uri, String res_type, String res_action,
			String res_description, Boolean res_enable, Integer mod_id) {
		super();
		this.res_id = res_id;
		this.res_uri = res_uri;
		this.res_type = res_type;
		this.res_action = res_action;
		this.res_description = res_description;
		this.res_enable = res_enable;
		this.mod_id = mod_id;
	}

	public Integer getRes_id() {
		return res_id;
	}

	public void setRes_id(Integer res_id) {
		this.res_id = res_id;
	}

	public String getRes_uri() {
		return res_uri;
	}

	public void setRes_uri(String res_uri) {
		this.res_uri = res_uri;
	}

	public String getRes_type() {
		return res_type;
	}

	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}

	public String getRes_action() {
		return res_action;
	}

	public void setRes_action(String res_action) {
		this.res_action = res_action;
	}
	
	public String getRes_description() {
		return res_description;
	}

	public void setRes_description(String res_description) {
		this.res_description = res_description;
	}

	public Boolean getRes_enable() {
		return res_enable;
	}

	public void setRes_enable(Boolean res_enable) {
		this.res_enable = res_enable;
	}

	public Integer getMod_id() {
		return mod_id;
	}

	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}

	@Override
	public String toString() {
		return "Resource [res_id=" + res_id + ", res_uri=" + res_uri
				+ ", res_type=" + res_type + ", res_action=" + res_action + ", res_description="
				+ res_description + ", res_enable=" + res_enable + ", mod_id="
				+ mod_id + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("res_id", res_id);
		map.put("res_uri", res_uri);
		map.put("res_type", res_type);
		map.put("res_action", res_action);
		map.put("res_description", res_description);
		map.put("res_enable", res_enable);
		map.put("mod_id", mod_id);
		
		return map;
	}
}
