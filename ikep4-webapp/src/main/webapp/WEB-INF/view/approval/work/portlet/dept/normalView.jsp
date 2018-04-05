<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMsg"   value="ui.approval.common.portlet" />
<c:set var="preList"   value="ui.approval.common.portlet.dept" /> 
<script type="text/javascript">
//<![CDATA[
(function($){
	
	$("#gsDivTab_s1").tabs();
	//문서 상세 정보
	getApprDetail_${portletConfigId} = function(apprId,entrustUserId,listType) {
		if(entrustUserId == undefined || entrustUserId == '') {
			location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType;
		}
		else {
			location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType+"&entrustUserId="+entrustUserId;
		}
	};
	
	$(document).ready(function() {   
		
	}); 

})(jQuery);
//]]>
</script> 	
<c:set var="preDetail"  value="ui.lightpack.board.portlet.recentBoard" />

<div id="${portletConfigId}" class="po_schedule_w">
	<div id="gsDivTab_s1" class="iKEP_tab_s" style="margin-bottom:0;">
		<ul>
			<li><a href="#gsTab-1"><ikep4j:message pre="${preList}" key="tab1" /></a></li>
			<li><a href="#gsTab-2"><ikep4j:message pre="${preList}" key="tab2" /></a></li>
		</ul>	
		<div id="gsTab-1" style="padding-bottom:0;">
			<div id="portletApprovalDept" class="tableList_1"> 
				<table summary="<ikep4j:message pre="${preList}" key="summary" />">
					<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="*"/>
						<col width="15%"/>
					</colgroup>
					<tbody>
						<c:choose>
						    <c:when test="${searchResult.emptyRecord}">
							    <tr>
							    	<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preMsg}' key='emptyRecord' /></td> 
							    </tr>				        
						    </c:when>
						    <c:otherwise> 
								<c:forEach var="aplist" items="${searchResult.entity}" varStatus="status"> 
									<tr>
										<td class="textCenter">
											<div class="ellipsis">
											<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preList}' key='apprDocType2' /></c:if>
											<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preList}' key='apprDocType1' /></c:if>
											</div>
										</td> 
										<td>
											<div class="ellipsis">
												<c:if test="${aplist.approvalId ne user.userId}">
												<a href="#a" onclick="getApprDetail_${portletConfigId}('${aplist.apprId}','${aplist.approvalId}','listApprDeptRec');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
												</c:if>
												<c:if test="${aplist.approvalId eq user.userId}">
												<a href="#a" onclick="getApprDetail_${portletConfigId}('${aplist.apprId}','','listApprDeptRec');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
												</c:if>
											</div>
										</td>
										<td class="textCenter">
											<div class="ellipsis">
											<span class="date"><ikep4j:timezone pattern="MM.dd" date="${aplist.apprReqDate}"/></span>
											</div>
										</td>
									</tr>	
								</c:forEach>				    
						    </c:otherwise>	 
						</c:choose> 																																		
					</tbody>
				</table>							
				<div class="l_b_corner"></div><div class="r_b_corner"></div>
			</div>
		</div>
		<div id="gsTab-2" style="padding-bottom:0;">
			<div id="portletApprovalUser" class="tableList_1"> 
				<table summary="<ikep4j:message pre="${preList}" key="summary" />">
					<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="*"/>
						<col width="15%"/>
					</colgroup>
					<tbody>
						<c:choose>
						    <c:when test="${userSearchResult.emptyRecord}">
							    <tr>
							    	<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preMsg}' key='emptyRecord' /></td> 
							    </tr>				        
						    </c:when>
						    <c:otherwise> 
								<c:forEach var="aplist" items="${userSearchResult.entity}" varStatus="status"> 
									<tr>
										<td class="textCenter">
											<div class="ellipsis">
											<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preList}' key='apprDocType2' /></c:if>
											<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preList}' key='apprDocType1' /></c:if>
											</div>
										</td> 
										<td>
											<div class="ellipsis">
												<c:if test="${aplist.approvalId ne user.userId}">
												<a href="#a" onclick="getApprDetail_${portletConfigId}('${aplist.apprId}','${aplist.approvalId}','listApprUserRec');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
												</c:if>
												<c:if test="${aplist.approvalId eq user.userId}">
												<a href="#a" onclick="getApprDetail_${portletConfigId}('${aplist.apprId}','','listApprUserRec');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
												</c:if>
											</div>
										</td>
										<td class="textCenter">
											<div class="ellipsis">
											<span class="date"><ikep4j:timezone pattern="MM.dd" date="${aplist.apprEndDate}"/></span>
											</div>
										</td>
									</tr>	
								</c:forEach>				    
						    </c:otherwise>	 
						</c:choose> 																																		
					</tbody>
				</table>							
				<div class="l_b_corner"></div><div class="r_b_corner"></div>
			</div>
		</div>
	</div>
</div>