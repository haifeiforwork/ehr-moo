<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>
<head>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>

<script type="text/javascript">
//<!--
	
	$(function(){
		$('a[name=detailLink]').click(function(){
			$('form[name=userListForm]').attr({
				action:"<c:url value='/user/createUser.do'/>"
			});
			$('form[name=userListForm]').submit();
		});
		
		$('#addButton').click(function(){
			$('form[name=userListForm]').attr({
				action:"<c:url value='/user/createUser.do'/>"
			});
			$('form[name=userListForm]').submit();
		});
		
		$('#bulkAddButton').click(function(){
			$('form[name=userListForm]').attr({
				action:"<c:url value='/user/bulkAdd.do'/>"
			});
			$('form[name=userListForm]').submit();
		});
	});
	
	
//-->
</script>
<title>사용자 관리 목록</title>
</head>

<body>

<form name="userListForm" >
	<!--리스트 테이블 [S]-->
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>ID</td>
			<td>사원번호</td>
			<td>부서</td>
			<td>이름</td>
			<td>등록일</td>
		</tr>
		<c:forEach var="result" items="${userList}" varStatus="loopStatus">
			<tr>
				<td><a name="detailLink" href="<c:url value='/user/createUser.do?id=${result.user_id}'/>" >${result.user_id}</a></td>
				<td><c:out value="${result.emp_no}"/></td>
				<td><c:out value="${result.team_name}"/></td>
				<td><c:out value="${result.user_name}"/></td>
				<td><c:out value="${result.regist_date}"/></td>
			</tr>
		</c:forEach>
	</table>
	<!--리스트 테이블 [E]-->

	<!--버튼 [S]-->
	<div align="right">
		<input type="button" value="등록하기" onclick="javascript:location.href='createUser.do'">
		<input type="button" value="파일로 등록" id="bulkAddButton">
	</div>
	<!--//버튼 [E]-->
</form>

</body>
</html>