<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="generalsQueryViewForm" action="GeneralsDoQuery!queryList" method="post">
	<table class="colTb" style="width: 60%" border="2" bgcolor="yellow" align="center">
		<caption>神將檢視</caption>
		<tr>
			<th width="20%">姓名</th>
			<td colspan="3" align="center">
				<s:property value="generalsDo.generalsName" />
			</td>
			<td rowspan="4" align="center">
				<a href="javascript:getImage()"><img width="120px" height="170px" src="<s:url value='/images/generals/photo/%{generalsDo.generalsId}.jpg' />"></a>
			</td>
		</tr>
		<tr>
			<th width="20%">承造經費</th>
			<td width="20%">
				<s:property value="generalsDo.makeCost" />
			</td>
			<th width="20%">承造日期</th>
			<td width="20%">
				<s:date name="generalsDo.makeDate" format="yyyy/MM/dd" />
			</td>
		</tr>
		
	</table>
	<div align="center">
		<br/>
		<input type="button" value="返回列表" onclick="reback()">
	</div>
	<div>
		<s:hidden name="queryGeneralsName" />
	</div>
</form>
<script type="text/javascript">

function reback(){
	history.go(-1);
// 	$('#generalsQueryViewForm').attr('action','GodGeneralsQuery!queryList').trigger('submit');
}

var vWindowWidth = $(window).width();
var vWindowHeight = $(window).height();
var vWidth = (vWindowWidth < 440 ? vWindowWidth : 440);
var vHeight = (vWindowHeight < 530 ? vWindowHeight : 530);
function getImage(){
	$.blockUI({ message: 
		"<div class='aCenter' style='overflow:auto;background-color: yellow'>" +
			"<img width='330px' height='500px' src='" + "<s:url value='/images/generals/photo/%{generalsDo.generalsId}.jpg' />" + "'>" +
			"<br/>" +
			"<input type='button' class='btnbg' value='關閉' onclick='cancel()' >" +
		"</div>"
		,
		css: {
			top: (vWindowHeight - vHeight) / 2 + "px",
			left: (vWindowWidth - vWidth) / 2 + "px",
			width: vWidth + "px",
			height: vHeight + "px",
			cursor: null
		}	
	});
}
function cancel(){
	$.unblockUI();
}
</script>