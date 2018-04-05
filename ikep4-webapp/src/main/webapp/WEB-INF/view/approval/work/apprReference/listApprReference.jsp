<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  	value="ui.approval.work.apprReference.ReferenceView.header" />
<c:set var="preView"  		value="ui.approval.work.apprReference.ReferenceView.view" />
<c:set var="preList"  		value="ui.approval.work.apprReference.ReferenceView.list" />
<c:set var="preButton"		value="ui.approval.work.apprReference.ReferenceView.button" />
<c:set var="preMessage"		value="message.approval.work.apprReference.ReferenceView" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<c:if test="${refCount > 0 }">

<script type="text/javascript">
<!-- 
(function($) {
		
	// 결재선 정보
	var	items		=	[];

	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {

	});
})(jQuery);  
//-->
</script>
			
<!--blockListTable Start-->
<b><ikep4j:message pre='${preList}' key='pageTitle'/></b>
<div class="blockDetail">
	<table summary="<ikep4j:message pre='${preList}' key='summary' />">
		<caption></caption>
		<col style="width: 20%;"/>
		<col style="width: 15%;"/>
		<col />
		<thead>
			<tr>	
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='refer' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='updateDate' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='receiveMessage' /></th>							
			</tr>
		</thead>
		<tbody>			
			
			<c:if test="${!empty list}">
			<c:forEach var="item" items="${list}">
			
			<tr>
			<td class="textCenter">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${item.userName} ${item.jobTitleName} ${item.groupName}
				</c:when>
				<c:otherwise>
					${item.userEnglishName} ${item.jobTitleEnglishName} ${item.groupEnglishName}
				</c:otherwise>
				</c:choose>				
			</td>
			<td class="textCenter"><ikep4j:timezone date="${item.updateDate}" pattern="yyyy.MM.dd HH:mm"/></td>
			<td class="textLeft">
				<% pageContext.setAttribute("newLineChar", "\n"); %> 
				${fn:replace(item.receiveMessage, newLineChar, '<br/>')}
			</td>
			</tr>

			</c:forEach>
			</c:if>		
			
		</tbody>
	</table>
</div>
</c:if>	
