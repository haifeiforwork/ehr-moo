<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j"%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="preHeader"
	value="ui.support.searchpreprocessor.header" />
<c:set var="preButton"
	value="ui.support.searchpreprocessor.common.button" />
<c:set var="preLeft"
	value="ui.support.searchpreprocessor.common.left" />
	
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shCore.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shBrushJScript.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shBrushXml.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jquery.jqplot.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/excanvas.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.barRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.pointLabels.min.js'/>"></script>
<c:set var="preFiled"  value="message.support.searchpreprocessor.field" /> 
<c:set var="preRequired"  value="message.support.searchpreprocessor" /> 

<ikep4j:message pre="${preFiled}" key="searchKeyword" var="searchKeyword"/>
<ikep4j:message pre="${preFiled}" key="date" var="date"/>
<script type="text/javascript">
<!--
<c:choose>
<c:when test="${user.localeCode == portal.defaultLocaleCode}">
 var myLocale =true;
</c:when>
<c:otherwise>
var myLocale =false; 
</c:otherwise>
</c:choose>

Number.prototype.to2 = function() { return (this > 9 ? "" : "0")+this; };
Date.MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
Date.DAYS   = ["Sun", "Mon", "Tue", "Wed", "Tur", "Fri", "Sat"];
Date.prototype.getDateString = function(dateFormat) {
  var result = "";
  
  dateFormat = dateFormat == 8 && "YYYY.MM.DD" ||
               dateFormat == 6 && "HH:mm:ss" ||
               dateFormat ||
               "YYYY.MM.DD HH:mm:ss";
  for (var i = 0; i < dateFormat.length; i++) {
    result += dateFormat.indexOf("YYYY", i) == i ? (i+=3, this.getFullYear()                     ) :
              dateFormat.indexOf("YY",   i) == i ? (i+=1, String(this.getFullYear()).substring(2)) :
              dateFormat.indexOf("MMM",  i) == i ? (i+=2, Date.MONTHS[this.getMonth()]           ) :
              dateFormat.indexOf("MM",   i) == i ? (i+=1, (this.getMonth()+1).to2()              ) :
              dateFormat.indexOf("M",    i) == i ? (      this.getMonth()+1                      ) :
              dateFormat.indexOf("DDD",  i) == i ? (i+=2, Date.DAYS[this.getDay()]               ) :
              dateFormat.indexOf("DD",   i) == i ? (i+=1, this.getDate().to2()                   ) :
              dateFormat.indexOf("D"   , i) == i ? (      this.getDate()                         ) :
              dateFormat.indexOf("hh",   i) == i ? (i+=1, this.getHours().to2()                  ) :
              dateFormat.indexOf("h",    i) == i ? (      this.getHours()                        ) :
              dateFormat.indexOf("mm",   i) == i ? (i+=1, this.getMinutes().to2()                ) :
              dateFormat.indexOf("m",    i) == i ? (      this.getMinutes()                      ) :
              dateFormat.indexOf("ss",   i) == i ? (i+=1, this.getSeconds().to2()                ) :
              dateFormat.indexOf("s",    i) == i ? (      this.getSeconds()                      ) :
                                                   (dateFormat.charAt(i)                         ) ;
  }
  return result;
};
//-->
</script> 
<script type="text/javascript">
(function($) {
	$(document).ready(function() {
		$("#divTab1").tabs();
		$("#date1").datepicker(
				{
				    minDate: '-3m',
				    maxDate: '+0d',
				    onSelect: function(dateText, inst) {
				    		var endDate = $jq("#date2").val();
				    		
				    		if( endDate.length > 0 )
				    		{
				    			var tStartDate = $jq("#date1").datepicker("getDate");
				    			var tEndDate = $jq("#date2").datepicker("getDate");
				    			
				    			var tEndDate2 = $jq("#date2").datepicker("getDate");;
				    			tEndDate2.setDate(tEndDate2.getDate()-31)
				    			
				    			//alert(tEndDate2.toLocaleString());
				    			
				    			if(tEndDate < tStartDate)
				    			{
				    				alert("<ikep4j:message key='ui.support.searchpreprocessor.lesthen'/>");
				    				$jq(this).val("");
				    			}	
				    			else if(  tEndDate2 > tStartDate )
				    			{
				    				alert("<ikep4j:message key='ui.support.searchpreprocessor.range'/>");
				    				$jq(this).val("");
				    			}	
				    		}	
				    	
				    }
				}	
		).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("#date2").datepicker(
				{
				    minDate: '-3m',
				    maxDate: '+0d',
				    onSelect: function(dateText, inst) {
			    		var startDate = $jq("#date1").val();
			    		
			    		if( startDate.length > 0 )
			    		{
			    			var tStartDate = $jq("#date1").datepicker("getDate");
			    			var tEndDate = $jq("#date2").datepicker("getDate");
			    			
			    			var tEndDate2 = $jq("#date2").datepicker("getDate");;
			    			tEndDate2.setDate(tEndDate2.getDate()-31)
			    			
			    			//alert(tStartDate.toLocaleString() +":"+ tEndDate.toLocaleString());
			    			
			    			if( tEndDate < tStartDate )
			    			{
			    				alert("<ikep4j:message key='ui.support.searchpreprocessor.grethen'/>");
			    				$jq(this).val("");
			    			}	
			    			else if(  tEndDate2 > tStartDate )
			    			{
			    				alert("<ikep4j:message key='ui.support.searchpreprocessor.range'/>");
			    				$jq(this).val("");
			    			}	
			    		}	
			    }
				}
		).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		
		var toDate = new Date();
		var tmpDate = toDate.getTime() - (7*24*60*60*1000);
		var fromDate = new Date();
		fromDate.setTime(tmpDate);
		
		$('#date1').val( fromDate.getDateString('YYYY.MM.DD') );
		$('#date2').val( toDate.getDateString('YYYY.MM.DD') );
	});
})(jQuery);
//-->
</script> 
<script type="text/javascript">
<!--


