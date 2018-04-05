<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>


<c:set var="preHeader"  value="ui.collpack.knowledgestreaming.header" /> 
<c:set var="preList"    value="ui.collpack.knowledgestreaming.list" />
<c:set var="preDetail"  value="ui.collpack.knowledgestreaming.detail" />
<c:set var="preButton"  value="ui.collpack.knowledgestreaming.button" /> 
<c:set var="preMessage" value="ui.collpack.knowledgestreaming.message" />

<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.barRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.pointLabels.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>

<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	$jq(document).ready(function() { 
			
			$("#searchBtn").click(function(){
				$('#intelligentSearchForm').submit();
			});	
			
			$("#divTab1").tabs();
		
			// 차트
			//$jq("#chart1").width($jq(".usage_graph").width()*0.98);
	
			$jq.jqplot.config.enablePlugins = true;
	
			// 공개
			//var line1 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">['${chartItem.ymd}', ${chartItem.publicDocCount}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
			// 전체
			//var line2 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">['${chartItem.ymd}', ${chartItem.allDocCount}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
			
			//var line1 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">[${chartItem.publicDocCount},${chartItemStatus.count}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
			var line2 = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">[${chartItem.allDocCount},${chartItemStatus.count}]<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
			var ticks = [<c:forEach var="chartItem" items="${knowledgeMonitorAccumulationChartList}" varStatus="chartItemStatus">'${chartItem.ymd}'<c:if test="${!chartItemStatus.last}">,</c:if></c:forEach>];
			
			var plot1 = $.jqplot('chart1', [line2], {         
				stackSeries: true,         
				captureRightClick: true,        
				seriesDefaults:{             
					renderer:$.jqplot.BarRenderer,             
					shadowAngle: 135,             
					rendererOptions: {                 
						barDirection: 'horizontal',                 
						highlightMouseDown: true                
						},             
					pointLabels: {show: true, formatString: '%d'}         
				},         
				legend: {             
					show: false,             
					location: 'e',             
					placement: 'outside'        
				},         
				axes: {             
					yaxis: {                 
						renderer: $.jqplot.CategoryAxisRenderer,
						ticks: ticks
					},
					xaxis: {
	                   tickOptions: {
	                       formatString: '%.0f'
	                   },
	                   min:0
	               }
				}     
			});  
			
			
			visitorChartDataList = [];
			visitorChartDataList2 = [];
			<c:forEach var="item" items="${knowledgeUseList}" varStatus="status">
				<c:if test="${status.count < 6}">
				var innnerCharData = ${item.fileDownloadCount};
				var innnerCharData2 = ${item.searchCount};
				visitorChartDataList.push( innnerCharData );
				visitorChartDataList2.push( innnerCharData2 );
				</c:if>
			</c:forEach>
			
			var ticks = ['<ikep4j:message pre="${preDetail}" key="label1" />','<ikep4j:message pre="${preDetail}" key="label2" />','<ikep4j:message pre="${preDetail}" key="label3" />','<ikep4j:message pre="${preDetail}" key="label4" />','<ikep4j:message pre="${preDetail}" key="label5" />'];
			
			plot2 = $.jqplot('chart2',[visitorChartDataList, visitorChartDataList2] ,{
		           legend: {
		                show:true,
		                location: 'nw',
		                yoffset: 6,
		                labels:['<ikep4j:message pre="${preDetail}" key="fileDownloadCount" />','<ikep4j:message pre="${preDetail}" key="searchCount" />']
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
							ticks: ticks
		               }
					}    
		       	});
			
			
			$jq("#moduleFieldName").val('Trend');
			getList(true);

	});
	
	
	getMore = function(isScrollSet) {
		
		$jq("#emptyDiv").hide();
		
		$jq("#pageIndex").val(eval($jq("#pageIndex").val())+1);
		
		$jq.ajax({     
			url : '<c:url value="/collpack/knowledgestreaming/subListForKnowledgeStreaming.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(result) {  
				$jq("#listDiv").append(result);
				if(isScrollSet) setScroll();
			}, 
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	getList = function() {
		
		$jq("#moreDiv").hide();
		$jq("#emptyDiv").hide();
		$jq("#pageIndex").val(1);
		
		$jq("#currentCount").val("0");
		
		$jq.ajax({     
			url : '<c:url value="/collpack/knowledgestreaming/subListForKnowledgeStreaming.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {container:"#divTab1"}, 
			success : function(result) {  
				
				$jq("#listDiv").children().remove();
				$jq("#listDiv").append(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	clickTab = function(type) {
		
		$jq("#moduleFieldName").val(type);
		getList();
	}
	
	setScroll = function() {
		
		var height = $jq(document).height() - $jq(window).height()
		$jq(window).scrollTop(height);

	};
	
	setMoreDiv = function() {
		
		var recordCount = eval($jq("#recordCount").val());
		var currentCount = eval($jq("#currentCount").val());
		
		if(currentCount == 0) {
			$jq("#emptyDiv").show();
		}
		else {
			$jq("#emptyDiv").hide();
		}
		
		if(recordCount < 10) {
			$jq("#moreDiv").hide();
		}
		else {
			$jq("#moreDiv").show();
		}
		
		if(currentCount == 10 && recordCount==0) {
			$jq("#emptyDiv").show();
		}

	};
	
	winOpen = function(url) {
		window.open(url, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");

	};
	
})(jQuery);  

//-->
</script>
		
			
<!--wrapper Start-->
<div id="wrapper">
		
	<!--blockContainer Start-->
	<div id="blockContainer">
	
		<!--blockMain Start-->
		<div id="blockMain">
				
			<!--mainContents Start-->
			<div id="mainContents" class="conPadding_2">			
				<h1 class="none"></h1>
				
				<!--blockLeft_Knowledge Start-->
				<div class="blockLeft_Knowledge">

					<div class="Intelligent_rBox mb10">
						<div class="knowledge_s_q">
							<a href="#a"><img src="<c:url value='/base/images/icon/ic_q1.gif'/>" alt="" /></a>
						</div>					
						<div class="knowledge_s_Title">
							<div class="ty1"><ikep4j:message pre="${preDetail}" key="toDayKm"/> <span>${toDayKm}</span></div>
							<div class="ty1"><ikep4j:message pre="${preDetail}" key="toDayQa"/> <span>${toDayQa}</span></div>
						</div>
						<div class="clear"></div>
						<div class="knowledge_s_Info">
							<p><ikep4j:message pre="${preDetail}" key="myPvi"/> : <span>${userPvi.pvi}</span></p>
							<p><ikep4j:message pre="${preDetail}" key="myPviRank"/> : <span>${userPvi.rank}</span></p>
						</div>
					</div>

					<!--corner_RoundBox07 Start-->
					<div class="corner_RoundBox07 padding10">
										
						<div class="People_t2">
							<h2 style="border-top:none;"><ikep4j:message pre="${preDetail}" key="recommendKm"/></h2>
						</div>
						<div class="knowledge_s_con">
							<ul>
								
								<c:forEach var="item" items="${recommendTagItemList}">
									<li><a href="#a" onclick="iKEP.tagging.goDetail('<c:url value="${item.tagItemUrl}"/>')">${item.tagItemName}</a></li>
								</c:forEach>
							
							</ul>	
						</div>									
	
						<div class="People_t2">
							<h2><ikep4j:message pre="${preDetail}" key="recommendTag"/></h2>
						</div>	
						<div class="clear"></div>
						<div class="tag_cloud lntel_tag_cloud mt10">
							
							<c:forEach var="item" items="${recommendTagList}">
								<span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}','');" class="tag${item.displayStep}">${item.tagName}</a></span>
							</c:forEach>

						</div>
						
						<div class="People_t2 mt10">
							<h2><ikep4j:message pre="${preDetail}" key="specialTag"/></h2>
							<!--
							<span class="edit"><a href="#a"><img src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="edit" /></a></span>
							  -->
						</div>	
						<div class="clear"></div>
						<div class="tag_cloud lntel_tag_cloud mt10">
						
							<c:forEach var="item" items="${expertTagList}">
								<span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}','');" class="tag${item.displayStep}">${item.tagName}</a></span>
							</c:forEach>
							
						</div>
						
						<div class="People_t2 mt10">
							<h2><ikep4j:message pre="${preDetail}" key="interestTag"/></h2>
							<!--
							<span class="edit"><a href="#a"><img src="<c:url value='/base/images/icon/ic_edit.gif'/>" alt="edit" /></a></span>
							  -->
						</div>	
						<div class="clear"></div>
						<div class="tag_cloud lntel_tag_cloud mt10">
						
							<c:forEach var="item" items="${intrestTagList}">
								<span><a href="#a" onclick="iKEP.tagging.tagSearchByName('${item.tagName}','');" class="tag${item.displayStep}">${item.tagName}</a></span>
							</c:forEach>
							
						</div>											

						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
					<!--//corner_RoundBox07 End-->
														
				</div>		
				<!--blockLeft_Knowledge End-->		

				<!--blockCenter_Knowledge Start-->
				<div class="blockCenter_Knowledge">
					<div id="tagResult"  class="blockCenter_Knowledge_con">
					
						<!--corner_RoundBox07 Start-->
						<div class="corner_RoundBox07 padding10" style="min-height:400px;">
						
							<!--blockSearch Start-->
							<form id="intelligentSearchForm" name="intelligentSearchForm" method="post" action="<c:url value="/servicepack/intelligentsearch/search.do"/>">
							<div class="cafe_main mb10">
								<h2><ikep4j:message pre="${preDetail}" key="find"/></h2>
								<!--conSearch Start-->
								<div class="conSearch">
									<input name="searchKeyword" id="searchKeyword" title="Search" style="width:239px" type="text" />
									<a class="sel_btn" href="#a" id="searchBtn"><span>Search</span></a>
									<div class="clear"></div>
								</div>
								<!--//conSearch End-->
							</div>
							</form>
							<!--//blockSearch End-->
							<div class="clear"></div>
							
							<form id="searchForm" method="post" action="" onsubmit="return false">  
				
							<spring:bind path="searchCondition.recordCount">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>	
							
							<spring:bind path="searchCondition.currentCount">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>	
							
							<spring:bind path="searchCondition.pageIndex">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>		
							
							<spring:bind path="searchCondition.moduleFieldName">
								<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
							</spring:bind>
				
							<!--tab Start-->		
							<div id="divTab1" class="iKEP_tab_poll">
								<ul>
									<li><a href="#tabs-1" onclick="clickTab('Trend')">Trend</a></li>
									<li><a href="#tabs-1" onclick="clickTab('Best')">Best</a></li>
									<li><a href="#tabs-1" onclick="clickTab('New')">New</a></li>
									<li><a href="#tabs-1" onclick="clickTab('Expert')">Expert</a></li>
									<li><a href="#tabs-1" onclick="clickTab('Interest')">Interest</a></li>
									<li><a href="#tabs-1" onclick="clickTab('RSS')">RSS</a></li>
								</ul>
							
								<div id="tabs-1">
									<!--Intel_content Start-->
									<div class="Intel_content">									
										<ul id="listDiv">								
											<li></li>
										</ul>
									</div>
									<!--//Intel_content End-->
								</div>		
								<!--//tab End-->		
							
								<!--blockButton_3 Start-->
								<div id="moreDiv" class="blockButton_3 mb0" onclick="getMore()"> 
									<a class="button_3" href="#a"><span><ikep4j:message pre="${preDetail}" key="more"/><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
								</div>
								<div id="emptyDiv" class="blockButton_3 mb0" > 
									<a class="button_3" href="#a"><span><ikep4j:message pre='${preDetail}' key='empty' /> </span></a>				
								</div>
								<!--//blockButton_3 End-->		
	
								<div class="l_t_corner"></div>
								<div class="r_t_corner"></div>
								<div class="l_b_corner"></div>
								<div class="r_b_corner"></div>	
											
							</div>
							
							</form>
						<!--//corner_RoundBox07 End-->		
						</div>		
					</div>
				</div>
				<!--//blockCenter_Knowledge End-->
				
				<!--blockRight_Knowledge Start-->
				<div class="blockRight_Knowledge">
					<!--corner_RoundBox07 Start-->
					<div class="corner_RoundBox07 padding10">
						
						<div class="People_t2">
							<h2 style="border-top:none;"><ikep4j:message pre="${preDetail}" key="tagCloud"/></h2>
						</div>
						<div class="mt10 mb20">							
							<div class="tag_cloud"  id="tag01">
								<script type="text/javascript">
									iKEP.createTagEmbedObject("#tag01", "<c:url value="/collpack/knowledgestreaming/tag.do"/>", "tagResult", 188, 140);
								</script>					
							</div>			
						</div>
						
						<div class="People_t2">
							<h2><ikep4j:message pre="${preDetail}" key="kmInfo"/></h2>
						</div>
						<div class="mt10 mb20">
							<div id="chart1" style="width:185px;height:200px;"></div>
						</div>						

						<div class="People_t2">
							<h2><ikep4j:message pre="${preDetail}" key="kmActi"/></h2>
						</div>
						<div class="mt10 mb10">
							<div id="chart2" style="width:190px;height:200px;"></div>
						</div>
														
						<div class="l_t_corner"></div>
						<div class="r_t_corner"></div>
						<div class="l_b_corner"></div>
						<div class="r_b_corner"></div>				
					</div>
					<!--//corner_RoundBox07 End-->
				</div>
				<!--//blockRight_Knowledge End-->					
					
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->
	
	</div>
	<!--//blockContainer End-->
	
</div>
<!--//wrapper End-->
