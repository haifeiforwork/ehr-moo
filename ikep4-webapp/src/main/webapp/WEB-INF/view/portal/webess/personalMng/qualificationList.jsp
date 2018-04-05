<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){});
})(jQuery);;
</script>
<form id="qualificationForm" name="qualificationForm" method="post">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>자격사항 조회</h1>

	<c:set var="etList" value="${result.ET_LIST}"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="*">자격유형</th>
					<th scope="col" width="10%">자격등급</th>
					<th scope="col" width="80px">취득일</th>
					<th scope="col" width="14%">자격번호</th>
					<th scope="col" width="14%">발행기관</th>
					<th scope="col" width="8%">선임여부</th>
					<th scope="col" width="6%">수당</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="7">자격사항 대한 정보가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.LICTX}"/></td>
								<td><c:out value="${result.LIGRT}"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.LICDT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.LICNO}"/></td>
								<td><c:out value="${result.PUBTX}"/></td>
								<td>
									<c:if test="${result.APPRV eq 'Y' }">
										<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
									</c:if>
								</td>
								<td><c:out value="${result.LICMT_T}"/></td>
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