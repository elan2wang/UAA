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

import org.uaa.admin.pojo.UserRole;

/**
 * @author wangjian
 * @create 2014年1月16日 上午11:31:13
 *
 */
public interface UserRoleMapper {
	public void insertUserRole(UserRole accountRole);

	public void deleteUserRoles(Map<String, Object> params);

	public List<UserRole> queryUserRoles(Map<String, Object> params);
}
