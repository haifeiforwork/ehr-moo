<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
	});
	
	$(window).load(function () {
		<c:forEach items="${etPernr }" var="etPernrRow">
			$.rowspan(".<c:out value="${etPernrRow.PERNR}"/>_TTEXT");
			$.rowspan(".<c:out value="${etPernrRow.PERNR}"/>_ATEXT");
		</c:forEach>
		
		window.print();
	});
	
	$.rowspan = function(tdClass){
		
		var $td = $(tdClass);
		var length = $td.length;
		var rowspan = 1;
		var curText;
		
		for(var i = 0 ; i < length ; i++){
			curText = $td.eq(i).text();
			for(var j = i + 1 ; j < length ; j++){
				if(curText == $td.eq(j).text()){
					rowspan++;	
					$td.eq(j).hide();
				}else{
					break;
				}
			}
			$td.eq(i).attr("rowspan", rowspan);
			i = j - 1;
			rowspan = 1;
		}
	};
	
})(jQuery);;
</script>
<style type="text/css">
@page a4sheet {size:21.0cm 29.7cm; }
@page{size:auto; margin:10mm;}
.a4 {page:a4sheet; page-break-after:always; }
.list01 table { font-size:9px !important; 
				text-align:center; 
				width:100%;
				border: solid #000 !important;
				border-width: 1px 0 0 1px !important; }

.list01 th { background:#e2e9f5; 
			 height : 15px; 
			 margin:2px 5px;
			 padding:3px 0;	
			 color:#333;	
			 line-height:14px; 
			 text-align:center; 
			 border: solid #000 !important;
			 border-width: 0 1px 1px 0 !important; }
			
.list01 td {height:15px;
			padding:2px 5px; 
			border: solid #000 !important;
			border-width: 0 1px 1px 0 !important; }
			
