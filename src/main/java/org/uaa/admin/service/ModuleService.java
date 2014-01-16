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
import org.uaa.admin.persistence.ModuleMapper;
import org.uaa.admin.pojo.Module;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:12:36
 *
 */
@Service
public class ModuleService extends BaseService {
	@Autowired
	private ModuleMapper moduleMapper;
	
	@Transactional
	public void insertModule(Module module) {
		moduleMapper.insertModule(module);
	}
	
	@Transactional
	public void updateModule(Module module) {
		moduleMapper.updateModule(module);
	}
	
	@Transactional
	public void deleteModule(Integer mod_id) {
		moduleMapper.deleteModule(mod_id);
	}
	
	public Module queryModuleById(Integer mod_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mod_id", mod_id);
		return moduleMapper.queryModule(params);
	}
	
	public List<Module> querySubModules(Integer father_mod, List<Integer> roles) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("father_mod", father_mod);
		params.put("roles", roles);
		return moduleMapper.querySubModules(params);
	}
	
	public List<Module> queryMenuModules(Integer mod_level, List<Integer> roles) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mod_level", mod_level);
		params.put("roles", roles);
		return moduleMapper.queryMenuModules(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Module> queryModules(Map params) {
		List<Module> list = moduleMapper.queryModules(params);
		Page page = (Page) params.get("page");
		if (page == null) {
			return list;
		}
		return paging(list, page);
	}

}
