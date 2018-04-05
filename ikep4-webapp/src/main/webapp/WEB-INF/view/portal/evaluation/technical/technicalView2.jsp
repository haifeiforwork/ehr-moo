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

		var gradescores = {};
		var referencevalues = {};
		<c:if test="${not empty etScore}">
			<c:forEach var="score" items="${etScore}">
				<c:if test="${score.GUBUN eq 'R'}">
					referencevalues['${score.SEMID}'] = ${score.GSVAL};
				</c:if>
				<c:if test="${score.GUBUN eq 'S'}">
					gradescores['${fn:split(score.ETEXT, ',')[1]}'] = ${score.GSVAL};
				</c:if>
			</c:forEach>
		</c:if>

		$.sumScoreAndAttendance = function() {
			var sumScore = $.SumScore('mtsco');
			var abdedScore = $('[name=abded_for_calc]').val();

			var sum = sumScore + parseFloat(abdedScore);

			return $.ScoreRound(sum, 1);
		}

		$.fn.calcScore = function() {
			var _tr = $(this).closest('tr');
			var rowCount = $(this).closest('tbody').find('tr').length;
			var refValue = referencevalues[$(this).data('semid')];
			var weight = parseFloat(refValue / rowCount);

			var gradeScore = gradescores[_tr.find('[name=mtapr]').val()];

			var mtsco = gradeScore == null ? 0 : $.ScoreRound((weight/100) * gradeScore * 20, 1);

			_tr.find('[name=mtsco]').val(mtsco);
		}

		$.fn.bindRadioScoreCheckedHandler = function() {
			$(this).change(function() {
				$(this).closest('tr').find('[name=mtapr]').val($(this).val());
				$(this).calcScore();
				$('[name=evalu_score_sum]').val($.sumScoreAndAttendance());
			});
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function(e) {
				e.preventDefault();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/technical/technicalList.do'/>",
					PARAM_ACTION : "EVALUATION",
					PARAM_PAGE_NUM : "${params.currentPage}"
				});
			});
		}

		$.bindButtonSaveHandler = function() {
			$('#btnSave').click(function(e) {
				e.preventDefault();

				if(confirm("저장하시겠습니까?")) {
					$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false});
				}
			});
		}

		$.bindButtonCompleteHandler = function() {
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
		}

		$.bindButtonCriteriaHandler = function() {
			$('#btnCriteria').click(function(e) {
				e.preventDefault();
				alert("준비중");
			});
		}

		$.initView = function() {
			$.bindButtonBackHandler();
			$.bindButtonSaveHandler();
			$.bindButtonCompleteHandler();
			$.bindButtonCriteriaHandler();

			$('#commonTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#stellTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			<c:if test="${params.APSTS eq '' || params.APSTS eq '1'}">
				$('#wrap').find('input:radio').each(function() { $(this).bindRadioScoreCheckedHandler(); });
				$('input:radio:checked').each(function() { $(this).calcScore() });
			</c:if>

			$('[name=evalu_score_sum]').val($.sumScoreAndAttendance());

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
			$('#commonTable').find('[name="ayear"]').each(function(index) { $(this).attr("name", "details1[" + index + "].ayear"); });
			$('#commonTable').find('[name="aprse"]').each(function(index) { $(this).attr("name", "details1[" + index + "].aprse"); });
			$('#commonTable').find('[name="otype"]').each(function(index) { $(this).attr("name", "details1[" + index + "].otype"); });
			$('#commonTable').find('[name="objid"]').each(function(index) { $(this).attr("name", "details1[" + index + "].objid"); });
			$('#commonTable').find('[name="stext"]').each(function(index) { $(this).attr("name", "details1[" + index + "].stext").prop('disabled', false); });
			$('#commonTable').find('[name="bhidx"]').each(function(index) { $(this).attr("name", "details1[" + index + "].bhidx").prop('disabled', false); });
			$('#commonTable').find('[name="seqnr"]').each(function(index) { $(this).attr("name", "details1[" + index + "].seqnr"); });
			$('#commonTable').find('[name="mtapr"]').each(function(index) { $(this).attr("name", "details1[" + index + "].mtapr"); });
			$('#commonTable').find('[name="mtsco"]').each(function(index) { $(this).attr("name", "details1[" + index + "].mtsco").prop('disabled', false); });
			$('#stellTable').find('[name="ayear"]').each(function(index) { $(this).attr("name", "details2[" + index + "].ayear"); });
			$('#stellTable').find('[name="aprse"]').each(function(index) { $(this).attr("name", "details2[" + index + "].aprse"); });
			$('#stellTable').find('[name="otype"]').each(function(index) { $(this).attr("name", "details2[" + index + "].otype"); });
			$('#stellTable').find('[name="objid"]').each(function(index) { $(this).attr("name", "details2[" + index + "].objid"); });
			$('#stellTable').find('[name="stext"]').each(function(index) { $(this).attr("name", "details2[" + index + "].stext").prop('disabled', false); });
			$('#stellTable').find('[name="bhidx"]').each(function(index) { $(this).attr("name", "details2[" + index + "].bhidx").prop('disabled', false); });
			$('#stellTable').find('[name="seqnr"]').each(function(index) { $(this).attr("name", "details2[" + index + "].seqnr"); });
			$('#stellTable').find('[name="mtapr"]').each(function(index) { $(this).attr("name", "details2[" + index + "].mtapr"); });
			$('#stellTable').find('[name="mtsco"]').each(function(index) { $(this).attr("name", "details2[" + index + "].mtsco").prop('disabled', false); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=details1]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
			$('[name^=details2]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
			$('[name="ayear"]').prop('disabled', false);
			$('[name="aprse"]').prop('disabled', false);
			$('[name="otype"]').prop('disabled', false);
			$('[name="objid"]').prop('disabled', false);
			$('[name="seqnr"]').prop('disabled', false);
			$('[name="mtapr"]').prop('disabled', false);
		}

		$.makeFormData = function(button) {
			var formData = $('#evaluationForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'orgeh', value:'${params.ORGEH}'});
			formData.push({name:'orgtx', value:'${params.ORGTX}'});
			formData.push({name:'aprse', value:'${params.APRSE}'});
			formData.push({name:'aprsenm', value:'${params.APRSENM}'});
			formData.push({name:'zzjik', value:'${params.ZZJIK}'});
			formData.push({name:'cotxt', value:'${params.COTXT}'});
			formData.push({name:'trfgr', value:'${params.TRFGR}'});
			formData.push({name:'tecgb', value:'${params.TECGB}'});
			formData.push({name:'tecgbx', value:'${params.TECGBX}'});
			formData.push({name:'aprsr', value:'${params.APRSR}'});
			formData.push({name:'aprsrnm', value:'${params.APRSRNM}'});
			formData.push({name:'zzjik1', value:'${params.ZZJIK1}'});
			formData.push({name:'cotxt1', value:'${params.COTXT1}'});
			formData.push({name:'mtrat', value:'${params.MTRAT}'});
			formData.push({name:'revwr', value:'${params.REVWR}'});
			formData.push({name:'revwrnm', value:'${params.REVWRNM}'});
			formData.push({name:'zzjik2', value:'${params.ZZJIK2}'});
			formData.push({name:'cotxt2', value:'${params.COTXT2}'});
			formData.push({name:'msdat', value:'${params.MSDAT}'});
			formData.push({name:'mtsco', value:'${params.MTSCO}'});
			formData.push({name:'adjsc', value:'${params.ADJSC}'});
			formData.push({name:'apsts', value:'${params.APSTS}'});
			formData.push({name:'apstsx', value:'${params.APSTSX}'});
			formData.push({name:'qkid1', value:'${params.QKID1}'});
			formData.push({name:'qkid2', value:'${params.QKID2}'});
			formData.push({name:'bsid', value:'${params.BSID}'});
			formData.push({name:'exrsn', value:'${params.EXRSN}'});
			formData.push({name:'hiredate', value:'${params.HIREDATE}'});
			formData.push({name:'pblve', value:'${params.PBLVE}'});
			formData.push({name:'lvcnt', value:'${params.LVCNT}'});
			formData.push({name:'ayear', value:'${params.AYEAR}'});
			formData.push({name:'abded', value:'${params.ABDED}'});
			formData.push({name:'exflg', value:'${params.EXFLG}'});
			formData.push({name:'seqno', value:'${params.SEQNO}'});
			formData.push({name:'srtky', value:'${params.SRTKY}'});
			formData.push({name:'modif', value:'${params.MODIF}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();
			$.beforSubmitRenameForModelAttribute();

			$.ajax({
				url : "<c:url value='/portal/evaluation/technical/technicalProc.do'/>"
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
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/technical/technicalList.do'/>",
								PARAM_ACTION : "EVALUATION",
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

		$.initView();

		<c:if test="${params.APSTS ge '2'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">기능직평가 평가자평가</h1>

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
				<td class="text_left"><c:out value="${esHeader.APRSENM}" />(<c:out value="${esHeader.APRSE}" />)</td>
				<th>직책</th>
				<td class="text_left"><c:out value="${esHeader.COTXT}" /></td>
				<th>직급</th>
				<td class="text_left"><c:out value="${esHeader.TRFGR}" /></td>
			</tr>
		</tbody>
	</table>

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/technical/technicalProc.do'/>">
	<h2 class="title margintop_20"><c:out value="${evQunam1}" /></h2>
	<table id="commonTable" class="contents_table">
		<colgroup>
		    <col style="width:15%;" />
		    <col style="" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:6%;" />
		</colgroup>
		<thead>
			<tr>
				<th>구분</th>
				<th>평가기준</th>
				<th>S</th>
				<th>A</th>
				<th>B</th>
				<th>C</th>
				<th>D</th>
				<th>점수</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty etDetail1}">
				<c:forEach var="result" items="${etDetail1}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="otype" value="${result.OTYPE}" />
							<input type="hidden" name="objid" value="${result.OBJID}" />
							<input type="hidden" name="mtapr" value="${result.MTAPR}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="ayear" value="${result.AYEAR}" />
							<input type="hidden" name="aprse" value="${result.APRSE}" />
							<input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled />
						</td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="GART1" value="S" <c:if test="${result.MTAPR eq 'S'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="GART1" value="A" <c:if test="${result.MTAPR eq 'A'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="GART1" value="B" <c:if test="${result.MTAPR eq 'B'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="GART1" value="C" <c:if test="${result.MTAPR eq 'C'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" data-semid="GART1" value="D" <c:if test="${result.MTAPR eq 'D'}">checked</c:if> /></td>
						<td class="text_center"><input type="text" name="mtsco" disabled style="width: 60px; text-align:center;" value="${result.MTSCO}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20"><c:out value="${evQunam2}" /></h2>
	<table id="stellTable" class="contents_table">
		<colgroup>
		    <col style="width:15%;" />
		    <col style="" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:5%;" />
		    <col style="width:6%;" />
		</colgroup>
		<thead>
			<tr>
				<th>구분</th>
				<th>평가기준</th>
				<th>S</th>
				<th>A</th>
				<th>B</th>
				<th>C</th>
				<th>D</th>
				<th>점수</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty etDetail2}">
				<c:forEach var="result" items="${etDetail2}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="otype" value="${result.OTYPE}" />
							<input type="hidden" name="objid" value="${result.OBJID}" />
							<input type="hidden" name="mtapr" value="${result.MTAPR}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="ayear" value="${result.AYEAR}" />
							<input type="hidden" name="aprse" value="${result.APRSE}" />
							<input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled />
						</td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="GART2" value="S" <c:if test="${result.MTAPR eq 'S'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="GART2" value="A" <c:if test="${result.MTAPR eq 'A'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="GART2" value="B" <c:if test="${result.MTAPR eq 'B'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="GART2" value="C" <c:if test="${result.MTAPR eq 'C'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_stell_${status.index}" data-semid="GART2" value="D" <c:if test="${result.MTAPR eq 'D'}">checked</c:if> /></td>
						<td class="text_center"><input type="text" name="mtsco" disabled style="width: 60px; text-align:center;" value="${result.MTSCO}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

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