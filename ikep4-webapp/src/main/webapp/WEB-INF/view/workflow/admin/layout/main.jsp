<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="adminMessage" value="message.workflow.admin" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<title>Workflow Administratoration</title>

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/theme01/jquery_ui_custom.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/common.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/theme01/theme.css'/>" />
<link href='<c:url value="/base/css/jstree/themes/ikep/style.css"/>' type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="contextRoot/base/js/jquery/jquery.validate-1.8.min.js"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value='/base/js/common.pack.js'/>"></script>
<script type="text/javascript">
(function($){
	 $(document).ready(function () {
		//=========================================================================
		//* 레프트 메뉴 Display설정  
		//=========================================================================
		iKEP.setLeftMenu();
     });

	//=========================================================================
	//* 모델러 팝업 
	//=========================================================================
	fnPopupModel = function(){
		document.body.style.overflow = "hidden";
		var layerDiv = document.createElement("div");
		var layerIframe = document.createElement("iframe");
		layerDiv.style.cssText = "width:100%;height:100%;z-index:99999;position:absolute";
		layerIframe.src = "<c:url value='/workflow/modeler/main.do'/>";
		layerIframe.width = screen.availWidth;
		layerIframe.height = document.body.clientHeight;
		layerIframe.frameborder = "0";
		layerIframe.scrolling = "no"; 
		layerIframe.frameborder = "0"; 
		layerIframe.marginheight = "0"; 
		layerIframe.style.border = "none";
		//layerIframe.style.filter = "Alpha(Opacity=90)";
		layerDiv.appendChild(layerIframe);
		document.body.appendChild(layerDiv);
		layerDiv.style.left = "0px";
		layerDiv.style.top = document.documentElement.scrollTop+"px";
	} 
})(jQuery);
</script>
</head> 
<body>
<!--wrapper Start-->
<div id="wrapper">
	<!--blockContainer Start-->
	<div id="blockContainer">
		<!--blockHeader Start-->
		<div id="blockHeader">
			<tiles:insertAttribute name="header" />
		</div>
		<div class="clear"></div>
		<!--//blockHeader End-->
		<!--blockMain Start-->
		<div id="blockMain">
			<tiles:insertAttribute name="leftmenu" />
			<div id="mainContents" >
			<tiles:insertAttribute name="contents" />
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
</body>
</html>	 