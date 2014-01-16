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
package org.uaa.admin.persistence;

import java.util.List;
import java.util.Map;

import org.uaa.admin.pojo.Department;

/**
 * @author wangjian
 * @create 2014年1月16日 上午11:25:01
 *
 */
public interface DepartmentMapper {

	public void addDepartment(Department department);

	public void deleteDepartment(Integer dep_id);

	public void updateDepartment(Department department);

	public Department queryDepartment(Map<String, Object> params);

	public List<Department> queryDepartments(Map<String, Object> params);
	
	public List<Department> queryDepartmentsByCondition(Map<String, Object> params);

	public Integer getDepartmentsCountByCondition(Map<String, Object> params);
}
