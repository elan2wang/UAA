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

import org.uaa.admin.pojo.User;

/**
 * @author wangjian
 * @create 2014年1月16日 下午3:59:50
 *
 */
public interface UserMapper {
	
	public Integer insertUser(User user);

	public void updateUser(User user);

	public void deleteUser(Integer user_id);
	
	public User queryUser(Map<String, Object> params);
	
	public List<User> queryUsers(Map<String, Object> params);
	
	///根据部门编号获取相关出纳账号
	public List<User> queryUsersByDepartmentId(Integer dep_id);
}
