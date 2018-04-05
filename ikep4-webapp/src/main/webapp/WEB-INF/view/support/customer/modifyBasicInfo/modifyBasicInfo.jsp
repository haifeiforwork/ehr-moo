<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>


<script type="text/javascript">

(function($) {

	
	$(document).ready(function() {
 		
		$("#divTab2").tabs();
	
		var id = $("#id").val();
		
		
    	tab = $("#divTab2").tabs({
    		
    		selected : 0, 
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
	

    	historyTab = function(){
    		
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    			
    		}
    	    location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicHistory.do?id=${basicInfo.id}'/>";
    	}
    	equipmentTab = function(){
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    			return ;
    		}
    		location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicInfoEquipment.do?id=${basicInfo.id}'/>";
    	}
    	mainPerson1Tab = function(){
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    		}
    		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainPerson1.do?id=${basicInfo.id}'/>";
    	}
    	mainPerson2Tab = function(){
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    		}
    		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainPerson2.do?id=${basicInfo.id}'/>";
    	}
    	personStaticTab = function(){
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    		}
    		location.href="<c:url value='/support/customer/customerBasicInfo/modifyPersonStatic.do?id=${basicInfo.id}'/>";
    	}
    	
    	mainBusinessTab = function(){
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    		}
    		location.href="<c:url value='/support/customer/customerBasicInfo/modifyMainBusiness.do?id=${basicInfo.id}'/>";
    	}	
    	relationTab = function(){
    		if(id == null || id == ""){
    			alert("기본정보를 저장한 뒤 저장가능합니다.");
    		}
    		location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicRelationCompany.do?id=${basicInfo.id}'/>";
    	}
    	
	

	$("a.saveBasicInfo").click(function(){
		$("#basicInfoForm").trigger("submit");
	});

	
	

	goSync = function(id) {
		var name = $("#name").val();
		
		var url = '<c:url value='/support/customer/customerBasicInfo/popupSAPSync.do?id='/>'+id+"&name="+name;
		var title = 'SAP SYNC'
		var width = 780;
		var height = 580;

	 	var dialog = iKEP.showDialog({ 
			title: title,
			url: url,
			width:width,
			height:height,
			modal: true,
			scroll: "yes",
			callback : function(result){
				alert("성공");
				
			}
		}); 
		
	};
	
	
	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
    $jq('#name').keypress( function(event) {
    	if(event.keyCode == '13'){
    		goSync();
    		return false;
    	}
	});
 	
	
	new iKEP.Validator("#basicInfoForm",{
		
		rules : {
			name	: {required: true},
			regno1	: {digits: true},
			regno2	: {digits: true},
			regno3	: {digits: true},
			corporationNo1 : {digits: true},
			corporationNo2 : {digits: true},
		/* 	eMail	: {email:true},*/
			
			//postNo	: "zipcode", 
			
		},
		messages : {
			name	: {direction: "bottom", required: "고객명은 필수 입력값입니다."},
			regno1	: {direction: "left",digits: "숫자만 입력 가능합니다."},
			regno2	: {direction: "left",digits: "숫자만 입력 가능합니다."},
			regno3	: {direction: "left",digits: "숫자만 입력 가능합니다."},
			corporationNo1	: {direction: "left",digits: "숫자만 입력 가능합니다."},
			corporationNo2	: {direction: "left",digits: "숫자만 입력 가능합니다."},
			/* eMail	: "이메일 주소 형식으로 입력해주세요.(xxx@xxx.xxx)", */
			
			//postNo	: "우편번호 형식으로 입력해주세요.(000-000)",
		},
		
		submitHandler : function(form){
			
			if(confirm("저장하시겠습니까?")){
				
				if($("#sapId").val() == "SAP 등록 후 동기화 필요"){
					
					$("#sapId").val("");
				}
				
				form.submit();
				alert("저장되었습니다.");
				
				
			}
		}
	});

	document.onkeydown = function () {
		
	    var backspace = 8;
	    var t = document.activeElement;
	 
	    if (event.keyCode == backspace) {
	    
	        if (t.tagName == "SELECT"){
	            return false;
	        }
	        if (t.tagName == "INPUT" && t.getAttribute("readonly") == "readonly"){
	            return false;
	        }
	        
	        
	    }
	}
	
	goMenu = function(id){
		
		if(id == undefined){
			url = "<c:url value='/support/customer/customerBasicInfo/detailBasicInfo.do?id='/>"+id;
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
                <div id="tabs-1">
                <form id="basicInfoForm" method="post" action="<c:url value='/support/customer/customerBasicInfo/saveBasicInfo.do'/>" >
				<input type="hidden" name="id" id="id" value="${basicInfo.id}" />
                	<!--blockDetail Start-->
						<div class="blockDetail">
						    <table summary="new group">
						        <caption>기본정보</caption>
						        <colgroup>
						            <col width="8%"/>
						            <col width="8%"/>
						            <col width="18%"/>
						            <col width="14%"/>
						            <col width="18%"/>
						            <col width="14%"/>
						            <col width="18%"/>
						        </colgroup>
						        <tbody>
						            <tr>
						                <th scope="row" colspan="2"><span class="colorPoint">*</span>고객명</th>
						                <td><input name="name" id="name" title="고객명" value="${basicInfo.name}" class="inputbox w60" type="text" />
						                <a class="button_s" <c:if test="${basicInfo.sapId ne null}">disabled</c:if>  href="javascript:goSync(${basicInfo.id});"><span>검색</span></a></td>
						                <th scope="row">오너(Owner)</th>
						                <td><input name="ceoName" title="오너" value="${basicInfo.ceoName}" class="inputbox w90" type="text" /></td>
						                <th scope="row">사업자등록번호</th>
						                <td><input name="regno1" title="사업자등록번호" value="${basicInfo.regno1}" <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 </c:if><c:if test="${basicInfo.sapId eq null}">inputbox </c:if>" type="text" maxlength="3" size="3"/> -
						                    <input name="regno2" title="사업자등록번호" value="${basicInfo.regno2}" <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 </c:if><c:if test="${basicInfo.sapId eq null}">inputbox </c:if>" type="text" maxlength="2" size="2"/> -
						                  <input name="regno3" title="사업자등록번호" value="${basicInfo.regno3}" <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 </c:if><c:if test="${basicInfo.sapId eq null}">inputbox </c:if>" type="text" maxlength="5" size="5"/> </td>
						            </tr>
						            <tr>
						                <th scope="row" colspan="2">SAP ID</th>
						                <td>
						                <input name="sapId" id="sapId" readonly = "readonly" title="sapId"  value="<c:if test="${basicInfo.sapId eq null}">SAP 등록 후 동기화 필요</c:if>${basicInfo.sapId}" class="inputbox1 w90" type="text" size="27" /></td>						                
			   		           		    <th scope="row" >FAX</th>
						                <td><input name="fax" title="FAX"   value="${basicInfo.fax}"  <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>" type="text" /></td>
						                <th scope="row">법인번호</th>
						                <td><input name="corporationNo1" title="법인번호1"  value="${basicInfo.corporationNo1}"  <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
										class="<c:if test="${basicInfo.sapId ne null}">inputbox1 </c:if><c:if test="${basicInfo.sapId eq null}">inputbox </c:if>" type="text" maxlength="6" size="6" />-
										<input name="corporationNo2" title="법인번호2"  value="${basicInfo.corporationNo2}"  <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
										class="<c:if test="${basicInfo.sapId ne null}">inputbox1 </c:if><c:if test="${basicInfo.sapId eq null}">inputbox </c:if>" type="text"  maxlength="7" size="7" />
										</td>						            	
						            </tr>
						             <tr>
						                <th scope="row" colspan="2">우편번호</th>
						                <td><input name="postNo" title="우편번호"  value="${basicInfo.postNo}"  <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>"  type="text"/></td>						                
						                <th scope="row" >대표이사</th>
						                <td><input name="director" title="대표이사"  value="${basicInfo.director}"   <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>"  type="text"/></td>
						                <th scope="row">대표전화</th>
						                <td><input name="telNo" title="대표전화" value="${basicInfo.telNo}"  <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>"  type="text"/></td>	
						            </tr>
						            <tr>
						            	<th scope="row" colspan="2">주소(도/시)</th>
						                <td colspan="3"><input name="address1" title="주소(도/시)" value="${basicInfo.address1}"   <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w100</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w100</c:if>"  type="text"/></td>
						                <th scope="row">이메일</th>
						                <td><input name="eMail" title="이메일" value="${basicInfo.eMail}"   <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>"  type="text"/></td>  					          
						            </tr>
						            <tr>
						            	<th scope="row" colspan="2">주소(동/번지)</th>
						                <td colspan="3"><input name="address2" title="주소(동/번지)"  value="${basicInfo.address2}"   <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w100</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w100</c:if>"   type="text"/></td>						                
						               	<th scope="row">담당영업사원</th>
						                <td><input name="businessEmployee" title="담당영업사원"  value="${basicInfo.businessEmployee}"  class="inputbox w90" type="text" /></td>					            						            
						           
						            </tr>
						            <tr>
						              	<th scope="row" colspan="2">상장여부</th>
						                <td><input name="sangjangFlag" title="상장여부"   value="${basicInfo.sangjangFlag}"  class="inputbox w90" type="text" /></td>						            
						                <th scope="row">창립일</th>
						                <td><input name="establishDate" title="창립일"  value="${basicInfo.establishDate}"  class="inputbox w90" type="text" /></td>
						            	<th scope="row" >주주구성</th>
						                <td><input name="stockholder" title="주주구성" value="${basicInfo.stockholder}" class="inputbox w90" type="text" /></td>						              
						            </tr>
						            <tr>
						            	<th scope="row" colspan="2">담당자</th>
						                <td><input name="charge" title="담당자"  value="${basicInfo.charge}"  class="inputbox w90" type="text" /></td>						          
						                <th scope="row">업종(고객구분)</th>
						                <td>
						                    <select title="용어검색분류" name="customerGubun" id="customerGubun">
						                        <option value=""  >선택</option>
						                        <option value="1" <c:if test="${selectedCustomerGubun eq '유통사'}">selected="selected"</c:if>>유통사</option>
						                        <option value="2" <c:if test="${selectedCustomerGubun eq '인쇄/가공사'}">selected="selected"</c:if>>인쇄/가공사</option>
						                        <option value="3" <c:if test="${selectedCustomerGubun eq '출판/기획사'}">selected="selected"</c:if>>출판/기획사</option>
						                        <option value="4" <c:if test="${selectedCustomerGubun eq '일반고객'}">selected="selected"</c:if>>일반고객</option>
						                    </select> 
						                </td>						                
						                 <th scope="row">당사비율</th>
						                <td><input name="companyPercent" title="당사비율"  value="${basicInfo.companyPercent}" class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						                <th scope="row" colspan="2">주거래은행</th>
						                <td><input name="mainBank" title="주거래은행"  value="${basicInfo.mainBank}" class="inputbox w90" type="text" /></td>
						                <th scope="row">결산월</th>
						                <td><input name="sattlingMonth" title="결산월"   value="${basicInfo.sattlingMonth}" class="inputbox w90" type="text" /></td>
						          		<th scope="row">종업원수</th>
						                <td><input name="employeeCount" title="종업원수"  value="${basicInfo.employeeCount}"  class="inputbox w90" type="text" /></td>
						            </tr>
						            <tr>
						            	<th scope="row" colspan="2">매출액</th>
						                <td><input name="sellingAmt" title="매출액"   value="${basicInfo.sellingAmt}" class="inputbox w90" type="text" /></td>
						            	<th scope="row">영업이익</th>
						                <td><input name="businessAmt" title="영업이익"  value="${basicInfo.businessAmt}" class="inputbox w90" type="text" /></td>
						                <th scope="row">세분화등급</th>
						                <td>
						                     <select title="용어검색분류" name="subdivisionGrade">
						                        <option value="">선택</option>
						                        <option value="1" <c:if test="${selectedSubdivisionGrade eq 'Target고객'}">selected="selected"</c:if>>Target 고객</option>
						                        <option value="2" <c:if test="${selectedSubdivisionGrade eq '이익개선고객'}">selected="selected"</c:if>>이익개선 고객</option>
						                        <option value="3" <c:if test="${selectedSubdivisionGrade eq '매출개선고객'}">selected="selected"</c:if>>매출개선 고객</option>
						                        <option value="4" <c:if test="${selectedSubdivisionGrade eq '관리대상고객'}">selected="selected"</c:if>>관리대상 고객</option>
						                    </select>
						                </td>
						            </tr>
						            <tr>
						            	<th scope="row" colspan="2">업태</th>
						                <td><input name="type" title="업태" value="${basicInfo.type}"    <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>"   type="text" /></td>
						            	<th scope="row">종목</th>
						                <td><input name="jijongType" title="종목" value="${basicInfo.jijongType}"   <c:if test="${basicInfo.sapId ne null}">readonly = "readonly"</c:if>
						                 class="<c:if test="${basicInfo.sapId ne null}">inputbox1 w90</c:if><c:if test="${basicInfo.sapId eq null}">inputbox w90</c:if>"   type="text" /></td>
						            	<th scope="row">월평균구매량</th>
						                <td><input name="monthBuying" title="월평균구매량" value="${basicInfo.monthBuying}"  class="inputbox w90" type="text" /></td>						                
						            </tr>
						            <tr>
						            	<th scope="row" rowspan="3">주소</th>
						                <th scope="row">본사</th>
						                <td colspan="5"><input name="headOfficeAddress" title="본사주소" value="${basicInfo.headOfficeAddress}" class="inputbox w100" type="text" /></td>						          
						            </tr>
						            <tr>
						            	<th scope="row">공장1</th>
						                <td colspan="5"><input name="factoryAddress1" title="공장1주소" value="${basicInfo.factoryAddress1}" class="inputbox w100" type="text" /></td>						            
						            </tr>
						            <tr>
						                <th scope="row">공장2</th>
						                <td colspan="5"><input name="factoryAddress2" title="공장2주소"  value="${basicInfo.factoryAddress2}" class="inputbox w100" type="text" /></td>	
						            </tr>
						        </tbody>
						    </table>
						</div>
						</form>
	
					
					<!--blockButton Start-->
					<div class="blockButton"> 
					    <ul>
					        <li><a class="button saveBasicInfo" href="#a"><span>등록</span></a></li>
					       <!--  <li><a class="button" href="javascript:historyTab();"><span>다음단계</span></a></li> -->
					        <li><a class="button" href="javascript:goMenu(${basicInfo.id});"><span>목록</span></a></li>
					    </ul>
					</div>
					<!--//blockButton End-->  
                </div>
                <div id="tabs-2"></div>
                <div id="tabs-3"></div>
                <div id="tabs-4"></div>
                <div id="tabs-5"></div>            
                <div id="tabs-6"></div>               
                <div id="tabs-7"></div>                
                <div id="tabs-8"></div>
            </div>				
        </div>		
        <!--//tab End-->

