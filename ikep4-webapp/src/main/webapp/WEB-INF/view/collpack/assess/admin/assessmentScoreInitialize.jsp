<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="menuPrefix">ui.collpack.assess.menu</c:set>
<c:set var="confirmPrefix">ui.collpack.assess.common.confirm</c:set>
<c:set var="assessmentScoreInitializePrefix">ui.collpack.assess.admin.assessmentScoreInitialize</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.assessmentScoreInitialize"/></h2>
</div>
<!--//pageTitle End-->

<div class="corner_RoundBox01" style="margin-bottom:10px;">
	<ikep4j:message pre="${assessmentScoreInitializePrefix}" key="head1"/><br/>
	<ikep4j:message pre="${assessmentScoreInitializePrefix}" key="head2"/>
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>
</div>

<!--blockButton_2 Start-->
<div class="blockButton_2 w20">
	<a class="button_2" href="#a" onclick="executeHandler();"><span><ikep4j:message pre="${assessmentScoreInitializePrefix}" key="button"/></span></a>
</div>
<!--//blockButton_2 End-->

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre="${assessmentScoreInitializePrefix}" key="subTitle"/></h3>
</div>
<!--//subTitle_2 End-->	
<!--blockListTable Start-->
<div class="MyContentsTable">
	<table summary="<ikep4j:message pre="${assessmentScoreInitializePrefix}" key="table.summary"/>">
		<caption></caption>
		<tbody>
		<c:forEach var="item" items="${assessmentManagementInitializationLogList}" varStatus="itemStatus">
			<tr class="msg_unread">
				<td scope="col" width="5%" class="tdFirst textRight_p20">${itemStatus.count}</td>
				<td scope="col" width="" class="textLeft_p20 tdLast"><fmt:formatDate value="${item.initialDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		<c:if test="${0 eq fn:length(assessmentManagementInitializationLogList)}">
			<tr class="msg_unread">
				<td scope="col" width="5%" class="tdFirst textRight_p20">1</td>
				<td scope="col" width="" class="textLeft_p20 tdLast">none</td>
			</tr>
		</c:if>
		</tbody>
	</table>
</div>
<!--//blockListTable End-->

<form id="dataForm" method="post" action="<c:url value='/collpack/assess/admin/scoreInitialize.do'/>">
</form>

<script type="text/javascript">
//<![CDATA[

var executeHandler = function() {
	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='execute'/>")) {
		return;
	}

	$jq("#dataForm").submit();
};

$jq(document).ready(function() {
});

//]]>
</script>
