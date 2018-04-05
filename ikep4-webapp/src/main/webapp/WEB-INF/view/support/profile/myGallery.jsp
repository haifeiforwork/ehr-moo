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
		//$("a.image-gallery").ikepGallery();
		parent.$jq("#contentIframe").contents().find("a.image-gallery").ikepGallery();   
	});  

})(jQuery);
//-->
</script>
<!--expert_topList Start-->						
	<h3><ikep4j:message pre='${preProfileMain}' key='photo.title'/></h3>
	<div class="more"><a onclick="goMyGallery()" href="#a"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
<table summary="<ikep4j:message pre="${preList}" key="summary" />" style="table-layout:fixed;width:100%;">   
	<caption></caption> 
	<tbody>
	<tr class="bgWhite">
	<td>
	<c:choose>
		<c:when test="${empty searchResult.entity}">
			<p align="center"><ikep4j:message pre='${preMsgProfile}' key='nodata'/></p>
		</c:when>
		<c:otherwise>
			<div id="slider" class="MyImage_topList">
			<c:forEach var="gallery" items="${searchResult.entity}" varStatus="status">

				<div class="MyImage_topPhoto_2" style="white-space:nowrap;">
					<span><a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${gallery.fileId}" title="${ikep4j:replaceQuot(gallery.title)}"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${gallery.fileId}&amp;thumbnailYn=Y" alt="image" /></a></span>
					<div class="MyImage_topPhoto_info_2">
						<div class="galleryViewTitle1 ellipsis"><a href="<c:url value='/lightpack/gallery/boardItem/readBoardItemView.do?targetUserId=${targetUserId}&amp;pageIndex=${searchResult.pageIndex}&amp;itemId=${gallery.targetId}&amp;popupYn=false'/>">${ikep4j:replaceQuot(gallery.title)}</a></div>
					</div>
				</div>
				<%--c:if test="${(status.index+1)%4==0}">
				<div class="clear"></div>
				</c:if--%>
			</c:forEach>										
			</div>
		</c:otherwise>
	</c:choose>	
	<div class="clear"></div>	
	</td>
	</tr> 	
	</tbody>
</table> 
<!--//expert_topList End-->


