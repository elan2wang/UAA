/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-21
 * 
 * this script is used by user_list.html
 */ 

// APIs used by this script
var department_list_api = "/1/departments";
var role_list_api = "/1/roles";
var user_list_api = "/1/users";
var user_assign_dep_api = "/1/users/assign_dep";
var user_assign_role_api = "/1/users/assign_role";
var user_add_api = "/1/users/add";
var user_update_api = "/1/users/update";
var user_delete_api = "/1/users/delete";
var user_switch_api = "/1/users/switch";
var user_view_api = "/1/users/view";
var user_reset_psd_api = "/1/users/reset";

$(function(){
	// 页面加载时触发，加载帐号列表
	init(user_list_api);

	// 筛选
	$("#btn_user_filter").click(function(){
		var data = get_filter_data();
		init(user_list_api+"?"+data);
	});
	
	//  点击新增帐号按钮时触发，加载部门列表
	$("#btn_user_add").click(function(){		
		$("#btn_user_add_submit").removeClass("hide");
		$("#btn_user_edit_submit").addClass("hide");
		load_departments("department");
	});

	// 新增帐号确认时触发，创建新帐号
	$("#btn_user_add_submit").click(function(){
		var username = $("#user_name").val();
		var email = $("#email").val();
		var mobile = $("#mobile").val();
		var department = $("#department").val();
		var password = md5("123456");
		$("#progress-bar").modal("show");
		$.ajax({
			url: user_add_api,
			type: "post",
			data: "username="+username+"&email="+email+"&mobile="+mobile+"&department="+
				  department+"&password="+password,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				$("#user_add_panel").modal("hide");
				var result_code = result.data.result_code;
				if(typeof(result_code) != "undefined" && parseInt(result_code) == 10000) {
					bootbox.alert(result.data.result_msg);
					// 刷新Tab
					refresh_tab();
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			}
		});
	});

	// 编辑帐号确认时触发，更新帐号
	$("#btn_user_edit_submit").click(function(){
		var user_id = $("#user_id").val();
		var username = $("#user_name").val();
		var email = $("#email").val();
		var mobile = $("#mobile").val();
		var department = $("#department").val();
		$("#progress-bar").modal("show");
		$.ajax({
			url: user_update_api,
			type: "post",
			data: "user_id="+user_id+"&username="+username+"&email="+email+"&mobile="
				  +mobile+"&department="+department,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				var result_code = result.data.result_code;
				if(typeof(result_code) != "undefined" && parseInt(result_code) == 10000) {
					$("#user_add_panel").modal("hide");
					bootbox.alert(result.data.result_msg);
					// 刷新Tab
					refresh_tab();
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			}
		});
	});
	
	// 分配角色时触发，保存
	$("#btn_save_role").click(function(){
		var roles = "";
		$("input[name='role[]']:checked").each(function(){
			roles += $(this).val()+",";
		});
		roles = roles.substr(0, roles.length-1);
		var user_id = $("#user_id").val();
		$.ajax({
			url: user_assign_role_api,
			type: "post",
			data: "user_id="+user_id+"&roles="+roles,
			dataType: "JSON",
			success: function(result) {
				var result_code = result.data.result_code;
				if (typeof(result_code) != "undefined" && result_code == 10000) {
					bootbox.alert(result.data.result_msg);
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			} 
		});
	});

	// 分配部门时触发，保存
	$("#btn_save_dep").click(function(){
		var deps = "";
		$("input[name='dep[]']:checked").each(function(){
			deps += $(this).val()+",";
		});
		deps = deps.substr(0, deps.length-1);
		var user_id = $("#user_id").val();
		$.ajax({
			url: user_assign_dep_api,
			type: "post",
			data: "user_id="+user_id+"&deps="+deps,
			dataType: "JSON",
			success: function(result) {
				var result_code = result.data.result_code;
				if (typeof(result_code) != "undefined" && result_code == 10000) {
					bootbox.alert(result.data.result_msg);
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			} 
		});
	});
	
});

function get_filter_data() {
	var data = '';
	if ($("#filter_user_enable").val() != "") {
		data += "&user_enable="+$("#filter_user_enable").val();
	}
	if ($("#filter_dep_id").val() != "") {
		data += "&dep_id="+$("#filter_dep_id").val();
	}
	if ($("#filter_username").val() != "") {
		data += "&username="+$("#filter_username").val();
	}
	if (data.length > 0) {
		data = data.substring(1, data.length);
	}
	return data;
}

function init(url) {
	$("#user_list").html(loading);
	var selected_dep = $("#filter_dep_id").val();
	load_departments("filter_dep_id", selected_dep);
	$.ajax({
		url: url,
		type: "get",
		data: {},
		dataType: "JSON",
		success: function (result) {
			if(typeof(result.data.result_code) != "undefined"){
				$("#auth_list").html('<tr><td colspan="8" style="text-align:center;"><span style="color:red;">'+result.data.result_msg+'</span></td></tr>');
			}
			else if(typeof(result.data) != "undefined" && result.data){
				var data = result.data;
				var currentItemCount = data.currentItemCount;
				if (currentItemCount > 0) {
					// 设置分页信息
					var itemsPerPage = data.itemsPerPage;
					var currentPage = Math.ceil(data.startIndex/data.itemsPerPage);
					var totalPages = Math.ceil(data.totalItems/data.itemsPerPage);
					set_pagination(currentPage, totalPages, itemsPerPage, data.nextLink);
					// 封装列表信息
					var items = data.items;
					var users = '';
					for (var i=0; i<items.length; i++) {
						var id = items[i].user_id;
						var username = items[i].username;
						users += '<tr><td>'+(i+1)+'</td>';
						users += '<td>'+username+'</td>';
						users += '<td>'+items[i].email+'</td>';
						users += '<td>'+items[i].mobile+'</td>';
						users += '<td>'+items[i].department+'</td>';
						var state, op_info, enable;
						if (items[i].user_enable == true) {
							state = '<i class="icon-ok-circle"></i>';
							op_info = '禁用该用户';
							enable = false;
						} else {
							state = '<i class="icon-ban-circle"></i>';
							op_info = '启用该用户';
							enable = true;
						}
						users += '<td style="text-align:center;"><a class="edit" title="'+op_info+'" href="javascript:void(0);" onclick="switch_enable('+id+','+enable+');return false;" >'+state+'</a></td>';
						users += '<td><a class="edit" title="编辑用户信息" href="javascript:void(0);" onclick="edit_user('+id+');return false;" ><i class="icon-pencil"></i></a>  ';
						users += '<a class="edit" title="查看用户档案" href="javascript:void(0);" onclick="view('+id+');return false;" ><i class="icon-eye-open"></i></a>  ';
						users += '<a class="edit" title="重置用户密码" href="javascript:void(0);" onclick="reset_psd('+id+',\''+username+'\');return false;" ><i class="icon-key"></i></a>  ';
						users += '<a class="edit" title="删除该用户" href="javascript:void(0);" onclick="delete_user('+id+');return false;" ><i class="icon-trash"></i></a>  ';
						users += '<a class="edit" title="角色和部门分配" config" href="javascript:void(0);" onclick="assing_role_dep('+id+',\''+items[i].username+'\');return false;" data-toggle="modal"><i class="icon-certificate"></i></a>';
						users += '</td></tr>';
					}
					$("#user_list").html(users);
				} else {
					$("#pagination").html("");
					$("#user_list").html(no_data);
				}
			}
		}
	});
}

/**
 * 添加和编辑帐号时加载部门列表
 */
function load_departments(tag_id, my_dep) {
	$.ajax({
		url: department_list_api,
		type: "get",
		data: {itemsPerPage: 10000},
		dataType: "JSON",
		success: function(result) {
			//有责任人可以添加
			if(typeof(result.data.items) != "undefined" && result.data.items){
				var options = '';
				var items = result.data.items;
				for(var i=0;i<items.length;i++){
					if (my_dep != "undefined" && my_dep == items[i].dep_id) {
						options += '<option selected="selected" value="';
					} else {
						options += '<option value="';
					}
					options += items[i].dep_id;
					options += '">';
					options += items[i].dep_name;
					options += '</option>';
				}
				$("#"+tag_id).html(options);
				if (tag_id == "filter_dep_id") {
					$("#filter_dep_id").prepend('<option value="">所有部门</option>');
				}
			}
		}
	});
}

/**
 * 编辑帐号信息
 * 
 * @param id
 */
function edit_user(id) {
	$.ajax({
		url: user_view_api,
		type: "get",
		data: "user_id="+id,
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.user_id) != "undefined"){
				$("#user_add_panel").modal("show");
				$("#btn_user_edit_submit").removeClass("hide");
				$("#btn_user_add_submit").addClass("hide");
				var user = result.data;
				$("#user_id").val(user.user_id);
				$("#user_name").val(user.username);
				$("#email").val(user.email);
				$("#mobile").val(user.mobile);
				load_departments("department", user.dep_id);
			}
		}
	});
}

