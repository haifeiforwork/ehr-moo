<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="knowledgeAccumulationInfoPrefix">ui.collpack.knowledgemonitor.knowledgeAccumulationInfo</c:set>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>

<div id="${portletConfigId}">
	<div class="" style="text-align:center;">
		<div style="height:20px;">
			<table align="right" summary="<ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="table.summary"/>">
				<tbody>
					<tr class="jqplot-table-legend">
						<td style="text-align:center;" class="jqplot-table-legend">
							<div>
								<div style="background-color:rgb(130, 188, 36);border-color: rgb(130, 188, 36);" class="jqplot-table-legend-swatch"></div>
							</div>
						</td>
						<td style="padding-top:0pt;" class="jqplot-table-legend"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="chart.legend.public"/></td>
						<td style="text-align: center;" class="jqplot-table-legend">
							<div>
								<div style="background-color: rgb(54, 54, 54); border-color: rgb(54, 54, 54);" class="jqplot-table-legend-swatch"></div>
							</div>
						</td>
						<td style="padding-top:0pt;" class="jqplot-table-legend"><ikep4j:message pre="${knowledgeAccumulationInfoPrefix}" key="chart.legend.all"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div id="chart1" style="height:250px;"></div>			
	</div>
</div>

<script type="text/javascript">
//<![CDATA[
$jq(document).ready(function() {

<c:if test="${0 lt fn:length(knowledgeMonitorAccumulationChartList)}">
	// 차트
	$jq.jqplot.config.enablePlugins = true;

	// 공개
	var line1 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">['${chartItem.ymd}', ${chartItem.publicDocCount}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
	// 전체
	var line2 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">['${chartItem.ymd}', ${chartItem.allDocCount}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];

	plot1 = $jq.jqplot('chart1', [line1, line2], {
		
		grid:{
			drawGridlines:false,
			background: '#ffffff',
			borderColor: '#555',
	        borderWidth: 0,
	        shadow: false
		},
		seriesColors: ["#82BC24","#363636"],
	    seriesDefaults:{
	    	shadow: true,
	    	shadowAngle: 65,
	    	renderer:$jq.jqplot.BarRenderer, 
	    	rendererOptions:{barMargin:10, barWidth: 20},
	    	pointLabels: {show: true, formatString: '%d'}
		},
	    axes:{
	    	xaxis:{
	    		renderer:$jq.jqplot.CategoryAxisRenderer,
	    		tickOptions:{
	                showGridline:false, 
	                markSize:0
	            }
	    	},
	    	yaxis: {
	    		showTicks: false,
	    		min:0,
	    		//max:1500
	    		//ticks:[0, 1500], 
                //tickOptions:{formatString:'%.0f'}
        	}
	    }
	});
</c:if>

});
//]]>
</script>
