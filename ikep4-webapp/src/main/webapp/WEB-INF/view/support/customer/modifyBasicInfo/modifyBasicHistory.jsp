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
	};
	personStaticTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyPersonStatic.do?id=${param.id}'/>";
	};
	mainBusinessTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainBusiness.do?id=${param.id}'/>";
	};	
	relationTab = function(){
		location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicRelationCompany.do?id=${param.id}'/>";
	};
	
	
	$(document).ready(function() {
		var id = $("#id").val();
	
		var historyCnt = $jq("#historyBody").children("tr").length;
		
		
		
	/* 	//달력
		$("input[name=yearDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); }); */
		$("#divTab2").tabs();
	
		
    	tab = $("#divTab2").tabs({
    		selected : 1, 
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
	

	

	$("a.saveHistory").click(function(){
		$("#basicHistoryForm").trigger("submit");
	});
	
	
	
	new iKEP.Validator("#basicHistoryForm",{
		
		
		/* rules  : {
			yearDate     : {required : true, rangelength : [1, 100] },
			yearContent       : {required : true }
		},
		messages : {
			yearDate     : {direction : "top",    required : "<ikep4j:message key="message.collpack.collaboration.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"},
			yearContent       : {direction : "top", 
				required : "<ikep4j:message key="message.collpack.collaboration.NotBlank.boardItem.tag" />"
				} 
		}, */   
		
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
	
	$jq("#historyCnt").val(historyCnt-1);
	
	
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


 function addHistoryCnt(){
	var historyCnt = $jq("#historyBody").children("tr").length;

	 $jq("#historyBody").append(
			 "<tr>"+
				"<td class=\"textCenter\"><input name=\"yearDate\" title=\"날짜\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
             "<td><input name=\"yearContent\" title=\"주요내용\" class=\"inputbox w100\" type=\"text\" /></td>"+
			  "</tr>"
	); 
	 historyCnt = historyCnt++;
	
	 iKEP.iFrameContentResize();
	 $jq("#historyCnt").val(historyCnt);
	 
 
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
                <div id="tabs-2">
                
                <form id="basicHistoryForm" method="post" action="<c:url value='/support/customer/customerBasicInfo/saveBasicHistory.do'/>" >
				    <input type="hidden" id="id" name="id" value="${param.id}" /> 
					<input type="hidden" name="historyCnt" id="historyCnt" value=""/>
					 <!--blockDetail Start-->
					<div class="blockDetail">
					    <table summary="new group">
					        <caption>연혁</caption>
					        <colgroup>
					            <col width="17%"/>
					            <col width="83%"/>
					        </colgroup>
					        <tbody id="historyBody">
					            <tr>
					                <th scope="row" class="textCenter">날짜</th>
					                <th scope="row" class="textCenter">주요내용</th>
					            </tr>
					            <c:if test="${basicHistory eq null }">
					            <tr>
						            <td class="textCenter">
										<%-- <input name="yearDate" title="날짜" value="<ikep4j:timezone date='${basicHistory.yearDate}'/>" class="inputbox datepicker" type="text" size="12" />
										<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></td> --%>
									<input name="yearDate" title="날짜"  class="inputbox w90" type="text" /></td>
					                <td><input name="yearContent" title="주요내용" value="" class="inputbox w100" type="text" /></td>
					            </tr>
					            </c:if>
					            <c:forEach var="basicHistory" items="${basicHistory}" varStatus="status">
					      
					            <tr>
						               <%--  <td class="textCenter">
										<input name="yearDate" title="날짜" value="<ikep4j:timezone date='${basicHistory.yearDate}'/>" class="inputbox datepicker" type="text" size="12" />
										<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></td> --%>
									<td class="textCenter"><input name="yearDate" title="날짜" value="${basicHistory.yearDate}" class="inputbox w90" type="text" /></td>
					                <td><input name="yearContent" title="주요내용" value="${basicHistory.yearContent}" class="inputbox w100" type="text" /></td>
					            </tr>
					            </c:forEach>  			                                    
					        </tbody>
					    </table>
					</div>
					<!--//blockDetail End-->									
					            
					<!--blockButton Start-->
					<div class="blockButton"> 
					    <ul>
					    	<li><a class="button" href="javascript:addHistoryCnt();"><span>추가</span></a></li>
					        <li><a class="button saveHistory" href="#a"><span>등록</span></a></li>
					       <!--  <li><a class="button" href="javascript:equipmentTab();"><span>다음단계</span></a></li> -->
					        <li><a class="button" href="javascript:goMenu(${param.id})"><span>목록</span></a></li>
					    </ul>
					</div>
					<!--//blockButton End-->
                </form>
                
                </div>
                <div id="tabs-3"></div>
                <div id="tabs-4"></div>
                <div id="tabs-5"></div>            
                <div id="tabs-6"></div>               
                <div id="tabs-7"></div>              
                <div id="tabs-8"></div>
            </div>				
        </div>		
        <!--//tab End-->




   
    

     
      