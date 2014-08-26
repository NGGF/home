
/**
 * 查詢旅行社代碼，供所有申請案HyCase表單使用。
 * 
 **/
jQuery(function() {
	var no = jQuery.trim(jQuery("#TravelGroup\\.travelAgencyNo").val());
	var branch = jQuery.trim(jQuery("#TravelGroup\\.travelNoBranch").val());
	
	jQuery("#TravelGroup\\.travelNoBranch").blur(function (){
		var no = jQuery(this).prev().val().trim();
		var branch = jQuery(this).val().trim();
				
		if(no == null || no == "" || branch == null || branch == ""){
			jQuery("#TravelAgencyMaster\\.travelAgencyName").val('');
			jQuery("#TravelAgencyMaster\\.travelAgencyName").css("color","red");
			jQuery("#TravelGroup\\.travelAgencyNo").attr("value","");
			jQuery("#TravelGroup\\.travelNoBranch").val("");
			jQuery("#TravelAgencyMaster\\.travelTelNo").val("");
			jQuery("#TravelAgencyMaster\\.address").val("");
			return;
		}else{
			var param = {travelAgencyNo:no,travelNoBranch:branch}
			jQuery.post('/nii_imm/Acc/Ajax!queryTaInfoById',param,takeTaInfo,"text");
		}
	});
	//觸發blur
	jQuery("#TravelGroup\\.travelNoBranch").blur();
	
	
	jQuery("#TravelGroup\\.travelNoBranch").next(":button").click(function (){
		var vWindowWidth = jQuery(window).width();
		var vWindowHeight = jQuery(window).height();
		var vWidth = (vWindowWidth < 740 ? vWindowWidth : 740);
		var vHeight = (vWindowHeight < 530 ? vWindowHeight : 530);
		jQuery.blockUI({ message: "<div><h3 class='aCenter'>" + "旅行社代碼查詢" + 
			"<input type='text' id='BU_travelAgencyName'/><input type='button' class='btnbg' id='queryTravelAgencyList' value='查詢'/><input type='button' class='btnbg' id='unblockBtn' value='關閉'/></h3>" + 
			"<div class='aCenter form' style='width: 640px; height: 440px; overflow: auto;' id='queryList'><div></div>",
			
			css: {
				top: (vWindowHeight - vHeight) / 2 + "px",
				left: (vWindowWidth - vWidth) / 2 + "px",
				width: vWidth + "px",
				height: vHeight + "px",
				cursor: null
			}	
		});
		
		jQuery('#unblockBtn').click(function() {
			jQuery.unblockUI();
		});
			
		//按下查詢的時候，查詢旅行社中文
		jQuery('#queryTravelAgencyList').click(function (){
			var agencyName = jQuery("#BU_travelAgencyName").val().trim();
			var param = {BU_travelAgencyName:agencyName};
			jQuery.post('/nii_imm/Acc/Ajax!queryTaInfoByName',param,selectTravelAgencyList,'text');
		});	
	});
});	

	function takeTaInfo(jsonStr){
		if(jsonStr == "null" || jsonStr == null || jsonStr == ''){
			jQuery("#TravelAgencyMaster\\.travelAgencyName").attr("value","無此代碼!");
			jQuery("#TravelAgencyMaster\\.travelAgencyName").css("color","red");
			jQuery("#TravelAgencyMaster\\.address").val('');
			jQuery("#TravelAgencyMaster\\.travelTelNo").val('');
			return;
		}else{
			var data = eval("("+jsonStr+")");
		  //jQ("#TravelGroup\\.travelAgencyNo").attr("value",travelAgencyNo);
		  //jQ("#TravelGroup\\.travelNoBranch").attr("value",travelNoBranch);
			jQuery("#TravelAgencyMaster\\.travelAgencyName").attr("value",data.travelAgencyName);
			jQuery("#TravelAgencyMaster\\.travelAgencyName").css("color","blue");
			jQuery("#TravelAgencyMaster\\.travelTelNo").val(data.travelTelNo);
			jQuery("#TravelAgencyMaster\\.address").val(data.postNo+data.addr4A);
		}
	}
	

		
		function selectTravelAgencyList(jsonStr){
			var data = eval("("+jsonStr+")");
			if(data != null && data.length != 0){
				var trTds = [];
				trTds.push('<table class="colTb" style="width: 95%;">');
				trTds.push('<tr>');
				trTds.push('<th>旅行社代碼</th>');
				trTds.push('<th>代碼分支</th>');
				trTds.push('<th>旅行社名稱</th>');
				trTds.push('<th>旅行社電話</th>');
				trTds.push('<th>旅行社地址</th>');
				trTds.push('<th>功能列</th>');
				trTds.push('</tr>');
				jQuery(data).each(function() {
					trTds.push('<tr>');
					trTds.push('<td>' + this.travelAgencyNo + '</td>');
					trTds.push('<td>' + this.travelNoBranch + '</td>');
					trTds.push('<td>' + this.travelAgencyName + '</td>');
					trTds.push('<td>' + this.travelTelNo + '</td>');
					trTds.push('<td>' + this.postNo + this.addr4A+  '</td>');
					trTds.push('<td> <input type="button" class="btnbg" value="選取" onclick="BU_get(\'' + this.travelAgencyNo + '\',\'' + this.travelNoBranch + '\',\'' + this.travelAgencyName+ '\',\'' + this.travelTelNo + '\',\'' + this.postNo+ '\',\'' + this.addr4A + '\')" /></td>');
					trTds.push('</tr>');
				});
				trTds.push('</table>');
				jQuery('#queryList').html(trTds.join(''));				
			}else{
				jQuery("#BU_travelAgencyName").val("查無資料");
				jQuery("#BU_travelAgencyName").css("color","red");
				return ;
			}
		}

	function BU_get(travelAgencyNo,travelNoBranch,travelAgencyName,travelTelNo,postNo,addr4A){
		jQuery.unblockUI();
		jQuery("#TravelGroup\\.travelAgencyNo").attr("value",travelAgencyNo);
		jQuery("#TravelGroup\\.travelNoBranch").attr("value",travelNoBranch);
		jQuery("#TravelAgencyMaster\\.travelAgencyName").attr("value",travelAgencyName);
		jQuery("#TravelAgencyMaster\\.travelAgencyName").css("color","blue");
		jQuery("#TravelAgencyMaster\\.travelTelNo").val(travelTelNo);
		jQuery("#TravelAgencyMaster\\.address").val(postNo+addr4A);
	}
