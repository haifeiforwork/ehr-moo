<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<%pageContext.setAttribute("crlf", "\r\n"); %>
<%pageContext.setAttribute("nbsp", " "); %>

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
		var eyear = $("#edYear").val();
		<c:forEach var="profitLoss" items="${profitLossList}">
		if("${profitLoss.divInfo}" == ""){
			if("${profitLoss.yearInfo}" > eyear){
				$(".yearPlus").show();
	    		$(".yearMoreOff").hide();
	    		$(".yearMoreOn").show();
	    		iKEP.iFrameContentResize();
			}
			$jq("#mnt1_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt1}' />");
			$jq("#mnt2_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt2}' />");
			$jq("#mnt3_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt3}' />");
			$jq("#mnt4_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt4}' />");
			$jq("#mnt5_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt5}' />");
			$jq("#mnt6_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt6}' />");
			$jq("#mnt7_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt7}' />");
			$jq("#mnt8_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt8}' />");
			$jq("#mnt9_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt9}' />");
			$jq("#mnt10_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt10}' />");
			$jq("#mnt11_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt11}' />");
			$jq("#mnt12_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt12}' />");
			$jq("#mnt13_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt13}' />");
			$jq("#mnt14_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt14}' />");
			$jq("#mnt15_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt15}' />");
			$jq("#mnt16_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt16}' />");
			$jq("#mnt17_${profitLoss.yearInfo}").append("<fmt:formatNumber value='${profitLoss.mnt17}' />");
		}else if("${profitLoss.divInfo}" == "mount"){
			$jq("#varMnt1").append("<fmt:formatNumber value='${profitLoss.mnt1}' />");
			$jq("#varMnt2").append("<fmt:formatNumber value='${profitLoss.mnt2}' />");
			$jq("#varMnt3").append("<fmt:formatNumber value='${profitLoss.mnt3}' />");
			$jq("#varMnt4").append("<fmt:formatNumber value='${profitLoss.mnt4}' />");
			$jq("#varMnt5").append("<fmt:formatNumber value='${profitLoss.mnt5}' />");
			$jq("#varMnt6").append("<fmt:formatNumber value='${profitLoss.mnt6}' />");
			$jq("#varMnt7").append("<fmt:formatNumber value='${profitLoss.mnt7}' />");
			$jq("#varMnt8").append("<fmt:formatNumber value='${profitLoss.mnt8}' />");
			$jq("#varMnt9").append("<fmt:formatNumber value='${profitLoss.mnt9}' />");
			$jq("#varMnt10").append("<fmt:formatNumber value='${profitLoss.mnt10}' />");
			$jq("#varMnt11").append("<fmt:formatNumber value='${profitLoss.mnt11}' />");
			$jq("#varMnt12").append("<fmt:formatNumber value='${profitLoss.mnt12}' />");
			$jq("#varMnt13").append("<fmt:formatNumber value='${profitLoss.mnt13}' />");
			$jq("#varMnt14").append("<fmt:formatNumber value='${profitLoss.mnt14}' />");
			$jq("#varMnt15").append("<fmt:formatNumber value='${profitLoss.mnt15}' />");
			$jq("#varMnt16").append("<fmt:formatNumber value='${profitLoss.mnt16}' />");
			$jq("#varMnt17").append("<fmt:formatNumber value='${profitLoss.mnt17}' />");
		}else if("${profitLoss.divInfo}" == "rate"){
			$jq("#varPer1").append("<fmt:formatNumber value='${profitLoss.mnt1}' />");
			$jq("#varPer2").append("<fmt:formatNumber value='${profitLoss.mnt2}' />");
			$jq("#varPer3").append("<fmt:formatNumber value='${profitLoss.mnt3}' />");
			$jq("#varPer4").append("<fmt:formatNumber value='${profitLoss.mnt4}' />");
			$jq("#varPer5").append("<fmt:formatNumber value='${profitLoss.mnt5}' />");
			$jq("#varPer6").append("<fmt:formatNumber value='${profitLoss.mnt6}' />");
			$jq("#varPer7").append("<fmt:formatNumber value='${profitLoss.mnt7}' />");
			$jq("#varPer8").append("<fmt:formatNumber value='${profitLoss.mnt8}' />");
			$jq("#varPer9").append("<fmt:formatNumber value='${profitLoss.mnt9}' />");
			$jq("#varPer10").append("<fmt:formatNumber value='${profitLoss.mnt10}' />");
			$jq("#varPer11").append("<fmt:formatNumber value='${profitLoss.mnt11}' />");
			$jq("#varPer12").append("<fmt:formatNumber value='${profitLoss.mnt12}' />");
			$jq("#varPer13").append("<fmt:formatNumber value='${profitLoss.mnt13}' />");
			$jq("#varPer14").append("<fmt:formatNumber value='${profitLoss.mnt14}' />");
			$jq("#varPer15").append("<fmt:formatNumber value='${profitLoss.mnt15}' />");
			$jq("#varPer16").append("<fmt:formatNumber value='${profitLoss.mnt16}' />");
			$jq("#varPer17").append("<fmt:formatNumber value='${profitLoss.mnt17}' />");		
		}
	</c:forEach>
	<c:forEach var="condition" items="${conditionList}">
	if("${condition.divInfo}" == ""){
		if("${condition.yearInfo}" > eyear){
			$(".yearPlus").show();
    		$(".yearMoreOff").hide();
    		$(".yearMoreOn").show();
    		iKEP.iFrameContentResize();
		}
		$jq("#mntB1_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt1}' />");
		$jq("#mntB2_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt2}' />");
		$jq("#mntB3_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt3}' />");
		$jq("#mntB4_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt4}' />");
		$jq("#mntB5_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt5}' />");
		$jq("#mntB6_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt6}' />");
		$jq("#mntB7_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt7}' />");
		$jq("#mntB8_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt8}' />");
		$jq("#mntB9_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt9}' />");
		$jq("#mntB10_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt10}' />");
		$jq("#mntB11_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt11}' />");
		$jq("#mntB12_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt12}' />");
		$jq("#mntB13_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt13}' />");
		$jq("#mntB14_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt14}' />");
		$jq("#mntB15_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt15}' />");
		$jq("#mntB16_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt16}' />");
		$jq("#mntB17_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt17}' />");
		$jq("#mntB18_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt18}' />");
		$jq("#mntB19_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt19}' />");
		$jq("#mntB20_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt20}' />");
		$jq("#mntB21_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt21}' />");
		$jq("#mntB22_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt22}' />");
		$jq("#mntB23_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt23}' />");
		$jq("#mntB24_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt24}' />");
		$jq("#mntB25_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt25}' />");
		$jq("#mntB26_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt26}' />");
		$jq("#mntB27_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt27}' />");
		$jq("#mntB28_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt28}' />");
		$jq("#mntB29_${condition.yearInfo}").append("<fmt:formatNumber value='${condition.mnt29}' />");
	}else if("${condition.divInfo}" == "mount"){
		$jq("#varMntB1").append("<fmt:formatNumber value='${condition.mnt1}' />");
		$jq("#varMntB2").append("<fmt:formatNumber value='${condition.mnt2}' />");
		$jq("#varMntB3").append("<fmt:formatNumber value='${condition.mnt3}' />");
		$jq("#varMntB4").append("<fmt:formatNumber value='${condition.mnt4}' />");
		$jq("#varMntB5").append("<fmt:formatNumber value='${condition.mnt5}' />");
		$jq("#varMntB6").append("<fmt:formatNumber value='${condition.mnt6}' />");
		$jq("#varMntB7").append("<fmt:formatNumber value='${condition.mnt7}' />");
		$jq("#varMntB8").append("<fmt:formatNumber value='${condition.mnt8}' />");
		$jq("#varMntB9").append("<fmt:formatNumber value='${condition.mnt9}' />");
		$jq("#varMntB10").append("<fmt:formatNumber value='${condition.mnt10}' />");
		$jq("#varMntB11").append("<fmt:formatNumber value='${condition.mnt11}' />");
		$jq("#varMntB12").append("<fmt:formatNumber value='${condition.mnt12}' />");
		$jq("#varMntB13").append("<fmt:formatNumber value='${condition.mnt13}' />");
		$jq("#varMntB14").append("<fmt:formatNumber value='${condition.mnt14}' />");
		$jq("#varMntB15").append("<fmt:formatNumber value='${condition.mnt15}' />");
		$jq("#varMntB16").append("<fmt:formatNumber value='${condition.mnt16}' />");
		$jq("#varMntB17").append("<fmt:formatNumber value='${condition.mnt17}' />");
		$jq("#varMntB18").append("<fmt:formatNumber value='${condition.mnt18}' />");
		$jq("#varMntB19").append("<fmt:formatNumber value='${condition.mnt19}' />");
		$jq("#varMntB20").append("<fmt:formatNumber value='${condition.mnt20}' />");
		$jq("#varMntB21").append("<fmt:formatNumber value='${condition.mnt21}' />");
		$jq("#varMntB22").append("<fmt:formatNumber value='${condition.mnt22}' />");
		$jq("#varMntB23").append("<fmt:formatNumber value='${condition.mnt23}' />");
		$jq("#varMntB24").append("<fmt:formatNumber value='${condition.mnt24}' />");
		$jq("#varMntB25").append("<fmt:formatNumber value='${condition.mnt25}' />");
		$jq("#varMntB26").append("<fmt:formatNumber value='${condition.mnt26}' />");
		$jq("#varMntB27").append("<fmt:formatNumber value='${condition.mnt27}' />");
		$jq("#varMntB28").append("<fmt:formatNumber value='${condition.mnt28}' />");
		$jq("#varMntB29").append("<fmt:formatNumber value='${condition.mnt29}' />");
	}else if("${condition.divInfo}" == "rate"){
		$jq("#varPerB1").append("<fmt:formatNumber value='${condition.mnt1}' />");
		$jq("#varPerB2").append("<fmt:formatNumber value='${condition.mnt2}' />");
		$jq("#varPerB3").append("<fmt:formatNumber value='${condition.mnt3}' />");
		$jq("#varPerB4").append("<fmt:formatNumber value='${condition.mnt4}' />");
		$jq("#varPerB5").append("<fmt:formatNumber value='${condition.mnt5}' />");
		$jq("#varPerB6").append("<fmt:formatNumber value='${condition.mnt6}' />");
		$jq("#varPerB7").append("<fmt:formatNumber value='${condition.mnt7}' />");
		$jq("#varPerB8").append("<fmt:formatNumber value='${condition.mnt8}' />");
		$jq("#varPerB9").append("<fmt:formatNumber value='${condition.mnt9}' />");
		$jq("#varPerB10").append("<fmt:formatNumber value='${condition.mnt10}' />");
		$jq("#varPerB11").append("<fmt:formatNumber value='${condition.mnt11}' />");
		$jq("#varPerB12").append("<fmt:formatNumber value='${condition.mnt12}' />");
		$jq("#varPerB13").append("<fmt:formatNumber value='${condition.mnt13}' />");
		$jq("#varPerB14").append("<fmt:formatNumber value='${condition.mnt14}' />");
		$jq("#varPerB15").append("<fmt:formatNumber value='${condition.mnt15}' />");
		$jq("#varPerB16").append("<fmt:formatNumber value='${condition.mnt16}' />");
		$jq("#varPerB17").append("<fmt:formatNumber value='${condition.mnt17}' />");		
		$jq("#varPerB18").append("<fmt:formatNumber value='${condition.mnt18}' />");
		$jq("#varPerB19").append("<fmt:formatNumber value='${condition.mnt19}' />");
		$jq("#varPerB20").append("<fmt:formatNumber value='${condition.mnt20}' />");
		$jq("#varPerB21").append("<fmt:formatNumber value='${condition.mnt21}' />");
		$jq("#varPerB22").append("<fmt:formatNumber value='${condition.mnt22}' />");	
		$jq("#varPerB23").append("<fmt:formatNumber value='${condition.mnt23}' />");
		$jq("#varPerB24").append("<fmt:formatNumber value='${condition.mnt24}' />");
		$jq("#varPerB25").append("<fmt:formatNumber value='${condition.mnt25}' />");
		$jq("#varPerB26").append("<fmt:formatNumber value='${condition.mnt26}' />");
		$jq("#varPerB27").append("<fmt:formatNumber value='${condition.mnt27}' />");	
		$jq("#varPerB28").append("<fmt:formatNumber value='${condition.mnt28}' />");	
		$jq("#varPerB29").append("<fmt:formatNumber value='${condition.mnt29}' />");
	}
