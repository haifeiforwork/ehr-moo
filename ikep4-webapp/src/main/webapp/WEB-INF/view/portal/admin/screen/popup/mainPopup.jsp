<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
<!--
(function($) {
    $jq(document).ready(function() {
    	
    	 //var winHeight = $(window).height();
		//var footerHeight = $("div#checkboxDiv").outerHeight(true);
		//$("div#contentsDiv").css("height", (winHeight - footerHeight) + "px"); 
    	
    	$jq("#popupCheck_${portalPopup.popupId}").click(function() {
    		var now = new Date(); 
			var dday = new Date(0000,00,00,00,00,00);
			var days = (dday - now) / 1000 / 60 / 60 / 24; 
			var daysRound = Math.floor(days); 
			var hours = (dday - now) / 1000 / 60 / 60 - (24 * daysRound); 
			var hoursRound = Math.floor(hours); 
			var minutes = (dday - now) / 1000 /60 - (24 * 60 * daysRound) - (60 * hoursRound); 
			var minutesRound = Math.floor(minutes); 
			var seconds = (dday - now) / 1000 - (24 * 60 * 60 * daysRound) - (60 * 60 * hoursRound) - (60 * minutesRound); 
			var secondsRound = Math.round(seconds); 
			var extime = (hoursRound*60*60)+(minutesRound*60)+secondsRound
    		var domain = document.domain;
    		
    		var option = {
    			domain: domain,
    	   		//expires: 1 * 24 * 60 * 60,
				expires: extime,
    	   		path: '/'
            };

    		if($jq("#popupCheck_${portalPopup.popupId}").attr("checked")) {
    			$.cookie("IKEP_POPUP_${portalPopup.popupId}", "none", option);
    		} else {
    			$.cookie("IKEP_POPUP_${portalPopup.popupId}", "", option);
    		}
    		
    		<c:if test="${portalPopup.windowYn == 'Y'}">
    			window.close();	
    		</c:if>
    		<c:if test="${portalPopup.windowYn != 'Y'}">
    			parent.dialogClose('${portalPopup.popupId}');	
			</c:if>
		});
    	
    	iKEP.setPopupImageRendering("#contentsDiv");
    	
    });
})(jQuery);
//-->
</script>

<c:choose>
	<c:when test="${portalPopup.popupType == 'C'}">
		<div id="contentsDiv" style="overflow: auto; padding-left: 8px; padding-right: 8px;">
			${portalPopup.contents}
		</div>
	</c:when>
	<c:otherwise>
		<div id="contentsDiv">
			<iframe width="100%" height="100%" frameborder="0" scrolling="auto" src="<c:url value='${portalPopup.popupUrl}'/>"></iframe>
		</div>
		
	</c:otherwise>
</c:choose>
<div id="checkboxDiv" style="width:100%; background-color: #CACACA;text-align: right; padding-bottom:1px;">
				<input type="checkbox" id="popupCheck_${portalPopup.popupId}" name="popupCheck_${portalPopup.popupId}" title="popupCheck" />
				<span><ikep4j:message key='message.portal.admin.screen.popup.mainPopup.checkMessage'/></span>
			</div>

