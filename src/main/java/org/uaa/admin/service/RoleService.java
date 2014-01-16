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
import org.uaa.common.BaseService;
import org.uaa.admin.persistence.RoleMapper;
import org.uaa.admin.pojo.Role;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:18:33
 *
 */
@Service
public class RoleService extends BaseService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Transactional
	public void insertRole(Role role) {
		roleMapper.insertRole(role);
	}
	
	@Transactional
	public void updateRole(Role role) {
		roleMapper.updateRole(role);
	}
	
	@Transactional
	public void deleteRole(Integer role_id) {
		roleMapper.deleteRole(role_id);
	}
	
	public Role queryRole(Integer role_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		return roleMapper.queryRole(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Role> queryRoles(Map params) {
		List<Role> list = roleMapper.queryRoles(params);
		Page page = (Page) params.get("page");
		return paging(list, page);
	}
	
	public List<Role> queryRolesByLevel(Integer level) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_level", level);
		return roleMapper.queryRoles(params);
	}
}
