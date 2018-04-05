<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.report" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" /> 
<c:set var="preReport"  value="message.servicepack.survey.report" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='title' var="title"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shCore.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shBrushJScript.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/syntaxhighlighter_3.0.83/shBrushXml.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jquery.jqplot.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/excanvas.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.barRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.categoryAxisRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.pointLabels.min.js'/>"></script>


 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
 
 
<!--
var excelBtnCnt = 0;


(function($){
	$(document).ready(function() {
		$("div[id^='progressbar']").each(function(){
			var val = parseFloat($(this).next().val());
			if( val > 0 && val < 1 ) val =1;
			
			if( val != '0')
			$(this).progressbar({
				  value: val
			 });
		});
		
		$("#downloadButton").click(function() {
			if(excelBtnCnt == 0){
				$('#downloadForm').submit();
				excelBtnCnt++;
	
			}else{
				alert("조회 후 엑셀 다운 받으세요.");
			}
	 		return false; 
	 	});
	});
})(jQuery);

function resultErro(){
	alert('<ikep4j:message  key="message.servicepack.survey.open.not.valid" />');
}
//-->
 </script> 
<!--mainContents Start-->
<form id="downloadForm" method="post" action="<c:url value='/servicepack/survey/report/resultDetailExcel.do'/>">
	<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
