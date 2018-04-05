<%--
=====================================================
	* 기능설명	:	workspace 내용 조회
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"	value="message.collpack.collaboration.workspace.viewWorkspace.header" />
<c:set var="preSearch"	value="message.collpack.collaboration.workspace.viewWorkspace.search" />
<c:set var="preCode"	value="message.collpack.common.code" />
<c:set var="preDetail"	value="message.collpack.collaboration.workspace.viewWorkspace.detail" />
<c:set var="preButton"	value="message.collpack.collaboration.workspace.viewWorkspace.button" />
<c:set var="preScript"  value="message.collpack.collaboration.workspace.listWorkspaceWaiting.script" />
<c:set var="preScript2"  value="message.collpack.collaboration.workspace.listCloseWorkspace.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {
	
	updateWorkspaceView = function() {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/workspace/updateWorkspaceView.do"/>",
			method:"GET"
		});
		//$jq("input[name=workspaceStatus]").val(status);
		$jq("#mainForm").submit(); 
	}
	
	updateStatus = function(status) {
		
		if( status=='O')
		{
			if(!confirm('<ikep4j:message pre='${preScript}' key='openMessage' />'))
			{
				return false; 	
			}
		}
		else if(status=='WR')
		{
			if(!confirm('<ikep4j:message pre='${preScript}' key='openRejectMessage' />'))
			{
				return false; 	
			}
		}
		else if(status=='C')
		{
			if(!confirm('<ikep4j:message pre='${preScript}' key='closeMessage' />'))
			{
				return false; 		
			}
		}
		
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/workspace/updateWorkspaceStatus.do"/>",
			method:"POST"
		});
		$jq("input[name=workspaceStatus]").val(status);
		$jq("input[name=listType]").val('WaitingOpen');
		$jq("#mainForm").submit(); 
	}
	
	updateCloseRejectStatus = function(status) {
		
		if(status=='O')
		{
			if(!confirm('<ikep4j:message pre='${preScript}' key='closeRejectMessage' />'))
			{
				return false; 		
			}
		}
		
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/workspace/updateWorkspaceCloseRejectStatus.do"/>",
			method:"POST"
		});
		$jq("input[name=workspaceStatus]").val(status);
		$jq("#mainForm").submit(); 
	}	
	
	deleteWorkspace = function() {
		
		if(!confirm('<ikep4j:message pre='${preScript2}' key='delete' />') ){
			return false; 		
		}
		
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/workspace/deleteWorkspace.do"/>",
			method:"POST"
		});
		$jq("#mainForm").submit(); 		
	}		
	
	listWorkspace = function() {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/workspace/${searchCondition.listUrl}"/>",
			method:"GET"
		});
		$jq("#mainForm").submit(); 
	}
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

	   iKEP.iFrameContentResize();  
	});
})(jQuery);  
//-->
</script>


<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/workspace/readWorkspaceView.do' />">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>

<input name="workspaceIds" id="workspaceIds" type="hidden" value="${workspace.workspaceId}" />


