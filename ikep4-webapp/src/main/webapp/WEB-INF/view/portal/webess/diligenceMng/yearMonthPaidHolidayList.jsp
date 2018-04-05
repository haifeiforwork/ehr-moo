<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");

		$("#searchButton").click(function() {
			$.callProgress();
			$("#yearMonthPaidHolidayForm").submit();
		});

		$("a[name=paidHoliday]").click(function(){

			$("#popupForm").html("");
			$("#popupForm").append($(this).next("span.rowInfo").html());

			var target = "paidHolidayPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=500px, height=300px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/retrievePaidHolidayUseList.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();

		});
	});
})(jQuery);
</script>
<form id="yearMonthPaidHolidayForm" name="yearMonthPaidHolidayForm" method="post" action="<c:url value='/portal/moorimess/diligenceMng/yearMonthPaidHolidayList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연월차 내역 조회</h1>
	
	<c:set var="yearList" value="${resultList.ET_YEAR }"/>

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
		<c:choose>
			<c:when test="${params.exBtrtl eq 'P200' && params.exYearGb eq 'X' }">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="20%">적치&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="20%"><c:out value="${resultList.EX_ANZHL_2 }"/></th>
							<th scope="col" width="20%">사용&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="20%">보상&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="20%">잔여&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${ET_LIST}">
							<tr>
								<td><c:out value="${result.ANZHL_A}"/></td>
								<td><c:out value="${result.ANZHL_B}"/></td>
								<td><c:out value="${result.ANZHL_C}"/></td>
								<td><c:out value="${result.ANZHL_C_1}"/></td>
								<td><c:out value="${result.ANZHL_D}"/></td>
							</tr>
						</c:forEach>
					 </tbody>
				</table>
			</c:when>
			<c:otherwise>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="25%">적치&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="25%"><c:out value="${resultList.EX_ANZHL_2 }"/></th>
							<th scope="col" width="25%">사용&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="25%">잔여&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${ET_LIST}">
							<tr>
								<td><c:out value="${result.ANZHL_A}"/></td>
								<td><c:out value="${result.ANZHL_B}"/></td>
								<td><c:out value="${result.ANZHL_C}"/></td>
								<td><c:out value="${result.ANZHL_D}"/></td>
							</tr>
						</c:forEach>
					 </tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="list01">
		<c:choose>
			<c:when test="${params.exBtrtl eq 'P200' && params.exYearGb eq 'X' }">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="60px">월</th>
							<th scope="col" width="20%">적치&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="*"><c:out value="${resultList.EX_ANZHL_2 }"/></th>
							<th scope="col" width="20%">사용&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="20%">보상&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="20%">잔여&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${ET_LIST_M}">
							<tr>
								<td><c:out value="${result.MONTHT}"/></td>
								<td><c:out value="${result.ANZHL_A}"/></td>
								<td><c:out value="${result.ANZHL_B}"/></td>
								<td>
									<c:choose>
										<c:when test="${fn:trim(result.ANZHL_C) ne '0.0' }">
											<a href="#" class="board_2" name="paidHoliday"><c:out value="${result.ANZHL_C}"/></a>
										</c:when>
										<c:otherwise>
											<c:out value="${result.ANZHL_C}"/>
										</c:otherwise>
									</c:choose>
									<span class="rowInfo">
										<input type="hidden" name="IM_YEAR"  value="<c:out value="${params.imYear}"/>"/>
										<input type="hidden" name="IM_MONTH" value="<c:out value="${fn:replace(result.MONTHT,'월','') }"/>"/>
										<input type="hidden" name="IM_FLAG"  value="<c:out value="${params.exFlag}"/>"/>
										<input type="hidden" name="exBtrtl"  value="<c:out value="${params.exBtrtl}"/>"/>
										<input type="hidden" name="exYearGb" value="<c:out value="${params.exYearGb}"/>"/>
									</span>
								</td>
								<td><c:out value="${result.ANZHL_C_1}"/></td>
								<td><c:out value="${result.ANZHL_D}"/></td>
							</tr>
						</c:forEach>
					 </tbody>
				</table>
			</c:when>
			<c:otherwise>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="60px">월</th>
							<th scope="col" width="23%">적치&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="*"><c:out value="${resultList.EX_ANZHL_2 }"/></th>
							<th scope="col" width="23%">사용&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
							<th scope="col" width="23%">잔여&nbsp;<c:out value="${resultList.EX_ANZHL_1 }"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="result" items="${ET_LIST_M}">
							<tr>
								<td><c:out value="${result.MONTHT}"/></td>
								<td><c:out value="${result.ANZHL_A}"/></td>
								<td><c:out value="${result.ANZHL_B}"/></td>
								<td>
									<c:choose>
										<c:when test="${fn:trim(result.ANZHL_C) ne '0.0' }">
											<a href="#" class="board_2" name="paidHoliday"><c:out value="${result.ANZHL_C}"/></a>
										</c:when>
										<c:otherwise>
											<c:out value="${result.ANZHL_C}"/>
										</c:otherwise>
									</c:choose>
									<span class="rowInfo">
										<input type="hidden" name="IM_YEAR"  value="<c:out value="${params.imYear}"/>"/>
										<input type="hidden" name="IM_MONTH" value="<c:out value="${fn:replace(result.MONTHT,'월','') }"/>"/>
										<input type="hidden" name="IM_FLAG"  value="<c:out value="${params.exFlag}"/>"/>
										<input type="hidden" name="exBtrtl"  value="<c:out value="${params.exBtrtl}"/>"/>
										<input type="hidden" name="exYearGb" value="<c:out value="${params.exYearGb}"/>"/>
									</span>
								</td>
								<td><c:out value="${result.ANZHL_D}"/></td>
							</tr>
						</c:forEach>
					 </tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="f_title">
		<p>※ 연차와 가산 연차가 제외된 일수임.</p>
		<p>※ 근속 1년 미만자 연차를 사용 시 익년도 발생 연차에서 해당일 수 만큼 공제됨.</p>
		<p>※ 대구공장 경우, 1~6월 발생월차가 근속 1년 미만자 연차에 표기됨.</p>
	</div>
</div>
</form>
<form id="popupForm" name="popupForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>