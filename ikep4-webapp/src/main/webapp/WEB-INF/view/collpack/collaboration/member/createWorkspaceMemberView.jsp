<%--
=====================================================
	* 기능설명	:	workspace 멤버 등록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.member.createWorkspaceMember.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.member.createWorkspaceMember.search" />
<c:set var="preCode"    value="message.collpack.common.code" />
<c:set var="preList"    value="message.collpack.collaboration.member.createWorkspaceMember.list" />
<c:set var="preButton"  value="message.collpack.collaboration.member.createWorkspaceMember.button" />
<c:set var="preScript"  value="message.collpack.collaboration.member.createWorkspaceMember.script" />
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
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				memberIntroduction : {
					required : true,
					maxlength : 500
				}
			},
			messages : {
				memberIntroduction : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.member.memberIntroduction" />",
					maxlength	:	"<ikep4j:message key="Size.member.memberIntroduction" />"
					
				}
			},
			submitHandler : function(form) {
				// 부가 검증 처리

				$.ajax({
					url : "<c:url value='/collpack/collaboration/member/createWorkspaceMember.do' />",
					type : "post",
					data : $(form).serialize(),
					success : function(result) {
						alert('<ikep4j:message pre='${preScript}' key='saveSuccess' />');
						parent.mainReload();
						//mainFrame.location.href= "<c:url value='/collpack/collaboration/main/Workspace.do'/>?workspaceId="+result; // WS 메인화면으로 이동
					},
					error : function(xhr, exMessage) {
					
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});		
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);
		
		/**
		 * Validation Logic End
		 */	
		 
		$jq("#saveMemberButton").click(function() {
			$jq("#mainForm").submit(); 	
			return false; 
		});
		$jq("#initMemberButton").click(function() {
			$jq("#mainForm")[0].reset(); 	
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
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/member/createWorkspaceMember.do' />">  
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

<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preList}" key="summaryCreateMember" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style=""/>
		<tbody>

		<spring:bind path="workspace.workspaceName">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preList}' key='workspaceName' /></th>
			<td>${workspace.workspaceName }</td> 
		</tr>				
		</spring:bind>
		<spring:bind path="member.memberName">
		<tr>
			<th scope="row"><ikep4j:message pre='${preList}' key='memberName' /></th>
			<td>
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${user.userName } ${user.teamName }
				</c:when>
				<c:otherwise>
					${user.userEnglishName } ${user.teamEnglishName }
				</c:otherwise>
				</c:choose>	
			</td> 
		</tr>
		</spring:bind>
		<spring:bind path="member.memberIntroduction">
		<tr> 
			<th scope="row"><ikep4j:message pre='${preList}' key='memberIntroduction' /></th>
			<td>
				<div>						
				<textarea 
				name="${status.expression}" 
				class="w100" 
				cols="" 
				rows="5" 
				title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
				>${status.value}</textarea>
				<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
				</div>
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
	<li><a id="initMemberButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='init'/>"><span><ikep4j:message pre='${preButton}' key='init'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		

 