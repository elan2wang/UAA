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
import java.sql.Timestamp;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wangjian
 * @create 2014年1月16日 下午2:33:56
 * @update TODO
 * 
 * 
 */
public class Profile implements Serializable {

	private static final long serialVersionUID = 1991158475114447084L;
	
	private Integer profile_id;
	private Integer user_id;
	private String nationality;
	private String language;
	private String gender;
	private Integer age;
	private String avatar;
	private Date birthday;
	private String realname;
	private String idtype;
	private String idnum;
	private String address;
	private String position;
	private String department;
	private String description;
	private Timestamp last_modify_time;
	private Integer last_modify_person;
	
	public Profile() {
		super();
	}

	public Profile(Integer profile_id, Integer user_id, String nationality,
			String language, String gender, Integer age, String avatar,
			Date birthday, String realname, String idtype, String idnum,
			String address, String position, String department,
			String description, Timestamp last_modify_time,
			Integer last_modify_person) {
		super();
		this.profile_id = profile_id;
		this.user_id = user_id;
		this.nationality = nationality;
		this.language = language;
		this.gender = gender;
		this.age = age;
		this.avatar = avatar;
		this.birthday = birthday;
		this.realname = realname;
		this.idtype = idtype;
		this.idnum = idnum;
		this.address = address;
		this.position = position;
		this.department = department;
		this.description = description;
		this.last_modify_time = last_modify_time;
		this.last_modify_person = last_modify_person;
	}

	public Integer getProfile_id() {
		return profile_id;
	}

	public void setProfile_id(Integer profile_id) {
		this.profile_id = profile_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(Timestamp last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public Integer getLast_modify_person() {
		return last_modify_person;
	}

	public void setLast_modify_person(Integer last_modify_person) {
		this.last_modify_person = last_modify_person;
	}

	@Override
	public String toString() {
		return "Profile [profile_id=" + profile_id + ", user_id="
				+ user_id + ", nationality=" + nationality + ", language="
				+ language + ", gender=" + gender + ", age=" + age
				+ ", avatar=" + avatar + ", birthday=" + birthday
				+ ", realname=" + realname + ", idtype=" + idtype + ", idnum="
				+ idnum + ", address=" + address + ", position=" + position
				+ ", department=" + department + ", description=" + description
				+ ", last_modify_time=" + last_modify_time
				+ ", last_modify_person=" + last_modify_person + "]";
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		map.put("profile_id", profile_id);
		map.put("user_id", user_id);
		map.put("nationality", nationality);
		map.put("language", language);
		map.put("gender", gender);
		map.put("age", age);
		map.put("avatar", avatar);
		map.put("birthday", birthday);
		map.put("realname", realname);
		map.put("idtype", idtype);
		map.put("idnum", idnum);
		map.put("address", address);
		map.put("position", position);
		map.put("department", department);
		map.put("description", description);
		map.put("last_modify_time", last_modify_time);
		map.put("last_modify_person", last_modify_person);
		
		return map;
		
	}
	
}
