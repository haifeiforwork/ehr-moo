<%--
=====================================================
	* 기능설명	:	workspace 등록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.member.viewWorkspaceMember.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.member.viewWorkspaceMember.search" />
<c:set var="preCode"    value="message.collpack.common.code" />
<c:set var="preList"    value="message.collpack.collaboration.member.viewWorkspaceMember.list" />
<c:set var="preButton"  value="message.collpack.collaboration.member.viewWorkspaceMember.button" />
<c:set var="preScript"  value="message.collpack.collaboration.member.viewWorkspaceMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript">
<!-- 
(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#saveMemberButton").click(function() {
			$jq("#mainForm").submit(); 	
			return false; 
		});
		$jq("#listMemberButton").click(function() {
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/collpack/collaboration/member/listWorkspaceMemberView.do"/>",
				method:"POST"
			});
			$jq("#mainForm").submit(); 	
			return false; 
		});		
	   
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
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/member/updateWorkspaceMember.do' />">  
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
<spring:bind path="searchCondition.memberId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.memberIds">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.memberLevel">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>

	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style=""/>
		<tbody>

		<spring:bind path="member.workspaceName">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>				
		</spring:bind>
		<spring:bind path="member.memberName">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
			<td>${status.value}</td> 
		</tr>
		</spring:bind>
		<spring:bind path="member.teamName">
		<tr>
			<th scope="row"><font color='#990000'>*</font>&nbsp;<ikep4j:message pre='${preList}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td>
		</tr>
		</spring:bind>
		<spring:bind path="member.jobDutyName">
		<tr>
			<th scope="row"><font color='#990000'>*</font>&nbsp;<ikep4j:message pre='${preList}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td>
		</tr>	
		</spring:bind>	
		<spring:bind path="member.memberIntroduction">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
			<td>
			<textarea name="description" class="tabletext"
				title="<ikep4j:message pre='${preList}' key='${status.expression}' />" cols="" rows="5">${status.value}</textarea>
			</td> 
		</tr>
		</spring:bind>

		</tbody>
	</table>

	
	
</div>
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveMemberButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<li><a id="listMemberButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		
 
