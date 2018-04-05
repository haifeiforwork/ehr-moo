<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<style>
textarea {
	overflow-y : hidden;
}
</style>

<script type="text/javascript">
var validator;

(function($){
	$(document).ready(function() {

		$.fn.bindComboChangeQitem = function() {
			$(this).change(function() {
				var $tr = $(this).closest('tr');
				var $selectedOption = $(this).find('option:selected');
				var selectedValue = $(this).val();

				$tr.find('[name=stext]').val($selectedOption.text());
				$tr.find('[name=dftxt]').text($('#dftxt_'+selectedValue, '#qitemDiv').text()).keyup();
				$tr.find('[name=bhidx]').text($('#bhidx_'+selectedValue, '#qitemDiv').text()).keyup();
			});
		}

		$.initView = function() {
			$('#btnPrint').hide();

			<c:if test="${params.QUSTS ge '03'}">
			$('#btnPrint').show();
			$.bindButtonPrintHandler();
			</c:if>

			$('#commonTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#stellTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('[name=qitem]').each(function() { $(this).bindComboChangeQitem(); });

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function() {
			$('[name=qitem]').each(function() { $(this).SelectboxToInput(); });

			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();

			$.DisabledBackgroundStyleChange();
		};

		$.isSelectedAllItem = function() {
			var isSelected = true;
			$('[name=qitem]').each(function() {
				if($(this).val() == '') isSelected = false;
			});
			return isSelected;
		}

		$.isDuplicatedItem = function() {
			var isDuplicated = false;

			var arrItem = new Array();
			var arrTemp = new Array();
			$('[name=qitem]').each(function() { arrItem.push($(this).val()); });

			$.each(arrItem, function(index, element) {
				if($.inArray(element, arrTemp) > -1) {
					isDuplicated = true;
				} else {
					arrTemp.push(element);
				}
			});

			return isDuplicated;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('[name="qitem"]').each(function(index) { $(this).attr("name", "goals[" + index + "].qitem"); });
			$('[name="stext"]').each(function(index) { $(this).attr("name", "goals[" + index + "].stext"); });
			$('[name="dftxt"]').each(function(index) { $(this).attr("name", "goals[" + index + "].dftxt").prop('disabled', false); });
			$('[name="bhidx"]').each(function(index) { $(this).attr("name", "goals[" + index + "].bhidx").prop('disabled', false); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=goals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name="dftxt"]').prop('disabled', true);
			$('[name="bhidx"]').prop('disabled', true);
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

			$.ajax({
				url : "<c:url value='/portal/evaluation/competence/competenceSetupProc.do'/>"
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
								PARAM_ACTION : "COMPETENCE_SETUP",
								PARAM_PAGE_NUM : "${params.currentPage}"
							});
						} else {
							$.afterSubmitRenameForView();
							$.initView();
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

		$('#btnBack').click(function() {
			$.callProgress();
			$.CallListPage({
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceList.do'/>",
				PARAM_ACTION : "COMPETENCE_SETUP",
				PARAM_PAGE_NUM : "${params.currentPage}"
			});
		});

		$('#btnSave').click(function(e) {
			e.preventDefault();

			$.beforSubmitRenameForModelAttribute();

			if(confirm("저장하시겠습니까?")) {
				$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false});
			} else {
				$.afterSubmitRenameForView();
			}
		});

		$('#btnComplete').click(function(e) {
			e.preventDefault();

			if(!$.isSelectedAllItem()) {
				alert("직무역량 4개를 모두 선정하셔야 합니다.");
				return false;
			}

			if($.isDuplicatedItem()) {
				alert("직무역량은 중복 선택할 수 없습니다.");
				return false;
			}

			$.beforSubmitRenameForModelAttribute();

			if(confirm("직무역량 선정이 완료되어 합의 진행됩니다.\n계속하시겠습니까?")) {
				$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
			} else {
				$.afterSubmitRenameForView();
			}
		});

		$.bindButtonPrintHandler = function() {
			$('#btnPrint').click(function(e) {
				e.preventDefault();

				var $inputElem = $('<input type="hidden" />');
				var $printForm = $('#ajaxForm');

				$printForm.empty();
				$printForm.append($inputElem.clone().attr('name', 'AYEAR').val('${params.AYEAR}'));
				$printForm.append($inputElem.clone().attr('name', 'SEQNO').val('${params.SEQNO}'));
				$printForm.append($inputElem.clone().attr('name', 'APRSE').val('${params.APRSE}'));
				$printForm.append($inputElem.clone().attr('name', 'APRSENM').val('${params.APRSENM}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR').val('${params.TRFGR}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK').val('${params.ZZJIK}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT').val('${params.COTXT}'));
				$printForm.append($inputElem.clone().attr('name', 'APRSR1').val('${params.APRSR1}'));
				$printForm.append($inputElem.clone().attr('name', 'APRSRNM1').val('${params.APRSRNM1}'));
				$printForm.append($inputElem.clone().attr('name', 'AQSTS1').val('${params.AQSTS1}'));
				$printForm.append($inputElem.clone().attr('name', 'AQSTSX1').val('${params.AQSTSX1}'));
				$printForm.append($inputElem.clone().attr('name', 'APRSR2').val('${params.APRSR2}'));
				$printForm.append($inputElem.clone().attr('name', 'APRSRNM2').val('${params.APRSRNM2}'));
				$printForm.append($inputElem.clone().attr('name', 'AQSTS2').val('${params.AQSTS2}'));
				$printForm.append($inputElem.clone().attr('name', 'AQSTSX2').val('${params.AQSTSX2}'));
				$printForm.append($inputElem.clone().attr('name', 'REVWR').val('${params.REVWR}'));
				$printForm.append($inputElem.clone().attr('name', 'REVWRNM').val('${params.REVWRNM}'));
				$printForm.append($inputElem.clone().attr('name', 'QUSTS').val('${params.QUSTS}'));
				$printForm.append($inputElem.clone().attr('name', 'QUSTSX').val('${params.QUSTSX}'));
				$printForm.append($inputElem.clone().attr('name', 'APSTS').val('${params.APSTS}'));
				$printForm.append($inputElem.clone().attr('name', 'APSTSX').val('${params.APSTSX}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGTX').val('${params.ORGTX}'));
				$printForm.append($inputElem.clone().attr('name', 'STELL').val('${params.STELL}'));
				$printForm.append($inputElem.clone().attr('name', 'STLTX').val('${params.STLTX}'));
				$printForm.append($inputElem.clone().attr('name', 'APITM').val('${params.APITM}'));

				$printForm.attr("method", "post");
				$printForm.attr("action", "<c:url value='/portal/evaluation/competence/competenceSetupViewPrint.do'/>");

				$.CallPrintPagePopup();

				$printForm.empty();
			});
		}

		$.initView();

		<c:if test="${params.QUSTS ge '02'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">역량평가 직무역량 선정</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="합의요청" href="#"><span>합의요청</span></a>
		<div class="float_right">
			<a class="btn_common" id="btnPrint" title="평가표 출력" href="#"><span>평가표 출력</span></a>
		</div>
	</div>

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/competence/competenceSetupProc.do'/>">
	<h2 class="title">공통역량</h2>
	<table class="contents_table" id="commonTable">
		<colgroup>
		    <col style="width:20%;" />
		    <col style="width:35%;" />
		    <col style="width:45%;" />
		</colgroup>
		<thead>
			<tr>
				<th>역량명</th>
				<th>정의</th>
				<th>행동지표</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty commonList}">
				<c:forEach var="result" items="${commonList}">
					<tr>
						<td valign="top"><input type="text" class="width_100" value="${result.STEXT}" disabled /></td>
						<td><textarea class="width_100" rows="3" disabled><c:out value="${result.DFTXT}" /></textarea></td>
						<td><textarea class="width_100" rows="3" disabled><c:out value="${result.BHIDX}" /></textarea></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">직무역량</h2>
	<table class="contents_table" id="stellTable">
		<colgroup>
		    <col style="width:20%;" />
		    <col style="width:35%;" />
		    <col style="width:45%;" />
		</colgroup>
		<thead>
			<tr>
				<th>역량명</th>
				<th>정의</th>
				<th>행동지표</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach begin="0" end="3" step="1" varStatus="status">
				<tr>
					<td valign="top">
						<select name="qitem" class="width_100">
							<option value="">선택</option>
							<c:forEach var="item" items="${qitemList}">
							<option value="${item.QITEM}" <c:if test="${stellList[status.index].QITEM eq item.QITEM}">selected</c:if>>${item.STEXT}</option>
							</c:forEach>
						</select>
						<input type="hidden" name="stext" value="${stellList[status.index].STEXT}" />
					</td>
					<td><textarea name="dftxt" class="width_100" rows="3" disabled><c:out value="${stellList[status.index].DFTXT}" /></textarea></td>
					<td><textarea name="bhidx" class="width_100" rows="3" disabled><c:out value="${stellList[status.index].BHIDX}" /></textarea></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>

	<form id="ajaxForm" name="ajaxForm" method="post"></form>

	<div id="qitemDiv" style="display:none;">
		<c:forEach var="item" items="${qitemList}">
			<textarea id="dftxt_${item.QITEM}">${item.DFTXT}</textarea>
			<textarea id="bhidx_${item.QITEM}">${item.BHIDX}</textarea>
		</c:forEach>
	</div>

</div>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>