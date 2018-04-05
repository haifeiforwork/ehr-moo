<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="prefix">message.portal.main.body</c:set>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portalPortletList" value="${portalMain.portletList}" />
<c:set var="portalPortletCategoryList" value="${portalMain.portletCategoryList}" />
<c:set var="pageLayoutList" value="${portalMain.pageLayoutList}" />
<c:set var="commonPageLayoutList" value="${portalMain.commonPageLayoutList}" />
<c:set var="activePortletList" value="${portalMain.activePortletList}" />
<c:set var="commonActivePortletList" value="${portalMain.commonActivePortletList}" />
<c:set var="themeList" value="${portalMain.themeList}" />
<c:set var="layoutList" value="${portalMain.layoutList}" />

<%-- <c:set var="portalMainLayout" value="top/left/right/bottom"/> --%>

<!--skipNavigation Start-->
<div id="skipNavigation">
	<p><a href="#blockMainTop"><ikep4j:message pre="${prefix}" key="skipNavigation.blockMainTop" /></a></p>	
	<p><a href="#portletWrap"><ikep4j:message pre="${prefix}" key="skipNavigation.portletWrap" /></a></p>
</div>
<!--//skipNavigation End-->

<!--wrapper Start-->
<div id="wrapper">

	<!--blockContainer Start-->
	<div id="blockContainer">
	
		<!--blockMain Start-->
		<div id="blockMain"
			<c:choose>
				<c:when test="${portalMainLayout == 'left'}"> class="mainLayoutLeft"</c:when>
				<c:when test="${portalMainLayout == 'right'}"> class="mainLayoutRight"</c:when>
			</c:choose>
		>
			
			<!--blockMainTop Start-->
			<c:if test="${portalMain.page.pageId == defPageId}">
			<div id="blockMainTop" <c:if test="${portalMainLayout == 'left' || portalMainLayout == 'right'}">style="width:${portalMain.page.commonPortletVerticalWidth}px;"</c:if> >
				<h1 class="none"><ikep4j:message pre="${prefix}" key="title1" /></h1>
				<c:if test="${!empty commonPageLayoutList}">
					<c:choose>
						<c:when test="${portalMainLayout == 'left' or portalMainLayout == 'right'}">
							<c:set var="commonPortletWidth" value="100"/>
							<c:set var="commonPortletMargin" value="0"/>
						</c:when>
						<c:otherwise>
							<c:set var="commonPortletMargin" value="${portalMain.commonMarginLeft}"/>
						</c:otherwise>
					</c:choose>
					<div>
						<c:forEach var="commonPageLayout" items="${commonPageLayoutList}" varStatus="i">
							<c:if test="${portalMainLayout != 'left' and portalMainLayout != 'right'}">
								<c:set var="commonPortletWidth" value="${commonPageLayout.width}"/>
							</c:if>
							<div class="commonPortlet" id="commonPortlet_${i.count}" style="width: ${commonPortletWidth}%; <c:if test='${i.count > 1}'>margin-left : ${commonPortletMargin}%;</c:if>"></div>
						</c:forEach>
						<div class="clear"></div>
					</div>
				</c:if>
			</div>
			</c:if>
			<!--//blockMainTop End-->
			
			<!--portletWrap Start-->
			<div id="portletWrap"
				<c:if test="${portalMainLayout == 'left'}">style="margin-left:${portalMain.page.commonPortletVerticalWidth}px;"</c:if>
				<c:if test="${portalMainLayout == 'right'}">style="margin-right:${portalMain.page.commonPortletVerticalWidth}px;"</c:if>
				>
				<h1 class="none"><ikep4j:message pre="${prefix}" key="title2" /></h1>
				
				<!--portletSetting Start-->
				<div id="portletSetting">
					<h2 class="none"><ikep4j:message pre="${prefix}" key="subTitle" /></h2>
					<ul class="po_setbtn">
						<c:if test="${portalMain.page.pageId == defPageId}">
							<li class="po_btn_theme"><a id="btnThemeControl" href="#a" title="<ikep4j:message pre="${prefix}" key="theme" />"><span><ikep4j:message pre="${prefix}" key="theme" /></span></a></li>
						</c:if>
						<li class="po_btn_portlet"><a id="btnPortletControl" href="#a" title="<ikep4j:message pre="${prefix}" key="img.setting" />"><span><ikep4j:message pre="${prefix}" key="portlet" /></span></a></li>
						<li class="mr5"><a id="btnPortletLayout" href="#a" title="<ikep4j:message pre="${prefix}" key="layoutSettings" />"><img src="<c:url value="/base/images/icon/ic_main_layer_layout.gif"/>" alt="<ikep4j:message pre="${prefix}" key="layoutSettings" />" /></a></li>
						<!-- li class="mr3"><a id="btnPortalMainLayout" href="#a" title="<ikep4j:message pre="${prefix}" key="portalMainLayoutSettings" />"><img src="<c:url value="/base/images/icon/ic_main_layout.gif"/>" alt="<ikep4j:message pre="${prefix}" key="portalMainLayoutSettings" />" /></a></li-->
					</ul>
					<div class="clear"></div>
					<!--portal layer start-->
					<div class="quicktxt_personalBox" id="portalLayoutSettingBox" style="position:absolute;top:30px; right:180px; margin:0; z-index:999; display: none;">
						<div class="quicktxt_menuBox">
							<div class="quicktxt_tit_personal dash"><span><ikep4j:message pre="${prefix}" key="portalMainLayoutSettings" /></span><a href="#a" class="btnLayerClose"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="" /></a></div>
							<div class="textCenter">
								<div class="mt10 mb5 inlineblock">
									<ul>
										<li class="btnTop<c:if test="${empty portalMainLayout || portalMainLayout == 'top'}"> on</c:if> floatLeft"><a href="#"></a></li>
										<li class="btnLeft<c:if test="${portalMainLayout == 'left'}"> on</c:if> floatLeft"><a href="#"></a></li>
										<li class="btnRight<c:if test="${portalMainLayout == 'right'}"> on</c:if> floatLeft"><a href="#"></a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<!--portal layer end-->
					<!--portlet layer start-->
					<div class="quicktxt_personalBox" id="portletLayoutSettingBox" style="position:absolute;top:30px; right:156px; margin:0; z-index:999; display: none;">
						<div class="quicktxt_menuBox">
							<div class="quicktxt_tit_personal dash"><span><ikep4j:message pre="${prefix}" key="layoutSettings" /></span><a href="#a" class="btnLayerClose"><img src="<c:url value="/base/images/icon/ic_close_layer.gif"/>" alt="" /></a></div>
							<div>
								<div class="textCenter mt15">
									<c:forEach var="layout" items="${layoutList}" varStatus="i">
										<c:if test="${layout.layoutNum > 0 && layout.layoutNum < 4}">
											<c:choose>
												<c:when test="${layout.layoutId == portalMain.userLayoutId}">
													<c:set var="layoutImgStr" value="on" />
												</c:when>
												<c:otherwise>
													<c:set var="layoutImgStr" value="" />
												</c:otherwise>
											</c:choose>
											<a href="#a" onclick="updateUserPageLayout('${layout.layoutId}');"><img src="<c:url value="/base/images/icon/ic_main_column${layout.layoutNum}${layoutImgStr}.png"/>" alt=""/></a>
										</c:if>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
					<!--portlet layer end-->
