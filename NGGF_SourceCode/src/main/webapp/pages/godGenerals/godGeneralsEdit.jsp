<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="godGeneralsCheckEditForm" action="UserEdit!userUpdate" method="post">
	<table class="colTb" style="width: 90%" border="2" bgcolor="yellow" align="center">
		<caption>神將更新</caption>
		<tr>
			<th width="40%">神將名稱</th>
			<td>
				<s:hidden name="generalsDo.generalsId" />
				<s:textfield name="generalsDo.generalsName" />
			</td>
		</tr>
		<tr>
			<th width="40%">承製經費</th>
			<td>
				<s:textfield name="generalsDo.makeCost" />
			</td>
		</tr>
		<tr>
			<th width="40%">承製日期</th>
			<td>
				<s:hidden id="makeDay" name="generalsDo.makeDate" />
				<s:set var="updateMakeDay"><s:date name='generalsDo.makeDate' format='yyyy/MM/dd' /></s:set>
				<s:textfield name="generalsDo.makeDateStr" id="makeDate" value="%{updateMakeDay}" />
				<img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" />
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<th>照片上傳</th> -->
<!-- 			<td> -->
<!-- 				<input type="file" > -->
<!-- 			</td> -->
<!-- 		</tr> -->
	</table>
	<div align="center">
		<br/>
		<input type="button" value="回列表" onclick="reback()">
		<input type="button" value="送出" onclick="generalsCheck()">
	</div>
	<div>
		<s:hidden name="queryGeneralsName" />
		<s:hidden id="generalsId" name="generalsId" />
	</div>
</form>
<script type="text/javascript">
jQuery(function($){
	var makeDay = $('#makeDay').val();
	if(makeDay == ''){
		$('#makeDate').val('');
	}
	//小日曆
	$("#makeDate").dynDateTime({
		button : ".next()"
	});
	//自動為日期加斜線
	$('#makeDate').blur(function(){
		if(/^\d{8}$/.test($(this).val()) == true){
			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
		}
	});
});

function reback(){
	history.go(-1);
// 	$('#godGeneralsCheckEditForm').attr('action','GodGeneralsQuery!queryList').trigger('submit');
}

function generalsCheck(){
	$('#godGeneralsCheckEditForm').attr('action','GodGeneralsEdit!updateUser').trigger('submit');
}
</script>