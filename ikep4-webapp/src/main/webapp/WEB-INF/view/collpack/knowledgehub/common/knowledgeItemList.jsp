<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgehub.common.message</c:set>
<c:set var="tablePrefix">ui.collpack.knowledgehub.common.table</c:set>

<table summary="<ikep4j:message pre="${messagePrefix}" key="summary"/>">
	<caption></caption>
	<thead>
		<tr>
			<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="orderNumber"/></th>
			<th scope="col" width="65%"><ikep4j:message pre="${tablePrefix}" key="title"/></th>
			<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="registName"/></th>
			<th scope="col" width="15%"><ikep4j:message pre="${tablePrefix}" key="registDate"/></th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="item" items="${knowledgeList}">
		<tr>
			<td>${item.recordNumber}</td>
			<td class="textLeft"><div class="ellipsis"><a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${item.tagItemUrl}"/>');">${item.tagItemName}</a></div></td>
			<td><div class="ellipsis"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.registerId}');">${item.userName}</a></div></td>
			<td><ikep4j:timezone date="${item.registDate}" pattern="yyyy.MM.dd"/></td>
		</tr>
	</c:forEach>
	<c:if test="${0 eq fn:length(knowledgeList)}">
		<tr>
			<td colspan="4" class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></td>
		</tr>
	</c:if>
	</tbody>
</table>
		