//*****************************************************************************************************

/**
 * hycase表單的欄位功能
 */
jQ(function(){
	jQ.fn.exists = function() {return this.length > 0;}
	
	//只有caseId = 1025才秀出團號，申請證別，申請次別
	jQ('#groupTb').hide();
	if (jQ('[name="caseId"]').val() == 1025) {
		jQ('#groupTb').show();
	}
	if (jQ('[name="caseId"]').val() != 1025) {
		jQ('#Traveller\\.applyPermitType').parent().parent().remove();
	}
	
	//基本資料的海外地址
	jQ('#Applicant\\.zipCodeA').click(function(){initNiiAddress({'tag':jQ(this),'model':'HyCase'});}).trigger('click'); 
	//關係人的人戶籍地址
	jQ('#ApplyCaseRelation\\.relRegistZipCode').click(function(){initNiiAddress({'tag':jQ(this),'model':'HyCase'});}).trigger('click');
	//關係人的聯絡地址
	jQ('#ApplyCaseRelation\\.relZipCode').click(function(){initNiiAddress({'tag':jQ(this),'model':'HyCase'});}).trigger('click');
	//代申請人的聯絡地址
	jQ('#TrustApply\\.zipCode').click(function(){initNiiAddress({'tag':jQ(this),'model':'HyCase'});}).trigger('click');
	
	/* 若代申請人資料表單不存在，則移除同關係人按鈕 */
	if (!jQ("#trvaelTb").exists()) {
		jQ("input:radio[name=theSameRel][value=2]").next().remove().end().remove();
	}
	/* 若關係人表單不存在，則移除同關係人按鈕 */
	if (!jQ("#relInfoForm").exists()) {
		jQ("input:radio[name=theSameRel][value=1]").next().remove().end().remove();
	}
	
	/*如果，同居住電話存在的話勾選同居住地電話*/
	if (jQ('#Traveller\\.contactTelNo').exists()) {
		jQ(':radio[name="theSameTel"][value="1"]').click(function() {
			jQ('#Traveller\\.contactTelNo').val(jQ('#Traveller\\.xTel').val());
		});
		
		jQ('#Traveller\\.contactTelNo').blur(function() {
			var telA = jQ('#Traveller\\.contactTelNo').val();
			var telB = jQ('#Traveller\\.xTel').val();
			if (telA != telB) {
				jQ(':radio[name="theSameTel"][value="1"]').prop('checked', '');
			}
		});
	}

	/* 如果同聯絡地址存在的話，勾選同聯絡電話 */
	if (jQ('#Traveller\\.contactAddr').exists()) {
		jQ(':radio[name="theSameAddr"][value="1"]').click(function() {
			jQ('#Traveller\\.contactAddr').val(jQ('#Traveller\\.address').val());
		});

		jQ('#Traveller\\.contactAddr').blur(function() {
			var addrA = jQ('#Traveller\\.address').val();
			var addrB = jQ('#Traveller\\.contactAddr').val();
			if (addrA != addrB) {
				jQ(':radio[name="theSameAddr"][value="1"]').prop('checked', '');
			}
		});
	}
	
	/*假如關係人頁簽、代申請人頁面都存在的話，勾選同關係人的動作*/
	if (jQ("#trvaelTb").exists() && jQ("#relInfoForm").exists() ) {
		jQ(":checkbox[name=theSameRel][value=1]").click(function (){
			if(jQ(this).prop("checked")){
				//中文姓名
				jQ("#TrustApply\\.chineseName").val(jQ("#ApplyCase\\.visitName").val());
				//身分證號
				jQ("#TrustApply\\.idNo").val(jQ("#ApplyCase\\.visitIdNo").val());
				//出生日期
				jQ("#TrustApply\\.birthDate").val(jQ("#ApplyCase\\.VisitBirthDate").val());
				//聯絡電話
				jQ("#TrustApply\\.telNo").val(jQ("#ApplyCaseRelation\\.relTel").val());
				//手機號碼
				jQ("#TrustApply\\.mobileNo").val(jQ("#ApplyCaseRelation\\.relationMobile").val());
				//與申請人關係
				jQ("#TrustApply\\.relation").val(jQ("#ApplyCaseRelation\\.relation").val());
				//聯絡地址
				jQ("#TrustApply\\.zipCode").val(jQ("#ApplyCaseRelation\\.relRegistZipCode").val());
				jQ("#TrustApply\\.city").val(jQ("#ApplyCaseRelation\\.relRegistCity").val());
				jQ("#TrustApply\\.township").val(jQ("#ApplyCaseRelation\\.relRegistTownship").val());
				jQ("#TrustApply\\.village").val(jQ("#ApplyCaseRelation\\.relRegistVillage").val());
				jQ("#TrustApply\\.neighborhood").val(jQ("#ApplyCaseRelation\\.relRegistNeighborhood").val());
				jQ("#TrustApply\\.road").val(jQ("#ApplyCaseRelation\\.relRegistRoad").val());
				jQ("#TrustApply\\.lane").val(jQ("#ApplyCaseRelation\\.relRegistLane").val());
				jQ("#TrustApply\\.alley").val(jQ("#ApplyCaseRelation\\.relRegistAlley").val());
				jQ("#TrustApply\\.number").val(jQ("#ApplyCaseRelation\\.relRegistNumber").val());
				
			}else{
				jQ("#TrustApply\\.chineseName").val("");
				//身分證號
				jQ("#TrustApply\\.idNo").val("");
				//出生日期
				jQ("#TrustApply\\.birthDate").val("");
				//聯絡電話
				jQ("#TrustApply\\.telNo").val("");
				//手機號碼
				jQ("#TrustApply\\.mobileNo").val("");
				//舉申請人關係
				jQ("#TrustApply\\.relation").val("");
				//聯絡地址
				jQ("#TrustApply\\.zipCode").val("");
				jQ("#TrustApply\\.city").val("");
				jQ("#TrustApply\\.township").val("");
				jQ("#TrustApply\\.village").val("");
				jQ("#TrustApply\\.neighborhood").val("");
				jQ("#TrustApply\\.road").val("");
				jQ("#TrustApply\\.lane").val("");
				jQ("#TrustApply\\.alley").val("");
				jQ("#TrustApply\\.number").val("");
			}
		});
	}
	
	
});