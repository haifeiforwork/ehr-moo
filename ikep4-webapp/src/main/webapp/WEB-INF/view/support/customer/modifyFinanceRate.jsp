<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:forEach var="rate" items="${rateList}" varStatus="rcnt">
<c:set var="rtcnt" value="${rcnt.count-1}"/>
</c:forEach>

<script type="text/javascript">
var openYear = 0;
(function($) {
	
	financeTab = function(){
		location.href = "<c:url value='/support/customer/customerFinance/modifyFinance.do?id=${param.id}'/>";	  
	};
	conditionTab = function(){
	    location.href="<c:url value='/support/customer/customerFinance/modifyFinanceCondition.do?id=${param.id}'/>";
	}
	rateTab = function(){
		location.href="<c:url value='/support/customer/customerFinance/modifyFinanceRate.do?id=${param.id}'/>";
	};
	
	
	$(document).ready(function() {
 		
		$("#divTab2").tabs();
		var id = $("#id").val();
		
    	tab = $("#divTab2").tabs({
    		selected : 2, 
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
				case "tabs-1" : financeTab();
					break;
				case "tabs-2" : conditionTab();
					break;
				case "tabs-3" : rateTab();
					break;
				}
			}
		});   
    	
    	var syear = $("#stYear").val();
		var eyear = $("#edYear").val();
		var rcnt = "${rtcnt}";
    	/*if(rcnt > eyear-syear+1){
    		$(".yearPlus").show();
    		$(".yearPlusFnc").hide();
    		$("#yearMoreOff").hide();
    		$("#yearMoreOn").show();
    		openYear = 1;
    		iKEP.iFrameContentResize();
    	}*/
	
    	<c:forEach var="rate" items="${rateList}">
    	if("${rate.divInfo}" == ""){
    		if("${rate.yearInfo}" > eyear){
        		$(".yearPlus").show();
        		$(".yearPlusFnc").hide();
        		$("#yearMoreOff").hide();
        		$("#yearMoreOn").show();
        		openYear = 1;
        		iKEP.iFrameContentResize();
        	}
    		$jq("#mnt1_${rate.yearInfo}").val("${rate.mnt1}");
    		$jq("#mnt2_${rate.yearInfo}").val("${rate.mnt2}");
    		$jq("#mnt3_${rate.yearInfo}").val("${rate.mnt3}");
    		$jq("#mnt4_${rate.yearInfo}").val("${rate.mnt4}");
    		$jq("#mnt5_${rate.yearInfo}").val("${rate.mnt5}");
    		$jq("#mnt6_${rate.yearInfo}").val("${rate.mnt6}");
    		$jq("#mnt7_${rate.yearInfo}").val("${rate.mnt7}");
    		$jq("#mnt8_${rate.yearInfo}").val("${rate.mnt8}");
    		$jq("#mnt9_${rate.yearInfo}").val("${rate.mnt9}");
    		$jq("#mnt10_${rate.yearInfo}").val("${rate.mnt10}");
    		$jq("#mnt11_${rate.yearInfo}").val("${rate.mnt11}");
    		$jq("#mnt12_${rate.yearInfo}").val("${rate.mnt12}");
    	}else if("${rate.divInfo}" == "rate"){
    		$jq("#varPer1").val("${rate.mnt1}");
    		$jq("#varPer2").val("${rate.mnt2}");
    		$jq("#varPer3").val("${rate.mnt3}");
    		$jq("#varPer4").val("${rate.mnt4}");
    		$jq("#varPer5").val("${rate.mnt5}");
    		$jq("#varPer6").val("${rate.mnt6}");
    		$jq("#varPer7").val("${rate.mnt7}");
    		$jq("#varPer8").val("${rate.mnt8}");
    		$jq("#varPer9").val("${rate.mnt9}");
    		$jq("#varPer10").val("${rate.mnt10}");
    		$jq("#varPer11").val("${rate.mnt11}");
    		$jq("#varPer12").val("${rate.mnt12}");
    	}
    </c:forEach>

	$("a.saveFinanceRate").click(function(){
		
		var styear = $("#stYear").val();
		var edyear = $("#edYear").val();
		var yearGroup = "";
		var mntGroup = "";
		var varPer = "";
		for(i=0;i<edyear-styear+1+openYear;i++){
			for(j=1;j<13;j++){
				if(mntGroup == ""){
					mntGroup = Number(styear)+i+"/mt"+j+"/"+$("#mnt1_"+(Number(styear)+i)).val();
				}else{
					if(yearGroup < Number(styear)+i){
						mntGroup = mntGroup+"_"+(Number(styear)+i)+"/mt"+j+"/"+$("#mnt"+j+"_"+(Number(styear)+i)).val();
					}else{
						mntGroup = mntGroup+"-"+(Number(styear)+i)+"/mt"+j+"/"+$("#mnt"+j+"_"+(Number(styear)+i)).val();
					}
				}
				yearGroup = Number(styear)+i;
			}
		}
		
		for(i=1;i<13;i++){
			if(varPer == ""){
				varPer = "varPer"+i+"/"+$("#varPer"+i).val();
			}else{
				varPer = varPer+"-varPer"+i+"/"+$("#varPer"+i).val();
			}
		}
		$("#yearValue").val(mntGroup);
		$("#perValue").val(varPer);
		$("#customerInfoId").val($("#customerId").val());
		$("#customerInfoName").val($("#customerName").val());
		$("#financeRateForm").trigger("submit");
	});
	
	$("a.yearPlusFnc").click(function(){	
		$(".yearPlus").show();
		$(".yearPlusFnc").hide();
		$("#yearMoreOff").hide();
		$("#yearMoreOn").show();
		openYear = 1;
		iKEP.iFrameContentResize();
	});
	
	$("a.searchCustomerId").click(function(){
		searchCustomer();
	});
	
	new iKEP.Validator("#financeRateForm",{
		
		rules : {},
		messages : {},
		
		submitHandler : function(form){
			var customerId = $("#customerId").val();
			if( customerId == ''){
				alert("<ikep4j:message key='NotNull.customer.mainselling.customer' />");
				$("#customer").focus();
			}else{
				if(confirm("저장하시겠습니까?")){
					form.submit();
					alert("저장되었습니다.");
				}
			}
		}
	});
	
	goMenu = function(id){
		
		if(id == undefined){
			url = "<c:url value='/support/customer/customerFinance/customerList.do'/>";
			location.href = url;
		}else{
			url = "<c:url value='/support/customer/customerFinance/detailFinance.do?id='/>"+id;
			location.href = url;
		}
		
	}	
		
		
	});
})(jQuery);

