<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<link type="text/css" rel="stylesheet" href="<c:url value="/base/css/syntaxhighlighter_3.0.83/shCoreDefault.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.4.4.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.7.custom.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/syntaxhighlighter_3.0.83/shCore.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/syntaxhighlighter_3.0.83/shBrushJScript.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/syntaxhighlighter_3.0.83/shBrushXml.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/ko.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
 
<script type="text/javascript"> 
(function($) {
	
	$.jqplot.config.enablePlugins = true;
	var visitorChartDataList, visitorChartDataList2;
	
	loadingVisitorStaticChart = function() {
		
		visitorChartDataList = [];
		visitorChartDataList2 = [];
		<c:forEach var="cafe" items="${cafeDateCountList}" varStatus="status">
			var innnerCharData = ['${cafe.baseDate}',${cafe.visitCount}];
			var innnerCharData2 = ['${cafe.baseDate}',${cafe.boardItemCount}];
			visitorChartDataList.push( innnerCharData );
			visitorChartDataList2.push( innnerCharData2 );
		</c:forEach>

		plot1 = $.jqplot('chart1',[visitorChartDataList, visitorChartDataList2] ,{
           title: '<ikep4j:message pre='${prefix}' key='dateCount' />',
           legend: {
                show:true,
                location: 'nw',
                yoffset: 6,
                labels:['<ikep4j:message pre="${prefix}" key="visitCount" />','<ikep4j:message pre="${prefix}" key="boardItemCount" />']
           },  // 범주 옵션
           axes: {
               xaxis: {
                   renderer: $.jqplot.CategoryAxisRenderer, 
                   tickOptions: {
                	   //formatString: '%m-%d'
                   }
               },
               yaxis: {
                   tickOptions: {
                       formatString: '%.0f'
                   },
                   min:0
               }
           }
       	});
	};
	
	
	$jq(document).ready(function() {
		iKEP.iFrameContentResize(); 
		loadingVisitorStaticChart();
	});
})(jQuery);
</script>		
			
			


<!--skipNavigation Start-->
<div id="skipNavigation">
</div>
<!--//skipNavigation End-->			
			
<!--wrapper Start-->
<div id="wrapper">
		
		<!--blockMain Start-->
		<div id="blockMain">

			
				<h3><ikep4j:message pre='${prefix}' key='dateCount' /></h3>
				
				<div class="blockBlank_20px"></div>
				
				<div class="visit_graph" style="width:100%">
					<span title="<ikep4j:message pre='${prefix}' key='dateCount' />">
						<div id="chart1" style="margin-top:-120px; margin-left:10px; margin-bottom:0px; width:95%; height:250px;"></div>
					</span>
				</div>

			<div class="clear"></div>
			
		</div>
		<!--//blockMain End-->
	
	
</div>
<!--//wrapper End-->
