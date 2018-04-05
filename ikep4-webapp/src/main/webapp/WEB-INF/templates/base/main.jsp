<%--
=====================================================
* 기능 설명 : 다이나믹 타일즈2 베이스 레이아웃 페이지
* 작성자 : 주길재
=====================================================
--%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--=============================-->
<!--JSTL & Custom Tag Libray Area-->
<!--=============================--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="destiny.link.slo.service.DestinySLO"%>
<%!
public void setSystemProperty( String key, String value) {
    System.setProperty( key, value);
}

public void setCookie( HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie( key, value);
    cookie.setPath( "/");
    cookie.setMaxAge( -1);
    cookie.setVersion( 0);
    cookie.setComment( "destiny slo test");
    cookie.setSecure( false);
    response.addCookie( cookie);
}
%>

<%

// ECM Server Address
//String sloServerAddress = "http://10.1.0.233:8080"; //개발
String sloServerAddress = "http://ecm.moorim.co.kr"; //운영
String sloAPIKey = "0VbXsZYobdOnciJmv4GQ3h16EvOjAoF0icK5sHMSvX4=";

// GW Login User Account
String userAccount = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId();

// GW Login User's GroupCode
String userGroupCode = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getGroupId(); // ex) E000;

// ECM Settings
setSystemProperty( "common.SloAddrKey", sloServerAddress);
setSystemProperty( "common.SloAPIKey", sloAPIKey);

setCookie( response, "ACCOUNT", userAccount);
setCookie( response, "GROUP_CODE", userGroupCode);
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%-- script src="<c:url value="/JavaScriptServlet"/>"></script --%>
<%--타이틀--%>
<c:set var="prefix" value="ui.templates.base.main"/>
<c:set var="preDetail"  value="ui.support.favorite.detail" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="title"><tiles:getAsString name="title"/></c:set>
<title><spring:message code="${title}" /></title>

<link rel="shortcut icon" href="<c:url value='/base/images/common/favicon.ico'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.layout-latest.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jclock.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
<script type="text/javascript">
//<![CDATA[
//레이아웃 변수들
var outerLayout, middleLayout, innerLayout;
var $mySpaceSplitter,
	mySpaceSplitterConfig = {
		splitWidth : 6,
		defaultWidth : 170,
		position : {min:100, max:300},
		expireTime : 60*60*24*7,
		cookiePath : location.pathname.substring(0, location.pathname.lastIndexOf("/")+1)	//location.pathname으로 적용하면 IE에서 안됨.
	};

