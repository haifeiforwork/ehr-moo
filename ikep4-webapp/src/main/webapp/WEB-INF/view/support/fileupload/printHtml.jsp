<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<%@ include file="/base/common/fileUploadControll.jsp"%> 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.fileupload.header" /> 
<c:set var="preList"    value="ui.support.fileupload.list" />
<c:set var="preDetail"  value="ui.support.fileupload.detail" />
<c:set var="preButton"  value="ui.support.fileupload.button" /> 
<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.printElement.js"/>"></script>

<script type="text/javascript" language="javascript">
<!--  
(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$('#toPrint').printElement({});
		//$('#toPrint').printElement({printMode: 'popup'});
		//$('#toPrint').printElement({leaveOpen: true, printMode: 'popup'});

		iKEP.closePop();
		
	});
})(jQuery);  
//-->	
</script>
<div>
	<div id="toPrint">
			${htmlDocument}
	</div>
</div>