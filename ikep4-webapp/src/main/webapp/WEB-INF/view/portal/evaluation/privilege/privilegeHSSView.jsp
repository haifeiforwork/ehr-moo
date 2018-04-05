<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<style>

textarea {
	overflow-y : hidden;
}

</style>

<script type="text/javascript">
(function($){
	$(document).ready(function() {

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function() {
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/privilege/privilegeHSS.do'/>",
					PARAM_EMPNO : "${params.empno}",
					PARAM_USERID : "${params.userId}"
				});
			});
		}

		$.initView = function() {
			$.bindButtonBackHandler();

			$('#commonTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('[name=evalu_score_sum]').val($.ScoreRound($.SumScore('mark'), 1));

			$.DisabledBackgroundStyleChange();
		};

		$.initView();

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">촉탁/별정직 평가자평가</h1>

	<div class="btn_wrap left margintop_20" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
	</div>

	<table class="list_table">
		<colgroup>
			<col style="width:13%;">
			<col style="width:20%;">
			<col style="width:13%;">
			<col style="width:20%;">
			<col style="width:13%;">
			<col style="width:20%;">
		</colgroup>
		<tbody>
			<tr>
				<th>성명</th>
				<td class="text_left"><c:out value="${esHeader.APRSENM}" />(<c:out value="${esHeader.PERNR}" />)</td>
				<th>사원구분</th>
				<td class="text_left"><c:out value="${esHeader.PKTXT}" /></td>
				<th>직급</th>
				<td class="text_left"><c:out value="${esHeader.TRFGR}" /></td>
			</tr>
		</tbody>
	</table>

	<table id="commonTable" class="contents_table margintop_20">
		<colgroup>
			<col style="width:18%;" />
			<col style="" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
		</colgroup>
		<thead>
			<tr>
				<th>항목</th>
				<th>내용</th>
				<th>S</th>
				<th>A</th>
				<th>B</th>
				<th>C</th>
				<th>D</th>
				<th>점수</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty etDetail}">
				<c:forEach var="result" items="${etDetail}" varStatus="status">
					<tr>
						<td valign="top"><input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled /></td>
						<td><textarea name="bhidx" class="width_100" rows="3" disabled><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="S" <c:if test="${result.NXGRD eq 'S'}">checked</c:if> disabled /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="A" <c:if test="${result.NXGRD eq 'A'}">checked</c:if> disabled /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="B" <c:if test="${result.NXGRD eq 'B'}">checked</c:if> disabled /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="C" <c:if test="${result.NXGRD eq 'C'}">checked</c:if> disabled /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="D" <c:if test="${result.NXGRD eq 'D'}">checked</c:if> disabled /></td>
						<td class="text_center"><input type="text" name="mark" style="width: 60px; text-align:center;" value="${result.MARK}" disabled /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<table class="contents_table no_border">
		<colgroup>
			<col style="width:18%;" />
			<col style="" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
			<col style="width:5%;" />
		</colgroup>
		<tbody>
			<tr>
				<td colspan="7"></td>
				<td class="text_center"><input type="text" name="evalu_score_sum" class="border_gray" style="width: 60px; text-align:center;" disabled /></td>
			</tr>
		</tbody>
	</table>

	<form id="ajaxForm" name="ajaxForm" method="post"></form>

</div>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>