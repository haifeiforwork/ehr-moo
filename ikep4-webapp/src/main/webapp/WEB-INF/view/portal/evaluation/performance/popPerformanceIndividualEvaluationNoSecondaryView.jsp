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

			//Header rowspan,colspan
			var fixedRows = [0, 1, 2, 3],
				nonFixedRows = [0, 1, 2, 3, 4, 5, 6, 15];

			$.each(fixedRows, function(index, value) {
				$ft_rc.Rowspan(value);
				$ft_c.Rowspan(value);
			});
			$.each(nonFixedRows, function(index, value) {
				$ft_r.Rowspan(value);
				$qualTable.Rowspan(value);
				$quanTable.Rowspan(value);
			});
			$ft_r.Colspan(0);
			$qualTable.Colspan(0);
			$quanTable.Colspan(0);

			//테이블 style 적용 - 미세조정
			$ft_rc.addClass(_tableClass);
			$ft_rc.each(function(index) {
				var changeWidth = (!$.browser.msie) ? $(this).width() + 4 : $(this).width() + 3;
				var changeHeight = $(this).height() + 1;
				if(index == 1)  changeHeight = changeHeight + 1;
				$(this).css('width', changeWidth).css('height', changeHeight);
			});
			$ft_c.addClass(_tableClass);
			$ft_c.each(function(index) {
				var changeWidth = $(this).width() + 3;
				var changeHeight = $(this).height() + 1;
				if(index == 1)  changeHeight = changeHeight + 1;
				$(this).css('width', changeWidth).css('height', changeHeight).parent().css('width', changeWidth);
			});
			$ft_r.addClass(_tableClass);
			$ft_rwrapper.css('width', $ft_rwrapper.parent().outerWidth() + _pad18);

			//개별영역 스크롤 숨기기 및 height 확장
			$ft_scroller.eq(0).hiddenDivScroll($qualTable.height());
			$ft_scroller.eq(1).hiddenDivScroll($quanTable.height());

			//가로스크롤 동기화
			$viewScroll.css('width', $ft_container.eq(0).outerWidth() + 16.55).css('height', '49px').css('overflow-x', 'scroll');
			$viewScroll.find('table').css('width', $qualTable.outerWidth());
			$viewScroll.scroll(function() {
				$('.ft_scroller').scrollLeft($(this).scrollLeft());
			});

			//정량목표 테이블 조정
			$ft_r.eq(0).find('tr').eq(1).find('th').eq(7).css('border-right-width', '0px').next().css('border-right-width', '0px');
			$('#qualTable > thead > tr').eq(1).find('th').eq(7).css('border-right-width', '0px').next().css('border-right-width', '0px');
			$('#qualTable').find('tbody > tr').each(function() {
				$(this).find('td').eq(7).css('border-right-width', '0px').next().css('border-right-width', '0px');
			});
		}

		$.initView = function() {
			$('#qualTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#quanTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$('[name=evalu_score_sum1]').val($.SumScore('apsco1'));
			$('[name=evalu_score_sum_total]').val($.SumScore('ptsum'));

			$.DisabledBackgroundStyleChange();

			$.initFixedTable();

			$.expandTextareaToTr();
		}

		$.expandTextareaToTr = function() {
			$('textarea', '#wrap').each(function() {
				$(this).css('height', $(this).closest('td').height() - 5);
			});
		}

		$('#btnBack').click(function(e) {
			e.preventDefault();
			$.CallListPage({
				SUBMIT_ACTION : "<c:url value='/portal/evaluation/performance/popPerformanceIndividualEvaluationList.do'/>",
				PARAM_EMPNO : "${params.APRSE}"
			});
		});

		$.initView();

		$(window).resize(function() {
			$('#viewScroll').css('width', $('.ft_container').eq(0).outerWidth());
		});

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">업적평가</h1>

	<div class="btn_wrap left" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
	</div>

	<form id="ajaxForm" name="ajaxForm" method="post"></form>
	<h2 class="title">정량목표</h2>
	<table id="qualTable" class="contents_table">
		<thead>
			<tr style="height:30px">
				<th>실행과제</th>
				<th>관리지표(기준/세부내용)</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
				<th>실적</th>
				<th>평가자</th>
				<th>평가자</th>
				<th>평가자</th>
				<th>평가자</th>
				<th>총점</th>
			</tr>
			<tr>
				<th>실행과제</th>
				<th>관리지표(기준/세부내용)</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
				<th>실적</th>
				<th style="width: 70px;"></th>
				<th style="width: 70px;">목표달성도</th>
				<th style="width: 70px;"></th>
				<th style="width: 70px;">계</th>
				<th>총점</th>
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
						<td></td>
						<td><input type="text" name="fsoac" disabled style="width: 60px;text-align:center;" value="${result.FSOAC}" /></td>
						<td></td>
						<td><input type="text" name="apsco1" disabled style="width: 60px;text-align:center;" value="${result.APSCO1}" /></td>
						<td><input type="text" name="ptsum" disabled style="width: 40px;text-align:center;" value="${result.PTSUM}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h2 class="title margintop_20">정성폭표</h2>
	<table id="quanTable" class="contents_table">
		<thead>
			<tr style="height:30px">
				<th>실행과제</th>
				<th>관리지표(기준/세부내용)</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
				<th>실적</th>
				<th>평가자</th>
				<th>평가자</th>
				<th>평가자</th>
				<th>평가자</th>
				<th>총점</th>
			</tr>
			<tr>
				<th>실행과제</th>
				<th>관리지표(기준/세부내용)</th>
				<th>목표</th>
				<th>가중치(%)</th>
				<th>중간점검</th>
				<th>중간점검 Feedback</th>
				<th>실적</th>
				<th style="width: 70px;">업무수행<br/>질적수준</th>
				<th style="width: 70px;">업무개선<br/>노력도</th>
				<th style="width: 70px;">업무<br/>수행양</th>
				<th style="width: 70px;">계</th>
				<th>총점</th>
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
						<td><textarea name="mdchk" disabled class="width_100" rows="5"><c:out value="${result.MDCHK}" /></textarea></td>
						<td><textarea name="mcfbk" disabled class="width_100" rows="5"><c:out value="${result.MCFBK}" /></textarea></td>
						<td><textarea name="outpt" disabled class="width_100" rows="5"><c:out value="${result.OUTPT}" /></textarea></td>
						<td><input type="text" name="fsoac" disabled style="width: 60px;text-align:center;" value="${result.FSOAC}" /></td>
						<td><input type="text" name="fsilv" disabled style="width: 60px;text-align:center;" value="${result.FSILV}" /></td>
						<td><input type="text" name="fstqt" disabled style="width: 60px;text-align:center;" value="${result.FSTQT}" /></td>
						<td><input type="text" name="apsco1" disabled style="width: 60px;text-align:center;" value="${result.APSCO1}" /></td>
						<td><input type="text" name="ptsum" disabled style="width: 40px;text-align:center;" value="${result.PTSUM}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div id="viewScroll">
		<table class="contents_table no_border">
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
				<col style="width: 70px;" />
			</colgroup>
			<tbody>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td class="text_center"><input type="text" name="evalu_score_sum1" class="border_gray" disabled style="width: 40px;text-align:center;"></td>
					<td class="text_center"><input type="text" name="evalu_score_sum_total" class="border_gray" disabled style="width: 40px;text-align:center;"></td>
				</tr>
			</tbody>
		</table>
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