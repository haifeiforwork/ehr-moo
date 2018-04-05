<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<c:set var="preTitle"    	value="ui.approval.testreq.view.title" />
<c:set var="preButton2"  	value="ui.approval.collaboration.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">

	<!--
	var dialogWindow = null;
	var fnCaller;
	var validator;
	var ecmFlg = "N";
	(function($){
		var dblClick=false;
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$(".cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		$(document).ready(function(){
			
			// 저장
			$("#saveButton").click(function() {
				var historyTxt = $('#historyTxt').val().replace(/^\s+/, "");
				if(historyTxt == '' || historyTxt == null || historyTxt.length == 0) {
					alert("내용을 입력해주세요");
					$('#historyTxt').focus();
					return;
				}
				$("#historyYn").val("Y");
				$("#editForm").submit();
				return false; 
			});
			
			// [validation] ============================================= Start			
			var validOptions = {
				
			    submitHandler : function(form) {

					if (!confirm("저장시 유첨파일 정보가 변경되어 되돌릴수 없습니다. " +"<ikep4j:message pre='${preMessage}' key='save'/>")) {
						return;
					}
						
	    			if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		    			btnTransfer_Onclick("editForm");
		    		}else{
		    			writeSubmit(form);
		    		}
				}
			  	
			};
			validator = new iKEP.Validator("#editForm", validOptions);
			
			writeSubmit = function(targetForm){
				$jq.ajax({
					url : iKEP.getContextRoot() + "/approval/collaboration/jobReq/ajaxUdateHistoryFile.do",
					type : "post",
					data : $("#editForm").serialize(),
					loadingElement : "#saveButton",
					success : function(data, textStatus, jqXHR) {
						alert("<ikep4j:message pre='${preMessage2}' key='suceess' />");
						dialogWindow.callback( data);
						dialogWindow.close();
						return false;
					},
					error : function(jqXHR, textStatus, errorThrown) {
						
						var arrException = $jq.parseJSON(jqXHR.responseText).exception;
						alert(arrException[0].defaultMessage);
					}
				});
				
				return false;
			};
			
	    	dextFileUploadInit("${testRequest.fileSize}" ,"${testRequest.fileAttachOption}", "${testRequest.allowType}");
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
		    
		});
		
	})(jQuery);
	//-->
</script>
	<h1 class="none"><ikep4j:message pre='${preTitle}' key="fileItemId" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preTitle}" key="fileItemId" /></h2> 
	</div>
	<!--//pageTitle End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail" style="width: 98%;">
		<div>
			<form id="editForm" name="editForm" method="post" action="">
				<input type="hidden" name="ecmFileId" value="" /> 
				<input type="hidden" name="ecmFileName" value="" />
				<input type="hidden" name="ecmFilePath" value=""/>  
				<input type="hidden" name="ecmFileUrl1" value=""/> 
				<input type="hidden" name="ecmFileUrl2" value=""/>
				<input type="hidden" name="jobReqMgntNo" value="${jobReq.jobReqMgntNo}"/>
				<input type=hidden name="fileItemId" value=""/>
				<input type=hidden name="historyYn" id="historyYn" value="N"/>
				<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
					<caption></caption>
					<colgroup>
						<col width="15%;">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">내용</th>
							<td>
								<textarea name="historyTxt" id="historyTxt" class="w100" title="법무 의견" cols="" rows="3" ></textarea>
							</td>
						</tr>
						<tr>
							<th><ikep4j:message pre="${preTitle}" key="fileItemId" /></th>
							<td>
								<table style="width:100%;" id="normalFileTb">
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
			</form>
		</div>
		
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="margin-top: 10px;"> 
			<ul>
					<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton2}' key='save'/></span></a></li>
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				<c:if test="${permission.fileBtnActive eq true}">
				</c:if>
				<c:if test="${permission.fileBtnActive eq false}">
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//blockDetail End-->
