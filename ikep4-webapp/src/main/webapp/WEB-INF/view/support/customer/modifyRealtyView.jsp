<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />


<script type="text/javascript">
var fileController = "";		//파일등록폼
var realtyCnt = 0;
(function($) {

	$(document).ready( function() {
		

		$("a.createRealty").click(function(){	
			var tempno = "";  
			var tempdivision = ""; 
			var tempaddress = "";  
			var tempowner = "";    
			var temprelation = ""; 
			var temppyeongSpace = "";   
			var temppyeongBuilding = "";
			var tempmeterSpace = "";    
			var tempmeterBuilding = ""; 
			var tempmeterSum = ""; 
			var temptotalSum = ""; 
			var temprightDate = "";
			var tempcollateral = "";    
			var temprightSum = ""; 
			var tempdebt = "";
			var tempnote = "";
			var no = "";  
			var division = ""; 
			var address = "";  
			var owner = "";    
			var relation = ""; 
			var pyeongSpace = "";   
			var pyeongBuilding = "";
			var meterSpace = "";    
			var meterBuilding = ""; 
			var meterSum = ""; 
			var totalSum = ""; 
			var rightDate = "";
			var collateral = "";    
			var rightSum = ""; 
			var debt = "";
			var note = "";
			
			for(i=0;i<realtyCnt;i++){
				tempno = $("#no"+i).val(); 
				tempdivision = $("#division"+i).val();  
				tempaddress = $("#address"+i).val();   
				tempowner = $("#owner"+i).val();     
				temprelation = $("#relation"+i).val();   
				temppyeongSpace = $("#pyeongSpace"+i).val();    
				temppyeongBuilding = $("#pyeongBuilding"+i).val(); 
				tempmeterSpace = $("#meterSpace"+i).val();     
				tempmeterBuilding = $("#meterBuilding"+i).val();   
				tempmeterSum = $("#meterSum"+i).val();  
				temptotalSum = $("#totalSum"+i).val();  
				temprightDate = $("#rightDate"+i).val(); 
				tempcollateral = $("#collateral"+i).val();      
				temprightSum = $("#rightSum"+i).val();  
				tempdebt = $("#debt"+i).val(); 
				tempnote = $("#note"+i).val(); 
				if(i == 0){
					no = tempno;
					division = tempdivision;
					address = tempaddress;
					owner = tempowner;
					relation = temprelation;
					pyeongSpace = temppyeongSpace;
					pyeongBuilding = temppyeongBuilding;
					meterSpace = tempmeterSpace;
					meterBuilding = tempmeterBuilding;
					meterSum = tempmeterSum;
					totalSum = temptotalSum;
					rightDate = temprightDate;
					collateral = tempcollateral;
					rightSum = temprightSum;
					debt = tempdebt;
					note = tempnote;
				}else{
					no = no+"|"+tempno;
					division = division+"|"+tempdivision;
					address = address+"|"+tempaddress;
					owner = owner+"|"+tempowner;
					relation = relation+"|"+temprelation;
					pyeongSpace = pyeongSpace+"|"+temppyeongSpace;
					pyeongBuilding = pyeongBuilding+"|"+temppyeongBuilding;
					meterSpace = meterSpace+"|"+tempmeterSpace;
					meterBuilding = meterBuilding+"|"+tempmeterBuilding;
					meterSum = meterSum+"|"+tempmeterSum;
					totalSum = totalSum+"|"+temptotalSum;
					rightDate = rightDate+"|"+temprightDate;
					collateral = collateral+"|"+tempcollateral;
					rightSum = rightSum+"|"+temprightSum;
					debt = debt+"|"+tempdebt;
					note = note+"|"+tempnote;
				}
			}
		
			$jq('input[name=no]').val(no);  
			$jq('input[name=division]').val(division); 
			$jq('input[name=address]').val(address);  
			$jq('input[name=owner]').val(owner);    
			$jq('input[name=relation]').val(relation); 
			$jq('input[name=pyeongSpace]').val(pyeongSpace);   
			$jq('input[name=pyeongBuilding]').val(pyeongBuilding);
			$jq('input[name=meterSpace]').val(meterSpace);    
			$jq('input[name=meterBuilding]').val(meterBuilding); 
			$jq('input[name=meterSum]').val(meterSum); 
			$jq('input[name=totalSum]').val(totalSum); 
			$jq('input[name=rightDate]').val(rightDate);
			$jq('input[name=collateral]').val(collateral);    
			$jq('input[name=rightSum]').val(rightSum); 
			$jq('input[name=debt]').val(debt);
			$jq('input[name=note]').val(note);
			$("#registerForm").trigger("submit"); 
			return false;
		});
		
		var no = "${realty.no}";
		var division = "${realty.division}";
		var address = "${realty.address}";  
		var owner = "${realty.owner}";    
		var relation = "${realty.relation}"; 
		var pyeongSpace = "${realty.pyeongSpace}";   
		var pyeongBuilding = "${realty.pyeongBuilding}";
		var meterSpace = "${realty.meterSpace}";    
		var meterBuilding = "${realty.meterBuilding}"; 
		var meterSum = "${realty.meterSum}"; 
		var totalSum = "${realty.totalSum}"; 
		var rightDate = "${realty.rightDate}";
		var collateral = "${realty.collateral}";    
		var rightSum = "${realty.rightSum}"; 
		var debt = "${realty.debt}";
		var note = "${realty.note}";
		var tempno = no.split("|");  
		var tempdivision = division.split("|"); 
		var tempaddress = address.split("|");  
		var tempowner = owner.split("|");    
		var temprelation = relation.split("|"); 
		var temppyeongSpace = pyeongSpace.split("|");   
		var temppyeongBuilding = pyeongBuilding.split("|");
		var tempmeterSpace = meterSpace.split("|");    
		var tempmeterBuilding = meterBuilding.split("|"); 
		var tempmeterSum = meterSum.split("|"); 
		var temptotalSum = totalSum.split("|"); 
		var temprightDate = rightDate.split("|");
		var tempcollateral = collateral.split("|");    
		var temprightSum = rightSum.split("|"); 
		var tempdebt = debt.split("|");
		var tempnote = note.split("|");

		for(i=0;i<tempno.length;i++){
			$jq("#realtyBody").append(
				"<tr>"+
					"<td style=\"text-align:center;\">"+tempno[i]+"<input name=\"no"+i+"\" id=\"no"+i+"\" class=\"inputbox w80\" type=\"hidden\" value=\""+tempno[i]+"\"  /></td>"+
					"<td><input name=\"division"+i+"\" id=\"division"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempdivision[i]+"\"  /></td>"+
					"<td><input name=\"address"+i+"\" id=\"address"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempaddress[i]+"\"  /></td>"+
					"<td><input name=\"owner"+i+"\" id=\"owner"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempowner[i]+"\"  /></td>"+
					"<td><input name=\"relation"+i+"\" id=\"relation"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+temprelation[i]+"\"  /></td>"+
					"<td><input name=\"pyeongSpace"+i+"\" id=\"pyeongSpace"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+temppyeongSpace[i]+"\"  /></td>"+
					"<td><input name=\"pyeongBuilding"+i+"\" id=\"pyeongBuilding"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+temppyeongBuilding[i]+"\"  /></td>"+
					"<td><input name=\"meterSpace"+i+"\" id=\"meterSpace"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempmeterSpace[i]+"\"  /></td>"+
					"<td><input name=\"meterBuilding"+i+"\" id=\"meterBuilding"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempmeterBuilding[i]+"\"  /></td>"+
					"<td><input name=\"meterSum"+i+"\" id=\"meterSum"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempmeterSum[i]+"\"  /></td>"+
					"<td><input name=\"totalSum"+i+"\" id=\"totalSum"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+temptotalSum[i]+"\"  /></td>"+
					"<td><input name=\"rightDate"+i+"\" id=\"rightDate"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+temprightDate[i]+"\"  /></td>"+
					"<td><input name=\"collateral"+i+"\" id=\"collateral"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempcollateral[i]+"\"  /></td>"+
					"<td><input name=\"debt"+i+"\" id=\"rightSum"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+temprightSum[i]+"\"  /></td>"+
					"<td><input name=\"debt"+i+"\" id=\"debt"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempdebt[i]+"\"  /></td>"+
					"<td><input name=\"note"+i+"\" id=\"note"+i+"\" class=\"inputbox w80\" type=\"text\" value=\""+tempnote[i]+"\"  /></td>"+
				"</tr>"
			);
			realtyCnt = i+1;
		}
		
		$("a.realtyPlus").click(function(){	
			var tmpNo = realtyCnt+1;
		$jq("#realtyBody").append(
			"<tr>"+
				"<td style=\"text-align:center;\">"+tmpNo+"<input name=\"no"+realtyCnt+"\" id=\"no"+realtyCnt+"\"  class=\"inputbox w80\" type=\"hidden\" value=\""+tmpNo+"\" /></td>"+
				"<td><input name=\"division"+realtyCnt+"\" id=\"division"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"address"+realtyCnt+"\" id=\"address"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"owner"+realtyCnt+"\" id=\"owner"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"relation"+realtyCnt+"\" id=\"relation"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"pyeongSpace"+realtyCnt+"\" id=\"pyeongSpace"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"pyeongBuilding"+realtyCnt+"\" id=\"pyeongBuilding"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"meterSpace"+realtyCnt+"\" id=\"meterSpace"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"meterBuilding"+realtyCnt+"\" id=\"meterBuilding"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"meterSum"+realtyCnt+"\" id=\"meterSum"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"totalSum"+realtyCnt+"\" id=\"totalSum"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"rightDate"+realtyCnt+"\"   id=\"rightDate"+realtyCnt+"\" class=\"inputbox w50 datepicker\" type=\"text\" /><img src=\"<c:url value="/base/images/icon/ic_cal.gif"/>\" align=\"absmiddle\" alt=\"calendar\"/></td>"+
				"<td><input name=\"collateral"+realtyCnt+"\" id=\"collateral"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"rightSum"+realtyCnt+"\" id=\"rightSum"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"debt"+realtyCnt+"\" id=\"debt"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
				"<td><input name=\"note"+realtyCnt+"\" id=\"note"+realtyCnt+"\"  class=\"inputbox w80\" type=\"text\"  />"+
			"</tr>"
		);
		
		
		$jq("input[name=rightDate"+realtyCnt+"]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		realtyCnt++;
		iKEP.iFrameContentResize();
	});

		 
		new iKEP.Validator("#registerForm", {   
			rules  : {
				subject     : {required : true},
				counselDate : {required : true},
				customer    : {required : true},
				content		: {required : true}
			},
			messages : {
				subject     : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.title" />"},
				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.counselDate" />"}, 
				customer    : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.customer" />"},
				content     : {direction : "bottom",required  : "<ikep4j:message key="NotNull.customer.qualityClaim.content" />"}		
			},    
			
		    submitHandler : function(form) { 
			

		    	
		    	if(confirm("저장하시겠습니까?")) {
		    			writeSubmit(form);
				}
		    }
		}); 
		
		
	  // dextFileUploadInit(""+20*1024*1024 ,"1", "all");

		   
		   
		writeSubmit = function(targetForm){
			
		
			$("body").ajaxLoadStart("button");
			targetForm.submit();
		
			
			
			
			
		};
		
		
		$("#searchButton").click(function(){
			searchCustomer();
		});
		
		goSearch = function(){
			
		}

	 	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#customer').keypress( function(event) {
        	if(event.keyCode == '13'){
				searchCustomer();
        		return false;
        	}
		});
	 	/*
		 var uploaderOptions = {// 사용할 수 있는 옵션 항목
					<c:if test="${!empty(fileDataListJson)}">
						files  :  ${fileDataListJson}, 
					</c:if>
				    // isUpdate : true,    // 등록 및 수정일때 true
			        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
			        maxTotalSize : 20*1024*1024,    // 최대 업로드 가능 용량(개별 파일 사이즈임)
			        allowFileType  : "all"    
		 };
		 
		var fileController = new iKEP.FileController("#registerForm", "#fileUploadArea", uploaderOptions); 
		*/
		 
		//달력
		$("input[name=counselDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
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
	<h2>부동산 정보</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->


<!--subTitle_1 Start-->
<div class="subTitle_2 noline">
	<h3>부동산 정보</h3>
</div>
<!--//subTitle_1 End-->



<form id="registerForm" name="registerForm" method="post" action="<c:url value='/support/customer/customerRealty/modifyRealty.do'/>" enctype="multipart/form-data" >
<%-- <input type="hidden" id="customerId" name="customerId" value="${realty.customerId}" /> 
<input type="hidden" name="customerName" value="${realty.customerName}" /> --%>
<!--blockDetail Start-->
<input type="hidden" name="seqNum" value="${realty.seqNum}" />
<input type="hidden" name="customerId" id="customerId" value="${realty.customerId}" />
<input type="hidden" name="customerName" value="${realty.customerName}" />
<input type="hidden" name="no" value=""/>  
<input type="hidden" name="division" value=""/> 
<input type="hidden" name="address" value=""/>  
<input type="hidden" name="owner" value=""/>    
<input type="hidden" name="rightDate" value=""/>  
<input type="hidden" name="relation" value=""/> 
<input type="hidden" name="pyeongSpace" value=""/>   
<input type="hidden" name="pyeongBuilding" value=""/>
<input type="hidden" name="meterSpace" value=""/>    
<input type="hidden" name="meterBuilding" value=""/> 
<input type="hidden" name="meterSum" value=""/> 
<input type="hidden" name="totalSum" value=""/> 
<input type="hidden" name="collateral" value=""/>    
<input type="hidden" name="rightSum" value=""/> 
<input type="hidden" name="debt" value=""/>
<input type="hidden" name="note" value=""/>
<!--blockDetail Start-->
<div class="blockDetail">
	<table>
		<caption></caption>
		<colgroup>
			<col width="3%" />
			<col width="6%" />
			<col width="10%" />
			<col width="6%" />
			<col width="8%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="5%" />
			<col width="7%" />
			<col width="6%" />
			<col width="7%" />
			<col width="8%" />
			<col width="7%" />
			<col width="6%" />
			<col width="6%" />
		</colgroup>
		<tbody id="realtyBody">
			<tr>
				<th colspan="2" style="text-align:center;">고객명</th>
				<td colspan="14">${realty.customerName}
				</td>
			</tr>
			<tr>
				<th rowspan="2" style="text-align:center;">NO.</th>
				<th rowspan="2" style="text-align:center;">구분</th>
				<th rowspan="2" style="text-align:center;">주소</th>
				<th rowspan="2" style="text-align:center;">소유자</th>
				<th rowspan="2" style="text-align:center;">소유자와의<br/>관계</th>
				<th colspan="2" style="text-align:center;">면적(단위:평)</th>
				<th colspan="2" style="text-align:center;">면적(단위:㎡)</th>
				<th colspan="2" style="text-align:center;">개별공시지가</th>
				<th colspan="4" style="text-align:center;">권리관계</th>
				<th rowspan="2" style="text-align:center;">비고<br/>(권리침해사항)</th>
			</tr>
			<tr>
				<th style="text-align:center;">대지</th>
				<th style="text-align:center;">건물</th>
				<th style="text-align:center;">대지</th>
				<th style="text-align:center;">건물</th>
				<th style="text-align:center;">㎡당 금액</th>
				<th style="text-align:center;">총금액</th>
				<th style="text-align:center;">설정일자</th>
				<th style="text-align:center;">근저당권자</th>
				<th style="text-align:center;">설정금액</th>
				<th style="text-align:center;">채무자</th>
			</tr>
		</tbody>
	</table>
	<table><tr><td style="text-align:right;border:0 0 0 0;">
		<a class="realtyPlus" href="#a">
		<img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" />
		</a>
	</td></tr></table>
	
</div>
<!--//blockDetail End-->
</form>

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a class="button createRealty" href="#a"><span>등록</span>
		</a>
		</li>
		<li><a class="button" href="<c:url value='/support/customer/customerRealty/realtyList.do'/>"><span>목록</span>
		</a>
		</li>		
	</ul>
</div>
<!--//blockButton End-->