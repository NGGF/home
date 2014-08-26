<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="showMenu" style="height: 700px; background-color: #5C9CCC;">&gt;</div>
<div id="hideMenu" class="menu" style="position: absolute; z-index: 100; height: 700px; background-color: #5C9CCC; overflow: auto;"></div>

<script>
	jQuery(document).ready(function($) {
		$("#showMenu").mouseenter(function() {
			$("#hideMenu").show();
			$("#showMenu").hide();
		});

		$("#hideMenu").mouseleave(function() {
			$("#showMenu").show();
			$("#hideMenu").hide();
		});

		// 依 jSon 字串組成功能選單
		if ('' != '${menuTree}') {
			var jSon = ${menuTree};
			var html;
			if (jSon.length > 0) {
				html = '<ul>';
				for ( var i = 0; i < jSon.length; i++) {
					html += getHtmlByJson(jSon[i]);
				}
				html += '</ul>';
			}
			$(".menu").html(html);
		}

		// 隱藏所有功能選單
		$(".menu ul ul").hide();

		// 若有已點選之功能選單 ID，展開該選單
		if ("" != "${menuTreeId}") {
			var $item = $("#menuTree_${menuTreeId}");
			while ($item.attr("class") != "menu") {
				$item = $item.parent();
				$item.show();
			}
		}

		// 功能選單點擊反應
		$(".menu li>a").click(function() {
			var href = $(this).attr("href");
			if (href && href != "#") {
				var warPath = "${warPath}";
				if ("" == warPath || warPath != href.substr(0, warPath.length)) {
					href += "?m=" + $(this).attr("id").substr(9);
					href += "&t=${userToken}";
				}
				$(this).attr("href", href);
				return true;
			} else {
				var $item = $(this);
                var isHidden = $item.next().is(":hidden");
				$(".menu ul ul").hide();
				if (isHidden)
					$item.next().show();
				while ($item.attr("class") != "menu") {
					$item = $item.parent();
					$item.show();
				}
			}
			return false;
		});

		$("#hideMenu").hide();
	});

	// 遞迴組成功能選單
	function getHtmlByJson(json) {
		var html = '<li>';
		if (json.t) {
			html += '<a id="menuTree_' + json.t + '" href="' + json.u + '" class="pageItem">';
		} else {
			html += '<a href="#">';
		}
		html += json.n + '</a>';
		if (json.c && json.c.length > 0) {
			html += '<ul>';
			for ( var i = 0; i < json.c.length; i++) {
				html += getHtmlByJson(json.c[i]);
			}
			html += '</ul>';
		}
		html += '</li>';
		return html;
	}
</script>
