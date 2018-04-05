<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="commonPrefix">ui.collpack.knowledgehub.common</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgehub.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgehub.common.button</c:set>
<c:set var="messagePrefix">ui.collpack.knowledgehub.common.message</c:set>
<c:set var="knowledgeMapMessagePrefix">ui.collpack.knowledgehub.knowledgeMapMain</c:set>
<c:set var="popularListPrefix">ui.collpack.knowledgehub.knowledgePopularListView</c:set>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<!--expert_topList Start-->
<div id="workContents">
<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle_main">
	<h2><ikep4j:message pre="${knowledgeMapMessagePrefix}" key="find.title"/></h2>
	<div class="conSearch">
		<input name="mainKnowledgeSearchTag" onkeypress="findKnowledgekeypress(event);" title="<ikep4j:message pre="${knowledgeMapMessagePrefix}" key="find.tooltip"/>" type="text" style="width:239px"/>
		<a class="sel_btn" href="#a" onclick="findKnowledge();"></a>
		<div class="clear"></div>
	</div>
</div>
<!--//pageTitle End-->

<div class="knowledgeMap_l">
	<div class="tag_cloud_c">
	<c:forEach var="item" items="${tagListCloud}">
		<span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}', '${usableModules}');" class="tag${item.displayStep}">${item.tagName}</a></span>
	</c:forEach>
	</div>

	<!--subTitle_2 Start-->
	<div class="subTitle_2">
		<h3><ikep4j:message pre="${knowledgeMapMessagePrefix}" key="message.popularKnowledge"/></h3>
		<div class="btn_more"><a href="#a" onclick="popularMoreClick();"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more"/></a></div>
	</div>
	<!--//subTitle_2 End-->

	<!--blockListTable Start-->
	<div class="blockListTable tag_summaryView">
		<%@ include file="/WEB-INF/view/collpack/knowledgehub/common/knowledgeItemSummary.jsp"%>
	</div>
	<!--//blockListTable End-->
</div>

<div class="knowledgeMap_r">
	<div class="knowledgeMap_rBox">
		<div class="subTitle_2">
			<h3><ikep4j:message pre="${knowledgeMapMessagePrefix}" key="message.recommendKnowledge"/></h3>
			<div class="btn_more"><a href="#a" onclick="recommendMoreClick();"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
		</div>
		<ul class="recom_knowledge">
		<c:forEach var="item" items="${knowledgeRecommendList}">
			<li><div class="ellipsis"><span class="cate_block_3"><span class="cate_tit_3">${item.itemTypeDisplayName}</span></span><a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${item.tagItemUrl}"/>');">${item.tagItemName}</a></div></li>
		</c:forEach>
		<c:if test="${0 eq fn:length(knowledgeRecommendList)}">
			<li class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></li>
		</c:if>
		</ul>
		
		<!--
		<div class="subTitle_2 mt10">
			<h3><ikep4j:message pre="${knowledgeMapMessagePrefix}" key="message.recommendTag"/></h3>
		</div>
		<div class="colorTag">
		<c:forEach var="item" items="${knowledgeRecommendTagList}" varStatus="tagItemStatus">
			<a href="#a" onclick="iKEP.tagging.tagSearch('${item.recommendTagId}');" style="color:#3366cc;"><span>${item.tagName}</span></a><c:if test="${!tagItemStatus.last}">,</c:if>
		</c:forEach>
		<c:if test="${0 eq fn:length(knowledgeRecommendTagList)}">
			<div class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></div>
		</c:if>
		</div>
		 -->
	</div>
	
	<!-- 
	<div class="popularTag colorTag">
		<div class="subTitle_2 noline mb10">
			<h3><ikep4j:message pre="${knowledgeMapMessagePrefix}" key="message.popularTag"/></h3>
		</div>
		<ul>
		<c:forEach var="item" items="${tagListPopular}" varStatus="itemStatus">
			<c:if test="${4 gt itemStatus.count}">
				<li class="tag_No0${itemStatus.count}"><strong><span class="none">No.${itemStatus.count}</span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}', '${usableModules}');">${item.tagName}</a></strong></li>
			</c:if>
			<c:if test="${3 lt itemStatus.count and itemStatus.count < 10}">
				<li class="tag_No0${itemStatus.count}"><span class="none">No.${itemStatus.count}</span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}', '${usableModules}');">${item.tagName}</a></li>
			</c:if>
			<c:if test="${10 eq itemStatus.count}">
				<li class="tag_No10"><span class="none">No.10</span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}', '${usableModules}');">${item.tagName}</a></li>
			</c:if>
		</c:forEach>
		<c:if test="${0 eq fn:length(tagListPopular)}">
			<li class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></li>
		</c:if>
		</ul>
	</div>
	 -->

</div>

</div>
</div>
<!--//expert_topList End-->


<script type="text/javascript">
//<![CDATA[

var popularMoreClick = function() {
	$jq("#menuPopularityKnowledge").click();
};

var recommendMoreClick = function() {
	$jq("#menuRecommendKnowledge").click();
};

var findKnowledge = function() {
	var tag = $jq("input[name=mainKnowledgeSearchTag]").val();

	if ("" != tag) {
		iKEP.tagging.tagSearchByName(tag, '${usableModules}');
	}
};

var findKnowledgekeypress = function(event) {
	if (event.which == '13') {
		findKnowledge();
	}
};

//]]>
</script>