/**
 * 查看用户的个人详细信息
 * 
 * @param id 帐号编号
 */
function view(id) {
	$.get("/admin/profile.html",{},function(result){
		$("#profile_panel").html(result);
		$.ajax({
			url: view_profile_api,
			type: "get",
			data: {user_id: id},
			dataType: "JSON",
			success: function(result) {
				if (typeof(result.data.result_code) != "undefined") {
					if (result.data.result_code == 20701) {
						bootbox.alert("该用户尚未填写个人信息!");
					} else {
						bootbox.alert(result.data.result_msg);
					}
					return;
				} else if (typeof(result.data.profile_id) != "undefined"){
					var profile = result.data;
					$("#realname").val(profile.realname);
					$("#age").val(profile.age);
					$("#nationality").val(profile.nationality);
					$("#language").val(profile.language);
					$("#gender").val(profile.gender);
					$("#birthday").val(profile.birthday);
					$("#idtype").val(profile.idtype);
					$("#idnum").val(profile.idnum);
					$("#department").val(profile.department);
					$("#position").val(profile.position);
					$("#address").val(profile.address);
					$("#description").val(profile.description);
					
					$("#btn_add_profile_submit").addClass("hide");
					$("#btn_edit_profile_submit").addClass("hide");
					$("#profile_panel").find("input").attr("disabled", true);
					$("#profile_panel").find("textarea").attr("disabled", true);
					$("#profile_panel").find("select").attr("disabled", true);
					$("#profile_panel").modal("show");
				}
			}
		});
	});
}

