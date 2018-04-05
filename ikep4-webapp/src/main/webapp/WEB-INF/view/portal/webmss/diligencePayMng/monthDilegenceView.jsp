<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("select[name=imMonth] option[value=<c:out value='${params.imMonth}'/>]").attr("selected", "selected");
		
		$("input[name=selEmpNo]").val("<c:out value="${params.selEmpNo}"/>");
		
		$("#searchButton").click(function() {
			$.callProgress();
			$("#monthlyDilegenceForm").submit();
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
		
		<c:if test="${resultMapList.EX_RESULT eq 'E'}">
			alert("<c:out value="${resultMapList.EX_MESSAGE}"/>");
		</c:if>
		
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
</style>
<form id="monthlyDilegenceForm" name="monthlyDilegenceForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/monthDilegenceView.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>월근태현황 조회</h1>
	
	<%@include file="/WEB-INF/view/portal/webmss/personalInfo.jsp"%>
	
	<c:set var="yearList" value="${resultMapCode.ET_YEAR }"/>
	<c:set var="monthList" value="${resultMapCode.ET_MONTH }"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">월 근태현황</p>
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
		
		<!-- 상단버튼-->
		<div class="button_box"></div>
		
		<c:set var="etList" value="${resultMapList.ET_LIST }"/>

		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="80px">일자</th>
						<th scope="col" width="*">근무형태</th>
						<th scope="col" width="7%">근무상태</th>
						<th scope="col" width="7%">근무시간</th>
						<th scope="col" width="7%">시간외Ⅰ</th>
						<th scope="col" width="7%">시간외Ⅱ</th>
						<th scope="col" width="7%">야간근로</th>
						<th scope="col" width="7%">야간초과</th>
						<th scope="col" width="7%">주휴(일요)</th>
						<th scope="col" width="7%">주휴(비번)</th>
						<th scope="col" width="7%">공휴근로</th>
						<th scope="col" width="7%">휴가근로</th>
						<th scope="col" width="7%">관리자</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty etList}">
							<tr>
								<td colspan="13">해당 월 근태평가 내역이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="result" items="${etList}">
								<tr>
									<td><c:out value="${result.DATUM_T}"/></td>
									<td><c:out value="${result.TTEXT}"/></td>
									<td><c:out value="${result.ATEXT}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL1}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL2}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL3}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL8}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL4}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL5}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL6}"/></td>
									<td class="f_right"><c:out value="${result.ANZHL7}"/></td>
									<td><c:out value="${result.ENAME}"/></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				 </tbody>
			</table>
		</div>
	</div>
	
	<input type="hidden" name="selEmpNo" value=""/>
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