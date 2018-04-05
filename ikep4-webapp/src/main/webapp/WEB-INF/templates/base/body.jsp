<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 Content page (blank)
* 작성자 : 주길재
=====================================================
--%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<iframe id="mainFrame" name="mainFrame" class="ui-layout-center b_rl" width="100%" height="100%" frameborder="0" scrolling="auto" src="<c:url value='${mainFrameUrl}'/>">
</iframe>