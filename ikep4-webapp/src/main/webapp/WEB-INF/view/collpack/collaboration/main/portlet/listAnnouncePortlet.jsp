<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		iKEP.iFrameContentResize();  
	});
})(jQuery);
</script>
<div class="tableList_2 mb15">


	<!--subTitle_1 Start-->
	<div class="subTitle_1">
		<h3>
			<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						${workspacePortletLayout.workspacePortlet.portletName}
					</c:when>
					<c:otherwise>
						${workspacePortletLayout.workspacePortlet.portletEnglishName}
					</c:otherwise>
				</c:choose>	
		</h3>
		<div class="btn_more"><a href="<c:url value='/collpack/collaboration/board/announce/listAnnounceItemView.do'/>?workspaceId=${workspacePortletLayout.workspaceId}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
	</div>
	<!--//subTitle_1 End-->	
	
	<table summary="${workspacePortletLayout.workspacePortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty announceItem}">
			<c:forEach var="announceItem" items="${announceItem}"  varStatus="status">
			<tr>
				<th class="ellipsis" width="*" scope="row">
					
					<!--
					<c:choose>
						<c:when test="${announcePermission>0}">
							<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/collaboration/board/announce/readAnnounceItemView.do?workspaceId=${workspacePortletLayout.workspaceId}&itemId=${announceItem.itemId}'/>&docPopup=true', '${ikep4j:replaceQuot(announceItem.title)}', 800, 500);" title="${announceItem.title}">${announceItem.title}</a>
						</c:when>
						<c:otherwise>
							${announceItem.title}
						</c:otherwise>
					</c:choose>
					-->
					  
					<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/collaboration/board/announce/readAnnounceItemView.do?workspaceId=${workspacePortletLayout.workspaceId}&itemId=${announceItem.itemId}'/>&docPopup=true', '${ikep4j:replaceQuot(announceItem.title)}', 800, 500);" title="${announceItem.title}">${announceItem.title}</a>
						
				</th>
				<td class="textCenter" width="80"><span class="name"><a href="#a">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						<a href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${announceItem.registerName}</a>
					</c:when>
					<c:otherwise>
						<a href="#a" onclick="iKEP.showUserContextMenu(this, '${announceItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${announceItem.registerEnglishName}</a>
					</c:otherwise>
					</c:choose>					
				</a></span></td>
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${announceItem.updateDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty announceItem}">
				<tr>
					<td class="textCenter" colspan="2"><ikep4j:message pre="${prefix}" key="portlet.noDataAnnounce" />	</td>
				</tr>			
			</c:if>			
																																							
		</tbody>
	</table>
	
</div>					
