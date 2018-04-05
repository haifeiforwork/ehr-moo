<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnPremium").click(function(){
			var target = "premium";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=850px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_INS}" var="result">
				<c:forEach items="${premiumKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etIns_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etInsCnt\" value=\"<c:out value="${fn:length(resultMap.ET_INS)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/premiumPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnMedical").click(function(){
			var target = "medical";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1200px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_TOT\" value=\"<c:out value="${resultMap.EX_TOT}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_TO3\" value=\"<c:out value="${resultMap.EX_TO3}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_MEDI}" var="result">
				<c:forEach items="${medicalKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etMedi_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etMediCnt\" value=\"<c:out value="${fn:length(resultMap.ET_MEDI)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/medicalPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnEducation").click(function(){
			var target = "education";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1000px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_EDU}" var="result">
				<c:forEach items="${educationKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etEdu_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etEduCnt\" value=\"<c:out value="${fn:length(resultMap.ET_EDU)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/educationPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnDonation").click(function(){
			var target = "donation";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1250px, height=600px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_DONA}" var="result">
				<c:forEach items="${donationKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etDona_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etDonaCnt\" value=\"<c:out value="${fn:length(resultMap.ET_DONA)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/donationPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnPension").click(function(){
			var target = "pension";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=800px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_BEGDA\" value=\"<c:out value="${resultMap.EX_BEGDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_ENDDA\" value=\"<c:out value="${resultMap.EX_ENDDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_BANK}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankCnt\" value=\"<c:out value="${fn:length(resultMap.ET_BANK)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_RETPE}" var="result">
				<c:forEach items="${pensionKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etRetpe_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etRetpeCnt\" value=\"<c:out value="${fn:length(resultMap.ET_RETPE)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/pensionPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnMonthlyRentPop").click(function(){
			var target = "rent";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1200px, height=500px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_TOT\" value=\"<c:out value="${resultMap.EX_TOT}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_PNSTY}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etPnstyKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etPnstyValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etPnstyCnt\" value=\"<c:out value="${fn:length(resultMap.ET_PNSTY)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_HOUTY}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etHoutyKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etHoutyValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etHoutyCnt\" value=\"<c:out value="${fn:length(resultMap.ET_HOUTY)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_RENT}" var="result">
				<c:forEach items="${rentKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etRent_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etRentCnt\" value=\"<c:out value="${fn:length(resultMap.ET_RENT)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/monthlyRentPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
	});
})(jQuery);;
</script>
<div class="table_box2">

	<p class="f_title margin5_0">보험료</p>
	
	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnPremium"><span>보험료입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="T1"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>사용금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">일반보장성보험<br></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FOTIS}" groupingUsed="true"/></td>
				<td>100만원</td>
			</tr>
			<tr>
				<td class="f_left">장애인전용 보장성보험</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FOHIS}" groupingUsed="true"/></td>
				<td>100만원</td>
			</tr>
			<tr class="list01_tr_gray">
				<td>보험료 계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM31_2}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>

	<div class="delimiter"></div>

	<p class="f_title  margin5_0">의료비</p>
	
	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnMedical"><span>의료비입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="T2"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>사용금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">일반의료비(본인 등 외 기본 공제자)<br></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FMDED}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">본인, 경로자, 장애인 의료비 </td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FMDOH}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr class="list01_tr_gray">
				<td>의료비 계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM32}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>

	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>의료비 지출액이 본인 총급여의 3%이하일 경우 의료비공제 혜택 없습니다.</td>
				<td align="right">&nbsp;</td>
			</tr>
		</tbody>
	</table>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">교육비</p>

	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnEducation"><span>교육비입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="T3"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="200px"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th colspan="2">구분</th>
				<th>사용금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">소득자 본인</td>
				<td class="f_left">공납금 (대학원 포함)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FCEEE}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td class="f_left">취학전 아동</td>
				<td class="f_left">유치원비, 학원비 등</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUMELE}" groupingUsed="true"/></td>
				<td>1인당 300만원</td>
			</tr>
			<tr>
				<td class="f_left">초.중.고등학교</td>
				<td class="f_left">공납금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUMSCH}" groupingUsed="true"/></td>
				<td>1인당 300만원</td>
			</tr>
			<tr>
				<td class="f_left">대학생(대학원 불포함)</td>
				<td class="f_left">공납금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUMUNI}" groupingUsed="true"/></td>
				<td>1인당 900만원</td>
			</tr>
			<tr>
				<td class="f_left">장애인</td>
				<td class="f_left">특수 교육비</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FCEHD}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr class="list01_tr_gray">
				<td colspan="2">교육비 계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM33}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">기부금</p>

	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnDonation"><span>기부금입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="T4"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>2013년 이용금액</th>
				<th>2014년 이용금액</th>
				<th>기부금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">정치자금기부금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXPOLDN}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXPOLDN2}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.POLDN}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td class="f_left">법정 기부금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXFLGDO}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXFLGDO2}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FLGDO}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td class="f_left">우리사주조합 기부금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXDON30}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXDON302}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.DON30}" groupingUsed="true"/></td>
				<td>30%</td>
			</tr>
			<tr>
				<td class="f_left">종교단체 외 지정기부금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXDESDO}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXDESDO2}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.DESDO}" groupingUsed="true"/></td>
				<td>30%</td>
			</tr>
			<tr>
				<td class="f_left">종교단체 지정기부금</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXRELDO}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.EXRELDO2}" groupingUsed="true"/></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.RELDO}" groupingUsed="true"/></td>
				<td>10%</td>
			</tr>
			<tr>
				<td class="f_left">노조회의 (별도 입력없음)</td>
				<td class="f_right">&nbsp;</td>
				<td class="f_right">&nbsp;</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_DOAMT}" groupingUsed="true"/></td>
				<td></td>
			</tr>
			<tr class="list01_tr_gray">
				<td>기부금공제액 계</td>
				<td class="f_right">&nbsp;</td>
				<td class="f_right">&nbsp;</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM42}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">연금계좌공제</p>

	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnPension"><span>연금계좌입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="T5"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>납입금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">퇴직연금 소득공제</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.RETPE}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">연금저축 소득공제</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FPERN}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr class="list01_tr_gray">
				<td class="f_left">연금계좌공제 계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM53}" groupingUsed="true"/></td>
				<td class="f_right">&nbsp;</td>
			</tr>
		</table>
	</span>

	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>개인연금저축(2000.12.31 이전 가입분)은 기타소득공제탭-&gt;개인연금저축공제에서 입력바랍니다.</td>
				<td align="right">&nbsp;</td>
			</tr>
		</tbody>
	</table>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">월세액 공제</p>

	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnMonthlyRentPop"><span>월세입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="T6"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>납입금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">월세액</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.MRNTD}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
		</table>
	</span>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">세액 공제</p>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th colspan="5">외국납부세액</th>
			</tr>
			<tr>
				<td class="f_left">납세액(외화)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FORCTT}" groupingUsed="true"/></td>
				<td>
					<c:forEach items="${resultMap.ET_FORCK }" var="result">
						<c:if test="${result.KEY eq resultMap.EX_TAX.FORCK }"><c:out value="${result.VALUE }"/></c:if>
					</c:forEach>
				</td>
				<td class="f_left">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="f_left">납세액(원화)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.KORCRT}" groupingUsed="true"/></td>
				<td>KRW</td>
				<td class="f_left">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="f_left">납세국명</td>
				<td>
					<c:out value="${resultMap.EX_TAX.TAXCT}"/>
				</td>
				<td>&nbsp;</td>
				<td class="f_left">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="f_left">납부일</td>
				<td class="f_right">
					<fmt:parseDate var="dateString" value="${resultMap.EX_TAX.GETDT}" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
				</td>
				<td>&nbsp;</td>
				<td class="f_left">신청서제출일</td>
				<td>
					<fmt:parseDate var="dateString" value="${resultMap.EX_TAX.PUTDT}" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td class="f_left">국외근무처</td>
				<td class="f_right"><c:out value="${resultMap.EX_TAX.ABPLA}"/></td>
				<td>&nbsp;</td>
				<td class="f_left">근무기간</td>
				<td><c:out value="${resultMap.EX_TAX.ABDUR}"/></td>
			</tr>
			<tr>
				<td class="f_left">직책</td>
				<td class="f_right"><c:out value="${resultMap.EX_TAX.ABJOB}"/></td>
				<td>&nbsp;</td>
				<td class="f_left">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>
</div>