<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>


<script type="text/javascript">

(function($) {
	
	basicInfoTab = function(){
		location.href = "<c:url value='/support/customer/customerBasicInfo/modifyBasicInfo.do?id=${param.id}'/>";	  
	};
	historyTab = function(){
	    location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicHistory.do?id=${param.id}'/>";
	}
	equipmentTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicInfoEquipment.do?id=${param.id}'/>";
	};
	mainPerson1Tab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainPerson1.do?id=${param.id}'/>";
	};
	mainPerson2Tab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainPerson2.do?id=${param.id}'/>";
	}
	personStaticTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyPersonStatic.do?id=${param.id}'/>";
	}
	mainBusinessTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainBusiness.do?id=${param.id}'/>";
	}	
	relationTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicRelationCompany.do?id=${param.id}'/>";
	}
	
	$(document).ready(function() {
 		
		$("#divTab2").tabs();
		var id = $("#id").val();
		
		
    	tab = $("#divTab2").tabs({
    		selected : 6, 
			select : function(event, ui) {
				switch($(ui.panel).attr("id")) {
				case "tabs-1" : basicInfoTab();
					break;
				case "tabs-2" : historyTab();
					break;
				case "tabs-3" : equipmentTab();
					break;
				case "tabs-4" : mainPerson1Tab();
					break;
				case "tabs-5" : mainPerson2Tab();
					break;
				case "tabs-6" : personStaticTab();
					break;
				case "tabs-7" : mainBusinessTab();
					break;
				case "tabs-8" : relationTab();
					break;
					
				}
			}
		});   
	
	var buyingCnt = $jq("#buyingBody").children("tr").length;

  	$jq("#buyingCnt").val(buyingCnt-1);

	var buyingCnt = $jq("#sellingBody").children("tr").length;

  	$jq("#sellingCnt").val(buyingCnt-1);	

    	
	$("a.saveButton").click(function(){
		$("#mainBusinessForm").trigger("submit");
	});
	
	
	
	new iKEP.Validator("#mainBusinessForm",{
		
		rules : {},
		messages : {},
		
		submitHandler : function(form){
			
			if(id == null || id == ""){
				alert("기본정보를 저장한 뒤 저장 가능합니다.");
			}else{
				if(confirm("저장하시겠습니까?")){
					
					form.submit();
					alert("저장되었습니다.");
					
				}
			}
		}
	});
	
	goMenu = function(id){
		
		if(id == undefined){
			url = "<c:url value='/support/customer/customerBasicInfo/customerList.do'/>";
			location.href = url;
		}else{
			url = "<c:url value='/support/customer/customerBasicInfo/detailBasicInfo.do?id='/>"+id;
			location.href = url;
		}
		
	}	
		
		
	});
})(jQuery);


function addBuyingTr(){
	
	var buyingCnt = $jq("#buyingBody").children("tr").length;
	
	$jq("#buyingBody").append(
		"<tr>"+
            "<td class=\"textCenter\"><input name=\"mainCustomer_b\" title=\"업체명\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"monthAvg_b\" title=\"월평균 판매량\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"chargeEmployee_b\"  title=\"담당 영업사원\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"mainProduct_b\"  title=\"주요지종\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"sellingBuyingRate_b\"  title=\"구매비율\" class=\"inputbox w90\" type=\"text\"  /></td>"+
        "</tr>"
			
	);
	
	buyingCnt = buyingCnt++;
	iKEP.iFrameContentResize();
	$jq("#buyingCnt").val(buyingCnt-1);

}

