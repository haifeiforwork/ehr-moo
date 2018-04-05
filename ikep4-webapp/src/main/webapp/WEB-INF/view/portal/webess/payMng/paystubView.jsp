<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<c:set var="exPerson"   value="${resultMap.EX_PERSON }"/>
<c:set var="etResult"   value="${resultMap.ET_RESULT }"/>
<c:set var="exPay"      value="${resultMap.EX_PAY }"/>

<c:set var="etComment"  value="${resultMap.ET_COMMENT }"/>
<c:set var="etComment2"  value="${resultMap.ET_COMMENT2 }"/>
<c:set var="etBoard"    value="${resultMap.ET_BOARD }"/>
<c:set var="etPay1"     value="${resultMap.ET_PAY1 }"/>
<c:set var="etCollect2" value="${resultMap.ET_COLLECT2 }"/>
<c:set var="etCollect3" value="${resultMap.ET_COLLECT3 }"/>
<c:set var="etCollect4" value="${resultMap.ET_COLLECT4 }"/>
<c:set var="etSearch"   value="${resultMap.ET_SEARCH }"/>

<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#searchButton").click(function() {
			$.callProgress();
			$("#paystubForm").submit();
		});

		$("#btnPrint").click(function(){
			$(".print").hide();
			document.execCommand("print", false, null);
			//window.print();
			$(".print").show();
		});

		$("#paystubForm").append("<span class=\"searchList\"></span>");
		<c:forEach var="result" items="${etSearch }">
			$("#paystubForm").find("span.searchList").append("<input type=\"hidden\" name=\"KEY\" value=\"<c:out value="${result.KEY}"/>\"/>");
			$("#paystubForm").find("span.searchList").append("<input type=\"hidden\" name=\"VALUE\" value=\"<c:out value="${result.VALUE}"/>\"/>");
		</c:forEach>

		$("#paystubForm").find("span.searchList").append("<input type=\"hidden\" name=\"searchCnt\" value=\"<c:out value='${fn:length(etSearch)}'/>\"/>");

		$("select[name=imSearch] option[value=<c:out value='${params.imSearch}'/>]").attr("selected", "selected");
		$("input[name=imFlag]").val("<c:out value="${resultMap.EX_FLAG}"/>");
	});
})(jQuery);

</script>

<style>
.div_table{
	width:100%;
}
.div_table td{
	padding:5px;
}
</style>




