<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>
<head>

<c:set var="jobRankList" value="${jobRankList}"/>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--
	
	$(function(){
		$('#addButton').click(function(){
			$('form[name=jobRankListForm]').attr({
				action:"<c:url value='/jobrank/createJobrank.do'/>"
			});
			$('form[name=jobRankListForm]').submit();
		});
	});
	
	
//-->
</script>
<title>직급코드관리 목록</title>
</head>

<body>

<form name="jobRankListForm" >
	<!--리스트 테이블 [S]-->
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>직급코드</td>
			<td>코드이름</td>
			<td>포탈ID</td>
			<td>등록자</td>
			<td>등록일</td>
		</tr>
		<c:forEach var="result" items="${jobRankList}" varStatus="loopStatus">
			<tr>
				<td><a name="detailLink" href="<c:url value='/jobrank/createJobrank.do?code=${result.job_rank_code}'/>" >${result.job_rank_code}</a></td>
				<td><c:out value="${result.job_rank_name}"/></td>
				<td><c:out value="${result.portal_id}"/></td>
				<td><c:out value="${result.register_name}"/></td>
				<td><c:out value="${result.regist_date}"/></td>
			</tr>
		</c:forEach>
	</table>
	<!--리스트 테이블 [E]-->
</form>
	<!--버튼 [S]-->
	<div align="right">
		<input type="button" value="등록하기" onclick="javascript:location.href='createJobrank.do'">
	</div>
	<!--//버튼 [E]-->


</body>
</html>