function addSellingTr(){
	
	var sellingCnt = $jq("#sellingBody").children("tr").length;
	$jq("#sellingBody").append(
		"<tr>"+
            "<td class=\"textCenter\"><input name=\"mainCustomer_s\"  title=\"업체명\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"monthAvg_s\"  title=\"월평균 판매량\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"chargeEmployee_s\"  title=\"담당 영업사원\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"mainProduct_s\"  title=\"주요지종\" class=\"inputbox w90\" type=\"text\"  /></td>"+
            "<td class=\"textCenter\"><input name=\"sellingBuyingRate_s\" title=\"구매비율\" class=\"inputbox w90\" type=\"text\"  /></td>"+
        "</tr>"
			
	);
	
	sellingCnt = sellingCnt++;
	iKEP.iFrameContentResize();
	$jq("#sellingCnt").val(sellingCnt-1);

	
}

</script>


<h1 class="none">컨텐츠영역</h1>
								
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>고객리스트</h2>	
	<div class="clear"></div>
</div>
<!--//tableTop End-->

        <!--tab Start-->		
        <div id="divTab2" class="iKEP_tab_poll">
            <ul>
                <li><a href="#tabs-1" >기본정보</a></li>
                <li><a href="#tabs-2" >연혁</a></li>
                <li><a href="#tabs-3" >설비현황</a></li>
                <li><a href="#tabs-4" >주요임원</a></li>
                <li><a href="#tabs-5" >KEY MAN</a></li>
                <li><a href="#tabs-6" >인원현황</a></li>
                <li><a href="#tabs-7" >주거래선</a></li>
                <li><a href="#tabs-8" >관계사현황</a></li>
            </ul>
            <div>        
                <div id="tabs-1"></div>
                <div id="tabs-2"></div>
                <div id="tabs-3"></div>
                <div id="tabs-4"></div>
                <div id="tabs-5"></div>            
                <div id="tabs-6"></div>               
                <div id="tabs-7">
                <form id="mainBusinessForm" method="post" action="<c:url value='/support/customer/customerBasicInfo/saveMainBusiness.do'/>" >
                	<input type="hidden" id="id" name="id" value="${param.id}" />
                	<input type="hidden" name="buyingCnt" id="buyingCnt" value="" />
                	<input type="hidden" name="sellingCnt" id="sellingCnt" value="" />
                	<!--subTitle_1 Start-->
	                <div class="subTitle_2 noline">
	                    <h3>구매</h3>
	                </div>
	                <!--//subTitle_1 End-->	
	                
	                <!--blockDetail Start-->
	                <div class="blockDetail">
	                    <table summary="new group">
	                        <caption>구매</caption>
	                        <colgroup>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                        </colgroup>
	                        <tbody id="buyingBody">
	                    	
	                        	 <tr>
	                                <th scope="row" class="textCenter">구매조건</th>
	                                <td colspan="4"><input name="condition_b" title="직위" value="${buyingCondition}" class="inputbox w100" type="text" /></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="textCenter">업체명</th>
	                                <th scope="row" class="textCenter">월평균 판매량</th>
	                                <th scope="row" class="textCenter">담당 영업사원</th>
	                                <th scope="row" class="textCenter">주요지종</th>
	                                <th scope="row" class="textCenter">구매비율</th>
	                            </tr>
	                            <c:if test="${mainBusiness eq null }">
	                            	<tr>
		                                <td class="textCenter"><input name="mainCustomer_b" value ="" title="업체명" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="monthAvg_b" value ="" title="월평균 판매량" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="chargeEmployee_b" value ="" title="담당 영업사원" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="mainProduct_b" value ="" title="주요지종" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="sellingBuyingRate_b" value ="" title="구매비율" class="inputbox w90" type="text" /></td>
		                            </tr>
	                            </c:if>
		                        <c:forEach var="mainBusiness" items="${mainBusiness}" varStatus="status" >
		                        <c:if test="${mainBusiness.sellingBuyingFlag eq '1' }">    
		                            <tr>
		                                <td class="textCenter"><input name="mainCustomer_b" value ="${mainBusiness.mainCustomer}" title="업체명" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="monthAvg_b" value ="${mainBusiness.monthAvg}" title="월평균 판매량" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="chargeEmployee_b" value ="${mainBusiness.chargeEmployee}" title="담당 영업사원" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="mainProduct_b" value ="${mainBusiness.mainProduct}" title="주요지종" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="sellingBuyingRate_b" value ="${mainBusiness.sellingBuyingRate}" title="구매비율" class="inputbox w90" type="text" /></td>
		                            </tr>
		                        </c:if>
		                        </c:forEach> 
	                        </tbody>
	                    </table>
	                </div>
	                <!--//blockDetail End-->	
	                <div class="blockBlank_10px"></div>		
	                
	                <!--subTitle_1 Start-->
	                <div class="subTitle_2 noline">
	                    <h3>판매</h3>
	                </div>
	                <!--//subTitle_1 End-->	
	                
	                <!--blockDetail Start-->
	                <div class="blockDetail">
	                    <table summary="new group">
	                        <caption>판매</caption>
	                        <colgroup>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                            <col width="20%"/>
	                        </colgroup>
	                        <tbody id="sellingBody">
	                            <tr>
	                                <th scope="row" class="textCenter">판매조건</th>
	                                <td colspan="4"><input name="condition_s" title="직위" value="${sellingCondition}"  class="inputbox w100" type="text" /></td>
	                            </tr>
	                            <tr>
	                                <th scope="row" class="textCenter">업체명</th>
	                                <th scope="row" class="textCenter">월평균 판매량</th>
	                                <th scope="row" class="textCenter">담당 영업사원</th>
	                                <th scope="row" class="textCenter">주요생산제품</th>
	                                <th scope="row" class="textCenter">판매비율</th>
	                            </tr>
	                               <c:if test="${mainBusiness eq null }">
	                            	<tr>
		                                <td class="textCenter"><input name="mainCustomer_s" value ="" title="업체명" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="monthAvg_s" value ="" title="월평균 판매량" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="chargeEmployee_s" value ="" title="담당 영업사원" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="mainProduct_s" value ="" title="주요지종" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="sellingBuyingRate_s" value ="" title="구매비율" class="inputbox w90" type="text" /></td>
		                            </tr>
	                            </c:if>                       
	                            <c:forEach var="mainBusiness" items="${mainBusiness}" varStatus="status" >
		                        <c:if test="${mainBusiness.sellingBuyingFlag eq '2' }">    
		                            <tr>
		                                <td class="textCenter"><input name="mainCustomer_s" value ="${mainBusiness.mainCustomer}" title="업체명" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="monthAvg_s" value ="${mainBusiness.monthAvg}" title="월평균 판매량" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="chargeEmployee_s" value ="${mainBusiness.chargeEmployee}" title="담당 영업사원" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="mainProduct_s" value ="${mainBusiness.mainProduct}" title="주요지종" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="sellingBuyingRate_s" value ="${mainBusiness.sellingBuyingRate}" title="구매비율" class="inputbox w90" type="text" /></td>
		                            </tr>
		                        </c:if>
		                        </c:forEach>                           
	                        </tbody>
	                    </table>
	                </div>
	                <!--//blockDetail End-->	
	                
	                <!--blockButton Start-->
	                <div class="blockButton"> 
	                    <ul>
	                    	<li><a class="button" href="javascript:addBuyingTr();"><span>구매추가</span></a></li>
	                        <li><a class="button" href="javascript:addSellingTr();"><span>판매추가</span></a></li>
	                        <li><a class="button saveButton" href="#a"><span>등록</span></a></li>
	                        <!-- <li><a class="button" href="javascript:relationTab();"><span>다음단계</span></a></li> -->
	                        <li><a class="button" href="javascript:goMenu(${param.id});"><span>목록</span></a></li>
	                    </ul>
	                </div>
	                <!--//blockButton End-->
	             </form>
                </div>              
                <div id="tabs-8"></div>
            </div>				
        </div>		
        <!--//tab End-->




   
    

     
      


  

  

   
              
         