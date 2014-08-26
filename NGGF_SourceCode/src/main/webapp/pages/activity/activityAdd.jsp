<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<form id="activityAddForm" action="NoteCard" method="post">
	<table class="colTb" style="width: 90%" border="1" bgcolor="yellow" align="center">
		<caption>出軍活動新增</caption>
		<tr>
			<th width="20%">出軍起日</th>
			<td>${activityStartDay}
				<s:hidden name="activityStartDay" />
<%-- 				<input type="text" id="activityStartDay"/><img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" /> --%>
			</td>
			<th width="20%">出軍迄日</th>
			<td>${activityEndDay}
				<s:hidden name="activityEndDay" />
<%-- 				<input type="text" id="activityEndDay" /><img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" /> --%>
			</td>
		</tr>
		<tr id="generalsBookTr">
			<th>出軍神將</th>
			<td colspan="3">
				<div id="generalsBookDiv">
				</div>
				<input type="button" value="在家神將預約" onclick="generalsBook()">
			</td>
		</tr>
		<tr id="otherGeneralsTr">
			<th>
				支援神將
			</th>
			<td colspan="3">
				新增神將:
				<img align="top" style='cursor: pointer;' src="<s:url value="/images/icon_add.png"/>" onclick="addOtherGenerals()" />
				<input type="hidden" id="otherGeneralsDivSql" value="0" />
				<div id="otherGeneralsDiv">
				</div>
			</td>
		</tr>
		<tr id="userBookTr">
			<th>出軍人員</th>
			<td colspan="3">
<!-- 				<textarea rows="3" cols="70"> -->
<!-- 				</textarea> -->
				<div id="userBookDiv">
				</div>
				<input type="button" value="出軍會員預約" onclick="userBook()">
			</td>
		</tr>
		<tr id="otherUserBookTr">
			<th>支援人員</th>
			<td colspan="3">
				新增人員:
				<img align="top" style='cursor: pointer;' src="<s:url value="/images/icon_add.png"/>" onclick="addOtherUser()" />
				<input type="hidden" id="otherUserDivSql" value="0" />
				<div id="otherUserDiv">
				</div>
			</td>
		</tr>
		<tr id="otherInfoTr">
			<th width="20%">出軍人數</th>
			<td>
				<div id="userTotal"></div>
			</td>
			<th width="20%">接洽人</th>
			<td>
				<input type="text">
			</td>
		</tr>
	</table>
	<div align="center">
		<br/>
		<input type="button" value="清除" onclick="clean()">
		<input type="button" value="送出" onclick="printNote()">
	</div>
</form>
<div id='blockGeneralsDiv' class='aCenter' style="display: none;">
	<div class='aCenter'><h3 class='aCenter'>神將列表</h3></div>
	<div align="center">
	<input type="button" value="神將確定" onclick="getGenerals()" />
	<input type='button' class='btnbg' value='返回' onclick='cancel()' />
	</div>
	<div class='aCenter form' align='center' id='queryGeneralsList' style='width: 530px; height: 400px; overflow: auto;'></div>
</div>
<div id='blockUserDiv' class='aCenter' style="display: none;">
	<div class='aCenter'><h3 class='aCenter'>人員列表</h3></div>
	<div align="center">
	<input type="button" value="人員確定" onclick="getUser()" />
	<input type='button' class='btnbg' value='返回' onclick='cancel()' />
	</div>
	<div class='aCenter form' align='center' id='queryUserList' style='width: 530px; height: 400px; overflow: auto;'></div>
