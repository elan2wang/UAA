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
import org.uaa.admin.persistence.AuthorityResourceMapper;
import org.uaa.admin.pojo.Authority;
import org.uaa.admin.pojo.AuthorityResource;
import org.uaa.admin.pojo.Resource;
import org.uaa.common.BaseService;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:08:00
 *
 */
@Service
public class AuthorityResourceService extends BaseService {
	@Autowired
	private AuthorityResourceMapper authorityResourceMapper;

	public void assignResources(Integer auth_id, List<Integer> resources) {
		// delete first
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("auth_id", auth_id);
		authorityResourceMapper.deleteAuthorityResources(params);
		// assign with new resources
		for (Integer res_id : resources) {
			AuthorityResource authRes = new AuthorityResource(auth_id, res_id);
			authorityResourceMapper.insertAuthorityResource(authRes);;
		}
	}
	
	public List<AuthorityResource> queryAuthorityResourcesByAuth(Integer auth_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("auth_id", auth_id);
		return authorityResourceMapper.queryAuthorityResources(params);
	}
	
	public List<AuthorityResource> queryAuthorityResourcesByRes(Integer res_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("res_id", res_id);
		return authorityResourceMapper.queryAuthorityResources(params);
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> queryResourcesByAuth(Integer auth_id, Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("auth_id", auth_id);
		List<Resource> list = authorityResourceMapper.queryResourcesByAuth(params);
		
		if (page == null) return list;
		else return paging(list, page);
	}
	
	@SuppressWarnings("unchecked")
	public List<Authority> queryAuthoritiesByRes(Integer res_id, Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("res_id", res_id);
		List<Authority> list = authorityResourceMapper.queryAuthoritiesByRes(params);
		
		if (page == null) return list;
		else return paging(list, page);
	}

}
