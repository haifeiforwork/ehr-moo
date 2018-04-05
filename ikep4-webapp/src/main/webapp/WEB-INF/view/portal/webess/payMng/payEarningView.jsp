<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#searchButton").click(function() {
			$.callProgress();
			$("#payEarningForm").submit();
		});

		$("#btnPrint").click(function(){
			$(".print").hide();
			window.print();
			$(".print").show();
		});

		$("select[name=imYear] option[value=<c:out value="${params.imYear}"/>]").attr("selected", "selected");
	});
})(jQuery);;
</script>
<style type="text/css">
.week-sum{ background-color:#FFDFDC !important;}
</style>

<c:set var="etTab" value="${resultMapList.ET_TAB }"/>
<c:set var="et193" value="${resultMapList.ET_193 }"/>

<form id="payEarningForm" name="payEarningForm" method="post" action="<c:url value='/portal/moorimess/payMng/payEarningView.do'/>">

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>급여실적 조회</h1>
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
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="button_box print">
		<a href="#" class="button_img01" id="btnPrint"><span>인쇄</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">년간급여누계내역</p>
		<span style="display:inline-block; overflow-x:auto; .display:inline; *zoom:1"  class="list01">
			<table style="width:1200px;" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="40px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>

					<col width="*"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>

					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>

					<col width="70px"/>
					<col width="70px"/>
				</colgroup>
				<thead>
					<tr>
						<th>월</th>
						<th>급여</th>
						<th>상여</th>
						<th>성과금</th>
						<th>격려금</th>

						<th>일시금 및<br/>소급위로금</th>
						<th>휴가비</th>
						<th>연월차</th>
						<th>인정상여</th>
						<th>유사급여</th>

						<th>지급계</th>
						<th>소득세</th>
						<th>주민세</th>
						<th>건강보험</th>
						<th>국민연금</th>

						<th>고용보험</th>
						<th>노조회비</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${etTab }" var="result">
						<tr>
							<td><c:out value="${result.PAYDT }" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT121X}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT141X}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT141AX}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT141BX}" escapeXml="false"/></td>

							<td class="f_right"><c:out value="${result.BT141EX}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT141CX}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT141DX}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result.BT151X}" escapeXml="false"/></td>
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
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box" style="overflow-x:auto;">
		<p class="f_title" style="padding-bottom:10px">년간 유사 급여누계내역</p>
		<span style="display:inline-block; overflow-x:auto; .display:inline; *zoom:1" class="list01">
			<table border="0" cellpadding="0" cellspacing="0" style="width:2200px;">
				<caption></caption>
				<colgroup>
					<col width="40px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>

					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>

					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>
					<col width="70px"/>

					<col width="70px"/>
					<col width="*"/>
				</colgroup>
				<thead>
					<tr>
						<th>월</th>
						<th>유치원<br/>학자금</th>
						<th>학자금</th>
						<th>사내강사</th>
						<th>식대</th>

						<th>종합건강<br/>검진비</th>
						<th>면접관비</th>
						<th>부임<br/>이사비</th>
						<th>파견비</th>
						<th>카페테리아<br/>사용</th>

						<th>시상금</th>
						<th>제안<br/>시상금</th>
						<th>장기<br/>근속상</th>
						<th>포상금</th>
						<th>정보실적 포상금</th>

						<th>노사화합<br/>시상금</th>
						<th>무재해<br/>시상금</th>
						<th>통신비</th>
						<th>인정상여</th>
						<th>차량유지비</th>

						<th>보증보험</th>
						<th>의료비지원</th>
						<th>연말정산</th>
						<th>야유회비</th>
						<th>특근수당</th>

						<th>명절선물</th>
						<th>생일&창립<br/>기념선물</th>
						<th>피복대</th>
						<th>출산<br/>축하금</th>
						<th>차량<br/>지원금</th>
						<th>위탁<br/>보육비</th>

						<th>합계</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${et193 }" var="result">
						<tr>
							<td><c:out value="${result.PAYDT }" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2875_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2870_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2560_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2865_T']}" escapeXml="false"/></td>

							<td class="f_right"><c:out value="${result['2880_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2890_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2910_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2915_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2920_T']}" escapeXml="false"/></td>

							<td class="f_right"><c:out value="${result['2940_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2945_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2970_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2695_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2975_T']}" escapeXml="false"/></td>

							<td class="f_right"><c:out value="${result['2980_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2985_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2990_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2995_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2250_T']}" escapeXml="false"/></td>

							<td class="f_right"><c:out value="${result['2935_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2965_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['4040_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2585_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2610_T']}" escapeXml="false"/></td>

							<td class="f_right"><c:out value="${result['2996_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2997_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2998_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2895_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2925_T']}" escapeXml="false"/></td>
							<td class="f_right"><c:out value="${result['2999_T']}" escapeXml="false"/></td>

							<td class="f_right week-sum"><c:out value="${result.TOTAL_T}" escapeXml="false"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>
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