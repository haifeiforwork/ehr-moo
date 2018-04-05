<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

<c:set var="prefixWs"  value="message.collpack.collaboration.workspace.main.menu" />

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!--   

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
	});

})(jQuery);  
		 
//-->
</script>


<div id="${portletConfigId}" class="tableList_1">

<c:forEach var="workspace" items="${myWorkspaceList}" varStatus="status">


	<div class="pTitle_1">
		<a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspace.workspaceId}"> ${workspace.workspaceName}</a>
	</div>
	<table summary="${workspace.workspaceName}">
		<caption></caption>
		<tbody>
			<c:forEach var="itemList" items="${wsItemList}" varStatus="itemStatus">
			<c:if test="${workspace.workspaceId==itemList.workspaceId}">
			<tr>
				<c:if test="${!empty itemList.boardName}">
				<td width="*" scope="row"><div class="ellipsis"><a href="#a"  onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?itemId=${itemList.itemId}'/>&docPopup=true', '${board.boardName}', 800, 500);" title="${itemList.title}">[${itemList.boardName}] ${itemList.title}</a></div></td>
				</c:if>
				<c:if test="${empty itemList.boardName}">
				<td width="*" scope="row"><div class="ellipsis"><a href="#a"  onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/collaboration/board/announce/readAnnounceItemView.do?itemId=${itemList.itemId}workspaceId=${workspace.workspaceId}'/>&docPopup=true', '${board.boardName}', 800, 500);" title="${itemList.title}">[<ikep4j:message pre='${prefixWs}' key='detail.menu.announce' />] ${itemList.title}</a></div></td>
				</c:if>	
				<td width="55" class="textCenter">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${itemList.registerName}</a>
				</c:when>
				<c:otherwise>
				<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');">${itemList.registerEnglishName}</a>
				</c:otherwise>           
				</c:choose>
				</td>			
				<td width="30" class="textRight"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${itemList.itemRegistDate}"/></span></td>
			</tr>
			</c:if>
			</c:forEach>

		</tbody>
	</table>
	<c:if test="${!status.last}"><div class="dotline_1"></div></c:if>
	


</c:forEach>

<%--c:if test="${status.last}"><div class="l_b_corner"></div><div class="r_b_corner"></div></c:if--%>
</div>