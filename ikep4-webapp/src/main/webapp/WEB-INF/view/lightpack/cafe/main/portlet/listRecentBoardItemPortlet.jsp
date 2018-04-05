<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
				
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		iKEP.iFrameContentResize();  
	});
})(jQuery);
</script>

	<div class="tableList_2 mb15">

		<!--subTitle_1 Start-->
		<div class="subTitle_1">
			<h3>${cafePortletLayout.cafePortlet.portletName}</h3>
			<div class="btn_more"><a href="<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do'/>?searchConditionString=isAll^yes~cafeId^${cafe.cafeId}"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>
		<!--//subTitle_1 End-->	
		<table summary="${cafePortletLayout.cafePortlet.portletName}">
			<caption>
			</caption>
			<tbody>
				<c:if test="${!empty boardItemList}">
				<c:forEach var="boardItem" items="${boardItemList}"  varStatus="status">
				<tr>
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&docPopup=true', '${ikep4j:replaceQuot(boardItem.title)}', 800, 500);" title="${boardItem.title}">${boardItem.title}</a></th>
					<td class="textCenter" width="60"><span class="name">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">					
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName}</a>
				</c:when>
				<c:otherwise>
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerEnglishName}</a>				
				</c:otherwise>           
				</c:choose>	
					</span></td>
					<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.updateDate}"/></span></td>
				</tr>
				</c:forEach>
				</c:if>
				<c:if test="${empty boardItemList}">
				<tr>
					<td class="textCenter" ><ikep4j:message pre="${prefix}" key="list.empty" />	</td>
				</tr>					
				</c:if>		
			</tbody>
		</table>	
	</div>	

