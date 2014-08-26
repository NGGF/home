<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="userQueryViewForm" action="UserQuery!queryList" method="post">
	<table class="colTb" style="width: 90%" border="2" bgcolor="yellow" align="center">
		<col style="width: 15%">
		<col style="width: 20%">
		<col style="width: 15%">
		<col style="width: 20%">
		<col style="width: 30%">
		
		<caption>人員檢視</caption>
		<tr>
			<th width="20%">姓名</th>
			<td>
				<s:property value="userDo.userName" />
			</td>
			<th width="20%">身份別</th>
			<td>
				<s:if test='%{userDo.userType == "1"}'>
					戰鬥
				</s:if>
				<s:else>
					護軍
				</s:else>
			</td>
			<td rowspan="6" align="center">
				<a href="javascript:getImage()"><img width="110px" height="140px" src="<s:url value='/images/user/photo/%{userDo.userId}.jpg' />"></a>
			</td>
		</tr>
		<tr>
			<th width="20%">性別</th>
			<td>
				<s:if test='%{userDo.gender == "1"}'>
					男
				</s:if>
				<s:else>
					女
				</s:else>
				
			</td>
			<th width="20%">出生日期</th>
			<td>
				<s:date name="userDo.birthday" format="yyyy/MM/dd" />
			</td>
		</tr>
		<tr>
			<th width="20%">FB_電子郵件</th>
			<td>
				<s:property value="userDo.fbEMail" />
			</td>
			<th width="20%">小名</th>
			<td>
				<s:property value="userDo.nickname" />
			</td>
		</tr>
		<tr>
			<th width="20%">電話</th>
			<td>
				<s:if test="%{userDo.telArea != null && userDo.telArea != ''}">
					(<s:property value="userDo.telArea" />)
				</s:if>
				<s:if test="%{userDo.telNo != null && userDo.telNo != ''}">
					<s:property value="userDo.telNo" />
				</s:if>
				<s:if test="%{userDo.telExt != null && userDo.telExt != ''}">
					#<s:property value="userDo.telExt" />
				</s:if>
			</td>
			<th width="20%">手機</th>
			<td>
				<s:property value="userDo.mobilePhone" />
			</td>
		</tr>
		<tr>
			<th width="20%">身分字號</th>
			<td>
				<s:property value="userDo.personalId" />
			</td>
			<th width="20%">衣服尺寸</th>
			<td>
				<s:property value="userDo.underwearSize" />
			</td>
		</tr>
		<tr>
			<th width="20%">花靴尺寸</th>
			<td>
				<s:property value="userDo.bootsSize" />
			</td>
			<th width="20%">預備電子郵件</th>
			<td>
				<s:property value="userDo.reserveEmail" />
			</td>
		</tr>
		<tr>
			<th width="20%">民間專長</th>
			<td colspan="4">
				<s:property value="userDo.profession" />
			</td>
		</tr>
	</table>
	<div align="center">
		<br/>
		<input type="button" value="返回列表" onclick="reback()">
	</div>
	<div>
		<s:hidden name="queryName" />
	</div>
</form>
<script type="text/javascript">

function reback(){
	history.go(-1);
// 	$('#userQueryViewForm').attr('action','UserQuery!queryList').trigger('submit');
}

var vWindowWidth = $(window).width();
var vWindowHeight = $(window).height();
var vWidth = (vWindowWidth < 440 ? vWindowWidth : 440);
var vHeight = (vWindowHeight < 530 ? vWindowHeight : 530);
function getImage(){
	$.blockUI({ message: 
		"<div class='aCenter' style='overflow:auto;background-color: yellow'>" +
			"<img width='380px' height='500px' src='" + "<s:url value='/images/user/photo/%{userDo.userId}.jpg' />" + "'>" +
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