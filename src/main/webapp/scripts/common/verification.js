var verifyAttr = {
	number_reg:/^[\d]+$/gi,//验证数字
	lower_reg:/^[a-z]+$/gi,//小写验证
	upper_reg:/^[A-Z]+$/gi,//大写验证
	letter_reg:/^[a-zA-Z]+$/gi,//字母验证
	let_num_reg:/^[A-Za-z0-9]+$/gi,//字母和数字的组合
	email_reg:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/gi,//邮箱验证
	bank_account_reg:/^\d{19}$/gi,//银行账号验证
	vNumber:function(str){//验证是否为数字
		if(verifyAttr.number_reg.test(str)){
			return true;
		}
		return false;
	},
	vIsNull:function(str){//验证字符串是否为空
		if(str.length > 0){
			return true;
		}
		return false;
	},
	vStrMinLen:function(str, minLen){//验证字符串长度最小值
		if(str.length >= minLen){
			return true;
		}
		return false;
	},
	vStrMaxLen:function(str, maxLen){//验证字符串长度最大值
		if(str.length <= maxLen){
			return true;
		}
		return false;
	},
	vStrLetNum:function(str){//验证字母和数字的组合
		if(verifyAttr.let_num_reg.test(str)){
			return true;
		}
		return false;
	},
	vStrEqual:function(args1, args2){//验证是否相等
		if(args1 == args2){
			return true;
		}
		return false;
	},
	vEmail:function(email){//验证邮箱
		if(verifyAttr.email_reg.test(email)){
			return true;
		}
		return false;
	},
	vBankAccount:function(bank_account){//验证银行账号
		if(verifyAttr.bank_account_reg.test(bank_account)){
			return true;
		}
		return false;
	}
};