(function($) {
	
	$(document).ready(function () {
		outerLayout = $('#protalMainLayout').layout({ 
			spacing_open : 0   
			,center__onresize : resizeMyspaceScroll  // west resize 시 호출
		});
		
		// 아래의 $mySpaceSplitter.width()코드로 사이즈 디텍트가 안되서...
		var style = iKEP.getCssStyle("#LeftContainer a.splitter", "theme.css");
		mySpaceSplitterConfig.splitWidth = (style && parseInt(style.width, 10)) || 6;

		$mySpaceSplitter = $("a.splitter", "#LeftContainer").removeClass("splitter").hide();
		mySpaceSplitterConfig.splitWidth = $mySpaceSplitter.width() || mySpaceSplitterConfig.splitWidth;
        $mySpaceSplitter.draggable({
        	axis:"x",
        	disabled : true,
        	start : function() {},
        	stop : function(event, ui) {
        		var pos = ui.position.left + mySpaceSplitterConfig.splitWidth;
        		if(pos < mySpaceSplitterConfig.position.min) pos = mySpaceSplitterConfig.position.min;
				if(pos > mySpaceSplitterConfig.position.max) pos = mySpaceSplitterConfig.position.max;

        		ui.helper.css("left", (pos - mySpaceSplitterConfig.splitWidth) + "px");
        		$.cookie("mySpaceWidth", pos, {path:mySpaceSplitterConfig.cookiePath, expires:mySpaceSplitterConfig.expireTime});
        	},
        	drag : function(event, ui) {
        		var pos = ui.position.left + mySpaceSplitterConfig.splitWidth;//ui.position.left + mySpaceSplitterConfig.splitWidth;
        		if(pos >= mySpaceSplitterConfig.position.min && pos <= mySpaceSplitterConfig.position.max) {
	        		$("#LeftContainer").css("width", pos+"px");
	        		$("#protalMainLayout").css("margin-left", pos+"px");
        		} else {
        			if(pos < mySpaceSplitterConfig.position.min) pos = mySpaceSplitterConfig.position.min;
					if(pos > mySpaceSplitterConfig.position.max) pos = mySpaceSplitterConfig.position.max;
					
					$("#LeftContainer").css("width", pos+"px");
	        		$("#protalMainLayout").css("margin-left", pos+"px");
	        		
	        		//ui.helper.css("left", (pos - ui.helper.width()) + "px");
	        		
	        		return false;
        		}
        	}
        });

	
			showMySpace();
		
		
		/** 상단 메뉴 스크립트 시작 **/
		var mainMenu = $("#topMenu");
		var width = 0, $menuItems = mainMenu.children("ul").children("li");
		$menuItems.each(function() { width += $(this).outerWidth(); });
		mainMenu.css({width:width + "px"});
		
		$menuItems.bind("mouseenter mouseleave", function(event) {
			switch(event.type) {
				case "mouseenter" :
					$(this).siblings().children("ul").hide();
					$(this).children("ul").show()
						.end().children("a").addClass("hover");
					break;
				case "mouseleave" :
					$(this).children("ul").hide()
						.end().children("a").removeClass("hover");
					break;
			}
		});
		
		$.each($menuItems.children("ul"), function() {
			var subMenu = $(this);
			subMenu.css({left:((subMenu.parent().outerWidth() - subMenu.outerWidth()) / 2 + 1)+"px"})
				.click(function(event) {
					var el = event.target;
					if(el.tagName.toLowerCase() == "a") {
						var url = $(el).attr("href");
						if(url && url.indexOf("#") == -1) {
							subMenu.hide();
						}
					}
				});
		});
		/** 상당 메뉴 스크립트 끝 **/
		
		// system 스크립트
		$("#systemLink").click(showSystemLink);	
		
		// personal box 스크립트
		$("#personalBoxClose").click(hidePersonalBox);
		
        {	// left quick menu setting --------------------------------------------------
        	var menuWidth = 300;	// 메뉴 영역의 wrap 사이즈
    		var $ul = $("#divQuickMenuLeft").find("ul");
    		
    		var totalWidth = 0;	// 전체 메뉴 사이즈
    		$ul.children("li").each(function() { totalWidth += $(this).outerWidth(); });

    		$ul.width(totalWidth);
    		if(menuWidth < totalWidth) {
    			var width = 0;
    			$ul.children("li").each(function() {
    				if(width + $(this).outerWidth() > menuWidth) return false;
    				else width += $(this).outerWidth();
    			});
    			
    			menuWidth = width;

    			$ul.css("position", "absolute")
    				.parent().css("position", "relative")
    					.width(menuWidth).height($ul.outerHeight())
    					.siblings(".quick_move_l, .quick_move_r").bind("click", function() {
    						if($ul.filter(":animated").length > 0) return false;
    						
    						var position = $ul.position();
    						switch(true) {
    							case $(this).hasClass("quick_move_l") :
    								var moveLeft = position.left + menuWidth;	// 보여지는 영역 만큼 이동해야할 거리
    								if(moveLeft > 0) {	// 이동하고 났을때 위치가 보여지는 영역의 right point보다 멀리가면 보정
    									moveLeft = 0;
    								}
    								break;
    							case $(this).hasClass("quick_move_r") :
    								var moveLeft = position.left - menuWidth;	// 보여지는 영역 만큼 이동해야할 거리
    								if(((moveLeft - menuWidth)*-1) > $ul.width()) {	// 이동하고 났을때 위치가 보여지는 영역의 right point보다 멀리가면 보정
    									moveLeft = (moveLeft*-1) - ($ul.width() - position.left);
    								}
    								break;
    						}
    						
    						if(position.left != moveLeft) {
    							//$ul.css("left", moveLeft+"px");
    							$ul.animate({left:moveLeft+"px"}, "fast");
    						}
    					});
    		} else {
    			$ul.parent().siblings().each(function(idx){
    				if(idx < 2) $(this).hide();
    				else $(this).css("margin-left", "6px");
    			});
    		}
    	
    		$("#btnQuickmenuSetting").click(function(event) {	// quick menu setting button event
    			var $menuContainer = $("#divQuickMenuLeft");
    			var position = $menuContainer.offset();
    			var width = 500;
    			new iKEP.Dialog({
    				title : "Quick Menu Setting",
    				id : "quickMenuSetting",
    				url : "<c:url value='/portal/main/quickmenuSetting.do'/>",
    				width : width,
    				height : 400,
    				position : [position.left, position.top+$menuContainer.height()+2],
    				callback : function(result) {
    					// 메뉴 목록을 새로 rendering 해야함.
    					location.reload();	// 우선 reload 처리
    				},
    				resizable :false,
    				titleClass : "ui-dialog-titlebar ui-widget-header-quickmenu ui-corner-all ui-helper-clearfix"
    			});
    		});
        }// left quick menu setting --------------------------------------------------end.
        
     	// online help 팝업
		$("#onlineHelpLink").click(
			function(){
				openOnlineHelp();
			}
		);
     	// 패스워드 변경 팝업
		$("#newPassword").click(
			function(){
				openNewPassword();
			}
		);
     	
        iKEP.checkBrowser("#blockHeader");
        
        $("a", "#myspaceBar").click(function(event) {
        	event.preventDefault();
        	
    		if($(this).parent().hasClass("LeftContainer_bar_opened")) {
    			//hideMySpace();
    		} else {
    			showMySpace();
    		}
    	});
	}); 
	
	/* Online Help Popup */
	openOnlineHelp = function(){
		iKEP.popupOpen('<c:url value="/portal/main/onlineHelp.do"/>', {width:990, height:582, status:true, scrollbar:false, toolbar:false, location:false}, 'OnlineHelp');
	};
	
	/* newPassword Popup */
	openNewPassword = function(){
		iKEP.popupOpen('<c:url value="/portal/main/newPassword.do"/>', {width:530, height:260, status:true, scrollbar:false, toolbar:false, location:false}, 'NewPassword');
	};
	

	/* newPassword Popup */
	//openNewSapPassword = function(){
	//	iKEP.popupOpen('<c:url value="/portal/main/newSapPassword.do"/>', {width:530, height:260, status:true, scrollbar:false, toolbar:false, location:false}, 'NewSapPassword');
	//};
	
	
	///{initUser == 1}/		
	//openNewSapPassword();
	///
	
	 
	/* 개인영역 펼치기 */
	showMySpace = function(){
		var mySpaceWidth = $.cookie("mySpaceWidth") || mySpaceSplitterConfig.defaultWidth;
		mySpaceWidth = '110';
		var $spaceContent = $("#protalMyspace").show();
		
		$("#protalMainLayout").removeClass("protalLayoutLeftClosed")
			.addClass("protalLayoutLeftOpened")
			.css("margin-left", mySpaceWidth+"px");
		
		$("#LeftContainer").removeClass("closed")
			.addClass("opened")
			.css("width" , mySpaceWidth+"px");
		
		$("#myspaceBar").removeClass("LeftContainer_bar_closed")
			.addClass("LeftContainer_bar_opened");
			//.html('<a href="#a" onclick="hideMySpace();"><span>closed</span></a>');

			$mySpaceSplitter.draggable("enable")
			.addClass("splitter")
			.show();
		
		var url = "<c:url value='/portal/main/getMySpaceNew.do'/>";
		var parms = "";
// 		if($spaceContent.data("isLoaded") != true) {
			$.ajax({
				url : url,
				data : "",
				type : "get",
				loadingElement : {container:"#LeftContainer"}, 
				success : function(result) {
					$spaceContent.html(result);
					$spaceContent.data("isLoaded", true);
					resizeMyspaceScroll();
					outerLayout.resizeAll();
				},
				error : function(event, request, settings) { 
					alert("error");	
				}
			});	
// 		} else {
// 			resizeMyspaceScroll();
// 			outerLayout.resizeAll();
// 		}
		
		updateOpenOption(1);
	};
	
	/* 개인영역 숨기기 */
	hideMySpace = function(){
		$.cookie("mySpaceWidth", $("#LeftContainer").width(), {path:mySpaceSplitterConfig.cookiePath, expires:mySpaceSplitterConfig.expireTime});
		
		$("#protalMyspace").hide();

		$("#LeftContainer").removeClass("opened")
			.addClass("closed")
			.css("width", "6px");
		
		$("#myspaceBar").removeClass("LeftContainer_bar_opened")
			.addClass("LeftContainer_bar_closed");
			//.html('<a href="#a" onclick="showMySpace();"><span>open</span></a>');
		
		$("#protalMainLayout").removeClass("protalLayoutLeftOpened")
			.addClass("protalLayoutLeftClosed")
			.css("margin-left", "6px");
		
		$mySpaceSplitter.draggable("disable")
			.removeClass("splitter ui-draggable-disabled ui-state-disabled")
			.hide();
		
		outerLayout.resizeAll();
		updateOpenOption(0);
	};
	
	/* 개인영역 높이 조절 */
	resizeMyspaceScroll = function(){
		var mySpaceHeight = $(window).height();
		var presonal = $(".perInfo");
		var personalHeight = 0;
		var refreshHeight = 0;
		if(presonal.length>0){
			personalHeight = $(".perInfo").outerHeight()+5;
			refreshHeight = 27;
			$(".conList").height(mySpaceHeight-personalHeight-refreshHeight);
		}
	};
	
	refreshMySpace = function(){
		var $spaceContent = $("#protalMyspace");
		$spaceContent.data("isLoaded", false);
		showMySpace();
	};
	
	updateOpenOption = function(openOption){
		$.ajax({
			url : '<c:url value="/portal/main/updateOpenOption.do"/>',
			data : {openOption:openOption},
			type : "post",
			success : function(result) {
				
			},
			error : function(event, request, settings) { 
				alert("error");	
			}
		});	
	};
	
	/** System 정보 스크립트 시작 **/
	var callSystemInfo = false;  // 시스템 정보 ajax 호출 여부, 초기 false 호출후 true, system 정보는 한번만 가져온다.
	var makeSystemLayer = false;
	
	/* system 레이어 닫기 */
	hideSystemLink = function(){
		var $target = $(".utilmenu_system_layer");
		$(".utilmenu_system").removeClass("selected");
		$target.hide();
	};
	
	/* system 레이어 열기 */
	showSystemLink = function(){
		
		if(!makeSystemLayer){
			var $systemLayer = $('<div class="utilmenu_system_layer none"><div class="util_close"><a href="#" id="close_system" onclick="hideSystemLink();"><span>close</span></a></div></div>');
			
			if($.browser.msie && $.browser.version == "7.0"){
				$systemLayer.appendTo(document.body);
			}else{
				$(".utilmenu_system").append($systemLayer);
			}
			makeSystemLayer = true;
		}
		
		var $target = $(".utilmenu_system_layer");
		
		if($target.css("display") == 'none') {
			$(".utilmenu_system").addClass("selected");
			var $sys = $(".utilmenu_system");
			
			if($.browser.msie && $.browser.version == "7.0"){
				var linkWidth = $sys.width()/2;
				var linkTop = $sys.offset().top+$sys.innerHeight();
				var linkLeft = $sys.offset().left+linkWidth-($target.width()/2);
				$target.css({
					top:linkTop,
					left:linkLeft
				});
			}
			
			getSystemInfo($target);
			
			$target.show();
		} else {
			hideSystemLink();
		}
	};
	
	/* system 정보 ajax로 가져오기 */
	getSystemInfo = function(target){					
		var $target = target;
		if(!callSystemInfo){
			callSystemInfo = true;
			var url = "<c:url value='/portal/main/portalSystemLink.do' />";
			var parms = "";
			$.ajax({				
				url : url,
				data : "",
				type : "post",
				success : function(result) {
					$target.prepend(result);					
				},
				error : function(event, request, settings) { 
					alert("error");
					callSystemInfo = false;
					hideSystemLink();
				}
			});			
		}
	};
	
	mainReload = function(){
		location.reload();
	};
	/** System 정보 스크립트 끝 **/
	
	/** Personal Service 스크립트 시작 **/
	
	/* Personal Service Layer Box 보이기 */
	showPersonalBox = function(action){
		var $personalBoxContent = $("#personalBoxContent");
		
		if($personalBoxContent.data("action") != action) {
			$personalBoxContent.data("action", action);
			
			var $personalBox = $("#personalBox");
			var $personalBoxTitle = $("#personalBoxTitle");
					
			if(action == "personal"){
				$personalBox.removeClass("quicktxt_recentBox quicktxt_favoriteBox").addClass("quicktxt_personalBox");
				$personalBoxTitle.removeClass("quicktxt_tit_recent quicktxt_tit_favorite").addClass("quicktxt_tit_personal");
				$personalBoxTitle.children("span").html("Personal");
				getPersonalInfo("personal");
			}else if(action == "recent"){
				$personalBox.removeClass("quicktxt_personalBox quicktxt_favoriteBox").addClass("quicktxt_recentBox");
				$personalBoxTitle.removeClass("quicktxt_tit_personal quicktxt_tit_favorite").addClass("quicktxt_tit_recent");
				$personalBoxTitle.children("span").html("History");			
				getPersonalInfo("recent");
			}else{
				$personalBox.removeClass("quicktxt_personalBox quicktxt_recentBox").addClass("quicktxt_favoriteBox");
				$personalBoxTitle.removeClass("quicktxt_tit_personal quicktxt_tit_recent").addClass("quicktxt_tit_favorite");
				$personalBoxTitle.children("span").html("Favorite");
				getPersonalInfo("favorite");
			}
			
			$personalBox.show();
		} else {
			hidePersonalBox();
		}
	};
	
	/* Personal Service Layer Box 닫기 */
	hidePersonalBox = function(){
		$("#personalBoxContent").data("action", "");
		$("#personalBoxContent").empty();
		$("#personalBox").hide();
	};
	
	/* Personal Service 정보 ajax로 가져오기 */
	getPersonalInfo = function(action){
		var viewUrl = {personal:"personal/getShortcut.do", recent:"recent/getShortcut.do", favorite:"favorite/getShortcut.do"},
			height = {personal:80, recent:250, favorite:250};
 
		var $personalBoxContent = $("#personalBoxContent").empty().height(height[action]);
		
		var viewData = $personalBoxContent.data(action);
// 		if(viewData === undefined) {
			$.ajax({				
				url : iKEP.getContextRoot() + "/support/" + viewUrl[action],
				type : "post",
				loadingElement : {container:"#personalBoxContent"}, 
				success : function(result) {
					$personalBoxContent.data(action, result);
					$personalBoxContent.html(result);
				},
				error : function(event, request, settings) {
					alert("error");
				}
			});
// 		} else {
// 			$personalBoxContent.html(viewData);
// 		}
	};
	
	/* Personal Service Document ajax로 가져오기 */
	var peraonalDialog = null;
	viewPersonalDocument = function(url, el){
		var title = $(el).text();
		var width = $(window).width()*0.7;
		var height = $(window).height()*0.7;
		peraonalDialog = new iKEP.Dialog({
			title: title,
			url: url,
			width:width,
			height:height,
			modal: true
		});
	};
	
	showMainFrameDialog = function(url, title, width, height, collback) {
		
		var mainFrameDialog = null;
		mainFrameDialog = new iKEP.Dialog({
			title: title,
			url: url,
			width:width,
			height:height,
			modal: true,
			collback : collback
		});
	};
	
	portalNoteLink = function() {
   	 	var $portalNoteLayer = $("#portalNoteLayer");

   		if($portalNoteLayer.css("display") == 'none') {
   		 	$portalNoteLayer.show();
   	   	 	$portalNoteLayer.ajaxLoadStart();
   		 	getPortalNote();
   	 	} else {
   		 	$portalNoteLayer.hide();
   		 	$portalNoteLayer.html("");
   	 	}
    };
    
	
	/** Personal Service 스크립트 끝 **/
})(jQuery);

