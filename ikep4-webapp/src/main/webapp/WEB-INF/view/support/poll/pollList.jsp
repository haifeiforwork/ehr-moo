<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preAdmin"    value="ui.support.poll.admin" />
<c:set var="preSub"    value="ui.support.poll.sub" />
<c:set var="preList"    value="ui.support.poll.list" />
<c:set var="preForm"    value="ui.support.poll.form" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="defaultLocaleCode" value="${sessionScope['ikep.defaultLocaleCode']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$(document).ready(function() {
		var selectIndex = "0";
		var isCompleteIndex = "0";
		if ( ${searchCondition.isComplete} == '0' ) {
			selectIndex = "0";
		} else if ( ${searchCondition.isComplete} == '1' ) {
			selectIndex = "0";
		} else if ( ${searchCondition.isComplete} == '2' ) {
			selectIndex = "1";
		}
        switch ('${searchCondition.isResultExists}') {
	        case "1":
	        	$("#pollListAppliedButton").addClass("button_s selected");
	            break;
	        case "0":					
	    		$("#pollListNonAppliedButton").addClass("button_s selected");
	            break;
	        default:
	        	$("#pollListAllButton").addClass("button_s selected");
	            break;
    	}		
		
		$("#divTab1").tabs({     
			selected : selectIndex,    
			cache : true,     
			select : function(event, ui) {   
					if ( ${searchCondition.isComplete} == '0' ) {
						isCompleteIndex = "0";
					} else {
						isCompleteIndex = ui.index + 1;
					}
					//location.href = 'pollList.do?isComplete=' + ui.index + '&isResultExists=' + '${searchCondition.isResultExists}';
					//location.href = 'pollList.do?isComplete=' + isCompleteIndex + '&isResultExists=' + '${searchCondition.isResultExists}';
					location.href = 'pollList.do?isComplete=' + isCompleteIndex;
			},     
			load : function(event, ui) {        
				//iKEP.debug(ui);     
				} 
		}); 
		$("#date1").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("#date2").datepicker().next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		var remainingDay = 20;
		var voteDay = 11;
		var allDay = 31;
		var persent = 0;				
	    var now = new Date();
	    var yyyy1 = now.getFullYear();
	    var mm1 = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
	    var dd1 = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();
	    var toDay = yyyy1 + mm1 + dd1;
		$(".voteRange").each(function() {
			//var item = voteItems[idx];
			//var persent = item.voteDay / item.allDay * 100;
			var curId = $(this).attr('id').split("-");
			var startDate = curId[1];
			var endDate = curId[2];
	
			//allDay = dayDiff($(this).attr('startDate'),$(this).attr('endDate'));
			//voteDay = dayDiff($(this).attr('startDate'),toDay);
			allDay = dayDiff(startDate,endDate);
			voteDay = dayDiff(startDate,toDay);
			persent = voteDay/allDay*100;	
			$(this).progressbar({ 
	  			value: persent 
	 		});
		});

		$("#pollListButton a").click(function(){
            switch (this.id) {
                case "pollListAllButton":
                    location.href='pollList.do?isComplete=' + '${searchCondition.isComplete}' + '&isResultExists=';
                    break;
                case "pollListAppliedButton":					
                	location.href='pollList.do?isComplete=' + '${searchCondition.isComplete}' + '&isResultExists=1';
                    break;
                case "pollListNonAppliedButton":
                	location.href='pollList.do?isComplete=' + '${searchCondition.isComplete}' + '&isResultExists=0';
                	break;
                default:
                    break;
            }
        });		
	});	
	
	// 바차트 생성
	barChart = function(el, data, ticks) {	 
		$jq.jqplot(el, [data], {
			grid: {                
                background: '#FCFCFC',
                drawGridlines: false
            },
        	seriesDefaults:{
                renderer:$jq.jqplot.BarRenderer,
                pointLabels: { show: true }
            },
            axes: {
                xaxis: {
                    renderer: $jq.jqplot.CategoryAxisRenderer,
                    ticks: ticks
                },
                yaxis: {
                	min:0,
                	max:getMax(data)
                }
            },
            highlighter: { show: false }
        });
		
	};
	
	// 파이차트 생성
	pieChart = function(el, data) { 
		//iKEP.debug(data);
		$jq.jqplot(el, [data], {
            grid: {
                drawBorder: false, 
                drawGridlines: false,
                background: '#FCFCFC',
                shadow:false
            },
            seriesDefaults:{
                renderer:$jq.jqplot.PieRenderer,
                rendererOptions: {
                    showDataLabels: true,
                    shadow:false,
                    sliceMargin:2
                }
            },
            legend: {
                show: true,                
                location: 'e'
            }
        });
	};	
	
	// 찬성반대차트 생성
	etcChart = function(el, data) {
		var yesPercent = 0;
		var noPercent = 0;
		var totalCnt = 0;
		for(var i=0; i<data.length; i++) {
			totalCnt += data[i];
		}		
		yesPercent = data[0]/totalCnt*100;
		yesPercent = Math.round(yesPercent*10)/10;
		noPercent = 100 - yesPercent;

		$("#chart"+el).progressbar({  
  			value: yesPercent 
 		});			
		$("#yes"+el).text( "<ikep4j:message pre='${preList}' key='yes' /> : "+yesPercent + "%");
		$("#no"+el).text( "<ikep4j:message pre='${preList}' key='no' /> : "+noPercent+"%");
	};		
})(jQuery); 

	function getMax(arr) {
		 var max = arr[0]; 
		 for(var i=1; i<arr.length; i++) 
		  if(max < arr[i]) 
		   max = arr[i]; 
		 return (max+max*30/100); 
	}
	function show_layer(){
		$jq("#layer_p").dialog({width: 520, height:160, modal:true, resizable: false});
	}
	function dayDiff(d1,d2){
		var yyyy1 = d1.substring(0,4);
		var mm1 = d1.substring(4,6);
		var dd1 = d1.substring(6,8);
		var yyyy2 = d2.substring(0,4);
		var mm2 = d2.substring(4,6);
		var dd2 = d2.substring(6,8);	
		var date1 = new Date(yyyy1,mm1,dd1);
		var date2 = new Date(yyyy2,mm2,dd2);
		return Math.ceil((date2 - date1) / 1000 / 24 / 60 / 60); 
	}
	var pageIndex = 2;		
	function moreList(){	
		if(${searchCondition.pageCount} < pageIndex){
			return;	
		}
	
		$jq.ajax({    
			url : "listMore.do",  
			data : {pageIndex:pageIndex
				,isComplete:'${searchCondition.isComplete}'
				,isResultExists:'${searchCondition.isResultExists}'
			},     
			type : "post",  
			dataType : "html",
			success : function(result) { 
				if(result){
					$jq('#listFrame').append(result);
					++pageIndex;
					if(${searchCondition.pageCount} < pageIndex){
						$jq('#moreText').text("<ikep4j:message pre='${preList}' key='dataEmpty' />");
					}
				}
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}
	
	function completePoll(pollId) {
		if (pollId == "") {
			alert("<ikep4j:message pre='${preList}' key='blockComplete' />");
			return;
		}	
		if(confirm("<ikep4j:message pre='${preList}' key='confirmComplete' />")) {
			$jq.ajax({
				url : '<c:url value="completePoll.do" />',
				data : {pollId:pollId,status:'2'},
				type : "post",
				success : function(result) {
					document.location.href="pollList.do?isComplete=2";
				},
				error:function(){
					alert("<ikep4j:message pre='${preList}' key='failComplete' />");
				}
			});	
		} else {
			return;
		}
	}
	
	function openPoll(pollId) {
		if (pollId == "") {
			alert("<ikep4j:message pre='${preList}' key='blockOpen' />");
			return;
		}	
		if(confirm("<ikep4j:message pre='${preList}' key='confirmOpen' />")) {
			$jq.ajax({
				url : '<c:url value="completePoll.do" />',
				data : {pollId:pollId,status:'1'},
				type : "post",
				success : function(result) {
					document.location.href="pollList.do?isComplete=1";
				},
				error:function(){
					alert("<ikep4j:message pre='${preList}' key='failOpen' />");
				}
			});	
		} else {
			return;
		}
	}
	
	function deletePoll(pollId) {
		if (pollId == "") {
			alert("<ikep4j:message pre='${preList}' key='blockDel' />");
			return;
		}	
		if(confirm("<ikep4j:message pre='${preList}' key='confirmDel' />")) {
			$jq.ajax({
				url : '<c:url value="deletePoll.do" />',
				data : {pollId:pollId},
				type : "post",
				success : function(result) { 
					document.location.reload();
				},
				error:function(){
					alert("<ikep4j:message pre='${preList}' key='failDel' />");
				}
			});	
		} else {
			return;
		}
	}
	function modifyPoll(pollId) {
		location.href='formPoll.do?pollId='+pollId;	
	}
	function openPollWin(pollId,mode) {
	
		//window.open (pageURL, '', 'top=0,width=630,height=350,resizable=no, status=no,scrollbars=no, toolbar=no, menubar=no');
		//var pageURL = "<c:url value='/support/poll/viewPoll.do' />" + "?pollId=" + pollId + "&viewMode=" + mode + "&docPopup=true";
		//iKEP.popupOpen(pageURL, {width:580, height:350}, "pollPopup")
		location.href="viewPoll.do"+"?pollId=" + pollId + "&viewMode=" + mode;
	}
	function validTime(){
		alert("투표기간에 투표할 수 있습니다.");
	}
	function validTime2(){
		alert("투표 진행 후 사용할 수 있습니다.");
	}
//]]>
</script> 

<h1 class="none">contents</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<c:choose>
		<c:when test="${searchCondition.isComplete == '0'}">
			<h2><ikep4j:message pre='${preList}' key='myPoll' /></h2>
		</c:when>
		<c:otherwise>
		<h2><ikep4j:message pre='${preSub}' key='applyPoll' /></h2>					 				
		</c:otherwise>		 			
	</c:choose>		
</div>
<!--//pageTitle End-->

<!--tab Start-->		
<div id="divTab1" class="iKEP_tab_poll">
	<ul>
 		<c:choose>
 			<c:when test="${searchCondition.isComplete != '0'}">
				<li class="w50"><a href="#tabs-1"><ikep4j:message pre='${preList}' key='currentPoll' /></a></li>
				<li class="w50"><a href="#tabs-2"><ikep4j:message pre='${preList}' key='finishPoll' /></a></li>
 			</c:when>
 			<c:otherwise>
 				<li class="w50"><a href="#tabs-1"><ikep4j:message pre='${preList}' key='myPoll' /></a></li>
 			</c:otherwise>		 			
 		</c:choose>						
	</ul>
	<div id="tabs-1" style="display: none;"></div>
	<div id="tabs-2" style="display: none;"></div>						
</div>		
<!--//tab End-->				

<!--blockButton Start-->
<c:if test="${searchCondition.isComplete != '0'}">
<div class="blockButton" id="pollListButton"> 
	<ul>
		<li><a class="button_s" href="#a" id="pollListAllButton"><span><ikep4j:message pre='${preList}' key='all' /></span></a></li>
		<li><a class="button_s" href="#a" id="pollListAppliedButton"><span><ikep4j:message pre='${preList}' key='applyPoll' /></span></a></li>		
		<li><a class="button_s" href="#a" id="pollListNonAppliedButton"><span><ikep4j:message pre='${preList}' key='notApplyPoll' /></span></a></li>												
	</ul>
</div>
</c:if>
<!--//blockButton End-->	

<c:if test="${searchResult.pageCount == 0}">
	<div style="margin-top:5px;margin-left:15px;"><ikep4j:message pre='${preList}' key='pollEmpty' /></div>				
</c:if>
<!--online_poll Start-->
<div id="listFrame">
	<c:forEach var="poll" items="${searchResult.entity}" varStatus="pollLoopCount">
	
		<c:choose>			
			<c:when test="${poll.isApply == '0'}">
				<c:set var="applyImgName" value="ic_vote_select"/>
			</c:when>
			<c:otherwise>
				<c:set var="applyImgName" value="ic_vote"/>
			</c:otherwise>
		</c:choose>				 			
	<div class="blockTableRead online_poll">
		<h3 class="none">Question</h3>
		<div class="qnaimg">
			<img src="<c:url value='/base/images/icon/${applyImgName}.gif' />" alt="" width="50" height="50" />
		</div>				
		<div class="blockTableRead_t">
			<c:if test="${searchCondition.isComplete == '1'}">
				<div class="online_poll_num"><span>${poll.resultCnt}</span> <ikep4j:message pre='${preList}' key='etc' /></div>
			</c:if>					
			<p>${poll.question}</p> &nbsp;
			<div class="summaryViewInfo">
		 		<c:choose>
		 			<c:when test="${poll.permissionType == '0'}">
		 				<span class="ty1"><ikep4j:message pre='${preForm}' key='previewPermissionType01' /></span>
		 			</c:when>
		 			<c:otherwise>
		 				<span class="ty1"><ikep4j:message pre='${preForm}' key='previewPermissionType02' /></span>
		 			</c:otherwise>		 			
		 		</c:choose>								 					
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
				<c:if test="${searchCondition.isComplete == '0'}">
				  	<ikep4j:message pre='${preList}' key='writing' />
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
				</c:if>	
				<c:if test="${searchCondition.isComplete == '1'}">
				  	<ikep4j:message pre='${preList}' key='applying' />
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
				</c:if>	
				<c:if test="${searchCondition.isComplete == '2'}">
				  	<ikep4j:message pre='${preList}' key='finish' />
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
				</c:if>																						
				<span class="summaryViewInfo_name"><ikep4j:message pre='${preForm}' key='userName' /> :
				     <c:choose>
				      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
				       ${poll.registerName} ${poll.jobTitleName} ${poll.teamName}
				      </c:when>
				      <c:otherwise>
				       ${poll.registerEnglishName} ${poll.jobTitleEnglishName} ${poll.teamEnglishName}
				      </c:otherwise>
				     </c:choose>							
				</span>
				<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
				<span><ikep4j:message pre='${preForm}' key='date' /> : <strong><ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>~<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/></strong></span>
			</div>
		</div>
	
		<c:if test="${searchCondition.isComplete == '1'}">
			<div class="online_poll_graph">
				<div class="online_poll_balloon">
					<span><ikep4j:message pre='${preList}' key='remain' /> : </span><span class="colorPoint">${poll.remainDayCnt} <ikep4j:message pre='${preList}' key='date' /></span>
					<div class="online_poll_balloon_r"></div>
				</div>
				<div id="voteRange${pollLoopCount.count}-<ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type2" keyString="true"/>-<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type2" keyString="true"/>" class="voteRange">
					<div class="bar_l"></div>
					<div class="bar_r"></div>
				</div>
	
				<div class="online_poll_day">
					<span><ikep4j:message pre='${preList}' key='startDate' /><br /><ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/></span><span class="lastday"><ikep4j:message pre='${preList}' key='endDate' /><br /><ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/></span>
				</div>
			</div>
		</c:if>

		<table width="100%" summary="" >
			<caption></caption>
			<tbody>
				<tr>
					<td valign="top">		
						<div class="online_poll_list">						
							<ul>
								<c:forEach var="answer" items="${poll.answerList}" varStatus="answerLoop">
									<li>(${answerLoop.count}) ${answer.answerTitle}</li>
								</c:forEach>																				
							</ul>
						</div>									
						<c:if test="${searchCondition.isComplete == '2'}">
							<div class="online_poll_num1 lp70"><img src="<c:url value='/base/images/icon/ic_arrow_3.gif' />" alt="" width="5px" height="7px"/> <ikep4j:message pre='${preForm}' key='total' /> <span>${poll.resultCnt}</span> <ikep4j:message pre='${preList}' key='etc' /></div>
						</c:if>								
					</td>
					<td width="250" valign="top" >
						<c:if test="${searchCondition.isComplete == '2'}">
						<div class="online_poll_graph1">									
				 		<c:choose>			
				 			<c:when test="${poll.chartType == '2'}">						 			
								<div class="online_poll_graph">											
									<div id="chart${pollLoopCount.count}-<ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type2" keyString="true"/>-<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type2" keyString="true"/>" class="voteRange">
										<div class="bar_l"></div>
										<div class="bar_r"></div>
									</div>
									<div class="online_poll_day">
										<span id="yes${pollLoopCount.count}"></span>
										<span class="lastday" id="no${pollLoopCount.count}"></span>
									</div>
								</div>	
				 			</c:when>
				 			<c:otherwise>
				 				<c:if test="${poll.chartType == '0'}">
				 					<div id="chart${pollLoopCount.count}" style="margin-top:10px;width:190px; height:165px;"></div>
				 				</c:if>
				 				<c:if test="${poll.chartType == '1'}">
				 					<div id="chart${pollLoopCount.count}" style="margin-right:20px;width:250px; height:230px;"></div>
				 				</c:if>
				 			</c:otherwise>
				 		</c:choose>	
	
						<script type="text/javascript">
							$jq(document).ready(function() {
							    var barData = new Array(); 
							    var pieData = new Array();
								<c:forEach var="answer" items="${poll.answerList}" varStatus="answerLoop">
									barData[${answerLoop.count}-1] = ${answer.answerTotal};
									pieData[${answerLoop.count}-1] = ['(${answerLoop.count})', ${answer.answerTotal}];
								</c:forEach>					      
							    var ticks = new Array(); 
							    for ( i =0; i< ${poll.answerCount}; i++ ) {
							    	ticks[i] = "("+eval(i+1)+")";
							    }
							    
							    if ( ${poll.resultCnt} >0 ) {
						 		<c:choose>			
						 			<c:when test="${poll.chartType == '0'}">
						 				barChart("chart${pollLoopCount.count}", barData, ticks);
						 			</c:when>
						 			<c:when test="${poll.chartType == '1'}">
						 				pieChart("chart${pollLoopCount.count}", pieData);	
					 				</c:when>										 			
						 			<c:otherwise>
						 				etcChart("${pollLoopCount.count}", barData);
						 			</c:otherwise>
					 			</c:choose>	
							    }
							});
						</script>									
	
							
						</div>
						</c:if>
					</td>								
				</tr>
			</tbody>
		</table>
	
		<!--blockButton Start-->
		<div class="blockButton textLeft lp70">
			<ul>
	 		<c:choose>
	 			<c:when test="${searchCondition.isComplete == '0'}">
	 				<c:if test="${poll.status == '0'}">
						<li><a class="button" href="#a" onclick="openPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='open' /></span></a></li>
						<li><a class="button" href="#a" onclick="modifyPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='modify' /></span></a></li>
					</c:if>	
					<c:if test="${poll.status == '1'}">
						<li><a class="button" href="#a" onclick="completePoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='finish' /></span></a></li>
					</c:if>	
						<li><a class="button" href="#a" onclick="deletePoll('${poll.pollId}')"><span><ikep4j:message pre='${preAdmin}' key='del' /></span></a></li>
	 			</c:when>
	 			<c:when test="${searchCondition.isComplete == '2'}">
	 				<li><a class="button" href="#a" onclick="openPollWin('${poll.pollId}','result')"><span><ikep4j:message pre='${preList}' key='result' /></span></a></li>															
	 			</c:when>
	 			<c:when test="${searchCondition.isComplete == '1'}">
					<c:if test="${(poll.permissionType == '0' || poll.isTarget != '0') && poll.isApply == '0' }">
						<c:if test="${poll.startDate > toDate}">
						<li><a class="button" href="#a" onclick="validTime();"><span><ikep4j:message pre='${preList}' key='do' /></span></a></li>
						</c:if>
						<c:if test="${poll.startDate <= toDate}">
						<li><a class="button" href="#a" onclick="openPollWin('${poll.pollId}','apply')"><span><ikep4j:message pre='${preList}' key='do' /></span></a></li>
						</c:if>					
					</c:if>
			 		<c:choose>
			 			<c:when test="${isAdmin == true}">
			 				<c:set var="modeName" value="admin"/>
			 			</c:when>
			 			<c:otherwise>
			 				<c:set var="modeName" value="result"/>
			 			</c:otherwise>		 			
			 		</c:choose>
			 		<c:if test="${poll.startDate <= toDate}">									
					<li><a class="button" href="#a" onclick="openPollWin('${poll.pollId}','${modeName}')"><span><ikep4j:message pre='${preList}' key='result' /></span></a></li>
	 				</c:if>
	 				<c:if test="${poll.startDate > toDate}">
	 				<li><a class="button" href="#a" onclick="validTime2();"><span><ikep4j:message pre='${preList}' key='result' /></span></a></li>
	 				</c:if>
	 			</c:when>		 			
	 		</c:choose>	
	 		<li></li>					 							
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	</c:forEach>
</div>
<!--//online_poll End-->

<c:if test="${searchResult.recordCount > 10}">
	<!--blockButton_3 Start-->
	<div class="blockButton_3"> 
		<a class="button_3" href="#a" onclick="moreList();return false;"><span id="moreText"><ikep4j:message pre='${preList}' key='morePage' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="" /></span></a>				
	</div>
	<!--//blockButton_3 End-->	
</c:if>	