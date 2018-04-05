<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>
<head>

<!--<c:set var="localeCodeList" value="${localeCodeList}"/>-->

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--
	
	$(function(){
		$('#addButton').click(function(){
			$('form[name=localeCodeListForm]').attr({
				action:"<c:url value='/localecode/createLocalecode.do'/>"
			});
			$('form[name=localeCodeListForm]').submit();
		});
	});
	
	
//-->
</script>
<title>로케일코드관리 목록</title>
</head>

<body>

<form name="localeCodeListForm" >
	<!--리스트 테이블 [S]-->
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>로케일코드</td>
			<td>코드이름</td>
			<td>등록자</td>
			<td>등록일</td>
		</tr>
		<c:forEach var="result" items="${localeCodeList}" varStatus="loopStatus">
			<tr>
				<td><a name="detailLink" href="<c:url value='/localecode/createLocalecode.do?code=${result.locale_code}'/>" >${result.locale_code}</a></td>
				<td><c:out value="${result.locale_name}"/></td>
				<td><c:out value="${result.register_name}"/></td>
				<td><c:out value="${result.regist_date}"/></td>
			</tr>
		</c:forEach>
	</table>
	<!--리스트 테이블 [E]-->
</form>
	<!--버튼 [S]-->
	<div align="right">
		<input type="button" value="등록하기" onclick="javascript:location.href='createLocalecode.do'">
	</div>
	<!--//버튼 [E]-->


</body>
</html>