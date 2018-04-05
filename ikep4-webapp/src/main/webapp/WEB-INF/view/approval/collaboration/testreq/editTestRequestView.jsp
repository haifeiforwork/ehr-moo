<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preButton"  	value="ui.approval.collaboration.button" />
<c:set var="preDefault"  	value="ui.approval.collaboration.default" />
<c:set var="preMessage"  	value="ui.approval.collaboration.message" />
<c:set var="preTitle"  		value="ui.approval.testreq.view.title" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script> 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<script type="text/javascript">

<!--
var ecmFlg = "N";
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
		var url = "<c:url value='/approval/collaboration/testreq/createTestRequest.do'/>";
		if('${viewMode}' == "modify") {
			url = "<c:url value='/approval/collaboration/testreq/updateTestRequest.do'/>";
		}
		
		var companyChkVal = "";		
		for( var i=0; i < 3; i++) {
			
			var addChar = "0";
			var isChecked = $("#companyChkVal"+i).is(":checked");
			if( isChecked) {
				addChar = "1";
			}
			companyChkVal = companyChkVal + "" + addChar;
		}
		if( companyChkVal == "000") {
			companyChkVal = "";
		}
		$("#npdForm").find("input[name='companyChkVal']").val(companyChkVal);
		
		
		$("#npdForm").attr( "action", url);
		$("#npdForm").submit();
	}
	
	// 승인
	approve = function() {
		
		var confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem'/>";
		var succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess' />";
		
		<c:if test="${permission.drafter eq true}">
			confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem3'/>";
			succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess2' />";
		</c:if>
		
		if(confirm( confirmMessage )) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/testreq/ajaxApproveTestRequest.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert( succMessage);
					var url = "<c:url value='/approval/collaboration/testreq/editTestRequestView.do'/>";
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
					var url = "<c:url value='/approval/collaboration/testreq/editTestRequestView.do'/>";
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				}
			});
		}
	}
	
	// 승인/저장
	approveWithSave = function() {
		
		if( $("#npdForm").valid() == false) {
			return;
		}
		$("input[name='reqScheduleEtcTxt']").attr("disabled", false); //값이 안넘어가 잠시 풀어둠
		
		var confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem'/>";
		var succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess' />";
		
		<c:if test="${permission.drafter eq true}">
			confirmMessage = "<ikep4j:message pre='${preMessage}' key='approveItem2'/>";
			succMessage = "<ikep4j:message pre='${preMessage}' key='approveSuceess2' />";
		</c:if>
		
		if(confirm( confirmMessage )) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/testreq/ajaxUpdateWithApproveTestRequest.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert( succMessage);
					var url = "<c:url value='/approval/collaboration/testreq/editTestRequestView.do'/>";
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					$("input[name='reqScheduleEtcTxt']").attr("disabled", true); //값만 넘기고 다시 잠금
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
					var url = "<c:url value='/approval/collaboration/testreq/editTestRequestView.do'/>";
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				}
			});
		}
	}
	
	
	
	// 초기화
	initRejct = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='receiptItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/testreq/ajaxInitTestRequest.do",
				type : "post",
				data : $("#npdForm").serialize(),
				loadingElement : "",
				success : function(data, textStatus, jqXHR) {
					alert("<ikep4j:message pre='${preMessage}' key='receiptSuceess' />");
					var url = "<c:url value='/approval/collaboration/testreq/editTestRequestView.do'/>";
					$("#moveForm").attr( "action", url);
					$("#moveForm").submit();
					return false;
				},
				error : function(jqXHR, textStatus, errorThrown) {
					var arrException = $jq.parseJSON(jqXHR.responseText).exception;
					alert(arrException[0].defaultMessage);
					return false;
				}
			});
		}
	}
	
	// 반려 
	reject = function() {
		
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='rejectReason' />",
			url: "<c:url value='/approval/collaboration/testreq/rejectTestRequestView.do?trMgntNo=' />"+ "${testRequest.trMgntNo}",
			modal: true,
			width: 700,
			height: 240,
			callback : function() {
				var url = "<c:url value='/approval/collaboration/testreq/editTestRequestView.do'/>";
				$("#moveForm").attr( "action", url);
 				$("#moveForm").submit();
 				return false;
			}
		});
	}
	
	// 삭제
	del = function() {
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='deleteItem'/>")) {
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/approval/collaboration/testreq/ajaxDeletTestRequest.do",
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
	
	// 추가사항
	addWriteDetail = function() {
	
		iKEP.showDialog({
			title: "<ikep4j:message pre='${preTitle}' key='addWriteDetail' />",
			url: "<c:url value='/approval/collaboration/testreq/addWriteDetailTestRequestPopView.do?trMgntNo=' />"+ "${testRequest.trMgntNo}" ,
			modal: true,
			width: 700,
			height: 250,
			callback : function() {
				
 				return false;
			}
		});
	}
	
	// 파일팝업 ActiveX
	openPopActiveX = function() {
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/testreq/editFilePopViewActiveX.do?trMgntNo='/>" + "${testRequest.trMgntNo}" +"&fileItemId=" + fileItemId;
		
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
		var targetUrl = "<c:url value='/approval/collaboration/testreq/editFilePopViewEcm.do?trMgntNo='/>" + "${testRequest.trMgntNo}" +"&fileItemId=" + fileItemId;
		
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
	
	// 목록으로 가기
	goList = function(){
		var url = "<c:url value='/approval/collaboration/testreq/listTestRequestView.do'/>";
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
			
			if( cachedTdObj.attr("id") == "addrcvDeptId") {
				$("input[name='rcvDeptId']").val( groupId);
				$("input[name='rcvDeptNm']").val( teamName);
			}
// 			alert(managerName);
			alert( "선택한 임직원( "+ managerName + " )으로 선택이 완료되었습니다.");
			cachedTdObj.find('.searchUserName').val(managerName);
			cachedTdObj.find('.searchUserId').val(userId);
		});
	};
	
	ubiOpen = function() {
		
		var ubiProposalUrl = "<c:url value='/approval/collaboration/testreq/ubiReport.do?trMgntNo='/>" + "${testRequest.trMgntNo}";
		
		window.open( ubiProposalUrl, 'ubiReport', 'menubar=no, status=no, toolbar=no, location=no, resizable=yes');
	}
	
	$(document).ready(function() {
		iKEP.iFrameContentResize();
	
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		var defaultValidRule = {   
				rules  : {},
				messages : {},
				submitHandler : function(form) {
		    	
		    	if(confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) {
		    		
		    		if("${testRequest.processGroupNo}" == "1") {
		    			var frm =$("#npdForm");
		    			var reqScheduleCd = frm.find("input[name='reqScheduleCd']:checked").val();
		    			var reqScheduleEtcTxt = frm.find("input[name='reqScheduleEtcTxt']").val();
		    			
		    			if( reqScheduleCd == "50" && reqScheduleEtcTxt =="") {
		    				alert("요구일정을 '기타' 선택시 요구일정 기타내역은 필수 입니다. ");
		    				frm.find("input[name='reqScheduleEtcTxt']").focus();
		    				return;
		    			}
		    		}
	    			writeSubmit(form);
				}
		    }
		};
		
		var requredMsg = "<ikep4j:message pre='${preMessage}' key='required'/>";
		var rangeLength100Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.100'/>";
		var rangeLength500Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.500'/>";
		var rangeLength1000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.1000'/>";
		var rangeLength2000Msg = "<ikep4j:message pre='${preMessage}' key='rangelength.2000'/>";
		
		var reqValidRule = {
				rules  : {
					itemKindCd : {required : true},
					rcvDeptId : {required : true},
					testReqTitle : {required : true, rangelength : [1, 2000]},
					reqScheduleCd : {required : true},
					reqDetail : {required : true},
					reqApprEmpNo : {required : true},
					rcvEmpNo : {required : true},
					companyChkVal : {required : true}
				},
				messages : {
					itemKindCd : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='itemKindCd'/>" + requredMsg },
					rcvDeptId : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='rcvDeptNm'/>"+ requredMsg },
					testReqTitle : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='rcvDeptNm'/>"+ requredMsg , rangelength : "<ikep4j:message pre='${preTitle}' key='custName'/>" + rangeLength500Msg},
					reqScheduleCd : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='reqScheduleCd'/>"+ requredMsg },
					reqDetail : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='reqDetail'/>"+ requredMsg},
					reqApprEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='apprEmpNo'/>"+ requredMsg},
					rcvEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='rcvEmpNm'/>"+ requredMsg},
					companyChkVal : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='companyChkVal'/>"+ requredMsg},
				}
		}
		var rcvValidRule = { 
				rules  : {
					completePlanDate : {required : true},
					rcvApprEmpNo : {required : true},
					resDeptOpinion :{required : true, rangelength : [1, 1000]}
				},
				messages : {
					completePlanDate : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='completePlanDate'/>"+ requredMsg},
					rcvApprEmpNo : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='apprEmpNo'/>"+ requredMsg},
					resDeptOpinion : {direction : "top",    required : "<ikep4j:message pre='${preTitle}' key='resDeptOpinion'/>"+ requredMsg, rangelength : "<ikep4j:message pre='${preTitle}' key='sndReviewOpinion'/>" + rangeLength1000Msg}
				}
		};
		
		var formValidRule = {};
		
		if("${testRequest.processGroupNo}" == "1") {
			formValidRule = $.extend( defaultValidRule, reqValidRule);
		}else if("${testRequest.processGroupNo}" == "2") {
			formValidRule = $.extend( defaultValidRule, rcvValidRule );
		}
		
		<c:if test="${permission.saveBtnActive eq true}">
			new iKEP.Validator("#npdForm", formValidRule);
		</c:if>
		
		writeSubmit = function(targetForm){
		
			$("body").ajaxLoadStart();
			targetForm.submit();
			return false;
		};
		
	    // 달력
	    var datepickerOpts = {
	    		dateFormat: 'yy.mm.dd', 
	    		yearRange:'c-20:c+10',
	    		beforeShow: function(input) {
	    		    var i_offset= $(input).offset(); //클릭된 input의 위치값 체크
	    		    setTimeout(function(){
	    		       $('#ui-datepicker-div').css({'top':i_offset.top -170});      //datepicker의 div의 포지션을 강제로 input 위치에 그리고 좌측은 모바일이여서 작기때문에 무조건 10px에 놓았다.
	    		    });
	    		}
	   		};
	    $("input[name=reqScheduleEtcTxt]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=completePlanDate]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
	    
	    // 사람찾기 - 검토자, 승인자 변경
	    $jq('.searchUserName').keypress( function(event) {
			cachedTdObj = $(this).parent("div");
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
	    
		
		// 권한별 화면 UI변경
		// 요구일정
		$("input[name='reqScheduleCd']").change(function(){
			var val = $(this).val();
			if( val == 50) {
				$("input[name='reqScheduleEtcTxt']").attr("disabled", false);
				$("#reqScheduleEtcTxtDateCal").show();
			}else{
				$("input[name='reqScheduleEtcTxt']").attr("disabled", true);
				$("#reqScheduleEtcTxtDateCal").hide();
			}
		});
		
		var reqFlowViewEnale = "${permission.reqViewActive}";
		if( reqFlowViewEnale == 'true') {
			showElement("req");
		}
		var rcvFlowViewEnale = "${permission.rcvViewActive}";
		if( rcvFlowViewEnale == 'true') {
			showElement( "rcv");
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
		
		// 회사 체크값
		var searchCompanyChkVal = $("input[name='companyChkVal']").val();
		if( searchCompanyChkVal != "") {
			if( searchCompanyChkVal.length == 3) {
				
				for( var i=0; i < 3; i++) {
					var val = searchCompanyChkVal.substring(i, i+1);
					if( val == "1") {
						
						$("#companyChkVal"+i).attr("checked", true);
					}
				}
			}
		}
		
		<c:if test="${not empty fileDataListJson}">
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
		        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	    </c:if>
		
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
.devReqTitleArea{text-align:center; width:100%; height: 100%; line-height: 50px; vertical-align: middle; font-size:30px; font-weight:bold;}
.floatLeft{float:left !important;}
.marginLeft10{margin-left:10px !important;}
.inputReadOnly{ border: 0 !important; background: #fff !important;}
.inputReadOnly2{ background: #fff !important;}
.container INPUT[type='text'] {width:100%;}
.inputNum{text-align:right !important;}
.thMinH{min-height: 28px !important;}
.paddingRight15{padding-right: 10px !important;}
</style>

<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="interceptorKey"  value="collpack.collaboration.board" type="hidden"/> 
</form>

<form id="moveForm" name="moveForm" action="">
	<input type="hidden" name="searchConditionString" value="${searchConditionString }" /> 
	<input type="hidden" name="trMgntNo" value="${testRequest.trMgntNo}" />
	<input type="hidden" name="viewMode" value="${viewMode}"/>
</form>
	
<div class="container"> 
	<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<c:if test="${viewMode eq 'modify'}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span><ikep4j:message pre="${preButton}" key="ubiReport" /></span></a></li>
				</c:if>
				<c:if test="${permission.initRejctBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="initRejct();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${permission.receiptBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="receipt();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${permission.saveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="save();" ><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
				</c:if>
				<c:if test="${permission.deleteBtnActive eq true }">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="del();" ><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<c:if test="${permission.approveBtnActive eq true}">
					<li>
						<c:if test="${permission.saveBtnActive eq true}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="approveWithSave();" >
						</c:if>
						<c:if test="${permission.saveBtnActive eq false}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="approve();" >
						</c:if>
							
						<c:if test="${permission.drafter eq false}">
							<span><ikep4j:message pre="${preButton}" key="approval" /></span>
						</c:if>
						<c:if test="${permission.drafter eq true}">
							<span><ikep4j:message pre="${preButton}" key="approval2" /></span>
						</c:if>
						</a>
					</li>
				</c:if>
				<c:if test="${permission.rejectBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="deny" /></span></a></li>
				</c:if>
				<c:if test="${permission.rejectResonBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="denyReason" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll ne true && permission.reqViewActive eq true} ">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopActiveX();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll eq true && permission.reqViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopEcm();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="goList();"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
				<c:if test="${permission.addWriteDetailBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="addWriteDetail();" ><span>추가사항</span></a></li>
				</c:if>
			 </ul>
		</div>
		<!--//blockButton End-->
	
	<h1 class="none"><ikep4j:message pre="${preTitle}" key="devRequest" /></h1> 
	
<form id="npdForm" name="npdForm" method="post" action="" enctype="multipart/form-data">
	<input type="hidden" name="trMgntNo" value="${testRequest.trMgntNo}" /> 
	<input type="hidden" name="processStatus" value="${testRequest.processStatus}" /> 
	<input type="hidden" name="apprStsCd" value="${testRequest.apprStsCd}" /> 
	<input type="hidden" name="approveStsCd" value="" /> 
	<!-- ecmFile 변수 -->	
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
	<input type="hidden" name="processGroupNo" value="${testRequest.processGroupNo}" />
	<!--  Title Area Start -->
	
		<!--  title -->
		
		<div class="devReqTitleArea" style="height:60px;">
			<span><ikep4j:message pre="${preTitle}" key="testRequest" /></span>
		</div>
		<div style="height:20px;margin-bottom: 10px;">
			<div style="float:right;">
				<input type="checkbox" class="checkbox reqElement" id="companyChkVal0" disabled="disabled" /><span>&nbsp;MP&nbsp;&nbsp;</span>
				<input type="checkbox" class="checkbox reqElement" id="companyChkVal1" disabled="disabled" /><span>&nbsp;P&P&nbsp;&nbsp;</span>
				<input type="checkbox" class="checkbox reqElement" id="companyChkVal2" disabled="disabled" /> <span>&nbsp;SP&nbsp;&nbsp;</span>
				<spring:bind path="testRequest.companyChkVal">
					<input type="hidden" name="${status.expression}" value="${status.value}" />
				</spring:bind>
			</div>
		</div>
	<!--  Title Area End -->
	
	<!--blockDetail Start-->
	<div class="blockDetail">
		<!-- main1 Area Start -->
		<div class="main1Area">
			<table summary="<ikep4j:message pre="${preTitle}" key="summary" />">
				<caption></caption>
				<tbody> 
					<tr>
						<!-- 작성자 -->
						<th class="textCenter thMinH" style="width:80px;"><ikep4j:message pre="${preTitle}" key="writeEmpNm" /></th>
						<td>
							<spring:bind path="testRequest.writeEmpNo">
								<input name="${status.expression}" type="hidden" value="${status.value}"  />
							</spring:bind>
							${ testRequest.writeEmpNm}
						</td>
						
						<!-- 작성일 -->
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="writeDate" /></th>
						<td>
							${testRequest.writeDate }
						</td>
						<!-- 품목 -->
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="itemKindCd" /></th>
						<td>
							<div class="reqEnableArea" style="display:none;">
								<spring:bind path="testRequest.itemKindCd">
									<select name="${status.expression}" style="width:100%;" class="reqElement" disabled="disabled" >
										<option value=""><ikep4j:message pre='${preDefault}' key='option.choose'/></option>
										<c:forEach var="code" items="${ c00016List}">
											<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
										</c:forEach>
									</select> 
								</spring:bind>
							</div>
							<div class="reqDisableArea">
								<c:forEach var="code" items="${ c00016List}">
									<c:if test="${code.comCd eq testRequest.itemKindCd}"><c:out value="${ code.comNm}"></c:out></c:if>
								</c:forEach>
								<span>${newProductDev.mgntNo}</span>
							</div>
						</td>
						<!-- 의뢰부서 ( 결재 상태 ) -->
						<td rowspan="3" class="clearMarPadd0" style="width:300px;height:100%;">
							<table class="clearMarPadd0">
								<tr>
									<th rowspan="3" class="textCenter w20pix">의<br/>뢰<br/>부<br/>서</th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="draft" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="review" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="approval" /></th>
								</tr>
								<tr>
									<!-- 의뢰부서:기안자  -->
									<td class="nameArea" style="height: 51px;">
										<spring:bind path="testRequest.reqDraftEmpNo">
											<input name="${status.expression}" type="hidden" value="${status.value}"  />
										</spring:bind>
										<p>${ testRequest.reqDraftEmpNm}</p>
										<p>( ${ testRequest.reqDraftDate} )</p>
									</td>
									<!-- 의뢰부서:검토자  -->
									<td class="nameArea">
										<div class="reqEnableArea paddingRight15" style="display:none;">
											<spring:bind path="testRequest.reqReviewEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="testRequest.reqReviewEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="reqDisableArea">
											<p>${ testRequest.reqReviewEmpNm}</p>
											<p>
												<c:if test="${testRequest.reqReviewDate != '' && testRequest.reqReviewDate ne null}">( ${ testRequest.reqReviewDate} )</c:if> 
											</p>
										</div>
									</td>
									<!-- 의뢰부서 :승인자 -->
									<td class="nameArea">
										<div class="reqEnableArea paddingRight15" style="display:none;">
											<spring:bind path="testRequest.reqApprEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="testRequest.reqApprEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="reqDisableArea">
											<p>${ testRequest.reqApprEmpNm}</p>
											<p>
												<c:if test="${testRequest.reqApprDate != '' && testRequest.reqApprDate ne null}">( ${ testRequest.reqApprDate} )</c:if> 
											</p>
										</div>
									</td>
								</tr>
								<!-- 의뢰 진행 상태-->
								<tr>
									<td class="textCenter" style="height: 15px;">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq testRequest.reqDraftStsCd}">${code.charCol1}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq testRequest.reqReviewStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq testRequest.reqApprStsCd and code.numCol1 eq 1 }">${code.comNm}</c:if>
										</c:forEach>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<!-- 의뢰부서 -->
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="writeDeptNm" /></th>
						<td>
							<spring:bind path="testRequest.writeDeptId">
								<input name="${status.expression}" type="hidden" value="${status.value}"  />
							</spring:bind>
							${ testRequest.writeDeptNm}
						</td>
						
						<!-- 수신 -->
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="rcvDeptNm" /></th>
						<td class="paddingRight15">
							<spring:bind path="testRequest.rcvDeptId">
								<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}"  />
							</spring:bind>
							<spring:bind path="testRequest.rcvDeptNm">
								<input id="${status.expression}" name="${status.expression}" type="text" readonly="readonly" class="inputbox inputReadOnly" value="${status.value}"  />
							</spring:bind>
						</td>
						<!-- 담당자 : 수신자-->
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="rcvEmpNm" /></th>
						<td>
							<div class="reqEnableArea paddingRight15" id="addrcvDeptId" style="display:none;">
								<spring:bind path="testRequest.rcvEmpNo">
			                    	<input id="${status.expression}" name="${status.expression}" class="searchUserId" type="hidden" value="${status.value}"  />
		                    	</spring:bind>
		                    	<spring:bind path="testRequest.rcvEmpNm">
			                    	<input id="${status.expression}" name="${status.expression}"  class="searchUserName inputbox" type="text" value="${status.value}" style="width:100%;" />
		                    	</spring:bind>
							</div>
							<div class="reqDisableArea">
								${testRequest.rcvEmpNm }
							</div>
						</td>
					</tr>
					<tr>
						<!--제목 -->
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="testReqTitle" /></th>
						<td style="padding-right: 15px;" colspan="5">
							<spring:bind path="testRequest.testReqTitle">
								<input name="${status.expression}" type="text" class="reqElement inputReadOnly inputbox" value="${status.value}" style="width:100%;" readonly="readonly" />
							</spring:bind>
						</td >
					</tr>
					<!-- 요구일정 -->
					<tr>
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="reqScheduleCd" /></th>
						<td colspan="6">
							<div class="floatLeft" >
								<spring:bind path="testRequest.reqScheduleCd">
									<c:forEach var="code" items="${c00013List}">
										<input type="radio" class="reqElement radio" disabled="disabled" name="${status.expression}" <c:if test="${code.comCd eq status.value}">checked="checked"</c:if> value="${code.comCd }" /> <span>${code.comNm}</span>
									</c:forEach>
								</spring:bind>
							</div>
							<div class="floatLeft marginLeft10">
								<spring:bind path="testRequest.reqScheduleEtcTxt">
									<input name="${status.expression}" id="${status.expression}" disabled="disabled" class="inputbox w20 datepicker calDate inputReadOnly2" type="text" maxlength="10" size="50" value="${status.value}"/>
								</spring:bind>
						        <img id="reqScheduleEtcTxtDateCal" style="display: none;" src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
							</div>
						</td>
					</tr>
					<!-- 시험의뢰내용 -->
					<tr>
						<td colspan="7" style="padding-right: 15px;">
							<spring:bind path="testRequest.reqDetail">
								<textarea name="${status.expression}" class="reqElement readOnlyFocusBlur" readonly="readonly" style="line-height:16px; overflow:auto; width:100%; height:400px; border:none; background:none;">${status.value}</textarea>
							</spring:bind>
						</td>
					</tr>
					<!-- 유첨자료 -->
					<tr>
						<th class="textCenter thMinH"><ikep4j:message pre="${preTitle}" key="fileItemId" /></th>
						<td colspan="6">
							<div id="fileDownload"></div>
							<spring:bind path="testRequest.fileItemId">
							<input type="hidden" name="${status.expression}"  value="${status.value}"/>
							</spring:bind>
						</td>
					</tr>
				</tbody> 
			</table>
		</div>
		<!-- main1 Area End -->
		
		<div class="mb20"></div>
			
		<!-- main3 Area Start -->
		<div class="blockDetail main3Area">
			<table>
				<colgroup>
					<col style="width:31px;">
					<col style="width:*;">
					<col style="width:316px;">
				</colgroup>
				<tbody>
					<tr>
						<th colspan="3" class="textCenter"><ikep4j:message pre="${preTitle}" key="testRcvApprEmpNm" /></th>
					</tr>
					<!-- 2차검토부서접수  -->
					<tr>
						<th class="textCenter">의<br>견</th>
						<td class="clearMarPadd0">
							<table>
								<tr>
									<!-- 완료예정일 -->
									<th class="textCenter" style="width:100px;"><ikep4j:message pre="${preTitle}" key="completePlanDate" /> </th>
									<td>
										<div class="rcvEnableArea" style="display:none;">
											<spring:bind path="testRequest.completePlanDate">
												<input name="${status.expression}" class="inputbox w20 datepicker calDate" type="text" maxlength="10" size="50" value="${status.value}"/>
											</spring:bind>
							        		<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						        		</div>
						        		<div class="rcvDisableArea">
						        			${testRequest.completePlanDate }
						        		</div>
									</td>
								</tr>
								<tr>
									<!-- 주관부서 의견 -->
									<td colspan="2">
										<spring:bind path="testRequest.resDeptOpinion">
											<textarea name="${status.expression}" class="rcvElement readOnlyFocusBlur" readonly="readonly" style="padding:0 0;overflow:auto;  line-height:16px; width:100%; height:68px; border:none; background:none;">${status.value}</textarea>
										</spring:bind>
									</td>
								</tr>
							</table>
						</td>
						<td class="clearMarPadd0">
							<table>
								<tr>
									<th rowspan="3" class="textCenter w20pix">접<br/>수<br/>부<br/>서</th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="draft" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="review" /></th>
									<th class="textCenter"><ikep4j:message pre="${ preTitle}" key="approval" /></th>
								</tr>
								<tr>
									<!-- 주관부서 : 기안자  -->
									<td class="nameArea" style="height: 40px;">
										<spring:bind path="testRequest.rcvDraftEmpNo">
											<input name="${status.expression}" type="hidden" value="${status.value}"  />
										</spring:bind>
										<p>${ testRequest.rcvDraftEmpNm}</p>
										<c:if test="${testRequest.rcvDraftDate != '' && testRequest.rcvDraftDate ne null}">( ${ testRequest.rcvDraftDate} )</c:if>
									</td>
									<!-- 주관부서  : 검토자  -->
									<td class="nameArea">
										<div class="rcvEnableArea paddingRight15" style="display:none;">
											<spring:bind path="testRequest.rcvReviewEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="testRequest.rcvReviewEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="rcvDisableArea">
											<p>${ testRequest.rcvReviewEmpNm}</p>
											<p>
												<c:if test="${testRequest.rcvReviewDate != '' && testRequest.rcvReviewDate ne null}">( ${ testRequest.rcvReviewDate} )</c:if> 
											</p>
										</div>
									</td>
									<!-- 주관부서  : 승인자 -->
									<td class="nameArea">
										<div class="rcvEnableArea paddingRight15" style="display:none;">
											<spring:bind path="testRequest.rcvApprEmpNo">
												<input name="${status.expression}" type="hidden" class="searchUserId" value="${status.value}"  />
											</spring:bind>
											<spring:bind path="testRequest.rcvApprEmpNm">
												<input name="${status.expression}" type="text" class="searchUserName inputbox" value="${status.value}" />
											</spring:bind>
										</div>
										<div class="rcvDisableArea">
											<p>${ testRequest.rcvApprEmpNm}</p>
											<p>
												<c:if test="${testRequest.rcvApprDate != '' && testRequest.rcvApprDate ne null}">( ${ testRequest.rcvApprDate} )</c:if> 
											</p>
										</div>
									</td>
								</tr>
								<!-- 주관부서  진행 상태 -->
								<tr>
									<td class="textCenter" style="height: 20px;">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq testRequest.rcvDraftStsCd}">${code.charCol1}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq testRequest.rcvReviewStsCd and code.numCol1 eq 1}">${code.comNm}</c:if>
										</c:forEach>
									</td>
									<td class="textCenter">
										<c:forEach var="code" items="${ c00005List}">
											<c:if test="${code.comCd eq testRequest.rcvApprStsCd and code.numCol1 eq 1 }">${code.comNm}</c:if>
										</c:forEach>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- main3 Area End -->
	</div> 
						
	</form>
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<c:if test="${viewMode eq 'modify'}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span><ikep4j:message pre="${preButton}" key="ubiReport" /></span></a></li>
				</c:if>
				<c:if test="${permission.initRejctBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="initRejct();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${permission.receiptBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="receipt();" ><span><ikep4j:message pre="${preButton}" key="receipt" /></span></a></li>
				</c:if>
				<c:if test="${permission.saveBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="save();" ><span><ikep4j:message pre="${preButton}" key="save" /></span></a></li>
				</c:if>
				<c:if test="${permission.deleteBtnActive eq true }">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="del();" ><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>
				</c:if>
				<c:if test="${permission.approveBtnActive eq true}">
					<li>
						<c:if test="${permission.saveBtnActive eq true}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="approveWithSave();" >
						</c:if>
						<c:if test="${permission.saveBtnActive eq false}">
							<a class="button listBoardItemButton" href="javascript:;" onclick="approve();" >
						</c:if>
							
						<c:if test="${permission.drafter eq false}">
							<span><ikep4j:message pre="${preButton}" key="approval" /></span>
						</c:if>
						<c:if test="${permission.drafter eq true}">
							<span><ikep4j:message pre="${preButton}" key="approval2" /></span>
						</c:if>
						</a>
					</li>
				</c:if>
				<c:if test="${permission.rejectBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="deny" /></span></a></li>
				</c:if>
				<c:if test="${permission.rejectResonBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="reject();" ><span><ikep4j:message pre="${preButton}" key="denyReason" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll ne true && permission.reqViewActive eq true} ">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopActiveX();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<c:if test="${permission.ecmRoll eq true && permission.reqViewActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="openPopEcm();" ><span><ikep4j:message pre="${preButton}" key="fileItemId" /></span></a></li>
				</c:if>
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="goList();"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
				<c:if test="${permission.addWriteDetailBtnActive eq true}">
					<li><a class="button listBoardItemButton" href="javascript:;" onclick="addWriteDetail();" ><span>추가사항</span></a></li>
				</c:if>
			 </ul>
		</div>
		<!--//blockButton End-->
		
	</div>