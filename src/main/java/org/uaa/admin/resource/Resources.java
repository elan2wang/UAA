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

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import org.uaa.admin.service.ResourceService;
import org.uaa.common.BaseResource;
import org.uaa.admin.pojo.AuthorityResource;
import org.uaa.admin.pojo.Resource;
import org.uaa.admin.service.AuthorityResourceService;
import org.uaa.common.ConfigUtil;
import org.uaa.common.Page;
import org.uaa.common.http.ResponseWithStatus;
import org.uaa.common.http.ResponseWithData;


/**
 * @author wangjian
 * @create 2014年1月9日 下午4:19:01
 *
 */
@Path("/resources")
@Controller
public class Resources extends BaseResource {
	private static Logger log = LoggerFactory.getLogger(Resources.class);

	@Autowired
	private ResourceService resourceService;
	@Autowired
	private AuthorityResourceService authorityResourceService;

	private String request = "//////";
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String list(@QueryParam("mod_id") Integer mod_id,
			@QueryParam("res_type") String res_type, @QueryParam("res_uri") String res_uri,
			@QueryParam("res_action") String res_action, @QueryParam("res_enable") Boolean res_enable,
			@QueryParam("start") Integer start, @QueryParam("itemsPerPage") Integer itemsPerPage) {
		// 获取参数
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Page page = new Page();
		params.put("page", page);
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);
		if (res_enable != null) params.put("res_enable", res_enable);
		if (mod_id != null && mod_id != 0) params.put("mod_id", mod_id);
		if (res_type != null && !res_type.equals("")) params.put("res_type", res_type);
		if (res_action != null && !res_action.equals("")) params.put("res_action", res_action);
		if (res_uri != null && !res_uri.equals("")) params.put("res_uri", res_uri);

		// 获取数据		
		List<Resource> list = resourceService.queryResources(params);
		List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
		for (Resource res : list) resObjList.add(res.toMap());

