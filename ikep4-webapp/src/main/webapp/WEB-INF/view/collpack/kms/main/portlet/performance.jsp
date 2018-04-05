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
	    //var ticks1 = new Array();
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
		    	/* else{
		    		s2[idx2] = tempRegCnt[0];
		    		idx2++;
		    	}	 */
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
	   			/* else{
	   				ticks2[idx2] = tempTeam[0];
		   			idx2++;
	   			} */
	   			
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
	    	    
	    	    /* var plot2 = $.jqplot('chart2', [s2], {
	    		seriesColors: ['#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7'],
	    	        seriesDefaults:{
	    	            renderer:$.jqplot.BarRenderer,  // 차트 형태
	    	            pointLabels: { show: true },     // 포인트 표시 여부
	    	            rendererOptions: {
	    	                varyBarColor: true,
	    	                barDirection: 'horizontal'
	    	            }
	    	        },
	    	        axes: {
	    	            xaxis: {
	    	                renderer: $.jqplot.CategoryAxisRenderer,  // x축 표시
	    	                ticks: ticks2                // x축 범주 표시
	    	            },
	    	        	yaxis: {
	    	        		min : 0,
	    	        		max : 100
	    	        	}
	    	        },
	    			grid: {
	    			background: '#f1f6f9' , //그리드 배경색상
	    			
	    		   },
	    	        highlighter: { show: false }  // highlight 기능 비활성화
	    	    });  */
	});
	
	searchPerformance = function(){
	searchPerformanceBody($("#searchYear").val(),$("#searchMonth").val());
		/* $jq.ajax({
			url : '<c:url value="/collpack/kms/main/portlet/performanceSearch.do"/>',
			data : {searchYear:$("#searchYear").val(), searchMonth:$("#searchMonth").val()},
			type : "get",
			success : function(result) {
				
				gridView(result);
			},
			error : function() {
				
			}
		}); */

	}; 
	
	gridView = function(result){
		$("#chart1").html("");
		alert("${arrRegSumSearch}");
		var s1 = new Array();
		var ticks0 = new Array();
	    //var ticks1 = new Array();
	    var idx1 = 0;
	    var s2 = new Array();
	    var ticks2 = new Array();
	    var idx2 = 0;
		    <c:forEach var='regCnt' items='${arrRegSumSearch}' begin='0' step='1'>
		    	var tempRegCnt = '${regCnt}'.split("^");
		    	if(tempRegCnt[1] == "01"){
		    		ticks0[idx1] = tempRegCnt[0];
		    		idx1++;
		    	}
		    	/* else{
		    		s2[idx2] = tempRegCnt[0];
		    		idx2++;
		    	}	 */
	       	</c:forEach>
	    	
	       	idx1 = 0;
	       	idx2 = 0;
	   		 <c:forEach var='team'  items='${arrTeamSearch}' begin='0' step='1'>
	   			var tempTeam = '${team}'.split("^");
	   			var ticks1=[];
	   			if(tempTeam[1] == "01"){
	   				ticks1.push(ticks0[idx1]); 
	   			 	ticks1.push(tempTeam[0]+"&nbsp;&nbsp;&nbsp;"); 
	   			 	s1.push(ticks1);
		   			idx1++;
	   			}
	   			/* else{
	   				ticks2[idx2] = tempTeam[0];
		   			idx2++;
	   			} */
	   			
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
	    	    
	    	    /* var plot2 = $.jqplot('chart2', [s2], {
	    		seriesColors: ['#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7','#016bb7'],
	    	        seriesDefaults:{
	    	            renderer:$.jqplot.BarRenderer,  // 차트 형태
	    	            pointLabels: { show: true },     // 포인트 표시 여부
	    	            rendererOptions: {
	    	                varyBarColor: true,
	    	                barDirection: 'horizontal'
	    	            }
	    	        },
	    	        axes: {
	    	            xaxis: {
	    	                renderer: $.jqplot.CategoryAxisRenderer,  // x축 표시
	    	                ticks: ticks2                // x축 범주 표시
	    	            },
	    	        	yaxis: {
	    	        		min : 0,
	    	        		max : 100
	    	        	}
	    	        },
	    			grid: {
	    			background: '#f1f6f9' , //그리드 배경색상
	    			
	    		   },
	    	        highlighter: { show: false }  // highlight 기능 비활성화
	    	    });  */
	};
	
})(jQuery);
</script>
	
	<div class="kmbox">
	<div class="tableList_3 mb15">
	<!--subTitle_1 Start-->
	<div class="subTitle_1a">
		<h3>
			부문별 달성율(%)
		</h3>		
	</div>
    <select name="searchYear" id="searchYear" onchange="javascript: searchPerformance();">                               		
       <c:set var="compareYear" value="${searchYear}" />
       <c:if test="${empty compareYear}">
       	<c:set var="compareYear" value="${nowYear}" />
       </c:if>
       <c:forEach var="year" begin="2015" end="${nowYear}" step="1">
       	<option value="${year}" <c:if test="${year eq compareYear}">selected="selected"</c:if>>${year}</option>
       </c:forEach>
    </select>년                       	
    <select name="searchMonth" id="searchMonth" onchange="javascript: searchPerformance();">                               		
     <c:set var="compareMonth" value="${searchMonth}" />
     <c:if test="${empty compareMonth}">
     	<c:set var="compareMonth" value="${nowMonth}" />
     </c:if>
       <option value="01" <c:if test="${'01' eq compareMonth}">selected="selected"</c:if>>1</option>
       <option value="02" <c:if test="${'02' eq compareMonth}">selected="selected"</c:if>>2</option>
       <option value="03" <c:if test="${'03' eq compareMonth}">selected="selected"</c:if>>3</option>
       <option value="04" <c:if test="${'04' eq compareMonth}">selected="selected"</c:if>>4</option>
       <option value="05" <c:if test="${'05' eq compareMonth}">selected="selected"</c:if>>5</option>
       <option value="06" <c:if test="${'06' eq compareMonth}">selected="selected"</c:if>>6</option>
       <option value="07" <c:if test="${'07' eq compareMonth}">selected="selected"</c:if>>7</option>
       <option value="08" <c:if test="${'08' eq compareMonth}">selected="selected"</c:if>>8</option>
       <option value="09" <c:if test="${'09' eq compareMonth}">selected="selected"</c:if>>9</option>
       <option value="10" <c:if test="${'10' eq compareMonth}">selected="selected"</c:if>>10</option>
       <option value="11" <c:if test="${'11' eq compareMonth}">selected="selected"</c:if>>11</option>
       <option value="12" <c:if test="${'12' eq compareMonth}">selected="selected"</c:if>>12</option>
    </select>월		                       	
	<div style="width:60%;font-weight:bold;font-size:14px;padding-left:10px;margin-top:10px;"><span></span></div>
	<div id="chart1" style="width:90%; height:300px;"></div>
	<!-- <div id="chart2" style="margin-top:20px; margin-left:20px; width:90%; height:200px;"></div> -->
</div>	
</div>				
<div class="kmboxline"></div>
