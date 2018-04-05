<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMsg"   value="ui.approval.common.portlet" />
<c:set var="preList"   value="ui.approval.common.portlet.todo" />

<script type="text/javascript">
//<![CDATA[
(function($){
	
	//문서 상세 정보
	getApprDetail_${portletConfigId} = function(apprId,entrustUserId) {
		var listType = "listApprTodo";
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
<div id="${portletConfigId}">
<div id="portletlightpackRecentBoard" class="tableList_1"> 
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<colgroup>
			<col width="*"/>
			<col width="15%"/>
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
					<c:forEach var="myRequestItem" items="${searchResult.entity}" varStatus="status"> 
						<tr>
							<td>
								<div class="ellipsis">
									<c:if test="${myRequestItem.approvalId ne user.userId}">
									<a href="#a" onclick="getApprDetail_${portletConfigId}('${myRequestItem.apprId}','${myRequestItem.approvalId}');" title="${myRequestItem.apprTitle}">${myRequestItem.apprTitle}</a>
									</c:if>
									<c:if test="${myRequestItem.approvalId eq user.userId}">
									<a href="#a" onclick="getApprDetail_${portletConfigId}('${myRequestItem.apprId}','');" title="${myRequestItem.apprTitle}">${myRequestItem.apprTitle}</a>
									</c:if>
								</div>
							</td>
							<td>
								<div class="ellipsis">
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${myRequestItem.registerId}', 'bottom');" title="${myRequestItem.registerName}">${myRequestItem.registerName}</a>
								</div>
							</td> 
							<td class="textRight">
								<div class="ellipsis">
								<span class="date"><ikep4j:timezone pattern="MM.dd" date="${myRequestItem.apprReqDate}"/></span>
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