<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  	value="ui.approval.collaboration.button" />
<c:set var="preDefault"  	value="ui.approval.collaboration.default" />
<c:set var="preMessage"  	value="ui.approval.collaboration.message" />
<c:set var="preTitle"  		value="ui.approval.proposal.view.title" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script> 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<script type="text/javascript">

<!--
var ecmFlg = "N";
var btnAction = "save";
(function($){
	
	var cachedTdObj;
	
	setRcvDeptGroup = function(data) {
		var groupName = "";
		var groupId = "";
		
		$jq.each(data, function() {
			
			groupName = $jq.trim(this.name);
			groupId = $jq.trim(this.code);	
			
			$jq("#sndReviewDeptId").val(groupId);
			$jq("#sndReviewDeptNm").val(groupName);
		});
	};
	
	//저장
	save = function(){
		var url = "<c:url value='/approval/collaboration/proposal/createProposal.do'/>";
		if('${viewMode}' == "modify") {
			url = "<c:url value='/approval/collaboration/proposal/updateProposal.do'/>";
		}
		
		$("#npdForm").find("input[name='saveYn']").val("Y");
		$("#npdForm").attr( "action", url);
		$("#npdForm").submit();
	}
	
	tempSave = function() {
		
		var url = "<c:url value='/approval/collaboration/proposal/createProposal.do'/>";
		if('${viewMode}' == "modify") {
			url = "<c:url value='/approval/collaboration/proposal/updateProposal.do'/>";
		}
		btnAction = "tempSave";
		
		$("#npdForm").find("input[name='saveYn']").val("N");
		$("#npdForm").attr( "action", url);
		$("#npdForm").submit();
	}
	
	// 삭제
	del = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='deleteItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/proposal/ajaxDeletProposal.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert("<ikep4j:message pre='${preMessage}' key='deleteSuccess' />");
					goList();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
					goList();
					return false;
				}
			});
			
		}
	}
	
	ubiOpen = function() {
		
		var ubiProposalUrl = "<c:url value='/approval/collaboration/proposal/ubiReport.do?proposalNo='/>" + "${proposal.proposalNo}";
	
		window.open( ubiProposalUrl, 'ubiReport', 'menubar=no, status=no, toolbar=no, location=no, resizable=yes');
	}
	
	// 파일팝업 ActiveX
	openPopActiveX = function() {
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/proposal/editFilePopViewActiveX.do?proposalNo='/>" + "${proposal.proposalNo}" +"&fileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='fileItemId' />",
			url: targetUrl,
			modal: true,
			width: 700,
			height: 370,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas; 
				// 파일 저장이 완료된후 유첨자료 화면에 뿌림
				
				$("input[name='fileItemId']").val( datas.fileItemId);
				$("#fileDownload").empty();
				var uploaderOptions = {
						   files : datas.fileDataListJson, 
						   isUpdate : false 
						}; 
						        
			    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
			}
		});
	}
	// 파일팝업 Ecm
	openPopEcm = function() {
		
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/proposal/editFilePopViewEcm.do?proposalNo='/>" + "${proposal.proposalNo}" +"&fileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='fileItemId' />",
			url: targetUrl,
			modal: true,
			width: 800,
			height: 370,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas; 
				// 파일 저장이 완료된후 유첨자료 화면에 뿌림
				$("input[name='fileItemId']").val( datas.fileItemId);
				$("#fileDownload").empty();
				var uploaderOptions = {
						   files : datas.fileDataListJson, 
						   isUpdate : false 
						}; 
						        
			    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
			}
		});
	}
	goOpinion = function( _type) {
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='opinion' />",
			url: "<c:url value='/approval/collaboration/proposal/opinionProposalPopView.do?proposalNo='/>"+ "${proposal.proposalNo}" +"&opinionType="+_type,
			modal: true,
			width: 800,
			height: 370,
			callback : function() {
				var url = "<c:url value='/approval/collaboration/proposal/editProposalView.do'/>";
				$("#moveForm").attr( "action", url);
				$("#moveForm").submit();
			}
		});
	}
	
	// 목록으로 가기
	goList = function(){
		var url = "<c:url value='/approval/collaboration/proposal/listProposalView.do'/>";
		$("#moveForm").attr( "action", url);
		$("#moveForm").submit();
		return false;
	}
	
	setUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		var groupId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.empNo);	
			groupId = $jq.trim(this.group);	
			
			
			alert( "선택한 임직원( "+ managerName + " )으로 선택이 완료되었습니다.");
			cachedTdObj.find('.searchUserName').val(managerName);
			cachedTdObj.find('.searchUserId').val(userId);
		});
	};
	
	$(document).ready(function() {  
		iKEP.iFrameContentResize();
	
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		var requredMsg = "<ikep4j:message pre='${preMessage}' key='required'/>";
		var rangeLength100Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.100'/>";
		var rangeLength500Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.500'/>";
		var rangeLength1000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.1000'/>";
		var rangeLength2000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.2000'/>";
		
		var defaultValidRule = {
				rules  : {
					custName : {required : true, rangelength : [1, 100]},
					paperGrpName : {required : true, rangelength : [1, 100]},
					productName : {required : true, rangelength : [1, 100]},
					keyFeatureFunction : {rangelength : [1, 2000]},
					custRequests : {rangelength : [1, 2000]},
					marketStatus : {rangelength : [1, 2000]},
					proposalReason : {rangelength : [1, 2000]},
					goalSaleQty : {rangelength : [1, 100]},
					goalSalePrice : {rangelength : [1, 100]},
					devPeriod : {rangelength : [1, 100]}
				},
				messages : {
					custName : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='custName'/>"+ requredMsg , rangelength : "<ikep4j:message pre='${preTitle}' key='keyFeatureFunction'/>" + rangeLength100Msg},
					paperGrpName : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='paperGrpName'/>"+ requredMsg , rangelength : "<ikep4j:message pre='${preTitle}' key='paperGrpName'/>" + rangeLength100Msg},
					productName : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='productName'/>"+ requredMsg , rangelength : "<ikep4j:message pre='${preTitle}' key='productName'/>" + rangeLength100Msg},
					keyFeatureFunction : {direction : "top",  rangelength : "<ikep4j:message pre='${preTitle}' key='keyFeatureFunction'/>" + rangeLength2000Msg},
					custRequests : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='custRequests'/>" + rangeLength2000Msg},
					marketStatus : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='marketStatus'/>" + rangeLength2000Msg},
					proposalReason : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='proposalReason'/>" + rangeLength1000Msg},
					goalSaleQty : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='goalSaleQty1'/>" + rangeLength100Msg},
					goalSalePrice : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='goalSaleQty1'/>" + rangeLength100Msg},
					devPeriod : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='marketStatus'/>" + rangeLength100Msg}
				},
				submitHandler : function(form) {
			    	
					var confirmMsg = "저장완료시 더이상 기본사항을 수정할수 없습니다. \n" + "<ikep4j:message pre='${preMessage}' key='save'/>";
			    	if( btnAction == "tempSave") {
			    		confirmMsg = "<ikep4j:message pre='${preMessage}' key='tempSave'/>";
			    	}
			    	
			    	if(confirm( confirmMsg)) {
			    		
		    			writeSubmit(form);
					}
				}
		}
		var tcsValidRule = {
				rules  : {
					apprComment : {rangelength : [1, 2000]}
				},
				messages : {
					apprComment : {direction : "top", rangelength : "<ikep4j:message pre='${preTitle}' key='apprComment'/>" + rangeLength2000Msg}
				},
				submitHandler : function(form) {
			    	
					var confirmMsg = "<ikep4j:message pre='${preMessage}' key='save'/>";
			    	if( btnAction == "tempSave") {
			    		confirmMsg = "<ikep4j:message pre='${preMessage}' key='tempSave'/>";
			    	}
			    	
			    	if(confirm( confirmMsg)) {
			    		
		    			writeSubmit(form);
					}
				}
		}
		
		
		<c:if test="${permission.reqViewActive eq true}">
			new iKEP.Validator("#npdForm", defaultValidRule);
		</c:if>
		
		<c:if test="${permission.tcsViewActive eq true && permission.reqViewActive eq false}">
			new iKEP.Validator("#npdForm", tcsValidRule);
		</c:if>
		<c:if test="${permission.tcsViewActive eq true && permission.reqViewActive eq true}">
			new iKEP.Validator("#npdForm", formValidRule = $.extend( defaultValidRule, tcsValidRule ));
		</c:if>
		
		writeSubmit = function(targetForm){
		
			$("body").ajaxLoadStart();
			targetForm.submit();
			return false;
		};
		
	  
	    // 사람찾기 - 검토자, 승인자 변경
	    $jq('.searchUserName').keypress( function(event) {
			cachedTdObj = $(this).parent("div");
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
	    
	    $jq('.addrSearchBtn').click( function() {
		    
	    	$(this).parent("div").find('.searchUserName').trigger("keypress");
	    	//입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
// 			cachedTdObj.find('.searchUserName').trigger("keypress");
		});
		
		$('.addrBtn').click( function() {
			cachedTdObj = $(this).parent("div");
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		$jq('.btnDeleteControl').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
		    $(this).parent("div").find("input").val("");
		});
	    
		
		var reqViewActive = "${permission.reqViewActive}";
		if( reqViewActive == 'true') {
			showElement("req");
		}
		var tcsViewActive = "${permission.tcsViewActive}";
		if( tcsViewActive == 'true') {
			showElement("tcs");
		}
		
		
		function showElement( _type) {
			
			$("."+_type+"EnableArea").show();
			$("."+_type+"DisableArea").hide();
			$("."+_type+"Element").each(function(){ 
				if( this.tagName == 'INPUT' ) {
					var type = $(this).attr('type').toLowerCase();
					if( type == 'checkbox' || type == 'radio' ) {
						$(this).attr("disabled", false).removeClass("inputReadOnly");	
					}else{
						$(this).attr("readonly", false).removeClass("inputReadOnly");
					}
					
				} else if( this.tagName == 'SELECT'){
					
					$(this).attr("disabled", false);
				} else {
					$(this).attr("readonly", false).removeClass("inputReadOnly");	
				}
			});
		}
		
		// 임직원 선택 제거시 알림 메시지
		$(".searchUserName").change(function(){
	    	var val = $(this).val();
	    	var idObj = $(this).parent("div").find(".searchUserId");
	    	var idval = $(this).parent("div").find(".searchUserId").val() || "";
	    	
	    	if( val == '' && idval) {
	    		alert("선택된 임직원이 삭제되었습니다.");
	    		idObj.val("");
	    	}
	    });
		
		<c:if test="${not empty fileDataListJson}">
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
		        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	    </c:if>
	    
	    $('.onlyNumb').blur(function () { 
	        var thisVal = $(this).val();
	    	$(this).val( makeComma(thisVal));
	    });
	    
	    function makeComma( _val ) {

	    	var val = _val + "";
	    	val = val.replace(/,/g, '');
	    	var len = val.length;
	    	var rstr = "";


	    	for( var i=len-1,count=0;i>=0;i--,count++ ) {
	    		if( count%3 == 0 && count != 0 ) rstr = ","+rstr;
	    		rstr = val.charAt(i)+rstr;
	    	}

	    	return rstr;

	    }
	    
	 	// readonly일때 포커스 안가도록 처리
	    $(".readOnlyFocusBlur").focus(function(){
	    	
	    	var isReadOnlyEl = $(this).attr("readonly") || '';
	    	if( isReadOnlyEl.toLowerCase() == 'readonly') {
	    		$(this).blur();
	    	}
	    });
		
	});
})(jQuery);
	
