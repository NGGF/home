/* 
 * 動態
 * 
 * */
var $codeQryMarkConfig = {
	qryMarkcountTol: 1, //參數初使值
	maxCount: 5, //最大數量
	codeQryMarkHtml: "",
	dataCategory: "21",
	dataCategoryType: "3",
	showTbId:"codeQryMarkShow", //顯示的table id
	markImgId: "",  //按下新增圖示之Id
	blockContainerId: "#revPageDiv"
}

;(function($){
	$.fn.dynaCodeQry = function(arg) {
		$(this).click(function() {
			var options = $.extend($codeQryMarkConfig, arg || {});
			options.markImgId = $(this).attr("id");
			initData(options);
			$("#" + options.showTbId).append( $.format(options.codeQryMarkHtml, options.qryMarkcountTol++ ) );
			$("#" + options.showTbId).find(":text[data-category]:last").niiCode({blockContainer: options.blockContainerId});
			$("#" + options.showTbId).find(".delCodeQryMark:last").click(function() {
				$(this).parents("tr:first").remove();
				enableMarkImg(options);
			});
			enableMarkImg(options);
		});
	}
	
	function enableMarkImg(options) {
		var $codeQryMark = $("input[name^='codeQryMark']");
		var count = (undefined == $codeQryMark)?0:$codeQryMark.length;
		if ( count > options.maxCount) {
			$("#" + options.markImgId).attr("disabled", true); 
			$("#" + options.markImgId).attr("style", "visibility:hidden"); 
		} else {
			$("#" + options.markImgId).attr("disabled", false);
			$("#" + options.markImgId).attr("style", "");
		}
	}
	
	function initData(options){
		options.codeQryMarkHtml = '	<tr><td>'
			+  '<img src="' + _cp + '/images/icon_minus.jpg" class="delCodeQryMark" />&nbsp' 
			+ 	'<input type="text" size="5" data-category="'
			+ options.dataCategory +'" data-categoryType="'
			+ options.dataCategoryType +'" tabindex="1" name="codeQryMark{0}"/>&nbsp' 
			+ 	'<input type="button" class="btnbg" value="查代碼"/>' 
			+  '</td></tr>' ;	
	}
	
	$.fn.getDynaCodeQryAry = function (showId){
		var codeAry = $.makeArray(
				$("#" + showId).find("input[name^=codeQryMark]").filter(function(index) {
		  			return $(this).val() !="";
			}) );
		return codeAry;
	}
	
	$.fn.chkAllDynaCodeValid = function (showId){ //空白亦認為沒有輸入正確承辦狀況碼
		var flag = true;
		$("#" + showId).find("input[name^=codeQryMark]").each(function(){
			flag &= $(this).data("codeValid");
		});
	
		return flag;
	}
	
})(jQuery);