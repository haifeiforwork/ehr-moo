<%--
=====================================================
* 기능 설명 : modalDailog container window - modalDialog에서 페이지 네비게이션이 가능하도록 하기 위해 container 윈도우로 활요
* 작성자 : 유화선
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%--=============================-->
<!--JSTL & Custom Tag Libray Area-->
<!--=============================--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- script src="<c:url value="/JavaScriptServlet"/>"></script --%>
<!--타이틀-->
<title><tiles:getAsString name="title"/></title>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript">
var interface = null;

(function($) {
	var resetIframeSize = function() {
		var containerMargin = 0, $container = $("#popup_contents_2");
		var height = $(window).height() - $("#popup_title_2").outerHeight() - containerMargin;
		$container.height(height);
	};
	
	$(document).ready(function() {
		resetIframeSize();
		
		var poolIdx = iKEP.getUrlArguments("poolIdx");
		if(poolIdx > -1 && opener) {
			interface = opener.iKEP.popupInterfacePool[poolIdx];
			
			if(interface.windowTitle) window.document.title = interface.windowTitle;
			if(interface.documentTitle) $("h1", "#popup_title_2").html(interface.documentTitle);
		
			var url = interface.url;
			url += (url.indexOf("?") > 0 ? "&" : "?") + "poolIdx=" + poolIdx;
	
			$("#frm").attr("src", url);
			
			$(window).resize(function() {
				resetIframeSize();
			})/*.unload(function() {
				opener && interface["callback"] && interface["callback"]();
			})*/;
			
			$("#btnClose").click(function() {
				//iKEP.returnPopup();
				window.close();
			});
		}
		//window.moveTo(0,0);
		//window.resizeTo(window.screen.width, window.screen.height);
		//resetIframeSize();
		
	});
})(jQuery);
</script>
</head>

<body>
<div id="popup_2">

	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1>타이틀 영역</h1>
		<a id="btnClose" href="#a"><span>닫기</span></a> 
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents_2">
	
		<iframe id="frm" style="width:100%; height:100%; border-width:0;"></iframe>
		
	</div>
	<!--//popup_contents End-->
			
</div>
</body>
</html>