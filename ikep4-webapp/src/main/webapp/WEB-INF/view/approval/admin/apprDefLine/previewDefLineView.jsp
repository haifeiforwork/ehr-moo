<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.admin.apprDefLine.listView"/>

<c:set var="preHeader"	value="ui.approval.admin.apprDefLine.listView.header"/>
<c:set var="preList"	value="ui.approval.admin.apprDefLine.listView.list"/>
<c:set var="preSearch"	value="ui.approval.admin.apprDefLine.listView.search"/>
<c:set var="preButton"	value="ui.approval.admin.apprDefLine.listView.button"/>					
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<!--blockListTable Start-->
<div class="blockDetail">	
	
	<table summary="<ikep4j:message pre='${preList}' key='summary1' />">
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 25%;"/>
		<col style="width: 40%;"/>		
		<col />
		<thead>		
			<tr>				
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='seq' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='approverId' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='teamName' /></th>
				<th class="textCenter" scope="col"><ikep4j:message pre='${preList}' key='apprType' /></th>
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
							<td class="textCenter">${defLineInfo.apprOrder}</td>						
						    <td class="textCenter">
							<c:choose>
			   				<c:when test="${defLineInfo.approverType==0}">	
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.userName} ${defLineInfo.approverJobTitleName}
									</c:when>
									<c:otherwise> 
										${defLineInfo.userEnglishName} ${defLineInfo.approverJobTitleEnglishName}
									</c:otherwise>
								</c:choose>				   					
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==1}">
			    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.groupName}
									</c:when>
									<c:otherwise> 
										${defLineInfo.groupEnglishName}
									</c:otherwise>
								</c:choose>
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==2}">	
			    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.jobDutyName}
									</c:when>
									<c:otherwise> 
										${defLineInfo.jobDutyEnglishName}
									</c:otherwise>
								</c:choose>			    			
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==3}">	
			    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.userName} ${defLineInfo.approverJobTitleName} (${defLineInfo.groupName} <ikep4j:message pre='${preList}' key='leader' />)
									</c:when>
									<c:otherwise> 
										${defLineInfo.userEnglishName} ${defLineInfo.approverJobTitleEnglishName} (${defLineInfo.groupEnglishName} <ikep4j:message pre='${preList}' key='leader' />)
									</c:otherwise>
								</c:choose>			    			
			   				</c:when>			   				
			    			</c:choose>				    			
						    </td>						
						    <td class="textCenter">
							<c:choose>
			   				<c:when test="${defLineInfo.approverType==0}">	
			    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.teamName}
									</c:when>
									<c:otherwise> 
										${defLineInfo.teamEnglishName}
									</c:otherwise>
								</c:choose>			   					
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==1}">					    
			    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.groupName}
									</c:when>
									<c:otherwise> 
										${defLineInfo.groupEnglishName}
									</c:otherwise>
								</c:choose>
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==2}">
			   				</c:when>
			    			<c:when test="${defLineInfo.approverType==3}">					    
			    				<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
										${defLineInfo.groupName}
									</c:when>
									<c:otherwise> 
										${defLineInfo.groupEnglishName}
									</c:otherwise>
								</c:choose>
			   				</c:when>			   				
			    			</c:choose>				    			
						    </td>
						    <td class="textCenter">
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
