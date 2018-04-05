<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<c:forEach var="file" items="${fileList}" varStatus="loopStatus">
	<li>
		<a href="#a" onclick="javascipt:setAddontoContent('${file.displayCode}');">${file.fileRealName}</a>
	</li>
</c:forEach>