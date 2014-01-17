/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-21
 * 
 * this script is used by auth_list.html
 */ 

//APIs used by this script
var auth_list_api = "/1/auths";
var auth_add_api = "/1/auths/add";
var auth_update_api = "/1/auths/update";
var auth_delete_api = "/1/auths/delete";
var auth_view_api = "/1/auths/view";
var auth_switch_api = "/1/auths/switch";
var auth_assign_res_api = "/1/auths/assign_res";
var resource_list_api = "/1/resources";
var resource_list_by_mod_api = "/1/resources/list_by_mod";
var resource_list_by_auth_api = "/1/resources/list_by_auth";
var mod_list_api = "/1/modules";

$(function(){
	// 页面加载时触发，加载权限列表
	init(auth_list_api);

	// 筛选
	$("#btn_auth_filter").click(function(){
		var data = get_filter_data();
		init(auth_list_api+"?"+data);
	});

	// 点击新增权限按钮时触发，加载二级模块列表
	$("#btn_auth_add").click(function(){
		$("#btn_auth_add_submit").removeClass("hide");
		$("#btn_auth_edit_submit").addClass("hide");
		load_modules(2);
	});

	// 新增权限确认时触发，创建新权限
	$("#btn_auth_add_submit").click(function(){
		$("#auth_add_panel").modal("hide");
		var auth_name = $("#auth_name").val();
		var auth_type = $("#auth_type").val();
		var auth_description = $("#auth_description").val();
		var mod_id = $("#mod_id").val();
		$("#progress-bar").modal("show");
		$.ajax({
			url: auth_add_api,
			type: "post",
			data: "auth_name="+auth_name+"&auth_type="+auth_type+"&auth_description="
			+auth_description+"&mod_id="+mod_id,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				if(typeof(result.data.auth_id) != "undefined") {
					bootbox.alert(result.data.result_msg);
					// 刷新Tab
					refresh_tab();
				}
			}
		});
	});

	// 新增权限确认时触发，创建新权限
	$("#btn_auth_edit_submit").click(function(){
		$("#auth_add_panel").modal("hide");
		var auth_id = $("#auth_id").val();
		var auth_name = $("#auth_name").val();
		var auth_type = $("#auth_type").val();
		var auth_description = $("#auth_description").val();
		var mod_id = $("#mod_id").val();
		$("#progress-bar").modal("show");
		$.ajax({
			url: auth_update_api,
			type: "post",
			data: "auth_name="+auth_name+"&auth_type="+auth_type+"&auth_description="
			+auth_description+"&mod_id="+mod_id+"&auth_id="+auth_id,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				if(typeof(result.data.auth_id) != "undefined") {
					bootbox.alert(result.data.result_msg);
					// 刷新Tab
					refresh_tab();
				}
			}
		});
	});

	$("#btn_assign_res_submit").click(function(){
		var auth_id = $("#auth_id").val();
		var resources = '';
		$("input[name='checked_res']:checked").each(function(){
			resources += $(this).val()+",";
		});
		if (resources.length != 0) {
			resources = resources.substr(0, resources.length-1);
		}
		$("#assgin_res_panel").modal("hide");
		$.ajax({
			url: auth_assign_res_api,
			type: "post",
			data: {auth_id :auth_id, resources: resources},
			dataType: "JSON",
			success: function (result) {
				if ( typeof(result.data) != "undefined") {
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
	if ($("#filter_auth_enable").val() != "") {
		data += "&auth_enable="+$("#filter_auth_enable").val();
	}
	if ($("#filter_auth_type").val() != "") {
		data += "&auth_type="+$("#filter_auth_type").val();
	}
	if ($("#filter_auth_name").val() != "") {
		data += "&auth_name="+$("#filter_auth_name").val();
	}
	if (data.length > 0) {
		data = data.substring(1, data.length);
	}
	return data;
}

/**
 * 加载数据列表
 * 
 */
function init(url) {
	$("#auth_list").html(loading);
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
					var auths = '';
					for (var i=0; i<items.length; i++) {
						var id = items[i].auth_id;
						auths += '<tr><td>'+(i+1)+'</td>';
						auths += '<td>'+items[i].auth_name+'</td>';
						auths += '<td>'+items[i].auth_type+'</td>';
						auths += '<td>'+items[i].auth_description+'</td>';
						var state, op_info, enable;
						if (items[i].auth_enable == true) {
							state = '<i class="icon-ok-circle"></i>';
							op_info = '禁用该权限';
							enable = false;
						} else {
							state = '<i class="icon-ban-circle"></i>';
							op_info = '启用该权限';
							enable = true;
						}
						auths += '<td style="text-align:center;"><a class="edit" title="'+op_info+'" href="javascript:void(0);" onclick="switch_enable('+id+','+enable+');return false;" >'+state+'</a></td>';
						auths += '<td><a class="edit" title="编辑权限信息" href="javascript:void(0);" onclick="edit_auth('+id+');return false;" ><i class="icon-pencil"></i></a>  ';
						auths += '<a class="edit" title="删除该权限" href="javascript:void(0);" onclick="delete_auth('+id+');return false;" ><i class="icon-trash"></i></a>  ';
						auths += '<a class="edit" title="分配资源" href="javascript:void(0);" onclick="assign_res('+id+',\''+items[i].auth_name+'\')" data-toggle="modal"><i class="icon-certificate"></i></a>';
						auths += '</td></tr>';
					}
					$("#auth_list").html(auths);
				} else {
					$("#pagination").html("");
					$("#auth_list").html(no_data);
				}
			}	
		}
	});
}