/**
 * 删除帐号
 * 
 * @param id 待删除帐号编号
 */
function delete_user(id) {
	bootbox.confirm("您确定要删除该用户么?", function(result) {
		if (result) {
			$.ajax({
				url: user_delete_api,
				type: "post",
				data: {user_id: id},
				dataType: "JSON",
				success: function(result) {
					var result_code = result.data.result_code;
					if (typeof(result_code) != "undefined" && result_code == 10000) {
						bootbox.alert(result.data.result_msg);
						// 刷新Tab
						refresh_tab();
					} else if (typeof(result.data.result_code) != "undefined") {
						bootbox.alert(result.data.result_msg);
					}
				}
			});
		}
	});
}

/**
 * 密码重置
 * 
 * @param id 帐号编号
 */
function reset_psd(id, username) {
	bootbox.confirm("您确定要重置["+username+"]的密码么?", function(result) {
		if (result) {
			$.ajax({
				url: user_reset_psd_api,
				type: "post",
				data: {user_id: id},
				dataType: "JSON",
				success: function(result) {
					var result_code = result.data.result_code;
					if (typeof(result_code) != "undefined" && result_code == 10000) {
						bootbox.alert(result.data.result_msg);
					} else if (typeof(result.data.result_code) != "undefined") {
						bootbox.alert(result.data.result_msg);
					}
				}
			});
		}
	});
}

/**
 * 启用／禁用帐号
 * 
 * @param id 帐号编号
 * @param enable true表示启用帐号，false表示禁用帐号
 */
