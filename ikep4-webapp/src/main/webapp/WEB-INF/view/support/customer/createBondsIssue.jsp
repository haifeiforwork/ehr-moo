<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
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
		
		

		$("a.createBondsIssue").click(function(){

			var tmpClaimGubunName=jQuery("#claimGubun option:selected").text();
			$jq('input[name=claimGubunName]').val(tmpClaimGubunName);
			$("#BondsIssueForm").trigger("submit"); 
			return false;
			
		});

	
		
		 
		new iKEP.Validator("#BondsIssueForm", {   
			rules  : {
				subject     : {required : true},
				counselDate : {required : true},
				counselor   : {required : true},
				customer    : {required : true},
				client      : {required : true},
				content		: {required : true}
			},
			messages : {
				subject     : {direction : "bottom",    required : "<ikep4j:message key="NotNull.customer.bondsIssue.title" />"},
				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.bondsIssue.counselDate" />"},
				counselor   : {direction : "top", required : "<ikep4j:message key="NotNull.customer.bondsIssue.counselor" />"}, 
				customer    : {direction : "bottom",  required: "<ikep4j:message key="NotNull.customer.bondsIssue.customer" />"},
				client      : {direction : "top", required : "<ikep4j:message key="NotNull.customer.bondsIssue.client" />"},
				content      : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.bondsIssue.content" />"},		
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
		    		//	btnTransfer_Onclick("BondsIssueForm");
		    		//}else{
		    			//alert("파일업로드 없음");
		    			writeSubmit(form);
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
		 
		var fileController = new iKEP.FileController("#BondsIssueForm", "#fileUploadArea", uploaderOptions);  */
		 
		//달력
		$("input[name=counselDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
   	    //dextFileUploadInit(""+20*1024*1024 ,"1", "all");
	    
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
	<h2>채권 관련 이슈</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->
<form id="BondsIssueForm" method="post" action="<c:url value='/support/customer/customerBondsIssue/createBondsIssue.do'/>" enctype="multipart/form-data" >
	<div class="subTitle_2 noline">
					<h3>채권 관련 이슈 등록</h3>
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
                                <th scope="row"><span class="colorPoint">*</span>고객명</th>
								<td colspan="5"><input name="customer" title="고객명" class="inputbox w100" type="text" /></td>
							</tr>
						    <tr>
						    	<th scope="row">구분</th>
								<td colspan="3">
									<select title="구분" name="claimGubun" id="claimGubun">
				                        <option value=""  >선택</option>
				                        <option value="1" <c:if test="${selectedClaimGubun eq '신용보험'}">selected="selected"</c:if>>신용보험</option>
				                        <option value="2" <c:if test="${selectedClaimGubun eq '담보'}">selected="selected"</c:if>>담보</option>
				                        <option value="3" <c:if test="${selectedClaimGubun eq '여신한도'}">selected="selected"</c:if>>여신한도</option>
				                        <option value="4" <c:if test="${selectedClaimGubun eq 'Sub거래선'}">selected="selected"</c:if>>Sub거래선</option>
				                        <option value="6" <c:if test="${selectedClaimGubun eq '배서인'}">selected="selected"</c:if>>배서인</option>
				                        <option value="5" <c:if test="${selectedClaimGubun eq '기타'}">selected="selected"</c:if>>기타</option>
				                    </select> 
				                    <input type="hidden" name="claimGubunName" value=""/>
								</td>
								<th scope="row">등급</th>
								<td>
									<select title="등급" name="factory" id="factory">
				                        <option value=""  >선택</option>
				                        <option value="S" <c:if test="${selectedFactory eq 'S'}">selected="selected"</c:if>>S</option>
				                        <option value="A" <c:if test="${selectedFactory eq 'A'}">selected="selected"</c:if>>A</option>
				                        <option value="B" <c:if test="${selectedFactory eq 'B'}">selected="selected"</c:if>>B</option>
				                        <option value="C" <c:if test="${selectedFactory eq 'C'}">selected="selected"</c:if>>C</option>
				                        <option value="D" <c:if test="${selectedFactory eq 'D'}">selected="selected"</c:if>>D</option>
				                        <option value="E" <c:if test="${selectedFactory eq 'E'}">selected="selected"</c:if>>E</option>
				                        <option value="F" <c:if test="${selectedFactory eq 'F'}">selected="selected"</c:if>>F</option>
				                    </select> 
								</td>
							</tr>
							<tr>
								<spring:bind path="bondsIssue.counselDate">
                                <th scope="row"><span class="colorPoint">*</span>등록일</th>
								<td>
                                	<input name="counselDate"   id="counselDate" title="등록일" class="inputbox w80 datepicker" type="text" value=""  /> 
                                	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>                   
                                </td>
                                </spring:bind>
								<th scope="row"><span class="colorPoint">*</span>등록자</th>
								<td colspan="3"><input name="counselor" title="등록자" class="inputbox w100" type="text"  /></td>
							</tr>
							<tr>
								<th scope="row"><span class="colorPoint">*</span>제목</th>
								<td colspan="5"><input name="subject" title="제목" class="inputbox w100" type="text" /></td>
							</tr>
                            <tr>
								<th scope="row"><span class="colorPoint">*</span>이슈내용</th>
								<td colspan="5"><textarea name="content" class="w100" title="이슈내용" cols="" rows="10"></textarea></td>
							</tr>
                            <!-- <tr>
								<th scope="row">조치사항 및<br />조치결과</th>
								<td colspan="5"><textarea name="actions" class="w100" title="조치사항 및 조치결과" cols="" rows="10"></textarea></td>
							</tr> -->
                        </tbody>
					</table>
				</div>
                <!--//blockDetail End-->
                               
                <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button createBondsIssue" href="#a"><span>등록</span></a></li>
                        <li><a class="button" href="<c:url value='/support/customer/customerBondsIssue/bondsIssueList.do'/>"><span>목록</span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
                
                <div class="blockBlank_10px"></div>		
                
                
</form>        