<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 베이스 레이아웃 페이지
* 작성자 : 주길재
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--=============================-->
<!--JSTL & Custom Tag Libray Area-->
<!--=============================--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%--타이틀--%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="title"><tiles:getAsString name="title"/></c:set>
<title><spring:message code="${title}" /></title>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript">
	var flg = "0";
	function selfFocus(){

		if(flg == "0"){
		self.focus();
		}
		flg = "1";
		
	}
</script>
</head>

<c:set var="popupThemeUrl" value="" />
<c:choose>
	<c:when test="${portalPopup.popupTheme == '1'}">
		<c:set var="popupThemeUrl" value="/base/images/common/skinDesign_05.gif" />
	</c:when>
	<c:when test="${portalPopup.popupTheme == '2'}">
		<c:set var="popupThemeUrl" value="/base/images/common/skinDesign_07.gif" />
	</c:when>
	<c:when test="${portalPopup.popupTheme == '3'}">
		<c:set var="popupThemeUrl" value="/base/images/common/skinDesign_04.gif" />
	</c:when>
	<c:when test="${portalPopup.popupTheme == '4'}">
		<c:set var="popupThemeUrl" value="/base/images/common/skinDesign_03.gif" />
	</c:when>
</c:choose>

<body style="margin: 0px; <c:if test="${!empty popupThemeUrl}">background:url('<c:url value='${popupThemeUrl}'/>') repeat;</c:if>" onBlur="selfFocus();">
	<tiles:insertAttribute name="body" />
</body>
</html>