<!-- 					<div class="clear"></div> -->
					<div id="divPortletSetting" class="portletSetting_c relative none">
						<div>
							<div class="portletcat">
								<div id="divPortletGroup"></div>
							</div>
							<div class="portletsum">
								<div id="divPortletControl"></div>
							</div>
							
						</div>
						<div class="floatRight mt10">
							<div class="portletreset"><a id="btnPortletReset" href="#a"><img src="<c:url value="/base/images/theme/theme01/basic/btn_reset.gif"/>" title="<ikep4j:message pre="${prefix}" key="img.reset" />"  alt="<ikep4j:message pre="${prefix}" key="img.reset" />"/></a></div>
							<div class="portletclose"><a id="btnPortletclose" href="#a"><span>Close</span></a></div>
						</div>
						<div class="clear"></div>
						<div class="l_t_corner"></div><div class="l_b_corner"></div><div class="r_b_corner"></div>
					</div>
					<div id="divThemeSetting" class="portletSetting_c relative none">
						<div>
						<div class="portletcurrent">
							<span><ikep4j:message pre="${prefix}" key="selectTheme" /></span>
							<c:forEach var="theme" items="${themeList}" varStatus="i">
								<c:if test="${user.userTheme.themeId == theme.themeId}">
									<img src="<c:url value='/base/images/theme/${theme.cssPath}/basic/theme.jpg'/>" alt="<ikep4j:message pre="${prefix}" key="themePreViewImage" />" style="width: 120px; height: 90px;"/><p>${user.userTheme.themeName}</p>
								</c:if>
							</c:forEach>
						<!--
							<span><ikep4j:message pre="${prefix}" key="selectTheme" /></span>
							<img style="width:160px; height:120px;" src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${user.userTheme.previewImageId}&amp;thumbnailYn=Y" title="<ikep4j:message pre="${prefix}" key="themePreViewImage" />" alt="<ikep4j:message pre="${prefix}" key="themePreViewImage" />"/><p>${user.userTheme.themeName}</p>
						-->
						</div>
						<div class="portlettheme">
						</div>
						</div>
						<div class="clear"></div>
						<div class="l_t_corner"></div><div class="l_b_corner"></div><div class="r_b_corner"></div>
					</div>
				</div>	
				<!--//portletSetting End-->
				<!--portlet Start-->
				
				<div id="portlet">
					<div class="maximize"></div>
					<c:forEach var="pageLayout" items="${pageLayoutList}" varStatus="i">
						<div id="portlet_<c:out value='${i.count}'/>" style="float:left; width: <c:out value='${pageLayout.width}'/>%; <c:if test='${i.count > 1}'>margin-left : ${portalMain.marginLeft}%;</c:if>"></div>
					</c:forEach>
					<div class="clear"></div>
				</div>
				<!--//portlet End-->
			</div>
			<!--//portletWrap End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->
		
	</div>
	<!--//blockContainer End-->

