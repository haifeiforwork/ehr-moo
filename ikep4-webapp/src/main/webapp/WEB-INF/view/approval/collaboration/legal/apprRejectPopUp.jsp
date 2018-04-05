<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
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
				var rejectReason = $('#rejectReason').val().replace(/^\s+/, "");
				if(rejectReason == '' || rejectReason == null || rejectReason.length == 0) {
					alert("반려사유를 입력해주세요");
					$('#rejectReason').focus();
					return;
				}
				
				if (confirm("반려하시겠습니까?")) {
					$jq.ajax({
						url : iKEP.getContextRoot() + "/approval/collaboration/legal/ajaxContractRejectSave.do",
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
	<c:if test="${apprStsCd eq '04'}"><h2>반려 사유</h2></c:if>
		
	<c:if test="${apprStsCd ne '04'}"><h2>반려 사유 등록</h2> </c:if>
	</div>
	<!--//pageTitle End-->
	<form id="rejectForm" name="rejectForm" method="post" action="">
		<input type="hidden" name="contractMgntNo" value="${contractMgntNo}" />
		<input type="hidden" name="apprLv" value="${apprLv}" />
		<input type="hidden" name="apprStsCd" value="${apprStsCd}" />
		<div class="blockDetail">
			<table width="100%">
				<caption></caption>
				<colgroup>
					<col width="20%" />
					<col width="80%" />
				</colgroup>
				<tbody>
					<tr>
						<th>반려사유</th>
						<td>
<!-- 							<input name="rejectReason" id="rejectReason" value="" title="반려사유" class="inputbox w100" type="text" /> -->
						<textarea name="rejectReason" id="rejectReason"  class="w100" title="반려사유" cols="" rows="3"  <c:if test="${apprStsCd eq '04'}"> readonly onfocus="this.blur()"</c:if> >${rejectReason}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
			<c:if test="${apprStsCd eq '04'}">
				<li><a id="cancelButton"	class="button" href="#a"><span>확인</span></a></li>
			</c:if>
			<c:if test="${apprStsCd ne '04'}">
				<li><a id="saveButton"		class="button" href="#a"><span>반려</span></a></li>
				<li><a id="cancelButton"	class="button" href="#a"><span>취소</span></a></li>
			</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
		
	</form>	
<!--//blockListTable End-->	