function getPortalNote() {
	$jq.ajax({
		url : "<c:url value='/support/note/portalNote.do'/>",
		data : {},
		type : "get",
		dataType : "html",
		success : function(result) {
			$jq("#portalNoteLayer").html(result);
		}
	});
}

function credu(openUrl) {
	$jq.ajax({
		url : "<c:url value='/portal/main/credu.do'/>",
		data : {},
		type : "get",
		dataType : "html",
		success : function() {
			var tmpEmpNo = "${user.empNo}";
		    window.open(openUrl+'?p_grcode=SRV1750&p_code='+tmpEmpNo+'&p_gubun=cono','credu','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
		}
	});
	 //http://www.credu.com/pls/cyber/Ygp_path_sso.gatePathNew
}

function recPop(openUrl){
	$jq.ajax({
		url : "<c:url value='/portal/main/recOpen.do'/>",
		data : $jq("#recForm").serialize(),
		type : "post",
		success : function(result) {
			var rec = result.split("-_-");
			window.open(openUrl+'?mpsId='+encodeURIComponent(rec[0])+'&mpsNo='+encodeURIComponent(rec[1]),'rec','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
		}
	});
	//http://recruit.moorim.co.kr/epLoginRequest
}

function mpsPop(openUrl){
	$jq.ajax({
		url : "<c:url value='/portal/main/mpsOpen.do'/>",
		data : $jq("#mpsForm").serialize(),
		type : "post",
		success : function(result) {
			var mps = result.split("-_-");
			window.open(openUrl+'?mpsId='+encodeURIComponent(mps[0])+'&mpsNo='+encodeURIComponent(mps[1]),'mps','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
		}
	});
	//http://mps.moorim.co.kr/retrieveEpUserInfo.do
}

function mplPop(openUrl){
	$jq.ajax({
		url : "<c:url value='/portal/main/mplOpen.do'/>",
		data : $jq("#mplForm").serialize(),
		type : "post",
		success : function(result) {
			var mpl = result.split("-_-");
			//window.open('http://palletdev.moorim.co.kr/retrieveEpUserInfo.do?mplId='+encodeURIComponent(mpl[0])+'&mplNo='+encodeURIComponent(mpl[1]),'mpl','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
			window.open(openUrl+'?mplId='+encodeURIComponent(mpl[0])+'&mplNo='+encodeURIComponent(mpl[1]),'mpl','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
		}
	});
	//http://pallet.moorim.co.kr/retrieveEpUserInfo.do
}


function mlcPop(openUrl){
	$jq.ajax({
		url : "<c:url value='/portal/main/mlcOpen.do'/>",
		data : $jq("#mpsForm").serialize(),
		type : "post",
		success : function(result) {
			var mps = result.split("-_-");
			window.open(openUrl+'?mpsId='+encodeURIComponent(mps[0])+'&mpsNo='+encodeURIComponent(mps[1]),'mlc','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
		}
	});
	//http://mlc.moorim.co.kr/retrieveEpUserInfo.do
}

function salesPop(openUrl){
	$jq.ajax({
		url : "<c:url value='/portal/main/salesOpen.do'/>",
		data : $jq("#mpsForm").serialize(),
		type : "post",
		success : function(result) {
			var sales = result;
			window.open(openUrl+'?param='+encodeURIComponent(sales),'sales','width=1280,height=720,location=1,menubar=1,scrollbars=1,toolbar=1,resizable=1');
		}
	});
	
}



    function Cyberdigmpopup() {
        //팝업 설정( 해당 다이얼로그에 맞도록 수정)
        var position = "left=0,top=0,width=" + screen.availWidth + ",height="+ screen.availHeight + ",directories=1,menubar=1,resizable=1,scrollbars=1,toolbar=1,location=1";

        var w = window.open( "<c:url value='/base/common/destinySLO.jsp?TARGET_URL=main'/>","_blank", position);

        w.resizeTo(screen.availWidth, screen.availHeight);

    }


//]]>
</script>
</head>
<form name="mpsForm" id="mpsForm">
<input type="hidden" id="mpsId" name="mpsId" value="${user.userId}"/>
<input type="hidden" id="mpsNo" name="mpsNo" value="${user.empNo}"/>
</form>
<form name="mplForm" id="mplForm">
<input type="hidden" id="mplId" name="mplId" value="${user.userId}"/>
<input type="hidden" id="mplNo" name="mplNo" value="${user.empNo}"/>
</form>
<form name="recForm" id="recForm">
<input type="hidden" id="recId" name="recId" value="${user.userId}"/>
<input type="hidden" id="recNo" name="recNo" value="${user.empNo}"/>
</form>
<body class="bg_img portalLayout" style="overflow:hidden;">
<!--skipNavigation Start-->
<div id="skipNavigation">
	<p><a href="#topInfo"><ikep4j:message pre="${prefix}" key="skipNavigation.topInfo" /></a></p>
	<p><a href="#topMenu"><ikep4j:message pre="${prefix}" key="skipNavigation.topMenu" /></a></p>	
	<p><a href="#blockHeaderSub"><ikep4j:message pre="${prefix}" key="skipNavigation.blockHeaderSub" /></a></p>
	<p><a href="#mainFrame"><ikep4j:message pre="${prefix}" key="skipNavigation.mainFrame" /></a></p>
</div>
<!--//skipNavigation End-->

<div id="LeftContainer" class="closed">	
	<div id="protalMyspace"></div>
	<div class="LeftContainer_bar">
		<!-- <div class="LeftContainer_bar_closed" id="myspaceBar">
			<a href="#a"><span>open</span></a>
		</div> -->
	</div>
</div>
<!--Layout Start-->
<div id="protalMainLayout" class="protalLayoutLeftClosed">	
	<div class="ui-layout-north">		
		<!--wrapper Start-->
		<div id="wrapper">
			<!--blockContainer Start-->
			<div id="blockContainer">
				<!--blockHeader Start-->
				<div id="blockHeader">
					<div id="blockHeader_r">&nbsp;</div>
					<tiles:insertAttribute name="header" />
				</div>	
				<!--blockHeader End-->
			</div>
		</div>
		<!--wrapper End-->
	</div>
	<tiles:insertAttribute name="body" />
	
</div>
<!--Layout End-->

<div id="personalShortcutDiv" style="display:none;"></div>

<!--note layer start-->
<div class="note_menu_extension" id="portalNoteLayer" style="left: 70px; top:100px; display: none; z-index: 998;"></div>
<!--note layer end-->

</body>
</html>