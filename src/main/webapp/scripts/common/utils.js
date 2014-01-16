/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－09-29
 * 
 */


/**
 * 获得系统当前时间，格式为: yyyy-MM-dd hh:mm:ss
 * @returns {String}
 */
function getCurTime()
{
	var now=new Date();
	var y=checkTime(now.getFullYear());
	var M=checkTime(now.getMonth()+1);
	var d=checkTime(now.getDate());
	var h=checkTime(now.getHours());
	var m=checkTime(now.getMinutes());
	var s=checkTime(now.getSeconds());
	
	return y+"-"+M+"-"+d+" "+h+":"+m+":"+s;
}

function checkTime(i)
{
	if (i<10)
	{
		i="0" + i;
	}
	return i;
}

/**
 * 刷新当前Tab页面
 */
function refresh_tab() {
	$("#pagination ul li.active a").click();
}