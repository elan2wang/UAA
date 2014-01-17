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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.uaa.admin.pojo.User;
import org.uaa.admin.pojo.Module;
import org.uaa.admin.pojo.Role;
import org.uaa.admin.service.UserService;
import org.uaa.admin.service.ModuleService;
import org.uaa.admin.service.ProfileService;
import org.uaa.admin.service.RoleService;
import org.uaa.common.BaseResource;
import org.uaa.common.http.ResponseWithData;
import org.uaa.common.http.ResponseWithStatus;
import org.uaa.security.SecuritySupport;
import org.uaa.security.core.SecurityContextHolder;

/**
 * @author wangjian
 * @create 2014年1月16日 下午1:48:15
 *
 */
@Path("/")
@Controller
public class Admins extends BaseResource{
	private static Logger log = LoggerFactory.getLogger(Admins.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private RoleService roleService;
	
	private String request = "//////";
	
	@Path("login") @POST
	@Produces(MediaType.APPLICATION_JSON)
	public String login() {
		log.info("Login successfully");
		
		request = uriInfo.getRequestUri().toString();
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Login Successfully");
		return response.toJson();
	}
	
	@Path("page_init") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String page_init() {
		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		// load user basic info
		Integer uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
		String username = SecurityContextHolder.getContext().getAuthenticationToken().getUsername();
		List<Integer> roles = SecurityContextHolder.getContext().getAuthenticationToken().getRoles();
		
		User user = userService.queryUserById(uid);
		attrs.put("uid", uid);
		attrs.put("name", username);
		attrs.put("mobile", user.getMobile());
		// load role info
		List<Map<String, Object>> role_list = new ArrayList<Map<String, Object>>();
		for (Integer role_id : roles) {
			Role role = roleService.queryRole(role_id);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("role_id", role.getRole_id());
			map.put("role_level", role.getRole_level());
			map.put("role_name", role.getRole_name());
			role_list.add(map);
		}
		attrs.put("roles", role_list);
		// load module info
		List<Module> modules = moduleService.queryMenuModules(1, roles);
		List<Map<String, Object>> mod_list = new ArrayList<Map<String, Object>>();
		for (Module mod : modules) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("mod_id", mod.getMod_id());
			map.put("mod_level", mod.getMod_level());
			map.put("mod_name", mod.getMod_name());
			map.put("mod_link", mod.getMod_link());
			map.put("mod_order", mod.getMod_order());
			mod_list.add(map);
		}
		attrs.put("modules", mod_list);
		
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}
	
	@Path("tabs_init") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String tabs_init(@QueryParam("father_mod") Integer father_mod) {
		List<Integer> roles = SecurityContextHolder.getContext().getAuthenticationToken().getRoles();
		List<Module> subModules = moduleService.querySubModules(father_mod, roles);
		
		Map<String, Object> attrs = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> subs = new ArrayList<Map<String, Object>>();
		for (Module mod : subModules) {
			Map<String, Object> sub = mod.toMap();
			subs.add(sub);
		}
		attrs.put("tabs", subs);
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}
	
	
	@Path("reload_security_meta_source") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String reloadSecurityMetaSource() {
		SecuritySupport.reloadSecurityMetadataSource();
		
		request = uriInfo.getRequestUri().toString();
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "reload security meta source successfully");
		return response.toJson();
	}
		
}
