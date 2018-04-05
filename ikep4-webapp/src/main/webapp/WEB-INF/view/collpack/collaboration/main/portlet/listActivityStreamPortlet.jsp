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

function showDatail2(url, title) {
	
	url = iKEP.getContextRoot() + url;
	//iKEP.popupOpen(url, {width:800, height:600});
	
	var width = 800;
	var height = 500;
	
	parent.parent.showMainFrameDialog(url, title, width, height);
};
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
			<div class="btn_more"><a href="<c:url value="/collpack/collaboration/main/listActivityStream.do" />?workspaceId=${workspacePortletLayout.workspaceId}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>
		<!--//subTitle_1 End-->
			
		<table summary="${workspacePortletLayout.workspacePortlet.portletName}">
			<caption></caption>
			<tbody>
				<c:if test="${!empty activityStream}">
				<c:forEach var="activity" items="${activityStream}"  varStatus="status">
				<c:if test="${status.index<5}">
				<tr>
					<th class="ellipsis" width="*" scope="row"><a href="#a">${activity.activityDescription}</a></th>
					<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${activity.activityDate}"/></span></td>
				</tr>
				</c:if>				
				</c:forEach>
				</c:if>

				<c:if test="${empty activityStream}">
				<tr>
					<td class="textCenter" colspan="2"><ikep4j:message pre="${prefix}" key="portlet.noDataActivity" /></td>
				</tr>				
				</c:if>																																						
			</tbody>
		</table>	
		

	</div>
