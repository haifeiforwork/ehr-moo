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
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/promotion/promotionList.do'/>",
					PARAM_ACTION : "GOAL_SETTING_LIST",
					PARAM_PAGE_NUM : "${params.currentPage}"
				});
			});
		}

		$.bindButtonSaveHandler = function() {
			$('#btnSave').click(function(e) {
				e.preventDefault();

				$.beforSubmitRenameForModelAttribute();

				if(confirm("저장하시겠습니까?")) {
					$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false});
				} else {
					$.afterSubmitRenameForView();
				}
			});
		}

		$.bindButtonCompleteHandler = function() {
			$('#btnComplete').click(function(e) {
				e.preventDefault();

				if(!$.isAllWriteItem()) {
					alert("모든 항목에 대해 작성하셔야 합니다.");
					return false;
				}

				$.beforSubmitRenameForModelAttribute();

				if(confirm("완료하시겠습니까?")) {
					$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
				} else {
					$.afterSubmitRenameForView();
				}
			});
		}

		$.bindButtonPrintHandler = function() {
			$('#btnPrint').click(function(e) {
				e.preventDefault();

				var $inputElem = $('<input type="hidden" />');
				var $printForm = $('#ajaxForm');

				$printForm.empty();
				$printForm.append($inputElem.clone().attr('name', 'AYEAR').val('${params.AYEAR}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGEH').val('${params.ORGEH}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGTX').val('${params.ORGTX}'));
				$printForm.append($inputElem.clone().attr('name', 'RSPER').val('${params.RSPER}'));
				$printForm.append($inputElem.clone().attr('name', 'RSPERNM').val('${params.RSPERNM}'));
				$printForm.append($inputElem.clone().attr('name', 'PERSG').val('${params.PERSG}'));
				$printForm.append($inputElem.clone().attr('name', 'PERSK').val('${params.PERSK}'));
				$printForm.append($inputElem.clone().attr('name', 'STELL').val('${params.STELL}'));
				$printForm.append($inputElem.clone().attr('name', 'STLTX').val('${params.STLTX}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK').val('${params.ZZJIK}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT').val('${params.COTXT}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR').val('${params.TRFGR}'));
				$printForm.append($inputElem.clone().attr('name', 'RTRFGR').val('${params.RTRFGR}'));
				$printForm.append($inputElem.clone().attr('name', 'RSDAT').val('${params.RSDAT}'));
				$printForm.append($inputElem.clone().attr('name', 'KTCDAT').val('${params.KTCDAT}'));
				$printForm.append($inputElem.clone().attr('name', 'KPTERM').val('${params.KPTERM}'));
				$printForm.append($inputElem.clone().attr('name', 'KPYEAR').val('${params.KPYEAR}'));
				$printForm.append($inputElem.clone().attr('name', 'RSSTS').val('${params.RSSTS}'));
				$printForm.append($inputElem.clone().attr('name', 'RSSTSX').val('${params.RSSTSX}'));
				$printForm.append($inputElem.clone().attr('name', 'RSSTSDL').val('${params.RSSTSDL}'));
				$printForm.append($inputElem.clone().attr('name', 'BIGO').val('${params.BIGO}'));
				$printForm.append($inputElem.clone().attr('name', 'SRTKY').val('${params.SRTKY}'));
				$printForm.append($inputElem.clone().attr('name', 'MODIF').val('${params.MODIF}'));

				$printForm.attr("method", "post");
				$printForm.attr("action", "<c:url value='/portal/evaluation/promotion/goalSettingViewPrint.do'/>");

				$.CallPrintPagePopup();

				$printForm.empty();
			});
		}

		$.initView = function() {
			$.bindButtonBackHandler();
			$.bindButtonSaveHandler();
			$.bindButtonCompleteHandler();
			$.bindButtonPrintHandler();

			$('#wrap').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
		};

		$.viewMode = function(status) {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();

			$.toggleDisabledAllComponent(true);

			$.DisabledBackgroundStyleChange();
		};

		$.beforSubmitRenameForModelAttribute = function() {
			$('[name="chaprnm"]').each(function(index) { $(this).attr("name", "subjects[" + index + "].chaprnm"); });
			$('[name="chaprd1"]').each(function(index) { $(this).attr("name", "subjects[" + index + "].chaprd1"); });
			$('[name="chaprd2"]').each(function(index) { $(this).attr("name", "subjects[" + index + "].chaprd2"); });
			$('[name="chaprd3"]').each(function(index) { $(this).attr("name", "subjects[" + index + "].chaprd3"); });
			$('[name="chaprd4"]').each(function(index) { $(this).attr("name", "subjects[" + index + "].chaprd4"); });
			$('[name="chaprd5"]').each(function(index) { $(this).attr("name", "subjects[" + index + "].chaprd5"); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=subjects]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
		}

		$.makeFormData = function(button) {
			var formData = $('#promotionForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'ayear', value:'${rslst.AYEAR}'});
			formData.push({name:'orgeh', value:'${rslst.ORGEH}'});
			formData.push({name:'orgtx', value:'${rslst.ORGTX}'});
			formData.push({name:'rsper', value:'${rslst.RSPER}'});
			formData.push({name:'rspernm', value:'${rslst.RSPERNM}'});
			formData.push({name:'persg', value:'${rslst.PERSG}'});
			formData.push({name:'persk', value:'${rslst.PERSK}'});
			formData.push({name:'stell', value:'${rslst.STELL}'});
			formData.push({name:'stltx', value:'${rslst.STLTX}'});
			formData.push({name:'zzjik', value:'${rslst.ZZJIK}'});
			formData.push({name:'cotxt', value:'${rslst.COTXT}'});
			formData.push({name:'trfgr', value:'${rslst.TRFGR}'});
			formData.push({name:'rtrfgr', value:'${rslst.RTRFGR}'});
			formData.push({name:'rsdat', value:'${rslst.RSDAT}'});
			formData.push({name:'ktcdat', value:'${rslst.KTCDAT}'});
			formData.push({name:'kpterm', value:'${rslst.KPTERM}'});
			formData.push({name:'kpyear', value:'${rslst.KPYEAR}'});
			formData.push({name:'rssts', value:'${rslst.RSSTS}'});
			formData.push({name:'rsstsx', value:'${rslst.RSSTSX}'});
			formData.push({name:'rsstsdl', value:'${rslst.RSSTSDL}'});
			formData.push({name:'bigo', value:'${rslst.BIGO}'});
			formData.push({name:'srtky', value:'${rslst.SRTKY}'});
			formData.push({name:'modif', value:'${rslst.MODIF}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/promotion/goalSettingProc.do'/>"
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
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/promotion/promotionList.do'/>",
								PARAM_ACTION : "GOAL_SETTING_LIST",
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

		$.isAllWriteItem = function() {
			var isAllWrite = true;
			if($('[name=chaprnm]').val() == '') isAllWrite = false;
			if($('[name=chaprd1]').val() == '') isAllWrite = false;
			if($('[name=chaprd2]').val() == '') isAllWrite = false;
			if($('[name=chaprd3]').val() == '') isAllWrite = false;
			if($('[name=chaprd4]').val() == '') isAllWrite = false;
			if($('[name=chaprd5]').val() == '') isAllWrite = false;

			return isAllWrite;
		}

		$.toggleDisabledAllComponent = function(isDisabled) {
			$('#wrap').find('input, textarea').each(function() { $(this).prop('disabled', isDisabled); });
		}

		$.initView();

		<c:if test="${params.RSSTS eq '2'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">승격 도전과제 등록</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="제출" href="#"><span>제출</span></a>
		<a class="btn_common" id="btnPrint" title="출력" href=""><span>출력</span></a>
	</div>

	<hr />

	<form id="promotionForm" name="promotionForm" method="post" action="<c:url value='/portal/evaluation/promotion/goalSettingProc.do'/>">

	<h2 class="title margintop_20">과제명칭</h2>
	<table class="contents_table">
		<colgroup>
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<td><input type="text" name="chaprnm" class="width_100" value="${goalList[0].CHAPRNM}" /></td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20">과제선정이유</h2>
	<table class="contents_table">
		<colgroup>
			<col style="width: 25%;">
			<col style="width: 75%;">
		</colgroup>
		<tbody>
			<tr>
				<th>현상의 문제점</th>
				<td><textarea name="chaprd1" class="width_100" rows="4"><c:out value="${goalList[0].CHAPRD1}" /></textarea></td>
			</tr>
			<tr>
				<th>경쟁기업과의 차이</th>
				<td><textarea name="chaprd2" class="width_100" rows="4"><c:out value="${goalList[0].CHAPRD2}" /></textarea></td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20">과제수행방법</h2>
	<table class="contents_table">
		<colgroup>
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<td><textarea name="chaprd3" class="width_100" rows="4"><c:out value="${goalList[0].CHAPRD3}" /></textarea></td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20">과제수행의 기대결과</h2>
	<table class="contents_table">
		<colgroup>
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<td><textarea name="chaprd4" class="width_100" rows="4"><c:out value="${goalList[0].CHAPRD4}" /></textarea></td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20">과제수행결과의 효과</h2>
	<table class="contents_table">
		<colgroup>
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<td><textarea name="chaprd5" class="width_100" rows="4"><c:out value="${goalList[0].CHAPRD5}" /></textarea></td>
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