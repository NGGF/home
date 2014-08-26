<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="activityAddForm" action="ActivityAdd" method="post">
	<table class="colTb" style="width: 90%" border="2" bgcolor="yellow" align="center">
		<caption><font size="16">內湖神將聯誼會備記卡</font></caption>
		<tr>
			<th width="20%">集合地點：</th>
			<td width="30%">
				
			</td>
			<th width="20%">進場時間：</th>
			<td width="30%">
				
			</td>
		</tr>
		<tr>
			<th width="20%">出軍日期：</th>
			<td>
				國曆_年_月_日<br/>
				農曆_年_月_日
			</td>
		</tr>
		<tr>
			<th>出軍地點：</th>
		</tr>
		<tr>
			<th>出軍神將名單：</th>
		</tr>
		<tr>
			<th width="20%">出軍人數：</th>
			<td>
				
			</td>
			<th width="20%">接洽人：</th>
			<td>
				陳芒果
			</td>
		</tr>
	</table>
	<div>------------------------------------------以上</div>
	<div align="center">
		<br/>
		<input type="button" value="列印" onclick="">
	</div>
</form>
<script type="text/javascript">
jQuery(function($){
	//小日曆
	$("#birthDay").dynDateTime({
		button : ".next()"
	});
	//自動為日期加斜線
	$('#birthDay').blur(function(){
		if(/^\d{8}$/.test($(this).val()) == true){
			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
		}
	});
});

function printNote(){
	$('#activityAddForm').attr('NoteCard').trigger('submit');
}
</script>