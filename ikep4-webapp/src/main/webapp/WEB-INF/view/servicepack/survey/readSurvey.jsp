<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.read" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 
<c:set var="preMail"  value="ui.servicepack.survey.mail.list" /> 
<c:set var="preSearch"  value="ui.servicepack.survey.common.searchCondition" /> 
<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='title' var="title"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>


 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   
(function($) {

	joinRefresh= function(){
		location.reload();
	};
	
	$jq(document).ready(function() { 
		function surveyExtraView(value){
			if( value == 0 )
				$jq('#surveyExtraView').hide();
			else
				$jq('#surveyExtraView').show();
		}
		surveyExtraView(${survey.surveyTarget});
		
		$jq("#deleteButton,#deleteButton1").click(function() {
	 		if( confirm("<ikep4j:message pre='${preMessage}' key='delete'/>") )
	 		{
	 			$jq("#deleteForm").submit();
	 		}
	 		return false; 
	 	});
		
		$jq("#endButton,#endButton1").click(function() {
	 		if( confirm("<ikep4j:message pre='${preMessage}' key='end'/>") )
	 		{
	 			$jq("#endForm").submit();
	 		}
	 		return false; 
	 	});
		
		
		$jq("#publishButton,#publishButton1").click(function() {
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
					$jq("#publishForm input[name='mailSendYn']").val( result.mailSendYn );
					$jq("#publishForm input[name='sendOption']").val( result.sendOption );
					$jq("#publishForm input[name='sendComment']").val( result.sendComment );
					$jq("#publishForm").submit();
				}
			});
	 		return false; 
	 	});
		
		
		
		$jq("#mailButton,#mailButton1").click(function() {
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
		
		$jq("#mailNoIntButton,#mailNoIntButton1").click(function() {
	 		
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
					$jq("#mailNotForm input[name='mailSendYn']").val( result.mailSendYn );
					$jq("#mailNotForm input[name='sendOption']").val( result.sendOption );
					$jq("#mailNotForm input[name='sendComment']").val( result.sendComment );
					$jq("#mailNotForm").submit();
				}
			});
	 		
	 		return false; 
	 	});
		
		$jq("#copyButton,#copyButton1").click(function() {
	 		if( confirm("<ikep4j:message pre='${preMessage}' key='copy'/>") )
	 		{
	 			$jq("#copyForm").submit();
	 		}
	 		return false; 
	 	});
		
		$jq("#approverButton,#approverButton1").click(function() {
	 		if( confirm("<ikep4j:message pre='${preMessage}' key='wait'/>") )
	 		{
	 			$jq("#approverForm").submit();
	 		}
	 		return false; 
	 	});
		
	
	
		$jq("#questionJoinButton,#questionJoinButton1").click(function(){
			var dialog  = iKEP.showDialog({     
				title 		: '연결문항 설정',
				url: "<c:url value='/servicepack/survey/question/joinQuestionMng.do?surveyId='/>${survey.surveyId }",
				modal 		: true,
				width 		: 800,
				height 		: 500
			});
		});	
	
		/**
		 * 양식 미리보기 화면 오픈.
		 */
		$("#previewButton, #previewButton1").click(function(){
				iKEP.showDialog({     
				title 		: 'Preview',
				url 		: "<c:url value='/servicepack/survey/previewSurvey.do?surveyId='/>${survey.surveyId }",
				modal 		: true,
				width 		: 800,
				height 		: 500
			});
		});	
		
		
	});  
})(jQuery); 

