<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="preImage" value="message.portal.admin.screen.image" />
<c:set var="prefix" value="message.portal.admin.screen.logoImage.updateLogoImageForm"/>
<c:set var="preButton"  value="ui.support.fileupload.button" />

<script type="text/javascript" language="javascript">

(function($) {
	var uploader, uploadResult = null;
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		//left menu setting
		$jq("#logoImageLinkOfLeft").click();
		
		$(window).unload(function() {
			if(uploadResult != null)
				$.get("<c:url value='/portal/admin/screen/logoImage/cancelLogoImage.do'/>", {fileId:uploadResult.fileId});
		});
		
		$("#btnSave").click(function() {
			$("#divBody").ajaxLoadStart();
			$.get("<c:url value='/portal/admin/screen/logoImage/updateLogoImage.do'/>", {fileId:uploadResult.fileId})
				.success(function() {
					alert("<ikep4j:message pre='${prefix}' key='alert.saveMessage' />");
					$("#divBody").ajaxLoadComplete();
					
					orgLogoImage = uploadResult.fileId;
					uploadResult = null;
					
					if(top.changeLogoImage) {
						top.changeLogoImage(orgLogoImage);
					}
					
					$jq("#btnReset").show();
					$jq("#btnSave, #btnCancel").hide();
					viewReady();
				});
		});
		
		$("#btnCancel").click(function() {
			$("#divBody").ajaxLoadStart();
			$.get("<c:url value='/portal/admin/screen/logoImage/cancelLogoImage.do'/>", {fileId:uploadResult.fileId})
				.success(function() {
					
					var regurl=/eptest.moorim.co.kr/g;
					var defaultLogo="<c:url value='/base/images/common/logo.png'/>";
					if (regurl.test(location.href)) { 
						defaultLogo="<c:url value='/base/images/common/logo_test.gif'/>";
					}else{
						defaultLogo="<c:url value='/base/images/common/logo.png'/>";
					}
					
					var imgSrc = orgLogoImage ? "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + orgLogoImage : defaultLogo;
					$("img.logo_bg").attr("src", imgSrc);
					$("#divBody").ajaxLoadComplete();
					
					uploadResult = null;
					
					$jq("#btnSave, #btnCancel").hide();
					viewReady();
				});
		});
		
		$jq("#btnSave, #btnCancel").hide();
		
		<c:choose>
			<c:when test="${logoImageYn != 'Y'}">
				var orgLogoImage = null;
				$jq("#btnReset").hide();
				
				var regurl=/eptest.moorim.co.kr/g;
				
				if (regurl.test(location.href)) { 
					$("img.logo_bg").attr("src", "<c:url value='/base/images/common/logo_test.gif'/>");
				}else{
					$("img.logo_bg").attr("src", "<c:url value='/base/images/common/logo.png'/>");
				}
			</c:when>
			<c:otherwise>
				var orgLogoImage = "${logoImageId}";
				$("img.logo_bg").attr("src", "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + orgLogoImage);
			</c:otherwise>
		</c:choose>
		
		$jq("#btnReset").click(function() {
			if(confirm("<ikep4j:message pre='${prefix}' key='confirmReset' />")) {
				if(top.changeLogoImage) {
					top.changeLogoImage("");
				}
				
				location.href = "<c:url value='/portal/admin/screen/logoImage/resetLogoImage.do'/>";
			}
		});
		
		function viewReady() {
			$("#inputFile").css("color", "#999").val("<ikep4j:message pre='${preMessage}' key='file.select' />");
		};
		
		uploader = new plupload.Uploader({  
			runtimes : "flash",
			browse_button : "btnSelectFile",
			container : "divBody",
			max_file_size : "3mb",
			url : iKEP.getContextRoot()+iKEP.config.uploader.uploadUrl,
			flash_swf_url : iKEP.getContextRoot() + "/base/js/plupload/plupload.flash.swf",  
			filters : [  
				{title : "Image files", extensions : "jpg,gif,png"}
			],
			resize : {width : 108, height : 22, quality : 90},
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
		    				$.get("<c:url value='/portal/admin/screen/logoImage/cancelLogoImage.do'/>", {fileId:uploadResult.fileId});
		    			}
		        		uploadResult = json;
		    		} else {
		    			throw new Exception("upload error");
		    		}
				},
				UploadComplete : function() {
					if(uploadResult["status"] == "success") {
						$("img.logo_bg").attr("src", "<c:url value='/support/fileupload/downloadFile.do'/>?fileId=" + uploadResult.fileId);
						$jq("#btnSave, #btnCancel").show();
					}
					
					uploader.refresh();
					
					$("#divBody").ajaxLoadComplete();
				}
			}
		});
					
		setTimeout(function() {uploader.init();}, 10);
		
		$("#commentSizeInfo").html("<ikep4j:message pre='${preImage}' key='size' /> : "
			+ "<ikep4j:message pre='${preImage}' key='width' /> " + $("img.logo_bg").css("width") + ", "
			+ "<ikep4j:message pre='${preImage}' key='height' /> " + $("img.logo_bg").css("height")
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
					<th scope="row" width="18%"><ikep4j:message pre='${prefix}' key='logoImage' /></th>
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
	
		<!--blockHeader Start-->
		<div id="blockHeader">	
		
			<!--topInfo Start-->	
			<div id="topInfo">
				<h1 class="none"><ikep4j:message pre='${prefix}' key='headerTitle' /></h1>
				<div id="topLogo">
					<a href="#a" id="preViewer">
						<img class="logo_bg" src="about:blank" title="<ikep4j:message pre='${prefix}' key='img.ikepLogo' />" alt="<ikep4j:message pre='${prefix}' key='img.ikepLogo' />"/>
					</a>
				</div>
				<!--utilMenu Start-->	
				<div id="utilMenu">
					<ul>
						<li class="utilmenu_system"><a href="#a" id="systemLink" title="<ikep4j:message pre='${prefix}' key='system' />"><span><ikep4j:message pre='${prefix}' key='system' /></span></a></li>			
						<li class="utilmenu_sitemap"><a href="#a" title="<ikep4j:message pre='${prefix}' key='sitemap' />"><span><ikep4j:message pre='${prefix}' key='sitemap' /></span></a></li>
						<li class="utilmenu_help"><a href="#a" title="<ikep4j:message pre='${prefix}' key='help' />"><span><ikep4j:message pre='${prefix}' key='help' /></span></a></li>
						<li class="utilmenu_admin"><a href="#a" title="<ikep4j:message pre='${prefix}' key='admin' />"><span><ikep4j:message pre='${prefix}' key='admin' /></span></a></li>
					</ul>
				</div>
				<div class="headerSearch" style="top: 8px;">
					<div class="headerSearch_sel">
						<h2 class="none"><ikep4j:message pre="${prefix}" key="subTitle2" /></h2>
						<a class="sel_con" href="#a"><ikep4j:message pre="${prefix}" key="user" /></a>
						<div class="headerSearch_layer none">
							<ul>
								<li><a href="#a"><ikep4j:message pre="${prefix}" key="user" /></a></li>
							</ul>
						</div>
					</div>
					<input type="text" />
					<a class="sel_btn" href="#a"><span></span></a>
					<div class="clear"></div>
				</div>
				
				<!--//utilMenu End-->				
				<!--personInfo Start-->					
				<div id="personInfo">
					<span class="info_date"><ikep4j:timezone date="${today}" pattern="message.support.timezone.dateformat.type1" keyString="true" /></span>
					<span class="info_team">
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${user.teamName}
								<a href="#a">
									<span class="info_name">
										${user.userName}
									</span>
								</a>
								${user.jobTitleName}
							</c:when>
							<c:otherwise>
								${user.teamEnglishName}
								<a href="#a">
									<span class="info_name">
										${user.userEnglishName}
									</span>
								</a>
								${user.jobTitleEnglishName}
							</c:otherwise>
						</c:choose>
					</span> 
					<span class="info_btn"><a href="#a" title="<ikep4j:message pre='${prefix}' key='logout' />"><span><ikep4j:message pre='${prefix}' key='logout' /></span></a></span>
				</div>
				<!--//personInfo End-->						
				<div class="clear"></div>
			</div>
			<!--//topInfo End-->
						
			<!--topMenu Start-->
			<div id="topMenu" style="width: 712px;">
				<h1 class="none"><ikep4j:message pre='${prefix}' key='menu.noneTitle' /></h1>			
				<ul>
					<li><a class="iconMenu_01" href="#work" title="<ikep4j:message pre='${prefix}' key='menu.work' />"><span><ikep4j:message pre='${prefix}' key='menu.work' /></span></a></li>
					<li><a class="iconMenu_02" href="#messages" title="<ikep4j:message pre='${prefix}' key='menu.messages' />"><span><ikep4j:message pre='${prefix}' key='menu.messages' /></span></a></li>
					<li><a class="iconMenu_03" href="#collaboration" title="<ikep4j:message pre='${prefix}' key='menu.collaboration' />"><span><ikep4j:message pre='${prefix}' key='menu.collaboration' /></span></a></li>
					<li><a class="iconMenu_04" href="#knowledge" title="<ikep4j:message pre='${prefix}' key='menu.knowledge' />"><span><ikep4j:message pre='${prefix}' key='menu.knowledge' /></span></a></li>
					<li><a class="iconMenu_05" href="#people" title="<ikep4j:message pre='${prefix}' key='menu.people' />"><span><ikep4j:message pre='${prefix}' key='menu.people' /></span></a></li>
					<li><a class="iconMenu_06" href="#innovation" title="<ikep4j:message pre='${prefix}' key='menu.innovation' />"><span><ikep4j:message pre='${prefix}' key='menu.innovation' /></span></a></li>																				
				</ul>
			</div>
			<!--//topMenu End-->	
			
		</div>
		<!--blockHeader End-->
	</div>
</div>