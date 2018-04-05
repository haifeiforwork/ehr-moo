<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>작성요령</h1>
	
	<c:choose>
		<c:when test="${params.helpType eq 'S1' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_005_2015_2.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		<c:when test="${params.helpType eq 'S2' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_007_2015.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		
		<c:when test="${params.helpType eq 'O1' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_012_2015_1.jpg"/>" alt="" height="500" width="795"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_012_2015_2.jpg"/>" alt="" height="500" width="795"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_012_2015_3.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		<c:when test="${params.helpType eq 'O2' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_009_2011.gif"/>" alt="" height="500" width="795"/>
		</c:when>
		<c:when test="${params.helpType eq 'O3' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_005_2015_1.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		<c:when test="${params.helpType eq 'O4' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_015_2015.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		
		<c:when test="${params.helpType eq 'T1' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_002_2015.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		<c:when test="${params.helpType eq 'T2' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_003_2015_1.jpg"/>" alt="" height="500" width="780"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_003_2015_2.jpg"/>" alt="" height="500" width="780"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_003_2015_3.jpg"/>" alt="" height="500" width="780"/>
		</c:when>
		<c:when test="${params.helpType eq 'T3' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_004_2015_1.jpg"/>" alt="" height="500" width="780"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_004_2015_2.jpg"/>" alt="" height="500" width="780"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_004_2015_3.jpg"/>" alt="" height="500" width="780"/>
		</c:when>
		<c:when test="${params.helpType eq 'T4' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_008_2015_1.jpg"/>" alt="" height="500" width="780"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_008_2015_2.jpg"/>" alt="" height="500" width="780"/>
		</c:when>
		<c:when test="${params.helpType eq 'T5' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_014_2015.jpg"/>" alt="" height="500" width="795"/>
		</c:when>
		<c:when test="${params.helpType eq 'T6' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_013_2015_1.jpg"/>" alt="" height="500" width="780"/>
			<img src="<c:url value="/base/images/ess/yearEndSettlement/HELP_013_2015_2.jpg"/>" alt="" height="500" width="780"/>
		</c:when>
		
		<c:when test="${params.helpType eq 'P1' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/help_print_popup.jpg"/>" alt="" height="520" width="480"/>
			<div class="f_title">※ 컴퓨터설정(프린터, 디스플레이 등)에 따라 여백을 1로 조정해도 최소지원값인</div>
			<div class="f_title">&nbsp;&nbsp;&nbsp;&nbsp;4.23이나 5로 재조정되는 경우가 있습니다.</div>
			<div class="f_title">&nbsp;&nbsp;&nbsp;&nbsp;금액부분이 잘리지 않으면 문제되지 않으므로 그대로 출력해주시길 바랍니다.</div>
		</c:when>
		<c:when test="${params.helpType eq 'P2' }">
			<img src="<c:url value="/base/images/ess/yearEndSettlement/help_print_ie8_2011.jpg"/>" alt="" height="520" width="480"/>
			<div class="f_title">※ 컴퓨터설정(프린터, 디스플레이 등)에 따라 여백을 1로 조정해도 최소지원값인</div>
			<div class="f_title">&nbsp;&nbsp;&nbsp;&nbsp;4.23이나 5로 재조정되는 경우가 있습니다.</div>
			<div class="f_title">&nbsp;&nbsp;&nbsp;&nbsp;금액부분이 잘리지 않으면 문제되지 않으므로 그대로 출력해주시길 바랍니다.</div>
		</c:when>
	</c:choose>
	
</div>
</form>
<form name="ajaxForm" id="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>