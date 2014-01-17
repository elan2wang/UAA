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
package org.uaa.security.persistence;

import java.util.List;
import java.util.Map;

import org.uaa.admin.pojo.Apilog;
import org.uaa.admin.pojo.Resource;
import org.uaa.admin.pojo.User;


/**
 * @author wangjian
 * @create 2014年1月16日 下午2:26:12
 *
 */
public interface SecurityMapper {
	public List<Resource> queryPublicResources();

	public List<Resource> queryNonPublicResources();
	
	public User queryUserByUsername(Map<String, Object> params);
	
	public List<Integer> queryUserRoles(Map<String, Object> params);
	
	public List<Integer> queryNeededRoles(Map<String, Object> params);
	
	public void updateLastLoginInfo(Map<String, Object> params);
	
	public void insertLog(Apilog apilog);
	
	public void updateLog(Apilog apilog);
}
