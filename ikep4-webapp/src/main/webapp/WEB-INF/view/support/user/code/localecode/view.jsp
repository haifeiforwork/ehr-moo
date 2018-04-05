<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>
<head>
<!-- Scriptlet -->

<c:set var="jobRank" value="${jobRank}"/>

<script type="text/javascript" src="<c:url value='/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--

	$(function(){
		$('#modifyButton').click(function(){
			$('form[name=jobRankDetailForm]').attr({
				action:"<c:url value='/action/com/code/jobDuty/jobDutyCodeModify'/>"
			});
			$('form[name=jobRankDetailForm]').submit();
		});
		
		$('#deleteButton').click(function(){
			if(confirm('삭제하시겠습니까?')) {
				$('form[name=jobRankDetailForm]').attr({
					action:"<c:url value='/action/com/code/jobDuty/delete'/>"
				});
				$('form[name=jobRankDetailForm]').submit();	
			}
		});
		
		$('#listButton').click(function(){
			$('form[name=jobRankDetailForm]').attr({
				action:"<c:url value='/action/com/code/jobDuty/jobDutyCodeList'/>"
			});
			$('form[name=jobRankDetailForm]').submit();
		});
	});
	
//-->
</script>
<title>직급코드 상세보기</title>
</head>
<body>
	<!--리스트 테이블 [S]-->
	<form name="jobRankDetailForm" method="post" action="">
	<input type="hidden" name="code" value="${jobRank.code}" />
	<!--리스트 테이블 [S]-->
	
	<br>
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td width="30%">직급코드</td>
			<td width="70%">&nbsp;<c:out value="${jobRank.code}"/></td>
		</tr>
		<tr>
			<td width="30%">직급이름</td>
			<td width="70%">&nbsp;<c:out value="${jobRank.name}"/></td>
		</tr>
		<tr>
			<td width="30%">정렬순서</td>
			<td width="70%">&nbsp;<c:out value="${jobRank.sort_order}"/></td>
		</tr>
		<tr>
			<td width="30%">등록자(ID/이름)</td>
			<td width="70%">&nbsp;<c:out value="${jobRank.register_id}"/>/<c:out value="${jobRank.register_name}"/></td>
		</tr>
		<tr>
			<td width="30%">등록일</td>
			<td width="70%">&nbsp;<c:out value="${jobRank.regist_date}"/></td>
		</tr>			
	</table>
	<!--리스트 테이블 [E]-->
	
	<br>
	
	<!--버튼 [S]-->
	<div align="right">
		<input type="button" value="수정" id="modifyButton">&nbsp;&nbsp;
		<input type="button" value="삭제" id="deleteButton">&nbsp;&nbsp;
		<input type="button" value="목록" id="listButton">
	</div>
	<!--//버튼 [E]-->

	</form>
</body>
</html>