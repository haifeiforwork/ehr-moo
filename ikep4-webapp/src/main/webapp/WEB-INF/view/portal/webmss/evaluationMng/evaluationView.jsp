<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/tablesort/style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/tab/style.css"/>" />

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("input[name=selEmpNo]").val("<c:out value="${params.selEmpNo}"/>");
		
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
		
	});
})(jQuery);;
</script>
<style type="text/css">
.cursor-pointer{cursor:pointer;}
</style>
<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/moorimmss/evaluationMng/evaluationList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>평가이력 조회</h1>
	
	<%@include file="/WEB-INF/view/portal/webmss/personalInfo.jsp"%>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">개인별 평가이력</p>
		
		<c:set var="etList" value="${resultHeader.ET_LIST }"/>
	
		<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="10%"/>
					<col width="*"/>
					<col width="10%"/>
					<col width="15%"/>
					<col width="10%"/>
					<col width="10%"/>
					<col width="10%"/>
					
				</colgroup>
				<tbody>
					<tr>
						<th class="list02_td_bg">년도</th>
						<th class="list02_td_bg">소속</th>
						<th class="list02_td_bg">직급</th>
						<th class="list02_td_bg">직책</th>
						<th class="list02_td_bg">직무</th>
						<th class="list02_td_bg">업적평가</th>
						<th class="list02_td_bg">역량평가</th>
					</tr>
					<c:forEach var="etl" items="${etList}" varStatus="status">
						<tr>
							<td class="list02_td"><c:out value="${etl.ZYEAR}"/></td>
							<td class="list02_td"><c:out value="${etl.ORGTX}"/></td>
							<td class="list02_td"><c:out value="${etl.TRFGR}"/></td>
							<td class="list02_td"><c:out value="${etl.COTXT}"/></td>
							<td class="list02_td"><c:out value="${etl.STLTX}"/></td>
							<td class="list02_td"><c:out value="${etl.ACHGR}"/></td>
							<td class="list02_td"><c:out value="${etl.CAPGR}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>
	
	<input type="hidden" name="selEmpNo" value=""/>
	<input type="hidden" name="imFirst" value="X"/>
</div>
</form>
<form id="popupForm" name="popupForm">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
