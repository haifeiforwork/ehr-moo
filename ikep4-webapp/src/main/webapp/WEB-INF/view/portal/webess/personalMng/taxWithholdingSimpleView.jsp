<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
	
	});
	
	$(window).bind('beforeunload', function(){
		opener.printComplete();
	});
	
})(jQuery);

/* window.onload = function()
{
	opener.printComplete();
} */
</script>

<form id="taxWithholdingForm" name="taxWithholdingForm" method="post" >

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근로소득 원천징수영수증 조회</h1>

	<div class="button_box"></div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">근로소득 원천징수 영수증</p>
		<embed id="pdfDocument"  src="<c:url value='/portal/moorimess/personalMng/taxWithholdingGetSimplePDF.do'/>?IM_CERTI=<c:out value="${params.IM_CERTI}"/>&IM_APPID=<c:out value="${params.IM_APPID}"/>&IM_BEGDA=<c:out value="${params.IM_BEGDA}"/>&IM_WERKS=<c:out value="${params.IM_WERKS}"/>&imYear=<c:out value="${params.imYear }"/>#toolbar=0" width="100%" height="500px" type='application/pdf'></embed>
	</div>

	<div class="table_box" style="height:40px">
		<a href="http://get.adobe.com/kr/reader/" target='_blank' class="left">
			<img name="adobe" height="39" src="<c:url value="/base/images/ess/get_adobe_reader.png"/>" width="158" border=0>
		</a>
		<p>만약 Viewer 가 정상적으로 보이지 않는다면 Adobe Reader 가 설치되어 있는지 확인 하시기 바랍니다.</p>
		<p>Adobe Reader 가 설치되지 않은 사용자는 왼편 링크를 통해 설치 하시기 바랍니다.</p>
	</div>
	<b>□ 마우스 우클릭 후, 인쇄(P)버튼을 누르면 출력이 가능합니다.</b>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">


try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}

</script>