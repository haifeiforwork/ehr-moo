<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.expertnetwork.common.message</c:set>
<c:set var="expertNetworkMessagePrefix">ui.collpack.expertnetwork.expertNetworkMain</c:set>

<!--pageTitle Start-->
<div id="pageTitle_main">
	<h2><ikep4j:message pre="${expertNetworkMessagePrefix}" key="find.title"/></h2>
	<div class="conSearch">
		<input name="findExpertSearchTag" onkeypress="findExpertkeypress(event);" title="<ikep4j:message pre="${expertNetworkMessagePrefix}" key="find.tooltip"/>" type="text" style="width:239px"/>
		<a class="sel_btn" href="#a" onclick="findExpert();"></a>
		<div class="clear"></div>
	</div>
	<div class="search_tag"><img src="<c:url value='/base/images/icon/ic_favorite_2.gif'/>" alt=""/>
	<c:forEach var="tagItem" items="${tagListPopular}" varStatus="tagItemStatus">
		<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tagItem.tagName}','${tagItem.tagItemType}','${tagItem.tagItemSubType}');">${tagItem.tagName}</a><c:if test="${!tagItemStatus.last}">,</c:if>
	</c:forEach>
	</div>
</div>
<!--//pageTitle End-->

<script type="text/javascript">
//<![CDATA[
var findExpert = function() {
	var tag = $jq("input[name=findExpertSearchTag]").val();

	if ("" != tag) {
		iKEP.tagging.tagSearchByName(tag);
	}
};

var findExpertkeypress = function(event) {
	if (event.which == '13') {
		findExpert();
	}
};
//]]>
</script>