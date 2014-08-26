/*
 * niiDateFmChk 日期格式驗證,需為 0 或 yyyy/MM/dd 或 yyyyMMdd, 此需套用jQuery validator
 * 此為特例，允許日期輸入0
 * @param value 
 * @param element
 * @param arg
 */
function niiDateFmChk0(value, element, arg) {
	if (value == 0) {
		return true;
	} else {
		return niiDateFmChk(value, element, arg);
	}
}

/*
 * niiDateFmChk 日期格式驗證,需為yyyy/MM/dd 或 yyyyMMdd, 此需套用jQuery validator
 * @param value 
 * @param element
 * @param arg
 */
function niiDateFmChk(value, element, arg){
	var re = new RegExp("^([0-9]{4})[/]?([0-9]{2})[/]?([0-9]{2})$"); 
	if ( re.test(value) == true ) {        //match
		var dateStr = value.replace(/\/+/g, '');
		var accDate = new Date(dateStr.substring(0,4), dateStr.substring(4,6) - 1, dateStr.substring(6)); 
		var tempDate = accDate.getFullYear() + 
			( (accDate.getMonth() < 9)?"0":"" ) + (accDate.getMonth() + 1 ) + 
			( (accDate.getDate() < 10)?"0":""  ) + accDate.getDate();
		var newFmtDate = accDate.getFullYear() + "/" 	+ 
			( (accDate.getMonth() < 9)?"0":"" ) + (accDate.getMonth() + 1 ) + "/" + 
			( (accDate.getDate() < 10)?"0":""  ) + accDate.getDate();
		return (dateStr == tempDate)?( jQuery(element).val(newFmtDate) ):false;
	} else {
		return this.optional(element);
	}
}

/*
 * niiDateFmChkWithToday需為yyyy/MM/dd 或 yyyyMMdd, 不得超過系統日判斷
 * @param value 
 * @param element
 * @param arg
 */
function niiDateFmChkWithToday(value, element, arg){
		
	var validate = false;
	
	var dateStr = value.replace(/\/+/g, '');
	
	var accDate = new Date(dateStr.substring(0,4), dateStr.substring(4,6) - 1, dateStr.substring(6));
	
	var sysDate = new Date();
	
	//alert(accDate.getYear() + " " + accDate.getMonth() + " " + accDate.getDate());
	//alert(sysDate.getYear() + " " + sysDate.getMonth() + " " + sysDate.getDate());
				
	if(accDate.getYear() > sysDate.getYear()){
		validate =  false;		
	}
	else if(accDate.getYear() == sysDate.getYear() && accDate.getMonth() > sysDate.getMonth()){
		validate =  false;
	}
	else if(accDate.getYear() == sysDate.getYear() 
			&& accDate.getMonth() == sysDate.getMonth() 
			&& accDate.getDate() > sysDate.getDate()){
		validate =  false;		
	}
	else{
		validate = true;
	}
		
	return validate;
	
}

/*
 * charValidate 不可包含特殊字元檢查, 此需套用jQuery validator
 * @param value 
 * @param element
 */
function charValidate(value, element) {
//	var pattern = new RegExp("[`~!@%#$^&*()=|{}':;',　\\[\\]<>/?\\.；：%……+￥（）【】‘”“'。，、？]");
	var pattern = new RegExp("[`~!@%#$^&*()=|{}':;'　\\[\\]<>?\\.；：%……+￥（）【】‘”“'。，、？]"); //
	return this.optional(element) || !pattern.test(value);
}

/*
 * englishNameValidate 英文姓名檢核機制, 此需套用jQuery validator
 * 僅可輸入 [a-zA-Z0-9, _]等字元，並會自動轉為大寫
 * @param value 
 * @param element
 */
function englishNameValidate(value, element) {
	var result = this.optional(element);
	var reg = new RegExp("^([\\w ,]*)$");
	
	if (!result && reg.test(value)) {
		jQuery(element).val(value.toUpperCase());
		result = true;
	}

	return result;
}

/*
 * 中文檢核機制，該欄位只可輸入中文
 * @param value 
 * @param element
 */
function chineseValidate(value, element) {
	var reg = new RegExp("^[\u4E00-\\u9fa5]*$");
	return this.optional(element) || reg.test(value);
}

/*
 * 全形字檢核，輸入內容不可有全形字之驗證
 * @param value 
 * @param element
 */
function holomorphWordValidate(value, element) {
	return this.optional(element) || (escape(value).indexOf("%u") == -1);
}