(function($) {
	$(document).ready(function() {
		
		var validOptions = {
			    rules : {
			    	sdate :    {
			            required : true
			        },
			        edate :    {
			            required : true
			        }
			    },
			    messages : {
			    	sdate : {
			            direction : "top",
			            required : "<ikep4j:message pre="${preRequired}" key="required" arguments="${date}"/>" 
			        },
			        edate : {
			            direction : "top",
			            required : "<ikep4j:message pre="${preRequired}" key="required" arguments="${date}"/>" 
			        }
			    },
			    notice : {
			    	sdate : "${date}",
			    	edate : "${date}"
			    },
			    submitHandler : function(form) {
			    		$.post('<c:url value="/support/searchpreprocessor/report/reportHistory/ajaxDay.do"/>',
						 	{'startDt':$('#date1').val(),'endDt':$('#date2').val()})
						 .success(function(data) {
							 var s1 = [];
							 var ticks = [];
							 
							 $(data).each(function(index){
								 var date = new Date(this.resultDt) ;
								 
								 if( data.length == index+1)
									 ticks.push(date.getDateString('MM/DD')+"(<ikep4j:message pre="${preFiled}" key="reportToday" />)"); 
								 else
									 ticks.push(date.getDateString('MM/DD'));
								 s1.push(this.totalCount);
							 });
							 
							 printChart(s1,ticks);
						 })
						 .error(function(event, request, settings) { alert("error"); }); 
				}
			 };

		new iKEP.Validator("#searchForm", validOptions);
		
		$("#searchBtn,#chartViewButton1").click(function(){
			$("#searchForm").trigger("submit");
		});	
		
		
		var printChart = function(s1,ticks){
	        
	        var plot1 = $.jqplot('chart1', [s1], {
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
	                yaxis:{min:0, max:100}
	            },
	            highlighter: { show: false }
	        }); 
		}
		
		$("#searchBtn").click();
	});
})(jQuery);

(function($) {
	$(document).ready(function() {
		$("#chartViewButton2").click(function(){
			$.post('<c:url value="/support/searchpreprocessor/report/reportHistory/ajaxMonth.do"/>')
			 .success(function(data) {
				 var s1 = [];
				 var ticks = [];
				 
				 $(data).each(function(index){
					 var date = new Date(this.resultDt) ;
					 if( data.length == index+1)
						 ticks.push(date.getDateString('YY/MM')+"(<ikep4j:message pre="${preFiled}" key="reportMonth" />)"); 
					 else
					 	ticks.push(date.getDateString('YY/MM'));
					 s1.push(this.totalCount);
				 });
				 
				 printChart(s1,ticks);
			 })
			 .error(function(event, request, settings) { alert("error"); }); 
		});	
		
		
		var printChart = function(s1,ticks){
	        
	        var plot1 = $.jqplot('chart2', [s1], {
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
	                yaxis:{min:0, max:100}
	            },
	            highlighter: { show: false }
	        }); 
		}
		
		
	});
})(jQuery);
//-->
</script> 
<h1 class="none">
	<ikep4j:message pre="${preLeft}" key="7" />
</h1>


<!--//pageTitle End-->

<div class="subTitle_3">
	<h3>
		<ikep4j:message pre="${preHeader}" key="subTitle.7" />
	</h3>
</div>

<div id="divTab1" class="iKEP_tab">
	<ul>
		<li><a href="#tabs-1" id="chartViewButton1"><ikep4j:message pre="${preFiled}" key="todayView" /></a>
		</li>
		<li><a href="#tabs-2"  id="chartViewButton2"><ikep4j:message pre="${preFiled}" key="monthView" /></a>
		</li>
	</ul>

	<div>
		<div id="tabs-1">
			<!--blockSearch Start-->
			<div class="blockSearch">
				<div class="corner_RoundBox03">
					<form id="searchForm"  name="searchForm" onsubmit="return false" action="/support/searchpreprocessor/report/reportHistory/ajaxDay.do">
						<table summary="<ikep4j:message pre="${preFiled}" key="searchTable" />">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row" width="5%"><ikep4j:message pre="${preFiled}" key="serachAvgView" /></th>
									<td width="95%">
										<div class="subInfo">
											<input type="text" class="inputbox" id="date1" name="sdate"
												title="<ikep4j:message pre="${preFiled}" key="sdate" />" size="10" /> <img class="dateicon"
												src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${preFiled}" key="calendar" />" />
											~ <input type="text" class="inputbox" id="date2" name="edate"
												title="<ikep4j:message pre="${preFiled}" key="edate" />"  size="10" /> <img class="dateicon"
												src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre="${preFiled}" key="calendar" />" />
										</div></td>
								</tr>
							</tbody>
						</table>
						<div class="searchBtn">
							<a href="#a" id="searchBtn"><span>Search</span>
							</a>
						</div>
					</form>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>
			</div>
			<!--//blockSearch End-->
			<div class="corner_RoundBox02">
				<div>
					<div class="pre_graph" style="text-align: center;" id="chart1"></div>
				</div>
				<div class="clear"></div>
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
				<div class="l_b_corner"></div>
				<div class="r_b_corner"></div>
			</div>
		</div>
		<div id="tabs-2">
			<div class="corner_RoundBox02">
				<div>
					<div class="pre_graph" style="text-align: center;"  id="chart2"></div>
				</div>
				<div class="clear"></div>
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
				<div class="l_b_corner"></div>
				<div class="r_b_corner"></div>
			</div>
		</div>
	</div>
</div>
<!--//tab End-->







