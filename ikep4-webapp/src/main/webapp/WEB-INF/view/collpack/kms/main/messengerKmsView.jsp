<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.kms.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 검색정보 선언 Start --%> 
<c:set var="isDebug" value="false" />
<c:set var="query" value="" />
<%-- 검색정보 선언 End --%>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>

<script type="text/javascript" src="${contextRoot}/base/js/portlet.js"></script>
<script type="text/javascript" src="${contextRoot}/search/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextRoot}/search/js/jquery-ui.min.js"></script>

<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/layout/layout-default-latest.css" /> 
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/theme06/jquery_ui_custom.css" />
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/common.css" />
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/theme06/theme.css" />

<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery.ui.datepicker.customize.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery.jstree.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery.validate-1.8.1.min.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/plugins.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/etc.plugins.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/langResource/ko.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/common.pack.js"></script>


<script type="text/javascript"> 
(function($) {

	$jq(document).ready(function() {	
		
	
		
		getPortletLeftView();
		
		iKEP.iFrameContentResize(); 
	});
	
	
	showKmsFrameDialog = function(url, title, width, height, collback) {
		
		/* var mainFrameDialog = null;
		mainFrameDialog = new iKEP.Dialog({
			title: title,
			url: url,
			width:width,
			height:height,
			modal: true,
			collback : collback
		}); */
		
		iKEP.popupOpen(url, {width:800, height:600}, "kmsPop");
	};
	
	getPortletLeftView = function() {
		<c:forEach var="layoutList" items="${kmsPortletLayoutList}" varStatus="status" begin="0" end="1" step="1" >
		<c:if test="${layoutList.colIndex==1}">
			
			<c:if test="${!empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.kmsPortlet.viewUrl}"/>?boardId=${layoutList.boardId}&portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>
			<c:if test="${empty layoutList.boardId}">
			var url ="<c:url value="${layoutList.kmsPortlet.viewUrl}"/>?portletLayoutId=${layoutList.portletLayoutId}";
			</c:if>		
			$jq("<div/>").load(url)			
	    	.error(function(event, request, settings) { alert("error"); })
	    	.appendTo("#portlet_left<c:out value='${layoutList.rowIndex}'/>");		

		</c:if>
		</c:forEach>
	};
	
	createView = function() {
		var url = "<c:url value='/portal/main/kmsCreateView.do' />";
		iKEP.popupOpen(url, {width:800, height:600}, "kmsCreate");
		//self.close();
	};
	
	kmsView = function() {
		var url = "<c:url value='/portal/main/kmsMain.do' />";
		iKEP.popupOpen(url, {width:800, height:600}, "kmsMain");
		//self.close();
	};
	
})(jQuery);



function doFoward(query) {
	
	document.search.query.value = document.search.query.value;
	document.search.submit();
}

function doFowardPop( query ) {
	
	document.search.query.value = query;
	document.search.submit();
}


function pressCheck() {   
	if (event.keyCode == 13) {
		return doFoward();
	}else{
		return false;
	}
}

</script>

<h1 class="none"><ikep4j:message pre="${prefix}" key="contents.pageTitle" /></h1>
<div class="clear"></div>
<!--blockSearch Start-->
<font color="red" style="font: bold;"><strong>
&nbsp;&nbsp;<지식광장 공지사항><br><br>
&nbsp;&nbsp;안녕하십니까?<br>
&nbsp;&nbsp;지식광장 운영과 관련하여 공지사항이 있어 알려드립니다.<br>
&nbsp;&nbsp;’18년 1월 29일부로 지식광장 및 Sales map을 이용하실 수 있습니다. <br>
&nbsp;&nbsp;이에 1월 29일부터 지식광장/Sales map 조회 및 작성이 가능하며,<br>
&nbsp;&nbsp;실적은 2월(1월 29일 작성분도 2월 실적으로 집계)부터 집계하도록 하겠습니다.<br>
&nbsp;&nbsp;(※ ’18년 연간 실적 집계시 ’17년 12월 ~ ’18년 1월 의무건수는 제외)<br>
&nbsp;&nbsp;감사합니다.
</strong></font>
<!--blockLeft Start-->
<div id="blockLeft">
	<c:forEach var="layoutList" items="${kmsPortletLayoutList}" begin="0" end="1" step="1"  varStatus="status">	
	<c:if test="${layoutList.colIndex==1}">	
		<div id="portlet_left<c:out value='${layoutList.rowIndex}'/>"></div>
	</c:if>
	</c:forEach>	
</div>

<!--//blockLeft End-->
<div class="blockButton" style="text-align: center;">
   	<a class="button" href="#a" onclick="javaScript:kmsView();"><span>지식광장 이동</span></a> 
	<a class="button" href="#a" onclick="javaScript:createView();"><span>지식등록</span></a>
</div>

<div class="clear"></div>
<!--//blockRight End-->



