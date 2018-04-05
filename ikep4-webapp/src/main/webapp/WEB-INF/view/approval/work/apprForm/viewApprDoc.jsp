﻿<%--
=====================================================
* 기능 설명 : 기안문 상세
* 작성자    : approval
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>    
<c:set var="user" value="${sessionScope['ikep.user']}" /> 
   
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"          value="ui.approval.apprForm.header" /> 
<c:set var="preList"            value="ui.approval.apprForm.list" />
<c:set var="preView"            value="ui.approval.apprForm.view" />
<c:set var="preIfm"             value="ui.approval.apprForm.iFM" /> 
<c:set var="preButton"          value="ui.approval.apprForm.button" />
<c:set var="preIfmMessage"      value="message.approval.apprForm.iFM" />
<c:set var="preViewMessage"     value="message.approval.apprForm.view" />
<c:set var="preListMessage"     value="message.approval.apprForm.list" />    
<c:set var="preLineMessage"		value="message.approval.apprForm.apprLine.docView" />
<c:set var="preLineView"		value="ui.approval.apprForm.apprLine" />
<c:set var="preApprLineView"	value="ui.approval.work.apprLine.listApprLineView.view"/>
<%-- 메시지 관련 Prefix 선언 End --%>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/work/apprDoc.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprDoc.js"/>"></script>
<script type="text/javascript">

