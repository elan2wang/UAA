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
package org.uaa.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.uaa.admin.pojo.User;
import org.uaa.admin.pojo.Department;
import org.uaa.admin.service.UserService;
import org.uaa.admin.service.DepartmentService;
import org.uaa.common.Page;

/**
 * @author wangjian
 * @create 2013年8月22日 下午10:06:15
 * 
 */
public class BaseResource {

	@Context
	protected UriInfo uriInfo;
	
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> generateQueryResult(Page page, List list) {
		StringBuilder previousLink = new StringBuilder();
		StringBuilder nextLink = new StringBuilder();
		String path = uriInfo.getPath();
		previousLink.append("/1/"+path+"?");
		nextLink.append("/1/"+path+"?");
		Boolean exist_start = false;
		Map queryParameters = uriInfo.getQueryParameters();
		Iterator it = queryParameters.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry)it.next();
			String key = entry.getKey().toString();
			String value = entry.getValue().toString().replace("[", "").replace("]", "");
			if(key.equalsIgnoreCase("start")){
				exist_start = true;
				if(Integer.parseInt(value) - page.getCurrentItemCount() <= 0){
					previousLink.append("start=1&");
				}else{
					previousLink.append("start=" + (page.getStartIndex() - page.getItemsPerPage()) + "&");
				}
				if(page.getStartIndex() + page.getCurrentItemCount() >= page.getTotalItems()){
					nextLink.append("start=" + page.getStartIndex() + "&");
				}else{
					nextLink.append("start=" + (page.getStartIndex() + page.getItemsPerPage()) + "&");
				}
			}else{
				previousLink.append(key + "=" + value+ "&");
				nextLink.append(key + "=" + value+ "&");
			}	
		}
		if(!exist_start){
			if(page.getItemsPerPage() < page.getTotalItems()){
				nextLink.append("start=" + (page.getItemsPerPage()+1));
			}
		}
		if (nextLink.charAt(nextLink.length()-1) == '&' || nextLink.charAt(nextLink.length()-1) == '?')
			nextLink.deleteCharAt(nextLink.length()-1);
		if (previousLink.charAt(previousLink.length()-1) == '&' || previousLink.charAt(previousLink.length()-1) == '?')
			previousLink.deleteCharAt(previousLink.length()-1);
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("currentItemCount", page.getCurrentItemCount());
		map.put("itemsPerPage", page.getItemsPerPage());
		map.put("startIndex", page.getStartIndex());
		map.put("totalItems", page.getTotalItems());
		map.put("nextLink", nextLink);
		map.put("previousLink", previousLink);
		map.put("items", list);
		return map;
	}
	
	/**
	 * 获得所有部门编号和部门名称的Map对象
	 * 
	 * @return Map<Integer,String>
	 */
	protected Map<Integer, String> getDepMap(DepartmentService departmentService) {
		List<Department> depList = departmentService.queryDepartments(new HashMap<String, Object>());
		Map<Integer, String> depMap = new HashMap<Integer, String>();
		for (Department dep : depList) {
			depMap.put(dep.getDep_id(), dep.getDep_name());
		}
		return depMap;
	}

	/**
	 * 获得所有帐号编号和名称的Map对象
	 * 
	 * @return Map<Integer,String>
	 */
	protected Map<Integer, String> getUserMap(UserService accountService) {
		Map<String, Object> params = new HashMap<String, Object>();
		Page page = new Page(100000, 1);
		params.put("page", page);
		List<User> accountList = accountService.queryUsers(params);
		Map<Integer, String> accountMap = new HashMap<Integer, String>();
		for (User account : accountList) {
			accountMap.put(account.getUser_id(), account.getUsername());
		}
		return accountMap;
	}
}
