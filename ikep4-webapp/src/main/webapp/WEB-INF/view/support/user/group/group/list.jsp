<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>
<head>

<!-- JS or CSS -->
<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>

<script type="text/javascript">
//<!--
	
	$(function(){
		$('#addButton').click(function(){
			$('form[name=groupListForm]').attr({
				action:"<c:url value='/group/createGroup.do'/>"
			});
			$('form[name=groupListForm]').submit();
		});
		
		$('#bulkAddButton').click(function(){
			$('form[name=groupListForm]').attr({
				action:"<c:url value='/group/groupBulkAdd.do'/>"
			});
			$('form[name=groupListForm]').submit();
		});
	});
	
	
//-->
</script>
<title>그룹 관리 목록</title>
</head>

<body>

<form name="groupListForm" >
	<!--리스트 테이블 [S]-->
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>그룹 ID</td>
			<td>그룹 이름</td>
			<td>그룹 타입</td>
			<td>하위그룹수</td>
			<td>등록자</td>
			<td>등록일</td>
		</tr>
		<c:forEach var="result" items="${groupList}" varStatus="loopStatus">
			<tr>
				<td><a name="detailLink" href="<c:url value='/group/createGroup.do?id=${result.group_id}'/>" >${result.group_id}</a></td>
				<td><c:out value="${result.group_name}"/></td>
				<td><c:out value="${result.group_type_name}"/></td>
				<td><c:out value="${result.child_group_count}"/></td>
				<td><c:out value="${result.register_name}"/></td>
				<td><c:out value="${result.regist_date}"/></td>
			</tr>
		</c:forEach>
	</table>
	<!--리스트 테이블 [E]-->
</form>
	<!--버튼 [S]-->
	<div align="right">
		<input type="button" value="등록하기" onclick="javascript:location.href='createGroup.do'">
		<input type="button" value="파일로 등록" id="bulkAddButton">
	</div>
	<!--//버튼 [E]-->


</body>
</html>