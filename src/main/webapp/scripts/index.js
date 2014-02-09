/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-19
 * 
 * this script is used by index.html
 */ 


//APIs used by this script
var page_init_api = "/1/page_init";
var tabs_init_api = "/1/tabs_init"; 

/**
 * initialize the page
 */
$(function(){
	$.ajax({
		url: page_init_api,
		type:"get",
		data:{},
		dataType:"JSON",
		success:function(result){
			var data = result.data;
			if (typeof(data.result_code) != "undefined") {
				window.location.href = "/login.html";
			} else if (typeof(data.uid) != "undefined") {
				//设置隐藏的用户ID
				$("#uid").html(data.uid);

				//设置顶部导航用户名
				if(data.name.length > 0){
					$("#username").html(data.name);
				}

				//加载导航栏菜单
				load_menu(data.modules);

				//建立web socket连接
				//establish_socket(data.uid);
			}
		}
	});
});

/**
 * BEGIN 页面加载相关 ==========================================================================================
 */

/**
 * 加载导航栏菜单
 * 
 * @param modules 模块对象数组
 */
function load_menu(modules){
	var menu_html = '';
	if(modules.length > 0){
		for(var i=0;i<modules.length;i++){
			var mod_id = modules[i].mod_id;
			var mod_name = modules[i].mod_name;
			var mod_link = modules[i].mod_link;
			if(mod_id && mod_name && mod_link){
				menu_html += '<li ';
				if(i == 0){
					menu_html += 'class="active" ';
				}
				menu_html += 'id="menu'+mod_id+'">';
				menu_html += '<a onfocus="this.blur();" href="javascript:void(0);" onclick="load_tab_list('+mod_id+');return false;">'+mod_name+'</a>';
				menu_html += '</li>';
			}
			// 将消息管理模块的id保存在页面上，在go_to_msg_list()方法中使用
			if(mod_name == "消息管理") {
				$("#msg_mod_id").html(mod_id);
			}
		}
	}
	//append this to menu
	$("#main_menu").html(menu_html);
}


/**
 * 加载模块的子模块tab列表
 * 
 * @param mod_id  模块编号
 */
function load_tab_list(mod_id) {
	$("#main_menu").find("li").removeClass("active");
	$("#menu"+mod_id).addClass("active");

	$.ajax({
		url: tabs_init_api,
		type: "get",
		data: {father_mod: mod_id},
		dateType: "JSON",
		error: function(msg) {
			alert("load tab list failed");
		},
		success: function(result) {
			if(result && typeof(result.data) != "undefined") {
				var data = result.data;
				var tabs = data.tabs;
				var tabs_str = '';
				var init_mod_id = 0;
				var init_mod_link = '';
				for(var i=0; i < tabs.length; i++) {
					var mod_id = tabs[i].mod_id;
					var mod_name = tabs[i].mod_name;
					var mod_link = tabs[i].mod_link;
					if(mod_id && mod_name && mod_link) {
						tabs_str += '<li ';
						if(i == 0) {
							tabs_str += 'class="active" ';
							init_mod_id = mod_id;
							init_mod_link = mod_link;
						}
						tabs_str += 'id="tab'+mod_id+'">';
						tabs_str += '<a href="javascript:void(0);" onfocus="this.blur();" onclick="load_tab_page(\''+mod_id+'\',\''+mod_link+'\');return false;">'+mod_name+'</a>';
						tabs_str += '</li>';
					}
				}
				$("#tab_list").html(tabs_str);
				//加载默认的tab页面
				load_tab_page(init_mod_id, init_mod_link);
			}
		}
	});
}

/**
 * 加载tab页面的内容
 *  
 * @param mod_id tab页面的模块编号
 * @param mod_link tab页面的模块链接
 */
function load_tab_page(mod_id, mod_link) {
	$("#tab_list").find("li").removeClass("active");
	$("#tab"+mod_id).addClass("active");
	$.ajax({
		url: mod_link,
		type: "get",
		data: {},
		dataType: "html",
		error: function(msg) {
			alert("页面加载失败\n");
		},
		success: function(result){
			$("#tab_content").html(result);
		}
	});
}
/**
 * END 页面加载相关 ============================================================================================
 */

/**
 * BEGIN 消息提醒 =============================================================================================
 */

/**
 * 跳转到消息列表页面
 * 
 * @param type
 */
function go_to_msg_list(type) {
	var msg_mod_id = parseInt($("#msg_mod_id").html());

	$("#main_menu").find("li").removeClass("active");
	$("#menu"+msg_mod_id).addClass("active");

	$.ajax({
		url: tabs_init_api,
		type: "get",
		data: {father_mod: msg_mod_id},
		dateType: "JSON",
		error: function(msg) {
			alert("load tab list failed");
		},
		success: function(result) {
			if(result && typeof(result.data) != "undefined") {
				var data = result.data;
				var tabs = data.tabs;
				var tabs_str = '';
				var init_mod_id = 0;
				var init_mod_link = '';
				for(var i=0; i < tabs.length; i++) {
					var mod_id = tabs[i].mod_id;
					var mod_name = tabs[i].mod_name;
					var mod_link = tabs[i].mod_link;
					if(mod_id && mod_name && mod_link) {
						tabs_str += '<li ';
						if(i == 0) {
							tabs_str += 'class="active" ';
							init_mod_id = mod_id;
							init_mod_link = mod_link;
						}
						tabs_str += 'id="tab'+mod_id+'">';
						tabs_str += '<a href="javascript:void(0);" onfocus="this.blur();" onclick="load_tab_page(\''+mod_id+'\',\''+mod_link+'\');return false;">'+mod_name+'</a>';
						tabs_str += '</li>';
					}
				}
				$("#tab_list").html(tabs_str);
				//加载默认的tab页面
				$("#tab_list").find("li").removeClass("active");
				$("#tab"+init_mod_id).addClass("active");
				$.ajax({
					url: init_mod_link,
					type: "get",
					data: {},
					dataType: "html",
					error: function(msg) {
						alert("页面加载失败\n");
					},
					success: function(result){
						$("#tab_content").html(result);
						if (typeof(type) != "undefined") {
							$("#filter_state").val("new");
							$("#filter_msg_type").val(type);
							$("#legend").html("未读"+get_msg_type(type));
						}
					}
				});
			}
		}
	});
}

/**
 * END 消息提醒 ===============================================================================================
 */