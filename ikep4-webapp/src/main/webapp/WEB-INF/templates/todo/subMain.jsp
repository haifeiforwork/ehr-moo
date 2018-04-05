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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- script src="<c:url value="/JavaScriptServlet"/>"></script --%>
<%--타이틀--%>
<c:set var="title"><tiles:getAsString name="title"/></c:set>
<title><spring:message code="${title}" /></title>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/theme.css"/>" />

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
</head>

<body>
<!--wrapper Start-->
<div id="wrapper">

	<!--blockContainer Start-->
	<div id="blockContainer" class="leftbg">
		
		<!--blockMain Start-->
		<div id="blockMain">
			<div id="leftMenu">
				<tiles:insertAttribute name="menu" />
			</div>
			
			<div id="mainContents">
				<tiles:insertAttribute name="bodyMain" />
				<tiles:insertAttribute name="bodyTab1" />
				<tiles:insertAttribute name="bodyTab2" />
				
				</div>		
				<!--//tab End-->
			</div>
		</div>
		<!--blockMain End-->
		
	</div>
	<!--blockContainer End-->
	
	<!--
	<div id="blockFooter">
		<div class="footer">
			<tiles:insertAttribute name="footer" />
		</div>
	</div>
	-->

</div>
<!--wrapper End-->
</body>
</html>