<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.ideation.ideaListMore" />
<c:set var="preUiView" 			value="ui.collpack.ideation.view" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="requestUrl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<c:forEach var="idea" items="${ideaList}" varStatus="loop">
	
	<tr class="lineRow">
		<td> 
			<div class="summaryViewTitle">
				<c:if test="${isAdmin == true && fn:contains(requestUrl,'lastList.do')}">
					<input type="checkbox" name="delCheck" value="${idea.itemId}" style="float: left;"/>&nbsp;
				</c:if>
				<c:if test="${idea.bestItem == 1 }"><span class="cate_block_1"><span class="cate_tit_1"><ikep4j:message pre='${preUi}' key='best'/></span></span></c:if><c:if test="${idea.adoptItem == 1 }"><span class="cate_block_4"><span class="cate_tit_4"><ikep4j:message pre='${preUi}' key='adopt'/></span></span></c:if><!-- <c:if test="${idea.businessItem == 2}"><span class="cate_block_3"><span class="cate_tit_3"><ikep4j:message pre='${preUi}' key='adopt'/></span></span></c:if> --><c:if test="${idea.businessItem == 1}"><span class="cate_block_5"><span class="cate_tit_5"><ikep4j:message pre='${preUi}' key='adopt'/></span></span></c:if><a href="getView.do?itemId=${idea.itemId }<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">${idea.title} </a>
			</div>
			<div class="summaryViewInfo">
				<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${idea.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${idea.registerName} ${idea.jobTitleName}</c:when><c:otherwise>${idea.userEnglishName} ${idea.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
				<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${idea.teamName}</c:when><c:otherwise>${idea.teamEnglishName}</c:otherwise></c:choose></span>
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				<span><ikep4j:timezone date="${idea.registDate}" pattern="message.collpack.ideation.timezone.dateformat.type2" keyString="true"/></span>
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				<span><ikep4j:message pre='${preUi}' key='hit'/> <strong>${idea.hitCount}</strong></span>
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				<span><ikep4j:message pre='${preUi}' key='recomm'/> <strong>${idea.recommendCount}</strong></span>
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				<span><ikep4j:message pre='${preUi}' key='lineReply'/> <strong>${idea.linereplyCount}</strong></span>
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
				<span><ikep4j:message pre='${preUi}' key='adopt'/> <strong>${idea.adoptCount}</strong></span>
		    </div>
			<div class="summaryViewCon"><a href="getView.do?itemId=${idea.itemId }<c:if test="${!empty(param.docIframe) }">&amp;docPopup=${param.docIframe}&amp;docIframe=${param.docIframe}</c:if>">
				<ikep4j:extractText text="${idea.contents}" length="400"/></a>
			</div>
			<div class="summaryViewTag"><span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='tag'/></span></span> 
				<c:forEach var="tag" items="${idea.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearch('${tag.tagId}', '${tag.tagItemType}', '${tag.tagItemSubType}');$jq('#orderFrame').hide();return false;">${tag.tagName}</a></c:forEach>
			</div>
		</td>
	</tr>	
			
</c:forEach>
<c:if test="${fn:length(ideaList) == 0}">
	<tr><td>
		<ikep4j:message key='message.collpack.ideation.noData'/>
	</td></tr>
</c:if>
