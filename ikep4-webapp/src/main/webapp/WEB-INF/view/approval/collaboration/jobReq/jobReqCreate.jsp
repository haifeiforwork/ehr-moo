<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<style>
 .container{width:950px;}
 .topTable table{
 	border: 1px solid #c5c5c5;
 	border-collapse:collapse;
 	align: center;
 }
 .topTable table th, td {
    border: 1px solid #c5c5c5; 
    border-collapse:collapse;
    text-align: center;
    border-top:1px solid #e0e0e0;
    height:30px;
  }
  .blockDetail table th, td {
  	text-align:center;
  }
</style>

<script type="text/javascript">
var fileController = "";		//파일등록폼
var cachedTdObj;
(function($) {

	
	$(document).ready( function() {
		<c:if test="${not empty fileDataListJson}">
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
		        
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions);
	    </c:if>
	    
		setUser = function(data) {
			var managerName = "";
			var userId = "";
			
			$jq.each(data, function() {
			managerName = $jq.trim(this.userName);
			userId = $jq.trim(this.empNo);	
				
// 				jobTitleName = $jq.trim(this.jobTitleName);
// 				teamName = $jq.trim(this.teamName);
// 				userId = $jq.trim(this.empNo);	
				
// 				if(jobTitleName.length > 0) {
					
// 					managerName += " " + jobTitleName;
// 				}
				
// 				if(teamName.length > 0) {
					
// 					managerName += " " + teamName;
// 				}
				
// 				$jq("#searchUserName").val(managerName);
// 				$jq("#searchUserId").val(userId);
				cachedTdObj.find('.searchUserName').val(managerName);
				cachedTdObj.find('.searchUserId').val(userId);
			});
		};
		
		// [검색-직원선택] ================== Start
		$jq('.addrSearchBtn').click( function() {
			cachedTdObj = $(this).parent("td");
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			cachedTdObj.find('.searchUserName').trigger("keypress");
		});
		
		$jq('.searchUserName').keypress( function(event) {
			cachedTdObj = $(this).parent("td");
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser2(event, "N", setUser);			
		});
		
		$(".goSave").click(function(){
			var jobReqTitle = $('#jobReqTitle').val();
			
			if(jobReqTitle == '' || jobReqTitle == null || jobReqTitle.length == 0) {
				alert("제목을 입력해주세요");
				$('#jobReqTitle').focus();
				return;
			}
			
			var jobRelatedCd = '';
			$('input:checkbox[name="jobRelated"]').each(function() {
				if(this.checked){//checked 처리된 항목의 값
					jobRelatedCd += '1';
				} else {
					jobRelatedCd += '0';
				}
			});
			
			$('#jobRelatedCd').val(jobRelatedCd); //업무관련성 코드값 세팅
			
			if(confirm("저장하시겠습니까?")) {
				$jq.ajax({
					url : '<c:url value='/approval/collaboration/jobReq/ajaxJobReqSave.do'/>',
					data : $jq("#jobReqCreateForm").serialize(),
					type : "post",
					success : function(result) {
						if(result.success == 'Y') {
							var id = result.jobReqMgntNo;
							alert("저장하였습니다.");
							location.href="<c:url value='/approval/collaboration/jobReq/jobReqDetailRe.do?jobReqMgntNo='/>"+id+"&apprLv=1&processStatus=10";
						} else {
							alert("저장중 오류가 발생하였습니다");
							return;
						}
					}
				});
			}
		});
		
		
		new iKEP.Validator("#registerForm", {   
			rules  : {
// 				subject     : {required : true}
// 				counselDate : {required : true},
// 				customer    : {required : true}
// 				content		: {required : true}
			},
			messages : {
// 				subject     : {direction : "bottom",    required : "<ikep4j:message key="NotNull.customer.qualityClaim.title" />"}
// 				counselDate : {direction : "bottom", required : "<ikep4j:message key="NotNull.customer.qualityClaim.counselDate" />"}, 
// 				customer    : {direction : "bottom",  required: "<ikep4j:message key="NotNull.customer.qualityClaim.customer" />"},
// 				content      : {direction : "top", required : "<ikep4j:message key="NotNull.customer.qualityClaim.content" />"},		
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
	// 파일팝업 ActiveX
	openPopActiveX = function() {
		var jobReqMgntNo = $("input[name='jobReqMgntNo']").val();
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/jobReq/editFilePopViewActiveX.do?jobReqMgntNo='/>" + jobReqMgntNo +"&fileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "액티브X용 파일업로드",
			url: targetUrl,
			modal: true,
			width: 700,
			height: 370,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas; 
				// 파일 저장이 완료된후 유첨자료 화면에 뿌림
				$("input[name='fileItemId']").val( datas.fileItemId);
				$("#fileDownload").empty();
				var uploaderOptions = {
						   files : datas.fileDataListJson, 
						   isUpdate : false 
						}; 
						        
			    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
			}
		});
	}
	
	// 파일팝업 Ecm
	openPopEcm = function() {
		var jobReqMgntNo = $("input[name='jobReqMgntNo']").val();
		var fileItemId = $("input[name='fileItemId']").val();
		var targetUrl = "<c:url value='/approval/collaboration/jobReq/editFilePopViewEcm.do?jobReqMgntNo='/>" + jobReqMgntNo +"&fileItemId=" + fileItemId;
		
		iKEP.showDialog({
			title: "ECM용 파일업로드",
			url: targetUrl,
			modal: true,
			width: 800,
			height: 370,
			callback : function(_p) {
				var jsonDatas = $jq.parseJSON(_p);
				var datas =jsonDatas.datas; 
				// 파일 저장이 완료된후 유첨자료 화면에 뿌림
				$("input[name='fileItemId']").val( datas.fileItemId);
				$("#fileDownload").empty();
				var uploaderOptions = {
						   files : datas.fileDataListJson, 
						   isUpdate : false 
						}; 
						        
			    new iKEP.FileController(null, "#fileDownload", uploaderOptions);
			}
		});
	}

})(jQuery);

function searchCustomer(){

	var name = document.getElementById("customer").value;
	if(!name){
		alert("고객명을 입력해주세요.");
	}else{ 				
// 		 var url = "<c:url value='/support/customer/customerCommon/popupCustomer.do?customer='/>"+name;
// 		 iKEP.popupOpen(url,{width:700,height:490},'CustomerSearch'); 
	}

}

</script>

<h1 class="none">컨텐츠영역</h1>
<div class="container">
<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>업무 의뢰 요청서 등록</h2>
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<!--subTitle_1 Start-->
	
	<!--//subTitle_1 End-->
	
	
	<!-- <div><p></p></div> -->
	<form id="jobReqCreateForm" method="post" action="<c:url value='/approval/collaboration/jobReq/ajaxJobReqSave.do'/>" enctype="multipart/form-data" >
		<input type="hidden" name="ecmFileId" value="" /> 
		<input type="hidden" name="ecmFileName" value="" />
		<input type="hidden" name="ecmFilePath" value=""/>  
		<input type="hidden" name="ecmFileUrl1" value=""/> 
		<input type="hidden" name="ecmFileUrl2" value=""/>
		<div class="blockButton" style="margin-top: 35px;">
				<ul>
					<li>
						<a class="button goSave" id="goSave" href="#a"><span>저장</span></a>
					</li>
				<c:if test="!${ecmRoll}" >
					<li>
						<a class="button" href="javascript:;" onclick="openPopActiveX();" ><span>유첨자료</span></a>
					</li>	 
				</c:if>
				<c:if test="${ecmRoll}" >
					<li>
						<a class="button" href="javascript:;" onclick="openPopEcm();" ><span>유첨자료</span></a>
					</li>
				</c:if>	 
					<li>
						<a class="button goList" id="goList" href="<c:url value="/approval/collaboration/jobReq/jobReqList.do"/>" ><span>목록</span></a>
					</li>
				</ul>
			</div>
		<div width="100%"  class="topTable">
			<div  style="float: left; width: 30%;">
				<table width="100%">
					<caption></caption>
					<colgroup>
						<col width="10%" />
						<col width="30%" />
						<col width="30%" />
						<col width="30%" />
					</colgroup>
					<tbody>
						<tr>
							<td rowspan="3">기<br />안<br />부<br />서</td>
							<td>기안</td>
							<td>검토</td>
							<td>승인</td>
						</tr>
						<tr>
							<td style="height:50px;">
								${user.userName}
							</td>
							<td>
			                    	<input name="reqReviewEmpNo" class="searchUserId" type="hidden" value="" />
			                    	<input name="reqReviewEmpName" class="searchUserName" type="text" value="" size="7" style="border:1px solid #CCC;"  />
		                    	<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a" style="display:none">
									<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
								</a>
		                    </td>
							<td>
		                    	<input name="reqApprEmpNo" id="reqApprEmpNo"  class="searchUserId" type="hidden" value="${lederInfo.empNo}"  />
		                    	<input name="reqApprEmpName" id="reqApprEmpName" class="searchUserName" type="text" value="${lederInfo.userName}" size="7" style="border:1px solid #CCC;"  />
		                    	<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a" style="display:none">
									<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
								</a>
							</td>
						</tr>
						<tr>
							<td><span class="colorPoint">작성</span></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div  style="float: left; width: 40%; visibility: hidden;" >
				<table width="100%" style="border=0px;">
					
				</table>
			</div>
			<div  style="float: left; width: 30%;" >
				<table width="100%">
					<caption></caption>
					<colgroup>
						<col width="10%" />
						<col width="30%" />
						<col width="30%" />
						<col width="30%" />
					</colgroup>
					<tbody>
						<tr>
							<td rowspan="3">주<br />관<br />부<br />서</td>
							<td>기안</td>
							<td>검토</td>
							<td>승인</td>
						</tr>
						<tr>
							<td style="height:50px;"></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td></td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--blockDetail Start-->
		<div class="blockDetail" style="border-top:0px solid #3b90d0; top:30px;">
			<table summary="상담이력 상세조회" style="border:1px solid #e0e0e0; table-layout: auto;">
				<caption></caption>
				<colgroup>
					<col width="15%" />
					<col width="18%" />
					<col width="15%" />
					<col width="18%" />
					<col width="15%" />
					<col width="18%" />
				</colgroup>
				<tbody>
					<tr>
						<th scope="row"><span class="colorPoint">*</span>제목</th>
						<td colspan="5" style=""><input name="jobReqTitle" id="jobReqTitle" value="" title="제목" class="inputbox w100" type="text"  />
						</td>
					</tr>
					<tr>
						<th scope="row"></span>의뢰자</th>
						<td style="text-align:left; padding-left:17px;">
							<input name="reqEmpNo" value="${user.empNo}" title="의뢰자ID" class="inputbox w90" type="hidden" readonly="readonly" />
							${user.userName}
						</td>
						<th scope="row">소속</th>
						<td style="text-align:left; padding-left:17px;">
							<input name="reqDeptId" value="${user.groupId}" title="소속ID" class="inputbox w90" type="hidden" readonly="readonly"/>
							${user.teamName}
						</td>
						<th scope="row">연락처</th>
						<td style="text-align:left; padding-left:17px;">
							<input name="reqContactNo" value="${user.officePhoneNo}" title="연락처" class="inputbox w90" type="hidden" readonly="readonly" />
							${user.officePhoneNo}
						</td>
					</tr>
					<tr>
						<th scope="row"></span>상대방</th>
						<td>
							<input name="partyName" value="" title="고계약당사자객명" class="inputbox w90" type="text"  /> 
						</td>
						<th scope="row" colspan="2">업무 검토 완료시한</th>
						<td colspan="2" style="text-align:left;">
							<input name="reviewEndTxt" id="reviewEndTxt" title="업무 검토 완료시한" class="inputbox w95 datepicker" type="text" value="" />
						</td>
					</tr>
					<tr>
						<th scope="row">사실관계</th>
						<td colspan="5">
							<textarea name="factRelated" class="w100" title="사실관계" cols="" rows="15"></textarea>
						</td>
					</tr>		
					<tr>
						<th scope="row">협조유형</th>
						<td colspan="5" style="text-align:left;">
						&nbsp;
						<input name="jobRelated" id="" class="checkbox" title="checkbox" type="checkbox" /> 소송관련
						<input name="jobRelated" id="" class="checkbox" title="checkbox" type="checkbox" /> 변호사 자문 요청
						<input name="jobRelated" id="" class="checkbox" title="checkbox" type="checkbox" /> 관계 법령과 판례 조사
						<input name="jobRelated" id="" class="checkbox" title="checkbox" type="checkbox" /> 기타 법무적 분쟁
						<input name="jobRelatedCd" id="jobRelatedCd" class="checkbox" title="checkbox" type="hidden" value="" />
					</td>
					</tr>		
					<tr>
						<th scope="row">중점검토사항</th>
						<td colspan="5">
							<textarea name="keyReviewFocus" class="w100" title="권리에 관한 사항" cols="" rows="7" ></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">법무 의견</th>
						<td colspan="5">
							<textarea name="legalOpinion" class="w100" title="법무의견" cols="" rows="7" disabled></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">관련자료 설명</th>
						<td colspan="5">
							<textarea name="relatedDataExplain" class="w100" title="기타 의뢰부서 요청사항" cols="" rows="7" disabled></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">유첨자료</th>
						<!-- 유첨자료 -->
						<td colspan="5">
							<div id="fileDownload"></div>
							<input type="hidden" name="fileItemId"  value="${resultDetail.fileItemId}"/>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
		<!--blockButton Start-->
		<div class="blockButton" style="margin-top: 35px;">
			<ul>
				<li>
					<a class="button" href="javascript:;" onclick="openPopActiveX();" ><span>유첨자료</span></a>
				</li>
				<li>
					<a class="button goSave" id="goSave" href="#a"><span>저장</span></a>
				</li>
				<li>
					<a class="button goList" id="goList" href="<c:url value="/approval/collaboration/jobReq/jobReqList.do"/>"><span>목록</span></a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
	</form>
</div>