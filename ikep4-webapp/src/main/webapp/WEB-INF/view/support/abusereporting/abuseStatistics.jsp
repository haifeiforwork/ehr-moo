<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  	value="ui.support.abusereporting.arAbuseHistory.detail" />
<c:set var="preAbuseSearch"	value="ui.support.abusereporting.arAbuseSearchCondition.detail" />
<c:set var="preLabel" 		value="ui.support.abusereporting.label" />
<c:set var="preButton"  	value="ui.support.abusereporting.button" />
<c:set var="preMenu"  		value="ui.support.abusereporting.menu" />
<c:set var="preSearch"  	value="ui.communication.common.searchCondition" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />

 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		// left menu setting
		 //iKEP.setLeftMenu();

		$("#startDate").datepicker()
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
    	
    	$("#endDate").datepicker()
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
    	
	});

	getList = function() {
		$("#searchForm").submit(); 
		
/*		$jq.ajax({     
			url : '<c:url value="/support/abusereporting/getAbuseReportList.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {  
				$jq("#listDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
*/		
	};
	
})(jQuery);
-->
</script>

			<form id="searchForm" method="post" action="<c:url value='/support/abusereporting/getAbuseStatistics.do' />" > 
			 
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preMenu}' key='abuseStatistics'  /></h2>
				</div>
				<!--//pageTitle End-->
				
				<!--blockSearch Start-->
				<div class="blockSearch">
					<div class="corner_RoundBox03">
						<table summary="Abuse 리포트 테이블">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre='${preLabel}' key='module' /></th>
									<td colspan="3">		
										<spring:bind path="searchCondition.moduleCode">  
											<select name="${status.expression}" title='<ikep4j:message pre='${preAbuseSearch}' key='${status.expression}' />'>
												<option value="ALL"><ikep4j:message pre='${preLabel}' key='allModule' /></option>
												<c:forEach var="arModule" items="${arModulelist}">
													<option value="${arModule.moduleCode}" <c:if test="${arModule.moduleCode eq status.value}">selected="selected"</c:if>>${arModule.moduleName}</option>
												</c:forEach> 											
											</select>
										</spring:bind>
									</td>									
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${preLabel}' key='searchDate' /></th>
									<td colspan="3">	
										<spring:bind path="searchCondition.startDate">
											<input 
											name="${status.expression}" 
											id="${status.expression}" 
											type="text" 
											class="inputbox datepicker" 
											value="${status.value}" 
											size="10" 
											title="<ikep4j:message pre='${preAbuseSearch}' key='${status.expression}' />"
											/> 
											<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="datepicker" style="cursor:pointer;" />
										</spring:bind> ~	
										<spring:bind path="searchCondition.endDate">
											<input 
											name="${status.expression}" 
											id="${status.expression}" 
											type="text" 
											class="inputbox datepicker" 
											value="${status.value}" 
											size="10" 
											title="<ikep4j:message pre='${preAbuseSearch}' key='${status.expression}' />"
											/> 
											<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="datepicker" style="cursor:pointer;" />
										</spring:bind>		
									</td>								
								</tr>													
							</tbody>
						</table>
						<div class="searchBtn"><a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" onclick="javascript:getList()" ><span><ikep4j:message pre='${preButton}' key='search' /></span></a></div>
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
					<div class="clear"></div>
				</div>	
				<!--//blockSearch End-->
					
	
				<!--blockListTable Start-->
				<div class="MyContentsTable">
					<table summary="My Document">
						<caption></caption>
						<tbody>
							<c:choose>
							    <c:when test="${empty arAbuseQueryReturnList}">
									<tr>
										<td class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
							    	<c:set var="maxValue" /> 
									<c:forEach var="arAbuseQueryReturn" items="${arAbuseQueryReturnList}" varStatus="status">
										<c:if test="${1 == status.count}"> <c:set var="maxValue" value="${arAbuseQueryReturn.cnt }" /> </c:if>
										<tr class="msg_unread">
											<td scope="col" width="5%" class="tdFirst textRight_p20"><c:out value="${status.count}"/></td>
											<td scope="col" width="80%" class="textLeft_p20 tdLast">
												<script type="text/javascript"> 
												<!--
												(function($) {
													$(document).ready(function() {
														$("#progressbar${status.count}").progressbar({
															value: ${(arAbuseQueryReturn.cnt / maxValue) * 100 }
														});	
													});	
												})(jQuery);
												-->
												</script>
												<div class="survey_graph">
													<div id="progressbar${status.count}"></div>		
												</div>
											</td>
											<td scope="col" width="15%" class="textLeft_p20 tdLast">${arAbuseQueryReturn.keyword }(${arAbuseQueryReturn.cnt })</td>
										</tr>		
									</c:forEach>					      
							    </c:otherwise> 
							</c:choose>  																																																																														
						</tbody>
					</table>
 
				</div>
				<!--//blockListTable End-->
				
			</form>
							