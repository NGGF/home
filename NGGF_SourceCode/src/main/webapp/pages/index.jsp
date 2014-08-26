<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table>
	<tr>
		<td>
			<a href="javascript:getImage_1()">
				<img height="400px" width="280px" src="images/bg/god_1.jpg">
			</a>
			<div align="center"><font color="red">趙光明</font></div>
		</td>
		<td>
			<a href="javascript:getImage_2()">
			<img height="400px" width="280px" src="images/bg/god_2.jpg">
			</a>
			<div align="center"><font color="red">玄天上帝</font></div>
		</td>
		<td>
			<a href="javascript:getImage_3()">
			<img height="400px" width="280px" src="images/bg/god_3.jpg">
			</a>
			<div align="center"><font color="red">康席</font></div>
		</td>
	</tr>
</table>
<script type="text/javascript">
	var vWindowWidth = $(window).width();
	var vWindowHeight = $(window).height();
	var vWidth = (vWindowWidth < 440 ? vWindowWidth : 440);
	var vHeight = (vWindowHeight < 530 ? vWindowHeight : 530);
	function getImage_1(){
		$.blockUI({ message: 
			"<div class='aCenter' style='overflow:auto;background-color: yellow'>" +
				"<img width='380px' src='images/bg/god_1.jpg'>" +
				"<br/>" +
				"<input type='button' class='btnbg' value='關閉' onclick='cancel()' >" +
			"</div>"
			,
			css: {
				top: (vWindowHeight - vHeight) / 2 + "px",
				left: (vWindowWidth - vWidth) / 2 + "px",
				width: vWidth + "px",
				height: vHeight + "px",
				cursor: null
			}	
		});
	}
	function getImage_2(){
		$.blockUI({ message: 
			"<div class='aCenter' style='overflow:auto;background-color: yellow'>" +
				"<img width='380px' src='images/bg/god_2.jpg'>" +
				"<br/>" +
				"<input type='button' class='btnbg' value='關閉' onclick='cancel()' >" +
			"</div>"
			,
			css: {
				top: (vWindowHeight - vHeight) / 2 + "px",
				left: (vWindowWidth - vWidth) / 2 + "px",
				width: vWidth + "px",
				height: vHeight + "px",
				cursor: null
			}	
		});
	}
	function getImage_3(){
		$.blockUI({ message: 
			"<div class='aCenter' style='overflow:auto;background-color: yellow'>" +
				"<img width='380px' src='images/bg/god_3.jpg'>" +
				"<br/>" +
				"<input type='button' class='btnbg' value='關閉' onclick='cancel()' >" +
			"</div>"
			,
			css: {
				top: (vWindowHeight - vHeight) / 2 + "px",
				left: (vWindowWidth - vWidth) / 2 + "px",
				width: vWidth + "px",
				height: vHeight + "px",
				cursor: null
			}	
		});
	}
	function cancel(){
		$.unblockUI();
	}
</script>