/**
 * 点击分配权限时触发，加载模块列表
 * 
 * @param auth_id
 * @param auth_name
 */
function assign_res(auth_id, auth_name) {
	$("#auth_id").val(auth_id);
	$("#modal_header").html("分配资源["+auth_name+"]");
	$("#assgin_res_panel").modal("show");
	$("#res_list").html("您没有选择任何模块");
	$("#selected_res_list").html(loading);
	// get modules tree
	$.ajax({
		url: mod_list_api,
		type: "get",
		data: {mod_level: 1, itemsPerPage: 10000},
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data) != "undefined" && result.data.items.length != 0){
				var items = result.data.items;
				if (items.length > 0) {
					var modules = '<option value="null">请选择模块</option>';
					for (var i=0; i<items.length; i++) {
						var mod_id = items[i].mod_id;
						var mod_name = items[i].mod_name;
						modules += '<option value="'+mod_id+'">'+mod_name+'</option>';
					}
					$("#modules").html(modules);
				} else {
					$("#modules").html("没有可选模块");
				}
			}
		}
	});
	// get already assigned resources
	$.ajax({
		url: resource_list_by_auth_api,
		type: "get",
		data: {auth_id:auth_id, itemsPerPage:10000},
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data) != "undefined" && result.data.items.length != 0){
				var resources = '';
				var items = result.data.items;
				for(var i=0;i<items.length;i++){
					var id = items[i].res_id;
					resources += '<label class="task-bor" title="'+items[i].res_description+'">';
					resources += '<input checked="checked" name="checked_res" type="checkbox" value="'+id+'" ';
					resources += 'onclick="uncheck(this)" ';
					resources += 'style="margin: -1px 5px 0;">'+items[i].res_action+"@"+items[i].res_uri+'</label>';
				}
				$("#selected_res_list").html(resources);
			} else {
				$("#selected_res_list").html(no_data);
			}
		}
	});
}

/**
 * 获取指定模块的所有资源列表
 * 如果当前权限已拥有该资源，则选中
 * 
 * @param mod_id 模块编号
 */
