<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript"> 
(function($) {
	$(document).ready(function() {
		<c:if test="${empty boardItem}">
			//alert('<ikep4j:message pre='${prefix}' key='boardItem.empty' />');
		</c:if>
	});
})(jQuery);
</script>	

<c:if test="${!empty boardItem}">
 		<!--blockListTable Start-->
		<div class="blockListTable TeamColl_summaryView">
			<table summary="<ikep4j:message pre="${prefix}" key="boardItem" />">
				<caption></caption>						
				<tbody>
				
					<c:forEach var="boardItem" items="${boardItem}"  varStatus="status">
					<c:choose>
					<c:when test="${boardItem.typeName=='Team'}">
					<c:set var="class" value="cate_tit_1"/>				
					</c:when>
					<c:when test="${boardItem.typeName=='TFT'}">
					<c:set var="class" value="cate_tit_2"/>				
					</c:when>
					<c:when test="${boardItem.typeName=='Cop'}">
					<c:set var="class" value="cate_tit_3"/>				
					</c:when>
					<c:otherwise>
					<c:set var="class" value="cate_tit_4"/>				
					</c:otherwise>
					</c:choose>					
					<tr name="boardItemLine">
						<td>
							<div class="TeamColl_summaryViewTitle">
								<div class="cate_block_1"><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${boardItem.workspaceId}" title="${boardItem.workspaceName}"><span class="${class}">${boardItem.workspaceName}</span></a></div>
								<span><a onclick="javascript:viewItem('<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?workspaceId='/>${boardItem.workspaceId}&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;docPopup=true')" href="#a">${boardItem.title}</a></span>
							</div>
							<div class="summaryViewInfo">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName} ${boardItem.jobTitleName}</a></span><span class="timeline">${ikep4j:getTimeInterval(boardItem.updateDate, user.localeCode)}</span>
								</c:when>
								<c:otherwise>
									<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerEnglishName} ${boardItem.jobTitleEnglishName}</a></span><span class="timeline">${ikep4j:getTimeInterval(boardItem.updateDate, user.localeCode)}</span>
								</c:otherwise>
								</c:choose>								
							</div>
							<spring:htmlEscape defaultHtmlEscape="false"> 
							<div class="summaryViewCon"><a onclick="javascript:viewItem('<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?workspaceId='/>${boardItem.workspaceId}&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;docPopup=true')" href="#a">${ikep4j:replaceQuot(fn:substring(boardItem.contents, 0, 300))}</a></div>
        					</spring:htmlEscape>
        					
							<c:if test="${not empty boardItem.tagList}">
							<div class="summaryViewTag">
								<span class="ic_tag"><span><ikep4j:message pre='${prefix}' key='board.tag' /></span></span>
								<!--tagList--> 
								<c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
								<c:if test="${tagLoop.count != 1}">, </c:if>
								<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
								</c:forEach>
							</div>
							</c:if>

						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>				
		</div>
		<!--//blockListTable End-->
</c:if>