//-->
</script>

<style>
.nameArea {height: 40px; text-align: center;}
.container{width:950px;}
.clearMarPadd0{ margin: 0 !important; padding: 0 !important; border:0px !important; }
.w25percent{width:25% !important;}
.w30percent{width:30% !important;}
.w40percent{width:40% !important;}
.w50percent{width:50% !important;}
.w60percent{width:60% !important;}
.w90percent{width:90% !important;}
.w100percent{width:100% !important;}
.w20pix{width:20px; !important;}
.w30pix{width:30px; !important;}
.w40pix{width:40px; !important;}
.w50pix{width:60px; !important;}
.w60pix{width:60px; !important;}
.w70pix{width:70px; !important;}
.w100pix{width:100px; !important;}
.w150pix{width:150px; !important;}
.w200pix{width:200px; !important;}
.devReqTitleArea{text-align:center; width:100%; height: 100%; line-height: 50px; vertical-align: middle; font-size:30px; font-weight:bold;}
.floatLeft{float:left !important;}
.marginLeft10{margin-left:10px !important;}
.inputReadOnly{ border: 0 !important; background: #fff !important;}
.inputReadOnly2{ background: #fff !important;}
.container INPUT[type='text'] {width:100%;}
.inputNum{text-align:right !important;}
.thMinH{min-height: 28px !important;}
.paddingRight16{padding-right: 16px !important;}
.approveTdBot{height: 51px; border-bottom: 0px !important;}
.approveTdTop{height: 15px; border-top: 0px !important;}
.fakeBtn{margin-right:10px; border:1px solid; background-color: #fff; width: 30px;}
</style>


<form id="moveForm" name="moveForm" action="">
	<input type="hidden" name="searchConditionString" value="${searchConditionString }" /> 
	<input type="hidden" name="proposalNo" value="${proposal.proposalNo}" />
	<input type="hidden" name="viewMode" value="${viewMode}"/>
</form>
	
<div class="container"> 
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<c:if test="${viewMode eq 'modify'}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span><ikep4j:message pre="${preButton}" key="ubiReport" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll ne true && permission.reqViewActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopActiveX();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll eq true && permission.reqViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopEcm();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${permission.tempSaveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="tempSave();" ><span><ikep4j:message pre="${preButton}" key="tempSave" /></span></a></li>
				</c:if>
				<c:if test="${permission.saveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="save();" ><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
				</c:if>
				<c:if test="${permission.deleteBtnActive eq true }">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="del();" ><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="goList();"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			 </ul>
		</div>
		<!--//blockButton End-->
	
	<h1 class="none"><ikep4j:message pre="${preTitle}" key="devRequest" /></h1> 
	
<form id="npdForm" name="npdForm" method="post" action="" enctype="multipart/form-data">
	<input type="hidden" name="proposalNo" value="${proposal.proposalNo}" /> 
	<input type="hidden" name="saveYn" value="${proposal.saveYn}" /> 
	
	<!-- ecmFile 변수 -->	
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
	<!--  Title Area Start -->
	<div style="height:110px;">
		
		<div class="blockDetail floatLeft" style="width: 220px;">
			<table>
				<colgroup>
					<col width="70px;"/>
					<col width="*" />
				</colgroup>
				<tbody>
					<!-- 관리번호 -->
					<tr>
						<th class="textCenter" style="height: 30px;" ><ikep4j:message pre="${preTitle}" key="proposalNo" /></th>
						<td>
							<div class="reqEnableArea" style="display:none;">
								<spring:bind path="proposal.companyCode">
									<select name="${status.expression}">
										<c:forEach var="code" items="${ c00010List}">
											<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
										</c:forEach>
									</select>
								</spring:bind>
								<span>${proposal.proposalNo}</span>
							</div>
							<div class="reqDisableArea">
								<c:forEach var="code" items="${ c00010List}">
									<c:if test="${code.comCd eq proposal.companyCode}"><c:out value="${ code.comNm}"></c:out></c:if>
								</c:forEach>
								<span>${proposal.proposalNo}</span>
							</div>
						</td>
					</tr>
					<!-- 접수자 -->
					<tr>
						<th class="textCenter" style="height: 30px;"><ikep4j:message pre="${preTitle}" key="tcsRcvEmpNm" /></th>
						<td>
							${proposal.tcsRcvEmpNm }
						</td>
					</tr>
					<!-- 접수일자 -->
					<tr>
						<th class="textCenter" style="height: 30px;"><ikep4j:message pre="${preTitle}" key="tcsRcvDate" /></th>
						<td>
							${proposal.tcsRcvDate }
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- 제안서 -->
		<div class="devReqTitleArea floatLeft" style="width: 150px; padding-top: 25px;">
			<span><ikep4j:message pre="${preTitle}" key="proposal" /></span>
		</div>
		<!-- 의뢰부서 -->
		<div class="blockDetail floatRight" style="width: 250px;">
			<table class="clearMarPadd0">
				<tr>
					<th rowspan="3" class="textCenter w20pix">접<br/>수<br/>부<br/>서</th>
					<th class="textCenter w50pix">기안</th>
					<th class="textCenter w50pix">검토</th>
					<th class="textCenter w50pix">승인</th>
				</tr>
				<tr>
					<td class="nameArea approveTdBot"></td>
					<td class="nameArea approveTdBot"></td>
					<td class="nameArea approveTdBot"></td>
				</tr>
				<!-- 의뢰 진행 상태 sss-->
				<tr>
					<td class="textCenter approveTdTop">/</td>
					<td class="textCenter approveTdTop">/</td>
					<td class="textCenter approveTdTop">/</td>
				</tr>
			</table>
		</div>
		
		<div class="blockDetail floatRight" style="width: 320px;margin-right:10px;">
			<table class="clearMarPadd0">
				<tr>
					<th rowspan="3" class="textCenter w20pix">의<br/>뢰<br/>부<br/>서</th>
					<th class="textCenter w50pix">기안</th>
					<th class="textCenter w50pix">1차검토</th>
					<th class="textCenter w50pix">2차검토</th>
					<th class="textCenter w50pix">승인</th>
				</tr>
				<tr>
					<td class="nameArea approveTdBot"></td>
					<td class="nameArea approveTdBot"></td>
					<td class="nameArea approveTdBot"></td>
					<td class="nameArea approveTdBot"></td>
				</tr>
				<!-- 의뢰 진행 상태 sss-->
				<tr style="height: 15px;border-top: 0px !important;">
					<td class="textCenter approveTdTop">/</td>
					<td class="textCenter approveTdTop">/</td>
					<td class="textCenter approveTdTop">/</td>
					<td class="textCenter approveTdTop">/</td>
				</tr>
			</table>
		</div>
		
	</div>
		<!--  title -->
	<!--  Title Area End -->
	
	<div class="mb20"></div>
	
	<!--blockDetail Start-->
	<div class="blockDetail" >
		<table summary="<ikep4j:message pre="${preTitle}" key="summary"/>" style="margin: 0px;">
			<caption></caption>
				<colgroup>
					<col width="80px;"/>
					<col width="60px;"/>
					<col width="120px;"/>
					<col width="*"/>
					<col width="120px;"/>
					<col width="300px;"/>
				</colgroup>
			<tbody> 
				<tr>
					<th colspan="2" class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="reqDeptNm" /></th>
					<td colspan="4" class="clearMarPadd0">
						<table>
							<tr>
								<th class="textCenter" style="border-left:0px;"><ikep4j:message pre="${preTitle}" key="reqDeptNm2"/></th>
								<th class="textCenter w150pix"><ikep4j:message pre="${preTitle}" key="reqUserRank"/></th>
								<th class="textCenter w150pix"><ikep4j:message pre="${preTitle}" key="reqEmpNm"/></th>
								<th class="textCenter w200pix"><ikep4j:message pre="${preTitle}" key="reqDate"/></th>
							</tr>
							<tr>
								<!-- 의뢰부서 : 부서명 -->
								<td class="textCenter" style="border-left:0px;">
									${proposal.reqDeptNm }
								</td>
								<!-- 의뢰부서 : 직급 -->
								<td class="textCenter">
									${proposal.reqUserRank }
								</td>
								<!-- 의뢰부서 : 성명 -->
								<td class="textCenter">
									${proposal.reqEmpNm }
								</td>
								<!-- 의뢰부서 : 작성일자 -->
								<td class="textCenter">
									${proposal.reqDate }
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<!-- 고객명 -->
					<th colspan="2" class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="custName" /></th>
					<td class="paddingRight16" colspan="2">
						<spring:bind path="proposal.custName">
							<textarea name="${status.expression}" class="reqElement inputReadOnly" readonly="readonly" style="text-align:center; line-height:16px; overflow:auto; width:100%; height:35px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
					<!-- 지군명 -->
					<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="paperGrpName" /></th>
					<td class="paddingRight16">
						<spring:bind path="proposal.paperGrpName">
							<textarea name="${status.expression}" class="reqElement inputReadOnly" readonly="readonly" style="text-align:center; line-height:16px; overflow:auto; width:100%; height:35px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 제품명 -->
					<th colspan="2"  class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="productName" /></th>
					<td colspan="4" class="paddingRight16">
						<spring:bind path="proposal.productName">
							<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" style="width:100%;" readonly="readonly" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 제안사유 -->
					<th colspan="2" class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="proposalReason" /></th>
					<td colspan="4" class="paddingRight16">
						<spring:bind path="proposal.proposalReason">
							<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" style="width:100%;" readonly="readonly" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 주요특징 및 기능 -->
					<th colspan="2" class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="keyFeatureFunction1" /><br><ikep4j:message pre="${preTitle}" key="keyFeatureFunction2" /></th>
					<td colspan="4" style="padding-right: 15px;">
						<spring:bind path="proposal.keyFeatureFunction">
							<textarea name="${status.expression}" class="reqElement readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:100px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 고객요청사항-->
					<th colspan="2" class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="custRequests1" /><br><ikep4j:message pre="${preTitle}" key="custRequests2" /></th>
					<td colspan="4" style="padding-right: 15px;">
						<spring:bind path="proposal.custRequests">
							<textarea name="${status.expression}" class="reqElement readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:100px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 시장현황-->
					<th colspan="2" class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="marketStatus" /></th>
					<td colspan="4" style="padding-right: 15px;">
						<spring:bind path="proposal.marketStatus">
							<textarea name="${status.expression}" class="reqElement readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:100px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 판매목표 -->
					<th colspan="2" class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="goalSaleQty" /></th>
					<th class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="goalSaleQty1" /></th>
					<!-- 판매량 -->
					<td class="paddingRight16">
						<spring:bind path="proposal.goalSaleQty">
							<textarea name="${status.expression}" class="reqElement inputReadOnly readOnlyFocusBlur" readonly="readonly" style="text-align:center; line-height:16px; overflow:auto; width:100%; height:35px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
					<!-- 판매단가 -->
					<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="goalSalePrice" /></th>
					<td class="paddingRight16">
						<spring:bind path="proposal.goalSalePrice">
							<textarea name="${status.expression}" class="reqElement inputReadOnly readOnlyFocusBlur" readonly="readonly" style="text-align:center; line-height:16px; overflow:auto; width:100%; height:35px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 지원요청-->
					<th colspan="2" class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="supportReqCd" /></th>
					<td colspan="4" style="padding-right: 15px;">
						<spring:bind path="proposal.supportReqCd">
							<c:forEach var="code" items="${c00006List}">
								<input type="radio" class="reqElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
							</c:forEach>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 개발기간 -->
					<th colspan="2" class="textCenter thMinH" style="width:100px; "><ikep4j:message pre="${preTitle}" key="devPeriod" /></th>
					<td colspan="4" class="paddingRight16">
						<spring:bind path="proposal.devPeriod">
							<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox readOnlyFocusBlur" value="${status.value}" style="width:100%;" readonly="readonly" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 개발구분-->
					<th colspan="2" class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="devGbnCd" /></th>
					<td colspan="4">
						<spring:bind path="proposal.devGbnCd">
							<c:forEach var="code" items="${c00007List}">
								<input type="radio" class="reqElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span style="margin-right: 20px;">${code.comNm}</span>
								<c:if test="${code.comCd eq 20}" ><br /> </c:if>
							</c:forEach>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 기술검토의견 : 품질 보증부-->
					<th class="textCenter thMinH" rowspan="2"><ikep4j:message pre="${preTitle}" key="opinion" /><br/><ikep4j:message pre="${preTitle}" key="opinion1" /><br/><ikep4j:message pre="${preTitle}" key="opinion2" /></th>
					<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="qadOpinion1" /><br/><ikep4j:message pre="${preTitle}" key="qadOpinion2" /></th>
					<td colspan="4">
						<spring:bind path="proposal.qadOpinion">
							<textarea name="${status.expression}" readonly="readonly" class="readOnlyFocusBlur" style="line-height:16px; overflow:auto; width:89%; height:50px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
						<c:if test="${permission.qadAuth eq true}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="goOpinion('qad');"><span><ikep4j:message pre='${preButton}' key='opinion'/></span></a>
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- 기술검토의견 : 신제품 연구팀-->
					<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="labOpinion1" /><br/><ikep4j:message pre="${preTitle}" key="labOpinion2" /></th>
					<td colspan="4">
						<spring:bind path="proposal.labOpinion">
							<textarea name="${status.expression}" readonly="readonly" class="readOnlyFocusBlur" style="line-height:16px; overflow:auto; width:89%; height:50px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
						<c:if test="${permission.labAuth eq true}">
							<a class="button listBoardItemButton" href="javascript:;"  onclick="goOpinion('lab');"><span><ikep4j:message pre='${preButton}' key='opinion'/></span></a>
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- 영업팀-->
					<th class="textCenter thMinH" colspan="2">
						<ikep4j:message pre="${preTitle}" key="salesOpinion" /><br>
						<p class="floatRight fakeBtn"><ikep4j:message pre='${preButton}' key='approve'/></p>
					</th>
					<td colspan="4">
						<spring:bind path="proposal.salesOpinion">
							<textarea name="${status.expression}" readonly="readonly" class="readOnlyFocusBlur" style="line-height:16px; overflow:auto; width:89%; height:100px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
						<c:if test="${permission.salesAuth eq true}">
							<a class="button" href="javascript:;"  onclick="goOpinion('sales');"><span><ikep4j:message pre='${preButton}' key='opinion'/></span></a>
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- SCM팀 -->
					<th class="textCenter thMinH" colspan="2" rowspan="2">
						<ikep4j:message pre="${preTitle}" key="scmOpinion" /><br>
						<p class="floatRight fakeBtn"><ikep4j:message pre='${preButton}' key='approve'/></p>
					</th>
					<td colspan="4">
						<div class="reqEnableArea" style="display:none;">
							<ikep4j:message pre="${preTitle}" key="userNm" />
							<spring:bind path="proposal.scmEmpNo">
								<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
							</spring:bind>
							<spring:bind path="proposal.scmEmpNm">
								<input name="${status.expression}" type="text" size="20" style="width: 200px;" class="searchUserName inputbox" value="${status.value}" />
							</spring:bind>
							<a href="#a" class="button_ic addrSearchBtn" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
							</a>
							<a href="#a" class="button_ic addrBtn">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
							</a>
							<a href="#a" class="button_ic valign_bottom btnDeleteControl" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
						</div>
						<div class="reqDisableArea">
							<ikep4j:message pre="${preTitle}" key="userNm" /> <span>${proposal.scmEmpNm }</span>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<spring:bind path="proposal.scmOpinion">
							<textarea name="${status.expression}" readonly="readonly"  class="readOnlyFocusBlur" style="line-height:16px; overflow:auto; width:89%; height:50px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
						<c:if test="${permission.scmAuth eq true}">
							<a class="button" href="javascript:;"  onclick="goOpinion('scm');"><span><ikep4j:message pre='${preButton}' key='opinion'/></span></a>
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- 마케팅팀-->
					<th class="textCenter thMinH" colspan="2" rowspan="3">
						<ikep4j:message pre="${preTitle}" key="marketOpinion" /><br>
						<p class="floatRight fakeBtn"><ikep4j:message pre='${preButton}' key='approve'/></p>
					</th>
					
					<td colspan="4">
						<!-- 마케팅팀 : 담당자-->
						<div class="reqEnableArea" style="display:none;">
							<ikep4j:message pre="${preTitle}" key="userNm" />
							<spring:bind path="proposal.marketEmpNo">
								<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
							</spring:bind>
							<spring:bind path="proposal.marketEmpNm">
								<input name="${status.expression}" type="text" size="20" style="width: 200px;" class="searchUserName inputbox" value="${status.value}" />
							</spring:bind>
							<a href="#a" class="button_ic addrSearchBtn" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
							</a>
							<a href="#a" class="button_ic addrBtn">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
							</a>
							<a href="#a" class="button_ic valign_bottom btnDeleteControl" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
						</div>
						<div class="reqDisableArea">
							<ikep4j:message pre="${preTitle}" key="userNm" /> <span>${proposal.marketEmpNm }</span>
						</div>
					</td>
				</tr>
			
				<tr>
					<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="marketGradeCd" /></th>
					<!-- 마케팅팀 : 신제품관리등급 -->
					<td colspan="3">
						<spring:bind path="proposal.marketGradeCd">
							<c:forEach var="code" items="${c00018List}">
								<input type="radio" class="reqElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
							</c:forEach>
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 마케팅팀 : 의견 -->
					<td colspan="4">
						<spring:bind path="proposal.marketOpinion">
							<textarea name="${status.expression}"  class="readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:89%; height:20px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
						<c:if test="${permission.marketAuth eq true}">
						<a class="button" href="javascript:;"  onclick="goOpinion('market');"><span><ikep4j:message pre='${preButton}' key='opinion'/></span></a>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<!-- TCS팀 종합의견-->
					<th class="textCenter thMinH" colspan="2">
						<ikep4j:message pre="${preTitle}" key="tcsTotalOpinion1" /><br>
						<ikep4j:message pre="${preTitle}" key="tcsTotalOpinion2" /><br>
					</th>
					<td colspan="4">
						<spring:bind path="proposal.tcsTotalOpinion">
							<textarea name="${status.expression}"  class="tcsElement readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:89%; height:100px; border:none; background:none;">${status.value}</textarea>
						</spring:bind>
						<c:if test="${permission.tcsAuth eq true}">
							<a class="button" href="javascript:;"  onclick="goOpinion('tcs');"><span><ikep4j:message pre='${preButton}' key='opinion'/></span></a>
						</c:if>
					</td>
				</tr>
				<tr>
					<!-- 승인 Comment -->
					<th class="textCenter thMinH" colspan="2">
						<ikep4j:message pre="${preTitle}" key="apprComment" />
					</th>
					<td colspan="4" class="paddingRight16">
						<spring:bind path="proposal.apprComment">
							<input name="${status.expression}" type="text" class="tcsElement inputReadOnly inputbox readOnlyFocusBlur" value="${status.value}" style="width:100%;" readonly="readonly" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<!-- 제안서 상태 -->
					<th class="textCenter thMinH" colspan="2">
						<ikep4j:message pre="${preTitle}" key="proposalStatus" />
					</th>
					<td colspan="4" class="paddingRight16">
						<spring:bind path="proposal.proposalStatus">
							<c:forEach var="code" items="${c00019List}">
								<input type="radio" class="tcsElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
							</c:forEach>
						</spring:bind>
					</td>
				</tr>
				<!-- 첨부파일 -->
				<tr>
					<th class="textCenter thMinH" colspan="2"><ikep4j:message pre="${preTitle}" key="fileItemId" /></th>
					<td colspan="4">
						<div id="fileDownload"></div>
						<spring:bind path="proposal.fileItemId">
						<input type="hidden" name="${status.expression}"  value="${status.value}"/>
						</spring:bind>
					</td>
				</tr>
			</tbody> 
		</table>
			
	</div> 
						
	</form>
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<c:if test="${viewMode eq 'modify'}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span><ikep4j:message pre="${preButton}" key="ubiReport" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll ne true && permission.reqViewActive eq true}">
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopActiveX();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll eq true && permission.reqViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopEcm();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${permission.tempSaveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="tempSave();" ><span><ikep4j:message pre="${preButton}" key="tempSave" /></span></a></li>
				</c:if>
				<c:if test="${permission.saveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="save();" ><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
				</c:if>
				<c:if test="${permission.deleteBtnActive eq true }">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="del();" ><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="goList();"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			 </ul>
		</div>
		<!--//blockButton End-->
		
</div>
<!-- Container End -->