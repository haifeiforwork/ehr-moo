<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){});
})(jQuery);;
</script>
<form id="careerForm" name="careerForm" method="post">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>입사전 경력 조회</h1>

	<c:set var="etList" value="${result.ET_LIST}"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="16%">근무회사</th>
					<th scope="col" width="16%">근무기간</th>
					<th scope="col" width="16%">근무부서</th>
					<th scope="col" width="*%">담당업무</th>
					<th scope="col" width="16%">최종직위</th>
					<th scope="col" width="10%">연봉</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="6">해당 경력사항이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.ARBGB}"/></td>
								<td><c:out value="${result.PERIOD}"/></td>
								<td><c:out value="${result.ZZORG}"/></td>
								<td><c:out value="${result.ZZJOB}"/></td>
								<td><c:out value="${result.ZZJGD}"/></td>
								<td class="f_right"><c:out value="${result.SALARY}"/></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
</div>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>