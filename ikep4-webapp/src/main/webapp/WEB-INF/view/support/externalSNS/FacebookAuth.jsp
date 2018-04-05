<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preMessage" value="message.support.externalSNS" />
<html>
	<head>
		<%@ include file="/base/common/meta.jsp"%>
		<script type="text/javascript">
		<c:if test="${authFlag == 'SuccessAuth' }">
			alert("<ikep4j:message pre='${preMessage}' key='success'/>");
			iKEP.returnPopup("success");
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
	</head>
</html>




