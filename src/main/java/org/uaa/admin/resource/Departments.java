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
import java.util.HashMap;
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
import org.uaa.common.BaseResource;
import org.uaa.admin.pojo.UserDepartment;
import org.uaa.admin.pojo.Department;
import org.uaa.admin.service.UserDepartmentService;
import org.uaa.admin.service.DepartmentService;
import org.uaa.common.Page;
import org.uaa.common.http.ResponseWithData;
import org.uaa.common.http.ResponseWithStatus;

/**
 * @author wangjian
 * @create 2014年1月16日 下午1:37:43
 *
 */
@Path("departments")
@Controller
public class Departments extends BaseResource{

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserDepartmentService userDepartmentService;

	private String request = "//////";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list(@QueryParam("start") Integer start,  @QueryParam("itemsPerPage") Integer itemsPerPage, 
			@QueryParam("user_id") Integer user_id) {
		// 获取参数
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Page page = new Page();
		params.put("page", page);
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);

		// 获取数据
		/* BEGIN 用于给用户分配部门时，默认选中已分配部门 */
		List<Integer> depList = new ArrayList<Integer>();
		if (user_id != null) {
			List<UserDepartment> myList = userDepartmentService.queryUserDepartments(user_id);
			if (myList != null) {
				for (UserDepartment ad : myList) {
					depList.add(ad.getDep_id());
				}
			}
		}
		/* END 用于给用户分配部门时，默认选中已分配部门 */
		List<Department> list = departmentService.queryDepartments(params);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (Department dep : list){
			Map<String, Object> item = dep.toMap();
			/* BEGIN 用于给用户分配部门时，默认选中已分配部门 */
			if (user_id != null) {
				if (depList.contains(dep.getDep_id())) {
					item.put("checked", true);
				} else {
					item.put("checked", false);
				}
			}
			/* END 用于给用户分配部门时，默认选中已分配部门 */
			items.add(item);
		}

		Map<String, Object> attrs = generateQueryResult(page, items);
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}

	@Path("/view") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDepartment(@QueryParam("dep_id") Integer dep_id){
		Department department = departmentService.queryDepartment(dep_id);
		ResponseWithData response = new ResponseWithData(department.toMap());
		return response.toJson();
	}

	@Path("/add") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addDepartment(@FormParam("dep_name") String dep_name,
			@FormParam("dep_level") Integer dep_level, @FormParam("address") String address){
		request = uriInfo.getRequestUri().toString();
		Department department = new Department(dep_level, dep_name, address);
		departmentService.addDepartment(department);
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Add Department Successfully");
		return response.toJson();
	}

	@Path("/update") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addDepartment(@FormParam("dep_id") Integer dep_id, 
			@FormParam("dep_name") String dep_name, @FormParam("dep_level") Integer dep_level, 
			@FormParam("address") String address){
		request = uriInfo.getRequestUri().toString();
		Department department = departmentService.queryDepartment(dep_id);
		department.setDep_level(dep_level);
		department.setDep_name(dep_name);
		department.setAddress(address);
		departmentService.updateDepartment(department);
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Update Department Successfully");
		return response.toJson();
	}

	@Path("/destroy") @POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String deleteDepartment(@FormParam("dep_id") Integer dep_id){
		request = uriInfo.getRequestUri().toString();
		departmentService.deleteDepartment(dep_id);
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Delete Department Successfully");
		return response.toJson();
	}

	@Path("/list") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDepartmentsList(@QueryParam("dep_level") Integer dep_level, 
			@QueryParam("dep_name") String dep_name, @QueryParam("start") Integer start, 
			@QueryParam("itemsPerPage") Integer itemsPerPage){

		// 获取查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		Page page = new Page();
		params.put("page", page);
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);
		if (dep_level != null) params.put("dep_level", dep_level);
		if (dep_name != null) params.put("dep_name", dep_name);

		// 获取数据
		List<Department> deps = departmentService.queryDepartments(params);
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (Department dep : deps) {
			items.add(dep.toMap());
		}

		// 生成返回数据
		Map<String, Object> attrs = generateQueryResult(page, items);
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}
}

