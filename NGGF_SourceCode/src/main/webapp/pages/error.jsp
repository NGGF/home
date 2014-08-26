<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>系統異常錯誤</h1>

<table class="rowTb">
	<tr>
		<th>錯誤訊息</th>
		<td>
            <s:property value="exception.message" />
            <!-- for invalid token -->
            <s:iterator value="actionErrors">
                <br/>
                <s:property/>
            </s:iterator>
        </td>
	</tr>
	<tr>
		<td colspan="2" style="max-width: 600px;">
			<input type="button" id="showDetail" value="+" />
			<p id="messageDetail">
				<s:property value="%{exceptionStack}" />
			</p>
		</td>
	</tr>
</table>

<div class="aCenter">
	<form action="index">
		<input type="submit" value="返回首頁" />
	</form>
</div>

<script>
	$(document).ready(function() {
		$("#messageDetail").hide();
		$("#showDetail").click(function() {
			if ("+" == $(this).val()) {
				$("#messageDetail").show();
				$(this).val("-");
			} else {
				$("#messageDetail").hide();
				$(this).val("+");
			}
		});
	});
</script>
