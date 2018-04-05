<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<c:if test="${'4' == showType }">
	<li><a href="#a" onclick="javascript:setMblogGroupId('');"> <ikep4j:message pre='${preLabel}' key='allUser' /></a></li>
</c:if>
<c:choose>
	<c:when test="${not empty ownerGroupList || '3' == showType}">
		<c:forEach var="ownerGroup" items="${ownerGroupList}" varStatus="loopStatus">
			<li>
				<c:if test="${'1' == showType || '3' == showType }">
					<a href="<c:url value='/socialpack/microblogging/groupHome.do?mbgroupId=${ownerGroup.mbgroupId}' />">${ownerGroup.mbgroupName}</a>
				</c:if>
				<c:if test="${'2' == showType}">
					<a href="#a" onclick="javascript:searchByMbgroupId('${ownerGroup.mbgroupId }');">${ownerGroup.mbgroupName}</a>
				</c:if>
				<c:if test="${'4' == showType}">
					<a href="#a" onclick="javascript:setMblogGroupId('${ownerGroup.mbgroupId }');">${ownerGroup.mbgroupName}</a>
				</c:if>
			</li>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<li><ikep4j:message pre='${preMessage}' key='list.empty' /></li>
	</c:otherwise>
</c:choose>
<c:if test="${'3' == showType }">
	<li><a href="#a" onclick="javascript:addGroupPop();"> <ikep4j:message pre='${preTap}' key='newGroup' /></a></li>
</c:if>