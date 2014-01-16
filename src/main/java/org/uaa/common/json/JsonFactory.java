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
package org.uaa.common.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author wangjian
 * @create 2013年7月30日 下午8:06:59
 * 
 */
public class JsonFactory {
	
	public static String toJson(Map<String, Object> attributes) throws IOException {
		StringWriter out = new StringWriter();
		JsonWriter writer = new JsonWriter(out);
		writeObject(writer, attributes);
		return out.toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void writeObject(JsonWriter out, Map<String, Object> attributes) throws IOException {
		out.beginObject();
		for (Entry<String,Object> entry : attributes.entrySet()){
			out.name(entry.getKey());
			if (entry.getValue() instanceof Map) {
				writeObject(out, (Map) entry.getValue());
			} else if (entry.getValue() instanceof List) {
				write(out, (List) entry.getValue());
			} else {
				out.value(entry.getValue());
			}
 		}
		out.endObject();
	}
	
	@SuppressWarnings("unchecked")
	private static void write(JsonWriter out, List<Object> list) throws IOException {
		out.beginArray();
		
		if (list != null && list.size() != 0) {
			if (list.get(0) instanceof Map) {
				for (Object obj : list) {
					writeObject(out, (Map<String, Object>) obj);
				}
			} else {
				for (Object obj : list) {
					out.value(obj);
				}
			}
		}
		
		out.endArray();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws IOException {
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("apiVersion", "1.0");
		map.put("date", new Date());
		
		List list = new ArrayList<LinkedHashMap<String, Object>>();
		Map<String, Object> person1 = new LinkedHashMap<String, Object>();
		person1.put("name", "张三");
		person1.put("mobile", "18818200000");
		Map<String, Object> person2 = new LinkedHashMap<String, Object>();
		person2.put("name", "李四");
		person2.put("mobile", "18818200001");
		list.add(person1);
		list.add(person2);
		map.put("data", list);
		
		Map<String, Object> module = new LinkedHashMap<String, Object>();
		module.put("mod_id", 1);
		module.put("mod_name", "财务管理");
		module.put("mod_link", "/finacial.html");
		map.put("module", module);
		
		Integer[] aa = {1,2,3,4,5,688888888};
		map.put("ids", Arrays.asList(aa));
		
		map.put("enable", true);
		
		System.out.println(JsonFactory.toJson(map));
	}

}
