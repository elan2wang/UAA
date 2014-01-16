/**
 * author:	Elan Wang
 * email:	shohokh@gmail.com
 * create:	2013－8-26
 * 
 * this script is cited by index.html
 * 
 */

/**
 * 任务状态转义
 * 
 * @param state
 * @returns {String}
 */
function get_state(state){
	var res_state = "";
	switch(state){
	case "not_start":
		res_state = "未开始";
		break;
	case "ongoing":
		res_state = "进行中";
		break;
	case "finished":
		res_state = "已完成";
		break;
	case "confirmed":
		res_state = "已确认";
		break;
	default:
		res_state = "Unknown";
	break;
	}
	return res_state;
}
/**
 * 周期类型转义
 * 
 * @param periodicity
 * @returns {String}
 */
function get_periodicity(periodicity) {
	var res = "";
	switch(periodicity){
	case "oncely":
		res = "无重复";
		break;
	case "daily":
		res = "每天";
		break;
	case "weekly":
		res = "每周";
		break;
	case "monthly":
		res = "每月";
		break;
	case "yearly":
		res = "每年";
		break;
	default:
		res = "Unknown";
	break;
	}
	return res;
}

/**
 * 出账状态
 * @param state
 * @returns {String}
 */
function get_finance_payments_state(state){
	var payment_state = "";
	switch(state){
	case "new":
		payment_state = "新创建";
		break;
	case "agreed":
		payment_state = "审批通过";
		break;
	case "disagreed":
		payment_state = "审批未通过";
		break;
	case "accountant_payed":
		payment_state = "财务已出账";
		break;
	case "teller_payed":
		payment_state = "出纳已出账";
		break;
	case "closed":
		payment_state = "关闭";
		break;
	default:
		payment_state = "Unknown";
	break;
	}
	return payment_state;
}

/**
 * 出账类型
 * @param pay_type
 * @returns {String}
 */
function get_finance_payments_pay_type(pay_type){
	var payment_pay_type = "";
	switch(pay_type){
	case "cash":
		payment_pay_type = "现金";
		break;
	case "transfer":
		payment_pay_type = "转账";
		break;
	default:
		payment_pay_type = "Unknown";
	break;
	}
	return payment_pay_type;
}

/**
 * 发票状态
 * @param invoice_state
 * @returns {String}
 */
function get_finance_payments_invoice_state(invoice_state){
	var payment_invoice_state = "";
	switch(invoice_state){
	case "open":
		payment_invoice_state = "发票未确认";
		break;
	case "closed":
		payment_invoice_state = "发票已确认";
		break;
	default:
		payment_invoice_state = "Unknown";
	break;
	}
	return payment_invoice_state;
}

/**
 * 消息类型转义
 * 
 * @param msg_type
 */
function get_msg_type(msg_type) {
	var res = "";
	switch(msg_type){
	case "income_msg":
		res = "入帐消息";
		break;
	case "task_msg":
		res = "任务消息";
		break;
	case "payment_msg":
		res = "出帐消息";
		break;
	case "sysadmin_msg":
		res = "系统消息";
		break;
	default:
		res = "Unknown";
	break;
	}
	return res;
}