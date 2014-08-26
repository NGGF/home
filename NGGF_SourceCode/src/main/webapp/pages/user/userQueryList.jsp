<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="display" uri="/WEB-INF/tags/displaytag.tld"%>
<style>
	.displayTag a{
		color: white;
		text-decoration: none;
	}
</style>
<link type="text/css" rel="stylesheet" href="<s:url value='/css/display-tag.css' />"></link>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<s:form id="userDoListForm" action="UserQuery!queryView" method="post">
	<s:if test="userDoList != null && userDoList.size > 0">
		<div>
			<span><font color="red">戰鬥人員</font>：<s:property value="userType1Count" />名</span>
			<span><font color="red">護軍人員</font>：<s:property value="userType2Count" />名</span>
		</div>
		<div id="userDoListDiv">
			<display:table name="userDoList" sort="list" id="result" class="displayTag" requestURI="UserQuery!queryList" pagesize="10">
				<display:column title="序號" value="${result_rowNum}" sortable="true" />
				<display:column title="會員編號" property="userId" sortable="true" />
				<display:column title="身分別" value="${result.userType=='1'?'戰鬥':'護軍'}" sortable="true" />
				<display:column title="姓名" property="userName" sortable="true" />
				<display:column title="小名" property="nickname" />
				<display:column title="性別" value="${result.gender=='1'?'男':'女'}" sortable="true">
				</display:column>
				<display:column title="生日" property="birthday" format="{0,date,yyyy/MM/dd}" />
				<display:column title="功能選項">
					<input type="button" value="檢視" onclick="userQueryView('${result.userId}')" />
					<input type="button" value="修改" onclick="userEdit('${result.userId}')" />
					<input type="button" value="除名" onclick="userDelete('${result.userId}')" />
				</display:column>
			</display:table>
			<div id="displayFooter" align="center" class="aCenter"></div>
		</div>
	</s:if>
	<s:else>
		查無資料
	</s:else>
	<div class="aCenter">
	</div>
	<div>
		<s:hidden name="queryName" />
		<s:hidden id="userId" name="queryId" />
	</div>
</s:form>
<script type="text/javascript">
	jQuery(function($){
		$("#userDoListDiv .pagelinks").appendTo("#displayFooter");
		$("#userDoListDiv .pagebanner").appendTo("#displayFooter");
	});
	
	function userQueryView(userId){
		$('#userId').val(userId);
		$('#userDoListForm').attr('action','UserQuery!queryView').trigger('submit');
	}
	function userEdit(userId){
		$('#userId').val(userId);
		$('#userDoListForm').attr('action','UserEdit').trigger('submit');
	}
	function userDelete(userId){
		$('#userId').val(userId);
		niiMessage('建置中');
	}
</script>