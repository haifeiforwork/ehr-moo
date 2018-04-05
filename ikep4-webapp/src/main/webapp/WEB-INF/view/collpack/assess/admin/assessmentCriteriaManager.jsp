<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="menuPrefix">ui.collpack.assess.menu</c:set>
<c:set var="buttonPrefix">ui.collpack.assess.common.button</c:set>
<c:set var="messagePrefix">ui.collpack.assess.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.assess.common.confirm</c:set>
<c:set var="tablePrefix">ui.collpack.assess.admin.assessmentCriteriaManager.table</c:set>
<c:set var="validationMessagePrefix"></c:set>
<c:set var="assessmentCriteriaManagerPrefix">ui.collpack.assess.admin.assessmentCriteriaManager</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.assessmentCriteriaManager"/></h2>
</div>
<!--//pageTitle End-->

<form id="dataForm" action="">
<!--tableTop Start-->
<div class="mb5">
	<div><ikep4j:message pre="${tablePrefix}" key="top"/>
		<%--
		<input type="text" class="inputbox" readonly="readonly" name="evaluationStartDateString" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="<fmt:formatDate value="${assessmentManagementPolicy.evaluationStartDate}" pattern="yyyy.MM.dd"/>" size="10" style="display:none;"/>&nbsp;<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${messagePrefix}" key="calender"/>" style="display:none;"/>
		<span id="evaluationStartDateSpan"><fmt:formatDate value="${assessmentManagementPolicy.evaluationStartDate}" pattern="yyyy.MM.dd"/></span>
		 --%>
		<fmt:formatDate value="${assessmentManagementPolicy.evaluationStartDate}" pattern="yyyy.MM.dd"/>
	</div>
