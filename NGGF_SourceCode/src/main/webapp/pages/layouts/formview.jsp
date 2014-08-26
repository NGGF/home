<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:getAsString name="title" /></title>
<!-- jQuery base -->
<link type="text/css" rel="stylesheet" href="<s:url value="/js/jquery/themes/base/jquery.ui.all.css"/>" />
<link type="text/css" rel="stylesheet" href="<s:url value="/js/jquery/themes/redmond/jquery-ui-1.8.16.custom.css"/>" />
<script type="text/javascript" src="<s:url value="/js/jquery/jquery-1.6.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/ui/jquery.ui.all.js"/>"></script>

<!-- jQuery plugin -->
<link type="text/css" rel="stylesheet" href="<s:url value="/css/calendar-blue2.css"/>"></link>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.json-2.3.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.validate.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/additional-methods.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/messages_tw.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.blockUI.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/jquery.dynDateTimeMinGo.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/jquery/calendar-big5.js"/>"></script>

<!-- DHTMLX Menu -->
<link type="text/css" rel="stylesheet" href="<s:url value="/js/dhtmlxMenu/skins/dhtmlxmenu_evta.css"/>" />
<script type="text/javascript" src="<s:url value="/js/dhtmlxMenu/dhtmlxcommon.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxMenu/dhtmlxmenu.js"/>"></script>

<!-- DHTMLX GRID -->
<link type="text/css" rel="STYLESHEET" href="<s:url value="/js/dhtmlxGrid/dhtmlxgrid.css"/>">
<link type="text/css" rel="STYLESHEET" href="<s:url value="/js/dhtmlxGrid/ext/dhtmlxgrid_pgn_bricks.css"/>" />
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/dhtmlxgrid.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/dhtmlxgridcell.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/excells/dhtmlxgrid_excell_cntr.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/excells/dhtmlxgrid_excell_link.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/excells/dhtmlxgrid_excell_acheck.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/ext/dhtmlxgrid_nxml.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/ext/dhtmlxgrid_drag.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/ext/dhtmlxgrid_pgn.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/dhtmlxGrid/ext/dhtmlxgrid_splt.js"/>"></script>

<link type="text/css" rel="stylesheet" href="<s:url value="/css/design.css"/>">
<link type="text/css" rel="stylesheet" href="<s:url value="/css/action.css"/>">
<script type="text/javascript">
var jQ = jQuery.noConflict();
var _cp = '${pageContext.request.contextPath}';
function alert(msg, func) {
	jQ("#dialog-message p").text(msg);
	jQ("#dialog-message").dialog({
		resizable: false,
		modal: true,
		title: "系統訊息",
		buttons: {
			Ok: function() {
				jQ(this).dialog("close");
			}
		},
		close: function(event, ui) { 
			if (func) {
				func.call();
			}
		}
	});
}
</script>
<script type="text/javascript" src="<s:url value="/js/action.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/common.js?v=20121031"/>"></script>
<script type="text/javascript" src="<s:url value="/js/niiValidate.js?v=20121030"/>"></script>
</head>
<body>
	<table style="width: 1024px; height: 768px; margin: 0px auto 0px auto;">
		<tr>
			<td class="leftbg" style="width: 10px;"><tiles:insertAttribute name="menu" /></td>
			<td style="vertical-align: top;"><tiles:insertAttribute name="body" /></td>
		</tr>
	</table>
</body>
</html>