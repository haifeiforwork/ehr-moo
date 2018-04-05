<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preUi" 	value="ui.collpack.forum.itemListMore" />

<c:forEach var="item" items="${itemList}" varStatus="loop">
	
	<li class="lineRow <c:if test="${itemId ==  item.itemId}">selected</c:if> item_${item.itemId }">
		<div class="forum_list_photo"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose>"><img src="<c:url value='${item.profilePicturePath}'/>" alt="profileImage" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>	
		<p><a href="#a" onclick="getViewItem('${item.itemId }');">
			<c:if test="${item.bestItem == 1}"><img src='<c:url value="/base/images/icon/ic_best.gif"/>' alt="best" /></c:if>
			${item.title}
			</a>
		</p>
		<div class="forum_list_info">
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>
			<span class="forum_date"><ikep4j:timezone date="${item.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='hit'/>  <strong id="itemHitCount_${item.itemId }">${item.hitCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='agree'/>  <strong id="itemAgreementCount_${item.itemId }">${item.agreementCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='opp'/>  <strong id="itemOppositionCount_${item.itemId }">${item.oppositionCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='favorite'/>  <strong id="itemFavoriteCount_${item.itemId }">${item.favoriteCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='line'/>  <strong id="itemLinereplyCount_${item.itemId }">${item.linereplyCount}</strong></span>
		</div>																	
	</li>		
	
</c:forEach>

<c:if test="${empty(itemList)}">
	<li>
		<ikep4j:message key='message.collpack.forum.noData.item'/>
	</li>
</c:if>