</c:forEach>
<c:forEach var="rate" items="${rateList}">
	if("${rate.divInfo}" == ""){
		if("${rate.yearInfo}" > eyear){
			$(".yearPlus").show();
    		$(".yearMoreOff").hide();
    		$(".yearMoreOn").show();
    		iKEP.iFrameContentResize();
		}
		$jq("#mntC1_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt1}' />");
		$jq("#mntC2_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt2}' />");
		$jq("#mntC3_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt3}' />");
		$jq("#mntC4_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt4}' />");
		$jq("#mntC5_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt5}' />");
		$jq("#mntC6_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt6}' />");
		$jq("#mntC7_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt7}' />");
		$jq("#mntC8_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt8}' />");
		$jq("#mntC9_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt9}' />");
		$jq("#mntC10_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt10}' />");
		$jq("#mntC11_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt11}' />");
		$jq("#mntC12_${rate.yearInfo}").append("<fmt:formatNumber value='${rate.mnt12}' />");
	}else if("${rate.divInfo}" == "rate"){
		$jq("#varPerC1").append("<fmt:formatNumber value='${rate.mnt1}' />");
		$jq("#varPerC2").append("<fmt:formatNumber value='${rate.mnt2}' />");
		$jq("#varPerC3").append("<fmt:formatNumber value='${rate.mnt3}' />");
		$jq("#varPerC4").append("<fmt:formatNumber value='${rate.mnt4}' />");
		$jq("#varPerC5").append("<fmt:formatNumber value='${rate.mnt5}' />");
		$jq("#varPerC6").append("<fmt:formatNumber value='${rate.mnt6}' />");
		$jq("#varPerC7").append("<fmt:formatNumber value='${rate.mnt7}' />");
		$jq("#varPerC8").append("<fmt:formatNumber value='${rate.mnt8}' />");
		$jq("#varPerC9").append("<fmt:formatNumber value='${rate.mnt9}' />");
		$jq("#varPerC10").append("<fmt:formatNumber value='${rate.mnt10}' />");
		$jq("#varPerC11").append("<fmt:formatNumber value='${rate.mnt11}' />");
		$jq("#varPerC12").append("<fmt:formatNumber value='${rate.mnt12}' />");
	}
