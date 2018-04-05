<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){});
})(jQuery);;
</script>

<form id="educationForm" name="educationForm" method="post" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>상벌내역 조회</h1>

	<c:set var="etList1" value="${result.ET_LIST1}"/>
	<c:set var="etList2" value="${result.ET_LIST2}"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">포상내역</p>

		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="14%">포상유형</th>
						<th scope="col" width="80px">포상일자</th>
						<th scope="col" width="14%">포상구분</th>
						<th scope="col" width="8%">사내/외 구분</th>
						<th scope="col" width="8%">포상금</th>
						<th scope="col" width="20%">수여자</th>
						<th scope="col" width="*">포상내역</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty etList1}">
							<tr>
								<td colspan="7">조회된 내역이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${etList1}">
								<tr>
									<td><c:out value="${result.AWDTX}"/></td>
									<td>
										<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</td>
									<td><c:out value="${result.REWDTX}"/></td>
									<td><c:out value="${result.AWDGTX}"/></td>
									<td class="f_right">
										<fmt:formatNumber value="${result.ZZAMT}" groupingUsed="true"/>
									</td>
									<td><c:out value="${result.ZZAWD}"/></td>
									<td class="f_left"><c:out value="${result.ZZTXT}"/></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				 </tbody>
			</table>
		</div>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">징계내역</p>

		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="8%">징계유형</th>
					<th scope="col" width="80px">징계일</th>
					<th scope="col" width="8%">징계구분명</th>
					<th scope="col" width="26%">징계제목</th>
					<th scope="col" width="*">징계사유</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList2}">
						<tr>
							<td colspan="5">조회된 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList2}">
							<tr>
								<td><c:out value="${result.PUNTX}"/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.PUNTXT}"/></td>
								<td><c:out value="${result.ZZTTL}"/></td>
								<td class="f_left"><c:out value="${result.ZZBAS}"/></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
			</table>
		</div>
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