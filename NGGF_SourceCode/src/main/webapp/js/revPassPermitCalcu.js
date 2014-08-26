
//check form #revPassForm data
function validateRevPassForm(){
	$(":text[data-category]").filter(
			function(){
				return $(this).attr("data-category") != undefined;
				}).each(function(){
					$(this).rules("add", {
						niiCode: true
					});
				});

	if (  $("#revPassForm").valid() == false ) {
		return false;	
	}
	
	//for Dynamic code start
	var objAry = {mark1:"",mark2:"", mark3:"", mark4:"", mark5:"", mark6:""};
	var codeAry = $("#revPassForm").getDynaCodeQryAry("codeQryMarkShow"); //passCodeQryMarkCfg.showTbId
	//clear data
	for (var key in objAry  ) {
		$('#'+key).val('');
	}
	var index = 0;
	for (var key in objAry  ) {
		if ( index < codeAry.length ) {
			$('#'+key).val( $(codeAry[index]).val() );
		} 
		index++;
	}
	//for Dynamic code end
	return true;
}

function showResultData(result){	
	for (var atr in result) {
		if ( atr == "approveMarks") {
			if ( result[atr] != null ) {
				$(result[atr]).each(function(index){
					var $codeQryMark = $("input[name^='codeQryMark']");
					if ( $codeQryMark.length >= (index+1) ){
						$codeQryMark.filter(function(idx){ return idx==index;}).val(this);
					} else {
						$("#codeQryMarkImg").trigger("click");
						$codeQryMark = $("input[name^='codeQryMark']");
						$codeQryMark.filter(function(idx){ return idx==index;}).val(this);
					}	
				})
			}			
		} else {
			$("#" + atr).val(result[atr]);	
		}
	}	
	$("#permitNoCode").trigger("blur");
}


function clearData(result) {
	for (var atr in result) {
		if ( atr == "approveMarks") {
			var $codeQryMark = $("input[name^='codeQryMark']");
			$codeQryMark.each(function() {
				$(this).val('');
			})
		} else {
			$("#" + atr).val('');	
		}
	}
}

function showRulesLog(logs){
	var htmlLog = "<br/><br/>Rules:<br/>";
	if ( null != logs ) {
		htmlLog = htmlLog + logs.join("<br/>");	
		}
	$("#showLogs").html(htmlLog);
}

function showRulesLogMap(logMaps){	
	var htmlLog = "<br/><br/>Rules:<br/>";
	for (var logReveiveNo in logMaps) {
		var htmlLogsub = "";
		for (var i=0;i <= logMaps[logReveiveNo].length;i++  ) {
			if ( undefined != logMaps[logReveiveNo][i] ) {
				htmlLogsub = htmlLogsub + logMaps[logReveiveNo][i] + "<br/>";	
			}
		}
		htmlLog = htmlLog + "ReveiveNo "+ logReveiveNo +": <br/>" + htmlLogsub + "<br/>";	
	}

	$("#showLogs").html(htmlLog);
}

function showProcessNiiUi(msg){
	var vWidth = 220;
	var vHeight = 150;
	$.blockUI({  
        message: "<br/><img src='../images/loading.gif' />" + msg,  
        css: {  
        	top:  ($(window).height() - vHeight) /2 + 'px',   
        	left: ($(window).width() - vWidth) /2 + 'px',  
        	width: vWidth+'px', 
        	height: vHeight+'px',  
        }  
	});
}


//for Pass check field
function showFieldErrValidMsg(errMsgList, warnMsgList) {
	$(".highlightedRow").removeClass('highlightedRow');
	var msg = "";
	for (var i = 0; i < errMsgList.length; i++) {
		revPassChangeClr(errMsgList[i].fieldName);
		msg += errMsgList[i].errorMsg + "<br/>";
	}	
	if ( "" != msg && undefined != msg) {
		niiMessage({msg:msg,custHeight: (49*errMsgList.length)});
	} else {
		showFieldWarnValidMsg(warnMsgList);
	}
	
}
//for Pass check field
function revPassChangeClr(idName){
	if ( idName == "approveMark" ) {
		var $codeQryMark = $("input[name^='codeQryMark']");
		$codeQryMark.each(function() {
			$(this).addClass('highlightedRow');
		})
	} else {
		$("#"+idName).addClass('highlightedRow');	
	}
}

//for Pass check field
function showFieldWarnValidMsg(warnMsgList) {
	var msg = "";
	for (var i = 0; i < warnMsgList.length; i++) {	
		revPassChangeClr(warnMsgList[i].fieldName);
		msg += warnMsgList[i].errorMsg + "<br/>";
	}	
	if ( "" != msg && undefined != msg) {
		niiMessage({msg:msg,
			btn1function:function(){niiMessage({msg:"是否確認審核通過?",btn1function:callRevPassPermitCancelChk,btn2name:"取消"}); },
			btn2name:"取消", 
			custHeight: (50*warnMsgList.length)});	
	} else {
		niiMessage({msg:"是否確認審核通過?",btn1function:callRevPassPermitCancelChk,btn2name:"取消"});
	}	
}

//計算效期rule
function callPassCalculateExpDuration(isTest){
	$.getJSON( _cp + '/Rev/passCalculateExpDuration' , 
			 $("#revPassForm").serialize(),
	
			function(result, status, xhr) {
				if (status != "success" ) {
					niiMessage('發生錯誤，請洽系統管理員');
				} else if ( result == null ) {
					niiMessage('發生錯誤，請洽系統管理員>');
				} else if ( result.errorMsg != "") {
					niiMessage(result.errorMsg);
				} else {
					clearData(result.revPermit);
					showResultData(result.revPermit);
					if (isTest) {
						showRulesLog(result.rulesLogs);
					}
					$.unblockUI();
				}
	});
}