</div>
<!--//wrapper End-->

<form id="userPageLayoutForm" method="post" action="<c:url value='/portal/main/updateUserPageLayout.do'/>" onsubmit="return false;">
	<input type="hidden" name="pageId" value="${portalMain.page.pageId}"/>
	<input type="hidden" name="layoutId"/>
</form>
<form id="portletResetForm" method="post" action="<c:url value='/portal/main/removePortletReset.do'/>" onsubmit="return false;">
	<input type="hidden" name="pageId" value="${portalMain.page.pageId}"/>
</form>
<form id="themeForm" method="post" target="_parent" action="<c:url value='/portal/main/updateUserTheme.do'/>" onsubmit="return false;">
	<input type="hidden" name="pageId" value="${portalMain.page.pageId}"/>
	<input type="hidden" name="themeId"/>
</form>

<script id="tmpTheme" type="text/x-jquery-tmpl">
	
							<ul>
								<c:forEach var="theme" items="${themeList}" varStatus="i">
								<li <c:if test="${user.userTheme.themeId == theme.themeId}">class="selected"</c:if>>
								<!-- 
									<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${theme.previewImageId}" title="<ikep4j:message pre="${prefix}" key="themePreViewImage" />">
										<img style="width:120px; height:90px;" src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${theme.previewImageId}&amp;thumbnailYn=Y" alt="<ikep4j:message pre="${prefix}" key="themePreViewImage" />"/>
									</a>
 								-->
 									<a class="image-gallery" href="<c:url value='/base/images/theme/${theme.cssPath}/basic/theme_large.jpg'/>" title="<ikep4j:message pre="${prefix}" key="themePreViewImage" />">
										<img src="<c:url value='/base/images/theme/${theme.cssPath}/basic/theme.jpg'/>" alt="<ikep4j:message pre="${prefix}" key="themePreViewImage" />" style="width: 120px; height: 90px;"/>
									</a>
									<p><a href="#a" onclick="javascript: updateUserTheme('${theme.themeId}');">${theme.themeName}</a></p>
									<c:if test='${user.userTheme.themeId == theme.themeId}'>
										<div class="btn_portlet_sel selected"><a href="#a" onclick="javascript: updateUserTheme('${theme.themeId}');"><span><ikep4j:message pre="${prefix}" key="selectOn" /></span></a></div>
									</c:if>
									<c:if test='${user.userTheme.themeId != theme.themeId}'>
										<div class="btn_portlet_sel"><a href="#a" onclick="javascript: updateUserTheme('${theme.themeId}');"><span><ikep4j:message pre="${prefix}" key="selectOff" /></span></a></div>
									</c:if>
								</li>
								</c:forEach>																																														
							</ul>
						