</div>
<!--//tableTop End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="100" class="textCenter"><ikep4j:message pre="${tablePrefix}" key="col1"/></th>
				<td width="35%" class="textCenter table_color03" scope="col"><ikep4j:message pre="${tablePrefix}" key="col2"/></td>
				<td width="10%" class="textCenter table_color03" scope="col"><ikep4j:message pre="${tablePrefix}" key="col3"/></td>
				<td width="20%" class="textCenter table_color03" scope="col"><ikep4j:message pre="${tablePrefix}" key="col4"/></td>
				<td width="20%" class="textCenter table_color03" scope="col"><ikep4j:message pre="${tablePrefix}" key="col5"/></td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th rowspan="4" class="textCenter" scope="col"><ikep4j:message pre="${tablePrefix}" key="row1"/></th>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row1.item1"/></td>
				<td class="textCenter"><div><input name="itemRegistScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.itemRegistScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.itemRegistScore}"/></span></div></td>
				<td rowspan="4" class="textCenter"><div><input name="contributionMax" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.contributionMax}" size="10" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.contributionMax}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/></div></td>
				<td rowspan="4" class="textCenter"><div><input name="contributionWeight" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.contributionWeight}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.contributionWeight}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="percent"/></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row1.item2"/></td>
				<td class="textCenter"><div><input name="bestItemScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.bestItemScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.bestItemScore}"/></span></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row1.item3"/></td>
				<td class="textCenter"><div><input name="answerScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.answerScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.answerScore}"/></span></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row1.item4"/></td>
				<td class="textCenter"><div><input name="bestAnswerScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.bestAnswerScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.bestAnswerScore}"/></span></div></td>
			</tr>
			<tr>
				<th width="100" rowspan="2" class="textCenter" scope="col"><ikep4j:message pre="${tablePrefix}" key="row2"/></th>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row2.item1"/></td>
				<td class="textCenter"><div><input name="linereplyScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.linereplyScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.linereplyScore}"/></span></div></td>
				<td rowspan="2" class="textCenter"><div><input name="participationMax" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.participationMax}" size="10" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.participationMax}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/></div></td>
				<td rowspan="2" class="textCenter"><div><input name="participationWeight" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.participationWeight}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.participationWeight}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="percent"/></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row2.item3"/></td>
				<td class="textCenter"><div><input name="recommendScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.recommendScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.recommendScore}"/></span></div></td>
			</tr>
			<tr>
				<th width="100" rowspan="2" class="textCenter" scope="col"><ikep4j:message pre="${tablePrefix}" key="row3"/></th>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row3.item1"/></td>
				<td class="textCenter"><div><input name="hitScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.hitScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.hitScore}"/></span></div></td>
				<td rowspan="2" class="textCenter"><div><input name="applicationMax" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.applicationMax}" size="10" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.applicationMax}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/></div></td>
				<td rowspan="2" class="textCenter"><div><input name="applicationWeight" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.applicationWeight}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.applicationWeight}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="percent"/></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row3.item2"/></td>
				<td class="textCenter"><div><input name="searchScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.searchScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.searchScore}"/></span></div></td>
			</tr>
			<tr>
				<th width="100" rowspan="5" class="textCenter" scope="col"><ikep4j:message pre="${tablePrefix}" key="row4"/></th>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row4.item1"/></td>
				<td class="textCenter"><div><input name="followScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.followScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.followScore}"/></span></div></td>
				<td rowspan="5" class="textCenter"><div><input name="friendshipMax" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.friendshipMax}" size="10" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.friendshipMax}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/></div></td>
				<td rowspan="5" class="textCenter"><div><input name="friendshipWeight" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.friendshipWeight}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.friendshipWeight}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="percent"/></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row4.item2"/></td>
				<td class="textCenter"><div><input name="followingScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.followingScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.followingScore}"/></span></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row4.item3"/></td>
				<td class="textCenter"><div><input name="crossFollowingScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.crossFollowingScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.crossFollowingScore}"/></span></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row4.item4"/></td>
				<td class="textCenter"><div><input name="mblogGroupScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.mblogGroupScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.mblogGroupScore}"/></span></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row4.item5"/></td>
				<td class="textCenter"><div><input name="guestbookItemScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.guestbookItemScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.guestbookItemScore}"/></span></div></td>
			</tr>
			<tr>
				<th width="100" rowspan="3" class="textCenter" scope="col"><ikep4j:message pre="${tablePrefix}" key="row5"/></th>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row5.item1"/></td>
				<td class="textCenter"><div><input name="tweetScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.tweetScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.tweetScore}"/></span></div></td>
				<td rowspan="3" class="textCenter"><div><input name="leadershipMax" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.leadershipMax}" size="10" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.leadershipMax}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="point"/></div></td>
				<td rowspan="3" class="textCenter"><div><input name="leadershipWeight" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.leadershipWeight}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.leadershipWeight}"/></span>&nbsp;<ikep4j:message pre="${messagePrefix}" key="percent"/></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row5.item2"/></td>
				<td class="textCenter"><div><input name="retweetScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.retweetScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.retweetScore}"/></span></div></td>
			</tr>
			<tr>
				<td class="textCenter"><ikep4j:message pre="${tablePrefix}" key="row5.item4"/></td>
				<td class="textCenter"><div><input name="profileVisitScore" type="text" class="inputbox" title="<ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="input.title"/>" value="${assessmentManagementPolicy.profileVisitScore}" size="4" style="display:none;"/><span><fmt:formatNumber value="${assessmentManagementPolicy.profileVisitScore}"/></span></div></td>
			</tr>
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
<div class="directive mb10">
	<ul>
		<li><span><ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="directive1"/></span></li>
		<li><span><ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="directive2"/></span></li>
		<li><span><ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="directive3"/></span></li>
		<li><span><ikep4j:message pre="${assessmentCriteriaManagerPrefix}" key="directive4"/></span></li>
	</ul>
</div>
<!--//directive End-->

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
		url : iKEP.getContextRoot() + "/collpack/assess/admin/savePolicy.do",
		type : "post",
		data : $jq(form).serialize(),
		loadingElement : "#saveBtn",
		success : function(data, textStatus, jqXHR) {
			location.href = $jq("#menuAssessmentCriteriaManager").attr("href");
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
	$jq("#dataForm input").each(function(){
		$jq(this).val($jq(this).next().text().replace(/,/g, ''));
	});

	//$jq("#dataForm input[name=evaluationStartDateString]").val($jq("#evaluationStartDateSpan").text());

	$jq("#saveBtn").show();
	$jq("#cancelBtn").show();
	$jq("#modifyBtn").hide();
	$jq("#dataForm input").show();
	$jq("#dataForm img").show();
	$jq("#dataForm span").hide();
};

var statusRead = function() {
	$jq("#saveBtn").hide();
	$jq("#cancelBtn").hide();
	$jq("#modifyBtn").show();
	$jq("#dataForm input").hide();
	$jq("#dataForm img").hide();
	$jq("#dataForm span").show();
};

