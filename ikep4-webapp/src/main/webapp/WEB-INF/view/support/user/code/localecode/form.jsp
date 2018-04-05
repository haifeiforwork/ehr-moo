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
			var codeVal = $('#locale_code').val();
			if(codeVal==""){
				alert('Code를 입력하세요');
				return ;
			}
			checkVar = true;
			$.ajax({
				type:'POST',
				url:"<c:url value='/localecode/checkCode.do'/>",
				dataType:'text',
				data:({ code:$('#locale_code').val() }),
				error: 
					function(textStatus) {
					    alert('error:' + textStatus);
				    },
				success:
					function(data){
						if(data=='1') {
							alert('중복된 Code 입니다.');
							$('#locale_code').val("");
						} else {
							alert('중복된 Code가 아닙니다.');
						}
					}
			});
		});
		
		// 등록
		$('#createButton').click(function(){
			if(checkVar==true) {
				$('form[name=localeCodeAddForm]').attr({
					action:"<c:url value='/localecode/createLocalecode.do'/>"
				});
				$('form[name=localeCodeAddForm]').submit();
			} else {
				alert('Code 중복확인이 필요합니다.');
			}
		});
		
		$('#modifyButton').click(function(){
			$('form[name=localeCodeAddForm]').attr({
				action:"<c:url value='/localecode/createLocalecode.do'/>"
			});
			$('form[name=localeCodeAddForm]').submit();
		});
		
		// 삭제
		$('#deleteButton').click(function(){
			$('form[name=localeCodeAddForm]').attr({
				action:"<c:url value='/localecode/deleteLocalecode.do'/>"
			});
			$('form[name=localeCodeAddForm]').submit();
		});

	});
//-->
</script>

<title>로케일 코드 등록</title>
</head>

<body>
<form:form name="localeCodeAddForm" modelAttribute="localeCode" commandName="localeCode" method="post" action="createLocalecode.do">

	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td width="20%"><b>* 로케일코드</b></td>
			<c:choose>
				<c:when test="${empty localeCode.locale_code}">
					<td width="40%">
						&nbsp;<form:input for="locale_code" path="locale_code" />
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="locale_code" cssClass="error"/></td>
				</c:when>
				<c:otherwise>
					<td width="40%">
						&nbsp;<form:input for="locale_code" path="locale_code" readonly="false"/>
						&nbsp;<input type="button" id="checkButton" value="중복검사" >
					</td>
					<td width="40%">&nbsp;<form:errors path="locale_code" cssClass="error"/></td>
				</c:otherwise>
			</c:choose>				
		</tr>
		<tr>
			<td width="20%"><b>* 로케일이름</b></td>
			<td width="40%">&nbsp;<form:input for="locale_name" path="locale_name" /></td>
			<td width="40%">&nbsp;<form:errors path="locale_name" cssClass="error"/></td>
		</tr>
		<tr>
			<td width="20%"><b>* 설명</b></td>
			<td width="40%">&nbsp;<form:input for="description" path="description" /></td>
			<td width="40%">&nbsp;<form:errors path="description" cssClass="error"/></td>
		</tr>
		<tr>
			<td width="20%"><b>* 정렬순서</b></td>
			<td width="40%">&nbsp;<form:input for="sort_order" path="sort_order" /></td>
			<td width="40%">&nbsp;<form:errors path="sort_order" cssClass="error"/></td>
		</tr>
	</table>

	<!--버튼 [S]-->
	<div align="right">
		<c:choose>
			<c:when test="${empty localeCode.locale_code}">
				<!-- <input type="submit" value="등록"> -->
				<input type="button" id="createButton" name="createButton" value="등록">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listLocalecode.do'">
			</c:when>
			<c:otherwise>
				<!-- <input type="submit" value="수정"> -->
				<input type="button" id="modifyButton" name="modifyButton" value="수정">
				<input type="button" id="deleteButton" name="deleteButton" value="삭제">
				<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listLocalecode.do'">		
			</c:otherwise>
		</c:choose>
	</div>
	<!--//버튼 [E]-->
</form:form>

</body>
</html>