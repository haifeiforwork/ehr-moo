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

		var gradescores = {S:5, A:4, B:3, C:2, D:1};

		$.fn.calcScore = function() {
			var _tr = $(this).closest('tr');
			var rowCount = $(this).closest('tbody').find('tr').length;
			var weight = parseFloat(100 / rowCount);

			var gradeScore = gradescores[_tr.find('[name=nxgrd]').val()];

			var mtsco = gradeScore == null ? 0 : $.ScoreRound((weight/100) * gradeScore * 20, 1);

			_tr.find('[name=mark]').val(mtsco);
		}

		$.fn.bindRadioScoreCheckedHandler = function() {
			$(this).change(function() {
				$(this).closest('tr').find('[name=nxgrd]').val($(this).val());
				$(this).calcScore();
				$('[name=evalu_score_sum]').val($.ScoreRound($.SumScore('mark'), 1));
			});
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function(e) {
				e.preventDefault();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/privilege/privilegeList.do'/>",
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

			<c:if test="${params.APSTS eq '' || params.APSTS eq '1'}">
				$('#wrap').find('input:radio').each(function() { $(this).bindRadioScoreCheckedHandler(); });
				$('input:radio:checked').each(function() { $(this).calcScore() });
			</c:if>

			$('[name=evalu_score_sum]').val($.ScoreRound($.SumScore('mark'), 1));

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

			return isAllChecked;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('#commonTable').find('[name="zyear"]').each(function(index) { $(this).attr("name", "details[" + index + "].zyear"); });
			$('#commonTable').find('[name="pernr"]').each(function(index) { $(this).attr("name", "details[" + index + "].pernr"); });
			$('#commonTable').find('[name="otype"]').each(function(index) { $(this).attr("name", "details[" + index + "].otype"); });
			$('#commonTable').find('[name="objid"]').each(function(index) { $(this).attr("name", "details[" + index + "].objid"); });
			$('#commonTable').find('[name="dftxt"]').each(function(index) { $(this).attr("name", "details[" + index + "].dftxt"); });
			$('#commonTable').find('[name="stext"]').each(function(index) { $(this).attr("name", "details[" + index + "].stext").prop('disabled', false); });
			$('#commonTable').find('[name="bhidx"]').each(function(index) { $(this).attr("name", "details[" + index + "].bhidx").prop('disabled', false); });
			$('#commonTable').find('[name="seqnr"]').each(function(index) { $(this).attr("name", "details[" + index + "].seqnr"); });
			$('#commonTable').find('[name="nxgrd"]').each(function(index) { $(this).attr("name", "details[" + index + "].nxgrd"); });
			$('#commonTable').find('[name="mark"]').each(function(index) { $(this).attr("name", "details[" + index + "].mark").prop('disabled', false); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=details]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name="stext"]').prop('disabled', true);
			$('[name="bhidx"]').prop('disabled', true);
			$('[name="mark"]').prop('disabled', true);
		}

		$.makeFormData = function(button) {
			var formData = $('#evaluationForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'locat', value:'${params.LOCAT}'});
			formData.push({name:'locatx', value:'${params.LOCATX}'});
			formData.push({name:'orgeh', value:'${params.ORGEH}'});
			formData.push({name:'orgtx', value:'${params.ORGTX}'});
			formData.push({name:'pernr', value:'${params.PERNR}'});
			formData.push({name:'aprsenm', value:'${params.APRSENM}'});
			formData.push({name:'zzjik', value:'${params.ZZJIK}'});
			formData.push({name:'cotxt', value:'${params.COTXT}'});
			formData.push({name:'persk', value:'${params.PERSK}'});
			formData.push({name:'pktxt', value:'${params.PKTXT}'});
			formData.push({name:'trfgr', value:'${params.TRFGR}'});
			formData.push({name:'ppernr', value:'${params.PPERNR}'});
			formData.push({name:'aprsrnm', value:'${params.APRSRNM}'});
			formData.push({name:'zzjik1', value:'${params.ZZJIK1}'});
			formData.push({name:'cotxt1', value:'${params.COTXT1}'});
			formData.push({name:'porgeh', value:'${params.PORGEH}'});
			formData.push({name:'porgtx', value:'${params.PORGTX}'});
			formData.push({name:'reviewer', value:'${params.REVIEWER}'});
			formData.push({name:'revwrnm', value:'${params.REVWRNM}'});
			formData.push({name:'zzjik2', value:'${params.ZZJIK2}'});
			formData.push({name:'cotxt2', value:'${params.COTXT2}'});
			formData.push({name:'reasn', value:'${params.REASN}'});
			formData.push({name:'reasnx', value:'${params.REASNX}'});
			formData.push({name:'lrating', value:'${params.LRATING}'});
			formData.push({name:'mark', value:'${params.MARK}'});
			formData.push({name:'apsts', value:'${params.APSTS}'});
			formData.push({name:'apstsx', value:'${params.APSTSX}'});
			formData.push({name:'zyear', value:'${params.ZYEAR}'});
			formData.push({name:'exflg', value:'${params.EXFLG}'});
			formData.push({name:'srtky', value:'${params.SRTKY}'});
			formData.push({name:'modif', value:'${params.MODIF}'});
			formData.push({name:'orgeh2', value:'${params.ORGEH2}'});
			formData.push({name:'orgtx2', value:'${params.ORGTX2}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();
			$.beforSubmitRenameForModelAttribute();

			$.ajax({
				url : "<c:url value='/portal/evaluation/privilege/privilegeProc.do'/>"
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
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/privilege/privilegeList.do'/>",
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

	<h1 class="title">촉탁/별정직 평가자평가</h1>

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
			<col style="width:10%;">
			<col style="width:23%;">
			<col style="width:10%;">
			<col style="width:23%;">
			<col style="width:10%;">
			<col style="width:23%;">
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

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/privilege/privilegeProc.do'/>">
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
						<td valign="top">
							<input type="hidden" name="zyear" value="${result.ZYEAR == '' ? params.ZYEAR : result.ZYEAR}" />
							<input type="hidden" name="pernr" value="${result.PERNR}" />
							<input type="hidden" name="otype" value="${result.OTYPE}" />
							<input type="hidden" name="objid" value="${result.OBJID}" />
							<input type="hidden" name="dftxt" value="${result.DFTXT}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="nxgrd" value="${result.NXGRD}" />
							<input type="text" name="stext" class="width_100" value="${result.STEXT}" disabled />
						</td>
						<td><textarea disabled name="bhidx" class="width_100" rows="3"><c:out value="${result.BHIDX}" /></textarea></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="S" <c:if test="${result.NXGRD eq 'S'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="A" <c:if test="${result.NXGRD eq 'A'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="B" <c:if test="${result.NXGRD eq 'B'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="C" <c:if test="${result.NXGRD eq 'C'}">checked</c:if> /></td>
						<td class="text_center"><input type="radio" name="apgrd_common_${status.index}" value="D" <c:if test="${result.NXGRD eq 'D'}">checked</c:if> /></td>
						<td class="text_center"><input type="text" name="mark" disabled style="width: 60px; text-align:center;" value="${result.MARK}" /></td>
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