<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){});
})(jQuery);;
</script>
<form id="educationForm" name="educationForm" method="post" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>학력사항 조회</h1>
	
	<c:set var="etList" value="${result.ET_LIST}"/>
	
	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="20%">기간</th>
					<th scope="col" width="14%">학교유형</th>
					<th scope="col" width="14%">학교</th>
					<th scope="col" width="10%">본/분교</th>
					<th scope="col" width="10%">학위</th>
					<th scope="col" width="14%">전공</th>
					<th scope="col" width="*">부전공</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="7">학력사항 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.PERIOD}"/></td>
								<td><c:out value="${result.SNTXT}"/></td>
								<td><c:out value="${result.SCHTX}"/></td>
								<td><c:out value="${result.ZZTXT}"/></td>
								<td><c:out value="${result.SATXT}"/></td>
								<td><c:out value="${result.STTXT}"/></td>
								<td><c:out value="${result.FTEXT}"/></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>