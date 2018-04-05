<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="prefix" value="ui.support.customer.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.customer.manInfoItemList" />

<script type="text/javascript">

var familyTrTemp = $jq("#familyBody").children("tr").length+3;
var careerTrTemp = $jq("#careerBody").children("tr").length+3;


(function($) {

	
	$(document).ready( function() {
		//alert("first:"+screen.height);
	
		/*
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		*/
		
		$("a.saveBoardItemButton").click(function() {
			

			$jq("#familyTrTemp").val(familyTrTemp);
			$jq("#careerTrTemp").val(careerTrTemp);
			
			
			var fileName = $jq("input[name=file]", registerForm).val();
			
			if(fileName != "") {
				if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
					alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
					return;
				}
			}
			
			
			$("#registerForm").trigger("submit"); 
			return false;
		}); 
		
		
		new iKEP.Validator("#registerForm", {   
			rules  : {
				customerName  : {required : true},
				man			  : {required : true},
			},
			messages : {
				customerName     : {direction : "top",    required : "<ikep4j:message key="NotNull.customer.manInfo.customerName" />"},
				man : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.manInfo.man" />"},
			},   
			
		    submitHandler : function(form) {  
		    	
		    	if(confirm("수정한 내용을 저장하시겠습니까?")) {
		    		var customerId = $("#customerId").val();
		    		var customerName = $("#customer").val();
					var tempCustomerId = "${manInfoItem.customerId}";
					var tempCustomerName = "${manInfoItem.customerName}";
					if( customerId == tempCustomerId && customerName != tempCustomerName){
						alert("고객명은 검색한 결과만 사용 가능합니다.");
						$("#customer").focus();
					}else if(customerId != tempCustomerId && customerName == tempCustomerName){
						alert("고객명은 검색한 결과만 사용 가능합니다.");
						$("#customer").focus();
					}else{	
						$("body").ajaxLoadStart("button");
						form.submit();
					}
				}	
		    }
			
			
		});   
		
		
		$("#searchButton").click(function(){
			searchCustomer();
		});
		
		
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#customer').keypress( function(event) {
        	if(event.keyCode == '13'){
				searchCustomer();
        		return false;
        	}
		});
		
		
	});
	
	
	
})(jQuery);

function addFamilyCnt(){

 	var familyTrs =$jq("#familyBody").children("tr");
 
 	//alert(familyTrs.length);
	if(familyTrs.length-1<6){
	 	$jq("#familyBody").append(
				  "<tr>"+
					"<td><input name=\"familyRelation\" title=\"관계\" class=\"inputbox w90\" type=\"text\"  /></td>"+
	                "<td><input name=\"familyName\" title=\"성명\" class=\"inputbox w90\" type=\"text\"  /></td>"+
				  "</tr>"
	 	);
	
	 	familyTrTemp++;
	 	iKEP.iFrameContentResize();
	}else{
		alert("최대 6개까지 추가 할 수 있습니다.");
	}
		
}


function addCareerCnt(){
 	var careerTrs =$jq("#careerBody").children("tr");
 
 	
	if(careerTrs.length-1<6){
	 	$jq("#careerBody").append(		 
	 			 "<tr>"+				
		 			 "<td><input name=\"mainCareer\" title=\"주요경력\" class=\"inputbox w90\" type=\"text\"  /></td>"+
	                 "<td><input name=\"preCareer\" title=\"이전경력\" class=\"inputbox w90\" type=\"text\"  /></td>"+
	                 "<td><input name=\"mainBusiness\" title=\"조직 내 주요 업무\" class=\"inputbox w90\" type=\"text\"  /></td>"+
				 "</tr>"
	 	);
	 	careerTrTemp++;
	 	iKEP.iFrameContentResize();

		
	}else{
		alert("최대 6개까지 추가 할 수 있습니다.");
	}
		
}

function searchCustomer(){

	var name = document.getElementById("customer").value;
	if(!name){
			alert("고객명을 입력해주세요.");
		}else{ 				
			 var url = "<c:url value='/support/customer/customerCommon/popupCustomer.do?customer='/>"+name;
			 iKEP.popupOpen(url,{width:700,height:490},'CustomerSearch'); 
			
		}

}


/* 
function updateManInfo(){
	alaert("111");
	$jq("#familyTrTemp").val(familyTrTemp);
	$jq("#careerTrTemp").val(careerTrTemp);
	
	
	var fileName = $jq("input[name=file]", registerForm).val();
	
	if(fileName != "") {
		if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
			alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
			return;
		}
	}
	
	
	
	$jq("#registerForm").submit();
}
 */

