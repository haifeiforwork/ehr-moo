<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  

<c:set var="preMsg"   value="ui.approval.common.portlet" />
<c:set var="preButton" value="ui.approval.common.button" />
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
		$("#updatePageCount").click(function() { 
		    $.post('<c:url value="/approval/work/portlet/display/updatePageCount.do"/>', $("#configForm").serialize()) 
		    .success(function(data) {
		    	alert('<ikep4j:message pre='${preMsg}' key='updateSuccess' />');
		    	
		    	$("#portletApprovalDisplayList").parent().parent().parent().trigger("click:reload");
		    })
		    .error(function(event, request, settings) { 
		    	alert('<ikep4j:message pre='${preMsg}' key='updateError' />');
		    	
		    });  
		});
	});  

})(jQuery);	
//]]>
</script> 
<form id="configForm">
<div class="poEdit">
	<div>
		<ikep4j:message pre='${preMsg}' key='selectPageCount' />:
		<select title="<ikep4j:message pre='${preMsg}' key='selectPageCount' />" name="count">
			<option value="3"  <c:if test="${count eq 3}">selected="selected"</c:if>>3</option>
			<option value="5"  <c:if test="${count eq 5}">selected="selected"</c:if>>5</option>
			<option value="7"  <c:if test="${count eq 7}">selected="selected"</c:if>>7</option>
			<option value="10" <c:if test="${count eq 10}">selected="selected"</c:if>>10</option>
		</select>
	</div>
	<div class="textRight"><a href="#a" id="updatePageCount" class="button_s"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></div>
</div>
</form>
<div id="portletApprovalDisplayList" class="tableList_1"> 
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
					<c:forEach var="apdisplay" items="${searchResult.entity}" varStatus="status"> 
						<tr>
							<td>
								<div class="ellipsis">
									<a href="#a" onclick="getApprDetail_${portletConfigId}('${apdisplay.apprId}');">${apdisplay.apprTitle}</a>
								</div>
							</td>
							<td>
								<div class="ellipsis">
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${apdisplay.registerId}', 'bottom');" title="${myRequestItem.examReqName}">${apdisplay.registerName}</a>
								</div>
							</td> 
							<td class="textRight">
								<div class="ellipsis">
								<span class="date"><ikep4j:timezone pattern="MM.dd" date="${apdisplay.dRegistDate}"/></span>
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