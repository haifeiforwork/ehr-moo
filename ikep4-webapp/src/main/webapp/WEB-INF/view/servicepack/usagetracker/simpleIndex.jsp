<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />
<c:set var="preUi" value="ui.servicepack.usagetracker.main" />
<c:set var="preCommon" value="ui.servicepack.usagetracker.common" />

<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pieRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pieRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>

<script  type="text/javascript">
<!--
(function($) {
	
	var week = ['<ikep4j:message pre='${preCommon}' key='day.1'/>',
	 			'<ikep4j:message pre='${preCommon}' key='day.2'/>',
	 			'<ikep4j:message pre='${preCommon}' key='day.3'/>',
	 			'<ikep4j:message pre='${preCommon}' key='day.4'/>',
	 			'<ikep4j:message pre='${preCommon}' key='day.5'/>',
	 			'<ikep4j:message pre='${preCommon}' key='day.6'/>',
	 			'<ikep4j:message pre='${preCommon}' key='day.7'/>'];
	
	var plot1,plot2,plot3,plot4;
	
	$(document).ready(function() {
			
		grathWidthSet();
		
		$(window).resize(function() {
			reGrathWidthSet();
		});
				
		
		
		
		$.post('<c:url value="/servicepack/usagetracker/main/ajax/1/report.do"/>')
		 .success(function(data) {
				 var s1 = [];
				 $(data).each(function(index){
					 var ticks = [];
					 ticks.push(this.usageCount); 
					 ticks.push(this.moduleId); 
					 s1.push(ticks);
				 });
			 
				 printMenu(s1);
		 })
		 .error(function(event, request, settings) { alert("error"); }); 
		
		
		
	});
	
	
	grathWidthSet = function(){
		var leftWidth = $(".blockLeft").width();
		$("#loginStatsGraph").width(leftWidth-20);
		leftWidth = Math.floor(leftWidth*0.95);
		$("#menuUsageGraph").width(leftWidth);
		
		var rightWidth = $(".blockRight").width();
		$("#browserStatsGraph").width(rightWidth-20);
		rightWidth = Math.floor(rightWidth*0.95);
		$("#portletusageGraph").width(rightWidth);
	};	
	
	reGrathWidthSet = function(){
		var leftWidth = $(".blockLeft").width();
		$("#loginStatsGraph").width(leftWidth-20);
		leftWidth = Math.floor(leftWidth*0.95);
		$("#menuUsageGraph").width(leftWidth);
		
		var rightWidth = $(".blockRight").width();
		$("#browserStatsGraph").width(rightWidth-20);
		rightWidth = Math.floor(rightWidth*0.95);
		$("#portletusageGraph").width(rightWidth);
		
		plot1.replot();
		plot2.replot();
		plot3.replot();
		plot4.replot();
		
	};
	
	var printLogin = function(s1,ticks){
		plot1 = $.jqplot('loginStatsGraph', [s1] , {
			grid: {
   	    		drawGridlines: false,
   	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false	
   	    	},   	    	
   	    	seriesColors: ["#363636"],
   	    	seriesDefaults:{
   	    		shadow: true,   // show shadow or not.
		        shadowAngle: 70,    // angle (degrees) of the shadow, clockwise from x axis.
   	    		renderer:$.jqplot.BarRenderer,  // 차트 형태
   	    		rendererOptions: {
   	    			barWidth: 35
   				},
   	    		pointLabels: { show: true }     // 포인트 표시 여부
   	    	},
   	    	axes: {
   	    		xaxis: {
   	    			renderer: $.jqplot.CategoryAxisRenderer,  // x축 표시
   	    			ticks: ticks,                // x축 범주 표시
   	    			tickOptions:{
		                markSize:0
		            }
   	    		},
   	    		yaxis: {
   	    			min:0,
	                showTicks: false,
		    		tickOptions:{formatString:'%.0f'}
	        	}
   	    	}
			/*
			grid: {drawGridlines: false},
   	    	seriesDefaults:{
                renderer:$.jqplot.BarRenderer,
                pointLabels: { show: true , formatString: '%d'}
            },
            axes: {
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: ticks
                },
                yaxis:{min:0}
            },
            highlighter: { show: false } */
   	    });	
	};
	
	var printBrowser = function(s1){
		plot2 = $.jqplot('browserStatsGraph', [s1], {
	    	grid: {
	    		drawGridlines: false,
   	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false
	    	},
	    	title:'',   // 차트 타이틀
	    	seriesColors:['#93c8fb', '#ff66fd', '#ffcc66', '#99cc66', '#d099fe','#fe9798','#2dc8fe','#fdf701','#d3ca6b'],
	    	seriesDefaults:{
	    		shadow: true,   // show shadow or not.
		        shadowAngle: 45,    // angle (degrees) of the shadow, clockwise from x axis.
	    		renderer:$.jqplot.PieRenderer,
	    		rendererOptions: {
	    			sliceMargin:2,   // 파이 조각 표시 시 마진값
	    			showDataLabels: true,
	    			dataLabelThreshold: 1,
	                dataLabelFormatString: '%.1f%%'
	    		}
	    	},
	    	legend: {
	    		show: true,
	    		location: 'e',
	    		escapeHTML:true
	    	}
	    }); 
	};
	
	
	var printMenu = function(s1){
     	plot3 = $.jqplot('menuUsageGraph', [s1], {
    		grid: {
	    		drawGridlines: false,
	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false	
	    	}, 
	    	seriesColors: ["#99cc66"],
	    	seriesDefaults: {
	    		renderer:$.jqplot.BarRenderer,
	    		pointLabels: { 
	    			show: true, 
	    			location: 'e',  // 포인트 e(왼쪽)에 표시
	    			edgeTolerance: -15,
	    			
	    		},
	    		shadow:false,
	    		shadowAngle: 90,    // 그림자 각도
	    		rendererOptions: {
    				barDirection: 'horizontal'  // bar 수평표시
    			}
    		},
    		axes: {
    			xaxis:{
    				min:0,
    				showTicks: true,
   	    			tickOptions:{
		                formatString:'%.0f'
		            }
    			},
    			yaxis: {
    				min:0,
    				tickOptions:{markSize:0},
    				renderer: $.jqplot.CategoryAxisRenderer
    			}
    		}
	   });
	};
	
	var printPortlet = function(s1){
    	plot4 = $.jqplot('portletusageGraph', [s1], {
	    	grid: {
	    		drawGridlines: false,
	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false	
	    	}, 
	    	seriesColors: ["#7f7dff"],
    		seriesDefaults: {
	    		renderer:$.jqplot.BarRenderer,
	    		pointLabels: { show: true, location: 'e', edgeTolerance: -15 }, // 포인트 e(왼쪽)에 표시
	    		shadow:false,
	    		shadowAngle: 135,    // 그림자 각도
	    		rendererOptions: {
    				barDirection: 'horizontal'  // bar 수평표시
    			}
    		},
    		axes: {
    			xaxis:{
    				min:0,
    				showTicks: true,
   	    			tickOptions:{
		                formatString:'%.0f'
		            }
    			},
    			yaxis: {
    				min:0,
    				tickOptions:{markSize:0},
    				renderer: $.jqplot.CategoryAxisRenderer
    			}
    		}
    	});	
	};
	
})(jQuery);

-->
</script>

	<h1 class="none">컨텐츠영역</h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${preUi}' key='pagetitle'/></h2>					
	</div>
	<!--//pageTitle End-->
	
	<div class="blockBlank_5px"></div>
	
	<!--blockLeft Start-->
	<div class="blockLeft" style="width:49%">
		<div class="gray_box">			
			<div class="box_title">
				<h3><ikep4j:message pre='${preUi}' key='menuUsageRank'/></h3>
				<div class="r_btns"><a href="<c:url value='/servicepack/usagetracker/rank/simpleMenu.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
			</div>
			<div class="usage_graph">
				<div id="menuUsageGraph" style="margin-top:10px; margin-left:10px;margin-right:10px;height:350px;"></div>
			</div>
		</div>
	</div>
	<!--blockLeft End-->
	
