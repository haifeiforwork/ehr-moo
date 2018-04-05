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
    		selected : 5, 
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
	

	$("a.savePersonStatic").click(function(){
		$("#personStaticForm").trigger("submit");
	});
	
	
	
	new iKEP.Validator("#personStaticForm",{
		
		rules : {
			manEmployee1:{required: true},
			manEmployee2:{required: true},
			manEmployee3:{required: true},
			manEmployee4:{required: true},
			womanEmployee1:{required: true},
			womanEmployee2:{required: true},
			womanEmployee3:{required: true},
			womanEmployee4:{required: true}
		},
		messages : {
			manEmployee1:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			manEmployee2:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			manEmployee3:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			manEmployee4:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			womanEmployee1:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			womanEmployee2:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			womanEmployee3:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."},
			womanEmployee4:{direction: "bottom", required: "필수 입력값입니다. 초기 값은 0입니다."}
			
		},
		
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
                <div id="tabs-3"></div>
                <div id="tabs-4"></div>
                <div id="tabs-5"></div>            
                <div id="tabs-6">
                <form id="personStaticForm" method="post" action="<c:url value='/support/customer/customerBasicInfo/savePersonStatic.do'/>">
		   			<input type="hidden" name="id" id="id" value="${personStatic.id}" />
		   			<!--blockDetail Start-->
		                <div class="blockDetail">
		                    <table summary="new group">
		                        <caption>인원현황</caption>
		                        <colgroup>
		                            <col width="20%"/>
		                            <col width="20%"/>
		                            <col width="20%"/>
		                            <col width="20%"/>
		                            <col width="20%"/>
		                        </colgroup>
		                        <tbody>
		                            <tr>
		                                <th scope="row" class="textCenter">구분</th>
		                                <th scope="row" class="textCenter">임원</th>
		                                <th scope="row" class="textCenter">차/부장</th>
		                                <th scope="row" class="textCenter">과장/대리</th>
		                                <th scope="row" class="textCenter">계장/주임/사원</th>
		                            </tr>
		                            <tr>
		                                <td class="textCenter">남</td>
		                                <td class="textCenter"><input name="manEmployee1" title="임원" value="${personStatic.manEmployee1}" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="manEmployee2" title="차/부장" value="${personStatic.manEmployee2}" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="manEmployee3" title="과장/대리" value="${personStatic.manEmployee3}" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="manEmployee4" title="계장/주임/사원" value="${personStatic.manEmployee4}" class="inputbox w90" type="text" /></td>
		                            </tr>
		                            <tr>
		                                <td class="textCenter">녀</td>
		                                <td class="textCenter"><input name="womanEmployee1" title="임원" value="${personStatic.womanEmployee1}" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="womanEmployee2" title="차/부장" value="${personStatic.womanEmployee2}"  class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="womanEmployee3" title="과장/대리" value="${personStatic.womanEmployee3}" class="inputbox w90" type="text" /></td>
		                                <td class="textCenter"><input name="womanEmployee4" title="계장/주임/사원" value="${personStatic.womanEmployee4}"  class="inputbox w90" type="text" /></td>
		                            </tr>
		                            <tr>
		                                <td class="textCenter">합계</td>
		                                <td class="textCenter"><input name="totalEmployee1" title="임원" value="${personStatic.totalEmployee1}" class="inputbox w90" type="text" readonly="readonly" /></td>
		                                <td class="textCenter"><input name="totalEmployee2" title="차/부장" value="${personStatic.totalEmployee2}" class="inputbox w90" type="text" readonly="readonly" /></td>
		                                <td class="textCenter"><input name="totalEmployee3" title="과장/대리" value="${personStatic.totalEmployee3}" class="inputbox w90" type="text" readonly="readonly" /></td>
		                                <td class="textCenter"><input name="totalEmployee4" title="계장/주임/사원" value="${personStatic.totalEmployee4}" class="inputbox w90" type="text" readonly="readonly" /></td>
		                            </tr>
		                        </tbody>
		                    </table>
		                </div>
		                <!--//blockDetail End-->	
		                
		                <!--blockButton Start-->
		                <div class="blockButton"> 
		                    <ul>
		                        <li><a class="button savePersonStatic" href="#a"><span>등록</span></a></li>
		                       <!--  <li><a class="button" href="javascript:mainBusinessTab();"><span>다음단계</span></a></li> -->
		                        <li><a class="button" href="javascript:goMenu(${param.id});"><span>목록</span></a></li>
		                    </ul>
		                </div>
		                <!--//blockButton End-->
		          </form>
                </div>               
                <div id="tabs-7"></div>              
                <div id="tabs-8"></div>
            </div>				
        </div>		
        <!--//tab End-->




   
    

     
      


  