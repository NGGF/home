<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="userEditForm" action="UserEdit!userUpdate" method="post">
	<table class="colTb" style="width: 90%" border="2" bgcolor="yellow" align="center">
		<caption>人員更新</caption>
		<tr>
			<th width="20%">姓名</th>
			<td>
				<s:hidden name="userDo.userId" />
				<s:textfield name="userDo.userName" />
			</td>
			<th width="20%">身份別</th>
			<td>
				<s:radio list="#{'1':'戰鬥','2':'護軍'}" name="userDo.userType" value="1" />
			</td>
		</tr>
		<tr>
			<th width="20%">性別</th>
			<td>
				<s:radio list="#{'1':'男','0':'女'}" name="userDo.gender" value="#{userDo.gender}"/>
			</td>
			<th width="20%">出生日期</th>
			<td>
				<s:hidden id="birthdate" name="userDo.birthday" />
				<s:set var="updateBirthday"><s:date name='userDo.birthday' format='yyyy/MM/dd' /></s:set>
				<s:textfield name="userDo.birthdayStr" id="birthday" value="%{updateBirthday}" />
				<img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" />
			</td>
		</tr>
		<tr>
			<th width="20%">FB_電子郵件</th>
			<td>
				<s:textfield name="userDo.fbEMail" />
			</td>
			<th width="20%">小名</th>
			<td>
				<s:textfield name="userDo.nickname" />
			</td>
		</tr>
		<tr>
			<th width="20%">電話</th>
			<td>
				(<s:textfield name="userDo.telArea" size="3" />)
				<s:textfield name="userDo.telNo" />
				#<s:textfield name="userDo.telExt" size="5" />
			</td>
			<th width="20%">手機</th>
			<td>
				<s:textfield name="userDo.mobilePhone" />
			</td>
		</tr>
		<tr>
			<th width="20%">身分字號</th>
			<td>
				<s:textfield name="userDo.personalId" />
			</td>
			<th width="20%">衣服尺寸</th>
			<td>
				<s:select list="#{'S':'S','M':'M','L':'L','XL':'XL','2L':'2L','3L':'3L','4L':'4L','5L':'5L'}" name="userDo.underwearSize" value="#{userDo.underwearSize}" />
			</td>
		</tr>
		<tr>
			<th width="20%">花靴尺寸</th>
			<td>
				<s:select list="#{'X':'X','40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46'}" name="userDo.bootsSize" value="#{userDo.bootsSize}" />
			</td>
			<th width="20%">預備電子郵件</th>
			<td>
				<s:textfield name="userDo.reserveEmail" />
			</td>
		</tr>
		<tr>
			<th width="20%">民間專長</th>
			<td>
				<s:textfield name="userDo.profession" size="40" />
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
		<input type="button" value="送出" onclick="userCheck()">
	</div>
	<div>
		<s:hidden name="queryName" />
		<s:hidden name="queryId" />
	</div>
</form>
<script type="text/javascript">
jQuery(function($){
	var birthdate = $('#birthdate').val();
	if(birthdate == ''){
		$('#birthday').val('');
	}
	//小日曆
	$("#birthday").dynDateTime({
		button : ".next()"
	});
	//自動為日期加斜線
	$('#birthday').blur(function(){
		if(/^\d{8}$/.test($(this).val()) == true){
			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
		}
	});
});

function reback(){
	history.go(-1);
// 	$('#userEditForm').attr('action','UserQuery!queryList').trigger('submit');
}

function userCheck(){
	$('#userEditForm').attr('action','UserEdit!updateUser').trigger('submit');
}
</script>