<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.socialpack.microblogging.poll.header" /> 
<c:set var="prList"     value="ui.socialpack.microblogging.poll.list" />
<c:set var="preDetail"  value="ui.socialpack.microblogging.poll.detail" />
<c:set var="preButton"  value="ui.socialpack.microblogging.poll.button" /> 
<c:set var="preMessage" value="ui.socialpack.microblogging.poll.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">
<!--   

(function($) {
	
	
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		opener.setPollId('${pollId}');
		iKEP.closePop();
		
	});
	
	
	
})(jQuery);  

//-->
</script>
