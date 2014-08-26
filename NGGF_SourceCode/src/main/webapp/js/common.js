/*
 * 此為common.js放置各模組可共通使用的function, 故請同步修改iim, imm, fee模組之common.js 
 * function命名時,請儘量特殊
 */
;(function($) {
	/*
	 * jQuery Plug-in niiCode 代碼查詢共用元件
	 * -----------------------------------------------------------------------------
	 * 使用方式 1：.niiCode(category, code)
	 * 描述：將代碼中文說明帶入所選的 tag 中
	 * category: 代碼類別
	 * code: 代碼
	 * -----------------------------------------------------------------------------
	 * 使用方式 2：.niiCode(options)
	 * 描述：將所選的 input:text 綁定事件，輸入代碼後 onblur 會在右邊顯示代碼中文說明，
	 *     將查詢代碼按鈕綁定事件，會顯示 blockUI 供使用者查詢代碼。
	 * 前置準備：要使用此元件的網頁要有以上的代碼
	 * 		  <input type="text" id="xxx" name="xxx" data-category="xxx" data-categoryType="xxx"
	 * 			data-filter1="" data-filter2="" data-filter3="" data-order="" />
	 *        <input type="button" value="xxx"/>
	 *        data-category: 放置代碼類別
	 *        data-categoryType: 放置代碼中文說明類別。1:顯示說明1。2:顯示說明2。3:顯示說明1+說明2。
	 *        data-filter1: 過濾條件1。optional。
	 *        data-filter2: 過濾條件2。optional。
	 *        data-filter3: 過濾條件3。optional。
	 *        data-order: 是否用資料庫表格欄位 OrderList 排序。true or false。default false。
	 * options:代碼查詢共用元件選項，data type: object。
	 * 		   options.blockContainer: 此選項為當要綁定的 input 已經在 blockUI 上所使用。
	 *                                 給定目前 blockUI container 的 jQuery selector，
	 *                                 ex: var options = {blockContainer: "#blockUIDiv"};
	 *         options.initQuery: 是否要在查詢頁面(blockUI)一打開即查詢所有代碼，boolean，default true。
	 * 額外資料：若要檢核代碼是否為共用元件所查出的正確代碼 -> $(代碼input).data("codeValid")，回傳 boolean
	 * 		     若要取得代碼名稱 -> $(代碼input).data("codeName")，回傳 string
	 */
	$.fn.niiCode = function() {
		if (arguments.length < 2) {
			var options = $.extend({
				initQuery: true
		    }, arguments[0] || {});
			return this.each(function() {
				$(this).blur(function() {
					var $text = $(this).data({
						codeValid: false,
						codeName: ""
					});
					if ($.trim($text.val()) == "") {
						showCodeDesc($text, "", false);
						return;
					}
					var upperCase = "";
					upperCase = $text.val().toUpperCase();
					$text.val(upperCase);
					$.getJSON(_cp + '/codeAction!queryCode', {
						"condition.category": $text.attr("data-category"),
						"categoryType": $text.attr("data-categoryType"),
						"code": $text.val(),
						"condition.filter1": convertStringData($text.attr("data-filter1"), "string"),
						"condition.filter2": convertStringData($text.attr("data-filter2"), "string"),
						"condition.filter3": convertStringData($text.attr("data-filter3"), "string"),
						"condition.sortByField": convertStringData($text.attr("data-order"), "boolean")
					},
					function(result, status, xhr) {
						if (status != "success") {
							showCodeDesc($text, "發生錯鋘，請洽系統管理員!", true);
						} else if (result == null) {
							showCodeDesc($text, "無此代碼!", true);
						} else {
							showCodeDesc($text, result, false);
						}
					});
				});
				var $text = $(this).data({
					codeValid: false,
					codeName: ""
				});
				$(this).next(":button").click(function() {
					var vWindowWidth = $(window).width();
					var vWindowHeight = $(window).height();
					var vWidth = (vWindowWidth < 640 ? vWindowWidth : 640);
					var vHeight = (vWindowHeight < 480 ? vWindowHeight : 480);
					var blockUIOptions = {
						message: "<div><h3 class='aCenter'>" + $(this).parent().prev("th").text() + "代碼查詢" + 
							"<input type='text' id='niiCodeQueryText'/><input type='button' class='btnbg' id='niiCodeQueryButton' value='查詢'/><input type='button' class='btnbg' id='niiCodeCloseButton' value='關閉'/></h3>" + 
							"<div class='aCenter form' style='width: 640px; height: 440px; overflow: auto;' id='niiCodeQueryResultDiv'><div></div>",
						css: {
							top: (vWindowHeight - vHeight) / 2 + "px",
							left: (vWindowWidth - vWidth) / 2 + "px",
							width: vWidth + "px",
							height: vHeight + "px",
							cursor: null
						}
					};
					options.blockContainer ? $(options.blockContainer).block(blockUIOptions) : $.blockUI(blockUIOptions);
					$("#niiCodeQueryButton").click(function() {
						$("#niiCodeQueryResultDiv").html("<img src='" + _cp + "/images/loading.gif'/>");
						$.post(_cp + '/codeAction!queryCodeList', {
							"condition.category": $text.attr("data-category"),
							"queryName": convertStringData($("#niiCodeQueryText").val(), "string"),
							"condition.filter1": convertStringData($text.attr("data-filter1"), "string"),
							"condition.filter2": convertStringData($text.attr("data-filter2"), "string"),
							"condition.filter3": convertStringData($text.attr("data-filter3"), "string"),
							"condition.sortByField": convertStringData($text.attr("data-order"), "boolean")
						},
						function(result, status, xhr) {
							if (status != "success") {
								niiMessage("發生錯鋘，請洽系統管理員!");
							} else {
								showQueryResult($text, result,$text.attr("data-categoryType"));
							}
						}, "json");
					});
					$("#niiCodeCloseButton").click(function() {
						unblock(options);
					});
					if (options.initQuery) {
						$("#niiCodeQueryButton").click();
					}
				});
				if ($.trim($(this).val()) != "") {
					$(this).blur();
				}
			});
		} else if (arguments.length == 2) {
			var category = arguments[0];
			var code = arguments[1];
			return this.each(function() {
				$(this).load(_cp + '/codeAction #container', {category: category, code: code});
			});
		}
		function showCodeDesc($text, desc, isError) {
			if ($text.nextAll("span.niiCodeSpan").length == 0) {
				var id = ($text.attr("id") || $text.attr("name") || $.now()) + "CodeDescSpan";
				$text.nextAll().last().after("<span id='" + id + "' class='niiCodeSpan' style='color: " + (isError ? "red" : "blue") + ";'>" + desc + "</span>");
			} else {
				$text.nextAll("span.niiCodeSpan").text(desc).css("color", isError ? "red" : "blue");
			}
			$text.data({
				codeValid: !isError,
				codeName: isError ? "" : desc
			}).focusout();
			$text.change();
		}
		function showQueryResult($text, codeList,categoryType) {
			if (codeList.length == 0) {
				$("#niiCodeQueryResultDiv").html("<label class='error'>無此代碼!</label>");
				return;
			}
			var html = [];
			html.push("<table class='colTb' style='width: 95%;'>");
			html.push("<tr><th>代碼</th><th>代碼名稱</th><th>功能列</th></tr>");
			if(categoryType == '2'){
				for (var i = 0; i < codeList.length; i++) {
					html.push("<tr><td>" + codeList[i].code + "</td><td>" + ((codeList[i].comment == null)?'':codeList[i].comment) + "</td><td><input type='button' class='btnbg' value='選擇' data-code='" + codeList[i].code + "' data-code-name='" + ((codeList[i].comment == null)?'':codeList[i].comment) + "'/></td></tr>");
				}
			}else if(categoryType == '3'){
				for (var i = 0; i < codeList.length; i++) {
					html.push("<tr><td>" + codeList[i].code + "</td><td>" + codeList[i].codeName + ((codeList[i].comment == null)?'':codeList[i].comment) + "</td><td><input type='button' class='btnbg' value='選擇' data-code='" + codeList[i].code + "' data-code-name='" + codeList[i].codeName + ((codeList[i].comment == null)?'':codeList[i].comment) + "'/></td></tr>");
				}
			}else{
				for (var i = 0; i < codeList.length; i++) {
					html.push("<tr><td>" + codeList[i].code + "</td><td>" + codeList[i].codeName + "</td><td><input type='button' class='btnbg' value='選擇' data-code='" + codeList[i].code + "' data-code-name='" + codeList[i].codeName + "'/></td></tr>");
				}
			}
			html.push("</table>");
			$("#niiCodeQueryResultDiv").html(html.join("")).find(":button").click(function() {
				$text.val($(this).attr("data-code"));
				showCodeDesc($text, $(this).attr("data-code-name"), false);
				unblock(options);
			});
			
		}
	};
	/*
	 * jQuery Plug-in niiDeptUser 部門使用者查詢共用元件
	 * -----------------------------------------------------------------------------
	 * 使用方式：.niiDeptUser(options)
	 * 描述：將所選的 input:text 綁定事件，輸入代碼後 onblur 會在右邊顯示代碼中文說明，
	 *     將查詢代碼按鈕綁定事件，會顯示 blockUI 供使用者查詢代碼。
	 * 前置準備：要使用此元件的網頁要有以下的代碼
	 * 		  <input type="text" id="xxx" name="xxx" data-group-id="xxx"/><!-- 部門代碼欄位 -->
	 * 		  data-group-id: 放置查詢的群組類別，optional。
	 *        <input type="button" value="xxx"/><!-- 部門代碼查詢按鈕 -->
	 *        <input type="text" id="xxx" name="xxx"/><!-- 使用者代碼欄位 -->
	 *        data-userType: 放置查詢的user類別。1:所有使用者。2:只查可指派的承辦員
	 *        <input type="button" value="xxx"/><!-- 使用者代碼查詢按鈕 -->
	 * options:部門使用者查詢選項，optional，data type: object。
	 * 		   options.blockContainer: 此選項為當要綁定的 input 已經在 blockUI 上所使用。
	 *                                 給定目前 blockUI container 的 jQuery selector，
	 *                                 ex: var options = {blockContainer: "#blockUIDiv"};
	 *         options.initQuery: 是否要在查詢頁面(blockUI)一打開即查詢所有代碼，boolean，default true。
	 *         options.userOnly: 是否只要顯示使用者的元件，boolean，default false。
	 *                           注意： 部門的 input 必須要先給定 value，不然會造成錯誤。
	 *                           	    若部門的 value 設定為-1，則查詢時不鎖定部門。
	 * 額外資料：若要檢核部門代碼是否為共用元件所查出的正確代碼 -> $(部門代碼input).data("deptValid")，回傳 boolean
	 * 		     若要取得部門名稱 -> $(部門代碼input).data("deptName")，回傳 string
	 *		     若要檢核使用者代碼是否為共用元件所查出的正確代碼 -> $(使用者代碼input).data("userValid")，回傳 boolean
	 * 		     若要取得使用者名稱 -> $(使用者代碼input).data("userName")，回傳 string
	 * 
	 * 使用方式：.niiDeptUser('destroy')
	 * 描述：取消部門使用者查詢共用元件
	 */
	$.fn.niiDeptUser = function() {
		if (arguments[0] === "destroy") {
			return this.each(function() {
				var $deptText = $(this).unbind(".niiDeptUser");
				var $deptButton = $deptText.next(":button").unbind(".niiDeptUser");
				var $userText = $deptButton.next(":text").unbind(".niiDeptUser");
				var $userButton = $userText.next(":button").unbind(".niiDeptUser").prop("disabled", false);
				$userButton.nextAll("span").remove();
			});
		}
		var options = $.extend({
			initQuery: true,
			userOnly: false
	    }, arguments[0] || {});
		return this.each(function() {
			var $deptText = $(this);
			var $deptButton = $deptText.next(":button");
			var $userText = $deptButton.next(":text");
			var $userButton = $userText.next(":button").prop("disabled", true);
			var deptId = ($deptText.attr("id") || $deptText.attr("name") || $.now()) + "DeptDescSpan";
			$userButton.after("<span id='" + deptId + "'></span>");
			var $deptSpan = $("#" + deptId.replace(".", "\\."));
			var userId = ($userText.attr("id") || $userText.attr("name") || $.now()) + "UserDescSpan";
			$deptSpan.after("<span id='" + userId + "'></span>");
			var $userSpan = $("#" + userId.replace(".", "\\."));
			var upperCase = "";
			$deptText.bind("blur.niiDeptUser", function() {
				$deptText.data({deptUserResult: false, deptValid: false, deptName: null});
				if ($.trim($deptText.val()) == "") {
					$deptText.val("");
					showDesc($deptSpan, "", false);
					$userText.blur();
					if (options.userOnly) {
						confirmWindow("部門使用者查詢共用元件設定錯誤!");
					}
					return;
				}
				if (options.userOnly && $deptText.val() == "-1") {
					$deptText.data({deptUserResult: true, deptValid: true, deptName: ""});
					$userButton.prop("disabled", false);
					return;
				}
				upperCase = $deptText.val().toUpperCase();
				$deptText.val(upperCase);
				$.getJSON(_cp + '/deptUserAction!queryDept', {
					deptId: upperCase,
					groupId: convertStringData($deptText.attr("data-group-id"), "string")
				},
				function(result, status, xhr) {
					var success;
					var text;
					if (status != "success") {
						success = false;
						text = "發生錯鋘，請洽系統管理員!";
					} else if (result == null) {
						success = false;
						text = "無此部門!";
					} else {
						success = true;
						text = result.deptName;
					}
					$deptText.data({deptUserResult: success, deptValid: success, deptName: success ? text : null}).focusout();
					showDesc($deptSpan, text, success);
					$userText.blur();
					$userButton.prop("disabled", !success);
					if (options.userOnly && !success) {
						confirmWindow("部門使用者查詢共用元件設定錯誤!");
					}
				});
			}).blur();
			$deptButton.bind("click.niiDeptUser", function() {
				var vWindowWidth = $(window).width();
				var vWindowHeight = $(window).height();
				var vWidth = (vWindowWidth < 640 ? vWindowWidth : 640);
				var vHeight = (vWindowHeight < 480 ? vWindowHeight : 480);
				var blockUIOptions = {
					message: "<div><h3 class='aCenter'>" + $(this).parent().prev("th").text() + "部門代碼查詢" + 
						"<input type='text' id='niiDeptUserQueryText'/><input type='button' class='btnbg' id='niiDeptUserQueryButton' value='查詢'/><input type='button' class='btnbg' id='niiDeptUserCloseButton' value='關閉'/></h3>" + 
						"<div class='aCenter form' style='width: 640px; height: 440px; overflow: auto;' id='niiDeptUserQueryResultDiv'><div></div>",
					css: {
						top: (vWindowHeight - vHeight) / 2 + "px",
						left: (vWindowWidth - vWidth) / 2 + "px",
						width: vWidth + "px",
						height: vHeight + "px",
						cursor: null
					}
				};
				options.blockContainer ? $(options.blockContainer).block(blockUIOptions) : $.blockUI(blockUIOptions);
				$("#niiDeptUserQueryButton").click(function() {
					$("#niiDeptUserQueryResultDiv").html("<img src='" + _cp + "/images/loading.gif'/>");
					$.post(_cp + '/deptUserAction!queryDeptList', {
						deptName: $("#niiDeptUserQueryText").val(),
						groupId: convertStringData($deptText.attr("data-group-id"), "string")
					},
					function(result, status, xhr) {
						if (status != "success") {
							niiMessage("發生錯鋘，請洽系統管理員!");
						} else {
							if (result.length == 0) {
								$("#niiDeptUserQueryResultDiv").html("<label class='error'>無此部門代碼!</label>");
								return;
							}
							var html = [];
							html.push("<table class='colTb' style='width: 95%;'>");
							html.push("<tr><th>部門代碼</th><th>部門名稱</th><th>功能列</th></tr>");
							for (var i = 0; i < result.length; i++) {
								html.push("<tr><td>" + result[i].deptId + "</td><td>" + result[i].deptName + "</td><td><input type='button' class='btnbg' value='選擇' data-dept-id='" + result[i].deptId + "' data-dept-name='" + result[i].deptName + "'/></td></tr>");
							}
							html.push("</table>");
							$("#niiDeptUserQueryResultDiv").html(html.join("")).find(":button").click(function() {
								$deptText.val($(this).attr("data-dept-id")).data({deptUserResult: true, deptValid: true, deptName: $(this).attr("data-dept-name")}).focusout();
								showDesc($deptSpan, $(this).attr("data-dept-name"), true);
								$userText.blur();
								$userButton.prop("disabled", false);
								unblock(options);
							});
						}
					}, "json");
				});
				$("#niiDeptUserCloseButton").click(function() {
					unblock(options);
				});
				if (options.initQuery) {
					$("#niiDeptUserQueryButton").click();
				}
			});
			$userText.bind("blur.niiDeptUser", function() {
				$userText.data({userValid: false, userName: null});
				if ($.trim($userText.val()) == "") {
					$userText.val("");
					showDesc($userSpan, "", false);
					return;
				}
				if (!$deptText.data("deptUserResult")) {
					showDesc($userSpan, "請先輸入正確的部門代碼!", false);
					return;
				}
				upperCase = $userText.val().toUpperCase();
				$userText.val(upperCase);
				$.getJSON(_cp + '/deptUserAction!queryUser', {
					deptId: $deptText.val(),
					userId: upperCase,
					userType: $userText.attr("data-userType")
				},
				function(result, status, xhr) {
					var success;
					var text;
					if (status != "success") {
						success = false;
						text = "發生錯鋘，請洽系統管理員!";
					} else if (result == null) {
						success = false;
						text = "無此使用者!";
					} else {
						success = true;
						text = result.userName;
					}
					$userText.data({userValid: success, userName: success ? text : null}).focusout();
					showDesc($userSpan, text, success);
				});
			}).blur();
			$userButton.bind("click.niiDeptUser", function() {
				if (!$deptText.data("deptUserResult")) {
					niiMessage("請先輸入正確的部門代碼!");
					return;
				}
				var vWindowWidth = $(window).width();
				var vWindowHeight = $(window).height();
				var vWidth = (vWindowWidth < 640 ? vWindowWidth : 640);
				var vHeight = (vWindowHeight < 480 ? vWindowHeight : 480);
				var blockUIOptions = {
					message: "<div><h3 class='aCenter'>" + $(this).parent().prev("th").text() + "使用者代碼查詢" + 
						"<input type='text' id='niiDeptUserQueryText'/><input type='button' class='btnbg' id='niiDeptUserQueryButton' value='查詢'/><input type='button' class='btnbg' id='niiDeptUserCloseButton' value='關閉'/></h3>" + 
						"<div class='aCenter form' style='width: 640px; height: 440px; overflow: auto;' id='niiDeptUserQueryResultDiv'><div></div>",
					css: {
						top: (vWindowHeight - vHeight) / 2 + "px",
						left: (vWindowWidth - vWidth) / 2 + "px",
						width: vWidth + "px",
						height: vHeight + "px",
						cursor: null
					}
				};
				options.blockContainer ? $(options.blockContainer).block(blockUIOptions) : $.blockUI(blockUIOptions);
				$("#niiDeptUserQueryButton").click(function() {
					$("#niiDeptUserQueryResultDiv").html("<img src='" + _cp + "/images/loading.gif'/>");
					$.post(_cp + '/deptUserAction!queryUserList', {
						deptId: $deptText.val(),
						userName: $("#niiDeptUserQueryText").val(),
						userType: $userText.attr("data-userType")
					},
					function(result, status, xhr) {
						if (status != "success") {
							niiMessage("發生錯鋘，請洽系統管理員!");
						} else {
							if (result.length == 0) {
								$("#niiDeptUserQueryResultDiv").html("<label class='error'>無此使用者代碼!</label>");
								return;
							}
							var html = [];
							html.push("<table class='colTb' style='width: 95%;'>");
							html.push("<tr><th>使用者代碼</th><th>使用者名稱</th><th>功能列</th></tr>");
							for (var i = 0; i < result.length; i++) {
								html.push("<tr><td>" + result[i].userId + "</td><td>" + result[i].userName + "</td><td><input type='button' class='btnbg' value='選擇' data-user-id='" + result[i].userId + "' data-user-name='" + result[i].userName + "'/></td></tr>");
							}
							html.push("</table>");
							$("#niiDeptUserQueryResultDiv").html(html.join("")).find(":button").click(function() {
								$userText.val($(this).attr("data-user-id")).data({userValid: true, userName: $(this).attr("data-user-name")}).focusout();
								showDesc($userSpan, $(this).attr("data-user-name"), true);
								unblock(options);
							});
						}
					}, "json");
				});
				$("#niiDeptUserCloseButton").click(function() {
					unblock(options);
				});
				if (options.initQuery) {
					$("#niiDeptUserQueryButton").click();
				}
			});
			if (options.userOnly) {
				$deptText.hide();
				$deptButton.hide();
				$deptSpan.hide();
			}
		});
		function showDesc($span, text, success) {
			$span.html(text).css("color", success ? "blue" : "red");
		}
	};
	/*
	 * jQuery Plug-in niiGroupDept 部門群組代碼查詢共用元件
	 * -----------------------------------------------------------------------------
	 * 使用方式 1：.niiGroupDept(options)
	 * 描述：將所選的 input:text 綁定事件，輸入代碼後 onblur 會在右邊顯示部門中文說明，
	 *     將查詢代碼按鈕綁定事件，會顯示 blockUI 供使用者查詢代碼。
	 * 前置準備：要使用此元件的網頁要有以上的代碼
	 * 		  <input type="text" id="xxx" name="xxx" data-groupId="xxx" data-title="群組名稱"/>
	 *        <input type="button" value="xxx"/>
	 *        data-groupId: 放置部門群組Id,若未設定則不設限群組
	 *        data-title:群組名稱 顯示在blockUI的表頭名稱
	 * options:部門群組代碼查詢共用元件選項，data type: object。
	 * 		   options.blockContainer: 此選項為當要綁定的 input 已經在 blockUI 上所使用。
	 *                                 給定目前 blockUI container 的 jQuery selector，
	 *                                 ex: var options = {blockContainer: "#blockUIDiv"};
	 *         options.initQuery: 是否要在查詢頁面(blockUI)一打開即查詢所有代碼，boolean，default true。
	 */
	$.fn.niiGroupDept = function(){
		var options = $.extend({
			initQuery: true
	    }, arguments[0] || {});
		return this.each(function() {
			$(this).blur(function() {
				var $text = $(this);
				if ($.trim($text.val()) == "") {
					showGDeptDesc($text, "", false);
					return;
				}
				var upperCase = "";
				upperCase = $text.val().toUpperCase();
				$text.val(upperCase);
				$.getJSON(_cp + '/groupDeptAction', {
					groupId: ($text.attr("data-groupId") ==undefined)?"":$text.attr("data-groupId"),
					deptId: $text.val()
				},
				function(result, status, xhr) {
					if (status != "success") {
						showGDeptDesc($text, "發生錯鋘，請洽系統管理員!", true);
					} else if (result == null) {
						showGDeptDesc($text, "無此代碼!", true);
					} else {
						showGDeptDesc($text, result, false);
					}
				});
			});
			var $text = $(this);
			$(this).next(":button").click(function() {
				var vWindowWidth = $(window).width();
				var vWindowHeight = $(window).height();
				var vWidth = (vWindowWidth < 640 ? vWindowWidth : 640);
				var vHeight = (vWindowHeight < 480 ? vWindowHeight : 480);
				var blockUIOptions = {
					message: "<div><h3 class='aCenter'>" + 
						( (undefined == $text.attr("data-title"))? $(this).parent().prev("th").text(): $text.attr("data-title")) 
						+ "代碼查詢" + 
						"<input type='text' id='niiGDeptQueryText'/><input type='button' class='btnbg' id='niiGDeptQueryButton' value='查詢'/><input type='button' class='btnbg' id='niiGDeptCloseButton' value='關閉'/></h3>" + 
						"<div class='aCenter form' style='width: 640px; height: 440px; overflow: auto;' id='niiGDeptQueryResultDiv'><div></div>",
					css: {
						top: (vWindowHeight - vHeight) / 2 + "px",
						left: (vWindowWidth - vWidth) / 2 + "px",
						width: vWidth + "px",
						height: vHeight + "px",
						cursor: null
					}
				};
				options.blockContainer ? $(options.blockContainer).block(blockUIOptions) : $.blockUI(blockUIOptions);
				$("#niiGDeptQueryButton").click(function() {
					$("#niiGDeptQueryResultDiv").html("<img src='" + _cp + "/images/loading.gif'/>");
					$.post(_cp + '/groupDeptAction!queryGDeptList', {
						groupId: ($text.attr("data-groupId") ==undefined)?"":$text.attr("data-groupId"),
						queryDeptName: $("#niiGDeptQueryText").val()
					},
					function(result, status, xhr) {
						if (status != "success") {
							niiMessage("發生錯鋘，請洽系統管理員!");
						} else {
							showGDQueryResult($text, result);
						}
					}, "json");
				});
				$("#niiGDeptCloseButton").click(function() {
					unblock(options);
				});
				if (options.initQuery) {
					$("#niiGDeptQueryButton").click();
				}
			});
			if ($.trim($(this).val()) != "") {
				$(this).blur();
			}
		});
		
		function showGDeptDesc($text, desc, isError) {
			if ($text.nextAll("span.niiGDeptSpan").length == 0) {
				var id = ($text.attr("id") || $text.attr("name") || $.now()) + "GDeptDescSpan";
				$text.nextAll().first().after("<span id='" + id + "' class='niiGDeptSpan' style='color: " + (isError ? "red" : "blue") + ";'>" + desc + "</span>");
			} else {
				$text.nextAll("span.niiGDeptSpan").text(desc).css("color", isError ? "red" : "blue");
			}
		}
		
		function showGDQueryResult($text, deptList) {
			if (deptList == undefined || deptList.length == 0) {
				$("#niiGDeptQueryResultDiv").html("<label class='error'>查無資料!</label>");
				return;
			}
			var html = [];
			html.push("<table class='colTb' style='width: 95%;'>");
			html.push("<tr><th>代碼</th><th>部門名稱</th><th>功能列</th></tr>");
			for (var i = 0; i < deptList.length; i++) {
				html.push("<tr><td>" + deptList[i].deptId + "</td><td>" + deptList[i].deptName + "</td><td><input type='button' class='btnbg' value='選擇' data-code='" + deptList[i].deptId + "' data-code-name='" + deptList[i].deptName + "'/></td></tr>");
			}
			html.push("</table>");
			$("#niiGDeptQueryResultDiv").html(html.join("")).find(":button").click(function() {
				$text.val($(this).data("code"));
				showGDeptDesc($text, $(this).data("codeName"), false);
				unblock(options);
			});
			
		}
	};	
	function unblock(options) {
		options.blockContainer ? $(options.blockContainer).unblock() : $.unblockUI();
	}
	function convertStringData(data, convertType) {
		switch (convertType) {
		case "string":
			data = data == null ? "" : $.trim(data);
			break;
		case "boolean":
			data = data == null ? false : ("true" == $.trim(data).toLowerCase() ? true : false);
			break;
		};
		return data;
	}

	/*
	 * jQuery Plug-in niiReceiverIdQry 收件人代碼查詢共用元件
	 * -----------------------------------------------------------------------------
	 * date : 2012/09/18 PM 6
	 * author : pat.guo
	 * 使用方式 1：.niiReceiverIdQry
	 * 描述：將所選的 input:text 綁定事件，輸入代碼後 onblur 會在右邊顯示收件人中文姓名，
	 *     點查詢將查詢代碼按鈕綁定事件，會顯示 blockUI 供使用者查詢代碼。
	 * 前置準備：要使用此元件的網頁要有以下的代碼
	 * 		  <input type="text" id="xxx" name="xxx" />
	 *        <input type="button" value="xxx"/>
	 * 範例:
	 * 		step1:
                <input type="text" id="receiverId" />
                <input type='button' class='btnbg'  value='查詢'/>
                <span style='color:blue'></span>
            step2:
                <script>
                	$("#receiverId").niiReceiverIdQry();
                </script> 
	 */	
	$.fn.niiReceiverIdQry = function(){
		return this.each(function(){	
			$btn = $(this).next(":button")
			$(this).blur(function(){
				var value=$(this).val().trim();
				$btn = $(this).next(":button")		
				$span = $btn.next("span")				
				if(value == ""){
					$span.text("");
					return;
				}
				var upperCase = $(this).val().toUpperCase();
				$(this).val(upperCase);
				value = upperCase;
				$.ajax({
					type : "post",
					dateType : "json",
					url : "/nii_imm/deptUserAction!queryUserByReceiverId?deptId="+value,
					success :function(data){
						if(null != data[0]){
							$span.css("color","blue").text(data[0].userName);
						}else{
							$span.css("color","red").text("查無此收件人ID");
						}					
					}					
				})
			})
			$btn.click(function(){
				$this = $(this).prev("input")
				var vWindowWidth = $(window).width();
				var vWindowHeight = $(window).height();
				var vWidth = (vWindowWidth < 640 ? vWindowWidth : 640);
				var vHeight = (vWindowHeight < 480 ? vWindowHeight : 480);
				var blockUIOptions = {
					message: "<div><h3 class='aCenter'>" + 
						 "收件人代碼查詢  (中文或英文) " + 
						"<input type='text'  id='niiReceiverIdBlockText'/><input type='button' class='btnbg' id='niiReceiverIdBlockQueryBtn' value='查詢'/><input type='button' class='btnbg' id='niiReceiverIdBlockCloseBtn' value='關閉'/></h3>" + 
						"<div class='aCenter form' style='width: 640px; height: 440px; overflow: auto;' id='niiReceiverIdDataList'><div></div>",
					css: {
						top: (vWindowHeight - vHeight) / 2 + "px",
						left: (vWindowWidth - vWidth) / 2 + "px",
						width: vWidth + "px",
						height: vHeight + "px",
						cursor: null
					}
				};				
				$.blockUI(blockUIOptions);	
				$('#niiReceiverIdBlockQueryBtn').bind('click',function(){	
					$("#niiReceiverIdDataList > table").remove();					
					receiverIdQry($this)					
				})
				$('#niiReceiverIdBlockCloseBtn').bind('click',function(){
					unblock()
				})			
			})
			function receiverIdQry($this){					
				var receiverIdValue = $("#niiReceiverIdBlockText").val().trim();
				if(receiverIdValue == ""){
					alert('請輸入資料!')
					return;
				}
				var urlStr="";
				var urls =new Array(2);
				urls[0] = "/nii_imm/deptUserAction!queryUserByReceiverId";
				urls[1] = "/nii_imm/deptUserAction!queryUserByUserName";				
				var reg = new RegExp("\\w")
				if(true==reg.test(receiverIdValue) ? urlStr=urls[0]:urlStr=urls[1] )
				var html = [];			
				$.ajax({				
					type : "post",
					dateType : "json",
					contentTypeString : "utf-8",
					data : {userName:receiverIdValue,deptId:receiverIdValue},					
					url : urlStr,
					beforeSend : function(){
						$('#niiReceiverIdBlockQueryBtn').attr("disabled","disabled")							
					},
					success : function(data){					
						if(null != data[0]){
							html.push("<table class='colTb' style='width: 95%;'>");
							html.push("<tr><th>收件人ID</th><th>收件人姓名</th><th>收件人部門名稱</th><th>收件人部門代碼</th><th>功能選項</th></tr>");
							$(data).each(function(){
								html.push("<tr><td>" + this.userId + "</td><td>" + this.userName + "</td><td>"+ this.deptId + "</td><td>"+ this.deptName + "</td><td>" +
										  "<input type='button' class='btnbg chooseDataUserId' value='選擇'  userId="+this.userId+" userName="+this.userName+" deptId="+this.deptId+" deptName="+this.deptName+"/></td></tr>");								
							})
							html.push("</table>");
							$("#niiReceiverIdDataList").html(html.join(""))							
						}else{
							html.push("<table class='colTb' style='width: 95%;'>");
							html.push("<tr><th>收件人ID</th><th>收件人姓名</th><th>收件人部門名稱</th><th>收件人部門代碼</th><th>功能選項</th></tr>");							
							html.push("<tr><td colspan='5' style='text-align:center'>查無資料</td></tr></table>")							
							$("#niiReceiverIdDataList").html(html.join(""))													
						}
						$(".chooseDataUserId").bind("click",function(){
							$text=$this
							$btn = $text.next(":button")		
							$span = $btn.next("span")
							var userName = $(this).attr("userName");
							var receiverId = $(this).attr("userId")							
							$span.css("color","blue").text("").append(userName)
							$text.val("").attr("value",receiverId)							
							unblock()
						});	
						$('#niiReceiverIdBlockQueryBtn').removeAttr("disabled")							
					},
					error : function(){alert('查詢失敗，請重新查詢!')} 
				  })
			}		
			function unblock(){				
				$.unblockUI()
			}
		})
	}	
})(jQuery);

