<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:forEach var="condition" items="${conditionList}" varStatus="ccnt">
<c:set var="ctcnt" value="${ccnt.count-2}"/>
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
		var id = $("#id").val();
	
		var historyCnt = $jq("#historyBody").children("tr").length;
		
		
		
	/* 	//달력
		$("input[name=yearDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); }); */
		$("#divTab2").tabs();
	
		
    	tab = $("#divTab2").tabs({
    		selected : 1, 
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
		var ccnt = "${ctcnt}";
    	/*if(ccnt > eyear-syear+1){
    		$(".yearPlus").show();
    		$(".yearPlusFnc").hide();
    		$("#yearMoreOff").hide();
    		$("#yearMoreOn").show();
    		openYear = 1;
    		iKEP.iFrameContentResize();
    	}*/
	
    	<c:forEach var="condition" items="${conditionList}">
    	if("${condition.divInfo}" == ""){
    		if("${condition.yearInfo}" > eyear){
        		$(".yearPlus").show();
        		$(".yearPlusFnc").hide();
        		$("#yearMoreOff").hide();
        		$("#yearMoreOn").show();
        		openYear = 1;
        		iKEP.iFrameContentResize();
        	}
    		$jq("#mnt1_${condition.yearInfo}").val("${condition.mnt1}");
    		$jq("#mnt2_${condition.yearInfo}").val("${condition.mnt2}");
    		$jq("#mnt3_${condition.yearInfo}").val("${condition.mnt3}");
    		$jq("#mnt4_${condition.yearInfo}").val("${condition.mnt4}");
    		$jq("#mnt5_${condition.yearInfo}").val("${condition.mnt5}");
    		$jq("#mnt6_${condition.yearInfo}").val("${condition.mnt6}");
    		$jq("#mnt7_${condition.yearInfo}").val("${condition.mnt7}");
    		$jq("#mnt8_${condition.yearInfo}").val("${condition.mnt8}");
    		$jq("#mnt9_${condition.yearInfo}").val("${condition.mnt9}");
    		$jq("#mnt10_${condition.yearInfo}").val("${condition.mnt10}");
    		$jq("#mnt11_${condition.yearInfo}").val("${condition.mnt11}");
    		$jq("#mnt12_${condition.yearInfo}").val("${condition.mnt12}");
    		$jq("#mnt13_${condition.yearInfo}").val("${condition.mnt13}");
    		$jq("#mnt14_${condition.yearInfo}").val("${condition.mnt14}");
    		$jq("#mnt15_${condition.yearInfo}").val("${condition.mnt15}");
    		$jq("#mnt16_${condition.yearInfo}").val("${condition.mnt16}");
    		$jq("#mnt17_${condition.yearInfo}").val("${condition.mnt17}");
    		$jq("#mnt18_${condition.yearInfo}").val("${condition.mnt18}");
    		$jq("#mnt19_${condition.yearInfo}").val("${condition.mnt19}");
    		$jq("#mnt20_${condition.yearInfo}").val("${condition.mnt20}");
    		$jq("#mnt21_${condition.yearInfo}").val("${condition.mnt21}");
    		$jq("#mnt22_${condition.yearInfo}").val("${condition.mnt22}");
    		$jq("#mnt23_${condition.yearInfo}").val("${condition.mnt23}");
    		$jq("#mnt24_${condition.yearInfo}").val("${condition.mnt24}");
    		$jq("#mnt25_${condition.yearInfo}").val("${condition.mnt25}");
    		$jq("#mnt26_${condition.yearInfo}").val("${condition.mnt26}");
    		$jq("#mnt27_${condition.yearInfo}").val("${condition.mnt27}");
    		$jq("#mnt28_${condition.yearInfo}").val("${condition.mnt28}");
    		$jq("#mnt29_${condition.yearInfo}").val("${condition.mnt29}");
    	}else if("${condition.divInfo}" == "mount"){
    		$jq("#varMnt1").val("${condition.mnt1}");
    		$jq("#varMnt2").val("${condition.mnt2}");
    		$jq("#varMnt3").val("${condition.mnt3}");
    		$jq("#varMnt4").val("${condition.mnt4}");
    		$jq("#varMnt5").val("${condition.mnt5}");
    		$jq("#varMnt6").val("${condition.mnt6}");
    		$jq("#varMnt7").val("${condition.mnt7}");
    		$jq("#varMnt8").val("${condition.mnt8}");
    		$jq("#varMnt9").val("${condition.mnt9}");
    		$jq("#varMnt10").val("${condition.mnt10}");
    		$jq("#varMnt11").val("${condition.mnt11}");
    		$jq("#varMnt12").val("${condition.mnt12}");
    		$jq("#varMnt13").val("${condition.mnt13}");
    		$jq("#varMnt14").val("${condition.mnt14}");
    		$jq("#varMnt15").val("${condition.mnt15}");
    		$jq("#varMnt16").val("${condition.mnt16}");
    		$jq("#varMnt17").val("${condition.mnt17}");
    		$jq("#varMnt18").val("${condition.mnt18}");
    		$jq("#varMnt19").val("${condition.mnt19}");
    		$jq("#varMnt20").val("${condition.mnt20}");
    		$jq("#varMnt21").val("${condition.mnt21}");
    		$jq("#varMnt22").val("${condition.mnt22}");
    		$jq("#varMnt23").val("${condition.mnt23}");
    		$jq("#varMnt24").val("${condition.mnt24}");
    		$jq("#varMnt25").val("${condition.mnt25}");
    		$jq("#varMnt26").val("${condition.mnt26}");
    		$jq("#varMnt27").val("${condition.mnt27}");
    		$jq("#varMnt28").val("${condition.mnt28}");
    		$jq("#varMnt29").val("${condition.mnt29}");
    	}else if("${condition.divInfo}" == "rate"){
    		$jq("#varPer1").val("${condition.mnt1}");
    		$jq("#varPer2").val("${condition.mnt2}");
    		$jq("#varPer3").val("${condition.mnt3}");
    		$jq("#varPer4").val("${condition.mnt4}");
    		$jq("#varPer5").val("${condition.mnt5}");
    		$jq("#varPer6").val("${condition.mnt6}");
    		$jq("#varPer7").val("${condition.mnt7}");
    		$jq("#varPer8").val("${condition.mnt8}");
    		$jq("#varPer9").val("${condition.mnt9}");
    		$jq("#varPer10").val("${condition.mnt10}");
    		$jq("#varPer11").val("${condition.mnt11}");
    		$jq("#varPer12").val("${condition.mnt12}");
    		$jq("#varPer13").val("${condition.mnt13}");
    		$jq("#varPer14").val("${condition.mnt14}");
    		$jq("#varPer15").val("${condition.mnt15}");
    		$jq("#varPer16").val("${condition.mnt16}");
    		$jq("#varPer17").val("${condition.mnt17}");		
    		$jq("#varPer18").val("${condition.mnt18}");
    		$jq("#varPer19").val("${condition.mnt19}");
    		$jq("#varPer20").val("${condition.mnt20}");
    		$jq("#varPer21").val("${condition.mnt21}");
    		$jq("#varPer22").val("${condition.mnt22}");	
    		$jq("#varPer23").val("${condition.mnt23}");
    		$jq("#varPer24").val("${condition.mnt24}");
    		$jq("#varPer25").val("${condition.mnt25}");
    		$jq("#varPer26").val("${condition.mnt26}");
    		$jq("#varPer27").val("${condition.mnt27}");	
    		$jq("#varPer28").val("${condition.mnt28}");	
    		$jq("#varMnt29").val("${condition.mnt29}");
    	}
    </c:forEach>
	

	$("a.saveHistory").click(function(){
		var styear = $("#stYear").val();
		var edyear = $("#edYear").val();
		var yearGroup = "";
		var mntGroup = "";
		var varMnt = "";
		var varPer = "";
		for(i=0;i<edyear-styear+1+openYear;i++){
			for(j=1;j<30;j++){
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
		for(i=1;i<30;i++){
			if(varMnt == ""){
				varMnt = "varMnt"+i+"/"+$("#varMnt"+i).val();
			}else{
				varMnt = varMnt+"^varMnt"+i+"/"+$("#varMnt"+i).val();
			}
		}
		
		for(i=1;i<30;i++){
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
		$("#financeConditionForm").trigger("submit");
	});
	
	$("a.summaryBtn").click(function(){
		var styear = $("#stYear").val();
		var edyear = $("#edYear").val();
		var yearGroup = "";
		var mntGroup = "";
		var varMnt = "";
		var varPer = "";
		for(i=0;i<edyear-styear+1+openYear;i++){
			for(j=1;j<30;j++){
				if(j == "5"){
					$("#mnt5_"+(Number(styear)+i)).val($("#mnt1_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt2_"+(Number(styear)+i)).val().replace(/,/gi,""));
				}
				if(j == "12"){
					$("#mnt12_"+(Number(styear)+i)).val($("#mnt6_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt7_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt8_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt13_"+(Number(styear)+i)).val().replace(/,/gi,""));
				}
				if(j == "14"){
					$("#mnt14_"+(Number(styear)+i)).val(Number($("#mnt1_"+(Number(styear)+i)).val().replace(/,/gi,"")) + Number($("#mnt6_"+(Number(styear)+i)).val().replace(/,/gi,"")));
				}
				if(j == "19"){
					$("#mnt19_"+(Number(styear)+i)).val($("#mnt15_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt16_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt17_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt18_"+(Number(styear)+i)).val().replace(/,/gi,""));
				}
				if(j == "23"){
					$("#mnt23_"+(Number(styear)+i)).val($("#mnt20_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt21_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt22_"+(Number(styear)+i)).val().replace(/,/gi,""));
				}
				if(j == "24"){
					$("#mnt24_"+(Number(styear)+i)).val(Number($("#mnt15_"+(Number(styear)+i)).val().replace(/,/gi,"")) + Number($("#mnt20_"+(Number(styear)+i)).val().replace(/,/gi,"")));
				}
				if(j == "28"){
					$("#mnt28_"+(Number(styear)+i)).val($("#mnt14_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt24_"+(Number(styear)+i)).val().replace(/,/gi,""));
					$("#mnt27_"+(Number(styear)+i)).val($("#mnt28_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt25_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt26_"+(Number(styear)+i)).val().replace(/,/gi,""));
				}
				if(j == "29"){
					$("#mnt29_"+(Number(styear)+i)).val($("#mnt2_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt3_"+(Number(styear)+i)).val().replace(/,/gi,"") - $("#mnt4_"+(Number(styear)+i)).val().replace(/,/gi,""));
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
		$("#varMnt18").val($("#mnt18_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt18_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt19").val($("#mnt19_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt19_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt20").val($("#mnt20_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt20_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt21").val($("#mnt21_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt21_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt22").val($("#mnt22_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt22_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt23").val($("#mnt23_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt23_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt24").val($("#mnt24_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt24_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt25").val($("#mnt25_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt25_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt26").val($("#mnt26_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt26_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt27").val($("#mnt27_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt27_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt28").val($("#mnt28_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt28_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		$("#varMnt29").val($("#mnt29_"+(Number(edyear))).val().replace(/,/gi,"") - $("#mnt29_"+(Number(edyear)-1)).val().replace(/,/gi,""));
		
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

		if($("#varMnt18").val() == "0" || $("#mnt18_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer18").val("0");
		}else{
			$("#varPer18").val((($("#varMnt18").val() / $("#mnt18_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt19").val() == "0" || $("#mnt19_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer19").val("0");
		}else{
			$("#varPer19").val((($("#varMnt19").val() / $("#mnt19_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt20").val() == "0" || $("#mnt20_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer20").val("0");
		}else{
			$("#varPer20").val((($("#varMnt20").val() / $("#mnt20_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt21").val() == "0" || $("#mnt21_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer21").val("0");
		}else{
			$("#varPer21").val((($("#varMnt21").val() / $("#mnt21_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt22").val() == "0" || $("#mnt22_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer22").val("0");
		}else{
			$("#varPer22").val((($("#varMnt22").val() / $("#mnt22_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt23").val() == "0" || $("#mnt23_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer23").val("0");
		}else{
			$("#varPer23").val((($("#varMnt23").val() / $("#mnt23_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt24").val() == "0" || $("#mnt24_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer24").val("0");
		}else{
			$("#varPer24").val((($("#varMnt24").val() / $("#mnt24_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt25").val() == "0" || $("#mnt25_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer25").val("0");
		}else{
			$("#varPer25").val((($("#varMnt25").val() / $("#mnt25_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt26").val() == "0" || $("#mnt26_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer26").val("0");
		}else{
			$("#varPer26").val((($("#varMnt26").val() / $("#mnt26_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt27").val() == "0" || $("#mnt27_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer27").val("0");
		}else{
			$("#varPer27").val((($("#varMnt27").val() / $("#mnt27_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

		if($("#varMnt28").val() == "0" || $("#mnt28_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer28").val("0");
		}else{
			$("#varPer28").val((($("#varMnt28").val() / $("#mnt28_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}
		
		if($("#varMnt29").val() == "0" || $("#mnt29_"+(Number(edyear)-1)).val() == "0"){
			$("#varPer29").val("0");
		}else{
			$("#varPer29").val((($("#varMnt29").val() / $("#mnt29_"+(Number(edyear)-1)).val().replace(/,/gi,""))*100).toFixed(1));
		}

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
	
	$jq('#customer').keypress( function(event) {
    	if(event.keyCode == '13'){
    		searchCustomer();
    		return false;
    	}
	});
	
	new iKEP.Validator("#financeConditionForm",{
		
		
		/* rules  : {
			yearDate     : {required : true, rangelength : [1, 100] },
			yearContent       : {required : true }
		},
		messages : {
			yearDate     : {direction : "top",    required : "<ikep4j:message key="message.collpack.collaboration.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"},
			yearContent       : {direction : "top", 
				required : "<ikep4j:message key="message.collpack.collaboration.NotBlank.boardItem.tag" />"
				} 
		}, */   
		
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
	
	$jq("#historyCnt").val(historyCnt-1);
	
	
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


 function addHistoryCnt(){
	var historyCnt = $jq("#historyBody").children("tr").length;

	 $jq("#historyBody").append(
			 "<tr>"+
				"<td class=\"textCenter\"><input name=\"yearDate\" title=\"날짜\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
             "<td><input name=\"yearContent\" title=\"주요내용\" class=\"inputbox w100\" type=\"text\" /></td>"+
			  "</tr>"
	); 
	 historyCnt = historyCnt++;
	
	 iKEP.iFrameContentResize();
	 $jq("#historyCnt").val(historyCnt);
	 
 
} 

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
                <div id="tabs-1"></div>
                <div id="tabs-2">
                
                <form id="financeConditionForm" method="post" action="<c:url value='/support/customer/customerFinance/saveFinanceCondition.do'/>" >
				    <input type="hidden" id="id" name="id" value="${param.id}" /> 
					<input type="hidden" name="historyCnt" id="historyCnt" value=""/>
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
						            	<th style="text-align:left;">유동자산</th>
						            	<th><input name="mnt1_${standardYear}" id="mnt1_${standardYear}" value=""  class="inputbox w90" type="text" /></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt1_${standardYear+loop.count}" id="mnt1_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt1_${endYear+1}" id="mnt1_${endYear+1}" value=""  class="inputbox w90" type="text" /></th>
						            	<th><input name="varMnt1" id="varMnt1" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer1" id="varPer1" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;당좌자산</th>
						            	<td><input name="mnt2_${standardYear}" id="mnt2_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt2_${standardYear+loop.count}" id="mnt2_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt2_${endYear+1}" id="mnt2_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt2" id="varMnt2" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer2" id="varPer2" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;현금및현금성자산</th>
						            	<td><input name="mnt3_${standardYear}" id="mnt3_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt3_${standardYear+loop.count}" id="mnt3_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt3_${endYear+1}" id="mnt3_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt3" id="varMnt3" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer3" id="varPer3" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;매출채권</th>
						            	<td><input name="mnt4_${standardYear}" id="mnt4_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt4_${standardYear+loop.count}" id="mnt4_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt4_${endYear+1}" id="mnt4_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt4" id="varMnt4" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer4" id="varPer4" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;기타</th>
						            	<td><input name="mnt29_${standardYear}" id="mnt29_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt29_${standardYear+loop.count}" id="mnt29_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt29_${endYear+1}" id="mnt29_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt29" id="varMnt29" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer29" id="varPer29" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;재고자산</th>
						            	<td><input name="mnt5_${standardYear}" id="mnt5_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt5_${standardYear+loop.count}" id="mnt5_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt5_${endYear+1}" id="mnt5_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt5" id="varMnt5" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer5" id="varPer5" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">비유동자산</th>
						            	<th><input name="mnt6_${standardYear}" id="mnt6_${standardYear}" value=""  class="inputbox w90" type="text" /></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt6_${standardYear+loop.count}" id="mnt6_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt6_${endYear+1}" id="mnt6_${endYear+1}" value=""  class="inputbox w90" type="text" /></th>
						            	<th><input name="varMnt6" id="varMnt6" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer6" id="varPer6" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;투자자산</th>
						            	<td><input name="mnt7_${standardYear}" id="mnt7_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt7_${standardYear+loop.count}" id="mnt7_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt7_${endYear+1}" id="mnt7_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt7" id="varMnt7" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer7" id="varPer7" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;유형자산</th>
						            	<td><input name="mnt8_${standardYear}" id="mnt8_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt8_${standardYear+loop.count}" id="mnt8_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt8_${endYear+1}" id="mnt8_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt8" id="varMnt8" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer8" id="varPer8" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;토지</th>
						            	<td><input name="mnt9_${standardYear}" id="mnt9_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt9_${standardYear+loop.count}" id="mnt9_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt9_${endYear+1}" id="mnt9_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt9" id="varMnt9" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer9" id="varPer9" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;건물</th>
						            	<td><input name="mnt10_${standardYear}" id="mnt10_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt10_${standardYear+loop.count}" id="mnt10_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt10_${endYear+1}" id="mnt10_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt10" id="varMnt10" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer10" id="varPer10" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;&nbsp;&nbsp;기계장치</th>
						            	<td><input name="mnt11_${standardYear}" id="mnt11_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt11_${standardYear+loop.count}" id="mnt11_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt11_${endYear+1}" id="mnt11_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt11" id="varMnt11" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer11" id="varPer11" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;무형자산</th>
						            	<td><input name="mnt12_${standardYear}" id="mnt12_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt12_${standardYear+loop.count}" id="mnt12_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt12_${endYear+1}" id="mnt12_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt12" id="varMnt12" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer12" id="varPer12" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타비유동자산</th>
						            	<td><input name="mnt13_${standardYear}" id="mnt13_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt13_${standardYear+loop.count}" id="mnt13_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt13_${endYear+1}" id="mnt13_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt13" id="varMnt13" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer13" id="varPer13" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">자산총계</th>
						            	<th><input name="mnt14_${standardYear}" id="mnt14_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt14_${standardYear+loop.count}" id="mnt14_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt14_${endYear+1}" id="mnt14_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt14" id="varMnt14" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer14" id="varPer14" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">유동부채</th>
						            	<th><input name="mnt15_${standardYear}" id="mnt15_${standardYear}" value=""  class="inputbox w90" type="text" /></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt15_${standardYear+loop.count}" id="mnt15_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt15_${endYear+1}" id="mnt15_${endYear+1}" value=""  class="inputbox w90" type="text" /></th>
						            	<th><input name="varMnt15" id="varMnt15" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer15" id="varPer15" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;매입채무</th>
						            	<td><input name="mnt16_${standardYear}" id="mnt16_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt16_${standardYear+loop.count}" id="mnt16_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt16_${endYear+1}" id="mnt16_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt16" id="varMnt16" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer16" id="varPer16" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;단기차입금</th>
						            	<td><input name="mnt17_${standardYear}" id="mnt17_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt17_${standardYear+loop.count}" id="mnt17_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt17_${endYear+1}" id="mnt17_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt17" id="varMnt17" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer17" id="varPer17" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;유동성장기부채</th>
						            	<td><input name="mnt18_${standardYear}" id="mnt18_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt18_${standardYear+loop.count}" id="mnt18_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt18_${endYear+1}" id="mnt18_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt18" id="varMnt18" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer18" id="varPer18" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td><input name="mnt19_${standardYear}" id="mnt19_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt19_${standardYear+loop.count}" id="mnt19_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt19_${endYear+1}" id="mnt19_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt19" id="varMnt19" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer19" id="varPer19" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">비유동부채</th>
						            	<th><input name="mnt20_${standardYear}" id="mnt20_${standardYear}" value=""  class="inputbox w90" type="text" /></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt20_${standardYear+loop.count}" id="mnt20_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt20_${endYear+1}" id="mnt20_${endYear+1}" value=""  class="inputbox w90" type="text" /></th>
						            	<th><input name="varMnt20" id="varMnt20" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer20" id="varPer20" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;장기차입금</th>
						            	<td><input name="mnt21_${standardYear}" id="mnt21_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt21_${standardYear+loop.count}" id="mnt21_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt21_${endYear+1}" id="mnt21_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt21" id="varMnt21" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer21" id="varPer21" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;사채</th>
						            	<td><input name="mnt22_${standardYear}" id="mnt22_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt22_${standardYear+loop.count}" id="mnt22_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt22_${endYear+1}" id="mnt22_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt22" id="varMnt22" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer22" id="varPer22" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">&nbsp;&nbsp;기타</th>
						            	<td><input name="mnt23_${standardYear}" id="mnt23_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt23_${standardYear+loop.count}" id="mnt23_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt23_${endYear+1}" id="mnt23_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt23" id="varMnt23" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer23" id="varPer23" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">부채총계</th>
						            	<th><input name="mnt24_${standardYear}" id="mnt24_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt24_${standardYear+loop.count}" id="mnt24_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt24_${endYear+1}" id="mnt24_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt24" id="varMnt24" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer24" id="varPer24" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">자본금</th>
						            	<td><input name="mnt25_${standardYear}" id="mnt25_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt25_${standardYear+loop.count}" id="mnt25_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt25_${endYear+1}" id="mnt25_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt25" id="varMnt25" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer25" id="varPer25" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">이익잉여금</th>
						            	<td><input name="mnt26_${standardYear}" id="mnt26_${standardYear}" value=""  class="inputbox w90" type="text" /></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt26_${standardYear+loop.count}" id="mnt26_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" /></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt26_${endYear+1}" id="mnt26_${endYear+1}" value=""  class="inputbox w90" type="text" /></td>
						            	<td><input name="varMnt26" id="varMnt26" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer26" id="varPer26" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;background-color:white;">기타</th>
						            	<td><input name="mnt27_${standardYear}" id="mnt27_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<td><input name="mnt27_${standardYear+loop.count}" id="mnt27_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	</c:forEach>
						            	<td class="yearPlus" style="display:none;"><input name="mnt27_${endYear+1}" id="mnt27_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varMnt27" id="varMnt27" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            	<td><input name="varPer27" id="varPer27" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;"/></td>
						            </tr>
						            <tr>
						            	<th style="text-align:left;">자본총계</th>
						            	<th><input name="mnt28_${standardYear}" id="mnt28_${standardYear}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<c:forEach begin="1" end="${endYear-standardYear}" varStatus="loop">
						            		<th><input name="mnt28_${standardYear+loop.count}" id="mnt28_${standardYear+loop.count}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	</c:forEach>
						            	<th class="yearPlus" style="display:none;"><input name="mnt28_${endYear+1}" id="mnt28_${endYear+1}" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varMnt28" id="varMnt28" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            	<th><input name="varPer28" id="varPer28" value=""  class="inputbox w90" type="text" readonly="readonly" style="border:none; border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px;background-color:#eff7fb;"/></th>
						            </tr>
						            
						        </tbody>
						    </table>
						</div>									
					            
					<!--blockButton Start-->
					<div class="blockButton"> 
					    <ul>
					    	<li><a class="button summaryBtn" href="#a"><span>자동합계</span></a></li>
					        <li><a class="button saveHistory" href="#a"><span>등록</span></a></li>
					       <!--  <li><a class="button" href="javascript:equipmentTab();"><span>다음단계</span></a></li> -->
					        <li><a class="button" href="javascript:goMenu(${param.id})"><span>목록</span></a></li>
					    </ul>
					</div>
					<!--//blockButton End-->
                </form>
                
                </div>
                <div id="tabs-3"></div>
            </div>				
        </div>		
        <!--//tab End-->




   
    

     
      