<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
var fileController = "";		//파일등록폼
var relComCnt = 1;
var chkRegInfo = "0";
(function($) {

	
	$(document).ready( function() {
		
	     function zeroPad(n) {
             return (n < 10 ? '0' : '') + n;
       }
    	
		
		 var date = new Date();
	     var year = date.getFullYear();
         var monthTmp = date.getMonth()+1;
         var month = zeroPad(monthTmp);        
         var dayTmp = date.getDate();
         var day = zeroPad(dayTmp);
         var nowDate = year+"."+month+"."+day;
		 $("#counselDate").val(nowDate);
		
		 $("#partnerName").keypress( function(event) {
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
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/support/partner/partnerQualityClaimSell/checkRegInfo.do",
				type : "post",
				data : $jq("#QualityClaimSellForm").serialize(),
				dataType : 'text',
				success : function(result) {
					$jq.each(result, function(){
						if(result == "success"){
							chkRegInfo = "1";
						}else{
							chkRegInfo = "2";
						}
						
					});
				}
			});
			
			$jq('input[name=relationCompany]').val(relationCompany);
			var tmpFacName=jQuery("#factory option:selected").text();
			$jq('input[name=factoryName]').val(tmpFacName);
			var tmpClaimGubunName=jQuery("#claimGubun option:selected").text();
			$jq('input[name=claimGubunName]').val(tmpClaimGubunName);
			$("#QualityClaimSellForm").trigger("submit");
		
			return false;
			
		});
		
		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readerList');
			$jq('option:selected',$rPermissionList).remove();

		});	
		
		$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트

			var items = [];
			var $sel = $jq("#QualityClaimSellForm").find("[name=readerList]");
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
						case "common" : tpl = "addrBookItemGroup"; break;
					}
					
					if(this.type=="group"){
						this.code="G:"+this.code;
					}else if(this.type=="common"){
						this.code="C:"+this.code;
					}else{
						this.id ="U:"+this.id;
					}
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);
		
				})
			};
			iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:0}});	
		});	 
		
		$jq.template("addrBookItemUser", "<option value='\${id}'>\${userName}/\${jobTitleName}/\${teamName}</option>");
		$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");
		

	
		
		 
		new iKEP.Validator("#QualityClaimSellForm", {   
			rules  : {
				/* subject     : {required : true},
				counselDate : {required : true},
				counselor   : {required : true},
				partner    : {required : true},
				client      : {required : true},
				content		: {required : true} */
			},
			messages : {
				/* subject     : {direction : "bottom",    required : "<ikep4j:message key="NotNull.partner.qualityClaim.title" />"},
				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.partner.qualityClaim.counselDate" />"},
				counselor   : {direction : "top", required : "<ikep4j:message key="NotNull.partner.qualityClaim.counselor" />"}, 
				partner    : {direction : "bottom",  required: "<ikep4j:message key="NotNull.partner.qualityClaim.partner" />"},
				client      : {direction : "top", required : "<ikep4j:message key="NotNull.partner.qualityClaim.client" />"},
				content      : {direction : "bottom", required : "<ikep4j:message key="NotNull.partner.qualityClaim.content" />"},		 */
			},   
			
		    submitHandler : function(form) { 
			
				
		    	if(confirm("저장하시겠습니까?")) {
		    		
					/* fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) { 
								
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					}); */
					
					//if(document.all["FileUploadManager"].Count>0){
		    		//	btnTransfer_Onclick("QualityClaimSellForm");
		    		//}else{
		    			//alert("파일업로드 없음");
						if(chkRegInfo == "1"){
							alert("이미 등록된 거래처입니다.");
							return;
						}else{
							writeSubmit(form);
						}
		    			
		    		//}
					
					
				}
		    }
		}); 
		
/* 		 var uploaderOptions = {// 사용할 수 있는 옵션 항목
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
		
   	    //dextFileUploadInit(""+20*1024*1024 ,"1", "all");
	    
		writeSubmit = function(targetForm){
			
			$jq("#readerList>option").attr("selected",true);	   
		
				$("body").ajaxLoadStart("button");
				targetForm.submit();
			
		};
		
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

</script>

<body></body>
<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>Contact Report</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->
<form id="QualityClaimSellForm" method="post" action="<c:url value='/support/partner/partnerQualityClaimSell/createQualityClaimSell.do'/>" enctype="multipart/form-data" >
	<input type="hidden" name="relationCompany" value=""/>
	<div class="subTitle_2 noline">
					<h3>Contact Report 등록</h3>
				</div>
				<!--//subTitle_1 End-->	
                
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
								<td><input name="address" id="address" title="주소" class="inputbox w90" type="text"  /></td>
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
								<td><input name="email" id="email" title="" class="inputbox w90" type="text"/></td>
								<th scope="row">SAP ID</th>
								<td colspan="3"><input name="sapId" id="sapId" title="" class="inputbox w90" type="text" readonly="readonly" /></td>
							</tr>
							<tr>
								<th scope="row">구분</th>
								<td>
									<select title="구분" name="purpose" id="purpose">
				                        <option value=""  >선택</option>
				                        <c:if test="${crRole1}">
				                        <option value="CR01" >주원료</option>
				                        </c:if>
				                        <c:if test="${crRole2}">
				                        <option value="CR02" >부원료</option>
				                        </c:if>
				                        <c:if test="${crRole3}">
				                        <option value="CR03" >기자재</option>
				                        </c:if>
				                        <c:if test="${crRole4}">
				                        <option value="CR04" >포장재</option>
				                        </c:if>
				                        <c:if test="${crRole5}">
				                        <option value="CR05" >펄프판매</option>
				                        </c:if>
				                        <c:if test="${crRole7}">
				                        <option value="CR07" >펠릿</option>
				                        </c:if>
				                        <c:if test="${crRole6}">
				                        <option value="CR06" >기타</option>
				                        </c:if>
				                    </select> 
								</td>
								<th scope="row">품목</th>
								<td colspan="3"><input name="items" title="" class="inputbox w90" type="text"  /></td>
							</tr>
							</tbody>
					</table>
					</div>
					<div class="blockBlank_50px"></div>	
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
								<spring:bind path="qualityClaimSell.counselDate">
                                <th scope="row">등록일</th>
								<td>
                                	<input name="counselDate"   id="counselDate" title="등록일" class="inputbox w80 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>                   
                                </td>
                                </spring:bind>
								<th scope="row">상담자</th>
								<td colspan="3"><input name="counselor" title="상담자" class="inputbox w90" type="text" value="${user.userName}" /></td>
							</tr>
							<tr>
                                <th scope="row">면담형태</th>
								<td>
                                	<select title="면담형태" name="counselType" id="counselType">
				                        <option value=""  >선택</option>
				                        <option value="1" >유선</option>
				                        <option value="2" >방문</option>
				                    </select> 
                                </td>
								<th scope="row">피상담자</th>
								<td colspan="3"><input name="requestor" title="피상담자" class="inputbox w90" type="text" value="" /></td>
							</tr>
							<tr>
								<th scope="row">제목</th>
								<td colspan="5"><input name="counselTitle" title="제목" class="inputbox w100" type="text" /></td>
							</tr>
							<tr>
								<th scope="row">상담내용</th>
								<td colspan="5"><textarea name="counselContent" class="w100" title="상담내용" cols="" rows="30"></textarea></td>
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                               
                <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button createQualityCalim" href="#a"><span>등록</span></a></li>
                        <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/qualityClaimSellList.do'/>"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                
                <div class="blockBlank_10px"></div>		
                
                
</form>        