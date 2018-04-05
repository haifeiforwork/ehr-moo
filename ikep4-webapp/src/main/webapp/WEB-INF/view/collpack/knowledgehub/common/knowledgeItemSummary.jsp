<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgehub.common.message</c:set>
<c:set var="knowledgeMapMessagePrefix">ui.collpack.knowledgehub.knowledgeMapMain</c:set>

<table summary="<ikep4j:message pre="${messagePrefix}" key="summary"/>">
	<caption></caption>
	<tbody>
		<c:forEach var="item" items="${knowledgeList}">
		<tr>
			<td>
				<div class="tag_summaryViewTitle">
					<span class="cate_block_3"><span class="cate_tit_3">${item.itemTypeDisplayName}</span></span><a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${item.tagItemUrl}"/>');">${item.tagItemName}</a>
				</div>
				<div class="summaryViewInfo">
					<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.registerId}');">${item.userName}&nbsp;${item.jobTitleName}</a></span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt=""/>
					<span class="summaryViewInfo_name">${item.teamName}</span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt=""/>
					<span><ikep4j:timezone date="${item.registDate}" pattern="yyyy.MM.dd"/></span>
					<c:if test="${isScore}">
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt=""/>
					<span><ikep4j:message pre="${knowledgeMapMessagePrefix}" key="message.score"/>&nbsp;<strong><fmt:formatNumber value="${item.score}"/></strong></span>
					</c:if>
				</div>
				<div class="summaryViewCon"><a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${item.tagItemUrl}"/>');">${item.tagItemContents}</a></div>
				<div class="summaryViewTag"><span class="ic_tag"><span><ikep4j:message pre="${messagePrefix}" key="tag"/></span></span>
				<c:forEach var="tagItem" items="${item.tagList}" varStatus="tagItemStatus">
					<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}','${tagItem.tagItemType}','${tagItem.tagItemSubType}');">${tagItem.tagName}</a><c:if test="${!tagItemStatus.last}">,</c:if>
				</c:forEach>
				</div>
			</td>
		</tr>
		</c:forEach>
		<c:if test="${0 eq fn:length(knowledgeList)}">
			<tr>
				<td class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></td>
			</tr>
		</c:if>
	</tbody>
</table>
		