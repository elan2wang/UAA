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
package org.uaa.security.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.uaa.admin.pojo.Resource;
import org.uaa.security.SecurityService;


/**
 * @author wangjian
 * @create 2014年1月8日 下午3:17:40
 *
 */
@Component
public class SecurityMetadataSource {

	private static Logger log = LoggerFactory.getLogger(SecurityMetadataSource.class);
	
	private SecurityService securityService;

	/**
	 * key : resource link
	 * value: role list
	 * 
	 * store all the private resources and their needed roles
	 */
	private Map<String, List<Integer>> nonPublicResourcesMap = null;
	private List<String> publicResources = null;
	
	@Autowired
	public SecurityMetadataSource(SecurityService securityService) {
		this.securityService = securityService;
		loadDefinedResource();
	}

	/**
	 * invoked in constructor to load all the resources and their need roles
	 * and store them in the HashMap
	 * 
	 */
	private void loadDefinedResource() {
		// fill in resourceMap
		List<Resource> non_public_res_list = securityService.queryNonPublicResources();
		nonPublicResourcesMap = new HashMap<String, List<Integer>>();
		if(non_public_res_list == null) return;

		for(Resource res : non_public_res_list) {
			String res_uri = res.getRes_uri();
			String res_action = res.getRes_action();
			List<Integer> roleList = securityService.queryNeededRoles(res_uri, res_action);
			if (roleList == null || roleList.size() == 0) {
				log.info("haven't assigned roles who can access this resource:"+res_action+"@"+res_uri+", please check.");
				continue;
			}
			nonPublicResourcesMap.put(res_action+"@"+res_uri, roleList);
		}
		
		// fill in publicResources
		publicResources = new ArrayList<String>();
		List<Resource> public_res_list = securityService.queryPublicResources();
		for(Resource res : public_res_list) {
			String res_uri = res.getRes_uri();
			String res_action = res.getRes_action();
			publicResources.add(res_action+"@"+res_uri);
		}
	}

	/**
	 * Invoked when the RBAC configuration changed
	 *
	 */
	public void reloadDefinedResource() {
		loadDefinedResource();
	}

	/**
	 * get the needed roleId list of the specific resource
	 * represented by the request URI
	 *
	 * @param uri the request URI
	 * @param action the request action (GET, POST, PUT, DETELE)
	 * @return List<String> roleId list
	 */
	public List<Integer> getNeededRoles(String uri, String action) {
		StringBuffer sb = new StringBuffer();
		sb.append(action+"@");
		if(uri.indexOf("?") != -1) {
			sb.append(uri.split("?")[0]);
		} else {
			sb.append(uri);
		}
		
		
		if (nonPublicResourcesMap == null) {
			return null;
		}
		return nonPublicResourcesMap.get(sb.toString());
	}

	public boolean isPublic(String uri, String action) {
		StringBuffer sb = new StringBuffer();
		sb.append(action+"@");
		if(uri.indexOf("?") != -1) {
			sb.append(uri.split("?")[0]);
		} else {
			sb.append(uri);
		}
		
		return publicResources.contains(sb.toString());
	}
}