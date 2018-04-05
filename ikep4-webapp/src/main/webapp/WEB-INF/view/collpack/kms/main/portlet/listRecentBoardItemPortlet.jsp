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
		$jq('.knowhowDivR').hide();
		$jq('.originDivR').hide();
		$jq('.normalTabR').css("background-color","#EAEAEA");
	});
	
	selectTabR = function(tab) {
		if(tab == "0"){ 
			$jq('.normalDivR').show();
			$jq('.knowhowDivR').hide();
			$jq('.originDivR').hide();
			$jq('.normalTabR').css("background-color","#EAEAEA");
			$jq('.knowhowTabR').css("background-color","#FFFFFF");
			$jq('.originTabR').css("background-color","#FFFFFF");
			$jq('#moreNormal').show();
			$jq('#moreKnowhow').hide();
			$jq('#moreOrigin').hide();
		}else if(tab == "1"){
			$jq('.knowhowDivR').show();
			$jq('.normalDivR').hide();
			$jq('.originDivR').hide();
			$jq('.knowhowTabR').css("background-color","#EAEAEA");
			$jq('.normalTabR').css("background-color","#FFFFFF");
			$jq('.originTabR').css("background-color","#FFFFFF");
			$jq('#moreKnowhow').show();
			$jq('#moreNormal').hide();
			$jq('#moreOrigin').hide();
		}else{
			$jq('.originDivR').show();
			$jq('.normalDivR').hide();
			$jq('.knowhowDivR').hide();
			$jq('.originTabR').css("background-color","#EAEAEA");
			$jq('.normalTabR').css("background-color","#FFFFFF");
			$jq('.knowhowTabR').css("background-color","#FFFFFF");
			$jq('#moreOrigin').show();
			$jq('#moreNormal').hide();
			$jq('#moreKnowhow').hide();
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
		<c:choose>
		<c:when test="${kmsPortletLayout.kmsPortlet.portletId eq 'KMS_PORTLET_01'}">
			<c:set var="isKnowHow" value="1"/>
		</c:when>
		<c:otherwise>
			<c:set var="isKnowHow" value="0"/>
		</c:otherwise>
		</c:choose>
		<div id="moreNormal" class="btn_more"><a href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=1'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		<div id="moreKnowhow" class="btn_more" style="display:none;"><a href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=0'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		<div id="moreOrigin" class="btn_more" style="display:none;"><a href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=3'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
	</div>
	
	<table>
		<tr>
			<td onclick="selectTabR(0);" class="normalTabR" width="30%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">일반정보</span></td>
			<td onclick="selectTabR(1);" class="knowhowTabR" width="30%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">업무노하우</span></td>
			<td onclick="selectTabR(2);" class="originTabR" width="30%" style="border-top:#d4d4d4 1px solid;border-bottom:#d4d4d4 1px solid;border-left:#d4d4d4 1px solid;border-right:#d4d4d4 1px solid;cursor:pointer;" align="center"><span style="font-weight:bold;">원문 게시판</span></td>
			<td width="*" style="border-bottom:#d4d4d4 1px solid;"></td>
		</tr>
	</table>
	<!--//subTitle_1 End-->		
	<table summary="${kmsPortletLayout.kmsPortlet.portletName}">
		<caption></caption>
		<tbody>
			<c:if test="${!empty boardItem1}">
			<c:forEach var="boardItem1" items="${boardItem1}"  varStatus="status">
			<tr class="normalDivR">
				<%-- <c:if test="${cokmrole}"> --%>
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="showKmsFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem1.isKnowhow}&boardId=${boardItem1.boardId}&itemId=${boardItem1.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem1.title)}', 800, 600);">${boardItem1.title}</a></th>
				<%-- </c:if>
				<c:if test="${!cokmrole}">
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem1.isKnowhow}&boardId=${boardItem1.boardId}&itemId=${boardItem1.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem1.title)}', 800, 600);">${boardItem1.title}</a></th>
				</c:if> --%>
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
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem1.registDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty boardItem1}">
				<tr class="normalDivR">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>		
			<c:if test="${!empty boardItem2}">
			<c:forEach var="boardItem2" items="${boardItem2}"  varStatus="status">
			<tr class="knowhowDivR">
				<%-- <c:if test="${cokmrole}"> --%>
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="showKmsFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem2.isKnowhow}&boardId=${boardItem2.boardId}&itemId=${boardItem2.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem2.title)}', 800, 600);">${boardItem2.title}</a></th>
				<%-- </c:if>
				<c:if test="${!cokmrole}">
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem2.isKnowhow}&boardId=${boardItem2.boardId}&itemId=${boardItem2.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem2.title)}', 800, 600);">${boardItem2.title}</a></th>
				</c:if> --%>
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
				<tr class="knowhowDivR">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>		
			<c:if test="${!empty boardItem3}">
			<c:forEach var="boardItem3" items="${boardItem3}"  varStatus="status">
			<tr class="originDivR">
				<%-- <c:if test="${cokmrole}"> --%>
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="showKmsFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem3.isKnowhow}&boardId=${boardItem3.boardId}&itemId=${boardItem3.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem3.title)}', 800, 600);">${boardItem3.title}</a></th>
				<%-- </c:if>
				<c:if test="${!cokmrole}">
					<th class="ellipsis" width="*" scope="row"><a href="#a" onclick="parent.parent.showMainFrameDialog('<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?isKnowhow=${boardItem3.isKnowhow}&boardId=${boardItem3.boardId}&itemId=${boardItem3.itemId}'/>&popupYn=true', '${ikep4j:replaceQuot(boardItem3.title)}', 800, 600);">${boardItem3.title}</a></th>
				</c:if> --%>
				<td class="textCenter" width="60"><span class="name"><a href="#a">
					<c:choose>
					<c:when test="${user.localeCode == portal.defaultLocaleCode}">
						<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem3.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem3.registerName}</a>
					</c:when>
					<c:otherwise>
						<a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem3.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem3.registerEnglishName}</a>				
					</c:otherwise>
					</c:choose>					
				</a></span></td>
				<td class="textRight" width="60"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd" date="${boardItem3.registDate}"/></span></td>
			</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty boardItem3}">
				<tr class="originDivR">
					<td class="textCenter" colspan="2"><ikep4j:message key="message.collpack.kms.main.portlet.noData" />	</td>
				</tr>			
			</c:if>																																		
		</tbody>
	</table>	
</div>	
</div>				
<div class="kmboxline"></div>