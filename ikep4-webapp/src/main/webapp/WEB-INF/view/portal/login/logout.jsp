<%--
=====================================================
* 기능 설명 : 스프링 시큐리트의 커스텀 로그인 페이지 샘플
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>

<c:set var="prefix" value="message.portal.login.logout"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
  <head>
    <title>Logout</title>
  </head>
  <body>
  	<ikep4j:message pre="${prefix}" key="message1" /><br/>
  	<ikep4j:message pre="${prefix}" key="message2" /><p/>
  	
  	<a href='loginForm.do'><ikep4j:message pre="${prefix}" key="loginPage" /></a>

  </body>
</html>