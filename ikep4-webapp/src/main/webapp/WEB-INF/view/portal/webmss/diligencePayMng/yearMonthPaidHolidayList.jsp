<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("input[name=selEmpNo]").val("<c:out value="${params.selEmpNo}"/>");
		
		$("#searchButton").click(function() {
			$.callProgress();
			$("#yearMonthPaidHolidayForm").submit();
		});
		
		$("#photo").click(function(){
			var url = "<c:url value="${resultHeader.EX_HEADER.PHOTO}"/>";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=367px, height=460px, top=200px, left=300px, resizable=no";
			var popup = window.open(url, null, param);

			popup.focus();
		});
		
		$("a[name=detail]").click(function(){
			$("#popupForm").find("input[name=selEmpNo]").remove();
			$("#popupForm").append("<input type=\"hidden\" name=\"selEmpNo\" value=\"<c:out value="${params.selEmpNo}"/>\"/>");

			var target = "detailPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1200px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/personInfoDetailPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();

		});
		
		$("a[name=paidHoliday]").click(function(){

			$("#popupForm").html("");
			$("#popupForm").append($(this).next("span.rowInfo").html());
			$("#popupForm").append("<input type=\"hidden\" name=\"selEmpNo\" value=\"<c:out value="${params.selEmpNo}"/>\">");

			var target = "paidHolidayPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=500px, height=300px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/retrievePaidHolidayUseList.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();

		});
		
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
</style>
<form id="yearMonthPaidHolidayForm" name="yearMonthPaidHolidayForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/yearMonthPaidHolidayList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>연월차내역조회</h1>
	
	<%@include file="/WEB-INF/view/portal/webmss/personalInfo.jsp"%>
	
	<c:set var="yearList" value="${resultMapCode.ET_YEAR }"/>
	<c:set var="monthList" value="${resultMapCode.ET_MONTH }"/>
	
	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">부서원 연월차 내역조회</p>
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
		
		<!-- 상단버튼-->
		<div class="button_box"></div>
		
		<c:choose>
			<c:when test="${resultMapList.EX_BTRTL eq 'P200' && resultMapList.EX_YEAR_GB eq 'X'}">
				<c:set var="etList" value="${resultMapList.ET_LIST_1 }"/>
				<c:set var="etListM" value="${resultMapList.ET_LIST_M_1 }"/>
			</c:when>
			<c:otherwise>
				<c:set var="etList" value="${resultMapList.ET_LIST }"/>
				<c:set var="etListM" value="${resultMapList.ET_LIST_M }"/>
			</c:otherwise>
		</c:choose>

		<div class="list01">
			<c:choose>
				<c:when test="${resultMapList.EX_BTRTL eq 'P200' && resultMapList.EX_YEAR_GB eq 'X'}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="25%"><c:out value="${resultMapCode.EX_ANZHL_2 }"/></th>
								<th scope="col" width="25%">사용&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="25%">보상&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="25%">잔여&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.ANZHL_B}"/></td>
											<td><c:out value="${result.ANZHL_C}"/></td>
											<td><c:out value="${result.ANZHL_C_1}"/></td>
											<td><c:out value="${result.ANZHL_D}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="4">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:when>
				<c:otherwise>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="25%">적치&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="25%"><c:out value="${resultMapCode.EX_ANZHL_2 }"/></th>
								<th scope="col" width="25%">사용&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="25%">잔여&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etList }">
									<c:forEach var="result" items="${etList}">
										<tr>
											<td><c:out value="${result.ANZHL_A}"/></td>
											<td><c:out value="${result.ANZHL_B}"/></td>
											<td><c:out value="${result.ANZHL_C}"/></td>
											<td><c:out value="${result.ANZHL_D}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="4">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						 </tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	
		<div class="list01">
			<c:choose>
				<c:when test="${resultMapList.EX_BTRTL eq 'P200' && resultMapList.EX_YEAR_GB eq 'X'}">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%">월</th>
								<th scope="col" width="23%"><c:out value="${resultMapCode.EX_ANZHL_2 }"/></th>
								<th scope="col" width="*">사용&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="23%">보상&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="23%">잔여&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etListM }">
									<c:forEach var="result" items="${etListM}">
										<tr>
											<td><c:out value="${result.MONTHT}"/></td>
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
													<input type="hidden" name="exBtrtl"  value="<c:out value="${resultMapList.EX_BTRTL}"/>"/>
													<input type="hidden" name="exYearGb" value="<c:out value="${resultMapList.EX_YEAR_GB}"/>"/>
												</span>
											</td>
											<td><c:out value="${result.ANZHL_C_1}"/></td>
											<td><c:out value="${result.ANZHL_D}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
							
						 </tbody>
					</table>
				</c:when>
				<c:otherwise>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%">월</th>
								<th scope="col" width="23%">적치&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="*"><c:out value="${resultMapCode.EX_ANZHL_2 }"/></th>
								<th scope="col" width="23%">사용&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
								<th scope="col" width="23%">잔여&nbsp;<c:out value="${resultMapCode.EX_ANZHL_1 }"/></th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${!empty etListM }">
									<c:forEach var="result" items="${etListM}">
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
													<input type="hidden" name="exBtrtl"  value="<c:out value="${resultMapList.EX_BTRTL}"/>"/>
													<input type="hidden" name="exYearGb" value="<c:out value="${resultMapList.EX_YEAR_GB}"/>"/>
												</span>
											</td>
											<td><c:out value="${result.ANZHL_D}"/></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5">해당 조회 내역이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
							
						 </tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="f_title">
			<c:choose>
				<c:when test="${resultMapList.EX_YEAR_GB eq 'X' }">
					<c:choose>
						<c:when test="${empty resultMapList.EX_FLAG }">
							<p>※ 연차(선부여 포함)의 일수는 가공의 일수임.</p>
						</c:when>
						<c:otherwise>
							<p>※ 연차(선부여 포함)/월차(선부여 포함)의 일수는 가공의 일수임.</p>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${empty resultMapList.EX_YEAR_GB}">
					<c:choose>
						<c:when test="${resultMapList.EX_BTRTL eq 'P200'}">
							<p>1)연차와 가산 연차(근속 연차)는 제외된 일수임.</p>
							<p>2)당해년도 만근시 연차가 발생함.</p>
							<p>-.사무직(비조합원) / 관리직 15일.</p>
							<p>-.사무직(조합원) / 기능직 10일.</p>
						</c:when>
						<c:otherwise>
							<p>※ 연차와 가산 연차가 제외된 일수임.</p>
						</c:otherwise>
					</c:choose>
					
					<c:if test="${empty resultMapList.EX_FLAG }">
						<p>※ 근속 1년 미만자 연차를 사용 시 익년도 발생 연차에서 해당일 수 만큼 공제됨.</p>
						<p>※ 대구공장 경우, 1~6월 발생월차가 근속 1년 미만자 연차에 표기됨.</p>
					</c:if>
					
				</c:when>
			</c:choose>
			
		</div>
	</div>
	
	<input type="hidden" name="selEmpNo" value=""/>
	<input type="hidden" name="imFirst" value="X"/>
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
