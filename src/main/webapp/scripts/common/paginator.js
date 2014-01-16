/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-26
 * 
 * this script is cited by index.html
 * all the list page need to invoke this function to set pagination
 * 
 */

/**
 * 该方法必须在列表页面的init(url)方法中调用
 * 
 */
function set_pagination(currentPage, totalPages, itemsPerPage, url){
	var options = {
			currentPage: currentPage,
			totalPages: totalPages,
			numberOfPages:10,
			itemTexts: function (type, page, current) {
				switch (type) {
				case "first":
					return "首页";
				case "prev":
					return "前一页";
				case "next":
					return "下一页";
				case "last":
					return "末页";
				case "page":
					return page;
				}
			},
			tooltipTitles: function (type, page, current) {
				switch (type) {
				case "first":
					return "跳转到首页";
				case "prev":
					return "跳转到上一页";
				case "next":
					return "跳转到下一页";
				case "last":
					return "跳转到最后一页";
				case "page":
					return "跳转到第"+page+"页";
				}
			},
			itemContainerClass: function (type, page, current) {
				return (page == current) ? "active" : "pointer-cursor";
			},
			pageUrl: function(type, page, current){
				var start = (page - 1) * itemsPerPage + 1;
				var current_url = '';
				if (url.indexOf("start") != -1) {
					current_url += url.replace(/start=[\d]+/gi,"start="+start);
				} else if (url.indexOf("?") == -1){
					current_url += url+"?start="+start;
				} else {
					current_url += url+"&start="+start;
				}
				return current_url;
			},
			onPageClicked: function(e,originalEvent,type,page){
				var current_url = "";
				switch(type){
				case "first":
					current_url = $("#pagination ul li a[title='跳转到首页']").prop("href");
					break;
				case "prev":
					current_url = $("#pagination ul li a[title='跳转到上一页']").prop("href");
					break;
				case "next":
					current_url = $("#pagination ul li a[title='跳转到下一页']").prop("href");
					break;
				case "last":
					current_url = $("#pagination ul li a[title='跳转到最后一页']").prop("href");
					break;
				case "page":
					current_url = $("#pagination ul li a[title='跳转到第"+page+"页']").prop("href");
					break;
				}
				originalEvent.preventDefault();
				init(current_url);
			}
	};
	$('#pagination').bootstrapPaginator(options);
}