/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-21
 * 
 * this script is used by role_list.html
 */ 

// APIs used by this script
var role_list_api = "/1/roles";
var role_update_api = "/1/roles/update";
var role_add_api = "/1/roles/add";
var role_delete_api = "/1/roles/delete";
var role_switch_api = "/1/roles/switch";
var role_view_api = "/1/roles/view";
var role_assign_auth_api = "/1/roles/assign_auth";
var mod_tree_api = "/1/modules/tree";
var auth_list_api = "/1/auths";

$(function(){
	// 页面加载时触发，加载角色列表
	init(role_list_api);
	
	// 筛选
	$("#btn_role_filter").click(function(){
		var data = get_filter_data();
		init(role_list_api+"?"+data);
	});
	
	$("#btn_role_add").click(function(){
		$("#btn_role_add_submit").removeClass("hide");
		$("#btn_role_edit_submit").addClass("hide");
	});
	
	// 提交创建的请求，创建新角色
	$("#btn_role_add_submit").click(function(){
		$("#role_add_panel").modal("hide");
		var role_name = $("#role_name").val();
		var role_level = $("#role_level").val();
		var role_description = encodeURI($("#role_description").val());
		var role_type = $("#role_type").val();
		$.ajax({
			url: role_add_api,
			type: "post",
			data: "role_name="+role_name+"&role_level="+role_level+"&role_description="
				  +role_description+"&role_type="+role_type,
			dataType: "JSON",
			success: function(result) {
				var result_code = result.data.result_code;
				if(typeof(result_code) != "undefined" && parseInt(result_code) == 10000) {
					bootbox.alert(result.data.result_msg);
					// 刷新tab
					refresh_tab();
				}
			}
		});
	});
	
	// 提交修改角色信息请求，更新角色
	$("#btn_role_edit_submit").click(function(){
		$("#role_add_panel").modal("hide");
		var role_id = $("#role_id").val();
		var role_name = $("#role_name").val();
		var role_level = $("#role_level").val();
		var role_description = $("#role_description").val();
		var role_type = $("#role_type").val();
		$.ajax({
			url: role_update_api,
			type: "post",
			data: "role_id="+role_id+"&role_name="+role_name+"&role_level="+role_level+"&role_description="
				  +role_description+"&role_type="+role_type,
			dataType: "JSON",
			success: function(result) {
				var result_code = result.data.result_code;
				if(typeof(result_code) != "undefined" && parseInt(result_code) == 10000) {
					bootbox.alert(result.data.result_msg);
					// 刷新tab
					refresh_tab();
				}
			}
		});
	});
	
	// 分配权限时提交
	$("#assign_auth_submit").click(function(){
		var role_id = $("#role_id").val();
		var mod_id = $("#mod_id").val();
		var auth_ids = '';
		$("input[name='auth']:checked").each(function(){
			auth_ids += $(this).val()+",";
		});
		if (auth_ids.length > 0) {
			auth_ids = auth_ids.substr(0, auth_ids.length-1);
		}
		// 保存角色的权限信息
		$.ajax({
			url: role_assign_auth_api,
			type: "post",
			data: {role_id: role_id, mod_id: mod_id, auth_ids: auth_ids},
			dataType: "JSON",
			success: function(result) {
				if (typeof(result.data) != "undefined") {
					bootbox.alert(result.data.result_msg);
					if (auth_ids == "") {
						$("#sub_mod"+mod_id).removeAttr("checked");
					} else {
						$("#sub_mod"+mod_id).prop("checked", "checked");
					}
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			}
		});
	});
	
	App.init();//app.js的应用初始化
});

function get_filter_data() {
	var data = '';
	if ($("#filter_role_enable").val() != "") {
		data += "&role_enable="+$("#filter_role_enable").val();
	}
	if ($("#filter_role_name").val() != "") {
		data += "&role_name="+$("#filter_role_name").val();
	}
	if (data.length > 0) {
		data = data.substring(1, data.length);
	}
	return data;
}

/**
 * 加载数据列表
 * 
 * @param url 请求API
 */
function init(url) {
	$("#role_list").html(loading);
	$.ajax({
		url: url,
		type: "get",
		data: {},
		dataType: "JSON",
		success: function (result) {
			if(typeof(result.data.result_code) != "undefined"){
				$("#role_list").html('<tr><td colspan="8" style="text-align:center;"><span style="color:red;">'+result.data.result_msg+'</span></td></tr>');
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
					var roles = '';
					for (var i=0; i<items.length; i++) {
						var id = items[i].role_id;
						roles += '<tr><td>'+(i+1)+'</td>';
						roles += '<td>'+items[i].role_name+'</td>';
						roles += '<td>'+items[i].role_level+'</td>';
						roles += '<td>'+items[i].role_type+'</td>';
						roles += '<td>'+items[i].role_description+'</td>';
						var state, op_info, enable;
						if (items[i].role_enable == true) {
							state = '<i class="icon-ok-circle"></i>';
							op_info = '禁用该角色';
							enable = false;
						} else {
							state = '<i class="icon-ban-circle"></i>';
							op_info = '启用该角色';
							enable = true;
						}
						roles += '<td style="text-align:center;"><a class="edit" title="'+op_info+'" href="javascript:void(0);" onclick="switch_enable('+id+','+enable+');return false;" >'+state+'</a></td>';
						roles += '<td><a class="edit" title="编辑角色信息" href="javascript:void(0);" onclick="edit_role('+id+');return false;" ><i class="icon-pencil"></i></a>  ';
						roles += '<a class="edit" title="删除角色信息" href="javascript:void(0);" onclick="delete_role('+id+');return false;" ><i class="icon-trash"></i></a>  ';
						roles += '<a class="edit" title="权限分配" href="javascript:void(0);" onclick="assign_auth('+id+',\''+items[i].role_name+'\');return false;"><i class="icon-certificate"></i></a>';
						roles += '</td></tr>';
					}
					$("#role_list").html(roles);
				} else {
					$("#pagination").html("");
					$("#role_list").html(no_data);
				}
			}
		}
	});
}

