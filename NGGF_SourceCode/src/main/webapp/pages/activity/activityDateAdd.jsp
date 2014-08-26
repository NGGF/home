<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<form id="activityDateAddForm" action="ActivityAdd!addActivity" method="post">
	<table class="colTb" style="width: 90%" border="2" bgcolor="yellow" align="center">
		<caption>出軍活動新增</caption>
		<tr>
			<th width="20%">出軍起日</th>
			<td>
				<input type="text" name="activityStartDay" id="activityStartDay" /><img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" />
			</td>
			<th width="20%">出軍迄日</th>
			<td>
				<input type="text" name="activityEndDay" id="activityEndDay" /><img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" />
			</td>
		</tr>
		<tr>
			<th width="20%">集合地點</th>
			<td>
				<input type="text"/>
			</td>
			<th width="20%">進場時間</th>
			<td>
				<input type="text" value="05:30" />
			</td>
		</tr>
		<tr>
			<th>出軍地點</th>
			<td colspan="3">
				<textarea rows="3" cols="70">
				</textarea>
			</td>
		</tr>
	</table>
	<div align="center">
		<br/>
		<input type="reset" value="清除">
		<input type="button" value="繼續" onclick="activityAdd()">
	</div>
</form>
<script type="text/javascript">
jQuery(function($){
	//小日曆
	$("#activityStartDay").dynDateTime({
		button : ".next()"
	});
	$("#activityEndDay").dynDateTime({
		button : ".next()"
	});
	//自動為日期加斜線
	$('#activityStartDay').blur(function(){
		if(/^\d{8}$/.test($(this).val()) == true){
			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
		}
	});
	$('#activityEndDay').blur(function(){
		if(/^\d{8}$/.test($(this).val()) == true){
			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
		}
	});
});

function activityAdd(){
	var startDay = $.trim($('#activityStartDay').val());
	var endDay = $.trim($('#activityEndDay').val());
	if(startDay == '' || endDay == ''){
		niiMessage('請輸入起迄日');
		return;
	}
	$('#activityDateAddForm').attr('action','ActivityAdd!addActivity').trigger('submit');
}
</script>