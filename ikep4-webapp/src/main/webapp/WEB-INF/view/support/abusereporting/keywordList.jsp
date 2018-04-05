<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<c:forEach var="arKeyword" items="${arKeywordlist}" varStatus="loopStatus">
	<tr style="cursor:pointer;">
		<td class="textCenter"><input name="keywords" class="checkbox" title="checkbox" type="checkbox" value="${arKeyword}" /></td>
		<td onclick="javascript:getRegForm('3','${arKeyword}');">${arKeyword}</td>
	</tr>
</c:forEach>
				