</script>

<iframe name="mailLoginFrame" id="mailLoginFrame" frameboarder="0" height="0" width="0"></iframe>
<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		
		// 개발, 운영 메일서버 URL 구분 
		var mailHost ="http://webmail.moorim.co.kr";
		var mailDomain ="moorim.co.kr";
		var mailD="4";
		var regurl=/eptest.moorim.co.kr/g;
		
		if (regurl.test(location.href)) { 
			mailHost="http://mailtest.moorim.co.kr";
			mailDomain="eptest.co.kr";
			mailD="2";
		}
		
		mailLoginFrame.location.href=mailHost+"/common/sso.do?d="+mailD+"&ssoParam=mailUid=${user.userId},domain="+mailDomain;

		
		$("#btnPortletControl, #btnThemeControl").click(function() {
			var $this = $(this);
			
			if($this.attr("id") == "btnPortletControl" && !$this.data("isSet")) {
				var portletManager = PortletManager.getInstance();
				portletManager.setControl();
				$this.data("isSet", true);
			}
			
			if($this.attr("id") == "btnThemeControl") {
				var isset = $this.data("isSetTheme");
				if(!isset) {
					$this.data("isSetTheme", true);
					$("#tmpTheme").tmpl().appendTo("div.portlettheme");
					iKEP.showGallery($("a.image-gallery"));
				}
			}
			
			$this.parent().toggleClass("selected")
				.siblings().removeClass("selected");
			switch($this.attr("id")) {
				case "btnThemeControl" :
					$("#divPortletSetting").hide();
					$("#divThemeSetting").toggle();
					break;
				case "btnPortletControl" :
					$("#divThemeSetting").hide();
					$("#divPortletSetting").toggle();
					break;
			}
		});
		
		$("#btnPortalMainLayout, #btnPortletLayout").click(function() {
			switch($(this).attr("id")) {
				case "btnPortalMainLayout" :
					$("#portalLayoutSettingBox").toggle();
					$("#portletLayoutSettingBox").hide();
					break;
				case "btnPortletLayout" :
					$("#portletLayoutSettingBox").toggle();
					$("#portalLayoutSettingBox").hide();
					break;
			}
		});
		
		$("a.btnLayerClose").click(function() {
			$(this).parents("div.quicktxt_personalBox:first").hide();
		});
		
		$("ul", "#portalLayoutSettingBox").click(function(event) {
			var el = event.target;
			if(el.tagName.toLowerCase() == "a") {
				var layout = "";
				$btn = $(el).parent();
				switch(true) {
					case $btn.hasClass("btnTop") :	layout = "top";
						break;
					case $btn.hasClass("btnLeft") :	layout = "left";
						break;
					case $btn.hasClass("btnRight") :	layout = "right";
						break;
					default : return false;
				}
				
				$.post("<c:url value="/portal/main/updatePortalMainLayout.do"/>", {portalMainLayout:layout})
					.success(function() {
						location.reload();
					})
					.error();
			}
		});
		
		$("#btnPortletReset").click(function() {
			if(confirm("<ikep4j:message pre="${prefix}" key="confirm.resetMessage1" />\n\n<ikep4j:message pre="${prefix}" key="confirm.resetMessage2" />")) {
				$("#portletResetForm")[0].submit();
			}
		});
		
		//iKEP.showGallery($("a.image-gallery"));
		
		<c:if test="${isAdmin && portalMainLayout == 'left'}">
			var portletCommonLayoutInfo = {
				splitWidth : 9,
				position : {max:600, min:150},
				layoutPersonalLeftMargin : 0
			};
			var $divCommon = $("#blockMainTop"),
				$divPersonal = $("#portletWrap");
			var $spliter = $('<div/>').appendTo($divCommon);
			var $splitHint = $('<div class="main_LayoutLeft_tootip"><p>${portalMain.page.commonPortletVerticalWidth}px</p></div>').appendTo($divCommon);

			$spliter.addClass("main_splitbarV")
				.css("left", (${portalMain.page.commonPortletVerticalWidth}-portletCommonLayoutInfo.splitWidth)+"px")
				.mouseover(function() { $splitHint.show().css("left", ($(this).position().left-($splitHint.width()+4))+"px"); })
				.mouseout(function() { $splitHint.hide(); })
				.draggable({
		        	axis:"x",
		        	//disabled : true,
		        	grid: [ 5,0 ],
		        	start : function() {
		        		$splitHint.show();
		        	},
		        	stop : function(event, ui) {
		        		var pos = ui.position.left + portletCommonLayoutInfo.splitWidth;
		        		if(pos < portletCommonLayoutInfo.position.min) pos = portletCommonLayoutInfo.position.min;
						if(pos > portletCommonLayoutInfo.position.max) pos = portletCommonLayoutInfo.position.max;
		
		        		ui.helper.css("left", (pos - portletCommonLayoutInfo.splitWidth) + "px");
		        		
		        		//$splitHint.hide();
 		        		$.post("<c:url value="/portal/admin/screen/page/updateCommonPortletWidth.do"/>", {pageId:"${portalMain.page.pageId}", width:pos})
 		        			.success(function() {})
 		        			.error(function() {});
		        	},
		        	drag : function(event, ui) {
		        		var pos = ui.position.left + portletCommonLayoutInfo.splitWidth;//ui.position.left + portletCommonLayoutInfo.splitWidth;
		        		if(pos >= portletCommonLayoutInfo.position.min && pos <= portletCommonLayoutInfo.position.max) {
		        			$divCommon.css("width", pos+"px");
		        			$divPersonal.css("margin-left", (portletCommonLayoutInfo.layoutPersonalLeftMargin + pos)+"px");
		        			
		        			$splitHint.children("p").text(pos+"px");
		        			$splitHint.css("left", (pos-($splitHint.width()+portletCommonLayoutInfo.splitWidth+4))+"px");
		        		} else {
		        			if(pos < portletCommonLayoutInfo.position.min) pos = portletCommonLayoutInfo.position.min;
							if(pos > portletCommonLayoutInfo.position.max) pos = portletCommonLayoutInfo.position.max;
							
							$divCommon.css("width", pos+"px");
							$divPersonal.css("margin-left", (portletCommonLayoutInfo.layoutPersonalLeftMargin + pos)+"px");
			        		
			        		//ui.helper.css("left", (pos - ui.helper.width()) + "px");
			        		
			        		return false;
		        		}
		        	}
		        });
		</c:if>
		
	});
	
	
	updateUserTheme = function (themeId) {
		if(confirm("<ikep4j:message pre="${prefix}" key="confirm.themeMessage" />")) {
			var themeForm = $("#themeForm"); 
			
			$("input[name=themeId]", themeForm).val(themeId);
			themeForm[0].submit();
		}
	};
	
	updateUserPageLayout = function (layoutId) {
		if(confirm("<ikep4j:message pre="${prefix}" key="confirm.layoutMessage" />")) {
			var userPageLayoutForm =$("#userPageLayoutForm"); 
			
			$("input[name=layoutId]", userPageLayoutForm).val(layoutId);
			userPageLayoutForm[0].submit();
		}
	};
	
	
	
	
	
	
})(jQuery);


