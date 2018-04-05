<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript"> 
(function($) {
	$jq(document).ready(function() {
		iKEP.iFrameContentResize();		
		
		$("#moreButton").click(function() { 
			parent.location.href = "<c:url value='/collpack/qna/listUrgentQna.do'/>";   
		});
		
		
	});
})(jQuery);
</script>
<div class="kmbox">
	<div class="tableList_3 mb15">


	<!--subTitle_1 Start-->
	<div class="subTitle_1a">
		<h3>
			${kmsPortletLayout.kmsPortlet.portletName}
		</h3>
		<div class="btn_more"><a href="<c:url value='/collpack/kms/board/boardItem/listLatestItemView.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
	</div>
	<!--//subTitle_1 End-->		
	<table summary="${kmsPortletLayout.kmsPortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty newList}">
			<c:forEach var="qna" items="${newList}"  varStatus="status">
			<tr>
				<th class="ellipsis" width="*" scope="row">					  
					<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/qna/getQna.do?qnaId=${qna.qnaGroupId}&amp;listType=Main'/>&docPopup=true', '${ikep4j:replaceQuot(announceItem.title)}', 800, 600);" title="${qna.title}">${qna.title}</a>						
				</th>
				<td class="textCenter" width="60"><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${qna.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${qna.registerName}</a></span></td>
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${qna.updateDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty newList}">
				<tr>
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>			
																																							
		</tbody>
	</table>
	
</div>	
</div>							
<div class="kmboxline"></div>
