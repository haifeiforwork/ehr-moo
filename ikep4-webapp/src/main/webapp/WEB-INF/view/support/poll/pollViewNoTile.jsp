<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preForm"    value="ui.support.poll.form" />
<c:set var="preAdmin"    value="ui.support.poll.admin" />
<c:set var="preList"    value="ui.support.poll.list" />
<c:set var="user" value="${sessionScope['ikep.user']}" />


<script type="text/javascript">
<!--
(function($) { 
	//바차트
	barChart = function(el, data, ticks) {	 
		$jq.jqplot(el, [data], {
			grid: {                
                background: '#FCFCFC'
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
	//파이차트
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
	//찬성반대그래프
	etcChart = function(el, data) {  
		var yesPercent = 0;
		var noPercent = 0;
		var totalCnt = 0;

		for(var i=0; i<data.length; i++) {
			totalCnt += data[i];
		}		
		if ( totalCnt != 0 ) {
			yesPercent = data[0]/totalCnt*100;
			yesPercent = Math.round(yesPercent*10)/10;
			noPercent = 100 - yesPercent;
		}
		$("#chart"+el).progressbar({  
  			value: yesPercent 
 		});			
		$("#yes"+el).text( "<ikep4j:message pre='${preList}' key='yes' /> : "+yesPercent + "%");
		$("#no"+el).text( "<ikep4j:message pre='${preList}' key='no' /> : "+noPercent+"%");
	};
})(jQuery); 
//차트 요소등 가장 최대값 가져오기 (최대 막대 높이를 설정하기 위함)
function getMax(arr) {
	 var max = arr[0]; 
	 for(var i=1; i<arr.length; i++) 
	  if(max < arr[i]) 
	   max = arr[i]; 
	 return (max+max*30/100); 
}
//투표마감
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
				//window.close();
				//parent.opener.location.href="pollList.do?isComplete=2";
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
//투표오픈
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
				//window.close();
				//parent.opener.location.href="pollList.do?isComplete=1";
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
//투표삭제
function deletePoll(pollId,mode) {
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
				//window.close();
				if ( mode == "view") {
					//parent.opener.location.reload();
					document.opener.location.href="pollList.do?isComplete="+status;
				} else if ( mode == "result") {
					var status = ${poll.status};
					//parent.opener.location.href="pollList.do?isComplete="+status;
					document.opener.location.href="pollList.do?isComplete="+status;
				}
			},
			error:function(){
				alert("<ikep4j:message pre='${preList}' key='failDel' />");
			}
		});	
	} else {
		return;
	}
}
//투표하기
function applyPoll() {
	var rdAnswer = $jq("input[name=rdAnswer]:radio:checked").val();
	var rdLen = $jq("input[name=rdAnswer]:radio:checked").length;
	var rdUserId = "${user.userId}";

	if (rdLen == 0) {
		alert("<ikep4j:message pre='${preList}' key='answerEmpty' />");
		return;
	}
	var pollId = ${poll.pollId};

	//이미 투표했는지 여부 체크
	$jq.ajax({
		url : '<c:url value="checkAlreadyApply.do" />',
		data : {pollId:pollId,answerId:rdAnswer,answerUserId:rdUserId},
		type : "post",
		success : function(result) { 
			if ( result != 0 ) { //이미투표했을 경우 경고창
				alert("<ikep4j:message pre='${preList}' key='alreadyApply' />");
			} else {
				$jq.ajax({
					url : '<c:url value="applyPoll.do" />',
					data : {pollId:pollId,answerId:rdAnswer},
					type : "post",
					success : function(result) { 
						alert("<ikep4j:message pre='${preList}' key='successApply' />");
					},
					error:function(){
						alert("<ikep4j:message pre='${preList}' key='failApply' />");
					}
				});					
			}
		},
		error:function(){
			alert("<ikep4j:message pre='${preList}' key='failApply' />");
		}
	});	
}
//투표수정
function modifyPoll(pollId) {
	document.location.href='formPoll.do?pollId='+pollId;
}
//투표결과보기
function resultPoll(pollId) {
	var mode = "result";
	var pageURL = "<c:url value='/support/poll/viewPoll.do' />" + "?pollId=" + pollId + "&viewMode=" + mode + "&docPopup=true";
	document.location.href=pageURL;
}
//-->
</script> 
				<div class="blockTableRead online_poll">
					<c:set var="viewMode" value="${poll.viewMode }"/>
					<c:set var="toDate"><ikep4j:timezone date='${poll.toDate}' pattern='message.support.poll.timezone.dateformat.type' keyString='true'/></c:set>
					<c:set var="endDate"><ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/></c:set>
					<c:if test="${toDate > endDate}">
						<c:set var="viewMode" value="result"/>
					</c:if>
					<c:choose>			
				 			<c:when test="${poll.isApply == '0'}">
				 				<c:set var="applyImgName" value="ic_vote_select"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="applyImgName" value="ic_vote"/>
				 			</c:otherwise>
				 	</c:choose>
				 	<div class="qnaimg">
							<img src="<c:url value='/base/images/icon/${applyImgName}.gif' />" alt="" width="50" height="50" />
					</div>
					<h3 class="none">Question</h3>
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
							<span class="summaryViewInfo_name"><ikep4j:message pre='${preForm}' key='userName' /> : ${poll.registerName}</span>
							<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
							<span><ikep4j:message pre='${preAdmin}' key='date' /> : <strong><ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>~<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/></strong></span>
						</div>
					</div>
					
					<table width="100%" summary="" >
						<caption></caption>
						<tbody>
							<tr>
								<td valign="top">		
									<div class="online_poll_list">						
										
											<c:forEach var="answer" items="${poll.answerList}" varStatus="answerLoop">
										 		<c:choose>			
										 			<c:when test="${viewMode == 'apply'}">	
										 				<div class="mb5">
										 					<label><input id="rdAnswer" name="rdAnswer" type="radio" class="radio" value="${answer.answerId}" title="${answer.answerTitle}" style="vertical-align:middle;">&nbsp; (${answerLoop.count}) ${answer.answerTitle}</label>
										 				</div>
										 			</c:when>
										 			<c:otherwise>
										 				<ul>
										 					<li>(${answerLoop.count}) ${answer.answerTitle}
										 					<c:if test="${viewMode == 'result'}">&nbsp;
										 					${answer.answerTotal}/${answer.answerTotalSum}
										 					(${answer.answerPercent}%)
										 					</c:if>
										 					</li>
										 				</ul>
										 			</c:otherwise>	
										 		</c:choose>									 													
											</c:forEach>																				
										
									</div>																
								</td>
								
								<td width="250" valign="top" >
									<c:if test="${viewMode == 'result'}">
									<div class="online_poll_graph1">									
							 		<c:choose>			
							 			<c:when test="${poll.chartType == '2'}">						 			
											<div class="online_poll_graph">											
												<div id="chart1" class="voteRange">
													<div class="bar_l"></div>
													<div class="bar_r"></div>
												</div>
												<div class="online_poll_day">
													<span id="yes1"></span>
													<span class="lastday" id="no1"></span>
												</div>
											</div>	
							 			</c:when>
							 			<c:otherwise>
							 				<div id="chart1" style="margin-right:20px;width:200px; height:184px;"></div>
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
									 				barChart("chart1", barData, ticks);
									 			</c:when>
									 			<c:when test="${poll.chartType == '1'}">
									 				pieChart("chart1", pieData);	
								 				</c:when>										 			
									 			<c:otherwise>
									 				etcChart("1", barData);
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
					
					<div class="blockButton textLeft lp70"> 
						<ul>
				 			<c:if test="${viewMode == 'result'}">
								<li><a class="button" href="#a" onclick="window.close()"><span><ikep4j:message pre='${preForm}' key='cancel' /></span></a></li>					 				
				 			</c:if>
				 			<c:if test="${viewMode == 'apply'}">
				 				<li><a class="button" href="#a" onclick="applyPoll()"><span><ikep4j:message pre='${preList}' key='do' /></span></a></li>
				 				<li><a class="button" href="#a" onclick="resultPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='result' /></span></a></li>	
				 			</c:if>
						</ul>
					</div>				
				</div>			