<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgemonitor.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgemonitor.common.button</c:set>
<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>
<c:set var="moduleManagerPrefix">ui.collpack.knowledgemonitor.administrator.moduleManager</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.moduleManager"/></h2>
</div>
<!--//pageTitle End-->

<!--blockLeft Start-->
<div class="blockLeft" style="width:40%">
	<div class="leftTree treeBox" style="margin-bottom:10px;">
	<form id="dataForm" method="post" action="<c:url value='/collpack/knowledgemonitor/admin/saveModule.do'/>">
		<ul id="maglist">
		<c:forEach var="item" items="${knowledgeMonitorModuleList}">
			<li>
				<input title="${item.moduleName}" value="${item.moduleCode}" <c:if test="${1 eq item.isUsage}">checked="checked"</c:if>
					   name="moduleCode" disabled="disabled" class="checkbox" type="checkbox"/>
				<span style="display:none;">${item.isUsage}</span>
				&nbsp;${item.moduleName}
			</li>
		</c:forEach>
		</ul>
	</form>
	</div>
	<!--blockButton Start-->

	<div class="blockButton">
		<ul>
			<li><a class="button" href="#a" onclick="saveBtnHandler();" id="saveBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="save"/></span></a></li>
			<li><a class="button" href="#a" onclick="cancelBtnHandler();" id="cancelBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
			<li><a class="button" href="#a" onclick="modifyBtnHandler();" id="modifyBtn"><span><ikep4j:message pre="${buttonPrefix}" key="modify"/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->

</div>
<!--//blockLeft End-->

<script type="text/javascript">
//<![CDATA[

var modifyBtnHandler = function() {
	statusEdit();
};

var saveBtnHandler = function() {
	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
		return;
	}

	$jq("#dataForm").submit();
};

var cancelBtnHandler = function() {
	$jq("#dataForm input").each(function() {
		if ("1" == $jq(this).next().text()) {
			$jq(this).attr("checked", true);
		} else {
			$jq(this).removeAttr("checked");
		}
	});

	statusRead();
};


var statusEdit = function() {
	$jq("#saveBtn").show();
	$jq("#cancelBtn").show();
	$jq("#modifyBtn").hide();
	$jq("#dataForm input").removeAttr("disabled");
};

var statusRead = function() {
	$jq("#saveBtn").hide();
	$jq("#cancelBtn").hide();
	$jq("#modifyBtn").show();
	$jq("#dataForm input").attr("disabled", true);
};

$jq(document).ready(function() {
});

//]]>
</script>
