<%@ page isErrorPage="true"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<c:set var="prefix" value="message.portal.login.accessdenied"/>
<html>
<head>
<title>Error</title>
</head>
<script language="javascript">
</script>
<body bgcolor="#ffffff" text="#000000">
	<ikep4j:message pre="${prefix}" key="noAuthority" /><br/>
	<c:url value="/logout.do" var="logoutUrl"/>
	<a href="${logoutUrl}"><ikep4j:message pre="${prefix}" key="logout" /></a>
</body>
</html>

