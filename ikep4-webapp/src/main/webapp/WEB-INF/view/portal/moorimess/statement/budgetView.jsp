<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>
<c:forEach var="profitLoss" items="${profitLossList}" varStatus="pcnt">
<c:set var="pfcnt" value="${pcnt.count-2}"/>
</c:forEach>
<c:forEach var="condition" items="${conditionList}" varStatus="ccnt">
<c:set var="ctcnt" value="${ccnt.count-2}"/>
</c:forEach>
<c:forEach var="rate" items="${rateList}" varStatus="rcnt">
<c:set var="rtcnt" value="${rcnt.count-2}"/>
</c:forEach>

<script type="text/javascript">
function customerGradePopup(){
	 var url = "<c:url value='/support/customer/customerFinance/popupCustomerGrade.do'/>";
	 window.open(url,'customerGrade','width=800,height=490,scrollbars=1,toolbar=0'); 
	
}

function goDelete(){
	
	if(confirm("고객정보를 삭제하시겠습니까?")){
		location.href = "<c:url value='/support/customer/customerFinance/deleteFinance.do?id=${finance.id}'/>";
	}
}

(function($) {
	$(document).ready(function() {
		

$("#createPrintButton").click(function() {			
	var url = iKEP.getContextRoot() + '/support/customer/customerFinance/detailFinancePrint.do?id=${finance.id}';
	iKEP.popupOpen(url, {width:800, height:560}, "ItemPrint");
});	
	});
})(jQuery);
</script>
<c:set var="standardYear" value="${startDt}" />
<c:set var="endYear" value="${endDt}" />
<input type="hidden" name="stYear" id="stYear" value="${standardYear}" />
<input type="hidden" name="edYear" id="edYear" value="${endYear}" />

	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>실적금액 상세조회</h2>		
					<!-- <div class="tableSearch">
						<select title="검색조건" name="tablesch_01">
							<option value="">고객</option>
                            <option value="">담당자</option>
						</select>													
						<input type="text" class="inputbox" title="inputbox" name="" value="" size="20" />
                       <a class="ic_search" id="searchBoardItemButton"  href="#a"><span>검색</span></a>                  
					</div> -->	
					<div class="clear"></div>
				</div>
				<span style="float:right;font-size:10px;">(단위 : 원)</span>
	 			<!--//tableTop End-->
				<div class="blockButton"> 
                <ul>           
				</ul>
				</div>
				<div class="clear"></div> 
				
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
						    <table style="text-align:center;font-size:11px;">
						        <caption></caption>
						        <colgroup>
						            <col width="8%"/>
						            <col width="10%"/>
						            <col width="10%"/>
						            <col width="10%"/>
						            <col width="15%"/>
						            <col width="8%"/>
						            <col width="17%"/>
						            <col width="16%"/>
						            <col width="7%"/>
						        </colgroup>
						        <tbody>
						        	<tr>
						        		<th style="text-align:center;">전기일</th>
						        		<th style="text-align:center;">전표번호</th>
						        		<th style="text-align:center;">예산신청부서</th>
						        		<th style="text-align:center;">예산사용부서</th>
						        		<th style="text-align:center;">계정과목</th>
						        		<th style="text-align:center;">금액</th>
						        		<th style="text-align:center;">거래처</th>
						        		<th style="text-align:center;">전표적요</th>
						        		<th style="text-align:center;">작성자</th>
						        	</tr>
						        	<c:choose>
						        		<c:when test="${empty statementList}">
											<tr>
												<td colspan="9" class="emptyRecord">예산 실적/계획이 없습니다.</td>
											</tr>
										</c:when>
						        		<c:otherwise>
						        			<c:forEach var="item" items="${statementList}" varStatus="status">
						        				<tr>
									        		<td>${item.budat}</td>
									        		<td>${item.docnr}</td>
									        		<td>${item.rzkostlt}</td>
									        		<td>${item.rcntrt}</td>
									        		<td style="text-align:left;">${item.racctt}</td>
									        		<td style="text-align:right;">
									        			<fmt:formatNumber value="${item.hsl}" type="number" />
									        		</td>
									        		<td style="text-align:left;">${item.zuonr}
													<c:if test="${item.zterm == 'FB00'}">
														&nbsp;- 현금지급
													</c:if>
													<c:if test="${item.zterm != 'FB00'}">
													
													</c:if>
													</td>
									        		<td style="text-align:left;">${item.sgtxt}</td>
									        		<td>${item.usnamt}</td>
									        	</tr>
						        			</c:forEach>
						        		</c:otherwise>
						        	</c:choose>
						        </tbody>
						    </table>
						</div>
				<!--//blockDetail End-->
				
						</div>
                
                        		