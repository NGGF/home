<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<link type="text/css" rel="stylesheet" href="<s:url value="/css/calendar-blue2.css"/>"></link>
	<script type="text/javascript" src="<s:url value="/js/jquery/jquery-1.6.4.min.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery/jquery.blockUI.js"/>"></script>
	<script type="text/javascript" src="<s:url value='/js/common.js' />" ></script>
	<script type="text/javascript" src="<s:url value="/js/jquery/jquery.dynDateTimeMinGo.js"/>"></script>
	<script type="text/javascript" src="<s:url value="/js/jquery/calendar-big5.js"/>"></script>
	<style>
		body {
/* 			background-color:#b0c4de; */
			background: #fff url(../images/bg/registry_bg.jpg) repeat 0px 0px;
		}
	</style>
	<body>
		<form id="registryForm" action="RegistryAdd" method="post">
			<table align="center" border="1" style="width: 30%;background-color: #b0c4de;">
				<caption><font size="16" style="color: red">內湖神將聯誼會</font></caption>
				<tr>
					<th>帳號</th>
					<td>
						<input type="text" id="accId" name="accountDo.accId" />
						<input type="button" value="檢查帳號" onclick="checkAccId()" />
						<span id="isCheckResult"></span>
						<input type="hidden" id="isCheck" />
					</td>
				</tr>
				<tr>
					<th>密碼</th>
					<td><input type="password" id="password" name="accountDo.accPwd" /></td>
				</tr>
				<tr>
					<th>確認密碼</th>
					<td><input type="password" id="rePassword" /></td>
				</tr>
				<tr>
					<th>姓名</th>
					<td><input type="text" name="accountDo.name" /></td>
				</tr>
				<tr>
			
					<th>生日</th>
					<td>
						<input type="text" id="birthday" name="accountDo.birthdayStr" />
						<img src="<s:url value="/images/icon_calendar.gif"/>" />
					</td>
				</tr>
				<tr>
					<th>性別</th>
					<td>
						<select name="accountDo.gender">
							<option value="1" id="male">男
							<option value="0" id="female">女
						</select>
					</td>
				</tr>
				<tr>
					<th>電子郵件</th>
					<td>
						<input type="text" name="accountDo.eMail" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" value="送出" onclick="registryAdd()">
				<input type="reset" value="清除" />
				<input type="button" value="離開" onclick="toLogin()">
			</div>
		</form>
		<form id="toLoginForm" action="login" method="post"></form>
	</body>
	<head>
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
			
			function toLogin(){
				$('#toLoginForm').submit();
			}
			function checkAccId(){
				var accId = $('#accId').val();
				if(accId == ''){
					$('#isCheckResult').html("");
					$('#isCheck').val("");
				}else{
					$.post('/NGGF/Registry/Registry!isDuplicate',{'accId':accId},isDuplicateResult,'text');
				}
			}
			
			function isDuplicateResult(jsonStr){
				var data = eval("("+jsonStr+")");
				var isDuplicate = data;
				if(isDuplicate == true){
					$('#isCheckResult').html("<font color='red'>帳號已重複</font>");
					$('#isCheck').val(isDuplicate);
					$('#accId').val('');
				}else{
					$('#isCheckResult').html("<font color='green'>OK</font>");
					$('#isCheck').val(isDuplicate);
				}
			}
			
			function registryAdd(){
				var accId = $('#accId').val();
				if(accId == ''){
					niiMessage('帳號不得為空');
					return ;
				}
				
				checkAccId();
				var isCheck = $('#isCheck').val();
				if(isCheck == 'true'){
					niiMessage('請重新輸入一個可用的帳號');
					return ;
				}
				var password = $('#password').val();
				var rePassword = $('#rePassword').val();
				if(password != rePassword){
					$('#rePassword').val('');
					niiMessage('確認密碼不符，請重新輸入');
					return ;
				}
				
				var isCheck = $('#isCheck').val();
				if(isCheck == ''){
					niiMessage('帳號是否可用檢查中，<br/>請等會兒再按一次送出！！');
					return ;
				}else{
					$('#registryForm').attr('action','RegistryAdd').trigger('submit');
				}
			}
		</script>
	</head>
</html>