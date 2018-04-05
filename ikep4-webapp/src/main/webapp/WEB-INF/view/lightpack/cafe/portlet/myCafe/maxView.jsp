<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

<script type="text/javascript">
<!--   
(function($) {	
	getCommentDetailView_${portletConfigId} = function(itemUrl) {
		
		itemUrl = iKEP.getContextRoot() + itemUrl;
		$("#frame_${portletConfigId}").ajaxLoadStart();
		$jq("#frame_${portletConfigId}").attr("src", itemUrl).bind("load", function(){ $("#frame_${portletConfigId}").ajaxLoadComplete(); }); 
		
	};	
})(jQuery);  
//-->
</script>
<div id="${portletConfigId}" class="tableList_1">
	<c:choose>
	    <c:when test="${empty myCafeList}">
			<div class="pTitle_1">
				<ikep4j:message pre='${portletList}' key='list.empty' />
			</div>			        
	    </c:when>
	    <c:otherwise>
			<c:forEach var="cafe" items="${myCafeList}">
				<div class="pTitle_1">
					<a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}"><div class="ellipsis">${cafe.cafeName}</div></a>
				</div>				
				<c:if test="${!empty cafe.boardItemList}">					
					<table summary="${cafe.cafeName}">
						<caption></caption>
						<tbody>						
							<c:forEach var="item" items="${cafe.boardItemList}">							
								<tr>
									<th width="*" scope="row">
										<div class="ellipsis"><a href="#a" onclick="showDatail_${portletConfigId}('/lightpack/cafe/board/boardItem/readBoardItemLinkView.do?boardId=${item.boardId}&itemId=${item.itemId}&docPopup=true','${item.itemId}','','${item.title}')">
										${item.title}</a></div>
									</th>	
									<td width="80"><div class="ellipsis"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.registerId}', 'bottom')" title="${item.registerName}">${item.registerName}</a></div></td>
									<td width="90" class="textRight"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${item.registDate}"/></span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:forEach>
		</c:otherwise>	
	</c:choose>
</div>