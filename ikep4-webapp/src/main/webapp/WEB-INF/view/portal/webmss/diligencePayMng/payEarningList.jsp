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
			$("#payEarningForm").submit();
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
		
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
.week-sum{ background-color:#FFDFDC !important;}
</style>
<form id="payEarningForm" name="payEarningForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/payEarningList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>급여실적 조회</h1>
	
	<%@include file="/WEB-INF/view/portal/webmss/personalInfo.jsp"%>
	
	<c:set var="yearList" value="${resultMapCode.ET_YEAR }"/>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">년간급여누계내역</p>
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
		
		<c:set var="etTab" value="${resultMapList.ET_TAB }"/>

		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="40px">월</th>
						<th scope="col" width="7%">급여</th>
						<th scope="col" width="7%">상여</th>
						<th scope="col" width="6%">성과금</th>
						<th scope="col" width="6%">격려금</th>
						<th scope="col" width="*">일시금 및<br/>소급위로금</th>
						<th scope="col" width="6%">휴가비</th>
						<th scope="col" width="7%">연월차</th>
						<th scope="col" width="6%">유사급여</th>
						<th scope="col" width="7%">지급계</th>
						<th scope="col" width="7%">소득세</th>
						<th scope="col" width="6%">주민세</th>
						<th scope="col" width="6%">건강보험</th>
						<th scope="col" width="6%">국민연금</th>
						<th scope="col" width="6%">고용보험</th>
						<th scope="col" width="6%">노조회비</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${!empty etTab }">
							<c:forEach var="result" items="${etTab}">
								<tr>
									<td><c:out value="${result.PAYDT}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT121X}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT141X}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT141AX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT141BX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT141EX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT141CX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT141DX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT193X}" escapeXml="false"/></td>
									<td class="f_right week-sum"><c:out value="${result.BTTOTX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT301X}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT302X}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT4MEX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT4PEX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT4UEX}" escapeXml="false"/></td>
									<td class="f_right"><c:out value="${result.BT156X}" escapeXml="false"/></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="16">해당 조회 내역이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				 </tbody>
			</table>
			<div style="display:inline-block;"></div>
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