</div>
<script type="text/javascript">
jQuery(function($){
// 	//小日曆
// 	$("#activityStartDay").dynDateTime({
// 		button : ".next()"
// 	});
// 	$("#activityEndDay").dynDateTime({
// 		button : ".next()"
// 	});
// 	//自動為日期加斜線
// 	$('#activityStartDay').blur(function(){
// 		if(/^\d{8}$/.test($(this).val()) == true){
// 			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
// 		}
// 	});
// 	$('#activityEndDay').blur(function(){
// 		if(/^\d{8}$/.test($(this).val()) == true){
// 			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
// 		}
// 	});
	
// 	$('#generalsBookTr').hide();
// 	$('#otherGeneralsTr').hide();
// 	$('#userBookTr').hide();
// 	$('#otherUserBookTr').hide();
// 	$('#otherInfoTr').hide();

	queryGenerals();
	queryUser();
});

function printNote(){
	$('#activityAddForm').attr('action','NoteCard').trigger('submit');
}
function clean(){
	$('#activityAddForm').attr('action','ActivityAdd!addActivity').trigger('submit');
}

function generalsBook(){
	var options = {width: 530,height: 500};
	$.blockUI({ 
			message: $('#blockGeneralsDiv')
			,css:getBlockUIOptionsCSS(options)
	});
}

function cancel(){
	$.unblockUI();
}

function queryGenerals(){
	$.post('/NGGF/Activity/ActivityAdd!querygeneralsList',selectQueryGeneralsList,'text');
}

function selectQueryGeneralsList(jsonStr){
	var data = eval("("+jsonStr+")");
	if(data != null && data.length != 0){
		var trTds = [];
		trTds.push('<table class="colTb" align="center" border="1" style="width: 90%;">');
		trTds.push('<tr>');
		trTds.push('<th><input type="checkbox" id="checkAll" name="checkAll" onclick="selectAll()" />全選</th>');
// 		trTds.push('<th>神將編號</th>');
		trTds.push('<th>姓名</th>');
		trTds.push('<th>註記</th>');
		trTds.push('</tr>');
		$(data).each(function() {
			trTds.push('<tr>');
			trTds.push('<td><input type="checkbox" value="' + this.generalsId + ',' + this.generalsName + '" name="filterChks" /></td>');
// 			trTds.push('<td>' + this.generalsId + '</td>');
			trTds.push('<td>' + '<a style="text-decoration: none;" href="javascript:enlarge(\'' + this.generalsId + '\');">' + this.generalsName + '</a>' + '</td>');
			trTds.push('<td>' + '' + '</td>');
			trTds.push('</tr>');
		});
		trTds.push('</table>');
		$('#queryGeneralsList').html(trTds.join(''));
	}else{
		showGeneralsErrMsg('查無資料');
		return ;
	}
}
function selectAll(){
	if($(':checkbox[name=checkAll]:checked').length ==0){
		$(':checkbox[name=filterChks]').each(function(){this.checked = false;});
	}else{
		$(':checkbox[name=filterChks]').each(function(){this.checked = true;});
	}
}
function showGeneralsErrMsg(msg) {
	$('#queryGeneralsList').html("<div class='aCenter error'>" + msg + "</div>");
}
function getGenerals(){
	if($(':checkbox[name=filterChks]:checked').length >0){
		var tableStr = '<table class="colTb" align="center" style="width: 90%;"><tr>' + '<div align="center">預約神將如下:<br/>-----------------------------------</div>';
		var tds = '';
		var sql = 0;
		$(':checkbox[name=filterChks]').each(function(){
			if(this.checked){
				if(sql != 0){
					if((sql%4) == '0'){
						tds = tds + '</tr><tr>';
					}
				}
				sql = sql + 1;
				var value = this.value;
				var sliptVals = value.split(',');
				tds = tds + '<td>'+ sliptVals[1] + '<input type="hidden" name="bookGeneralsList" id="' +  sliptVals[0] + '" value="' +  sliptVals[1] + '" /></td>';
			}
		});
		tableStr = tableStr + tds;
		tableStr = tableStr + '</tr></table>';
		$('#generalsBookDiv').html(tableStr);
	}else{
		$('#generalsBookDiv').html('');
	}
	cancel();
}
function enlarge(url){
	var vWindowWidth = $(window).width();
	var vWindowHeight = $(window).height();
	var vWidth = (vWindowWidth < 285 ? vWindowWidth : 285);
	var vHeight = (vWindowHeight < 345 ? vWindowHeight : 345);
	$('#blockGeneralsDiv').block({
		message:
			'<div>'+
				'<img width="285" height="315" src="<s:url value="/images/generals/photo/' + url + '.jpg" />" />' + 
				'<div class="aCenter">'+
					'<input type="button" class="btnbg" value="關閉" onclick="$(\'#blockGeneralsDiv\').unblock()" /> '+ 
				'</div>'+
			'</div>'	
		,css: {
			top: (vWindowHeight - vHeight) / 2 + "px",
			left: (vWindowWidth - vWidth) / 2 + "px",
			width: vWidth + "px",
			height: vHeight + "px",
			cursor: null
		}	
	});
}

