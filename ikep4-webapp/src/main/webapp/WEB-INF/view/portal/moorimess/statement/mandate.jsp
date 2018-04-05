<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preMandator"    value="ui.lightpack.planner.mandator" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>
 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 

				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>전표 결재 위임</h2>
					<div class="clear"></div>
				</div>
				
				<!--blockListTable Start-->
				<div class="blockListTable">
				<form id="mgmtForm" action="delete.do">
				<input id="mandatorId" name="mandatorId" type="hidden" value=""/>
				<input id="ireqgubun" name="ireqgubun" type="hidden" value=""/>
				<input id="uname" name="uname" type="hidden" value="${statementDetail.uname}" />
				<input id="rfcUserId" name="rfcUserId" type="hidden" value="${statementDetail.uname}" />
				
					<table>
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="12%">이름</th>
								<th scope="col" width="15%">ID</th>
								<th scope="col" width="15%">부서</th>
								<th scope="col" width="46%">전표결제 권한 위임 부서</th>
								<th scope="col" width="12%">위임일자</th>
							</tr>
						</thead>
						<tbody>
						<c:choose>
			        		<c:when test="${empty statementDetail}">
								<tr>
									<td colspan="5" class="emptyRecord">등록된 위임자가 없습니다.</td>
								</tr>
							</c:when>
							<c:when test="${statementDetail.isRight==false}">
								<tr>
									<td colspan="5" class="emptyRecord">전표 결제 권한이 없습니다.</td>
								</tr>
							</c:when>
			        		<c:otherwise>
		        				<tr>
					        		<td>${statementDetail.rfcUserName}</td>
					        		<td>${statementDetail.uname}</td>
					        		<td>${statementDetail.mandatorDept}</td>
					        		<td style="background-color:#f3f3f3;">${statementDetail.kostlt}</td>
					        		<td style="background-color:#f3f3f3;">${statementDetail.uname2date}</td>
					        	</tr>
			        		</c:otherwise>
			        	</c:choose>
						</tbody>
					</table>	
				</form>				
				</div>
				<!--//blockListTable End-->	
							
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${empty statementDetail}">
 							<li id="btn_insert"><a class="button" href="#a"><span>등록</span></a></li>
 						</c:if>
 						<c:if test="${not empty statementDetail&&statementDetail.isRight=='true'}">
							<li id="btn_edit"><a class="button" href="#a"><span>수정</span></a></li>
							<li id="btn_delete"><a class="button" href="#a"><span>해제</span></a></li>
						</c:if>
					</ul>
				</div>
				<!--//blockButton End-->	
													
			<!--//mainContents End-->
<script type="text/javascript">


(function($) {
	$jq(document).ready(function(){
		
		var createUrl = iKEP.getContextRoot() + "/portal/moorimess/statementCommon/mandateCreate.do";
		
		$jq("#btn_edit, #btn_insert").click(function() {
			document.location.href = "<c:url value='/portal/moorimess/statementCommon/mandateEditForm.do'/>";
		});
		
		$("#btn_delete").click(function() {
			if(confirm('위임자의 권한을 해제 하시겠습니까?')) {
				$("#ireqgubun").val("2");
				//alert($jq("#uname").val());
				$("#mgmtForm").attr("method", "post");
				$("#mgmtForm").attr("action", createUrl);
				$("#ireqgubun").val("2");
				$("#mgmtForm").submit();
			}
		});
		
	
	});
	
})(jQuery);

</script>
			