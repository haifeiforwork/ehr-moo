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
var uploader;
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
		uploader = new plupload.Uploader({  
			runtimes : "flash",
			browse_button : "btnSelectFile",
			container : "divImage",
			max_file_size : "1mb",
			url : strUrl,
			flash_swf_url : iKEP.getContextRoot()+"/base/js/plupload/plupload.flash.swf",  
			filters : [  
				{title : "Image files", extensions : "jpg,gif,png"}
			],
			<%-- resize 없이 원본을 올려야 되나 IE외의 다른 브라우저에서 오류 발생으로 임시 처리함. --%>
			<%--
			<c:choose>
				<c:when test="${targetId == 'picture'}">resize : {width : 800, height : 800, quality : 90},</c:when>
				<c:when test="${targetId == 'pf_picture'}">resize : {width : 75, height : 75, quality : 90},</c:when>
			</c:choose>
			--%>
			resize : {width : 800, height : 800, quality : 90},
			multi_selection : false,
			multipart : true,
			multipart_params : {isProfile:"Y", userId:"${userId}", targetId:"${targetId}"},
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
					alert("<ikep4j:message key='support.fileupload.message.upload.error'/>");
	
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
						
						$("#profileImage").attr("src", iKEP.getContextRoot() + uploadResult.filePath);
						$("#btnSend").children().text("<ikep4j:message key='ui.support.profile.common.button.confirm'/>");
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
	<%-- big image
		<c:choose>
		
			<c:when test="${targetId == 'picture'}"> 
	--%>
				<div class="prPhoto1_Pop_Img">
					<img id="profileImage" src="about:blank" onerror="this.src='<c:url value="/base/images/common/photo_170x170.gif"/>'" width="100%"/>
				</div>
				<div class="prPhoto1_Pop_Desc">
					<div>
						<input id="inputFile" type="text" readonly="readonly" value="file controller initiling" class="inputbox" style="width:210px;">
						<a id="btnSelectFile" href="#a" class="button_s" style="vertical-align:middle;"><span><ikep4j:message pre='${preButton}' key='select' /></span></a>
					</div>
					<!--debate_point Start-->
					<div class="prPhoto1_Pop">			
						<div class="prPhoto1_Pop_box">
							<span><ikep4j:message pre='${preMessage}' key='file1.box' /></span>
						</div>
						<div class="prPhoto1_Pop_box_txt">※ <ikep4j:message pre='${preMessage}' key='file.msg' /></div>
					</div>
					<!--//debate_point End-->
					<div class="blockButton">
						<ul>
							<li>
								<a id="btnSend" href="#a" class="button"><span><ikep4j:message pre='${preButton}' key='upload' /></span></a>
							</li>
						</ul>
					</div>
				</div>
		<%--
			</c:when>
		--%>
		<%-- small image 
			<c:when test="${targetId == 'pf_picture'}">
				<div class="prPhoto2_Pop_Img">
					<img id="profileImage" src="about:blank" onerror="this.src='<c:url value="/base/images/common/photo_50x50.gif"/>'" width="100%"/>
				</div>
				<div class="prPhoto2_Pop_Desc">
					<div>
						<input id="inputFile" type="text" readonly="readonly" value="file controller initiling" class="inputbox" style="width:310px;">
						<a id="btnSelectFile" href="#a" class="button_s" style="vertical-align:middle;"><span><ikep4j:message pre='${preButton}' key='select' /></span></a>
					</div>
					<div class="prPhoto2_Pop">		
						<div class="prPhoto2_Pop_box" style="margin:7px;">
							<span><ikep4j:message pre='${preMessage}' key='file2.box' /></span>
						</div>
						<div class="prPhoto2_Pop_box_txt"><ikep4j:message pre='${preMessage}' key='file2.description' /><br/><ikep4j:message pre='${preMessage}' key='file.msg' /></div>
					</div>
					<div class="blockButton">
						<ul>
							<li>
								<a id="btnSend" href="#a" class="button"><span><ikep4j:message pre='${preButton}' key='upload' /></span></a>
							</li>
						</ul>
					</div>
				</div>
			</c:when>
		</c:choose>
		 --%>
		<div class="clear"></div>
	</div>
</div>