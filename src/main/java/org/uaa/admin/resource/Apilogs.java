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
package org.uaa.admin.resource;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.uaa.admin.service.ApilogService;
import org.uaa.common.BaseResource;
import org.uaa.common.http.ResponseWithData;

/**
 * @author wangjian
 * @create 2014年1月17日 上午9:11:24
 *
 */
@Path("/logs")
@Controller
public class Apilogs extends BaseResource{

	private final static Integer default_interval = 10;
	@Autowired
	private ApilogService apilogService;

	@Path("/addrs") @GET
	public String getUniqueAddrCount(@QueryParam("interval") Integer interval) {
		if (interval == null) interval = default_interval;

		Timestamp now = new Timestamp(System.currentTimeMillis());
		Integer count = apilogService.getUniqueAddrCount(interval, now.getTime());

		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		attrs.put("count", count);

		ResponseWithData res = new ResponseWithData(attrs);
		return res.toJson();
	}

	@Path("/requests") @GET
	public String getRequestCount(@QueryParam("interval") Integer interval) {
		if (interval == null) interval = default_interval;

		Timestamp now = new Timestamp(System.currentTimeMillis());
		Integer count = apilogService.getRequestCount(interval, now.getTime());

		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		attrs.put("count", count);

		ResponseWithData res = new ResponseWithData(attrs);
		return res.toJson();
	}

}
