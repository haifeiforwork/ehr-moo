<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<%@ include file="/base/common/taglibs.jsp"%>
	<%@ include file="/base/common/meta.jsp" %>
	<title></title>
</head>	
<script type="text/javascript">

//<!--
	
	function startProcess() {
		document.getElementById("frm_Command").command.value	= "startProcess";
		document.getElementById("frm_Command").processId.value	= document.getElementById("textProcessId").value;
		document.getElementById("frm_Command").userId.value		= document.getElementById("textUserId").value;
		document.getElementById("frm_Command").title.value		= document.getElementById("textTitle").value;
		document.getElementById("frm_Command").userId.value		= document.getElementById("textUserId").value;
		document.getElementById("frm_Command").submit();
	}	
	
	function completeWorkItem() {
		document.getElementById("frm_Command").command.value	= "completeWorkItem";
		document.getElementById("frm_Command").processId.value	= document.getElementById("textProcessId").value;
		document.getElementById("frm_Command").processInsId.value	= document.getElementById("textProcessInsId").value;
		document.getElementById("frm_Command").activityId.value	= document.getElementById("textActivityId").value;
		document.getElementById("frm_Command").insLogId.value		= document.getElementById("textInsLogId").value;
		document.getElementById("frm_Command").userId.value		= document.getElementById("textUserId").value;
		document.getElementById("frm_Command").submit();
	}
	
	function deleteInstance() {
		document.getElementById("frm_Command").command.value	= "deleteInstance";
		document.getElementById("frm_Command").processId.value	= document.getElementById("textProcessId").value;
		document.getElementById("frm_Command").processInsId.value	= document.getElementById("textProcessInsId").value;
		document.getElementById("frm_Command").activityId.value	= document.getElementById("textActivityId").value;
		document.getElementById("frm_Command").insLogId.value		= document.getElementById("textInsLogId").value;
		document.getElementById("frm_Command").userId.value		= document.getElementById("textUserId").value;
		document.getElementById("frm_Command").submit();
	}	
	
	

	function popupWorkform(processInsId, activityId, insLogId) {
		var top; 
		var left; 
		var width = 800;
		var height = 600;
		var userId	= document.getElementById("textUserId").value;

		left = (screen.width - width) / 2; 
		top = (screen.height - height) / 2;		
		var contextUrl	= "<c:url value='/workflow/workform.do'/>" + "?processInsId=" + processInsId + "&activityId=" + activityId + "&insLogId=" + insLogId + "&userId=" + userId;
		window.open(contextUrl, "", "top=" + top + ", left=" + left +", width="+ width + ", height="+height+", resizable=yes,scrollbars=yes");
	}

	function searchListEnter() {
		searchList();
	}	
	
	function searchList() {
		var contextUrl	= "<c:url value='/workflow/todolistTest.do'/>" + "?userId=" + document.getElementById("textUserId").value;;
		location.href = contextUrl;
	}	
	
	function checkUp(logId, instanceId) {
		document.getElementById("textInsLogId").value		= logId;
		document.getElementById("textProcessInsId").value	= instanceId;
	}
	
	function changeProcess(procId) {
		document.getElementById("textProcessId").value		= procId;
	}
	
	function selectChangeProcess(obj) {
		alert(obj.selectedIndex);
		procId		= obj.options[obj.selectedIndex].value;
		alert(procId);
		changeProcess(procId);
	} 
	

//-->

</script>
<body>
	<h1>
		
	</h1>
<table border="1" width="100%" height="500">
  
	<tr height="25" align="left">
    
		<td>
			<table border="0" width="100%">
				<tr>
					<td align="left">
						&nbsp;&nbsp;Workflow 테스트 (${title}) : ${count}
					</td>
					<td align="right">
						사용자 : <input type="text" name="textUserId" value="${userId}" onKeyDown="if(event.keyCode=='13'){searchListEnter();}"/>
						<input type="button" value="Search" onclick="searchList()" style="width:60">
					</td>
				</tr>
			</table>
		</td>
  
	</tr>
<c:choose>
	<c:when test="${title == 'To-Do 리스트'}">
	<tr height="25" align="left">
    
		<td align="left">
			<input type="button" value="StartProcess" onclick="startProcess()" style="width:100">
			<input type="text" name="textProcessId" value="TEST_PROCESS"/>
			<select name="selectProcess" onchange="selectChangeProcess(this)">
				<option value="APPROVAL_PROCESS">전자결재</option>
				<option value="TEST_PROCESS">테스트</option>
				<option value="CAS_PROCESS">CAS</option>
			</select>
			타이틀 : <input type="text" name="textTitle" value="" style="width:300"/>
		</td>
	</tr>
	<tr height="25" align="left">
    
		<td align="left">
			<input type="button" value="deleteIns" onclick="deleteInstance()" style="width:100">
			InstanceId:<input type="text" name="textProcessInsId" value=""/>
			activityId:<input type="text" name="textActivityId" value=""/>
			logId:<input type="text" name="textInsLogId" value=""/>
		</td>
	</tr>	
	</c:when>	
</c:choose>	
	<tr>
		<td valign="top">
		
		
			<table width="100%"  style="font-size:13;" border="1">
				<caption></caption>
				<thead>
					<tr bgcolor="#dddddd">
						<th scope="col" width="10%">로그 ID</th>
						<th scope="col" width="10%">인스턴스ID</th>
						<th scope="col" width="30%">프로세스</th>
						<th scope="col" width="25%">액티비티</th>
						<th scope="col" width="20%">타이틀</th>
						<th scope="col" width="5%">&nbsp;</th>
					</tr>
				</thead>		
				<tbody>
				<c:choose>
					<c:when test="${!empty todoList}">
						<c:forEach var="todoList" items="${todoList}" varStatus="status">
						<tr class='${status.count%2 == 1 ? "bgWhite" : "bgGray"}'>
							<td><a href="#" onclick="checkUp('${todoList.insLogId}', '${todoList.instanceId}')">${todoList.insLogId}</a></td>					
							<td>${todoList.instanceId}</td>
							<td>${todoList.processName}&nbsp;(${todoList.processId})</td>
							<td>${todoList.activityName}&nbsp;(${todoList.activityId})</td>
							<td>${todoList.title}</td>
							<td><input name="" type="button" value="실행" onclick="popupWorkform('${todoList.instanceId}', '${todoList.activityId}', '${todoList.insLogId}');"/></td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="7" align="center">데이타가 존재하지 않습니다.</td>
						</tr>
					</c:otherwise>	
				</c:choose>		
				</tbody>
			</table>
		</td>
	</tr>
</table>

<form id="frm_Command" method="post" action="<c:url value='/workflow/workflow.do'/>" target="ifrm_Deploy">
	<input type="hidden" name="command" value=""/>
	<input type="hidden" name="xpdlStream" value=""/>
	<input type="hidden" name="processId" value=""/>
	<input type="hidden" name="processInsId" value=""/>
	<input type="hidden" name="activityId" value=""/>
	<input type="hidden" name="insLogId" value=""/>
	<input type="hidden" name="userId" value=""/>
	<input type="hidden" name="title" value=""/>
</form>
<iframe name="ifrm_Deploy" style="display:none"></iframe>

</body>
</html>