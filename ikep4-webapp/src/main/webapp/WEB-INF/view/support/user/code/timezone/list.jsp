<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>
<head>

<c:set var="timezoneList" value="${timezoneList}"/>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--
	
	$(function(){
		$('#addButton').click(function(){
			$('form[name=timezoneListForm]').attr({
				action:"<c:url value='/timezone/createTimezone.do'/>"
			});
			$('form[name=timezoneListForm]').submit();
		});
	});
	
	
//-->
</script>
<title>TIMEZONE 관리 목록</title>
</head>

<body>

<form name="timezoneListForm" >
	<!--리스트 테이블 [S]-->
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>TIMEZONE ID</td>
			<td>TIMEZONE 이름</td>
			<td>등록자</td>
			<td>등록일</td>
		</tr>
		<c:forEach var="result" items="${timezoneList}" varStatus="loopStatus">
			<tr>
				<td><a name="detailLink" href="<c:url value='/timezone/createTimezone.do?id=${result.timezone_id}'/>" >${result.timezone_id}</a></td>
				<td><c:out value="${result.timezone_name}"/></td>
				<td><c:out value="${result.register_name}"/></td>
				<td><c:out value="${result.regist_date}"/></td>
			</tr>
		</c:forEach>
	</table>
	<!--리스트 테이블 [E]-->
</form>
	<!--버튼 [S]-->
	<div align="right">
		<input type="button" value="등록하기" onclick="javascript:location.href='createTimezone.do'">
	</div>
	<!--//버튼 [E]-->


</body>
</html>