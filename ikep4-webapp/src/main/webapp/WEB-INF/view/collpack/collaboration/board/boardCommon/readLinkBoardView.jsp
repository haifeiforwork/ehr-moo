<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<script type="text/javascript">
<!--   

	<c:choose>
		<c:when test="${board.urlPopup == 0}">
			
			setTimeout(function() {
				$jq("#iframeLink").attr("src", "${board.url}");
			}, 500);
		
		</c:when>
		<c:when test="${board.urlPopup == 1}">
			iKEP.popupOpen("${board.url}", {width:800, height:600}, "${board.boardName}");
		</c:when>
	</c:choose>
	
	$jq(document).ready(function() { 
		iKEP.iFrameContentResize();
	});   
	
//-->
</script>

<c:if test="${board.boardPopup == 0}">
	<iframe id="iframeLink" class="ui-layout-center" width="100%" height="700" frameborder="0" scrolling="auto" src=""/>">
	</iframe>
</c:if>
