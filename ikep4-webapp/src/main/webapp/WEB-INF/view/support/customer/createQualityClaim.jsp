<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
var fileController = "";		//파일등록폼
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
		
		

		$("a.createQualityCalim").click(function(){

			
			$("#QualityClaimForm").trigger("submit"); 
			return false;
			
		});

	
		
		 
		new iKEP.Validator("#QualityClaimForm", {   
			rules  : {
				subject     : {required : true},
				counselDate : {required : true},
				counselor   : {required : true},
				customer    : {required : true},
				client      : {required : true},
				content		: {required : true}
			},
			messages : {
				subject     : {direction : "bottom",    required : "<ikep4j:message key="NotNull.customer.qualityClaim.title" />"},
				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.counselDate" />"},
				counselor   : {direction : "top", required : "<ikep4j:message key="NotNull.customer.qualityClaim.counselor" />"}, 
				customer    : {direction : "bottom",  required: "<ikep4j:message key="NotNull.customer.qualityClaim.customer" />"},
				client      : {direction : "top", required : "<ikep4j:message key="NotNull.customer.qualityClaim.client" />"},
				content      : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.content" />"},		
			},   
			
		    submitHandler : function(form) { 
			
				
		    	if(confirm("저장하시겠습니까?")) {
		    		
					/* fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) { 
								
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					}); */
					
					if(document.all["FileUploadManager"].Count>0){
		    			btnTransfer_Onclick("QualityClaimForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			writeSubmit(form);
		    		}
					
					
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
		 
		var fileController = new iKEP.FileController("#QualityClaimForm", "#fileUploadArea", uploaderOptions);  */
		 
		//달력
		$("input[name=counselDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
   	    dextFileUploadInit(""+20*1024*1024 ,"1", "all");
	    
		writeSubmit = function(targetForm){
			
		
				$("body").ajaxLoadStart("button");
				targetForm.submit();
			
		};
		
	});
	
	
	
})(jQuery);


</script>


