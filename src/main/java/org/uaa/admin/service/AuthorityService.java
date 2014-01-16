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
import org.uaa.admin.persistence.AuthorityMapper;
import org.uaa.admin.pojo.Authority;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:10:15
 *
 */
@Service
public class AuthorityService extends BaseService {
	@Autowired
	private AuthorityMapper authorityMapper;
	
	@Transactional
	public void insertAuthority(Authority auth) {
		authorityMapper.insertAuthority(auth);
	}
	
	@Transactional
	public void updateAuthority(Authority auth) {
		authorityMapper.updateAuthority(auth);
	}
	
	@Transactional
	public void deleteAuthority(Integer auth_id) {
		authorityMapper.deleteAuthority(auth_id);
	}
	
	public Authority queryAuthorityById(Integer auth_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("auth_id", auth_id);
		return authorityMapper.queryAuthority(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Authority> queryAuthorities(Map params) {
		List<Authority> list = authorityMapper.queryAuthorities(params);
		Page page = (Page) params.get("page");
		return paging(list, page);
	}

}