</c:forEach>
printWindow();
	});
})(jQuery);

function printWindow() 
{
	//factory.printing.paperSize = "A4"
    factory.printing.header = "" //머릿글
	factory.printing.footer = "" //바닥글
	factory.printing.portrait = true //true는 세로 출력, false는 가로 출력
	factory.printing.leftMargin = 5.33 //왼쪽 여백
	factory.printing.topMargin = 5.23 //위쪽 여백
	factory.printing.rightMargin = 5.33 //오른쪽 여백
	factory.printing.bottomMargin = 4.23 //바닥 여백	
	
	factory.printing.Preview();
	setTimeout("javascript:iKEP.closePop()", 100);

}	
</script>
<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>
<c:set var="standardYear" value="${startDt}" />
<c:set var="endYear" value="${endDt}" />
<input type="hidden" name="stYear" id="stYear" value="${standardYear}" />
<input type="hidden" name="edYear" id="edYear" value="${endYear}" />

	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>재무/손익 정보</h2>		
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
	 			<!--//tableTop End-->
				<div class="clear"></div> 
                <!--subTitle_1 Start-->
				<!--//subTitle_1 End-->	

				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="">
						<caption></caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="35%"/>
                            <col width="15%"/>
                            <col width="35%"/>
                        </colgroup>
						<tbody>
               		  		<tr>
								<th scope="row" style="text-align:center;">고객명</th>
								<td colspan="3">${finance.name}</td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                
                <div class="blockBlank_10px"></div>		
				
				<!--subTitle_2 Start-->
				<table width="100%">
					<tr>
						<td width="80%">
							<div class="subTitle_2 noline">
								<h3>1.손익현황</h3>
							</div>
						</td>
						<td width="20%" style="text-align:right;"><font color="red"><strong>(단위 : 백만원)</strong></font></td>
					</tr>
				</table>
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
						    <table summary="new group">
						        <caption></caption>
						        <colgroup>
						            <col width="15%"/>
						            <c:forEach begin="1" end="${endYear-standardYear+1}">
						            	<col width=""/>
						            </c:forEach>
						            <col width="" class="yearPlus" style="display:none;"/>
						            <col width=""/>
						            <col width=""/>
						        </colgroup>
						        <tbody>
						            <tr>
						            	<th style="text-align:center;" rowspan="2">항목</th>
						            	<th style="text-align:center;" colspan="${endYear-standardYear+3}" id="yearMoreOff" class="yearMoreOff">년도</th>
						            	<th colspan="${endYear-standardYear+4}" id="yearMoreOn" class="yearMoreOn" style="display:none;text-align:center;">년도</th>
						            </tr>
						            <tr>
						            	<th style="text-align:center;">${standardYear}년</th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th style="text-align:center;">${standardYear+loop.count}년</th>
						            	</c:forEach>
						            	
						            	<th class="yearPlus" style="display:none;text-align:center;">${endYear+1}년</th>
						            	<th style="text-align:center;">증감액</th>
						            	<th style="text-align:center;">증감율</th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출액</th>
						            	<td name="mnt1_${standardYear}" id="mnt1_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mnt1_${standardYear+loop.count}" id="mnt1_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;text-align:right" name="mnt1_${endYear+1}" id="mnt1_${endYear+1}"></td>
						            	<td name="varMnt1" id="varMnt1" style="text-align:right"></td>
						            	<td name="varPer1" id="varPer1" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">매출원가</th>
						            	<td name="mnt2_${standardYear}" id="mnt2_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mnt2_${standardYear+loop.count}" id="mnt2_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;text-align:right" name="mnt2_${endYear+1}" id="mnt2_${endYear+1}"></td>
						            	<td name="varMnt2" id="varMnt2" style="text-align:right"></td>
						            	<td name="varPer2" id="varPer2" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출총이익</th>
						            	<td name="mnt3_${standardYear}" id="mnt3_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mnt3_${standardYear+loop.count}" id="mnt3_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;text-align:right" name="mnt3_${endYear+1}" id="mnt3_${endYear+1}"></td>
						            	<td name="varMnt3" id="varMnt3" style="text-align:right"></td>
						            	<td name="varPer3" id="varPer3" style="text-align:right"></td>
						            </tr>
						                <tr>
											<th style="text-align:left;background-color:white;">판매관리비</th>
											<td name="mnt4_${standardYear}" id="mnt4_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt4_${standardYear+loop.count}" id="mnt4_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt4_${endYear+1}" id="mnt4_${endYear+1}"></td>
											<td name="varMnt4" id="varMnt4" style="text-align:right"></td>
											<td name="varPer4" id="varPer4" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;인건비</th>
											<td name="mnt5_${standardYear}" id="mnt5_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt5_${standardYear+loop.count}" id="mnt5_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt5_${endYear+1}" id="mnt5_${endYear+1}"></td>
											<td name="varMnt5" id="varMnt5" style="text-align:right"></td>
											<td name="varPer5" id="varPer5" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;대손상각비</th>
											<td name="mnt6_${standardYear}" id="mnt6_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt6_${standardYear+loop.count}" id="mnt6_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt6_${endYear+1}" id="mnt6_${endYear+1}"></td>
											<td name="varMnt6" id="varMnt6" style="text-align:right"></td>
											<td name="varPer6" id="varPer6" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
											<td name="mnt7_${standardYear}" id="mnt7_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt7_${standardYear+loop.count}" id="mnt7_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt7_${endYear+1}" id="mnt7_${endYear+1}"></td>
											<td name="varMnt7" id="varMnt7" style="text-align:right"></td>
											<td name="varPer7" id="varPer7" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;">영업이익</th>
											<td name="mnt8_${standardYear}" id="mnt8_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt8_${standardYear+loop.count}" id="mnt8_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt8_${endYear+1}" id="mnt8_${endYear+1}"></td>
											<td name="varMnt8" id="varMnt8" style="text-align:right"></td>
											<td name="varPer8" id="varPer8" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">영업외수익</th>
											<td name="mnt9_${standardYear}" id="mnt9_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt9_${standardYear+loop.count}" id="mnt9_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt9_${endYear+1}" id="mnt9_${endYear+1}"></td>
											<td name="varMnt9" id="varMnt9" style="text-align:right"></td>
											<td name="varPer9" id="varPer9" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;이자수익</th>
											<td name="mnt10_${standardYear}" id="mnt10_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt10_${standardYear+loop.count}" id="mnt10_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt10_${endYear+1}" id="mnt10_${endYear+1}"></td>
											<td name="varMnt10" id="varMnt10" style="text-align:right"></td>
											<td name="varPer10" id="varPer10" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
											<td name="mnt11_${standardYear}" id="mnt11_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt11_${standardYear+loop.count}" id="mnt11_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt11_${endYear+1}" id="mnt11_${endYear+1}"></td>
											<td name="varMnt11" id="varMnt11" style="text-align:right"></td>
											<td name="varPer11" id="varPer11" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">영업외비용</th>
											<td name="mnt12_${standardYear}" id="mnt12_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt12_${standardYear+loop.count}" id="mnt12_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt12_${endYear+1}" id="mnt12_${endYear+1}"></td>
											<td name="varMnt12" id="varMnt12" style="text-align:right"></td>
											<td name="varPer12" id="varPer12" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;이자수익</th>
											<td name="mnt13_${standardYear}" id="mnt13_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt13_${standardYear+loop.count}" id="mnt13_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt13_${endYear+1}" id="mnt13_${endYear+1}"></td>
											<td name="varMnt13" id="varMnt13" style="text-align:right"></td>
											<td name="varPer13" id="varPer13" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
											<td name="mnt14_${standardYear}" id="mnt14_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt14_${standardYear+loop.count}" id="mnt14_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt14_${endYear+1}" id="mnt14_${endYear+1}"></td>
											<td name="varMnt14" id="varMnt14" style="text-align:right"></td>
											<td name="varPer14" id="varPer14" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;">법인세비용차감전이익</th>
											<td name="mnt15_${standardYear}" id="mnt15_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt15_${standardYear+loop.count}" id="mnt15_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt15_${endYear+1}" id="mnt15_${endYear+1}"></td>
											<td name="varMnt15" id="varMnt15" style="text-align:right"></td>
											<td name="varPer15" id="varPer15" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;background-color:white;">법인세비용</th>
											<td name="mnt16_${standardYear}" id="mnt16_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt16_${standardYear+loop.count}" id="mnt16_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt16_${endYear+1}" id="mnt16_${endYear+1}"></td>
											<td name="varMnt16" id="varMnt16" style="text-align:right"></td>
											<td name="varPer16" id="varPer16" style="text-align:right"></td>
									    </tr>
									    <tr>
											<th style="text-align:left;">당기순이익</th>
											<td name="mnt17_${standardYear}" id="mnt17_${standardYear}" style="text-align:right"></td>
											<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
												<td name="mnt17_${standardYear+loop.count}" id="mnt17_${standardYear+loop.count}" style="text-align:right"></td>
											</c:forEach>
											<td class="yearPlus" style="display:none;text-align:right" name="mnt17_${endYear+1}" id="mnt17_${endYear+1}"></td>
											<td name="varMnt17" id="varMnt17" style="text-align:right"></td>
											<td name="varPer17" id="varPer17" style="text-align:right"></td>
									    </tr>
						        </tbody>
						    </table>
						</div>
				<!--//blockDetail End-->
				
				<table width="100%">
					<tr>
						<td width="80%">
							<div class="subTitle_2 noline">
								<h3>2.재무현황</h3>
							</div>
						</td>
						<td width="20%" style="text-align:right;"><font color="red"><strong>(단위 : 백만원)</strong></font></td>
					</tr>
				</table>
              	<div class="blockDetail">
						    <table summary="new group">
						        <caption></caption>
						        <colgroup>
						            <col width="15%"/>
						            <c:forEach begin="1" end="${endYear-standardYear+1}">
						            	<col width=""/>
						            </c:forEach>
						            <col width="" class="yearPlus" style="display:none;"/>
						            <col width=""/>
						            <col width=""/>
						        </colgroup>
						        <tbody>
						            <tr>
						            	<th style="text-align:center;" rowspan="2">항목</th>
						            	<th style="text-align:center;" colspan="${endYear-standardYear+3}" id="yearMoreOff" class="yearMoreOff">년도</th>
						            	<th colspan="${endYear-standardYear+4}" id="yearMoreOn" class="yearMoreOn" style="display:none;text-align:center;">년도</th>
						            </tr>
						            <tr>
						            	<th style="text-align:center;">${standardYear}년</th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th style="text-align:center;" >${standardYear+loop.count}년</th>
						            	</c:forEach>
						            	
						            	<th class="yearPlus" style="display:none;text-align:center;">${endYear+1}년</th>
						            	<th style="text-align:center;">증감액</th>
						            	<th style="text-align:center;">증감율</th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">유동자산</th>
						            	<td name="mntB1_${standardYear}" id="mntB1_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB1_${standardYear+loop.count}" id="mntB1_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB1_${endYear+1}" id="mntB1_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB1" id="varMntB1" style="text-align:right" style="text-align:right"></td>
						            	<td name="varPerB1" id="varPerB1" style="text-align:right" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;당좌자산</th>
						            	<td name="mntB2_${standardYear}" id="mntB2_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB2_${standardYear+loop.count}" id="mntB2_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB2_${endYear+1}" id="mntB2_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB2" id="varMntB2" style="text-align:right"></td>
						            	<td name="varPerB2" id="varPerB2" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;현금및현금성자산</th>
						            	<td name="mntB3_${standardYear}" id="mntB3_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB3_${standardYear+loop.count}" id="mntB3_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB3_${endYear+1}" id="mntB3_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB3" id="varMntB3" style="text-align:right"></td>
						            	<td name="varPerB3" id="varPerB3" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;매출채권</th>
						            	<td name="mntB4_${standardYear}" id="mntB4_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB4_${standardYear+loop.count}" id="mntB4_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB4_${endYear+1}" id="mntB4_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB4" id="varMntB4" style="text-align:right"></td>
						            	<td name="varPerB4" id="varPerB4" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;기타</th>
						            	<td name="mntB29_${standardYear}" id="mntB29_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB29_${standardYear+loop.count}" id="mntB29_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB29_${endYear+1}" id="mntB29_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB29" id="varMntB29" style="text-align:right"></td>
						            	<td name="varPerB29" id="varPerB29" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;재고자산</th>
						            	<td name="mntB5_${standardYear}" id="mntB5_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB5_${standardYear+loop.count}" id="mntB5_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB5_${endYear+1}" id="mntB5_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB5" id="varMntB5" style="text-align:right"></td>
						            	<td name="varPerB5" id="varPerB5" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">비유동자산</th>
						            	<td name="mntB6_${standardYear}" id="mntB6_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB6_${standardYear+loop.count}" id="mntB6_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB6_${endYear+1}" id="mntB6_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB6" id="varMntB6" style="text-align:right"></td>
						            	<td name="varPerB6" id="varPerB6" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;투자자산</th>
						            	<td name="mntB7_${standardYear}" id="mntB7_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB7_${standardYear+loop.count}" id="mntB7_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB7_${endYear+1}" id="mntB7_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB7" id="varMntB7" style="text-align:right"></td>
						            	<td name="varPerB7" id="varPerB7" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;유형자산</th>
						            	<td name="mntB8_${standardYear}" id="mntB8_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB8_${standardYear+loop.count}" id="mntB8_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB8_${endYear+1}" id="mntB8_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB8" id="varMntB8" style="text-align:right"></td>
						            	<td name="varPerB8" id="varPerB8" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;토지</th>
						            	<td name="mntB9_${standardYear}" id="mntB9_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB9_${standardYear+loop.count}" id="mntB9_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB9_${endYear+1}" id="mntB9_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB9" id="varMntB9" style="text-align:right"></td>
						            	<td name="varPerB9" id="varPerB9" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;건물</th>
						            	<td name="mntB10_${standardYear}" id="mntB10_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB10_${standardYear+loop.count}" id="mntB10_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB10_${endYear+1}" id="mntB10_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB10" id="varMntB10" style="text-align:right"></td>
						            	<td name="varPerB10" id="varPerB10" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;기계장치</th>
						            	<td name="mntB11_${standardYear}" id="mntB11_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB11_${standardYear+loop.count}" id="mntB11_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB11_${endYear+1}" id="mntB11_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB11" id="varMntB11" style="text-align:right"></td>
						            	<td name="varPerB11" id="varPerB11" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;무형자산</th>
						            	<td name="mntB12_${standardYear}" id="mntB12_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB12_${standardYear+loop.count}" id="mntB12_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB12_${endYear+1}" id="mntB12_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB12" id="varMntB12" style="text-align:right"></td>
						            	<td name="varPerB12" id="varPerB12" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타비유동자산</th>
						            	<td name="mntB13_${standardYear}" id="mntB13_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB13_${standardYear+loop.count}" id="mntB13_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB13_${endYear+1}" id="mntB13_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB13" id="varMntB13" style="text-align:right"></td>
						            	<td name="varPerB13" id="varPerB13" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">자산총계</th>
						            	<td name="mntB14_${standardYear}" id="mntB14_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB14_${standardYear+loop.count}" id="mntB14_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB14_${endYear+1}" id="mntB14_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB14" id="varMntB14" style="text-align:right"></td>
						            	<td name="varPerB14" id="varPerB14" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">유동부채</th>
						            	<td name="mntB15_${standardYear}" id="mntB15_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB15_${standardYear+loop.count}" id="mntB15_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB15_${endYear+1}" id="mntB15_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB15" id="varMntB15" style="text-align:right"></td>
						            	<td name="varPerB15" id="varPerB15" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;매입채무</th>
						            	<td name="mntB16_${standardYear}" id="mntB16_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB16_${standardYear+loop.count}" id="mntB16_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB16_${endYear+1}" id="mntB16_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB16" id="varMntB16" style="text-align:right"></td>
						            	<td name="varPerB16" id="varPerB16" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;단기차입금</th>
						            	<td name="mntB17_${standardYear}" id="mntB17_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB17_${standardYear+loop.count}" id="mntB17_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB17_${endYear+1}" id="mntB17_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB17" id="varMntB17" style="text-align:right"></td>
						            	<td name="varPerB17" id="varPerB17" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;유동성장기부채</th>
						            	<td name="mntB18_${standardYear}" id="mntB18_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB18_${standardYear+loop.count}" id="mntB18_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB18_${endYear+1}" id="mntB18_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB18" id="varMntB18" style="text-align:right"></td>
						            	<td name="varPerB18" id="varPerB18" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td name="mntB19_${standardYear}" id="mntB19_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB19_${standardYear+loop.count}" id="mntB19_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB19_${endYear+1}" id="mntB19_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB19" id="varMntB19" style="text-align:right"></td>
						            	<td name="varPerB19" id="varPerB19" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">비유동부채</th>
						            	<td name="mntB20_${standardYear}" id="mntB20_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB20_${standardYear+loop.count}" id="mntB20_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB20_${endYear+1}" id="mntB20_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB20" id="varMntB20" style="text-align:right"></td>
						            	<td name="varPerB20" id="varPerB20" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;장기차입금</th>
						            	<td name="mntB21_${standardYear}" id="mntB21_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB21_${standardYear+loop.count}" id="mntB21_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB21_${endYear+1}" id="mntB21_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB21" id="varMntB21" style="text-align:right"></td>
						            	<td name="varPerB21" id="varPerB21" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;사채</th>
						            	<td name="mntB22_${standardYear}" id="mntB22_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB22_${standardYear+loop.count}" id="mntB22_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB22_${endYear+1}" id="mntB22_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB22" id="varMntB22" style="text-align:right"></td>
						            	<td name="varPerB22" id="varPerB22" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td name="mntB23_${standardYear}" id="mntB23_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB23_${standardYear+loop.count}" id="mntB23_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB23_${endYear+1}" id="mntB23_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB23" id="varMntB23" style="text-align:right"></td>
						            	<td name="varPerB23" id="varPerB23" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">부채총계</th>
						            	<td name="mntB24_${standardYear}" id="mntB24_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB24_${standardYear+loop.count}" id="mntB24_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB24_${endYear+1}" id="mntB24_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB24" id="varMntB24" style="text-align:right"></td>
						            	<td name="varPerB24" id="varPerB24" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">자본금</th>
						            	<td name="mntB25_${standardYear}" id="mntB25_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB25_${standardYear+loop.count}" id="mntB25_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB25_${endYear+1}" id="mntB25_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB25" id="varMntB25" style="text-align:right"></td>
						            	<td name="varPerB25" id="varPerB25" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">이익잉여금</th>
						            	<td name="mntB26_${standardYear}" id="mntB26_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB26_${standardYear+loop.count}" id="mntB26_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB26_${endYear+1}" id="mntB26_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB26" id="varMntB26" style="text-align:right"></td>
						            	<td name="varPerB26" id="varPerB26" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">기타</th>
						            	<td name="mntB27_${standardYear}" id="mntB27_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB27_${standardYear+loop.count}" id="mntB27_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB27_${endYear+1}" id="mntB27_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB27" id="varMntB27" style="text-align:right"></td>
						            	<td name="varPerB27" id="varPerB27" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">자본총계</th>
						            	<td name="mntB28_${standardYear}" id="mntB28_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntB28_${standardYear+loop.count}" id="mntB28_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntB28_${endYear+1}" id="mntB28_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varMntB28" id="varMntB28" style="text-align:right"></td>
						            	<td name="varPerB28" id="varPerB28" style="text-align:right"></td>
						            </tr>
						            
						        </tbody>
						    </table>
						</div>
						
						<table width="100%">
							<tr>
								<td width="80%">
									<div class="subTitle_2 noline">
										<h3>3.재무비율</h3>
									</div>
								</td>
								<td width="20%" style="text-align:right;"><font color="red"><strong>(단위 : %)</strong></font></td>
							</tr>
						</table>
						<div class="blockDetail">
						    <table summary="new group">
						        <caption></caption>
						        <colgroup>
						            <col width="20%"/>
						            <c:forEach begin="1" end="${endYear-standardYear+1}">
						            	<col width=""/>
						            </c:forEach>
						            <col width="" class="yearPlus" style="display:none;"/>
						            <col width=""/>
						        </colgroup>
						        <tbody>
						            <tr>
						            	<th style="text-align:center;" rowspan="2">항목</th>
						            	<th style="text-align:center;" colspan="${endYear-standardYear+2}" id="yearMoreOff" class="yearMoreOff">년도</th>
						            	<th colspan="${endYear-standardYear+3}" id="yearMoreOn" class="yearMoreOn" style="display:none;text-align:center;">년도</th>
						            </tr>
						            <tr>
						            	<th style="text-align:center;">${standardYear}년</th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th style="text-align:center;">${standardYear+loop.count}년</th>
						            	</c:forEach>
						            	
						            	<th class="yearPlus" style="display:none;text-align:center;">${endYear+1}년</th>
						            	<th style="text-align:center;">증감율</th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">유동비율</th>
						            	<td name="mntC1_${standardYear}" id="mntC1_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC1_${standardYear+loop.count}" id="mntC1_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC1_${endYear+1}" id="mntC1_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC1" id="varPerC1" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">부채비율</th>
						            	<td name="mntC2_${standardYear}" id="mntC2_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC2_${standardYear+loop.count}" id="mntC2_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC2_${endYear+1}" id="mntC2_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC2" id="varPerC2" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">자기자본비율</th>
						            	<td name="mntC3_${standardYear}" id="mntC3_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC3_${standardYear+loop.count}" id="mntC3_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC3_${endYear+1}" id="mntC3_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC3" id="varPerC3" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">차입금의존도</th>
						            	<td name="mntC4_${standardYear}" id="mntC4_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC4_${standardYear+loop.count}" id="mntC4_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC4_${endYear+1}" id="mntC4_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC4" id="varPerC4" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">이자보상배율</th>
						            	<td name="mntC5_${standardYear}" id="mntC5_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC5_${standardYear+loop.count}" id="mntC5_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC5_${endYear+1}" id="mntC5_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC5" id="varPerC5" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출액총이익율</th>
						            	<td name="mntC6_${standardYear}" id="mntC6_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC6_${standardYear+loop.count}" id="mntC6_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC6_${endYear+1}" id="mntC6_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC6" id="varPerC6" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">영업이익율</th>
						            	<td name="mntC7_${standardYear}" id="mntC7_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC7_${standardYear+loop.count}" id="mntC7_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC7_${endYear+1}" id="mntC7_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC7" id="varPerC7" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">법인세비용차감전이익율</th>
						            	<td name="mntC8_${standardYear}" id="mntC8_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC8_${standardYear+loop.count}" id="mntC8_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC8_${endYear+1}" id="mntC8_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC8" id="varPerC8" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">순이익율</th>
						            	<td name="mntC9_${standardYear}" id="mntC9_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC9_${standardYear+loop.count}" id="mntC9_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC9_${endYear+1}" id="mntC9_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC9" id="varPerC9" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출채권회전율</th>
						            	<td name="mntC10_${standardYear}" id="mntC10_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC10_${standardYear+loop.count}" id="mntC10_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC10_${endYear+1}" id="mntC10_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC10" id="varPerC10" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매입채무회전율</th>
						            	<td name="mntC11_${standardYear}" id="mntC11_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC11_${standardYear+loop.count}" id="mntC11_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC11_${endYear+1}" id="mntC11_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC11" id="varPerC11" style="text-align:right"></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">재고자산회전율</th>
						            	<td name="mntC12_${standardYear}" id="mntC12_${standardYear}" style="text-align:right"></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td name="mntC12_${standardYear+loop.count}" id="mntC12_${standardYear+loop.count}" style="text-align:right"></td>
						            	</c:forEach>
						            	<td class="yearPlus" name="mntC12_${endYear+1}" id="mntC12_${endYear+1}" style="text-align:right;display:none;"></td>
						            	<td name="varPerC12" id="varPerC12" style="text-align:right"></td>
						            </tr>
						        </tbody>
						    </table>
						</div>
                
