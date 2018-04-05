<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
var fileController = "";		//파일등록폼
var relComCnt = 1;
(function($) {

	
	$(document).ready( function() {
		

		$('#partnerName').keypress( function(event) {
	    	if(event.keyCode == '13'){
	    		goSync();
	    		return false;
	    	}
		});
		$("a.createQualityCalim").click(function(){
			var company = "";
			var type = "";
			var note = "";
			var relationCompany = "";
			for(i=0;i<relComCnt;i++){
				company = $("#relCompany"+i).val();
				type = $("#relType"+i).val();
				note = $("#relNote"+i).val();
				if(relationCompany == ""){
					if(company != ""){
						relationCompany = company+"|"+type+"|"+note;
					}
				}else{
					if(company != ""){
						relationCompany = relationCompany+"^"+company+"|"+type+"|"+note;
					}
				}
			}
			
			if($('#purpose > option:selected').val() == ""){
				alert("구분을 선택해 주세요");
				return;
			}
			
			$jq('input[name=relationCompany]').val(relationCompany);
			var tmpFacName=jQuery("#factory option:selected").text();
			$jq('input[name=factoryName]').val(tmpFacName);
			var tmpClaimGubunName=jQuery("#claimGubun option:selected").text();
			$jq('input[name=claimGubunName]').val(tmpClaimGubunName);
			$("#QualityClaimSellForm").trigger("submit"); 
			return false;
			
		});
		
		var relCompany = "${qualityClaimSell.relationCompany}";
		if(relCompany != ""){
			var relCompany1 = relCompany.split("^");
			var relCompany2 = "";
			for(i=0;i<relCompany1.length;i++){
				relCompany2 = relCompany1[i].split("|");
				$jq("#relationCompanyBody").append(
					"<tr>"+
					    "<td><input name=\"relCompany"+i+"\" id=\"relCompany"+i+"\" value=\""+relCompany2[0]+"\" title=\"업체\" class=\"inputbox w100\" type=\"text\" /></td>"+
					    "<td><input name=\"relType"+i+"\" id=\"relType"+i+"\" value=\""+relCompany2[1]+"\" title=\"업종\" class=\"inputbox w100\" type=\"text\" /></td>"+
					    "<td><input name=\"relNote"+i+"\" id=\"relNote"+i+"\" value=\""+relCompany2[2]+"\" title=\"비고\" class=\"inputbox w100\" type=\"text\" /></td>"+
					    "<td><input name=\"relNote"+i+"\" id=\"relNote"+i+"\" value=\""+relCompany2[2]+"\" title=\"비고\" class=\"inputbox w100\" type=\"text\" /></td>"+
					 "</tr>"
				);
			}
			relComCnt = relCompany1.length;	
		}else{
			$jq("#relationCompanyBody").append(
				"<tr>"+
				    "<td><input name=\"relCompany0\" id=\"relCompany0\" value=\"\" title=\"업체\" class=\"inputbox w100\" type=\"text\" /></td>"+
				    "<td><input name=\"relType0\" id=\"relType0\" value=\"\" title=\"생산Capacity\" class=\"inputbox w100\" type=\"text\" /></td>"+
				    "<td><input name=\"relNote0\" id=\"relNote0\" value=\"\" title=\"품목\" class=\"inputbox w100\" type=\"text\" /></td>"+
				    "<td><input name=\"relNote0\" id=\"relNote0\" value=\"\" title=\"금액\" class=\"inputbox w100\" type=\"text\" /></td>"+
				 "</tr>"
			);
		}
		
		
		 
		new iKEP.Validator("#QualityClaimSellForm", {   
			rules  : {
				subject     : {required : true},
				counselDate : {required : true},
				counselor   : {required : true},
				partner    : {required : true},
				client      : {required : true},
				content		: {required : true}
			},
			messages : {
				subject     : {direction : "bottom",    required : "<ikep4j:message key="NotNull.partner.qualityClaimSell.title" />"},
				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.partner.qualityClaimSell.counselDate" />"},
				counselor   : {direction : "top", required : "<ikep4j:message key="NotNull.partner.qualityClaimSell.counselor" />"}, 
				partner    : {direction : "bottom",  required: "<ikep4j:message key="NotNull.partner.qualityClaimSell.partner" />"},
				client      : {direction : "top", required : "<ikep4j:message key="NotNull.partner.qualityClaimSell.client" />"},
				content      : {direction : "bottom", required : "<ikep4j:message key="NotNull.partner.qualityClaimSell.content" />"},		
			},    
			
		    submitHandler : function(form) { 
			
				
		   /*  	if(confirm("저장하시겠습니까?")) {
		    		
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) { 
								
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					});
				} */
		    	
		    	if(confirm("저장하시겠습니까?")) {
		    		//alert("oldSizes:"+oldSizes+"\n"+document.getElementById("FileUploadManager").Size+"\n"+document.all["FileUploadManager"].Count);
		    		//if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		    		//	btnTransfer_Onclick("QualityClaimSellForm");
		    		//}else{
		    		//	oldFileSetting(oldFiles , form);
		    			writeSubmit(form);
		    		//}
				}
		    	
		    	
		    	
		    }
		}); 
		
		
		   /*dextFileUploadInit(""+20*1024*1024 ,"1", "all");

		   var oldFiles;
		   var oldSizes;
		   <c:if test="${not empty fileDataListJson}"> 
		   oldFiles = ${fileDataListJson};
		   $jq.each(oldFiles,function(index){
			   var fileInfo = $.extend({index:index}, this);
			   document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
			});
		   
		   dextFileUploadRefresh(); 
		   oldSizes =document.getElementById("FileUploadManager").Size;
		   </c:if> 
			   */
			   
			writeSubmit = function(targetForm){
				
			
					$("body").ajaxLoadStart("button");
					targetForm.submit();
				
			};
		
		/* 
		 var uploaderOptions = {// 사용할 수 있는 옵션 항목
					<c:if test="${!empty(fileDataListJson)}">
						files  :  ${fileDataListJson}, 
					</c:if>
				    // isUpdate : true,    // 등록 및 수정일때 true
			        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
			        maxTotalSize : 20*1024*1024,    // 최대 업로드 가능 용량(개별 파일 사이즈임)
			        allowFileType  : "all"    
		 };
		 
		var fileController = new iKEP.FileController("#QualityClaimSellForm", "#fileUploadArea", uploaderOptions);  */
		 
		//달력
		$("input[name=counselDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
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

relComPlus = function(){
	
	$jq("#relationCompanyBody").append(
		 "<tr>"+
            "<td><input name=\"relCompany"+relComCnt+"\" id=\"relCompany"+relComCnt+"\" title=\"업체\" class=\"inputbox w100\" type=\"text\" /></td>"+
            "<td><input name=\"relType"+relComCnt+"\" id=\"relType"+relComCnt+"\" title=\"생산Capacity\" class=\"inputbox w100\" type=\"text\" /></td>"+
            "<td><input name=\"relNote"+relComCnt+"\" id=\"relNote"+relComCnt+"\" title=\"품목\" class=\"inputbox w100\" type=\"text\" /></td>"+
            "<td><input name=\"relNote"+relComCnt+"\" id=\"relNote"+relComCnt+"\" title=\"금액\" class=\"inputbox w100\" type=\"text\" /></td>"+
         "</tr>"
	);
	relComCnt++;
	iKEP.iFrameContentResize();
}

</script>
<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>Contact Report</h2>
	<div class="clear"></div>
</div>
<div class="blockButton"> 
                    <ul>
                        <li><a class="button createQualityCalim" href="#a"><span>등록</span></a></li>
                        <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do'/>?seqNum=${qualityClaimSell.seqNum}"><span>취소</a></li>
                        <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/qualityClaimSellList.do'/>"><span>목록</span></a></li>
                    </ul>
                </div>
<!--//tableTop End-->

<form id="QualityClaimSellForm" method="post" action="<c:url value='/support/partner/partnerQualityClaimSell/modifyQualityClaimSell.do'/>" enctype="multipart/form-data" >
	<input type="hidden" name="seqNum" value="${qualityClaimSell.seqNum}" />
	<input type="hidden" name="relationCompany" value=""/>
	<div class="subTitle_2 noline">
					<h3>Contact Report 수정</h3>
				</div>
				<!--//subTitle_1 End-->	
                
				<!--blockDetail Start-->
                <div class="blockDetail">
					<table summary="상담이력 상세조회">
						<caption></caption>
                        <colgroup>
                            <col width="15%"/>
                            <col width="32%"/>
                            <col width="10%"/>
                            <col width="15%"/>
                            <col width="10%"/>
                            <col width="23%"/>
                        </colgroup>
						<tbody>
							
							<tr>
                                <th scope="row">거래처명</th>
								<td><input name="partnerName" id="partnerName" title="고객명" value="${qualityClaimSell.partnerName}"  class="inputbox w90" type="text" /><a class="ic_search valign_middle searchPartnerId" href="javascript:goSync();"><span>검색</span></a></td>
								<th scope="row">대표자</th>
								<td colspan="3"><input name="ceoName" id="ceoName" title="대표자"  value="${qualityClaimSell.ceoName}"  class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
                                <th scope="row">업태</th>
								<td><input name="category" id="category" title="업태" value="${qualityClaimSell.category}"  class="inputbox w90" type="text" /></td>
								<th scope="row">사업자번호</th>
								<td colspan="3"><input name="businessNo" id="businessNo" title=""  value="${qualityClaimSell.businessNo}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">종목</th>
								<td><input name="sector" title="종목" id="sector" value="${qualityClaimSell.sector}"  class="inputbox w90" type="text" /></td>
								<th scope="row">법인번호</th>
								<td colspan="3"><input name="corporationNo" id="corporationNo" title=""  value="${qualityClaimSell.corporationNo}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">우편번호</th>
								<td><input name="zipNo" id="zipNo" title="" value="${qualityClaimSell.zipNo}"  class="inputbox w90" type="text" /></td>
								<th scope="row">담당자</th>
								<td colspan="3"><input name="keyMan" id="keyMan" title="담당자"  value="${qualityClaimSell.keyMan}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">주소</th>
								<td><input name="address" id="address" title="주소" value="${qualityClaimSell.address}"  class="inputbox w90" type="text" /></td>
								<th scope="row">연락처</th>
								<td colspan="3"><input name="contacts" id="contacts" title="연락처"  value="${qualityClaimSell.contacts}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">대표전화</th>
								<td><input name="mainPhone" id="mainPhone" title="대표전화" value="${qualityClaimSell.mainPhone}"  class="inputbox w90" type="text" /></td>
								<th scope="row">Fax</th>
								<td colspan="3"><input name="fax" id="fax" title="Fax" value="${qualityClaimSell.fax}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
                                <th scope="row">E-mail</th>
								<td><input name="email" id="email" title="" value="${qualityClaimSell.email}"  class="inputbox w90" type="text" /></td>
								<th scope="row">SAP ID</th>
								<td colspan="3"><input name="sapId" id="sapId" title="" value="${qualityClaimSell.sapId}"  class="inputbox w90" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">구분</th>
								<td>
									<select title="구분" name="purpose" id="purpose">
				                        <option value=""  >선택</option>
				                        <c:if test="${crRole1}">
				                        <option value="CR01" <c:if test="${qualityClaimSell.purpose eq 'CR01'}">selected="selected"</c:if>>주원료</option>
				                        </c:if>
				                        <c:if test="${crRole2}">
				                        <option value="CR02" <c:if test="${qualityClaimSell.purpose eq 'CR02'}">selected="selected"</c:if>>부원료</option>
				                        </c:if>
				                        <c:if test="${crRole3}">
				                        <option value="CR03" <c:if test="${qualityClaimSell.purpose eq 'CR03'}">selected="selected"</c:if>>기자재</option>
				                        </c:if>
				                        <c:if test="${crRole4}">
				                        <option value="CR04" <c:if test="${qualityClaimSell.purpose eq 'CR04'}">selected="selected"</c:if>>포장재</option>
				                        </c:if>
				                        <c:if test="${crRole5}">
				                        <option value="CR05" <c:if test="${qualityClaimSell.purpose eq 'CR05'}">selected="selected"</c:if>>펄프판매</option>
				                        </c:if>
				                        <c:if test="${crRole7}">
				                        <option value="CR07" <c:if test="${qualityClaimSell.purpose eq 'CR07'}">selected="selected"</c:if>>펠릿</option>
				                        </c:if>
				                        <c:if test="${crRole6}">
				                        <option value="CR06" <c:if test="${qualityClaimSell.purpose eq 'CR06'}">selected="selected"</c:if>>기타</option>
				                        </c:if>
				                    </select> 
								</td>
								<th scope="row">품목</th>
								<td colspan="3"><input name="items" title="" value="${qualityClaimSell.items}"  class="inputbox w90" type="text" /></td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                               
                <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button createQualityCalim" href="#a"><span>등록</span></a></li>
                        <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do'/>?seqNum=${qualityClaimSell.seqNum}"><span>취소</a></li>
                        <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/qualityClaimSellList.do'/>"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                
                <div class="blockBlank_10px"></div>		
                
                
</form>        