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
	<div class="tableList_1 mb15">
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
			<!--div class="btn_more"><a href="#a"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div-->
		</div>
		<!--//subTitle_1 End-->	
		
		<div class="pTitle_1">
			<ikep4j:message pre="${prefix}" key="portlet.birthdayTitle" />
		</div>
		<table summary="<ikep4j:message pre='${prefix}' key='portlet.birthdayTableSummary' />">
			<caption></caption>
			<tbody>
			<c:if test="${empty birthdayList}">
			<tr>
				<td class="textCenter">
					<ikep4j:message pre="${prefix}" key="portlet.noDataAnniversary" />
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty birthdayList}">
			<c:forEach var="birthday" items="${birthdayList}" varStatus="status">
			<tr>
				<c:choose>
				<c:when test="${currentDate == birthday.birthday}">
				<th class="today" scope="row">
					<img alt="<ikep4j:message pre='${preList}' key='birthday' />" src="<c:url value='/base/images/icon/ic_birthday.gif'/>" />
				</c:when>
				<c:otherwise>
				<th class="ellipsis" scope="row">
				</c:otherwise>
				</c:choose>
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${birthday.userId}', 'bottom');iKEP.iFrameContentResize(); return false;">
						${birthday.userName}&nbsp;${birthday.jobPositionName}(${birthday.groupName})
					</a>
				</th>
				<c:choose>
				<c:when test="${currentDate == birthday.birthday}">
				<td class="textRight today">
				</c:when>
				<c:otherwise>
				<td class="textRight">
				</c:otherwise>
				</c:choose>
					<span class="date">
						${birthday.birthdayMmDdType}
					</span>
				</td>
			</tr>	
			</c:forEach>
			</c:if>
											
			</tbody>
		</table>
		<%--
		<div class="dotline_1"></div>
		<div class="pTitle_1">
			<ikep4j:message pre="${prefix}" key="portlet.weddingAnnivTitle" />
		</div>								
		<table summary="<ikep4j:message pre='${prefix}' key='portlet.weddingAnnivTableSummary' />">
			<caption></caption>
			<tbody>
			<c:if test="${empty weddingAnnivList}">
			<tr>
				<td class="textCenter" colspan="2">
					<ikep4j:message pre="${prefix}" key="portlet.noDataAnniversary" />
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty weddingAnnivList}">
			<c:forEach var="weddingAnniv" items="${weddingAnnivList}" varStatus="status">
			<tr>
				<c:choose>
				<c:when test="${currentDate == weddingAnniv.weddingAnniv}">
				<th class="today" scope="row">
					<img alt="<ikep4j:message pre='${preList}' key='weddingAnniv' />" src="<c:url value='/base/images/icon/ic_wedding.gif'/>" />
				</c:when>
				<c:otherwise>
				<th scope="row">
				</c:otherwise>
				</c:choose>
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${weddingAnniv.userId}', 'bottom');iKEP.iFrameContentResize(); return false;">
						${weddingAnniv.userName}&nbsp;${weddingAnniv.jobPositionName}(${weddingAnniv.groupName})
					</a>
				</th>
				<c:choose>
				<c:when test="${currentDate == weddingAnniv.weddingAnniv}">
				<td class="textRight today">
				</c:when>
				<c:otherwise>
				<td class="textRight">
				</c:otherwise>
				</c:choose>
					<span class="date">
						${weddingAnniv.weddingAnnivMmDdType}
					</span>
				</td>
			</tr>	
			</c:forEach>
			</c:if>	
																		
			</tbody>
		</table>	
		--%>	
	</div>			
											
