<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgemonitor.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgemonitor.common.button</c:set>
<c:set var="pagePrefix">ui.collpack.knowledgemonitor.common.page</c:set>
<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>
<c:set var="knowledgeRankingInfoPrefix">ui.collpack.knowledgemonitor.knowledgeRankingInfo</c:set>
<c:set var="knowledgeAccumulationInfoPrefix">ui.collpack.knowledgemonitor.knowledgeAccumulationInfo</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="knowledgeRankingInfo"/></h2>
</div>
<!--//pageTitle End-->

<form id="searchForm" method="post" action="<c:url value='/collpack/knowledgemonitor/knowledgeRankingInfoView.do'/>">
<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.summary"/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="search.module"/></th>
					<td scope="row" style="padding:0;">
					<c:forEach var="item" items="${knowledgeMonitorModuleList}">
						<input title="${item.moduleName}" value="${item.moduleCode}" <c:if test="${item.checked}">checked="checked"</c:if> name="moduleCodes" class="checkbox" type="checkbox"/>&nbsp;${item.moduleName}&nbsp;
					</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.date"/></th>
					<td scope="row" style="padding:0;">
						<select name="itemType" onchange="itemTypeChangeHandler(this.value);" style="float:left; margin-right:3px;">
							<option value="month" <c:if test="${'month' eq pageCondition.itemType}">selected="selected"</c:if>><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.date.type.month"/></option>
							<option value="week" <c:if test="${'week' eq pageCondition.itemType}">selected="selected"</c:if>><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.date.type.week"/></option>
						</select>
						<div class="subInfo">
							<div id="weekInfoDiv" style="display:none; width:300px; float:left;">
								<input type="text" class="inputbox" id="fromDate" name="fromDate" readonly="readonly" title="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.fromDate"/>" value="${pageCondition.fromDate}" size="10" /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.fromDate"/>"/>
								&nbsp;~&nbsp;
								<input type="text" class="inputbox" id="toDate" name="toDate" readonly="readonly" title="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.toDate"/>" value="${pageCondition.toDate}" size="10" /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.toDate"/>"/>
							</div>
							<div id="monthInfoDiv" style="display:none; width:300px; float:left;">
								<select name="fromYear">
								<c:forEach var="item" begin="2010" end="2030" step="1">
									<option value="${item}" <c:if test="${item eq pageCondition.fromYear}">selected="selected"</c:if>>${item}</option>
								</c:forEach>
								</select>
								<select name="fromMonth">
									<option value="01" <c:if test="${'01' eq pageCondition.fromMonth}">selected="selected"</c:if>>01</option>
									<option value="02" <c:if test="${'02' eq pageCondition.fromMonth}">selected="selected"</c:if>>02</option>
									<option value="03" <c:if test="${'03' eq pageCondition.fromMonth}">selected="selected"</c:if>>03</option>
									<option value="04" <c:if test="${'04' eq pageCondition.fromMonth}">selected="selected"</c:if>>04</option>
									<option value="05" <c:if test="${'05' eq pageCondition.fromMonth}">selected="selected"</c:if>>05</option>
									<option value="06" <c:if test="${'06' eq pageCondition.fromMonth}">selected="selected"</c:if>>06</option>
									<option value="07" <c:if test="${'07' eq pageCondition.fromMonth}">selected="selected"</c:if>>07</option>
									<option value="08" <c:if test="${'08' eq pageCondition.fromMonth}">selected="selected"</c:if>>08</option>
									<option value="09" <c:if test="${'09' eq pageCondition.fromMonth}">selected="selected"</c:if>>09</option>
									<option value="10" <c:if test="${'10' eq pageCondition.fromMonth}">selected="selected"</c:if>>10</option>
									<option value="11" <c:if test="${'11' eq pageCondition.fromMonth}">selected="selected"</c:if>>11</option>
									<option value="12" <c:if test="${'12' eq pageCondition.fromMonth}">selected="selected"</c:if>>12</option>
								</select>
								&nbsp;~&nbsp;
								<select name="toYear">
								<c:forEach var="item" begin="2011" end="2030" step="1">
									<option value="${item}" <c:if test="${item eq pageCondition.toYear}">selected="selected"</c:if>>${item}</option>
								</c:forEach>
								</select>
								<select name="toMonth">
									<option value="01" <c:if test="${'01' eq pageCondition.toMonth}">selected="selected"</c:if>>01</option>
									<option value="02" <c:if test="${'02' eq pageCondition.toMonth}">selected="selected"</c:if>>02</option>
									<option value="03" <c:if test="${'03' eq pageCondition.toMonth}">selected="selected"</c:if>>03</option>
									<option value="04" <c:if test="${'04' eq pageCondition.toMonth}">selected="selected"</c:if>>04</option>
									<option value="05" <c:if test="${'05' eq pageCondition.toMonth}">selected="selected"</c:if>>05</option>
									<option value="06" <c:if test="${'06' eq pageCondition.toMonth}">selected="selected"</c:if>>06</option>
									<option value="07" <c:if test="${'07' eq pageCondition.toMonth}">selected="selected"</c:if>>07</option>
									<option value="08" <c:if test="${'08' eq pageCondition.toMonth}">selected="selected"</c:if>>08</option>
									<option value="09" <c:if test="${'09' eq pageCondition.toMonth}">selected="selected"</c:if>>09</option>
									<option value="10" <c:if test="${'10' eq pageCondition.toMonth}">selected="selected"</c:if>>10</option>
									<option value="11" <c:if test="${'11' eq pageCondition.toMonth}">selected="selected"</c:if>>11</option>
									<option value="12" <c:if test="${'12' eq pageCondition.toMonth}">selected="selected"</c:if>>12</option>
								</select>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="searchBtn"><a href="#a" onclick="searchBtnHandler();"><span>Search</span></a></div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>	
