<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="buttonPrefix">ui.collpack.knowledgehub.common.button</c:set>
<c:set var="messagePrefix">ui.collpack.knowledgehub.common.message</c:set>
<c:set var="searchPrefix">ui.collpack.knowledgehub.common.search</c:set>
<c:set var="pagePrefix">ui.collpack.knowledgehub.common.page</c:set>
<c:set var="knowledgeMapMainPrefix">ui.collpack.knowledgehub.knowledgeMapMain</c:set>
<c:set var="popularListPrefix">ui.collpack.knowledgehub.knowledgePopularListView</c:set>

<div id="tagResult">

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${popularListPrefix}" key="subTitle"/></h2>
</div>
<!--//pageTitle End-->

<form id="searchForm" action="">
<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre="${popularListPrefix}" key="subTitle"/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="4%"><ikep4j:message pre='${searchPrefix}' key='title'/></th>
					<td scope="row">
						<!-- 
						<a class="button_s" href="#a" onclick="pageReflashMonthBetween(-6);"><span><ikep4j:message pre='${searchPrefix}' key='between.6month'/></span></a>
						 -->
						<a class="button_s" href="#a" onclick="pageReflashMonthBetween(-3);"><span><ikep4j:message pre='${searchPrefix}' key='between.3month'/></span></a>
						<a class="button_s" href="#a" onclick="pageReflashMonthBetween(-1);"><span><ikep4j:message pre='${searchPrefix}' key='between.1month'/></span></a>&nbsp;
						<div class="subInfo">
							<spring:bind path="pageCondition.fromDate">
								<input type="text" class="inputbox" name="${status.expression}" readonly="readonly" title="<ikep4j:message pre='${searchPrefix}' key='date.from'/>" value="${status.value}" size="10"/>&nbsp;<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${messagePrefix}' key='calender'/>"/>
							</spring:bind>
							&nbsp;~&nbsp;
							<spring:bind path="pageCondition.toDate">
								<input type="text" class="inputbox" name="${status.expression}" readonly="readonly" title="<ikep4j:message pre='${searchPrefix}' key='date.to'/>" value="${status.value}" size="10"/>&nbsp;<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${messagePrefix}' key='calender'/>"/>
							</spring:bind>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="searchBtn"><a href="#a" onclick="pageReflashDB(0);"><span>Search</span></a></div>
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
		<spring:bind path="pageCondition.reInit">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.searchFlag">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
		</spring:bind>
		<spring:bind path="pageCondition.countOfPage">
			<select name="${status.expression}" title="<ikep4j:message pre="${pagePrefix}" key="countOfPage"/>" onchange="pageReflash();">
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
<div class="blockListTable summaryView">
	<%@ include file="/WEB-INF/view/collpack/knowledgehub/common/knowledgeItemSummary.jsp"%>

	<!--Page Numbur Start-->
	<c:if test="${0 ne fn:length(knowledgeList)}">
	<%@ include file="/WEB-INF/view/common/page.jsp"%>
	</c:if>
	<!--//Page Numbur End-->
</div>
<!--//blockListTable End-->

</div>


<!-- Knowledge List 페이징-->
<script type="text/javascript">
//<![CDATA[

//페이지
var pageSubmit = function(page) {
	$jq("#searchForm input[name=requestPage]").val(page);

	$jq.ajax({
		url : iKEP.getContextRoot() + "/collpack/knowledgehub/personal/knowledgePopularListViewAjax.do",
		type : "get",
		data : $jq("#searchForm").serialize(),
		loadingElement : {container : "#workContents"},
		success : function(data, textStatus, jqXHR) {
			$jq("#workContents").empty();
			$jq("#workContents").html(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("knowledgePopularList Page Load Fail!!!");
		}
	});
};

//페이지 Reflash
var pageReflash = function() {
	$jq("#searchForm input[name=searchFlag]").val(0);
	$jq("#searchForm input[name=reInit]").val(true);
	var _page = $jq("#searchForm input[name=page]").val();
	pageSubmit(_page);
};

// Search 버튼 클릭
var pageReflashDB = function(flag) {
	$jq("#searchForm input[name=searchFlag]").val(flag);
	$jq("#searchForm input[name=reInit]").val(true);
	pageSubmit(1);
};

// 검색조건(최근1년, 최근6개월....) 버튼 클릭
var pageReflashMonthBetween = function(month) {
	pageReflashDB(month);
};

$jq(document).ready(function() {
	$jq("#searchForm input[name=fromDate]").datepicker({hoverWeek:true,hoverStart:0,hoverWeekResult:"start"}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });
	$jq("#searchForm input[name=toDate]").datepicker({hoverWeek:true,hoverStart:0,hoverWeekResult:"end"}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });
});
//]]>
</script>