var sortAreas = [
<c:forEach var="pageLayout" items="${pageLayoutList}" varStatus="i">
    "#portlet_<c:out value='${i.count}'/>"
    <c:if test="${!i.last}">,</c:if>
</c:forEach>
];

<c:set var="rowCount" value="0" />
<c:set var="colIndexChk" value="0"/>
var activePortlets = [
<c:forEach var="userPortlet" items="${activePortletList}" varStatus="i">
	<c:choose>
		<c:when test="${userPortlet.colIndex == colIndexChk}">
			<c:set var="rowCount" value="${rowCount + 1}" />
		</c:when>
		<c:otherwise>
			<c:set var="rowCount" value="0" />
			<c:set var="colIndexChk" value="${userPortlet.colIndex}"/>
		</c:otherwise>
	</c:choose>
	{id:"${userPortlet.portletId}",
	 portletConfigId:"${userPortlet.portletConfigId}",
	 layout:"#portlet_${userPortlet.colIndex}", 
	 seq:${rowCount},
	 viewMode: "${userPortlet.viewMode}",
	 headerMode: "${userPortlet.headerMode}"}<c:if test="${!i.last}">,</c:if>
</c:forEach>
];

var portletGroups = [ {code:"ALL", name:"ALL", desc:"all"}
<c:forEach var="portletCategory" items="${portalPortletCategoryList}" varStatus="i">
 	,{code:"<c:out value='${portletCategory.portletCategoryId}'/>", 
     name:"<c:out value='${portletCategory.portletCategoryName}'/>", 
     desc:"<c:out value='${portletCategory.description}'/>"}
</c:forEach>
];

