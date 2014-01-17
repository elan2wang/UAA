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
package org.uaa.security;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uaa.admin.pojo.Apilog;
import org.uaa.admin.pojo.Resource;
import org.uaa.admin.pojo.User;
import org.uaa.security.persistence.SecurityMapper;

/**
 * @author wangjian
 * @create 2014年1月16日 下午2:18:34
 *
 */
@Service
public class SecurityService {

	@Autowired
	private SecurityMapper securityMapper;
	
	public List<Resource> queryPublicResources() {
		return securityMapper.queryPublicResources();
	}

	public List<Resource> queryNonPublicResources(){
		return securityMapper.queryNonPublicResources();
	}

	public User queryUserByUsername(String username){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		return securityMapper.queryUserByUsername(params);
	}
	
	public List<Integer> queryUserRoles(Integer user_id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user_id", user_id);
		return securityMapper.queryUserRoles(params);
	}

	public List<Integer> queryNeededRoles(String resource, String action){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("res_uri", resource);
		params.put("res_action", action);
		return securityMapper.queryNeededRoles(params);
	}
	
	public void updateLastLoginInfo(String username, String addr) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		params.put("last_login_addr", addr);
		params.put("last_login_time", new Timestamp(System.currentTimeMillis()));
		securityMapper.updateLastLoginInfo(params);
	}
	
	public void insertLog(Apilog apilog) {
		securityMapper.insertLog(apilog);
	}

	public void updateLog(Apilog apilog) {
		securityMapper.updateLog(apilog);
	}
}
