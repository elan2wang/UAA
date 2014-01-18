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
package org.uaa.admin.raml;

/**
 * @author wangjian
 * @create 2014年1月9日 下午12:11:16
 *
 */
public class RamlParser {
	/*public static List<Resource> getResources(Reader reader) throws IOException {
		List<Resource> list = new ArrayList<Resource>();
		
		Raml raml = new RamlDocumentBuilder().build(reader);
		enumerateResources(list, raml.getResources());
		reader.close();
		
		return list;
	}

	public static List<Action> getActions(Resource resource) {
		List<Action> list = new ArrayList<Action>();
		Map<ActionType, Action> actions = resource.getActions();  
		Iterator<Entry<ActionType, Action>> iter = actions.entrySet().iterator();
		while(iter.hasNext()) {
			Action action = iter.next().getValue();
			list.add(action);
		}
		return list;
	}

	private static void enumerateResources(List<Resource> list, Map<String, Resource> resources) {
		Iterator<Entry<String, Resource>> iter = resources.entrySet().iterator();
		while (iter.hasNext()) {
			Resource resource = iter.next().getValue();
			list.add(resource);
			if (resource.getResources() != null) {
				enumerateResources(list, resource.getResources());
			}
		}
	}

	public static void main(String[] args) {
		FileReader ramlBuffer;
		try {
			ramlBuffer = new FileReader(new File("uaa.raml"));
			List<Resource> resources = getResources(ramlBuffer);

			for (Resource res : resources) {
				List<Action> actions = getActions(res);
				for (Action action : actions) {
					System.out.println(res.getUri()+"\t\t"+action.getType());
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/}
