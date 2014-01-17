function checkUsername(){
	$(".username_group").removeClass("error");
	$("#msg-tip").html("");
	var username = $("#username").val();
	if(!verifyAttr.vIsNull(username)){//验证用户名是否为空
		$(".username_group").addClass("error");
		$("#username_help").html("enter your username");
		return false;
	}
	if(!verifyAttr.vStrMaxLen(username, 20)){//验证用户名是否大于20位
		$(".username_group").addClass("error");
		$("#username_help").html("username is less than 20 characters");
		return false;
	}
	$(".username_group").removeClass("error");
	$("#msg-tip").html("");
	return true;
}
function checkPassword(){
	$(".password_group").removeClass("error");
	$("#msg-tip").html("");
	var password = $("#password").val();
	if(!verifyAttr.vIsNull(password)){//验证密码是否为空
		$(".password_group").addClass("error");
		$("#password_help").html("enter your passwords");
		return false;
	}
	if(!verifyAttr.vStrMinLen(password, 6)){//验证密码是否小于8位
		$(".password_group").addClass("error");
		$("#password_help").html("password is longer than 6 characters");
		return false;
	}
	if(!verifyAttr.vStrMaxLen(password, 20)){//验证密码是否大于20位
		$(".password_group").addClass("error");
		$("#password_help").html("password is less than 20 characters");
		return false;
	}
	$(".password_group").removeClass("error");
	$("#msg-tip").html("");
	return true;
}
function removeErrorTip(attr){
	$("."+attr+"_group").removeClass("error");
	$("#"+attr+"_help").html("");
}
function goLogin(){
	$(".res_msg").addClass("hide");
	if(checkUsername() && checkPassword()){//密码和用户名都验证通过
		myDialog.show_dialog({
			content:'<img alt="Sign in" src="images/loading.gif" />Sign in...',
			minHeight:26,
			selector:'waiting_login'
		});
		var username = $("#username").val();
		var password = md5($("#password").val());
		$.ajax({
			url:"/1/login",//登录接口
			data:{username:username,password:password},
			type:"post",
			dataType: "json",
			success:function(result){
				$("#waiting_login").dialog('close');
				if(typeof(result.error_code) != 'undefined'){
					$(".res_msg").removeClass("hide");
					$(".res_msg span").html(result.error_msg);
				}
				else if(typeof(result.data.result_code) != 'undefined' && result.data.result_code == "10000"){
					window.location.href = "/index.html";
				}
				else{
					$(".res_msg").removeClass("hide");
					$(".res_msg span").html("系统错误！");
				}
			},
			error:function(){
				
			}
		});
	}
}