<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.innovation.normalView"/>
<div id="${portletConfigId}">
<h2 class="none"><ikep4j:message pre="${prefix}" key="title" /></h2>
<div class="mtitle"><img src="<c:url value='/base/images/common/mtitle_management.gif'/>" /></div>
<a 
	<c:choose>
		<c:when test="${!empty portletInnovation.url}">
			href="<c:url value='${portletInnovation.url}'/>"	
		</c:when>
		<c:otherwise>
			href="#a"
		</c:otherwise>
	</c:choose>
	<c:if test="${portletInnovation.target == 'INNER'}">
		target="mainFrame"
	</c:if>
	<c:if test="${portletInnovation.target == 'WINDOW'}">
		target="_blank"
	</c:if>
>
<c:if test="${innovationImageYn == 'Y'}">
	<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletInnovation.imageFileId}" title="<ikep4j:message pre="${prefix}" key="img.innovationImage" />" /><!--권장이미지 사이즈 : 750x600-->
</c:if>
<c:if test="${innovationImageYn != 'Y'}">
	<img src="<c:url value='/base/images/common/img_management.gif'/>" title="<ikep4j:message pre="${prefix}" key="img.innovationImage" />" /><!--권장이미지 사이즈 : 750x600-->
</c:if>
</a>
</div>
