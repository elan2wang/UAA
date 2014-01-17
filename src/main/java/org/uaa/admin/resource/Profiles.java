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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.uaa.admin.pojo.Profile;
import org.uaa.admin.service.ProfileService;
import org.uaa.common.BaseResource;
import org.uaa.common.ConfigUtil;
import org.uaa.common.http.ResponseWithStatus;
import org.uaa.common.http.ResponseWithData;
import org.uaa.security.core.SecurityContextHolder;

/**
 * @author wangjian
 * @create 2014年1月16日 下午1:42:58
 *
 */
@Path("/profiles")
@Controller
public class Profiles extends BaseResource{
	private static final DateFormat birthDayformat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	@Autowired
	private ProfileService profileService;
	
	private String request = "//////";
	
	@Path("/add") @POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addProfile(@FormParam("real_name") String realname, @FormParam("age") Integer age,
			@FormParam("nationality") String nationality, @FormParam("language") String language, 
			@FormParam("gender") String gender, @FormParam("birthday") String birthday, 
			@FormParam("idtype") String idtype, @FormParam("idnum") String idnum, 
			@FormParam("department") String department, @FormParam("position") String position, 
			@FormParam("address") String address, @FormParam("description") String description) {
		request = uriInfo.getRequestUri().toString();
		Integer uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
		Profile profile = new Profile();
		
		profile.setUser_id(uid);
		if (realname != null && !realname.equals("")) profile.setRealname(realname);
		if (age != null && age != 0) profile.setAge(age);
		if (nationality != null && !nationality.equals("")) profile.setNationality(nationality);
		if (language != null && !language.equals("")) profile.setLanguage(language);
		if (gender != null && !gender.equals("")) profile.setGender(gender);
		if (birthday != null && !birthday.equals("")) {
			try {
				java.sql.Date birth = new java.sql.Date(birthDayformat.parse(birthday).getTime());
				profile.setBirthday(birth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idtype != null && !idtype.equals("")) profile.setIdtype(idtype);
		if (idnum != null && !idnum.equals("")) profile.setIdnum(idnum);
		if (department != null && !department.equals("")) profile.setDepartment(department);
		if (position != null && !position.equals("")) profile.setPosition(position);
		if (address != null && !address.equals("")) profile.setAddress(address);
		if (description != null && !description.equals("")) profile.setDescription(description);
		profile.setLast_modify_person(uid);
		profile.setLast_modify_time(new Timestamp(System.currentTimeMillis()));
		
		profileService.insertProfile(profile);
		
		request = uriInfo.getPath();
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Add Profile Successfully");
		
		return response.toJson();
	}
	
	@Path("/update") @POST
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProfile(@FormParam("real_name") String realname, @FormParam("age") Integer age,
			@FormParam("nationality") String nationality, @FormParam("language") String language, 
			@FormParam("gender") String gender, @FormParam("birthday") String birthday, 
			@FormParam("idtype") String idtype, @FormParam("idnum") String idnum, 
			@FormParam("department") String department, @FormParam("position") String position, 
			@FormParam("address") String address, @FormParam("description") String description) {
		request = uriInfo.getRequestUri().toString();
		Integer uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
		Profile profile = profileService.queryProfile(uid);
		
		if (realname != null && !realname.equals("")) profile.setRealname(realname);
		if (age != null && age != 0) profile.setAge(age);
		if (nationality != null && !nationality.equals("")) profile.setNationality(nationality);
		if (language != null && !language.equals("")) profile.setLanguage(language);
		if (gender != null && !gender.equals("")) profile.setGender(gender);
		if (birthday != null && !birthday.equals("")) {
			try {
				java.sql.Date birth = new java.sql.Date(birthDayformat.parse(birthday).getTime());
				profile.setBirthday(birth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idtype != null && !idtype.equals("")) profile.setIdtype(idtype);
		if (idnum != null && !idnum.equals("")) profile.setIdnum(idnum);
		if (department != null && !department.equals("")) profile.setDepartment(department);
		if (position != null && !position.equals("")) profile.setPosition(position);
		if (address != null && !address.equals("")) profile.setAddress(address);
		if (description != null && !description.equals("")) profile.setDescription(description);
		profile.setLast_modify_person(uid);
		profile.setLast_modify_time(new Timestamp(System.currentTimeMillis()));
		
		profileService.updateProfile(profile);
		
		ResponseWithStatus response = new ResponseWithStatus(request, "10000", "Update Profile Successfully");
		
		return response.toJson();
	}
	
	@Path("/view") @GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getProfile(@QueryParam("user_id") Integer user_id) {
		request = uriInfo.getRequestUri().toString();
		Integer uid;
		if (user_id == null) {
			uid = SecurityContextHolder.getContext().getAuthenticationToken().getUid();
		} else {
			uid = user_id;
		}
		Profile profile = profileService.queryProfile(uid);
		if (profile == null) {
			String request = uriInfo.getRequestUri().toString();
			ResponseWithStatus response = new ResponseWithStatus(request, "20701", ConfigUtil.getValue("20701"));
			return response.toJson();
		}
		
		ResponseWithData response = new ResponseWithData(profile.toMap());
		
		return response.toJson();
	}
}
