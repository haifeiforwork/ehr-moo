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

		$.sumWeight = function() {
			var sum = 0,
				_weight;

			$('input[name=weight]').each(function() {
				_weight = $(this).val();
				if(_weight != '' && !isNaN(_weight)) sum += parseInt(_weight);
			});

			return sum;
		}

		$.fn.setLayer = function(data) {
			var _this = $(this),
				$layer_title_txt = $('#layer_title_txt'),
				$layer_button_txt = $('#layer_button_txt');

			_this.find('.btn_close')
				.unbind()
				.click(function(e) {
					e.preventDefault();
					_this.closeLayer();
				});

			$layer_title_txt.text(data.title);
			$layer_button_txt
					.text(data.button)
				.parent()
					.attr('title', data.button)
				.end();

			if(typeof data.func == 'function') {
				$layer_button_txt.parent()
						.unbind()
						.click(function(e) {
							e.preventDefault();
							data.func.call();
						})
					.next()
						.unbind()
						.click(function(e) {
							e.preventDefault();
							_this.closeLayer();
						})
					.end();
			}
		}

		$.fn.closeLayer = function() {
			$('[name=rjtext]').val('');
			$('.mask').hide();
			$(this).hide();
		}

		$.fn.showLayer = function() {
			var $this = $(this),
				$mask = $('.mask'),
				maskHeight = $(document).height(),
	        	maskWidth = $(window).width(),
	        	left = 250,
	        	top = 150;

	        $mask
	        	.css({'width':maskWidth,'height':maskHeight})
	        	.fadeIn(1000)
	        	.fadeTo("slow",0.8);

	        $this
	        	.css({'left':left,'top':top, 'position':'absolute'})
	        	.draggable()
	        	.show();
		}

		$.initView = function() {
			$.bindButtonBackHandler();
			$.bindButtonAgreementHandler();
			$.bindButtonRefuseHandler();
			$.bindButtonOrderHandler();

			$('#qualTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#quanTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('input[name=weight_sum]').val($.sumWeight());

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function() {
			$('#btnAgreement').unbind().hide();
			$('#btnRefuse').unbind().hide();

			$.DisabledBackgroundStyleChange();
		};

		$.beforSubmitRenameForModelAttribute = function() {
			$('textarea[name="atask"]').each(function(index) { $(this).attr("name", "goals[" + index + "].atask").prop("disabled", false); });
			$('textarea[name="dtail"]').each(function(index) { $(this).attr("name", "goals[" + index + "].dtail").prop("disabled", false); });
			$('textarea[name="trget"]').each(function(index) { $(this).attr("name", "goals[" + index + "].trget").prop("disabled", false); });
			$('input[name="weight"]').each(function(index) { $(this).attr("name", "goals[" + index + "].weight").prop("disabled", false); });
			$('input[name="gubun"]').each(function(index) { $(this).attr("name", "goals[" + index + "].gubun").prop("disabled", false); });
			$('input[name="numbr"]').each(function(index) { $(this).attr("name", "goals[" + index + "].numbr").prop("disabled", false); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=goals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
		}

		$.makeFormData = function(button) {
			var formData = $('#evaluationForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'ayear', value:'${params.AYEAR}'});
			formData.push({name:'aprse', value:'${params.APRSE}'});
			formData.push({name:'seqno', value:'${params.SEQNO}'});
			formData.push({name:'rjtext', value:$('[name=rjtext]').val()});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();
			$.beforSubmitRenameForModelAttribute();

			$.ajax({
				url : "<c:url value='/portal/evaluation/performance/objectiveAgreementProc.do'/>"
				, type : "post"
				, data : $.makeFormData(params.button)
				, success : function(result) {
					if(params.isCloseDialog) $("#refuseReasonDiv").closeLayer();

					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);

						$.afterSubmitRenameForView();
						$.stopProgress();
					} else {
						alert(params.sucessMsg);
						$.CallListPage({
							SUBMIT_ACTION : "<c:url value='/portal/evaluation/performance/objectiveList.do'/>",
							PARAM_ACTION : "OBJECTIVE_AGREEMENT",
							PARAM_PAGE_NUM : "${params.currentPage}"
						});
					}
				}
				, error : function(xhr, exMessage) {
					if(params.isCloseDialog) $("#refuseReasonDiv").closeLayer();

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
					PARAM_ACTION : "OBJECTIVE_AGREEMENT",
					PARAM_PAGE_NUM : "${params.currentPage}"
				});
			});
		}

		$.bindButtonAgreementHandler = function() {
			$('#btnAgreement').click(function(e) {
				e.preventDefault();

				if(confirm("합의하시겠습니까?")) {
					$.businessProcess({button : 'AGRE', isCloseDialog : false, sucessMsg : '합의하였습니다.'});
				}
			});
		}

		$.bindButtonRefuseHandler = function() {
			$('#btnRefuse').click(function(e) {
				e.preventDefault();

				if(confirm("반려하시겠습니까?")) {
					$("#refuseReasonDiv").setLayer({
						title:"반려내역",
						button:"반려",
						func:function() {
							$.businessProcess({button : 'REJT', isCloseDialog : true, sucessMsg : '반려하였습니다.'});
						}
					});
					$("#refuseReasonDiv").showLayer();
				}
			});
		}

		$.bindButtonOrderHandler = function() {
			$('#btnOrder').click(function(e) {
				e.preventDefault();

				if(confirm("목표수정지시 하시겠습니까?")) {
					$("#refuseReasonDiv").setLayer({
						title:"목표수정지시",
						button:"수정지시",
						func:function() {
							$.businessProcess({button : 'RGOL', isCloseDialog : true, sucessMsg : '목표수정지시 하였습니다.'});
						}
					});
					$("#refuseReasonDiv").showLayer();
				}
			});
		}

		$.initView();

		<c:if test="${params.ACHVSTS ge '03'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">업적평가 목표합의</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnAgreement" title="합의" href="#"><span>합의</span></a>
		<a class="btn_common" id="btnRefuse" title="반려" href="#"><span>반려</span></a>
		<c:if test="${export.EV_MODIF eq 'X'}">
			<a class="btn_common" id="btnOrder" title="목표수정지시" href="#"><span>목표수정지시</span></a>
		</c:if>
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

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/performance/objectiveAgreementProc.do'/>">
	<h2 class="title margintop_20">정량</h2>
	<table id="qualTable" class="contents_table">
		<colgroup>
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 10%;" />
		</colgroup>
		<thead>
			<tr>
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
						<td><textarea name="atask" disabled class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" disabled class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" disabled class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td class="text_center">
							<input type="text" name="weight" disabled style="width: 60px;text-align:center;" value="${result.WEIGHT}">
							<input type="hidden" name="gubun" value="${result.GUBUN}" />
							<input type="hidden" name="numbr" value="${result.NUMBR}" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">정성</h2>
	<table id="quanTable" class="contents_table">
		<colgroup>
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 10%;" />
		</colgroup>
		<thead>
			<tr>
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
						<td><textarea name="atask" disabled class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" disabled class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" disabled class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td class="text_center">
							<input type="text" name="weight" disabled style="width: 60px;text-align:center;" value="${result.WEIGHT}" />
							<input type="hidden" name="gubun" value="${result.GUBUN}" />
							<input type="hidden" name="numbr" value="${result.NUMBR}" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<table class="contents_table no_border">
		<colgroup>
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 30%;" />
			<col style="width: 10%;" />
		</colgroup>
		<tbody>
			<tr>
				<td colspan="3"></td>
				<td class="text_center"><input type="text" name="weight_sum" disabled style="width: 60px;text-align:center;"></td>
			</tr>
		</tbody>
	</table>
	</form>

	<form id="ajaxForm" name="ajaxForm" method="post"></form>

</div>

<div class="mask" style="position:absolute;left:0;top:0;z-index:9999;background-color:#000;display:none;"></div>
<div class="eva_popup" id="refuseReasonDiv" style="display:none;width:500px;background-color: #ffffff;z-index:99999;"> <!-- 팝업 띄울때 width:500px -->
	<div class="popup_header">
		<span class="title" id="layer_title_txt">반려내역</span>
		<a href="#" class="btn_close">닫기</a>
	</div>
	<div class="popup_contents">
		<textarea name="rjtext" class="width_100" rows="5"></textarea>
		<div class="btn_wrap right">
			<a class="btn_common" title="반려" href="#"><span id="layer_button_txt">반려</span></a>
			<a class="btn_common" title="닫기" href="#"><span>닫기</span></a>
		</div>
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