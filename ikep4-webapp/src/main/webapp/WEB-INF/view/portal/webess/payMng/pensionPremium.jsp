<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnWorkPlace").click(function(){
				
			var target = "workPlace";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1050px, height=300px, top=100px, left=200px, resizable=no";
			
			var popup = window.open("", target, param);
			
			$("#popupForm").html("");
			
			<c:forEach items="${resultMap.ET_LAST}" var="result">
			$("#popupForm").append("<input type=\"hidden\" name=\"etLastKey\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"etLastValue\" value=\"<c:out value="${result.VALUE}"/>\"/>");
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etLastCnt\" value=\"<c:out value="${fn:length(resultMap.ET_LAST)}"/>\"/>");
			
			<c:forEach items="${taxKeySet}" var="key">
			$("#popupForm").append("<input type=\"hidden\" name=\"exTax_<c:out value="${key}"/>\" value=\"<c:out value="${resultMap.EX_TAX[key]}"/>\"/>");
			</c:forEach>
			
			<c:forEach items="${resultMap.ET_LAST2}" var="result">
				<c:forEach items="${last2KeySet}" var="key">
				$("#popupForm").append("<input type=\"hidden\" name=\"etLast2_<c:out value="${key}"/>\" value=\"<c:out value="${result[key]}"/>\"/>");
				</c:forEach>
			</c:forEach>
			
			$("#popupForm").append("<input type=\"hidden\" name=\"etLast2Cnt\" value=\"<c:out value="${fn:length(resultMap.ET_LAST2)}"/>\"/>");
			
			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/payMng/workPlacePop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();
			
			popup.focus();
		});
	});
})(jQuery);;
</script>
<div class="table_box2">
	<p class="f_title margin5_0">연금보험료 공제</p>
	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="*"/>
				<col width="200px"/>
				<col width="150px"/>
				<col width="150px"/>
			</colgroup>
			<tr>
				<th colspan="2">근무지 구분</th>
				<th>지출보험료</th>
				<th>한도액</th>
			</tr>
			<tr>
				<td rowspan="4">연금보험료(국민연금, 공무원연금, 군인연금 등)</td>
				<td class="f_left">종(전)근무지</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSNPM}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr>
				<td class="f_left">종(현)근무지</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.NOW_SUM}" groupingUsed="true"/></td>
				<td>전액</td>
			</tr>
			<tr class="list01_tr_gray">
				<td>계</td>
				<td class="f_right"><fmt:formatNumber value="${resultMap.EX_TAX.ZSUM}" groupingUsed="true"/></td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td>
					<img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/>
				</td>
				<td>주(현)근무지의 연금보험료(국민연금)은 자동계산됩니다..(별도 입력없음)</td>
				<td align="right">
					<c:if test="${resultMap.EX_DISABLED eq 'FALSE'}">
					<a href="#" class="button_img05" id="btnWorkPlace"><span>종(전)근무지 추가</span></a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="10">
					<img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/>
				</td>
				<td colspan="2">종전근무지명이 없을 경우 공장운영부/HR운영팀으로 연락바랍니다. </td>
			</tr>
		</tbody>
	</table>
</div>