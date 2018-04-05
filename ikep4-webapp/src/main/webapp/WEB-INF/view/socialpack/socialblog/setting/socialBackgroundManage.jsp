<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preManageMenu" value="ui.socialpack.socialblog.management.menu" />
<c:set var="preManageDesign" value="ui.socialpack.socialblog.management.designsetting" />
<c:set var="preManageStat" value="ui.socialpack.socialblog.management.statistics" />

<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" />
<c:set var="preMessageSet"  value="message.socialpack.socialblog.management" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript">

(function($){

	$jq(document).ready(function() { 
		
		$jq("#btnSave").click(function() {
			if(confirm("<ikep4j:message pre='${preMessageSet}' key='afterUpdate.save' />")) {
				
				$bgimage = $jq('input[name=bgimage].radio:checked').val() ;
				
				if( $bgimage == 1){
					// 첨부파일을 등록 
					var fileName = $jq("input[name=file]").val();

					if(fileName == "") {
						alert('<ikep4j:message pre='${preMessageSet}' key='file.select' />');
						return;
					}
					
					if($jq("#imageYn").val() == '1') {
						//if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF)$/)) {
						if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
							alert('<ikep4j:message pre='${preMessageSet}' key='file.image' />');
							return;
						}
					}
				}
				
				$jq("#fileForm").submit();
				
			}
			
		});
		
	});
	
	// 카테고리 관리 메뉴 조회
	getCategoryManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialblogSettingHome.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getLayoutManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialLayoutManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getBackgroundManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialBackgroundManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getVisitorManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialVisitorManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
})(jQuery); 

