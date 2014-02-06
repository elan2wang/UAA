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
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uaa.admin.persistence.ApilogMapper;

/**
 * @author wangjian
 * @create 2014年1月17日 上午9:10:59
 *
 */
@Component
public class ApilogService {

	@Autowired
	private ApilogMapper apilogMapper;
	
	public Integer getRequestCount(Integer interval, Long end_time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("interval", interval);
		params.put("end_time", end_time);
		return apilogMapper.getRequestCount(params);
	}

	public Integer getUniqueAddrCount(Integer interval, Long end_time) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("interval", interval);
		params.put("end_time", end_time);
		return apilogMapper.getUniqueAddrCount(params);
	}

}
