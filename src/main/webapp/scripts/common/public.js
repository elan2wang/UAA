var myDialog = {
	minWidth: 200,//最小宽度
	minHeight: 100,//最小高度
	has_title: 0,//是否有标题
	hide_title_class: "hide-dialog-title",
	title: "",//标题
	content: "",//内容
	selector: "",//弹框选择器(请用ID)
	set_attrs:function(data){
		for(var proName in data){
			if(typeof(myDialog[proName]) != "undefined"){
				myDialog[proName] = data[proName];
			}
		}
	},
	show_dialog:function(data){
		myDialog.set_attrs(data);
		if(myDialog.has_title == 1){
			myDialog.hide_title_class = "";
		}
		$("#"+myDialog.selector).html(myDialog.content);
		$("#"+myDialog.selector).dialog({
			title:myDialog.title,
			dialogClass: myDialog.hide_title_class,
			draggable: false,
			modal: true,
			minHeight: myDialog.minHeight,
			minWidth: myDialog.minWidth,
			closeOnEscape:false
		});
	}
};

//选择当前列所有复选框
/*
 * @param {object} dcm		顶部复选框
 * @param {string} child_name	列表复选框name
 * @returns
 */
function check_all(dcm, child_name){
	$("input[name='"+child_name+"']").prop("checked",dcm.checked);
}
//检查当前列复选框是否全部选中
/*
 * 
 * @param {string} child_name	列表复选框name
 * @param {strign} parent_id	顶部复选框id
 * @returns
 */
function verify_check_all(child_name, parent_id){
	var is_all_checked = false;
	var all_items = $("input[name='"+child_name+"']");
	var check_items = $("input[name='"+child_name+"']:checked");
	if(check_items.length === all_items.length){
		is_all_checked = true;
	}
	$("#"+parent_id).prop("checked",is_all_checked);
}
//显示和隐藏弹框右边操作面板内容
function hide_show_conetent(operator){
	var hide_show_obj = $(operator).closest(".portlet").find(".portlet-body");
	$(hide_show_obj).slideToggle("normal",function(){
		if($(operator).hasClass("collapse")){
			$(operator).removeClass("collapse").addClass("expand");
		}
		else if($(operator).hasClass("expand")){
			$(operator).removeClass("expand").addClass("collapse");
		}
	});
}
//获取查询条件
/*
 * @param {object} params	条件条目
 * @returns
 */
function get_search_condition(params){
	if(typeof(params.conditions) != "undefined" && params.conditions){
		var condition_str = "";
		var conditions = params.conditions;
		for(var i=0;i<conditions.length;i++){
			if(conditions[i].length > 0){
				var value_id = conditions[i];
				var condition_value = $("#search_"+value_id).val();
				if(condition_value.length > 0){
					condition_str += "&"+value_id+"="+condition_value;
				}
			}
		}
		if(condition_str.length > 0){
			condition_str = condition_str.substr(1);
		}
		return condition_str;
	}
	return "";
}
