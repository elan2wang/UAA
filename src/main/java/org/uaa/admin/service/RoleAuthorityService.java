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
import org.uaa.admin.persistence.RoleAuthorityMapper;
import org.uaa.admin.pojo.RoleAuthority;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:16:20
 *
 */
@Service
public class RoleAuthorityService {

	@Autowired
	private RoleAuthorityMapper roleAuthorityMapper;
	
	@Transactional
	public void deleteAuthorities(Integer role_id, Integer mod_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("mod_id", mod_id);
		roleAuthorityMapper.deleteRoleAuthorities(params);
	}
	
	@Transactional
	public void assignAuthorities(Integer role_id, Integer mod_id, List<Integer> authorities) {
		// delete first
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("mod_id", mod_id);
		roleAuthorityMapper.deleteRoleAuthorities(params);
		// assign with new authorities
		for (Integer auth_id : authorities) {
			RoleAuthority roleAuthority = new RoleAuthority(role_id, auth_id);
			roleAuthorityMapper.insertRoleAuthority(roleAuthority);
		}
	}
	
	public List<RoleAuthority> queryRoleAuthorities(Map<String, Object> params) {
		return roleAuthorityMapper.queryRoleAuthorities(params);
	}


}
