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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.uaa.admin.pojo.User;
import org.uaa.admin.service.UserService;
import org.uaa.common.BaseResource;
import org.uaa.common.Page;
import org.uaa.common.http.ResponseWithData;
import org.uaa.admin.service.UserDepartmentService;
import org.uaa.admin.service.UserRoleService;
import org.uaa.admin.service.DepartmentService;
import org.uaa.admin.service.RoleService;
import org.uaa.common.ConfigUtil;
import org.uaa.common.Crypto;
import org.uaa.common.http.ResponseWithStatus;
import org.uaa.security.core.SecurityContextHolder;


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
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserDepartmentService userDepartmentService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;
	
	private String request = "//////";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list(@QueryParam("start") Integer start, @QueryParam("itemsPerPage") Integer itemsPerPage,
			@QueryParam("user_enable") Boolean user_enable, @QueryParam("dep_id") Integer dep_id,
			@QueryParam("username") String username) {
		// 获取参数
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Page page = new Page();
		params.put("page", page);
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);
		if (user_enable != null) params.put("user_enable", user_enable);
		if (dep_id != null) params.put("dep_id", dep_id);
		if (username != null && !username.equals("")) params.put("username", username);
		
		// 获取数据
		Map<Integer, String> depMap = getDepMap(departmentService);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		List<User> list = userService.queryUsers(params);
		for (User user : list){
			Map<String, Object> item = user.toMap();
			items.add(item);
			item.put("department", depMap.get(user.getDep_id()));
		}
		
		// 生成返回JSON
		Map<String, Object> attrs = generateQueryResult(page, items);
		ResponseWithData response = new ResponseWithData(attrs);
		
		return response.toJson();
	}
	
	@Path("/view") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@QueryParam("user_id") Integer user_id) {
		request = uriInfo.getRequestUri().toString();
		User user = userService.queryUserById(user_id);
		if (user == null) {
			ResponseWithStatus response = new ResponseWithStatus(request, "20001", ConfigUtil.getValue("20001"));
			return response.toJson();
		}
		Map<String, Object> item = user.toMap();
		item.put("creator", userService.queryUserById(
				user.getUser_id()).getUsername());
		
		ResponseWithData response = new ResponseWithData(item);
		
		return response.toJson();
	}
	
	@Path("/add") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addUser(@FormParam("username") String username, 
			@FormParam("department") Integer department, @FormParam("password") String password, 
			@FormParam("email") String email, @FormParam("mobile") String mobile) throws NoSuchAlgorithmException {
		request = uriInfo.getRequestUri().toString();
		Integer uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
		
		// 先用MD5加密，再用添加盐值的MD5加密
		String psd = Crypto.MD5Encrypt(password);
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(psd);
		user.setEmail(email);
		user.setMobile(mobile); 
		user.setDep_id(department);
		user.setCreator(uid);
		user.setUser_enable(true);
		user.setUser_status("offline");
		
		userService.insertUser(user);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Add User Successfully");
		return response.toJson();
	}
	
	@Path("/update") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateUser(@FormParam("user_id") Integer user_id, 
			@FormParam("username") String username, @FormParam("department") Integer department,
			@FormParam("email") String email, @FormParam("mobile") String mobile) {
		request = uriInfo.getRequestUri().toString();
		User user = userService.queryUserById(user_id);
		user.setUsername(username);
		user.setEmail(email);
		user.setMobile(mobile);
		user.setDep_id(department);
		
		userService.updateUser(user);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Update User Successfully");
		return response.toJson();
	}
	
	@Path("/reset") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String resetPassword(@FormParam("user_id") Integer user_id) {
		String origin_psd = "765760843A4A6D9BB2876FAD7F6FB164"; // 先用MD5加密，再用添加盐值的MD5加密
		
		User user = userService.queryUserById(user_id);
		user.setPassword(origin_psd);
		userService.updateUser(user);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Reset Password Successfully");
		return response.toJson();
	}
	
	@Path("/password") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String changePassword(@FormParam("old_psd") String old_psd, @FormParam("new_psd") String new_psd) {
		request = uriInfo.getRequestUri().toString();
		Integer uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
		User user = userService.queryUserById(uid);
		if (!Crypto.MD5Encrypt(old_psd).equals(user.getPassword())) {
			ResponseWithStatus response = new ResponseWithStatus(request, "20002", ConfigUtil.getValue("20002"));
			return response.toJson();
		}
		user.setPassword(Crypto.MD5Encrypt(new_psd));
		userService.updateUser(user);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Change Password Successfully");
		return response.toJson();
	}
	
	@Path("/switch") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String switchUser(@FormParam("user_id") Integer user_id, 
			@FormParam("enable") Boolean enable) {
		request = uriInfo.getRequestUri().toString();
		User user = userService.queryUserById(user_id);
		user.setUser_enable(enable);
		
		userService.updateUser(user);
		String msg = null;
		if (enable) {
			msg = "Enable User Successfully";
		} else {
			msg = "Disable User Successfully";
		}

		ResponseWithStatus response = new ResponseWithStatus(request, "10000", msg);
		return response.toJson();
	}
	
	@Path("/delete") @POST
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteUser(@FormParam("user_id") Integer user_id) {
		request = uriInfo.getRequestUri().toString();
		// if user not exist
		if (userService.queryUserById(user_id) == null) {
			ResponseWithStatus response = new ResponseWithStatus(request, "20001", ConfigUtil.getValue("20001"));
			return response.toJson();
		}
		// if user exist
		userService.deleteUser(user_id);
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Delete User Successfully");
		return response.toJson();
	}
	
	@Path("/assign_role") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String assignRole(@FormParam("user_id") Integer user_id,
			@FormParam("roles") String roles) {
		request = uriInfo.getRequestUri().toString();
		List<Integer> role_list = new ArrayList<Integer>();
		for (String role : roles.split(",")){
			role_list.add(Integer.parseInt(role));
		}
		userRoleService.assignRole(user_id, role_list);
		
		User user = userService.queryUserById(user_id);
		userService.updateUser(user);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Assign Roles Successfully");
		return response.toJson();
	}
	
	@Path("/assign_dep") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String assignDep(@FormParam("user_id") Integer user_id,
			@FormParam("deps") String deps) {
		request = uriInfo.getRequestUri().toString();
		List<Integer> dep_list = new ArrayList<Integer>();
		for (String dep : deps.split(",")){
			dep_list.add(Integer.parseInt(dep));
		}
		userDepartmentService.assignDepartment(user_id, dep_list);
		
		User user = userService.queryUserById(user_id);
		userService.updateUser(user);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Assign Departments Successfully");
		return response.toJson();
	}
	
}