</div>	
<!--//blockSearch End-->
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<div class="listInfo">
		<spring:bind path="pageCondition.totalCount">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.requestPage">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.page">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.notFirstAccess">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.countOfPage">
			<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre="${pagePrefix}" key="countOfPage"/>" onchange="searchBtnHandler();">
				<option <c:if test="${status.value eq 10}">selected="selected"</c:if>>10</option>
				<option <c:if test="${status.value eq 15}">selected="selected"</c:if>>15</option>
				<option <c:if test="${status.value eq 20}">selected="selected"</c:if>>20</option>
				<option <c:if test="${status.value eq 30}">selected="selected"</c:if>>30</option>
				<option <c:if test="${status.value eq 50}">selected="selected"</c:if>>50</option>
			</select>
		</spring:bind>
		<div class="totalNum"><span><ikep4j:message pre="${pagePrefix}" key="total"/>&nbsp;<fmt:formatNumber value="${pageCondition.totalCount}"/></span></div>
	</div>
	<div class="clear"></div>
</div>
<!--//tableTop End-->
</form>

<!--blockListTable Start-->
<div class="blockListTable">
	<table summary="<ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="5%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col01"/></th>
				<th scope="col" width="8%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col02"/></th>
				<th scope="col" width="*"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col03"/></th>
				<th scope="col" width="8%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col04"/></th>
				<th scope="col" width="10%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col05"/></th>
				<th scope="col" width="6%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col06"/></th>
				<th scope="col" width="6%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col07"/></th>
				<th scope="col" width="6%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col08"/></th>
				<th scope="col" width="6%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col09"/></th>
				<th scope="col" width="6%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col10"/></th>
				<th scope="col" width="6%"><ikep4j:message pre="${knowledgeRankingInfoPrefix}" key="table.title.col11"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${knowledgeMonitorCviPointList}">
			<tr>
				<td>${item.rankNumber}</td>
				<td>${item.moduleName}</td>
				<td class="textLeft"><div class="ellipsis"><a href="#a" onclick="itemPopup('${item.itemUrl}');">${item.title}</a></div></td>
				<td><div class="ellipsis"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.registerId}');">${item.userName}</a></div></td>
				<td><ikep4j:timezone date="${item.registDate}" pattern="yyyy.MM.dd"/></td>
				<td><fmt:formatNumber value="${item.cviScore}"/></td>
				<td><fmt:formatNumber value="${item.hitCount}"/></td>
				<td><fmt:formatNumber value="${item.recommendCount}"/></td>
				<td><fmt:formatNumber value="${item.linereplyCount}"/></td>
				<td><fmt:formatNumber value="${item.favoriteCount}"/></td>
				<td><fmt:formatNumber value="${item.distributeCount}"/></td>
			</tr>
		</c:forEach>
		<c:if test="${0 eq fn:length(knowledgeMonitorCviPointList)}">
			<tr>
				<td colspan="11" class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<!--Page Numbur Start-->
	<c:if test="${0 ne fn:length(knowledgeMonitorCviPointList)}">
	<%@ include file="/WEB-INF/view/common/page.jsp"%>
	</c:if>
</div>
<!--blockListTable Start-->

<script type="text/javascript">
//<![CDATA[

var searchBtnHandler = function() {
	$jq("#searchForm").submit();
	$jq("#mainContents").ajaxLoadStart();
};

//페이지
var pageSubmit = function(page) {
	$jq("#searchForm input[name=requestPage]").val(page);

	searchBtnHandler();
};

var itemPopup = function(url) {
	iKEP.popupOpen(iKEP.getContextRoot() + url, {width:800, height:500}, "itemPopup");
};


var itemTypeChangeHandler = function(item) {
	if ("week" == item) {
		$jq("#monthInfoDiv").hide();
		$jq("#weekInfoDiv").show();
	} else {
		$jq("#monthInfoDiv").show();
		$jq("#weekInfoDiv").hide();
	}
};


$jq(document).ready(function() {
	
	// 검색기간종류
	itemTypeChangeHandler("${pageCondition.itemType}");

	// 달력
	$jq("#fromDate").datepicker({hoverWeek:true,hoverStart:0,hoverWeekResult:"start"}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });
	$jq("#toDate").datepicker({hoverWeek:true,hoverStart:0,hoverWeekResult:"end"}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });


});

//]]>
</script>
