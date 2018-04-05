<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("input[name=startDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		$("#searchButton").click(function() {
			$.callProgress();
			$.setParam();
			$("#monthDiligenceReportForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"X\"/>");
			$("#monthDiligenceReportForm").append("<input type=\"hidden\" name=\"imVrc\" value=\"<c:out value="${resultMap.EX_VRC}"/>\"/>");
			$("#monthDiligenceReportForm").submit();
		});
		
		$("a[name=page]").click(function(){
			$.callProgress();
			$("#monthDiligenceReportForm").find("input[name=currentPage]").val($(this).data("page"));
			<c:if test="${params.imFirst eq 'X'}">
			$("#monthDiligenceReportForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"X\"/>");
			$("#monthDiligenceReportForm").append("<input type=\"hidden\" name=\"imVrc\" value=\"<c:out value="${resultMap.EX_VRC}"/>\"/>");
			</c:if>
			$("#monthDiligenceReportForm").submit();
		});
		
		$("#btnPrint").click(function(){
			
			var target = "printPopup";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=750px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);
			
			$("#printPopForm").html("");
			
			$("#printPopForm").append("<input type=\"hidden\" name=\"hidStartDate\" value=\"<c:out value="${params.hidStartDate}"/>\"/>");
			$("#printPopForm").append("<input type=\"hidden\" name=\"hidEndDate\" value=\"<c:out value="${params.hidEndDate}"/>\"/>");
			$("#printPopForm").append("<input type=\"hidden\" name=\"hidImPernr\" value=\"<c:out value="${params.hidImPernr}"/>\"/>");
			$("#printPopForm").append("<input type=\"hidden\" name=\"hidImTotal\" value=\"<c:out value="${params.hidImTotal}"/>\"/>");
			<c:if test="${params.imFirst eq 'X'}">
			$("#printPopForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"<c:out value="${params.imFirst}"/>\"/>");
			$("#printPopForm").append("<input type=\"hidden\" name=\"imVrc\" value=\"<c:out value="${resultMap.EX_VRC}"/>\"/>");
			</c:if>

			$("#printPopForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/monthDiligencePrintPop.do'/>");
			$("#printPopForm").attr("target", target);
			$("#printPopForm").submit();
			
			popup.focus();
		});
		
		$("#btnExcel").click(function(){

			$("#excelForm").html("");

			$("#excelForm").append("<input type=\"hidden\" name=\"hidStartDate\" value=\"<c:out value="${params.hidStartDate}"/>\"/>");
			$("#excelForm").append("<input type=\"hidden\" name=\"hidEndDate\" value=\"<c:out value="${params.hidEndDate}"/>\"/>");
			$("#excelForm").append("<input type=\"hidden\" name=\"hidImPernr\" value=\"<c:out value="${params.hidImPernr}"/>\"/>");
			$("#excelForm").append("<input type=\"hidden\" name=\"hidImTotal\" value=\"<c:out value="${params.hidImTotal}"/>\"/>");
			
			<c:if test="${params.imFirst eq 'X'}">
			$("#excelForm").append("<input type=\"hidden\" name=\"imFirst\" value=\"<c:out value="${params.imFirst}"/>\"/>");
			$("#excelForm").append("<input type=\"hidden\" name=\"imVrc\" value=\"<c:out value="${resultMap.EX_VRC}"/>\"/>");
			</c:if>

			$("#excelForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/downloadMonthDiligenceReport.do'/>");
			$("#excelForm").submit();
		});
		
		$.initSet = function(){
			$("input[name=startDate]").val("<c:out value="${params.hidStartDate}"/>");
			$("input[name=hidStartDate]").val("<c:out value="${params.hidStartDate}"/>");

			$("input[name=endDate]").val("<c:out value="${params.hidEndDate}"/>");
			$("input[name=hidEndDate]").val("<c:out value="${params.hidEndDate}"/>");
			
			$("select[name=imPernr] option[value=<c:out value="${params.hidImPernr}"/>]").attr("selected", "selected");
			$("input[name=hidImPernr]").val("<c:out value="${params.hidImPernr}"/>");
			
			<c:if test="${params.hidImTotal eq 'X'}">
			$("input[name=imTotal]").attr("checked", "checked");
			$("input[name=hidImTotal]").val("<c:out value="${params.hidImTotal}"/>");
			</c:if>
			
			$("input[name=currentPage]").val("<c:out value="${params.currentPage}"/>");
		};
		
		$.setParam = function(){
			$("#monthDiligenceReportForm").append("<input type=\"hidden\" name=\"clearCache\" value=\"Y\"/>");
			
			$("input[name=currentPage]").val("1");
			
			$("input[name=hidStartDate]").val($("input[name=startDate]").val());
			$("input[name=hidEndDate]").val($("input[name=endDate]").val());
			$("input[name=hidImPernr]").val($("select[name=imPernr]").val());
			
			if($("input[name=imTotal]").is(":checked")){
				$("input[name=hidImTotal]").val("X");
			}else{
				$("input[name=hidImTotal]").val("");
			}
		};
		
		$.initSet();
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
.week-sum{ background-color:#FFDFDC !important;}
</style>

<form id="monthDiligenceReportForm" name="monthDiligenceReportForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/monthDiligenceReportList.do'/>" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>월 근태보고서 조회</h1>
	
	<c:set var="etBanwon" value="${resultMap.ET_BANWON }"/>

	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">조회기간</span></td>
					<td>
						<input type="text" name="startDate" id="startDate" value="" class="input datepicker parameter" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>&nbsp;(<c:out value="${resultMap.EX_BEGWK }"/>)
						&nbsp;~&nbsp;
						<input type="text" name="endDate" id="endDate" value="" class="input datepicker parameter" readonly="readonly"/>
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>&nbsp;(<c:out value="${resultMap.EX_ENDWK }"/>)
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td width="50px" class="f_right"><span class="f_333">성명</span></td>
					<td>
						<select name="imPernr" id="imPernr">
							<c:forEach var="result" items="${etBanwon }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="search_delimiter"></div>
		<table border="0">
			<tbody>
				<tr>
					<td><label for="imTotal"><input type="checkbox" name="imTotal" id="imTotal" value=""/>&nbsp;누계로 보기</label></td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="button_box">
		<a href="#" class="button_img01" id="btnPrint"><span>인쇄</span></a>
		<a href="#" class="button_img01" id="btnExcel"><span>Excel Down</span></a>
	</div>
	
	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="70px">사원번호</th>
					<th scope="col" width="70px">성명</th>
					<th scope="col" width="*">부서명</th>
					<c:if test="${empty params.hidImTotal }">
						<th scope="col" width="80px">일자</th>
						<th scope="col" width="6%">근무형태</th>
						
						<th scope="col" width="6%">근무상태</th>
					</c:if>
					<th scope="col" width="6%">근무시간</th>
					<th scope="col" width="6%">시간외I</th>
					<th scope="col" width="6%">시간외II</th>
					<th scope="col" width="6%">야간근로</th>
					
					<th scope="col" width="6%">야간초과</th>
					<th scope="col" width="6%">주휴(비번)</th>
					<th scope="col" width="6%">공휴근로</th>
					<th scope="col" width="6%">휴가근로</th>
					<th scope="col" width="80px">시간외수당</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="15">해당 데이터가 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td><c:out value="${result.PERNR}"/></td>
								<td><c:out value="${result.ENAME}"/></td>
								<td><c:out value="${result.ORGTX}"/></td>
								<c:if test="${empty params.hidImTotal }">
									<td>
										<fmt:parseDate var="dateString" value="${result.DATUM}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</td>
									<td><c:out value="${result.TTEXT}"/></td>
									
									<td><c:out value="${result.ATEXT}"/></td>
								</c:if>
								<td>
									<c:if test="${result.WKTIM ne '0.00' }">
										<c:out value="${result.WKTIM}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.OVTM1 ne '0.00' }">
										<c:out value="${result.OVTM1}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.OVTM2 ne '0.00' }">
										<c:out value="${result.OVTM2}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.NIGHT ne '0.00' }">
										<c:out value="${result.NIGHT}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.OVNIG ne '0.00' }">
										<c:out value="${result.OVNIG}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.WKOFF ne '0.00' }">
										<c:out value="${result.WKOFF}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.HOLWK ne '0.00' }">
										<c:out value="${result.HOLWK}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.VACWK ne '0.00' }">
										<c:out value="${result.VACWK}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${result.OVTIM ne '0' }">
										<c:out value="${result.OVTIM}"/>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
	
	<c:if test="${!empty etList }">
		<%@include file="/WEB-INF/view/portal/webess/paging.jsp"%>
	</c:if>
	
	<input type="hidden" name="currentPage" value=""/>
	
	<input type="hidden" name="hidStartDate" value=""/>
	<input type="hidden" name="hidEndDate" value=""/>
	<input type="hidden" name="hidImPernr" value=""/>
	<input type="hidden" name="hidImTotal" value=""/>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="printPopForm" name="printPopForm" method="post">
</form>
<form id="excelForm" name="excelForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
