<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.maxView"/>

<c:set var="channel" value="${channel}" />

<script type="text/javascript">
<!--   
	 function winOpen(url) {
		window.open(url, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
	 }
//-->
</script>
<c:if test="${!empty channel.channelItemList}">
<h4 class="guidetitle_han"><c:out value="${channel.channelTitle}"/></h4>
<c:forEach var="item" items="${channel.channelItemList}" varStatus="i">
	<c:if test="${portletRss.listCount >= i.count}">
		<a href="#a" onclick="javascript: winOpen('<c:out value='${item.itemUrl}' escapeXml='false'/>');"><c:out value="${item.itemTitle}"/></a><br/>
	</c:if>
</c:forEach>
</c:if>
<c:if test="${empty channel.channelItemList}">
	<ikep4j:message pre="${prefix}" key="rssInvalidUrlMessage" />
</c:if>
<c:if test="${empty portletRss.rssUrl}">
	<ikep4j:message pre="${prefix}" key="rssNoUrlMessage" />
</c:if>