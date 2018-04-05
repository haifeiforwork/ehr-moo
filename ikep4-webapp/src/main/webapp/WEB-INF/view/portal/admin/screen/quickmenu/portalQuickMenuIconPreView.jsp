<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

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
<c:set var="user" value="${sessionScope['ikep.user']}" />
<title>iKEP4.0</title>

<c:set var="preMain" value="ui.portal.admin.screen.quickmenu.portalQuickMenuIconPreView.main"/>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/ko.js"/>"></script>

<script type="text/javascript">
//<![CDATA[
//]]>
</script>
</head>

<body>

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div class="" id="layer_p" title="<ikep4j:message pre='${preMain}' key='layerTitle' />">
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3><ikep4j:message pre="${preMain}" key="classId" /> : <span>ACDEXX</span></h3>
	</div>
	<!--//subTitle_2 End-->
	
	<!--topMenu Start-->
	<div id="topMenu3">
		<h1 class="none"><ikep4j:message pre="${preMain}" key="title" /></h1>			
		<ul>
			<li><p class="textCenter"><strong>1<ikep4j:message pre="${preMain}" key="theme" /></strong></p><a class="quickic_mail" title="Mail" href="#" onclick="return false;"><span>Mail</span></a></li>
			<li><p class="textCenter"><strong>2<ikep4j:message pre="${preMain}" key="theme" /></strong></p><a class="quickic_appr" title="appr" href="#" onclick="return false;"><span>Approval</span></a></li>
			<li><p class="textCenter"><strong>3<ikep4j:message pre="${preMain}" key="theme" /></strong></p><a class="quickic_todo" title="todo" href="#" onclick="return false;"><span>To-DO</span></a></li>
			<li><p class="textCenter"><strong>4<ikep4j:message pre="${preMain}" key="theme" /></strong></p><a class="quickic_plan" title="plan" href="#" onclick="return false;"><span>Plan</span></a></li>
			<li><p class="textCenter"><strong>5<ikep4j:message pre="${preMain}" key="theme" /></strong></p><a class="photo_noimage" title="noimage" href="#" onclick="return false;"><span>noimage</span></a></li>		
		</ul>
	</div>
	<!--//topMenu End-->
</div>	
<!-- //Modal window End -->
</body>
</html>