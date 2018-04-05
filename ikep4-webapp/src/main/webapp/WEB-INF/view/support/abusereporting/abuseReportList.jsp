<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  	value="ui.support.abusereporting.arAbuseHistory.detail" />
<c:set var="preAbuseSearch"	value="ui.support.abusereporting.arAbuseSearchCondition.detail" />
<c:set var="preLabel" 		value="ui.support.abusereporting.label" />
<c:set var="preButton"  	value="ui.support.abusereporting.button" />
<c:set var="preMenu"  		value="ui.support.abusereporting.menu" />
<c:set var="preSearch"  	value="ui.ikep4.common.searchCondition" /> 

<c:set var="sessionPortal" 	value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" 	value="${sessionScope['ikep.user']}" />

 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		// left menu setting
		 //iKEP.setLeftMenu();

		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#keyword').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		}); 

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

			<form id="searchForm" method="post" action="<c:url value='/support/abusereporting/getAbuseReportList.do' />" > 
			 
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preMenu}' key='abuseReport'  /></h2>
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
								<tr>
									<th scope="row"><ikep4j:message pre='${preLabel}' key='keyword' /></th>
									<td colspan="3">			
										<spring:bind path="searchCondition.keyword">  					
											<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preAbuseSearch}' key='${status.expression}' />'  size="40" />
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
				
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="listInfo">
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="code" items="${boardCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span>${searchResult.recordCount}</span></div>
					</div>	
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->	
	
				<!--blockListTable Start-->
				<div class="blockListTable summaryView">
					<table summary="Reporting 리스트 ">
						<caption></caption>						
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="arAbuseHistory" items="${searchResult.entity}">
										<tr>
											<td>
												<div class="summaryViewCon">${arAbuseHistory.contents}</div>
												<div class="summaryViewInfo">
													<span class="summaryViewInfo_name">
													<a href="#a">
														<c:choose>
															<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
																${arAbuseHistory.registerName} ${arAbuseHistory.jobTitleName}
															</c:when>
															<c:otherwise>
																${arAbuseHistory.registerEnglishName} ${arAbuseHistory.jobTitleEnglishName}
															</c:otherwise>
														</c:choose>
													</a>
													</span>
													<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />	
													<span><ikep4j:timezone date="${arAbuseHistory.registDate}" pattern="yyyy.MM.dd HH:mm"/></span>
													<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />							
													<span>${arAbuseHistory.moduleName}</span>
													<img src="<c:url value='/base/images/common/bar_info.gif' />" alt="" />						
													<span>
														<c:if test="${'1' == arAbuseHistory.isExternal}"> <ikep4j:message pre='${preLabel}' key='outter' /> </c:if>
														<c:if test="${'1' != arAbuseHistory.isExternal}"> <ikep4j:message pre='${preLabel}' key='inner' /> </c:if>
													</span>
												</div>
											</td>
										</tr>
									</c:forEach>					      
							    </c:otherwise> 
							</c:choose>  																																														
						</tbody>
					</table>
					
					<!--Page Numbur Start--> 
					<spring:bind path="searchCondition.pageIndex">
						<ikep4j:pagination searchFormId="searchForm" ajaxEventFunctionName="getList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<!--//Page Numbur End-->
					
			
				</div>
				<!--//blockListTable End-->

			</form>
							