function searchCustomer(){

	var name = document.getElementById("customer").value;
	if(!name){
			alert("고객명을 입력해주세요.");
		}else{ 				
			 var url = "<c:url value='/support/customer/customerCommon/popupCustomer.do?customer='/>"+name;
			
			 iKEP.popupOpen(url,{width:700,height:490},'CustomerSearch'); 
			
		}

}
</script>


<h1 class="none">컨텐츠영역</h1>
								
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>재무/손익 정보</h2>	
	<div class="clear"></div>
</div>
<!--//tableTop End-->
<form id="registerForm" name="registerForm">
<input type="hidden" id="customerId" name="customerId" value="${finance.id}" /> 
<input type="hidden" name="customerName" id="customerName" value="${finance.name}" />
<table width="100%">
<tr>
	<td width="80%"><strong>고객명</strong>&nbsp;<input id="customer" name="customer" value="${finance.name}" title="고객명" class="inputbox" type="text"  /> 
	<a class="ic_search valign_middle searchCustomerId" href="#a"><span>검색</span></a>
	</td>
	<td width="20%" style="text-align:right;"><font color="red"><strong>(단위 : %)</strong></font></td>
</tr>
</table>
</form>
        <!--tab Start-->		
        <div id="divTab2" class="iKEP_tab_poll">
            <ul>
                <li><a href="#tabs-1" >손익현황</a></li>
                <li><a href="#tabs-2" >재무현황</a></li>
                <li><a href="#tabs-3" >재무비율</a></li>
            </ul>
            <div>        
                <div id="tabs-1"></div>
                <div id="tabs-2"></div>
                <div id="tabs-3">
                	<form id="financeRateForm" method="post" action="<c:url value='/support/customer/customerFinance/saveFinanceRate.do'/>">
                	<input type="hidden" name="id" id="id" value="${id}" />
                	<input type="hidden" name="yearValue" id="yearValue" value="" />
                <input type="hidden" name="perValue" id="perValue" value="" />
                <input type="hidden" name="customerInfoId" id="customerInfoId" value="" />
                <input type="hidden" name="customerInfoName" id="customerInfoName" value="" />
                	<!--blockDetail Start-->
					 <c:set var="standardYear" value="${startDt}" />
				<c:set var="endYear" value="${endDt}" />
				<input type="hidden" name="stYear" id="stYear" value="${standardYear}" />
				<input type="hidden" name="edYear" id="edYear" value="${endYear}" />
                	<!--blockDetail Start-->
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
						            	<th style="text-align:center;" colspan="${endYear-standardYear+2}" id="yearMoreOff">년도</th>
						            	<th colspan="${endYear-standardYear+3}" id="yearMoreOn" style="display:none;text-align:center;">년도</th>
						            </tr>
						            <tr>
						            	<th style="text-align:center;">${standardYear}년</th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th style="text-align:center;">${standardYear+loop.count}년
							            		<c:if test="${endYear==standardYear+loop.count}"> 
								            		<a class="yearPlusFnc" href="#a" >
														<img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" style="vertical-align:middle;"/>
													</a>
												</c:if>
						            		</th>
						            	</c:forEach>
						            	
						            	<th class="yearPlus" style="display:none;text-align:center;">${endYear+1}년</th>
						            	<th style="text-align:center;">증감율</th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">유동비율</th>
						            	<td><input name="mnt1_${standardYear}" id="mnt1_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt1_${standardYear+loop.count}" id="mnt1_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt1_${endYear+1}" id="mnt1_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer1" id="varPer1" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">부채비율</th>
						            	<td><input name="mnt2_${standardYear}" id="mnt2_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt2_${standardYear+loop.count}" id="mnt2_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt2_${endYear+1}" id="mnt2_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer2" id="varPer2" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">자기자본비율</th>
						            	<td><input name="mnt3_${standardYear}" id="mnt3_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt3_${standardYear+loop.count}" id="mnt3_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt3_${endYear+1}" id="mnt3_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer3" id="varPer3" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">차입금의존도</th>
						            	<td><input name="mnt4_${standardYear}" id="mnt4_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt4_${standardYear+loop.count}" id="mnt4_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt4_${endYear+1}" id="mnt4_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer4" id="varPer4" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">이자보상배율</th>
						            	<td><input name="mnt5_${standardYear}" id="mnt5_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt5_${standardYear+loop.count}" id="mnt5_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt5_${endYear+1}" id="mnt5_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer5" id="varPer5" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출액총이익율</th>
						            	<td><input name="mnt6_${standardYear}" id="mnt6_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt6_${standardYear+loop.count}" id="mnt6_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt6_${endYear+1}" id="mnt6_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer6" id="varPer6" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">영업이익율</th>
						            	<td><input name="mnt7_${standardYear}" id="mnt7_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt7_${standardYear+loop.count}" id="mnt7_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt7_${endYear+1}" id="mnt7_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer7" id="varPer7" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">법인세비용차감전이익율</th>
						            	<td><input name="mnt8_${standardYear}" id="mnt8_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt8_${standardYear+loop.count}" id="mnt8_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt8_${endYear+1}" id="mnt8_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer8" id="varPer8" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">순이익율</th>
						            	<td><input name="mnt9_${standardYear}" id="mnt9_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt9_${standardYear+loop.count}" id="mnt9_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt9_${endYear+1}" id="mnt9_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer9" id="varPer9" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출채권최전율</th>
						            	<td><input name="mnt10_${standardYear}" id="mnt10_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt10_${standardYear+loop.count}" id="mnt10_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt10_${endYear+1}" id="mnt10_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer10" id="varPer10" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매입채무회전율</th>
						            	<td><input name="mnt11_${standardYear}" id="mnt11_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt11_${standardYear+loop.count}" id="mnt11_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt11_${endYear+1}" id="mnt11_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer11" id="varPer11" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">재고자산회전율</th>
						            	<td><input name="mnt12_${standardYear}" id="mnt12_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt12_${standardYear+loop.count}" id="mnt12_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt12_${endYear+1}" id="mnt12_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varPer12" id="varPer12" value=""  class="inputbox w90" type="text" /></td>
						            </tr>
						        </tbody>
						    </table>
						</div>	
					 <!--//blockDetail End-->									
					             
					 <!--blockButton Start-->
					 <div class="blockButton"> 
					     <ul>
					         <li><a class="button saveFinanceRate" href="#a"><span>등록</span></a></li>
					       <!--   <li><a class="button" href="javascript:mainPerson1Tab();"><span>다음단계</span></a></li> -->
					         <li><a class="button" href="javascript:goMenu(${param.id});"><span>목록</span></a></li>
					     </ul>
					 </div>
					 <!--//blockButton End-->
					 </form>
                </div>
            </div>				
        </div>		
        <!--//tab End-->




   
    

     
      

