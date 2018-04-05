<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>

<script type="text/javascript">
<!-- 
(function($) {	
	showDatail_${portletConfigId} = function(url, itemId, subId, title) {
		//url = iKEP.getContextRoot() + url;
		//iKEP.popupOpen(url, {width:800, height:600});
		url = iKEP.getContextRoot() + url + itemId;
		var width = $(window).width()*0.8;
		var height = $(window).height()*0.8;
		
		var options = {
			windowTitle : title,
			documentTitle : title,
			width:600, height:500, modal:true
		};
		
		iKEP.portletPopupOpen(url, options);

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
									<td width="55"><div class="ellipsis"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.registerId}', 'bottom')" title="${item.registerName}">${item.registerName}</a></div></td>
									<td width="30" class="textRight"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${item.registDate}"/></span></td>
								</tr>
							</c:forEach>							
						</tbody>
					</table>
				</c:if>
			</c:forEach>
		</c:otherwise>	
	</c:choose>	
</div>