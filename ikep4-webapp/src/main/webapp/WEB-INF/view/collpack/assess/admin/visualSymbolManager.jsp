<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="menuPrefix">ui.collpack.assess.menu</c:set>
<c:set var="confirmPrefix">ui.collpack.assess.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.assess.common.button</c:set>
<c:set var="messagePrefix">ui.collpack.assess.common.message</c:set>
<c:set var="visualSymbolManagerPrefix">ui.collpack.assess.admin.visualSymbolManager</c:set>
<c:set var="validationMessagePrefix">message.collpack.assessmentManagementPolicy.common</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.visualSymbolManager"/></h2>
</div>
<!--//pageTitle End-->

<!--tableTop Start-->
<div class="mb5">
	<div class="floatLeft"><span class="colorPoint"><ikep4j:message pre="${visualSymbolManagerPrefix}" key="message.maxPvi"/><strong><fmt:formatNumber value="${maxPvi}"/></strong>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/></span></div>
	<div class="clear"></div>
	<input id="maxScore" name="maxScore" type="hidden" value="${maxPvi}"/>
</div>
<!--//tableTop End-->

<form id="dataForm" method="post" action="<c:url value='/collpack/assess/admin/saveVisualSymbol.do'/>">
<input name="symbolId0" type="hidden" value="${assessmentManagementPviSymbolList[0].symbolId}"/>
<input name="symbolId1" type="hidden" value="${assessmentManagementPviSymbolList[1].symbolId}"/>
<input name="symbolId2" type="hidden" value="${assessmentManagementPviSymbolList[2].symbolId}"/>
<input name="symbolId3" type="hidden" value="${assessmentManagementPviSymbolList[3].symbolId}"/>
<input name="symbolId4" type="hidden" value="${assessmentManagementPviSymbolList[4].symbolId}"/>
<input name="symbolId5" type="hidden" value="${assessmentManagementPviSymbolList[5].symbolId}"/>
<input name="symbolId6" type="hidden" value="${assessmentManagementPviSymbolList[6].symbolId}"/>
<input name="symbolId7" type="hidden" value="${assessmentManagementPviSymbolList[7].symbolId}"/>
<input name="symbolId8" type="hidden" value="${assessmentManagementPviSymbolList[8].symbolId}"/>
<input name="symbolId9" type="hidden" value="${assessmentManagementPviSymbolList[9].symbolId}"/>