function mod_change(obj) {
	$("#res_list").html(loading);
	var auth_id = $("#auth_id").val();
	var mod_id = $(obj).val();
	if (mod_id != "null") {
		$.ajax({
			url: resource_list_by_mod_api,
			type:"get",
			data:{mod_id:mod_id, auth_id:auth_id, res_enable:true, itemsPerPage:10000},
			dataType:"JSON",
			success:function(result){
				if(typeof(result.data) != "undefined" && result.data.items.length != 0){
					var resources = '';
					var items = result.data.items;
					for(var i=0;i<items.length;i++){
						var id = items[i].res_id;
						resources += '<label class="task-bor" title="'+items[i].res_description+'">';
						resources += '<input name="res" type="checkbox" value="'+id+'" ';
						if(items[i].checked){
							resources += 'checked="checked" ';
						}
						resources += 'onclick="check(this)" ';
						resources += 'style="margin: -1px 5px 0;">'+items[i].res_action+"@"+items[i].res_uri+'</label>';
					}
					$("#res_list").html(resources);
				} else {
					$("#res_list").html(no_data);
				}
			}
		});
	} else {
		$("#res_list").html("您没有选择任何模块");
	}
}

function check(obj) {
	if ($(obj).prop("checked")) {
		$(obj).attr("name", "checked_res");
		$(obj).attr("onclick", "uncheck(this)");
		var item = $(obj).parent();
		if ($("#selected_res_list").html() == no_data) {
			$("#selected_res_list").html(item);
		} else {
			$("#selected_res_list").append(item);	
		}
	}
}

function uncheck(obj) {
	if (!$(obj).prop("checked")) {
		var item = $(obj).parent();
		$(obj).attr("name", "res");
		$(obj).attr("onclick", "check(this)");
		$("#res_list").append(item);
	}
}

/**
 * 删除权限
 * 
 * @param id 权限编号
 */
function delete_auth(id) {
	bootbox.confirm("您确定要删除该权限么?", function(result) {
		if (result) {
			$.ajax({
				url: auth_delete_api,
				type: "post",
				data: "auth_id="+id,
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
	});
}

/**
 * 启用／禁用权限
 * 
 * @param id 权限编号
 * @param enable true启用，false禁用
 */
function switch_enable(id, enable) {
	$.ajax({
		url: auth_switch_api,
		type: "post",
		data: "auth_id="+id+"&enable="+enable,
		dataType: "JSON",
		success: function(result) {
			var result_code = result.data.result_code;
			if (typeof(result_code) != "undefined" && result_code == 10000) {
				bootbox.alert(result.data.result_msg);
				// 刷新Tab页面
				refresh_tab();
			}
		}
	});
}

/**
 * 点击编辑按钮触发，加载权限信息填充表单
 * 
 * @param id 权限编号
 */
function edit_auth(id) {
	$.ajax({
		url: auth_view_api,
		type: "get",
		data: "auth_id="+id,
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.auth_id) != "undefined"){
				$("#auth_add_panel").modal("show");
				$("#btn_auth_edit_submit").removeClass("hide");
				$("#btn_auth_add_submit").addClass("hide");
				var auth = result.data;
				$("#auth_id").val(auth.auth_id);
				$("#auth_name").val(auth.auth_name);
				$("#auth_type").val(auth.auth_type);
				$("#auth_description").val(auth.auth_description);
				load_modules(2, auth.mod_id);
			}
		}
	});
}

/**
 * 加载模块列表
 * 
 * @param mod_level 模块级别
 * @param my_mod 当前模块编号
 */
function load_modules(mod_level, my_mod) {
	$.ajax({
		url: mod_list_api,
		type: "get",
		data: {mod_level: mod_level, itemsPerPage: 10000},
		dataType: "JSON",
		success: function(result) {
			//有责任人可以添加
			if(typeof(result.data.items) != "undefined" && result.data.items){
				var options = '';
				var items = result.data.items;
				for(var i=0;i<items.length;i++){
					if (my_mod != "undefined" && my_mod == parseInt(items[i].mod_id)) {
						options += '<option selected="selected" value="';
					} else {
						options += '<option value="';
					}
					options += items[i].mod_id;
					options += '">';
					options += items[i].mod_name;
					options += '</option>';
				}
				$("#mod_id").html(options);
			}
		}
	});
}
