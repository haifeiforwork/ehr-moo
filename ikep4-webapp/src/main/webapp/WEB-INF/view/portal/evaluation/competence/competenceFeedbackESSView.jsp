<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>
<!--[if lte IE 8]><![endif]-->
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/excanvas.min.js"></script>
<!--[if lte IE 8]><![endif]-->
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery.flot.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery.flot.symbol.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/jquery.flot.axislabels.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/JUMFlot.min.js"></script>

<script type="text/javascript">
(function($){
	$(document).ready(function() {

		$.initView = function() {
			$.bindButtonBackHandler();

			$('#evaluationData').Rowspan(0);

			$.drawChartSpider();

			$('[name="footers[0].fbtxt"]').AutoHeightResizeTextarea();
			$('[name="footers[0].fbtxt"]').prop("disabled", true);

			$.DisabledBackgroundStyleChange();
		}

		$.drawChartSpider = function() {
			var d1 = [], d2 = [], d3 = [], label = [], d1_value, d2_value, d3_value;
			var num_check=/^[-|+]?\d+\.?\d*$/;

			$('#evaluationData > tr').each(function(index) {
				label[index] = {label:$(this).find('th').eq(1).text()};

				d1_value = $(this).find('td').eq(0).text();
				d2_value = $(this).find('td').eq(1).text();
				d3_value = $(this).find('td').eq(2).text();

				d1.push([index, parseFloat(num_check.test(d1_value) ? d1_value : 0)]);
				d2.push([index, parseFloat(num_check.test(d2_value) ? d2_value : 0)]);
				d3.push([index, parseFloat(num_check.test(d3_value) ? d3_value : 0)]);
			});

			var data = [
					{ label: "평가점수",color:"blue",data: d1, spider: {show: true} },
				    { label: "사업장평균",color:"red",data: d2, spider: {show: true} },
				    { label: "전사평균",color:"green",data: d3, spider: {show: true} }
			];

			var options = {
				series:{
					editMode: 'v',
					editable:true,
			        spider:{
			        	scaleMode: "all",
						active: true,
						highlight: {mode: "area"},
						legMin: 0,
						legMax: 100,
			            legs: {
			            		font: "bold 11px Malgun Gothic, '맑은 고딕', 'AppleGothic', 'Dotum', 'sans-serif'",
								data: label,
								fillStyle: "black",
			                	legScaleMax:0,
			                	legScaleMin:0,
			                	legStartAngle:0,
			                	labelWidth: 0,
			                	labelSpace: 10
						},
			            spiderSize: 0.7,
			            lineWidth: 1,
			            connection: {width: 2},
			            pointSize: 3
					}
				},
			    grid:{
			        hoverable: true,
			        clickable: false,
			        editable:false,
			        tickColor: "rgba(0,0,0,0.2)",
			        mode: "radar"
			    }
			};

			$.plot($("#chartSpider"),data,options);
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function(e) {
				e.preventDefault();
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/competence/competenceFeedBackList.do'/>",
					PARAM_ACTION : "COMPETENCE_FEEDBACK_ESS",
					PARAM_PAGE_NUM : "${params.currentPage}"
				});
			});
		}

		$.initView();

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">역량평가 Feedback</h1>

	<div class="btn_wrap left">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
	</div>

	<div class="float_wrap">
		<div class="float_left width_50">
			<div class="chart_box" style="text-align: center;">
				<div id="chartSpider" style="width:500px;height:500px;display:inline-block;"></div>
			</div>
		</div>
		<div class="float_right width_50">
			<table class="contents_table">
				<colgroup>
					<col style="width:20%;" />
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<th>우수역량</th>
						<td class="comp_text">
							<input type="hidden" name="footers[0].stext1" value="${etFooter[0].STEXT1}" />
							${etFooter[0].STEXT1}
						</td>
					</tr>
				</tbody>
			</table>
			<p class="disc marginbottom_20">
				※ 우수역량은 평가점수가 사업장평균(동일직급 기준) 대비 10점 이상 높은 역량을 의미함
			</p>
			<table class="contents_table">
				<colgroup>
					<col style="width:20%;" />
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<th>개발필요역량</th>
						<td class="comp_text">
							<input type="hidden" name="footers[0].stext2" value="${etFooter[0].STEXT2}" />
							${etFooter[0].STEXT2}
						</td>
					</tr>
				</tbody>
			</table>
			<p class="disc marginbottom_20">
				※ 개발필요 역량은 평가점수가 사업장 평균(동일직급 기준) 대비 10점 이상 낮은 역량을 의미함
			</p>
			<table class="list_table">
				<colgroup>
					<col style="width: 15%;" />
					<col style="width: 25%;" />
					<col style="width: 20%;" />
					<col style="width: 20%;" />
					<col style="width: 20%;" />
				</colgroup>
				<thead>
					<tr>
						<th colspan="2">역량</th>
						<th>평가점수</th>
						<th>사업장 평균</th>
						<th>전사 평균</th>
					</tr>
				</thead>
				<tbody id="evaluationData">
					<c:if test="${not empty etDetail}">
						<c:forEach var="result" items="${etDetail}">
							<tr>
								<th>${result.QKTXT}</th>
								<th class="text_left">${result.STEXT}</th>
								<td>${result.FNSCO}</td>
								<td>${result.LOCAV}</td>
								<td>${result.ENTAV}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<p class="disc">
				※ 전사 평균 및 사업장 평균은 동일직급 기준으로 산출
			</p>
		</div>
	</div>

	<h2 class="title margintop_30">
		역량육성을 위한 피드백
	</h2>
	<table class="contents_table">
		<colgroup>
			<col style="">
		</colgroup>
		<tbody>
			<tr>
				<td>
					<textarea name="footers[0].fbtxt" class="width_100" rows="4">${etFooter[0].FBTXT}</textarea>
				</td>
			</tr>
		</tbody>
	</table>

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