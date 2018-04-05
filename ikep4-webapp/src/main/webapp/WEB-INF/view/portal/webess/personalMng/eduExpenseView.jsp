<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnBack").click(function() {
			$("#eduExpenseForm").attr("action", "<c:url value='/portal/moorimess/personalMng/eduExpenseList.do'/>");
			$("#eduExpenseForm").submit();
		});
	});
})(jQuery);;
</script>
<form id="eduExpenseForm" name="eduExpenseForm" method="post" action="<c:url value='/portal/moorimess/payMng/loanView.do'/>">

<c:set var="etList" value="${resultMap.ET_LIST }"/>

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>학자금 신청/조회</h1>

	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
	</div>
	<c:choose>
		<c:when test="${params.ASTAT eq '04' or params.ASTAT eq '09'}">
			<div class="list03">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list03_td_bg">성명</th>
							<td class="list03_td"><c:out value="${params.FAMNM }"/></td>
							<th class="list03_td_bg">학자금 유형</th>
							<td class="list03_td"><c:out value="${params.SUBNM }"/></td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="14%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th>지급일자</th>
							<th>지급유무</th>
							<th>입학금</th>
							<th>등록금</th>
							<th>학교명</th>
							<th>학년</th>
							<th>학기(분기)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${etList }" var="result">
							<tr>
								<td>
									<fmt:parseDate var="dateString" value="${result.PAYDT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.PAYTX}"/></td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ENFEE}" groupingUsed="true"/>
								</td>
								<td class="f_right">
									<fmt:formatNumber value="${result.TOFEE}" groupingUsed="true"/>
								</td>
								<td><c:out value="${result.SCHTX}"/></td>
								<td><c:out value="${result.SCHYR}"/></td>
								<td><c:out value="${result.TERMS}"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:when>
		<c:otherwise>
			<div class="list03">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="15%"/>
						<col width="35%"/>
						<col width="15%"/>
						<col width="35%"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list03_td_bg">신청일자</th>
							<td class="list03_td" colspan="3">
								<fmt:parseDate var="dateString" value="${params.REQDT}" pattern="yyyyMMdd" />
								<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
							</td>
						</tr>
						<tr>
							<th class="list03_td_bg">이름</th>
							<td class="list03_td" colspan="3"><c:out value="${params.FAMNM }"/></td>
						</tr>
						<tr>
							<th class="list03_td_bg">학교유형</th>
							<td class="list03_td"><c:out value="${params.SUBNM }"/></td>
							<th class="list03_td_bg">학교명</th>
							<td class="list03_td"><c:out value="${params.SCHTX }"/></td>
						</tr>
						<tr>
							<th class="list03_td_bg">학년</th>
							<td class="list03_td" colspan="3"><c:out value="${params.SCHYR }"/></td>
						</tr>
						<tr>
							<th class="list03_td_bg">입학금</th>
							<td class="list03_td">
								<fmt:formatNumber value="${params.ENFEE}" groupingUsed="true"/>
							</td>
							<th class="list03_td_bg">등록금/수업료</th>
							<td class="list03_td">
								<fmt:formatNumber value="${params.TOFEE}" groupingUsed="true"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</c:otherwise>
	</c:choose>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>