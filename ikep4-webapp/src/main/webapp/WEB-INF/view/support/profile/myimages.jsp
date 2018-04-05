<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--   
(function($){	
	$(document).ready(function() {
		iKEP.showGallery($("a.image-gallery"));
	});  

})(jQuery);
//-->
</script>
						
<h3><ikep4j:message pre='${preProfileMain}' key='images.title'/></h3>
<div class="more"><a onclick="goMyImages()" href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>	
	<c:choose>
	<c:when test="${empty searchResult.entity}">
		<p align="center"><ikep4j:message pre='${preMsgProfile}' key='nodata'/></p>
	</c:when>
	<c:otherwise>

	<div class="MyImage_topList" id="slider">
		<c:forEach var="favorite" items="${searchResult.entity}" varStatus="status">
		<c:if test="${status.index <= 7 }" >
		<div class="MyImage_topPhoto_2">
			<span><a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${favorite.fileId}" title="${ikep4j:replaceQuot(favorite.title)}"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${favorite.fileId}&ampthumbnailYn=Y" alt="image" /></a></span>
			<div class="MyImage_topPhoto_info_2">
				<div class="MyImage_topPhoto_title"><a href="#a" onclick="showDatailForProfile('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')">${ikep4j:replaceQuot(favorite.title)}</a></div>
			</div>
		</div>
		</c:if>
		</c:forEach>										
	</div>

	</c:otherwise>
	</c:choose>	
	<div class="clear"></div>					