/*
 * NiiMessage 使用jquery block UI顯示訊息  
 * @param msg
 */
function niiMessage(msg) {
	confirmWindow(msg);
}

/*
 * confirmWindow 之預設參數內容
 */
var $confirmDefaults = {
	title: "提示訊息",
	msg: "",
	btn1name: "確認",
	btn1function: null,
	btn2name: null,
	btn2function: null,
	blockContainer: null,
	autoUnblock: true,
	custHeight: 90
};

/*
 * confirmWindow 使用jquery block UI 產生確認視窗
 * @param opts 傳遞所需之參數，若為字串，則使用預設參數(參數如上方顯示)，字串內容為訊息內容，
 */
function confirmWindow(opts) {
	if (opts) {
		if (typeof opts == 'string') {
			opts = $.extend({}, $confirmDefaults, {msg: opts} || {});
		} else {
			opts = $.extend({}, $confirmDefaults, opts || {});
		}
	} else {
		return;
	}

	var height = [30, opts.custHeight];
	var vHeight = height[0] + height[1] + 40;
	var vWidth = 250;
	var dateLong = "blockBtn" + (new Date().getTime());

	var titleStyle = "text-align: left; color: #000000; background-color: #eeeeee;";
	titleStyle += "height: " + height[0] + "px; font-size: 16px;";

	var messageStyle = "text-align: center; color: #FF0000;";
	messageStyle += "height: " + height[1] / 3 + "px; font-size: 1.2em;";

	var $blockWindow = jQuery("<div/>");
	var $buttonDiv = jQuery("<div/>", {
		'class' : "aCenter",
		style: "height: 40px;text-align:center"
	});

	// 設定 title 樣式與內容
	jQuery("<div/>", {
		text: opts.title,
		style: titleStyle
	}).appendTo($blockWindow); 

	// 設定訊息區樣式與內容
	jQuery("<div/>", {
		style: "height: " + height[1] + "px"
	}).append(jQuery("<div/>", {
		style: "height: " + height[1] / 3 + "px"
	})).append(jQuery("<div/>", {
		html: opts.msg,
		style: messageStyle
	})).appendTo($blockWindow); 

	// 設定功能按鍵樣式與內容
	$buttonDiv.append(jQuery("<input/>", {
		id: dateLong + "-1",
		type: 'button',
		value: opts.btn1name,
		'class' : 'btnbg'
	}));

	if (opts.btn2name != undefined) {
		$buttonDiv.append(jQuery("<input/>", {
			id: dateLong + "-2",
			type: 'button',
			value: opts.btn2name,
			'class' : 'btnbg'
		}));
	}

	$buttonDiv.appendTo($blockWindow); 

	var blockUIOptions = {  
        css: {  
        	top:  ($(window).height() - vHeight) /2 + 'px',   
        	left: ($(window).width() - vWidth) /2 + 'px',  
        	width: vWidth + 'px', 
        	height: vHeight + 'px',  
        },
        message: $blockWindow
	};

	opts.blockContainer ? $(opts.blockContainer).block(blockUIOptions) : $.blockUI(blockUIOptions);

	$("#" + dateLong + "-1").click(function() {
		if (opts.autoUnblock)
			opts.blockContainer ? $(opts.blockContainer).unblock() : $.unblockUI();

		if (opts.btn1function && typeof opts.btn1function == 'function')
			opts.btn1function();
	});

	if (opts.btn2name != undefined) {
		$("#" + dateLong + "-2").click(function() {
			if (opts.autoUnblock)
				opts.blockContainer ? $(opts.blockContainer).unblock() : $.unblockUI();

			if (opts.btn2function && typeof opts.btn2function == 'function')
				opts.btn2function();
		});
	}
}