.total_tr{ background-color:#FFDFDC !important;}
</style>

<form id="monthDiligenceReportForm" name="monthDiligenceReportForm" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/monthDiligencePrintPop.do'/>" >
<div id="wrap">
	
	<c:set var="totWktim" value="0"/>
	<c:set var="totOvtm1" value="0"/>
	<c:set var="totOvtm2" value="0"/>
	<c:set var="totNight" value="0"/>
	<c:set var="totOvnig" value="0"/>
	<c:set var="totWksun" value="0"/>
	<c:set var="totWkoff" value="0"/>
	<c:set var="totHolwk" value="0"/>
	<c:set var="totVacwk" value="0"/>
	<c:set var="totOvtim" value="0"/>
	
	<c:set var="totSumWktim" value="0"/>
	<c:set var="totSumOvtm1" value="0"/>
	<c:set var="totSumOvtm2" value="0"/>
	<c:set var="totSumNight" value="0"/>
	<c:set var="totSumOvnig" value="0"/>
	<c:set var="totSumWksun" value="0"/>
	<c:set var="totSumWkoff" value="0"/>
	<c:set var="totSumHolwk" value="0"/>
	<c:set var="totSumVacwk" value="0"/>
	<c:set var="totSumOvtim" value="0"/>
	
	<c:choose>
		<c:when test="${params.hidImTotal eq 'X' }">
			<div class="list01 a4">
				<p class="f_title" style="padding-bottom:10px">
					부서 : <c:out value="${etList[0].ORGTX }"/>&nbsp;&nbsp;&nbsp;&nbsp;
					조회기간 : <c:out value="${params.hidStartDate }"/> ~ <c:out value="${params.hidEndDate }"/>&nbsp;&nbsp;&nbsp;&nbsp;
					관리자 : <c:out value="${etList[0].ADMIN }"/>
				</p>
				
				<table border="0" cellpadding="0" cellspacing="0" class="pernrTab">
					<caption></caption>
					<thead>
						<tr>
							<th scope="col" width="45px">사원번호</th>
							<th scope="col" width="45px">성명</th>
							<th scope="col" width="*">부서명</th>
							<th scope="col" width="45px">근무시간</th>
							<th scope="col" width="45px">시간외I</th>
							
							<th scope="col" width="45px">시간외II</th>
							<th scope="col" width="45px">야간근로</th>
							<th scope="col" width="45px">야간초과</th>
							<th scope="col" width="55px">주휴(일요)</th>
							<th scope="col" width="55px">주휴(비번)</th>
							
							<th scope="col" width="45px">공휴근로</th>
							<th scope="col" width="45px">휴가근로</th>
							<th scope="col" width="55px">시간외수당</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${etList }" var="etListRow" varStatus="inx">
							<tr>
								<td><c:out value="${etListRow.PERNR}"/></td>
								<td><c:out value="${etListRow.ENAME}"/></td>
								<td><c:out value="${etListRow.ORGTX}"/></td>
								<td>
									<c:if test="${etListRow.WKTIM ne '0.00' }">
										<c:out value="${etListRow.WKTIM}"/>
										<c:set var="totWktim" value="${totWktim + etListRow.WKTIM}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.OVTM1 ne '0.00' }">
										<c:out value="${etListRow.OVTM1}"/>
										<c:set var="totOvtm1" value="${totOvtm1 + etListRow.OVTM1}"/>
									</c:if>
								</td>
								
								<td>
									<c:if test="${etListRow.OVTM2 ne '0.00' }">
										<c:out value="${etListRow.OVTM2}"/>
										<c:set var="totOvtm2" value="${totOvtm2 + etListRow.OVTM2}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.NIGHT ne '0.00' }">
										<c:out value="${etListRow.NIGHT}"/>
										<c:set var="totNight" value="${totNight + etListRow.NIGHT}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.OVNIG ne '0.00' }">
										<c:out value="${etListRow.OVNIG}"/>
										<c:set var="totOvnig" value="${totOvnig + etListRow.OVNIG}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.WKSUN ne '0.00' }">
										<c:out value="${etListRow.WKSUN}"/>
										<c:set var="totWksun" value="${totWksun + etListRow.WKSUN}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.WKOFF ne '0.00' }">
										<c:out value="${etListRow.WKOFF}"/>
										<c:set var="totWkoff" value="${totWkoff + etListRow.WKOFF}"/>
									</c:if>
								</td>
								
								<td>
									<c:if test="${etListRow.HOLWK ne '0.00' }">
										<c:out value="${etListRow.HOLWK}"/>
										<c:set var="totHolwk" value="${totHolwk + etListRow.HOLWK}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.VACWK ne '0.00' }">
										<c:out value="${etListRow.VACWK}"/>
										<c:set var="totVacwk" value="${totVacwk + etListRow.VACWK}"/>
									</c:if>
								</td>
								<td>
									<c:if test="${etListRow.OVTIM ne '0' }">
										<c:out value="${etListRow.OVTIM}"/>
										<c:set var="totOvtim" value="${totOvtim + etListRow.OVTIM}"/>
									</c:if>
								</td>
							</tr>
							<c:if test="${inx.last }">
								<tr class="total_tr">
									<td colspan="3">총  계</td>
									<td>
										<c:if test="${totWktim ne '0' }">
											<fmt:formatNumber value="${totWktim }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totOvtm1 ne '0' }">
											<fmt:formatNumber value="${totOvtm1 }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totOvtm2 ne '0' }">
											<fmt:formatNumber value="${totOvtm2 }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totNight ne '0' }">
											<fmt:formatNumber value="${totNight }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totOvnig ne '0' }">
											<fmt:formatNumber value="${totOvnig }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totWksun ne '0' }">
											<fmt:formatNumber value="${totWksun }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totWkoff ne '0' }">
											<fmt:formatNumber value="${totWkoff }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totHolwk ne '0' }">
											<fmt:formatNumber value="${totHolwk }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totVacwk ne '0' }">
											<fmt:formatNumber value="${totVacwk }" pattern="##0.00"/>
										</c:if>
									</td>
									<td>
										<c:if test="${totOvtim ne '0' }">
											<c:out value="${totOvtim}"/>
										</c:if>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach items="${etPernr }" var="etPernrRow" varStatus="inx">
				<div class="list01 a4">
					<p class="f_title" style="padding-bottom:10px">
						<c:forEach items="${etList }" var="etListRow">
							<c:if test="${etPernrRow.PERNR eq etListRow.PERNR && etListRow.DATUM eq fn:replace(params.hidEndDate, '-', '')}">
								<fmt:parseDate var="dateString" value="${etListRow.DATUM}" pattern="yyyyMMdd" />
								성명 : <c:out value="${etListRow.ENAME }"/>&nbsp;&nbsp;&nbsp;&nbsp;
								부서 : <c:out value="${etListRow.ORGTX }"/>&nbsp;&nbsp;&nbsp;&nbsp;
								연도 : <fmt:formatDate value="${dateString}" pattern="yyyy" />&nbsp;&nbsp;&nbsp;&nbsp;
								관리자 : <c:out value="${etListRow.ADMIN }"/>
							</c:if>
						</c:forEach>
					</p>
					<table border="0" cellpadding="0" cellspacing="0" class="pernrTab">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="40px">일자</th>
								<th scope="col" width="70px">근무형태</th>
								<th scope="col" width="70px">근무상태</th>
								<th scope="col" width="50px">근무시간</th>
								<th scope="col" width="50px">시간외I</th>
								<th scope="col" width="50px">시간외II</th>
								<th scope="col" width="50px">야간근로</th>
								<th scope="col" width="50px">야간초과</th>
								<th scope="col" width="50px">주휴(일요)</th>
								<th scope="col" width="50px">주휴(비번)</th>
								<th scope="col" width="50px">공휴근로</th>
								<th scope="col" width="50px">휴가근로</th>
								<th scope="col" width="*">시간외수당</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${etList }" var="etListRow">
								<c:if test="${etPernrRow.PERNR eq etListRow.PERNR }">
									<tr>
										<td>
											<fmt:parseDate var="dateString" value="${etListRow.DATUM}" pattern="yyyyMMdd" />
											<fmt:formatDate value="${dateString}" pattern="MM.dd" />
										</td>
										<td class="<c:out value="${etPernrRow.PERNR }"/>_TTEXT"><c:out value="${etListRow.TTEXT}"/></td>
										<td class="<c:out value="${etPernrRow.PERNR }"/>_ATEXT"><c:out value="${etListRow.ATEXT}"/></td>
										<td>
											<c:if test="${etListRow.WKTIM ne '0.00' }">
												<c:out value="${etListRow.WKTIM}"/>
												<c:set var="totWktim" value="${totWktim + etListRow.WKTIM}"/>
												<c:set var="totSumWktim" value="${totSumWktim + etListRow.WKTIM}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.OVTM1 ne '0.00' }">
												<c:out value="${etListRow.OVTM1}"/>
												<c:set var="totOvtm1" value="${totOvtm1 + etListRow.OVTM1}"/>
												<c:set var="totSumOvtm1" value="${totSumOvtm1 + etListRow.OVTM1}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.OVTM2 ne '0.00' }">
												<c:out value="${etListRow.OVTM2}"/>
												<c:set var="totOvtm2" value="${totOvtm2 + etListRow.OVTM2}"/>
												<c:set var="totSumOvtm2" value="${totSumOvtm2 + etListRow.OVTM2}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.NIGHT ne '0.00' }">
												<c:out value="${etListRow.NIGHT}"/>
												<c:set var="totNight" value="${totNight + etListRow.NIGHT}"/>
												<c:set var="totSumNight" value="${totSumNight + etListRow.NIGHT}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.OVNIG ne '0.00' }">
												<c:out value="${etListRow.OVNIG}"/>
												<c:set var="totOvnig" value="${totOvnig + etListRow.OVNIG}"/>
												<c:set var="totSumOvnig" value="${totSumOvnig + etListRow.OVNIG}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.WKSUN ne '0.00' }">
												<c:out value="${etListRow.WKSUN}"/>
												<c:set var="totWksun" value="${totWksun + etListRow.WKSUN}"/>
												<c:set var="totSumWksun" value="${totSumWksun + etListRow.WKSUN}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.WKOFF ne '0.00' }">
												<c:out value="${etListRow.WKOFF}"/>
												<c:set var="totWkoff" value="${totWkoff + etListRow.WKOFF}"/>
												<c:set var="totSumWkoff" value="${totSumWkoff + etListRow.WKOFF}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.HOLWK ne '0.00' }">
												<c:out value="${etListRow.HOLWK}"/>
												<c:set var="totHolwk" value="${totHolwk + etListRow.HOLWK}"/>
												<c:set var="totSumHolwk" value="${totSumHolwk + etListRow.HOLWK}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.VACWK ne '0.00' }">
												<c:out value="${etListRow.VACWK}"/>
												<c:set var="totVacwk" value="${totVacwk + etListRow.VACWK}"/>
												<c:set var="totSumVacwk" value="${totSumVacwk + etListRow.VACWK}"/>
											</c:if>
										</td>
										<td>
											<c:if test="${etListRow.OVTIM ne '0' }">
												<c:out value="${etListRow.OVTIM}"/>
												<c:set var="totOvtim" value="${totOvtim + etListRow.OVTIM}"/>
												<c:set var="totSumOvtim" value="${totSumOvtim + etListRow.OVTIM}"/>
											</c:if>
										</td>
									</tr>
									
									<c:if test="${etListRow.DATUM eq fn:replace(params.hidEndDate, '-', '') }">
										<tr class="total_tr">
											<td colspan="3">인  별  총  계</td>
											<td>
												<c:if test="${totWktim ne '0' }">
													<fmt:formatNumber value="${totWktim }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totOvtm1 ne '0' }">
													<fmt:formatNumber value="${totOvtm1 }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totOvtm2 ne '0' }">
													<fmt:formatNumber value="${totOvtm2 }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totNight ne '0' }">
													<fmt:formatNumber value="${totNight }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totOvnig ne '0' }">
													<fmt:formatNumber value="${totOvnig }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totWksun ne '0' }">
													<fmt:formatNumber value="${totWksun }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totWkoff ne '0' }">
													<fmt:formatNumber value="${totWkoff }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totHolwk ne '0' }">
													<fmt:formatNumber value="${totHolwk }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totVacwk ne '0' }">
													<fmt:formatNumber value="${totVacwk }" pattern="##0.00"/>
												</c:if>
											</td>
											<td>
												<c:if test="${totOvtim ne '0' }">
													<c:out value="${totOvtim}"/>
												</c:if>
											</td>
										</tr>
										
										<c:set var="totWktim" value="0"/>
										<c:set var="totOvtm1" value="0"/>
										<c:set var="totOvtm2" value="0"/>
										<c:set var="totNight" value="0"/>
										<c:set var="totOvnig" value="0"/>
										<c:set var="totWksun" value="0"/>
										<c:set var="totWkoff" value="0"/>
										<c:set var="totHolwk" value="0"/>
										<c:set var="totVacwk" value="0"/>
										<c:set var="totOvtim" value="0"/>
										
										<c:if test="${inx.last }">
											<tr class="total_tr">
												<td colspan="3">총  계</td>
												<td>
													<c:if test="${totSumWktim ne '0' }">
														<fmt:formatNumber value="${totSumWktim }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumOvtm1 ne '0' }">
														<fmt:formatNumber value="${totSumOvtm1 }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumOvtm2 ne '0' }">
														<fmt:formatNumber value="${totSumOvtm2 }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumNight ne '0' }">
														<fmt:formatNumber value="${totSumNight }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumOvnig ne '0' }">
														<fmt:formatNumber value="${totSumOvnig }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumWksun ne '0' }">
														<fmt:formatNumber value="${totSumWksun }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumWkoff ne '0' }">
														<fmt:formatNumber value="${totSumWkoff }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumHolwk ne '0' }">
														<fmt:formatNumber value="${totSumHolwk }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumVacwk ne '0' }">
														<fmt:formatNumber value="${totSumVacwk }" pattern="##0.00"/>
													</c:if>
												</td>
												<td>
													<c:if test="${totSumOvtim ne '0' }">
														<c:out value="${totSumOvtim}"/>
													</c:if>
												</td>
											</tr>
										</c:if>
									</c:if>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	
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
