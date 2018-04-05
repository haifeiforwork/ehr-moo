<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="user" value="${sessionScope['ikep.user']}" />


<script type="text/javascript">

(function($) {

	
	$(document).ready( function() {
		parent.setLicurrent('#majorRetailer');
		
		$("#readerListViewButton").click( function() {  
			var url = "<c:url value='/support/customer/customerBasicInfo/listReaderView.do?id=${customerId}&divCode=MS'/>";
			iKEP.popupOpen(url , {width:700, height:500});
		});
		});
	
	
	
})(jQuery);


function goModify(seqNum){
	$jq("#modifyForm_"+seqNum).trigger("submit");
	return false;
	
}

</script>

	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>${customer} 주요판매처</h2>	
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
				
					
           
			
			 <!--blockButton Start-->
               <div class="blockButton"> 
                   <ul> 
	                   <%-- 	<c:choose>
	                   	<c:when test="${registerId eq user.userId || isAdmin eq true}"> 
	                   	 <li><a class="button" href="<c:url value='/support/customer/customerMainSelling/createMainSellingView.do?customer=${customer}&customerId=${customerId}'/>"><span>등록</span></a></li>
	                    </c:when>
	                    </c:choose> --%>
	                    <li><a id="readerListViewButton" class="button" href="#a"><span>조회현황</span></a></li>
                    	<li><a class="button" href="<c:url value='/support/customer/customerMainSelling/mainSellingList.do'/>"><span>목록</span></a></li>
                   </ul>
               </div>
               <!--//blockButton End-->
               
                <!--subTitle_1 Start-->
				<div class="subTitle_2 noline">
					<h3> 주요판매처 상세조회 </h3>
				</div>
				<!--//subTitle_1 End-->	
			
						<c:forEach var="mainSellingItem" items="${mainSellingDetailList}" varStatus="status">
						<form id="modifyForm_${mainSellingItem.seqNum}" method="post" action="<c:url value='/support/customer/customerMainSelling/modifyMainSellingView.do'/>" >							
							<input type="hidden"  name="customerId" value="${customerId}" />
							<input type="hidden"  name="customer" value="${customer}" />
							<input type="hidden"  name="seqNum" value="${mainSellingItem.seqNum}" />
							<input name="sellingName" type="hidden"  value="${mainSellingItem.sellingName}" />
							<input name="sellingType" type="hidden"  value="${mainSellingItem.sellingType}" />
							<input name="buyingEmployee" type="hidden"  value="${mainSellingItem.buyingEmployee}" />
							<input name="address" type="hidden"  value="${mainSellingItem.address}" />
							<input name="tel" type="hidden"  value="${mainSellingItem.tel}" />
							<input name="fax" type="hidden"  value="${mainSellingItem.fax}" />
							<input name="pName" type="hidden"  value="${mainSellingItem.pName}" />
							<input name="dealForm" type="hidden"  value="${mainSellingItem.dealForm}" />
							<input name="buyingAmt" type="hidden"  value="${mainSellingItem.buyingAmt}" />
							<input name="registerId" type="hidden"  value="${mainSellingItem.registerId}" />
						</form>
						   <!--blockDetail Start-->
				                <div class="blockDetail">
									<table summary="주요판매처">
										<caption></caption>
				                        <colgroup>
				                            <col width="10%"/>
				                            <col width="*"/>
				                            <col width="10%"/>
				                            <col width="15%"/>
				                            <col width="10%"/>
				                            <col width="15%"/>
				                        </colgroup>
										<tbody>
				               		  		<tr>
												<th scope="row">판매처명</th>
												<td>
												<c:choose>
												<c:when test="${mainSellingItem.registerId eq user.userId || isAdmin eq true}">
												<a href="javascript:goModify(${mainSellingItem.seqNum});">${mainSellingItem.sellingName}</a>
												</c:when>
												<c:otherwise>
												${mainSellingItem.sellingName}
												</c:otherwise>
												</c:choose>
												
												</td>
				                                <th scope="row">업종</th>
												<td>${mainSellingItem.sellingType}</td>
				                                <th scope="row">담당자</th>
												<td>${mainSellingItem.buyingEmployee}</td>
											</tr>
											<tr>
												<th scope="row">주소</th>
												<td>${mainSellingItem.address}</td>
				                                <th scope="row">연락처</th>
												<td>${mainSellingItem.tel}</td>
				                                <th scope="row">팩스</th>
												<td>${mainSellingItem.fax}</td>
											</tr>
				                            <tr>
												<th scope="row">구매지종</th>
												<td>${mainSellingItem.pName}</td>
				                                <th scope="row">거래형태</th>
												<td>${mainSellingItem.dealForm }</td>
				                                <th scope="row">구매량</th>
												<td>${mainSellingItem.buyingAmt}</td>
											</tr>
				                        </tbody>
									</table>
								</div>
				                <!--//blockDetail End-->
						
						</c:forEach>					      
                					
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
			 <!--blockButton Start-->
               <div class="blockButton"> 
                   <ul>
                   <c:choose>
                   	<c:when test="${registerId eq user.userId || isAdmin eq true}"> 
                   	 <li><a class="button" href="<c:url value='/support/customer/customerMainSelling/createMainSellingView.do?customer=${customer}&customerId=${customerId}'/>"><span>등록</span></a></li>
                    </c:when>
                    </c:choose>
                     <li><a class="button" href="<c:url value='/support/customer/customerMainSelling/mainSellingList.do'/>"><span>목록</span></a></li>
                   </ul>
               </div>
               <!--//blockButton End-->
               
			<div class="blockBlank_10px"></div>
					