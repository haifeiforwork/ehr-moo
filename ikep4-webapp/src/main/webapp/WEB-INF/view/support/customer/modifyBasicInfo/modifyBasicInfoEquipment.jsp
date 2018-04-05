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
    		selected : 2, 
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
	

	$("a.saveBasicEquipment").click(function(){
		$("#basicEquipmentForm").trigger("submit");
	});
	
	
	new iKEP.Validator("#basicEquipmentForm",{
		
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
                <div id="tabs-3">
                	<form id="basicEquipmentForm" method="post" action="<c:url value='/support/customer/customerBasicInfo/saveBasicEquipment.do'/>">
                	<input type="hidden" name="id" id="id" value="${id}" />
                	<!--blockDetail Start-->
					 <div class="blockDetail">
					     <table summary="new group">
					         <caption>설비현황</caption>
					         <colgroup>
					             <col width="20%"/>
					             <col width="80%"/>
					         </colgroup>
					         <tbody>
					             <tr>
					                 <th scope="row">창고명</th>
					                 <td><input name="equipmentStoreName" title="대상자" value="${basicInfo.equipmentStoreName}" class="inputbox w100" type="text" /></td>
					             </tr>
					             <tr>
					                 <th scope="row">창고설명</th>
					                 <td><textarea name="equipmentStoreExplan" class="w100"  title="창고설명" cols="" rows="3">${basicInfo.equipmentStoreExplan}</textarea></td>
					             </tr>
					             <tr>
					                 <th scope="row">인쇄기명</th>
					                 <td><input name="equipmentPrintName" title="대상자" value="${basicInfo.equipmentPrintName}" class="inputbox w100" type="text" /></td>
					             </tr>
					             <tr>
					                 <th scope="row">인쇄기설명</th>
					                 <td><textarea name="equipmentPrintExplan" class="w100"  title="인쇄기설명" cols="" rows="3">${basicInfo.equipmentPrintExplan}</textarea></td>
					             </tr>
					             <tr>
					                 <th scope="row">기타명</th>
					                 <td><input name="equipmentEtcName" title="대상자" value="${basicInfo.equipmentEtcName}" class="inputbox w100" type="text"  /></td>
					             </tr>
					             <tr>
					                 <th scope="row">기타설명</th>
					                 <td><textarea name="equipmentEtcExplan" class="w100" title="기타설명" cols="" rows="3">${basicInfo.equipmentEtcExplan}</textarea></td>
					             </tr>
					         </tbody>
					     </table>
					 </div>
					 <!--//blockDetail End-->									
					             
					 <!--blockButton Start-->
					 <div class="blockButton"> 
					     <ul>
					         <li><a class="button saveBasicEquipment" href="#a"><span>등록</span></a></li>
					       <!--   <li><a class="button" href="javascript:mainPerson1Tab();"><span>다음단계</span></a></li> -->
					         <li><a class="button" href="javascript:goMenu(${param.id});"><span>목록</span></a></li>
					     </ul>
					 </div>
					 <!--//blockButton End-->
					 </form>
                </div>
                <div id="tabs-4"></div>
                <div id="tabs-5"></div>            
                <div id="tabs-6"></div>               
                <div id="tabs-7"></div>              
                <div id="tabs-8"></div>
            </div>				
        </div>		
        <!--//tab End-->




   
    

     
      

