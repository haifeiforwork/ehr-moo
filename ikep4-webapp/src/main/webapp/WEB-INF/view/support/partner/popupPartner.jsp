<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<script type="text/javascript">

var partnerId;
var partnerName;

function sendPartnerId(id,name){
	partnerId = id;
	partnerName = name;
	//alert(partnerId);
	//alert(partnerName);
	
    opener.registerForm.partnerId.value = partnerId;
	
	opener.registerForm.partnerName.value= partnerName;
	opener.registerForm.partner.value= partnerName;
	//alert(opener.CounselHistoryForm.partnerId.value);
	//alert(opener.CounselHistoryForm.partnerName.value);
	self.close(); 
}

</script>


<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>거래처 찾기</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

    <!--subTitle_1 Start-->
	<div class="subTitle_2 noline">
		<h3>거래처 리스트</h3>
	</div>
	<!--//subTitle_1 End-->	

 <div class="blockDetail">
<table summary="거래처 찾기">
		<caption></caption>
		<colgroup>
			<col style="width: 50%;"/>
			<col style="width: 20%;"/>
			<col style="width: 30%;"/>
		</colgroup>
		<thead>
        <tr align="center">
            <th style="text-align:center">거래처명</th>
            <th style="text-align:center">대표자</th>
            <th style="text-align:center">업종</th>                   
        </tr>
        </thead>
	<tbody>
	<c:choose>
		<c:when test="${fn:length(partner) eq 0}">
			<tr align="center">
				<td colspan="3">검색된 결과가 없습니다.</td>
			</tr>
		</c:when>
		<c:otherwise>	
			<c:forEach var="partner" items="${partner}" varStatus="status">	
			<tr align="center">
				<td><a href="javascript:sendPartnerId('${partner.partnerId}','${partner.partnerName}')">${partner.partnerName}</a></td>
				<td>${partner.ceoName}</td>
				<td>${partner.partnerGubun}</td>
			</tr>
			</c:forEach>
	</c:otherwise>
	</c:choose>
	</tbody>
</table>

</div>
<div class="clear"></div>
<div class="blockBlank_10px"></div>		
