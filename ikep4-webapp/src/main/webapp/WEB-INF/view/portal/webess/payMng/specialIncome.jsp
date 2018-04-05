<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnInterest").click(function(){
			
			var target = "interest";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=800px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			$("#popupForm").append("<input type=\"hidden\" name=\"EX_YEAR\" value=\"<c:out value="${resultMap.EX_YEAR}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"REGNO\" value=\"<c:out value="${resultMap.EX_PER_INFO.REGNO}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_REPAY}" var="result">
				<c:forEach items="${repayKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etRepay_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etRepayCnt\" value=\"<c:out value="${fn:length(resultMap.ET_REPAY)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/interestPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
		
		$("#btnBorrow").click(function(){
			
			var target = "borrow";
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
			
			<c:forEach items="${resultMap.ET_LOANE5}" var="result">
				<c:forEach items="${rentKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etLoane5_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_LOANE6}" var="result">
				<c:forEach items="${rentKeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etLoane6_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etLoane5Cnt\" value=\"<c:out value="${fn:length(resultMap.ET_LOANE5)}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etLoane6Cnt\" value=\"<c:out value="${fn:length(resultMap.ET_LOANE6)}"/>\"/>");
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/borrowPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
	});
})(jQuery);
</script>
<div class="table_box2">
	<p class="f_title margin5_0">보험료</p>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="200px"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th colspan="2">근무지 구분</th>
				<th>보험료</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td rowspan="2">건강보험, 장기요양보험</td>
				<td class="f_left">종(전)근무지</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSMED}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td class="f_left">주(현)근무지</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.NOW_4ME}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td rowspan="2">고용보험</td>
				<td class="f_left">종(전)근무지</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSEIM}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td class="f_left">주(현)근무지</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.NOW_4UE}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr class="list01_tr_gray">
				<td colspan="2">보험료 계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM31}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>

	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>주(현) 근무지의 건강보험/고용보험료는 자동계산 됩니다. (별도 입력없음) </td>
				<td align="right">&nbsp;</td>
			</tr>
		</tbody>
	</table>
	
	<div class="delimiter"></div>

	<p class="f_title margin5_0">주택자금</p>

	<table width="100%" border="0">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnBorrow"><span>거주자차입금입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="S1"><span>작성요령</span></a>
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
				<th colspan="3">주택임차 차입금 원리금 상환액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td rowspan="2">주택임차 차입금 원리금 상환액</td>
				<td class="f_left">대출기관(금융기관)차입</td>
				<td>
					<span class="help_wrap">
						<input type="text" class="input f_right amount taxParam <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>" name="repay" value="<fmt:formatNumber value="${resultMap.EX_TAX.REPAY}" groupingUsed="true"/>" <c:if test="${resultMap.EX_DISABLED eq 'TRUE'}"> disabled </c:if>/>
					</span>
				</td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">거주지(개인)차입</td>
				<td class="f_right">
					<input type="hidden"  value="<fmt:formatNumber value="${resultMap.EX_TAX.INDPL}" groupingUsed="true"/>" />
					<fmt:formatNumber value="${resultMap.EX_TAX.INDPL}" groupingUsed="true"/>
				</td>
				<td>작성요령참고</td>
			</tr>
		</table>
	</span>

	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td>
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnInterest"><span>이자상환액입력</span></a>
					<a href="#" class="button_img05" name="btnWritingTipPop" data-type="S2"><span>작성요령</span></a>
					</c:if>
				</td>
			</tr>
		</tbody>
	</table>

	<span class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="300px"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th colspan="2">장기주택 저당 차입금 이자상환액</th>
				<th>이자 상환액</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td rowspan="3" class="f_left">① 2011.12.31 이전 차입분</td>
				<td class="f_left">차입금이자 (15년 미만)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.FPRDO}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">차입금이자 (15년~29년)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INTTL}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">차입금이자 (30년이상)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INSLN}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td rowspan="2" class="f_left">② 2012.1.1 이후 차입분   (15년 이상)</td>
				<td class="f_left">고정금리/비거치상환대출</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INTFN}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">기타대출</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INTOT}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td rowspan="4" class="f_left">③ 2015.1.1 이후 차입분</td>
				<td class="f_left">고정금리 &amp; 비거치식 상환대출 (15년 이상)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INT15A}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">고정금리 or 비거치식 상환대출 (15년 이상)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INT15O}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">기타대출 (15년 이상)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INT15E}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr>
				<td class="f_left">고정금리 or 비거치식 상환대출 (10년 이상)</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.INT10O}" groupingUsed="true"/></td>
				<td>작성요령참고</td>
			</tr>
			<tr class="list01_tr_gray">
				<td colspan="2">주택자금공제액 계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM41}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</span>
</div>