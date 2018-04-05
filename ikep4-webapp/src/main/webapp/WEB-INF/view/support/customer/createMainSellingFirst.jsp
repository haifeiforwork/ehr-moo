<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<script type="text/javascript">

(function($) {

	
	$(document).ready( function() {
		
		$("a.saveButton").click(function(){

		  		var url = "<c:url value='/support/customer/customerMainSelling/checkMainSelling.do'/>";
				var customerId =  $("#customerId").val();
		  	$.ajax({
				url : url,
				data :{'customerId': customerId },
				type : "get",
				success : function(data) {
				    if(!data){
						$("#registerForm").trigger("submit"); 
					}else{
						alert("기존에 등록된 고객사가 존재합니다.");
					}  
				},
				error : function(event, request, settings) { 
					alert("error");	
				}
			});	
		
		});
		
		new iKEP.Validator("#registerForm", {   
			rules  : {
				customer		: {required : true},
				sellingName     : {required : true},
				buyingEmployee  : {required : true},
				pName			: {required : true}
			},
			messages : {
				customer        : {direction : "top",    required : "<ikep4j:message key='NotNull.customer.mainselling.customer' />"},
				sellingName     : {direction : "top",    required : "<ikep4j:message key='NotNull.customer.mainselling.sellingName' />"},
				buyingEmployee  : {direction : "left",    required : "<ikep4j:message key='NotNull.customer.mainselling.buyingEmployee' />"},
				pName			: {direction : "bottom",  required : "<ikep4j:message key='NotNull.customer.mainselling.pName' />"}		
			},   
			
		    submitHandler : function(form) { 
			
				var customerId = $("#customerId").val();
				
		    	if(confirm("저장하시겠습니까?")) {

					if( customerId == ''){
						alert("고객명은 검색한 결과만 사용 가능합니다.");
						$("#customer").focus();
					}else{	
					$("body").ajaxLoadStart("button");
					form.submit();
					}
				
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

<form id="registerForm" name="mainSellingFirstForm" method="post" action="<c:url value='/support/customer/customerMainSelling/createMainSellingFirst.do'/>" >
<input type="hidden" id="customerId" name="customerId" value="" /> 
<input type="hidden" name="customerName" value="" />


<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>주요 판매처</h2>	
				</div>
				<!--//tableTop End-->
                
                <!--subTitle_1 Start-->
				<div class="subTitle_2 noline">
					<h3>주요 판매처 등록</h3>
				</div>
				<!--//subTitle_1 End-->	
				
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="주요판매처">
						<caption></caption>
                        <colgroup>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                        </colgroup>
						<tbody>
							<tr>
                            	<th scope="row" ><span class="colorPoint">*</span>고객명</th>
								<td colspan="7"><input id="customer" name="customer" title="고객명" class="inputbox w80" type="text" size="30" />
								<a class="ic_search valign_middle searchCustomerId" href="#a"><span>검색</span></a></td>
							</tr>
               		  		<tr>                           	
								<th scope="row"><span class="colorPoint">*</span>판매처명</th>
								<td colspan="3"><input name="sellingName" title="판매처명" class="inputbox w90" type="text"  /></td>
                                <th scope="row">판매처업종</th>
								<td><input name="sellingType" title="판매처업종" class="inputbox w90" type="text"  /></td>
                                <th scope="row"><span class="colorPoint">*</span>구매담당자</th>
								<td><input name="buyingEmployee" title="구매담당자" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3"><input name="address" title="주소" class="inputbox w90" type="text" /></td>
                                <th scope="row">연락처</th>
								<td><input name="tel" title="연락처" class="inputbox w90" type="text"  /></td>
                                <th scope="row">팩스</th>
								<td><input name="fax" title="팩스" class="inputbox w90" type="text"  /></td>
							</tr>
                            <tr>
								<th scope="row"><span class="colorPoint">*</span>구매지종</th>
								<td colspan="3"><input name="pName" title="구매지종" class="inputbox w90" type="text"  /></td>
                                <th scope="row">거래형태</th>
								<td><input name="dealForm" title="거래형태" class="inputbox w90" type="text" /></td>
                                <th scope="row">구매량</th>
								<td><input name="buyingAmt" title="구매량" class="inputbox w90" type="text"  /></td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
      </form>
                <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button saveButton" href="#a"><span>등록</span></a></li>
                       <!--  <li><a class="button" href="#a"><span>삭제</span></a></li> -->
                        <li><a class="button" href="<c:url value='/support/customer/customerMainSelling/mainSellingList.do'/>"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                					
