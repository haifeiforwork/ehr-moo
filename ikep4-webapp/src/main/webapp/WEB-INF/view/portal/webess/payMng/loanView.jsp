<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#searchButton").click(function() {
			$.callProgress();
			$("#loanForm").submit();
		});

		$("#btnAllSearch").click(function(){
			$.callProgress();
			$("#loanForm").find("input[name=imAll]").remove();
			$("#loanForm").append("<input type=\"hidden\" name=\"imAll\" value=\"X\"/>");
			$("#loanForm").submit();
		});

		$("#btnBack").click(function() {
			$("#loanForm").attr("action", "<c:url value='/portal/moorimess/payMng/loanList.do'/>");
			$("#loanForm").submit();
		});

		$("#btnPrint").click(function(){
			$(".print").hide();
			window.print();
			$(".print").show();
		});

		<c:forEach items="${keySet}" var="key">
			$("#loanForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${params[key]}"/>\"/>");
		</c:forEach>

		$("select[name=imYear] option[value=<c:out value="${params.imYear}"/>]").attr("selected", "selected");
		$("select[name=imMonth] option[value=<c:out value="${params.imMonth}"/>]").attr("selected", "selected");

	});
})(jQuery);;
</script>

<c:set var="yearList"  value="${resultMapCode.ET_YEAR}"/>
<c:set var="monthList" value="${resultMapCode.ET_MONTH}"/>

<c:set var="etDetail" value="${resultMap.ET_DETAIL }"/>
<c:set var="etSum"    value="${resultMap.ET_SUM }"/>

<form id="loanForm" name="loanForm" method="post" action="<c:url value='/portal/moorimess/payMng/loanView.do'/>">

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>대출내역 조회</h1>

	<div class="search_box print">
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
						<a href="#" class="button_img06" id="btnAllSearch"><span>전체조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="button_box print">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnPrint"><span>인쇄</span></a>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="20%"/>
				<col width="15%"/>
				<col width="20%"/>
				<col width="15%"/>
				<col width="20%"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">대출금 유형</th>
					<td class="list03_td"><c:out value="${params.ZDLART }"/></td>
					<th class="list03_td_bg">대출 순번</th>
					<td class="list03_td"><c:out value="${params.OBJPS }"/></td>
					<th class="list03_td_bg">대출금</th>
					<td class="list03_td">
						<fmt:formatNumber value="${params.DARBT}" groupingUsed="true"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">대출 일자</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.DATBW}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
					</td>
					<th class="list03_td_bg">만기 일자</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.ENDDA}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
					</td>
					<th class="list03_td_bg">이율</th>
					<td class="list03_td"><c:out value="${params.INDIN }"/></td>
				</tr>
				<tr>
					<th class="list03_td_bg">거치 월수</th>
					<td class="list03_td"><c:out value="${params.GRPRD }"/></td>
					<td class="list03_td" colspan="4"></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="*"/>
			</colgroup>
			<thead>
				<tr>
					<th>년 월</th>
					<th>구분</th>
					<th>원금상환액</th>
					<th>이자상환액</th>
					<th>잔액</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty etDetail}">
						<c:forEach items="${etDetail }" var="result">
							<tr>
								<td><c:out value="${result.ZDATE}"/></td>
								<td><c:out value="${result.OCCTX}"/></td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ZWONG}" groupingUsed="true"/>
								</td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ZEJAG}" groupingUsed="true"/>
								</td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ZREST}" groupingUsed="true"/>
								</td>
							</tr>
						</c:forEach>
						<c:forEach items="${etSum }" var="result">
							<tr>
								<td class="f_center"><c:out value="${result.ZTEXT }"/></td>
								<td><c:out value="${result.OCCTX }"/></td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ZWONG_S}" groupingUsed="true"/>
								</td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ZEJAG_S}" groupingUsed="true"/>
								</td>
								<td class="f_right">
									<fmt:formatNumber value="${result.ZREST_S}" groupingUsed="true"/>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td class="f_center" colspan="5">조회된 대출상환 내역이 없습니다.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>
<span class="rowInfo">
</span>
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