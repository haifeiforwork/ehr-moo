<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="title"><tiles:getAsString name="title"/></c:set>
<title><spring:message code="${title}" /></title>
<%-- script src="<c:url value="/JavaScriptServlet"/>"></script --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/futureplanet/futureplanet.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />

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
	<!-- fWrapper -->
	<div class="fWrapper">
    	<!-- fHeader -->
    	<div class="fHeader" id="header">
    	
        	<tiles:insertAttribute name="header" />
        	
        </div>
        <!-- fHeader end -->
        
        <!-- fLeftMenu -->
        <div class="fLeftMenu">
        
        	<tiles:insertAttribute name="menu" />
			
        </div>
        <!-- fLeftMenu end -->
 
		<!-- fmainFrame -->
        <div class="fmainFrame">
        
        	<tiles:insertAttribute name="body" />
        	
        </div>
        <!-- fmainFrame end -->
    </div>
    <!-- fWrapper end -->
</body>
</html>
