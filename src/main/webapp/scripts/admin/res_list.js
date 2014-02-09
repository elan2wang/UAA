/**
 * resor:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-21
 * 
 * this script is used by res_list.html
 */ 

// APIs used by this script
var resource_list_api = "/1/resources";
var resource_add_api = "/1/resources/add";
var resource_update_api = "/1/resources/update";
var resource_delete_api = "/1/resources/delete";
var resource_switch_api = "/1/resources/switch";
var resource_view_api = "/1/resources/view";
var mod_list_api = "/1/modules";

$(function(){
	// 页面加载时触发，加载资源列表
	init(resource_list_api);
	
	// 筛选
	$("#btn_res_filter").click(function(){
		var data = get_filter_data();
		init(resource_list_api+"?"+data);
	});
	
	// 点击新增资源按钮时触发，加载二级模块列表
	$("#btn_res_add").click(function(){
		$("#btn_res_add_submit").removeClass("hide");
		$("#btn_res_edit_submit").addClass("hide");
		load_modules(1);
	});
	
	// 新增资源确认时触发，创建新资源
	$("#btn_res_add_submit").click(function(){
		$("#res_add_panel").modal("hide");
		var res_uri = $("#res_uri").val();
		var res_type = $("#res_type").val();
		var res_action = $("#res_action").val();
		var res_description = $("#res_description").val();
		var mod_id = $("#mod_id").val();
		$("#progress-bar").modal("show");
		$.ajax({
			url: resource_add_api,
			type: "post",
			data: "res_uri="+res_uri+"&res_type="+res_type+"&res_action="+res_action
					+"&res_description="+res_description+"&mod_id="+mod_id,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				if(typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
					// 刷新列表
					refresh_tab();
				}
			}
		});
	});
	
	// 修改资源确认时触发，修改资源
	$("#btn_res_edit_submit").click(function(){
		$("#res_add_panel").modal("hide");
		var res_id = $("#res_id").val();
		var res_uri = $("#res_uri").val();
		var res_type = $("#res_type").val();
		var res_description = $("#res_description").val();
		var mod_id = $("#mod_id").val();
		$("#progress-bar").modal("show");
		$.ajax({
			url: resource_update_api,
			type: "post",
			data: "res_uri="+res_uri+"&res_type="+res_type+"&res_description="
				  +res_description+"&mod_id="+mod_id+"&res_id="+res_id,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				if(typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
					// 刷新列表
					refresh_tab();
				}
			}
		});
	});
});

function get_filter_data() {
	var data = '';
	if ($("#filter_res_enable").val() != "") {
		data += "&res_enable="+$("#filter_res_enable").val();
	}
	if ($("#filter_res_type").val() != "") {
		data += "&res_type="+$("#filter_res_type").val();
	}
	if ($("#filter_res_uri").val() != "") {
		data += "&res_uri="+$("#filter_res_uri").val();
	}
	if ($("#filter_res_action").val() != "") {
		data += "&res_action="+$("#filter_res_action").val();
	}
	if (data.length > 0) {
		data = data.substring(1, data.length);
	}
	return data;
}

function init(url) {
	$("#res_list").html(loading);
	$.ajax({
		url: url,
		type: "get",
		data: {},
		dataType: "JSON",
		success: function (result) {
			if(typeof(result.data.result_code) != "undefined"){
				$("#res_list").html('<tr><td colspan="8" style="text-align:center;"><span style="color:red;">'+result.data.result_msg+'</span></td></tr>');
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
					var resources = '';
					for (var i=0; i<items.length; i++) {
						var id = items[i].res_id;
						resources += '<tr><td>'+(i+1)+'</td>';
						resources += '<td>'+items[i].res_uri+'</td>';
						resources += '<td>'+items[i].res_action+'</td>';
						resources += '<td>'+items[i].res_type+'</td>';
						resources += '<td>'+items[i].res_description+'</td>';
						var state, op_info, enable;
						if (items[i].res_enable == true) {
							state = '<i class="icon-ok-circle"></i>';
							op_info = '禁用该资源';
							enable = false;
						} else {
							state = '<i class="icon-ban-circle"></i>';
							op_info = '启用该资源';
							enable = true;
						}
						resources += '<td style="text-align:center;"><a class="edit" title="'+op_info+'" href="javascript:void(0);" onclick="switch_enable('+id+','+enable+');return false;" >'+state+'</a></td>';
						resources += '<td><a class="edit" title="编辑资源信息" href="javascript:void(0);" onclick="edit_res('+id+');return false;" ><i class="icon-pencil"></i></a>  ';
						resources += '<a class="edit" title="删除该资源" href="javascript:void(0);" onclick="delete_res('+id+');return false;" ><i class="icon-trash"></i></a>';
						resources += '</td></tr>';
					}
					$("#res_list").html(resources);
				} else {
					$("#pagination").html("");
					$("#res_list").html(no_data);
				}
			}

		}
	});	
}

/**
 * 删除资源
 * 
 * @param id 资源编号
 */
function delete_res(id) {
	bootbox.confirm("您确定要删除该资源么?", function(result) {
		if (result) {
			$.ajax({
				url: resource_delete_api,
				type: "post",
				data: "res_id="+id,
				dataType: "JSON",
				success: function(result) {
					var result_code = result.data.result_code;
					if (typeof(result_code) != "undefined" && result_code == 10000) {
						bootbox.alert(result.data.result_msg);
						// 刷新列表
						refresh_tab();
					}
				}
			});
		}
	});
}

/**
 * 启用／禁用资源
 * 
 * @param id 资源编号
 * @param enable true启用，false禁用
 */
function switch_enable(id, enable) {
	$.ajax({
		url: resource_switch_api,
		type: "post",
		data: "res_id="+id+"&enable="+enable,
		dataType: "JSON",
		success: function(result) {
			var result_code = result.data.result_code;
			if (typeof(result_code) != "undefined" && result_code == 10000) {
				bootbox.alert(result.data.result_msg);
				// 刷新列表
				refresh_tab();
			}
		}
	});
}

/**
 * 点击编辑按钮触发，加载资源信息填充表单
 * 
 * @param id 资源编号
 */
function edit_res(id) {
	$.ajax({
		url: resource_view_api,
		type: "get",
		data: "res_id="+id,
		dataType: "JSON",
		success: function(result) {
			if(typeof(result.data.res_id) != "undefined"){
				$("#res_add_panel").modal("show");
				$("#btn_res_edit_submit").removeClass("hide");
				$("#btn_res_add_submit").addClass("hide");
				var res = result.data;
				$("#res_id").val(res.res_id);
				$("#res_uri").val(res.res_uri);
				$('#res_action').val(res.res_action);
				$("#res_type").val(res.res_type);
				$("#res_description").val(res.res_description);
				load_modules(1, res.mod_id);
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
