<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link type="text/css" rel="stylesheet" href="<s:url value="/css/header.css"/>"></link>
<script type="text/javascript" src="<s:url value="/js/serviceTime.js"/>"></script>
<style>
	.btnlog {
	    background-color: #FFF000;
	    border: 1px solid #B19A01;
	    color: #B14C01;
	    margin: 0 2px;
	    padding: 3px 8px 1px;
	}

</style>
<!-- <div style="background: #fff url(../images/bg/header.jpg) repeat-x 0px 0px;"> -->
<div>
	<font size="7" style="color:red" >
	<marquee behavior="left" loop="-1" direction="left" width="1024">
	<a href="javascript:toIndex()" style="text-decoration: none;color: red;">內湖神將聯誼會</a>
	</marquee>
	</font>
<%-- 	<img src="<s:url value='/images/bg/stamp_1.jpg' />"> --%>
	<div>
		<div class="header">
			<div class="time">
				<label>系統時間:</label> <em id="timer"></em>
			</div>
		<!-- 	<div -->
		<!-- 		style="position: absolute; top: 10px; right: 10px; color: #FF0000;"> -->
		<!-- 		<label>登出倒數：</label> <em id="expireTime">30:00</em> -->
		<!-- 	</div> -->
			<div id="userInfoDiv" class="user">
				<s:form action="LogoutAction" namespace="/" method="post">
					<label style="color:#00F;">使用者：</label>
					<em id="userName"></em>
					<input type="submit" value="登出" class="btnlog" />
				</s:form>
				<s:form id="toIndexForm" method="post">
					<s:hidden name="accId" />
					<s:hidden name="accPwd" />
				</s:form>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		if ("" != "${userName}") {
			$('#timer').showSystemTime({
				serviceTimeMillis : "${serviceTime}"
// 				,expireTime : "${expireTime}"
			});
			$("#userName").text("${userName}");
			$("#userInfoDiv").show();
		} else {
			$("#userInfoDiv").hide();
		}
	});
	
	function toIndex(){
		$('#toIndexForm').attr('action',_cp+'/index').trigger('submit');
	}
</script>
