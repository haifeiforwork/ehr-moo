<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<script type="text/javascript">

var customerId;
var customerName;

function sendCustomerId(id,name){
	customerId = id;
	customerName = name;
	//alert(customerId);
	//alert(customerName);
	
    opener.registerForm.customerId.value = customerId;
	
	opener.registerForm.customerName.value= customerName;
	opener.registerForm.customer.value= customerName;
	//alert(opener.CounselHistoryForm.customerId.value);
	//alert(opener.CounselHistoryForm.customerName.value);
	self.close(); 
}

</script>


<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>고객 찾기</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

    <!--subTitle_1 Start-->
	<div class="subTitle_2 noline">
		<h3>고객 리스트</h3>
	</div>
	<!--//subTitle_1 End-->	

 <div class="blockDetail">
<table summary="고객 찾기">
		<caption></caption>
		<colgroup>
			<col style="width: 50%;"/>
			<col style="width: 20%;"/>
			<col style="width: 30%;"/>
		</colgroup>
		<thead>
        <tr align="center">
            <th style="text-align:center">고객명</th>
            <th style="text-align:center">대표자</th>
            <th style="text-align:center">사업자등록번호</th>                   
        </tr>
        </thead>
	<tbody>
	<c:choose>
		<c:when test="${fn:length(customer) eq 0}">
			<tr align="center">
				<td colspan="3">검색된 결과가 없습니다.</td>
			</tr>
		</c:when>
		<c:otherwise>	
			<c:forEach var="customer" items="${customer}" varStatus="status">	
			<tr align="center">
				<td><a href="javascript:sendCustomerId('${customer.id}','${customer.name}')">${customer.name}</a></td>
				<td>${customer.director}</td>
				<td>${customer.regno}</td>
			</tr>
			</c:forEach>
	</c:otherwise>
	</c:choose>
	</tbody>
</table>

</div>
<div class="clear"></div>
<div class="blockBlank_10px"></div>		
