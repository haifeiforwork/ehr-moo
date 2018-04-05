<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/tab/style.css"/>" />

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("input[name=selEmpNo]").val("<c:out value="${params.selEmpNo}"/>");
		
		$("input[name=appointType][value=<c:out value="${params.appointType}"/>]").attr("checked", "checked");
		
		$("input[name=appointType]").click(function(){
			$.callProgress();
			$("#personalInfoForm").submit();
		});
		
		$("#photo").click(function(){
			var url = "<c:url value="${resultHeader.EX_HEADER.PHOTO}"/>";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=367px, height=460px, top=200px, left=300px, resizable=no";
			var popup = window.open(url, null, param);

			popup.focus();
		});
		
		$("a[name=detail]").click(function(){
			$("#popupForm").find("input[name=selEmpNo]").remove();
			$("#popupForm").append("<input type=\"hidden\" name=\"selEmpNo\" value=\"<c:out value="${params.selEmpNo}"/>\"/>");

			var target = "detailPop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=1200px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/personInfoDetailPop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();

		});
		
		//On Click Event
		$("ul.tabs li").click(function() {

			$("ul.tabs li").removeClass("active"); //Remove any "active" class
			$(this).addClass("active"); //Add "active" class to selected tab
			$(".tab_content").hide(); //Hide all tab content

			var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
			$(activeTab).fadeIn(); //Fade in the active ID content
			return false;
		});

		$("ul.tabs li:first").click();
		//Tab End
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
</style>
<form id="personalInfoForm" name="personalInfoForm" method="post" action="<c:url value='/portal/moorimmss/personalMng/personalInfoView.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>인사기본 조회</h1>
	
	<%@include file="/WEB-INF/view/portal/webmss/personalInfo.jsp"%>

	<div class="table_box">
		<ul class="tabs" style="margin-bottom:20px">
			<li><a href="#tab1">발령사항</a></li>
			<c:if test="${exHeader.STAT1 ne '0' && exHeader.STAT1 ne '3'}">
				<li><a href="#tab2">학력사항</a></li>
				<li><a href="#tab3">주소사항</a></li>
				<li><a href="#tab4">상벌내역</a></li>
				<li><a href="#tab5">자격사항</a></li>
				<li><a href="#tab6">외국어점수</a></li>
				<li><a href="#tab7">가족사항</a></li>
				<li><a href="#tab8">입사전 경력사항</a></li>
			</c:if>
		</ul>
		
		<div class="tab_container">
			<div id="tab1" class="tab_content" style="display:none">
				<%@include file="/WEB-INF/view/portal/webmss/personalMng/appointList.jsp"%>
			</div>
			<c:if test="${exHeader.STAT1 ne '0' && exHeader.STAT1 ne '3'}">
				<div id="tab2" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/educationList.jsp"%>
				</div>
				<div id="tab3" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/addressList.jsp"%>
				</div>
				<div id="tab4" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/prizeDisciplineList.jsp"%>
				</div>
				<div id="tab5" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/qualificationList.jsp"%>
				</div>
				<div id="tab6" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/foreignLanguageList.jsp"%>
				</div>
				<div id="tab7" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/familyList.jsp"%>
				</div>
				<div id="tab8" class="tab_content" style="display:none">
					<%@include file="/WEB-INF/view/portal/webmss/personalMng/careerList.jsp"%>
				</div>
			</c:if>
		</div>
	</div>
	
	<input type="hidden" name="selEmpNo" value=""/>
	<input type="hidden" name="imFirst" value="X"/>
</div>
</form>
<form id="popupForm" name="popupForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
