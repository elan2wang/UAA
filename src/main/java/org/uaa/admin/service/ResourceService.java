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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uaa.admin.persistence.ResourceMapper;
import org.uaa.admin.pojo.Resource;
import org.uaa.common.BaseService;
import org.uaa.common.Page;


/**
 * @author wangjian
 * @create 2014年1月9日 下午4:45:11
 *
 */
@Service
public class ResourceService extends BaseService {

	@Autowired
	private ResourceMapper resourceMapper;
	
	@Transactional
	public void insertResource(Resource res) {
		resourceMapper.insertResource(res);
	}
	
	@Transactional
	public void updateResource(Resource res) {
		resourceMapper.updateResource(res);
	}
	
	@Transactional
	public void deleteResource(Integer res_id) {
		resourceMapper.deleteResource(res_id);
	}
	
	public Resource queryResourceById(Integer res_id) {
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("res_id", res_id);
		
		return resourceMapper.queryResource(params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Resource> queryResources(Map params) {
		List<Resource> list = resourceMapper.queryResources(params);
		Page page = (Page) params.get("page");		
		return paging(list, page);
	}

	/*public void loadResourcesFromRaml(Reader reader) throws IOException {
		List<org.uaa.admin.pojo.Resource> list = new ArrayList<org.uaa.admin.pojo.Resource>();
		List<org.raml.model.Resource> resources = RamlParser.getResources(reader);
		for (org.raml.model.Resource res : resources) {
			org.uaa.admin.pojo.Resource resource = new org.uaa.admin.pojo.Resource();
			resource.setRes_description(res.getDescription());
			resource.setRes_enable(true);
			resource.setRes_type(res.getType());
			resource.setRes_uri(res.getUri());
			list.add(resource);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourceList", list);
		resourceMapper.insertResourceBatch(params);
	}*/

}
