<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pieRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>

<script type="text/javascript"> 

(function($) {
	$jq(document).ready(function() {
		
		var s1 = new Array();
		var ticks0 = new Array();
	    var idx1 = 0;
	    var s2 = new Array();
	    var ticks2 = new Array();
	    var idx2 = 0;
		    <c:forEach var='regCnt' items='${arrRegSum}' begin='0' step='1'>
		    	var tempRegCnt = '${regCnt}'.split("^");
		    	if(tempRegCnt[1] == "01"){
		    		ticks0[idx1] = tempRegCnt[0];
		    		idx1++;
		    	}
	       	</c:forEach>
	    	
	       	idx1 = 0;
	       	idx2 = 0;
	   		 <c:forEach var='team'  items='${arrTeam}' begin='0' step='1'>
	   			var tempTeam = '${team}'.split("^");
	   			var ticks1=[];
	   			if(tempTeam[1] == "01"){
	   				ticks1.push(ticks0[idx1]); 
	   			 	ticks1.push(tempTeam[0]+"&nbsp;&nbsp;&nbsp;"); 
	   			 	s1.push(ticks1);
		   			idx1++;
	   			}
	   			
	       	</c:forEach>	
	  
	     
	       	var plot1 = $.jqplot('chart1', [s1], {
	    		seriesColors: ['#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7'],
	    	        seriesDefaults:{
	    	            renderer:$.jqplot.BarRenderer,  // 차트 형태
	    	            pointLabels: { show: true },     // 포인트 표시 여부
	    	            rendererOptions: {
	    	                varyBarColor: true,
	    	                barDirection: 'horizontal',
	    	                barWidth:10
	    	            },
	    	            shadow : false
	    	        },
	    	        axes: {
	    	             xaxis: {
	    	            	min : 0,
	    	        		max : 125,
	    	        		numberTicks: 6,
	    	        		tickOptions : {formatString : '%d'}
	    	        		
	    	            }, 
	    	        	yaxis: {
	    	        		
	    	        		renderer: $.jqplot.CategoryAxisRenderer  // x축 표시
	    	               //ticks: ticks1                // x축 범주 표시
	    	        	}
	    	        },
	    			grid: {
	    			background: '#f1f6f9' , //그리드 배경색상
	    			
	    		   },
	    	        highlighter: { show: false }  // highlight 기능 비활성화
	    	    }); 
	    	    
	});
	
	
})(jQuery);
</script>
	
