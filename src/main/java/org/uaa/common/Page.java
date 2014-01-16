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

import java.io.Serializable;

/**
 * @author wangjian
 * @create 2013年8月17日 上午10:29:16
 * 
 */
public class Page implements Serializable{

	private static final long serialVersionUID = -6927490839198113549L;
	
	public static final Integer defaultStart = 1;
	public static final Integer defaultItemsPerPage = 10;
	
	private Integer startIndex;
	private Integer itemsPerPage;
	private Integer currentItemCount;
	private Integer totalItems;
	
	public Page() {
		super();
		this.startIndex = defaultStart;
		this.itemsPerPage = defaultItemsPerPage;
	}

	public Page(Integer itemsPerPage, Integer startIndex) {
		this.itemsPerPage = itemsPerPage;
		this.startIndex = startIndex;
	}
	
	public Integer getCurrentItemCount() {
		return currentItemCount;
	}

	public void setCurrentItemCount(Integer currentItemCount) {
		this.currentItemCount = currentItemCount;
	}

	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}

}
