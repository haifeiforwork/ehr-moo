<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="prefix" value="ui.support.partner.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.partner.manInfoItemList" />

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
		$('#partnerName').keypress( function(event) {
	    	if(event.keyCode == '13'){
	    		goSync();
	    		return false;
	    	}
		});
		
		$("a.saveBoardItemButton").click(function() {
			

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
				/* partnerName  : {required : true}, */
				man			  : {required : true},
			},
			messages : {
				/* partnerName     : {direction : "top",    required : "<ikep4j:message key="NotNull.partner.manInfo.partnerName" />"}, */
				man : {direction : "bottom", required : "<ikep4j:message key="NotNull.partner.manInfo.man" />"},
			},   
			
		    submitHandler : function(form) {  
		    	
		    	if(confirm("수정한 내용을 저장하시겠습니까?")) {
		    		var partnerId = $("#partnerId").val();
		    		var partnerName = $("#partner").val();
					var tempPartnerId = "${manInfoItem.partnerId}";
					var tempPartnerName = "${manInfoItem.partnerName}";
					if( partnerId == tempPartnerId && partnerName != tempPartnerName){
						alert("거래처명은 검색한 결과만 사용 가능합니다.");
						$("#partner").focus();
					}else if(partnerId != tempPartnerId && partnerName == tempPartnerName){
						alert("거래처명은 검색한 결과만 사용 가능합니다.");
						$("#partner").focus();
					}else{	
						$("body").ajaxLoadStart("button");
						form.submit();
					}
				}	
		    }
			
			
		});   
		
		
		$("#searchButton").click(function(){
			searchPartner();
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

function searchPartner(){

	var name = document.getElementById("partner").value;
	if(!name){
			alert("거래처명을 입력해주세요.");
		}else{ 				
			 var url = "<c:url value='/support/partner/partnerCommon/popupPartner.do?partner='/>"+name;
			 iKEP.popupOpen(url,{width:700,height:490},'PartnerSearch'); 
			
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
		
		
<form id="registerForm" method="post" action="<c:url value='/support/partner/partnerCommon/modifyManInfo.do'/>" enctype="multipart/form-data">
	<input name="seqNum"  type="hidden" value="${manInfoItem.seqNum}" />
	<input type="hidden" id="userId" name="userId" value="${user.userId}" />
	<input type="hidden" id="registerId" name="registerId" value="${manInfoItem.registerId}" />
	<input type="hidden" id="familyTrTemp" name="familyTrTemp" value="" />
	<input type="hidden" id="careerTrTemp" name="careerTrTemp" value="" /> 	
	<input type="hidden" name="partnerId" id="partnerId" value="${manInfoItem.partnerId}" />
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
						<li><a class="button saveBoardItemButton" href="#a"><span>수정</span></a></li>
						<li><a class="button" href="<c:url value='/support/partner/partnerCommon/detailManInfo.do'/>?seqNum=${manInfoItem.seqNum}"><span>취소</a></li>
						<li><a class="button" href="<c:url value='/support/partner/partnerCommon/manInfo.do'/>"><span>목록</span></a></li>
					</ul>
				</div>
				<div class="subTitle_2 noline">
					<h3>거래처정보</h3>
				</div>
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="상담이력 상세조회">
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
								<td><input name="partnerName" id="partnerName" title="고객명" value="${manInfoItem.partnerName}"  class="inputbox w90" type="text" /><a class="ic_search valign_middle searchPartnerId" href="javascript:goSync();"><span>검색</span></a></td>
								<th scope="row">대표자</th>
								<td colspan="3"><input name="ceoName" id="ceoName" title="대표자"  value="${manInfoItem.ceoName}"  class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
                                <th scope="row">업태</th>
								<td><input name="category" id="category" title="업태" value="${manInfoItem.category}"  class="inputbox w90" type="text" /></td>
								<th scope="row">사업자번호</th>
								<td colspan="3"><input name="businessNo" id="businessNo" title=""  value="${manInfoItem.businessNo}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">종목</th>
								<td><input name="sector" id="sector" title="종목" value="${manInfoItem.sector}"  class="inputbox w90" type="text" /></td>
								<th scope="row">법인번호</th>
								<td colspan="3"><input name="corporationNo" id="corporationNo" title=""  value="${manInfoItem.corporationNo}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">우편번호</th>
								<td><input name="zipNo" id="zipNo" title="" value="${manInfoItem.zipNo}"  class="inputbox w90" type="text" /></td>
								<th scope="row">담당자</th>
								<td colspan="3"><input name="keyMan" id="keyMan" title="담당자"  value="${manInfoItem.keyMan}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td><input name="address" id="address" title="주소" value="${manInfoItem.address}"  class="inputbox w90" type="text" /></td>
								<th scope="row">연락처</th>
								<td colspan="3"><input name="contacts" id="contacts" title="연락처"  value="${manInfoItem.contacts}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">대표전화</th>
								<td><input name="mainPhone" id="mainPhone" title="대표전화" value="${manInfoItem.mainPhone}"  class="inputbox w90" type="text" /></td>
								<th scope="row">Fax</th>
								<td colspan="3"><input name="fax" id="fax" title="Fax" value="${manInfoItem.fax}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">E-mail</th>
								<td><input name="email" id="email" title="" value="${manInfoItem.email}"  class="inputbox w90" type="text" /></td>
								<th scope="row">SAP ID</th>
								<td colspan="3"><input name="sapId" id="sapId" title="" value="${manInfoItem.sapId}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">구분</th>
								<td>
									<select title="구분" name="purpose" id="purpose">
				                        <option value=""  >선택</option>
				                        <option value="CR01" <c:if test="${manInfoItem.purpose eq 'CR01'}">selected="selected"</c:if>>주원료</option>
				                        <option value="CR02" <c:if test="${manInfoItem.purpose eq 'CR02'}">selected="selected"</c:if>>부원료</option>
				                        <option value="CR03" <c:if test="${manInfoItem.purpose eq 'CR03'}">selected="selected"</c:if>>기자재</option>
				                        <option value="CR04" <c:if test="${manInfoItem.purpose eq 'CR04'}">selected="selected"</c:if>>포장재</option>
				                        <option value="CR05" <c:if test="${manInfoItem.purpose eq 'CR05'}">selected="selected"</c:if>>펄프판매</option>
				                        <option value="CR07" <c:if test="${manInfoItem.purpose eq 'CR07'}">selected="selected"</c:if>>펠릿</option>
				                        <option value="CR06" <c:if test="${manInfoItem.purpose eq 'CR06'}">selected="selected"</c:if>>기타</option>
				                    </select> 
								</td>
								<th scope="row">품목</th>
								<td colspan="3"><input name="items" title="" value="${manInfoItem.items}"  class="inputbox w90" type="text" /></td>
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
               		  		<input name="partner"  id="partner" value="${manInfoItem.partnerName}" title="거래처명" class="inputbox w80" type="hidden" />
							<tr>
								<th scope="row">성명</th>
								<td><input name="man" title="성명" value="${manInfoItem.man}" class="inputbox w90" type="text" /></td>
								<th scope="row">직위</th>
								<td><input name="jobTitle" title="직위" value="${manInfoItem.jobTitle}" class="inputbox w90" type="text" /></td>
                                <th scope="row">군경력</th>
								<td><input name="army" title="군경력" value="${manInfoItem.army}" class="inputbox w90" type="text" /></td>
                                <th scope="row">출신지역</th>
								<td><input name="nativeArea" title="출신지역" value="${manInfoItem.nativeArea}" class="inputbox w90" type="text" /></td>
								<td rowspan="5" class="textCenter">
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
								<td colspan="3"><input name="ceoEmail" title="이메일" value="${manInfoItem.ceoEmail}"  class="inputbox w90" type="text"/></td>
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
							<c:forEach var="manCareer" items="${manCareer}" varStatus="status">
							<c:if test="${status.index==0}">
                            <tr>
								<td><input name="joinDate"   id="joinDate" class="inputbox w80 datepicker" type="text" value="${manCareer.joinDate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>   </td>
                                <td><input name="leaveDate"   id="leaveDate" class="inputbox w80 datepicker" type="text" value="${manCareer.leaveDate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></td>
                                <td><input name="moveDate"   id="moveDate" class="inputbox w80 datepicker" type="text" value="${manCareer.moveDate}"  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/></td>
							</tr>
							</c:if>
							</c:forEach>
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
						<li><a class="button saveBoardItemButton" href="#a"><span>수정</span></a></li>
						<li><a class="button" href="<c:url value='/support/partner/partnerCommon/detailManInfo.do'/>?seqNum=${manInfoItem.seqNum}"><span>취소</a></li>
						<li><a class="button" href="<c:url value='/support/partner/partnerCommon/manInfo.do'/>"><span>목록</span></a></li>
					</ul>
				</div>

				
				
				
</form>
            