function addOtherGenerals(){
	var otherGeneralsCount = $(':text[name=otherGenerals]').length;
	var otherGeneralsDivSql = $('#otherGeneralsDivSql').val();
	if(otherGeneralsCount == 0){
		$('#otherGeneralsDiv').html('支援神將名單如下:'); 	
	}
	var addedSql = (parseInt(otherGeneralsDivSql)+1);
	var giveId = 'otherG' + addedSql;
	$('#otherGeneralsDivSql').val(addedSql);
	var giveDivId = giveId + 'Div';
	$('#otherGeneralsDiv').append('<div id="' + giveDivId + '">' 
			+ '<input type="text" name="otherGenerals" id="\'' + giveId + '\'" />'
			+ '<img align="top" style="cursor: pointer;" src="<s:url value="/images/icon_del.png"/>" onclick="delOtherGenerals(\'' + giveDivId + '\')" />'
			+ '</div>'
		);
}

function delOtherGenerals(divId){
	var id = '#' + divId;
	var otherGeneralsCount = $(':text[name=otherGenerals]').length;
	if(otherGeneralsCount == 1){
		$('#otherGeneralsDiv').html('');
		$('#otherGeneralsDivSql').val(0);
	}else{
		$('div').remove(id);
	}
}

function userBook(){
	var options = {width: 530,height: 500};
	$.blockUI({ 
			message: $('#blockUserDiv')
			,css:getBlockUIOptionsCSS(options)
	});
}

function queryUser(){
	$.post('/NGGF/Activity/ActivityAdd!queryUserList',selectQueryUserList,'text');
}
function selectQueryUserList(jsonStr){
	var data = eval("("+jsonStr+")");
	if(data != null && data.length != 0){
		var trTds = [];
		trTds.push('<table class="colTb" align="center" border="1" style="width: 90%;">');
		trTds.push('<tr>');
		trTds.push('<th><input type="checkbox" id="checkUserAll" name="checkUserAll" onclick="selectUserAll()" />全選</th>');
		trTds.push('<th>人員姓名</th>');
		trTds.push('<th>人員小名</th>');
		trTds.push('<th>註記</th>');
		trTds.push('</tr>');
		$(data).each(function() {
			trTds.push('<tr>');
			trTds.push('<td><input type="checkbox" value="' + this.userId + ',' + this.userName + '" name="filterUserChks" /></td>');
			trTds.push('<td>' + this.userName + '</td>');
			trTds.push('<td>' + '<a style="text-decoration: none;" href="javascript:userEnlarge(\'' + this.userId + '\');">' + this.nickname + '</a>' + '</td>');
			trTds.push('<td>' + '' + '</td>');
			trTds.push('</tr>');
		});
		trTds.push('</table>');
		$('#queryUserList').html(trTds.join(''));
	}else{
		showGeneralsErrMsg('查無資料');
		return ;
	}
}
function selectUserAll(){
	if($(':checkbox[name=checkUserAll]:checked').length ==0){
		$(':checkbox[name=filterUserChks]').each(function(){this.checked = false;});
	}else{
		$(':checkbox[name=filterUserChks]').each(function(){this.checked = true;});
	}
}
function showGeneralsErrMsg(msg) {
	$('#queryUserList').html("<div class='aCenter error'>" + msg + "</div>");
}

