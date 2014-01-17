/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－09－28
 * 
 * this script is used by mod_list.html
 */ 

// APIs used by this script
var mod_tree_api = "/1/modules/tree";
var mod_list_api = "/1/modules";
var mod_view_api = "/1/modules/view";
var mod_add_api = "/1/modules/add";
var mod_update_api = "/1/modules/update";
var mod_delete_api = "/1/modules/delete";
var mod_switch_api = "/1/modules/switch";

$(function(){
	//app.js的应用初始化
	App.init();
	
	// 加载模块树
	$.ajax({
		url: mod_tree_api,
		type: "get",
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
						module += '<li><a href="javascript:;" onclick="view_module('+modid+')">';
						module += '<i class="icon-folder-open"></i> <span class="title">'+modname+'</span><span class="arrow"></span></a>';
						if (items[i].subs.length > 0) {
							var subs = items[i].subs;
							module += '<ul class="sub-menu">';
							for (var j=0; j<subs.length; j++) {
								var subid = subs[j].mod_id;
								var subname = subs[j].mod_name;
								module += '<li><a href="javascript:;" onclick="view_module('+subid+')">';
								module += '<i class="icon-folder-close"></i> <span class="title">'+subname+'</span></a></li>';
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
	
	// 添加新模块
	$("#btn_mod_add_submit").click(function(){
		$("#progress-bar").modal("show");
		// 获取表单数据
		var data = $(this.form).serialize();
		$.ajax({
			url: mod_add_api,
			type: "post",
			data: data,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			}
		});
	});
	
	// 编辑模块信息
	$("#btn_mod_edit_submit").click(function(){
		$("#progress-bar").modal("show");
		// 获取表单数据
		var data = $(this.form).serialize();
		$.ajax({
			url: mod_update_api,
			type: "post",
			data: data,
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			}
		});
	});
	
	// 启用/禁用 模块
	$("#btn_mod_switch_submit").click(function(){
		var mod_id = $("#mod_id").val();
		var mod_enable = $("#mod_enable").val();
		$.ajax({
			url: mod_switch_api,
			type: "post",
			data: {mod_id:mod_id, mod_enable:mod_enable},
			dataType: "JSON",
			success: function(result) {
				if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				} else if (typeof(result.data.result_code) != "undefined") {
					bootbox.alert(result.data.result_msg);
				}
			}
		});
	});
	
	// 删除模块
	$("#btn_mod_delete_submit").click(function(){
		var mod_id = $("#mod_id").val();
		var confirm_msg = '';
		if ($("#mod_level").val() == "1") {
			confirm_msg = "您将删除该模块及其所有子模块，您确定删除么";
		} else {
			confirm_msg = "您确定要删除该模块么";
		}
		bootbox.confirm(confirm_msg, function(result){
			if(result) {
				$.ajax({
					url: mod_delete_api,
					type: "post",
					data: {mod_id:mod_id},
					dataType: "JSON",
					success: function(result) {
						if (typeof(result.data.result_code) != "undefined") {
							bootbox.alert(result.data.result_msg);
						} else if (typeof(result.data.result_code) != "undefined") {
							bootbox.alert(result.data.result_msg);
						}
					}
				});
			}
		});
	});
});


/**
 * 模块级别改变时，控制父模块的显隐性
 * @param father_mod
 */
function level_change(father_mod) {
	if ($('#mod_level').val() == "1") {
		$('#father_mod').html('<option value="1">根模块</option>');
	} else if ($('#mod_level').val() == "2"){
		$.ajax({
			url: mod_list_api,
			type: "get",
			data: {mod_level: 1, itemsPerPage: 10000},
			dataType: "JSON",
			success: function(result) {
				var items = result.data.items;
				if (typeof(items) != "undefined" && items.length > 0) {
					var options = '';
					for (var i=0; i<items.length; i++){
						var mod_id = items[i].mod_id;
						var mod_name = items[i].mod_name;
						options += '<option value="'+mod_id+'">'+mod_name+'</option>';
					}
					$('#father_mod').html(options);
					if (typeof(father_mod) != "undefined") {
						$('#father_mod').val(father_mod);
					}
				}
			}
		});
	}
}

/**
 * 查看模块详细信息
 * @param mod_id
 */
function view_module(mod_id) {
	$.ajax({
		url: mod_view_api,
		type: "get",
		data: {mod_id: mod_id},
		dataType: "JSON",
		success: function(result) {
			if (typeof(result.data) != "undefined") {
				var mod=result.data;
				$('#mod_id').val(mod.mod_id);
				$('#mod_enable').val(mod.mod_enable);
				$('#mod_level').val(mod.mod_level);
				level_change(mod.father_mod);
				$('#mod_type').val(mod.mod_type);
				$('#mod_name').val(mod.mod_name);
				$('#mod_link').val(mod.mod_link);
				$('#mod_order').val(mod.mod_order);
				$('#mod_description').val(mod.mod_description);
				// 设置button的显示
				if (mod.mod_enable) {
					$('#btn_mod_switch_submit').html('禁用');
				}
				$('#btn_mod_add_submit').addClass('hide');
				$('#btn_mod_edit_submit').removeClass('hide');
				$('#btn_mod_switch_submit').removeClass('hide');
				$('#btn_mod_delete_submit').removeClass('hide');
			} else if (typeof(result.data.result_code) != "undefined") {
				bootbox.alert(result.data.result_msg);
			}
		}
	});
}

