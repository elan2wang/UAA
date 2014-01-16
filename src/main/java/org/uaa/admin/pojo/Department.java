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
 * @create 2014年1月16日 上午10:17:11
 * @update TODO
 * 
 * 
 */
public class Department implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 3320118454768607917L;

	private Integer dep_id;
	private Integer dep_level;
	private String dep_name;
	private String address;
	
	public Department() {
		super();
	}

	public Department(Integer dep_id, Integer dep_level, String dep_name,
			String address) {
		super();
		this.dep_id = dep_id;
		this.dep_level = dep_level;
		this.dep_name = dep_name;
		this.address = address;
	}

	
	
	public Department(Integer dep_level, String dep_name, String address) {
		super();
		this.dep_level = dep_level;
		this.dep_name = dep_name;
		this.address = address;
	}

	public Integer getDep_id() {
		return dep_id;
	}

	public void setDep_id(Integer dep_id) {
		this.dep_id = dep_id;
	}

	public Integer getDep_level() {
		return dep_level;
	}

	public void setDep_level(Integer dep_level) {
		this.dep_level = dep_level;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Department [dep_id=" + dep_id + ", dep_level=" + dep_level
				+ ", dep_name=" + dep_name + ", address=" + address + "]";
	}

	public Object clone() {
		Department obj = null;
		try {
			obj = (Department) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("dep_id", dep_id);
		map.put("dep_level", dep_level);
		map.put("dep_name", dep_name);
		map.put("address", address);
		
		return map;
	}
}