</script>		
		
		
<form id="registerForm" method="post" action="<c:url value='/support/customer/customerCommon/modifyManInfo.do'/>" enctype="multipart/form-data">
	<input name="seqNum"  type="hidden" value="${manInfoItem.seqNum}" />
	<input type="hidden" id="userId" name="userId" value="${user.userId}" />
	<input type="hidden" id="registerId" name="registerId" value="${manInfoItem.registerId}" />
	<input type="hidden" id="familyTrTemp" name="familyTrTemp" value="" />
	<input type="hidden" id="careerTrTemp" name="careerTrTemp" value="" /> 	
	<input type="hidden" name="customerId" id="customerId" value="${manInfoItem.customerId}" />
		<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>인물정보</h2>	
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
				
                <!--subTitle_1 Start-->
				<div class="subTitle_2 noline">
					<h3>기본정보</h3>
				</div>
				<!--//subTitle_1 End-->	
                
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="기본정보">
						<caption></caption>
                        <colgroup>
							<col width="9%" />
							<col width="12%" />
							<col width="9%" />
							<col width="12%" />
							<col width="11%" />
							<col width="12%" />
							<col width="9%" />
							<col width="12%" />
							<col width="14%" />
						</colgroup>
						<tbody>
               		  		<tr>
								<th scope="row"><span class="colorPoint">*</span>고객명</th>
								<td colspan="7"><input name="customerName"  id="customer" value="${manInfoItem.customerName}" title="고객명" class="inputbox w80" type="text" />
								<a class="ic_search valign_middle" id="searchButton" href="#a"><span>검색</span></a>
								</td>
                                <td rowspan="6" class="textCenter">
                                <!--<img src="../../images/common/photo_110x90.gif" alt="image" />
								<div class="prPhoto_edit"><a href="#a"><img src="../../images/common/btn_edit.png" alt="edit" /></a></div>-->
                                <c:choose>
								<c:when test="${manInfoItem.sajinSys == null || manInfoItem.sajinSys ==''}">
									<img src="<c:url value='/base/images/common/noimage_110x90.gif'/>" title="인물 사진" width="110" height="90"/>
								</c:when>				 
								<c:otherwise>
									<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${manInfoItem.sajinSys}" title="<ikep4j:message pre="${prefix}" key="img.managermessageImage" />"  width="110" height="90" />
								</c:otherwise>
								</c:choose>
                                </td>
							</tr>
							<tr>
								<th scope="row"><span class="colorPoint">*</span>성명</th>
								<td><input name="man" title="성명" value="${manInfoItem.man}" class="inputbox w90" type="text" /></td>
								<th scope="row">직위</th>
								<td><input name="jobTitle" title="직위" value="${manInfoItem.jobTitle}" class="inputbox w90" type="text" /></td>
                                <th scope="row">군경력</th>
								<td><input name="army" title="군경력" value="${manInfoItem.army}" class="inputbox w90" type="text" /></td>
                                <th scope="row">출신지역</th>
								<td><input name="nativeArea" title="출신지역" value="${manInfoItem.nativeArea}" class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row">전화</th>
								<td><input name="phone" title="전화" value="${manInfoItem.phone}" class="inputbox w90" type="text" /></td>
								<th scope="row">휴대전화</th>
								<td><input name="cellPhone" title="휴대전화" value="${manInfoItem.cellPhone}" class="inputbox w90" type="text" /></td>
                                <th scope="row">생년월일</th>
								<td><input name="birthday" title="생년월일" value="${manInfoItem.birthday}" class="inputbox w90" type="text" /></td>
                                <th scope="row">종교</th>
								<td><input name="religion" title="종교" value="${manInfoItem.religion}" class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row">이메일</th>
								<td colspan="3"><input name="email" title="이메일" value="${manInfoItem.email}"  class="inputbox w90" type="text"/></td>
								<th scope="row">흡연여부</th>
								<td><input name="cigarette" title="흡연여부" value="${manInfoItem.cigarette}"  class="inputbox w90" type="text" /></td>
                                <th scope="row">취미</th>
								<td><input name="hobby" title="취미" value="${manInfoItem.hobby}"  class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row">주소</th>
								<td colspan="5"><input name="homeAddress" title="주소" value="${manInfoItem.homeAddress}"  class="inputbox w100" type="text" /></td>
								<th scope="row">주량</th>
								<td><input name="drink" title="주량" value="${manInfoItem.drink}"  class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row">출신고교</th>
								<td><input name="nativeHighschool" title="출신고교" value="${manInfoItem.nativeHighschool}"  class="inputbox w90" type="text" /></td>
								<th scope="row">출신대학</th>
								<td><input name="nativeUniversity" title="출신대학" value="${manInfoItem.nativeUniversity}"  class="inputbox w90" type="text" /></td>
                                <th scope="row">출신교(기타)</th>
								<td colspan="3"><input name="nativeEtcSchool" title="출신교" value="${manInfoItem.nativeEtcSchool}"  class="inputbox w90" type="text"/></td>
							</tr>
                            <tr>															
								<th scope="row">사진파일</th>
								<td colspan="8">                           
                                <input name="file" type="file" class="file w100" title="이미지 등록">                                     
                                </td>
                           </tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                
                <div class="blockBlank_10px"></div>		
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>경력</h3>
				</div>
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="경력">
						<caption>경력</caption>
                        <colgroup>
                            <col width="30%"/>
                            <col width="30%"/>
                            <col width="40%"/>
                        </colgroup>
						<tbody id="careerBody">
               		  		<tr>
								<th scope="row" class="textCenter">주요경력</th>
								<th scope="row" class="textCenter">이전경력</th>
								<th scope="row" class="textCenter">조직 내 주요 업무</th>
							</tr>
							
								<c:choose>
									<c:when test="${fn:length(manCareer) eq '0'}">
										<tr>							
											<td><input name="mainCareer" title="주요경력" value="" class="inputbox w90" type="text" /></td>
											<td><input name="preCareer" title="이전경력" value="" class="inputbox w90" type="text" /></td>
											<td><input name="mainBusiness" title="조직 내 주요 업무" value="" class="inputbox w90" type="text" size="50" /></td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach var="manCareer" items="${manCareer}" varStatus="status">
											<tr>
												<td><input name="mainCareer" title="주요경력" value="${manCareer.mainCareer}" class="inputbox w90" type="text" /></td>
												<td><input name="preCareer" title="이전경력" value="${manCareer.preCareer}" class="inputbox w90" type="text" /></td>
												<td><input name="mainBusiness" title="조직 내 주요 업무" value="${manCareer.mainBusiness}" class="inputbox w90" type="text" size="50" /></td>
												<input type="hidden"  name="careernum" value="${manCareer.careernum}" />
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
                        </tbody>
					</table>
				</div>
				<!--//blockDetail End-->
                <div class="blockBlank_10px"></div>		
                
                <!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>가족사항</h3>
				</div>
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="가족사항">
						<caption>가족사항</caption>
                        <colgroup>
                            <col width="50%"/>
                            <col width="50%"/>
                        </colgroup>
						<tbody  id="familyBody" >
               		  		<tr>
								<th scope="row" class="textCenter">관계</th>
								<th scope="row" class="textCenter">성명</th>
							</tr>
							<c:choose>
								<c:when test="${fn:length(manFamily) eq '0'}">
									<tr>
										<td><input name="familyRelation" title="관계" value="" class="inputbox w90" type="text" /></td>
										<td><input name="familyName" title="성명"  value="" class="inputbox w90" type="text" /></td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="manFamily" items="${manFamily}" varStatus="status">
										<tr>
											<td><input name="familyRelation" title="관계" value="${manFamily.familyRelation}" class="inputbox w90" type="text" /></td>
											<td><input name="familyName" title="성명"  value="${manFamily.familyName}" class="inputbox w90" type="text" /></td>
											<input type="hidden" name="familyNum" value="${manFamily.familyNum}">
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
                        </tbody>
					</table>
				</div>
				<!--//blockDetail End-->
                <div class="blockBlank_10px"></div>	
                
                <!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>성격 및 특이사항</h3>
				</div>
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="성격 및 특이사항">
						<caption></caption>
                        <colgroup>
                            <col width="100%"/>
                        </colgroup>
						<tbody>
                            <tr>
								<td><textarea name="etc"  class="w100" title="성격 및 특이사항" cols="" rows="10">${manInfoItem.etc}</textarea></td>
							</tr>
                        </tbody>
					</table>
				</div>
				<!--//blockDetail End-->
    
                <!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="javascript:addCareerCnt();"><span>경력추가</span></a></li>
						<li><a class="button" href="javascript:addFamilyCnt();"><span>가족추가</span></a></li>
						<li><a class="button saveBoardItemButton" href="#a"><span>수정</span></a></li>
						<li><a class="button" href="<c:url value='/support/customer/customerCommon/manInfo.do'/>"><span>목록</span></a></li>
					</ul>
				</div>

				
				
				
</form>
            