/*
 * dynDateTime 掛上日期元件
 * @param selector (input text object)   
 */
function dynDateTime(selector) {
	jQuery(selector).dynDateTime({
		button: ".next()"
	});
}

//------------------------------------------------------------------------------
/*
功能:連動式地址
	date : 2012/09/13 PM 6
	author : pat.guo
****************************
20120919 更新
1.選擇縣市下拉選單自動添加值到屬性 dese2(對應db的CodeDataValueDesc2)，codeVal(對應db的CodeDataValue)
2.選擇鄉鎮市區下拉選單自動添加值到屬性 dese2(對應db的CodeDataValueDesc2，也就是郵遞區號)，codeVal(對應db的CodeDataValue)
ps:請自行判斷 縣市 及 鄉鎮市區 的value屬性是否為空值

20120923 更新
1.增加參數判斷

20120923 更新
1.使用者可自訂網頁一載入的初始值(郵遞區號的屬性value，縣市的屬性codeVal，鄉鎮市區的屬性codeVal)，
    程式將會自動抓值。
ps:如果使用者自訂值，又設定參數，程式將會以參數為準，忽略使用者的設定值。

20121004 更新 : Brian Su
1. 資料改由 zipCodeService 取得(一次取回全部365筆)
2. 修正內部取資料方式改為由下列三個Array判斷與產出
	zipCodeArray - 存放ZipCode全部
	cityArray - 存放 City 資料(變動) 
	townShipArray - 存放 townShip 資料(變動)
ZipCode object 屬性有五個 :
	zipCode : 郵遞區號(3)
	zipCityCode : 縣市代碼(5)
	zipCityName : 縣市名稱
	zipTownShipCode : 區代碼
	zipTownShipName : 區名稱

****************************	  	
功能說明:
1. 選擇縣市，帶出鄉政市區
2. 選擇鄉鎮市區，帶出郵遞區號 
3. 參數說明
	============================================
	 參數名稱              是否設定    	型態       說明
	=============================================
	'tag'         true     	物件		 郵遞區號的位子
	'zipCode'     false     字串 	手動設定郵遞區號
	'city'        false 	字串		手動設定縣市
	'township'    false 	字串		手動鄉鎮市區
	'model' :     falwe		字串		預設common，非HyCase不用設定，例如: 'model' : 'HyCase'
	 注意!:如果使用者自訂值，又設定參數，程式將會以參數為準，忽略使用者的設定值。
	=============================================
PS. 網頁上有多個連動式地址也可共用                             
使用方式:     
	step 1.
		    請在網頁上依序建立以下格式格式:分別為 (ㄧ)郵遞區號、(二)縣市、(三)鄉鎮市區
	      PS:請注意!ID名稱一定要定義，不然功能無法正常工作 ，
			  請自行判斷 縣市下拉選單 及 鄉鎮市區下拉選單 的 codeVal屬性 是否為空值   	  	          		
	      (一)<input type="text"  id='XXX' readonly="readonly" style="width:40px" value=''/>
	      (二)<select  desc2="" codeVal=""></select>
	      (三)<select  desc2="" codeVal=""></select>  
	        	 	
	step 2.並在您的jsp底下加入語法
		   ps:XXX為自訂義的 "郵遞區號id"
		   	<script>
              $("#XXX").click(function(){
                  initNiiAddress({
                      'tag':$(this)
                  });
              }).trigger('click');        
		    </script> 	    
*/
function initNiiAddress(objMap){
	var $ = jQuery;
	var option = {
		'tag' :new Object() ,
		'zipCode' : '',
		'city' :'',
		'township' : '',
		'model' : 'common'
	};	

	//郵遞區號
	var $ZipCode ;
	//縣市
	var $City ;
	//鄉鎮市區
	var $Township ;		
	//從DB帶出的cityValue
	var getDBZipCodeValue;
	//從DB帶出的cityValue
	var getDBCityValue;
	//從DB帶出的TownshipValue
	var getDBTownshipValue;
	//暫存
	var zipCodeValTemp;
	// ZipCodeArray
	var zipCodeArray = [];
	// CityArray
	var cityArray = [];
	// TownShip
	var townShipArray = [];
	
	function checkCityExist(zipCode,cityArray){
		var result = false;
		for ( var i = 0; i < cityArray.length; i++) {
			var check = cityArray[i];
			if(check.cityName == zipCode.cityName){
				result = true;
			}
		}
		return result;
	}
	
	function findZipCode(zipCodeValue){
		var result = null;
		for ( var i = 0; i < zipCodeArray.length; i++) {
			var zipCode = zipCodeArray[i];
			if(zipCode.zipCode == zipCodeValue){
				result = zipCode;
			}
		}
		return result;
	}
	
	var initAddress ={
		 initZipCodeArray : function() {
			    //關閉非同步 , 資料載入後才繼續
			    $.ajaxSetup({async:false});
			    $.get(_cp + "/Com/zipCodeQry!initZipCode",function(data){
			    	zipCodeArray = data;
			    },"json");	
			    //開啟非同步
			    $.ajaxSetup({async:true}); 
			    
			    for ( var i = 0; i < zipCodeArray.length; i++) {
					var zipCode = zipCodeArray[i];
					var cityExist = checkCityExist(zipCode,cityArray);
					if(!cityExist){
						cityArray.push(zipCode);
					}
				}
		 },	
		 initDBVal : function() {
				 getDBZipCodeValue = ''	
				 getDBCityValue = ''
				 getDBTownshipValue	= ''		 
		 },	
		 initCity : function () {
				user.initAttr()
			 	$ZipCode.val('');
			 	$City.append("<option  desc2='' value=''>請選擇</option>");		 	
				$Township.append("<option desc2='' value=''>請選擇</option>")  			 
				addOption.addCity();
//			 	$.post("/nii_imm/Com/TaiwanAddressQry!initCity",{selVal:612},addOption.addCity,"json")
		 },
		 initSel : function(){
				$Township.children("option").remove()			 	
				$Township.append("<option desc2='' codeVal=''>請選擇</option>")  
			 	$ZipCode.val('');				
			 	$City.children("option:eq(0)").attr("selected","selected");				
			 	$City.children("option:eq(0)").attr("selected","selected");				
				$City.attr({"desc2" : '' ,"codeVal" : ''})	
				$Township.attr({"desc2" : '' ,"codeVal" : ''})	
		 },
		 initTownship : function(){
			 	$ZipCode.val('');
				$Township.attr({"desc2" : '' ,"codeVal" : ''})	
		 }		 
	}
	
	var addOption ={
		 addCity : function (obj) {
			    for ( var i = 0; i < cityArray.length; i++) {
			    	var zipCode = cityArray[i];
			    	$City.append("<option value="+zipCode.cityCode+" desc2=\"\">"+zipCode.cityName+"</option>");
				}
//				$(obj).each(function(){
//					$City.append("<option value="+this.code+" desc2="+this.comment+">"+this.codeName+"</option>")
//				});				
				user.setStart()
		 },	
		 addTownship : function(obj){  
				$ZipCode.val("")
				$Township.children("option").remove();
				$Township.append("<option desc2='' value=''>請選擇</option>")  			
//			   for(i=0;i<obj.length;i++){
//			       desc2 = obj[i].comment ;
//			       if(desc2 != ' ')
//			       $Township.append("<option value="+obj[i].code+" desc2="+obj[i].comment+">"+obj[i].codeName+"</option>")                                   
//			   }
				
			   for ( var i = 0; i < townShipArray.length; i++) {
			    	var townShip = townShipArray[i];
			        $Township.append("<option value="+townShip.townShipCode+" desc2=\""+townShip.zipCode+"\">"+townShip.townShipName+"</option>");                                 
				}
				
			   $Township.removeAttr("disabled"); 
		       if(isEmpty(getDBTownshipValue) == false)  user.setSelTownship();	
		}
	}	
	
	
	
	var optionChange ={
		 cityChange : function () {
			var desc2 = $City.children("option:selected").attr("desc2");
			var value = $City.children("option:selected").attr("value");
			if("請選擇" == value || "" == value){
				initAddress.initSel();
				return;
			}	
			$City.attr({"desc2" : desc2 ,"codeVal" : value})
			$Township.attr({"desc2" : "" ,"codeVal" : "","disabled":'true'})	
			
//			$.post("/nii_imm/Com/TaiwanAddressQry!initTownship",{selVal:desc2},addOption.addTownship,"json")	
			
			townShipArray = [];
			for ( var i = 0; i < zipCodeArray.length; i++) {
				var zipCode = zipCodeArray[i];
				if(value == zipCode.cityCode){
					townShipArray.push(zipCode);
				}
			}
			
			addOption.addTownship(null);
			
		 },
		 townshipChange : function () {
			var desc2 = $Township.children("option:selected").attr("desc2");
			var value = $Township.children("option:selected").attr("value"); 	
			if("請選擇" == value || "" == value){
				initAddress.initTownship();
				return;
			}	
			$Township.attr("desc2", desc2);
			$Township.attr("codeVal", value);
			if(isEmpty(getDBTownshipValue) == false){
				 user.setZipCode()			
			}else{
				$ZipCode.val(desc2);					
			}
		 },
		 zipCodeChange : function () {
				var zipCodeValue = $ZipCode.attr("value");
				if(zipCodeValue.length ==3 ){
					var zipCode = findZipCode(zipCodeValue);
					if(zipCode == null){
						$ZipCode.val(''); // 查無此郵遞區號
						$City.children("option").remove();
						addOption.addCity();
						$City.attr({'desc2' : '' ,'codeVal' : ''});
						$Township.attr({'desc2' : '' ,'codeVal' : '','disabled':'true'});
						$City.val('');
						$Township.val('');
					}else{ // 有找到郵遞區號 -> 設定給他
						var cityCode = zipCode.cityCode;
						var townShipCode = zipCode.townShipCode;
						$City.children("option").remove();
						addOption.addCity();
						$City.attr({'desc2' : '' ,'codeVal' : cityCode});
						$City.val(cityCode);
						optionChange.cityChange();
						$Township.attr({'desc2' : zipCodeValue ,'codeVal' : townShipCode});
						$Township.val(townShipCode);
						$ZipCode.val(zipCodeValue);
					}
				}
//				if("請選擇" == value || "" == value){
//					initAddress.initTownship();
//					return;
//				}	
//				$Township.attr("desc2", desc2);
//				$Township.attr("codeVal", value);
//				if(isEmpty(getDBTownshipValue) == false){
//					 user.setZipCode()			
//				}else{
//					$ZipCode.val(desc2);					
//				}
			 }	
	}	

	//設定傳入參數，並且初始化
	var user ={ 			 
			 initAttr : function(){
					for(var key in objMap){
						if(typeof(option[key]) != 'undefined')	
							option[key] = objMap[key]
					}
					if(option.model == "common"){
						 ZipCode = option.tag;
						 $ZipCode = $(ZipCode) 
						 $City = $ZipCode.next("select");
						 $Township = $City.next("select");							 
						 user.getOptionVal()
					}else if(option.model == "HyCase"){
						 ZipCode = option.tag;
						 $ZipCode = $(ZipCode) 
						 $City = $ZipCode.parent().next().children("select")
						 $Township = $City.parent().next().children("select")	
						 user.getOptionVal()
					}		 
			 },
			 //判斷codeVal值是使用者自訂參數或是sel本身就有值
			 getOptionVal : function(){	
				 var getZipCodeVal = $ZipCode.val();
				 var getCityCodeVal = $City.attr("codeVal");
				 var getTownshipCodeVal = $Township.attr("codeVal");	
				 if(isEmpty(option.zipCode) == false){
					 getDBZipCodeValue = option.zipCode;				 
				 }else if(isEmpty(getZipCodeVal) == false){
					 getDBZipCodeValue = getZipCodeVal;						 
				 }				 
				 if(isEmpty(option.city) == false){
					 getDBCityValue = option.city;				 
				 }else if(isEmpty(getCityCodeVal) == false){
					 getDBCityValue = getCityCodeVal;						 
				 }
				 if(isEmpty(option.township) == false){
					 getDBTownshipValue = option.township;				 
				 }else if(isEmpty(getTownshipCodeVal) == false){
					 getDBTownshipValue = getTownshipCodeVal;						 
				 }	
			 },
			 setStart : function(){

					if(isEmpty(getDBCityValue) == false && isEmpty(getDBTownshipValue) == false){
						user.setSelCity();		
					}else if(isEmpty(getDBCityValue) == false && isEmpty(getDBZipCodeValue) == false){
						$City.children("option[value="+getDBCityValue+"]").attr("selected","selected");
						$ZipCode.val(getDBZipCodeValue)	
					}else if(isEmpty(getDBCityValue) == false){
						$City.children("option[value="+getDBCityValue+"]").attr("selected","selected");
						optionChange.cityChange();		
					}else{
						user.setZipCode();						
					}	
			 },
			 setZipCode : function(){
				 if(isEmpty(option.zipCode) == false){					 
					 $ZipCode.val(option.zipCode)				 
				 }else if(isEmpty(getDBZipCodeValue) == false){					 
					 $ZipCode.val(getDBZipCodeValue)
				 }
				 initAddress.initDBVal()	
			 },			 
			 setSelCity : function(){
				 if(user.checkCityOption(getDBCityValue) == true){
					 $City.children("option[value="+getDBCityValue+"]").attr("selected","selected");
					 optionChange.cityChange();	
				 }	
			 },
			 setSelTownship : function(){
				 if(user.checkTownshipOption(getDBTownshipValue) == true){
					 $Township.children("option[value="+getDBTownshipValue+"]").attr("selected","selected");
					 optionChange.townshipChange();				 
				 }
				 user.setZipCode()
			 },		 
			 checkCityOption : function(value){
				 var cityOptionCount = $City.children("option").length;
				 var result = false;
				 for(i=0;i<cityOptionCount;i++){
					 getValue = $City.children("option:eq("+i+")").attr("value");
					 if(value == getValue ){
						 result=true;					 
						 return result;
					 }
				 }
				 return result;
			 },
			 checkTownshipOption : function(value){
				 var townshipOptionCount = $Township.children("option").length;
				 var result = false;
				 for(i=0;i<townshipOptionCount;i++){
					 getValue = $Township.children("option:eq("+i+")").attr("value");
					 if(value == getValue ){
						 result=true;					 
						 return result;
					 }
				 }
				 return result;
			 }		 
	}
	
	//判斷空值或是null
	var isEmpty = function(value){		
		var val = $.trim(value);
		 if(val !=null && val != '' && val.length > 0 && val != 'undefined'){
			 return false;
		 }else{
			 return true;
		 }
	}
	
	//設定屬性為空值 (傳入變數名稱)
	var setAttrEmpty = function(value){
				count = 0;				
				if('getDBTownshipValue' == value){
					getDBTownshipValue = '';		
					count++
				}else if('getDBCityValue' == value){
					getDBCityValue = '';		
					count++					
				}else if('getDBZipCodeValue' == value){
					getZipCodeValue = '';		
					count++					
				}
				if(count == 0){
					alert("參數設定失敗，參數是 = "+value)
				}
	}	
		
	initAddress.initZipCodeArray();
	initAddress.initCity();
	$City.bind('change',optionChange.cityChange)
	$Township.bind('change',optionChange.townshipChange)	
	$ZipCode.unbind("click");
	$ZipCode.bind('keyup',optionChange.zipCodeChange)
}
//------------------------------------------------------------------------------

