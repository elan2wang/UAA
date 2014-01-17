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
package org.uaa.admin.pojo;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangjian
 * @create 2014年1月16日 下午9:40:51
 *
 */
public class Apilog implements Serializable {

	private static final long serialVersionUID = -1190935279799584532L;

	private Integer log_id;
	private Integer uid;
	private String res_uri;
	private String res_action;
	private String addr;
	private String client;
	private Long request_time;
	private Long response_time;
	private Long time;
	
	public Apilog() {
		super();
	}
	
	public Apilog(Integer log_id, Integer uid, String res_uri,
			String res_action, String addr, String client, Long request_time,
			Long response_time, Long time) {
		super();
		this.log_id = log_id;
		this.uid = uid;
		this.res_uri = res_uri;
		this.res_action = res_action;
		this.addr = addr;
		this.client = client;
		this.request_time = request_time;
		this.response_time = response_time;
		this.time = time;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("log_id", log_id);
		map.put("uid", uid);
		map.put("res_uri", res_uri);
		map.put("res_action", res_action);
		map.put("addr", addr);
		map.put("client", client);
		map.put("request_time", request_time);
		map.put("response_time", response_time);
		map.put("time", time);
		
		return map;
	}
	
	@Override
	public String toString() {
		return "Apilog [log_id=" + log_id + ", uid=" + uid + ", res_uri="
				+ res_uri + ", res_action=" + res_action + ", addr=" + addr
				+ ", client=" + client + ", request_time=" + request_time + ", response_time="
				+ response_time + ", time=" + time + "]";
	}

	public Integer getLog_id() {
		return log_id;
	}
	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getRes_uri() {
		return res_uri;
	}
	public void setRes_uri(String res_uri) {
		this.res_uri = res_uri;
	}
	public String getRes_action() {
		return res_action;
	}
	public void setRes_action(String res_action) {
		this.res_action = res_action;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public Long getRequest_time() {
		return request_time;
	}
	public void setRequest_time(Long request_time) {
		this.request_time = request_time;
	}
	public Long getResponse_time() {
		return response_time;
	}
	public void setResponse_time(Long response_time) {
		this.response_time = response_time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getTime() {
		return time;
	}
}
