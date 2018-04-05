<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="confirmPrefix">ui.collpack.knowledgehub.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgehub.common.button</c:set>
<c:set var="validationMessagePrefix"></c:set>
<c:set var="messagePrefix">ui.collpack.knowledgehub.admin.popularityKnowledgePolicyManagementView</c:set>

<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${messagePrefix}" key="pageTitle"/></h2>
</div>
<!--//pageTitle End-->

<!--blockDetail Start-->
<div class="blockDetail clear">
	<form id="dataForm" action="">
		<input name="policyId" type="hidden" value="${knowledgeMapPolicy.policyId}"/>
	<table summary="<ikep4j:message pre="${messagePrefix}" key="subTitle"/>">
		<caption></caption>
		<thead>
			<tr>
				<th rowspan="2" width="25%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="grid.title1"/></td>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="grid.title2"/></th>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="grid.title3"/></th>
				<th scope="col" width="25%" class="textCenter"><ikep4j:message pre="${messagePrefix}" key="grid.title4"/></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${messagePrefix}" key="grid.message1"/></td>
				<td class="textCenter"><div>
					<input name="hitWeight" class="inputbox" size="3" type="text" value="${knowledgeMapPolicy.hitWeight}" style="display:none;"/>
					<span><fmt:formatNumber value="${knowledgeMapPolicy.hitWeight}"/></span>&nbsp;%
				</div></td>
				<td class="textCenter"><div>
					<input name="recommendWeight" class="inputbox" size="3" type="text" value="${knowledgeMapPolicy.recommendWeight}" style="display:none;"/>
					<span><fmt:formatNumber value="${knowledgeMapPolicy.recommendWeight}"/></span>&nbsp;%
				</div></td>
				<td class="textCenter"><div>
					<input name="favoriteWeight" class="inputbox" size="3" type="text" value="${knowledgeMapPolicy.favoriteWeight}" style="display:none;"/>
					<span><fmt:formatNumber value="${knowledgeMapPolicy.favoriteWeight}"/></span>&nbsp;%
				</div></td>
			</tr>
		</tbody>
	</table>
	</form>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a class="button" href="#a" onclick="$jq('#dataForm').submit();" id="saveBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="save"/></span></a></li>
		<li><a class="button" href="#a" onclick="cancelBtnHandler();" id="cancelBtn" style="display:none;"><span><ikep4j:message pre="${buttonPrefix}" key="cancel"/></span></a></li>
		<li><a class="button" href="#a" onclick="modifyBtnHandler();" id="modifyBtn"><span><ikep4j:message pre="${buttonPrefix}" key="modify"/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

<!--directive Start-->
<div class="directive mb15">
	<ul>
		<li><span><ikep4j:message pre="${messagePrefix}" key="topInfo.comment21"/><br/><ikep4j:message pre="${messagePrefix}" key="topInfo.comment22"/></span></li>
		<li><span><ikep4j:message pre="${messagePrefix}" key="topInfo.comment3"/></span></li>
	</ul>
</div>
<!--//directive End-->

</div>

<script type="text/javascript">
//<![CDATA[
var validator;

var modifyBtnHandler = function() {
	statusEdit();
};

var saveBtnHandler = function(form) {
	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
		return;
	}

	$jq.ajax({
		url : iKEP.getContextRoot() + "//collpack/knowledgehub//admin/savePolicy.do",
		type : "post",
		data : $jq(form).serialize(),
		loadingElement : "#saveBtn",
		success : function(data, textStatus, jqXHR) {
			$jq("#menuPopularityKnowledgePolicyManagement").click();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			var errorItems = $jq.parseJSON(jqXHR.responseText).exception;
			validator.showErrors(errorItems);
		}
	});
};

var cancelBtnHandler = function() {
	$jq("#dataForm label[class=valid-error]").hide();

	statusRead();
};

var statusEdit = function() {
	$jq("#dataForm input[type=text]").each(function(){
		$jq(this).val($jq(this).next().text().replace(/,/g, ''));
	});

	$jq("#saveBtn").show();
	$jq("#cancelBtn").show();
	$jq("#modifyBtn").hide();
	$jq("#dataForm input[type=text]").show();
	$jq("#dataForm span").hide();
};

var statusRead = function() {
	$jq("#saveBtn").hide();
	$jq("#cancelBtn").hide();
	$jq("#modifyBtn").show();
	$jq("#dataForm input[type=text]").hide();
	$jq("#dataForm span").show();
};
//]]>
</script>

<script type="text/javascript">
//<![CDATA[
var validOptions = {
		rules : {
			hitWeight : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			recommendWeight : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			favoriteWeight : {
				required : true,
				digits : true,
				range : [0, 1000]
			}
		},
		messages : {
			hitWeight : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.knowledgeMapPolicy.hitWeight'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Digits.knowledgeMapPolicy.hitWeight'/>",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.knowledgeMapPolicy.hitWeight'/>"
			},
			recommendWeight : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.knowledgeMapPolicy.recommendWeight'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Digits.knowledgeMapPolicy.recommendWeight'/>",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.knowledgeMapPolicy.recommendWeight'/>"
			},
			favoriteWeight : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.knowledgeMapPolicy.favoriteWeight'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Digits.knowledgeMapPolicy.favoriteWeight'/>",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.knowledgeMapPolicy.favoriteWeight'/>"
			}
	    },
	    submitHandler : saveBtnHandler
	};

$jq(document).ready(function() {
	validator = new iKEP.Validator("#dataForm", validOptions);
});
//]]>
</script>
