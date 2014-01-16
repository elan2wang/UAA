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
import org.uaa.admin.persistence.UserDepartmentMapper;
import org.uaa.admin.pojo.UserDepartment;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:03:58
 *
 */
@Service
public class UserDepartmentService extends BaseService {

	@Autowired
	private UserDepartmentMapper userDepartmentMapper;
	
	public void assignDepartment(Integer user_id, List<Integer> deps) {
		// delete first
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		userDepartmentMapper.deleteUserDepartments(params);
		// assign with new departments
		for (Integer dep_id : deps) {
			UserDepartment userDepartment = new UserDepartment(user_id, dep_id);
			userDepartmentMapper.insertUserDepartment(userDepartment);
		}
	}
	
	public List<UserDepartment> queryUserDepartments(Integer user_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		return userDepartmentMapper.queryUserDepartments(params);
	}

}
