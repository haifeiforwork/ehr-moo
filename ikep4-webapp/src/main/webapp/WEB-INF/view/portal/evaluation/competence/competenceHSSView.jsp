<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<style>
textarea {
	overflow-y : hidden;
}
</style>

<script type="text/javascript">
var refuseDialog;

(function($){
	$(document).ready(function() {

		$.initView = function() {
			$.bindButtonBackHandler();

			$('#commonTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#stellTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('[name=apsco1_sum]').val($.SumScore('apsco1'));
			$('[name=apsco2_sum]').val($.SumScore('apsco2'));

			$.DisabledBackgroundStyleChange();

			if('${params.APRSR2}' == '00000000') $.removeSecondaryMarkup();
		};

		$.removeSecondaryMarkup = function() {
			var tableIdArr = ['#commonTable', '#stellTable'];

			$.each(tableIdArr, function(index, id) {
				$(id).find('thead > tr:eq(0) > th')
					.eq(3)
						.prop('colspan', '4')
						.text('평가')
					.end()
					.eq(4)
						.remove()
					.end();

				$(id).find('tbody > tr').each(function() {
					$(this).find('td')
						.eq(3)
							.prop('colspan', '2')
						.end()
						.eq(4)
							.prop('colspan', '2')
						.end()
						.eq(6)
							.remove()
						.end()
						.eq(5)
							.remove()
						.end();
				});
			});

			$('.no_border > tbody > tr').eq(0).find('td')
				.eq(3)
					.remove()
				.end()
				.eq(2)
					.remove()
				.end()
				.eq(1)
					.prop('colspan', '2')
				.end();
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function(e) {
				e.preventDefault();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceHSS.do'/>",
					PARAM_EMPNO : "${params.empno}",
					PARAM_USERID : "${params.userId}"
				});
			});
		}

		$.initView();

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">역량평가</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
	</div>

	<h2 class="title margintop_20">${evQunam1}</h2>
	<table id="commonTable" class="contents_table">
		<colgroup>
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		    <col style="" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		</colgroup>
		<thead>
			<tr>
				<th>역량명</th>
				<th>정의</th>
				<th>행동지표</th>
				<th colspan="2">1차평가</th>
				<th colspan="2">2차평가</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty commonList}">
				<c:forEach var="result" items="${commonList}" varStatus="status">
					<tr>
						<td valign="top"><input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled /></td>
						<td><textarea disabled name="dftxt" class="width_100" rows="3"><c:out value="${result.DFTXT}" /></textarea></td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="text" name="apgrd1" value="${result.APGRD1}" disabled style="width:60px;text-align:center;"></td>
						<td class="text_center"><input type="text" name="apsco1" value="${result.APSCO1}" disabled style="width:60px;text-align:center;"></td>
						<td class="text_center"><input type="text" name="apgrd2" value="${result.APGRD2}" disabled style="width:60px;text-align:center;"></td>
						<td class="text_center"><input type="text" name="apsco2" value="${result.APSCO2}" disabled style="width:60px;text-align:center;"></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">${evQunam2}</h2>
	<table id="stellTable" class="contents_table">
		<colgroup>
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		    <col style="" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		</colgroup>
		<thead>
			<tr>
				<th>역량명</th>
				<th>정의</th>
				<th>행동지표</th>
				<th colspan="2">1차평가</th>
				<th colspan="2">2차평가</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty stellList}">
				<c:forEach var="result" items="${stellList}" varStatus="status">
					<tr>
						<td valign="top"><input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled /></td>
						<td><textarea disabled name="dftxt" class="width_100" rows="3"><c:out value="${result.DFTXT}" /></textarea></td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="text" name="apgrd1" value="${result.APGRD1}" disabled style="width:60px;text-align:center;"></td>
						<td class="text_center"><input type="text" name="apsco1" value="${result.APSCO1}" disabled style="width:60px;text-align:center;"></td>
						<td class="text_center"><input type="text" name="apgrd2" value="${result.APGRD2}" disabled style="width:60px;text-align:center;"></td>
						<td class="text_center"><input type="text" name="apsco2" value="${result.APSCO2}" disabled style="width:60px;text-align:center;"></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<table class="contents_table no_border">
		<colgroup>
		    <col style="" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		    <col style="width:8%;" />
		</colgroup>
		<tbody>
			<tr>
		      <td></td>
		      <td class="text_center"><input type="text" name="apsco1_sum" class="border_gray" disabled style="width:60px;text-align:center;"></td>
		      <td></td>
		      <td class="text_center"><input type="text" name="apsco2_sum" class="border_gray" disabled style="width:60px;text-align:center;"></td>
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