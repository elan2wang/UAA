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
import org.uaa.admin.persistence.UserMapper;
import org.uaa.admin.pojo.User;
import org.uaa.common.BaseService;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月16日 下午3:59:10
 *
 */
@Service
public class UserService extends BaseService{
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	public void insertUser(User user) {
		userMapper.insertUser(user);
	}
	
	@Transactional
	public void updateUser(User user) {
		userMapper.updateUser(user);
	}
	
	@Transactional
	public void deleteUser(Integer user_id) {
		userMapper.deleteUser(user_id);
	}
	
	public User queryUserByUsername(String username) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		
		return userMapper.queryUser(params);
	}
	
	public User queryUserById(Integer user_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		return userMapper.queryUser(params);
	}

	public User queryUserByMobile(String mobile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		
		return userMapper.queryUser(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<User> queryUsers(Map params) {
		List<User> list = userMapper.queryUsers(params);
		Page page = (Page) params.get("page");
		return paging(list, page);
	}
	
	//根据部门编号获取相关出纳账号
	public List<User> queryUsersByDepartmentId(Integer dep_id) {
		return userMapper.queryUsersByDepartmentId(dep_id);
	}
}
