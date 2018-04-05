<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("select[name=imMonth] option[value=<c:out value='${params.imMonth}'/>]").attr("selected", "selected");

		$("#searchButton").click(function() {
			$.callProgress();
			$("#evaluationForm").submit();
		});

		if("<c:out value="${params.EX_RESULT}"/>" != "S"){
			alert("<c:out value="${params.EX_MESSAGE}"/>");
		}
	});
})(jQuery);;
</script>
<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/moorimess/diligenceMng/evaluationList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>월 근태평가 Feedback</h1>

	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">조회년월</span></td>
					<td>
						<select name="imYear" id="imYear">
							<c:forEach var="result" items="${yearList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
						<select name="imMonth" id="imMonth">
							<c:forEach var="result" items="${monthList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="button_box"></div>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="*">일자</th>
					<th scope="col" width="7%">근무형태</th>
					<th scope="col" width="7%">근무상태</th>
					<th scope="col" width="7%">근무시간</th>
					<th scope="col" width="7%">시간외Ⅰ</th>
					<th scope="col" width="7%">시간외Ⅱ</th>
					<th scope="col" width="7%">야간근로</th>
					<th scope="col" width="7%">야간 초과</th>
					<th scope="col" width="8%">주휴(일요일)</th>
					<th scope="col" width="7%">주휴(비번)</th>
					<th scope="col" width="7%">공휴근로</th>
					<th scope="col" width="7%">휴가근로</th>
					<th scope="col" width="7%">관리자</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty resultList}">
						<tr>
							<td colspan="13">해당 월 근태평가 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${resultList}" varStatus="inx">
							<c:if test="${!inx.last }">
								<tr>
									<td>
										<fmt:parseDate var="dateString" value="${result.DATUM}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</td>
									<td><c:out value="${result.TTEXT}"/></td>
									<td><c:out value="${result.ATEXT}"/></td>
									<td><c:out value="${result.ANZHL}"/></td>
									<td><c:out value="${result.ANZHL1}"/></td>
									<td><c:out value="${result.ANZHL2}"/></td>
									<td><c:out value="${result.ANZHL3}"/></td>
									<td><c:out value="${result.ANZHL8}"/></td>
									<td><c:out value="${result.ANZHL4}"/></td>
									<td><c:out value="${result.ANZHL5}"/></td>
									<td><c:out value="${result.ANZHL6}"/></td>
									<td><c:out value="${result.ANZHL7}"/></td>
									<td><c:out value="${result.ENAME}"/></td>
								</tr>
							</c:if>
						</c:forEach>
						<c:forEach var="result" items="${resultSum}" varStatus="inx">
							<tr>
								<td><b><c:out value="${result.DATUM_T}"/></b></td>
								<td></td>
								<td></td>
								<td><b><c:out value="${result.ANZHL}"/></b></td>
								<td><b><c:out value="${result.ANZHL1}"/></b></td>
								<td><b><c:out value="${result.ANZHL2}"/></b></td>
								<td><b><c:out value="${result.ANZHL3}"/></b></td>
								<td><b><c:out value="${result.ANZHL8}"/></b></td>
								<td><b><c:out value="${result.ANZHL4}"/></b></td>
								<td><b><c:out value="${result.ANZHL5}"/></b></td>
								<td><b><c:out value="${result.ANZHL6}"/></b></td>
								<td><b><c:out value="${result.ANZHL7}"/></b></td>
								<td></td>
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