<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>품질/클레임 상담내역</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->
<form id="QualityClaimForm" method="post" action="<c:url value='/support/customer/customerQualityClaim/createQualityClaim.do'/>" enctype="multipart/form-data" >
	<div class="subTitle_2 noline">
					<h3>품질/클레임 상담내역 등록</h3>
				</div>
				<!--//subTitle_1 End-->	
                
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
								<th scope="row">공장</th>
								<td>
									<select title="공장분류" name="factory" id="factory">
				                        <option value=""  >선택</option>
				                        <option value="1" <c:if test="${selectedFactory eq '진주'}">selected="selected"</c:if>>진주</option>
				                        <option value="2" <c:if test="${selectedFactory eq '울산'}">selected="selected"</c:if>>울산</option>
				                        <option value="3" <c:if test="${selectedFactory eq '대구'}">selected="selected"</c:if>>대구</option>
				                    </select> 
								</td>
								<th scope="row">구분</th>
								<td colspan="3">
									<select title="구분" name="claimGubun" id="claimGubun">
				                        <option value=""  >선택</option>
				                        <option value="1" <c:if test="${selectedClaimGubun eq 'A/S'}">selected="selected"</c:if>>A/S</option>
				                        <option value="2" <c:if test="${selectedClaimGubun eq 'B/S'}">selected="selected"</c:if>>B/S</option>
				                        <option value="3" <c:if test="${selectedClaimGubun eq '컴플레인'}">selected="selected"</c:if>>컴플레인</option>
				                    </select> 
								</td>
							</tr>
               		  		<tr>
								<th scope="row"><span class="colorPoint">*</span>제목</th>
								<td colspan="5"><input name="subject" title="제목" class="inputbox w100" type="text" /></td>
							</tr>
							<tr>
								<spring:bind path="qualityClaim.counselDate">
                                <th scope="row"><span class="colorPoint">*</span>상담일</th>
								<td>
                                	<input name="counselDate"   id="counselDate" title="상담일" class="inputbox w80 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>                   
                                </td>
                                </spring:bind>
								<th scope="row"><span class="colorPoint">*</span>상담자</th>
								<td><input name="counselor" title="상담자" class="inputbox w90" type="text"  /></td>
								<th scope="row">영업담당자</th>
								<td><input name="salesman" title="영업담당자" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
                                <th scope="row"><span class="colorPoint">*</span>고객명</th>
								<td><input name="customer" title="고객명" class="inputbox w80" type="text" /></td>
								<th scope="row"><span class="colorPoint">*</span>피상담자</th>
								<td><input name="client" title="피상담자" class="inputbox w90" type="text"  /></td>
								<th scope="row">연락처</th>
								<td><input name="clientPhone" title="연락처" class="inputbox w90" type="text" /></td>
							</tr>
                            <tr>
                                <th scope="row">가공처명</th>
								<td><input name="fabrication" title="가공처명" class="inputbox w80" type="text"/></td>
								<th scope="row">담당자</th>
								<td><input name="charge" title="담당자" class="inputbox w90" type="text"  /></td>
								<th scope="row">연락처</th>
								<td><input name="chargePhone" title="연락처" class="inputbox w90" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row">지종</th>
								<td colspan="5"><input name="jijong" title="지종" class="inputbox w100" type="text"  /></td>
							</tr>
						    <tr>
								<th scope="row">평량</th>
								<td colspan="5"><input name="weight" title="평량" class="inputbox w100" type="text"  /></td>
							</tr>
                            <tr>
								<th scope="row"><span class="colorPoint">*</span>상담내용</th>
								<td colspan="5"><textarea name="content" class="w100" title="상담내용" cols="" rows="10"></textarea></td>
							</tr>
                            <tr>
								<th scope="row">조치사항 및<br />조치결과</th>
								<td colspan="5"><textarea name="actions" class="w100" title="조치사항 및 조치결과" cols="" rows="10"></textarea></td>
							</tr>
                            <tr>
								<th scope="row">클레임<br />발생현황</th>
								<td colspan="5"><textarea name="claims" class="w100" title="클레임 발생현황" cols="" rows="10"></textarea></td>
							</tr>
                            <tr>
								<th scope="row">설비현황</th>
								<td colspan="5"><textarea name="facilityHistory" class="w100" title="설비현황" cols="" rows="10"></textarea></td>
							</tr>
                      
                            <tr>
								<th scope="row">주인쇄물</th>
								<td colspan="5"><input name="mainPrint" title="주인쇄물" class="inputbox w100" type="text"  /></td>
							</tr>
                            <tr>
								<th scope="row">선호회사</th>
								<td colspan="5"><input name="mainCompany" title="선호회사" class="inputbox w100" type="text"  /></td>
							</tr>
                            <tr>
								<th scope="row">주요거래처</th>
								<td colspan="5"><input name="mainConnection" title="주요거래처" class="inputbox w100" type="text" /></td>
							</tr>
                            <tr>
							    <th scope="row">첨부파일</th>
								<td colspan="5">
                                 <table style="width:100%;">
									<tr>
										<td colspan="2" style="border-color:#e5e5e5">
											<OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
												height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
												 <param name="ButtonVisible" value="FALSE" />
												 <param name="VisibleContextMenu" value="FALSE" />
												 <param name="StatusBarVisible" value="FALSE" />
												 <param name="VisibleListViewFrame" value="FALSE" />
											</OBJECT>
										</td>
									<tr>
							
									<tr>
							            <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
										<td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
										<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
										<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />	
										</td>
									</tr>
								</table>	
                                </td>                          
							</tr>
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                               
                <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button createQualityCalim" href="#a"><span>등록</span></a></li>
                        <li><a class="button" href="<c:url value='/support/customer/customerQualityClaim/qualityClaimList.do'/>"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                
                <div class="blockBlank_10px"></div>		
                
                
</form>        