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
		$jq('.knowhowDivH').hide();
		$jq('.normalTabH').css("background-color","#EAEAEA");
	});
	
	selectTabH = function(tab) {
		if(tab == "0"){ 
			$jq('.normalDivH').show();
			$jq('.knowhowDivH').hide();
			$jq('.normalTabH').css("background-color","#EAEAEA");
			$jq('.knowhowTabH').css("background-color","#FFFFFF");
		}else{
			$jq('.knowhowDivH').show();
			$jq('.normalDivH').hide();
			$jq('.knowhowTabH').css("background-color","#EAEAEA");
			$jq('.normalTabH').css("background-color","#FFFFFF");
		}
	}
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
	
	<!-- <table>
		<tr>
			<td onclick="selectTabH(0);" class="normalTabH" width="30%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">일반정보</span></td>
			<td onclick="selectTabH(1);" class="knowhowTabH" width="30%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">업무노하우</span></td>
			<td width="*" style="border-bottom:#d4d4d4 1px solid;"></td>
		</tr>
	</table> -->
	<!--//subTitle_1 End-->		
	<table summary="${kmsPortletLayout.kmsPortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty boardItem1}">
			<c:forEach var="boardItem1" items="${boardItem1}"  varStatus="status">
			<tr class="normalDivH">
				<%-- <c:if test="${cokmrole}"> --%>
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="showKmsFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem1.isKnowhow}&boardId=${boardItem1.boardId}&itemId=${boardItem1.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem1.title)}', 800, 600);">${boardItem1.title}</a></th>
				<%-- </c:if>
				<c:if test="${!cokmrole}">
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem1.isKnowhow}&boardId=${boardItem1.boardId}&itemId=${boardItem1.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem1.title)}', 800, 600);">${boardItem1.title}</a></th>
				</c:if> --%>
				<c:choose>
					<c:when test="${boardItem1.isKnowhow == 0 || boardItem1.isKnowhow == 3}">
						<td class="textCenter" width="60"><span class="name"><a href="#a">
							<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem1.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem1.registerName}</a>
							</c:when>
							<c:otherwise>
								<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem1.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem1.registerEnglishName}</a>				
							</c:otherwise>
							</c:choose>					
						</a></span></td>
					</c:when>
					<c:otherwise>
						<c:if test="${isSystemAdmin}">
							<td class="textCenter" width="60"><span class="name"><a href="#a">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem1.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem1.registerName}</a>
								</c:when>
								<c:otherwise>
									<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem1.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem1.registerEnglishName}</a>				
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
					<c:when test="${boardItem1.isKnowhow==0}">
						업무노하우
					</c:when>
					<c:when test="${boardItem1.isKnowhow==1}">
						일반정보
					</c:when>
					<c:when test="${boardItem1.isKnowhow==3}">
						원문게시판
					</c:when>
				</c:choose>
				</span></td>
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem1.registDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty boardItem1}">
				<tr class="normalDivH">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>		
			<%-- <c:if test="${!empty boardItem2}">
			<c:forEach var="boardItem2" items="${boardItem2}"  varStatus="status">
			<tr class="knowhowDivH">
				<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem2.isKnowhow}&boardId=${boardItem2.boardId}&itemId=${boardItem2.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem2.title)}', 800, 600);">${boardItem2.title}</a></th>
				<td class="textCenter" width="60"><span class="name"><a href="#a">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem2.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem2.registerName}</a>
					</c:when>
					<c:otherwise>
						<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem2.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem2.registerEnglishName}</a>				
					</c:otherwise>
					</c:choose>					
				</a></span></td>
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem2.registDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty boardItem2}">
				<tr class="knowhowDivH">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>	 --%>																																		
		</tbody>
	</table>	
</div>	
</div>				
<div class="kmboxline"></div>