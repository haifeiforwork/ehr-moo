<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preUi" 			value="ui.collpack.forum.bestList" />

<c:if test="${pageCondition.pageIndex == 1 }">
<ul class="forum_list mb15" id="itemFrame">
</c:if>

<c:forEach var="item" items="${itemList}" varStatus="loop">
		<li>
			<p><c:if test="${item.bestItem == 1}">
					<img src='<c:url value="/base/images/icon/ic_best.gif"/>' alt="best" /> 
				</c:if>
				<a href="getView.do?itemId=${item.itemId }<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">${item.title }</a></p> <span class="forum_topBox_num">(${item.linereplyCount})</span>
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${item.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.registerName} ${item.jobTitleName}</c:when><c:otherwise>${item.userEnglishName} ${item.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.teamName}</c:when><c:otherwise>${item.teamEnglishName}</c:otherwise></c:choose></span>	
			<span class="forum_date"><ikep4j:timezone date="${item.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
		</li>		 
</c:forEach>


<c:if test="${empty(itemList)}">
	<li>
	<ikep4j:message key='message.collpack.forum.searchResult.nodata'/>
	</li>
</c:if>
			
<c:if test="${pageCondition.pageIndex == 1 }">
</ul>
</c:if>

<c:if test="${totalCount > 10 && pageCondition.pageIndex == 1}">
	<div class="blockButton_3" > 
		<a class="button_3" href="#a" onclick="listSearchMore('${totalCount}');" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
	</div>
</c:if>