</form>	
				<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageDetailTitle" /></h1> 
				<!--pageTitle Start-->
				<div id="pageTitle"> 
					<h2><ikep4j:message pre="${preHeader}" key="pageDetailTitle" /></h2> 
				</div> 

				<!--//subTitle_2 End-->
				<div class="survey_preview_t">				
						<h3>${survey.title}</h3>
						<p><c:choose>
							<c:when test="${survey.contentsType eq 0 }">
							 	${fn:replace(survey.contents, crlf, "<br/>")}
							</c:when>
							<c:otherwise>
							<img id="profilePictureImage"
						     src="<c:url value='/support/fileupload/downloadFile.do?fileId=${fileId}' />" alt="<ikep4j:message pre='${preList}' key='contentsImage' />" />
							</c:otherwise>
						</c:choose>
					  </p>	
					</div>
					<div class="survey_preview_b">
							<c:set var="questionIndex" value="0"/>
							<c:set var="chartIndex" value="0"/>
							<c:forEach var="questionGroup" items="${questionGroupList}" varStatus="gloopStatus">
								<c:forEach var="question" items="${questionGroup.questionList}" varStatus="qloopStatus">
								<c:set var="questionIndex" value="${questionIndex+1}"/>
								<div class="blockListTable">
									<c:choose>
										<c:when test="${fn:indexOf(question.questionType,'A')>=0 || fn:indexOf(question.questionType,'B')>=0}">
											<p><strong>[${questionIndex}]</strong>&nbsp;${question.title}</p>
											<div class="blockDetail">
												<table summary="일반게시판">
													<thead>
														<tr>
															<th scope="col" width="50%" class="textCenter"><ikep4j:message pre="${preReport}" key="item" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="responsePerson" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="avg" /></th>
															<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preReport}" key="graph" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="etc" /></th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="answer" items="${question.answer}" varStatus="answerloopStatus">
															<tr>
																<td class="textLeft">${answer.title}</td>
																<td class="textCenter">${answer.responseCnt}/${totalSendLog}</td>
																<td class="textCenter">
																	<c:set var="avg" value="0"/>
																	<c:choose>
																		<c:when test="${totalSendLog > 0 }">
																			<fmt:formatNumber var="avg" value="${(answer.responseCnt/totalSendLog)*100 }" pattern="#0.00"/>
																			${avg}										
																		</c:when>
																		<c:otherwise>0</c:otherwise>
																	</c:choose>
																</td>
																<td>
																	<div class="survey_graph">
																		<div id="progressbar_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}"></div>	
																		<input type="hidden" id="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}" name="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}" value="${avg}" title=""/>				
																	</div>
																</td>
																<td>
																	 <c:if test="${!empty answer.responseText}">${answer.responseText}</c:if> 
																</td>
															</tr>
														</c:forEach>	
													</tbody>
												</table>
											</div>
										</c:when>
										<c:when test="${fn:indexOf(question.questionType,'C')>=0}">
											<p><strong>[${questionIndex}]</strong>&nbsp;${question.title}</p>
											<div class="blockDetail">
												<table summary="일반게시판">
													<thead>
														<tr>
															<th scope="col" width="50%" class="textCenter"><ikep4j:message pre="${preReport}" key="item" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="seq" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="responsePerson" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="avg" /></th>
															<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preReport}" key="graph" /></th>
														</tr>
													</thead>
													<tbody>
													    <c:set var="answerRowCnt" value="${fn:length(question.answer)}"/> 
														<c:forEach var="answer" items="${question.answer}" varStatus="answerloopStatus">
															<c:forEach var="item" items="${answer.response1}" varStatus="itemStatus">
																<tr>
																	<c:if test="${itemStatus.first}"><td class="textLeft" rowspan="${answerRowCnt}">${answer.title}</td></c:if>
																	<td class="textCenter">${itemStatus.index+1}</td>
																	<td class="textCenter">${item}/${totalSendLog}</td>
																	<td class="textCenter">
																		<c:set var="avg" value="0"/>
																		<c:choose>
																			<c:when test="${totalSendLog > 0 }">
																				<fmt:formatNumber var="avg" value="${(item/totalSendLog)*100 }" pattern="#0.00"/>
																				${avg}										
																			</c:when>
																			<c:otherwise>0</c:otherwise>
																		</c:choose>
																	</td>
																	<td>
																		<div class="survey_graph">
																			<div id="progressbar_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}"></div>	
																			<input type="hidden" id="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}" name="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}" value="${avg}" title=""/>					
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</c:forEach>	
													</tbody>
												</table>
											</div>
										</c:when>
										<c:when test="${fn:indexOf(question.questionType,'D')>=0}">
											<p><strong>[${questionIndex}]</strong>&nbsp;${question.title}</p>
											<div class="blockDetail">
												<table summary="일반게시판">
													<thead>
														<tr>
															<th scope="col" width="*" class="textCenter"><ikep4j:message pre="${preReport}" key="item" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="seq" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="responsePerson" /></th>
															<th scope="col" width="10%" class="textCenter"><ikep4j:message pre="${preReport}" key="avg" /></th>
															<th scope="col" width="20%" class="textCenter"><ikep4j:message pre="${preReport}" key="graph" /></th>
														</tr>
													</thead>
													<tbody>
														<c:forEach var="answer" items="${question.answer}" varStatus="answerloopStatus">
															<c:forEach var="item" items="${answer.response2}" varStatus="itemStatus">
															    <c:set var="answerRowCnt" value="${fn:length(answer.response2)}"/> 
															    <c:if test="${itemStatus.first && question.questionType eq 'D6'}">
															    	<tr>
																		<c:if test="${itemStatus.first}"><td class="textLeft" rowspan="${answerRowCnt+1}">${answer.title}</td></c:if>
																		<td class="textCenter">N/A</td>
																		<td class="textCenter">${answer.response1[0]}/${totalSendLog}</td>
																		<td class="textCenter">
																			<c:set var="avg1" value="0"/>
																			<c:choose>
																				<c:when test="${totalSendLog > 0 }">
																					<fmt:formatNumber var="avg1" value="${(answer.response1[0]/totalSendLog)*100 }" pattern="#0.00"/>
																					${avg1}										
																				</c:when>
																				<c:otherwise>0</c:otherwise>
																			</c:choose>
																		</td>
																		<td>
																			<div class="survey_graph">
																				<div id="progressbar_f_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}"></div>	
																				<input type="hidden" id="v_f_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}" name="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}" value="${avg1}" title=""/>					
																			</div>
																		</td>
																	</tr>
															    </c:if>
																<tr>
																	<c:if test="${itemStatus.first && question.questionType ne 'D6'}"><td class="textLeft" rowspan="${answerRowCnt}">${answer.title}</td></c:if>
																	<td class="textCenter">${itemStatus.index+1}</td>
																	<td class="textCenter">${item}/${totalSendLog}</td>
																	<td class="textCenter">
																		<c:set var="avg" value="0"/>
																		<c:choose>
																			<c:when test="${totalSendLog > 0 }">
																				<fmt:formatNumber var="avg" value="${(item/totalSendLog)*100 }" pattern="#0.00"/>
																				${avg}										
																			</c:when>
																			<c:otherwise>0</c:otherwise>
																		</c:choose>
																	</td>
																	<td>
																		<div class="survey_graph">
																			<div id="progressbar_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}"></div>		
																			<input type="hidden" id="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}" name="v_progressbar${gloopStatus.index}_${qloopStatus.index}_${answerloopStatus.index}_${itemStatus.index}" value="${avg}" title=""/>			
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</c:forEach>	
													</tbody>
												</table>
											</div>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>		
								</div>	
								</c:forEach>
							</c:forEach>
						</div>
								
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<c:if test="${isAdmin eq true || user.userId eq survey.registerId}">
						<li><a id="downloadButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='download'/></span></a></li>
						</c:if>
						<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/surveyEndList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
 