function switch_enable(id, enable) {
	$.ajax({
		url: user_switch_api,
		type: "post",
		data: "user_id="+id+"&enable="+enable,
		dataType: "JSON",
		success: function(result) {
			var result_code = result.data.result_code;
			if (typeof(result_code) != "undefined" && result_code == 10000) {
				bootbox.alert(result.data.result_msg);
				// 刷新Tab
				refresh_tab();
			}
		}
	});
}

/**
 * 点击“分配角色和部门”时触发，加载角色和部门列表
 *  
 * @param id 帐号的编号
 */
function assing_role_dep(id, username) {
	$("#role_dep_assign_panel_header").html("分配部门和角色"+"["+username+"]");
	$("#role_dep_assign_panel").modal();
	$("#user_id").val(id);
	$.ajax({
		url: role_list_api,
		type: "get",
		data: {user_id: id, itemsPerPage: 10000},
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.items) != "undefined" && result.data.items){
				//<label class="task-bor"><input type="checkbox" value="1" style="margin: -1px 5px 0;">门店经理</label>
				var roles1 = '', roles2 = '', roles3 ='';
				var items = result.data.items;
				for (var i=0; i<items.length; i++) {
					if (parseInt(items[i].role_level) == 1) {
						roles1 += '<label class="task-bor"><input type="checkbox" name="role[]" value="';
						roles1 += items[i].role_id+'" ';
						if (items[i].checked) {
							roles1 += 'checked="checked" ';
						}
						roles1 += 'style="margin: -1px 5px 0;">';
						roles1 += items[i].role_name;
						roles1 += '</label>';
					} else if (parseInt(items[i].role_level) == 2) {
						roles2 += '<label class="task-bor"><input type="checkbox" name="role[]" value="';
						roles2 += items[i].role_id+'" ';
						if (items[i].checked) {
							roles2 += 'checked="checked" ';
						}
						roles2 += 'style="margin: -1px 5px 0;">';
						roles2 += items[i].role_name;
						roles2 += '</label>';
					} else if (parseInt(items[i].role_level) == 3) {
						roles3 += '<label class="task-bor"><input type="checkbox" name="role[]" value="';
						roles3 += items[i].role_id+'" ';
						if (items[i].checked) {
							roles3 += 'checked="checked" ';
						}
						roles3 += 'style="margin: -1px 5px 0;">';
						roles3 += items[i].role_name;
						roles3 += '</label>';
					}
				}
				$("#role_list1").html(roles1);
				$("#role_list2").html(roles2);
				$("#role_list3").html(roles3);
			}
		}
	});
	$.ajax({
		url: department_list_api,
		type: "get",
		data: {user_id: id, itemsPerPage: 10000},
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.items) != "undefined" && result.data.items){
				var deps1 = '', deps2 = '', deps3 ='';
				var items = result.data.items;
				for (var i=0; i<items.length; i++) {
					if (parseInt(items[i].dep_level) == 1) {
						deps1 += '<label class="task-bor"><input type="checkbox" name="dep[]" value="';
						deps1 += items[i].dep_id+'" ';
						if (items[i].checked) {
							deps1 += 'checked="checked" ';
						}
						deps1 += 'style="margin: -1px 5px 0;">';
						deps1 += items[i].dep_name;
						deps1 += '</label>';
					} else if (parseInt(items[i].dep_level) == 2) {
						deps2 += '<label class="task-bor"><input type="checkbox" name="dep[]" value="';
						deps2 += items[i].dep_id+'" ';
						if (items[i].checked) {
							deps2 += 'checked="checked" ';
						}
						deps2 += 'style="margin: -1px 5px 0;">';
						deps2 += items[i].dep_name;
						deps2 += '</label>';
					} else if (parseInt(items[i].dep_level) == 3) {
						deps3 += '<label class="task-bor"><input type="checkbox" name="dep[]" value="';
						deps3 += items[i].dep_id+'" ';
						if (items[i].checked) {
							deps3 += 'checked="checked" ';
						}
						deps3 += 'style="margin: -1px 5px 0;">';
						deps3 += items[i].dep_name;
						deps3 += '</label>';
					}
				}
				$("#dep_list1").html(deps1);
				$("#dep_list2").html(deps2);
				$("#dep_list3").html(deps3);
			}
		}
	});
}
