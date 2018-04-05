<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preManageMenu" value="ui.socialpack.socialblog.management.menu" />
<c:set var="preManageDesign" value="ui.socialpack.socialblog.management.designsetting" />
<c:set var="preManageStat" value="ui.socialpack.socialblog.management.statistics" />

<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript">

(function($){

	$.jqplot.config.enablePlugins = true;
	var visitorChartDataList, categoryDataList;
	
	loadingVisitorStaticChart = function() {
		
		visitorChartDataList = [];
		categoryDataList = [];
		
		<c:forEach var="visitList" items="${socialBlogVisitList}" varStatus="status">
			var innnerCharData = ${visitList.visitorCount};
			visitorChartDataList.push( innnerCharData );
			var innnerCatetoryData = ${visitList.visitBaseDate};
			categoryDataList.push( innnerCatetoryData );
		</c:forEach>

		plot1 = $.jqplot('chart1',[visitorChartDataList],{
           title: '<ikep4j:message pre="${preManageStat}" key="visitor.title" />',
           legend: {
                show:true,
                location: 'nw',
                yoffset: 6,
                labels:['<ikep4j:message pre="${preManageStat}" key="visitor.menu2" />']
           },
           axes: {             
				yaxis: {                 
					tickOptions: {
	                       formatString: '%.0f'
	                   },
	                   min:0
				},
				xaxis: {
					renderer: $.jqplot.CategoryAxisRenderer,
					ticks: categoryDataList
              }
			}    
       	});
	};
	
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		loadingVisitorStaticChart();
		// 화면 로딩시 각각 페이지 호출 종료
		
		//<ikep4j:message pre='${preButton}' key='calendar' />
		$("#datetext").datepicker({
			//showOn: "button",
			//buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			//buttonImageOnly: false,
			//showOtherMonths : true,
			selectOtherMonths: true,
			hoverWeek : true,
			onSelect: function(date) {
				$("#weeklyListForm input[name=weeklyTerm]").val(date);
				viewVisitorStaticChart(date,'${baseDateType}','');
			}
		}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

	});
	
})(jQuery);

</script>

						<form name="weeklyListForm" id="weeklyListForm" action="" >
						<input name="weeklyTerm" type="hidden" value="${weeklyTerm}" />
						<div class="fc" id="calendar">
							<table class="fc-header"  style="width:100%;" summary="<ikep4j:message pre='${preManageStat}' key='visitor.menu2' />">
								<caption></caption>
								<tbody>
									<tr>
										<td style="width:160px;"></td>
										<td class="fc-header-center">
											<div id="datepicker"></div><!-- dayFlag= -->
											<span class="fc-button-prev"><a href="#a" onclick="viewVisitorStaticChart('${weeklyTerm}','${baseDateType}','prev');"><span class="none"><ikep4j:message pre='${preManageStat}' key='visitor.chart.before' /></span></a></span>
											<div class="fc-header-title">
											<h2>${weeklyTerm}</h2>
											</div>
											<span class="fc-button-next"><a href="#a" onclick="viewVisitorStaticChart('${weeklyTerm}','${baseDateType}','next');"><span class="none"><ikep4j:message pre='${preManageStat}' key='visitor.chart.after' /></span></a> </span>
											<c:if test="${baseDateType == 'DAILY'}"> 
											<span style="position:absolute;">
													<span class="btn_cal">
														<input type="hidden" id="datetext" /><a class="button_s" href="#a"><span><img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preButton}' key='calendar' />"/></span></a>
													</span>
											</span>
											</c:if>
										</td>
										<td class="fc-header-right">
											<ul>
												<li><a class="button_s" href="#a" onclick="viewVisitorStaticChart('','DAILY','');"><span><ikep4j:message pre='${preManageStat}' key='visitor.chart.byDay' /></span></a></li>
												<li><a class="button_s" href="#a" onclick="viewVisitorStaticChart('','MONTH','');"><span><ikep4j:message pre='${preManageStat}' key='visitor.chart.byMonth' /></span></a></li>
		
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						</form>
						
						<div class="visit_graph">
							<span title="<ikep4j:message pre='${preManageStat}' key='visitor.chart.area' />">
								<div id="chart1" style="margin-top:-120px; margin-left:80px; margin-bottom:0px; width:500px; height:300px;"></div>
							</span>
						</div>
