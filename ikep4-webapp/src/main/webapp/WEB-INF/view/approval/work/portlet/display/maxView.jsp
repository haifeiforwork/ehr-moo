<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="preMsg"   value="ui.approval.common.portlet" />
<c:set var="preList"   value="ui.approval.common.portlet.display" />
<script type="text/javascript">
//<![CDATA[
(function($){	 
	
	//문서 상세 정보
	getApprDetail_${portletConfigId} = function(apprId) {
		var listType = "listApprDisplayWaiting";
		location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType;
	};
	
	$(document).ready(function() {
		
	}); 

})(jQuery);	
//]]>
</script> 	
<!--blockListTable Start-->
<div id="${portletConfigId}"> 
	<div id="portletApprovalDisplay" class="tableList_1"> 
		<table summary="<ikep4j:message pre="${preList}" key="summary" />" id="listTable">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="10%"/>
				<col width="*"/>
				<col width="10%"/>
				<col width="15%"/>
			</colgroup> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr class="bgWhite">
							<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preMsg}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
						<c:forEach var="apdisplay" items="${searchResult.entity}"> 
						<tr> 
							<td>
								<c:if test="${apdisplay.apprDocType eq '0'}"><ikep4j:message pre='${preList}' key='apprDocType0' /></c:if>
								<c:if test="${apdisplay.apprDocType eq '1'}"><ikep4j:message pre='${preList}' key='apprDocType1' /></c:if>
							</td> 
							<td>${apdisplay.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis">
									<a href="#a" onclick="getApprDetail_${portletConfigId}('${apdisplay.apprId}');">${apdisplay.apprTitle}</a>
								</div>
							</td>
							<td><a href="#a" onclick="iKEP.showUserContextMenu(this, '${apdisplay.registerId}', 'bottom');" title="${myRequestItem.examReqName}">${apdisplay.registerName}</a></td>
							<td class="textRight"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${apdisplay.dRegistDate}"/></span></td>
						</tr>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody> 
		</table> 
	</div> 
</div>