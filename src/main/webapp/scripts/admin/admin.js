/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-29
 * 
 * this script is used by index.html
 */ 

// APIs used by this script
var change_password_api = "/1/users/password";
var view_profile_api = "/1/profiles/view";
var add_profile_api = "/1/profiles/add";
var update_profile_api = "/1/profiles/update";
var logout_api = "/1/logout";
	
/**
 * BEGIN 用户退出系统 ==========================================================================================
 */
function logout() {
	$.get("/1/logout",{},function(result){
		window.location.href = "/login.html";
	});
}

/**
 * BEGIN 密码修改相关 ==========================================================================================
 */

/**
 * 显示密码修改的面板
 */
function change_password() {
	$.get("/admin/repassd.html",{},function(result){
		$("#re_passowrd_panel").html(result);
		$("#re_passowrd_panel").modal("show");
	});
}

/**
 * 提交密码修改请求
 */
function change_password_submit() {
	var new_psd = $("#new_password").val();
	var confirm_psd = $("#confirm_password").val();
	if (confirm_psd != new_psd) {
		bootbox.alert("新密码与确认新密码不一致，请确认后提交");
		return;
	}
	var old_psd = md5($("#origin_password").val());
	data = "old_psd="+old_psd+"&new_psd="+md5(new_psd);
	$.ajax({
		url: change_password_api,
		type: "post",
		data: data,
		dataType: "JSON",
		success: function(result) {
			if (typeof(result.data) != "undefined") {
				$("#re_passowrd_panel").modal("hide");
				bootbox.alert(result.data.result_msg);
				$.ajax({
					url: logout_api,
					type: "get",
					data: {},
					dataType: "JSON",
					success: function(result) {
						window.location.href = "/login.html";
					},
					error: function(result) {
						window.location.href = "/login.html";
					}
				});
			} else if (typeof(result.data.result_code) != "undefined") {
				bootbox.alert(result.data.result_msg);
				return;
			}
		},
		error: function(result) {
			bootbox.alert("密码修改请求提交失败!");
		}
	});
	
}

/**
 * END 密码修改相关 ===========================================================================================
 */


/**
 * BEGIN 个人档案相关 ==========================================================================================
 */

/**
 * 查看个人档案
 */
function view_profile(uid) {
	$.get("/admin/profile.html",{},function(result){
		$("#profile_panel").html(result);
		$.ajax({
			url: view_profile_api,
			type: "get",
			data: {user_id: uid},
			dataType: "JSON",
			success: function(result) {
				if (typeof(result.data.result_code) != "undefined" && result.data.result_code == "20701") {
					$("#btn_add_profile_submit").removeClass("hide");
					$("#btn_edit_profile_submit").addClass("hide");
				} else if (typeof(result.data.profile_id) != "undefined"){
					var profile = result.data;
					$("#btn_add_profile_submit").addClass("hide");
					$("#btn_edit_profile_submit").removeClass("hide");
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
				}
				$("#profile_panel").modal("show");
			}
		});
	});
}

/**
 * 添加个人档案
 */
function add_profile_submit(obj) {
	var data = $(obj.form).serialize();
	$.ajax({
		url: add_profile_api,
		type: "post",
		data: data,
		dataType: "JSON",
		success: function(result) {
			if (typeof(result.data.result_code) != "undefined") {
				bootbox.alert("档案信息添加成功");
			} else if (typeof(result.data.result_code) != "undefined") {
				bootbox.alert(result.data.result_msg);
			}
		},
		error: function(result) {
			bootbox.alert("请求提交失败");
		}
	});
}

/**
 * 编辑个人档案
 */
function edit_profile_submit(obj) {
	var data = $(obj.form).serialize();
	$.ajax({
		url: update_profile_api,
		type: "post",
		data: data,
		dataType: "JSON",
		success: function(result) {
			if (typeof(result.data.result_code) != "undefined") {
				bootbox.alert("档案信息修改成功");
			} else if (typeof(result.data.result_code) != "undefined") {
				bootbox.alert(result.data.result_msg);
			}
		},
		error: function(result) {
			bootbox.alert("请求提交失败");
		}
	});
}
/**
 * BEGIN 个人档案相关 ==========================================================================================
 */