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
					<h2>전표 사용/승인 내역 상세 조회</h2>		
					<div class="clear"></div>
				</div>
	 			<!--//tableTop End-->
				<div class="blockButton"> 
                <ul>           
				</ul>
				</div>
				<div class="clear"></div> 

                
                <div class="blockBlank_10px"></div>		
				
				
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
						        		<td style="text-align:center;">4265869107146610</td>
						        		<th style="text-align:center;">카드담당자</th>
						        		<td style="text-align:center;">정대진</td>
						        		<th style="text-align:center;">카드총액</th>
						        		<td style="text-align:right;">76,000</td>
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
						            <col width="10%"/>
						            <col width="15%"/>
						            <col width="20%"/>
						            <col width="10%"/>
						            <col width="10%"/>
						            <col width="10%"/>
						            <col width="20%"/>
						        </colgroup>
						        <tbody>
						        	<tr>
					        			<th style="text-align:center;" colspan="2">전표번호</th>
					        			<td style="text-align:left;" colspan="6">6100001650</td>
						        	</tr>
						        	<tr>
						        		<th style="text-align:center;">번호</th>
						        		<th style="text-align:center;">유형</th>
						        		<th style="text-align:center;">계정번호</th>
						        		<th style="text-align:center;">계정과목</th>
						        		<th style="text-align:center;">차변금액</th>
						        		<th style="text-align:center;">대변금액</th>
						        		<th style="text-align:center;">세금코드</th>
						        		<th style="text-align:center;">적요</th>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td>1</td>
						        		<td>
						        			차변
						        		</td>
						        		<td>53313301</td>
						        		<td>(일)회의비-일반회의비</td>
						        		<td style="text-align:right;">
						        			62,182
						        		</td>
						        		<td style="text-align:right;">
						        			-
						        		</td>
						        		<td>A3</td>
						        		<td>회의식대</td>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td>2</td>
						        		<td>
						        			대변
						        		</td>
						        		<td>21113101</td>
						        		<td>미지급금-원화</td>
						        		<td style="text-align:right;">
						        			-
						        		</td>
						        		<td style="text-align:right;">
						        			68,400
						        		</td>
						        		<td>A3</td>
						        		<td>회의식대</td>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td>3</td>
						        		<td>
						        			차변
						        		</td>
						        		<td>11121101</td>
						        		<td>부과세대급금</td>
						        		<td style="text-align:right;">
						        			6,218
						        		</td>
						        		<td style="text-align:right;">
						        			-
						        		</td>
						        		<td>A3</td>
						        		<td>회의식대</td>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td style="background-color:silver;" colspan="4">합계</td>
						        		<td style="text-align:right;background-color:silver;">68,400</td>
						        		<td style="text-align:right;background-color:silver;">68,400</td>
						        		<td style="background-color:silver;"></td>
						        		<td style="background-color:silver;"></td>
						        	</tr>
						        	<tr>
					        			<th style="text-align:center;" colspan="2">전표번호</th>
					        			<td style="text-align:left;" colspan="6">6100001651</td>
						        	</tr>
						        	<tr>
						        		<th style="text-align:center;">번호</th>
						        		<th style="text-align:center;">유형</th>
						        		<th style="text-align:center;">계정번호</th>
						        		<th style="text-align:center;">계정과목</th>
						        		<th style="text-align:center;">차변금액</th>
						        		<th style="text-align:center;">대변금액</th>
						        		<th style="text-align:center;">세금코드</th>
						        		<th style="text-align:center;">적요</th>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td>1</td>
						        		<td>
						        			차변
						        		</td>
						        		<td>53313301</td>
						        		<td>(일)회의비-일반회의비</td>
						        		<td style="text-align:right;">
						        			7,600
						        		</td>
						        		<td style="text-align:right;">
						        			-
						        		</td>
						        		<td>C2</td>
						        		<td>회의식대</td>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td>3</td>
						        		<td>
						        			대변
						        		</td>
						        		<td>21113101</td>
						        		<td>미지급금-원화</td>
						        		<td style="text-align:right;">
						        			-
						        		</td>
						        		<td style="text-align:right;">
						        			7,600
						        		</td>
						        		<td>C2</td>
						        		<td>회의식대</td>
						        	</tr>
						        	<tr style="text-align:center;">
						        		<td style="background-color:silver;" colspan="4">합계</td>
						        		<td style="text-align:right;background-color:silver;">7,600</td>
						        		<td style="text-align:right;background-color:silver;">7,600</td>
						        		<td style="background-color:silver;"></td>
						        		<td style="background-color:silver;"></td>
						        	</tr>
						        </tbody>
						    </table>
						</div>
				<!--//blockDetail End-->
				
						</div>
                
                        		