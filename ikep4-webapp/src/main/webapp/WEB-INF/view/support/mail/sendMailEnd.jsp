<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.mail.header" /> 
<c:set var="preList"    value="ui.support.mail.list" />
<c:set var="preDetail"  value="ui.support.mail.detail" />
<c:set var="preButton"  value="ui.support.mail.button" /> 
<c:set var="preMessage" value="ui.support.mail.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">

(function($) {
	
	if(${reStr == "success"}) {
		alert('<ikep4j:message pre='${preMessage}' key='send.success' />');
		if(opener && opener.callbackMail){
			opener.callbackMail();
		}
		this.close();
	}
	else {
		alert('<ikep4j:message pre='${preMessage}' key='send.error' />');
		history.back();
	}
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
	
		
	});
	
})(jQuery);  


	
</script>

	