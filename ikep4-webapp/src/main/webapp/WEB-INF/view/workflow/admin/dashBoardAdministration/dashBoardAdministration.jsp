<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.workflow.admin.dashBoardAdministration.dashBoardAdministration" /> 
<c:set var="preSearch"  value="ui.workflow.admin.dashBoardAdministration.dashBoardAdministration.search" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>
<link type="text/css" rel="stylesheet" href="<c:url value='/base/css/syntaxhighlighter_3.0.83/shCoreDefault.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/plot_chart/jquery.jqplot.css'/>" />
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shCore.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shBrushJScript.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shBrushXml.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jquery.jqplot.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/excanvas.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.pieRenderer.min.js'/>"></script>

<!-- 프로세스별 인스턴스 진행현황 / 프로세스별 인스턴스 누적현황 컬럼고정 CSS적용 -->
<style type="text/css">
.headerFixedTable{
        width: 100%;
    }
.headerFixedTbody {
        height: 250px;
        overflow-y: auto;
        overflow-x: hidden;
    }
.thWidthProcessName{
	width:58%;  
   *width:60%; 
}  
.thWidthProcessCount{
	width:32%;  
   *width:30%; 
}   
</style>
<!--[if IE]>
<style type="text/css">
.headerFixedDiv {
        position: relative;
        height: 250px;
        width: 100%;
        overflow-y: auto;
        overflow-x: hidden;
    }
.headerFixedTr {
        position: relative;
        top: expression(this.offsetParent.scrollTop);
    }
.headerFixedTbody {
        height: auto;
    }
</style>
<![endif]--> 

<style type="text/css">
.ui-widget-content {border:0;}
</style>
<script type="text/javascript">
(function($){
	//=========================================================================
	// * Jquery Layout
	//=========================================================================
	$(document).ready(function () {
		//=========================================================================
		//* 총누적건수(기간별건수) 값 지정
		//=========================================================================
		$("#searchCondition > option[value=${searchCondition}]").attr("selected",true);
		
		//=========================================================================
		//* 총누적건수(기간별건수) ComboBox 값 변경시
		//=========================================================================
		$("#searchCondition").change(function() {
			$("#dashBoardAdministrationForm").submit(); 
			return false; 
		});
		
		SyntaxHighlighter.highlight();
		//=========================================================================
		//* 인스턴스 진행현황 Pie Chart 설정
		//=========================================================================
		var s1 = [["<ikep4j:message pre='${preHeader}' key='progress'/>",${instanceStateCount.RUNNING}], ["<ikep4j:message pre='${preHeader}' key='delay'/>",${instanceStateCount.OVERDUE}], ["<ikep4j:message pre='${preHeader}' key='error'/>",${instanceStateCount.FAULT}]];
	        
        var plot1 = $.jqplot('chart1', [s1], {
            grid: {
                drawBorder: false, 
                drawGridlines: false,
                background: '#ffffff',
                shadow:false
            },
            seriesDefaults:{
                renderer:$.jqplot.PieRenderer,
                rendererOptions: {
                    showDataLabels: true
                }
            },
            legend: {
                show: true,
                rendererOptions: {
                    numberRows: 1
                },
                location: 's'
            }
        });
        //=========================================================================
		//* 프로세스별 인스턴스 총 누적건수 Pie Chart 설정
		//=========================================================================
        var s2 = ${processInstanceCount};
        var plot2 = $.jqplot('chart2', [s2], {
            grid: {
                drawBorder: false, 
                drawGridlines: false,
                background: '#ffffff',
                shadow:false
            },
            seriesDefaults:{
                renderer:$.jqplot.PieRenderer,
                rendererOptions: {
                    showDataLabels: true
                }
            },
            legend: {
                show: true,
                rendererOptions: {
                    numberRows: 2
                },
                location: 's'
            }
        });
	});
})(jQuery);

</script>
</head>
<body>
<!--blockSearch Start-->
<form id="dashBoardAdministrationForm" method="post" action="<c:url value='/workflow/admin/dashBoardAdministration.do'/>">
<h1 class="none"><ikep4j:message pre='${preHeader}' key='dashBoard'/></h1>
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='dashBoard'/></h2>
	
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${preHeader}' key='workflow'/></li>
			<li><ikep4j:message pre='${preHeader}' key='current'/></li>
			<li class="liLast"><ikep4j:message pre='${preHeader}' key='dashBoard'/></li>
		</ul>
	</div>
