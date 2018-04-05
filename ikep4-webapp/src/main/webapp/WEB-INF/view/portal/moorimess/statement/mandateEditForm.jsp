<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preMessage"  value="ui.lightpack.planner.common.message" /> 
<c:set var="preMandator"    value="ui.lightpack.planner.mandator" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
 <%--메시지 관련 Prefix 선언 End--%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
<script type="text/javascript" language="javascript">
<!--
(function($) {

	
	$jq(document).ready(function() { 
		var $searchFld  = $jq("#search", "#mgmtForm");
		var $trusteeId	= $jq("#trusteeId");
		var createUrl = iKEP.getContextRoot() + "/portal/moorimess/statementCommon/mandateCreate.do";
		var deleteUrl = iKEP.getContextRoot() + "/portal/moorimess/statementCommon/mandateDelete.do";
		$searchFld.keypress(function(event) {
			var that = this;
			iKEP.searchUser(event, "N", function( data ) {
				if(data && data.length > 0) {
					var user = data[0];
					$jq("#rfcUserName").html(user.userName);
					$jq("#rfcUserSapId").html(user.sapId);
					$jq("#rfcUserId").val(user.id);
					$jq("#uname").val(user.sapId);
					$jq("#rfcTeam").html(user.teamName);
				} else {
					alert(iKEPLang.planner.messageText.noResult);
				}
			});		
		});
	
		$jq("#btn_searchUser", "#mgmtForm").click(function(event) {
			$searchFld.trigger("keypress");	
		});
		
		$("#btn_save").click(function() {
			if($("#rfcUserId").val().length > 0) {
				if($jq("#uname").val()=='MOORIM12'){
					alert('공용아이디는 전표 결제 위임을 할 수 없습니다.');
					return;
				}
				$("#ireqgubun").val("1");
				$("#mgmtForm").attr("method", "post");
				$("#mgmtForm").attr("action", createUrl);
				$("#mgmtForm").submit();			
			} else {
				alert(iKEPLang.planner.messageText.checkTrustee);
			}
		});
	});
})(jQuery);
//-->
</script>

				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>전표 결재 위임</h2>
					<div class="clear"></div>
				</div>
				
				<!--blockDetail Start-->
				<div class="blockDetail">
				<form id="mgmtForm" action="">
				<input id="rfcUserId" name="rfcUserId" type="hidden" value="" />
				<input id="uname" name="uname" type="hidden" value="" />
				<input id="ireqgubun" name="ireqgubun" type="hidden" value="" />
					<table>
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="20%" style="text-align:center;">위임자</th>
								<td width="*">
									<div>
										<input id="search" name="userName" title="<ikep4j:message pre="${preMandator}" key="searchTrustee" />" type="text" class="inputbox"/>
										<a id="btn_searchUser" class="button_s button_ic" href="#a">
										<span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" />Search</span></a>
									</div>
								</td>
							</tr>	
						</tbody>
					</table>
					<table style="text-align:center;">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="12%" style="text-align:center;">이름</th>
								<th scope="col" width="15%" style="text-align:center;">ID</th>
								<th scope="col" width="15%" style="text-align:center;">부서</th>
								<th scope="col" width="46%" style="text-align:center;">전표결제 권한 위임 부서</th>
								<th scope="col" width="12%" style="text-align:center;">위임일자</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td id="rfcUserName">${statementDetail.rfcUserName}</td>
								<td id="rfcUserSapId">${statementDetail.uname}</td>
								<td id="rfcTeam">${statementDetail.mandatorDept}</td>
								<td id="rfcCostCenter" style="background-color:#f3f3f3;">${statementDetail.kostlt}</td>
								<td id="rfcDate" style="background-color:#f3f3f3;">${statementDetail.uname2date}</td>
							</tr>																																	
						</tbody>
					</table>
					<div class="none"><input id="dummy" name="dummy" type="text" style="height:0px" /></div>
				</form>
				</div>
				<!--//blockDetail End-->
							
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li id="btn_save"><a class="button" href="#a"><span>저장</span></a></li>
						<c:if test="${!empty mandator.trusteeName}">
							<li id="btn_delete"><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="delete" /></span></a></li>	
						</c:if>					
					</ul>
				</div>
				<!--//blockButton End-->	
													
			<!--//mainContents End-->
</script>