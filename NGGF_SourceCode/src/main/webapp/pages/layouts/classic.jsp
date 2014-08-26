<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
	th{
		text-align: left;
 		background-color: #086A87; 
	}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<link type="text/css" rel="stylesheet" href="<s:url value="/js/jquery/themes/base/jquery.ui.all.css"/>">
<link type="text/css" rel="stylesheet" href="<s:url value="/js/jquery/themes/redmond/jquery-ui-1.8.16.custom.css"/>">
<link type="text/css" rel="stylesheet" href="<s:url value="/css/calendar-blue2.css"/>"></link>
<%-- <link type="text/css" rel="stylesheet" href="<s:url value="/css/design.css"/>"> --%>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery-1.6.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/ui/jquery.ui.all.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.dynDateTimeMinGo.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/calendar-big5.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/additional-methods.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/messages_tw.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.blockUI.js"/>"></script>
<script type="text/javascript">
var _cp = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript" src="<s:url value='/js/common.js?v=20121031'/>" ></script>
<script type="text/javascript" src="<s:url value='/js/niiValidate.js?v=20121030'/>" ></script>
</head>
<body style="background-color: black">
	<table class="wrap" border="2" align="center">
		<tr class="header" style="height: 100;">
			<td colspan="2">
				<tiles:insertAttribute name="header" />
			</td>
		</tr>
		<tr class="layout" style="height: 400px;" bgcolor="#a9a9a9">
			<td class="leftbg" style="vertical-align: top;width: 160px;" bgcolor="White"><tiles:insertAttribute name="menu" /></td>
			<td style="vertical-align: top;width: 620px;" class="form" id="tilesBody" bgcolor="yellow">
				<tiles:insertAttribute name="body" />
			</td>
		</tr>
		<tr style="height: 68px;" align="center">
			<td colspan="2" bgcolor="#a9a9a9"><tiles:insertAttribute name="footer" /></td>
		</tr>
	</table>
</body>
</html>