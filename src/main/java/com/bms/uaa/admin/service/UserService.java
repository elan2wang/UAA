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
package com.bms.uaa.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bms.uaa.admin.persistence.UserMapper;
import com.bms.uaa.admin.pojo.User;
import com.bms.uaa.common.BaseService;
import com.bms.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月7日 下午3:59:10
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
	
	public User queryUsers(Integer user_id) {
		return userMapper.queryUser(user_id);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> queryUsers(Map<String, Object> params) {
		List<User> list = userMapper.queryUsers(params);
		Page page = (Page) params.get("page");
		return paging(list, page);
	}
}
