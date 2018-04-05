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
<c:set var="preHeader"  value="message.collpack.collaboration.member.readWorkspaceMember.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.member.readWorkspaceMember.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preDetail"  value="message.collpack.collaboration.member.readWorkspaceMember.detail" />
<c:set var="preButton"  value="message.collpack.collaboration.member.readWorkspaceMember.button" />
<c:set var="preScript"  value="message.collpack.collaboration.member.readWorkspaceMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {
	deleteMember = function() {
		
		if('${member.memberLevel}'=='1')
		{
			alert('<ikep4j:message pre='${preScript}' key='notDeleteSysop' />');
			return false;			
		}
		if(!confirm('<ikep4j:message pre='${preScript}' key='deleteMember' />'))
		{
			return false; 	
		}
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/member/deleteWorkspaceMember.do"/>"
		});
		$jq("#mainForm").submit(); 

		return false; 	
	}
	
	// 회원 등급 수정
	updateMemberLevel	=	function(memberLevel,updateType) {
		
		$jq("input[name=updateType]").val(updateType);
		
		if(!confirm('<ikep4j:message pre='${preScript}' key='changeMemberLevel' />'))
		{
			return false; 	
		}
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/member/updateWorkspaceMemberLevel.do"/>"
		});
		$jq("input[name=memberLevel]").val(memberLevel);
		$jq("#mainForm").submit(); 

		return false; 		
		
	}
	// 회원 등급 수정
	updateSysop	=	function(memberLevel,updateType) {
	
		$jq("input[name=updateType]").val(updateType);
		
		$jq("input[name=memberLevel]").val(memberLevel);
		
		if('${member.memberId}'!='${user.userId}' && '${member.memberLevel}'=='1')
		{
			if(!confirm('<ikep4j:message pre='${preScript}' key='changeSysop' />'))
				return false;
		}
		
		if('${member.memberId}'=='${user.userId}' && '${member.memberLevel}'=='1')
		{
			alert('<ikep4j:message pre='${preScript}' key='sameUser' />')
			return false; 	
		}
		
		$jq.ajax({
			url : '<c:url value='/collpack/collaboration/member/updateSysop.do' />?'+$jq("#mainForm").serialize(),
			type : "post",
			success : function(result) {
				alert('<ikep4j:message pre='${preScript}' key='successSysop' />');
				
				if(result.memberLevel=='1')
					document.location.href="/collpack/collaboration/member/listWorkspaceMemberView.do?workspaceId="+$jq("input[name=workspaceId]").val()+"&listType=${searchCondition.listType}";
					//$jq("#searchButton").click();
				else
					parent.mainReload();
					//document.location.href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId="+$jq("input[name=workspaceId]").val();
			}
		});	
					
		/**$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/member/updateWorkspaceMemberSysop.do"/>"
		});
		$jq("input[name=memberLevel]").val(memberLevel);
		$jq("#mainForm").submit(); **/

		return false; 		
		
	}	
	// 회원 등급 수정
	updateMemberJoin	=	function(memberLevel) {
		$jq("input[name=updateType]").val(updateType);
		if(!confirm('<ikep4j:message pre='${preScript}' key='joinMember' />'))
		{
			return false; 	
		}	

		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/collpack/collaboration/member/updateWorkspaceMemberLevel.do"/>"
		});
		$jq("input[name=memberLevel]").val(memberLevel);
		$jq("#mainForm").submit(); 

		return false; 		
		
	}	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#saveMemberButton").click(function() {
			$jq("#mainForm").submit(); 	
			return false; 
		});
		$jq("#listButton").click(function() {
			document.location.href="<c:url value="/collpack/collaboration/member/listWorkspaceMemberView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=${searchCondition.listType}";
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
<form id="mainForm" name="mainForm" method="post">  
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

<spring:bind path="searchCondition.memberLevel">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.updateType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<input name="memberIds" type="hidden" value="${member.memberId }"/>

<div class="blockDetail">
	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>
		<col style=""/>		
		<tbody>

		<spring:bind path="member.workspaceName">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td> 
		</tr>				
		</spring:bind>
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='memberName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${member.memberId}', 'bottom')" title="${member.memberName}">
					${member.memberName}
					</a>
				</c:when>
				<c:otherwise>
					<a href="#a" onclick="iKEP.showUserContextMenu(this, '${member.memberId}', 'bottom')" title="${member.memberEnglishName}">
					${member.memberEnglishName}
					</a>
				</c:otherwise>
				</c:choose>	
			</td>


			<spring:bind path="member.memberLevel">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td>
				<c:choose>
		    		<c:when test="${member.memberLevel=='1'}"> 
						<ikep4j:message pre='${preCommon}' key='member.1' />
					</c:when>
		    		<c:when test="${member.memberLevel=='2'}"> 
						<ikep4j:message pre='${preCommon}' key='member.2' />
					</c:when>
					<c:when test="${member.memberLevel=='3'}"> 
						<ikep4j:message pre='${preCommon}' key='member.3' />
					</c:when>
		    		<c:when test="${member.memberLevel=='4'}"> 
						<ikep4j:message pre='${preCommon}' key='member.4' />
					</c:when>
					<c:when test="${member.memberLevel=='5'}"> 
						<ikep4j:message pre='${preCommon}' key='member.5' />
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>	
			</td>
			</spring:bind>		
		</tr>
		
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='teamName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${member.teamName}
				</c:when>
				<c:otherwise>
					${member.memberEnglishName}
				</c:otherwise>
				</c:choose>				
			</td>

			<th scope="row"><ikep4j:message pre='${preDetail}' key='jobTitleName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${member.jobTitleName}
				</c:when>
				<c:otherwise>
					${member.jobTitleEnglishName}
				</c:otherwise>
				</c:choose>				
			</td>
		</tr>
		

		<spring:bind path="member.memberIntroduction">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">${status.value}</td> 
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
<c:if test="${member.memberLevel!=5 }">
	<li><a class="button" href="#a" onclick="updateSysop('1','manage')" title="<ikep4j:message pre='${preButton}' key='sysop'/>"><span><ikep4j:message pre='${preButton}' key='sysop'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateMemberLevel('2','level')" title="<ikep4j:message pre='${preButton}' key='manage'/>"><span><ikep4j:message pre='${preButton}' key='manage'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateMemberLevel('3','level')" title="<ikep4j:message pre='${preButton}' key='member'/>"><span><ikep4j:message pre='${preButton}' key='member'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateMemberLevel('4','level')" title="<ikep4j:message pre='${preButton}' key='associateMember'/>"><span><ikep4j:message pre='${preButton}' key='associateMember'/></span></a></li>
	<li><a class="button" href="#a" onclick="deleteMember()" title="<ikep4j:message pre='${preButton}' key='memberOut'/>"><span><ikep4j:message pre='${preButton}' key='memberOut'/></span></a></li>
</c:if>
<c:if test="${member.memberLevel==5 }">
	<li><a class="button" href="#a" onclick="updateMemberJoin('3','join')" title="<ikep4j:message pre='${preButton}' key='memberJoin'/>"><span><ikep4j:message pre='${preButton}' key='memberJoin'/></span></a></li>
	<li><a class="button" href="#a" onclick="updateMemberJoin('4','join')" title="<ikep4j:message pre='${preButton}' key='associateMemberJoin'/>"><span><ikep4j:message pre='${preButton}' key='associateMemberJoin'/></span></a></li>
	<li><a class="button" href="#a" onclick="deleteMember()" title="<ikep4j:message pre='${preButton}' key='memberReject'/>"><span><ikep4j:message pre='${preButton}' key='memberReject'/></span></a></li>
</c:if>
<li><a id="listButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		