//呼叫前證作廢檢查rule
function callRevPassPermitCancelChk(isTest){	
	if ( $("#receiveNo").val() == $("#firstReceiveNo").val())  {
		if (isTest) {
			niiMessage('本次收件號與首次收件號相同, 不必檢核前證作廢');
		}
		passSubmit();
	} else {
		showProcessNiiUi("前證作廢檢核中...");
		$.getJSON( _cp + '/Rev/revPassPermitCancelChk', 
				 $("#revPassForm").serialize(),	
				function(result, status, xhr) {
					if (status != "success" ) {
						niiMessage('發生錯誤，請洽系統管理員');
					} else if ( result == null ) {
						niiMessage('發生錯誤，請洽系統管理員>');
					} else if ( result.errorMsg != "") {
						niiMessage(result.errorMsg);
					} else {
						if (isTest) {
							showRulesLogMap(result.rulesLogsMap);
						}
						showPermitCancelList(result.resultPermitList);
					}
		});

	}
}


//顯示前證作廢清單
function showPermitCancelList(result){
	if ( undefined == result || result.length == 0 ) {		
		passSubmit();
		return ;
	}
	
	var html = [];
	html.push("<table class='colTb' style='width: 95%;'>");
	html.push("<tr><th>SEQ</th><th>receiveNo</th><th>是否需作廢</th><th>lossMark</th><th>證字號</th><th>decideDate</th><th>applyDate</th><th>出境證效期</th><th>入境證效期</th><th>最近出境日</th><th>最近入境日</th></tr>"); 
	for (var i = 0; i < result.length; i++) {
		html.push("<tr><td>" + (i+1)+ "</td><td>" 
				+ result[i].receiveNo + "</td><td>" 
				+ result[i].permitStatusResult + "</td><td>" 
				+ result[i].lossMark + "</td><td>" 
				+ result[i].permitNoCode + "</td><td>"
				+ result[i].decideDate + "</td><td>" 
				+ result[i].applyDate + "</td><td>" 
				+ result[i].exitExpiryDate + "</td><td>"
				+ result[i].entryExpiryDate + "</td><td>"
				+ result[i].entryExitRecordsExitDate + "</td><td>" 
				+ result[i].entryExitRecordsEntryDate + "</td></tr>");
	}
	html.push("</table>");
	
	var cxlJson = makePermitCancelJson(result); 
	//alert("cxlJson=" + $.toJSON( result ));	
	
	var vWindowWidth = $(window).width();
	var vWindowHeight = $(window).height();
	var vWidth = (vWindowWidth < 640 ? vWindowWidth : 800);
	var vHeight = (vWindowHeight < 480 ? vWindowHeight : 480);
	
	var blockUIOptions = {
			message: "<div><h3 class='aCenter'>前證作廢清單</h3>" + 
				"<div class='aCenter form' style='width: 800px; height: 440px; overflow: auto;' id='niiGDeptQueryResultDiv'>" + 
				html.join("") + "<div></div>" + 
				 "<input type='button' class='btnbg' id='niiCancelCfmBtn' value='確定'  />" + 
				 "<input type='button' class='btnbg'  value='取消' onclick='$.unblockUI()' />",
			css: {
				top: (vWindowHeight - vHeight) / 2 + "px",
				left: (vWindowWidth - vWidth) / 2 + "px",
				width: vWidth + "px",
				height: vHeight + "px",
				cursor: null
			}
		};	
	$.blockUI(blockUIOptions);
	
	$('#niiCancelCfmBtn').bind('click',function(){
		$("#permitCancelJson").val(cxlJson);
		//niiMessage("確認前作廢");
		passSubmit();
	})
}

function makePermitCancelJson(result){
	var jsonStr = [];
	jsonStr.push('[');
	for (var i = 0; i < result.length; i++) {
		if ( i > 0) {
			jsonStr.push(',')
		}
		jsonStr.push('{');
		jsonStr.push('"receiveNo"');
		jsonStr.push(':"');
		jsonStr.push(result[i].receiveNo);
		jsonStr.push('",');
		jsonStr.push('"permitStatusResult"');
		jsonStr.push(':"');
		jsonStr.push(result[i].permitStatusResult);
		jsonStr.push('",');
		jsonStr.push('"lossMark"');
		jsonStr.push(':"');
		jsonStr.push(result[i].lossMark);
		jsonStr.push('",');  
		jsonStr.push('"mark1"');
		jsonStr.push(':"');
		jsonStr.push(result[i].mark1);
		jsonStr.push('",');
		jsonStr.push('"mark2"');
		jsonStr.push(':"');
		jsonStr.push(result[i].mark2);
		jsonStr.push('",');
		jsonStr.push('"mark3"');
		jsonStr.push(':"');
		jsonStr.push(result[i].mark3);
		jsonStr.push('",');
		jsonStr.push('"mark4"');
		jsonStr.push(':"');
		jsonStr.push(result[i].mark4);
		jsonStr.push('",');
		jsonStr.push('"mark5"');
		jsonStr.push(':"');
		jsonStr.push(result[i].mark5);
		jsonStr.push('",');
		jsonStr.push('"mark6"');
		jsonStr.push(':"');
		jsonStr.push(result[i].mark6);
		jsonStr.push('"}');
	}		
	jsonStr.push(']');
	
	return jsonStr.join('');
}