<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
					

														<c:forEach var="boardItem" items="${boardItemList}">
															
															<ul>
																<li class="cafe_text_2">
																	<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&docPopup=true', '${board.boardName}', 800, 500);" title="${boardItem.title}">
																	<div class="ellipsis">${boardItem.title}</div></a></li>
																<li>
																	<span class="corporateViewInfo pr0">
																		<span><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom')">${boardItem.registerName}</a></span>
																		<span><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.registDate}"/></span>
																	</span>								
																</li>
															</ul>
														
														</c:forEach>				      
												 
												 					
					
<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	$jq(document).ready(function() {
		
		$jq("#recordCount_${searchCondition.cafeId}").val("${searchCondition.recordCount}");
		$jq("#currentCount_${searchCondition.cafeId}").val("${searchCondition.currentCount}");
	
	});
	
	
})(jQuery);  

//-->
</script>
					