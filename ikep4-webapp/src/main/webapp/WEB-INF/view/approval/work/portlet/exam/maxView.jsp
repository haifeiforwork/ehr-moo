<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMsg"   value="ui.approval.common.portlet" />
<c:set var="preList"   value="ui.approval.common.portlet.exam" />

<script type="text/javascript">
//<![CDATA[
(function($){	 
	
	//문서 상세 정보
	getApprDetail_${portletConfigId} = function(apprId) {
		var listType = "listApprRequestExam";
		location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType;
	};
	
	$(document).ready(function() {
		
	}); 

})(jQuery);	
//]]>
</script> 	
<!--blockListTable Start-->
<div id="${portletConfigId}"> 
	<div id="portletApprovalExam" class="tableList_1"> 
		<table summary="<ikep4j:message pre="${preList}" key="summary" />" id="listTable">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="*"/>
				<col width="10%"/>
				<col width="15%"/>
				<col width="10%"/>
				<col width="15%"/>
			</colgroup> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr class="bgWhite">
							<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preMsg}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
						<c:forEach var="aplist" items="${searchResult.entity}"> 
						<tr>  
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis">
									<a href="#a" onclick="getApprDetail_${portletConfigId}('${aplist.apprId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a>
								</div>
							</td>
							<td><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.examReqId}', 'bottom')">${aplist.examReqName}</a></td>
							<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.examReqDate}"/></td>
							<td><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></td>
							<td><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/></span></td>
						</tr>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody> 
		</table> 
	</div> 
</div>