<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<!-- 메시지 관련 Prefix 선언  -->
<c:set var="preCommonMessage" value="message.workflow.admin.common" />
<c:set var="preHeader" value="ui.workflow.admin.participants" />
<!-- Jquery(selectable) CSS -->
<style type="text/css">
	#ulRoleResult { list-style-type: none; margin: 0; padding: 0; width: 99%; text-align:center; }
	#ulRoleResult li {margin: 3px; padding: 0.4em; font-size: 1em; height: 18px; border:1px solid #eee; }
	#ulRoleResult .ui-selecting {background: #FECA40; }
	#ulRoleResult .ui-selected { background: #F39814; color: white; }
</style>

<script type="text/javascript">
(function($){
	//=========================================================================
	//* OnLoad 함수
	//=========================================================================	
	$(document).ready(function() {
		//=========================================================================
		//* Jquery(selectable) 적용
		//=========================================================================
		$("#ulRoleResult").selectable();
		
		//=========================================================================
		//*  페이지 클릭이벤트
		//=========================================================================
		fnPageAction = function(formObject){
			$jq.post("<c:url value='/workflow/admin/participantsSearchRole.do' />",formObject)
			.success(function(result) {
				$("#divSearchResult").html(result); 
			})
			.error(function(event, request, settings) { alert("error"); });
		}
	});
})(jQuery);
</script>
<form id="participantsListForm" method="post">  
<spring:bind path="roleSearchCondition.participantSearchTitle">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preHeader}' key='${status.expression}' />" />
</spring:bind>
<div class="blockDetail" style="height:220px;overflow-x:hidden;overflow-y:auto;width:99%;">
	<table>
		<tr>
			<th scope="col" class="textCenter">
				<ikep4j:message pre='${preHeader}' key='roleId' />
			</th>
			<th scope="col" class="textCenter">
				<ikep4j:message pre='${preHeader}' key='roleName' />
			</th>
		</tr>
		<c:choose>
			<c:when test="${roleSearchResult.emptyRecord}">
		<tr>
			<td colspan="2" class="textCenter"><ikep4j:message pre='${preCommonMessage}' key='dataNotFound'/></td>
		</tr>
	</table>		
			</c:when>
	
			<c:otherwise>
	</table>
		<ol id="ulRoleResult" class="list-selectable">
		<c:forEach var="listRole" items="${roleSearchResult.entity}" varStatus="status" >
			<li name="${listRole.roleName}" data='{"type":"role","id":"${listRole.roleId}"}'>
				<table>
					<tr>
						<td style="background-color:transparent;border:0;" class="textLeft">${listRole.roleId}</td>
						<td style="background-color:transparent;border:0;" class="textCenter">${listRole.roleName}</td>
					</tr>
				</table>
			</li>	
		</c:forEach>
		</ol>
			</c:otherwise>
	</c:choose>
</div>
<!--Page Numbur Start--> 
<spring:bind path="roleSearchCondition.pageIndex">
<ikep4j:pagination searchFormId="participantsListForm" ajaxEventFunctionName="fnPageAction" pageIndexInput="${status.expression}" searchCondition="${roleSearchCondition}" />
<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preHeader}' key='pageIndex'/>" />
</spring:bind> 
<!--//Page Numbur End-->
</form>