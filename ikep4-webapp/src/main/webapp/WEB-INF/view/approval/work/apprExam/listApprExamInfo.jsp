<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"  value="ui.approval.work.exam.form.header" />
<c:set var="preForm"  	value="ui.approval.work.exam.form" />
<c:set var="preDiv"  	value="ui.approval.work.exam" />
<c:set var="preValidation" value="ui.approval.work.exam.validation" />
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<c:if test="${!empty listApprExamInfo}">
<script type="text/javascript">
<!-- 
(function($) {
	/**
	 * onload시 수행할 코드
	 */
	 //검토 내용 등록
	 examContentSave = function(apprId,examContent,examUserId) {
		
		 examContent = $jq("#examContent").val();
		if(examContent == "") { 
			alert('<ikep4j:message pre="${preValidation}" key="checkExamContent" />');
			return false;
		}
		if(confirm('<ikep4j:message pre="${preValidation}" key="confirmExamContent" />')) {
			 $.post("<c:url value='/approval/work/apprExam/updateApprExamInfoSave.do'/>", {apprId:apprId,examContent:examContent,examUserId:examUserId})
				.success(function(result) {
					getViewApprLine(apprId);
				})
				.error(function(event, request, settings) { alert("error"); });
		}
	 };
	
	$(document).ready(function() {
		
	});
})(jQuery);  
//-->
</script>
	<b><ikep4j:message pre="${preHeader}" key="pageTitle" /></b>
	<!--tab Start-->		
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 30%;"/>
		<col style="width: 15%;"/>
		<col style="width: 30%;"/>
		<col style="width: 15%;"/>
		<thead>
			<tr>				
				<th scope="col" rowspan="2" class="textCenter" ><ikep4j:message pre='${preForm}' key='list.division' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preForm}' key='list.examReqName' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preForm}' key='list.examReqDate' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preForm}' key='list.examUserName' /></th>
				<th scope="col" class="textCenter"><ikep4j:message pre='${preForm}' key='list.examDate' /></th>
			</tr>
			<tr>				
				<th scope="col" colspan="2" class="textCenter"><ikep4j:message pre='${preForm}' key='list.examReqContent' /></th>
				<th scope="col" colspan="2" class="textCenter"><ikep4j:message pre='${preForm}' key='list.examContent' /></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty listApprExamInfo}">
					<tr>
						<td colspan="5" class="emptyRecord textCenter"><ikep4j:message pre='${preValidation}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="item" items="${listApprExamInfo}">
							<c:if test="${item.isOpen eq '0'}">
								<tr>
									<td rowspan="2" class="textCenter">
										<c:if test="${item.isOpen eq '0'}"><ikep4j:message pre='${preDiv}' key='display.open' /></c:if> 
										<c:if test="${item.isOpen eq '1'}"><ikep4j:message pre='${preDiv}' key='display.close' /></c:if>
									</td>
									<td> ${item.examReqName}&nbsp;${item.examReqTeamName}</td>
									<td class="textCenter"> <ikep4j:timezone pattern="yyyy.MM.dd" date="${item.examReqDate}"/></td>
									<td> ${item.examName}&nbsp;${item.examTeamName}</td>
									<td class="textCenter"> <ikep4j:timezone pattern="yyyy.MM.dd" date="${item.examDate}"/></td>
								</tr>
								<tr>
									<% pageContext.setAttribute("newLineChar", "\n"); %> 
									<td colspan="2" class="textLeft">
										${fn:replace(item.examReqContent, newLineChar, '<br/>')}
									</td>
									<td colspan="2" class="textLeft">
										<c:if test="${item.examContent eq null}">
											<c:if test="${item.examUserId eq user.userId}">
											<textarea name="examContent" id="examContent" class="tabletext w100" title="<ikep4j:message pre='${preForm}' key='list.examContent' />" cols="150" rows="5"></textarea>
											<br/>
											<div class="blockButton">
												<a href="#a" class="button_s mt5" id="examSaveBtn" onclick="examContentSave('${item.apprId}','${item.examContent}','${item.examUserId}')"><span>SAVE</span></a>
											</div>
											</c:if>
										</c:if>
										<c:if test="${item.examContent ne null}">
										${fn:replace(item.examContent, newLineChar, '<br/>')}
										</c:if>
									</td>
								</tr>
							</c:if>
							<c:if test="${item.isOpen eq '1'}">
								<tr>
									<td rowspan="2" class="textCenter">
										<c:if test="${item.isOpen eq '0'}"><ikep4j:message pre='${preDiv}' key='display.open' /></c:if> 
										<c:if test="${item.isOpen eq '1'}"><ikep4j:message pre='${preDiv}' key='display.close' /></c:if>
									</td>
									<td> ${item.examReqName}&nbsp;${item.examReqTeamName}</td>
									<td class="textCenter"> <ikep4j:timezone pattern="yyyy.MM.dd" date="${item.examReqDate}"/></td>
									<td> ${item.examName}&nbsp;${item.examTeamName}</td>
									<td class="textCenter"> <ikep4j:timezone pattern="yyyy.MM.dd" date="${item.examDate}"/></td>
								</tr>
								<tr>
									<% pageContext.setAttribute("newLineChar", "\n"); %> 
									<td colspan="2" class="textLeft">
										<c:choose>
											<c:when test="${item.examId eq user.userId or item.examReqId eq user.userId or item.examFirstReqId eq user.userId or item.examUserId eq user.userId}">
												${fn:replace(item.examReqContent, newLineChar, '<br/>')}
											</c:when>
											<c:otherwise>
												<ikep4j:message pre='${preDiv}' key='display.close' />
											</c:otherwise>
										</c:choose>
									</td>
									<td colspan="2" class="textLeft">
										<c:if test="${item.examContent eq null}">
											<c:if test="${item.examUserId eq user.userId}">
											<textarea name="examContent" id="examContent" class="tabletext" title="<ikep4j:message pre='${preForm}' key='list.examContent' />" cols="150" rows="5"></textarea>
											<br/>
											<a href="#a" id="examSaveBtn" onclick="examContentSave('${item.apprId}','${item.examContent}','${item.examUserId}')">SAVE</a>
											</c:if>
										</c:if>
										<c:if test="${item.examContent ne null}">
										<c:choose>
											<c:when test="${item.examId eq user.userId or item.examReqId eq user.userId or item.examFirstReqId eq user.userId or item.examUserId eq user.userId}">
												${fn:replace(item.examContent, newLineChar, '<br/>')}
											</c:when>
											<c:otherwise>
												<ikep4j:message pre='${preDiv}' key='display.close' />
											</c:otherwise>
										</c:choose>
										</c:if>
									</td>
								</tr>
							</c:if>
						
					</c:forEach>
			    </c:otherwise>
			</c:choose>
		</tbody>
	</table>	
	</div>		
	<!--//tab End-->
</c:if>		
