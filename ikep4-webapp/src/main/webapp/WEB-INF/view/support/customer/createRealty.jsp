<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
var fileController = "";		//파일등록폼
var realtyCnt = 1;
(function($) {

	
	$(document).ready( function() {
		
	     function zeroPad(n) {
             return (n < 10 ? '0' : '') + n;
       }
    	
		
		 var date = new Date();
	     var year = date.getFullYear();
         var monthTmp = date.getMonth()+1;
         var month = zeroPad(monthTmp);        
         var dayTmp = date.getDate();
         var day = zeroPad(dayTmp);
         var nowDate = year+"."+month+"."+day;
		 $("#counselDate").val(nowDate);
		
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
			},
			messages : {
			},   
			
		    submitHandler : function(form) { 
			
				
				
		    	if(confirm("저장하시겠습니까?")) {
		    		
		    		/*
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) {
							
							if( customerId == ''){
								alert("고객명은 검색한 결과만 사용 가능합니다.");
								$("#customer").focus();
							}else{	
							$("body").ajaxLoadStart("button");
							form.submit();
							}
						}
					});
					*/
					
					//if(document.all["FileUploadManager"].Count>0){
		    		//	btnTransfer_Onclick("registerForm");
		    		//}else{
		    			//alert("파일업로드 없음");
		    			writeSubmit(form);
		    		//}
				}
		    }
		}); 
		
		
		$("a.searchCustomerId").click(function(){
			searchCustomer();
		});

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
						files  :  ${fileDataListJson}, // 파일 목록 
					</c:if>
				    // isUpdate : true,    // 등록 및 수정일때 true
			        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
			        maxTotalSize : 20*1024*1024,    // 최대 업로드 가능 용량(개별 파일 사이즈임)
			        allowFileType  : "all"    
		 };
		
		var fileController = new iKEP.FileController("#registerForm", "#fileUploadArea", uploaderOptions); 
		*/
		 
		//달력
		$("input[name=rightDate0]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		//dextFileUploadInit(""+20*1024*1024 ,"1", "all");
	    
		writeSubmit = function(targetForm){
			
			var customerId = $("#customerId").val();
			
			if( customerId == ''){
				alert("<ikep4j:message key='NotNull.customer.mainselling.customer' />");
				$("#customer").focus();
			}else{	
				$("body").ajaxLoadStart("button");
				targetForm.submit();
			}
		};
		
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



<form id="registerForm" name="registerForm" method="post" action="<c:url value='/support/customer/customerRealty/createRealty.do'/>" enctype="multipart/form-data" >
<input type="hidden" id="customerId" name="customerId" value="" /> 
<input type="hidden" name="customerName" value="" />
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
				<td colspan="14"><input id="customer" name="customer" title="고객명" class="inputbox w40" type="text"  /> 
				<a class="ic_search valign_middle searchCustomerId" href="#a"><span>검색</span></a>
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
			<tr>
				<td style="text-align:center;">1<input name="no0" id="no0" title="" class="inputbox w80" type="hidden" value="1" /></td>
				<td><input name="division0" id="division0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="address0" id="address0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="owner0" id="owner0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="relation0" id="relation0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="pyeongSpace0" id="pyeongSpace0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="pyeongBuilding0" id="pyeongBuilding0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="meterSpace0" id="meterSpace0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="meterBuilding0" id="meterBuilding0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="meterSum0" id="meterSum0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="totalSum0" id="totalSum0" title="" class="inputbox w90" type="text"  /></td>
				<td>
					<input name="rightDate0"   id="rightDate0" class="inputbox w50 datepicker" type="text" value=""  /> 
                  	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>    
				</td>
				<td><input name="collateral0" id="collateral0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="rightSum0" id="rightSum0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="debt0" id="debt0" title="" class="inputbox w90" type="text"  /></td>
				<td><input name="note0" id="note0" title="" class="inputbox w80" type="text"  /></td>
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