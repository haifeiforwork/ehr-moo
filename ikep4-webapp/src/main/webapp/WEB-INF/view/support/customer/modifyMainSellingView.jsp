<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>



<script type="text/javascript">


(function($) {

	
	$(document).ready( function() {
		

		$("a.saveButton").click(function(){			
			$("#modifyMainSellingForm").trigger("submit"); 
			return false;
		});
		
		new iKEP.Validator("#modifyMainSellingForm", {   
			rules  : {
				sellingName     : {required : true},
				buyingEmployee  : {required : true},
				pName			: {required : true},
			},
			messages : {
				sellingName     : {direction : "top",    required : "<ikep4j:message key='NotNull.customer.mainselling.sellingName' />"},
				buyingEmployee  : {direction : "left",    required : "<ikep4j:message key='NotNull.customer.mainselling.buyingEmployee' />"},
				pName			: {direction : "bottom",  required : "<ikep4j:message key='NotNull.customer.mainselling.pName' />"},
			}, 	
		    submitHandler : function(form) {
		    				if(confirm("수정하시겠습니까?")){
								$("body").ajaxLoadStart("button");
								form.submit();
		    				}
			}
			
		}); 
		
		$("a.deleteButton").click(function(){
			var url = "<c:url value='/support/customer/customerMainSelling/deleteMainSelling.do'/>";
			
			if(confirm("삭제하시겠습니까?")){
				$.ajax({
					url : url,
					data :{'seqNum': '${mainSellingItem.seqNum}',
						   'registerId' : '${mainSellingItem.registerId}',
						   'customerId' : '${mainSellingItem.customerId}',
						   'customer' : '${mainSellingItem.customer}'},
					type : "get",
					success : function(data) {
						alert("삭제가 완료되었습니다.");				
						if(data != '0' ){
							location.href="<c:url value = '/support/customer/customerMainSelling/detailMainSellingView.do?customerId=${mainSellingItem.customerId}&customer=${mainSellingItem.customer}'/>";
						}else{
							location.href="<c:url value = '/support/customer/customerMainSelling/mainSellingList.do'/>";
						}
						
					},
					error : function(event, request, settings) { 
						alert("error");	
					}
				});	
			}
			
		}); 
		
		 
		 
		
	});
	

	
})(jQuery);



</script>




	

<form id="modifyMainSellingForm" name="modifyMainSellingForm" method="post" action="<c:url value='/support/customer/customerMainSelling/modifyMainSelling.do'/>" >
	<input id="customerId" name="customerId" type="hidden" value="${mainSellingItem.customerId}" />			
	<input id="customer" name="customer" type="hidden" value="${mainSellingItem.customer}" />			
	<input type="hidden"  name="seqNum" value="${mainSellingItem.seqNum}" />	
				<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>${mainSellingItem.customer} 주요판매처</h2>	
				</div>
				<!--//tableTop End-->
                
                <!--subTitle_1 Start-->
				<div class="subTitle_2 noline">
					<h3>주요판매처 수정</h3>
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
                            	<th scope="row">고객명</th>
								<td>${mainSellingItem.customer}</td>
								<th scope="row"><span class="colorPoint">*</span>판매처명</th>
								<td><input name="sellingName" title="판매처명" value="${mainSellingItem.sellingName}" class="inputbox w90" type="text" /></td>
                                <th scope="row">업종</th>
								<td><input name="sellingType" title="판매처업종" value="${mainSellingItem.sellingType}" class="inputbox w90" type="text" /></td>
                                <th scope="row"><span class="colorPoint">*</span>담당자</th>
								<td><input name="buyingEmployee" title="구매담당자" value="${mainSellingItem.buyingEmployee}" class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3"><input name="address" title="주소" value="${mainSellingItem.address}" class="inputbox w100" type="text" /></td>
                                <th scope="row">연락처</th>
								<td><input name="tel" title="연락처" value="${mainSellingItem.tel}" class="inputbox w90" type="text" /></td>
                                <th scope="row">팩스</th>
								<td><input name="fax" title="팩스" value="${mainSellingItem.fax}" class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row"><span class="colorPoint">*</span>구매지종</th>
								<td colspan="3"><input name="pName" title="구매지종" value="${mainSellingItem.pName}" class="inputbox w100" type="text" /></td>
                                <th scope="row">거래형태</th>
								<td><input name="dealForm" title="거래형태" value="${mainSellingItem.dealForm}" class="inputbox w90" type="text" /></td>
                                <th scope="row">구매량</th>
								<td><input name="buyingAmt" title="구매량" value="${mainSellingItem.buyingAmt}" class="inputbox w90" type="text" /></td>
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
                        <li><a class="button deleteButton" href="#a"><span>삭제</span></a></li>
                        <li><a class="button" href="javascript:history.back();"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                					
