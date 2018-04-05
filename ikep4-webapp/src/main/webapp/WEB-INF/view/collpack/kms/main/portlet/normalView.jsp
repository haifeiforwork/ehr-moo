<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">
(function($) {
	
	$(document).ready(function() {
		$("#divTab_b").tabs();
		  
	});
})(jQuery);
</script>
<div id="divTab_b" class="iKEP_tab_s">
		<ul>
			<li><a href="#ktabs-1">일반정보</a></li><div class="more"><a href="<c:url value='/collpack/kms/main/knowledgeShare.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more"></a></div>
			<li><a href="#ktabs-2">업무노하우</a></li><div class="more"><a href="<c:url value='/collpack/kms/main/knowledgeShare.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more"></a></div>
			<li><a href="#ktabs-3">원문 게시판</a></li><div class="more"><a href="<c:url value='/collpack/kms/main/knowledgeShare.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more"></a></div>
		</ul>
		<div>
			<div id="ktabs-1" style="padding-bottom:0px !important">
				<div class="tableList_1 mb15">
				<table summary="">
				<caption></caption>
				<tbody>							
					<c:choose>
					<c:when test="${empty normalItemList}">
						<tr>
							<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
						</tr>
					</c:when>
					<c:otherwise>
					<c:forEach var="boardItem" items="${normalItemList}"  varStatus="status">
					<tr style="background:none !important">
						<td class="t_po1">
							<div class="ellipsis">
							<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem.isKnowhow}&boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem.title)}', 800, 600);">${boardItem.title}</a>
							</div>
						</td>
						<td class="textCenter" width="60">
						<c:if test="${isSystemAdmin}">
						<span class="name">						
							<a href="#a" style="color:#555 !important" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName}</a>				
						</span>
						</c:if>
						</td>
						<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.registDate}"/></span></td>
					</tr>
					</c:forEach>
					</c:otherwise>
					</c:choose>																																		
				</tbody>
				</table>
				</div>
			</div>
			<div id="ktabs-2" style="padding-bottom:0px !important">
				<div class="tableList_1 mb15">
				<table summary="">
				<caption></caption>
				<tbody>							
					<c:choose>
					<c:when test="${empty knowHowItemList}">
						<tr>
							<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
						</tr>
					</c:when>
					<c:otherwise>
					<c:forEach var="boardItem" items="${knowHowItemList}"  varStatus="status">
					<tr style="background:none !important">
						
						<td class="t_po1"><div class="ellipsis">
						 <a href="#a"  onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem.isKnowhow}&boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem.title)}', 800, 600);">${boardItem.title}</a></div></td>
						<td class="textCenter" width="60"><span class="name">						
							<a href="#a" style="color:#555 !important" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName}</a>				
						</span></td>
						<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.registDate}"/></span></td>
					</tr>
					</c:forEach>
					</c:otherwise>
					</c:choose>																																		
				</tbody>
				</table>
				</div>
			</div>
			<div id="ktabs-3" style="padding-bottom:0px !important">
				<div class="tableList_1 mb15">
				<table summary="">
				<caption></caption>
				<tbody>							
					<c:choose>
					<c:when test="${empty originItemList}">
						<tr>
							<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
						</tr>
					</c:when>
					<c:otherwise>
					<c:forEach var="boardItem" items="${originItemList}"  varStatus="status">
					<tr style="background:none !important">
						<td class="t_po1"><div class="ellipsis">
						 <a href="#a"  onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem.isKnowhow}&boardId=${boardItem.boardId}&itemId=${boardItem.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem.title)}', 800, 600);">${boardItem.title}</a></div></td>
						<td class="textCenter" width="60"><span class="name">						
							<a href="#a" style="color:#555 !important" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName}</a>				
						</span></td>
						<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem.registDate}"/></span></td>
					</tr>
					</c:forEach>
					</c:otherwise>
					</c:choose>																																		
				</tbody>
				</table>
				</div>
			</div>
		</div>				
</div>