<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%--
=====================================================
* 기능 설명 : 스프링 시큐리트의 커스텀 로그인 페이지 샘플
* 작성자 : 주길재
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="prefix" value="message.portal.login.loginForm"/>	
<c:set var="contextRoot" value="${pageContext.request.contextPath}" scope="request"/>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<c:set var="title"><tiles:getAsString name="title"/></c:set>
<%-- script src="${contextRoot}/JavaScriptServlet"/>"></script --%>
<title><spring:message code="${title}" /></title>
<link rel="shortcut icon" href="${contextRoot}/base/images/common/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/theme01/jquery_ui_custom.css" />
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/common.css" />
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/theme01/theme.css" />
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery.ui.datepicker.customize.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/plugins.pack.js"></script>
<script type="text/javascript">var contextRoot = "${contextRoot}";</script>
<script type="text/javascript" src="${contextRoot}/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/common.pack.js"></script>
</head>
<body>
<tiles:insertAttribute name="body" />
</body>
</html>