function updateEndDate(endDate,surveyId){
	var url = "<c:url value='/servicepack/survey/updateEndDateView.do'/>?endDate="+endDate+"&surveyId="+surveyId;
	window.open( url ,'surveyEndDate','resizable=yes,scrollbars=yes,width=600,height=250');
}
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!-- pageTitle Start -->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!-- pageTitle End --> 
<!-- blockDetail Start -->
<!-- blockButton Start -->
	<div class="blockButton"> 
		<ul>
			<c:if test="${surveyAuth.publish}"><li><a id="publishButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='publish'/></span></a></li></c:if>
			<c:if test="${surveyAuth.end}"><li><a id="endButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='finish'/></span></a></li></c:if>
			<c:if test="${surveyAuth.mail}"><li><a id="mailButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='sendMail'/></span></a></li></c:if>
			<!--<c:if test="${surveyAuth.mail}"><li><a id="mailNoIntButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='responseNotInSendMail'/></span></a></li></c:if>-->
			<c:if test="${surveyAuth.approve}"><li><a id="approverButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='requestApprove'/></span></a></li></c:if>
			<c:if test="${surveyAuth.surveyEdit}"><li><a id="editButton" class="button" href="<c:url value='/servicepack/survey/updateSurvey.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='surveyEdit'/></span></a></li></c:if>
			<c:if test="${surveyAuth.questionEdit}">
				<li><a id="questionButton" class="button" href="<c:url value='/servicepack/survey/question/createQuestion.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='questionEdit'/></span></a></li>
				<li><a id="questionJoinButton" class="button" ><span>연결문항설정</span></a></li>
				
			</c:if>
			<li><a id="previewButton" class="button" ><span>설문보기(연결문항 셋팅결과 포함)</span></a></li>
			<c:if test="${surveyAuth.delete}"><li><a id="deleteButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li></c:if>
			<c:if test="${surveyAuth.copy}"><li><a id="copyButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='copy'/></span></a></li></c:if>
			<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/surveyList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	

	<form id="mailNotForm" method="post" action="<c:url value='/servicepack/survey/sendNotMail.do'/>">
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="mailSendYn"  value="1" title=""/>
		<input type="hidden" name="sendOption"  value="0" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
	<form id="mailForm" method="post" action="<c:url value='/servicepack/survey/sendMail.do'/>">
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="mailSendYn"  value="1" title=""/>
		<input type="hidden" name="sendOption"  value="0" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
    <form id="endForm" method="post" action="<c:url value='/servicepack/survey/approve/approveSubmit.do'/>">
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="surveyStatus" value="4" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
	<form id="publishForm" method="post" action="<c:url value='/servicepack/survey/approve/approveSubmit.do'/>">
	    <input type="hidden" name="surveyTitle" value="${survey.title}"/>   
		<input type="hidden" name="surveyId" value="${survey.surveyId }"/>
		<input type="hidden" name="surveyStatus" value="3" title=""/>
		<input type="hidden" name="mailSendYn"  value="1" title=""/>
		<input type="hidden" name="sendOption"  value="0" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
	<form id="approverForm" method="post" action="<c:url value='/servicepack/survey/approve/approveSubmit.do'/>">
	    <input type="hidden" name="surveyTitle" value="${survey.title}"/>
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="surveyStatus" value="1" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
	<form id="deleteForm" method="post" action="<c:url value='/servicepack/survey/deleteSurvey.do'/>">
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
	<form id="copyForm" method="post" action="<c:url value='/servicepack/survey/copySurvey.do'/>">
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="sendComment"  value="" title=""/>
	</form>	
	
	<div class="blockDetail">
	<table  summary="<ikep4j:message pre="${preList}" key="summary" />" style="table-layout:auto;">
		<tbody> 
			<tr> 
				<spring:bind path="survey.title">
				<th scope="row" width="15%;"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td colspan="3" width="85%;">
					${status.value}
				</td>	
				</spring:bind>
			</tr>
			<tr>  
				<th scope="row"><ikep4j:message pre='${preList}' key='contents' /></th>
				<td  colspan="3">
				<c:choose>
					<c:when test="${survey.contentsType eq 0 }">
					<c:set var="contents1" value="${fn:replace(survey.contents, crlf, '<br/>')}" />
					${fn:replace(contents1, " ", "&nbsp;")}
					</c:when>
					<c:otherwise>
					<img id="profilePictureImage"
				     src="<c:url value='/support/fileupload/downloadFile.do?fileId=${fileId}' />"
				     width="50" height="50" alt="<ikep4j:message pre='${preList}' key='contentsSmallImage' />" />
					</c:otherwise>
				</c:choose>
				</td> 
			</tr>	
			<tr>  
				<th scope="row"   width="15%;"><ikep4j:message pre='${preList}' key='openDate' /></th>
				<td  width="35%;">
					<div id="datepicker"></div> 
					<spring:bind path="survey.startDate">
					${status.value}
					</spring:bind> ~	
					<spring:bind path="survey.endDate">
					${status.value}&nbsp;
					<c:if test="${surveyAuth.end}"><a id="updateEndDate" class="button_s" href="javascript:updateEndDate('${status.value}','${survey.surveyId}');"><span>설문종료일 변경</span></a></c:if>
					</spring:bind>
										
				</td>  
				<spring:bind path="survey.anonymous">
				<th scope="row"  width="15%;"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td width="35%;">
					<c:forEach var="code" items="${anonymousList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>
				</spring:bind>
			</tr>		
			<tr>  
				<spring:bind path="survey.resultOpen">
				<th scope="row"  width="15%;"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td  width="35%;">
					<c:forEach var="code" items="${resultOpenList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>
				</spring:bind>
				<spring:bind path="survey.rejectButton">
				<th scope="row"  width="15%;"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td width="35%;">
					<c:forEach var="code" items="${rejectButtonList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>
				</spring:bind>
			</tr>
			
			<tr>  
				<spring:bind path="survey.surveyTarget">
				<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td  colspan="3">
					<c:forEach var="code" items="${surveyTargetList}"> 
						<c:if test="${code.key eq status.value && status.value ne '1'}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach>
					
					<c:if test="${status.value eq '1'}">
					<div class="blockShuttle">
					  <div class="shuttle_l" style="width:49%;">
						<div class="sbox">
							<div class="shuttleTitle">
								<ikep4j:message pre='${preList}' key='surveyTargetList' />
							</div>
							<div class="shuttleCon" style="height:70px;">
								<c:if test="${not empty survey.targetUsers}">
								<ul>
									<c:forEach var="targetUser" items="${survey.targetUsers}" varStatus="loopStatus">
										<c:choose>
										      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
										      	<li>${targetUser.userName} ${targetUser.jobTitleName} ${targetUser.teamName}</li>
										      </c:when>
										      <c:otherwise>
										      	<li>${targetUser.userEnglishName} ${targetUser.jobTitleEnglishName} ${targetUser.teamEnglishName}</li>
										      </c:otherwise>
										</c:choose>
									</c:forEach>
								</ul>
								</c:if>
							</div>
						</div>
					</div>
					<div class="shuttle_r" style="width:49%;">
						<div class="sbox">
							<div class="shuttleTitle">
								<ikep4j:message pre='${preList}' key='targetGroupList' />
							</div>
							<div class="shuttleCon" style="height:70px;">
								<c:if test="${not empty survey.targetGroups}">
								<ul>
									<c:forEach var="targetGroup" items="${survey.targetGroups}" varStatus="loopStatus">
										<li>${user.localeCode == portal.defaultLocaleCode ? targetGroup.groupName : targetGroup.groupEnglishName}</li>
									</c:forEach>
								</ul>
								</c:if>
							</div>
						</div>
					</div>	
				 </div>		
				 </c:if>
				</td>
				</spring:bind>
			</tr>
		</tbody> 
	</table>
	</div>
	<div class="clear"></div>
	<div class="blockListTable" style="margin-top:20px;margin-buttom:20px;">  
	<table  summary="<ikep4j:message pre="${preList}" key="summary" />">   
			<thead>
				<tr>
					<th scope="col" width="10%"  class="textCenter"><ikep4j:message pre='${preMail}' key='sendSeq' /></th>
					<th scope="col" width="15%"  class="textCenter"><ikep4j:message pre='${preMail}' key='sendOption' /></th>
					<th scope="col" width="15%"  class="textCenter"><ikep4j:message pre='${preMail}' key='requestTime' /></th>
					<th scope="col" width="15%"  class="textCenter"><ikep4j:message pre='${preMail}' key='startTime' /></th>
					<th scope="col" width="15%"  class="textCenter"><ikep4j:message pre='${preMail}' key='endTime' /></th>
					<th scope="col" width="10%"  class="textCenter"><ikep4j:message pre='${preMail}' key='successCnt' /></th>
					<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preMail}' key='totalCnt' /></th>
					<th scope="col" width="10%" class="textCenter"><ikep4j:message pre='${preMail}' key='responseCnt' /></th>
				</tr>
			</thead> 
			<tbody>
				<c:set var="logCnt" value="0"/>
				<c:forEach var="logInfo" items="${requestLogList}">
				<c:set var="logCnt" value="${logCnt+1}"/>
				<tr>
					<td> ${logInfo.sendSeq}</td>
					<td><ikep4j:message pre='ui.servicepack.survey.mail.timer' key='${logInfo.sendOption}' /></td>
					<td><ikep4j:timezone  date="${logInfo.sendRequestDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
					<td><ikep4j:timezone  date="${logInfo.sendStartDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
					<td><ikep4j:timezone  date="${logInfo.sendEndDate}"  pattern="message.servicepack.survey.timezone.dateformat.yyyyMMddhhmmss" keyString="true"/></td>
					<td>${logInfo.sendSuccessCount}</td>
					<td>${logInfo.sendTargetCount}</td>
					<td>${responseCnt}</td>
				</tr>
				</c:forEach>	
			    <c:if test="${logCnt <= 0 }">
					<tr>
						<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:if>
			</tbody>
		</table>
		</div>
		
		<div class="clear"></div>
		
		<div class="directive"> 
			<ul>
				<li><span style="color:#bf000f">아래 설문은 연결문항이 모두 오픈된 상태의 설문입니다. 연결문항셋팅을 확인하려면 미리보기를 눌러주세요.</span></li>	
				<li><span style="color:#bf000f">문항수정 완료후에 최종적으로 연결문항설정을 해주십시요.</span></li>	
				<li><span style="color:#bf000f">( 연결문항설정은 [선택형문항]에서만 가능합니다. )</span></li>	
				<li><span style="color:#bf000f">연결문항설정을 가장 마지막에 하지 않으면 문항수정시 기존 연결문항설정정보가 삭제 될 수 있습니다.</span></li>			
				<li><span style="color:#bf000f">연결문항설정을 수정저장할때는 이전 연결정보가 모두 사라지고 최종연결정보만 설정됩니다.</span></li>															
			</ul>
		</div>

		<div style="height:10px"></div>
		
		<%@ include file="/WEB-INF/view/servicepack/survey/includeReadSurvey.jsp"%>


		<div class="directive"> 
			<ul>
				<li><span style="color:#bf000f">위 설문은 연결문항이 모두 오픈된 상태의 설문입니다. 연결문항셋팅을 확인하려면 미리보기를 눌러주세요.</span></li>	
				<li><span style="color:#bf000f">문항수정 완료후에 최종적으로 연결문항설정을 해주십시요.</span></li>	
				<li><span style="color:#bf000f">( 연결문항설정은 [선택형문항]에서만 가능합니다. )</span></li>		
				<li><span style="color:#bf000f">연결문항설정을 가장 마지막에 하지 않으면 문항수정시 기존 연결문항설정정보가 삭제 될 수 있습니다.</span></li>			
				<li><span style="color:#bf000f">연결문항설정을 수정저장할때는 이전 연결정보가 모두 사라지고 최종연결정보만 설정됩니다.</span></li>															
			</ul>
		</div>


		<div style="height:10px"></div>
		
