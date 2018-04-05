<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
  
<script type="text/javascript">
<!--   

	<c:choose>
		<c:when test="${award.awardPopup == 0}">
			
			setTimeout(function() {
				$jq("#iframeLink").attr("src", "${award.url}");
			}, 500);
		
		</c:when>
		<c:when test="${award.awardPopup == 1}">

			var awardName = "";
			<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">awardName = "${award.awardName}";</c:when>
				<c:otherwise>awardName = "${award.awardEnglishName}";</c:otherwise>
			</c:choose>
			iKEP.popupOpen("${award.url}", {width:800, height:600}, awardName);
		</c:when>
	</c:choose>
	
	$jq(document).ready(function() { 
		iKEP.iFrameContentResize();
	});   
	
//-->
</script>

<c:if test="${award.awardPopup == 0}">
	<iframe id="iframeLink" class="ui-layout-center" width="100%" height="700" frameborder="0" scrolling="auto" src=""/>">
	</iframe>
</c:if>
