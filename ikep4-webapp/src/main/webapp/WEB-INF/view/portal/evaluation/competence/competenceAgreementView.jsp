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

		$.fn.setLayer = function(data) {
			var $this = $(this),
				$layer_title_txt = $('#layer_title_txt'),
				$layer_button_txt = $('#layer_button_txt');

			$this.find('.btn_close')
				.unbind()
				.click(function(e) {
					e.preventDefault();
					$this.closeLayer();
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
							$this.closeLayer();
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
			$('#commonTable > tbody > tr').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#stellTable > tbody > tr').each(function() { $(this).AutoHeightResizeTextarea(); });

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function() {
			$('#btnAgreement').unbind().hide();
			$('#btnRefuse').unbind().hide();

			$.DisabledBackgroundStyleChange();
		};

		$.beforSubmitRenameForModelAttribute = function() {
			$('[name="qitem"]').each(function(index) { $(this).attr("name", "goals[" + index + "].qitem"); });
			$('[name="stext"]').each(function(index) { $(this).attr("name", "goals[" + index + "].stext").prop('disabled', false); });
			$('[name="dftxt"]').each(function(index) { $(this).attr("name", "goals[" + index + "].dftxt").prop('disabled', false); });
			$('[name="bhidx"]').each(function(index) { $(this).attr("name", "goals[" + index + "].bhidx").prop('disabled', false); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=goals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
			$('[name="stext"]').prop('disabled', true);
			$('[name="dftxt"]').prop('disabled', true);
			$('[name="bhidx"]').prop('disabled', true);
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
				url : "<c:url value='/portal/evaluation/competence/competenceAgreementProc.do'/>"
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
							SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceList.do'/>",
							PARAM_ACTION : "COMPETENCE_AGREEMENT",
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

		$('#btnBack').click(function(e) {
			e.preventDefault();
			$.CallListPage({
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceList.do'/>",
				PARAM_ACTION : "COMPETENCE_AGREEMENT",
				PARAM_PAGE_NUM : "${params.currentPage}"
			});
		});

		$('#btnAgreement').click(function(e) {
			e.preventDefault();

			if(confirm("합의하시겠습니까?")) {
				$.businessProcess({button : 'AGRE', isCloseDialog : false, sucessMsg : '합의하였습니다.'});
			}
		});

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

		$('#btnOrder').click(function(e) {
			e.preventDefault();

			if(confirm("직무역량항목 수정지시 하시겠습니까?")) {
				$("#refuseReasonDiv").setLayer({
					title:"직무역량항목 수정지시",
					button:"수정지시",
					func:function() {
						$.businessProcess({button : 'RGOL', isCloseDialog : true, sucessMsg : '직무역량항목 수정지시 하였습니다.'});
					}
				});
				$("#refuseReasonDiv").showLayer();
			}
		});

		$.initView();

		<c:if test="${params.QUSTS ge '03'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">역량평가 직무역량 항목 합의</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnAgreement" title="합의" href="#"><span>합의</span></a>
		<a class="btn_common" id="btnRefuse" title="반려" href="#"><span>반려</span></a>
		<c:if test="${params.QUSTS eq '03'}">
			<a class="btn_common" id="btnOrder" title="목표수정지시" href="#"><span>직무역량항목 수정지시</span></a>
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
				<td class="text_left">${esHeader.APRSENM}(${esHeader.APRSE})</td>
				<th>직책</th>
				<td class="text_left">${esHeader.COTXT}</td>
				<th>직무</th>
				<td class="text_left">${esHeader.STLTX}</td>
			</tr>
			<tr>
				<th>1차평가자</th>
				<td class="text_left">${esHeader.APRSRNM1}</td>
				<th>2차평가자</th>
				<td class="text_left">${esHeader.APRSRNM2}</td>
				<th>Reviewer</th>
				<td class="text_left">${esHeader.REVWRNM}</td>
			</tr>
		</tbody>
	</table>

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/competence/competenceAgreementProc.do'/>">
	<h2 class="title">공통역량</h2>
	<table id="commonTable" class="contents_table">
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
	<table id="stellTable" class="contents_table">
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
			<c:if test="${not empty stellList}">
				<c:forEach var="result" items="${stellList}">
					<tr>
						<td valign="top">
							<input type="hidden" name="qitem" value="${result.QITEM}" />
							<input type="text" name="stext" class="width_100" disabled value="${result.STEXT}" />
						</td>
						<td><textarea name="dftxt" class="width_100" rows="3" disabled><c:out value="${result.DFTXT}" /></textarea></td>
						<td><textarea name="bhidx" class="width_100" rows="3" disabled><c:out value="${result.BHIDX}" /></textarea></td>
					</tr>
				</c:forEach>
			</c:if>
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