</div>
<div id="pageTitle">
<div class="totalNum"><ikep4j:message pre='${preHeader}' key='partition'/> : <span class="colorPoint">${partitionCount}<ikep4j:message pre='${preHeader}' key='count'/></span></div>
<div class="totalNum"><ikep4j:message pre='${preHeader}' key='process'/> : <span class="colorPoint">${processCount}<ikep4j:message pre='${preHeader}' key='count'/></span></div>  
</div>
<div class="clear"></div>
<div class="clear"></div>
<div style="width:100%; height:300px;">
	<!--blockLeft Start-->
	<div class="blockLeft" style="width:28%;">	
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><ikep4j:message pre='${preHeader}' key='totalInstancePercent'/></h3>
		</div>
		<!--//subTitle_2 End-->						
		<div id="chart1"></div> 
	</div>
	<!--//blockLeft End-->		
	
	<!--blockRight Start-->
	<div class="blockRight" style="width:70%;">		
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><ikep4j:message pre='${preHeader}' key='processInstanceProgress'/></h3>
		</div>
		<!--//subTitle_2 End-->									
		<!--blockDetail Start-->		
		<div class="blockDetail headerFixedDiv">
			<table class="headerFixedTable" summary="<ikep4j:message pre='${preHeader}' key='processInstanceProgress'/>" style="border-collapse:separate;" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr class="headerFixedTr">
					    <th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preHeader}' key='no'/></th>
						<th scope="col" width="60%"   class="textCenter"><ikep4j:message pre='${preHeader}' key='processName'/></th>
						<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preHeader}' key='progress'/></th>
						<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preHeader}' key='delay'/></th>
						<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preHeader}' key='error'/></th>
					</tr>
				</thead>
				<tbody class="headerFixedTbody">
					<c:forEach var="listCurrentInstance" items="${listCurrentInstance}" varStatus="status">
					<tr>
						<td width="10%" class="textCenter" style="background-color:transparent;">${listCurrentInstance.NO}</td>
						<td width="60%" class="textLeft"   style="background-color:transparent;"><div class="ellipsis">${listCurrentInstance.PROCESS_NAME}</div></td>					
						<td width="10%" class="textCenter" style="background-color:transparent;">${listCurrentInstance.RUNNING}</td>
						<td width="10%" class="textCenter" style="background-color:transparent;">${listCurrentInstance.OVERDUE}</td>
						<td width="10%" class="textCenter" style="background-color:transparent;">${listCurrentInstance.FAULT}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
	</div>
</div>
<div class="clear"></div>

<!--//blockLight End-->		
<div style="width:100%; height:300px;">
	<!--blockLeft Start-->
	<div class="blockLeft" style="width:28%;">	
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><ikep4j:message pre='${preHeader}' key='processInstanceAccumulate'/></h3>
		</div>
		<!--//subTitle_2 End-->		
		<div id="chart2"></div>
	</div>
	<!--//blockLeft End-->		

	<!--blockRight Start-->
	<div class="blockRight" style="width:70%;">		
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<div style="float:left;"><h3><ikep4j:message pre='${preHeader}' key='processInstanceAccumulateCount'/></h3></div>
			<div style="float:right;">
			<ikep4j:message pre='${preSearch}' key='day'/>:
			<select id="searchCondition" name="searchCondition" title="<ikep4j:message pre='${preSearch}' key='day'/>">
				<option value="1"><ikep4j:message pre='${preSearch}' key='dayOne'/></option>
				<option value="7"><ikep4j:message pre='${preSearch}' key='daySeven'/></option>
				<option value="30"><ikep4j:message pre='${preSearch}' key='dayOneMonth'/></option>
				<option value="60"><ikep4j:message pre='${preSearch}' key='dayTwoMonth'/></option>
				<option value="90"><ikep4j:message pre='${preSearch}' key='dayThreeMonth'/></option>
			</select>
			</div>
		</div>
		<!--//subTitle_2 End-->		
		<div class="clear"></div>						
		<!--blockDetail Start-->					
		<div class="blockDetail headerFixedDiv">
			<table class="headerFixedTable" summary="<ikep4j:message pre='${preHeader}' key='processInstanceAccumulateCount'/>" style="border-collapse:separate;" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr class="headerFixedTr">
						<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preHeader}' key='no'/></th>
						<th scope="col" class="textCenter thWidthProcessName"><ikep4j:message pre='${preHeader}' key='processName'/></th>
						<th scope="col" class="textCenter thWidthProcessCount"><ikep4j:message pre='${preHeader}' key='processInstanceCount'/></th>
					</tr>
				</thead>
				<tbody class="headerFixedTbody">
					<c:forEach var="listAccumulateInstance" items="${listAccumulateInstance}" varStatus="status">
					<tr>
						<td class="textCenter" style="background-color:transparent;">${listAccumulateInstance.NO}</td>
						<td class="textLeft thWidthProcessName"><div class="ellipsis">${listAccumulateInstance.PROCESS_NAME}</div></td>					
						<td class="textCenter thWidthProcessCount" style="background-color:transparent;">
							<div style="width:30%;float: left;">
								${listAccumulateInstance.TOTAL_COUNT} (<font color="red"><b>${listAccumulateInstance.CONDITION_COUNT}</b></font>)
							</div>
							<div id="barCharts${listAccumulateInstance.NO}" style="width:65%;height:15px;float: left;">
								<script type="text/javascript">
									(function($){
										$(document).ready(function () {
									        $("#barCharts${listAccumulateInstance.NO}").progressbar({value: ${listAccumulateInstance.CONDITION_PERCENT}},{max:1000});
									        
										});
									})(jQuery);
								</script>
							</div>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
	</div>
<!--//blockLight End-->		
</div>	
</form>
</body>
</html>
