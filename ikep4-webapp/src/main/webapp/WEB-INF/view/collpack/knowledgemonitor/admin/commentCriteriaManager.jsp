<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgemonitor.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgemonitor.common.button</c:set>
<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>
<c:set var="commentCriteriaManagerPrefix">ui.collpack.knowledgemonitor.administrator.commentCriteriaManager</c:set>
<c:set var="validationMessagePrefix">message.collpack.knowledgemonitor.common</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.commentCriteriaManager"/></h2>
</div>
<!--//pageTitle End-->

<!--subTitle_2 Start-->
<div class="subTitle_2 noline" style="height:20px;">
	<h4><ikep4j:message pre="${commentCriteriaManagerPrefix}" key="subTitle"/></h4>
</div>
<!--//subTitle_2 End-->
<div class="blockBlank_10px"></div>
<!--blockDetail Start-->
<div class="blockDetail clear" style="width:35%;">
	<form id="dataForm" method="post" action="<c:url value='/collpack/knowledgemonitor/admin/saveCommentCriteria.do'/>">
	<table summary="<ikep4j:message pre="${commentCriteriaManagerPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${commentCriteriaManagerPrefix}" key="grid.col1"/></th>
				<th scope="col" width="13%" class="textCenter"><ikep4j:message pre="${commentCriteriaManagerPrefix}" key="grid.col2"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${knowledgeMonitorModuleList}">
			<tr>
				<td>${item.moduleName}</td>
				<td class="textCenter"><div>
					<input name="${item.moduleCode}" class="inputbox" size="5" type="text" value="${item.goodLinereplyCutline}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.goodLinereplyCutline}"/></span>
				</div></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton" style="width:35%;">
	<ul>
		<li><a class="button" href="#a" onclick="$jq('#dataForm').submit();" id="saveBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="save"/></span></a></li>
		<li><a class="button" href="#a" onclick="cancelBtnHandler();" id="cancelBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
		<li><a class="button" href="#a" onclick="modifyBtnHandler();" id="modifyBtn"><span><ikep4j:message pre="${buttonPrefix}" key="modify"/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

<script type="text/javascript">
//<![CDATA[

var modifyBtnHandler = function() {
	statusEdit();
};

var saveBtnHandler = function(form) {
	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
		return;
	}

	form.submit();
};

var cancelBtnHandler = function() {
	$jq("#dataForm label[class=valid-error]").hide();

	statusRead();
};

var statusEdit = function() {
	$jq("#dataForm input").each(function(){
		$jq(this).val($jq(this).next().text().replace(/,/g, ''));
	});

	$jq("#saveBtn").show();
	$jq("#cancelBtn").show();
	$jq("#modifyBtn").hide();
	$jq("#dataForm input").show();
	$jq("#dataForm span").hide();
};

var statusRead = function() {
	$jq("#saveBtn").hide();
	$jq("#cancelBtn").hide();
	$jq("#modifyBtn").show();
	$jq("#dataForm input").hide();
	$jq("#dataForm span").show();
};

//]]>
</script>

<script type="text/javascript">
//<![CDATA[

var validOptions = {
		rules : {
	   	<c:forEach var="item" items="${knowledgeMonitorModuleList}" varStatus="itemStatus">
			${item.moduleCode} : {
				required : true,
				digits : true,
				range : [1, 1000]
			}<c:if test="${!itemStatus.last}">,</c:if>
		</c:forEach>
		},
		messages : {
		<c:forEach var="item" items="${knowledgeMonitorModuleList}" varStatus="itemStatus">
			${item.moduleCode} : {
				direction : "right",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "1 ~ 1000 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			}<c:if test="${!itemStatus.last}">,</c:if>
		</c:forEach>
	    },
	    submitHandler : saveBtnHandler
	};

$jq(document).ready(function() {
	new iKEP.Validator("#dataForm", validOptions);
});

//]]>
</script>
