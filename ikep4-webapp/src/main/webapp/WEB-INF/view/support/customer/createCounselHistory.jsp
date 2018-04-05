<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
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
		
		$("a.createCounselHistory").click(function(){			
			$("#registerForm").trigger("submit"); 
			return false;
		});
		
		new iKEP.Validator("#registerForm", {   
			rules  : {
				subject     : {required : true},
				counselDate : {required : true},
				customer    : {required : true},
				content		: {required : true}
			},
			messages : {
				subject     : {direction : "bottom",    required : "<ikep4j:message key="NotNull.customer.qualityClaim.title" />"},
				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.counselDate" />"}, 
				customer    : {direction : "bottom",  required: "<ikep4j:message key="NotNull.customer.qualityClaim.customer" />"},
				content      : {direction : "top", required : "<ikep4j:message key="NotNull.customer.qualityClaim.content" />"},		
			},   
			
		    submitHandler : function(form) { 
			
				
				
		    	if(confirm("저장하시겠습니까?")) {
		    		
		    		/*
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) {
							
							if( customerId == ''){
								alert("고객명은 검색한 결과만 사용 가능합니다.");
								$("#customer").focus();
							}else{	
							$("body").ajaxLoadStart("button");
							form.submit();
							}
						}
					});
					*/
					
					if(document.all["FileUploadManager"].Count>0){
		    			btnTransfer_Onclick("registerForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			writeSubmit(form);
		    		}
				}
		    }
		}); 
		
		
		$("a.searchCustomerId").click(function(){
			searchCustomer();
		});

	 	//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#customer').keypress( function(event) {
        	if(event.keyCode == '13'){
				searchCustomer();
        		return false;
        	}
		});
	 	
	
		/*
		 var uploaderOptions = {// 사용할 수 있는 옵션 항목
					<c:if test="${!empty(fileDataListJson)}">
						files  :  ${fileDataListJson}, // 파일 목록 
					</c:if>
				    // isUpdate : true,    // 등록 및 수정일때 true
			        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
			        maxTotalSize : 20*1024*1024,    // 최대 업로드 가능 용량(개별 파일 사이즈임)
			        allowFileType  : "all"    
		 };
		
		var fileController = new iKEP.FileController("#registerForm", "#fileUploadArea", uploaderOptions); 
		*/
		 
		//달력
		$("input[name=counselDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
	    dextFileUploadInit(""+20*1024*1024 ,"1", "all");
	    
		writeSubmit = function(targetForm){
			
			var customerId = $("#customerId").val();
			
			if( customerId == ''){
				alert("고객명은 검색한 결과만 사용 가능합니다.");
				$("#customer").focus();
			}else{	
				$("body").ajaxLoadStart("button");
				targetForm.submit();
			}
		};
		
	});
	

	
})(jQuery);

function searchCustomer(){

		var name = document.getElementById("customer").value;
		if(!name){
				alert("고객명을 입력해주세요.");
			}else{ 				
				 var url = "<c:url value='/support/customer/customerCommon/popupCustomer.do?customer='/>"+name;
				 iKEP.popupOpen(url,{width:700,height:490},'CustomerSearch'); 
				
			}

}




</script>



<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>고객별 상담내역</h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--subTitle_1 Start-->
<div class="subTitle_2 noline">
	<h3>상담이력 등록</h3>
</div>
<!--//subTitle_1 End-->



<form id="registerForm" name="registerForm" method="post" action="<c:url value='/support/customer/customerCounselHistory/createCounselHistory.do'/>" enctype="multipart/form-data" >
<input type="hidden" id="customerId" name="customerId" value="" /> 
<input type="hidden" name="customerName" value="" />
<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="상담이력 상세조회">
		<caption></caption>
		<colgroup>
			<col width="10%" />
			<col width="32%" />
			<col width="10%" />
			<col width="15%" />
			<col width="10%" />
			<col width="23%" />
		</colgroup>
		<tbody>
			<tr>
				<th scope="row"><span class="colorPoint">*</span>제목</th>
				<td colspan="5"><input name="subject" value="" title="제목" class="inputbox w100" type="text"  />
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span>상담일</th>
				<td><input name="counselDate" id="counselDate" value="" title="상담일" class="inputbox w80 datepicker" type="text" />
				<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/> </td>
				<th scope="row">상담자</th>
				<td><input name="counselor" value="" title="상담자" class="inputbox w90" type="text"/>
				</td>
				<th scope="row">부서</th>
				<td><input name="counselDept" value="" title="부서" class="inputbox w90" type="text" />
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span>고객명</th>
				<td><input id="customer" name="customer" value="" title="고객명" class="inputbox w80" type="text"  /> 
				<a class="ic_search valign_middle searchCustomerId" href="#a"><span>검색</span></a>
				</td>
				<th scope="row">피상담자</th>
				<td><input name="client" value="" title="피상담자" class="inputbox w90" type="text" />
				</td>
				<th scope="row">부서</th>
				<td><input name="clientDept" value="" title="부서" class="inputbox w90" type="text"  />
				</td>
			</tr>			
			<tr>
				<th scope="row"><span class="colorPoint">*</span>상담내용</th>
				<td colspan="5"><textarea name="content" class="w100" title="상담내용" cols="" rows="20"></textarea>
				</td>
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
</form>

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a class="button createCounselHistory" href="#a"><span>등록</span>
		</a>
		</li>
		<li><a class="button" href="<c:url value='/support/customer/customerCounselHistory/counselHistoryList.do'/>"><span>목록</span>
		</a>
		</li>
	
	</ul>
</div>
<!--//blockButton End-->