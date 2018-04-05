<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>

<head>
<%@ include file="/base/common/meta.jsp" %>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--
	
	$(function(){
		$('#addButton').click(function(){
			$('form[name=grouptypeListForm]').attr({
				action:"<c:url value='/grouptype/createGrouptype.do'/>"
			});
			$('form[name=grouptypeListForm]').submit();
		});
	});
	
	
//-->
</script>
<title>그룹타입 관리 목록</title>
</head>

<body>

<form name="grouptypeListForm" >
	<!--리스트 테이블 [S]-->
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>그룹타입 ID</td>
			<td>그룹타입 이름</td>
			<td>포탈 ID</td>
			<td>등록자</td>
			<td>등록일</td>
		</tr>
		<c:forEach var="result" items="${grouptypeList}" varStatus="loopStatus">
			<tr>
				<td><a name="detailLink" href="<c:url value='/grouptype/createGrouptype.do?id=${result.group_type_id}'/>" >${result.group_type_id}</a></td>
				<td><c:out value="${result.group_type_name}"/></td>
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
		<input type="button" value="등록하기" onclick="javascript:location.href='createGrouptype.do'">
	</div>
	<!--//버튼 [E]-->


</body>
</html>