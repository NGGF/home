<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type='text/javascript' src="<s:url value='/js/jquery/treeview/demo.js' />"></script>
<script type='text/javascript' src="<s:url value='/js/jquery/treeview/jquery.cookie.js' />"></script>
<script type='text/javascript' src="<s:url value='/js/jquery/treeview/jquery.treeview.js' />"></script>
<link rel="stylesheet" type="text/css" href="<s:url value="/css/jquery/treeview/jquery.treeview.css"/>">

<script type="text/javascript">
	$(document).ready(function(){
		$("#menutree").treeview({
			animated: "fast",
			collapsed: true,
			unique: true,
			persist: "cookie",
			toggle: function() {
				window.console && console.log("%o was toggled", this);
			}
		});
	});
</script>

<ul id="menutree" class="menutree">
	<li><span class="folder">管理系統</span>
		<ul>
			<li>
				<span class="folder">人員管理</span>
				<ul>
					<li>
						<span>
							<a href="<s:url value='/User/UserAdd' />">將腳新增</a>
						</span>
					</li>
					<li>
						<span>
							<a href="<s:url value='/User/UserQuery' />">將腳查詢</a>
						</span>
					</li>
				</ul>
			</li>
			<li>
				<span class="folder">神將管理</span>
				<ul>
					<li>
						<span>
							<a href="<s:url value='/GodGenerals/GodGeneralsAdd' />">神將新增</a>
						</span>
					</li>
					<li>
						<span>
							<a href="<s:url value='/GodGenerals/GodGeneralsQuery' />">神將查詢</a>
						</span>
					</li>
				</ul>
			</li>
			<li>
				<span class="folder">出軍活動管理</span>
				<ul>
					<li>
						<span>
							<a href="<s:url value='/Activity/ActivityAdd' />">出軍新增</a>
						</span>
					</li>
					<li>
						<span>
							<a href="#">出軍查詢</a>
						</span>
					</li>
				</ul>
			</li>
		</ul>
	</li>
</ul>

<!-- <div class="menu"> -->
<%-- 	<a style="cursor: pointer;text-decoration: none;" href="<s:url value='/User/UserAdd' />"><font>人員新增</font></a><br/> --%>
<%-- 	<a style="cursor: pointer;text-decoration: none;" href="<s:url value='/GodGenerals/GodGeneralsAdd' />"><font>神將新增</font></a><br/> --%>
<%-- 	<a style="cursor: pointer;text-decoration: none;" href="<s:url value='/Activity/ActivityAdd' />"><font>出軍活動新增</font></a><br/>	 --%>
<!-- </div> -->