<!-- blockDetail End --> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${surveyAuth.publish}"><li><a id="publishButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='publish'/></span></a></li></c:if>
			<c:if test="${surveyAuth.end}"><li><a id="endButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='finish'/></span></a></li></c:if>
			<c:if test="${surveyAuth.mail}"><li><a id="mailButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='sendMail'/></span></a></li></c:if>
			<!-- 
			<c:if test="${surveyAuth.mail}"><li><a id="mailNoIntButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='responseNotInSendMail'/></span></a></li></c:if>
			 -->
			<c:if test="${surveyAuth.approve}"><li><a id="approverButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='requestApprove'/></span></a></li></c:if>
			<c:if test="${surveyAuth.surveyEdit}"><li><a id="editButton1" class="button" href="<c:url value='/servicepack/survey/updateSurvey.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='surveyEdit'/></span></a></li></c:if>
			<c:if test="${surveyAuth.questionEdit}">
				<li><a id="questionButton1" class="button" href="<c:url value='/servicepack/survey/question/createQuestion.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='questionEdit'/></span></a></li>
				<li><a id="questionJoinButton1" class="button" ><span>연결문항설정</span></a></li>
			</c:if>
			<li><a id="previewButton1" class="button" ><span>설문보기(연결문항 셋팅결과 포함)</span></a></li>
			<c:if test="${surveyAuth.delete}"><li><a id="deleteButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li></c:if>
			<c:if test="${surveyAuth.copy}"><li><a id="copyButton1" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='copy'/></span></a></li></c:if>
			<li><a id="listButton1" class="button" href="<c:url value='/servicepack/survey/surveyList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
<!-- tableBottom End --> 
<!-- 높이 조절 -->
	<div class="blockBlank_10px"></div>
