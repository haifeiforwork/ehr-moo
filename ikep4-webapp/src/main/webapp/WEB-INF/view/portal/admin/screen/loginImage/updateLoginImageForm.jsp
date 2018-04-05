<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preImage" value="message.portal.admin.screen.image" />
<c:set var="prefix" value="message.portal.admin.screen.loginImage.updateLoginImageForm"/>
<c:set var="preButton"  value="ui.support.fileupload.button" />

<script type="text/javascript" language="javascript">

(function($) {
	var uploader,
		uploadResult = null;
	
	$jq(document).ready(function() { 
		
		//left menu setting
		$("#loginImageLinkOfLeft").click();
		
		$(window).unload(function() {
			if(uploadResult != null)
				$.get("<c:url value='/portal/admin/screen/loginImage/cancelLoginImage.do'/>", {fileId:uploadResult.fileId});
		});
		
		$("#btnSave").click(function() {
			$("#divBody").ajaxLoadStart();
			$.get("<c:url value='/portal/admin/screen/loginImage/updateLoginImage.do'/>", {fileId:uploadResult.fileId})
				.success(function() {
					alert("<ikep4j:message pre='${prefix}' key='alert.saveMessage' />");
					$("#divBody").ajaxLoadComplete();
					
					orgLoginImage = uploadResult.fileId;
					uploadResult = null;
					
					$jq("#btnReset").show();
					$jq("#btnSave, #btnCancel").hide();
					viewReady();
				});
		});
		
		$("#btnCancel").click(function() {
			$("#divBody").ajaxLoadStart();
			$.get("<c:url value='/portal/admin/screen/loginImage/cancelLoginImage.do'/>", {fileId:uploadResult.fileId})
				.success(function() {
					var imgSrc = orgLoginImage ? "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + orgLoginImage : "<c:url value="/base/images/login/bg_img.jpg"/>";
					$("img.login_bg").attr("src", imgSrc);
					$("#divBody").ajaxLoadComplete();
					
					uploadResult = null;
					
					$jq("#btnSave, #btnCancel").hide();
					viewReady();
				});
		});
		
		$jq("#btnSave, #btnCancel").hide();
		<c:choose>
			<c:when test="${loginImageYn != 'Y'}">
				var orgLoginImage = null;
				$jq("#btnReset").hide();
				$("img.login_bg").attr("src", "<c:url value='/base/images/login/bg_img.jpg'/>");
			</c:when>
			<c:otherwise>
				var orgLoginImage = "${loginImageId}";
				$("img.login_bg").attr("src", "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + orgLoginImage);
			</c:otherwise>
		</c:choose>
		
		$jq("#btnReset").click(function() {
			if(confirm("<ikep4j:message pre='${prefix}' key='confirmReset' />")) {
				location.href = "<c:url value='/portal/admin/screen/loginImage/resetLoginImage.do'/>";
			}
		});
		
		function viewReady() {
			$("#inputFile").css("color", "#999")
				.val("<ikep4j:message pre='${preMessage}' key='file.select' />");
		}
		
		uploader = new plupload.Uploader({  
			runtimes : "flash",
			browse_button : "btnSelectFile",
			container : "divBody",
			max_file_size : "3mb",
			url : iKEP.getContextRoot()+iKEP.config.uploader.uploadUrl,
			flash_swf_url : iKEP.getContextRoot()+"/base/js/plupload/plupload.flash.swf",  
			filters : [  
				{title : "Image files", extensions : "jpg,gif,png"}
			],
			resize : {width : 750, height : 600, quality : 90},
			multi_selection : false,
			multipart : true,
			multipart_params : {isProfile:"N", userId:"admin", targetId:"picture"},
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
					
					$("#divBody").ajaxLoadStart();
					uploader.start();
				},
				Error : function(uploader, err) {
					alert("<ikep4j:message key='support.fileupload.message.upload.error'/>");
				},
				FileUploaded : function(uploader, file, xhr) {
		    		var json = $.parseJSON(xhr.response);
		    		if(json["status"] == "success") {
		    			if(uploadResult != null) {
		    				$.get("<c:url value='/portal/admin/screen/loginImage/cancelLoginImage.do'/>", {fileId:uploadResult.fileId});
		    			}
		        		uploadResult = json;
		    		} else {
		    			throw new Exception("upload error");
		    		}
				},
				UploadComplete : function() {
					if(uploadResult["status"] == "success") {
						$("img.login_bg").attr("src", "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + uploadResult.fileId);
						$jq("#btnSave, #btnCancel").show();
					}
					
					uploader.refresh();
					
					$("#divBody").ajaxLoadComplete();
				}
			}
		});
					
		setTimeout(function() {uploader.init();}, 10);
		
		$("#commentSizeInfo").html("<ikep4j:message pre='${preImage}' key='size' /> : "
			+ "<ikep4j:message pre='${preImage}' key='width' /> " + $("img.login_bg").css("width") + ", "
			+ "<ikep4j:message pre='${preImage}' key='height' /> " + $("img.login_bg").css("height")
		);
	});
	
})(jQuery);
</script>
<div id="divBody">
	<h1 class="none"><ikep4j:message pre='${prefix}' key='nonePageTitle' /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${prefix}' key='pageTitle' /> </h2>
		<!--  div id="pageLocation">
			<ul>
				<li class="liFirst"><ikep4j:message pre='${prefix}' key='home' /></li>
				<li><ikep4j:message pre='${prefix}' key='1depth' /></li>
				<li><ikep4j:message pre='${prefix}' key='2depth' /></li>
				<li class="liLast"><ikep4j:message pre='${prefix}' key='lastdepth' /></li>
			</ul>
		</div-->
	</div>
	<!--//pageTitle End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${prefix}' key='summary' />">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${prefix}' key='portalName' /></th>
					<td width="82%">${portalName}</td>
				</tr>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${prefix}' key='loginImage' /></th>
					<td width="82%">
						<input id="inputFile" type="text" readonly="readonly" value="file controller initiling" class="inputbox" style="width:210px;">
						<a id="btnSelectFile" class="button_s" href="#a" style="vertical-align:middle;"><span><ikep4j:message pre='${preButton}' key='select' /></span></a>
						<a id="btnSave" class="button_s" href="#a" style="vertical-align:middle;" title="<ikep4j:message pre='${prefix}' key='button.save' />"><span><ikep4j:message pre='${prefix}' key='button.save' /></span></a>
						<a id="btnCancel" class="button_s" href="#a" style="vertical-align:middle;" title="<ikep4j:message pre='${prefix}' key='button.cancel' />"><span><ikep4j:message pre='${prefix}' key='button.cancel' /></span></a>
						<p id="commentSizeInfo"></p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3 style="float:left;"><ikep4j:message pre='${prefix}' key='subTitle' /></h3>
		<div style="float:right;">
			<a id="btnReset" class="button_s" href="#a" style="vertical-align:middle;" title="<ikep4j:message pre='${prefix}' key='button.reset' />"><span><ikep4j:message pre='${prefix}' key='button.reset' /></span></a>
		</div>
	</div>
	<div class="clear"></div>
	<!--//subTitle_2 End-->
	
	<div class="login_preview">
		<div id="blockLogin">
			<table summary="">
				<caption></caption>
				<tbody>
					<tr>
						<td class="textCenter">
							<div class="loginBox">
								<div id="preViewer">
									<img class="login_bg" src="about:blank" title="<ikep4j:message pre="${prefix}" key="loginImage" />" alt="<ikep4j:message pre="${prefix}" key="loginImage" />"/>
								</div>
								<div class="loginInput">
									<fieldset>
										<legend><ikep4j:message pre='${prefix}' key='legend' /></legend>
					
										<div class="login_id"><input name="ID" type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='id' />" value="ID" size="16" /></div>
										<div class="login_pw"><input name="password" type="text" class="inputbox" title="<ikep4j:message pre='${prefix}' key='password' />" value="Password" size="16" /></div>
										<div class="login_btn"><input type="image" src="<c:url value='/base/images/login/btn_login.gif'/>" title="<ikep4j:message pre='${prefix}' key='login' />" /></div>
										<div class="login_info"><label><input name="" id="saveId" type="checkbox" value="" /> <ikep4j:message pre='${prefix}' key='saveId' /></label></div>
									</fieldset>
								</div>	
							</div>						
						</td>
					</tr>
				</tbody>
			</table>			
		</div>
	</div>	

</div>