function getUser(){
	if($(':checkbox[name=filterUserChks]:checked').length >0){
		var tableStr = '<table class="colTb" align="center" style="width: 90%;"><tr>' + '<div align="center">預約人員如下:<br/>-----------------------------------</div>';
		var tds = '';
		var sql = 0;
		$(':checkbox[name=filterUserChks]').each(function(){
			if(this.checked){
				if(sql != 0){
					if((sql%4) == '0'){
						tds = tds + '</tr><tr>';
					}
				}
				sql = sql + 1;
				var value = this.value;
				var sliptVals = value.split(',');
				tds = tds + '<td>'+ sliptVals[1] + '<input type="hidden" name="bookUserList" id="' +  sliptVals[0] + '" value="' +  sliptVals[1] + '" /></td>';
			}
		});
		tableStr = tableStr + tds;
		tableStr = tableStr + '</tr></table>';
		$('#userBookDiv').html(tableStr);
	}else{
		$('#userBookDiv').html('');
	}
	cancel();
	activityCount();
}

function userEnlarge(url){
	var vWindowWidth = $(window).width();
	var vWindowHeight = $(window).height();
	var vWidth = (vWindowWidth < 285 ? vWindowWidth : 285);
	var vHeight = (vWindowHeight < 345 ? vWindowHeight : 345);
	$('#blockUserDiv').block({
		message:
			'<div>'+
				'<img width="285" height="315" src="<s:url value="/images/user/photo/' + url + '.jpg" />" />' + 
				'<div class="aCenter">'+
					'<input type="button" class="btnbg" value="關閉" onclick="$(\'#blockUserDiv\').unblock()" /> '+ 
				'</div>'+
			'</div>'	
		,css: {
			top: (vWindowHeight - vHeight) / 2 + "px",
			left: (vWindowWidth - vWidth) / 2 + "px",
			width: vWidth + "px",
			height: vHeight + "px",
			cursor: null
		}	
	});
}

function addOtherUser(){
	var otherUserCount = $(':text[name=otherUser]').length;
	var otherUserDivSql = $('#otherUserDivSql').val();
	if(otherUserCount == 0){
		$('#otherUserDiv').html('支援人員名單如下:'); 	
	}
	var addedSql = (parseInt(otherUserDivSql)+1);
	var giveId = 'otherU' + addedSql;
	$('#otherUserDivSql').val(addedSql);
	var giveDivId = giveId + 'Div';
	$('#otherUserDiv').append('<div id="' + giveDivId + '">' 
			+ '<img align="top" style="cursor: pointer;" src="<s:url value="/images/icon_del.png"/>" onclick="delOtherUser(\'' + giveDivId + '\')" />'
			+ '姓名:<input type="text" name="otherUser" id="\'' + giveId + '\'" />'
			+ '身分證:<input type="text" name="otherUserIdNo" />'
			+ '生日:<input type="text" name="otherUserBirthday" value="1911/01/01" />'
			+ '</div>'
		);
	activityCount();
}

function delOtherUser(divId){
	var id = '#' + divId;
	var otherUserCount = $(':text[name=otherUser]').length;
	if(otherUserCount == 1){
		$('#otherUserDiv').html('');
		$('#otherUserDivSql').val(0);
	}else{
		$('div').remove(id);
	}
	activityCount();
}

function activityCount(){
	var userCount = $(':checkbox[name=filterUserChks]:checked').length;
	var otherUserCount = $(':text[name=otherUser]').length;
	var userTotal = parseInt(userCount) + parseInt(otherUserCount);
	$('#userTotal').text(userTotal);
}
</script>