/*
 * getBlockUIOptionsCSS 取得 blockUI options 的 css 參數
 * @param options 參數
 *                data type: object
 *                default value : {
 *                    width: 800,
 *                    height: 600,
 *                    cursor: null
 *                }
 */
function getBlockUIOptionsCSS(options) {
	var opt = jQuery.extend({
		width: 640,
		height: 480,
		cursor: null
	}, options || {});
	var windowWidth = jQuery(window).width();
	var windowHeight = jQuery(window).height();
	var width = (windowWidth < opt.width ? windowWidth : opt.width);
	var height = (windowHeight < opt.height ? windowHeight : opt.height);
	var css = {
		top: (windowHeight - height) / 2 + "px",
		left: (windowWidth - width) / 2 + "px",
		width: width + "px",
		height: height + "px",
		cursor: opt.cursor
	};
	return css;
}

/*
 * addSlashToDate 為日期字串加上斜線
 * @param dateString 日期字串，須為8位數字的字串
 */
function addSlashToDate(dateString) {
	if (/^\d{8}$/.test(dateString) == false) {
		return dateString;
	}
	
	return dateString.substr(0, 4) + "/" + dateString.substr(4, 2) + "/" + dateString.substr(6);
}

/*
 * 提供onkeyup時英文小寫轉大寫的功能
 * 使用方式如下:
 * 
 * <input id="receiveNo" type="text">
 * 
 * $('#receiveNo').niiToUpperCase();
 */
;(function($){
	$.fn.niiToUpperCase = function() {
		$(this).bind("keyup.niiToUpperCaseKeyup change.niiToUpperCaseChange", function(){
			$(this).val($(this).val().toUpperCase());
		});
	}
})(jQuery);