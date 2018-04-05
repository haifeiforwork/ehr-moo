<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">
(function($) {
	
	$(document).ready(function() {
		  
	});
})(jQuery);
</script>
<div >
	<ul style="text-align:right;padding-bottom:7px;">
			<div class="more"><a href="<c:url value='/collpack/kms/main/knowledgeShare.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more"></a></div>
		</ul>
		<div>
			<div style="padding-bottom:0px !important">
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
						<c:set var="titleLength" value="${fn:length(boardItem.title)}" />
						<c:set var="isMore" value="false"/>
						<c:if test="${titleLength > 18}">
							<c:set var="titleLength" value="18" />
							<c:set var="isMore" value="true" />
						</c:if>
						<td class="t_po1">
							<div class="ellipsis">
							<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readKeyInfoItemView.do?gubun=2&itemId=${boardItem.itemId}'/>', '${ikep4j:replaceQuot(boardItem.title)}', 800, 600);">${fn:substring(boardItem.title, 0, titleLength)}<c:if test="${isMore eq 'true'}">...</c:if></a>
							</div>
						</td>
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