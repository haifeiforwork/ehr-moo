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
			var codeVal = $('#job_duty_code').val();
			if(codeVal==""){
				alert('Code를 입력하세요');
				return ;
			}
			checkVar = true;
			$.ajax({
				type:'POST',
				url:"<c:url value='/jobduty/checkCode.do'/>",
				dataType:'text',
				data:({ code:$('#job_duty_code').val() }),
				error: 
					function(textStatus) {
					    alert('error:' + textStatus);
				    },
				success:
					function(data){
						if(data=='1') {
							alert('중복된 Code 입니다.');
							$('#job_duty_code').val("");
						} else {
							alert('중복된 Code가 아닙니다.');
						}
					}
			});
		});
		
		// 등록
		$('#createButton').click(function(){
			if(checkVar==true) {
				$('form[name=jobDutyAddForm]').attr({
					action:"<c:url value='/jobduty/createJobduty.do'/>"
				});
				$('form[name=jobDutyAddForm]').submit();
			} else {
				alert('Code 중복확인이 필요합니다.');
			}
		});
		
		$('#modifyButton').click(function(){
			$('form[name=jobDutyAddForm]').attr({
				action:"<c:url value='/jobduty/createJobduty.do'/>"
			});
			$('form[name=jobDutyAddForm]').submit();
		});
		
		// 삭제
		$('#deleteButton').click(function(){
			$('form[name=jobDutyAddForm]').attr({
				action:"<c:url value='/jobduty/deleteJobduty.do'/>"
			});
			$('form[name=jobDutyAddForm]').submit();
		});

	});
//-->
</script>

<title>직급 코드 등록</title>
</head>

<body>
<form:form name="jobDutyAddForm" modelAttribute="jobDuty" commandName="jobDuty" method="post" action="createJobduty.do">

	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td width="20%"><b>* 직급코드</b></td>
			<c:choose>
				<c:when test="${empty jobDuty.job_duty_code}">
					<td width="40%">
						&nbsp;<form:input for="job_duty_code" path="job_duty_code" />
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="job_duty_code" cssClass="error"/></td>
				</c:when>
				<c:otherwise>
					<td width="40%">
						&nbsp;<form:input for="job_duty_code" path="job_duty_code" readonly="false"/>
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="job_duty_code" cssClass="error"/></td>
				</c:otherwise>
			</c:choose>				
		</tr>
		<tr>
			<td width="20%"><b>* 직급이름</b></td>
			<td width="40%">&nbsp;<form:input for="job_duty_name" path="job_duty_name" /></td>
			<td width="40%">&nbsp;<form:errors path="job_duty_name" cssClass="error"/></td>
		</tr>
		<tr>
			<td width="20%"><b>* 정렬순서</b></td>
			<td width="40%">&nbsp;<form:input for="sort_order" path="sort_order" /></td>
			<td width="40%">&nbsp;<form:errors path="sort_order" cssClass="error"/></td>
		</tr>
		<tr>
			<td width="20%"><b>포탈ID</b></td>
			<td width="40%">&nbsp;<form:input for="portal_id" path="portal_id" /></td>
			<td width="40%">&nbsp;<form:errors path="portal_id" cssClass="error"/></td>
		</tr>
	</table>

	<!--버튼 [S]-->
	<div align="right">
		<c:choose>
			<c:when test="${empty jobDuty.job_duty_code}">
				<!-- <input type="submit" value="등록"> -->
				<input type="button" id="createButton" name="createButton" value="등록">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listJobduty.do'">
			</c:when>
			<c:otherwise>
				<!-- <input type="submit" value="수정"> -->
				<input type="button" id="modifyButton" name="modifyButton" value="수정">
				<input type="button" id="deleteButton" name="deleteButton" value="삭제">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listJobduty.do'">		
			</c:otherwise>
		</c:choose>
	</div>
	<!--//버튼 [E]-->
</form:form>

</body>
</html>