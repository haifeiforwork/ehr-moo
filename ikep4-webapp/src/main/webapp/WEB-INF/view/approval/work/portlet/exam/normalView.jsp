<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 

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
<c:set var="preDetail"  value="ui.lightpack.board.portlet.recentBoard" />
<div id="${portletConfigId}">
<div id="portletApprovalExam" class="tableList_1"> 
	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<colgroup>
			<col width="*"/>
			<col width="15%"/>
			<col width="10%"/>
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
									<a href="#a" onclick="getApprDetail_${portletConfigId}('${myRequestItem.apprId}');" title="${myRequestItem.apprTitle}">${myRequestItem.apprTitle}</a>
								</div>
							</td>
							<td>
								<div class="ellipsis">
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${myRequestItem.examReqName}', 'bottom');" title="${myRequestItem.examReqName}">${myRequestItem.examReqName}</a>
								</div>
							</td> 
							<td class="textRight">
								<div class="ellipsis">
								<span class="date"><ikep4j:timezone pattern="MM.dd" date="${myRequestItem.examReqDate}"/></span>
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