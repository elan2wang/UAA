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
package com.bms.uaa.admin.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bms.uaa.admin.pojo.User;
import com.bms.uaa.admin.service.UserService;
import com.bms.uaa.common.BaseResource;
import com.bms.uaa.common.Page;
import com.bms.uaa.common.http.ResponseWithData;


/**
 * @author wangjian
 * @create 2014年1月7日 下午3:57:33
 *
 */

@Path("/users")
@Controller
public class Users extends BaseResource{

	@Autowired
	private UserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUsers(@QueryParam("start") Integer start, 
			@QueryParam("itemsPerPage") Integer itemsPerPage) {
		Map<String, Object>  params = new HashMap<String, Object>();
		
		Page page = new Page();
		params.put("page", page);
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);
		
		List<User> list = userService.queryUsers(params);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (User user : list){
			Map<String, Object> item = user.toMap();
			items.add(item);
		}
		
		// 生成返回JSON
		Map<String, Object> attrs = generateQueryResult(page, items);
		ResponseWithData res = new ResponseWithData(attrs);
		
		return res.toJson();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addUser() {

		return null;
	}

	@GET @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@PathParam("id") Integer user_id) {
		User user = userService.queryUsers(user_id);
		ResponseWithData res = new ResponseWithData(user.toMap());
		
		return res.toJson();
	}

	@PUT @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUser() {

		return null;
	}

	@DELETE @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser() {
		
		return null;
	}
}
