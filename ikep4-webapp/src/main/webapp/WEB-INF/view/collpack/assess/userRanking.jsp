<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="menuPrefix">ui.collpack.assess.menu</c:set>
<c:set var="messagePrefix">ui.collpack.assess.common.message</c:set>
<c:set var="pagePrefix">ui.collpack.assess.common.page</c:set>
<c:set var="tablePrefix">ui.collpack.assess.userRanking.table</c:set>

<form id="searchForm" method="post" action="">
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2><ikep4j:message pre="${menuPrefix}" key="userRanking"/></h2>
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
		<spring:bind path="pageCondition.pviOrder">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.countOfPage">
			<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre="${pagePrefix}" key="countOfPage"/>" onchange="searchHandler();">
				<option <c:if test="${status.value eq 10}">selected="selected"</c:if>>10</option>
				<option <c:if test="${status.value eq 15}">selected="selected"</c:if>>15</option>
				<option <c:if test="${status.value eq 20}">selected="selected"</c:if>>20</option>
				<option <c:if test="${status.value eq 30}">selected="selected"</c:if>>30</option>
				<option <c:if test="${status.value eq 50}">selected="selected"</c:if>>50</option>
			</select>
		</spring:bind>
		<div class="totalNum"><span><ikep4j:message pre="${pagePrefix}" key="total"/>&nbsp;<fmt:formatNumber value="${pageCondition.totalCount}"/></span></div>
	</div>
	<div class="listView_2">
		<ul>
			<li><a href="#a" onclick="excelBtnHandler();"><img src="<c:url value="/base/images/icon/ic_xls.gif"/>" alt="<ikep4j:message pre="${messagePrefix}" key="excel"/>" style="padding-top:2px;"/></a></li>
		</ul>
	</div>
	<div class="tableSearch">
		<select title="<ikep4j:message pre="${messagePrefix}" key="searchOption"/>" name="tablesch_01">
			<option value=""><ikep4j:message pre="${messagePrefix}" key="searchOption.title"/></option>
		</select>
		<spring:bind path="pageCondition.userName">
			<input type="text" class="inputbox" title="inputbox" size="20" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<a href="#a" onclick="searchBtnHandler();" class="ic_search"><span><ikep4j:message pre="${messagePrefix}" key="search"/></span></a>
	</div>
	<div class="clear"></div>
</div>
<!--//tableTop End-->
</form>

<!--blockListTable Start-->
<div class="blockListTable">
	<table summary="<ikep4j:message pre="${tablePrefix}" key="summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="5%"><ikep4j:message pre="${tablePrefix}" key="col1"/></th>
				<th scope="col" width="12%"><ikep4j:message pre="${tablePrefix}" key="col2"/></th>
				<th scope="col" width="18%"><ikep4j:message pre="${tablePrefix}" key="col3"/></th>
				<th scope="col" width="15%"><ikep4j:message pre="${tablePrefix}" key="col4"/>&nbsp;<c:if test="${0 eq pageCondition.pviOrder}"><a href="#a" onclick="orderChangeHandler(1);"><img src="<c:url value="/base/images/icon/ic_tablesort_up.gif"/>" alt="<ikep4j:message pre="${messagePrefix}" key="asc"/>"/></a></c:if><c:if test="${1 eq pageCondition.pviOrder}"><a href="#a" onclick="orderChangeHandler(0);"><img src="<c:url value="/base/images/icon/ic_tablesort_down.gif"/>" alt="<ikep4j:message pre="${messagePrefix}" key="desc"/>"/></a></c:if></th>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="col5"/></th>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="col6"/></th>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="col7"/></th>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="col8"/></th>
				<th scope="col" width="10%"><ikep4j:message pre="${tablePrefix}" key="col9"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${assessmentManagementUserPviList}">
			<tr>
				<td><fmt:formatNumber value="${item.rank}"/></td>
				<td><div class="ellipsis"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.userId}');">${item.userName}&nbsp;${item.jobTitleName}</a></div></td>
				<td><div class="ellipsis">${item.teamName}</div></td>
				<td><fmt:formatNumber value="${item.pvi}"/></td>
				<td><fmt:formatNumber value="${item.contributionScore}"/></td>
				<td><fmt:formatNumber value="${item.participationScore}"/></td>
				<td><fmt:formatNumber value="${item.applicationScore}"/></td>
				<td><fmt:formatNumber value="${item.friendshipScore}"/></td>
				<td><fmt:formatNumber value="${item.influenceScore}"/></td>
			</tr>
		</c:forEach>
		<c:if test="${0 eq fn:length(assessmentManagementUserPviList)}">
			<tr>
				<td colspan="9" class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<!--Page Numbur Start-->
	<c:if test="${0 ne fn:length(assessmentManagementUserPviList)}">
	<%@ include file="/WEB-INF/view/common/page.jsp"%>
	</c:if>
<!--//Page Numbur End-->
</div>
<!--//blockListTable End-->

<script type="text/javascript">
//<![CDATA[

var excelBtnHandler = function() {
	$jq("#searchForm").attr("action", "<c:url value='/collpack/assess/userRankingExcel.do'/>");
	$jq("#searchForm").submit();
};

var orderChangeHandler = function(type) {
	$jq("#searchForm input[name=pviOrder]").val(type);
	searchHandler();
};

var searchHandler = function() {
	$jq("#searchForm").attr("action", "<c:url value='/collpack/assess/userRanking.do'/>");
	$jq("#searchForm").submit();
};

var searchBtnHandler = function() {
	$jq("#searchForm input[name=requestPage]").val(1);
	searchHandler();
};

//페이지
var pageSubmit = function(page) {
	$jq("#searchForm input[name=requestPage]").val(page);
	searchHandler();
};

$jq(document).ready(function() {
});

//]]>
</script>