$jq(document).ready(function() {
	$jq("#dataForm input[name=evaluationStartDateString]")
		.datepicker(
			$jq.extend($jq.extend($jq.datepicker.regional[iKEPLang.datepicker.langCode], {yearSuffix:""}),
			{changeYear:true, changeMonth:true}))
		.next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });

});

//]]>
</script>

<script type="text/javascript">
//<![CDATA[

var validOptions = {
		rules : {
			evaluationStartDateString : {
				//required : true,
				//date : true
			},
			contributionMax : {
				range : [1, 999999999]
			},
			contributionWeight : {
				range : [0, 1000]
			},
			participationMax : {
				range : [1, 999999999]
			},
			participationWeight : {
				range : [0, 1000]
			},
			applicationMax : {
				range : [1, 999999999]
			},
			applicationWeight : {
				range : [0, 1000]
			},
			friendshipMax : {
				range : [1, 999999999]
			},
			friendshipWeight : {
				range : [0, 1000]
			},
			leadershipMax : {
				range : [1, 999999999]
			},
			leadershipWeight : {
				range : [0, 1000]
			},
			itemRegistScore : {
				range : [0, 1000]
			},
			bestItemScore : {
				range : [0, 1000]
			},
			answerScore : {
				range : [0, 1000]
			},
			bestAnswerScore : {
				range : [0, 1000]
			},
			linereplyScore : {
				range : [0, 1000]
			},
			recommendScore : {
				range : [0, 1000]
			},
			hitScore : {
				range : [0, 1000]
			},
			searchScore : {
				range : [0, 1000]
			},
			followScore : {
				range : [0, 1000]
			},
			followingScore : {
				range : [0, 1000]
			},
			crossFollowingScore : {
				range : [0, 1000]
			},
			mblogGroupScore : {
				range : [0, 1000]
			},
			guestbookItemScore : {
				range : [0, 1000]
			},
			tweetScore : {
				range : [0, 1000]
			},
			retweetScore : {
				range : [0, 1000]
			},
			profileVisitScore : {
				range : [0, 1000]
			},
		},
		messages : {
			evaluationStartDateString : {
				direction : "bottom",
				required : "<ikep4j:message pre='${validationMessagePrefix}' key='NotNull.assessmentManagementPolicy.evaluationStartDateString'/>",

				date : "<ikep4j:message pre='${validationMessagePrefix}' key='Pattern.assessmentManagementPolicy.evaluationStartDateString'/>"
			},
			contributionMax : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.contributionMax'/>"
			},
			contributionWeight : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.contributionWeight'/>"
			},
			participationMax : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.participationMax'/>"
			},
			participationWeight : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.participationWeight'/>"
			},
			applicationMax : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.applicationMax'/>"
			},
			applicationWeight : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.applicationWeight'/>"
			},
			friendshipMax : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.friendshipMax'/>"
			},
			friendshipWeight : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.friendshipWeight'/>"
			},
			leadershipMax : {
				direction : "left",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.leadershipMax'/>"
			},
			leadershipWeight : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.leadershipWeight'/>"
			},
			itemRegistScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.itemRegistScore'/>"
			},
			bestItemScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.bestItemScore'/>"
			},
			answerScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.answerScore'/>"
			},
			bestAnswerScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.bestAnswerScore'/>"
			},
			linereplyScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.linereplyScore'/>"
			},
			recommendScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.recommendScore'/>"
			},
			hitScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.hitScore'/>"
			},
			searchScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.searchScore'/>"
			},
			followScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.followScore'/>"
			},
			followingScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.followingScore'/>"
			},
			crossFollowingScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.crossFollowingScore'/>"
			},
			mblogGroupScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.mblogGroupScore'/>"
			},
			guestbookItemScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.guestbookItemScore'/>"
			},
			tweetScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.tweetScore'/>"
			},
			retweetScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.retweetScore'/>"
			},
			profileVisitScore : {
				direction : "bottom",
				range : "<ikep4j:message pre='${validationMessagePrefix}' key='Min.assessmentManagementPolicy.profileVisitScore'/>"
			}
	    },
	    submitHandler : saveBtnHandler
	};

$jq(document).ready(function() {
	validator = new iKEP.Validator("#dataForm", validOptions);
});

//]]>
</script>