function phoneValidate(value, element){
	var pattern = new RegExp("[0-9]");
	return this.optional(element) || pattern.test(value);
}

/*
 * 統一證號檢查機制 
 * @param value 
 * @param element
 * @param params
 */                            
function residentNoValidate(value, element, params){
	
	var validate = false;
			
	//值為null or 空白不檢查 回傳true
	if(value == null || value == ""){			
		return true;
	}
	
	if(residentNoValidateFormat(value) && residentNoValidateRule(value)){
		validate = true;
	}
	else{
		validate = false;
	}
	
	return this.optional(element) || validate;
}

function residentNoValidateFormat(value){
	
	var validate = false;
	var validLength = 10;
	var patternFirst = new RegExp("[ABCDEFGHJKLMNPQRSTUVXYWZIO]");
	var patternSecond = new RegExp("[ABCD]");
	var patternPostfix = new RegExp("^[0-9]+$");
	//alert(value);
	//alert(element);
			
	//值長度不為指定長度皆不合法
	if(value.length != validLength){
		return false;
	}
	//第一個字必需符合patternFirst
	var firstChar = value.substr(0, 1);
	//alert(firstChar);
	if(patternFirst.test(firstChar) == false){
		return false;
	}		
	//第二個字必需符合patternSecond
	var secondChar = value.substr(1, 1);
	//alert(secondChar);
	if(patternSecond.test(secondChar) == false){
		return false;
	}		
	//剩下的字串必需符合patternPostFix
	var postfixString = value.substr(2, 8);
	//alert(postfixString);
	if(patternPostfix.test(postfixString) == false){			
		return false;
	}
	
	return true;
}

function residentNoValidateRule(value){
	
	var validate = false;
	var charString = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
	var numString;
	var resultNum;
	
	numString = (charString.indexOf(value.substr(0, 1)) + 10)
	            + '' + ((charString.indexOf(value.substr(1, 1)) + 10) % 10)
	            + '' + value.substr(2, 8);
	
	//alert(numString);
	
	resultNum = parseInt(numString.substr(0,1)) + 
    			parseInt(numString.substr(1,1)) * 9 + 
    			parseInt(numString.substr(2,1)) * 8 + 
    			parseInt(numString.substr(3,1)) * 7 +           
    			parseInt(numString.substr(4,1)) * 6 + 
    			parseInt(numString.substr(5,1)) * 5 + 
    			parseInt(numString.substr(6,1)) * 4 + 
    			parseInt(numString.substr(7,1)) * 3 + 
    			parseInt(numString.substr(8,1)) * 2 + 
    			parseInt(numString.substr(9,1)) + 
    			parseInt(numString.substr(10,1));
	
	//alert(resultNum);
	
	if( (resultNum % 10) != 0){    		
		//var resultString = resultNum - parseInt(numString.substr(10,1)) + '';
		//var tailNum = 10 - parseInt(resultString.substr(resultString.length - 1, 1));    		
		//alert("尾數應該為" + tailNum);
		validate = false;
	}
	else{
		validate = true;
	}
	
	return validate;
}

