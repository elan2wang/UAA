/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-26
 * 
 * this script is used by index.html
 */ 

//加载列表时的loading图片
var loading = '<tr><td colspan="20" style="text-align:center;">' +
			  '<img alt="正在加载" src="images/loading.gif" />正在加载列表，请稍等...</td></tr>';

//加载列表时，没有符合条件的数据
var no_data = '<tr><td colspan="20" style="text-align:center;">没有符合条件的数据</td></tr>';

//日历添加中文
(function($){
	$.fn.datetimepicker.dates['zh-CN'] = {
			days:["星期日","星期一","星期二","星期三","星期四","星期五","星期六","星期日"],
			daysShort:["周日","周一","周二","周三","周四","周五","周六","周日"],
			daysMin:["日","一","二","三","四","五","六","日"],
			months:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
			monthsShort:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
			today:"今日",
			clear: "清空",
			suffix:[],
			meridiem:[]
	};
})(jQuery);