var portlets = [
<c:forEach var="portlet" items="${portalPortletList}" varStatus="i">
 	{id:"${portlet.portletId}", 
 	 groupId:"${portlet.portletCategoryId}", 
 	 title:"${portlet.portletName}", 
 	 constructor:${portlet.linkType}, 
 	 area:"#portlet_${portlet.defaultColIndex}",
 	 multipleMode:"${portlet.multipleMode}",
 	 headerMode: "${portlet.headerMode}",
 	 publicOption: "${portlet.publicOption}",
 	 moveable: "${portlet.moveable}",
 	 options:{title:"${portlet.portletName}",
 			  <c:if test="${!empty portlet.normalViewUrl}">url:"<c:url value='${portlet.normalViewUrl}'/>",</c:if>
 			  <c:if test="${portlet.linkType eq 'PortletIFrameExt'}">iframeHeight:"<c:url value='${portlet.iframeHeight}'/>",</c:if>
 		 	  icons:{
 		 		<c:if test="${portlet.reloadMode == 1}">
 		 		  reload:true,
 		 		</c:if>
 		 		<c:if test="${portlet.publicOption == 0 && portlet.removeMode == 1}">
 		 		  close:true,
 		 		</c:if>
 		 		<c:choose>
	 				<c:when test="${portlet.moreMode == 1}">
	 					more:{url:"<c:url value='${portlet.moreViewUrl}'/>"},
	 				</c:when>
	 				<c:otherwise>
	 					more:false,
	 				</c:otherwise>
	 			</c:choose>
 		 		<c:choose>
	 				<c:when test="${portlet.portletType eq 'HTML'}">
		 				<c:choose>
		 					<c:when test="${portlet.maxMode == 1}">
	 							maximize:{url:"<c:url value='${portlet.maxViewUrl}'/>"},
	 						</c:when>
	 			 			<c:otherwise>
	 			 				maximize:false,
	 			 			</c:otherwise>
	 			 		</c:choose>
	 				</c:when>
	 				<c:otherwise>
	 					maximize:{url:""},
	 				</c:otherwise>
	 			</c:choose>
 		 		<c:choose>
	 				<c:when test="${portlet.publicOption == 0 && portlet.configMode == 1}">
	 					config:{url:"<c:url value='${portlet.configViewUrl}'/>"},
	 				</c:when>
	 				<c:otherwise>
	 					config:false,
	 				</c:otherwise>
	 			</c:choose>
	 			<c:choose>
	 				<c:when test="${portlet.helpMode == 1}">
	 					help:{url:"<c:url value='${portlet.helpViewUrl}'/>"}
	 				</c:when>
	 				<c:otherwise>
	 					help:false
	 				</c:otherwise>
	 			</c:choose>
	 			
 	 		  }
	  		}, 
     imagePath:"${portlet.previewImageId}",
     desc:"${portlet.portletName}"}
 	<c:if test="${!i.last}">,</c:if>
</c:forEach>
];

