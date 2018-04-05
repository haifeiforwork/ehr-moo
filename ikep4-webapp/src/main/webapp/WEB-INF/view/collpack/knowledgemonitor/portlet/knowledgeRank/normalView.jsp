<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="knowledgeRankingInfoPrefix">ui.collpack.knowledgemonitor.knowledgeRankingInfo</c:set>

<div class="tableList_1" id="${portletConfigId}">
	<div class="blockListTable msgTable" style="width:100% !important; min-width:10px !important;">
		<table summary="<ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.summary"/>">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="8%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col01"/></th>
					<th scope="col" width="20%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col02"/></th>
					<th scope="col" width="52%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col03"/></th>
					<th class="tdLast" scope="col" width="20%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col04"/></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="item" items="${knowledgeMonitorCviPointList}">
				<tr>
					<td class="textCenter">${item.rankNumber}</td>
					<td class="textCenter"><div class="ellipsis" title="${item.moduleName}">${item.moduleName}</div></td>
					<td class="textLeft"><div class="ellipsis"><a href="#a" onclick="itemPopup('${item.itemUrl}');" title="${item.title}">${item.title}</a></div></td>
					<td class="textCenter tdLast"><div class="ellipsis"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.registerId}');" title="${item.userName}">${item.userName}</a></div></td>
				</tr>
			</c:forEach>
			<c:if test="${0 eq fn:length(knowledgeMonitorCviPointList)}">
				<tr>
					<td colspan="4" class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></td>
				</tr>
			</c:if>
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript">
//<![CDATA[

var itemPopup = function(url) {
	iKEP.popupOpen(iKEP.getContextRoot() + url, {width:800, height:500}, "itemPopup");
};

//]]>
</script>
