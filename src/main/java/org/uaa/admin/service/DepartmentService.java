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
package org.uaa.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uaa.common.BaseService;
import org.uaa.admin.persistence.DepartmentMapper;
import org.uaa.admin.pojo.Department;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:11:44
 *
 */
@Service
public class DepartmentService extends BaseService {

	@Autowired
	private DepartmentMapper departmentMapper;
	

	public void addDepartment(Department department){
		departmentMapper.addDepartment(department);
	}
	
	public void updateDepartment(Department department){
		departmentMapper.updateDepartment(department);
	}
	
	public void deleteDepartment(Integer dep_id){
		departmentMapper.deleteDepartment(dep_id);
	}
	
	public Department queryDepartment(Integer dep_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dep_id", dep_id);
		return departmentMapper.queryDepartment(params);
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Department> queryDepartments(Map<String, Object> params) {
		List<Department> list = departmentMapper.queryDepartments(params);
		Page page = (Page) params.get("page");
		if (page == null) {
			return list;
		}
		return paging(list, page);
	}
	
	public List<Department> queryDepartmentsByLevel(Integer level) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dep_level", level);
		return departmentMapper.queryDepartments(params);
	}
	
}
