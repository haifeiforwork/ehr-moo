<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.apprsign.form.header" />
<c:set var="preForm"  	value="ui.approval.work.apprsign.form" />
<c:set var="preValidation" value="ui.approval.work.apprsign.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script type="text/javascript">
	<!--
	
	(function($){
		
		$(document).ready(function(){
				
			
		});
		
	})(jQuery);
	//-->
</script>

				<div class="blockDetail">
				
						<form id="historyForm" name="historyForm" method="post" action="" title="">  
		
							<table summary="<ikep4j:message pre='${preHeader}' key='title' />" width="100%">
								<caption></caption>
								<thead>
									<tr>
										<th scope="col" width="70%" class="textCenter"><ikep4j:message pre='${preForm}' key='signImage' /></th>
										<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preForm}' key='registDate' /></th>
										<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preForm}' key='default' /></th>
									</tr>
								</thead>						
								<tbody>
									
									<c:choose>
									    <c:when test="${searchResult.emptyRecord}">
											<tr>
												<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
											</tr>				        
									    </c:when>
									    <c:otherwise>
											<c:forEach var="sign" items="${searchResult.entity}" varStatus="status">
											<tr>
												<td class="textCenter">
													<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${sign.signFileId}' />" width="60" height="60" alt="<ikep4j:message pre='${preForm}' key='signImage' />" />
												</td>	
												<td class="textCenter">
													<ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${sign.registDate}"/>
												</td>
												<td class="textCenter">
													<c:if test="${sign.isDefault == '1'}">
														<ikep4j:message pre='${preForm}' key='defaultY' />
													</c:if>
													<c:if test="${sign.isDefault == '0'}">
														<ikep4j:message pre='${preForm}' key='defaultN' />
													</c:if>
												</td>
											</tr>
											</c:forEach>				      
									    </c:otherwise> 
									</c:choose>  
								
								</tbody>
							</table>
							
							<!--Page Numbur Start--> 
							<spring:bind path="searchCondition.pageIndex">
								<ikep4j:pagination searchFormId="historyForm" ajaxEventFunctionName="getHistoryList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
								<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
							</spring:bind> 
							<!--//Page Numbur End-->
						
						</form>	
				
				</div>