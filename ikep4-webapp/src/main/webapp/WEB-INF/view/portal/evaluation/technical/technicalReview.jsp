<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<script type="text/javascript">
var boardList = [];
(function($){
	$(document).ready(function() {

		<c:if test="${not empty etList}">
			<c:forEach var="result" varStatus="varStatus" items="${etList}">
				boardList.push({
					AYEAR:"${result.AYEAR}",
					ORGEH:"${result.ORGEH}",
					ORGTX:"${result.ORGTX}",
					APRSE:"${result.APRSE}",
					APRSENM:"${result.APRSENM}",
					TRFGR:"${result.TRFGR}",
					FNSCO:"${result.FNSCO}",
					ADJSC:"${result.ADJSC}",
					CMQSC:"${result.CMQSC}",
					RVSC1:"${result.RVSC1}",
					RVSC2:"${result.RVSC2}",
					RVWSC:"${result.RVWSC}",
					REVWR:"${result.REVWR}",
					REVWRNM:"${result.REVWRNM}",
					RVSTS:"${result.RVSTS}",
					RVSTSX:"${result.RVSTSX}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}"
				});
			</c:forEach>
		</c:if>

		$.adjustableNum = function() {
			var evMrevw = parseFloat('${evMrevw}');
			var num = Math.ceil(boardList.length * evMrevw);
			$('input[name=adjustable_num]').val(num);
		}

		$.adjustNum = function() {
			var adjCount = 0;
			$('#reviewTable > tbody > tr').each(function() {
				if($(this).find('select').length > 0) {
					if($(this).find('select[name=rvsc1]').val() != '' || $(this).find('select[name=rvsc2]').val() != '') {
						adjCount++;
					}
				}
			});

			$('input[name=adjustment_num]').val(adjCount);
		}

		$.calcAllReviewScore = function() {
			$('#reviewTable > tbody > tr').each(function() {
				var evalScore = parseFloat($(this).find('td:nth-child(5)').text());
				var selectScore = 0;
				if($('[name=rvsc1]', this).val()) selectScore = $('[name=rvsc1]', this).val();
				if($('[name=rvsc2]', this).val()) selectScore = $('[name=rvsc2]', this).val();

				$(this).find('td:last').text(selectScore == '' ? evalScore : $.ScoreRound(evalScore + parseInt(selectScore), 1));
			});
		}

		$.fn.calcReviewScore = function() {
			var _selScore = $(this).val();
			var _prevScore = parseFloat($(this).closest('tr').find('td:nth-child(5)').text());
			$(this).closest('tr').find('td:last').text(_selScore == '' ? _prevScore : $.ScoreRound(_prevScore + parseInt(_selScore), 1));
		}

		$.fn.bindChangeSelect = function(target) {
			$(this).each(function() {
				$(this).change(function() {
					$(this).calcReviewScore();
					$(this).closest('tr').find('select[name='+target+']').val('');

					$.adjustNum();
				});
			});
		}

		$.viewMode = function() {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();
			$('select[name=rvsc1]').each(function() { $(this).prop("disabled", true) });
			$('select[name=rvsc2]').each(function() { $(this).prop("disabled", true) });
		};

		$.initSet = function() {
			if('${esReturn.MSGTY}' == 'W') {
				$.viewMode();
				alert('${esReturn.MSGTX}');

				return false;
			}

			$.adjustableNum();
			$.adjustNum();

			if(!$('#reviewTable').find('.emptyRecord').length) $.calcAllReviewScore();

			$('select[name=rvsc1]').bindChangeSelect('rvsc2');
			$('select[name=rvsc2]').bindChangeSelect('rvsc1');
		};

		$.pageReload = function() {
			$("#ajaxForm")
				.attr("method", "post")
				.attr("action", "<c:url value='/portal/evaluation/technical/technicalReviewList.do'/>")
				.submit();
		}

		$.isDuplicateSelScore = function() {
			var isDup = false;
			$('#reviewTable > tbody > tr').each(function() {
				if($(this).find('select[name=rvsc1]').val() != '' && $(this).find('select[name=rvsc2]').val() != '') {
					isDup = true;
				}
			});

			return isDup;
		}

		$.isOverAdjustment = function() {
			var isOver = false;
			var a = $('input[name=adjustable_num]').val();
			var b = $('input[name=adjustment_num]').val();
			if(b == '') b = 0;

			if(parseInt(b) > parseInt(a)) {
				isOver = true;
			}

			return isOver;
		}

		$.makeFormData = function(button) {
			var formData = $('#evaluationForm').serializeArray();
			formData.push({name:'button', value:button});
			$.each(boardList, function(index, item) {
				var rvsc1 = $('#reviewTable > tbody > tr').eq(index).find('[name=rvsc1]').val();
				var rvsc2 = $('#reviewTable > tbody > tr').eq(index).find('[name=rvsc2]').val();

				formData.push({name:'reviews['+index+'].ayear', value:item.AYEAR});
				formData.push({name:'reviews['+index+'].orgeh', value:item.ORGEH});
				formData.push({name:'reviews['+index+'].orgtx', value:item.ORGTX});
				formData.push({name:'reviews['+index+'].aprse', value:item.APRSE});
				formData.push({name:'reviews['+index+'].aprsenm', value:item.APRSENM});
				formData.push({name:'reviews['+index+'].trfgr', value:item.TRFGR});
				formData.push({name:'reviews['+index+'].fnsco', value:item.FNSCO});
				formData.push({name:'reviews['+index+'].adjsc', value:item.ADJSC});
				formData.push({name:'reviews['+index+'].cmqsc', value:item.CMQSC});
				formData.push({name:'reviews['+index+'].rvsc1', value:rvsc1 != '' ? rvsc1 : (rvsc2 != '' ? rvsc2 : '')});
				formData.push({name:'reviews['+index+'].rvwsc', value:$('#reviewTable > tbody > tr').eq(index).find('td:last').text()});
				formData.push({name:'reviews['+index+'].revwr', value:item.REVWR});
				formData.push({name:'reviews['+index+'].revwrnm', value:item.REVWRNM});
				formData.push({name:'reviews['+index+'].rvsts', value:item.RVSTS});
				formData.push({name:'reviews['+index+'].rvstsx', value:item.RVSTSX});
				formData.push({name:'reviews['+index+'].srtky', value:item.SRTKY});
				formData.push({name:'reviews['+index+'].modif', value:item.MODIF});
			});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/technical/technicalReviewProc.do'/>"
				, type : "post"
				, data : $.makeFormData(params.button)
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);

						$.stopProgress();
					} else {
						alert(params.sucessMsg);

						if(params.isCallList) {
							$.pageReload();
						} else {
							$.stopProgress();
						}
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);

					$.stopProgress();
				}
			});
		}

		$('#reviewTable > tbody').find('a').click(function(e) {
			e.preventDefault();

			var empno = $(this).closest('tr').find('td').eq(1).text();
			var url = iKEP.getContextRoot() + "/portal/evaluation/technical/popTechnicalIndividualEvaluationList.do?empno=" + empno;
			iKEP.popupOpen(url, {width:900, height:700, scrollbar:true}, "evaluationContentsPopup");
		});

		$('#btnSave').click(function(e) {
			e.preventDefault();

			if($('#reviewTable').find('.emptyRecord').length) {
				return false;
			}

			if(confirm("저장하시겠습니까?")) {
				$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : true});
			}
		});

		$('#btnComplete').click(function(e) {
			e.preventDefault();

			if($('#reviewTable').find('.emptyRecord').length) {
				return false;
			}

			if($.isDuplicateSelScore()) {
				alert("동일 피평가자에 대해 가/감점을 동시에 입력하지 않습니다.");
				return false;
			}

			if($.isOverAdjustment()) {
				alert("조정가능인원을 초과하였습니다.");
				return false;
			}

			if(confirm("완료하시겠습니까?")) {
				$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true});
			}
		});

		$.initSet();

		<c:if test="${evRvsts eq '2'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/technical/technicalReviewList.do'/>">
	<h1 class="title">기능직평가 Review</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnSave" title="저장" href=""><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="완료" href=""><span>완료</span></a>
	</div>
	<div class="table_top">
		조정인원/조정가능인원 <input type="text" name="adjustment_num" disabled style="width: 40px;text-align:center;"> / <input type="text" name="adjustable_num" disabled style="width: 40px;text-align:center;">
		<div class="float_right">진행상태 : ${evRvstsx}</div>
	</div>
	<table class="list_table" id="reviewTable">
		<colgroup>
		    <col style="width:;" />
		    <col style="width:10%;" />
		    <col style="width:10%;" />
		    <col style="width:9%;" />
		    <col style="width:10%;" />
		    <col style="width:10%;" />
		    <col style="width:10%;" />
		    <col style="width:125px;" />
		</colgroup>
		<thead>
			<tr>
				<th>조직</th>
				<th>사번</th>
				<th>성명</th>
				<th>직급</th>
				<th>역량평가점수</th>
				<th>가점</th>
				<th>감점</th>
				<th>Review 점수</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty etList}">
					<tr>
						<td colspan="8" class="emptyRecord center">내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${etList}">
						<tr>
							<td>${result.ORGTX}</td>
							<td>${result.APRSE}</td>
							<td><a href="#">${result.APRSENM}</a></td>
							<td>${result.TRFGR}</td>
							<td>${result.ADJSC}</td>
							<td>
								<fmt:parseNumber var="RVSC1" integerOnly="true" type="number" value="${result.RVSC1}" />
								<select class="width_100" name="rvsc1">
									<option value="">선택</option>
									<option value="5" <c:if test="${RVSC1 eq 5}">selected</c:if>>5</option>
									<option value="4" <c:if test="${RVSC1 eq 4}">selected</c:if>>4</option>
									<option value="3" <c:if test="${RVSC1 eq 3}">selected</c:if>>3</option>
									<option value="2" <c:if test="${RVSC1 eq 2}">selected</c:if>>2</option>
									<option value="1" <c:if test="${RVSC1 eq 1}">selected</c:if>>1</option>
								</select>
							</td>
							<td>
								<fmt:parseNumber var="RVSC2" integerOnly="true" type="number" value="${result.RVSC2}" />
								<select class="width_100" name="rvsc2">
									<option value="">선택</option>
									<option value="-5" <c:if test="${RVSC1 eq -5}">selected</c:if>>5</option>
									<option value="-4" <c:if test="${RVSC1 eq -4}">selected</c:if>>4</option>
									<option value="-3" <c:if test="${RVSC1 eq -3}">selected</c:if>>3</option>
									<option value="-2" <c:if test="${RVSC1 eq -2}">selected</c:if>>2</option>
									<option value="-1" <c:if test="${RVSC1 eq -1}">selected</c:if>>1</option>
								</select>
							</td>
							<td>0</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
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