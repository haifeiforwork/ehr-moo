<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.report" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" /> 
<c:set var="preReport"  value="message.servicepack.survey.report" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 

<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='title' var="title"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   
$jq(document).ready(function() {
	$jq("div[id^='progressbar']").each(function(){
		var val = parseFloat($jq(this).next().val());
		
		if( val > 0 && val < 1 ) val =1;
		
		$jq(this).progressbar({
			  value: val
		 });
	});
	
	$jq("#mailButton").click(function() {
		var dialog = new iKEP.Dialog({
			title: "<ikep4j:message key='ui.servicepack.survey.header.open.pageTitle'/>",
			url: "<c:url value='/servicepack/survey/open.do?surveyId='/>${survey.surveyId }",
			modal: true,
			width: 500,
			height: 250,
			resizable:false,
			scroll:"no",
			params : {},
			callback : function(result) {
				$jq("#mailForm input[name='mailSendYn']").val( result.mailSendYn );
				$jq("#mailForm input[name='sendOption']").val( result.sendOption );
				$jq("#mailForm input[name='sendComment']").val( result.sendComment );
				$jq("#mailForm").submit();
			}
		});
 		return false; 
 	});
	
});

function resultErro(){
	alert('<ikep4j:message  key="message.servicepack.survey.open.not.valid" />');
}
//-->
 </script> 
<!--mainContents Start-->
				<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
				<!--pageTitle Start-->
				<div id="pageTitle"> 
					<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
				</div> 
	
					
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>${survey.title}</h3>
				</div>
				<!--//subTitle_2 End-->

				<!--blockDetail Start-->					
				<div class="blockListTable">
				    <form id="mailForm" method="post" action="<c:url value='/servicepack/survey/sendNotMail.do'/>">
						<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
						<input type="hidden" name="mailSendYn"  value="1" title=""/>
						<input type="hidden" name="sendOption"  value="0" title=""/>
						<input type="hidden" name="sendComment"  value="" title=""/>
					</form>	
					<table summary="설문결과">
						<thead>
							<tr>
								<th scope="col" width="13%"><ikep4j:message pre="${preReport}" key="yyyymmdd" /></th>
								<th scope="col" width="13%"><ikep4j:message pre="${preReport}" key="day" /></th>
								<th scope="col" width="13%"><ikep4j:message pre="${preReport}" key="responsePerson" /></th>
								<th scope="col" width="8%"><ikep4j:message pre="${preReport}" key="avg" /></th>

								<th scope="col" width="*"><ikep4j:message pre="${preReport}" key="graph" /></th>
							</tr>
						</thead>
						<tbody>
						<c:set var="responseTotalPerson" value="0"/>
							<c:forEach var="response" items="${resultList}" varStatus="qloopStatus">
							<tr>
								<td><ikep4j:timezone  date="${response.responseDate}"   pattern="message.servicepack.survey.timezone.dateformat.yyyyMMdd" keyString="true"/></td>
								<td><ikep4j:message pre="ui.servicepack.survey.common.day" key='${response.day}'/></td>
								<td>${response.responseCnt}</td>
								<td class="textRight"><fmt:formatNumber var="totalAvg" value="${response.responseAvg}" pattern="#0.00"/>
										${totalAvg} &nbsp; &nbsp;</td>
								<td>
									<div class="survey_graph">
										<div id="progressbar${qloopStatus.index}"></div>
										<input type="hidden" id="v_progressbar${qloopStatus.index}" name="v_progressbar${qloopStatus.index}" value="${response.responseAvg}" title=""/>	
									</div>
								</td>
							</tr>
							<c:set var="responseTotalPerson" value="${responseTotalPerson+response.responseCnt}"/>
								
							</c:forEach>		

							<tr class="bgSelected">
								<td colspan="2"><ikep4j:message pre="${preReport}" key='sum'/></td>
								<td>${responseTotalPerson}</td>
								<td class="textRight">
								<c:choose>
									<c:when test="${totalSendLog > 0 }">
										<fmt:formatNumber var="totalAvg" value="${(responseTotalPerson/totalSendLog)*100 }" pattern="#0.00"/>
										${totalAvg}						
									</c:when>
									<c:otherwise>0</c:otherwise>
								</c:choose>
								 &nbsp; &nbsp;</td>
								<td> <ikep4j:message pre="${preReport}" key='responseTotalPerson' arguments="${responseTotalPerson}"/>/ 
									 <ikep4j:message pre="${preReport}" key='responseLogTotalPerson' arguments="${totalSendLog}"/>
								</td>
							</tr>							
						</tbody>

					</table>
				</div>
				<!--//blockDetail End-->
								
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
					    <c:if test="${surveyAuth.mail}">
					    <li><a id="editButton1" class="button" href="<c:url value='/servicepack/survey/updateSurvey.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='surveyEdit'/></span></a></li>
						<li><a id="questionButton1" class="button" href="<c:url value='/servicepack/survey/question/createQuestion.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='questionEdit'/></span></a></li>
						<li><a id="questionJoinButton1" class="button" ><span>연결문항설정</span></a></li>
					    <li><a id="mailButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='responseNotInSendMail'/></span></a></li>
					    </c:if>
						<li><a class="button" href="<c:url value='/servicepack/survey/report/resultDetail.do'/>?surveyId=${survey.surveyId }"><span><ikep4j:message pre='${preButton}' key='resultDetail'/></span></a></li>
						<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/surveyEndList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
 
 