<!--//  
    iFC.constant.apprType[0]        = "<ikep4j:message pre='${preIfm}' key='apprType.appr'/>";
    iFC.constant.apprType[1]        = "<ikep4j:message pre='${preIfm}' key='apprType.agree'/>";
    iFC.constant.apprType[2]        = "<ikep4j:message pre='${preIfm}' key='apprType.choiceAgree'/>";
    iFC.constant.apprType[3]        = "<ikep4j:message pre='${preIfm}' key='apprType.receive'/>";
    iFC.constant.apprTypeShort[0]   = "<ikep4j:message pre='${preIfm}' key='apprTypeShort.agree'/>";
    iFC.constant.apprTypeShort[1]   = "<ikep4j:message pre='${preIfm}' key='apprTypeShort.choiceAgree'/>";
    iFC.constant.apprStatus[0]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.pending'/>";
    iFC.constant.apprStatus[1]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.progress'/>";
    iFC.constant.apprStatus[2]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.approval'/>";
    iFC.constant.apprStatus[3]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.reject'/>";
    iFC.constant.apprStatus[4]      = "<ikep4j:message pre='${preIfm}' key='apprStatus.agreement'/>";
    
    iFC.message.notChecked          = "<ikep4j:message pre='${preIfm}' key='notChecked'/>";
    iFC.message.checked             = "<ikep4j:message pre='${preIfm}' key='checked'/>";
    iFC.message.approvalPopUp       = "<ikep4j:message pre='${preIfm}' key='approvalPopUp'/>";
    iFC.message.reasonPopUp         = "<ikep4j:message pre='${preIfm}' key='reasonPopUp'/>";
    iFC.message.docDeatilPopUp      = "<ikep4j:message pre='${preIfm}' key='docDeatilPopUp'/>";

	var	DOC2_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.canntComplete' />";
	var DOC3_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.canntReject' />";
	var	DOC4_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.canntReturn' />";
	var	LINE0_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.canntCancel' />";
	var	LINE2_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.alreadyComplete' />";
	var	LINE3_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.alreadyReject' />";
	var	LINE4_APPR	="<ikep4j:message pre='${preLineMessage}' key='appr.alreadyAgreed' />";


	var	DOC2_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.canntComplete' />";
	var DOC3_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.canntReject' />";
	var	DOC4_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.canntReturn' />";
	var	LINE0_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.canntCancel' />";
	var	LINE2_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.alreadyComplete' />";
	var	LINE3_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.alreadyReject' />";
	var	LINE4_EXAM	="<ikep4j:message pre='${preLineMessage}' key='exam.alreadyAgreed' />";
		
	var	DOC2_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.canntComplete' />";
	var DOC3_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.canntReject' />";
	var	DOC4_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.canntReturn' />";
	var	LINE0_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.canntCancel' />";
	var	LINE2_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.alreadyComplete' />";
	var	LINE3_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.alreadyReject' />";
	var	LINE4_BACK	="<ikep4j:message pre='${preLineMessage}' key='back.alreadyAgreed' />";

	var	OK_BACK		="<ikep4j:message pre='${preLineMessage}' key='back.ok' />";	
	var	NODOC		="<ikep4j:message pre='${preLineMessage}' key='noDoc' />";								
	
	var IS_MESSAGE_OPEN=true; // 협조공문 의견공개여부
	
	<c:if test="${!empty apprDoc.parentApprId}">//복사본
		IS_MESSAGE_OPEN=false;
	</c:if>
	<c:if test="${empty apprDoc.parentApprId}">//원본
		<c:if test="${apprDoc.apprDocType==1 && apprConfig.isMessageOpen=='0'}">
		IS_MESSAGE_OPEN=false;
		</c:if>	
	</c:if>	
	
	
	(function($) {
		
		// 문서 현재 상태 체크
		var entrustUserId = "${apprDoc.entrustUserId}";
        getDocStatus = function(apprId,type) {

			// 문서 정보 확인
			var status = false;
		
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprLine/getDocStatus.do"/>',     
				data :  {apprId : apprId, entrustUserId : entrustUserId},     
				type : "get", 
				async : false,
				success : function(result) {  
			
					if(result=="OK") {
						status = true;
					} else if(result=="NODOC") {
						alert(NODOC);
					} else {
						var str = eval(result+'_'+type);
						alert(str);
						
					}
				},
				error : function(event, request, settings){
					 alert("error getDocStatus");
				}
			});			
			return status;
		};
		// 문서 존재유무 - 결재취소로 Child 문서가 삭제되므로 조회에서 화면 제어
        existsDocStatus = function(apprId) {

			// 문서 정보 확인
			var status = false;		
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprLine/existsDocStatus.do"/>',     
				data :  {apprId : apprId},     
				type : "get", 
				async : false,
				success : function(result) {  
			
					if(result=="OK") {
						status = true;
					} else if(result=="NODOC") {
						alert(NODOC);
					} 
				},
				error : function(event, request, settings){
					 alert("error existsDocStatus");
				}
			});			
			return status;
		}; 	
		

		// 문서완료여부
        getDocComplete = function(apprId) {

			// 문서 정보 확인
			var status = false;
		
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprLine/getDocComplete.do"/>',     
				data :  {apprId : apprId},     
				type : "get", 
				async : false,
				success : function(result) {  
			
					if(result=="OK") {
						status = true;
					} else {
						alert('<ikep4j:message pre='${preLineMessage}' key='docProgress' />');
						status = false;
						
					}
				},
				error : function(event, request, settings){
					 alert("error getDocComplete");
				}
			});			
			return status;
		}; 		
		//결재처리
        approvalReqPopup = function(apprId) {

			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preLineView}' key='popup.apprProcess' />",
				url 		: "<c:url value='/approval/work/apprLine/updateApprovalForm.do'/>?apprId="+apprId+"&entrustUserId="+entrustUserId,
				modal 		: true,
				width 		: 700,
				height 		: 300,
				params 		: { apprId : apprId , entrustUserId : entrustUserId },
				callback : function(result) {
					if(result == "OK") {
						alert('<ikep4j:message pre='${preLineMessage}' key='approval' />');
						$("body").ajaxLoadStart("button");
					} else {
						var str = eval(result+'_APPR');
						alert(str);						
																																												
						$("body").ajaxLoadStart("button");
					}
					$("#listApprDocButton").click();
				},
				error : function(event, request, settings){
					 alert("error approvalReqPopup");
					 $("body").ajaxLoadComplete();
				}
			});
			
		};

		//접수자 전결처리
		approvalRecEndPopup = function(apprId) {
		
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preLineView}' key='popup.receiveApproval' />",
				url 		: "<c:url value='/approval/work/apprLine/updateRecEndForm.do'/>?apprId="+apprId+"&entrustUserId="+entrustUserId,
				modal 		: true,
				width 		: 700,
				height 		: 300,
				params 		: {apprId:apprId},
				callback : function(result) {
					if(result == "OK") {
						alert('<ikep4j:message pre='${preLineMessage}' key='receiveApproval' />');
						$("body").ajaxLoadStart("button");
					}
					$("#listApprDocButton").click();
				}
			});
		};		
		
		//참조자 의견입력
		setDocReference = function(apprId) {
		
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message key='ui.approval.work.apprReference.ReferenceView.title'/>",
				url 		: "<c:url value='/approval/work/apprReference/updateApprReferenceForm.do'/>?apprId="+apprId,
				modal 		: true,
				width 		: 700,
				height 		: 250,
				params 		: {apprId:apprId},
				callback : function(result) {
					if(result == "OK") {
						alert("<ikep4j:message key='message.approval.work.apprReference.ReferenceView.success'/>");
						getViewApprReference(apprId);
						$jq("#setDocReference").hide();
					} 
				}
			});
		};
		
		getViewApprReference = function(apprId) {
			
			var $target =$("#viewApprReference");
			$target.empty();
 
			$target.ajaxLoadStart("button");
			$.post("<c:url value='/approval/work/apprReference/listApprReference.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$target.append(result);
					$target.ajaxLoadComplete();
					iKEP.iFrameResize();
				})
				.error(function(event, request, settings) { 
					alert("error getViewApprReference"); 
					$target.ajaxLoadComplete();
				});
		};		
			
		//검토요청
		examRequestPopup = function(apprId) {
		
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message key='ui.approval.work.exam.form.title'/>",
				url 		: "<c:url value='/approval/work/apprExam/updateApprExamForm.do'/>?apprId="+apprId+"&entrustUserId="+entrustUserId,
				modal 		: true,
				width 		: 700,
				height 		: 430,
				params 		: {apprId:apprId},
				callback : function(result) {
					if(result == "OK") {
						alert("<ikep4j:message key='ui.approval.work.exam.validation.confirmExam'/>");
						getViewApprLine(apprId);	
					} 
				}
			});
		};
		
		getViewApprLine = function(apprId) {
			
			var $target =$("#viewApprLine");
			$target.empty();
			//$target.toggle();
			$target.ajaxLoadStart("button");
			$.post("<c:url value='/approval/work/apprExam/listApprExamInfo.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$target.append(result);
					$target.ajaxLoadComplete();
					iKEP.iFrameResize();
				})
				.error(function(event, request, settings) { 
					alert("error getViewApprLine"); 
					$target.ajaxLoadComplete();
				});
		};
		
		getViewApprLineMessage = function(apprId) {
			
			var $target1 =$("#ApprLineMessage");
			$target1.empty();
			//$target.toggle();
			$target1.ajaxLoadStart("button");
			$.post("<c:url value='/approval/work/apprLine/listApprLineMessage.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$target1.append(result);
					$target1.ajaxLoadComplete();
					iKEP.iFrameResize();
				})
				.error(function(event, request, settings) { 
					alert("error getViewApprLineMessage"); 
					$target1.ajaxLoadComplete();
				});
		};
		
		apprDocStatus = function(apprId){
			
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preLineView}' key='popup.progress' />",
				url 		: "<c:url value='/approval/work/apprLine/listApprLineForm.do'/>?apprId="+apprId,
				modal 		: true,
				width 		: 900,
				height 		: 430,
				params 		: {apprId:apprId},
				callback : function(result) {
					
				}
			});
		};
		apprDocSubAllStatus = function(apprId){
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preLineView}' key='popup.treatment' />",
				url 		: "<c:url value='/approval/work/apprLine/listApprLineSubAll.do'/>?apprId="+apprId+"&apprType=0",
				modal 		: true,
				width 		: 800,
				height 		: 430,
				params 		: {apprId:apprId,apprType:0},
				callback : function(result) {
					
				}
			});
		};	
		apprDocRestoreStatus = function(apprId){
			dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preLineView}' key='popup.restore' />",
				url 		: "<c:url value='/approval/work/apprLine/listApprLineAdmin.do'/>?apprId="+apprId+"&apprType=0",
				modal 		: true,
				width 		: 800,
				height 		: 430,
				params 		: {apprId:apprId,apprType:0},
				callback : function(result) {
					if(result == "OKR") {
						alert('<ikep4j:message pre='${preLineMessage}' key='restore' />');
						$("body").ajaxLoadStart("button");
						location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType=listApprDocAllAdmin";
					} else if(result == "OKW") {
						alert('<ikep4j:message pre='${preLineMessage}' key='withdraw' />');
						$("body").ajaxLoadStart("button");
						location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType=listApprDocAllAdmin";
					} else {
						location.reload();					
					}				
				}
			});
		};		
		apprDocPC = function(html, fileName){
			
			dialog = new iKEP.Dialog({     
				title 		: "<ikep4j:message pre='${preButton}' key='savePC'/>",
				url 		: "<c:url value='/approval/work/apprWorkDoc/createApprDocPC.do'/>",
				modal 		: true,
				width 		: 500,
				height 		: 220,
				params 		: {"html":html,"fileName":fileName},
				callback : function(result) {
					
				}
			});
		};
		
		//공람지정
		apprDisplayPopup = function(apprId) {
			
			 dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message key='ui.approval.work.apprDisplay.formTitle'/>", 
				url 		: "<c:url value='/approval/work/apprDisplay/createApprDisplayForm.do'/>?apprId="+apprId,
				modal 		: true,
				width 		: 700,
				height 		: 350,
				params 		: {apprId:apprId},
				callback : function(result) {
					if(result == "OK") {
						alert("<ikep4j:message key='ui.approval.work.apprDisplay.selectedDisplay'/>");
						$("body").ajaxLoadStart("button");
						location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType=myRequestListComplete";
					}
				}
			});
			 
		};
		
		//공람지정 목록
		apprDisplayListPopup = function(apprId) {
			
			 dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message key='ui.approval.work.apprDisplay.title'/>",
				url 		: "<c:url value='/approval/work/apprDisplay/listApprDisplayPop.do'/>?apprId="+apprId,
				modal 		: true,
				width 		: 800,
				height 		: 550,
				params 		: {apprId:apprId},
				callback : function(result) {
					//alert('aaaaa');
				}
			});			 
		};
		
		//개인보관함
		apprUserDocPopup = function(apprId) {
			
			 dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message key='ui.approval.work.apprlist.pageTitle.listApprUserDoc'/>",
				url 		: "<c:url value='/approval/work/userdoc/apprUserDocMenu.do'/>?usePop=Y&apprId="+apprId,
				modal 		: true,
				width 		: 250,
				height 		: 350,
				params 		: {apprId:apprId},
				callback : function(result) {
					//alert('aaaaa');
				}
			});			 
		};
		
		//수정이력
		apprDocHistoryPopup = function(apprId) {

			 dialog = new iKEP.showDialog({     
				title 		: "<ikep4j:message pre='${preButton}' key='viewApprDocHistory'/>",
				url 		: "<c:url value='/approval/work/apprWorkDoc/listApprDocHistory.do'/>?apprId="+apprId,
				modal 		: true,
				width 		: 800,
				height 		: 400,
				params 		: {apprId:apprId},
				callback : function(result) {
				}
			});			 
		};
		
		//결재 회수
		updateApprDocInit = function(apprId) {
            if(!confirm("<ikep4j:message pre='${preLineMessage}' key='withdrawConfirm' />")) return;
	        
	        $("body").ajaxLoadStart("button");
			$.post("<c:url value='/approval/work/apprWorkDoc/ajaxUpdateApprDocInit.do'/>", {apprId	:	apprId, entrustUserId : entrustUserId})
				.success(function(result) {
					//var str = eval(result+'_BACK');
					if(result == "false") {
						alert("<ikep4j:message pre='${preLineMessage}' key='noneInit' />");
						//location.reload();
						$("body").ajaxLoadComplete(); 
						return false;
					}else{
					    alert("<ikep4j:message pre='${preLineMessage}' key='successInit' />");
					    
					    $("#listApprDocButton").click();
					}					
					if(result=='OK') {
						$("#listApprDocButton").click();
						//location.href = $("#apprRejectOfLeft").attr("href");			
					} else {
						return false;
					}
				})
				.error(function(event, request, settings) { 
					alert("error updateApprDocInit"); 
					$("body").ajaxLoadComplete();
				});
		};
		
		//결재 취소
		updateApprDocCancel = function(apprId) {
            if(!confirm("<ikep4j:message pre='${preLineMessage}' key='cancel' />"))
            	return;
	        
	        $("body").ajaxLoadStart("button");
			$.post("<c:url value='/approval/work/apprLine/updateApprovalCancel.do'/>", {apprId	:	apprId, entrustUserId : entrustUserId})
				.success(function(result) {
					$("body").ajaxLoadComplete();
					if(result == "false") {
						alert("<ikep4j:message pre='${preLineMessage}' key='noneCancel' />");
						location.reload();
						return false;
					}else{
					    alert("<ikep4j:message pre='${preLineMessage}' key='successCancel' />");
					    $("#apprDocForm input[name=listType]").val("listApprComplete");
					    $("#listApprDocButton").click();
					}
				})
				.error(function(event, request, settings) { 
					alert("error updateApprDocCancel");
					$("body").ajaxLoadComplete(); 
				});
		};	
		
		//- onload시 수행할 코드
		$(document).ready(function() {

			<c:choose>
				<c:when test="${apprDoc.listType eq 'myRequestList'}">
					$jq("#apprMyrequestOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'myRequestListComplete'}">
					$jq("#apprMyRequestCompleteOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listCompleteAppr'}">
					$jq("#completeApprOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDocAllAdmin'}">
					$jq("#apprDocAllAdminLinkOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDocAllUser'}">
					$jq("#apprDocAllUserOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDocSecurity'}">
					$jq("#apprDocSecurityLinkOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'rejectList'}">
					$jq("#apprRejectOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'tempList'}">
					$jq("#apprTempOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprTodo'}">
					$jq("#apprTodoOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprComplete'}">
					$jq("#apprCompleteOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprIntegrate'}">
					$jq("#apprIntegrateOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDeptRec'}">
					$jq("#apprDeptRecOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprUserRec'}">
					$jq("#apprUserRecOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDeptIntegrate'}">
					$jq("#apprDeptIntegrateOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprReference'}">
					$jq("#apprReferenceOfLeft").click();
				</c:when>
				<c:when test="${searchCondition.listType eq 'listApprCompleteRef'}">
					$jq("#apprCompleteRefOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprReading'}">
					$jq("#apprReadingOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprReadingAssign'}">
					$jq("#apprReadingAssignOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprDelegate'}">
					$jq("#apprDelegateOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprRequestExam'}">
					$jq("#apprRequestExamOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprCompleteExam'}">
					$jq("#apprCompleteExamOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprExam'}">
					$jq("#apprExamOfLeft").click();
				</c:when>
				<c:when test="${apprDoc.listType eq 'listApprAgreement'}">
					$jq("#apprAgreementOfLeft").click();
				</c:when>
				<c:otherwise>
					$jq("#apprTodoOfLeft").click();
				</c:otherwise>
			</c:choose>
		    //- validation
    	    var validOptions = {
                rules  : {                    
                },
                messages : {                    
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
  	            	$("#apprDocForm input[name=apprDocStatus]").val("1");
    	            if($(form).find("input[name=apprLine]").val()=="" && $(form).find("input[name=apprDocStatus]").val()=="1"){
    	                alert("<ikep4j:message pre='${preLineMessage}' key='selectApproval' />");
    	                $("#apprDocForm input[name=apprDocStatus]").val("${apprDoc.apprDocStatus}");    	               
    	                return false;
    	            }
    	            
    	            // 결재 요청시 기안의견 팝업 호출
    	            if(!iFC.object.process && $(form).find("input[name=apprDocStatus]").val()=="1"){
    	                iFU.viewChildApprDocRegisterMessagePopUp();
    	                $("#apprDocForm input[name=apprDocStatus]").val("${apprDoc.apprDocStatus}");
    	                return false;
    	            }
    	            
    	            var message = "<ikep4j:message pre='${preViewMessage}' key='apprRequest'/>";
    	            if(confirm(message)){               
    	                $("body").ajaxLoadStart("button");
                        form.submit();
    	            }else{
    	            	$("#apprDocForm input[name=apprDocStatus]").val("${apprDoc.apprDocStatus}")
    	                return false;
    	            }
                }
    		};			
            //- validator mapping
            new iKEP.Validator("#apprDocForm", validOptions);
                		
		    //- user info
    		iFU.setUserInfo("${user.userId}", "${user.localeCode}", "${apprDoc.registerId}");
    		
    		//- initialize form
    		iFU.initializeApprDocForm("#apprDocForm", "view");
    		
    		//- popup모드
    		<c:if test="${apprDoc.popupYn}">
    		$("body").css("padding-right", "10px");
    		</c:if>
    		
    		// 버튼영역 실행
        	$("#formButtonDiv a").click(function(){
        	    switch (this.id) {

        	    	//문서 다운로드
        	        case "savePCButton":
			            apprDocPC($("#guideConFrame").html(),'${apprDoc.apprTitle}');
                        break;
                   
                    //공문 시행
        	        case "generateOfficial":
        	        	location.href = iKEP.getContextRoot()+"/approval/work/apprOfficial/apprOfficialForm.do?apprId=" + $("#apprDocForm input[name=apprId]").val();
                        break;

        	        // 목록
                    case "listApprDocButton":
                        $("body").ajaxLoadStart("button");
                        if('${apprDocAuth.listType}' == 'listApprUserDoc') {
                        	$.ajax({    
            	    			url : "<c:url value="/approval/work/userdoc/listApprUserDoc.do"/>?folderId=${apprDoc.folderId}",     
            	    			data : {},     
            	    			success : function(result) {
            	    				$jq("#mainContents").empty();
            	    				$jq("#mainContents").append(result);
            	    				$("body").ajaxLoadComplete();
            	    			},
            	    			error : function(event, request, settings){
            	    			 	alert("error");
            	    			}
            	    		}) ;
                        }else {
                        	$("#listForm").attr("action", iKEP.getContextRoot()+"/approval/${apprDocAuth.returnUrl}");
                            $("#listForm").submit();
                        }
                        break;
                         
        	        // 인쇄
        	        case "printApprDocButton":
        	            iFU.print($("#guideConFrame").html());
        			    break;
        			    
        		    // 삭제
        	        case "deleteApprDocButton":
        	            removeApprDoc();
        			    break;
        			    
        			// 결재자 지정
        			case "addApprLineButton":
        				var parentApprId = $("#apprDocForm input[name=parentApprId]").val();
        				var docStatus = $("#apprDocForm input[name=apprDocStatus]").val();
        				// 문서 존재유무 확인
        				if( existsDocStatus($("#apprDocForm input[name=apprId]").val()) ) {
        					if( ( parentApprId != "" && docStatus=='6') || getDocStatus( $("#apprDocForm input[name=apprId]").val(),'APPR') ) {
        			    		var apprId			=	$("#apprDocForm input[name=apprId]").val();
                				var apprLineType	=	$("#apprDocForm input[name=apprLineType]").val();
                		
                				var items = $("#apprDocForm input[name=apprLine]").val()==""?[]:$.parseJSON($("#apprDocForm input[name=apprLine]").val());
                				
                				<c:if test="${apprConfig.lineViewType=='0'}"> 
                				iKEP.showApprovalLine(iFU.setApprLine, items, {selectType:"all",apprId:apprId,apprLineType:apprLineType,entrustUserId:entrustUserId,  isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});
                				</c:if>
                				<c:if test="${apprConfig.lineViewType=='1'}"> 
                				iKEP.showApprovalLine(iFU.setApprLineHeight, items, {selectType:"all",apprId:apprId,apprLineType:apprLineType,entrustUserId:entrustUserId,  isAppend:false, tabs:{common:0, personal:0, collaboration:0, sns:0}});
                				</c:if>	                		

                				
        					}
        				} else {
        					$("#listApprDocButton").click();
        				}
        			    break;
        			    
        			 // 수정이력
        			case "viewApprDocHistoryButton":
                        apprDocHistoryPopup($("#apprDocForm input[name=apprId]").val());
        			    break;
        		
        		     // 진행현황
        			case "viewApprDocPorcessButton":
        				apprDocStatus($("#apprDocForm input[name=apprId]").val());
        			    break;

        		     // 원본결재정보/기안부서결재정보
        			case "viewParentApprDocPorcessButton":
        				apprDocStatus($("#apprDocForm input[name=parentApprId]").val());
        			    break;
           		   
					// Admin > 결재복원
        			case "viewApprDocRestoreButton":
        				if( getDocComplete($("#apprDocForm input[name=apprId]").val()) ) {
        					apprDocRestoreStatus($("#apprDocForm input[name=apprId]").val());
        				} else {
        					location.reload();
        				}
        			    break;
        			// 결재요청
                    case "createChildApprDocButton":
						if( existsDocStatus($("#apprDocForm input[name=apprId]").val()) ) {
							$("#apprDocForm").trigger("submit");
        			    } else {
        					$("#listApprDocButton").click();
        				}
        			    break;
        	    
        	        // 결재 회수
        			case "updateApprDocInitButton":
        				if( existsDocStatus($("#apprDocForm input[name=apprId]").val()) ) {
        					updateApprDocInit($("#apprDocForm input[name=apprId]").val());
        				} else {
        					$("#listApprDocButton").click();
        				}
        			    break;
            	    
        			// 결재 취소
        			case "updateApprDocCancelButton":
        				if( existsDocStatus($("#apprDocForm input[name=apprId]").val()) ) {
        					updateApprDocCancel($("#apprDocForm input[name=apprId]").val());
        				} else {
        					$("#listApprDocButton").click();
        				}
        			    break;      	    
        	   
        	        // 수정
        			case "editApprDocButton":
        			    $("body").ajaxLoadStart("button");
                        location.href="updateApprDocForm.do?apprId=" + $("#apprDocForm input[name=apprId]").val() + "&listType=" + $("#apprDocForm input[name=listType]").val() + "&entrustUserId=" + entrustUserId + "&linkType=" + $("#apprDocForm input[name=linkType]").val();
        			    break;
        			    
        	        // 결재
        			case "newApprDocProcessButton":
        				if( getDocStatus($("#apprDocForm input[name=apprId]").val(),'APPR') ) {
                        	approvalReqPopup($("#apprDocForm input[name=apprId]").val());
        				}
        			    break;
        			    
        			 // 접수자 전결    
        			case "recEndApprDocProcessButton":
        				if( existsDocStatus($("#apprDocForm input[name=apprId]").val()) ) {
                        	approvalRecEndPopup($("#apprDocForm input[name=apprId]").val());
                        } else {
        					$("#listApprDocButton").click();
        				}
        			    break;

        	        // 검토요청
    			    case "viewApprDocExaminationButton":
						//if( getDocStatus($("#apprDocForm input[name=apprId]").val(),'EXAM') ) {
							examRequestPopup($("#apprDocForm input[name=apprId]").val());
						//}
    			        break;
					// 참조의견
        			case "setDocReference":
        				setDocReference($("#apprDocForm input[name=apprId]").val());
        			    break;    			        
        	    
    			    // 공람지정
    			    case "viewApprDisplayButton":
    			    	apprDisplayPopup($("#apprDocForm input[name=apprId]").val());
    			        break;
    			        
    			     // 공람지정 목록
    			    case "viewApprDisplayListButton":
    			    	apprDisplayListPopup($("#apprDocForm input[name=apprId]").val());
    			        break;
    			    
    			     // 개인보관함 폴더목록
    			    case "viewApprUserDocButton":
    			    	apprUserDocPopup($("#apprDocForm input[name=apprId]").val());
    			        break;
    			        
                    default:
                        break;
                }   
            });
    		
    		//- 결재라인
		    <c:if test="${!empty apprLineList && apprDocAuth.apprDocStatus!=6}">
		    initApprLineList();
            </c:if>

    		//- 원본 결재라인 (합의/수신처 문서)
		    <c:if test="${!empty parentApprLineList}">
		    initParentApprLineList();
            </c:if>
            
            //-  수신처
		    <c:if test="${!empty apprReceiveLineList}">
		    initApprReceiveLineList();
            </c:if>
            
            //- 참조자
		    <c:if test="${!empty apprReferenceList}">
		    initReferenceList();
		    </c:if>
		    
		    //- 열람권한
		    <c:if test="${!empty apprReadGroupList || !empty apprReadUserList}">
		    initReadeList();
		    </c:if>
		    
		    //- 기결재 첨부
		    <c:if test="${!empty apprRelationsList}">
		    initRelationsList();
		    </c:if>
		    
		    // 연계 HTML
		    if($("#apprDocForm input[name=isLinkUrl]").val()=="1" && $("#apprDocForm input[name=linkDataType]").val()=="0" && $("#apprDocForm textarea[name=formLinkedData]").val()!=""){
		        $("#formLinkedHtmlDataDiv").html($("#apprDocForm textarea[name=formLinkedData]").val()).show();
		    }
		    
		    if($("#apprDocForm textarea[name=formLayoutData]").val()!=""){
    		    iFC.object.formLayoutData =  $.parseJSON($("#apprDocForm textarea[name=formLayoutData]").val());
	            iFC.object.formData       =  $.parseJSON($("#apprDocForm textarea[name=formData]").val());
	            iFU.printForm();	
	        }
	        
	         //- file download
    		<c:if test="${not empty apprDoc.fileDataList}"> 
    		var downloadOptions = {
    		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
    		   isUpdate : false 
    		};
    		
    		var files="";
            $.each(downloadOptions.files, function() {
                if(files!="") files += "<br/>";
	            files += this.fileRealName;
            });    		 
    		
    		$("#fileListDiv").html(files);
    		
    	    var fileDownloadController = new iKEP.FileController(null, "#fileDownloadDiv", downloadOptions); 
    	    </c:if> 
			
    	 	 //결재의견
			getViewApprLineMessage("${apprDoc.apprId}");
    	 	
    	 	// 참조자 참조의견
			getViewApprReference("${apprDoc.apprId}"); 
			   	 	 
    	    //- 검토요청
			getViewApprLine("${apprDoc.apprId}");
			
        });
        
        //- 참조자
		<c:if test="${!empty apprReferenceList}">
		initReferenceList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprReferenceList}">	
    		items.push({// 공통필수
    			    type            : "user",
					empNo           : "",
					id              : "${item.userId}",
					userName        : "${item.userName}",
					jobTitleName    : "${item.jobTitleName}",
					group           : "${item.groupId}",
					teamName        : "${item.teamName}",
					email           : "${item.mail}",
					mobile          : "",
					searchSubFlag   : false
    		});	
    		</c:forEach>
    		
    		iFU.setReferenceAddress(items, true);
    	};
    	</c:if>	
    	
    	//- 열람권한
		<c:if test="${!empty apprReadGroupList || !empty apprReadUserList}">
		initReadeList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprReadGroupList}">	
    		items.push({// 공통필수
    			    type            : "group", 
	                code            : "${item.groupId}", 
	                name            : "${item.groupName}", 
	                parent          : "${item.parentGroupId}",
	                groupTypeId     : "${item.groupTypeId}",
	                searchSubFlag   : false
    		});	
    		</c:forEach>
    		
    		<c:forEach var="item" items="${apprReadUserList}">	
    		items.push({// 공통필수
    			    type            : "user",
					empNo           : "",
					id              : "${item.userId}",
					userName        : "${item.userName}",
					jobTitleName    : "${item.jobTitleName}",
					group           : "${item.groupId}",
					teamName        : "${item.teamName}",
					email           : "${item.mail}",
					mobile          : "",
					searchSubFlag   : false
    		});	
    		</c:forEach>
    		iFU.setReadAddress(items, true);
    	};
    	</c:if>	
    	
    	//- 기결재 첨부
		<c:if test="${!empty apprRelationsList}">
		initRelationsList	=	function(){
    		// 기결재 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprRelationsList}">	
    		items.push({
					apprId          : "${item.apprId}",
					apprTitle       : "${item.apprTitle}"
    		});	
    		</c:forEach>
    		iFU.setApprMyRequestComplete(items);
    	};
    	</c:if>	
        
        
        //- 결재라인
		<c:if test="${!empty apprLineList && apprDocAuth.apprDocStatus!=6}">
		initApprLineList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
    		
    		<c:forEach var="item" items="${apprLineList}">
    		var endUser ="";
    		<c:if test="${!empty item.apprLineList}">
    		<c:forEach var="itemSub" items="${item.apprLineList}" varStatus="status">
    		<c:if test="${status.index==0}">
    		endUser ="${itemSub.approverName} ${itemSub.approverJobTitle}";
    		</c:if>
    		</c:forEach>
    		</c:if>
    		items.push({// 공통필수
    			type			:	${item.approverType}==1?"group":"user", 
    			apprType		:	${item.apprType},
    			id				:	"${item.approverId}",	
    			code			:	"${item.approverId}",    										
    			userName		:	"${item.approverName}",
    			teamName		:	"${item.approverGroupName}",
    			jobTitleName	:	"${item.approverJobTitle}",	
    			jobTitleCode	:	"${item.approverJobTitleCode}",
    			approverType	:	${item.approverType},		    			    				
    			apprOrder		:	${item.apprOrder},				
    			apprStatus		:	${item.apprStatus},
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			apprDate		:	"<ikep4j:timezone pattern="MM.dd HH:mm" date="${item.apprDate}"/>",
    			name			:	${item.approverType}==1?"${item.approverGroupName}":"${item.approverName} ${item.approverJobTitle} ${item.approverGroupName}",
    			endUser			:	endUser,
    			signFileId		:	"${item.signFileId}"==""?"":"${item.signFileId}"
    		});	
    		</c:forEach>
    		
    		<c:if test="${apprConfig.lineViewType=='0'}"> 
    		iFU.setApprLine(items);
    		</c:if>
    		<c:if test="${apprConfig.lineViewType=='1'}"> 
    		iFU.setApprLineHeight(items);
    		</c:if>
    	};
    	</c:if>	

        //- 원본 결재라인 정보
		<c:if test="${!empty parentApprLineList}">
		initParentApprLineList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items1 = [];
    		<c:forEach var="item" items="${parentApprLineList}">
    		var endUser ="";
    		<c:if test="${!empty item.apprLineList}">
    		<c:forEach var="itemSub" items="${item.apprLineList}" varStatus="status">
    		<c:if test="${status.index==0}">
    		endUser ="${itemSub.approverName} ${itemSub.approverJobTitle}";
    		</c:if>
    		</c:forEach>
    		</c:if>
    		items1.push({// 공통필수
    			type			:	${item.approverType}==1?"group":"user", 
    			apprType		:	${item.apprType},
    			id				:	"${item.approverId}",	
    			code			:	"${item.approverId}",    										
    			userName		:	"${item.approverName}",
    			teamName		:	"${item.approverGroupName}",
    			jobTitleName	:	"${item.approverJobTitle}",	
    			jobTitleCode	:	"${item.approverJobTitleCode}",
    			approverType	:	${item.approverType},		    			    				
    			apprOrder		:	${item.apprOrder},				
    			apprStatus		:	${item.apprStatus},
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			apprDate		:	"<ikep4j:timezone pattern="MM.dd HH:mm" date="${item.apprDate}"/>",
    			name			:	${item.approverType}==1?"${item.approverGroupName}":"${item.approverName} ${item.approverJobTitle} ${item.approverGroupName}",
    			endUser			:	endUser,
    			signFileId		:	"${item.signFileId}"==""?"":"${item.signFileId}"
    		});	
    		</c:forEach>
    		
    		<c:if test="${apprConfig.lineViewType=='0'}"> 
    		iFU.setParentApprLine(items1);
    		</c:if>
    		<c:if test="${apprConfig.lineViewType=='1'}"> 
    		iFU.setParentApprLineHeight(items1);
    		</c:if>    		
    	};
    	</c:if>	

    	
    	//- 수신처
    	<c:if test="${!empty apprReceiveLineList}">
    	initApprReceiveLineList	=	function(){
    		// 결재문서의 결재선 정보 초기 설정
    		var items = [];
    		<c:forEach var="item" items="${apprReceiveLineList}">	
    		items.push({// 공통필수
    			type			:	${item.approverType}==1?"group":"user",
    			apprType		:	${item.apprType},
    			id				:	"${item.approverId}",
    			code			:	"${item.approverId}",
    			userName		:	"${item.approverName}",
    			teamName		:	"${item.approverGroupName}",
    			jobTitleName	:	"${item.approverJobTitle}",	
    			jobTitleCode	:	"${item.approverJobTitleCode}",	    				
    			approverType	:	${item.approverType},
    			apprOrder		:	${item.apprOrder},				
    			apprStatus		:	${item.apprStatus},
    			lineModifyAuth	:	${item.lineModifyAuth},
    			docModifyAuth	:	${item.docModifyAuth},	
    			readModifyAuth	:	${item.readModifyAuth},	
    			apprDate		:	"${item.apprDate}"
    		});	
    		</c:forEach>
    		iFU.setApprReceiveLine(items);
    	};
    	</c:if>    
    	
    	//- 문서 삭제
    	removeApprDoc = function(){
    	    if(confirm("<ikep4j:message pre='${preListMessage}' key='delete'/>")){
    	        $("body").ajaxLoadStart("button");
    	        
    	        $.post(iKEP.getContextRoot()+"/approval/work/apprWorkDoc/deleteApprDoc.do", {"apprId" : $("#apprDocForm input[name=apprId]").val()})
            	.success(function(rst) {
            	    if(rst=="true"){
            		    $("#listApprDocButton").click();
            	    }else{
            	        $("body").ajaxLoadComplete();
            	        alert("<ikep4j:message pre='${preViewMessage}' key='notDeleteDoc'/>");
            	    }
            	})
            	.error(function(event, request, settings) { 
            		$("body").ajaxLoadComplete();
            		alert("error removeApprDoc"); 
            	});	
            }
    	};
    	
    	// 참조자 수정
    	setReferenceAddress = function(data){
            if(!confirm("<ikep4j:message pre='${preViewMessage}' key='changeReference'/>")) return;
            $("body").ajaxLoadStart("button");
            var result="";
            
            $.each(data, function() {
	            if(result!="") result += ";";
                result += this.id + "," + this.group;
            });
            
            $.post(iKEP.getContextRoot()+"/approval/work/apprWorkDoc/ajaxSetReference.do", {"referenceId":result, "apprId" : $("#apprDocForm input[name=apprId]").val(), "entrustUserId" : entrustUserId})
        	.success(function(rst) {
        	    $("body").ajaxLoadComplete();
        	    if(rst=="true"){
        		    iFU.setReferenceAddress(data);
        	    }else{
        	        alert("<ikep4j:message pre='${preViewMessage}' key='notChangeReference'/>");
        	    }
        	})
        	.error(function(event, request, settings) { 
        		$("body").ajaxLoadComplete();
        		alert("error setReferenceAddress"); 
        	});	
            
        };
        
        
        // 열람권한 수정
    	setReadAddress = function(data){
            if(!confirm("<ikep4j:message pre='${preViewMessage}' key='changeRead'/>")) return;
            $("body").ajaxLoadStart("button");
            var result="";            
            
            $.each(data, function() {
	            if(result!="") result += ";";
                
                if(this.type=="user")
                    result +=  "0" + "," + this.id + "," + this.group;
                else
                    result +=  "1" + "," + this.code + "," + this.code;                    
            });            
            
            $.post(iKEP.getContextRoot()+"/approval/work/apprWorkDoc/ajaxSetRead.do", {"readId":result, "apprId" : $("#apprDocForm input[name=apprId]").val(), "entrustUserId" : entrustUserId})
        	.success(function(rst) {
        	    $("body").ajaxLoadComplete();
        	    if(rst=="true"){
        		    iFU.setReadAddress(data);
        	    }else{
        	        alert("<ikep4j:message pre='${preViewMessage}' key='notchangeRead'/>");
        	    }
        	})
        	.error(function(event, request, settings) { 
        		$("body").ajaxLoadComplete();
        		alert("error setReadAddress"); 
        	});	
            
        };
        
	})(jQuery);  

//-->
</script>
<h1 class="none">contnet area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>기안문 상세</h2>
</div>
<!--//pageTitle End-->

<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	<!--blockDetail Start-->
	<div id="formButtonDiv" class="blockButton">
		<ul>	
		
    <c:choose>
	    <c:when test="${apprDocAuth.listType eq 'myRequestList' && apprDocAuth.register && (apprDocAuth.apprDocStatus eq 1 || apprDocAuth.apprDocStatus eq 2)}">
	    	<c:if test="${apprDocAuth.apprDocStatus eq 2 }">
	    		<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
	    		<c:if test="${apprDocAuth.useDisplayButton}">
	        		<li><a id="viewApprDisplayButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplay'/></span></a></li>
	        	</c:if>
	        	<c:if test="${!apprDocAuth.useDisplayButton}">
	        		<li><a id="viewApprDisplayListButton"    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplayList'/></span></a></li>
	        	</c:if>
	        	
			    <li><a id="editApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc'/></span></a></li>
				<li><a id="generateOfficial"                class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='generateOfficial'/></span></a></li>
				<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
			    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>		    		    
			    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
		        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
			    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
	    	</c:if>
	    	<c:if test="${apprDocAuth.apprDocStatus eq 1 }">
	    		 <!-- 기안문서 - 내가 기안한 문서 -->
		        <c:if test="${apprDocAuth.canInitApprDocStatus}">
			    <li><a id="updateApprDocInitButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='updateApprDocInit'/></span></a></li>
			    </c:if>
				<li><a id="viewApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
				<li><a id="printApprDocButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    </c:if>
        </c:when>   
        
	    <c:when test="${apprDocAuth.listType eq 'tempList' && apprDocAuth.register && apprDocAuth.apprDocStatus eq 0}">
	    <!-- 기안문서 - 임시저장 -->
	        <li><a id="editApprDocButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc2'/></span></a></li>
	        <li><a id="deleteApprDocButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='deleteApprDoc'/></span></a></li>
	        <li><a id="printApprDocButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
	    </c:when>  
	    
	    <c:when test="${apprDocAuth.listType eq 'rejectList' && apprDocAuth.register && (apprDocAuth.apprDocStatus eq 3 || apprDocAuth.apprDocStatus eq 4)}">
	    <!-- 기안문서 - 회수/반려 -->
	        <c:if test="${apprDocAuth.apprDocStatus==4}">
	        <li><a id="editApprDocButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc'/></span></a></li>
	        </c:if>
	        <c:if test="${apprDocAuth.apprDocStatus==3}">
	        <li><a id="editApprDocButton"           class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc'/></span></a></li>
	        <li><a id="viewApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
	        </c:if>
	        <li><a id="deleteApprDocButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='deleteApprDoc'/></span></a></li>
	        <li><a id="printApprDocButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
	    </c:when>   
	      
	    
	    <c:when test="${apprDocAuth.listType eq 'listApprTodo' && apprDocAuth.apprStatus eq 1 && !apprDoc.popupYn}">
	    <!-- 결재문서 - 결재해야할 문서 -->        
		    <li><a id="newApprDocProcessButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='newApprDocProcess'/></span></a></li>
		    
		    <c:if test="${apprDocAuth.lineModifyAuth eq 1 && apprConfig.isUpdateLine eq '1'}">
		    <li><a id="addApprLineButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine'/></span></a></li>
		        <!-- 협조공문시 -->
		        <c:if test="${apprDocAuth.apprDocType eq 1  && apprConfig.isReceive eq '1'}">
		        <li><a id="addApprReceiveLineButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprReceiveLine'/></span></a></li>
		        </c:if>
		    </c:if>
		    
		    <c:if test="${apprDocAuth.docModifyAuth eq 1 && apprConfig.isUpdateDoc eq '1'}">
		    <li><a id="editApprDocButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc2'/></span></a></li>
		    </c:if>
		    
		    <c:if test="${apprDocAuth.readModifyAuth eq 1 && apprConfig.isUpdateRead eq '1'}">		    
		    <li><a id="referenceAddressButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='referenceAddress'/></span></a></li>
		    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
		    </c:if>
		    
		    <li><a id="viewApprDocHistoryButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    
		    <c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
		    </c:if>
		    
		    <c:if test="${apprConfig.isExam eq '1'}">
		    <li><a id="viewApprDocExaminationButton"    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocExamination'/></span></a></li>
		    </c:if>
		    
		    <c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
			</c:if>
			
			<li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
        </c:when>   


        <c:when test="${apprDocAuth.listType eq 'listApprComplete' && (apprDocAuth.apprStatus eq 2 || apprDocAuth.apprStatus eq 3 || apprDocAuth.apprStatus eq 4)}">
        <!-- 결재문서 - 결재한 문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
			<c:if test="${apprDocAuth.canCancelApprDocStatus && apprDocAuth.apprDocStatus eq 1 && apprConfig.isCancel eq '1'}">
		    <li><a id="updateApprDocCancelButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='updateApprDocCancel'/></span></a></li>
		    </c:if>
		    <li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    
		    <c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
		    </c:if>		    
		    
		    <c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
			</c:if>
		    
			<li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>        
        </c:when>    

		<c:when test="${apprDocAuth.listType eq 'listApprRequestExam' && apprDocAuth.examUser && apprDocAuth.apprDocStatus eq 1}">
		<!-- 결재문서 - 검토요청 -->
		    <c:if test="${apprDocAuth.examIsRequest && apprConfig.isExam eq '1'}">
		    <li><a id="viewApprDocExaminationButton"    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocExamination'/></span></a></li>
		    </c:if>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>        
		</c:when>
		
        <c:when test="${apprDocAuth.listType eq 'listApprDeptRec'}">
        <!-- 수신문서 - 부서수신 문서 -->
        	<c:choose>
        		<c:when test="${apprDocAuth.apprDocStatus eq 6 }">
        			<li><a id="createChildApprDocButton" 		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='createApprDoc'/></span></a></li>
        			<li><a id="addApprLineButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine2'/></span></a></li>
        			<li><a id="recEndApprDocProcessButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='recEndApprDocProcess'/></span></a></li>
		    
					<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
					<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
					</c:if>
		    		<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		   			<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
					</c:if>					
        		</c:when>
        		<c:when test="${apprDocAuth.apprDocStatus eq 1 }">
        			<c:if test="${apprDocAuth.canInitApprDocStatus}">
        			<li><a id="updateApprDocInitButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='updateApprDocInit'/></span></a></li>
        			</c:if>
					<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
					<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
					</c:if>
		    		<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		   			<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
					</c:if>						
        		</c:when>
        	</c:choose>
        	<li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>      
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>  
            </c:if>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprUserRec'}">
        <!-- 수신문서 개인수신 문서 -->
        	<c:choose>
        		<c:when test="${apprDocAuth.apprDocStatus eq 6 }">
        			<li><a id="createChildApprDocButton" 		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='createApprDoc'/></span></a></li>
        			<li><a id="addApprLineButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine2'/></span></a></li>
        			<li><a id="recEndApprDocProcessButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='recEndApprDocProcess'/></span></a></li>
		    
					<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
					<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
					</c:if>
		    		<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		   			<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
					</c:if>					
        		</c:when>
        		<c:when test="${apprDocAuth.apprDocStatus eq 1 }">
        			<c:if test="${apprDocAuth.canInitApprDocStatus}">
        			<li><a id="updateApprDocInitButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='updateApprDocInit'/></span></a></li>
        			</c:if>
        			<li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
					<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
					<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
					</c:if>
		    		<c:if test="${!empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		   			<li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
					</c:if>						
        		</c:when>
        	</c:choose>
        	<li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li> 	
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>  
            </c:if>	        
        </c:when>
        
        
        <c:when test="${apprDocAuth.listType eq 'listApprDisplayWaiting' && apprDocAuth.displayUser && apprDocAuth.useDisplayDoc && apprDocAuth.apprDocStatus eq 2}">
        <!-- 공람문서 - 공람대기문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
            <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
            <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
            <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        <c:when test="${apprDocAuth.listType eq 'listApprDisplayComplete' && apprDocAuth.displayUser && apprDocAuth.useDisplayDoc && apprDocAuth.apprDocStatus eq 2}">
        <!-- 공람문서 - 공람완료문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
        	<li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
            <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
            <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        
        <c:when test="${apprDocAuth.listType eq 'listApprReference' && apprDocAuth.referencer && (apprDocAuth.apprDocStatus eq 1 || apprDocAuth.apprDocStatus eq 3)}">
        <!-- 참조문서 - 참조함 -->
            <c:if test="${apprDocAuth.apprDocStatus==1 && apprDocAuth.hasReference && apprConfig.isRefMsg eq '1'}">
            <li><a id="setDocReference" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='setDocReference'/></span></a></li>
            </c:if>
            <li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprReading' && apprDocAuth.reader && apprDocAuth.apprDocStatus eq 2}">
        <!-- 참조문서 - 열람획득문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
        	<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprReadingAssign' && apprDocAuth.register && apprDocAuth.apprDocStatus eq 2}">
        <!-- 참조문서 - 열람부여문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus eq 2}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
            <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
        	<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprDelegate' && (apprDocAuth.apprDocStatus==1 || apprDocAuth.apprDocStatus==3 || apprDocAuth.apprDocStatus==2)}">
        <!-- 참조문서 - 위임문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
        	<li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
        	<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <c:if test="${apprDocAuth.apprDocStatus=='2'}">
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
		    </c:if>
        </c:when>
        
        
        <c:when test="${(apprDocAuth.listType eq 'listApprIntegrate' || apprDocAuth.listType eq 'listApprUserDoc') &&  apprDocAuth.apprDocStatus==2 && !apprDoc.popupYn}">
        <!-- 완료문서 - 전체문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2' && apprDocAuth.listType ne 'listApprUserDoc'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId && apprDocAuth.useDisplayButton}">
        		<li><a id="viewApprDisplayButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplay'/></span></a></li>
        	</c:if>
        	<c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId && !apprDocAuth.useDisplayButton}">
        		<li><a id="viewApprDisplayListButton"    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplayList'/></span></a></li>
        	</c:if>
        	
        	<c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId}">
		    <li><a id="editApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc'/></span></a></li>
			<li><a id="generateOfficial"                class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='generateOfficial'/></span></a></li>
			</c:if>
			
			<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    
		    <c:if test="${apprDocAuth.register && !empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
		    </c:if>
		    
		    <c:if test="${apprDocAuth.register && !empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
			</c:if>
		    
		    <c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId}}">
		    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
            </c:if>
        
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprDeptIntegrate' &&  apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 부서결재 문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId && apprDocAuth.useDisplayButton}">
        		<li><a id="viewApprDisplayButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplay'/></span></a></li>
        	</c:if>
        	<c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId && !apprDocAuth.useDisplayButton}">
        		<li><a id="viewApprDisplayListButton"    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplayList'/></span></a></li>
        	</c:if>
        	
        	<c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId}">
		    <li><a id="editApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc'/></span></a></li>
			<li><a id="generateOfficial"                class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='generateOfficial'/></span></a></li>
			</c:if>
			
			<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    
		    <c:if test="${apprDocAuth.register && !empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 1}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess'/></span></a></li>
		    </c:if>
		    
		    <c:if test="${apprDocAuth.register && !empty apprDocAuth.parentApprId && apprDocAuth.parentApprType eq 3}">
		    <li><a id="viewParentApprDocPorcessButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewParentApprDocPorcess2'/></span></a></li>
			</c:if>
		    
		    <c:if test="${apprDocAuth.register && empty apprDocAuth.parentApprId}">
		    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
            </c:if>
        
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'myRequestListComplete' && apprDocAuth.register && apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 기안한문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <c:if test="${apprDocAuth.useDisplayButton}">
        		<li><a id="viewApprDisplayButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplay'/></span></a></li>
        	</c:if>
        	<c:if test="${!apprDocAuth.useDisplayButton}">
        		<li><a id="viewApprDisplayListButton"    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDisplayList'/></span></a></li>
        	</c:if>
        	
		    <li><a id="editApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc'/></span></a></li>
			<li><a id="generateOfficial"                class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='generateOfficial'/></span></a></li>
			<li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>		    		    
		    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listCompleteAppr' && apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 결재한문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>		    		    
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprAgreement' && apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 합의한문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>		    		    
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprCompleteRef' && apprDocAuth.referencer && apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 참조한 문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <li><a id="viewApprDocHistoryButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>		    		    
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprCompleteExam' && apprDocAuth.examStatus && apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 검토한문서 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>		    		    
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprDocAllUser' && apprDocAuth.apprDocStatus==2}">
        <!-- 완료문서 - 결재문서전체조회 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <li><a id="viewApprDocPorcessButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>		    		    
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprDocAllAdmin'}">
        <!-- Admin - 결재문서 관리 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
            <li><a id="deleteApprDocButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='deleteApprDoc'/></span></a></li>
        	<c:if test="${apprDocAuth.apprDocStatus==1}">
			<li><a id="viewApprDocRestoreButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='apprRestore'/></span></a></li>
		    <li><a id="addApprLineButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine'/></span></a></li>
	            <!-- 협조공문시 -->
    	        <c:if test="${apprDocAuth.apprDocType	eq 1}">
    	        <li><a id="addApprReceiveLineButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprReceiveLine'/></span></a></li>
    	        </c:if>		    
		    <li><a id="editApprDocButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc2'/></span></a></li>
		    <li><a id="referenceAddressButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='referenceAddress'/></span></a></li>
		    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
		    </c:if>
		    <li><a id="viewApprDocHistoryButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        
        <c:when test="${apprDocAuth.listType eq 'listApprDocSecurity'}">
        <!-- ADMIN - 결재문서 보안관리 -->
        	<c:if test="${apprDocAuth.apprDocStatus=='2'}">
        	<li><a id="viewApprUserDocButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprUserDoc'/></span></a></li>
        	</c:if>
	        <li><a id="deleteApprDocButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='deleteApprDoc'/></span></a></li>
        	<c:if test="${apprDocAuth.apprDocStatus=='1'}">
			<li><a id="viewApprDocRestoreButton"        class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='apprRestore'/></span></a></li>
		    <li><a id="addApprLineButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprLine'/></span></a></li>
	            <!-- 협조공문시 -->
    	        <c:if test="${apprDocAuth.apprDocType	eq 1}">
    	        <li><a id="addApprReceiveLineButton"	class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='addApprReceiveLine'/></span></a></li>
    	        </c:if>		    
		    <li><a id="editApprDocButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='editApprDoc2'/></span></a></li>
		    <li><a id="referenceAddressButton"			class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='referenceAddress'/></span></a></li>
		    <li><a id="readAddressButton"				class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='readAddress'/></span></a></li>
		    </c:if>
		    <li><a id="viewApprDocHistoryButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocHistory'/></span></a></li>
		    <li><a id="viewApprDocPorcessButton"		class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='viewApprDocPorcess'/></span></a></li>
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
       <c:when test="${apprDocAuth.listType eq 'listApprExam' && (apprDocAuth.apprDocStatus==1 || apprDocAuth.apprDocStatus==3)}">
        <!-- 완료문서 - 검토한문서 -->		    		    
	        <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="savePCButton"                    class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='savePC'/></span></a></li>
        </c:when>
        
        <c:when test="${apprDocAuth.listType eq 'listApprEntrust'}">
		<!-- 환경설정 - 수임현황 -->
		    <li><a id="printApprDocButton"              class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='printApprDoc'/></span></a></li>
		    <li><a id="listApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='listApprDoc'/></span></a></li>        
		</c:when>
        
        <c:otherwise>
            
        </c:otherwise> 
    </c:choose>  
    <c:if test="${!apprDoc.popupYn}">
        <li><a id="listApprDocButton"               class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='listApprDoc'/></span></a></li>
    </c:if>
		</ul>
	</div>
	<!--  결재선 지정 (원본/복사본 결재선 표시) -->
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td height="60" colspan="2" align="center" class="font_style1">${apprForm.formName}</td>
        </tr>
    </table>
    
	<c:if test="${apprConfig.lineViewType=='0'}"> 
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td colspan="2">
    	<!-- child 문서에서 원본 결재 -->
		<c:if test="${!empty apprDoc.parentApprId}">
		<c:set var="align" value="left"/>
    		<table width="100%" border="0" cellspacing="0" cellpadding="0">
        	<tr id="apprLine2Tr">
            <td colspan="2" align="right">
                <table border="0" cellspacing="0" cellpadding="0" class="table_style">
                    <tr id="apprLine2TTr">
                        <th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='apprTh' htmlEscape='fasle' /></th>
                    </tr>
                    <tr id="apprLine2BTr">
                    </tr>
                </table>
            </td>
			</tr>
			</table>		
		</c:if>
		
		<!-- 원본 현재 결재선 -->
		<c:if test="${empty apprDoc.parentApprId}">
		<c:set var="align" value="right"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="apprLineTable">
        	<tr id="apprLine0Tr">
            <td colspan="2" align="${align}">
                <table border="0" cellspacing="0" cellpadding="0" class="table_style">
                    <tr id="apprLine0TTr">
                        <th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='apprTh' htmlEscape='fasle' /></th>
                    </tr>
                    <tr id="apprLine0BTr">
                    </tr>
                </table>
            </td>
			</tr>
			</table>
		</c:if>    
    </td>
    </tr>
	<tr>
	<td colspan="2" align="right">&nbsp;</td>
	</tr>

	<c:if test="${!empty apprDoc.parentApprId}">
	<c:set var="align" value="left"/>
    <tr>
    <td>
		<!-- child 에서 현재문서 결재선 -->	
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr id="apprLine0Tr">
		<td colspan="2" align="${align}">
			<table border="0" cellspacing="0" cellpadding="0" class="table_style">
				<tr id="apprLine0TTr">
					<th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='apprTh' htmlEscape='fasle' /></th>
				</tr>
				<tr id="apprLine0BTr">
				</tr>
			</table>
		</td>
		</tr>
		</table>
	</td>
	<td>
		<!-- child 문서에서 원본 합의 -->			
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr id="apprLine3Tr" class="none">
		<td colspan="2" align="right">
			<table border="0" cellspacing="0" cellpadding="0" class="table_style">
				<tr id="apprLine3TTr">
					<th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='agreeTh' htmlEscape='fasle' /></th>
				</tr>
				<tr id="apprLine3BTr">
				</tr>
			</table>
		</td>
		</tr>
		</table>	
	</td>
	</tr>					
	</c:if>
	
	<!-- 원본 현재 합의 -->
	<c:if test="${empty apprDoc.parentApprId}">
	<c:set var="align" value="right"/>
    <tr>
    <td colspan="2">		
		<table width="100%" border="0" cellspacing="0" cellpadding="0" id="apprLineTable">
		<tr id="apprLine1Tr">
		<td colspan="2" align="${align}">
			<table border="0" cellspacing="0" cellpadding="0" class="table_style">
				<tr id="apprLine1TTr">
					<th width="25" height="100" rowspan="2" align="center" class="first"><ikep4j:message pre='${preView}' key='agreeTh' htmlEscape='fasle' /></th>
				</tr>
				<tr id="apprLine1BTr">
				</tr>
			</table>
		</td>
		</tr>
		</table>
	</td>
	</tr>
	</c:if> 
	
	<tr>
	<td colspan="2" align="right">&nbsp;</td>
	</tr>		
    </table>
	</c:if>     

	<c:if test="${apprConfig.lineViewType=='1'}"> 
		<!--blockListTable Start-->
		<div class="Approval_1">
		
			<table summary="<ikep4j:message pre='${preView}' key='subLine.summary' />">
				<caption></caption>
				<col style="width: 10%;"/>
				<col />
				<col style="width: 20%;"/>
				<col style="width: 12%;"/>
				<col style="width: 12%;"/>
				<col style="width: 15%;"/>
				
				<thead>
					<tr>				
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.no' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.approver' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.dept' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.apprType' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.status' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.apprDate' /></th>
					</tr>
				</thead>
				
				<tbody id="apprLineListBody" class="table_style">
				
				</tbody>
			</table>				
			<div class="clear"></div>	
		</div>		
			
		<c:if test="${!empty apprDoc.parentApprId}">
		<b><ikep4j:message pre='${preApprLineView}' key='pageTitle' /></b>
		<div class="Approval_1">
			<table summary="<ikep4j:message pre='${preView}' key='subLine.summary' />">
				<caption></caption>
				<col style="width: 10%;"/>
				<col />
				<col style="width: 20%;"/>
				<col style="width: 12%;"/>
				<col style="width: 12%;"/>
				<col style="width: 15%;"/>
				
				<thead>
					<tr>				
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.no' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.approver' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.dept' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.apprType' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.status' /></th>
						<th class="textCenter" scope="row"><ikep4j:message pre='${preApprLineView}' key='line.apprDate' /></th>
					</tr>
				</thead>
				
				<tbody id="apprLineListParentBody" class="table_style">
				
				</tbody>
			</table>
		</div>		
		</c:if> 					
		
	</c:if>

	<div class="Approval_1">
    <form id="apprDocForm" name="apprDocForm" method="post" action="updateChildApprDoc.do">
        <spring:bind path="apprDoc.formLayoutData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <!-- 연계 양식 -->
        <spring:bind path="apprDoc.formLinkedData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <!-- 양식 연계여부 -->
        <spring:bind path="apprForm.isLinkUrl">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 양식 데이터 타입 0:html, 1:json -->
        <spring:bind path="apprForm.linkDataType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <spring:bind path="apprDoc.formData">
        <textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
        </spring:bind>
        
        <spring:bind path="apprDoc.formId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        <spring:bind path="apprDoc.apprId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        <spring:bind path="apprDoc.parentApprId">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>        
        <spring:bind path="apprForm.apprDocType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        <spring:bind path="apprDoc.apprLine">
        <input type="hidden" name="${status.expression}"/>
        </spring:bind>
        <spring:bind path="apprDoc.apprReceiveLine">
        <input type="hidden" name="${status.expression}"/>
        </spring:bind>
        <spring:bind path="apprDoc.listType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        <spring:bind path="apprDoc.linkType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        <!-- 문서유형(0:순차,1:병렬) -->
        <spring:bind path="apprDoc.apprLineType">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>       
		
		<spring:bind path="apprDoc.apprDocStatus">
        <input type="hidden" name="${status.expression}" value="${status.value}"/>
        </spring:bind>
        
        <!-- 기안자 결재요청 시 등록 의견 -->
        <spring:bind path="apprDoc.registerMessage">
        <input type="hidden" name="${status.expression}" />
        </spring:bind>       
        
        <!-- 위임자 정보 -->
        <spring:bind path="apprDoc.entrustUserId">
        <input type="hidden" name="${status.expression}" />
        </spring:bind>
        
		<table summary="기본정보">
			<caption></caption>
			<tbody>
			    <tr>
					<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprNo'/></th>
					<td width="35%">${apprDoc.apprDocNo}</td>
					<th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprReqDate'/></th>
					<td width="35%"><ikep4j:timezone pattern="yyyy-MM-dd" date="${apprDoc.apprReqDate}"/></td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='registerName'/></th>
					<td>${apprDoc.registerName}</td>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprGroupId'/></th>
					<td>${apprDoc.apprGroupName}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprSecurity'/></th>
					<td>
					    <c:if test="${apprDoc.apprSecurityType eq 0}"><ikep4j:message pre='${preView}' key='gerneral'/></c:if>
    			        <c:if test="${apprDoc.apprSecurityType eq 1}"><ikep4j:message pre='${preView}' key='security'/></c:if>
    			    </td>
					<th scope="row"><ikep4j:message pre='${preView}' key='apprPeriodCd'/></th>
					<td>${apprDoc.apprPeriodNm}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preView}' key='referenceId'/> </th>
					<td>
					    <div id="referenceDiv"></div>
    					<spring:bind path="apprDoc.referenceId">
    					<input type="hidden" name="${status.expression}" />
    					</spring:bind>
    					<spring:bind path="apprDoc.referenceSet">
    					<select name="${status.expression}" class="inputbox w100" size="3" multiple="multiple" style="display:none;"></select>
    					</spring:bind>
				    </td>
					<th scope="row"><ikep4j:message pre='${preView}' key='readId'/></th>
					<td>
					    <div id="readDiv"></div>
					    <spring:bind path="apprDoc.readId">
    					<input type="hidden" name="${status.expression}" />
    					</spring:bind>
    					<spring:bind path="apprDoc.readSet">
    					<select name="${status.expression}" class="inputbox w100" size="3" multiple="multiple" style="display:none;"></select>
    					</spring:bind>
				    </td>
		        </tr>
		        <tr id="apprReceiveLineTr">
				    <th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprReceive'/></th>
					<td colspan="3" id="apprReceiveLineInfoDiv"></td>
				</tr>
				<tr>
				    <th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprRefInfo'/></th>
					<td colspan="3">
					    <ul id="apprRefInfoUl">
				        </ul>
					</td>
				</tr>
				<tr>
				    <th width="15%" scope="row"><ikep4j:message pre='${preView}' key='apprTitle'/></th>
					<td colspan="3">${apprDoc.apprTitle}</td>
				</tr>
				<c:if test="${not empty apprDoc.fileDataList}"> 
				<tr>
				    <th width="15%" scope="row"><ikep4j:message pre='${preView}' key='fileDataList'/></th>
					<td colspan="3"><div id="fileDownloadDiv" class="mt10"></div><div id="fileListDiv" class="none"></div></td>
				</tr>
				</c:if>
	        </tbody>
	    </table>
	    <div id="formLinkedHtmlDataDiv" style="border: 1px solid #e0e0e0;" class="mt10 padding10 none"></div>
	    <table summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
	        <tbody>
	        <c:if test="${not empty apprForm.formLayoutData}">
				<tr>
				    <td>
				    <div id="formLayoutDiv">
                		<table id="layoutTable" summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
                			<caption></caption>
                			<tbody>
                			</tbody>
                			<tfoot>
                            </tfoot>
                        </table>
                    </div>    
				    </td>
			    </tr>
	        </c:if>
			</tbody>
		</table>
		</form>
	</div>
	<c:if test="${apprForm.isApprEditor eq 1}">
    <div class="Approval_1">
	    <table summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
	        <tbody>
			    <tr>
			        <td>${apprDoc.formEditorData}</td>
                </tr>
            </tbody>
        </table>
     </div>
     </c:if>

	<div id="ApprLineMessage" class="pt10"></div>	
	<br />
	<div id="viewApprReference"></div>	
	<br />	
	<div id="viewApprLine"></div>	
	<br />
	<!--//blockDetail End-->

<form id="listForm" method="post"> 
    <spring:bind path="apprListSearchCondition.searchApprDocStatus">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchApprDocType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.apprId">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchStartDate">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchEndDate">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchApprTitle">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchFormName">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchReqUserName">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchUserName">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchIsHidden">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchListType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchGroupId">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchEntrustUserName">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.searchApprDocNo">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.listType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.linkType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.entrustUserId">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.entrustId">
	<input name="${status.expression}" type="hidden" value="${status.value}"/>
	</spring:bind>
	<spring:bind path="apprListSearchCondition.entrustType">
	<input name="${status.expression}" type="hidden" value="${status.value}"/>
	</spring:bind>
    <spring:bind path="apprListSearchCondition.searchlistSortType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.sortColumn">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.sortType">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.pageIndex">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="apprListSearchCondition.pagePerRecord">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
</form>	 
</div>