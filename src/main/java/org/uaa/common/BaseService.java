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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangjian
 * @create 2013年8月22日 下午10:21:35
 * 
 */
public class BaseService {

	private static Logger log = LoggerFactory.getLogger(BaseService.class);
	
	@SuppressWarnings("rawtypes")
	protected List paging(List list, Page page) {
		Integer start = page.getStartIndex();
		Integer itemsPerPage = page.getItemsPerPage();
		log.debug("start="+start+", itemsPerPage="+itemsPerPage);
		Integer end;
		if (start+itemsPerPage-1 > list.size()) {
			end = list.size();
		}  else {
			end = start+itemsPerPage-1;
		}
		page.setCurrentItemCount(end-start+1);
		page.setTotalItems(list.size());
		
		return list.subList(start-1, end);
	}

}