/*
 * 驗證身份證字號(10碼 , ex : Y220219294)(jquery validation function)
 * Author : Brian Su
 * 流程 : 
 *   1. 驗證格式是否符合
 *   2. 判斷檢查總和是否正確
 * 回傳值 : 
 *   value - 合法(回傳轉大寫的值)
 *   false - 不合法
 */
 function personIdChk(value, element, arg){
     value = value.toUpperCase();
     var pattern = /^[a-zA-Z]{1}[0-9]{9}$/;
     if (!pattern.test(value)) { // 格式不符合
         return this.optional(element);
     }else{// 數字判斷正確與否
         return (checkPersonIdSum(value))?( jQuery(element).val(value) ):false;
     }
 }
 function checkPersonIdSum(personId){
     var first = personId.substr(0, 1);
     var alphaArray = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
     var valueArray = new Array('10', '11', '12', '13', '14', '15', '16', '17', '34', '18', '19', '20', '21', '22', '35', '23', '24', '25', '26', '27', '28', '29', '32', '30', '31', '33');
     var index = jQuery.inArray(first, alphaArray);
     var firstNo = valueArray[index];
     var value = firstNo.concat(personId.substr(1, 8));
     var sum = parseInt(value.substr(0, 1));
     for (var i = 1; i <= 9; i++) {
         sum = sum + parseInt(value.substr(i, 1)) * (10 - i);
     }
     single = 10 - (sum % 10);
     //如果餘數為0檢查碼則為0
     if(sum % 10 == 0){
    	 single = 0;
     }
     var end = parseInt(personId.substr(9, 1));
     return (end == single)?true:false;
 }
 
 /*
  * 驗證日期與時間的格式(ex : 20121001175813 or 2012/10/01 17:58:13)
  * (jquery validation function)
  * Author : Brian Su
  * 流程 : 
  *   1. 先將輸入的值比對是否符合格式
  *   2. 去除所有非數字的符號，只保留純數字
  *   3. 針對數字驗證是否為合法,正確的數字
  * 回傳值 : 
  *   value - 合法(回傳轉成 yyyy/MM/dd HH:mm:ss 格式的)
  *   false - 不合法
  */
 function niiDateAndTimeFmChk(value, element, arg){
     var re = new RegExp('^([0-9]{4})[/]?([0-9]{2})[/]?([0-9]{2})[ ]?([0-9]{2})[:]?([0-9]{2})[:]?([0-9]{2})$'); 
     if ( re.test(value) == true ) {        //match
         // 去除掉非數字的 ( /\你要置換的字串/g )
         var dateStr = value.replace(/[^0-9]+/g, '');
         var accDate = new Date(dateStr.substring(0,4), dateStr.substring(4,6) - 1, dateStr.substring(6,8) ,
                 dateStr.substring(8,10) , dateStr.substring(10,12) , dateStr.substring(12,14)); 
         var year = accDate.getFullYear();
         var month = accDate.getMonth();
         var day = accDate.getDate();
         var hour = accDate.getHours();
         var minute = accDate.getMinutes();
         var second = accDate.getSeconds();
         var newFmtDate = year + '/' + 
             ( (month < 9)?'0':'' ) + (month + 1 ) + '/' + 
             ( (day < 10)?'0':''  ) + day + ' ' + 
             ( (hour < 10)?'0':'' ) + hour + ':' +
             ( (minute < 10)?'0':'' ) + minute + ':' + 
             ( (second < 10)?'0':'' ) + second;
         var tempDate = newFmtDate.replace(/[^0-9]+/g, '');
         return (dateStr == tempDate)?( jQuery(element).val(newFmtDate) ):false;
     } else {
         return this.optional(element);
     }
 }
 
 /*
  * 生日日期驗證(yyyy or yyyy/MM or yyyy/MM/dd 斜線可有可無)
  * (jquery validation function)
  * Author : Brian Su
  * 流程 : 
  *   1. 分別分三種格式去驗證 , 每種格式有自己的處理方式
  *   2. 只要有一個格式符合則回傳它該有的格式
  * 回傳值 : 
  *   value - 合法(回傳轉成 yyyy or yyyy/MM or yyyy/MM/dd 格式的)
  *   false - 不合法
  */
 function birthDateOptionFmChk(value, element, arg){
 	var pattern1 = new RegExp("^[0-9]{4}$");
     var pattern2 = new RegExp("^[0-9]{4}[/]?[0-9]{1,2}$");
     var pattern3 = new RegExp("^[0-9]{4}[/]?[0-9]{1,2}[/]?[0-9]{1,2}$");
     var isPattern1 = pattern1.test(value);
     var isPattern2 = pattern2.test(value);
     var isPattern3 = pattern3.test(value);
     if(isPattern1 || isPattern2 || isPattern3){
    		var dateStr = value.replace(/\/+/g, '');
    		var accDate = null;
    		var tempDate = null;
    		var newFmtDate = null;
			if(isPattern1){
     		accDate = new Date(dateStr.substring(0,4), 0 , 1);
     		var year = accDate.getFullYear();
     		tempDate = year;
     	    newFmtDate = year;     
			}
			else if(isPattern2){
     		accDate = new Date(dateStr.substring(0,4), dateStr.substring(4,6) - 1, 1); 
     		var year = accDate.getFullYear();
     		var month = accDate.getMonth();
     		tempDate = year + 
             ( (month < 9)?"0":"" ) + (month + 1 );
     	    newFmtDate = year + "/"    + 
             ( (month < 9)?"0":"" ) + (month + 1 );
			}
			else if(isPattern3){
     		accDate = new Date(dateStr.substring(0,4), dateStr.substring(4,6) - 1, dateStr.substring(6)); 
     		var year = accDate.getFullYear();
             var month = accDate.getMonth();
             var day = accDate.getDate();
     		tempDate = year + 
                 ( (month < 9)?"0":"" ) + (month + 1 ) + 
                 ( (day < 10)?"0":""  ) + day;
             newFmtDate = year + "/"    + 
                 ( (month < 9)?"0":"" ) + (month + 1 ) + "/" + 
                 ( (day < 10)?"0":""  ) + day;
     	}
         return (dateStr == tempDate)?( jQuery(element).val(newFmtDate) ):false;
     }else{ //繼續其他的驗證
     	return this.optional(element);
     }
 }

 function niiChkCellPhone(value, element) {
	 var reg = new RegExp("^[0]{1}[9]{1}[0-9]{8}$");
	return this.optional(element) || reg.test(value);
 }
 
 /* 
  * jQuery Validation Plugin
  * 
  * Add Method
  * 
  * Add a custom validation method.
  * It must consist of a name (must be a legal javascript identifier),
  * a javascript based function and a default string message.
  */
 ;(function($) {
	 /**
	  * 日期大於或等於另一個欄位的值
	  */
	 function niiDateGreaterThanOrEqual(value, element, params) { 
		 return this.optional(element) || value >= $(params).val(); 
	 }
	 
	 // 日期格式驗證,需為 0 或yyyy/MM/dd 或 yyyyMMdd
	 $.validator.addMethod("niiDateFmChk0", niiDateFmChk0, "請輸入yyyy/MM/dd");
	 // 日期格式驗證,需為yyyy/MM/dd 或 yyyyMMdd
	 $.validator.addMethod("niiDateFmChk", niiDateFmChk, "請輸入yyyy/MM/dd");
	 // 不可包含特殊字元檢查
	 $.validator.addMethod("charValidate", charValidate, "不可輸入特殊符號");
	 // 英文姓名檢核機制，僅可輸入 [a-zA-Z0-9, _]等字元，並會自動轉為大寫
	 $.validator.addMethod("englishNameValidate", englishNameValidate, "請輸入英文字");
	 // 中文檢核機制，該欄位只可輸入中文
	 $.validator.addMethod("chineseValidate", chineseValidate, "姓名只能輸入中文!");
	 // 全形字檢核，輸入內容不可有全形字之驗證
	 $.validator.addMethod("holomorphWordValidate", holomorphWordValidate, "內容不可有全形字");
	 // phoneValidate
	 $.validator.addMethod("phoneValidate", phoneValidate, "電話格式不正確");
	 // 統一證號檢查機制
	 $.validator.addMethod("residentNoValidate", residentNoValidate, "請輸入正確統一證號");
	 // 驗證身份證字號(10碼 , ex : Y220219294)
	 $.validator.addMethod("personIdChk", personIdChk, "請輸入正確身分證號!");
	 // 驗證日期與時間的格式(ex : 20121001175813 or 2012/10/01 17:58:13)
	 $.validator.addMethod("niiDateAndTimeFmChk", niiDateAndTimeFmChk, "請輸入正確的格式,EX:20120115121020!");
	 // 生日日期驗證(yyyy or yyyy/MM or yyyy/MM/dd 斜線可有可無)
	 $.validator.addMethod("birthDateOptionFmChk", birthDateOptionFmChk, "日期格式錯誤!");
	 // 手機驗證(10碼數字)
	 $.validator.addMethod("niiChkCellPhone", niiChkCellPhone, "手機號碼格式有誤");
	 // 代碼共用元件欄位的驗證
	 $.validator.addMethod("niiCode", function(value, element, params) {
		 return this.optional(element) || $(element).data("codeValid");
	 }, "請輸入正確代碼&nbsp;");
	 // 部門共用元件欄位的驗證
	 $.validator.addMethod("niiDept", function(value, element, params) {
		 return this.optional(element) || $(element).data("deptValid");
	 }, "請輸入正確部門&nbsp;");
	 // 使用者共用元件欄位的驗證
	 $.validator.addMethod("niiUser", function(value, element, params) {
		 return this.optional(element) || $(element).data("userValid");
	 }, "請輸入正確使用者&nbsp;");
	 // 日期不得超過系統日判斷
	 $.validator.addMethod("niiDateWithToday", niiDateFmChkWithToday, "日期不可大於今日!");
	 // 日期大於或等於另一個欄位的值
	 $.validator.addMethod("niiDateGreaterThanOrEqual", niiDateGreaterThanOrEqual, "日期錯誤");
	 
	/**
	 * 驗證省縣市與國籍分類代碼
	 * 
	 * 依據 birthPlace2 欄位內容使否填寫，決定驗證 birthPlaceCode 是否僅可填寫 [1,2]
	 * 參數設定方式二擇一
	 * 1. 於 birthPlace2 input 欄位上指定 class 為 'birthPlaceName'
	 *    此驗證功能將自動搜尋該欄位內內容值
	 *    呼叫設定參數設定為 true 即可(建議)
	 * 
	 * 2. 設定此驗證規則時，將 birthPlace2 欄位之ID名稱傳入
	 *    驗證功能將依照此 ID 取得內容值
	 */
	jQuery.validator.addMethod("birthPlaceCodeValidate", function(value, element, param) {
		var result = false;
		var placeStr;

		// 取得出生地名稱之輸入內容
		if (typeof param == 'string')
			placeStr = $("#" + param).val();
		else if (false == param)
			return true;
		else
			placeStr = $(element).parents("table").find("input.birthPlaceName").val();

		if (this.optional(element))
			result = true;
		else if (placeStr == undefined || placeStr == "")
			result = true;
		else if (value == "1" || value == "2")
			result = true;

		return result;
	}, "當出生地點已填寫時，僅可填寫(省縣/省市)");
	
	/**
	 * 驗證出生地代碼
	 * 
	 * 依據 birthPlaceCode 欄位內容，決定 birthPlace1 可填寫之內容驗證
	 * 錯誤訊息可動態調整：validateErrMsg
	 * 參數設定方式二擇一
	 * 1. 於 birthPlaceCode input 欄位上指定 class 為 'birthPlaceCode'
	 *    此驗證功能將自動搜尋該欄位內內容值
	 *    呼叫設定參數設定為 true 即可(建議)
	 * 
	 * 2. 設定此驗證規則時，將 birthPlaceCode 欄位之ID名稱傳入
	 *    驗證功能將依照此 ID 取得內容值
	 */
	var validateErrMsg;
	jQuery.validator.addMethod("birthPlace1Validate", function(value, element, param) {
		var result = false;
		var birthPlaceCode;
		var placeCode = [2, 3, 10, 18, 19, 20, 21, 28, 29, 30, 31, 41, 42, 43, 53, 54, 55];
		validateErrMsg = "檢核錯誤";

		if (typeof param == 'string')
			birthPlaceCode = $("#" + param).val();
		else if (false == param)
			return true;
		else
			birthPlaceCode = $(element).parents("table").find("input.birthPlaceCode").val();

		if (this.optional(element))
			result = true;
		else if (birthPlaceCode == undefined || birthPlaceCode == "")
			result = true;
		else if (birthPlaceCode == "3")
			if (jQuery.inArray(parseInt(value), placeCode) >= 0)
				result = true;
			else
				validateErrMsg = "出生地點檢核錯誤";
		else if (parseInt(birthPlaceCode) < 5)
			if (parseInt(value) <= 51)
				result = true;
			else
				validateErrMsg = "出生地點檢核錯誤";

		return result;
	}, function () {
		return validateErrMsg;
	});
 })(jQuery);

