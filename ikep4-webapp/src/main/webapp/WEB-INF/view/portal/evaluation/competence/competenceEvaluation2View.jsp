<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:choose>
	<c:when test="${params.APITM eq '3'}">
		<c:set var="semid" value="GART" />
	</c:when>
	<c:otherwise>
		<c:set var="semid" value="FLRT" />
	</c:otherwise>
</c:choose>

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

		var gradescores = {};
		var referencevalues = {};
		<c:if test="${not empty scoreList}">
			<c:forEach var="score" items="${scoreList}">
				<c:if test="${score.GUBUN eq 'R'}">
					referencevalues['${score.SEMID}'] = ${score.GSVAL};
				</c:if>
				<c:if test="${score.GUBUN eq 'S'}">
					gradescores['${score.ETEXT}'] = ${score.GSVAL};
				</c:if>
			</c:forEach>
		</c:if>

		$.fn.calcScore = function() {
			var $tr = $(this).closest('tr');
			var rowCount = $(this).closest('tbody').find('tr').length;
			var refValue = referencevalues[$(this).data('semid')];
			var weight = parseFloat(refValue / rowCount);

			var gradeScore1 = gradescores[$tr.find('[name=apgrd1]').val()];
			var gradeScore2 = gradescores[$tr.find('[name=apgrd2]').val()];

			var apsco1 = gradeScore1 == null ? 0 : $.ScoreRound((weight/100) * gradeScore1 * 10, 1);
			var apsco2 = gradeScore2 == null ? 0 : $.ScoreRound((weight/100) * gradeScore2 * 10, 1);

			$tr
				.find('[name=apsco1]')
					.val(apsco1)
				.end()
				.find('[name=apsco2]')
					.val(apsco2)
				.end()
				.find('[name=ptsum]')
					.val(parseFloat(apsco1) + parseFloat(apsco2))
				.end();
		}

		$.fn.bindRadioScoreCheckedHandler = function() {
			$(this).change(function() {
				$(this).closest('tr').find('[name=apgrd2]').val($(this).val());
				$(this).calcScore();
				$('[name=evalu_score_sum]').val($.SumScore('apsco2'));
			});
		}

		$.initView = function() {
			$('#commonTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#stellTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			<c:if test="${params.APSTS lt '2'}">
				$('#wrap').find('input:radio').each(function() { $(this).bindRadioScoreCheckedHandler(); });
				$('input:radio:checked').each(function() { $(this).calcScore() });
			</c:if>

			$('[name=evalu_score_sum]').val($.SumScore('apsco2'));

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function() {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();

			$('input:radio').prop('disabled', true);

			$.DisabledBackgroundStyleChange();
		};

		$.isAllCheckedRadio = function() {
			var isAllChecked = true;

			$('#commonTable > tbody > tr').each(function() {
				if($(this).find('input:radio:checked').length == 0) isAllChecked = false;
			});
			$('#stellTable > tbody > tr').each(function() {
				if($(this).find('input:radio:checked').length == 0) isAllChecked = false;
			});

			return isAllChecked;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('#commonTable').find('[name="qitem"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].qitem"); });
			$('#commonTable').find('[name="apgrd1"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].apgrd1"); });
			$('#commonTable').find('[name="apsco1"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].apsco1"); });
			$('#commonTable').find('[name="apgrd2"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].apgrd2"); });
			$('#commonTable').find('[name="ptsum"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].ptsum"); });
			$('#commonTable').find('[name="stext"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].stext").prop('disabled', false); });
			$('#commonTable').find('[name="dftxt"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].dftxt").prop('disabled', false); });
			$('#commonTable').find('[name="bhidx"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].bhidx").prop('disabled', false); });
			$('#commonTable').find('[name="apsco2"]').each(function(index) { $(this).attr("name", "cgoals[" + index + "].apsco2").prop('disabled', false); });
			$('#stellTable').find('[name="qitem"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].qitem"); });
			$('#stellTable').find('[name="apgrd1"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].apgrd1"); });
			$('#stellTable').find('[name="apsco1"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].apsco1"); });
			$('#stellTable').find('[name="apgrd2"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].apgrd2"); });
			$('#stellTable').find('[name="ptsum"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].ptsum"); });
			$('#stellTable').find('[name="stext"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].stext").prop('disabled', false); });
			$('#stellTable').find('[name="dftxt"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].dftxt").prop('disabled', false); });
			$('#stellTable').find('[name="bhidx"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].bhidx").prop('disabled', false); });
			$('#stellTable').find('[name="apsco2"]').each(function(index) { $(this).attr("name", "sgoals[" + index + "].apsco2").prop('disabled', false); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=cgoals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
			$('[name^=sgoals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
			$('[name="qitem"]').prop('disabled', false);
			$('[name="apgrd1"]').prop('disabled', false);
			$('[name="apsco1"]').prop('disabled', false);
			$('[name="apgrd2"]').prop('disabled', false);
			$('[name="ptsum"]').prop('disabled', false);
		}

		$.makeFormData = function(button) {
			var formData = $('#evaluationForm').serializeArray();

			formData.push({name:'button', value:button});
			formData.push({name:'ayear', value:'${params.AYEAR}'});
			formData.push({name:'aprse', value:'${params.APRSE}'});
			formData.push({name:'seqno', value:'${params.SEQNO}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();
			$.beforSubmitRenameForModelAttribute();

			$.ajax({
				url : "<c:url value='/portal/evaluation/competence/competenceEvaluationProc.do'/>"
				, type : "post"
				, data : $.makeFormData(params.button)
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);

						$.afterSubmitRenameForView();
						$.stopProgress();
					} else {
						alert(params.sucessMsg);

						if(params.isCallList) {
							$.CallListPage({
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceList.do'/>",
								PARAM_ACTION : "COMPETENCE_EVALUATION",
								PARAM_PAGE_NUM : "${params.currentPage}"
							});
						} else {
							$.afterSubmitRenameForView();
							$.stopProgress();
						}
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);

					$.afterSubmitRenameForView();
					$.stopProgress();
				}
			});
		}

		$('#btnBack').click(function(e) {
			e.preventDefault();
			$.CallListPage({
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceList.do'/>",
				PARAM_ACTION : "COMPETENCE_EVALUATION",
				PARAM_PAGE_NUM : "${params.currentPage}"
			});
		});

		$('#btnSave').click(function(e) {
			e.preventDefault();

			if(confirm("저장하시겠습니까?")) {
				$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false});
			}
		});

		$('#btnComplete').click(function(e) {
			e.preventDefault();

			if(!$.isAllCheckedRadio()) {
				alert("모든 항목에 대해 평가하셔야 합니다.");
				return false;
			}

			if(confirm("완료하시겠습니까?")) {
				$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
			}
		});

		$('#btnCriteria').click(function(e) {
			e.preventDefault();
			alert("준비중");
		});

		$.initView();

		<c:if test="${params.APSTS ge '2'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">역량평가 평가자평가</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="완료" href="#"><span>완료</span></a>
		<div class="float_right">
			<a class="btn_common" id="btnCriteria" title="평가 기준" href="#"><span>평가 기준</span></a>
		</div>
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
				<td class="text_left">${etHeader.APRSENM}(${etHeader.APRSE})</td>
				<th>직급</th>
				<td class="text_left">${etHeader.TRFGR}</td>
				<th>직무</th>
				<td class="text_left">${etHeader.STLTX}</td>
			</tr>
			<tr>
				<th>1차평가자</th>
				<td class="text_left">${etHeader.APRSRNM1}</td>
				<th>2차평가자</th>
				<td class="text_left">${etHeader.APRSRNM2}</td>
				<th>Reviewer</th>
				<td class="text_left">${etHeader.REVWRNM}</td>
			</tr>
		</tbody>
	</table>

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/competence/competenceEvaluationProc.do'/>">
	<h2 class="title margintop_20">${evQunam1}</h2>
	<table id="commonTable" class="contents_table">
		<colgroup>
		    <col style="width:10%;" />
		    <col style="width:15%;" />
		    <col style="" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:6%;" />
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2">역량명</th>
				<th rowspan="2">정의</th>
				<th rowspan="2">행동지표</th>
				<th colspan="3">S</th>
				<th colspan="3">A</th>
				<th colspan="3">B</th>
				<th colspan="3">C</th>
				<th colspan="3">D</th>
				<th rowspan="2">점수</th>
			</tr>
			<tr>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty commonList}">
				<c:forEach var="result" items="${commonList}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="qitem" value="${result.QITEM}" />
							<input type="hidden" name="apgrd1" value="${result.APGRD1}" />
							<input type="hidden" name="apsco1" value="${result.APSCO1}" />
							<input type="hidden" name="apgrd2" value="${result.APGRD2}" />
							<input type="hidden" name="ptsum" value="${result.PTSUM}" />
							<input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled />
						</td>
						<td><textarea disabled name="dftxt" class="width_100" rows="3"><c:out value="${result.DFTXT}" /></textarea></td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="S+" <c:if test="${result.APGRD2 eq 'S+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="S0" <c:if test="${result.APGRD2 eq 'S0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="S-" <c:if test="${result.APGRD2 eq 'S-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="A+" <c:if test="${result.APGRD2 eq 'A+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="A0" <c:if test="${result.APGRD2 eq 'A0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="A-" <c:if test="${result.APGRD2 eq 'A-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="B+" <c:if test="${result.APGRD2 eq 'B+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="B0" <c:if test="${result.APGRD2 eq 'B0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="B-" <c:if test="${result.APGRD2 eq 'B-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="C+" <c:if test="${result.APGRD2 eq 'C+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="C0" <c:if test="${result.APGRD2 eq 'C0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="C-" <c:if test="${result.APGRD2 eq 'C-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="D+" <c:if test="${result.APGRD2 eq 'D+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="D0" <c:if test="${result.APGRD2 eq 'D0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="${semid}1" value="D-" <c:if test="${result.APGRD2 eq 'D-'}">checked</c:if> /></td>
						<td class="text_center"><input type="text" name="apsco2" disabled style="width: 60px; text-align:center;" value="${result.APSCO2}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">${evQunam2}</h2>
	<table id="stellTable" class="contents_table">
		<colgroup>
		    <col style="width:10%;" />
		    <col style="width:15%;" />
		    <col style="" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:25px;" />
		    <col style="width:6%;" />
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2">역량명</th>
				<th rowspan="2">정의</th>
				<th rowspan="2">행동지표</th>
				<th colspan="3">S</th>
				<th colspan="3">A</th>
				<th colspan="3">B</th>
				<th colspan="3">C</th>
				<th colspan="3">D</th>
				<th rowspan="2">점수</th>
			</tr>
			<tr>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
				<th class="border_right_dotted">+</th>
				<th class="border_right_dotted">0</th>
				<th>-</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty stellList}">
				<c:forEach var="result" items="${stellList}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="qitem" value="${result.QITEM}" />
							<input type="hidden" name="apgrd1" value="${result.APGRD1}" />
							<input type="hidden" name="apsco1" value="${result.APSCO1}" />
							<input type="hidden" name="apgrd2" value="${result.APGRD2}" />
							<input type="hidden" name="ptsum" value="${result.PTSUM}" />
							<input disabled type="text" name="stext" class="width_100" value="${result.STEXT}" disabled />
						</td>
						<td><textarea disabled name="dftxt" class="width_100" rows="3"><c:out value="${result.DFTXT}" /></textarea></td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="S+" <c:if test="${result.APGRD2 eq 'S+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="S0" <c:if test="${result.APGRD2 eq 'S0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="S-" <c:if test="${result.APGRD2 eq 'S-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="A+" <c:if test="${result.APGRD2 eq 'A+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="A0" <c:if test="${result.APGRD2 eq 'A0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="A-" <c:if test="${result.APGRD2 eq 'A-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="B+" <c:if test="${result.APGRD2 eq 'B+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="B0" <c:if test="${result.APGRD2 eq 'B0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="B-" <c:if test="${result.APGRD2 eq 'B-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="C+" <c:if test="${result.APGRD2 eq 'C+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="C0" <c:if test="${result.APGRD2 eq 'C0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="C-" <c:if test="${result.APGRD2 eq 'C-'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="D+" <c:if test="${result.APGRD2 eq 'D+'}">checked</c:if> /></td>
						<td class="text_center border_right_dotted"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="D0" <c:if test="${result.APGRD2 eq 'D0'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="${semid}2" value="D-" <c:if test="${result.APGRD2 eq 'D-'}">checked</c:if> /></td>
						<td class="text_center"><input type="text" name="apsco2" disabled style="width: 60px; text-align:center;" value="${result.APSCO2}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<table class="contents_table no_border">
		<colgroup>
			<col style="width: 10%;" />
			<col style="width: 15%;" />
			<col style="" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 25px;" />
			<col style="width: 6%;" />
		</colgroup>
		<tbody>
			<tr>
				<td colspan="18"></td>
				<td class="text_center"><input type="text" disabled name="evalu_score_sum" class="border_gray" style="width: 60px; text-align:center;"></td>
			</tr>
		</tbody>
	</table>
	</form>

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