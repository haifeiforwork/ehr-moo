<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">

var familyTrTemp = $jq("#familyBody").children("tr").length+3;
var careerTrTemp = $jq("#careerBody").children("tr").length+3;

(function($) {

	
	$(document).ready( function() {
		
		$('#partnerName').keypress( function(event) {
	    	if(event.keyCode == '13'){
	    		goSync();
	    		return false;
	    	}
		});
		$("a.addManInfoButton").click(function(){
			
			$jq("#familyTrTemp").val(familyTrTemp);
			$jq("#careerTrTemp").val(careerTrTemp);
			
			if($('#purpose > option:selected').val() == ""){
				alert("구분을 선택해 주세요");
				return;
			}
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
/* 				partnerName  : {required : true}, */	
				man			  : {required : true},
		},
			messages : {
				/* partnerName     : {direction : "top",    required : "<ikep4j:message key="NotNull.partner.manInfo.partnerName" />"},*/
				man : {direction : "bottom", required : "<ikep4j:message key="NotNull.partner.manInfo.man" />"}, 
			},   
			
		    submitHandler : function(form) {  
		    	
		    	
		    	var partnerId = $("#partnerId").val();
		    	
		    	
		    	
		    	if(confirm("등록하시겠습니까?")) {
		    		
		    		/* if( partnerId == ''){
						alert("거래처명은 검색한 결과만 사용 가능합니다.");
						$("#partner").focus();
					}else{	
						$("body").ajaxLoadStart("button");
						form.submit();
					} */
		    		$("body").ajaxLoadStart("button");
					form.submit();
					
				}	 
		    }
			
			
		}); 
		
		
		
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#partner').keypress( function(event) {
        	if(event.keyCode == '13'){
				searchPartner();
        		return false;
        	}
		});
		
        $("input[name=joinDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
        $("input[name=leaveDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
        $("input[name=moveDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
	});
	
	sapInfoReceive = function(result){
		$("#partnerName").val(result.partnerName);
		$("#sapId").val(result.sapId);
		$("#category").val(result.category);
		$("#sector").val(result.sector);
		$("#zipNo").val(result.zipNo);
		$("#address").val(result.address);
		$("#mainPhone").val(result.mainPhone);
		$("#email").val(result.email);
		$("#ceoName").val(result.ceoName);
		$("#businessNo").val(result.businessNo);
		$("#corporationNo").val(result.corporationNo);
		$("#keyMan").val(result.keyMan);
		$("#contacts").val(result.contacts);
		$("#fax").val(result.fax);
	};
	
	goSync = function() {
		var name = $("#partnerName").val();
		if(name == ""){
			alert("거래처명을 입력하세요.");
			return;
		}
		
		var url = "<c:url value='/support/partner/partnerQualityClaimSell/popupSAPSync.do?name='/>"+name;
		
		window.open( url ,"","resizable=yes,scrollbars=yes,width=800,height=600");
		
	};
	
	
	
})(jQuery);


function searchPartner(){

	var name = document.getElementById("partner").value;
	if(!name){
			alert("거래처명을 입력해주세요.");
		}else{ 				
			 var url = "<c:url value='/support/partner/partnerCommon/popupPartner.do?partner='/>"+name;
			 iKEP.popupOpen(url,{width:700,height:490},'PartnerSearch'); 
			
		}

}



function addFamilyCnt(){

 	var familyTrs =$jq("#familyBody").children("tr");
 
 	//alert(familyTrs.length);
	if(familyTrs.length-1<6){
	 	$jq("#familyBody").append(
				  "<tr>"+
					"<td><input name=\"familyRelation\" title=\"관계\" class=\"inputbox w100\" type=\"text\"  /></td>"+
	                "<td><input name=\"familyName\" title=\"성명\" class=\"inputbox w100\" type=\"text\"  /></td>"+
				  "</tr>"
	 	);
	 	familyTrTemp++;
		iKEP.iFrameContentResize();
	}else{
		alert("최대 6개까지 추가 할 수 있습니다.");
	}
		
}

