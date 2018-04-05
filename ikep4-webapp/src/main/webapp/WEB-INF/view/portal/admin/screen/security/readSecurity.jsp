<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="prefixMessage" value="ui.portal.admin.screen.security.loadSecurity.alert"/>					
<c:set var="prefixUI" value="ui.portal.admin.screen.security.loadSecurity.form"/>

<script type="text/javascript">
//<!--	
(function($) {
	
	$jq(document).ready(function() {
    	// 공개, 비공개 여부에 따라 display 변경
    	changeRadio(<c:out value="${security.aclResourcePermissionRead.open}"/>);
	})
	
	changeHierarchyGroupName = function () {
		
		<c:forEach var="item" items="${security.aclResourcePermissionRead.groupPermissionList}">
			<c:if test="${item.hierarchyPermission == 1}">
			$obj = $jq("#${item.groupId}");
			text = $obj.text();
			$obj.text("[H]"+text);
			</c:if>
		</c:forEach>
	};
})(jQuery);

function changeRadio(value) {
	var optionArray = '${optionList}'.split(",");
	
	if(value == 0) { 
		for(var i=0;i<optionArray.length;i++)
		{
			$jq("#"+optionArray[i]+"_TR").show();
		}
	} else if(value == 1){
		for(var i=0;i<optionArray.length;i++)
		{
			$jq("#"+optionArray[i]+"_TR").hide();
		}
	}
}
//-->
</script>
<input type="hidden" id="security.addrGroupType" name="security.addrGroupType" value=""/>
<input type="hidden" id="security.groupTypeCount" name="security.groupTypeCount" value="0"/>
<input type="hidden" id="securityOptionList" name="securityOptionList" value=""/>

<input type="hidden" id="security.className" name="security.className" value="${security.className}"/>
<input type="hidden" id="security.resourceName" name="security.resourceName" value="${security.resourceName}"/>
<input type="hidden" id="security.operationName" name="security.operationName" value="${security.operationName}"/>
<input type="hidden" id="security.addrUserListAll" name="security.addrUserListAll" value=""/>
<input type="hidden" id="security.addrGroupTypeListAll" name="security.addrGroupTypeListAll" value=""/>
<input type="hidden" id="security.addrRoleListAll" name="security.addrRoleListAll" value=""/>
<input type="hidden" id="security.addrJobClassListAll" name="security.addrJobClassListAll" value=""/>
<input type="hidden" id="security.addrJobDutyListAll" name="security.addrJobDutyListAll" value=""/>
<input type="hidden" id="security.addrExpUserListAll" name="security.addrExpUserListAll" value=""/>
<input type="hidden" id="security.ownerId" name="security.ownerId" value="${security.ownerId}"/>
<input type="hidden" id="security.ownerName" name="security.ownerName" value="${security.ownerName}"/>

	<table id="securityTable">	
		<caption></caption>
		<colgroup>
			<col width="10%"/>
			<col width="8%"/>
			<col width="82%"/>
		</colgroup>
		<tbody>
				<!--resource Start-->
				<tr>
					<th scope="col" colspan="2">
						<ikep4j:message pre="${prefixUI}" key="aclResource" />
					</th>
					<td class="textLeft">
						<c:choose>
						<c:when test="${security.aclResourcePermissionRead.open == 1}">
						<ikep4j:message pre="${prefixUI}" key="openYes" />
						</c:when>
						<c:otherwise>
						<ikep4j:message pre="${prefixUI}" key="openNo" />
						</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<!--//resource End-->
				
				
				<tr id="User_TR" style="display:none;">
					<th rowspan="5" scope="row" class="textCenter" id="headTH">
						<ikep4j:message pre="${prefixUI}" key="resourceConfig" />
					</th>
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="user" /></th>
					<td>
						<c:forEach var="item" items="${security.aclResourcePermissionRead.assignUserDetailList}">
							<div id="${item.userId}">${item.userName} ${item.jobTitleName} (${item.teamName}, ${item.mail})</div>
						</c:forEach>
					</td>
				</tr>	
				
				<tr id="Group_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="group" /></th>
					<td>
						<c:forEach var="item" items="${security.aclResourcePermissionRead.groupDetailList}">
							<div id="${item.groupId}">${item.groupName}(${item.groupTypeName})</div>
						</c:forEach>
					</td>
				</tr>	
				<tr id="Role_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="role" /></th>
					<td>
						<c:forEach var="item" items="${security.aclResourcePermissionRead.roleDetailList}">
							<div id="${item.roleId}">${item.roleName}</div>
						</c:forEach>
						
					</td>
				</tr>	
				<tr id="Job_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="jobClass" /></th>
					<td>
						<c:forEach var="item" items="${security.aclResourcePermissionRead.jobClassDetailList}">
							<div id="${item.jobClassCode}">${item.jobClassName}</div>
						</c:forEach>
					</td>
				</tr>	
				<tr id="Duty_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="jobDuty" /></th>
					<td>
						<c:forEach var="item" items="${security.aclResourcePermissionRead.jobDutyDetailList}">
							<div id="${item.jobDutyCode}">${item.jobDutyName}</div>
						</c:forEach>
					</td>
				</tr>
				<tr id="ExpUser_TR" style="display:none;">
					<th scope="row"><ikep4j:message pre="${prefixUI}" key="exception" /></th>
					<td>
						<c:forEach var="item" items="${security.aclResourcePermissionRead.exceptUserDetailList}">
							<div id="${item.userId}">${item.userName} ${item.jobTitleName} (${item.teamName}, ${item.mail})</div>
						</c:forEach>
					</td>
				</tr>
	</tbody>
</table>		
<script type="text/javascript">
//<!--
changeHierarchyGroupName();
//-->
</script>