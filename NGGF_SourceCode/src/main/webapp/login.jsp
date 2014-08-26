<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery-1.6.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.blockUI.js"/>"></script>
<script type="text/javascript" src="<s:url value='/js/common.js' />" ></script>

<title>內湖神將聯誼會管理系統</title>
<link type="text/css" rel="stylesheet" href="<s:url value='/css/login.css'/>"></link>
<div class="login">
	<h2>請登入使用者帳號</h2>
	<div class="form_admin">
		<form id="loginForm" action="/NGGF/LoginAction" method="post" class="form">
			<label for="userId">帳號：</label>
			<input type="text" name="accId" /><br />
			<label for="userPass">密碼：</label>
			<input type="password" name="accPwd" /><br />
			<input type="submit" value="確定" class="btn_login" />
			<input type="reset" name="重設" value="清除" class="btn_login2" />
			<a href="javascript:registry();" style="color: blue; text-decoration: none;">
				註冊 
			</a>
		</form>
	</div>
	<div style="text-align: center; color: #FF0000; height: 20px;">${errors.login[0]}</div>
</div>
<div class="footer">
	台灣台北內湖神將聯誼會 版權所有 © NEIHU GOD GENERALS FRATERNITY <br>
	地址：台灣台北內湖神將聯誼會地址 電話： (02)XXXX-XXXX
</div>
<script type="text/javascript">
	function registry(){
		$('#loginForm').attr('action','Registry/Registry').trigger('submit');
	}
</script>
