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

		$.fn.bindRadioScoreCheckedHandler = function() {
			$(this).change(function() {
				$(this).closest('tr').find('[name=mrslt]').val($(this).val());
			});
		}

		$.initView = function() {
			if($('#multi1Table').length) $('#multi1Table').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			if($('#multi2Table').length) $('#multi2Table').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			if($('#multi3Table').length) $('#multi3Table').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			if($('#multi4Table').length) $('#multi4Table').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('#wrap').find('input:radio').each(function() { $(this).bindRadioScoreCheckedHandler(); });
		};

		$.viewMode = function() {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();
			$('#btnCriteria').unbind().hide();

			$('input:radio').unbind().prop('disabled', true);
		};

		$.isAllCheckedRadio = function() {
			var isAllChecked = true;

			$('#multi1Table > tbody > tr').each(function() {
				if($(this).find('input:radio:checked').length == 0) isAllChecked = false;
			});
			$('#multi2Table > tbody > tr').each(function() {
				if($(this).find('input:radio:checked').length == 0) isAllChecked = false;
			});
			$('#multi3Table > tbody > tr').each(function() {
				if($(this).find('input:radio:checked').length == 0) isAllChecked = false;
			});
			$('#multi4Table > tbody > tr').each(function() {
				if($(this).find('input:radio:checked').length == 0) isAllChecked = false;
			});

			return isAllChecked;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('[name="mrgrp"]').each(function(index) { $(this).attr("name", "details[" + index + "].mrgrp"); });
			$('[name="seqnr"]').each(function(index) { $(this).attr("name", "details[" + index + "].seqnr"); });
			$('[name="mroty"]').each(function(index) { $(this).attr("name", "details[" + index + "].mroty"); });
			$('[name="mrobj"]').each(function(index) { $(this).attr("name", "details[" + index + "].mrobj"); });
			$('[name="motyp"]').each(function(index) { $(this).attr("name", "details[" + index + "].motyp"); });
			$('[name="mobjd"]').each(function(index) { $(this).attr("name", "details[" + index + "].mobjd"); });
			$('[name="mstext"]').each(function(index) { $(this).attr("name", "details[" + index + "].mstext").prop('disabled', false); });
			$('[name="mdtail"]').each(function(index) { $(this).attr("name", "details[" + index + "].mdtail").prop('disabled', false); });
			$('[name="mrslt"]').each(function(index) { $(this).attr("name", "details[" + index + "].mrslt"); });

			$('input:radio').prop('disabled', true);
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=details]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name="mstext"]').prop('disabled', true);
			$('[name="mdtail"]').prop('disabled', true);

			$('input:radio').prop('disabled', false);
		}

		$.makeFormData = function(button) {
			var formData = $('#multisideForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'orgeh', value:'${params.ORGEH}'});
			formData.push({name:'orgtx', value:'${params.ORGTX}'});
			formData.push({name:'mappe', value:'${params.MAPPE}'});
			formData.push({name:'mapenm', value:'${params.MAPENM}'});
			formData.push({name:'persg', value:'${params.PERSG}'});
			formData.push({name:'zzjik', value:'${params.ZZJIK}'});
			formData.push({name:'cotxt', value:'${params.COTXT}'});
			formData.push({name:'trfgr', value:'${params.TRFGR}'});
			formData.push({name:'mappr', value:'${params.MAPPR}'});
			formData.push({name:'maprnm', value:'${params.MAPRNM}'});
			formData.push({name:'zzjik1', value:'${params.ZZJIK1}'});
			formData.push({name:'cotxt1', value:'${params.COTXT1}'});
			formData.push({name:'trfgr1', value:'${params.TRFGR1}'});
			formData.push({name:'msgrp', value:'${params.MSGRP}'});
			formData.push({name:'msgrpx', value:'${params.MSGRPX}'});
			formData.push({name:'msgrpdl', value:'${params.MSGRPDL}'});
			formData.push({name:'mssts', value:'${params.MSSTS}'});
			formData.push({name:'msstsx', value:'${params.MSSTSX}'});
			formData.push({name:'msstsdl', value:'${params.MSSTSDL}'});
			formData.push({name:'ayear', value:'${params.AYEAR}'});
			formData.push({name:'srtky', value:'${params.SRTKY}'});
			formData.push({name:'modif', value:'${params.MODIF}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/multiside/multisideDiagnosisProc.do'/>"
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
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/multiside/multisideDiagnosisList.do'/>",
								PARAM_ACTION : "MULTISIDE_DIAGNOSIS",
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
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/multiside/multisideDiagnosisList.do'/>",
				PARAM_ACTION : "MULTISIDE_DIAGNOSIS",
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

			if(!$.isAllCheckedRadio()) {
				alert("모든 항목에 대해 진단하셔야 합니다.");
				return false;
			}

			$.beforSubmitRenameForModelAttribute();

			if(confirm("완료하시겠습니까?")) {
				$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
			} else {
				$.afterSubmitRenameForView();
			}
		});

		$('#btnCriteria').click(function(e) {
			e.preventDefault();
			alert("준비중");
		});

		$.initView();

		<c:if test="${params.MSSTS ge '2'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">다면진단</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="완료" href="#"><span>완료</span></a>
		<div class="float_right">
			<a class="btn_common" id="btnCriteria" title="관찰 포인트" href="#"><span>관찰 포인트</span></a>
		</div>
	</div>

	<table class="list_table">
		<colgroup>
			<col style="width: 10%;">
			<col style="width: 23%;">
			<col style="width: 10%;">
			<col style="width: 23%;">
			<col style="width: 10%;">
			<col style="width: 23%;">
		</colgroup>
		<tbody>
			<tr>
				<th>성명</th>
				<td class="text_left"><c:out value="${esMappr.MAPENM}" />(<c:out value="${esMappr.MAPPE}" />)</td>
				<th>직책</th>
				<td class="text_left"><c:out value="${esMappr.COTXT}" /></td>
				<th>직급</th>
				<td class="text_left"><c:out value="${esMappr.TRFGR}" /></td>
			</tr>
			<tr>
		</tbody>
	</table>

	<form id="multisideForm" name="multisideForm" method="post" action="<c:url value='/portal/evaluation/multiside/multisideDiagnosisProc.do'/>">

	<c:if test="${not empty etDetail1List}">
		<h2 class="title margintop_20">공통역량</h2>
		<table class="contents_table" id="multi1Table">
			<colgroup>
			    <col style="width:18%;" />
			    <col style="" />
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${etDetail1List}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="mrgrp" value="${result.MRGRP}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="mroty" value="${result.MROTY}" />
							<input type="hidden" name="mrobj" value="${result.MROBJ}" />
							<input type="hidden" name="motyp" value="${result.MOTYP}" />
							<input type="hidden" name="mobjd" value="${result.MOBJD}" />
							<input type="hidden" name="mrslt" value="${result.MRSLT}" />
							<input type="text" name="mstext" class="width_100" value="${result.MSTEXT}" disabled />
						</td>
						<td><textarea name="mdtail" class="width_100" rows="3" disabled>${result.MDTAIL}</textarea></td>
						<td class="text_center"><input type="radio" name="mrslt1_${status.index}" <c:if test="${result.MRSLT eq '5'}">checked</c:if> value="5"></td>
						<td class="text_center"><input type="radio" name="mrslt1_${status.index}" <c:if test="${result.MRSLT eq '4'}">checked</c:if> value="4"></td>
						<td class="text_center"><input type="radio" name="mrslt1_${status.index}" <c:if test="${result.MRSLT eq '3'}">checked</c:if> value="3"></td>
						<td class="text_center"><input type="radio" name="mrslt1_${status.index}" <c:if test="${result.MRSLT eq '2'}">checked</c:if> value="2"></td>
						<td class="text_center"><input type="radio" name="mrslt1_${status.index}" <c:if test="${result.MRSLT eq '1'}">checked</c:if> value="1"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${not empty etDetail2List}">
		<h2 class="title margintop_20">리더십역량</h2>
		<table class="contents_table" id="multi2Table">
			<colgroup>
			    <col style="width:18%;" />
			    <col style="" />
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${etDetail2List}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="mrgrp" value="${result.MRGRP}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="mroty" value="${result.MROTY}" />
							<input type="hidden" name="mrobj" value="${result.MROBJ}" />
							<input type="hidden" name="motyp" value="${result.MOTYP}" />
							<input type="hidden" name="mobjd" value="${result.MOBJD}" />
							<input type="hidden" name="mrslt" value="${result.MRSLT}" />
							<input type="text" name="mstext" class="width_100" value="${result.MSTEXT}" disabled />
						</td>
						<td><textarea name="mdtail" class="width_100" rows="3" disabled>${result.MDTAIL}</textarea></td>
						<td class="text_center"><input type="radio" name="mrslt2_${status.index}" <c:if test="${result.MRSLT eq '5'}">checked</c:if> value="5"></td>
						<td class="text_center"><input type="radio" name="mrslt2_${status.index}" <c:if test="${result.MRSLT eq '4'}">checked</c:if> value="4"></td>
						<td class="text_center"><input type="radio" name="mrslt2_${status.index}" <c:if test="${result.MRSLT eq '3'}">checked</c:if> value="3"></td>
						<td class="text_center"><input type="radio" name="mrslt2_${status.index}" <c:if test="${result.MRSLT eq '2'}">checked</c:if> value="2"></td>
						<td class="text_center"><input type="radio" name="mrslt2_${status.index}" <c:if test="${result.MRSLT eq '1'}">checked</c:if> value="1"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${not empty etDetail3List}">
		<h2 class="title margintop_20">협업</h2>
		<table class="contents_table" id="multi3Table">
			<colgroup>
			    <col style="width:18%;" />
			    <col style="" />
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${etDetail3List}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="mrgrp" value="${result.MRGRP}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="mroty" value="${result.MROTY}" />
							<input type="hidden" name="mrobj" value="${result.MROBJ}" />
							<input type="hidden" name="motyp" value="${result.MOTYP}" />
							<input type="hidden" name="mobjd" value="${result.MOBJD}" />
							<input type="hidden" name="mrslt" value="${result.MRSLT}" />
							<input type="text" name="mstext" class="width_100" value="${result.MSTEXT}" disabled />
						</td>
						<td><textarea name="mdtail" class="width_100" rows="3" disabled><c:out value="${result.MDTAIL}" /></textarea></td>
						<td class="text_center"><input type="radio" name="mrslt3_${status.index}" <c:if test="${result.MRSLT eq '5'}">checked</c:if> value="5"></td>
						<td class="text_center"><input type="radio" name="mrslt3_${status.index}" <c:if test="${result.MRSLT eq '4'}">checked</c:if> value="4"></td>
						<td class="text_center"><input type="radio" name="mrslt3_${status.index}" <c:if test="${result.MRSLT eq '3'}">checked</c:if> value="3"></td>
						<td class="text_center"><input type="radio" name="mrslt3_${status.index}" <c:if test="${result.MRSLT eq '2'}">checked</c:if> value="2"></td>
						<td class="text_center"><input type="radio" name="mrslt3_${status.index}" <c:if test="${result.MRSLT eq '1'}">checked</c:if> value="1"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<c:if test="${not empty etDetail4List}">
		<h2 class="title margintop_20">동기부여</h2>
		<table class="contents_table" id="multi4Table">
			<colgroup>
			    <col style="width:18%;" />
			    <col style="" />
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
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${etDetail4List}" varStatus="status">
					<tr>
						<td valign="top">
							<input type="hidden" name="mrgrp" value="${result.MRGRP}" />
							<input type="hidden" name="seqnr" value="${result.SEQNR}" />
							<input type="hidden" name="mroty" value="${result.MROTY}" />
							<input type="hidden" name="mrobj" value="${result.MROBJ}" />
							<input type="hidden" name="motyp" value="${result.MOTYP}" />
							<input type="hidden" name="mobjd" value="${result.MOBJD}" />
							<input type="hidden" name="mrslt" value="${result.MRSLT}" />
							<input type="text" name="mstext" class="width_100" value="${result.MSTEXT}" disabled />
						</td>
						<td><textarea name="mdtail" class="width_100" rows="3" disabled>${result.MDTAIL}</textarea></td>
						<td class="text_center"><input type="radio" name="mrslt4_${status.index}" <c:if test="${result.MRSLT eq '5'}">checked</c:if> value="5"></td>
						<td class="text_center"><input type="radio" name="mrslt4_${status.index}" <c:if test="${result.MRSLT eq '4'}">checked</c:if> value="4"></td>
						<td class="text_center"><input type="radio" name="mrslt4_${status.index}" <c:if test="${result.MRSLT eq '3'}">checked</c:if> value="3"></td>
						<td class="text_center"><input type="radio" name="mrslt4_${status.index}" <c:if test="${result.MRSLT eq '2'}">checked</c:if> value="2"></td>
						<td class="text_center"><input type="radio" name="mrslt4_${status.index}" <c:if test="${result.MRSLT eq '1'}">checked</c:if> value="1"></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

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