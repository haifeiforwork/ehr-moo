<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.fileupload.header" /> 
<c:set var="preList"    value="ui.support.fileupload.list" />
<c:set var="preDetail"  value="ui.support.fileupload.detail" />
<c:set var="preButton"  value="ui.support.fileupload.button" /> 
<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	
	
	resizeWindow = function() {
		
		var winWidth = "400";
		var winHeight = "400";
		
		var maxWidth = screen.width;
		var maxHeight = screen.height;
		
		var imageWidth = $jq("#viewImageDiv").width()+70;
		var imageHeight = $jq("#viewImageDiv").height()+210;
		
		if(imageWidth < winWidth)
			winWidth = winWidth;
		else if (imageWidth > maxWidth)
			winWidth = maxWidth;
		else 
			winWidth = imageWidth;
		
		if(imageHeight < winHeight)
			winHeight = winHeight;
		else if (imageHeight > maxHeight)
			winHeight = maxHeight;
		else 
			winHeight = imageHeight;
		
		window.resizeTo(winWidth,winHeight);	
		
		var sWidth = (maxWidth-winWidth)/2;
		var sHeight = (maxHeight-winHeight)/2;
		
		window.moveTo(sWidth, sHeight);
	};
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		//setTimeout("resizeWindow()", 1000);
		
	});
	
})(jQuery);  


//-->	
</script>

		<!--popup Start-->
		<div id="popup">
		
				
				<div class="blockBlank_10px"></div>	
				
				<!--popup_contents Start-->
				<div id="popup_contents" style="text-align:center">
					
	
						<img id="viewImageDiv" src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${fileId}" />

					<div class="blockBlank_10px"></div>			
				
				
				</div>
				<!--//popup_contents End-->
				
				<!--popup_footer Start-->
				<div id="popup_footer"></div>
				<!--//popup_footer End-->
			
		</div>