<form id="paystubForm" name="paystubForm" method="post" action="<c:url value='/portal/moorimess/payMng/paystubView.do'/>">

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>임금명세서</h1>
	<div class="search_box print">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">조회년월</span></td>
					<td>
						<select name="imSearch" id="imSearch" style="padding-top:1px;">
							<c:forEach var="result" items="${etSearch }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
						<input type="hidden" name="imFlag" value=""/>
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
					<th class="list03_td_bg">소속</th>
					<td class="list03_td"><c:out value="${exPerson.ORGTX }"/></td>
					<th class="list03_td_bg">사번</th>
					<td class="list03_td"><c:out value="${exPerson.PERNR }"/></td>
				</tr>
				<tr>
					<th class="list03_td_bg">직급/호봉</th>
					<td class="list03_td"><c:out value="${exPerson.TRFGR }"/> <c:out value="${exPerson.TRFST }"/></td>
					<th class="list03_td_bg">성명</th>
					<td class="list03_td"><c:out value="${exPerson.ENAME }"/></td>
				</tr>
				<tr>
					<th class="list03_td_bg">지급구분</th>
					<td class="list03_td"><c:out value="${exPay.ZINDEX }"/></td>
					<th class="list03_td_bg">입금계좌</th>
					<td class="list03_td"><c:out value="${exPay.ZBANK }"/></td>
				</tr>
				<tr>
					<th class="list03_td_bg">지급일</th>
					<td class="list03_td"><c:out value="${exPay.PAYDT }"/></td>
					<td class="list03_td" colspan="2"></td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">당월내역</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
					<col width="20%"/>
				</colgroup>
				<thead>
					<tr>
						<th>기본급/시급</th>
						<th>통상시급</th>
						<th>지급총계</th>
						<th>공제총계</th>
						<th>차인지급액</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${etPay1 }" var="result">
						<tr>
							<td class="f_right"><c:out value="${result.ZZGIBON }"/></td>
							<td class="f_right"><c:out value="${result.ZZAVEG }"/></td>
							<td class="f_right"><c:out value="${result.ZZSUM }"/></td>
							<td class="f_right"><c:out value="${result.ZZTOTAL }"/></td>
							<td class="f_right"><c:out value="${result.ZZMINUS }"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<div class="table_box">
		<table class="div_table">
			<colgroup>
				<col width="50%"/>
				<col width="50%"/>
			</colgroup>
			<tr>
				<td valign="top">
					<div>
						<p class="f_title" style="padding-bottom:10px">지급내역</p>
						<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<caption></caption>
								<colgroup>
									<col width="33%"/>
									<col width="33%"/>
									<col width="*"/>
								</colgroup>
								<thead>
									<tr>
										<th>항목</th>
										<th>시간(일수)</th>
										<th>금액</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${empty etCollect2 }">
											<tr>
												<td colspan="3">데이터가 없습니다.</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${etCollect2 }" var="result">
												<tr>
													<td><c:out value="${result.PAYMENT_TXT }"/></td>
													<td><c:out value="${result.ANZHL }"/></td>
													<td class="f_right"><c:out value="${result.ZBETRG }"/></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									
								</tbody>
							</table>
						</span>
					</div>
				</td>
				<td valign="top">
					<div>
						<p class="f_title" style="padding-bottom:10px">공제내역</p>
						<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<caption></caption>
								<colgroup>
									<col width="50%"/>
									<col width="50%"/>
								</colgroup>
								<thead>
									<tr>
										<th>공제내역</th>
										<th>금액</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${empty etCollect3 }">
											<tr>
												<td colspan="2">데이터가 없습니다.</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${etCollect3 }" var="result">
												<tr>
													<td><c:out value="${result.PAYMENT_TXT }"/></td>
													<td class="f_right"><c:out value="${result.ZBETRG }"/></td>
												</tr>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</span>
						<span>
							<c:choose>
								<c:when test="${!empty etComment2 }">
									<c:forEach var="result" items="${etComment2 }">
										<p class="f_left"><c:out value="${result.ZCOMMENT}" escapeXml="false"/></p><br/>
									</c:forEach>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</span>
					</div>
				</td>
			</tr>
		</table>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">학자금 내역</p>
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
					<col width="25%"/>
				</colgroup>
				<thead>
					<tr>
						<th>항목</th>
						<th>금액</th>
						<th>항목</th>
						<th>금액</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty etResult }">
							<tr>
								<td colspan="4">데이터가 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach items="${etResult }" var="result">
								<tr>
									<td><c:out value="${result.LGTXT1 }"/></td>
									<td class="f_right"><c:out value="${result.BETRG1 }"/></td>
									<td><c:out value="${result.LGTXT2 }"/></td>
									<td class="f_right"><c:out value="${result.BETRG2 }"/></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					
				</tbody>
			</table>
		</span>
	</div>
	<div class="table_box">
		<c:choose>
			<c:when test="${!empty etComment }">
				<c:forEach var="result" items="${etComment }">
					<p class="f_center"><c:out value="${result.ZCOMMENT}" escapeXml="false"/></p><br/>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p class="f_center"><font size="3"><b>귀하의 노고에 진심으로 감사드립니다</b></font></p><br/>
			</c:otherwise>
		</c:choose>
		<p class="f_center"><font size="6"><b><c:out value="${exPerson.BUTXT}" /></b></font></p>
	</div>
	
	<div class="button_box">&nbsp;</div>
	
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