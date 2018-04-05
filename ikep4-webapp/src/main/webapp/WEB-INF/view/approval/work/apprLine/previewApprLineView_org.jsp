<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.admin.apprDefLine.listView"/>					
<c:set var="preHeader"	value="ui.approval.admin.apprDefLine.listView.header"/>
<c:set var="preList"	value="ui.approval.admin.apprDefLine.listView.list"/>
<c:set var="preView"	value="ui.approval.admin.apprDefLine.listView.view"/>
<c:set var="preSearch"	value="ui.approval.admin.apprDefLine.listView.search"/>
<c:set var="preButton"	value="ui.approval.admin.apprDefLine.listView.button"/>					
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<h1 class="none">Preview</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>Preview</h2>
</div>
<!--//pageTitle End-->

<!--blockListTable Start-->
<div class="blockListTable">	
	
	<table summary="<ikep4j:message pre='${preList}' key='summary1' />">
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 25%;"/>
		<col style="width: 40%;"/>		
		<col />
		<thead>
		
			<tr>
				
				<th scope="col">순번</th>
				<th scope="col">결재자</th>
				<th scope="col">부서</th>
				<th scope="col">결재유형</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			    <c:when test="${empty apprDefLine.defLineId}">
					<tr>
						<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="defLineInfo" items="${apprDefLine.apprDefLineInfo}" varStatus="status">
						<tr>
							<td>${defLineInfo.apprOrder}</td>
						
						    <td>
							<c:choose>
			   				<c:when test="${defLineInfo.approverType==0}">		
			   				${defLineInfo.userName} ${defLineInfo.approverJobTitleName}
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==1}">					    
			   				${defLineInfo.groupName}
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==2}">	
			    			${defLineInfo.jobDutyName}
			   				</c:when>
			    			</c:choose>				    			
						    </td>						
						    <td>
							<c:choose>
			   				<c:when test="${defLineInfo.approverType==0}">		
			   				${defLineInfo.teamName} 
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==1}">					    
			   				${defLineInfo.groupName}
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==2}">	

			   				</c:when>
			    			</c:choose>				    			
						    </td>
						    <td>
							<c:forEach var="code" items="${commonCode.apprTypeList}"> 
								<c:if test="${code.key eq defLineInfo.apprType}"><ikep4j:message key='${code.value}'/></c:if>
							</c:forEach>							
							</td>
						</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>

</div>
<!--//blockListTable End-->	
