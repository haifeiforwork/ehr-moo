<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="buttonPrefix">ui.collpack.knowledgehub.common.button</c:set>
<c:set var="recommendListPrefix">ui.collpack.knowledgehub.knowledgeRecommendListView</c:set>

<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${recommendListPrefix}" key="subTitle"/></h2>
</div>
<!--//pageTitle End-->

<!--blockListTable Start-->
<div class="blockListTable summaryView">
	<%@ include file="/WEB-INF/view//collpack/knowledgehub//common/knowledgeItemSummary.jsp"%>
</div>
<!--//blockListTable End-->

</div>

<script type="text/javascript">
//<![CDATA[

$jq(document).ready(function() {
});

//]]>
</script>
