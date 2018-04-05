<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preForm"    value="ui.support.poll.form" />
<c:set var="preAdmin"    value="ui.support.poll.admin" />
<c:set var="preList"    value="ui.support.poll.list" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
<!--
(function($) { 
	$(document).ready(function() {
		<c:if test="${docPopup == 'true'}">
		window.resizeTo(580,350);
		</c:if>
		$jq('#btnMyList').click(function(event) { //목록보기 이동
			if ( "${poll.viewMode}" == "admin" ) {
				document.location.href="pollAdminList.do";
			} else {
				document.location.href="pollList.do?isComplete=1";	
			}
		});			
	});
	
	barChart = function(el, data, ticks) {
		
		$jq("#"+el).css("border","1px solid #dfdfdf");
		
		$jq.jqplot(el, [data], {
			grid: {
   	    		drawGridlines: false,
   	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false	
   	    	},   	    	
   	    	seriesColors: ["#7f7dff"],
   	    	seriesDefaults:{
   	    		shadow: true,   // show shadow or not.
		        shadowAngle: 70,    // angle (degrees) of the shadow, clockwise from x axis.
   	    		renderer:$.jqplot.BarRenderer,  // 차트 형태
   	    		rendererOptions: {
   	    			barWidth: 20
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
	                max:getMax(data),
		    		tickOptions:{formatString:'%.0f'}
	        	}
   	    	}
        });
		
		/*
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
		*/
	};
	//파이차트
	pieChart = function(el, data) {
		$jq.jqplot(el, [data], {
			grid: {
	    		drawGridlines: false,
   	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false
	    	},
	    	title:'',   // 차트 타이틀
	    	seriesColors:['#93c8fb', '#ff66fd', '#fdf701', '#99cc66', '#ffcc66','#d099fe','#2dc8fe','#fe9798','#d3ca6b'],
	    	seriesDefaults:{
	    		shadow: false,   // show shadow or not.
		        shadowAngle: 45,    // angle (degrees) of the shadow, clockwise from x axis.
	    		renderer:$.jqplot.PieRenderer,
	    		rendererOptions: {
	    			sliceMargin:2,   // 파이 조각 표시 시 마진값
	    			showDataLabels: true,
	    			dataLabelThreshold: 1,
	                dataLabelFormatString: '%.0f%%'
	    		}
	    	},
	    	legend: {
	    		show: true,
	    		location: 'e',
	    		escapeHTML:true
	    	}
        });
		/*
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
		*/
	};		
	//찬성반대 그래프
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
//투표삭제하기
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
				if ( mode == "view") {
					location.href="pollList.do?isComplete="+status;
				} else if ( mode == "result") {
					var status = ${poll.status};
					location.href="pollList.do?isComplete="+status;
				} else if ( mode == "admin" ) {
					location.href="<c:url value='/support/poll/pollAdminList.do'/>"	
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
						document.location.href="pollList.do?isComplete=1";
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
//투표수정하기
function modifyPoll(pollId) {
	document.location.href='formPoll.do?pollId='+pollId;
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
		<div class="online_poll_num"><span>${poll.resultCnt}</span> <ikep4j:message pre='${preList}' key='etc' /></div>
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
				
				<td width="280">
					<c:if test="${viewMode == 'result' || viewMode == 'admin' }">
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
			 				<div id="chart1" style="margin-right:10px;margin-top:10px;width:250px;height:220px;"></div>
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
 				<c:if test="${poll.status == '1' && (poll.registerId == user.userId)}">
 					<li><a class="button" href="#a" onclick="completePoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='finish' /></span></a></li>
 				</c:if>
 				<c:if test="${poll.status == '2'}">
					<li><a class="button" href="#a" onclick="deletePoll('${poll.pollId}','result')"><span><ikep4j:message pre='${preAdmin}' key='del' /></span></a></li>
				</c:if>	
 			</c:if>
 			<c:if test="${viewMode == 'apply'}">
 				<li><a class="button" href="#a" onclick="applyPoll()"><span><ikep4j:message pre='${preList}' key='do' /></span></a></li>
 			</c:if>
 			<c:if test="${viewMode == 'admin' || isAdmin == true}">
 				<c:if test="${poll.status == '0'}">
					<li><a class="button" href="#a" onclick="openPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='open' /></span></a></li>
				</c:if>
				<c:if test="${poll.status == '1'}">
					<li><a class="button" href="#a" onclick="completePoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='finish' /></span></a></li>
				</c:if>
				<li><a class="button" href="#a" onclick="modifyPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='modify' /></span></a></li>
				<li><a class="button" href="#a" onclick="deletePoll('${poll.pollId}','admin')"><span><ikep4j:message pre='${preAdmin}' key='del' /></span></a></li>
 			</c:if>		 			
	 			<li><a class="button" href="#a"><span id="btnMyList"><ikep4j:message pre='${preForm}' key='list' /></span></a></li>	
		</ul>
	</div>				
</div>	
