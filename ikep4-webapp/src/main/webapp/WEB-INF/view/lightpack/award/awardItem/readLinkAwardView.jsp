<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<script type="text/javascript">
<!--  
(function($){	 
	$(document).ready(function() { 
		<c:choose>
			<c:when test="${popupYn eq '0'}">
				location.href = "${award.url}";
			</c:when>
			<c:when test="${popupYn eq '1'}">
				iKEP.dialogOpen("${award.url}", {width:900, height:500, resizable: true});
			</c:when>
		</c:choose>  
	});   
})(jQuery); 
//-->
</script>