</script>

	<!--popup_title Start-->
	<div id="popup_title">
		<h1><ikep4j:message pre='${preManage}' key='title' /></h1>
		<a href="javascript:iKEP.returnPopup();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->

	<!--popup_contents Start-->
	<div id="popup_contents">
	
		<!-- Modal window Start -->
		<!-- 사용시class="none"으로 설정 -->
		<div class="" id="layer_p" title="<ikep4j:message pre='${preManage}' key='title' />">
		
			<div class="blog_layout">
				
				<!--leftMenu Start-->
				<div class="floatLeft blog_left">
					<h1 class="none"><ikep4j:message pre='${preManageMenu}' key='leftmenu' /></h1>
					<div class="blog_leftmenu">	
						<ul>
							<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='title' /></a>
								<ul>
									<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='category.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='title' /></a>
								<ul>
									<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='layout.title' /></a></li>
									<li class="licurrent"><a onclick="getBackgroundManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='background.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='title' /></a>
								<ul>
									<li><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='visitor.title' /></a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<!--//leftMenu End-->
				
				<div class="floatRight" style="width:724px;">
					<div class="blog_layout_contents">
						
						<!--blog_backSelect Start-->
						<div class="blog_layout_Stitle">
							<ul class="floatLeft">
		
								<li><span><ikep4j:message pre='${preManageDesign}' key='background.menu' /></span></li>
							</ul>
							<div class="floatRight">
								<a id="btnSave" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='save' /></span></a>
							</div>
							<div class="clear"></div>
						</div>
						
						<div class="blog_backBg">
		
							<div class="blockLeft present_bg">
								<div class="backSelect_txt">
									<div><ikep4j:message pre='${preManageDesign}' key='background.selectedImg' /></div>
								</div>
								<div class="backSelect">
									<c:if test="${socialBlog.isPrivateImage == 0}">
										<c:choose>
											<c:when test="${socialBlog.imageUrl == null}">
												<img src="<c:url value='/base/images/common/noimage_blank.gif' />" alt="<ikep4j:message pre='${preManageDesign}'key='background.noImage' />"  width="140" height="140" />
											</c:when>
											<c:otherwise>
												<c:set var="socialBlog_imageUrl" value="${socialBlog.imageUrl}" />
												<img src="<c:url value='/base/images/socialblog/${fn:substring(socialBlog_imageUrl, 0, 11)}B${fn:substring(socialBlog.imageUrl, 11, 13)}.gif' />" alt="<ikep4j:message pre='${preManageDesign}' key='background.selectedImg' />" width="140" height="140" />
											</c:otherwise>
				       					</c:choose> 
									</c:if>
									<c:if test="${socialBlog.isPrivateImage == 1}">
										<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${socialBlog.imageFileId}' />" alt="" width="140" height="140" />
									</c:if>
		
								</div>
							</div>
		
							<form name="fileForm" id="fileForm" method="post" action="<c:url value="/socialpack/socialblog/setting/saveSocialBackgroundManage.do"/>" enctype="multipart/form-data">
							<div class="backSelectBox">	
								<ul>
									<li>			
										<img src="<c:url value='/base/images/common/noimage_blank.gif' />" alt="<ikep4j:message pre='${preManageDesign}'key='background.noImage' />" title="<ikep4j:message pre='${preManageDesign}'key='background.noImage' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='1' key='background.selectedImageIndex' />" name="bgimage" value="" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == '' }"> checked="checked" </c:if> /></p>
									</li>
									<li>
										<img src="<c:url value='/base/images/socialblog/skinDesign_01.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='1' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='1' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='1' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_01" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_01' }"> checked="checked" </c:if> /></p>
									</li>
									<li>
										<img src="<c:url value='/base/images/socialblog/skinDesign_02.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='2' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='2' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='2' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_02" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_02' }"> checked="checked" </c:if> /></p>
		
									</li>
									<li>
										<img src="<c:url value='/base/images/socialblog/skinDesign_03.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='3' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='3' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='3' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_03" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_03' }"> checked="checked" </c:if> /></p>
									</li>	
									<li>
										<img src="<c:url value='/base/images/socialblog/skinDesign_04.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='4' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='4' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='4' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_04" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_04' }"> checked="checked" </c:if> /></p>
									</li>
		
									<li>
										<img src="<c:url value='/base/images/socialblog/skinDesign_05.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='5' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='5' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='5' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_05" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_05' }"> checked="checked" </c:if> /></p>
									</li>
									<li>
										<img src="<c:url value='/base/images/socialblog/skinDesign_06.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='6' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='6' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='6' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_06" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_06' }" > checked="checked" </c:if> /></p>
									</li>	
									<li>
		
										<img src="<c:url value='/base/images/socialblog/skinDesign_07.gif' />" alt="<ikep4j:message pre='${preManageDesign}' arguments='7' key='background.imageIndex' />" title="<ikep4j:message pre='${preManageDesign}' arguments='7' key='background.imageIndex' />" />
										<p><input type="radio" class="radio" title="<ikep4j:message pre='${preManageDesign}' arguments='7' key='background.selectedImageIndex' />" name="bgimage" value="skinDesign_07" <c:if test="${socialBlog.isPrivateImage == 0 and socialBlog.imageUrl == 'skinDesign_07' }"> checked="checked" </c:if> /></p>
									</li>																		
								</ul>													
								<div class="clear"></div>
							</div>
							<!--//blog_backSelect End-->
							
							<div class="img_enrollment">
								<div class="img_title">
		
									<span>
										<input type="hidden" name="blogOwnerId" id="blogOwnerId" value="${socialBlog.ownerId}" />
							            <input type="hidden" name="editorAttachYn" id="editorAttachYn" value="0" />
							            <input type="hidden" name="imageYn" id="imageYn" value="1" />
										<input type="radio" class="radio" title="" <c:if test="${socialBlog.isPrivateImage == 1}"> checked="checked" </c:if> name="bgimage" value="1" />
										<strong><ikep4j:message pre='${preManageDesign}' key='background.updateImage' /></strong>
									</span> 
								</div>
								<div class="img_search">
									<input name="file" type="file" class="file w100" title="<ikep4j:message pre='${preManageDesign}' key='background.updateImage' />" />
								</div>
								<div class="img_txt">
									<p><ikep4j:message pre='${preManageDesign}' key='background.comment1' /></p>
									<p><ikep4j:message pre='${preManageDesign}' key='background.comment2' /></p>
									<p><ikep4j:message pre='${preManageDesign}' key='background.comment3' /></p>
								</div>
		
							</div>
							</form>
						</div>
						
					</div>
				</div>
				
			</div>
			
		</div>	
		<!-- //Modal window End -->
	</div>