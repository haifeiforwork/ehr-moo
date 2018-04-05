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
<!--타이틀-->
<title><tiles:getAsString name="title"/></title>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript">
var opener = null;
var argument = null;

(function($) {
	$(document).ready(function() {
		try {
			var url = dialogArguments.url;
		        opener = dialogArguments.parent || null;
		        argument = dialogArguments.argument || null;
		        
		        if(!dialogArguments["callback"]) {	// callback이 없으면...
		        	window[iKEP.popupReturnFunction] =  function(result) {
		        	    window.returnValue = result;
		        	    window.close();
		        	};
		        }

			if(url) $("#frmBody").attr("src", url + (url.indexOf("?") >= 0 ? "&" : "?") + "callback=setResult");
		} catch(e) {}
	});
})(jQuery);
</script>
</head>

<frameset rows="*,0%" frameborder="no" scroll="yes">
    <frame id="frmBody" src="about:blank" scrolling="yes"  marginwidth="0" marginheight="0" style="width:100%; height:100%;"/>
    <frame id="frmHidden" src="about:blank" noresize scrolling="no"  marginwidth="0" marginheight="0"/>    
</frameset>

</html>