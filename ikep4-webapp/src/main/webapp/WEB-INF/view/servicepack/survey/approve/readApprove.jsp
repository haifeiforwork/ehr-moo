<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.approve" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
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
<%pageContext.setAttribute("crlf", "\r\n"); %>
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   
$jq(document).ready(function() { 
	function surveyExtraView(value){
		if( value == 0 )
			$jq('#surveyExtraView').hide();
		else
			$jq('#surveyExtraView').show();
	}
	surveyExtraView(${survey.surveyTarget});

 	$jq("#approverButton").click(function() {
 		if( confirm("<ikep4j:message pre='${preMessage}' key='approve.save'/>") )
 			$jq("#surveyForm").submit();
 		
 		return false; 
 	});
	 
 	$jq("#rejectButton").click(function() {
	 var dialog = new iKEP.Dialog({
			title: "<ikep4j:message key='ui.servicepack.survey.header.reject.pageTitle'/>",
			url: "<c:url value='/servicepack/survey/approve/reject.do?surveyId='/>${survey.surveyId }",
			modal: true,
			width: 400,
			height: 250,
			resizable:false,
			scroll:"no",
			params : {},
			callback : function(result) {
				//alert("<ikep4j:message key='message.servicepack.survey.approve.rejectend'/>");
				$jq("#redirectForm").submit();
			}
		});
 	});
	 
});  
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="read.pageTitle" /></h1> 
<!-- pageTitle Start -->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="read.pageTitle" /></h2> 
</div> 
<!-- pageTitle End --> 
<!-- blockDetail Start -->
<div class="blockDetail">
	<form id="redirectForm" method="post" action="<c:url value='/servicepack/survey/approve/approveList.do'/>">
	</form>
	
	<form id="surveyForm" method="post" action="<c:url value='/servicepack/survey/approve/approveSubmit.do'/>">
	    <input type="hidden" name="surveyTitle" value="${survey.title}"/>
		<input type="hidden" name="surveyId" value="${survey.surveyId }" title=""/>
		<input type="hidden" name="surveyStatus" value="5" title=""/>
		<input type="hidden" name="approverId" value="${survey.approverId }" title=""/>
	</form>
	<table  summary="<ikep4j:message pre="${preList}" key="summary" />">
		<tbody> 
			<tr> 
				<spring:bind path="survey.title">
				<th scope="row" width="15%"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td colspan="3">
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
				<th scope="row"   width="15%"><ikep4j:message pre='${preList}' key='openDate' /></th>
				<td  width="35%">
					<div id="datepicker"></div> 
					<spring:bind path="survey.startDate">
					${status.value}
					</spring:bind> ~	
					<spring:bind path="survey.endDate">
					${status.value}
					</spring:bind>	
				</td>  
				<spring:bind path="survey.anonymous">
				<th scope="row"  width="15%"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${anonymousList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>
				</spring:bind>
			</tr>		
			<tr>  
				<spring:bind path="survey.resultOpen">
				<th scope="row"  width="15%"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td  width="35%">
					<c:forEach var="code" items="${resultOpenList}"> 
						<c:if test="${code.key eq status.value}"><ikep4j:message key='${code.value}'/></c:if>
					</c:forEach> 
				</td>
				</spring:bind>
				<spring:bind path="survey.rejectButton">
				<th scope="row"  width="15%"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td>
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
				 <%--					
					<div class="blockShuttle">
					  <div class="shuttle_l" style="width:49%">
						<div class="sbox">
							<div class="shuttleTitle">
								<ikep4j:message pre='${preList}' key='surveyTargetList' />
							</div>
							<div class="shuttleCon" style="height:70px;">
							<c:if test="${not empty survey.targetList}">
								<ul>
									<c:set var="targetListCnt" value="0"/> 
									<c:forEach var="target" items="${survey.targetList}" varStatus="loopStatus">
									 	 <c:set var="localeTotargetName" value=""/>
										<c:choose>
									      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
									       <c:set var="localeTotargetName" value="${target.targetName}"/>
									      </c:when>
									      <c:otherwise>
									        <c:set var="localeTotargetName" value="${target.engTargetName}"/>
									      </c:otherwise>
									     </c:choose>
									 	<c:set var="targetListCnt" value="${targetListCnt+1}"/>
									 	<li>${localeTotargetName}</li>
									</c:forEach>
								</ul>
								</c:if>
							</div>
						</div>
					</div>
					<div class="shuttle_r" style="width:49%">
						<div class="sbox">
							<div class="shuttleTitle">
								<ikep4j:message pre='${preList}' key='targetGroupList' />
							</div>
							<div class="shuttleCon" style="height:70px;">
							<c:if test="${not empty survey.targetGroupList}">
								<ul>
									<c:forEach var="target" items="${survey.targetGroupList}" varStatus="loopStatus">
									    <c:set var="localeTotargetName" value=""/>
											<c:choose>
										      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
										       <c:set var="localeTotargetName" value="${target.targetName}"/>
										      </c:when>
										      <c:otherwise>
										        <c:set var="localeTotargetName" value="${target.engTargetName}"/>
										      </c:otherwise>
										     </c:choose>
										 	<c:set var="targetGroupListCnt" value="${targetGroupListCnt+1}"/>
										 		<li>${localeTotargetName}</li>
									</c:forEach>
								</ul>
								</c:if>
							</div>
						</div>
					</div>	
				 </div>	
				 
				 --%>
				 	
				 </c:if>
				</td>
				</spring:bind>
			</tr>
		</tbody> 
	</table>
	
</div>
<!-- blockDetail End --> 
<!-- tableBottom Start -->
<div class="tableBottom">										
	<!-- blockButton Start -->
	<div class="blockButton"> 
		<ul>
			<c:if test="${ (user.userId  eq survey.approverId || isAdmin) && survey.surveyStatus eq 1 }">
			<li><a id="approverButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='approve'/></span></a></li>
			<li><a id="rejectButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='reject'/></span></a></li>
			</c:if>
			<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/approve/approveList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!-- blockButton End --> 
</div>
<%@ include file="/WEB-INF/view/servicepack/survey/includeSurvey.jsp"%>
<!-- tableBottom End --> 
<!-- 높이 조절 -->
<div class="blockBlank_10px"></div>
