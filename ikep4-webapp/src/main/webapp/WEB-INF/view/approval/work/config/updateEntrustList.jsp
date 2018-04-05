<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.entrust.form.header" />
<c:set var="preForm"  	value="ui.approval.work.entrust.form" />
<c:set var="preValidation" value="ui.approval.work.entrust.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script type="text/javascript">
	<!--
	
	(function($){
		
		viewApprList = function(entrustId,entrustType) {
			
			var url = iKEP.getContextRoot() + "/approval/work/apprlist/listApprEntrust.do?entrustId="+entrustId+"&entrustType="+entrustType;
		    var options = {
	    		windowTitle : "결재내역",
	    		documentTitle : "결재내역",
	    		width:850, height:600, modal:true,
	    		callback : function(result) {
	    			iKEP.debug(result);
	    		}
	    	};
	    	iKEP.popupOpen(url, options, "popupApprViewer");
		};
		
		
		$(document).ready(function(){
			
			$jq("#checkedAll").click(function() {

		 		if ($jq("#checkedAll").is(":checked")) {
		 			$jq('input[name=entrustIds]:not(checked)').attr("checked", true);
				}else{
			   		$jq('input[name=entrustIds]:checked').attr("checked", false);
			    }

		    });
			
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
										<th scope="col" width="5%" class="textCenter"><input type="checkbox" name="checkedAll" id="checkedAll" /></th>
										<th scope="col" width="20%" class="textCenter"><ikep4j:message pre='${preForm}' key='delegateperiod' /></th>
										<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preForm}' key='userName'/></th>
										<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preForm}' key='signUserName'/></th>
										<th scope="col" width="38%" class="textCenter"><ikep4j:message pre='${preForm}' key='reason' /></th>
										<th scope="col" width="7%" class="textCenter"><ikep4j:message pre='${preForm}' key='usage' /></th>
										<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preForm}' key='view' /></th>
									</tr>
								</thead>						
								<tbody>
									
									<c:choose>
									    <c:when test="${searchResult.emptyRecord}">
											<tr>
												<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preMessage}' key='list.empty' /></td> 
											</tr>				        
									    </c:when>
									    <c:otherwise>
											<c:forEach var="apprEntrust" items="${searchResult.entity}" varStatus="status">
											<tr>
												<td class="textCenter">
													<c:if test="${apprEntrust.usage == '1'}">
														<input type="checkbox" name="entrustIds" value="${apprEntrust.entrustId}"/>
													</c:if>
												</td>
												<td class="textCenter">
													<ikep4j:timezone date="${apprEntrust.startDate}" pattern="yyyy.MM.dd"/> ~
													<ikep4j:timezone date="${apprEntrust.endDate}" pattern="yyyy.MM.dd"/>
												</td>
												<td class="textCenter">
													<span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${apprEntrust.userId}', 'bottom')">${apprEntrust.userName}</a></span>
												</td>	
												<td class="textCenter">
													<span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${apprEntrust.signUserId}', 'bottom')">${apprEntrust.signUserName}</a></span>
												</td>	
												<td class="textLeft" title="${apprEntrust.reason}">
													<div class="ellipsis">${apprEntrust.reason}</div>
												</td>
												<td class="textCenter">
													<c:if test="${apprEntrust.usage == '1'}">
														<ikep4j:message pre='${preForm}' key='usageY' />
													</c:if>
													<c:if test="${apprEntrust.usage == '0'}">
														<ikep4j:message pre='${preForm}' key='usageN' />
													</c:if>
												</td>
												<td class="textCenter">
													<a class="button_ic" href="javascript:viewApprList('${apprEntrust.entrustId}','E')">
														<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="View" />View</span>
													</a>
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