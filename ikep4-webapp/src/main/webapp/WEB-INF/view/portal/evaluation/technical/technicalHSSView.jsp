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

		$.sumScoreAndAttendance = function() {
			var sumScore = $.SumScore('mtsco');
			var abdedScore = $('[name=abded_for_calc]').val() || 0;

			var sum = sumScore + parseFloat(abdedScore);

			return $.ScoreRound(sum, 1);
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function() {
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/technical/technicalHSS.do'/>",
					PARAM_EMPNO : "${params.empno}",
					PARAM_USERID : "${params.userId}"
				});
			});
		}

		$.initView = function() {
			$.bindButtonBackHandler();

			$('#commonTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#stellTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('[name=evalu_score_sum]').val($.sumScoreAndAttendance());

			$.DisabledBackgroundStyleChange();
		};

		$.initView();

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">기능직평가</h1>

	<div class="btn_wrap left">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
	</div>

	<table class="list_table">
		<colgroup>
			<col style="width: 13%;">
			<col style="width: 20%;">
			<col style="width: 13%;">
			<col style="width: 20%;">
			<col style="width: 13%;">
			<col style="width: 20%;">
		</colgroup>
		<tbody>
			<tr>
				<th>성명</th>
				<td class="text_left"><c:out value="${esHeader.APRSENM}" />(<c:out value="${esHeader.APRSE}" />)</td>
				<th>직책</th>
				<td class="text_left"><c:out value="${esHeader.COTXT}" /></td>
				<th>직급</th>
				<td class="text_left"><c:out value="${esHeader.TRFGR}" /></td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20"><c:out value="${evQunam1}" /></h2>
	<table id="commonTable" class="contents_table">
		<colgroup>
		    <col style="width:25%;" />
		    <col style="" />
		    <col style="width:6%;" />
		</colgroup>
		<thead>
			<tr>
				<th>역량명</th>
				<th>행동지표</th>
				<th>점수</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty etDetail1}">
				<c:forEach var="result" items="${etDetail1}" varStatus="status">
					<tr>
						<td valign="top"><input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled /></td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="text" name="mtsco" value="${result.MTSCO}" disabled style="width:60px;text-align:center;"></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20"><c:out value="${evQunam2}" /></h2>
	<table id="stellTable" class="contents_table">
		<colgroup>
		    <col style="width:25%;" />
		    <col style="" />
		    <col style="width:6%;" />
		</colgroup>
		<thead>
			<tr>
				<th>역량명</th>
				<th>행동지표</th>
				<th>점수</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty etDetail2}">
				<c:forEach var="result" items="${etDetail2}" varStatus="status">
					<tr>
						<td valign="top"><input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled /></td>
						<td><textarea name="bhidx" class="width_100" rows="3" disabled><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="text" name="mtsco" value="${result.MTSCO}" disabled style="width:60px;text-align:center;"></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<c:if test="${params.TECGB eq '2'}">
		<h2 class="title margintop_20">근태</h2>
		<table class="contents_table">
			<colgroup>
				<col style="" />
				<col style="width: 6%;" />
			</colgroup>
			<tbody>
				<tr>
					<th>조퇴, 지각 등 여부 감점 (별도기준)</th>
					<td class="text_center"><input type="text" name="abded_for_calc" value="${params.ABDED}" style="width: 60px; text-align:center;" disabled /></td>
				</tr>
			</tbody>
		</table>
	</c:if>

	<table class="contents_table no_border">
		<colgroup>
			<col style="" />
			<col style="width: 6%;" />
		</colgroup>
		<tbody>
			<tr>
				<td></td>
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