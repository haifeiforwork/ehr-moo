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
	</div>
	<!--//subTitle_1 End-->		
	<table summary="${kmsPortletLayout.kmsPortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty markItem}">
			<c:forEach var="markItem" items="${markItem}"  varStatus="status">
			<tr>
				
				<%-- <c:if test="${cokmrole}"> --%>
					<th class="ellipsis" width="*" scope="row">
					<c:if test="${!empty markItem.color}">
						<img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" />
					</c:if>
					<a href="#a" onclick="showKmsFrameDialog('<c:url value='/collpack/kms/board/boardItem/readExcellenceItemView.do?isKnowhow=${markItem.isKnowhow}&boardId=${markItem.boardId}&itemId=${markItem.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(markItem.title)}', 800, 600);" >
					${markItem.title}
					</a></th>
				<%-- </c:if>
				<c:if test="${!cokmrole}">
					<th class="ellipsis" width="*" scope="row">
					<c:if test="${!empty markItem.color}">
						<img src="<c:url value='/base/images/icon/ic_recommend.gif'/>" />
					</c:if>
					<a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readExcellenceItemView.do?isKnowhow=${markItem.isKnowhow}&boardId=${markItem.boardId}&itemId=${markItem.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(markItem.title)}', 800, 600);" >
					${markItem.title}
					</a></th>
				</c:if> --%>
				<c:choose>
					<c:when test="${markItem.isKnowhow == 0 || markItem.isKnowhow == 3}">
						<td class="textCenter" width="60"><span class="name"><a href="#a">
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${markItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${markItem.registerName}</a>
							</c:when>
							<c:otherwise>
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${markItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${markItem.registerEnglishName}</a>								
							</c:otherwise>
							</c:choose>					
						</a></span></td>
					</c:when>
					<c:otherwise>
						<c:if test="${isSystemAdmin}">
							<td class="textCenter" width="60"><span class="name"><a href="#a">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${markItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${markItem.registerName}</a>
								</c:when>
								<c:otherwise>
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${markItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${markItem.registerEnglishName}</a>								
								</c:otherwise>
								</c:choose>					
							</a></span></td>
						</c:if>
						<c:if test="${!isSystemAdmin}">
							<td class="textCenter" width="60"></td>
						</c:if>
					</c:otherwise>
				</c:choose>
				<td width="60"><span class="name">
				<c:choose>
					<c:when test="${markItem.isKnowhow==0}">
						업무노하우
					</c:when>
					<c:when test="${markItem.isKnowhow==1}">
						일반정보
					</c:when>
					<c:when test="${markItem.isKnowhow==3}">
						원문게시판
					</c:when>
				</c:choose>
				</span></td>
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${markItem.registDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty markItem}">
				<tr>
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>			
																																							
		</tbody>
	</table>
	
</div>	
</div>				
<div class="kmboxline"></div>