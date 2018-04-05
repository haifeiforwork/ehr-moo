<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>
<c:forEach var="statement1" items="${statementList1}" varStatus="status">
<c:set var="statementCnt1" value="${status.count}" />
</c:forEach>
<c:forEach var="statement2" items="${statementList2}" varStatus="status">
<c:set var="statementCnt2" value="${status.count}" />
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

	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>전표 내역 상세 조회</h2>		
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
				
				<div class="blockDetail">
						    <table>
						        <caption></caption>
						        <colgroup>
						            <col width="15%"/>
						            <col width="15%"/>
						            <col width="15%"/>
						            <col width="15%"/>
						            <col width="15%"/>
						            <col width="15%"/>
						        </colgroup>
						        <tbody>
						        	<tr>
						        		<th style="text-align:center;">법인카드번호</th>
						        		<td style="text-align:center;">${cardno}</td>
						        		<th style="text-align:center;">카드담당자</th>
						        		<td style="text-align:center;">${reqnamt}</td>
						        		<th style="text-align:center;">카드총액</th>
						        		<td style="text-align:right;"><fmt:formatNumber value="${dmbtr}" /></td>
						        	</tr>
						        </tbody>
						    </table>
						</div>
                
                <!--blockDetail Start-->
                <div class="blockDetail">
						    <table>
						        <caption></caption>
						        <colgroup>
						            <col width="5%"/>
						            <col width="5%"/>
						            <col width="8%"/>
						            <col width="20%"/>
						            <col width="10%"/>
						            <col width="10%"/>
						            <col width="8%"/>
						            <col width="17%"/>
						            <col width="17%"/>
						        </colgroup>
						        <tbody>
						        	<c:forEach var="statement1" items="${statementList1}" varStatus="status">
						        	<c:if test="${status.index==0}">
						        	<tr>
					        			<th style="text-align:center;" colspan="2">전표번호</th>
					        			<td style="text-align:left;" colspan="7">${statement1.belnr}</td>
						        	</tr>
						        	<tr>
						        		<th style="text-align:center;">번호</th>
						        		<th style="text-align:center;">유형</th>
						        		<th style="text-align:center;">계정번호</th>
						        		<th style="text-align:center;">계정과목</th>
						        		<th style="text-align:center;">차변금액</th>
						        		<th style="text-align:center;">대변금액</th>
						        		<th style="text-align:center;">세금코드</th>
						        		<th style="text-align:center;">거래처</th>
						        		<th style="text-align:center;">전표적요</th>
						        	</tr>
						        	</c:if>
						        	<tr style="text-align:center;">
						        		<td>${statement1.buzei}</td>
						        		<td>
						        			<c:if test="${statement1.shkzg=='H'}">
						        				대변
						        			</c:if>
						        			<c:if test="${statement1.shkzg=='S'}">
						        				차변
						        			</c:if>
						        		</td>
						        		<td><fmt:parseNumber value="${statement1.hkont}" /></td>
						        		<td style="text-align:left;">${statement1.zztxt50}</td>
						        		<td style="text-align:right;">
						        			<c:if test="${statement1.shkzg=='S'}">
						        				<fmt:formatNumber value="${statement1.dmbtr}" />
						        			</c:if>
						        		</td>
						        		<td style="text-align:right;">
						        			<c:if test="${statement1.shkzg=='H'}">
						        				<fmt:formatNumber value="${statement1.dmbtr}" />
						        			</c:if>
						        		</td>
						        		<td>${statement1.mwskz}</td>
						        		<td>${statement1.zuonr}</td>
						        		<td style="text-align:left;">${statement1.sgtxt}</td>
						        	</tr>
						        	<c:if test="${status.index+1==statementCnt1}">
						        	<tr style="text-align:center;">
						        		<td style="background-color:silver;" colspan="4">합계</td>
						        		<td style="text-align:right;background-color:silver;"><fmt:formatNumber value="${statement1.dmbtrs}" /></td>
						        		<td style="text-align:right;background-color:silver;"><fmt:formatNumber value="${statement1.dmbtrh}" /></td>
						        		<td style="background-color:silver;"></td>
						        		<td style="background-color:silver;"></td>
						        		<td style="background-color:silver;"></td>
						        	</tr>
						        	</c:if>
						        	</c:forEach>
						        	<c:forEach var="statement2" items="${statementList2}" varStatus="status">
						        	<c:if test="${status.index==0}">
						        	<tr>
					        			<th style="text-align:center;" colspan="2">전표번호</th>
					        			<td style="text-align:left;" colspan="7">${statement2.belnr}</td>
						        	</tr>
						        	<tr>
						        		<th style="text-align:center;">번호</th>
						        		<th style="text-align:center;">유형</th>
						        		<th style="text-align:center;">계정번호</th>
						        		<th style="text-align:center;">계정과목</th>
						        		<th style="text-align:center;">차변금액</th>
						        		<th style="text-align:center;">대변금액</th>
						        		<th style="text-align:center;">세금코드</th>
						        		<th style="text-align:center;">거래처</th>
						        		<th style="text-align:center;">적요</th>
						        	</tr>
						        	</c:if>
						        	<tr style="text-align:center;">
						        		<td>${statement2.buzei}</td>
						        		<td>
						        			<c:if test="${statement2.shkzg=='H'}">
						        				대변
						        			</c:if>
						        			<c:if test="${statement2.shkzg=='S'}">
						        				차변
						        			</c:if>
						        		</td>
						        		<td><fmt:parseNumber value="${statement2.hkont}" /></td>
						        		<td style="text-align:left;">${statement2.zztxt50}</td>
						        		<td style="text-align:right;">
						        			<c:if test="${statement2.shkzg=='S'}">
						        				<fmt:formatNumber value="${statement2.dmbtr}" />
						        			</c:if>
						        		</td>
						        		<td style="text-align:right;">
						        			<c:if test="${statement2.shkzg=='H'}">
						        				<fmt:formatNumber value="${statement2.dmbtr}" />
						        			</c:if>
						        		</td>
						        		<td>${statement2.mwskz}</td>
						        		<td>${statement2.zuonr}</td>
						        		<td style="text-align:left;">${statement2.sgtxt}</td>
						        	</tr>
						        	<c:if test="${status.index+1==statementCnt2}">
						        	<tr style="text-align:center;">
						        		<td style="background-color:silver;" colspan="4">합계</td>
						        		<td style="text-align:right;background-color:silver;"><fmt:formatNumber value="${statement2.dmbtrs}" /></td>
						        		<td style="text-align:right;background-color:silver;"><fmt:formatNumber value="${statement2.dmbtrh}" /></td>
						        		<td style="background-color:silver;"></td>
						        		<td style="background-color:silver;"></td>
						        		<td style="background-color:silver;"></td>
						        	</tr>
						        	</c:if>
						        	</c:forEach>
						        </tbody>
						    </table>
						</div>
						&nbsp;주) 세금코드 : C2 기타매입증빙 (0%), A3 매입부가세-신용카드 (10%)
				<!--//blockDetail End-->
				
						</div>
                
                        		