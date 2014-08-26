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
<s:form id="godGeneralsDoListForm" action="GodGeneralsQuery!queryView" method="post">
	<s:if test="generalsDoList != null && generalsDoList.size > 0">
		<div id="generalsDoListDiv">
			<display:table name="generalsDoList" sort="list" id="result" class="displayTag" requestURI="GodGeneralsQuery!queryList" pagesize="10">
				<display:column title="序號" value="${result_rowNum}" sortable="true" />
				<display:column title="神將編號" property="generalsId" sortable="true" />
				<display:column title="姓名" property="generalsName" sortable="true" />
				<display:column title="功能選項">
					<input type="button" value="檢視" onclick="godGeneralsQueryView('${result.generalsId}')" />
					<input type="button" value="修改" onclick="godGeneralsEdit('${result.generalsId}')" />
					<input type="button" value="除名" onclick="godGeneralsDelete('${result.generalsId}')" />
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
		<s:hidden name="queryGeneralsName" />
		<s:hidden id="generalsId" name="generalsId" />
	</div>
</s:form>
<script type="text/javascript">
	jQuery(function($){
		$("#generalsDoListDiv .pagelinks").appendTo("#displayFooter");
		$("#generalsDoListDiv .pagebanner").appendTo("#displayFooter");
	});
	
	function godGeneralsQueryView(generalsId){
		$('#generalsId').val(generalsId);
		$('#godGeneralsDoListForm').attr('action','GodGeneralsQuery!queryView').trigger('submit');
	}
	function godGeneralsEdit(generalsId){
		$('#generalsId').val(generalsId);
		$('#godGeneralsDoListForm').attr('action','GodGeneralsEdit').trigger('submit');
	}
	function userDelete(generalsId){
		$('#generalsId').val(generalsId);
		niiMessage('建置中');
	}
</script>