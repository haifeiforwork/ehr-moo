<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<script type="text/javascript">

(function($) {

	
	$(document).ready( function() {
		

		$("a.saveButton").click(function(){			
			$("#createMainSellingForm").trigger("submit"); 
			return false;
		});
		
		new iKEP.Validator("#createMainSellingForm", {   
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
							$("body").ajaxLoadStart("button");
							form.submit();
			}
			
		}); 
		
		 
		 
		 
		
	});
	

	
})(jQuery);



</script>



<form id="createMainSellingForm" name="createMainSellingForm" method="post" action="<c:url value='/support/customer/customerMainSelling/createMainSelling.do'/>" >
	<input id="customerId" name="customerId" type="hidden" value="${mainSelling.customerId}" />			
	<input id="customer" name="customer" type="hidden" value="${mainSelling.customer}" />			
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
                            	<th scope="row">고객명</th>
								<td>${mainSelling.customer}</td>
								<th scope="row"><span class="colorPoint">*</span>판매처명</th>
								<td><input name="sellingName" title="판매처명" class="inputbox w90" type="text"  /></td>
                                <th scope="row">판매처업종</th>
								<td><input name="sellingType" title="판매처업종" class="inputbox w90" type="text"  /></td>
                                <th scope="row"><span class="colorPoint">*</span>구매담당자</th>
								<td><input name="buyingEmployee" title="구매담당자" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td colspan="3"><input name="address" title="주소" class="inputbox w90" type="text" /></td>
                                <th scope="row">연락처</th>
								<td><input name="tel" title="연락처" class="inputbox w90" type="text" /></td>
                                <th scope="row">팩스</th>
								<td><input name="fax" title="팩스" class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row"><span class="colorPoint">*</span>구매지종</th>
								<td colspan="3"><input name="pName" title="구매지종" class="inputbox w90" type="text" /></td>
                                <th scope="row">거래형태</th>
								<td><input name="dealForm" title="거래형태" class="inputbox w90" type="text"  /></td>
                                <th scope="row">구매량</th>
								<td><input name="buyingAmt" title="구매량" class="inputbox w90" type="text" /></td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
</form>                
                
                <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button saveButton" href="#a"><span>저장</span></a></li>
                       <!--  <li><a class="button" href="#a"><span>삭제</span></a></li> -->
                        <li><a class="button" href="javascript:history.back();"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                					
