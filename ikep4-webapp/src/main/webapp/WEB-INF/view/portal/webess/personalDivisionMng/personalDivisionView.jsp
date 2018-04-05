<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="exList" value="${resultMap.EX_LIST}"/>
<c:set var="itRag" value="${resultMap.IT_RAG_PERNR}"/>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnBack").click(function(){
			$("#personalDivisionForm").attr("method", "post");
			$("#personalDivisionForm").attr("action", "<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionMngList.do'/>");
			$("#personalDivisionForm").submit();
		});
	});
})(jQuery);
</script>
<form id="personalDivisionForm" name="personalDivisionForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>개인별 업무분장 신청</h1>

	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
	</div>

	<div class="table_box">
		<p class="f_title" style="padding-bottom:10px">
			개인별 업무분장
		</p>

		<c:set var="etDpResult" value="${resultMap.ET_DP_RESULT }"/>

		<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" id="jobTable">
				<caption></caption>
				<colgroup>
					<col width="35px"/>
					<col width="15%"/>
					<col width="15%"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<thead>
					<tr>
						<th></th>
						<th>직군</th>
						<th>직무</th>
						<th>직무명</th>
						<th>업무</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="result" items="${etDpResult}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="flag" value=""/></td>
							<td><span class="ZICGUN_T"><c:out value="${result.ZICGUN_T }"/></span></td>
							<td><span class="OBJ"><c:out value="${result.OBJ}"/></span></td>
							<td><span class="OBJ_T"><c:out value="${result.OBJ_T}"/></span></td>
							<td class="f_left">
								<span class="TASK_T"><c:out value="${result.TASK_T }"/></span>
								<span class="dpResultInfo">
									<c:forEach items="${dpResultKeySet}" var="key">
										<input type="hidden" name="<c:out value="${key}"/>" value="${result[key] }"/>
									</c:forEach>
								</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</span>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<c:if test="${exList.LEADR2 ne '00000000'}">
				<tr>
					<th class="list03_td_bg">파트/센터장</th>
					<td class="list03_td"><c:out value="${exList.LEADR2 }"/>&nbsp;<c:out value="${exList.LEADR2_T }"/></td>
				</tr>
				</c:if>
				<c:if test="${exList.LEADR ne '00000000'}">
				<tr>
					<th class="list03_td_bg">합의자</th>
					<td class="list03_td"><c:out value="${exList.LEADR }"/>&nbsp;<c:out value="${exList.LEADR_T }"/></td>
				</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="jobLineForm" name="jobLineForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>