<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="userAddForm" action="UserAdd!inserUser" method="post">
	<table class="colTb" style="width: 90%" border="2" bgcolor="yellow" align="center">
		<caption>人員新增</caption>
		<tr>
			<th width="20%">姓名</th>
			<td>
				<input type="text" name="userDo.userName" />
			</td>
			<th width="20%">身份別</th>
			<td>
				<s:radio list="#{'1':'戰鬥','2':'護軍'}" name="userDo.userType" value="1" />
			</td>
		</tr>
		<tr>
			<th width="20%">性別</th>
			<td>
				<input type="radio" name="userDo.gender" value="1"  />男
				<input type="radio" name="userDo.gender" value="0" />女
			</td>
			<th width="20%">出生日期</th>
			<td>
				<input type="text" id="birthday" name="userDo.birthdayStr" />
				<img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" />
			</td>
		</tr>
		<tr>
			<th width="20%">FB_電子郵件</th>
			<td>
				<input type="text" name="userDo.fbEMail" />
			</td>
			<th width="20%">小名</th>
			<td>
				<input type="text" name="userDo.nickname" />
			</td>
		</tr>
		<tr>
			<th width="20%">電話</th>
			<td>
				<input type="text" name="userDo.telArea" value="02" size="3"/>
				<input type="text" name="userDo.telNo" />
				<input type="text" name="userDo.telExt" size="3"/>
			</td>
			<th width="20%">手機</th>
			<td>
				<input type="text" name="userDo.mobilePhone" />
			</td>
		</tr>
		<tr>
			<th width="20%">身分字號</th>
			<td>
				<input type="text" name="userDo.personalId" />
			</td>
			<th width="20%">衣服尺寸</th>
			<td>
				<select name="userDo.underwearSize" >
					<option value="S">S
					<option value="M">M
					<option value="L">L
					<option value="XL">XL
					<option value="2L">2L
					<option value="3L">3L
					<option value="4L">4L
					<option value="5L">5L
				</select>
			</td>
		</tr>
		<tr>
			<th width="20%">花靴尺寸</th>
			<td>
				<select name="userDo.bootsSize">
					<option value="X">X
					<option value="40">40
					<option value="41">41
					<option value="42">42
					<option value="43">43
					<option value="44">44
					<option value="45">45
					<option value="46">46
				</select>
			</td>
			<th width="20%">預備電子郵件</th>
			<td>
				<input type="text" name="userDo.reserveEmail" />
			</td>
		</tr>
		<tr>
			<th width="20%">民間專長</th>
			<td colspan="3">
				<input type="text" name="userDo.profession" size="40" />
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
		<input type="reset" value="清除">
		<input type="button" value="送出" onclick="userCheck()">
	</div>
</form>
<script type="text/javascript">
jQuery(function($){
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

function userCheck(){
	$('#userAddForm').attr('action','UserAdd!inserUser').trigger('submit');
}
</script>