/**
 * commonRules 共同驗證之預設欄位規則與訊息參數
 */
var ruleOptions = {
	gender : {
		required : true,
		digits : true,
		messages : {
			required : "請填寫性別代碼",
			digits : "性別代碼必須為數字"
		}
	},
	birthDate : {
		required : true,
		niiDateFmChk : true,
		messages : {
			required : "請填寫出生日期"
		}
	},
	birthPlaceCode : {
		required : true,
		digits : true,
		birthPlaceCodeValidate : true,
		messages : {
			required : "請填寫省縣市代碼",
			digits : "省縣市代碼必須為數字"
		}
	},
	birthPlace1 : {
		required : true,
		digits : true,
		birthPlace1Validate : true,
		messages : {
			required : "請填寫省籍代碼",
			digits : "省籍代碼必須為數字"
		}
	}
};

/**
 * 共同驗證方法
 * @param inputNames input tag 之 name array
 * @param opts 若有特殊需求，可覆改原有之設定規則與訊息
 */
function commonRules(inputNames, opts) {
 	var nameArray = [];
 	opts = $.extend({}, ruleOptions, opts || {});
 	
 	for ( var count = 0; count < inputNames.length; count++) {
 		var inputArray = inputNames[count].split(".");
 		nameArray.push(inputArray[inputArray.length - 1]);
 	}
 	
 	for ( var ruleName in opts) {
 		var key = jQuery.inArray(ruleName, nameArray);
 		if (key >= 0 && nameArray[key] == ruleName)
 			$("input[name='" + inputNames[key] + "']").rules("add", opts[ruleName]);
 	}
}
