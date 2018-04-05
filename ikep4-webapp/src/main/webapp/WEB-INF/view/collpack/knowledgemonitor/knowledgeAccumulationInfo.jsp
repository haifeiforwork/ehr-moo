<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgemonitor.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgemonitor.common.button</c:set>
<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>
<c:set var="knowledgeAccumulationInfoPrefix">ui.collpack.knowledgemonitor.knowledgeAccumulationInfo</c:set>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="knowledgeAccumulationInfo"/></h2>
</div>
<!--//pageTitle End-->

<form id="searchForm" method="post" action="">
	<input type="hidden" name="notFirstAccess" value="${searchCondition.notFirstAccess}"/>
<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="table.summary"/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.module"/></th>
					<td scope="row" style="padding:0;">
					<c:forEach var="item" items="${knowledgeMonitorModuleList}">
						<c:if test="${1 eq item.isUsage}">
							<input title="${item.moduleName}" value="${item.moduleCode}" <c:if test="${item.checked}">checked="checked"</c:if> name="moduleCodes" class="checkbox" type="checkbox"/>&nbsp;${item.moduleName}&nbsp;
						</c:if>
					</c:forEach>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.date"/></th>
					<td scope="row" style="padding:0;">
						<select name="itemType" onchange="itemTypeChangeHandler(this.value);" style="float:left; margin-right:3px;">
							<option value="month" <c:if test="${'month' eq searchCondition.itemType}">selected="selected"</c:if>><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.date.type.month"/></option>
							<option value="week" <c:if test="${'week' eq searchCondition.itemType}">selected="selected"</c:if>><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.date.type.week"/></option>
						</select>
						<div class="subInfo">
							<div id="weekInfoDiv" style="display:none; width:300px; float:left;">
								<input type="text" class="inputbox" id="fromDate" name="fromDate" readonly="readonly" title="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.fromDate"/>" value="${searchCondition.fromDate}" size="10" /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.fromDate"/>"/>
								&nbsp;~&nbsp;
								<input type="text" class="inputbox" id="toDate" name="toDate" readonly="readonly" title="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.toDate"/>" value="${searchCondition.toDate}" size="10" /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.toDate"/>"/>
							</div>
							<div id="monthInfoDiv" style="display:none; width:300px; float:left;">
								<select name="fromYear">
								<c:forEach var="item" begin="2010" end="2030" step="1">
									<option value="${item}" <c:if test="${item eq searchCondition.fromYear}">selected="selected"</c:if>>${item}</option>
								</c:forEach>
								</select>
								<select name="fromMonth">
									<option value="01" <c:if test="${'01' eq searchCondition.fromMonth}">selected="selected"</c:if>>01</option>
									<option value="02" <c:if test="${'02' eq searchCondition.fromMonth}">selected="selected"</c:if>>02</option>
									<option value="03" <c:if test="${'03' eq searchCondition.fromMonth}">selected="selected"</c:if>>03</option>
									<option value="04" <c:if test="${'04' eq searchCondition.fromMonth}">selected="selected"</c:if>>04</option>
									<option value="05" <c:if test="${'05' eq searchCondition.fromMonth}">selected="selected"</c:if>>05</option>
									<option value="06" <c:if test="${'06' eq searchCondition.fromMonth}">selected="selected"</c:if>>06</option>
									<option value="07" <c:if test="${'07' eq searchCondition.fromMonth}">selected="selected"</c:if>>07</option>
									<option value="08" <c:if test="${'08' eq searchCondition.fromMonth}">selected="selected"</c:if>>08</option>
									<option value="09" <c:if test="${'09' eq searchCondition.fromMonth}">selected="selected"</c:if>>09</option>
									<option value="10" <c:if test="${'10' eq searchCondition.fromMonth}">selected="selected"</c:if>>10</option>
									<option value="11" <c:if test="${'11' eq searchCondition.fromMonth}">selected="selected"</c:if>>11</option>
									<option value="12" <c:if test="${'12' eq searchCondition.fromMonth}">selected="selected"</c:if>>12</option>
								</select>
								&nbsp;~&nbsp;
								<select name="toYear">
								<c:forEach var="item" begin="2011" end="2030" step="1">
									<option value="${item}" <c:if test="${item eq searchCondition.toYear}">selected="selected"</c:if>>${item}</option>
								</c:forEach>
								</select>
								<select name="toMonth">
									<option value="01" <c:if test="${'01' eq searchCondition.toMonth}">selected="selected"</c:if>>01</option>
									<option value="02" <c:if test="${'02' eq searchCondition.toMonth}">selected="selected"</c:if>>02</option>
									<option value="03" <c:if test="${'03' eq searchCondition.toMonth}">selected="selected"</c:if>>03</option>
									<option value="04" <c:if test="${'04' eq searchCondition.toMonth}">selected="selected"</c:if>>04</option>
									<option value="05" <c:if test="${'05' eq searchCondition.toMonth}">selected="selected"</c:if>>05</option>
									<option value="06" <c:if test="${'06' eq searchCondition.toMonth}">selected="selected"</c:if>>06</option>
									<option value="07" <c:if test="${'07' eq searchCondition.toMonth}">selected="selected"</c:if>>07</option>
									<option value="08" <c:if test="${'08' eq searchCondition.toMonth}">selected="selected"</c:if>>08</option>
									<option value="09" <c:if test="${'09' eq searchCondition.toMonth}">selected="selected"</c:if>>09</option>
									<option value="10" <c:if test="${'10' eq searchCondition.toMonth}">selected="selected"</c:if>>10</option>
									<option value="11" <c:if test="${'11' eq searchCondition.toMonth}">selected="selected"</c:if>>11</option>
									<option value="12" <c:if test="${'12' eq searchCondition.toMonth}">selected="selected"</c:if>>12</option>
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

