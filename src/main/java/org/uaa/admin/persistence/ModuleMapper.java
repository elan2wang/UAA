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

import org.uaa.admin.pojo.Module;

/**
 * @author wangjian
 * @create 2014年1月16日 下午4:00:26
 *
 */
public interface ModuleMapper {
	
	public void insertModule(Module module);

	public void updateModule(Module module);

	public void deleteModule(Integer mod_id);

	public Module queryModule(Map<String, Object> params);

	public List<Module> queryModules(Map<String, Object> params);

	public List<Module> querySubModules(Map<String, Object> params);

	public List<Module> queryMenuModules(Map<String, Object> params);
}
