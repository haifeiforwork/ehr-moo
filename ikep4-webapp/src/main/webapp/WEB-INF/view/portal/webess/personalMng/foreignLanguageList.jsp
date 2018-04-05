<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){});
})(jQuery);;
</script>
<form id="foreignLanguageForm" name="foreignLanguageForm" method="post">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>외국어점수 조회</h1>

	<c:set var="etList" value="${result.ET_LIST}"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="8%">구분</th>
					<th scope="col" width="12%">평가구분</th>
					<th scope="col" width="*">평가기관</th>
					<th scope="col" width="80px">평가일자</th>
					<th scope="col" width="12%">필기점수</th>
					<th scope="col" width="12%">회화점수</th>
					<th scope="col" width="12%">종합점수</th>
					<th scope="col" width="12%">평가등급</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="8">외국어 점수에 대한 정보가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.LNTXT}"/></td>
								<td><c:out value="${result.GNTXT}"/></td>
								<td class="f_left"><c:out value="${result.APRMN}"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.APRDT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td class="f_right"><c:out value="${result.WTPNT}"/></td>
								<td class="f_right"><c:out value="${result.TKPNT}"/></td>
								<td class="f_right"><c:out value="${result.TTPNT}"/></td>
								<td><c:out value="${result.TTGRDTXT}"/></td>
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