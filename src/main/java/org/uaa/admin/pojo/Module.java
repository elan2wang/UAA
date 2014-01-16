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
 * @create 2014年1月16日 下午11:06:58
 * @update TODO
 * 
 * 
 */
public class Module implements Serializable{
	
	private static final long serialVersionUID = -1063986005334216316L;
	
	private Integer mod_id;
	private String	mod_name;
	private Integer mod_level;
	private Integer father_mod;
	private String	mod_link;
	private String	mod_type;
	private String	mod_description;
	private Double mod_order;
	private Boolean mod_enable;
	
	public Module() {
		super();
	}

	public Module(Integer mod_id, String mod_name, Integer mod_level, Integer father_mod,
			String mod_link, String mod_type, String mod_description,
			Double mod_order, Boolean mod_enable) {
		super();
		this.mod_id = mod_id;
		this.mod_name = mod_name;
		this.mod_level = mod_level;
		this.father_mod = father_mod;
		this.mod_link = mod_link;
		this.mod_type = mod_type;
		this.mod_description = mod_description;
		this.mod_order = mod_order;
		this.mod_enable = mod_enable;
	}
	
	public Integer getMod_id() {
		return mod_id;
	}
	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}
	public String getMod_name() {
		return mod_name;
	}
	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}
	public Integer getMod_level() {
		return mod_level;
	}
	public void setMod_level(Integer mod_level) {
		this.mod_level = mod_level;
	}
	public Integer getFather_mod() {
		return father_mod;
	}
	public void setFather_mod(Integer father_mod) {
		this.father_mod = father_mod;
	}
	public String getMod_link() {
		return mod_link;
	}
	public void setMod_link(String mod_link) {
		this.mod_link = mod_link;
	}
	public String getMod_type() {
		return mod_type;
	}
	public void setMod_type(String mod_type) {
		this.mod_type = mod_type;
	}
	public String getMod_description() {
		return mod_description;
	}
	public void setMod_description(String mod_description) {
		this.mod_description = mod_description;
	}
	public Double getMod_order() {
		return mod_order;
	}
	public void setMod_order(Double mod_order) {
		this.mod_order = mod_order;
	}
	public Boolean getMod_enable() {
		return mod_enable;
	}
	public void setMod_enable(Boolean mod_enable) {
		this.mod_enable = mod_enable;
	}
	
	@Override
	public String toString() {
		return "Module [mod_id=" + mod_id + ", mod_name=" + mod_name
				+ ", mod_level=" + mod_level + ", father_mod=" + father_mod
				+ ", mod_link=" + mod_link + ", mod_type=" + mod_type
				+ ", mod_description=" + mod_description + ", mod_order="
				+ mod_order + ", mod_enable=" + mod_enable + "]";
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("mod_id", mod_id);
		map.put("mod_name", mod_name);
		map.put("mod_level", mod_level);
		map.put("father_mod", father_mod);
		map.put("mod_link", mod_link);
		map.put("mod_type", mod_type);
		map.put("mod_description", mod_description);
		map.put("mod_order", mod_order);
		map.put("mod_enable", mod_enable);
		
		return map;
	}
	
}
