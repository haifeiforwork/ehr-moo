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
			var idVal = $('#group_type_id').val();
			if(idVal==""){
				alert("ID를 입력하세요");
				return ;
			}
			checkVar = true;
			$.ajax({
				type:'POST',
				url:"<c:url value='/grouptype/checkId.do'/>",
				dataType:'text',
				data:({ id:$('#group_type_id').val() }),
				error: 
					function(textStatus) {
					    alert('error:' + textStatus);
				    },
				success:
					function(data){
						if(data=='1') {
							alert('중복된 ID 입니다.');
							$('#group_type_id').val("");
						} else {
							alert('중복된 ID가 아닙니다.');
						}
					}
			});
		});
		
		// 등록
		$('#createButton').click(function(){
			if(checkVar==true) {
				$('form[name=grouptypeAddForm]').attr({
					action:"<c:url value='/grouptype/createGrouptype.do'/>"
				});
				$('form[name=grouptypeAddForm]').submit();
			} else {
				alert('ID 중복확인이 필요합니다.');
			}
		});
		
		$('#modifyButton').click(function(){
			$('form[name=grouptypeAddForm]').attr({
				action:"<c:url value='/grouptype/createGrouptype.do'/>"
			});
			$('form[name=grouptypeAddForm]').submit();
		});
		
		// 삭제
		$('#deleteButton').click(function(){
			$('form[name=grouptypeAddForm]').attr({
				action:"<c:url value='/grouptype/deleteGrouptype.do'/>"
			});
			$('form[name=grouptypeAddForm]').submit();
		});

	});
//-->
</script>

<title>TIMEZONE 코드 등록</title>
</head>

<body>
<form:form name="grouptypeAddForm" modelAttribute="groupType" commandName="groupType" method="post" action="createGrouptype.do">

	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td width="20%"><b>* 그룹타입 ID</b></td>
			<c:choose>
				<c:when test="${empty groupType.group_type_id}">
					<td width="40%">
						&nbsp;<form:input for="group_type_id" path="group_type_id" />
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="group_type_id" cssClass="error"/></td>
				</c:when>
				<c:otherwise>
					<td width="40%">
						&nbsp;<form:input for="group_type_id" path="group_type_id" readonly="false"/>
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="group_type_id" cssClass="error"/></td>
				</c:otherwise>
			</c:choose>				
		</tr>
		<tr>
			<td width="20%"><b>* 그룹타입이름</b></td>
			<td width="40%">&nbsp;<form:input for="group_type_name" path="group_type_name" /></td>
			<td width="40%">&nbsp;<form:errors path="group_type_name" cssClass="error"/></td>
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
			<c:when test="${empty groupType.group_type_id}">
				<!-- <input type="submit" value="등록"> -->
				<input type="button" id="createButton" name="createButton" value="등록">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listGrouptype.do'">
			</c:when>
			<c:otherwise>
				<!-- <input type="submit" value="수정"> -->
				<input type="button" id="modifyButton" name="modifyButton" value="수정">
				<input type="button" id="deleteButton" name="deleteButton" value="삭제">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listGrouptype.do'">		
			</c:otherwise>
		</c:choose>
	</div>
	<!--//버튼 [E]-->
</form:form>

</body>
</html>