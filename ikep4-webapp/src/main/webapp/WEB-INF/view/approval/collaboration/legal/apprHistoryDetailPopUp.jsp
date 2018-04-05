<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<%-- Tab Lib End --%> 
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preTitle"    	value="ui.approval.userauthmgnt.list.title" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	var dialogWindow = null;
	var fnCaller;
	var validator;
	(function($){
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		$(document).ready(function(){
			<c:if test="${not empty fileDataListJson}">
			var uploaderOptions = {
			   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
			   isUpdate : false 
			}; 
			        
		    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
		    </c:if>
		    
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});

// 			저장
// 			$("#saveButton").click(function() {
				
// 				$("#editForm").submit();
// 				return false; 
// 			});
			// 저장
			$("#saveButton").click(function() {
				var historyTxt = $('#historyTxt').val().replace(/^\s+/, "");
				if(historyTxt == '' || historyTxt == null || historyTxt.length == 0) {
					alert("내용을 입력해주세요");
					$('#historyTxt').focus();
					return;
				}
				if (confirm("등록하시겠습니까?")) {
					$jq.ajax({
						url : iKEP.getContextRoot() + "/approval/collaboration/legal/ajaxContractHistorySave.do",
						type : "post",
						data : $jq("#rejectForm").serialize(),
						loadingElement : "#saveButton",
						success : function(data) {
							if(data.success == "Y") {
								dialogWindow.callback(data.apprLegal);
								dialogWindow.close();
							} else {
								alert( "반려에 실패하였습니다.");
								dialogWindow.close();
							}
							
						}
					});
				}
			});
			
		});
	})(jQuery);
</script>
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2>검토 이력 상세정보</h2> 
	</div>
	<!--//pageTitle End-->
	<form id="rejectForm" name="rejectForm" method="post" action="">
		<input type="hidden" name="contractMgntNo" value="${contractMgntNo}" />
		<input type="hidden" name="apprLv" value="${apprLv}" />
		<div class="blockDetail" style="border-top:0px solid #3b90d0;">
		<table summary="상담이력 상세조회" style="border:1px solid #e0e0e0; table-layout: auto;">
			<caption></caption>
			<colgroup>
				<col width="10%" />
				<col width="80%" />
				<col width="10%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">내용</th>
					<td>
						${resultDetail.historyTxt}
					</td>
				</tr>
				<tr>
					<th scope="row">첨부파일</th>
					<!-- 유첨자료 -->
					<td colspan="2">
						<div id="fileDownload"></div>
						<input type="hidden" name="fileItemId"  value="${resultDetail.fileItemId}"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--blockButton Start-->
		<div class="blockButton"  style="text-align:center;"> 
			<ul>
				<li><a id="cancelButton" class="button" href="#a" ><span>닫기</span></a></li>
			</ul>
		</div>
	<!--blockButton End-->
	</form>	
<!--//blockListTable End-->	