(function($jq) {
	$jq(document).ready(function() {
		<c:forEach var="pageLayout" items="${pageLayoutList}" varStatus="i">
			$jq.data($jq("#portlet_${i.count}")[0], "pageLayoutId", '${pageLayout.pageLayoutId}');
			$jq.data($jq("#portlet_${i.count}")[0], "colIndex", ${i.count});
		</c:forEach>
		
		<c:if test="${portalMain.page.pageId == defPageId}">
			<c:forEach var="commonPortlet" items="${commonActivePortletList}" varStatus="i">
				<c:choose>
					<c:when test="${commonPortlet.linkType == 'PortletSimple'}">
						//$jq('<div/>').attr("id", "commonContent${commonPortlet.portletConfigId}").load('<c:url value="${commonPortlet.normalViewUrl}"/>?portletConfigId=${commonPortlet.portletConfigId}')
					    //.error(function(event, request, settings) { alert("error"); }).appendTo("#commonPortlet_${commonPortlet.colIndex}");
						
						$jq('<div/>').attr("id", "commonContent${commonPortlet.portletConfigId}")
							.css("height", "100px")
							.appendTo("#commonPortlet_<c:out value='${commonPortlet.colIndex}'/>")
							.load('<c:url value="${commonPortlet.normalViewUrl}"/>?portletConfigId=${commonPortlet.portletConfigId}&portletId=${commonPortlet.portletId}', function() {
								$jq(this).css("height", "").ajaxLoadComplete();
							}).error(function(event, request, settings) { alert("error"); })
					    	.ajaxLoadStart("container");
						
						$jq.data($jq("#commonContent${commonPortlet.portletConfigId}")[0], "normalViewUrl", '<c:url value="${commonPortlet.normalViewUrl}"/>');
					</c:when>
					<c:when test="${commonPortlet.linkType == 'PortletIFrame'}">
						var div = $jq('<div/>').appendTo("#commonPortlet_<c:out value='${commonPortlet.colIndex}'/>");
						$jq.ajax({
							url : "<c:url value='/portal/main/portletCheckUrl.do'/>",
							data : {checkUrl:'<c:url value="${commonPortlet.normalViewUrl}"/>'},
							type : "post",
							dataType : "html",
							success : function(result) {
								if(result == '200') {
									var iFrameStr = '<iframe src="<c:url value="${commonPortlet.normalViewUrl}"/>" frameborder="0" style="width:100%; height:100%;"></iframe>';
									div.append(iFrameStr);
									var iFrame = $jq("iframe", div);
									
									var body = iFrame[0].contentWindow || iFrame[0].contentDocument;
									body.onload = function() {
										var contentHeight = ($jq(body).height()+body.scrollMaxY) + "px";
										iFrame.css({height:contentHeight});
										$(iFrame).parent().css({height:contentHeight});
									};
								} else {
									div.append("<ikep4j:message pre="${prefix}" key="noPage" />");
								}
							},
							error : function() {alert("error");}
						});
					</c:when>
					<c:when test="${commonPortlet.linkType == 'PortletIFrameExt'}">
						var div = $jq('<div/>').appendTo("#commonPortlet_<c:out value='${commonPortlet.colIndex}'/>");
						$jq.ajax({
							url : "<c:url value='/portal/main/portletCheckUrl.do'/>",
							data : {checkUrl:'<c:url value="${commonPortlet.normalViewUrl}"/>'},
							type : "post",
							dataType : "html",
							success : function(result) {
								if(result == '200') {
									var iFrameStr = '<iframe src="<c:url value="${commonPortlet.normalViewUrl}"/>" frameborder="0" style="width:100%; height:100%;"></iframe>';
									div.append(iFrameStr);
									var iFrame = $jq("iframe", div);
									
									var contentHeight = "${commonPortlet.iframeHeight}px";
									iFrame.css({height:contentHeight});
									iFrame.parent().css({height:contentHeight});
								} else {
									div.append("<ikep4j:message pre="${prefix}" key="noPage" />");
								}
							},
							error : function() {alert("error");}
						});
					</c:when>
					<c:when test="${commonPortlet.linkType == 'PortletJSON'}">
						$jq.get('<c:url value="${commonPortlet.normalViewUrl}"/>')
				       .success(function(data) { 
				    	   var html = '<ul>';
							$jq.each(data, function(index, item) {
								html += '<li>' + (index+1) + '.' + item.title + '</li>';
							});
							html += '</ul>';
							
							$jq('<div/>').append(html).appendTo("#commonPortlet_<c:out value='${commonPortlet.colIndex}'/>");
				    	}) 
				       .error(function(event, request, settings) { alert("error"); });
					</c:when>
				</c:choose>
			</c:forEach>
		</c:if>
		
		commonContentReload = function (portletConfigId) {
			var normalViewUrl = $jq.data($jq("#commonContent"+portletConfigId)[0], "normalViewUrl");
			
			$jq('#commonContent'+portletConfigId).load(normalViewUrl + '?portletConfigId='+portletConfigId)
		    .error(function(event, request, settings) { alert("error"); });
		}
		
		new PortletManager({
			sortAreas : sortAreas,
			portlets : portlets,
			portletGroups : portletGroups,
			activePortlets : activePortlets,
			systemCode : '${portalMain.page.systemCode}'
		});
		
		
	});
})(jQuery);


//-->
</script>
