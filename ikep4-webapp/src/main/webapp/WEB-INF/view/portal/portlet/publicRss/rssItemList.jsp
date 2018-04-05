<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"    value="ui.lightpack.board.portlet.publicBoard" /> 

<%
	java.util.Date todate = new java.util.Date(); 
	request.setAttribute("todate", todate);
%>

<div id="${portletConfigId}" class="tableList_1">
<c:if test="${!empty channel.channelItemList}">
<table summary="" >
	<caption></caption>
	<tbody>
<c:forEach var="item" items="${channel.channelItemList}" varStatus="i">
	<c:if test="${publicRss.listCount >= i.count}">
	<tr>
		<td>
		<div class="ellipsis"><a href="#a" onclick="javascript: winOpen('<c:out value='${item.itemUrl}' escapeXml='false'/>');"><c:out value="${item.itemTitle}"/></a></div>
		</td>
	</tr>
	</c:if>
</c:forEach>
	</tbody>
</table>
</c:if>
<c:if test="${empty channel.channelItemList}">
	<ikep4j:message pre="${prefix}" key="rssInvalidUrlMessage" />
</c:if>
<c:if test="${empty publicRss.rssUrl}">
	<ikep4j:message pre="${prefix}" key="rssNoUrlMessage" />
</c:if>
</div>
<script type="text/javascript">
//<![CDATA[
     function winOpen(url) {
		window.open(url, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
	 }
	$jq("input[id=${channelId}_hidden]").val("open");
	$jq("#portletPublicRSS").ajaxLoadComplete();
//]]>
</script>
