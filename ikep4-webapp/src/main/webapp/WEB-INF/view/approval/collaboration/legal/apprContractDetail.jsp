<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<style>
	.container{width:950px;}
 	.topTable table{
	 	border: 1px solid #c5c5c5;
	 	border-collapse:collapse;
	 	align: center;
	 }
	.topTable table th, td {
	    border: 1px solid #c5c5c5; 
	    border-collapse:collapse;
	    border-top:1px solid #e0e0e0;
	    height:30px;
	 }
	.blockDetail table th, td {
  		text-align:center;
	}
	.blockListTable table th, td {
		height:15px;
	}
	.blockListTable table th, td { 
		word-break:break-all; 
		text-align:center; 
		line-height:14px; 
		padding:6px 5px 5px 5px; 
		border:1px solid #e0e0e0;}
</style>

<script type="text/javascript">
var fileController = "";		//파일등록폼
var cachedTdObj;
(function($) {

	
	$(document).ready( function() {
		<c:if test="${not empty fileDataListJson}">
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
		        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	    </c:if>
	    
		setUser = function(data) {
			var managerName = "";
			var userId = "";
			
			$jq.each(data, function() {
			managerName = $jq.trim(this.userName);
			userId = $jq.trim(this.empNo);	
				
				cachedTdObj.find('.searchUserName').val(managerName);
				cachedTdObj.find('.searchUserId').val(userId);
			});
		};
		
		// [검색-직원선택] ================== Start
		$jq('.addrSearchBtn').click( function() {
			cachedTdObj = $(this).parent("td");
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			cachedTdObj.find('.searchUserName').trigger("keypress");
		});
		
		$jq('.searchUserName').keypress( function(event) {
			cachedTdObj = $(this).parent("td");
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser2(event, "N", setUser);			
		});
		
		/* 기안부서접수 */
		$(".goReqReceip").click(function(){
			if(confirm("접수하시겠습니까?")) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractReqReceipt.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert("접수하였습니다.");
							var id = $('#contractMgntNo').val();
//	 						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
							location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+"&apprLv=1&processStatus=10";
						} else {
							alert("저장중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		/* 법무결제접수 */
		$(".goRcvReceip").click(function(){
			if(confirm("접수하시겠습니까?")) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractRcvReceipt.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert("접수하였습니다.");
							var id = $('#contractMgntNo').val();
//	 						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
							location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+"&apprLv=4&processStatus=20";
						} else {
							alert("저장중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		/* 승인 */
		$(".goArrp").click(function(){
			var title = "";
			var contents = "";
			if('${reqDraftYn}' == 'Y' || '${rcvDraftYn}' == 'Y') {
				txt = "상신";
				contents = "결제상신후에는 수정할 수 없습니다. 결재상신 하시겠습니까?";
			} else {
				txt = "승인";
				contents = "승인 하시겠습니까?";
			}
			if(confirm(contents)) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractAppr.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert(txt + " 하였습니다.");
							var id = $('#contractMgntNo').val();
							var apprLv = result.apprLegal.apprLv;
							var processStatus = result.apprLegal.processStatus;
//	 						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
							location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+'&apprLv='+apprLv+'&processStatus='+processStatus;
						} else {
							alert(txt + "중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		/* 기안부서 저장 */
		$(".goSave").click(function(){
			
			if(confirm("저장하시겠습니까?")) {
				var processStatus = $('#processStatus').val();
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractModify.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert("저장하였습니다.");
							var id = $('#contractMgntNo').val();
//	 						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
							location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+"&apprLv=1&processStatus="+processStatus;
						} else {
							alert("저장중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		/* 법무부서 저장 */
		$(".goSaveRcv").click(function(){
			var processStatus = $('#processStatus').val();
			if(confirm("저장하시겠습니까?")) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractModify.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert("저장하였습니다.");
							var id = $('#contractMgntNo').val();
// 	 						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
							location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+"&apprLv=4&processStatus="+processStatus;
						} else {
							alert("저장중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		/* 삭제 */
		$(".goDelete").click(function(){
			if(confirm("삭제하시겠습니까?")) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractDelete.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert("삭제하였습니다.");
							var id = $('#contractMgntNo').val();
//	 						alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
							location.href="<c:url value='/approval/collaboration/legal/apprContractList.do'/>";
						} else {
							alert("삭제중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		/* 반려 */
		$(".goReject").click(function(){
			var id = $('#contractMgntNo').val();
			var apprLv = $('#apprLv').val();
			var apprStsCd = $('#apprStsCd').val();
			iKEP.showDialog({
				title: "계약서 반려 요청",
				url: "<c:url value='/approval/collaboration/legal/apprRejectPopUp.do?contractMgntNo=' />"+id+"&apprLv="+apprLv+'&apprStsCd='+apprStsCd,
				modal: true,
				width: 800,
				height: 180,
				callback : saveCallbackReject
			});
		});
		//반려 콜백
		saveCallbackReject = function (result) {
			alert("반려되었습니다.");
			var id = $('#contractMgntNo').val();
			var apprLv = result.apprLv;
			var processStatus = result.processStatus;
//				alert("<ikep4j:message pre='${preMessage}' key='saveSuccess' />");
			location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+'&apprLv='+apprLv+'&processStatus='+processStatus;
// 			$("#searchButton").trigger("click");
		};
		
		/* 이력등록, 검토이력 등록용 파일업로드팝업창*/
		openPopHistory = function() {
			var contractMgntNo = $("input[name='contractMgntNo']").val();
			var apprLv = $('#apprLv').val();
			var title = "";
			var ecmYn = "";
			if('${ecmRoll}') { //ecm인경우
				title = "ECM 검토이력 등록";
				ecmYn = "Y";
			} else {
				title = "ActiveX 검토이력 등록";
				ecmYn = "N";
			}
			
			iKEP.showDialog({
				title: title,
				url: "<c:url value='/approval/collaboration/legal/apprHistoryPopUp.do?contractMgntNo='/>" + contractMgntNo + "&apprLv="+apprLv +"&ecmYn=" +ecmYn,
				modal: true,
				width: 700,
				height: 370,
				callback :function(_p) {
					location.reload();
				}
			});
		}
		
		//이력 상세
		goHistoryDetail= function(seq, id){
			iKEP.showDialog({
				title: "계약서 검토요청",
				url: "<c:url value='/approval/collaboration/legal/apprHistoryDetailPopUp.do?historySeqno=' />"+seq+'&contractMgntNo='+id,
				modal: true,
				width: 700,
				height: 240
			});
		};
		
		
		/* 종결처리 */
		$(".goHistoryEnd").click(function(){
			var id = $('#contractMgntNo').val();
			
			if(confirm("종결 하시겠습니까?")) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/legal/ajaxContractHistoryEnd.do'/>',
					data : $jq("#contractDetailForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							alert("종결하였습니다.");
							var id = $('#contractMgntNo').val();
							location.href="<c:url value='/approval/collaboration/legal/apprContractDetailRe.do?contractMgntNo='/>"+id+"&apprLv=7&processStatus=99";
						} else {
							alert("종결중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		//검토결과 등록
		$(".reviewResult").click(function(){
			var id = $('#contractMgntNo').val();
			var title = $('#contractTitle').val();
			var stsCd = $('#apprStsCd').val();
			var reqDate = $('#apprReqDate').val();
			var apprDate = $('#apprDate').val();
			var resultCnt = $('#resultCnt').val();
			var reqDeptId = $('#reqDeptId').val();
			var reqDeptName = $('#reqDeptName').val();
// 			var apprLv = $('#apprLv').val();

			if(resultCnt == 0) {
				iKEP.showDialog({
					title: "계약서 검토 결과 등록",
					url: "<c:url value='/approval/collaboration/legal/apprContractResultPopUp.do?contractMgntNo=' />"+id+'&contractTitle='+title+'&apprStsCd='+stsCd+'&apprReqDate='+reqDate+'&apprDate='+apprDate+'&reqDeptId='+reqDeptId+'&reqDeptName='+reqDeptName,
					modal: true,
					width: 800,
					height: 550,
					callback : saveCallbackResult
				});
			} else {
				iKEP.showDialog({
					title: "계약서 검토 결과 상세",
					url: "<c:url value='/approval/collaboration/legal/apprContractResultDetailPopUp.do?contractMgntNo=' />"+id+'&reqDeptId='+reqDeptId,
					modal: true,
					width: 800,
					height: 550,
					callback : saveCallbackResult
				});
			}
		});
		//검토결과 상세정보
		$(".reviewResultInfo").click(function(){
			var id = $('#contractMgntNo').val();
			var reqDeptId = $('#reqDeptId').val();
				iKEP.showDialog({
					title: "계약서 검토 결과 상세",
					url: "<c:url value='/approval/collaboration/legal/apprContractResultDetailPopUp.do?contractMgntNo=' />"+id+'&reqDeptId='+reqDeptId,
					modal: true,
					width: 800,
					height: 550
			});
		});
		
		//결과등록 콜백
		saveCallbackResult = function (type) {
			if(type == 'list') {
				location.href="<c:url value="/approval/collaboration/legal/apprContractList.do"/>";
			} else {
				location.reload();
			}
		};
		
		//달력
		$("input[name=reviewEndDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		writeSubmit = function(targetForm){
			
			var customerId = $("#customerId").val();
			
			if( customerId == ''){
				alert("고객명은 검색한 결과만 사용 가능합니다.");
				$("#customer").focus();
			}else{	
				$("body").ajaxLoadStart("button");
				targetForm.submit();
			}
		};
		
		
	});
	
	//UBI리포트
	ubiOpen = function() {
	 	var id = $("input[name='contractMgntNo']").val();
		var ubiProposalUrl = "<c:url value='/approval/collaboration/legal/ubiReport.do?id='/>"+ id + "&type=contractDetail";

		window.open( ubiProposalUrl, 'ubiReport', 'menubar=no, status=no, toolbar=no, location=no, resizable=yes');
	}
	
	// 파일팝업 ActiveX
	openPopActiveX = function() {
		var contractMgntNo = $("input[name='contractMgntNo']").val();
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/legal/editFilePopViewActiveX.do?contractMgntNo='/>" + contractMgntNo +"&fileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "액티브X용 파일업로드",
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
		var contractMgntNo = $("input[name='contractMgntNo']").val();
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/legal/editFilePopViewEcm.do?contractMgntNo='/>" + contractMgntNo +"&fileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "ECM용 파일업로드",
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
	
})(jQuery);


</script>



<h1 class="none">컨텐츠영역</h1>

<div class="container">
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>계약서 검토내역 수정</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--subTitle_1 Start-->

<!--//subTitle_1 End-->


<!-- <div><p></p></div> -->
<form id="contractDetailForm" name="contractDetailForm" method="post" action="<c:url value='/support/customer/customerCounselHistory/createCounselHistory.do'/>" enctype="multipart/form-data" >
	<input type="hidden" name="contractMgntNo" id="contractMgntNo" value="${resultDetail.contractMgntNo}" /> 
	<input type="hidden" name="processStatus" id="processStatus" value="${resultDetail.processStatus}" /> 
	<input type="hidden" name="apprStsCd" id="apprStsCd" value="${resultDetail.apprStsCd}" /> 
	<input type="hidden" name="apprReqDate" id="apprReqDate" value="<fmt:formatDate value="${resultDetail.apprReqDate}" pattern="yyyy.MM.dd"/>" /> 
	<input type="hidden" name="apprReqDate" id="apprDate" value="<fmt:formatDate value="${resultDetail.apprDate}" pattern="yyyy.MM.dd"/>" /> 
	<input type="hidden" name="apprLv" id="apprLv" value="${apprLv}" /> 
	<input type="hidden" name="resultCnt" id="resultCnt" value="${resultDetail.resultCnt}" />
	<input type="hidden" name="reqDeptId" id="reqDeptId" value="${resultDetail.reqDeptId}" />
	<input type="hidden" name="reqDeptName" id="reqDeptName" value="${resultDetail.reqDeptName}" />
	
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
	<div class="blockButton" style="margin-top: 35px;">
			<ul>
				<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span>레포팅출력</span></a></li>
		<!-- 기안부서 접수 -->
			<c:if test="${(resultDetail.processStatus eq '13' || resultDetail.processStatus eq '23') && resultDetail.apprStsCd eq '04' && reqDraftYn eq 'Y'}">
				<li>
					<a class="button goReqReceip" id="goReqReceip" href="#a"><span>접수</span></a>
				</li>
			</c:if>
		<!-- 법무결제 접수 -->
			<c:if test="${apprLv eq '4' &&resultDetail.processStatus eq '12' && userAuthMgntYn}">
				<li>
					<a class="button goRcvReceip" id="goRcvReceip" href="#a"><span>접수</span></a>
				</li>
			</c:if>
	 	<!-- 검토 승인자가 승인 안한상태 + 기안부서 기안자인 경우 -->
	 		<c:if test="${saveYn eq 'Y' && reqYn eq 'Y' && empNoYn eq 'Y' && reqDraftYn eq 'Y' && resultDetail.processStatus eq '10'}">
				<li>
					<a class="button goSave" id="goSave" href="#a"><span>수정</span></a>
				</li>
				<li>
					<a class="button goDelete" id="goDelete" href="#a"><span>삭제</span></a>
				</li>
			</c:if>
			<c:if test="${saveYn eq 'Y' && reqYn eq 'N' && empNoYn eq 'Y' && rcvDraftYn eq 'Y' && resultDetail.processStatus eq '20'}">
				<li>
					<a class="button goSaveRcv" id="goSaveRcv" href="#a"><span>수정</span></a>
				</li>
			</c:if>
			<c:if test="${resultDetail.apprYn eq 'Y' && agreementEmpNoYn eq 'Y'}">
				<li>
					<a class="button goArrp" id="goArrp" href="#a"><span>승인</span></a>
				</li>
			</c:if>
			<c:if test="${resultDetail.apprYn eq 'Y' && reqDraftYn eq 'N' && agreementEmpNoYn eq 'Y'}">
				<li>
					<a class="button goReject" id="goReject" href="#a"><span>반려</span></a>
				</li>
			</c:if>
			<c:if test="${resultDetail.apprStsCd eq '04'}">
				<li>
					<a class="button goReject" id="goReject" href="#a"><span>반려사유</span></a>
				</li>
			</c:if>
			<c:if test="${!ecmRoll && resultDetail.processStatus eq '10'}" >
				<li>
					<a class="button" href="javascript:;" onclick="openPopActiveX();" ><span>유첨자료</span></a>
				</li>	 
			</c:if>
			<c:if test="${ecmRoll && resultDetail.processStatus eq '10'}" >
				<li>
					<a class="button" href="javascript:;" onclick="openPopEcm();" ><span>유첨자료</span></a>
				</li>
			</c:if>
				<li>
					<a class="button goList" id="goList" href="<c:url value="/approval/collaboration/legal/apprContractList.do"/>"><span>목록</span></a>
				</li>
<%-- 			<c:if test="${resultDetail.processStatus eq '22' && empNoYn eq 'Y' && rcvDraftYn eq 'Y'}"> --%>
<!-- 				<li> -->
<!-- 					<a class="button reviewResult" id="reviewResult" href="#a"> <span>검토결과</span></a> -->
<!-- 				</li> -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${resultDetail.processStatus eq '22' && rcvDraftYn eq 'N'}"> --%>
<!-- 				<li> -->
<!-- 					<a class="button reviewResultInfo" id="reviewResultInfo" href="#a"> <span>검토결과</span></a> -->
<!-- 				</li> -->
<%-- 			</c:if> --%>
			</ul>
		</div>
		
	<div width="100%"  class="topTable">
		<div  style="float: left; width: 30%;">
			<table width="100%">
				<caption></caption>
				<colgroup>
					<col width="10%" />
					<col width="30%" />
					<col width="30%" />
					<col width="30%" />
				</colgroup>
				<tbody>
					<tr>
						<td rowspan="3">기<br />안<br />부<br />서</td>
						<td>기안</td>
						<td>검토</td>
						<td>승인</td>
					</tr>
					<tr>
						<td style="height:50px;">
	                    	<input name="reqDraftEmpNo" type="hidden" value="${resultDetail.reqDraftEmpNo}" />
							${resultDetail.reqDraftEmpName}
							<br /> <span style="color:gray;"><fmt:formatDate value="${resultDetail.reqDraftDate}" pattern="(MM/dd)"/></span>
	                    </td>
						<td>
			                <input name="reqReviewEmpNo" id="reqReviewEmpNo" class="searchUserId" type="hidden" value="${resultDetail.reqReviewEmpNo}" />
							<c:choose>
								<c:when test="${apprLv eq '1' && reqDraftYn eq 'Y'}">
			                    	<input name="reqReviewEmpName" id="reqReviewEmpName" class="searchUserName" type="text" value="${resultDetail.reqReviewEmpName}" size="7" style="border:1px solid #CCC;"  />
		                    		<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a" style="display:none">
										<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
									</a>
								</c:when>
								<c:otherwise>
			                    	<input name="reqReviewEmpName" id="reqReviewEmpName" class="searchUserName" type="hidden" value="${resultDetail.reqReviewEmpName}" size="7" style="border:1px solid #CCC;"  />
									${resultDetail.reqReviewEmpName}
								</c:otherwise>
							</c:choose>
							<br /> <span style="color:gray;"><fmt:formatDate value="${resultDetail.reqReviewDate}" pattern="(MM/dd)"/></span>
	                    </td>
						<td>
			                <input name="reqApprEmpNo" id="reqApprEmpNo"  class="searchUserId" type="hidden" value="${resultDetail.reqApprEmpNo}"  />
							<c:choose>
								<c:when test="${apprLv eq '1' && reqDraftYn eq 'Y'}">
			                    	<input name="reqApprEmpName" id="reqApprEmpName" class="searchUserName" type="text" value="${resultDetail.reqApprEmpName}" size="7" style="border:1px solid #CCC;"  />
			                    	<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a" style="display:none">
										<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
									</a>
								</c:when>
								<c:otherwise>
			                    	<input name="reqApprEmpName" id="reqApprEmpName" class="searchUserName" type="hidden" value="${resultDetail.reqApprEmpName}" size="7" style="border:1px solid #CCC;"  />
									${resultDetail.reqApprEmpName}
								</c:otherwise>
							</c:choose>
							<br /> <span style="color:gray;"><fmt:formatDate value="${resultDetail.reqApprDate}" pattern="(MM/dd)"/></span>
						</td>
					</tr>
					<tr>
						<td><span class="colorPoint">${resultDetail.reqDraftStsName}</span></td>
						<td><span class="colorPoint">${resultDetail.reqReviewStsName}</span></td>
						<td><span class="colorPoint">${resultDetail.reqApprStsName}</span></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div  style="float: left; width: 40%; visibility: hidden;" >
			<table width="100%" style="border=0px;">
				
			</table>
			
		</div>
		<div  style="float: left; width: 30%;" >
			<table width="100%">
				<caption></caption>
				<colgroup>
					<col width="10%" />
					<col width="30%" />
					<col width="30%" />
					<col width="30%" />
				</colgroup>
				<tbody>
					<tr>
						<td rowspan="3">주<br />관<br />부<br />서</td>
						<td>기안</td>
						<td>검토</td>
						<td>승인</td>
					</tr>
					<tr>
						<td style="height:50px;">
							<input name="rcvDraftEmpNo" type="hidden" value="${resultDetail.rcvDraftEmpNo}" />
							${resultDetail.rcvDraftEmpName}
							<br /> <span style="color:gray;"><fmt:formatDate value="${resultDetail.rcvDraftDate}" pattern="(MM/dd)"/></span>
						</td>
						<td>
							<input name="rcvReviewEmpNo" id="rcvReviewEmpNo" class="searchUserId" type="hidden" value="${resultDetail.rcvReviewEmpNo}" />
							<c:choose>
								<c:when test="${resultDetail.processStatus eq '20' && rcvDraftYn eq 'Y'}">
			                    	<input name="rcvReviewEmpName" id="rcvReviewEmpName" class="searchUserName" type="text" value="${resultDetail.rcvReviewEmpName}" size="7" style="border:1px solid #CCC;"  />
		                    		<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a" style="display:none">
										<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
									</a>
								</c:when>
								<c:otherwise>
			                    	<input name="rcvReviewEmpName" id="rcvReviewEmpName" class="searchUserName" type="hidden" value="${resultDetail.rcvReviewEmpName}" size="7" style="border:1px solid #CCC;"  />
									${resultDetail.rcvReviewEmpName}
								</c:otherwise>
							</c:choose>
							<br /> <span style="color:gray;"><fmt:formatDate value="${resultDetail.rcvReviewDate}" pattern="(MM/dd)"/></span>
						</td>
						<td>
			                <input name="rcvApprEmpNo" id="rcvApprEmpNo"  class="searchUserId" type="hidden" value="${resultDetail.rcvApprEmpNo}"  />
							<c:choose>
								<c:when test="${resultDetail.processStatus eq '20' && rcvDraftYn eq 'Y'}">
			                    	<input name="rcvApprEmpName" id="rcvApprEmpName" class="searchUserName" type="text" value="${resultDetail.rcvApprEmpName}" size="7" style="border:1px solid #CCC;"  />
			                    	<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a" style="display:none">
										<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
									</a>
								</c:when>
								<c:otherwise>
			                    	<input name="rcvApprEmpName" id="rcvApprEmpName" class="searchUserName" type="hidden" value="${resultDetail.rcvApprEmpName}" size="7" style="border:1px solid #CCC;"  />
									${resultDetail.rcvApprEmpName}
								</c:otherwise>
							</c:choose>
							<br /> <span style="color:gray;"><fmt:formatDate value="${resultDetail.rcvApprDate}" pattern="(MM/dd)"/></span>
						</td>
					</tr>
					<tr>
						<td><span class="colorPoint">${resultDetail.rcvDraftStsName}</span></td>
						<td><span class="colorPoint">${resultDetail.rcvReviewStsName}</span></td>
						<td><span class="colorPoint">${resultDetail.rcvApprStsName}</span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<!--blockDetail Start-->
	<div class="blockDetail" style="border-top:0px solid #3b90d0; top:30px;">
		<table summary="상담이력 상세조회" style="border:1px solid #e0e0e0; table-layout: auto;">
			<caption></caption>
			<colgroup>
				<col width="15%" />
				<col width="18%" />
				<col width="15%" />
				<col width="18%" />
				<col width="15%" />
				<col width="18%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row"><span class="colorPoint">*</span>제목</th>
					<td colspan="5">
						<input name="contractTitle" id="contractTitle" value="${resultDetail.contractTitle}" title="제목" class="inputbox w100" type="text" <c:if test="${apprLv ne '1' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> style="border:none;" readonly onfocus="this.blur()" </c:if> />
					</td>
				</tr>
				<tr>
					<th scope="row"></span>의뢰자</th>
					<td>
<%-- 						<span style ="font-weight: bold;">${resultDetail.reqEmpName}</span> --%>
						<input value="${resultDetail.reqEmpName}" title="의뢰자" class="inputbox w90" type="text" style="border:none;" readonly onfocus="this.blur()" />
						
					</td>
					<th scope="row">소속</th>
					<td style="text-align:left; ">
						<input value="${resultDetail.reqDeptName}" title="소속" class="inputbox w90" type="text" style="border:none;" readonly onfocus="this.blur()" />
					</td>
					<th scope="row">연락처</th>
					<td>
						<input value="${resultDetail.reqContactNo}" title="연락처" class="inputbox w90" type="text" style="border:none;" readonly onfocus="this.blur()" />
					</td>
				</tr>
				<tr>
					<th scope="row"></span>계약당사자</th>
					<td>
						<input name="contractParty" value="${resultDetail.contractParty}" title="계약당사자" class="inputbox w90" type="text" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> style="border:none;" readonly onfocus="this.blur()" </c:if> /> 
					</td>
					<th scope="row" colspan="2">계약서 검토 완료시한</th>
					<td colspan="2" style="text-align:left;">
						<c:choose>
							<c:when test="${saveYn eq 'Y' && reqYn eq 'Y' && reqDraftYn eq 'Y' || resultDetail.processStatus ne '10'}">
								<input name="reviewEndDate" id="reviewEndDate" title="계약서 검토 완료시한" class="inputbox w33 datepicker" type="text" value="<fmt:formatDate value="${resultDetail.reviewEndDate}" pattern="yyyy.MM.dd"/>" />
		           	    		<c:if test="${apprLv eq '1' && reqDraftYn eq 'Y'}"> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/> </c:if>
							</c:when>
							<c:otherwise>
								<input name="reviewEndDate2" id="reviewEndDate2" value="<fmt:formatDate value="${resultDetail.reviewEndDate}" pattern="yyyy.MM.dd"/>" title="계약서 검토 완료시한" class="inputbox w90" type="text" style="border:none;" readonly onfocus="this.blur()" />
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th scope="row">계약의 목적</th>
					<td colspan="5">
						<input name="contractPurpose" value="${resultDetail.contractPurpose}" title="계약의 목적" class="inputbox w100" type="text" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> style="border:none;" readonly onfocus="this.blur()" </c:if> />
					</td>
				</tr>		
			</tbody>
		</table>
		<table summary="상담이력 상세조회" style="border:1px solid #e0e0e0; border-top:0px; table-layout: auto;">
				<caption></caption>
				<colgroup>
					<col width="10%" />
					<col width="12%" />
					<col width="78%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row" rowspan="3">중점검토사항</th>
						<th scope="row">권리에 관한 사항</th>
						<td colspan="4">
							<textarea name="rightsTxt"  class="w100" title="권리에 관한 사항" cols="" rows="5" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> readonly onfocus="this.blur()" </c:if> >${resultDetail.rightsTxt}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">의무에 관한 사항</th>
						<td colspan="4">
							<textarea name="dutyTxt" class="w100" title="의무에 관한 사항" cols="" rows="5" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> readonly onfocus="this.blur()" </c:if> >${resultDetail.dutyTxt}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">주요 쟁점</th>
						<td colspan="4">
							<textarea name="keyIssue" class="w100" title="주요 쟁점" cols="" rows="5" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> readonly onfocus="this.blur()" </c:if> >${resultDetail.keyIssue}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">영문계약서의 경우 번역본 첨부</th>
						<td colspan="4">
							<textarea name="engContractEtcTxt" class="w100" title="영문계약서의 경우 번역본 첨부" cols="" rows="3" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> readonly onfocus="this.blur()" </c:if> >${resultDetail.engContractEtcTxt}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">기타 의뢰부서 요청사항</th>
						<td colspan="4">
							<textarea name="etcReqTxt" class="w100" title="기타 의뢰부서 요청사항" cols="" rows="5" <c:if test="${saveYn ne 'Y' || reqYn ne 'Y' || reqDraftYn ne 'Y' || resultDetail.processStatus ne '10'}"> readonly onfocus="this.blur()" </c:if> >${resultDetail.etcReqTxt}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">법무 의견</th>
						<td colspan="4">
							<textarea name="legalOpinion" class="w100" title="법무 의견" cols="" rows="5" <c:if test="${saveYn ne 'Y' || reqYn ne 'N' || resultDetail.processStatus ne '20'}"> readonly onfocus="this.blur()"  </c:if> >${resultDetail.legalOpinion}</textarea>
						</td>
					</tr>
					<tr>
						<th scope="row" colspan="2">유첨자료</th>
						<!-- 유첨자료 -->
						<td colspan="6">
							<div id="fileDownload"></div>
							<input type="hidden" name="fileItemId"  value="${resultDetail.fileItemId}"/>
						</td>
					</tr>
				</tbody>
			</table>
	</div>
	<!--//blockDetail End-->
	<!--blockButton Start-->
	<div class="blockButton" style="margin-top: 35px;">
		<ul>
			<li><a class="button listBoardItemButton" href="javascript:;" onclick="ubiOpen();" ><span>레포팅출력</span></a></li>
		<c:if test="${(resultDetail.processStatus eq '13' || resultDetail.processStatus eq '23') && resultDetail.apprStsCd eq '04' && reqDraftYn eq 'Y'}">
	<!-- 기안부서 접수 -->
			<li>
				<a class="button goReqReceip" id="goReqReceip" href="#a"><span>접수</span></a>
			</li>
		</c:if>
	<!-- 법무결제 접수 -->
		<c:if test="${apprLv eq '4' &&resultDetail.processStatus eq '12' && userAuthMgntYn}">
			<li>
				<a class="button goRcvReceip" id="goRcvReceip" href="#a"><span>접수</span></a>
			</li>
		</c:if>
 	<!-- 검토 승인자가 승인 안한상태 + 기안부서 기안자인 경우 -->
 		<c:if test="${saveYn eq 'Y' && reqYn eq 'Y' && empNoYn eq 'Y' && reqDraftYn eq 'Y' && resultDetail.processStatus eq '10'}">
			<li>
				<a class="button goSave" id="goSave" href="#a"><span>수정</span></a>
			</li>
			<li>
				<a class="button goDelete" id="goDelete" href="#a"><span>삭제</span></a>
			</li>
		</c:if>
		<c:if test="${saveYn eq 'Y' && reqYn eq 'N' && empNoYn eq 'Y' && rcvDraftYn eq 'Y' && resultDetail.processStatus eq '20'}">
			<li>
				<a class="button goSaveRcv" id="goSaveRcv" href="#a"><span>수정</span></a>
			</li>
		</c:if>
		<c:if test="${resultDetail.apprYn eq 'Y' && agreementEmpNoYn eq 'Y'}">
			<li>
				<a class="button goArrp" id="goArrp" href="#a"><span>승인</span></a>
			</li>
		</c:if>
		<c:if test="${resultDetail.apprYn eq 'Y' && reqDraftYn eq 'N' && agreementEmpNoYn eq 'Y'}">
			<li>
				<a class="button goReject" id="goReject" href="#a"><span>반려</span></a>
			</li>
		</c:if>
		<c:if test="${resultDetail.apprStsCd eq '04'}">
			<li>
				<a class="button goReject" id="goReject" href="#a"><span>반려사유</span></a>
			</li>
		</c:if>
		<c:if test="${!ecmRoll && resultDetail.processStatus eq '10'}" >
			<li>
				<a class="button" href="javascript:;" onclick="openPopActiveX();" ><span>유첨자료</span></a>
			</li>	 
		</c:if>
		<c:if test="${ecmRoll && resultDetail.processStatus eq '10'}" >
			<li>
				<a class="button" href="javascript:;" onclick="openPopEcm();" ><span>유첨자료</span></a>
			</li>
		</c:if>
			<li>
				<a class="button goList" id="goList" href="<c:url value="/approval/collaboration/legal/apprContractList.do"/>"><span>목록</span></a>
			</li>
		<%-- 			<c:if test="${resultDetail.processStatus eq '22' && empNoYn eq 'Y' && rcvDraftYn eq 'Y'}"> --%>
<!-- 				<li> -->
<!-- 					<a class="button reviewResult" id="reviewResult" href="#a"> <span>검토결과</span></a> -->
<!-- 				</li> -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${resultDetail.processStatus eq '22' && rcvDraftYn eq 'N'}"> --%>
<!-- 				<li> -->
<!-- 					<a class="button reviewResultInfo" id="reviewResultInfo" href="#a"> <span>검토결과</span></a> -->
<!-- 				</li> -->
<%-- 			</c:if> --%>
		</ul>
	</div>
	<!--//blockButton End-->
	</form>
	<div id="">
		<div style="margin-bottom: 7px;">
			<div class="floatLeft"><span class="ic_desc"></span></div> 
			<h3>계약서 검토 이력</h3> 
		</div>
		
		<div class="blockListTable" >
			<table summary="계약서" >
				<caption></caption>
				<thead>
					<tr >
						<th scope="col" width="15%" >일자</th>
						<th scope="col" width="15%">작성자</th>
						<th scope="col" width="55%">내용</th>
						<th scope="col" width="15%">첨부</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty resultList}">
							<tr>
								<td colspan="4" class="emptyRecord">
								조회된 이력이 없습니다.
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="resultList" items="${resultList}" varStatus="status">
								<tr style="height:17px;">
									<td><ikep4j:timezone pattern='yyyy.MM.dd' date='${resultList.writeDate}'/></td>
									<td>${resultList.writeEmpName}</td>
									<td class="textLeft" >
										<a href="#a"  onclick="goHistoryDetail('${resultList.historySeqno}', '${resultList.contractMgntNo}');">${resultList.historyTxt}</a>
									</td>
									<td>
										<c:if test="${resultList.cnt ne 0}">
											<img src="<c:url value="/base/images/icon/ic_attach.gif"/>"/>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="blockBlank_10px"></div>	
		</div>
		
		<div class="blockButton" style="margin-top: 5px; text-align:center;">
			<ul style="align:center;">
			<!-- 법무결제 작성중인경우 -->
				<li>
					<a class="button" href="javascript:;" onclick="openPopHistory();" ><span>이력등록</span></a>
				</li>
			<c:if test="${resultDetail.processStatus ne '99' && resultDetail.processStatus eq '22' && empNoYn eq 'Y'}">
				<c:if test="${rcvDraftYn eq 'Y'}">
					<li>
						<a class="button goHistoryEnd" id="goHistoryEnd" href="#a"><span>종결처리</span></a>
					</li>
				</c:if>
			</c:if>
			</ul>
		</div>
	</div>
</div>
	