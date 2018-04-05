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

		$.initView = function() {
			$.bindButtonBackHandler();
			$.bindButtonSaveHandler();
			$.bindButtonCompleteHandler();

			$('[name=mcfbk]').each(function() { $(this).AutoHeightResizeTextarea(); });

			$.DisabledBackgroundStyleChange();
		}

		$.viewMode = function() {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();
			$('textarea[name="mcfbk"]').each(function(index) { $(this).prop("disabled", true); });

			$.DisabledBackgroundStyleChange();
		};

		$.isNanModelRow = function() {
			var isEmpty = false;

			$('[name=mcfbk]').each(function() { if(!$(this).val()) isEmpty = true; });

			return isEmpty;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('textarea[name="atask"]').each(function(index) { $(this).attr("name", "goals[" + index + "].atask").prop("disabled", false); });
			$('textarea[name="dtail"]').each(function(index) { $(this).attr("name", "goals[" + index + "].dtail").prop("disabled", false); });
			$('textarea[name="trget"]').each(function(index) { $(this).attr("name", "goals[" + index + "].trget").prop("disabled", false); });
			$('input[name="weight"]').each(function(index) { $(this).attr("name", "goals[" + index + "].weight").prop("disabled", false); });
			$('input[name="gubun"]').each(function(index) { $(this).attr("name", "goals[" + index + "].gubun").prop("disabled", false); });
			$('input[name="numbr"]').each(function(index) { $(this).attr("name", "goals[" + index + "].numbr").prop("disabled", false); });
			$('textarea[name="mdchk"]').each(function(index) { $(this).attr("name", "goals[" + index + "].mdchk").prop("disabled", false); });
			$('textarea[name="mcfbk"]').each(function(index) { $(this).attr("name", "goals[" + index + "].mcfbk"); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=goals]').each(function(index) {
				$(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true);
			});
			$('textarea[name="mcfbk"]').each(function(index) { $(this).prop("disabled", false); });
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
				url : "<c:url value='/portal/evaluation/performance/objectiveFeedbackProc.do'/>"
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
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/performance/objectiveList.do'/>",
								PARAM_ACTION : "OBJECTIVE_FEEDBACK",
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

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function(e) {
				e.preventDefault();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/performance/objectiveList.do'/>",
					PARAM_ACTION : "OBJECTIVE_FEEDBACK",
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

				if($.isNanModelRow()) {
					alert("모든 Column의 내역을 작성하십시오.");
					return false;
				}

				if(confirm("완료하시겠습니까?")) {
					$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
				}
			});
		}

		$.initView();

		<c:if test="${params.ACHVSTS ge '07'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">업적평가 중간점검 Feedback</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="완료" href="#"><span>완료</span></a>
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
				<td class="text_left">${export.ES_HEADER.APRSENM}(${export.ES_HEADER.APRSE})</td>
				<th>직책</th>
				<td class="text_left">${export.ES_HEADER.COTXT}</td>
				<th>직무</th>
				<td class="text_left">${export.ES_HEADER.STLTX}</td>
			</tr>
			<tr>
				<th>1차평가자</th>
				<td class="text_left">${export.ES_HEADER.APRSRNM1}</td>
				<th>2차평가자</th>
				<td class="text_left">${export.ES_HEADER.APRSRNM2}</td>
				<th>Reviewer</th>
				<td class="text_left">${export.ES_HEADER.REVWRNM}</td>
			</tr>
		</tbody>
	</table>

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/performance/objectiveFeedbackProc.do'/>">
	<h2 class="title margintop_20">정량</h2>
	<table id="qualTable" class="contents_table">
		<colgroup>
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 9%;" />
			<col style="width: 18%;" />
			<col style="width: 19%;" />
		</colgroup>
		<thead>
			<tr>
				<th>실행계획/담당업무</th>
				<th>세부내용/산식</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty qualList}">
				<c:forEach var="result" items="${qualList}">
					<tr>
						<td><textarea name="atask" disabled class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" disabled class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" disabled class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td class="text_center">
							<input type="text" name="weight" disabled style="width: 60px;text-align:center;" value="${result.WEIGHT}">
							<input type="hidden" name="gubun" value="${result.GUBUN}" />
							<input type="hidden" name="numbr" value="${result.NUMBR}" />
						</td>
						<td><textarea disabled name="mdchk" class="width_100" rows="5"><c:out value="${result.MDCHK}" /></textarea></td>
						<td><textarea name="mcfbk" class="width_100" rows="5"><c:out value="${result.MCFBK}" /></textarea></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">정성</h2>
	<table id="quanTable" class="contents_table">
		<colgroup>
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 18%;" />
			<col style="width: 9%;" />
			<col style="width: 18%;" />
			<col style="width: 19%;" />
		</colgroup>
		<thead>
			<tr>
				<th>실행계획/담당업무</th>
				<th>세부내용/산식</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty quanList}">
				<c:forEach var="result" items="${quanList}">
					<tr>
						<td><textarea name="atask" disabled class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" disabled class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" disabled class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td class="text_center">
							<input type="text" name="weight" disabled style="width: 60px;text-align:center;" value="${result.WEIGHT}">
							<input type="hidden" name="gubun" value="${result.GUBUN}" />
							<input type="hidden" name="numbr" value="${result.NUMBR}" />
						</td>
						<td><textarea disabled name="mdchk" class="width_100" rows="5"><c:out value="${result.MDCHK}" /></textarea></td>
						<td><textarea name="mcfbk" class="width_100" rows="5"><c:out value="${result.MCFBK}" /></textarea></td>
					</tr>
				</c:forEach>
			</c:if>
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