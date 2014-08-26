<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="userQueryForm" action="UserQuery" method="post">
	<table class="colTb" style="width: 30%" border="2" bgcolor="yellow" align="center">
		<caption>人員查詢</caption>
		<tr>
			<th width="20%">姓名</th>
			<td width="30%">
				<input type="text" name="queryName" />
			</td>
	</table>
	<div align="center">
		<br/>
		<input type="button" value="查詢" onclick="userQuery()">
	</div>
</form>
<script type="text/javascript">

function userQuery(){
	$('#userQueryForm').attr('action','UserQuery!queryList').trigger('submit');
}
</script>