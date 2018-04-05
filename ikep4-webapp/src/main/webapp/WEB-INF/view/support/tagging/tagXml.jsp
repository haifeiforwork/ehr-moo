<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<?xml version="1.0" encoding="utf-8" ?>
<tags itemType="${itemType}" subItemType="${subItemType}">

<c:forEach var="tag" items="${tagList}" >
	<tag priority="${tag.displayStep}" idx="${tag.tagId}">${tag.tagName}</tag>
</c:forEach>
</tags>