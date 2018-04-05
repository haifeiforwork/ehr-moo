<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<html>

<head>
<%@ include file="/base/common/meta.jsp" %>

<script type="text/javascript" src="<c:url value='/base/js/jquery-1.4.4.js'/>"></script>
<script type="text/javascript">
//<!--

						
	$(document).ready(function(){
		
		$('#confirmButton').click(function(){
			$('form[name=groupBulkAddForm]').attr({
				action:"<c:url value='/group/bulkAdd.do'/>"
			});
			$('form[name=groupBulkAddForm]').submit();
		});
	});
	
//-->
</script>
</head>
<body>
<form name="groupBulkAddForm" method="post" enctype="multipart/form-data">
	<!--리스트 테이블 [S]-->
	<br>
	<table width="100%" border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td width="30%">&nbsp;<b>* 파일 선택</b></td>
			<td width="70%">&nbsp;<input type="file" name="file" id="file" /></td>
		</tr>
	</table>
	<br>
	<!--리스트 테이블 [E]-->
</form>

<!--버튼 [S]-->
<div align="right">
	<input type="button" id="templateButton" value="양식다운로드" onclick="javascript:location.href='downloadTemplate.do'"/>
	&nbsp;&nbsp;
	<input type="button" id="confirmButton" value="등록" />
	<input type="button" id="listButton" value="목록" onclick="javascript:location.href='listGroup.do'">		
</div>
<!--//버튼 [E]-->

</body>
</html>