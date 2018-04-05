<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<link href="<c:url value="/base/css/fixed_table_rc.css"/>" type="text/css" rel="stylesheet" media="all" />

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/fixed_table_rc.js"></script>

<style>
textarea {
	overflow-y : hidden;
}
</style>

<script type="text/javascript">
(function($){
	$(document).ready(function() {
		var gradescores = {S : 5, A : 4, B : 3, C : 2, D : 1}
		var referencevalues = {};
		<c:if test="${not empty scoreList}">
			<c:forEach var="score" items="${scoreList}">
				referencevalues['${score.SEMID}'] = ${score.GSVAL};
			</c:forEach>
		</c:if>

		$.fn.calcScore = function() {
			var $tr = $(this).closest('tr'),
				weight = $tr.find('[name=weight]').val(),
				scoreResult = 0,
				scoreResult2 = 0,
				grade,
				grade2,
				gradeScore,
				gradeScore2,
				refValue;

			$tr.find('select').each(function() {
				grade = $(this).val();
				grade2 = $(this).next().val();
				if(grade != '') {
					gradeScore = gradescores[grade];
					gradeScore2 = gradescores[grade2];
					refValue = referencevalues[$(this).data('semid')];

					scoreResult += (parseFloat(weight)/100) * gradeScore * (refValue/100) * 20;
					if(gradeScore2 != '' && !isNaN(gradeScore2)) {
						scoreResult2 += (parseFloat(weight)/100) * gradeScore2 * (refValue/100) * 20;
					}
				}
			});

			scoreResult = $.ScoreRound(scoreResult, 1);
			scoreResult2 = $.ScoreRound(scoreResult2, 1);

			$tr.find('[name=apsco2]').val(scoreResult);
			$tr.find('[name=apsco1]').val(scoreResult2);
			$tr.find('[name=ptsum]').val($.ScoreRound(parseFloat(scoreResult) + parseFloat(scoreResult2), 1));
		}

		$.fn.hiddenDivScroll = function(tableHeight) {
			var $this = $(this),
				$clone = $this.clone(true),
				$relContainer = $this.parent(),
				$container = $relContainer.parent(),
				$cwrapper = $relContainer.find('.ft_cwrapper'),
				$hiddenScrollDiv = $('<div></div>'),
				_pad20Height = tableHeight + 20,
				_pad04Height = tableHeight + 4;

			$hiddenScrollDiv.append($clone).css('height', _pad04Height).css('overflow', 'hidden');
			$this.remove();
			$relContainer.append($hiddenScrollDiv);
			$clone.css('height', _pad20Height);
			$cwrapper.css('height', _pad20Height);
			$container.css('height', _pad20Height);
		}

		$.initFixedTable = function() {
			var $qualTable = $('#qualTable'),
				$quanTable = $('#quanTable'),
				_pad18 = 18,
				_tableClass = $qualTable.attr('class'),
				_colModal = [ { width: 200, align: 'center' },
						      { width: 200, align: 'center' },
						      { width: 200, align: 'center' },
						      { width: 70, align: 'center' },
						      { width: 200, align: 'center' },
						      { width: 200, align: 'center' },
						      { width: 200, align: 'center' },
						      { width: 70, align: 'center' },
						      { width: 70, align: 'center' },
						      { width: 70, align: 'center' },
						      { width: 70, align: 'center' } ];

			$qualTable.fxdHdrCol({ fixedCols: 4, width: "100%", height: 280, colModal: _colModal, sort: false });
			$quanTable.fxdHdrCol({ fixedCols: 4, width: "100%", height: 280, colModal: _colModal, sort: false });

			var $ft_c = $('.ft_c'),
				$ft_rc = $('.ft_rc'),
				$ft_r = $('.ft_r'),
				$ft_rwrapper = $('.ft_rwrapper'),
				$ft_scroller = $('.ft_scroller'),
				$ft_container = $('.ft_container'),
				$viewScroll = $('#viewScroll');

			//복사된 object name 변경
			$ft_c.each(function() {
				$(this).find('textarea, input, select').each(function() { $(this).attr("name", "hidden_temp"); });
			});

			//테이블 style 적용 - 미세조정
			$ft_rc.addClass(_tableClass);
			$ft_rc.each(function(index) {
				var changeWidth = $(this).width() + 4;
				var changeHeight = $(this).height() + 1;
				if(index == 1)  changeHeight = changeHeight + 2;
				$(this).css('width', changeWidth).css('height', changeHeight);
			});
			$ft_c.addClass(_tableClass);
			$ft_c.each(function(index) {
				var changeWidth = $(this).width() + 3;
				var changeHeight = $(this).height() + 1;
				if(index == 1)  changeHeight = changeHeight + 2;
				$(this).css('width', changeWidth).css('height', changeHeight).parent().css('width', changeWidth);
			});
			$ft_r.addClass(_tableClass);
			$ft_rwrapper.css('width', $ft_rwrapper.parent().outerWidth() + _pad18);

			//개별영역 스크롤 숨기기 및 height 확장
			$ft_scroller.eq(0).hiddenDivScroll($qualTable.height());
			$ft_scroller.eq(1).hiddenDivScroll($quanTable.height());

			//가로스크롤 동기화
			$viewScroll.css('width', $ft_container.eq(0).outerWidth()).css('height', '49px').css('overflow-x', 'scroll');
			$viewScroll.find('table').css('width', $qualTable.outerWidth());
			$viewScroll.scroll(function() {
				$('.ft_scroller').scrollLeft($(this).scrollLeft());
			});

			//정량목표 테이블 조정
			$ft_r.eq(0).find('tr').eq(0).find('th').eq(7).prop('colsapn', '3').css('width', '210px').next().hide().next().hide();
			$('#qualTable').find('thead').find('tr').eq(0).find('th').eq(7).prop('colsapn', '3').css('width', '210px').next().hide().next().hide();
			$('#qualTable').find('tbody > tr').each(function() {
				$(this).find('td').eq(7).prop('colsapn', '3').next().hide().next().hide();
			});
		}

		$.initView = function() {
			$('#qualTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#quanTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			<c:if test="${params.APSTS lt '2'}">
				$('#wrap').find('select').each(function() {
					$(this).change(function() {
						$(this).calcScore();
						$('[name=evalu_score_sum]').val($.SumScore('apsco2'));
					});
				});

				$('[name=seopa]').each(function() { $(this).calcScore() });
			</c:if>

			$('[name=evalu_score_sum]').val($.SumScore('apsco2'));

			$('#wrap').find('select:disabled').each(function() { $(this).SelectboxToInput(); });

			$.DisabledBackgroundStyleChange();

			$.initFixedTable();

			$.expandTextareaToTr();
		}

		$.expandTextareaToTr = function() {
			$('textarea', '#wrap').each(function() {
				$(this).css('height', $(this).closest('td').height() - 5);
			});
		}

		$.viewMode = function() {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();
			$('[name=seopa]').each(function() { $(this).prop("disabled", true) });
			$('[name=seilv]').each(function() { $(this).prop("disabled", true) });
			$('[name=setqt]').each(function() { $(this).prop("disabled", true) });
			$('#wrap').find('select:disabled').each(function() {
				var componentName = $(this).attr('name');
				$(this).SelectboxToInput();
				$('[name="' + componentName + '"]').css('text-align', 'center');
			});

			$.DisabledBackgroundStyleChange();
		};

		$.isNanModelRow = function() {
			var isEmpty = false;

			$('[name=seopa]').each(function() { if($(this).val() == '') isEmpty = true; });
			$('[name=seilv]', '#quanTable').each(function() { if($(this).val() == '') isEmpty = true; });
			$('[name=setqt]', '#quanTable').each(function() { if($(this).val() == '') isEmpty = true; });

			return isEmpty;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('[name="atask"]').each(function(index) { $(this).attr("name", "goals[" + index + "].atask").prop("disabled", false); });
			$('[name="dtail"]').each(function(index) { $(this).attr("name", "goals[" + index + "].dtail").prop("disabled", false); });
			$('[name="trget"]').each(function(index) { $(this).attr("name", "goals[" + index + "].trget").prop("disabled", false); });
			$('[name="weight"]').each(function(index) { $(this).attr("name", "goals[" + index + "].weight").prop("disabled", false); });
			$('[name="gubun"]').each(function(index) { $(this).attr("name", "goals[" + index + "].gubun").prop("disabled", false); });
			$('[name="numbr"]').each(function(index) { $(this).attr("name", "goals[" + index + "].numbr").prop("disabled", false); });
			$('[name="mdchk"]').each(function(index) { $(this).attr("name", "goals[" + index + "].mdchk").prop("disabled", false); });
			$('[name="mcfbk"]').each(function(index) { $(this).attr("name", "goals[" + index + "].mcfbk").prop("disabled", false); });
			$('[name="outpt"]').each(function(index) { $(this).attr("name", "goals[" + index + "].outpt").prop("disabled", false); });
			$('[name="seopa"]').each(function(index) { $(this).attr("name", "goals[" + index + "].seopa"); });
			$('[name="seilv"]').each(function(index) { $(this).attr("name", "goals[" + index + "].seilv"); });
			$('[name="setqt"]').each(function(index) { $(this).attr("name", "goals[" + index + "].setqt"); });
			$('[name="fsoac"]').each(function(index) { $(this).attr("name", "goals[" + index + "].fsoac"); });
			$('[name="fsilv"]').each(function(index) { $(this).attr("name", "goals[" + index + "].fsilv"); });
			$('[name="fstqt"]').each(function(index) { $(this).attr("name", "goals[" + index + "].fstqt"); });
			$('[name="apsco2"]').each(function(index) { $(this).attr("name", "goals[" + index + "].apsco2").prop("disabled", false); });
			$('[name="apsco1"]').each(function(index) { $(this).attr("name", "goals[" + index + "].apsco1"); });
			$('[name="ptsum"]').each(function(index) { $(this).attr("name", "goals[" + index + "].ptsum"); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=goals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]).prop("disabled", true); });
			$('[name="seopa"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="seilv"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="setqt"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="fsoac"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="fsilv"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="fstqt"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="apsco1"]').each(function(index) { $(this).prop("disabled", false); });
			$('[name="ptsum"]').each(function(index) { $(this).prop("disabled", false); });
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
				url : "<c:url value='/portal/evaluation/performance/objectiveEvaluationProc.do'/>"
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
								PARAM_ACTION : "OBJECTIVE_EVALUATION",
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
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/performance/objectiveList.do'/>",
				PARAM_ACTION : "OBJECTIVE_EVALUATION",
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

			if($.isNanModelRow()) {
				alert("모든 Column의 내역을 작성하십시오.");
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

		$(window).resize(function(){
			$('#viewScroll').css('width', $('.ft_container').eq(0).outerWidth());
		});

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">업적평가 평가자평가</h1>

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
		    <col style="width:13%;">
		    <col style="width:20%;">
		    <col style="width:13%;">
		    <col style="width:20%;">
		    <col style="width:13%;">
		    <col style="width:20%;">
		</colgroup>
		<thead>
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

	<form id="ajaxForm" name="ajaxForm" method="post"></form>
	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/performance/objectiveEvaluationProc.do'/>">
	<h2 class="title margintop_20">정량목표</h2>
	<table id="qualTable" class="contents_table" style="width:1650px;">
		<colgroup>
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:70px;" />
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:70px;" />
		    <col style="width:70px;" />
		    <col style="width:70px;" />
		    <col style="width:70px;" />
		</colgroup>
		<thead>
			<tr>
				<th>실행과제</th>
				<th>관리지표(기준/세부내용)</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
				<th>실적</th>
				<th>목표달성도</th>
				<th>목표달성도</th>
				<th>목표달성도</th>
				<th>계</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty qualList}">
				<c:forEach var="result" items="${qualList}">
					<tr>
						<td><textarea name="atask" disabled class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" disabled class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" disabled class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td>
							<input type="text" name="weight" disabled style="width: 60px;text-align:center;" value="${result.WEIGHT}">
							<input type="hidden" name="gubun" value="${result.GUBUN}" />
							<input type="hidden" name="numbr" value="${result.NUMBR}" />
						</td>
						<td><textarea name="mdchk" disabled class="width_100" rows="5"><c:out value="${result.MDCHK}" /></textarea></td>
						<td><textarea name="mcfbk" disabled class="width_100" rows="5"><c:out value="${result.MCFBK}" /></textarea></td>
						<td><textarea name="outpt" disabled class="width_100" rows="5"><c:out value="${result.OUTPT}" /></textarea></td>
						<td>
							<select class="width_100" name="seopa" data-semid="EHAP1">
								<option value="">선택</option>
								<option value="S" <c:if test="${result.SEOPA eq 'S'}">selected</c:if>>S</option>
								<option value="A" <c:if test="${result.SEOPA eq 'A'}">selected</c:if>>A</option>
								<option value="B" <c:if test="${result.SEOPA eq 'B'}">selected</c:if>>B</option>
								<option value="C" <c:if test="${result.SEOPA eq 'C'}">selected</c:if>>C</option>
								<option value="D" <c:if test="${result.SEOPA eq 'D'}">selected</c:if>>D</option>
							</select>
							<input type="hidden" name="fsoac" value="${result.FSOAC}" />
						</td>
						<td>
							<input type="hidden" name="fsilv" />
							<input type="hidden" name="seilv" />
						</td>
						<td>
							<input type="hidden" name="fstqt" />
							<input type="hidden" name="setqt" />
						</td>
						<td>
							<input type="text" name="apsco2" disabled value="${result.APSCO2}" style="width: 60px;text-align:center;">
							<input type="hidden" name="apsco1" value="${result.APSCO1}" />
							<input type="hidden" name="ptsum" value="${result.PTSUM}" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">정성목표</h2>
	<table id="quanTable" class="contents_table" style="width:1650px;">
		<colgroup>
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:70px;" />
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:200px;" />
		    <col style="width:70px;" />
		    <col style="width:70px;" />
		    <col style="width:70px;" />
		    <col style="width:70px;" />
		</colgroup>
		<thead>
			<tr>
				<th>실행과제</th>
				<th>관리지표(기준/세부내용)</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
				<th>실적</th>
				<th>업무수행<br/>질적수준</th>
				<th>업무개선<br/>노력도</th>
				<th>업무<br/>수행양</th>
				<th>계</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty quanList}">
				<c:forEach var="result" items="${quanList}">
					<tr>
						<td><textarea name="atask" disabled class="width_100" rows="5"><c:out value="${result.ATASK}" /></textarea></td>
						<td><textarea name="dtail" disabled class="width_100" rows="5"><c:out value="${result.DTAIL}" /></textarea></td>
						<td><textarea name="trget" disabled class="width_100" rows="5"><c:out value="${result.TRGET}" /></textarea></td>
						<td>
							<input type="text" name="weight" disabled style="width: 60px;text-align:center;" value="${result.WEIGHT}">
							<input type="hidden" name="gubun" value="${result.GUBUN}" />
							<input type="hidden" name="numbr" value="${result.NUMBR}" />
						</td>
						<td><textarea name="mdchk" disabled class="width_100" rows="5"><c:out value="${result.MDCHK}" /></textarea></td>
						<td><textarea name="mcfbk" disabled class="width_100" rows="5"><c:out value="${result.MCFBK}" /></textarea></td>
						<td><textarea name="outpt" disabled class="width_100" rows="5"><c:out value="${result.OUTPT}" /></textarea></td>
						<td>
							<select class="width_100" name="seopa" data-semid="FHAP1">
								<option value="">선택</option>
								<option value="S" <c:if test="${result.SEOPA eq 'S'}">selected</c:if>>S</option>
								<option value="A" <c:if test="${result.SEOPA eq 'A'}">selected</c:if>>A</option>
								<option value="B" <c:if test="${result.SEOPA eq 'B'}">selected</c:if>>B</option>
								<option value="C" <c:if test="${result.SEOPA eq 'C'}">selected</c:if>>C</option>
								<option value="D" <c:if test="${result.SEOPA eq 'D'}">selected</c:if>>D</option>
							</select>
							<input type="hidden" name="fsoac" value="${result.FSOAC}" />
						</td>
						<td>
							<select class="width_100" name="seilv" data-semid="FHAP2">
								<option value="">선택</option>
								<option value="S" <c:if test="${result.SEILV eq 'S'}">selected</c:if>>S</option>
								<option value="A" <c:if test="${result.SEILV eq 'A'}">selected</c:if>>A</option>
								<option value="B" <c:if test="${result.SEILV eq 'B'}">selected</c:if>>B</option>
								<option value="C" <c:if test="${result.SEILV eq 'C'}">selected</c:if>>C</option>
								<option value="D" <c:if test="${result.SEILV eq 'D'}">selected</c:if>>D</option>
							</select>
							<input type="hidden" name="fsilv" value="${result.FSILV}" />
						</td>
						<td>
							<select class="width_100" name="setqt" data-semid="FHAP3">
								<option value="">선택</option>
								<option value="S" <c:if test="${result.SETQT eq 'S'}">selected</c:if>>S</option>
								<option value="A" <c:if test="${result.SETQT eq 'A'}">selected</c:if>>A</option>
								<option value="B" <c:if test="${result.SETQT eq 'B'}">selected</c:if>>B</option>
								<option value="C" <c:if test="${result.SETQT eq 'C'}">selected</c:if>>C</option>
								<option value="D" <c:if test="${result.SETQT eq 'D'}">selected</c:if>>D</option>
							</select>
							<input type="hidden" name="fstqt" value="${result.FSTQT}" />
						</td>
						<td>
							<input type="text" name="apsco2" value="${result.APSCO2}" disabled style="width: 60px;text-align:center;">
							<input type="hidden" name="apsco1" value="${result.APSCO1}" />
							<input type="hidden" name="ptsum" value="${result.PTSUM}" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div id="viewScroll">
		<table class="contents_table no_border" style="width:1650px;">
			<colgroup>
				<col style="width: 200px;" />
				<col style="width: 200px;" />
				<col style="width: 200px;" />
				<col style="width: 70px;" />
				<col style="width: 200px;" />
				<col style="width: 200px;" />
				<col style="width: 200px;" />
				<col style="width: 70px;" />
				<col style="width: 70px;" />
				<col style="width: 70px;" />
				<col style="width: 70px;" />
			</colgroup>
			<tbody>
				<tr>
					<td colspan="10"></td>
					<td class="text_center"><input type="text" name="evalu_score_sum" class="border_gray" disabled style="width: 60px;text-align:center;"></td>
				</tr>
			</tbody>
		</table>
	</div>
	</form>

</div>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>