		// 生成返回数据
		Map<String, Object> attrs = generateQueryResult(page, resObjList);
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}

	@GET @Path("/list_by_mod")
	@Produces(MediaType.APPLICATION_JSON)
	public String list(@QueryParam("mod_id") Integer mod_id, @QueryParam("auth_id") Integer auth_id,
			@QueryParam("start") Integer start, @QueryParam("itemsPerPage") Integer itemsPerPage,
			@QueryParam("res_enable") Boolean res_enable) {
		// 获取参数
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		Page page = new Page();
		params.put("page", page);
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);
		if (mod_id != null && mod_id != 0) params.put("mod_id", mod_id);
		if (res_enable != null) params.put("res_enable", res_enable);

		List<Integer> resList = new ArrayList<Integer>();
		if (auth_id != null) {
			List<AuthorityResource> myList = authorityResourceService.queryAuthorityResourcesByAuth(auth_id);
			if (myList != null) {
				for (AuthorityResource ar : myList) {
					resList.add(ar.getRes_id());
				}
			}
		}

		// 获取数据		
		List<Resource> list = resourceService.queryResources(params);
		List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
		for (Resource res : list) {
			Map<String, Object> obj = res.toMap();
			if (resList.contains(res.getRes_id())) {
				obj.put("checked", true);
			} else {
				obj.put("checked", false);
			}
			resObjList.add(obj);
		}
		
		// 生成返回数据
		Map<String, Object> attrs = generateQueryResult(page, resObjList);
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}

	@GET @Path("/list_by_auth")
	@Produces(MediaType.APPLICATION_JSON)
	public String getResourcesByAuth(@QueryParam("auth_id") Integer auth_id,
			@QueryParam("start") Integer start, @QueryParam("itemsPerPage") Integer itemsPerPage) {
		request = uriInfo.getRequestUri().toString();
		if (auth_id == null || auth_id ==0) {
			String error_msg = String.format(ConfigUtil.getValue("10010"), "auth_id");
			ResponseWithStatus response = new ResponseWithStatus(request, "10010", error_msg);
			return response.toJson();
		}

		Page page = new Page();
		if (start != null) page.setStartIndex(start);
		if (itemsPerPage != null) page.setItemsPerPage(itemsPerPage);

		List<Resource> list = authorityResourceService.queryResourcesByAuth(auth_id, page);
		
		List<Map<String, Object>> resObjList = new ArrayList<Map<String, Object>>();
		for (Resource res : list) resObjList.add(res.toMap());

		// 生成返回数据
		Map<String, Object> attrs = generateQueryResult(page, resObjList);
		ResponseWithData response = new ResponseWithData(attrs);
		return response.toJson();
	}

	@GET @Path("/view")
	@Produces(MediaType.APPLICATION_JSON)
	public String getResource(@QueryParam("res_id") Integer res_id) {
		request = uriInfo.getRequestUri().toString();
		Resource res = resourceService.queryResourceById(res_id);

		if (res == null) {
			String request = uriInfo.getAbsolutePath().toString();
			ResponseWithStatus response = new ResponseWithStatus(request, "20401", ConfigUtil.getValue("20401"));
			return response.toJson();
		}

		ResponseWithData response = new ResponseWithData(res.toMap());
		return response.toJson();
	}

	@POST @Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String addResource(@FormParam("res_uri") String res_uri, @FormParam("res_action") String res_action,
			@FormParam("res_type") String res_type, @FormParam("res_description") String res_description,
			@FormParam("mod_id") Integer mod_id) {
		request = uriInfo.getRequestUri().toString();
		Resource res = new Resource();
		res.setRes_description(res_description);
		res.setRes_action(res_action);
		res.setRes_enable(true);
		res.setRes_uri(res_uri);
		res.setRes_type(res_type);
		res.setMod_id(mod_id);

		resourceService.insertResource(res);

		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Add Resource Successfully");
		return response.toJson();
	}

	@POST @Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String updateResource(@FormParam("res_id") Integer res_id, @FormParam("res_uri") String res_uri,
			@FormParam("res_type") String res_type, @FormParam("res_description") String res_description,
			@FormParam("mod_id") Integer mod_id) {
		request = uriInfo.getRequestUri().toString();
		Resource res = resourceService.queryResourceById(res_id);
		if (res == null) {
			String request = uriInfo.getAbsolutePath().toString();
			ResponseWithStatus response = new ResponseWithStatus(request, "20401", ConfigUtil.getValue("20401"));
			return response.toJson();
		}

		res.setRes_description(res_description);
		res.setRes_uri(res_uri);
		res.setRes_type(res_type);
		log.debug("mod_id    "+mod_id);
		res.setMod_id(mod_id);
		resourceService.updateResource(res);

		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Update Resource Successfully");
		return response.toJson();
	}

	@Path("/delete")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteResource(@FormParam("res_id") Integer res_id) {
		Resource res = resourceService.queryResourceById(res_id);
		if (res == null) {
			String request = uriInfo.getAbsolutePath().toString();
			ResponseWithStatus response = new ResponseWithStatus(request, "20401", ConfigUtil.getValue("20401"));
			return response.toJson();
		}

		resourceService.deleteResource(res_id);

		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Delete Resource Successfully");
		return response.toJson();
	}

	@Path("/switch")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String switch_enable(@FormParam("res_id") Integer res_id, @FormParam("enable") Boolean enable) {
		request = uriInfo.getRequestUri().toString();
		Resource res = resourceService.queryResourceById(res_id);
		if (res == null) {
			String request = uriInfo.getAbsolutePath().toString();
			ResponseWithStatus response = new ResponseWithStatus(request, "20401", ConfigUtil.getValue("20401"));
			return response.toJson();
		}
		res.setRes_enable(enable);
		resourceService.updateResource(res);

		ResponseWithStatus response;
		if (enable) {
			response = new ResponseWithStatus(request, "10000", "Enable Resource Successfully");
		} else {
			response = new ResponseWithStatus(request, "10000", "Disable Resource Successfully");
		}
		return response.toJson();
	}
}
