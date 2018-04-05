<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<style>
.blockDetail table th {
	text-align:center;
}
</style>

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preTitle"    	value="ui.approval.userauthmgnt.list.title" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	var dialogWindow = null;
	var fnCaller;
	var validator;
	var useActXEditor = "Y";
	
	(function($){
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		$(document).ready(function(){
			// editor 초기화
			initEditorSet();
			//태그프리 Mime 데이타 세팅
// 			var tweBody = document.twe.MimeValue();
// 			$jq("input[name='reviewOpinion']").val(123);
// 			$jq("input[name='reviewOpinion']").html();
			//달력
			$("input[name=reportDate]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
			
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
			// 목록으로
			$("#goList").click(function() {
				dialogWindow.callback("list");
				dialogWindow.close();
			});
			// 저장
			$("#saveButton").click(function() {
				var tweBody = document.twe.MimeValue();
				$jq("input[name='reviewOpinion']").val(tweBody);
				
				if (confirm("등록하시겠습니까?")) {
					$jq.ajax({
						url : iKEP.getContextRoot() + "/approval/collaboration/jobReq/ajaxJobReqResultSave.do",
						type : "post",
						data : $jq("#resultForm").serialize(),
						loadingElement : "#saveButton",
						success : function(data) {
							if(data.success == "Y") {
								alert( "등록 하였습니다.");
								dialogWindow.callback();
								dialogWindow.close();
							} else {
								alert( "등록 실패하였습니다.");
								dialogWindow.close();
							}
							
						}
					});
				}
			});
			
		});
		/* editor 초기화  */
		initEditorSet = function() {
			// ActiveX Editor 사용 여부가 Y인 경우
		    if(useActXEditor == "Y"){
				// 브라우저가 ie인 경우
				if ($.browser.msie) {
					// div 높이, 넓이 세팅
					var cssObj = {
				      'height' : '320px',
				      'width' : '100%'
				    };
					$('#editorDiv').css(cssObj);
					// hidden 필드 추가(reviewOpinion) - 수정모드
					iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#reviewOpinion", 1);
					// hidden 필드 추가(reviewOpinion)
// 					iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#reviewOpinion",0);
					// ie 세팅
					$('input[name="msie"]').val('1');
				}else{
					// ckeditor 초기화.
					$("#reviewOpinion").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
					// ie 이외 브라우저 값 세팅
					$('input[name="msie"]').val('0');
				}
		    }else{
		    	// ckeditor 초기화.
				$("#reviewOpinion").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
		    }
		};
	})(jQuery);
</script>
<script language="JScript" for="twe" event="OnControlInit()">
// 	var form = document.resultForm;
	//alert(iKEP.getContextRoot()+":"+iKEP.getWebAppPath());
	document.twe.HtmlValue = $jq("input[name=reviewOpinion]").val();//.replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
// 	$jq("input[name=title]").focus();
</script>
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2>검토 결과 등록</h2> 
	</div>
	<!--//pageTitle End-->
	<form id="resultForm" name="resultForm" method="post" action="">
		<input type="hidden" name="jobReqMgntNo"  value="${jobReq.jobReqMgntNo}" />
		<input type="hidden" name="reportDeptId"  value="${user.groupId}" />
		<input type="hidden" name="reportEmpNo"   value="${user.empNo}" />
		<input type="hidden" name="apprStsCd"     value="${jobReq.apprStsCd}" />
		<input type="hidden" name="apprReqDate"   value="<fmt:formatDate value="${jobReq.apprReqDate}" pattern="yyyy.MM.dd"/>" />
		<input type="hidden" name="apprDate"      value="<fmt:formatDate value="${jobReq.apprDate}" pattern="yyyy.MM.dd"/>" />
		<input name="msie"   type="hidden" value="0" />
		
		<div class="blockDetail">
			<table summary="계약서 초안 검토" style="border:1px solid #e0e0e0;">
				<caption></caption>
				<colgroup>
					<col width="10%" />
					<col width="25%" />
					<col width="10%" />
					<col width="22%" />
					<col width="10%" />
					<col width="22%" />
				</colgroup>
				<tbody>
					<tr>
						<th>일시</th>
						<td>
							<input name="reportDate" id="reportDate" title="업무 검토 완료시한" class="inputbox w50 datepicker" type="text" value="<fmt:formatDate value="${today}" pattern="yyyy.MM.dd"/>" onfocus="this.blur();" />
		           	    	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
						</td>
						<th>부서</th>
						<td>
							<input name="" id="" title="부서" class="inputbox w90 datepicker" type="text" value="${user.teamName}" style="border:none;" readonly onfocus="this.blur();" />
						</td>
						<th>성명</th>
						<td>
							<input name="" id="" title="성명" class="inputbox w90 datepicker" type="text" value="${user.userName}" style="border:none;" readonly onfocus="this.blur();" />
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="5">
							<input name="jobReqTitle" id="jobReqTitle" title="제목" class="inputbox w90 datepicker" type="text" value="${jobReq.jobReqTitle}" style="border:none;" readonly onfocus="this.blur();" />
						</td>
					</tr>
					<tr>
						<td colspan="6" class="ckeditor"> 
							<div id="editorDiv"">					
								<textarea id="reviewOpinion"
								name="reviewOpinion" 
								class="inputbox w100 fullEditor"
								cols="" 
								rows="5" 
								title=""></textarea>					
							</div> 				
						</td> 
					</tr>
				</tbody>
			</table>
			<div class="blockBlank_10px"></div>	
		</div>
		
		<div class="blockButton" style="margin-top: 5px;">
			<ul style="align:center;">
			<!-- 법무결제 작성중인경우 -->
					<li>
						<a class="button" id="saveButton" href="#a"><span>저장</span></a>
					</li>
					<li>
						<a class="button" id="cancelButton" href="#a"><span>이전</span></a>
					</li>
					<li>
						<a class="button" id="goList" href="#a" /> <span>목록</span></a>
					</li>
			</ul>
		</div>
	</form>	
<!--//blockListTable End-->	
