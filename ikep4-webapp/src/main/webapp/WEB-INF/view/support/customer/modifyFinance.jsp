<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:forEach var="profitLoss" items="${profitLossList}" varStatus="pcnt">
<c:set var="pfcnt" value="${pcnt.count-2}"/>
</c:forEach>
<script type="text/javascript">
var openYear = 0;
var summaryYn = 0;

(function($) {

	
	$(document).ready(function() {
		$("#divTab2").tabs();
	
		var id = $("#id").val();
		
		
    	tab = $("#divTab2").tabs({
    		
    		selected : 0, 
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
		var pcnt = "${pfcnt}";
    	/*if(pcnt > eyear-syear+1){
    		$(".yearPlus").show();
    		$(".yearPlusFnc").hide();
    		$("#yearMoreOff").hide();
    		$("#yearMoreOn").show();
    		openYear = 1;
    		iKEP.iFrameContentResize();
    	}*/
    	<c:forEach var="profitLoss" items="${profitLossList}">
		if("${profitLoss.divInfo}" == ""){
			if("${profitLoss.yearInfo}" > eyear){
				$(".yearPlus").show();
	    		$(".yearPlusFnc").hide();
	    		$("#yearMoreOff").hide();
	    		$("#yearMoreOn").show();
	    		openYear = 1;
	    		iKEP.iFrameContentResize();
			}
			$jq("#mnt1_${profitLoss.yearInfo}").val("${profitLoss.mnt1}");
			$jq("#mnt2_${profitLoss.yearInfo}").val("${profitLoss.mnt2}");
			$jq("#mnt3_${profitLoss.yearInfo}").val("${profitLoss.mnt3}");
			$jq("#mnt4_${profitLoss.yearInfo}").val("${profitLoss.mnt4}");
			$jq("#mnt5_${profitLoss.yearInfo}").val("${profitLoss.mnt5}");
			$jq("#mnt6_${profitLoss.yearInfo}").val("${profitLoss.mnt6}");
			$jq("#mnt7_${profitLoss.yearInfo}").val("${profitLoss.mnt7}");
			$jq("#mnt8_${profitLoss.yearInfo}").val("${profitLoss.mnt8}");
			$jq("#mnt9_${profitLoss.yearInfo}").val("${profitLoss.mnt9}");
			$jq("#mnt10_${profitLoss.yearInfo}").val("${profitLoss.mnt10}");
			$jq("#mnt11_${profitLoss.yearInfo}").val("${profitLoss.mnt11}");
			$jq("#mnt12_${profitLoss.yearInfo}").val("${profitLoss.mnt12}");
			$jq("#mnt13_${profitLoss.yearInfo}").val("${profitLoss.mnt13}");
			$jq("#mnt14_${profitLoss.yearInfo}").val("${profitLoss.mnt14}");
			$jq("#mnt15_${profitLoss.yearInfo}").val("${profitLoss.mnt15}");
			$jq("#mnt16_${profitLoss.yearInfo}").val("${profitLoss.mnt16}");
			$jq("#mnt17_${profitLoss.yearInfo}").val("${profitLoss.mnt17}");
		}else if("${profitLoss.divInfo}" == "mount"){
			$jq("#varMnt1").val("${profitLoss.mnt1}");
			$jq("#varMnt2").val("${profitLoss.mnt2}");
			$jq("#varMnt3").val("${profitLoss.mnt3}");
			$jq("#varMnt4").val("${profitLoss.mnt4}");
			$jq("#varMnt5").val("${profitLoss.mnt5}");
			$jq("#varMnt6").val("${profitLoss.mnt6}");
			$jq("#varMnt7").val("${profitLoss.mnt7}");
			$jq("#varMnt8").val("${profitLoss.mnt8}");
			$jq("#varMnt9").val("${profitLoss.mnt9}");
			$jq("#varMnt10").val("${profitLoss.mnt10}");
			$jq("#varMnt11").val("${profitLoss.mnt11}");
			$jq("#varMnt12").val("${profitLoss.mnt12}");
			$jq("#varMnt13").val("${profitLoss.mnt13}");
			$jq("#varMnt14").val("${profitLoss.mnt14}");
			$jq("#varMnt15").val("${profitLoss.mnt15}");
			$jq("#varMnt16").val("${profitLoss.mnt16}");
			$jq("#varMnt17").val("${profitLoss.mnt17}");
		}else if("${profitLoss.divInfo}" == "rate"){
			$jq("#varPer1").val("${profitLoss.mnt1}");
			$jq("#varPer2").val("${profitLoss.mnt2}");
			$jq("#varPer3").val("${profitLoss.mnt3}");
			$jq("#varPer4").val("${profitLoss.mnt4}");
			$jq("#varPer5").val("${profitLoss.mnt5}");
			$jq("#varPer6").val("${profitLoss.mnt6}");
			$jq("#varPer7").val("${profitLoss.mnt7}");
			$jq("#varPer8").val("${profitLoss.mnt8}");
			$jq("#varPer9").val("${profitLoss.mnt9}");
			$jq("#varPer10").val("${profitLoss.mnt10}");
			$jq("#varPer11").val("${profitLoss.mnt11}");
			$jq("#varPer12").val("${profitLoss.mnt12}");
			$jq("#varPer13").val("${profitLoss.mnt13}");
			$jq("#varPer14").val("${profitLoss.mnt14}");
			$jq("#varPer15").val("${profitLoss.mnt15}");
			$jq("#varPer16").val("${profitLoss.mnt16}");
			$jq("#varPer17").val("${profitLoss.mnt17}");		
		}
	</c:forEach>
    	
	

    	conditionTab = function(){
    	    location.href="<c:url value='/support/customer/customerFinance/modifyFinanceCondition.do?id=${finance.id}'/>";
    	}
    	rateTab = function(){
    		location.href="<c:url value='/support/customer/customerFinance/modifyFinanceRate.do?id=${finance.id}'/>";
    	}
    	
    	
    	
    	$("a.summaryBtn").click(function(){
    		summaryYn = 1;
    		var styear = $("#stYear").val();
    		var edyear = $("#edYear").val();
    		var yearGroup = "";
    		var mntGroup = "";
    		var varMnt = "";
    		var varPer = "";
    		for(i=0;i<edyear-styear+1+openYear;i++){
    			for(j=1;j<18;j++){
    				if(j == "3"){
    					$("#mnt3_"+(Number(styear)+i)).val($("#mnt1_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt2_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    				if(j == "7"){
    					$("#mnt7_"+(Number(styear)+i)).val($("#mnt4_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt5_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt6_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    				if(j == "8"){
    					$("#mnt8_"+(Number(styear)+i)).val($("#mnt3_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt4_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    				if(j == "11"){
    					$("#mnt11_"+(Number(styear)+i)).val($("#mnt9_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt10_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    				if(j == "14"){
    					$("#mnt14_"+(Number(styear)+i)).val($("#mnt12_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt13_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    				if(j == "15"){
    					$("#mnt15_"+(Number(styear)+i)).val(Number($("#mnt8_"+(Number(styear)+i)).val().replace(/,/gi,"")) + Number($("#mnt9_"+(Number(styear)+i)).val().replace(/,/gi,"")) - $("#mnt12_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    				if(j == "17"){
    					$("#mnt17_"+(Number(styear)+i)).val($("#mnt15_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt16_"+(Number(styear)+i)).val().replace(/,/gi,""));
    				}
    			}
    		}
    		$("#varMnt1").val($("#mnt1_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt1_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt2").val($("#mnt2_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt2_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt3").val($("#mnt3_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt3_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt4").val($("#mnt4_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt4_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt5").val($("#mnt5_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt5_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt6").val($("#mnt6_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt6_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt7").val($("#mnt7_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt7_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt8").val($("#mnt8_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt8_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt9").val($("#mnt9_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt9_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt10").val($("#mnt10_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt10_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt11").val($("#mnt11_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt11_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt12").val($("#mnt12_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt12_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt13").val($("#mnt13_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt13_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt14").val($("#mnt14_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt14_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt15").val($("#mnt15_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt15_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt16").val($("#mnt16_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt16_"+(Number(edyear)-1)).val().replace(/,/gi,""));
    		$("#varMnt17").val($("#mnt17_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt17_"+(Number(edyear)-1)).val().replace(/,/gi,""));
			
    		if($("#varMnt1").val() == "0" || $("#mnt1_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer1").val("0");
    		}else{
    			$("#varPer1").val((($("#varMnt1").val() / $("#mnt1_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt2").val() == "0" || $("#mnt2_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer2").val("0");
    		}else{
    			$("#varPer2").val((($("#varMnt2").val() / $("#mnt1_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt3").val() == "0" || $("#mnt3_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer3").val("0");
    		}else{
    			$("#varPer3").val((($("#varMnt3").val() / $("#mnt3_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt4").val() == "0" || $("#mnt4_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer4").val("0");
    		}else{
    			$("#varPer4").val((($("#varMnt4").val() / $("#mnt4_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt5").val() == "0" || $("#mnt5_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer5").val("0");
    		}else{
    			$("#varPer5").val((($("#varMnt5").val() / $("#mnt5_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt6").val() == "0" || $("#mnt6_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer6").val("0");
    		}else{
    			$("#varPer6").val((($("#varMnt6").val() / $("#mnt6_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt7").val() == "0" || $("#mnt7_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer7").val("0");
    		}else{
    			$("#varPer7").val((($("#varMnt7").val() / $("#mnt7_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt8").val() == "0" || $("#mnt8_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer8").val("0");
    		}else{
    			$("#varPer8").val((($("#varMnt8").val() / $("#mnt8_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt9").val() == "0" || $("#mnt9_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer9").val("0");
    		}else{
    			$("#varPer9").val((($("#varMnt9").val() / $("#mnt9_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt10").val() == "0" || $("#mnt10_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer10").val("0");
    		}else{
    			$("#varPer10").val((($("#varMnt10").val() / $("#mnt10_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt11").val() == "0" || $("#mnt11_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer11").val("0");
    		}else{
    			$("#varPer11").val((($("#varMnt11").val() / $("#mnt11_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt12").val() == "0" || $("#mnt12_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer12").val("0");
    		}else{
    			$("#varPer12").val((($("#varMnt12").val() / $("#mnt12_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt13").val() == "0" || $("#mnt13_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer13").val("0");
    		}else{
    			$("#varPer13").val((($("#varMnt13").val() / $("#mnt13_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt14").val() == "0" || $("#mnt14_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer14").val("0");
    		}else{
    			$("#varPer14").val((($("#varMnt14").val() / $("#mnt14_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt15").val() == "0" || $("#mnt15_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer15").val("0");
    		}else{
    			$("#varPer15").val((($("#varMnt15").val() / $("#mnt15_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt16").val() == "0" || $("#mnt16_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer16").val("0");
    		}else{
    			$("#varPer16").val((($("#varMnt16").val() / $("#mnt16_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}

    		if($("#varMnt17").val() == "0" || $("#mnt17_"+(Number(edyear)-1)).val() == "0"){
    			$("#varPer17").val("0");
    		}else{
    			$("#varPer17").val((($("#varMnt17").val() / $("#mnt17_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
    		}
    		
    	});

	$("a.saveFinance").click(function(){
		if(summaryYn == 0){
			alert("자동합계 버튼을 클릭해시기 바랍니다.");
			return;
		}
		var styear = $("#stYear").val();
		var edyear = $("#edYear").val();
		var yearGroup = "";
		var mntGroup = "";
		var varMnt = "";
		var varPer = "";
		for(i=0;i<edyear-styear+1+openYear;i++){
			for(j=1;j<18;j++){
				if(mntGroup == ""){
					mntGroup = Number(styear)+i+"/mt"+j+"/"+$("#mnt1_"+(Number(styear)+i)).val().replace(/,/gi,"");
				}else{
					if(yearGroup < Number(styear)+i){
						mntGroup = mntGroup+"_"+(Number(styear)+i)+"/mt"+j+"/"+$("#mnt"+j+"_"+(Number(styear)+i)).val().replace(/,/gi,"");
					}else{
						mntGroup = mntGroup+"^"+(Number(styear)+i)+"/mt"+j+"/"+$("#mnt"+j+"_"+(Number(styear)+i)).val().replace(/,/gi,"");
					}
				}
				yearGroup = Number(styear)+i;
			}
		}
		for(i=1;i<18;i++){
			if(varMnt == ""){
				varMnt = "varMnt"+i+"/"+$("#varMnt"+i).val();
			}else{
				varMnt = varMnt+"^varMnt"+i+"/"+$("#varMnt"+i).val();
			}
		}
		
		for(i=1;i<18;i++){
			if(varPer == ""){
				varPer = "varPer"+i+"/"+$("#varPer"+i).val();
			}else{
				varPer = varPer+"^varPer"+i+"/"+$("#varPer"+i).val();
			}
		}
		$("#yearValue").val(mntGroup);
		$("#mntValue").val(varMnt);
		$("#perValue").val(varPer);
		$("#customerInfoId").val($("#customerId").val());
		$("#customerInfoName").val($("#customerName").val());
		
		$("#financeForm").trigger("submit");
	});

	$("a.searchCustomerId").click(function(){
		searchCustomer();
	});
	
	$jq('#customer').keypress( function(event) {
    	if(event.keyCode == '13'){
    		searchCustomer();
    		return false;
    	}
	});
	

	goSync = function(id) {
		var name = $("#name").val();
		
		var url = '<c:url value='/support/customer/customerFinance/popupSAPSync.do?id='/>'+id+"&name="+name;
		var title = 'SAP SYNC'
		var width = 780;
		var height = 580;

	 	var dialog = iKEP.showDialog({ 
			title: title,
			url: url,
			width:width,
			height:height,
			modal: true,
			scroll: "yes",
			callback : function(result){
				alert("성공");
				
			}
		}); 
		
	};
	
	$("a.yearPlusFnc").click(function(){	
		$(".yearPlus").show();
		$(".yearPlusFnc").hide();
		$("#yearMoreOff").hide();
		$("#yearMoreOn").show();
		openYear = 1;
		iKEP.iFrameContentResize();
	});
	
	
	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
    $jq('#name').keypress( function(event) {
    	if(event.keyCode == '13'){
    		goSync();
    		return false;
    	}
	});
 	
	
	new iKEP.Validator("#financeForm",{
		
		rules : {
		},
		messages : {
		},
		
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

	document.onkeydown = function () {
		
	    var backspace = 8;
	    var t = document.activeElement;
	 
	    if (event.keyCode == backspace) {
	    
	        if (t.tagName == "SELECT"){
	            return false;
	        }
	        if (t.tagName == "INPUT" && t.getAttribute("readonly") == "readonly"){
	            return false;
	        }
	        
	        
	    }
	}
	
	goMenu = function(id){
		
		if(id == undefined){
			url = "<c:url value='/support/customer/customerFinance/detailFinance.do?id='/>"+id;
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
	<td width="20%" style="text-align:right;"><font color="red"><strong>(단위 : 백만원)</strong></font></td>
</tr>
</table>
</form>
        <!--tab Start-->		
        <div id="divTab2" class="iKEP_tab_poll">
            <ul>
                <li><a href="#tabs-1" >손익현황</a></li>
                <li><a href="#tabs-2" >재무현황</a></li>
<!--                 <li><a href="#tabs-3" >재무비율</a></li> -->
            </ul>
            <div>        
                <div id="tabs-1">
                <form id="financeForm" method="post" action="<c:url value='/support/customer/customerFinance/saveFinance.do'/>" >
                <input type="hidden" name="yearValue" id="yearValue" value="" />
                <input type="hidden" name="mntValue" id="mntValue" value="" />
                <input type="hidden" name="perValue" id="perValue" value="" />
                <input type="hidden" name="customerInfoId" id="customerInfoId" value="" />
                <input type="hidden" name="customerInfoName" id="customerInfoName" value="" />
				<c:set var="standardYear" value="${startDt}" />
				<c:set var="endYear" value="${endDt}" />
				<input type="hidden" name="stYear" id="stYear" value="${standardYear}" />
				<input type="hidden" name="edYear" id="edYear" value="${endYear}" />
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
						            	<th style="text-align:center;" colspan="${endYear-standardYear+3}" id="yearMoreOff">년도</th>
						            	<th colspan="${endYear-standardYear+4}" id="yearMoreOn" style="display:none;text-align:center;">년도</th>
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
						            	<th style="text-align:center;">증감액</th>
						            	<th style="text-align:center;">증감율</th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출액</th>
						            	<th><input name="mnt1_${standardYear}" id="mnt1_${standardYear}" value=""  class="inputbox w90" type="text" /></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt1_${standardYear+loop.count}" id="mnt1_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt1_${endYear+1}" id="mnt1_${endYear+1}" value=""  class="inputbox w90" type="text" /></th>
						            	<th><input name="varMnt1" id="varMnt1" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer1" id="varPer1" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">매출원가</th>
						            	<td><input name="mnt2_${standardYear}" id="mnt2_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt2_${standardYear+loop.count}" id="mnt2_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt2_${endYear+1}" id="mnt2_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt2" id="varMnt2" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer2" id="varPer2" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">매출총이익</th>
						            	<th><input name="mnt3_${standardYear}" id="mnt3_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt3_${standardYear+loop.count}" id="mnt3_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt3_${endYear+1}" id="mnt3_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt3" id="varMnt3" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer3" id="varPer3" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">판매관리비</th>
						            	<td><input name="mnt4_${standardYear}" id="mnt4_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt4_${standardYear+loop.count}" id="mnt4_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt4_${endYear+1}" id="mnt4_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt4" id="varMnt4" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer4" id="varPer4" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;인건비</th>
						            	<td><input name="mnt5_${standardYear}" id="mnt5_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt5_${standardYear+loop.count}" id="mnt5_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt5_${endYear+1}" id="mnt5_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt5" id="varMnt5" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer5" id="varPer5" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;대손상각비</th>
						            	<td><input name="mnt6_${standardYear}" id="mnt6_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt6_${standardYear+loop.count}" id="mnt6_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt6_${endYear+1}" id="mnt6_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt6" id="varMnt6" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer6" id="varPer6" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td><input name="mnt7_${standardYear}" id="mnt7_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt7_${standardYear+loop.count}" id="mnt7_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt7_${endYear+1}" id="mnt7_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt7" id="varMnt7" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer7" id="varPer7" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">영업이익</th>
						            	<th><input name="mnt8_${standardYear}" id="mnt8_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt8_${standardYear+loop.count}" id="mnt8_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt8_${endYear+1}" id="mnt8_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt8" id="varMnt8" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer8" id="varPer8" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">영업외수익</th>
						            	<td><input name="mnt9_${standardYear}" id="mnt9_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt9_${standardYear+loop.count}" id="mnt9_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt9_${endYear+1}" id="mnt9_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt9" id="varMnt9" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer9" id="varPer9" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;이자수익</th>
						            	<td><input name="mnt10_${standardYear}" id="mnt10_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt10_${standardYear+loop.count}" id="mnt10_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt10_${endYear+1}" id="mnt10_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt10" id="varMnt10" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer10" id="varPer10" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td><input name="mnt11_${standardYear}" id="mnt11_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt11_${standardYear+loop.count}" id="mnt11_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt11_${endYear+1}" id="mnt11_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt11" id="varMnt11" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer11" id="varPer11" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">영업외비용</th>
						            	<td><input name="mnt12_${standardYear}" id="mnt12_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt12_${standardYear+loop.count}" id="mnt12_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt12_${endYear+1}" id="mnt12_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt12" id="varMnt12" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer12" id="varPer12" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;이자수익</th>
						            	<td><input name="mnt13_${standardYear}" id="mnt13_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt13_${standardYear+loop.count}" id="mnt13_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt13_${endYear+1}" id="mnt13_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt13" id="varMnt13" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer13" id="varPer13" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td><input name="mnt14_${standardYear}" id="mnt14_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt14_${standardYear+loop.count}" id="mnt14_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt14_${endYear+1}" id="mnt14_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt14" id="varMnt14" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer14" id="varPer14" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">법인세비용차감전이익</th>
						            	<th><input name="mnt15_${standardYear}" id="mnt15_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt15_${standardYear+loop.count}" id="mnt15_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt15_${endYear+1}" id="mnt15_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt15" id="varMnt15" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer15" id="varPer15" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">법인세비용</th>
						            	<td><input name="mnt16_${standardYear}" id="mnt16_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt16_${standardYear+loop.count}" id="mnt16_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt16_${endYear+1}" id="mnt16_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt16" id="varMnt16" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer16" id="varPer16" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">당기순이익</th>
						            	<th><input name="mnt17_${standardYear}" id="mnt17_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt17_${standardYear+loop.count}" id="mnt17_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt17_${endYear+1}" id="mnt17_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt17" id="varMnt17" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer17" id="varPer17" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						        </tbody>
						    </table>
						</div>
						</form>
	
					
					<!--blockButton Start-->
					<div class="blockButton"> 
					    <ul>
					    	<li><a class="button summaryBtn" href="#a"><span>자동합계</span></a></li>
					        <li><a class="button saveFinance" href="#a"><span>등록</span></a></li>
					       <!--  <li><a class="button" href="javascript:historyTab();"><span>다음단계</span></a></li> -->
					        <li><a class="button" href="javascript:goMenu(${finance.id});"><span>목록</span></a></li>
					    </ul>
					</div>
					<!--//blockButton End-->  
                </div>
                <div id="tabs-2"></div>
                <div id="tabs-3"></div>
            </div>				
        </div>		
        <!--//tab End-->

