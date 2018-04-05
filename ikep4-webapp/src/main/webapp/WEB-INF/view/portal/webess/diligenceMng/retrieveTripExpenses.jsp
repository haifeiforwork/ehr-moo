<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
(function($) {
	$(document).ready(function(){
		$("input[name=baseDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("#btnSearch").click(function(){
			$("#tripExpensesForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/retrieveTripExpenses.do'/>");
			$("#tripExpensesForm").attr("target", "_self");
			$("#tripExpensesForm").submit();
		});

		$("#btnClose").click(function(){
			self.close();
		});

		$("input[name=baseDate]").val("<c:out value="${params.IM_DATE}"/>");

		var form = $("#tripExpensesForm");

		form.append("<span class=\"rowInfo\"></span>");
		<c:forEach items="${keySet}" var="key">
			form.find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${IM_LIST[key]}"/>\"/>");
		</c:forEach>
	});
})(jQuery);
//-->
</script>

<style>
input.w70px {width:70px;}
</style>

<form name="tripExpensesForm" id="tripExpensesForm" method="post">
<div id="wrap" style="padding-top:10px; padding-left:7px;">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>출장 여비 기준 정보</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class='button_img01' id="btnClose"><span>닫기</span></a>
		<a href="#" class='button_img01' id="btnSearch"><span>조회</span></a>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="20%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">출장국가</th>
					<td class="list03_td">
						<c:if test="${params.tripDiv eq '1' || IM_LIST.ZCNTYP eq '1'}">KR</c:if>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">기준일자</th>
					<td class="list03_td">
						<input name="baseDate" id="baseDate" class="input datepicker" type="text" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">출장유형</th>
					<td class="list03_td">
						<c:forEach items="${tripTypeList }" var="result">
							<c:if test="${result.KEY eq params.tripType || result.KEY eq IM_LIST.ACTIVITY_TYPE}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">직급</th>
					<td class="list03_td"><c:out value="${costResult.EX_TRFGR }"/>&nbsp;-&nbsp;<c:out value="${costResult.EX_TRFTXT }"/></td>
				</tr>
				<tr>
					<th class="list03_td_bg">직책</th>
					<td class="list03_td"><c:out value="${costResult.EX_ZZJIK }"/>&nbsp;-&nbsp;<c:out value="${costResult.EX_JIKTXT }"/></td>
				</tr>
			</tbody>
		</table>
	</div>

	<c:set value="${costResult.EX_ZLIST }" var="exZList"/>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="20%"/>
				<col width="25%"/>
				<col width="25%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">숙박비</th>
					<td colspan="3" class="list03_td">
						<fmt:formatNumber value="${exZList.SBETRG }" groupingUsed="true"/>&nbsp;<c:out value="${exZList.WAERS }"/>&nbsp;X&nbsp;<c:out value="${exZList.SDAY }"/>&nbsp;박&nbsp;=&nbsp;<fmt:formatNumber value="${exZList.STBETG }" groupingUsed="true"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">일당</th>
					<td colspan="3" class="list03_td">
						<fmt:formatNumber value="${exZList.DBETRG }" groupingUsed="true"/>&nbsp;<c:out value="${exZList.WAERS }"/>&nbsp;X&nbsp;<c:out value="${exZList.ZDAY }"/>&nbsp;일&nbsp;=&nbsp;<fmt:formatNumber value="${exZList.DTBETG }" groupingUsed="true"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">식비</th>
					<td colspan="3" class="list03_td">
						<fmt:formatNumber value="${exZList.FBETRG }" groupingUsed="true"/>&nbsp;<c:out value="${exZList.WAERS }"/>&nbsp;X&nbsp;<c:out value="${exZList.ZDAY }"/>&nbsp;일&nbsp;=&nbsp;<fmt:formatNumber value="${exZList.FTBETG }" groupingUsed="true"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">교통비</th>
					<td class="list03_td">
						<c:out value="${exZList.ZTRNGD }"/>
					</td>
					<th class="list03_td_bg">
						예상 P/D<br/>
						예상 P/D(원화)<br/>
						적용환율
					</th>
					<td class="list03_td">
						<fmt:formatNumber value="${exZList.EXBETRG }" groupingUsed="true"/>&nbsp;(<c:out value="${exZList.WAERS }"/>)<br/>
						<fmt:formatNumber value="${exZList.TBETRG }" groupingUsed="true"/>&nbsp;원<br/>
						<c:out value="${exZList.KURSV }"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>