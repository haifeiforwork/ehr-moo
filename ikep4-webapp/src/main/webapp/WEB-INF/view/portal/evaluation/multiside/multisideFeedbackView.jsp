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

<style>
.chart-placeholder {
	width: 850px;
	height: 250px;
	font-size: 11px;
}
.chart-placeholder2 {
	width: 850px;
	height: 200px;
	font-size: 11px;
}
</style>

<script type="text/javascript">
(function($){
	$(document).ready(function() {

		$.initView = function() {
			$.bindButtonBackHandler();
			$.bindButtonPrintHandler();

			$.bindTabHandler();

			$.drawChartSpider();
			<c:if test="${params.PERSG ne '1'}">
			$.drawChartStick({CHART_ID : "#chartStic1", CHART_DATA_ID : "#chartBar1Data", DATA_INDEX : 2});
			</c:if>
			$.drawChartStick({CHART_ID : "#chartStic2", CHART_DATA_ID : "#chartBar1Data", DATA_INDEX : 4});
			$.drawChartStick({CHART_ID : "#chartStic3", CHART_DATA_ID : "#chartBar2Data", DATA_INDEX : 6});
			$.drawChartStick({CHART_ID : "#chartStic4", CHART_DATA_ID : "#chartBar2Data", DATA_INDEX : 7});
			$.drawChartStick({CHART_ID : "#chartStic5", CHART_DATA_ID : "#chartBar2Data", DATA_INDEX : 8});
			<c:if test="${eSkippv eq ''}">
			$.drawChartStick({CHART_ID : "#chartStic6", CHART_DATA_ID : "#chartBar3Data", DATA_INDEX : 6});
			$.drawChartStick({CHART_ID : "#chartStic7", CHART_DATA_ID : "#chartBar3Data", DATA_INDEX : 7});
			$.drawChartStick({CHART_ID : "#chartStic8", CHART_DATA_ID : "#chartBar3Data", DATA_INDEX : 8});
			</c:if>

			$('.add_tabcon').each(function(index) {
				if(index > 0) $(this).hide();
			});
		}

		$.drawChartSpider = function() {
			var d1 = [], d2 = [], d3 = [], label = [], d1_value, d2_value, d3_value;
			var num_check=/^[-|+]?\d+\.?\d*$/;

			$('#chartSpiderData > tr:not(.total)').each(function(index) {
				if($(this).find('th').length == 1) {
					label[index] = {label:$(this).find('th').eq(0).text()};
				} else {
					label[index] = {label:$(this).find('th').eq(1).text()};
				}

				d1_value = $(this).find('td').eq(0).text();
				d2_value = $(this).find('td').eq(1).text();
				d3_value = $(this).find('td').eq(2).text();

				d1.push([index, parseFloat(num_check.test(d1_value) ? d1_value : 0)]);
				d2.push([index, parseFloat(num_check.test(d2_value) ? d2_value : 0)]);
				d3.push([index, parseFloat(num_check.test(d3_value) ? d3_value : 0)]);
			});

			var data = [
					{ label: "상사",color:"blue",data: d1, spider: {show: true} },
				    { label: "동료",color:"red",data: d2, spider: {show: true} },
				    { label: "부하",color:"green",data: d3, spider: {show: true} }
			];

			var options = {
				series:{
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
			            connection: {width: 2},
			            pointSize: 5
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

		$.drawChartStick = function(options) {
			var d1 = [],
				ticks = [],
				lastIndex;

			ticks.push('');
			d1.push([0, null]);
			$(options.CHART_DATA_ID + ' > tr:not(.total)').each(function(index) {
				if($(this).find('th').length == 1) {
					ticks.push($(this).find('th').eq(0).text());
				} else {
					ticks.push($(this).find('th').eq(1).text());
				}

				d1.push([index+1, parseFloat($(this).find('td').eq(options.DATA_INDEX).text())]);
				lastIndex = index+1;
			});
			ticks.push('');
			d1.push([lastIndex+1, null]);

			var data = [{ data: d1, color: "#5482FF" }];
			var placeholder = $(options.CHART_ID);
			var p = $.plot(placeholder, data, {
				series: {
			        bars: {
			            show: true
			        }
			    },
				bars: {
					align: "center",
			        barWidth: 0.8
			    },
				xaxis: {
					ticks: null,
					axisLabel: " ",
			        axisLabelUseCanvas: true,
			        axisLabelFontSizePixels: 12,
			        axisLabelFontFamily: "'Malgun Gothic', '맑은 고딕', AppleGothic, Dotum, sans-serif",
			        axisLabelPadding: 20,
				},
				yaxis: {
					min: -20, max: 20,
					axisLabel: " ",
			        axisLabelUseCanvas: true,
			        axisLabelFontSizePixels: 12,
			        axisLabelFontFamily: "'Malgun Gothic', '맑은 고딕', AppleGothic, Dotum, sans-serif",
			        axisLabelPadding: 5
				},
				legend: {
			        noColumns: 0,
			        labelBoxBorderColor: "#000000",
			        position: "nw"
			    },
				grid: {
			        hoverable: true,
			        borderWidth: 1
			    }
			});

			$('.flot-y-axis').find('.tickLabel').each(function() {
				$(this)
					.css('top', parseInt($(this).css('top'))-1)
					.css('left', '0px');
			});
			/* $('.flot-x-axis').find('.tickLabel').each(function() {
				$(this).css('left', parseInt($(this).css('left'))-3);
			}); */
			$('.flot-x-axis').hide();

			/* $.each(p.getData()[0].data, function(i, el) {
				var o = p.pointOffset({x: el[0], y: el[1]});
				var num_check=/^[-|+]?\d+\.?\d*$/;
				if(num_check.test(el[1])) {
					var positionTop = el[1] < 0 ? placeholder.height() - 70 : 15;
					$('<div class="data-point-label">' + el[1] + '</div>').css( {
						position: 'absolute',
						left: o.left - 5,
						top: positionTop,
						display: 'none'
					}).appendTo(p.getPlaceholder()).fadeIn('slow');
				}
			}); */

			$.each(p.getData()[0].data, function(i, el) {
				var o = p.pointOffset({x: el[0], y: el[1]});
				$('<div class="data-point-label" style="writing-mode: tb; -ms-writing-mode: tb-lr;">' + ticks[i].substring(0, 6) + '</div>').css( {
					position: 'absolute',
					left: o.left - 10,
					top: (placeholder.height()/2) - 15,
					'font-size': '15px',
					'font-family': "'Malgun Gothic', '맑은 고딕', AppleGothic, Dotum, sans-serif",
					display: 'none'
				}).appendTo(p.getPlaceholder()).fadeIn('slow');
			});

			placeholder.useTooltip();
		}

		$.bindTabHandler = function() {
			$('.add_tabs > li').each(function(index, event) {
				$(this).click(function(e) {
					e.preventDefault();

					$(this).siblings().removeClass('selceted')
							.end().addClass('selceted');

					$('.add_tabcon').hide().eq(index).show();
				})
				.css('cursor', 'pointer');
			});
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function(e) {
				e.preventDefault();
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/multiside/multisideFeedbackList.do'/>",
					PARAM_PAGE_NUM : "${params.currentPage}"
				});
			});
		}

		$.bindButtonPrintHandler = function() {
			$('#btnPrint').click(function(e) {
				e.preventDefault();

				var $inputElem = $('<input type="hidden" />');
				var $printForm = $('#ajaxForm');

				$printForm.empty();
				$printForm.append($inputElem.clone().attr('name', 'AYEAR').val('${params.AYEAR}'));
				$printForm.append($inputElem.clone().attr('name', 'MAPPE').val('${params.MAPPE}'));
				$printForm.append($inputElem.clone().attr('name', 'MAPENM').val('${params.MAPENM}'));
				$printForm.append($inputElem.clone().attr('name', 'LOCAT').val('${params.LOCAT}'));
				$printForm.append($inputElem.clone().attr('name', 'LOCATX').val('${params.LOCATX}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGEH').val('${params.ORGEH}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGTX').val('${params.ORGTX}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK').val('${params.ZZJIK}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT').val('${params.COTXT}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR').val('${params.TRFGR}'));
				$printForm.append($inputElem.clone().attr('name', 'PLANS').val('${params.PLANS}'));
				$printForm.append($inputElem.clone().attr('name', 'PLSTX').val('${params.PLSTX}'));
				$printForm.append($inputElem.clone().attr('name', 'PERSG').val('${params.PERSG}'));
				$printForm.append($inputElem.clone().attr('name', 'PGTXT').val('${params.PGTXT}'));
				$printForm.append($inputElem.clone().attr('name', 'FBEGDA').val('${params.FBEGDA}'));
				$printForm.append($inputElem.clone().attr('name', 'FENDDA').val('${params.FENDDA}'));
				$printForm.append($inputElem.clone().attr('name', 'FDATUM').val('${params.FDATUM}'));
				$printForm.append($inputElem.clone().attr('name', 'SRTKY').val('${params.SRTKY}'));
				$printForm.append($inputElem.clone().attr('name', 'MODIF').val('${params.MODIF}'));

				$printForm.append("<input type=\"hidden\" name=\"action\" value=\"FEEDBACK_PRINT\"/>");

				$printForm.attr("method", "post");
				$printForm.attr("action", "<c:url value='/portal/evaluation/multiside/multisideFeedbackView.do'/>");

				$.CallPrintPagePopup();

				$printForm.empty();
			});
		}

		var previousPoint = null, previousLabel = null;
		$.fn.useTooltip = function () {
		    $(this).bind("plothover", function (event, pos, item) {
		        if (item) {
		            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
		                previousPoint = item.dataIndex;
		                previousLabel = item.series.label;
		                $("#tooltip").remove();

		                var x = item.datapoint[0];
		                var y = item.datapoint[1];

		                var color = item.series.color;

		                $.showTooltip(item.pageX,
		                        item.pageY,
		                        color,
		                        "<strong>" + y + "</strong>");
		            }
		        } else {
		            $("#tooltip").remove();
		            previousPoint = null;
		        }
		    });
		};

		$.showTooltip = function(x, y, color, contents) {
		    $('<div id="tooltip">' + contents + '</div>').css({
		        position: 'absolute',
		        display: 'none',
		        top: y - 40,
		        left: x - 60,
		        border: '2px solid ' + color,
		        padding: '3px',
		        'font-size': '9px',
		        'border-radius': '5px',
		        'background-color': '#fff',
		        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
		        opacity: 0.9
		    }).appendTo("body").fadeIn(200);
		}

		$.initView();

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">다면진단 Feedback</h1>

	<h2 class="title"><c:out value="${params.AYEAR}" />년</h2>

	<div class="btn_wrap left">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnPrint" title="인쇄" href="#"><span>인쇄</span></a>
	</div>

	<ul class="add_tabs">
		<li class="selceted">개요</li>
		<li>진단내용 및 항목</li>
		<li>결과 종합</li>
		<li>비교(전사/사업장)</li>
		<li>비교(진단요소별)</li>
		<c:if test="${eSkippv eq ''}">
			<li>비교(전년대비)</li>
		</c:if>
		<li>개발필요역량</li>
	</ul>

	<div class="add_tabcon" style="display:block;">
		<span class="title">1. 다면진단의 개요</span>
		<ul class="info_list">
			<li>
				본 다면진단은 무림의 리더계층이 보여주고 있는 공통역량 및 리더십역량의 수준을 종합적으로 검토하고 리더계층의 육성 및 자기개발 활동을 지원하기 위한 기본정보를 수집하는 목적으로 수행되고 있습니다.
			</li>
			<li>
				<span><c:out value="${etHeader1.COMMT1}" /></span>님의 다면진단은 공통역량과 리더십역량, 그리고 협업, 동기부여를 진단내용으로 <span><c:out value="${etHeader1.COMMT2}" /></span>까지 다수의 구성원들이 참여하여 실시되었습니다.
				<table class="list_table info">
					<colgroup>
						<col style="width:100px;" />
						<col style="width:100px;" />
						<col style="width:100px;" />
						<col style="width:100px;" />
						<col style="width:100px;" />
					</colgroup>
					<thead>
						<tr>
							<th colspan="2">구분</th>
							<th>상사</th>
							<th>동료</th>
							<th>부하</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td rowspan="2">역량</td>
							<td>공통역량</td>
							<td>●</td>
							<td>●</td>
							<td>●</td>
						</tr>
						<tr>
							<td>리더십역량</td>
							<td>●</td>
							<td>●</td>
							<td>●</td>
						</tr>
						<tr>
							<td colspan="2">협업</td>
							<td></td>
							<td>●</td>
							<td>●</td>
						</tr>
						<tr>
							<td colspan="2">동기부여</td>
							<td></td>
							<td>●</td>
							<td>●</td>
						</tr>
					</tbody>
				</table>
			</li>
			<li>
				<p>귀하의 다면진단에는 5점의 Likert 척도가 사용되었습니다.</p>
				<table class="list_table info">
					<colgroup>
						<col style="width:100px;" />
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:80px;" />
						<col style="width:82px;" />
					</colgroup>
					<thead>
						<tr>
							<th>척도</th>
							<th>탁월</th>
							<th>우수</th>
							<th>보통</th>
							<th>미흡</th>
							<th>매우미흡</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>점수</td>
							<td>5점</td>
							<td>4점</td>
							<td>3점</td>
							<td>2점</td>
							<td>1점</td>
						</tr>
					</tbody>
				</table>
			</li>
			<li>
				다면진단 결과는 귀하와 관련이 있는 다양한 사람들의 관점에서 귀하의 공통역량 및 리더십역량 수준을 파악한 것으로 귀하의 생각과 다르게 나타날 수 있습니다.
			</li>
			<li>
				리더십 발휘는 나 자신뿐만 아니라 타인과의 상호작용에서 의미를 가질 수 있으므로 귀하의 의견과 다르다고 무시하지 마시고, 귀하의 리더십 수준을 성찰하는데 참고하시기 바랍니다.
			</li>
			<li>
				제공되는 다면진단 결과가 귀하의 리더십 개발 활동에 의미있는 정보가 되었으면 합니다.
			</li>
		</ul>
	</div>

	<div class="add_tabcon" style="display:block;">
		<span class="title">2. 다면진단의 진단내용 및 항목</span>
		<p>본 다면진단에서는 공통역량과 리더십역량, 협업, 동기부여를 진단하고 있으며 내용은 다음과 같습니다.</p>
		<table class="list_table info" style="width:100%">
			<colgroup>
				<col style="width:80px;" />
				<col style="width:130px;" />
				<col style="" />
			</colgroup>
			<thead>
				<tr>
					<th>구분</th>
					<th>진단요소</th>
					<th>진단항목</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td rowspan="4">공통역량</td>
					<td>존중</td>
					<td class="text_left">
						<ul class="info_list">
							<li>고객 및 동료를 대할 때  바른말과 올바른 몸가짐으로 상대방을 높이고 소중히 대한다</li>
							<li>견해가 다른 새로운 아이디어와 관점을 가진 고객 및 동료를 대할 때에도 다양한 의견을 수용/적용한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>전문성</td>
					<td class="text_left">
						<ul class="info_list">
							<li>업무의 전문성/숙련을 지속적으로 향상시킨다</li>
							<li>최신 지식이나 기술을 습득하여 업무에 활용한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>책임감</td>
					<td class="text_left">
						<ul class="info_list">
							<li>자신이 맡은 업무를 중요하게 생각하고 목표 이상의 성과를 창출한다</li>
							<li>원칙과 약속은 반드시 지킨다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>끈기</td>
					<td class="text_left">
						<ul class="info_list">
							<li>업무 수행 중 발생하는 애로사항을 극복하고 묵묵히(성실히) 진행한다</li>
							<li>제한된 자원을 효과적으로 배분/활용하여 끝까지 완수한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td rowspan="5">리더십 역량</td>
					<td>성과관리</td>
					<td class="text_left">
						<ul class="info_list">
							<li>부서 목표달성을 위해 구성원과 목표 합의를 위해 노력한다.</li>
							<li>구성원의 업무 진척상황을 모니터링하고 기록/관리한다</li>
							<li>객관적 사실에 기반한 피드백을 하고, 업무향상을 위한 올바른  방법을 충실히 지도한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>부하육성</td>
					<td class="text_left">
						<ul class="info_list">
							<li>구성원의 업무수행 장점을 살리는 업무할당을 행한다</li>
							<li>구성원의 경력개발을 위한 조언과 자문에 충분한 시간을 할애한다</li>
							<li>구성원의 자기개발 활동에 관심을 갖고 기회가 될 때마다 격려한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>갈등관리</td>
					<td class="text_left">
						<ul class="info_list">
							<li>갈등 당사자 및 주변 사람들을 의견을 종합해서 갈등상황의 원인을 파악한다</li>
							<li>구성원간 갈등으로 부서 분위기 및 업무수행 차질 등 부정적 영향을 차단하여 근무 의욕을 유지한다</li>
							<li>갈등당사자들이 수용할 수 있는 갈등해결 방안을 제시한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>변화주도</td>
					<td class="text_left">
						<ul class="info_list">
							<li>환경변화에 대응하는 부서의 변화이슈를 찾고 구성원들과 공유한다</li>
							<li>부서내 변화의 방향을 설정하고, 변화계획을 구체적으로 수립한다</li>
							<li>부서 내 변화가 긍정적으로 전개될 수 있도록 모니터링하고 피드백 한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>공정성</td>
					<td class="text_left">
						<ul class="info_list">
							<li>회사의 원칙과 일관된 기준에 따라 판단하고, 결정한다</li>
							<li>동일한 기준으로 모든 구성원을 대하고, 강자의 논리에 주의하며 약자를 배려한다</li>
							<li>업무나 대인활동에서 판단 기준이나 이유를 구성원들이 알 수 있도록 설명한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td rowspan="4">협업</td>
					<td>선제적 대응</td>
					<td class="text_left">
						<ul class="info_list">
							<li>협력 업무와 관련된 잠재적인 이슈에 대하여 요청 자/부서와의 사전 공감대를 형성을 강조한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>이해조율</td>
					<td class="text_left">
						<ul class="info_list">
							<li>협력부서의 상황을 잘 이해하고 있으며 진행상 발생하는 이슈에 대하여 균형적이며 대안적인 해결책을 수용하려는 자세를 보인다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>업무이해 및 수용도</td>
					<td class="text_left">
						<ul class="info_list">
							<li>협력 업무에 대한 충분한 이해를 바탕으로 협력 업무 추진 및 기대수준 협의 시 적극적이며 우호적인 자세로 협조한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>업무협조</td>
					<td class="text_left">
						<ul class="info_list">
							<li>업무 협력 요청에 대체적으로 적시 및 정확한 업무 대응을 제공하고 있으며 후속적인 Follow up 을 위해 노력한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td rowspan="3">동기 부여</td>
					<td>역할 인식</td>
					<td class="text_left">
						<ul class="info_list">
							<li>리더로서 자신이 해야 할 임무와 역할을 정확히 인식하여 동기를  꺾는 일과  부여하는 일을 구분하여 실천한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>팀워크 강화</td>
					<td class="text_left">
						<ul class="info_list">
							<li>성과 창출을 위한 팀워크의 유지와 강화를 위해 힘쓴다</li>
							<li>목표 달성 과정에서 발생하는 팀워크 방해 요소를 적절히 제거하며 조직과 개인 모두의 성과 창출을 지원한다</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td>의욕고취</td>
					<td class="text_left">
						<ul class="info_list">
							<li>성과창출을 위한 핵심요인을 찾아내고 확보하는 방법을 구체적으로 도와 할 수 있다는 자신감을  불어넣는다</li>
						</ul>
					</td>
				</tr>
			</tbody>
		</table>
		<p class="info_disc">
			※ 진단항목은 진단자가 부하인 경우의 항목이며, 상사와 동료의 경우에는 다르게 표현된 항목이 있습니다.
		</p>
	</div>

	<div class="add_tabcon" style="display:block;">
		<span class="title">3. 다면진단 결과 종합</span>
		<p>귀하에 대한 2018년도 다면진단 결과를 종합해보면 다음과 같습니다.</p>
		<div class="result_box">
			<ul class="info_list">
				<c:if test="${etHeader201.COMMT1 ne null}">
					<li>귀하에 대한 다면진단 결과는 <span class="result"><c:out value="${etHeader201.COMMT1}" /></span>으로 나타나고 있습니다.</li>
				</c:if>
				<c:if test="${etHeader202.COMMT1 ne null}">
					<li>귀하에 대한 진단자 그룹별 진단수준은 <span class="result"><c:out value="${etHeader202.COMMT1}" /></span>의 순으로 나타나고 있습니다. </li>
				</c:if>
				<c:if test="${etHeader203.COMMT1 ne null}">
					<li>귀하에 대한 다면진단 결과는 전사 평균과 비교해서  <span class="result"><c:out value="${etHeader203.COMMT1}" /></span>
					<c:if test="${params.PERSG ne '1'}">
					, 사업장평균과 비교해서 <span class="result"><c:out value="${etHeader203.COMMT2}" /></span>
					</c:if>
					</li>
				</c:if>
			</ul>
		</div>
		<span class="title2">다면진단 결과</span>
		<div class="chart_box" style="height:520px;text-align: center;">
			<div id="chartSpider" style="width:600px;height:500px;display:inline-block;"></div>
		</div>
		<table class="contents_table">
			<colgroup>
				<col style="width:12%;">
				<col style="width:16%;">
				<col style="width:12%;">
				<col style="width:12%;">
				<col style="width:12%;">
				<col style="width:12%;">
				<col style="width:12%;">
				<col style="width:12%;">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">구분</th>
					<th rowspan="2" class="border_right_bold">진단요소</th>
					<th colspan="4" class="border_right_bold">나의 다면진단 결과</th>
					<th colspan="2">진단 평균</th>
				</tr>
				<tr>
					<th>상사</th>
					<th>동료</th>
					<th>부하</th>
					<th class="border_right_bold">종합점수</th>
					<th>사업장</th>
					<th>전사</th>
				</tr>
			</thead>
			<tbody id="chartSpiderData">
			<c:if test="${not empty etDetail1List}">
				<c:forEach var="result" items="${etDetail1List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail1List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center">${result.CPERFSC3}</td>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail2List}">
				<c:forEach var="result" items="${etDetail2List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail2List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center">${result.CPERFSC3}</td>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail3List}">
				<c:forEach var="result" items="${etDetail3List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail3List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center">${result.CPERFSC3}</td>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail4List}">
				<c:forEach var="result" items="${etDetail4List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail4List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center">${result.CPERFSC3}</td>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail5List}">
				<c:forEach var="result" items="${etDetail5List}" varStatus="status">
					<c:if test="${status.index eq 0}">
						<tr class="total">
							<th colspan="2" class="border_right_bold">${result.MRTEXT}</th>
							<td class="text_center">${result.CPERFSC1}</td>
							<td class="text_center">${result.CPERFSC2}</td>
							<td class="text_center">${result.CPERFSC3}</td>
							<td class="text_center border_right_bold">${result.CPERFSCT}</td>
							<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
							<td class="text_center">${result.CENTFSCT}</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
		<p class="info_disc">
			1. 종합점수는 진단자 그룹(상사, 동료, 부하)의 진단결과를 단순평균한 값을 의미함
		</p>
	</div>

	<div class="add_tabcon" style="display:block;">
		<span class="title">4. 다면진단 결과 차이비교 : ① 전사 및 소속사업장 평균과의 비교</span>
		<p>귀하에 대한 다면진단 결과를 전사 및 소속사업장의 평균과 비교해보면 다음과 같이 나타나고 있습니다. </p>
		<div class="result_box">
			<ul class="info_list">
				<c:if test="${params.PERSG ne '1' && etHeader301.COMMT1 ne null}">
					<li>소속사업장 평균과의 비교에서 귀하의 강점 요소는 <span class="result"><c:out value="${etHeader301.COMMT1}" /></span>, 개발필요 요소는 <span class="result"><c:out value="${etHeader301.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
				</c:if>
				<c:if test="${etHeader302.COMMT1 ne null}">
					<li>전사 평균과의 비교에서 귀하의 강점 요소는 <span class="result"><c:out value="${etHeader302.COMMT1}" /></span>, 개발필요 요소는 <span class="result"><c:out value="${etHeader302.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
				</c:if>
			</ul>
		</div>
		<span class="title2">다면진단 결과</span>
		<div class="chart_box" style="height:520px;">
			<c:if test="${params.PERSG ne '1'}">
				<div style="display:table;">
					<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">소속사업장<br>평균과의 비교</span>
					<div id="chartStic1" class="chart-placeholder" style="display:table-cell;"></div>
				</div>
			</c:if>
			<div style="display:table;">
				<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">전사<br>평균과의 비교</span>
				<div id="chartStic2" class="chart-placeholder" style="display:table-cell;"></div>
			</div>
		</div>
		<table class="contents_table">
			<colgroup>
				<col style="width:12%;">
				<col style="width:16%;">
				<col style="width:16%;">
				<col style="width:14%;">
				<col style="width:14%;">
				<col style="width:14%;">
				<col style="width:14%;">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">구분</th>
					<th rowspan="2" class="border_right_bold">진단요소</th>
					<th rowspan="2" class="border_right_bold">나의 종합점수<span class="num">①</span></th>
					<th colspan="2" class="border_right_bold">소속사업장 평균과의비교</th>
					<th colspan="2">전사 평균과의비교</th>
				</tr>
				<tr>
					<th>소속사업장<span class="num">②</span></th>
					<th class="border_right_bold">차이<span class="num">①-②</span></th>
					<th>전사<span class="num">②</span></th>
					<th>차이<span class="num">①-②</span></th>
				</tr>
			</thead>
			<tbody id="chartBar1Data">
		  	<c:if test="${not empty etDetail1List}">
				<c:forEach var="result" items="${etDetail1List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail1List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center border_right_bold"><c:if test="${params.PERSG ne '1'}">${result.CDLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
						<td class="text_center">${result.CDENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail2List}">
				<c:forEach var="result" items="${etDetail2List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail2List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center border_right_bold"><c:if test="${params.PERSG ne '1'}">${result.CDLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
						<td class="text_center">${result.CDENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail3List}">
				<c:forEach var="result" items="${etDetail3List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail3List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center border_right_bold"><c:if test="${params.PERSG ne '1'}">${result.CDLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
						<td class="text_center">${result.CDENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail4List}">
				<c:forEach var="result" items="${etDetail4List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail4List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center border_right_bold">${result.CPERFSCT}</td>
						<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
						<td class="text_center border_right_bold"><c:if test="${params.PERSG ne '1'}">${result.CDLOCFSCT}</c:if></td>
						<td class="text_center">${result.CENTFSCT}</td>
						<td class="text_center">${result.CDENTFSCT}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail5List}">
				<c:forEach var="result" items="${etDetail5List}" varStatus="status">
					<c:if test="${status.index eq 0}">
						<tr class="total">
							<th colspan="2" class="border_right_bold">${result.MRTEXT}</th>
							<td class="text_center border_right_bold">${result.CPERFSCT}</td>
							<td class="text_center"><c:if test="${params.PERSG ne '1'}">${result.CLOCFSCT}</c:if></td>
							<td class="text_center border_right_bold"><c:if test="${params.PERSG ne '1'}">${result.CDLOCFSCT}</c:if></td>
							<td class="text_center">${result.CENTFSCT}</td>
							<td class="text_center">${result.CDENTFSCT}</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
		<p class="info_disc">
			1. 나의 종합점수는 상사, 동료, 부하의 평균값을 단순 합산한 값을 의미하며, 협업과 동기부여의 진단요소의 경우에는 동료와 부하의 평균값을 단순 합산한 값을 의미함<br />
			2. 소속사업장 및 전사 평균은 종합점수 평균을 의미함
		</p>
	</div>

	<div class="add_tabcon" style="display:block;">
		<c:if test="${params.PERSG ne '1'}">
			<c:set var="compareTarget" value="소속사업장" />
			<c:set var="compareTargetText" value="소속사업장과" />
		</c:if>
		<c:if test="${params.PERSG eq '1'}">
			<c:set var="compareTarget" value="전사" />
			<c:set var="compareTargetText" value="전사와" />
		</c:if>
		<span class="title">4. 다면진단 결과 차이비교 : ② 진단요소별 진단자 그룹(상사, 동료, 부하)간 수준 차이 비교</span>
		<p>귀하에 대한 다면진단 결과에서 진단요소별로 진단자 그룹(상사, 동료, 부하)의 수준을 ${compareTarget}의 평균과 비교해 보면 다음과 같이 나타나고 있습니다. </p>
		<div class="result_box">
			<ul class="info_list">
				<c:if test="${etHeader401.COMMT1 ne null}">
					<li>상사의 진단결과를 ${compareTargetText} 비교해보면, 귀하의 강점 요소는 <span class="result"><c:out value="${etHeader401.COMMT1}" /></span>, 개발필요 요소는 <span class="result"><c:out value="${etHeader401.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
				</c:if>
				<c:if test="${etHeader402.COMMT1 ne null}">
					<li>동료의 진단결과를 ${compareTargetText} 비교해보면, 귀하의 강점 요소는 <span class="result"><c:out value="${etHeader402.COMMT1}" /></span>, 개발필요 요소는 <span class="result"><c:out value="${etHeader402.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
				</c:if>
				<c:if test="${etHeader403.COMMT1 ne null}">
					<li>부하의 진단결과를 ${compareTargetText} 비교해보면, 귀하의 강점 요소는 <span class="result"><c:out value="${etHeader403.COMMT1}" /></span>, 개발필요 요소는 <span class="result"><c:out value="${etHeader403.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
				</c:if>
			</ul>
		</div>
		<span class="title2">다면진단 결과</span>
		<div class="chart_box" style="height:620px;">
			<div style="display:table;">
				<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">상사의<br>진단결과</span>
				<div id="chartStic3" class="chart-placeholder2" style="display:table-cell;"></div>
			</div>
			<div style="display:table;">
				<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">동료의<br>진단결과</span>
				<div id="chartStic4" class="chart-placeholder2" style="display:table-cell;"></div>
			</div>
			<div style="display:table;">
				<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">부하의<br>진단결과</span>
				<div id="chartStic5" class="chart-placeholder2" style="display:table-cell;"></div>
			</div>
		</div>
		<table class="contents_table">
			<colgroup>
				<col style="width:12%;">
				<col style="width:16%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
				<col style="width:8%;">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">구분</th>
					<th rowspan="2" class="border_right_bold">진단요소</th>
					<th colspan="3" class="border_right_bold">다면진단 결과<span class="num">①</span></th>
					<th colspan="3" class="border_right_bold">${compareTarget} 평균<span class="num">②</span></th>
					<th colspan="3">차이<span class="num">①-②</span></th>
				</tr>
				<tr>
					<th>상사</th>
					<th>동료</th>
					<th class="border_right_bold">부하</th>
					<th>상사</th>
					<th>동료</th>
					<th class="border_right_bold">부하</th>
					<th>상사</th>
					<th>동료</th>
					<th>부하</th>
				</tr>
			</thead>
			<tbody id="chartBar2Data">
			<c:if test="${not empty etDetail1List}">
				<c:forEach var="result" items="${etDetail1List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail1List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center border_right_bold">${result.CPERFSC3}</td>
						<td class="text_center">${result.CLOCFSC1}</td>
						<td class="text_center">${result.CLOCFSC2}</td>
						<td class="text_center border_right_bold">${result.CLOCFSC3}</td>
						<td class="text_center">${result.CDLOCFSC1}</td>
						<td class="text_center">${result.CDLOCFSC2}</td>
						<td class="text_center">${result.CDLOCFSC3}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail2List}">
				<c:forEach var="result" items="${etDetail2List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail2List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center border_right_bold">${result.CPERFSC3}</td>
						<td class="text_center">${result.CLOCFSC1}</td>
						<td class="text_center">${result.CLOCFSC2}</td>
						<td class="text_center border_right_bold">${result.CLOCFSC3}</td>
						<td class="text_center">${result.CDLOCFSC1}</td>
						<td class="text_center">${result.CDLOCFSC2}</td>
						<td class="text_center">${result.CDLOCFSC3}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail3List}">
				<c:forEach var="result" items="${etDetail3List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail3List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center border_right_bold">${result.CPERFSC3}</td>
						<td class="text_center">${result.CLOCFSC1}</td>
						<td class="text_center">${result.CLOCFSC2}</td>
						<td class="text_center border_right_bold">${result.CLOCFSC3}</td>
						<td class="text_center">${result.CDLOCFSC1}</td>
						<td class="text_center">${result.CDLOCFSC2}</td>
						<td class="text_center">${result.CDLOCFSC3}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail4List}">
				<c:forEach var="result" items="${etDetail4List}" varStatus="status">
					<tr>
						<c:if test="${status.index eq 0}">
							<th rowspan="${fn:length(etDetail4List)}">${result.MRTEXT}</th>
						</c:if>
						<th class="border_right_bold">${result.MSTEXT}</th>
						<td class="text_center">${result.CPERFSC1}</td>
						<td class="text_center">${result.CPERFSC2}</td>
						<td class="text_center border_right_bold">${result.CPERFSC3}</td>
						<td class="text_center">${result.CLOCFSC1}</td>
						<td class="text_center">${result.CLOCFSC2}</td>
						<td class="text_center border_right_bold">${result.CLOCFSC3}</td>
						<td class="text_center">${result.CDLOCFSC1}</td>
						<td class="text_center">${result.CDLOCFSC2}</td>
						<td class="text_center">${result.CDLOCFSC3}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${not empty etDetail5List}">
				<c:forEach var="result" items="${etDetail5List}" varStatus="status">
					<c:if test="${status.index eq 0}">
						<tr class="total">
							<th colspan="2" class="border_right_bold">${result.MRTEXT}</th>
							<td class="text_center">${result.CPERFSC1}</td>
							<td class="text_center">${result.CPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPERFSC3}</td>
							<td class="text_center">${result.CLOCFSC1}</td>
							<td class="text_center">${result.CLOCFSC2}</td>
							<td class="text_center border_right_bold">${result.CLOCFSC3}</td>
							<td class="text_center">${result.CDLOCFSC1}</td>
							<td class="text_center">${result.CDLOCFSC2}</td>
							<td class="text_center">${result.CDLOCFSC3}</td>
						</tr>
					</c:if>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
	</div>

	<c:if test="${eSkippv eq ''}">
		<div class="add_tabcon" style="display:block;">
			<span class="title">4. 다면진단 결과 차이비교 : ③ 이전 다면진단 결과와의 비교</span>
			<p>귀하에 대한 다면진단 결과를 이전 진단결과와 비교해보면 다음과 같이 나타나고 있습니다. </p>
			<div class="result_box">
				<ul class="info_list">
					<c:if test="${etHeader501.COMMT1 ne null}">
						<li>상사의 다면진단 결과에서 뚜렷하게 향상된 요소는 <span class="result"><c:out value="${etHeader501.COMMT1}" /></span>, 저하된 요소는 <span class="result"><c:out value="${etHeader501.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
					</c:if>
					<c:if test="${etHeader502.COMMT1 ne null}">
						<li>동료의 다면진단 결과에서 뚜렷하게 향상된 요소는 <span class="result"><c:out value="${etHeader502.COMMT1}" /></span>, 저하된 요소는 <span class="result"><c:out value="${etHeader502.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
					</c:if>
					<c:if test="${etHeader503.COMMT1 ne null}">
						<li>부하의 다면진단 결과에서 뚜렷하게 향상된 요소는 <span class="result"><c:out value="${etHeader503.COMMT1}" /></span>, 저하된 요소는 <span class="result"><c:out value="${etHeader503.COMMT2}" /></span>로(으로) 나타나고 있습니다. </li>
					</c:if>
				</ul>
			</div>
			<span class="title2">다면진단 결과</span>
			<div class="chart_box" style="height:620px;">
				<div style="display:table;">
					<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">상사의<br>진단결과</span>
					<div id="chartStic6" class="chart-placeholder2" style="display:table-cell;"></div>
				</div>
				<div style="display:table;">
					<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">동료의<br>진단결과</span>
					<div id="chartStic7" class="chart-placeholder2" style="display:table-cell;"></div>
				</div>
				<div style="display:table;">
					<span class="title" style="display:table-cell; width:100px; vertical-align:middle; padding-bottom:20px;">부하의<br>진단결과</span>
					<div id="chartStic8" class="chart-placeholder2" style="display:table-cell;"></div>
				</div>
			</div>
			<table class="contents_table">
				<colgroup>
					<col style="width:12%;">
					<col style="width:16%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
					<col style="width:8%;">
				</colgroup>
				<thead>
					<tr>
						<th rowspan="2">구분</th>
						<th rowspan="2" class="border_right_bold">진단요소</th>
						<th colspan="3" class="border_right_bold">당해 년도<span class="num">①</span></th>
						<th colspan="3" class="border_right_bold">이전 년도<span class="num">②</span></th>
						<th colspan="3">차이<span class="num">①-②</span></th>
					</tr>
					<tr>
						<th>상사</th>
						<th>동료</th>
						<th class="border_right_bold">부하</th>
						<th>상사</th>
						<th>동료</th>
						<th class="border_right_bold">부하</th>
						<th>상사</th>
						<th>동료</th>
						<th>부하</th>
					</tr>
				</thead>
				<tbody id="chartBar3Data">
				<c:if test="${not empty etDetail1List}">
					<c:forEach var="result" items="${etDetail1List}" varStatus="status">
						<tr>
							<c:if test="${status.index eq 0}">
								<th rowspan="${fn:length(etDetail1List)}">${result.MRTEXT}</th>
							</c:if>
							<th class="border_right_bold">${result.MSTEXT}</th>
							<td class="text_center">${result.CPERFSC1}</td>
							<td class="text_center">${result.CPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPERFSC3}</td>
							<td class="text_center">${result.CPPERFSC1}</td>
							<td class="text_center">${result.CPPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPPERFSC3}</td>
							<td class="text_center">${result.CDPERFSC1}</td>
							<td class="text_center">${result.CDPERFSC2}</td>
							<td class="text_center">${result.CDPERFSC3}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${not empty etDetail2List}">
					<c:forEach var="result" items="${etDetail2List}" varStatus="status">
						<tr>
							<c:if test="${status.index eq 0}">
								<th rowspan="${fn:length(etDetail2List)}">${result.MRTEXT}</th>
							</c:if>
							<th class="border_right_bold">${result.MSTEXT}</th>
							<td class="text_center">${result.CPERFSC1}</td>
							<td class="text_center">${result.CPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPERFSC3}</td>
							<td class="text_center">${result.CPPERFSC1}</td>
							<td class="text_center">${result.CPPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPPERFSC3}</td>
							<td class="text_center">${result.CDPERFSC1}</td>
							<td class="text_center">${result.CDPERFSC2}</td>
							<td class="text_center">${result.CDPERFSC3}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${not empty etDetail3List}">
					<c:forEach var="result" items="${etDetail3List}" varStatus="status">
						<tr>
							<c:if test="${status.index eq 0}">
								<th rowspan="${fn:length(etDetail3List)}">${result.MRTEXT}</th>
							</c:if>
							<th class="border_right_bold">${result.MSTEXT}</th>
							<td class="text_center">${result.CPERFSC1}</td>
							<td class="text_center">${result.CPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPERFSC3}</td>
							<td class="text_center">${result.CPPERFSC1}</td>
							<td class="text_center">${result.CPPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPPERFSC3}</td>
							<td class="text_center">${result.CDPERFSC1}</td>
							<td class="text_center">${result.CDPERFSC2}</td>
							<td class="text_center">${result.CDPERFSC3}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${not empty etDetail4List}">
					<c:forEach var="result" items="${etDetail4List}" varStatus="status">
						<tr>
							<c:if test="${status.index eq 0}">
								<th rowspan="${fn:length(etDetail4List)}">${result.MRTEXT}</th>
							</c:if>
							<th class="border_right_bold">${result.MSTEXT}</th>
							<td class="text_center">${result.CPERFSC1}</td>
							<td class="text_center">${result.CPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPERFSC3}</td>
							<td class="text_center">${result.CPPERFSC1}</td>
							<td class="text_center">${result.CPPERFSC2}</td>
							<td class="text_center border_right_bold">${result.CPPERFSC3}</td>
							<td class="text_center">${result.CDPERFSC1}</td>
							<td class="text_center">${result.CDPERFSC2}</td>
							<td class="text_center">${result.CDPERFSC3}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${not empty etDetail5List}">
					<c:forEach var="result" items="${etDetail5List}" varStatus="status">
						<c:if test="${status.index eq 0}">
							<tr class="total">
								<th colspan="2" class="border_right_bold">${result.MRTEXT}</th>
								<td class="text_center">${result.CPERFSC1}</td>
								<td class="text_center">${result.CPERFSC2}</td>
								<td class="text_center border_right_bold">${result.CPERFSC3}</td>
								<td class="text_center">${result.CPPERFSC1}</td>
								<td class="text_center">${result.CPPERFSC2}</td>
								<td class="text_center border_right_bold">${result.CPPERFSC3}</td>
								<td class="text_center">${result.CDPERFSC1}</td>
								<td class="text_center">${result.CDPERFSC2}</td>
								<td class="text_center">${result.CDPERFSC3}</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
				</tbody>
			</table>
		</div>
	</c:if>

	<div class="add_tabcon" style="display:block;">
		<span class="title">5. 개발 필요역량</span>
		<p>귀하의 다면진단 결과를 토대로 다음과 같은 개발필요 요소를 도출하였으며, 상위 3개를 개발영역으로 추천합니다.</p>

		<span class="title2">진단결과 종합</span>
		<div class="total_box">
			<div class="result_box">
				<ul class="info_list">
					<c:if test="${etHeader601.COMMT1 ne null}">
						<li>귀하의 다면진단 결과는 <span class="result"><c:out value="${etHeader601.COMMT1}" /></span>로(으로) 나타나고 있습니다. </li>
					</c:if>
					<c:if test="${etHeader602.COMMT1 ne null}">
						<li>귀하에 대한 진단자그룹(상사, 동료, 부하)의 수준은 <span class="result"><c:out value="${etHeader602.COMMT1}" /></span>의 순으로 높게 나타나고 있습니다.</li>
					</c:if>
					<c:if test="${params.PERSG ne '1' && etHeader603.COMMT1 ne null}">
						<li>사업장 평균과 비교해서 귀하의 수준은 전체적으로 <span class="result"><c:out value="${etHeader603.COMMT1}" /></span>으로 나타나고 있습니다.</li>
					</c:if>
					<c:if test="${eSkippv eq '' && etHeader604.COMMT1 ne null}">
						<li>이전 년도와 비교해서 전체적으로 <span class="result"><c:out value="${etHeader604.COMMT1}" /></span> 것으로 나타나고 있습니다.</li>
					</c:if>
				</ul>
			</div>
			<div class="table_box">
				<table class="contents_table">
				  <colgroup>
				    <col style="width:20%;">
				    <col style="width:20%;">
				    <col style="width:30%;">
				    <col style="width:30%;">
				  </colgroup>
					<tbody>
						<tr>
							<th colspan="2">차이 비교 ①</th>
							<th>강점요소</th>
							<th>개발필요 요소</th>
						</tr>
					    <tr class="border_top_bold">
							<th rowspan="3">진단요소별 사업장 평균과 비교</th>
							<th class="border_right_bold">상사의 관점</th>
							<td class="text_center"><span class="result">${etHeader605.COMMT1}</span></td>
							<td class="text_center"><span class="result">${etHeader605.COMMT2}</span></td>
					    </tr>
					    <tr>
							<th class="border_right_bold">동료의 관점</th>
							<td class="text_center"><span class="result">${etHeader606.COMMT1}</span></td>
							<td class="text_center"><span class="result">${etHeader606.COMMT2}</span></td>
					    </tr>
					    <tr>
							<th class="border_right_bold">부하의 관점</th>
							<td class="text_center"><span class="result">${etHeader607.COMMT1}</span></td>
							<td class="text_center"><span class="result">${etHeader607.COMMT2}</span></td>
					    </tr>
						<tr class="border_top_bold">
							<th colspan="2">차이 비교 ②</th>
							<th>강점요소</th>
							<th>개발필요 요소</th>
						</tr>
					    <tr class="border_top_bold">
							<th rowspan="2">소속사업장 및 전사 평균과의 비교</th>
							<th class="border_right_bold">소속사업장</th>
							<td class="text_center"><span class="result"><c:if test="${params.PERSG ne '1'}">${etHeader608.COMMT1}</c:if></span></td>
							<td class="text_center"><span class="result"><c:if test="${params.PERSG ne '1'}">${etHeader608.COMMT2}</c:if></span></td>
					    </tr>
					    <tr>
							<th class="border_right_bold">전사</th>
							<td class="text_center"><span class="result">${etHeader609.COMMT1}</span></td>
							<td class="text_center"><span class="result">${etHeader609.COMMT2}</span></td>
					    </tr>
						<tr class="border_top_bold">
							<th colspan="2">차이 비교 ③</th>
							<th>향상된 요소</th>
							<th>저하된 요소</th>
						</tr>
					    <tr class="border_top_bold">
							<th rowspan="3">이전 년도 진단결과와의 비교</th>
							<th class="border_right_bold">상사의 관점</th>
							<td class="text_center"><span class="result"><c:if test="${eSkippv eq ''}">${etHeader610.COMMT1}</c:if></span></td>
							<td class="text_center"><span class="result"><c:if test="${eSkippv eq ''}">${etHeader610.COMMT2}</c:if></span></td>
					    </tr>
					    <tr>
							<th class="border_right_bold">동료의 관점</th>
							<td class="text_center"><span class="result"><c:if test="${eSkippv eq ''}">${etHeader611.COMMT1}</c:if></span></td>
							<td class="text_center"><span class="result"><c:if test="${eSkippv eq ''}">${etHeader611.COMMT2}</c:if></span></td>
					    </tr>
					    <tr>
							<th class="border_right_bold">부하의 관점</th>
							<td class="text_center"><span class="result"><c:if test="${eSkippv eq ''}">${etHeader612.COMMT1}</c:if></span></td>
							<td class="text_center"><span class="result"><c:if test="${eSkippv eq ''}">${etHeader612.COMMT2}</c:if></span></td>
					    </tr>
					</tbody>
				</table>
			</div>
		</div>

		<span class="title2">개발필요 요소 종합</span>
		<div class="chart_box total">
			<c:if test="${eSkippv eq ''}">
				${etHeader613.COMMT1}
			</c:if>
			<c:if test="${eSkippv ne ''}">
				${etHeader610.COMMT1}
			</c:if>
		</div>
	</div>

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