function addCareerCnt(){
	debugger;
 	var careerTrs =$jq("#careerBody").children("tr");
 
 	//alert($jq("#registerForm").height());
	if(careerTrs.length-1<6){
	 	$jq("#careerBody").append(		 
	 			 "<tr>"+				
		 			 "<td><input name=\"mainCareer\" title=\"주요경력\" class=\"inputbox w100\" type=\"text\"/></td>"+
	                 "<td><input name=\"preCareer\" title=\"이전경력\" class=\"inputbox w100\" type=\"text\"  /></td>"+
	                 "<td><input name=\"mainBusiness\" title=\"조직 내 주요 업무\" class=\"inputbox w100\" type=\"text\"  /></td>"+
				 "</tr>"
	 	);
	 	careerTrTemp++;
		iKEP.iFrameContentResize();
	}else{
		alert("최대 6개까지 추가 할 수 있습니다.");
	}
		
}





</script>


<form id="registerForm" method="post" action="<c:url value='/support/partner/partnerCommon/createManInfo.do'/>" enctype="multipart/form-data">
<input type="hidden" id="userId" name="userId" value="${user.userId}" />
<input type="hidden" id="familyTrTemp" name="familyTrTemp" value="" />
<input type="hidden" id="careerTrTemp" name="careerTrTemp" value="" />
<input type="hidden" id="partnerId" name="partnerId" value="" /> 


	<h1 class="none">컨텐츠영역</h1>
								
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>거래처/인물 정보</h2>	
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
				
				<div class="blockButton"> 
					<ul>
						<li><a class="button addManInfoButton" href="#a"><span>등록</span></a></li>
						<li><a class="button" href="<c:url value='/support/partner/partnerCommon/manInfo.do'/>"><span>목록</span></a></li>
					</ul>
				</div>
				<div class="subTitle_2 noline">
					<h3>거래처정보</h3>
				</div>
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table>
						<caption></caption>
                        <colgroup>
                            <col width="10%"/>
                            <col width="32%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="23%"/>
                        </colgroup>
						<tbody>
							<tr>
                                <th scope="row">거래처명</th>
								<td><input name="partnerName" id="partnerName" title="거래처명" class="inputbox w90" type="text" /><a class="ic_search valign_middle searchPartnerId" href="javascript:goSync();"><span>검색</span></a></td>
								<th scope="row">대표자</th>
								<td colspan="3"><input name="ceoName" id="ceoName" title="대표자" class="inputbox w90" type="text"  /></td>
							</tr>
                            <tr>
                                <th scope="row">업태</th>
								<td><input name="category" id="category" title="업태" class="inputbox w90" type="text"/></td>
								<th scope="row">사업자번호</th>
								<td colspan="3"><input name="businessNo" id="businessNo" title="" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
                                <th scope="row">종목</th>
								<td><input name="sector" id="sector" title="종목" class="inputbox w90" type="text"/></td>
								<th scope="row">법인번호</th>
								<td colspan="3"><input name="corporationNo" id="corporationNo" title="" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
                                <th scope="row">우편번호</th>
								<td><input name="zipNo" id="zipNo" title="" class="inputbox w90" type="text"/></td>
								<th scope="row">담당자</th>
								<td colspan="3"><input name="keyMan" id="keyMan" title="Key Man" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td><input name="address" title="주소" id="address" class="inputbox w90" type="text"  /></td>
								<th scope="row">연락처</th>
								<td colspan="3"><input name="contacts" id="contacts" title="연락처" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">대표전화</th>
								<td><input name="mainPhone" id="mainPhone" title="대표전화" class="inputbox w90" type="text"/></td>
								<th scope="row">Fax</th>
								<td colspan="3"><input name="fax" id="fax" title="Fax" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">E-mail</th>
								<td><input name="email" title="email" id="email" class="inputbox w90" type="text"/></td>
								<th scope="row">SAP ID</th>
								<td colspan="3"><input name="sapId" id="sapId" title="" class="inputbox w90" type="text" readonly="readonly" /></td>
							</tr> 
							<tr>
								<th scope="row">구분</th>
								<td>
									<select title="구분" name="purpose" id="purpose">
				                        <option value=""  >선택</option>
				                        <option value="CR01" >주원료</option>
				                        <option value="CR02" >부원료</option>
				                        <option value="CR03" >기자재</option>
				                        <option value="CR04" >포장재</option>
				                        <option value="CR05" >펄프판매</option>
				                        <option value="CR07" >펠릿</option>
				                        <option value="CR06" >기타</option>
				                    </select> 
								</td>
								<th scope="row">품목</th>
								<td colspan="3"><input name="items" title="" class="inputbox w90" type="text"  /></td>
							</tr>
							</tbody>
					</table>
					</div>
				<div class="blockBlank_10px"></div>	
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
                            <col width="9%"/>
                            <col width="11.5%"/>
                            <col width="9%"/>
                            <col width="11.5%"/>
                            <col width="9%"/>
                            <col width="11.5%"/>
                            <col width="9%"/>
                            <col width="11.5%"/>
                            <col width="18%"/>
                        </colgroup>
						<tbody>
							<tr><spring:bind path="manInfoItem.man">
								<th scope="row">성명</th>
								<td><input name="${status.expression}" 
										   title="성명" 
										   class="inputbox w90"
										   value="${status.value}"  
										   type="text" 
										    /></td></spring:bind>
								<spring:bind path="manInfoItem.jobTitle">
								<th scope="row">직위</th>
								<td><input name="${status.expression}" 
									       title="직위" 
									       class="inputbox w90"
									       value="${status.value}"   
									       type="text" 
									       /></td></spring:bind>
								<spring:bind path="manInfoItem.army">
                                <th scope="row">군경력</th>
								<td><input name="${status.expression}"  
										   title="군경력" 
										   class="inputbox w90"
										   value="${status.value}" 
										   type="text" 
										  /></td>
                                </spring:bind>
                                <spring:bind path="manInfoItem.nativeArea">
                                <th scope="row">출신지역</th>
								<td><input name="${status.expression}" 
										   title="출신지역" 
										   class="inputbox w90" 
										   value="${status.value}" 
										   type="text" 
										   /></td>
								</spring:bind>
								<td rowspan="5" class="textCenter">
                                <img src="<c:url value='/base/images/common/noimage_110x90.gif'/>" title="인물 사진" height="140"/>
                                </td>
							</tr>
                            <tr>
                            <spring:bind path="manInfoItem.phone">
								<th scope="row">전화</th>
								<td><input name="${status.expression}" 
										   title="전화" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										   /></td>
							</spring:bind>
							<spring:bind path="manInfoItem.cellPhone">							
								<th scope="row">휴대전화</th>
								<td><input name="${status.expression}" 
										  title="휴대전화" 
										  class="inputbox w90" 
										  value="${status.value}"
										  type="text" 
										   /></td>
							</spring:bind>
							<spring:bind path="manInfoItem.birthday">
                                <th scope="row">생년월일</th>
								<td><input name="${status.expression}" 
										   title="생년월일" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										    /></td>
							</spring:bind>
							<spring:bind path="manInfoItem.religion">
                                <th scope="row">종교</th>
								<td><input name="${status.expression}" 
										   title="종교" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										    /></td>
							</spring:bind>
							</tr>
                            <tr>
                            <spring:bind path="manInfoItem.ceoEmail">
								<th scope="row">이메일</th>
								<td colspan="3"><input name="${status.expression}" 
													   title="이메일" 
													   class="inputbox" 
													   value="${status.value}"
													   type="text w90" 
													  /></td>
							</spring:bind>
							<spring:bind path="manInfoItem.cigarette">
								<th scope="row">흡연여부</th>
								<td><input name="${status.expression}" 
										   title="흡연여부" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										    /></td>
							</spring:bind>
							<spring:bind path="manInfoItem.hobby">
                                <th scope="row">취미</th>
								<td><input name="${status.expression}"
										   title="취미" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										    /></td>
							</spring:bind>
							</tr>
                            <tr>
                            <spring:bind path="manInfoItem.homeAddress">
								<th scope="row">주소</th>
								<td colspan="5"><input name="${status.expression}" 
												       title="주소" 
												       class="inputbox w100" 
												       value="${status.value}"
												       type="text" 
												        /></td>
							</spring:bind>
							<spring:bind path="manInfoItem.drink">
								<th scope="row">주량</th>
								<td><input name="${status.expression}" 
										   title="주량" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										   /></td>
							</spring:bind>
							</tr>
                            <tr>
                            <spring:bind path="manInfoItem.nativeHighschool">
								<th scope="row">출신고교</th>
								<td><input name="${status.expression}"  
										   title="출신고교" 
										   class="inputbox w90" 
										   value="${status.value}"
										   type="text" 
										  /></td>
							</spring:bind>		   
							<spring:bind path="manInfoItem.nativeUniversity">
								<th scope="row">출신대학</th>
								<td><input name="${status.expression}" 
									       title="출신대학" 
									       class="inputbox w90" 
									       value="${status.value}"
									       type="text" 
									        /></td>
							</spring:bind>	
							<spring:bind path="manInfoItem.nativeEtcSchool">       
                                <th scope="row">출신교(기타)</th>
								<td colspan="3"><input name="${status.expression}"  
													   title="출신교" 
													   class="inputbox w90" 
													   value="${status.value}"
													   type="text" 
													   /></td>
							</spring:bind>						   
							</tr>
                            <tr>
                            <spring:bind path="manInfoItem.sajinSys">
								<th scope="row">사진파일</th>
								<td colspan="8">                               
                                <input name="file" type="file" class="file w100" title="이미지 등록">                                     
                                </td>
                            </spring:bind>    
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                
                <div class="blockBlank_10px"></div>		
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>경력 <img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" align="bottom" onclick="addCareerCnt();"/></h3>
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
                            <tr>
								<td><input name="mainCareer" title="주요경력" class="inputbox w100" type="text" /></td>
                                <td><input name="preCareer" title="이전경력" class="inputbox w100" type="text"  /></td>
                                <td><input name="mainBusiness" title="조직 내 주요 업무" class="inputbox w100" type="text"  /></td>
							</tr>
                            <tr>
								<td><input name="mainCareer" title="주요경력" class="inputbox w100" type="text"  /></td>
                                <td><input name="preCareer" title="이전경력" class="inputbox w100" type="text"  /></td>
                                <td><input name="mainBusiness" title="조직 내 주요 업무" class="inputbox w100" type="text"  /></td>
							</tr>
                            <tr>
								<td><input name="mainCareer" title="주요경력" class="inputbox w100" type="text"/></td>
                                <td><input name="preCareer" title="이전경력" class="inputbox w100" type="text"  /></td>
                                <td><input name="mainBusiness" title="조직 내 주요 업무" class="inputbox w100" type="text"  /></td>
							</tr>
							
                        </tbody>
					</table>
					<table>
						<caption></caption>
                        <colgroup>
                            <col width="30%"/>
                            <col width="30%"/>
                            <col width="40%"/>
                        </colgroup>
						<tbody>
               		  		<tr>
								<th scope="row" class="textCenter">입사일</th>
								<th scope="row" class="textCenter">퇴사일</th>
								<th scope="row" class="textCenter">부서이동</th>
							</tr>
                            <tr>
								<td><input name="joinDate"   id="joinDate" class="inputbox w80 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>   </td>
                                <td><input name="leaveDate"   id="leaveDate" class="inputbox w80 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></td>
                                <td><input name="moveDate"   id="moveDate" class="inputbox w80 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></td>
							</tr>
                        </tbody>
					</table>
				</div>
				
				<!--//blockDetail End-->
                <div class="blockBlank_10px"></div>		
                
                <!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>가족사항 <img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" align="bottom" onclick="addFamilyCnt();"/></h3>
				</div>
				<!--//subTitle_2 End-->	
                
                <!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="가족사항">
						<caption>가족사항</caption>
                        <colgroup>
                            <col width="30%"/>
                            <col width="70%"/>
                        </colgroup>
						<tbody id="familyBody">
               		  		<tr>
								<th scope="row" class="textCenter">관계</th>
								<th scope="row" class="textCenter">성명</th>
							</tr>
							
							  <tr>
								<td><input name="familyRelation" title="관계" class="inputbox w100" type="text"  /></td>
                                <td><input name="familyName" title="성명" class="inputbox w100" type="text"  /></td>
							  </tr>
							  <tr>
								<td><input name="familyRelation" title="관계" class="inputbox w100" type="text"  /></td>
                                <td><input name="familyName" title="성명" class="inputbox w100" type="text"  /></td>
							  </tr>
							  <tr>
								<td><input name="familyRelation" title="관계" class="inputbox w100" type="text" /></td>
                                <td><input name="familyName" title="성명" class="inputbox w100" type="text"  /></td>
							  </tr>
					
                          
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
								<td><textarea name="etc" class="w100" title="성격 및 특이사항" cols="" rows="10"></textarea></td>
							</tr>
                        </tbody>
					</table>
				</div>
				<!--//blockDetail End-->
                
                <!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button addManInfoButton" href="#a"><span>등록</span></a></li>
						<li><a class="button" href="<c:url value='/support/partner/partnerCommon/manInfo.do'/>"><span>목록</span></a></li>
					</ul>
				</div>
</form>