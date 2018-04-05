<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){});
})(jQuery);;
</script>

<form id="educationForm" name="educationForm" method="post" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>가족사항 조회</h1>

	<c:set var="etList" value="${result.ET_LIST}"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="12%">관계</th>
					<th scope="col" width="12%">이름</th>
					<th scope="col" width="12%">생일</th>
					<th scope="col" width="12%">학력</th>
					<th scope="col" width="12%">동거여부</th>
					<th scope="col" width="12%">부양가족여부</th>
					<th scope="col" width="12%">장애인여부</th>
					<th scope="col" width="*">자녀양육비여부</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="8">조회된 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.ZKDSVH}"/></td>
								<td><c:out value="${result.ZNAME}"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.FGBDT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.ZFASAR}"/></td>
								<td>
									<c:if test="${!empty result.ZLIVID }">
										<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
									</c:if>
								</td>
								<td>
									<c:if test="${!empty result.ZDPTID }">
										<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
									</c:if>
								</td>
								<td>
									<c:if test="${!empty result.ZHNDID }">
										<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
									</c:if>
								</td>
								<td>
									<c:if test="${!empty result.ZCHDID }">
										<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
	<div class="f_title">※ 해당 연말정산 기간내 사망자 정보는 주관부서 (인사팀, 총무팀) 로 연락바랍니다.</div>
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