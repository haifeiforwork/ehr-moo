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

<script type="text/javascript">
<!--
(function($) { 
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



//-->
</script> 
				<c:forEach var="poll" items="${searchResult.entity}" varStatus="pollLoopCount">
					<c:set var="curNum" value="${pollLoopCount.count+((searchCondition.pageCount-1)*10)}"/>
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
					<c:if test="${searchCondition.isComplete != '0'}">
						<div class="qnaimg">
							<img src="<c:url value='/base/images/icon/${applyImgName}.gif' />" alt="" width="50" height="50" />
						</div>				
					</c:if>
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
								<span><ikep4j:message pre='${preList}' key='remain' /> : </span><span id="days" class="colorPoint">${poll.remainDayCnt} <ikep4j:message pre='${preList}' key='date' /></span>
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
												<div id="chart${curNum}-<ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type2" keyString="true"/>-<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type2" keyString="true"/>" class="voteRange">
													<div class="bar_l"></div>
													<div class="bar_r"></div>
												</div>
												<div class="online_poll_day">
													<span id="yes${curNum}"></span>
													<span class="lastday" id="no${curNum}"></span>
												</div>
											</div>	
							 			</c:when>
							 			<c:otherwise>
							 				<c:if test="${poll.chartType == '0'}">
							 					<div id="chart${curNum}" style="margin-top:10px;width:190px; height:165px;"></div>
							 				</c:if>
							 				<c:if test="${poll.chartType == '1'}">
							 					<div id="chart${curNum}" style="margin-right:20px;width:250px; height:230px;"></div>
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
									 				barChart("chart${curNum}", barData, ticks);
									 			</c:when>
									 			<c:when test="${poll.chartType == '1'}">
									 				pieChart("chart${curNum}", pieData);
								 				</c:when>										 			
									 			<c:otherwise>
									 				etcChart("${curNum}", barData);
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
				 			<c:when test="${searchCondition.isComplete == '0' || searchCondition.isComplete == '2'}">
				 				<c:if test="${poll.status == '0'}">
									<li><a class="button" href="#a" onclick="openPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='open' /></span></a></li>
									<li><a class="button" href="#a" onclick="modifyPoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='modify' /></span></a></li>
									<li><a class="button" href="#a" onclick="deletePoll('${poll.pollId}')"><span><ikep4j:message pre='${preAdmin}' key='del' /></span></a></li>
								</c:if>	
								<c:if test="${poll.status == '2'}">
									<li><a class="button" href="#a" onclick="deletePoll('${poll.pollId}')"><span><ikep4j:message pre='${preAdmin}' key='del' /></span></a></li>
								</c:if>																
				 			</c:when>
				 			<c:otherwise>
								<c:if test="${searchCondition.isComplete == '1'}">
									<c:if test="${(poll.permissionType == '0' || poll.isTarget != '0') && poll.isApply == '0' }">
										<li><a class="button" href="#a" onclick="openPollWin('${poll.pollId}','apply')"><span><ikep4j:message pre='${preList}' key='do' /></span></a></li>
									</c:if>
							 		<c:choose>
							 			<c:when test="${isAdmin == true}">
							 				<c:set var="modeName" value="admin"/>
							 			</c:when>
							 			<c:otherwise>
							 				<c:set var="modeName" value="result"/>
							 			</c:otherwise>		 			
							 		</c:choose>									
									<li><a class="button" href="#a" onclick="openPollWin('${poll.pollId}','${modeName}')"><span><ikep4j:message pre='${preList}' key='result' /></span></a></li>									
								</c:if>
								<c:if test="${searchCondition.isComplete == '1' && ( poll.registerId == user.userId || isAdmin == true ) }">
									<li><a class="button" href="#a" onclick="completePoll('${poll.pollId}')"><span><ikep4j:message pre='${preList}' key='finish' /></span></a></li>
								</c:if>
				 			</c:otherwise>		 			
				 		</c:choose>					 						 								
						</ul>
					</div>

					<!--//blockButton End-->
					
				</div>
				</c:forEach>