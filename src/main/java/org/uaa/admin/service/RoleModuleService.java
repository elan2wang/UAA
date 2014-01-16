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
import org.uaa.admin.persistence.ModuleMapper;
import org.uaa.admin.persistence.RoleModuleMapper;
import org.uaa.admin.pojo.Module;
import org.uaa.admin.pojo.RoleModule;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:17:30
 *
 */
@Service
public class RoleModuleService {
	@Autowired
	private RoleModuleMapper roleModuleMapper;
	@Autowired
	private ModuleMapper moduleMapper;

	@Transactional
	public void deleteRoleModule(Integer role_id, Integer mod_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("mod_id", mod_id);
		roleModuleMapper.deleteRoleModules(params);
		// 判断是否删除该模块的父模块与角色的关联
		Module mod =moduleMapper.queryModule(params);
		Integer father_mod = mod.getFather_mod();
		params.put("father_mod", father_mod);
		List<RoleModule> list = roleModuleMapper.queryRoleModulesByFather(params);
		if (list == null || list.size() == 0) {
			params.put("mod_id", father_mod);
			roleModuleMapper.deleteRoleModules(params);
		}
	}

	@Transactional
	public void deleteRoleModules(Integer role_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		roleModuleMapper.deleteRoleModules(params);
	}

	@Transactional
	public void assignRoleModule(Integer role_id, Integer mod_id) {
		RoleModule roleModule = new RoleModule(role_id, mod_id);
		roleModuleMapper.insertRoleModule(roleModule);
	}

	@Transactional
	public void assignRoleModules(Integer role_id, List<Integer> mods) {
		// delete first
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		roleModuleMapper.deleteRoleModules(params);
		// assign with new modules
		for (Integer mod_id : mods) {
			RoleModule roleModule = new RoleModule(role_id, mod_id);
			roleModuleMapper.insertRoleModule(roleModule);
		}
	}

	public RoleModule queryRoleModule(Integer role_id, Integer mod_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		params.put("mod_id", mod_id);
		return roleModuleMapper.queryRoleModule(params);
	}

	public List<RoleModule> queryRoleModules(Integer role_id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id", role_id);
		return roleModuleMapper.queryRoleModules(params);
	}
}
