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
import org.uaa.admin.persistence.ProfileMapper;
import org.uaa.admin.pojo.Profile;

/**
 * @author wangjian
 * @create 2014年1月16日 下午12:14:54
 *
 */
@Service
public class ProfileService {

	@Autowired
	private ProfileMapper profileMapper;
	
	@Transactional
	public void insertProfile(Profile profile) {
		profileMapper.insertProfile(profile);
	}
	
	@Transactional
	public void updateProfile(Profile profile) {
		profileMapper.updateProfile(profile);
	}
	
	@Transactional
	public void deleteProfile(Integer profile_id) {
		profileMapper.deleteProfile(profile_id);
	}
	
	public Profile queryProfile(Integer account_id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account_id", account_id);
		return profileMapper.queryProfile(params);
	}
	
	//获得所有的Profile
	public List<Profile> queryProfiles(){
		return profileMapper.queryProfiles();
	}
}
