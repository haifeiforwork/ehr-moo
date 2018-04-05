<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<?xml version="1.0" encoding="utf-8"?>
<data>
	<initList>
		<c:choose>
		    <c:when test="${user.localeCode == portal.defaultLocaleCode}">
		    	<user id="<c:out value="${userInfo.userId}"/>" name="<c:out value="${userInfo.userName}"/>" position="<c:out value="${userInfo.jobTitleName}"/>" point="" groupid="" groupname="" pictureImgname="<c:url value='${userInfo.profilePicturePath}'/>" url=""/>
		    </c:when>
		    <c:otherwise>
		    	<user id="<c:out value="${userInfo.userId}"/>" name="<c:out value="${userInfo.userEnglishName}"/>" position="<c:out value="${userInfo.jobTitleEnglishName}"/>" point="" groupid="" groupname="" pictureImgname="<c:url value='${userInfo.profilePicturePath}'/>" url=""/>
		    </c:otherwise>
	    </c:choose>
	</initList>
	

	<groupList>
		<group id="1" name="Direct Network">
			<c:forEach var="item" items="${directList}">
				<c:choose>
				    <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userName}"/>" position="<c:out value="${item.jobTitleName}"/>" point="${item.point}" pictureImgname="<c:url value='${item.profilePicturePath}'/>" targetType="${item.targetType}"/>
				    </c:when>
				    <c:otherwise>
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userEnglishName}"/>" position="<c:out value="${item.jobTitleEnglishName}"/>" point="${item.point}" pictureImgname="<c:url value="${item.profilePicturePath}"/>" targetType="${item.targetType}"/>
				    </c:otherwise>
			    </c:choose>
			</c:forEach>
		</group>
		<group id="2" name="Communication Network">
			<c:forEach var="item" items="${communicationList}">
				<c:choose>
				    <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userName}"/>" position="<c:out value="${item.jobTitleName}"/>" point="${item.point}" pictureImgname="<c:url value='${item.profilePicturePath}'/>" targetType="${item.targetType}"/>
				    </c:when>
				    <c:otherwise>
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userEnglishName}"/>" position="<c:out value="${item.jobTitleEnglishName}"/>" point="${item.point}" pictureImgname="<c:url value="${item.profilePicturePath}"/>" targetType="${item.targetType}"/>
				    </c:otherwise>
			    </c:choose>
			</c:forEach>
		</group>
		<group id="3" name="Fellowship Network">
			<c:forEach var="item" items="${fellowshipList}">
				<c:choose>
				    <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userName}"/>" position="<c:out value="${item.jobTitleName}"/>" point="${item.point}" pictureImgname="<c:url value='${item.profilePicturePath}'/>" targetType="${item.targetType}"/>
				    </c:when>
				    <c:otherwise>
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userEnglishName}"/>" position="<c:out value="${item.jobTitleEnglishName}"/>" point="${item.point}" pictureImgname="<c:url value="${item.profilePicturePath}"/>" targetType="${item.targetType}"/>
				    </c:otherwise>
			    </c:choose>
			</c:forEach>
		</group>
		<group id="4" name="Expertise Network">
			<c:forEach var="item" items="${expertiseList}">
				<c:choose>
				    <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userName}"/>" position="<c:out value="${item.jobTitleName}"/>" point="${item.point}" pictureImgname="<c:url value='${item.profilePicturePath}'/>" targetType="${item.targetType}"/>
				    </c:when>
				    <c:otherwise>
				    	<user id="<c:out value="${item.userId}"/>" name="<c:out value="${item.userEnglishName}"/>" position="<c:out value="${item.jobTitleEnglishName}"/>" point="${item.point}" pictureImgname="<c:url value="${item.profilePicturePath}"/>" targetType="${item.targetType}"/>
				    </c:otherwise>
			    </c:choose>
			</c:forEach>
		</group>
	</groupList>
</data>