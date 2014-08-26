<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<font color="red"> <s:include value="/pages/msg.jsp"></s:include> </font>
<form id="godGeneralsAddForm" action="godGeneralsAdd" method="post">
	<table class="colTb" style="width: 40%" border="2" bgcolor="yellow" align="center">
		<caption>神將新增</caption>
		<tr>
			<th width="40%">神將名稱</th>
			<td>
				<input type="text" name="generalsDo.generalsName" />
			</td>
		</tr>
		<tr>
			<th width="40%">承製經費</th>
			<td>
				<input type="text" name="generalsDo.makeCost" />
			</td>
		</tr>
		<tr>
			<th width="40%">承製日期</th>
			<td>
				<input type="text" id="makeDate" name="generalsDo.makeDate" />
				<img style='cursor: pointer;' align="top" src="<s:url value="/js/jquery/themes/calendar-icon.png"/>" />
			</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<th>照片上傳</th> -->
<!-- 			<td> -->
<!-- 				<input type="file" > -->
<!-- 			</td> -->
<!-- 		</tr> -->
	</table>
	<div align="center">
		<br/>
		<input type="reset" value="清除">
		<input type="button" value="送出" onclick="check()">
	</div>
</form>
<script type="text/javascript">
jQuery(function($){
	//小日曆
	$("#makeDate").dynDateTime({
		button : ".next()"
	});
	//自動為日期加斜線
	$('#makeDate').blur(function(){
		if(/^\d{8}$/.test($(this).val()) == true){
			jQuery(this).val($(this).val().substr(0, 4) + "/" + $(this).val().substr(4, 2) + "/" + $(this).val().substr(6, 2));
		}
	});
});

function check(){
	$('#godGeneralsAddForm').attr('action','GodGeneralsAdd!inserGodGenerals').trigger('submit');
}
</script>