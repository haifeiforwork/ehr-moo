<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgemonitor.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgemonitor.common.button</c:set>
<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>
<c:set var="contentCriteriaManagerPrefix">ui.collpack.knowledgemonitor.administrator.contentCriteriaManager</c:set>
<c:set var="validationMessagePrefix">message.collpack.knowledgemonitor.common</c:set>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.contentCriteriaManager"/></h2>
</div>
<!--//pageTitle End-->

<form id="dataForm" method="post" action="<c:url value='/collpack/knowledgemonitor/admin/saveContentCriteria.do'/>">
<input name="moduleCodeCommaString" type="hidden" value="${moduleCodeCommaString}"/>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline" style="height:20px;">
	<div><h4><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="subTitle1"/>&nbsp;<input name="evaluationTerm" class="inputbox" size="2" type="text" value="${cviEvaluationTerm}" title="input box" style="display:none;"/><span>${cviEvaluationTerm}</span><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="subTitle2"/></h4></div>
</div>
<!--//subTitle_2 End-->
<div class="blockBlank_10px"></div>
<!--blockDetail Start-->
<div class="blockDetail clear">
	<table summary="<ikep4j:message pre="${contentCriteriaManagerPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="18%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col1"/></th>
				<th scope="col" width="13%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col2"/></th>
				<th scope="col" width="13%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col3"/></th>
				<th scope="col" width="13%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col4"/></th>
				<th scope="col" width="13%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col5"/></th>
				<th scope="col" width="13%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col6"/></th>
				<th scope="col" width="18%" class="textCenter"><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="grid.col7"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${knowledgeMonitorModuleList}">
			<tr>
				<td>${item.moduleName}</td>
				<td class="textCenter"><div>
					<input name="${item.moduleCode}Hit" class="inputbox w20" type="text" value="${item.hitWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.hitWeight}"/></span>&nbsp;%
				</div></td>
				<td class="textCenter"><div>
				<c:if test="${(IKepConstant.ITEM_TYPE_CODE_FORUM ne item.moduleCode) and (IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode) and (IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL ne item.moduleCode)}">
					<input name="${item.moduleCode}Recommend" class="inputbox w20" type="text" value="${item.recommendWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.recommendWeight}"/></span>&nbsp;%
				</c:if>
				</div></td>
				<td class="textCenter"><div>
				<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode}">
					<input name="${item.moduleCode}Linereply" class="inputbox w20" type="text" value="${item.linereplyWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.linereplyWeight}"/></span>&nbsp;%
				</c:if>
				</div></td>
				<td class="textCenter"><div>
					<input name="${item.moduleCode}Favorite" class="inputbox w20" type="text" value="${item.favoriteWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.favoriteWeight}"/></span>&nbsp;%
				</div></td>
				<td class="textCenter"><div>
				<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode}">
					<input name="${item.moduleCode}Distribute" class="inputbox w20" type="text" value="${item.distributeWeight}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.distributeWeight}"/></span>&nbsp;%
				</c:if>
				</div></td>
				<td class="textCenter"><div>
					<input name="${item.moduleCode}Cutline" class="inputbox" size="10" type="text" value="${item.goodDocCutline}" title="input box" style="display:none;"/>
					<span><fmt:formatNumber value="${item.goodDocCutline}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/>
				</div></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

</form>

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
<div class="directive">
	<ul>
		<li><span><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="directive1"/></span></li>
		<li><span><ikep4j:message pre="${contentCriteriaManagerPrefix}" key="directive2"/></span></li>
	</ul>
</div>
<!--//directive End-->

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
			evaluationTerm : {
				required : true,
				digits : true,
				range : [1, 100]
			},
	   	<c:forEach var="item" items="${knowledgeMonitorModuleList}" varStatus="itemStatus">
			${item.moduleCode}Hit : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			<c:if test="${(IKepConstant.ITEM_TYPE_CODE_FORUM ne item.moduleCode) and (IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode) and (IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL ne item.moduleCode)}">
			${item.moduleCode}Recommend : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			</c:if>
			<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode}">
			${item.moduleCode}Linereply : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			</c:if>
			${item.moduleCode}Favorite : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode}">
			${item.moduleCode}Distribute : {
				required : true,
				digits : true,
				range : [0, 1000]
			},
			</c:if>
			${item.moduleCode}Cutline : {
				required : true,
				digits : true,
				range : [1, 999999999]
			}<c:if test="${!itemStatus.last}">,</c:if>
		</c:forEach>
		},
		messages : {
			evaluationTerm : {
				direction : "top",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "1 ~ 100 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			},
		<c:forEach var="item" items="${knowledgeMonitorModuleList}" varStatus="itemStatus">
			${item.moduleCode}Hit : {
				direction : "right",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "0 ~ 1000 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			},
			<c:if test="${(IKepConstant.ITEM_TYPE_CODE_FORUM ne item.moduleCode) and (IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode) and (IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL ne item.moduleCode)}">
			${item.moduleCode}Recommend : {
				direction : "right",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "0 ~ 1000 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			},
			</c:if>
			<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode}">
			${item.moduleCode}Linereply : {
				direction : "right",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "0 ~ 1000 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			},
			</c:if>
			${item.moduleCode}Favorite : {
				direction : "right",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "0 ~ 1000 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			},
			<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY ne item.moduleCode}">
			${item.moduleCode}Distribute : {
				direction : "right",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "0 ~ 1000 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
			},
			</c:if>
			${item.moduleCode}Cutline : {
				direction : "left",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "1 ~ 999,999,999 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
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
