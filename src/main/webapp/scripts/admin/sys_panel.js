$(function(){
	$("#btn_reload_security_meta_source").click(function(){
		$("#progress-bar").modal("show");
		$.ajax({
			url: "/1/reload_security_meta_source",
			type: "get",
			data: {},
			dataType: "JSON",
			success: function(result) {
				$("#progress-bar").modal("hide");
				bootbox.alert("重载成功");
			},
			error: function(result) {
				$("progress-bar").modal("hide");
				bootbox.alert("重载失败");
			}
		});
	});
});