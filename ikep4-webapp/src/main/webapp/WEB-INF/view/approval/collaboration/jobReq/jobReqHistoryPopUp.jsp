<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preTitle"    	value="ui.approval.userauthmgnt.list.title" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	var dialogWindow = null;
	var fnCaller;
	var validator;
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
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});

			// 저장
			$("#saveButton").click(function() {
				var historyTxt = $('#historyTxt').val().replace(/^\s+/, "");
				if(historyTxt == '' || historyTxt == null || historyTxt.length == 0) {
					alert("내용을 입력해주세요");
					$('#historyTxt').focus();
					return;
				}
				if (confirm("등록하시겠습니까?")) {
					$jq.ajax({
						url : iKEP.getContextRoot() + "/approval/collaboration/jobReq/ajaxJobReqHistorySave.do",
						type : "post",
						data : $jq("#historyForm").serialize(),
						loadingElement : "#saveButton",
						success : function(data) {
							if(data.success == "Y") {
								dialogWindow.callback(data.jobReq);
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
	})(jQuery);
</script>
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2>이력 등록</h2> 
	</div>
	<!--//pageTitle End-->
	<form id="historyForm" name="historyForm" method="post" action="">
		<input type="hidden" name="jobReqMgntNo" value="${jobReqMgntNo}" />
		<input type="hidden" name="apprLv" value="${apprLv}" />
		<div class="blockDetail" style="border-top:0px solid #3b90d0;">
		<table summary="상담이력 상세조회" style="border:1px solid #e0e0e0; table-layout: auto;">
			<caption></caption>
			<colgroup>
				<col width="10%" />
				<col width="80%" />
				<col width="10%" />
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">내용</th>
					<td>
						<textarea name="historyTxt" id="historyTxt" class="w100" title="법무 의견" cols="" rows="5" ></textarea>
					</td>
<!-- 					<td rowspan="2"> -->
<!-- 					<a id="saveButton2" class="button2" href="#a" >이력등록</a> -->
<!-- 					<div> -->
<!-- 					<button>이력등록</button> -->
					
<!-- 					</div> -->
<!-- 					</td> -->
				</tr>
				<tr>
					<th scope="row">첨부파일</th>
					<td>
						<table style="width:100%;">
							<tr>
								<td style="border-color:#e5e5e5">
									<OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
										height="90" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
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
		
	<!--blockButton Start-->
		<div class="blockButton"  style="text-align:center;"> 
			<ul>
				<li><a id="saveButton" class="button" href="#a" ><span>이력등록</span></a></li>
				<li><a id="cancelButton" class="button" href="#a" ><span>닫기</span></a></li>
			</ul>
		</div>
	<!--blockButton End-->
	</form>	
<!--//blockListTable End-->	
