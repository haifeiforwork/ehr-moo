<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<%@ include file="/base/common/fileUploadControll.jsp"%> 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.fileupload.header" /> 
<c:set var="preList"    value="ui.support.fileupload.list" />
<c:set var="preDetail"  value="ui.support.fileupload.detail" />
<c:set var="preButton"  value="ui.support.fileupload.button" /> 
<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">
<!--  
(function($) {
	var dialogWindow = null;
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		fnCaller = function(param, dialog){
			$("#profileImage").attr("src", param.imgSrc);
			dialogWindow = dialog;
		}
		
		var viewReady = function() {
			$("#inputFile").css("color", "#999")
				.val("<ikep4j:message pre='${preMessage}' key='file.select' />");
		};
		
		var uploadResult = null, isUpdate = false;
		var strUrl = iKEP.getContextRoot()+iKEP.config.uploader.uploadUrl;
		var uploader = new plupload.Uploader({  
			runtimes : "flash",
			browse_button : "btnSelectFile",
			container : "divImage",
			max_file_size : "1mb",
			url : strUrl,
			flash_swf_url : iKEP.getContextRoot()+"/base/js/plupload/plupload.flash.swf",  
			filters : [  
				{title : "Image files", extensions : "jpg,gif,png"}
			],
			resize : {width : 800, height : 800, quality : 90},
			multi_selection : false,
			multipart : true,
			multipart_params : {isProfile:"", userId:"${userId}", targetId:"${targetId}"},
			preinit : {
				Init : function() { viewReady(); }
			},
			init : {
				FilesAdded : function(uploader, files) {
					var file = files[files.length-1];	// 단일 파일만 선택되도록 함.
					
					$("#inputFile").css("color", "")
						.val(file.name);
					
					for(var i=uploader.files.length-1; i>=0; i--) {	// 선택된 파일만 남기고 모두 삭제
						if(uploader.files[i].id != file.id) uploader.removeFile(uploader.files[i]);
					}
					
					uploadResult = "ready";
					$("#btnSend").children().text("<ikep4j:message pre='${preButton}' key='upload' />");
				},
				Error : function(uploader, err) {  
					alert("<ikep4j:message key='support.fileupload.message.upload.failMaxSize'/>");
	
					uploadResult = null;
				},
				FileUploaded : function(uploader, file, xhr) {
		    		var json = $.parseJSON(xhr.response);
		    		if(json["status"] == "success") {
		        		uploadResult = json;
		    		} else {
		    			throw new Exception("upload error");
		    		}
				},
				UploadComplete : function() {
					
					if(uploadResult["status"] == "success") {
						isUpdate = true;
						
						$("#profileImage").attr("src", "<c:url value='/support/fileupload/downloadFile.do?fileId=' />" + uploadResult.fileId);
						$("#btnSend").children().text("<ikep4j:message pre='${preButton}' key='confirm'/>");
					}
					
					uploader.refresh();
					
					viewReady();
					$("#divImage").ajaxLoadComplete();
				}
			}
		});
					
		setTimeout(function() {uploader.init();}, 10);
		
		$('#btnSend').click(function(event) {
			if(uploadResult == "ready") {
				$("#divImage").ajaxLoadStart();
				
				uploader.start();
				event.preventDefault();
			} else {
				if(isUpdate) {
					dialogWindow.callback({
						status : uploadResult.status,
						fileId : uploadResult.fileId,
						fileName : uploadResult.filePath,
						message : uploadResult.message,
						gubun : "${targetId}"
					});
					dialogWindow.close();
				} else {
					alert("<ikep4j:message pre='${preMessage}' key='file.select' />");
				}
			}
		});
	});
})(jQuery);  
//-->	
</script>

<div>
	<div id="divImage">
		<div>
			<ul>
				<li><ikep4j:message pre='${preMessage}' key='file.meetingroom.guide1' /></li>
				<li><ikep4j:message pre='${preMessage}' key='file.meetingroom.guide2' /></li>
			</ul>				
		</div>
		<div>
			<input id="inputFile" type="text" readonly="readonly" value="file controller initiling" class="inputbox" style="width:210px;">
			<a id="btnSelectFile" href="#a" class="button_s" style="vertical-align:middle;"><span><ikep4j:message pre='${preButton}' key='select' /></span></a>
			<a id="btnSend" href="#a" class="button_s" style="vertical-align:middle;"><span><ikep4j:message pre='${preButton}' key='upload' /></span></a>
		</div>
		<!--debate_point Start-->
		<div class="prPhoto2_Pop" style="text-align:center;">			
			<img id="profileImage" src="" onerror="this.src='<c:url value="/base/images/common/noimage_170x170.gif"/>'" />
		</div>
	</div>
</div>