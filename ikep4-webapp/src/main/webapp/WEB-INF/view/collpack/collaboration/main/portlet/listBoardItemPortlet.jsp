<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main" />
<%-- 메시지 관련 Prefix 선언 End --%>


<c:if test="${board.listType=='3'}">

<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		iKEP.showGallery($("a.image-gallery-${board.boardId}"));
		iKEP.iFrameContentResize();   
	});
})(jQuery);
</script>	

	<div class="tableList_2 mb15">
		<!--subTitle_1 Start-->
		<div class="subTitle_1">
			<h3>${board.boardName}</h3>
			<div class="btn_more"><a href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do'/>?workspaceId=${board.workspaceId}&amp;boardId=${board.boardId}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>
		<!--//subTitle_1 End-->	
		
		<table summary="${board.boardName}">
		<tr><td class="textCenter">
			<c:if test="${!empty boardItem}">
				<div class="cafe_gal">
					<ul>
						<c:forEach var="boardItem" items="${boardItem}"  varStatus="status">
							<c:if test="${!empty boardItem.imageFileId}">
							<li><a class="image-gallery-${board.boardId}" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${boardItem.imageFileId}&thumbnailYn=Y" title="${boardItem.title}"><img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${boardItem.imageFileId}&thumbnailYn=Y" alt="image" width="90px" height="70px"/></a></li>
							</c:if>
							<c:if test="${empty boardItem.imageFileId}">
							<li><img src="<c:url value='/base/images/common/photo_90x70.gif'/>" alt="image" width="90px" height="70px"/></li>
							</c:if>								
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<c:if test="${empty boardItem}">
				<ikep4j:message pre="${prefix}" key="portlet.noDataRecentItem" />
			</c:if>	
		</td></tr>
		</table>		
	</div>
</c:if>


<c:if test="${board.listType!='3'}">

<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		iKEP.iFrameContentResize();  
	});
})(jQuery);
</script>

	<div class="tableList_2 mb15">
		<div class="subTitle_1">
			<h3>${board.boardName}</h3>
			<div class="btn_more"><a href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do'/>?workspaceId=${board.workspaceId}&amp;boardId=${board.boardId}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>

		<table summary="${board.boardName}">
			<caption></caption>
			<tbody>
				<c:if test="${!empty boardItem}">
				<c:forEach var="boardItem" items="${boardItem}"  varStatus="status">
				<tr>
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&docPopup=true', '${ikep4j:replaceQuot(boardItem.title)}', 800, 500);" title="${boardItem.title}">${boardItem.title} </a></th>
					<td class="textCenter" width="80"><span class="name">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">					
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName}</a>
				</c:when>
				<c:otherwise>
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerEnglishName}</a>				
				</c:otherwise>           
				</c:choose>									
					</span>
					</td>
					<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.updateDate}"/></span></td>
				</tr>
				</c:forEach>
				</c:if>
				<c:if test="${empty boardItem}">
				<tr>
					<td class="textCenter"><ikep4j:message pre="${prefix}" key="portlet.noDataBoardItem" /></td>
				</tr>				
				</c:if>																																						
			</tbody>
		</table>	
	</div>	
</c:if>