/**
 * 点击分配权限时触发，加载模块树形结构
 * 
 * @param id 角色编号
 */
function assign_auth(role_id, role_name) {	
	$("#assign_auth_panel_header").html("权限分配["+role_name+"]");
	$("#assign_auth_panel").modal("show");
	$("#role_id").val(role_id);
	$("#auth_list").html("您没有选中任何模块");
	// get modules tree
	$.ajax({
		url: mod_tree_api,
		type: "get",
		data: {role_id: role_id},
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.items) != "undefined" && result.data.items){
				var items = result.data.items;
				if (items.length > 0) {
					var tree = '';
					for (var i=0; i<items.length; i++) {
						var module = '';
						var modid = items[i].mod_id;
						var modname = items[i].mod_name;
						module += '<li><a href="javascript:;"><input type="checkbox" disabled="disabled" ';
						if (items[i].checked) {
							module += 'checked="checked" ';
						}
						module += 'id="module'+modid+'" value="'+modid+'" >'+modname+'<span class="arrow"></span></a>';
						if (items[i].subs.length > 0) {
							var subs = items[i].subs;
							module += '<ul class="sub-menu">';
							for (var j=0; j<subs.length; j++) {
								var subid = subs[j].mod_id;
								var subname = subs[j].mod_name;
								module += '<li><a href="javascript:;" onclick="get_auth_list('+subid+')"><input type="checkbox" disabled="disabled" ';
								if (subs[j].checked) {
									module += 'checked="checked" ';
								}
								module += 'id="sub_mod'+subid+'" value="'+subid+'">'+subname+'<span class="arrow"></span></a></li>';
							}
							module += '</ul>';
						}
						module += '</li>';
						tree += module;
					}
					$("#modules").html(tree);
				} else {
					$("#modules").html("没有可分配的模块");
				}
			}
		}
	});
}

/**
 * 获取指定模块的权限列表
 * 
 * @param mod_id 模块编号
 */
function get_auth_list(mod_id) {
	$("#auth_list").html(loading);
	$("#mod_id").val(mod_id);
	$("#assign_auth_submit").addClass("hide");
	var role_id = $("#role_id").val();
	$.ajax({
		url: auth_list_api,
		type: "get",
		data: {itemsPerPage: 10000, mod_id: mod_id, auth_enable: true, role_id: role_id},
		dataType: "JSON",
		success: function(result) {
			if (typeof(result.data.items) != "undefined") {
				var count = result.data.currentItemCount;
				var auths = '';
				if (count > 0) {
					var items = result.data.items;
					for (var i=0; i<count; i++) {
						auths += '<label class="task-bor" title="'+items[i].auth_description+'">';
						auths += '<input name="auth" type="checkbox" value="'+items[i].auth_id+'" ';
						if(items[i].checked){
							auths += 'checked="checked" ';
						}
						auths += 'style="margin: -1px 5px 0;">'+items[i].auth_name+'</label>';
					}
					$("#assign_auth_submit").removeClass("hide");
				} else {
					auths += "没有符合条件的权限";
				}
				$("#auth_list").html(auths);
			}
		}
	});
}

/**
 * 启用／禁用角色
 * 
 * @param id 角色编号
 * @param enable true表示启用帐号，false表示禁用帐号
 */
function switch_enable(id, enable) {
	$.ajax({
		url: role_switch_api,
		type: "post",
		data: "role_id="+id+"&enable="+enable,
		dataType: "JSON",
		success: function(result) {
			var result_code = result.data.result_code;
			if (typeof(result_code) != "undefined" && result_code == 10000) {
				bootbox.alert(result.data.result_msg);
				// 刷新tab
				refresh_tab();
			}
		}
	});
}

/**
 * 删除角色
 * 
 * @param id 角色编号
 */
function delete_role(id) {
	bootbox.confirm("您确定要删除该角色么?", function(result) {
		if (result) {
			$.ajax({
				url: role_delete_api,
				type: "post",
				data: "role_id="+id,
				dataType: "JSON",
				success: function(result) {
					var result_code = result.data.result_code;
					if (typeof(result_code) != "undefined" && result_code == 10000) {
						bootbox.alert(result.data.result_msg);
						// 刷新tab
						refresh_tab();
					}
				}
			});
		}
	});
}

/**
 * 点击编辑按钮触发，获取角色信息
 * 
 * @param id 角色编号
 */
function edit_role(id) {
	$.ajax({
		url: role_view_api,
		type: "get",
		data: "role_id="+id,
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.role_id) != "undefined"){
				$("#role_add_panel").modal("show");
				$("#btn_role_edit_submit").removeClass("hide");
				$("#btn_role_add_submit").addClass("hide");
				var role = result.data;
				$("#role_id").val(role.role_id);
				$("#role_name").val(role.role_name);
				$("#role_level").val(role.role_level);
				$("#role_description").val(role.role_description);
				$("#role_type").val(role.role_type);
			}
		}
	});
}
