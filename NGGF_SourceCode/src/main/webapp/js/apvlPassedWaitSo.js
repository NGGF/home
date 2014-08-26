;(function($) {
	apvlPassedWaitSo = {
			opt : {
				applicant : {loveName:null, exitDate:null,entryDate:null, militaryClass:null, deadMark:null},
				applyCaseRelation : {relation:null, chineseName:null},
				applyCase : {team:null,position:null, chineseName:null, position:null,specialMark:null},
				url:"",
				successFun:revPassSubmit,
				isOk : false,
				ruleIdx : 0,
				rule : null
			},
			obj:"",
			initStart : function () {
				//set rule
				//alert($(this.obj).attr("attrUrl"));
				if ( undefined != this.obj ) {
					$(this.obj).attr("disabled", true);
					apvlPassedWaitSo.opt.rule = 
						[this.checkApplyCaseRelation, 
						 this.checkRule6, 
						 this.checkRule7, 
						 this.checkRule8, 
						 this.checkRule1, 
						 this.checkRule2, 
						 this.doneCheck];
					apvlPassedWaitSo.showProcessUi();
					this.getNextRule(this.opt.rule);
				} else {
					alert("error button obj");
				}
			},
			getNextRule : function (rule) {
				//alert(rule[this_opt.ruleIdx]);
				var this_opt = apvlPassedWaitSo.opt;
				if ( this_opt.ruleIdx < rule.length) {
					if (rule[this_opt.ruleIdx] && typeof rule[this_opt.ruleIdx] == 'function') {
						rule[this_opt.ruleIdx]();
					}	
				}
			}, 
			doneCheck : function () {
				$(apvlPassedWaitSo.obj).attr("disabled", false);
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.successFun && typeof this_opt.successFun == 'function') {
					this_opt.successFun(this_opt.url);
				} 
			},
			
			showErrorMsg : function(msg) {
				niiMessage(msg);
				$(apvlPassedWaitSo.obj).attr("disabled", false);
			},
			
			showProcessUi : function (){
				var vWidth = 220;
				var vHeight = 150;
				$.blockUI({  
			        message: "<br/><img src='../images/loading.gif' />系統審核檢驗中...",  
			        css: {  
			        	top:  ($(window).height() - vHeight) /2 + 'px',   
			        	left: ($(window).width() - vWidth) /2 + 'px',  
			        	width: vWidth+'px', 
			        	height: vHeight+'px',  
			        }  
				});
			},
			
			warnMsg: function (msg){
				var this_opt = apvlPassedWaitSo.opt;
				var vWidth = 220;
				var vHeight = 150;
				
				var htmlStr ="<div class='aCenter'><br/>"+msg+"<br/><br/><br/>" + 	
				"<!-- 確定 --><input type='button' id='apvCfmBtn' value='確認' class='btnbg'>" + 				
				"<!-- 取消 --><input type='button' id='apvUnblockBtn' value='取消' class='btnbg'></div>" +
			    "<script type='text/javascript'>" + 
			    "$('#apvUnblockBtn').click(function() {" + 
			    "$.unblockUI();$(apvlPassedWaitSo.obj).attr('disabled', false);}); " +
			    "$('#apvCfmBtn').click(function() {" +
			    "apvlPassedWaitSo.opt.ruleIdx++;" +
			    "apvlPassedWaitSo.getNextRule(apvlPassedWaitSo.opt.rule);" +
				"});</script>";		
				
				$.blockUI({  
			        message: htmlStr,  
			        css: {  
			        	top:  ($(window).height() - vHeight) /2 + 'px',   
			        	left: ($(window).width() - vWidth) /2 + 'px',  
			        	width: vWidth+'px', 
			        	height: vHeight+'px',  
			        }  
				});
			},
			
			/**
			 *  規則編號 5
			 *  若關係人為配偶，確定姓名須與m010之姓名一致：
			 *  檢查 Applicant.LoveName是否與ApplyCaseRelation.ChineseName一致 
			 */
			checkApplyCaseRelation : function () {
				var this_opt = apvlPassedWaitSo.opt;
				var relationCode = this_opt.applyCaseRelation.relation;
				//alert('relationCode='  + relationCode + ',loverName=' + this_opt.applicant.loveName + ',ApplyCaseRelation.ChineseName= ' + this_opt.applyCaseRelation.chineseName);
				if ( relationCode == 3) {
					if ( this_opt.applicant.loveName != 
						this_opt.applyCaseRelation.chineseName) {
						apvlPassedWaitSo.showErrorMsg("申請人配偶與關係人姓名不一致");
					} else {
						//alert("checkApplyCaseRelation:check ok")
						this_opt.ruleIdx++;
						apvlPassedWaitSo.getNextRule(this_opt.rule);
					}
				} else {
					//alert("checkApplyCaseRelation:not need to check");	
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}

			},
			/**
			 *  規則編號 6
			 *  若關係人為配偶，確定姓名須與m010之姓名一致：
			 *  檢查 Applicant.LoveName是否與ApplyCaseRelation.ChineseName一致 
			 */
			checkRule6 : function () {
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.applyCase.team  = '1M' && 
						(this_opt.applyCase.position == 67 || this_opt.applyCase.position == 68)  ) {
					if ( null != this_opt.applicant.exitDate 
							&& null != this_opt.applicant.entryDate
							&& ( this_opt.applicant.exitDate > this_opt.applicant.entryDate) ) {
						apvlPassedWaitSo.showErrorMsg("申請人不在台灣，不可發證");				
					} else {
						//是否有新的前科資料，檢查分文清單後才有前科資料
						$.getJSON(_cp + '/Rev/apvlPassedWaitSoValidation!checkCrimeRecords', {
							chineseName: this_opt.applyCase.chineseName
							},
						function(result, status, xhr) {
							if (status == "success") {
								if ( result.chkResult == false ) {
									apvlPassedWaitSo.showErrorMsg(result.errorMsg);	
								} else {
									this_opt.ruleIdx++;
									apvlPassedWaitSo.getNextRule(this_opt.rule);
								}
							} else {
								apvlPassedWaitSo.showErrorMsg("發生錯鋘，請洽系統管理員!"); 
							} 

						});
					} 		
				} else {
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}
			}, 
			/**
			 *  規則編號 7
			 *  檢查役別代碼合理性
			 */
			checkRule7 : function () {
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.applicant.militaryClass > 16) {
					apvlPassedWaitSo.showErrorMsg("役別不得超過16，請修正");	
				} else {
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}
			}, 
			/**
			 *  規則編號 8
			 *  檢查身分代碼合理性
			 */
			checkRule8 : function () {
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.applyCase.position > 394) {
					apvlPassedWaitSo.showErrorMsg("身分代碼錯誤，請修正");	
				} else {
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}
			}, 
			/**
			 *  規則編號 1
			 *  檢查申請人是否死亡
			 */
			checkRule1 : function () {
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.applicant.deadMark  == 'D') {
					apvlPassedWaitSo.warnMsg("是否仍要發證？");	
				} else {
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}
			}, 
			/**
			 *  規則編號 2
			 *  檢查特殊註記註記是否有管制註記
			 */
			checkRule2 : function () {
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.applyCase.specialMark  == 1) {
					apvlPassedWaitSo.warnMsg("此人有管制記錄，<br/>請確認是否已與管制確認");	
				} else {
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}
			}, 
			/**
			 *  規則編號 9
			 *  申請事由為定居
			 *  至少須前一次居留申請案取得國籍超過一年
			 */
			checkRule9 : function () {
				var this_opt = apvlPassedWaitSo.opt;
				if (this_opt.applyCase.reason  == 26 
						&& ( this_opt.applyCase.position == 371 || this_opt.applyCase.position == 372 )) {
					apvlPassedWaitSo.warnMsg("此人有管制記錄，<br/>請確認是否已與管制確認");	
				} else {
					this_opt.ruleIdx++;
					apvlPassedWaitSo.getNextRule(this_opt.rule);
				}
			}
			
	}
	
	jQuery.fn.apvlPassedWaitSoChk = function() {
		//alert("arguments.length=" + arguments.length);
		if (arguments.length >= 4 ) {
			//set arguments
			apvlPassedWaitSo.obj = arguments[0]
			apvlPassedWaitSo.opt.url = arguments[1];
			apvlPassedWaitSo.opt.applicant = $.extend(apvlPassedWaitSo.opt.applicant, arguments[2] || {});
			apvlPassedWaitSo.opt.applyCaseRelation = $.extend(apvlPassedWaitSo.opt.applyCaseRelation, arguments[3] || {});
			apvlPassedWaitSo.opt.applyCase = $.extend(apvlPassedWaitSo.opt.applyCase, arguments[4] || {});
			apvlPassedWaitSo.opt.isOk = false;
			apvlPassedWaitSo.opt.ruleIdx = 0;
			apvlPassedWaitSo.opt.rule = null;
			apvlPassedWaitSo.initStart();
		} else  {
			niiMessage("error argments");
		}

	};
})(jQuery);
