<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnCredit").click(function(){
			
			var target = "credit";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1130px, height=350px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_CRED}" var="result">
				<c:forEach items="${creditKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etCred_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etCredCnt\" value=\"<c:out value="${fn:length(resultMap.ET_CRED)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/creditPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnAnnuity").click(function(){
			
			var target = "annuity";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=900px, height=350px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_BEGDA\" value=\"<c:out value="${resultMap.EX_BEGDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_ENDDA\" value=\"<c:out value="${resultMap.EX_ENDDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_TOT\" value=\"<c:out value="${resultMap.EX_TOT}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_PENSAVE}" var="result">
				<c:forEach items="${annuityKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etPensave_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			$("#popupForm").append("<input type=\"hidden\" name=\"etPensaveCnt\" value=\"<c:out value="${fn:length(resultMap.ET_PENSAVE)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_BANK}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankCnt\" value=\"<c:out value="${fn:length(resultMap.ET_BANK)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/annuityPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnHouse").click(function(){
			
			var target = "house";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=900px, height=350px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_BEGDA\" value=\"<c:out value="${resultMap.EX_BEGDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_ENDDA\" value=\"<c:out value="${resultMap.EX_ENDDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_TOT\" value=\"<c:out value="${resultMap.EX_TOT}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_CHUNG}" var="result">
				<c:forEach items="${houseKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etChung_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			$("#popupForm").append("<input type=\"hidden\" name=\"etChungCnt\" value=\"<c:out value="${fn:length(resultMap.ET_CHUNG)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_BANK}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankCnt\" value=\"<c:out value="${fn:length(resultMap.ET_BANK)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/housePop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnPayment").click(function(){
			
			var target = "payment";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=900px, height=350px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_BEGDA\" value=\"<c:out value="${resultMap.EX_BEGDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_ENDDA\" value=\"<c:out value="${resultMap.EX_ENDDA}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_LTAIS}" var="result">
				<c:forEach items="${paymentKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etLtais_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			$("#popupForm").append("<input type=\"hidden\" name=\"etLtaisCnt\" value=\"<c:out value="${fn:length(resultMap.ET_LTAIS)}"/>\"/>");
			
			<c:forEach items="${resultMap.ET_BANK}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etBankCnt\" value=\"<c:out value="${fn:length(resultMap.ET_BANK)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/paymentPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
	});
})(jQuery);;
</script>
<div class="table_box2">

	<p class="f_title margin5_0">신용카드 소득공제</p>

	<table width="100%" border="0">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnCredit"><span>신용카드입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="O1"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span  class="list01">
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
				<td class="f_left">① 신용카드(전통시장ㆍ대중교통 사용분 제외)<br></td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.CRDCD}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">② 직불ㆍ선불카드(전통시장ㆍ대중교통 사용분 제외)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.DRTCD}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">③ 현금영수증(전통시장ㆍ대중교통 사용분 제외)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.CASHE}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">④ 전통시장사용분</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.CCRDCD}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">⑤ 대중교통이용분</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.PCRDCD}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">계(①+②+③+④+⑤)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM52}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<%-- <tr>
				<td class="f_left">⑥ 본인 신용카드 등 사용금액(2014년)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFCREPP}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">⑦ 본인 신용카드 등 사용금액(2015년)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFCREP}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr>
			<tr>
				<td class="f_left">⑧ 본인 추가공제율 사용분(2014년)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXPP}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr> --%>
			<%-- <tr>
				<td class="f_left">⑨ 본인 추가공제율 사용분(2014년)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXP}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr> --%>
			<%-- <tr>
				<td class="f_left">⑩ 본인 신용카드 등 사용금액(2015년)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFCREN}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr> --%>
			<%-- <tr>
				<td class="f_left">⑪ 본인 추가공제율 사용(2016년 상반기)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXM}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr> --%>
			<%-- <tr>
				<td class="f_left">⑫ 본인 추가공제율 사용(2015년 하반기)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.SELFTAXN}" groupingUsed="true"/></td>
				<td>작성요령 참조</td>
			</tr> --%>
		</table>
	</span>

	<span class="list04">
		<table border="0" cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td> 신용카드 등 입력방법 </td>
				</tr>
				<%-- <tr>
					<td width="10">&nbsp;</td>
					<td>① 본인사용액 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>- 14년도 사용액 : 일반·전통시장 ·대중교통  구분 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>- 15년도 사용액 : 일반·전통시장 ·대중교통  구분 없음 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>- 16년도 사용액 : 추가공제율 사용분 / 일반·전통시장 ·대중교통  구분 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>※ 2016년도 추가공제율 사용분은 무조건 전통시장 ·대중교통중 하나로 선택 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><span class="list04_td">  ② 부양가족 사용액 </span></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>- 14,15년도 사용액 : 미입력 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>- 16년도 사용액 : 일반·전통시장 ·대중교통  구분 </td>
				</tr>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td>2014·2015·2016년 본인 사용금액이 있는 경우 반드시 입력바랍니다. </td>
				</tr> --%>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td>신용카드 등 소득공제액 : 초과사용금액(총급여*25% 초과사용분) * 기본공제율 (%) </td>
				</tr>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td>기본공제율 : 신용카드 15%, 전통시장 · 대중교통 : 40%, 현금영수증 · 직불카드 30%</td>
				</tr>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td>절대 한도액  : ⓐ + ⓑ</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> ①  기본한도 : Min(총급여*20%, 300만원) (단, 1.2억원 초과자는 200만원 한도) </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> ②  추가한도 : Min (기본한도 초과액, 전통시장공제액, 100만원) +
                              Min (기본한도 초과액- 전통시장공제액, 대중교통공제액, 100만원) </td>
				</tr>
				<%-- <tr>
					<td colspan="2"><추가공제금액></td>
				</tr>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td> 2016년 상반기 추가공제율 사용분 – (2014년 추가공제율 사용분 *50%)</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> ① 2014년 대비 2015년 신용카드 등 본인 사용액  증가자에 한정 </td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> ② 전통시장, 대중교통, 직불카드 등 사용분 </td>
				</tr>
				<tr>
					<td><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
					<td> 추가사용액 공제율: 추가공제율 사용액 증가분 * 공제율 (20%)</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td> ※ 추가공제금액은 절대 한도액 (최대 500만원) 내에서 공제 가능함</td>
				</tr> --%>
			</tbody>
		</table>
	</span>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">개인연금저축 공제</p>

	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnAnnuity"><span>개인연금저축 입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="O2"><span>작성요령</span></a>
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
				<td class="f_left">개인연금저축</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FPERP}" groupingUsed="true"/></td>
				<td>40%와 72만원</td>
			</tr>
		</table>
	</span>
	
	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td> 연금저축(2000.12.31 이후 가입분)은 세액공제탭 -&gt; 연금계좌공제에서 입력바랍니다.</td>
			</tr>
		</tbody>
	</table>
	
	<div class="delimiter"></div>

	<p class="f_title margin5_0">주택마련저축 공제</p>

	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnHouse"><span>주택마련저축 입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="O3"><span>작성요령</span></a>
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
				<td class="f_left">청약저축</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.HSSAV}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">주택청약종합저축</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.MTHSS}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
		</table>
	</span>

	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td align="left">총급여 7천만원 이하인 자에 한해 공제 가능함. </td>
			</tr>
			<tr>
				<td width="10"></td>
				<td align="left">총급여 7천만원 초과자 : '14.12.31 이전 가입자는 '17.12.31 과세년도 납입분까지 120만원 납입한도로 공제 가능함.</td>
			</tr>
		</tbody>
	</table>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">장기집합투자증권저축</p>
	
	<table width="100%" border="0" class="table_button">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnPayment"><span>납입금 입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="O4"><span>작성요령</span></a>
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
				<td class="f_left">납입액</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.LTAIS}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
		</table>
	</span>

	<div class="delimiter"></div>

	<p class="f_title margin5_0">기타</p>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th>구분</th>
				<th>금액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td class="f_left">우리사주 출연금 소득공제</td>
				<td>
					<span class="help_wrap">
						<input type="text" class="input f_right amount taxParam <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>" name="comst" value="<fmt:formatNumber value="${resultMap.EX_TAX.COMST}" groupingUsed="true"/>" <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>/>
					</span>
				</td>
				<td></td>
			</tr>
			<tr>
				<td class="f_left">소기업/소상공인 부금</td>
				<td>
					<span class="help_wrap">
						<input type="text" class="input f_right amount taxParam <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>" name="smbfi" value="<fmt:formatNumber value="${resultMap.EX_TAX.SMBFI}" groupingUsed="true"/>" <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>/>
					</span>
				</td>
				<td></td>
			</tr>
			<tr>
				<td class="f_left">고용유지임금삭감액</td>
				<td>
					<span class="help_wrap">
						<input type="text" class="input f_right amount taxParam <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>" name="indif" value="<fmt:formatNumber value="${resultMap.EX_TAX.INDIF}" groupingUsed="true"/>" <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>/>
					</span>
				</td>
				<td></td>
			</tr>
		</table>
	</span>
</div>