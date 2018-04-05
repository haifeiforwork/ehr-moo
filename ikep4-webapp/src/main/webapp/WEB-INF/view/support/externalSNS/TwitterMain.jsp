<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<html>
<c:set var="preMessage" value="message.support.externalSNS" />
<head>
	<%@ include file="/base/common/meta.jsp" %>
	<script type="text/javascript">
	function auth(){
		document.location.href = '${authenticationURL}';
	}	
			
	</script>
</head>
<body>
	<script type="text/javascript">
	<c:if test="${authFlag == 'NeedAuth' }">
		auth();
	</c:if>
	<c:if test="${authFlag == 'AlreadyAuth' }">
		alert("<ikep4j:message pre='${preMessage}' key='already'/>");
		iKEP.returnPopup("already");
	</c:if>
	<c:if test="${authFlag == 'FalureAuth' }">
		alert("<ikep4j:message pre='${preMessage}' key='failure'/>");
		iKEP.returnPopup("failure");
	</c:if>
	</script>
</body>

</html>