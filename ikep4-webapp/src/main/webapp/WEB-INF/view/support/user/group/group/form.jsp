<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>

<head>
<%@ include file="/base/common/meta.jsp" %>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--
	var checkVar = false;

	$(document).ready(function(){
		$('#checkButton').click(function(){
			var idVal = $('#group_id').val();
			if(idVal==""){
				alert("ID를 입력하세요");
				return ;
			}
			checkVar = true;
			$.ajax({
				type:'POST',
				url:"<c:url value='/group/checkId.do'/>",
				dataType:'text',
				data:({ id:$('#group_id').val() }),
				error: 
					function(textStatus) {
					    alert('error:' + textStatus);
				    },
				success:
					function(data){
						if(data=='1') {
							alert('중복된 ID 입니다.');
							$('#group_id').val("");
						} else {
							alert('중복된 ID가 아닙니다.');
						}
					}
			});
		});
		
		// 등록
		$('#createButton').click(function(){
			if(checkVar==true) {
				$('form[name=groupAddForm]').attr({
					action:"<c:url value='/group/createGroup.do'/>"
				});
				$('form[name=groupAddForm]').submit();
			} else {
				alert('ID 중복확인이 필요합니다.');
			}
		});
		
		$('#modifyButton').click(function(){
			$('form[name=groupAddForm]').attr({
				action:"<c:url value='/group/createGroup.do'/>"
			});
			$('form[name=groupAddForm]').submit();
		});
		
		// 삭제
		$('#deleteButton').click(function(){
			$('form[name=groupAddForm]').attr({
				action:"<c:url value='/group/deleteGroup.do'/>"
			});
			$('form[name=groupAddForm]').submit();
		});

	});
//-->
</script>

<title>TIMEZONE 코드 등록</title>
</head>

<body>
<form:form name="groupAddForm" commandName="group" method="post" action="createGroup.do">

	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td width="20%"><b>* 그룹 ID</b></td>
			<c:choose>
				<c:when test="${empty group.group_id}">
					<td width="40%">
						&nbsp;<form:input for="group_id" path="group_id" />
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="group_id" cssClass="error"/></td>
				</c:when>
				<c:otherwise>
					<td width="40%">
						&nbsp;<form:input for="group_id" path="group_id" readonly="false"/>
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="group_id" cssClass="error"/></td>
				</c:otherwise>
			</c:choose>				
		</tr>
		<tr>
			<td width="20%"><b>* 그룹이름</b></td>
			<td width="40%">&nbsp;<form:input for="group_name" path="group_name" /></td>
			<td width="40%">&nbsp;<form:errors path="group_name" cssClass="error"/></td>
		</tr>
		<tr>
			<td width="20%">&nbsp;<b>* 소속그룹</b></td>
			<td width="40%">&nbsp;
				<form:select name="parent_group_id" id="parent_group_id" width="20" path="parent_group_id" for="parent_group_id">
					<c:forEach var="result" items="${parentIdList}" varStatus="loopStatus">
						<option label="<c:out value="${result.group_name}"/>" value="<c:out value="${result.group_id}"/>" <c:if test="${result.group_id==group.parent_group_id}">selected</c:if>>
							<c:out value="${result.group_name}"/>/<c:out value="${result.group_id}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="parent_group_id" cssClass="error"/></td>
		</tr>
		</tr>
		<tr>
			<td width="20%">&nbsp;<b>* 그룹타입</b></td>
			<td width="40%">&nbsp;
				<form:select name="group_type_id" id="group_type_id" width="20" path="group_type_id" for="group_type_id">
					<c:forEach var="result" items="${typeIdList}" varStatus="loopStatus">
						<option label="<c:out value="${result.group_type_name}"/>" value="<c:out value="${result.group_type_id}"/>" <c:if test="${result.group_type_id==group.group_type_id}">selected</c:if>>
							<c:out value="${result.group_type_name}"/>/<c:out value="${result.group_type_id}"/>
						</option>
					</c:forEach>
				</form:select>
			</td>
			<td width="40%">&nbsp;<form:errors path="group_type_id" cssClass="error"/></td>
		</tr>
		</tr>
		<tr>
			<td width="20%"><b>* 정렬순서</b></td>
			<td width="40%">&nbsp;<form:input for="sort_order" path="sort_order" /></td>
			<td width="40%">&nbsp;<form:errors path="sort_order" cssClass="error"/></td>
		</tr>
		<tr>
			<td width="20%"><b>포탈 ID</b></td>
			<td width="40%">&nbsp;<form:input for="portal_id" path="portal_id" /></td>
			<td width="40%">&nbsp;<form:errors path="portal_id" cssClass="error"/></td>
		</tr>
	</table>

	<!--버튼 [S]-->
	<div align="right">
		<c:choose>
			<c:when test="${empty group.group_id}">
				<!-- <input type="submit" value="등록"> -->
				<input type="button" id="createButton" name="createButton" value="등록">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listGroup.do'">
			</c:when>
			<c:otherwise>
				<!-- <input type="submit" value="수정"> -->
				<input type="button" id="modifyButton" name="modifyButton" value="수정">
				<input type="button" id="deleteButton" name="deleteButton" value="삭제">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listGroup.do'">		
			</c:otherwise>
		</c:choose>
	</div>
	<!--//버튼 [E]-->
</form:form>

</body>
</html>