<!--blockDetail Start-->
<div class="blockDetail mb20">
	<table summary="<ikep4j:message pre="${visualSymbolManagerPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th width="20%" class="textCenter table_color03" scope="col">1<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">2<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">3<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">4<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">5<ikep4j:message pre="${messagePrefix}" key="step"/></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter">
					<div id="step01Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step01Swf","<c:url value="/base/images/activity/personal/step_01.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step02Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step02Swf","<c:url value="/base/images/activity/personal/step_02.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step03Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step03Swf","<c:url value="/base/images/activity/personal/step_03.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step04Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step04Swf","<c:url value="/base/images/activity/personal/step_04.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step05Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step05Swf","<c:url value="/base/images/activity/personal/step_05.swf"/>", 138, 141);</script></div>
				</td>
			</tr>
			<tr>
				<th colspan="5" class="textCenter" scope="col"><ikep4j:message pre="${visualSymbolManagerPrefix}" key="message.sectionValue"/></th>
			</tr>
			<tr>
				<td class="textCenter"><input name="sectionValue0" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[0].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue1" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[1].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue2" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[2].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue3" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[3].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue4" type="text" class="inputbox"    title="inputbox" value="${assessmentManagementPviSymbolList[4].sectionValue}" size="4"/>&nbsp;%</td>
			</tr>
			<tr>
				<th colspan="5" class="textCenter" scope="col"><ikep4j:message pre="${visualSymbolManagerPrefix}" key="message.pviSectionScore"/></th>
			</tr>
			<tr>
				<td class="textCenter"><input name="sectionStartScore0" type="hidden" value="${assessmentManagementPviSymbolList[0].sectionStartScore}"/><span id="sectionStartScoreSpan0"><fmt:formatNumber value="${assessmentManagementPviSymbolList[0].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore0" type="hidden" value="${assessmentManagementPviSymbolList[0].sectionEndScore}"/><span id="sectionEndScoreSpan0"><fmt:formatNumber value="${assessmentManagementPviSymbolList[0].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore1" type="hidden" value="${assessmentManagementPviSymbolList[1].sectionStartScore}"/><span id="sectionStartScoreSpan1"><fmt:formatNumber value="${assessmentManagementPviSymbolList[1].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore1" type="hidden" value="${assessmentManagementPviSymbolList[1].sectionEndScore}"/><span id="sectionEndScoreSpan1"><fmt:formatNumber value="${assessmentManagementPviSymbolList[1].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore2" type="hidden" value="${assessmentManagementPviSymbolList[2].sectionStartScore}"/><span id="sectionStartScoreSpan2"><fmt:formatNumber value="${assessmentManagementPviSymbolList[2].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore2" type="hidden" value="${assessmentManagementPviSymbolList[2].sectionEndScore}"/><span id="sectionEndScoreSpan2"><fmt:formatNumber value="${assessmentManagementPviSymbolList[2].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore3" type="hidden" value="${assessmentManagementPviSymbolList[3].sectionStartScore}"/><span id="sectionStartScoreSpan3"><fmt:formatNumber value="${assessmentManagementPviSymbolList[3].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore3" type="hidden" value="${assessmentManagementPviSymbolList[3].sectionEndScore}"/><span id="sectionEndScoreSpan3"><fmt:formatNumber value="${assessmentManagementPviSymbolList[3].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore4" type="hidden" value="${assessmentManagementPviSymbolList[4].sectionStartScore}"/><span id="sectionStartScoreSpan4"><fmt:formatNumber value="${assessmentManagementPviSymbolList[4].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore4" type="hidden" value="${assessmentManagementPviSymbolList[4].sectionEndScore}"/><span id="sectionEndScoreSpan4"><fmt:formatNumber value="${assessmentManagementPviSymbolList[4].sectionEndScore}"/></span></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="<ikep4j:message pre="${visualSymbolManagerPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th width="20%" class="textCenter table_color03" scope="col">6<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">7<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">8<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">9<ikep4j:message pre="${messagePrefix}" key="step"/></th>
				<th width="20%" class="textCenter table_color03" scope="col">10<ikep4j:message pre="${messagePrefix}" key="step"/></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="textCenter">
				    <div id="step06Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step06Swf","<c:url value="/base/images/activity/personal/step_06.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step07Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step07Swf","<c:url value="/base/images/activity/personal/step_07.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step08Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step08Swf","<c:url value="/base/images/activity/personal/step_08.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step09Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step09Swf","<c:url value="/base/images/activity/personal/step_09.swf"/>", 138, 141);</script></div>
				</td>
				<td class="textCenter">
					<div id="step10Swf"><script type="text/javascript">iKEP.createSWFEmbedObject("#step10Swf","<c:url value="/base/images/activity/personal/step_10.swf"/>", 138, 141);</script></div>
				</td>
			</tr>
			<tr>
				<th colspan="5" class="textCenter" scope="col"><ikep4j:message pre="${visualSymbolManagerPrefix}" key="message.sectionValue"/></th>
			</tr>
			<tr>
				<td class="textCenter"><input name="sectionValue5" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[5].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue6" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[6].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue7" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[7].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue8" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[8].sectionValue}" size="4"/>&nbsp;%</td>
				<td class="textCenter"><input name="sectionValue9" type="text" class="inputbox" title="inputbox" value="${assessmentManagementPviSymbolList[9].sectionValue}" size="4"/>&nbsp;%</td>
			</tr>
			<tr>
				<th colspan="5" class="textCenter" scope="col"><ikep4j:message pre="${visualSymbolManagerPrefix}" key="message.pviSectionScore"/></th>
			</tr>
			<tr>
				<td class="textCenter"><input name="sectionStartScore5" type="hidden" value="${assessmentManagementPviSymbolList[5].sectionStartScore}"/><span id="sectionStartScoreSpan5"><fmt:formatNumber value="${assessmentManagementPviSymbolList[5].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore5" type="hidden" value="${assessmentManagementPviSymbolList[5].sectionEndScore}"/><span id="sectionEndScoreSpan5"><fmt:formatNumber value="${assessmentManagementPviSymbolList[5].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore6" type="hidden" value="${assessmentManagementPviSymbolList[6].sectionStartScore}"/><span id="sectionStartScoreSpan6"><fmt:formatNumber value="${assessmentManagementPviSymbolList[6].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore6" type="hidden" value="${assessmentManagementPviSymbolList[6].sectionEndScore}"/><span id="sectionEndScoreSpan6"><fmt:formatNumber value="${assessmentManagementPviSymbolList[6].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore7" type="hidden" value="${assessmentManagementPviSymbolList[7].sectionStartScore}"/><span id="sectionStartScoreSpan7"><fmt:formatNumber value="${assessmentManagementPviSymbolList[7].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore7" type="hidden" value="${assessmentManagementPviSymbolList[7].sectionEndScore}"/><span id="sectionEndScoreSpan7"><fmt:formatNumber value="${assessmentManagementPviSymbolList[7].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore8" type="hidden" value="${assessmentManagementPviSymbolList[8].sectionStartScore}"/><span id="sectionStartScoreSpan8"><fmt:formatNumber value="${assessmentManagementPviSymbolList[8].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore8" type="hidden" value="${assessmentManagementPviSymbolList[8].sectionEndScore}"/><span id="sectionEndScoreSpan8"><fmt:formatNumber value="${assessmentManagementPviSymbolList[8].sectionEndScore}"/></span></td>
				<td class="textCenter"><input name="sectionStartScore9" type="hidden" value="${assessmentManagementPviSymbolList[9].sectionStartScore}"/><span id="sectionStartScoreSpan9"><fmt:formatNumber value="${assessmentManagementPviSymbolList[9].sectionStartScore}"/></span>&nbsp;~&nbsp;<input name="sectionEndScore9" type="hidden" value="${assessmentManagementPviSymbolList[9].sectionEndScore}"/><span id="sectionEndScoreSpan9"><fmt:formatNumber value="${assessmentManagementPviSymbolList[9].sectionEndScore}"/></span></td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->
</form>

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a class="button" href="#a" onclick="calcScore();"><span><ikep4j:message pre="${buttonPrefix}" key="calc"/></span></a></li>
		<li><a class="button" href="#a" onclick="$jq('#dataForm').submit();" ><span><ikep4j:message pre="${buttonPrefix}" key="save"/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

<!--directive Start-->
<div class="directive">
	<ul>
		<li><span><ikep4j:message pre="${visualSymbolManagerPrefix}" key="directive1"/></span></li>
		<li><span><ikep4j:message pre="${visualSymbolManagerPrefix}" key="directive2"/></span></li>
		<li>
			<span>
				<ikep4j:message pre="${visualSymbolManagerPrefix}" key="directive3"/><br/>
				<ikep4j:message pre="${visualSymbolManagerPrefix}" key="directive4"/>
			</span>
		</li>
		<li><span><ikep4j:message pre="${visualSymbolManagerPrefix}" key="directive5"/></span></li>
	</ul>
</div>
<!--//directive End-->

<script type="text/javascript">
//<![CDATA[

var calcScore = function() {
	if (!sectionValueValidation()) {
		alert("<ikep4j:message pre='${visualSymbolManagerPrefix}' key='message.validation'/>");
		return;
	}

	var maxScore = parseInt($jq("#maxScore").val());
	var sectionValue = 0;
	var fromScore = 0;
	var toScore = -1;

	for (var idx = 0; idx < 10; idx++) {
		sectionValue = parseInt($jq("#dataForm input[name=sectionValue" + idx + "]").val());
		fromScore = toScore + 1;
		toScore = fromScore - 1 + Math.round(maxScore * sectionValue / 100);
		
		$jq("#sectionStartScoreSpan" + idx).text((""+fromScore).addComma());
		$jq("#sectionEndScoreSpan" + idx).text((""+toScore).addComma());
	}

	$jq("#sectionEndScoreSpan9").text((""+maxScore).addComma());

	$jq("#dataForm span").each(function(){
		$jq(this).prev().val($jq(this).text().replace(/,/g, ''));
	});
};

var saveBtnHandler = function(form) {
	if (!confirm("<ikep4j:message pre='${confirmPrefix}' key='save'/>")) {
		return;
	}

	if (!sectionValueValidation()) {
		alert("<ikep4j:message pre='${visualSymbolManagerPrefix}' key='message.validation'/>");
		return;
	}

	form.submit();
};

var sectionValueValidation = function() {
	var sum = 0;
	for (var idx = 0; idx < 10; idx++) {
		sum = sum + parseInt($jq("#dataForm input[name=sectionValue" + idx + "]").val());
	}

	return (100 == sum);
};


$jq(document).ready(function() {
});

//]]>
</script>

<script type="text/javascript">
//<![CDATA[

var validOptions = {
		rules : {
		<c:forEach var="item" varStatus="itemStatus" begin="0" end="9" step="1">
			sectionValue${item} : {
				required : true,
				digits : true,
				range : [1, 100]
			}<c:if test="${!itemStatus.last}">,</c:if>
		</c:forEach>
		},
		messages : {
		<c:forEach var="item" varStatus="itemStatus" begin="0" end="9" step="1">
			sectionValue${item} : {
				direction : "top",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotEmpty'/>",
				digits : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern'/>",
				range : "1 ~ 100 <ikep4j:message pre='${validationMessagePrefix}' key='Size'/>"
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
