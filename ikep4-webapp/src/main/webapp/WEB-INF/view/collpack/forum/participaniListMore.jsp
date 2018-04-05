<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:forEach var="participant" items="${participantList}" varStatus="loop">
	<li class="participant_${participantCount}"><a href="#a" onclick="viewPopUpProfile('${participant.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${participant.userName} ${participant.jobTitleName}</c:when><c:otherwise>${participant.userEnglishName} ${participant.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${participant.teamName}</c:when><c:otherwise>${participant.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${participant.profilePicturePath}'/>" alt="profile" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></li>
</c:forEach>	