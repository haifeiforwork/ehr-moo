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
		iKEP.showGallery($("a.image-gallery"));
		
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
			<div class="btn_more"><a href="<c:url value='/collpack/collaboration/workspace/getWorkspaceImage.do'/>?workspaceId=${workspacePortletLayout.workspaceId}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>
		<!--//subTitle_1 End-->	
		
		<table summary="${workspacePortletLayout.workspacePortlet.portletName}">
		<tr><td class="textCenter">
			<c:if test="${!empty searchResult.entity}">
				<div class="cafe_gal">
					<ul>
							<c:forEach var="file" items="${searchResult.entity}"  varStatus="status">
								<li><a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${file.fileId}&thumbnailYn=Y" title="${favorite.fileName}"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${file.fileId}&thumbnailYn=Y" alt="image" width="90px" height="70px"/></a></li>
							</c:forEach>
					</ul>
				</div>
			</c:if>
			<c:if test="${empty searchResult.entity}">
				<ikep4j:message pre="${prefix}" key="portlet.noDataRecentItem" />
			</c:if>	
		</td></tr>
		</table>
		
	</div>	