<div class="mb5">
	<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.message"/>		
	<span style="margin-right:5px;float:right;"><strong><input name="sumItem" class="checkbox" type="checkbox" value="1" <c:if test="${1 eq searchCondition.sumItem}">checked="checked"</c:if> onchange="searchBtnHandler();"/><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="search.accumulate"/>&nbsp;</strong></span>
</div>
<div class="clear"></div>

<!--graph Start-->
<div class="corner_RoundBox02">
	<div class="usage_graph" style="text-align:center;">
		<div id="chart1" style="height:400px;"></div>
	</div>
	<div class="clear"></div>
	<div class="l_t_corner"></div>
	<div class="r_t_corner"></div>
	<div class="l_b_corner"></div>
	<div class="r_b_corner"></div>
</div>
<!--//graph End-->
</form>

<div class="blockBlank_20px"></div>

<!--tableTop Start-->
<div class="tableTop">
	<div class="listInfo">
		<div class="totalNum"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="list.title"/></div>
	</div>
	<div class="tableSearch">
		<a href="#a" onclick="excelBtnHandler();"><img src="<c:url value="/base/images/icon/ic_xls.gif"/>" alt="<ikep4j:message pre="${messagePrefix}" key="excel"/>" /></a>
	</div>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--blockListTable Start-->
<div class="blockDetail">
	<table summary="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="table.summary"/>">
		<caption></caption>
		<thead>
			<tr>
				<th class="textCenter" scope="col"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="table.title.col1"/></th>
				<th class="textCenter" scope="col"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="table.title.col2"/></th>
				<c:forEach var="item" items="${knowledgeMonitorModuleList}">
					<c:if test="${item.checked}">
					<th class="textCenter" scope="col">${item.moduleName}</th>
					</c:if>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="listItem" items="${knowledgeMonitorAccumulationList}">
			<tr>
				<td class="textCenter">${listItem.ymd}</td>
				<td class="textRight"><fmt:formatNumber value="${listItem.publicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.allDocCount}"/>&nbsp;&nbsp;</td>
				<c:forEach var="moduleItem" items="${knowledgeMonitorModuleList}">
					<c:if test="${moduleItem.checked}">
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_BBS eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.bdPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.bdAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_CAFE eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.cfPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.cfAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_VOCABULARY eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.cvPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.cvAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_FORUM eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.frPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.frAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_IDEATION eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.idPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.idAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_QA eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.qaPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.qaAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_SOCIAL_BLOG eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.sbPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.sbAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.wmPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.wmAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
						<c:if test="${IKepConstant.ITEM_TYPE_CODE_WORK_SPACE eq moduleItem.moduleCode}">
							<td class="textRight"><fmt:formatNumber value="${listItem.wsPublicDocCount}"/>&nbsp;&nbsp;<br/><fmt:formatNumber value="${listItem.wsAllDocCount}"/>&nbsp;&nbsp;</td>
						</c:if>
					</c:if>
				</c:forEach>
			</tr>
			</c:forEach>
		</tbody>
	</table>							
</div>
<!--//blockListTable End-->

<script type="text/javascript">
//<![CDATA[

$jq(document).ready(function() {
	// 검색기간종류
	itemTypeChangeHandler("${searchCondition.itemType}");

	// 달력
	$jq("#fromDate").datepicker({hoverWeek:true,hoverStart:0,hoverWeekResult:"start"}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });
	$jq("#toDate").datepicker({hoverWeek:true,hoverStart:0,hoverWeekResult:"end"}).next().eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });

<c:if test="${0 lt fn:length(knowledgeMonitorAccumulationChartList)}">
	// 차트
	$jq("#chart1").width($jq(".usage_graph").width()*0.98);

	$jq.jqplot.config.enablePlugins = true;

	// 공개
	var line1 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">['${chartItem.ymd}', ${chartItem.publicDocCount}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
	// 전체
	var line2 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">['${chartItem.ymd}', ${chartItem.allDocCount}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];

	plot1 = $jq.jqplot('chart1', [line1, line2], {
	    seriesDefaults: {
	    	renderer:$jq.jqplot.BarRenderer,
	    	rendererOptions:{barPadding:10, barMargin:10}
		},
	    legend: {
	    	show:true,
	    	location: 'nw',
	    	labels:['<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="chart.legend.public"/>','<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="chart.legend.all"/>']
	    },
	    axes:{
	    	xaxis:{
	    		renderer:$jq.jqplot.CategoryAxisRenderer
	    	},
	    	yaxis:{
	    		min:0,
	    		tickOptions:{formatString:'%d'}
	    	}
	    }
	});
</c:if>

});

//]]>
</script>

<script type="text/javascript">
//<![CDATA[

var searchBtnHandler = function() {
	$jq("#searchForm").attr("action", "<c:url value='/collpack/knowledgemonitor/knowledgeAccumulationInfo.do'/>");
	$jq("#searchForm").submit();
	$jq("#mainContents").ajaxLoadStart();
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

var excelBtnHandler = function() {
	$jq("#searchForm").attr("action", "<c:url value='/collpack/knowledgemonitor/knowledgeAccumulationInfoExcel.do'/>");
	$jq("#searchForm").submit();
};

//]]>
</script>
