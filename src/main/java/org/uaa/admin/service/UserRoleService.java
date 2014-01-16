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
import org.springframework.transaction.annotation.Transactional;
import org.uaa.admin.persistence.UserRoleMapper;
import org.uaa.admin.pojo.UserRole;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:06:16
 *
 */
@Service
public class UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Transactional
	public void assignRole(Integer user_id, List<Integer> roles) {
		// delete first
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		userRoleMapper.deleteUserRoles(params);
		// assign with new roles
		for (Integer role_id : roles) {
			UserRole userRole = new UserRole(user_id, role_id);
			userRoleMapper.insertUserRole(userRole);
		}
	}

	public List<UserRole> queryUserRoles(Integer user_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		return userRoleMapper.queryUserRoles(params);
	}
}
