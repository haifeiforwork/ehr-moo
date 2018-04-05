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

var qualNumbrArr = new Array();
var quanNumbrArr = new Array();
var delGoalArr = new Array();

(function($){
	$(document).ready(function() {

		<c:if test="${not empty qualList}">
			<c:forEach var="result" items="${qualList}">
			qualNumbrArr.push(${result.NUMBR});
			</c:forEach>
		</c:if>
		<c:if test="${not empty quanList}">
			<c:forEach var="result" items="${quanList}">
			quanNumbrArr.push(${result.NUMBR});
			</c:forEach>
		</c:if>

		$.validator.setDefaults({
		    onkeyup:false,
		    onclick:false,
		    onfocusout:false,
		    showErrors:function(errorMap, errorList){
		        if(this.numberOfInvalids()) {
		            alert(errorList[0].message);
		        }
		    }
		});

		validator = $("#evaluationForm").validate();

		$.getNextNumbr = function(arr) {
			var max = 0;

			if(arr.length > 0) max = Math.max.apply(null, arr);
			arr.push(++max);

			return max;
		}

		$.sumWeight = function() {
			var sum = 0;

			$('input[name=weight]').each(function() {
				var _weight = $(this).val();
				if(_weight != '' && !isNaN(_weight)) sum += parseInt(_weight);
			});

			return sum;
		}

		$.fn.deleteLine = function() {
			if($(this).find('[name=numbr]').val()) {
				var xgubun = $(this).find('[name=gubun]').val();
				var xnumbr = $(this).find('[name=numbr]').val();

				delGoalArr.push({gubun: xgubun, numbr: xnumbr});
			}

			$(this).remove();
		};

		$.fn.bindWeightSum = function() {
			this.keyup(function() {
				$(this).val($(this).val().replace(/[^0-9]/g,""));
				$('input[name=weight_sum]').val($.sumWeight());
			});
		};

		$.fn.addLine = function(gubun) {
			$('#rowTemplate').tmpl({gubun:gubun}).appendTo(this);
			this.find('input[name=weight]:last').bindWeightSum();
			this.find('textarea[name=atask]:last').AutoHeightResizeTextarea();
			this.find('textarea[name=dtail]:last').AutoHeightResizeTextarea();
			this.find('textarea[name=trget]:last').AutoHeightResizeTextarea();
		};

		$.initView = function() {
			$('#btnPrint').hide();

			<c:if test="${params.ACHVSTS ge '03'}">
			$('#btnPrint').show();
			$.bindButtonPrintHandler();
			</c:if>

			var qualTrSize = $('#qualTable > tbody > tr').size();
			if(qualTrSize == 0) {
				$('#qualTable').addLine(1);
				$('#qualTable').addLine(1);
			}
			var quanTrSize = $('#quanTable > tbody > tr').size();
			if(quanTrSize == 0) {
				$('#quanTable').addLine(2);
				$('#quanTable').addLine(2);
			}

			$('#qualTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#quanTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('input[name=weight_sum]').val($.sumWeight());

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function() {
			$('textarea').each(function() { $(this).prop("disabled", true); });
			$('input[name=weight]').each(function() { $(this).prop("disabled", true); });
			$('[name=delCheck]').each(function() { $(this).prop("disabled", true); });

			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();
			$('#btnQualAdd').unbind().hide();
			$('#btnQualDel').unbind().hide();
			$('#btnQuanAdd').unbind().hide();
			$('#btnQuanDel').unbind().hide();

			$.DisabledBackgroundStyleChange();
		};

		$.removeNanModelRow = function() {
			var isEmpty;
			$('#qualTable > tbody > tr').each(function() {
				var $this = $(this);
				isEmpty = true;

				$this.find('textarea').each(function() {
					if($(this).val()) isEmpty = false;
				});
				if($this.find('input[name="weight"]').val()) isEmpty = false;

				if(isEmpty) $this.remove();
			});

			$('#quanTable > tbody > tr').each(function() {
				var $this = $(this);
				isEmpty = true;

				$this.find('textarea').each(function() {
					if($(this).val()) isEmpty = false;
				});
				if($this.find('input[name="weight"]').val()) isEmpty = false;

				if(isEmpty) $this.remove();
			});
		}

		$.isNanModelRow = function() {
			var isEmpty = false;

			$('#qualTable > tbody > tr').each(function() {
				$(this).find('textarea').each(function() { if(!$(this).val()) isEmpty = true; });
				if(!$(this).find('input[name="weight"]').val()) isEmpty = true;
			});

			$('#quanTable > tbody > tr').each(function() {
				$(this).find('textarea').each(function() { if(!$(this).val()) isEmpty = true; });
				if(!$(this).find('input[name="weight"]').val()) isEmpty = true;
			});

			return isEmpty;
		}

		$.checkWeightRange = function() {
			var isValid = true,
				_weight;

			$('input[name=weight]').each(function() {
				_weight = $(this).val();
				if(parseInt(_weight) < 10 || parseInt(_weight) > 50) isValid = false;
			});

			return isValid;
		}

		$.isEachTableExist = function() {
			var isExist = true;

			if($('#qualTable > tbody > tr').size() == 0) isExist = false;
			if($('#quanTable > tbody > tr').size() == 0) isExist = false;

			return isExist;
		}

		$.checkRowRange = function() {
			var isValid = true,
				totRow = $('#qualTable > tbody > tr').size() + $('#quanTable > tbody > tr').size();

			if(totRow < 3 || totRow > 6) isValid = false;

			return isValid;
		}

		$.setModelNumbr = function() {
			$('#qualTable > tbody > tr').each(function(index) {
				if(!$(this).find('input[name="numbr"]').val()) {
					$(this).find('input[name="numbr"]').val($.getNextNumbr(qualNumbrArr));
				}
			});

			$('#quanTable > tbody > tr').each(function(index) {
				if(!$(this).find('input[name="numbr"]').val()) {
					$(this).find('input[name="numbr"]').val($.getNextNumbr(quanNumbrArr));
				}
			});
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('textarea[name="atask"]').each(function(index) { $(this).attr("name", "goals[" + index + "].atask"); });
			$('textarea[name="dtail"]').each(function(index) { $(this).attr("name", "goals[" + index + "].dtail"); });
			$('textarea[name="trget"]').each(function(index) { $(this).attr("name", "goals[" + index + "].trget"); });
			$('input[name="weight"]').each(function(index) { $(this).attr("name", "goals[" + index + "].weight"); });
			$('input[name="gubun"]').each(function(index) { $(this).attr("name", "goals[" + index + "].gubun"); });
			$('input[name="numbr"]').each(function(index) { $(this).attr("name", "goals[" + index + "].numbr"); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=goals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
		}

		$.makeFormData = function(button) {
			var formData = $('#evaluationForm').serializeArray();

			formData.push({name:'button', value:button});
			formData.push({name:'ayear', value:'${params.AYEAR}'});
			formData.push({name:'aprse', value:'${params.APRSE}'});
			formData.push({name:'seqno', value:'${params.SEQNO}'});
			$.each(delGoalArr, function(index, value) {
				formData.push({name:'xgoals['+index+'].gubun', value:value.gubun});
				formData.push({name:'xgoals['+index+'].numbr', value:value.numbr});
			});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/performance/objectiveSettingProc.do'/>"
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
								PARAM_ACTION : "OBJECTIVE_SETTING",
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
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/performance/objectiveList.do'/>",
				PARAM_ACTION : "OBJECTIVE_SETTING",
				PARAM_PAGE_NUM : "${params.currentPage}"
			});
		});

		$('#btnSave').click(function(e) {
			e.preventDefault();

			$.removeNanModelRow();
			var modelSize = $('#qualTable > tbody > tr').size() + $('#quanTable > tbody > tr').size();
			if(modelSize == 0) {
				$.initView();
				alert("저장할 내용이 없습니다.");
				return false;
			}

			$.setModelNumbr();
			$.beforSubmitRenameForModelAttribute();

			$('[name*=".weight"]').each(function() { $(this).rules("add", {digits: true, messages: {digits: '가중치는 숫자만 입력 가능합니다.'}}) });
			if(!$("#evaluationForm").valid()) {
				validator.focusInvalid();
				return false;
			}

			if(confirm("저장하시겠습니까?")) {
				$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false});
			} else {
				$.afterSubmitRenameForView();
			}
		});

		$('#btnComplete').click(function(e) {
			e.preventDefault();

			if($.isNanModelRow()) {
				alert("모든 Column의 내역을 작성하십시오.");
				return false;
			}

			if(!$.checkWeightRange()) {
				alert("개별 항목별 가중치는 10이상 50이하여야 합니다.");
				return false;
			}

			if(!$.isEachTableExist()) {
				alert("정량/정성 각 영역별 항목이 반드시 존재하여야 합니다.");
				return false;
			}

			if(!$.checkRowRange()) {
				alert("항목수는 3개이상 6개 이하여야 합니다.");
				return false;
			}

			if($.sumWeight() !== 100) {
				alert("가중치 합은 100 입니다");
				return false;
			}

			$.setModelNumbr();
			$.beforSubmitRenameForModelAttribute();

			if(confirm("목표수립이 완료되어 합의 진행됩니다.\n계속하시겠습니까?")) {
				$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
			} else {
				$.afterSubmitRenameForView();
			}
		});

		$.bindButtonPrintHandler = function() {
			$('#btnPrint').click(function(e) {
				e.preventDefault();

				var $inputElem = $('<input type="hidden" />'),
					$printForm = $('#ajaxForm');

				$printForm
					.empty()
					.append($inputElem.clone().attr('name', 'AYEAR').val('${params.AYEAR}'))
					.append($inputElem.clone().attr('name', 'SEQNO').val('${params.SEQNO}'))
					.append($inputElem.clone().attr('name', 'APRSE').val('${params.APRSE}'))
					.append($inputElem.clone().attr('name', 'APRSENM').val('${params.APRSENM}'))
					.append($inputElem.clone().attr('name', 'TRFGR').val('${params.TRFGR}'))
					.append($inputElem.clone().attr('name', 'ZZJIK').val('${params.ZZJIK}'))
					.append($inputElem.clone().attr('name', 'COTXT').val('${params.COTXT}'))
					.append($inputElem.clone().attr('name', 'APRSR1').val('${params.APRSR1}'))
					.append($inputElem.clone().attr('name', 'APRSRNM1').val('${params.APRSRNM1}'))
					.append($inputElem.clone().attr('name', 'AQSTS1').val('${params.AQSTS1}'))
					.append($inputElem.clone().attr('name', 'AQSTSX1').val('${params.AQSTSX1}'))
					.append($inputElem.clone().attr('name', 'APRSR2').val('${params.APRSR2}'))
					.append($inputElem.clone().attr('name', 'APRSRNM2').val('${params.APRSRNM2}'))
					.append($inputElem.clone().attr('name', 'AQSTS2').val('${params.AQSTS2}'))
					.append($inputElem.clone().attr('name', 'AQSTSX2').val('${params.AQSTSX2}'))
					.append($inputElem.clone().attr('name', 'REVWR').val('${params.REVWR}'))
					.append($inputElem.clone().attr('name', 'REVWRNM').val('${params.REVWRNM}'))
					.append($inputElem.clone().attr('name', 'ACHVSTS').val('${params.ACHVSTS}'))
					.append($inputElem.clone().attr('name', 'ACHVSTSX').val('${params.ACHVSTSX}'))
					.append($inputElem.clone().attr('name', 'ORGTX').val('${params.ORGTX}'))
					.append($inputElem.clone().attr('name', 'STELL').val('${params.STELL}'))
					.append($inputElem.clone().attr('name', 'STLTX').val('${params.STLTX}'))
					.append($inputElem.clone().attr('name', 'APSTS').val('${params.APSTS}'))
					.append($inputElem.clone().attr('name', 'APSTSX').val('${params.APSTSX}'))
					.attr("method", "post")
					.attr("action", "<c:url value='/portal/evaluation/performance/objectiveSettingViewPrint.do'/>");

				$.CallPrintPagePopup();

				$printForm.empty();
			});
		}

		$('#btnQualAdd').click(function(e) {
			e.preventDefault();

			$('#qualTable').addLine(1);
		});

		$('#btnQualDel').click(function(e) {
			e.preventDefault();

			var delLineArr = new Array();

			$('#qualTable > tbody > tr').each(function(index) {
				if($(this).find('[name=delCheck]').is(':checked')) $(this).deleteLine();
			});

			$('input[name=weight_sum]').val($.sumWeight());
		});

		$('#btnQuanAdd').click(function(e) {
			e.preventDefault();

			$('#quanTable').addLine(2);
		});

		$('#btnQuanDel').click(function(e) {
			e.preventDefault();

			var delLineArr = new Array();

			$('#quanTable > tbody > tr').each(function(index) {
				if($(this).find('[name=delCheck]').is(':checked')) $(this).deleteLine();
			});

			$('input[name=weight_sum]').val($.sumWeight());
		});

		$('input[name=weight]').bindWeightSum();

		$.initView();

		<c:if test="${params.ACHVSTS ge '02'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>
<script id="rowTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td class="text_center"><input type="checkbox" name="delCheck"></td>
		<td><textarea name="atask" class="width_100" rows="5"></textarea></td>
		<td><textarea name="dtail" class="width_100" rows="5"></textarea></td>
		<td><textarea name="trget" class="width_100" rows="5"></textarea></td>
		<td class="text_center"><input type="text" name="weight" style="width: 60px;text-align:center;"></td>
		<input type="hidden" name="gubun" value="\${gubun}" />
		<input type="hidden" name="numbr" value="" />
	</tr>
</script>

<div id="wrap">

	<h1 class="title">업적평가 목표수립</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="합의요청" href="#"><span>합의요청</span></a>
		<div class="float_right">
			<a class="btn_common" id="btnPrint" title="평가표 출력" href="#"><span>평가표 출력</span></a>
		</div>
	</div>

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/performance/objectiveSettingProc.do'/>">
	<h2 class="title">
		정량
		<a class="btn_add" id="btnQualAdd">추가</a>
		<a class="btn_delete" id="btnQualDel">삭제</a>
	</h2>
	<table class="contents_table" id="qualTable">
		<colgroup>
			<col style="width: 4%;" />
			<col style="width: 29%;" />
			<col style="width: 29%;" />
			<col style="width: 29%;" />
			<col style="width: 9%;" />
		</colgroup>
		<thead>
			<tr>
				<th></th>
				<th>실행계획/담당업무</th>
				<th>세부내용/산식</th>
				<th>목표</th>
				<th>가중치(%)</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty qualList}">
				<c:forEach var="result" items="${qualList}">
					<tr>
						<td class="text_center"><input type="checkbox" name="delCheck"></td>
						<td><textarea name="atask" class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td class="text_center"><input type="text" name="weight" style="width: 60px;text-align:center;" value="${result.WEIGHT}" /></td>
						<input type="hidden" name="gubun" value="${result.GUBUN}" />
						<input type="hidden" name="numbr" value="${result.NUMBR}" />
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">
		정성
		<a class="btn_add" id="btnQuanAdd">추가</a>
		<a class="btn_delete" id="btnQuanDel">삭제</a>
	</h2>
	<table class="contents_table" id="quanTable">
		<colgroup>
			<col style="width: 4%;" />
			<col style="width: 29%;" />
			<col style="width: 29%;" />
			<col style="width: 29%;" />
			<col style="width: 9%;" />
		</colgroup>
		<thead>
			<tr>
				<th></th>
				<th>실행계획/담당업무</th>
				<th>세부내용/산식</th>
				<th>목표</th>
				<th>가중치(%)</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty quanList}">
				<c:forEach var="result" items="${quanList}">
					<tr>
						<td class="text_center"><input type="checkbox" name="delCheck"></td>
						<td><textarea name="atask" class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td class="text_center"><input type="text" name="weight" style="width: 60px;text-align:center;" value="${result.WEIGHT}" /></td>
						<input type="hidden" name="gubun" value="${result.GUBUN}" />
						<input type="hidden" name="numbr" value="${result.NUMBR}" />
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<table class="contents_table no_border">
		<colgroup>
			<col style="width: 4%;" />
			<col style="width: 29%;" />
			<col style="width: 29%;" />
			<col style="width: 29%;" />
			<col style="width: 9%;" />
		</colgroup>
		<tbody>
			<tr>
				<td colspan="4"></td>
				<td class="text_center"><input type="text" name="weight_sum" style="width: 60px;text-align:center;" disabled>
				</td>
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