<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>
		<col style=""/>	
		<tbody>
	
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
			<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${workspace.registerName}
			</c:when>
			<c:otherwise>
				${workspace.registerEnglishName}
			</c:otherwise>
			</c:choose>
			</td>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registDate' /></th>
			<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${workspace.registDate}"/></td>
		</tr>
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='typeId' /></th>
			<td>
			<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${workspace.typeName}
			</c:when>
			<c:otherwise>
				${workspace.typeEnglishName}
			</c:otherwise>
			</c:choose>			
			
			</td>
			<th scope="row"><span id="span_category_text"></span><ikep4j:message pre='${preDetail}' key='categoryName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${workspace.categoryName}
				</c:when>
				<c:otherwise>
					${workspace.categoryEnglishName}
				</c:otherwise>
				</c:choose>	
			</td>			
		</tr>
					
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='workspaceName' /></th>
			<td colspan="3">${workspace.workspaceName}</td>
		</tr>
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='introImage' /></th>
			<td colspan="3">
			<input name="fileId" type="hidden" value="" title="fileId" />
			<span id="viewDiv">
			<c:forEach var="fileDataList" items="${workspace.fileDataList}" varStatus="fileLoop">
			<img id='viewImageDiv' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' width='110' height='90'/>
			</c:forEach>
			</span>	
			</td>
		</tr>	
		

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='workspaceStatus' /></th>
			<td>
				<c:choose>
		    		<c:when test="${workspace.workspaceStatus=='O'}"> 
						<ikep4j:message pre='${preDetail}' key='O' />
					</c:when>
		    		<c:when test="${workspace.workspaceStatus=='WO'}"> 
						<ikep4j:message pre='${preDetail}' key='WO' />
					</c:when>
					<c:when test="${workspace.workspaceStatus=='WC'}"> 
						<ikep4j:message pre='${preDetail}' key='WC' />
					</c:when>
		    		<c:when test="${workspace.workspaceStatus=='C'}"> 
						<ikep4j:message pre='${preDetail}' key='C' />
					</c:when>
					<c:when test="${workspace.workspaceStatus=='WR'}"> 
						<ikep4j:message pre='${preDetail}' key='WR' />
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>			
			</td>
			<th scope="row"><span id="span_category_text"></span><ikep4j:message pre='${preDetail}' key='sysopName' /></th>
			<td>
				<a href="#a"  onclick="iKEP.showUserContextMenu(this, '${workspace.sysopId}', 'bottom');">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${workspace.sysopName}
				</c:when>
				<c:otherwise>
					${workspace.sysopEnglishName}
				</c:otherwise>
				</c:choose>		
				</a>		
			</td>			
		</tr>
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member' /></th>
			<td colspan="3">
			<c:forEach var="memberList" items="${workspace.memberList}">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${memberList.memberName } ${memberList.jobTitleName } ${memberList.teamName }<br/>
				</c:when>
				<c:otherwise>
					${memberList.memberEnglishName } ${memberList.jobTitleEnglishName } ${memberList.teamEnglishName }<br/>
				</c:otherwise>
				</c:choose>	
			</c:forEach>
			</select></td>
		</tr>

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='description' /></th>
			<td colspan="3">${workspace.description}</td>
		</tr>

		<tr>
			<th scope="row">&nbsp;<ikep4j:message pre='${preDetail}' key='tag' /></th>
			<td colspan="3">
			<c:forEach var="tag" items="${workspace.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>
			</td>
		</tr>
		</tbody>
	</table>

	
	
</div>
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<c:choose>
		<c:when test="${workspace.workspaceStatus=='WO'}"> 
			<c:if test="${isSystemAdmin}">
			<li><a class="button" href="#a" onclick="updateStatus('O')" title="<ikep4j:message pre='${preButton}' key='approveWorkspace'/>"><span><ikep4j:message pre='${preButton}' key='approveWorkspace'/></span></a></li>
			<li><a class="button" href="#a" onclick="updateStatus('WR')" title="<ikep4j:message pre='${preButton}' key='rejectWorkspace'/>"><span><ikep4j:message pre='${preButton}' key='rejectWorkspace'/></span></a></li>
			</c:if>
			<c:if test="${isSystemAdmin or workspace.sysopId==user.userId}">
			<li><a class="button" href="#a" onclick="updateWorkspaceView()" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
			</c:if>
		</c:when>
		<c:when test="${workspace.workspaceStatus=='WC' && isSystemAdmin}"> 
			<li><a class="button" href="#a" onclick="updateStatus('C')" title="<ikep4j:message pre='${preButton}' key='closeWorkspace'/>"><span><ikep4j:message pre='${preButton}' key='closeWorkspace'/></span></a></li>
			<li><a class="button" href="#a" onclick="updateCloseRejectStatus('O')" title="<ikep4j:message pre='${preButton}' key='closeRejectWorkspace'/>"><span><ikep4j:message pre='${preButton}' key='closeRejectWorkspace'/></span></a></li>
		</c:when>
		<c:when test="${workspace.workspaceStatus=='C' && isSystemAdmin}">
			<li><a class="button" href="#a" onclick="deleteWorkspace()" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
		</c:when>
	</c:choose>
	
	<li><a class="button" href="#a" onclick="listWorkspace()" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		


