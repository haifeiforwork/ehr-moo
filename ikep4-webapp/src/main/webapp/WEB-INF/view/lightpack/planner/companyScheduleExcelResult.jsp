<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"    value="ui.portal.excelresult.list" />
<c:set var="preDetail"  value="ui.portal.excelresult.detail" />
<c:set var="preButton"  value="ui.portal.excelresult.button" /> 
<c:set var="preButton2"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preMessage" value="ui.portal.excelresult.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="userListPrefix" value="message.portal.admin.member.user.list"/>

<c:set var="preLabel"    value="ui.lightpack.planner.common.label" />

 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">

(function($) {
	
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		var $hh = $jq("#popup").height()+100;
		
		if($hh > 768) $hh = 768;
		
		window.resizeTo(800,$hh);
		
		opener.location.reload();
		
		$jq("#cancelBtn").click(function() {
			
			iKEP.closePop();
		});
		
	});
	
})(jQuery);  


	
</script>

		<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1><ikep4j:message pre='${preButton2}' key='companyExcel' /></h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
											
					
						
							
							<table summary="<ikep4j:message pre='${preDetail}' key='title.user' />">
							<caption></caption>	
					
							<tbody>
							
								<tr>
									<th scope="row" width="20%"><ikep4j:message pre='${preDetail}' key='totalCount' /></th>
									<td>
										${totalCount}
									</td>
								</tr>								
								<tr>
									<th scope="row"><ikep4j:message pre='${preDetail}' key='successCount' /></th>
									<td>
										${successCount}
									</td>
								</tr>	
								<tr>
									<th scope="row"><ikep4j:message pre='${preDetail}' key='failCount' /></th>
									<td>
										${failCount}
									</td>
								</tr>	
											
							</tbody>	
							
							</table>
							
					</div>
					<!--//blockDetail End-->
					
					
					<!--blockListTable Start-->
					<div class="blockListTable">
							
							<table summary="<ikep4j:message pre='${preDetail}' key='title' />">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" width="5%">
											<ikep4j:message pre='${preList}' key='no' />
									</th>
									<th scope="col" width="*">
											<ikep4j:message pre="${preLabel}" key="title" />
									</th>
									<th scope="col" width="30%">
											<ikep4j:message pre="${preLabel}" key="period" />
									</th>
									<th scope="col" width="30%">
											<ikep4j:message pre='${preList}' key='errMsg' />
									</th>
								</tr>
							</thead>
							
							<tbody>
								<c:choose>
								    <c:when test="${empty scheduleExcelList && totalCount > 0}">
										<tr>
											<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
										</tr>				        
								    </c:when>
								     <c:when test="${empty scheduleExcelList && totalCount == 0}">
										<tr>
											<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.checkTemplate' /></td> 
										</tr>				        
								    </c:when>
								    <c:otherwise>
										<c:forEach var="schedule" items="${scheduleExcelList}" varStatus="status">
											<c:if test="${schedule.successYn=='N'}">
												<tr>
													<td>${status.count}</td>			
													<td class="textLeft">${schedule.title}</td>
													<td class="textLeft">
													<fmt:parseDate value="${schedule.startDate}" var="startDateDt" pattern="yyyymmdd"/>
													<fmt:formatDate value="${startDateDt}"  pattern="yyyy-mm-dd"/> ${schedule.startTime} ~ 
													<fmt:parseDate value="${schedule.endDate}" var="endDateDt" pattern="yyyymmdd"/>
													<fmt:formatDate value="${endDateDt}" pattern="yyyy-mm-dd"/> ${schedule.endTime} </td>
													<td class="textLeft">${schedule.errMsg}</td>
												</tr>
											</c:if>
										</c:forEach>				      
								    </c:otherwise> 
								</c:choose>  
									
																																																														
							</tbody>
							
						</table>	
						
							
					</div>
					<!--//blockListTable End-->			
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="cancelBtn" href="#"><span><ikep4j:message pre='${preButton}' key='close' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
			       
				
				</div>
				<!--//popup_contents End-->
			 
				<!--popup_footer Start-->
				<div id="popup_footer"></div>
				<!--//popup_footer End-->
				
					
		
		</div>
		<!--//popup End-->
		
		
		
		
	