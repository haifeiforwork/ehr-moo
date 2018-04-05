<%--
=====================================================
* 기능 설명 : Workflow Workplace 개인설정 부재중 설정
* 작성자 : 이재경
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="ui.workflow.workplace.statistics" />
<c:set var="prefixCommon"  value="ui.workflow.workplace.common" />
<c:set var="prefixCommonBtn"  value="ui.workflow.workplace.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>

<script type="text/javascript">
	<!--
	
	(function($){
		$(document).ready(function(){
			
			var processVerList = function(){
					var processId = $("#processId").val();
					$.get('<c:url value="/workflow/workplace/statistics/processVerList.do"/>?processId='+processId)
				     .success(function(data) {
				    	 //기존 select 태그를 지운다.
				    	 $("#verSpan").html("");
				    	 //span에 select 태그를 추가한다.
				    	 var verCombo = $('<select id="processVer" name="processVer" title="Version"/>').appendTo("#verSpan");
				    	 for( var i = 0 ; i < data.length ; i++ ){
				    		 verCombo.append('<option value="'+data[i].processVer+'">'+data[i].processVer+'</option>');
				    	 }
				     }) 
				     .error(function(event, request, settings) { alert("error"); });
			}
			
			$("#processId").change(function(){
				processVerList();
			});
			
			processVerList();
			
			
			//날짜 셋팅
			var today = new Date();
			if( "${workplaceSearchCondition.startPeriod}" == "" ){
				$("#startPeriod").val(today.getFullYear() + "." + AddFrontZero(today.getMonth()+1, 2) + "." + AddFrontZero(today.getDate(),2));
				$("#endPeriod").val(today.getFullYear() + "." + AddFrontZero(today.getMonth()+1, 2) + "." + AddFrontZero(today.getDate(),2));
			}
			
			$("#startPeriod").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			$("#endPeriod").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			
			$("#searchStatisticsButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
			
			$("#myForm").validate({ 
				submitHandler: function(form) {
	                  form.submit();
	                  return true;
	            },
	            rules: {
	            		processId: { required: true }
	            },
	            messages: {
	            		processId: { required: "프로세스를 선택하세요" }
	            }
			}); 
			
			//일자변경
			$("#searchperiod").change(function(){
				var period = $(this).val();
				var newDay = new Date(today.valueOf() - period*(24*60*60*1000));
				
				$("#startPeriod").val(newDay.getFullYear() + "-" + AddFrontZero(newDay.getMonth()+1, 2) + "-" + AddFrontZero(newDay.getDate(),2));
				$("#endPeriod").val(today.getFullYear() + "-" + AddFrontZero(today.getMonth()+1, 2) + "-" + AddFrontZero(today.getDate(),2));
			});
			
			//프로세스
			var url = '<c:url value="/workflow/modeler/prism.do"/>?mode=model&processId=${processBean.processId}&version=${processBean.processVer}&scale=1&minimapView=false';
			$("#statisticsProcess").html('<iframe id="prism" src="' + url + '" frameborder="0" height="100%" width="100%;" scrolling="no"></iframe>');
			
			//통계 챠트
			var s2 = [${processRunningCount}, ${processCompleteCount}, 0]; // 데이타
		    var ticks = ['<ikep4j:message pre='${prefix}' key='runningcount'/>', '<ikep4j:message pre='${prefix}' key='completecount'/>', '<ikep4j:message pre='${prefix}' key='abnormalcount'/>']; // 범주
		     
		    var plot1 = $.jqplot('procStatisticsBarChart', [s2], {
		        seriesDefaults:{
		            renderer:$.jqplot.BarRenderer,	// 차트 형태
		            rendererOptions:{barPadding: 8, barMargin: 20}
		        },
		        axes: {
		            xaxis: {
		                renderer: $.jqplot.CategoryAxisRenderer,  // x축 표시
		                ticks: ticks                			// x축 범주 표시
		            },
		            yaxis:{min:0, pad:1.2, showTicks: false}
		        },
		        highlighter: { show: false }  // highlight 기능 비활성화
		    }); 
			
		});
	})(jQuery);
		
	//입력값 앞에 '0'을 붙여서 입력받는 자리수의 문자열을 반환한다.
	function AddFrontZero(inValue, digits) {
		var result = '';
	  	inValue = inValue.toString();
	  	if (inValue.length < digits) {
	   	for (i = 0; i < digits - inValue.length; i++)
	    	result += '0';
	    }
	    result += inValue
		return result;
	}
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">컨텐츠영역</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='procstatistics'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${prefix}' key='workplace'/></li>
			<li><ikep4j:message pre='${prefix}' key='procstatistics'/></li>
			<li class="liLast"><ikep4j:message pre='${prefix}' key='procstatistics'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="myForm" name="myForm" method="post" action="<c:url value='/workflow/workplace/statistics/procStatistics.do' />">
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="검색테이블">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="5%"><ikep4j:message pre='${prefix}' key='process'/></th>
						<td width="35%">
							<spring:bind path="workplaceSearchCondition.processId">		
							<select title="<ikep4j:message pre='${prefix}' key='process'/>" id="${status.expression}" name="${status.expression}">
								<c:forEach var="code" items="${processList}">
									<option value="${code.processId}" <c:if test="${code.processId eq status.value}">selected="selected"</c:if>>${code.processName}</option>
								</c:forEach> 
							</select>
							</spring:bind>
							<span id="verSpan"></span>
						</td>
						
						<spring:bind path="workplaceSearchCondition.searchperiod">
						<th scope="row" width="5%"><ikep4j:message pre='${prefix}' key='${status.expression}'/></th>
						<td width="55%">		
							<select title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" id="${status.expression}" name="${status.expression}">
								<c:forEach var="code" items="${workplaceCode.searchPeriodList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>><spring:message code="${code.value}"/></option>
								</c:forEach> 
							</select>															
						</spring:bind>
							<div class="subInfo">
								<spring:bind path="workplaceSearchCondition.startPeriod">											
								<input type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" value="${status.value}" size="8" readonly="readonly"/> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefixCommon}' key='calendar'/>" /> ~
								</spring:bind>
								<spring:bind path="workplaceSearchCondition.endPeriod">
								<input type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" value="${status.value}" size="8" readonly="readonly"/> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefixCommon}' key='calendar'/>" />
								</spring:bind>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="searchBtn">
				<a id="searchStatisticsButton" name="searchStatisticsButton" href="#">
					<img src="<c:url value="/base/images/theme/theme01/basic/btn_search.gif"/>" alt="<ikep4j:message pre='${prefixCommonBtn}' key='search'/>" />
				</a>
			</div>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>				
		</div>
	</div>	
	<!--//blockSearch End-->	

			
	<div id="statisticsProcess" class="usage_graph" style="height:350px;"></div>		
	
	<div class="clear"></div>
	<div class="blockBlank_20px"></div>				
	
	<!--blockLeft Start-->
	<div class="blockLeft" style="width:49%">
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline2">
			<h3><ikep4j:message pre='${prefix}' key='basicinfo'/></h3>
		</div>
		<!--//subTitle_2 End-->
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="25%"><ikep4j:message pre='${prefix}' key='processid'/></th>
						<td>${processBean.processId}</td>
					</tr>
					<tr>
						<th scope="row" width="25%"><ikep4j:message pre='${prefix}' key='processver'/></th>
						<td>${processBean.processVer}</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${prefix}' key='processname'/></th>
						<td>${processBean.processName}</td>
					</tr>
					<tr>
						<th scope="row" width="25%"><ikep4j:message pre='${prefix}' key='deployperiod'/></th>
						<td>
							<c:choose>
								<c:when test="${!empty processBean.applyStartDate}">
									<ikep4j:timezone date="${processBean.applyStartDate}" pattern="yyyy.MM.dd HH:mm"/> ~ <ikep4j:timezone date="${processBean.applyEndDate}" pattern="yyyy.MM.dd HH:mm"/>
								</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${prefix}' key='author'/></th>
						<td>${processBean.authorName}</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${prefix}' key='description'/></th>
						<td>${processBean.description}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
	</div>
	<!--blockLeft End-->
	
	<!--blockRight Start-->
	<div class="blockRight" style="width:49%">
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline2">
			<h3><ikep4j:message pre='${prefix}' key='processstatus'/></h3>
		</div>
		<!--//subTitle_2 End-->
		<div class="usage_graph1">
			<!--graph Start-->
			<div class="blockLeft" style="width:25%">
				<ul class="graphText1">
					<li><ikep4j:message pre='${prefix}' key='runningcount'/> : <span class="colorPoint">${processRunningCount}</span></li>
					<li><ikep4j:message pre='${prefix}' key='completecount'/> : <span class="colorPoint">${processCompleteCount}</span></li>
					<li><ikep4j:message pre='${prefix}' key='abnormalcount'/> : <span class="colorPoint">0</span></li>
				</ul>
				<div class="clear"></div>
			</div>
			<div class="blockRight" style="width:75%">
				<div id="procStatisticsBarChart" style="text-align:center;width:90%; height:110px;"></div>
			</div>
			<!--//graph End-->							
		</div>
	</div>
	<!--blockRight End-->
</form>			
<!--//mainContents End-->