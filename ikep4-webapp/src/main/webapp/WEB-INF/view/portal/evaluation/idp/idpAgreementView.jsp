<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<style>

.mask {
	position : absolute;
	left : 0;
	top : 0;
	z-index : 9999;
	background-color : #000;
	display : none;
}

.eva_popup {
	width : 500px;
	overflow-y : auto;
	background-color : #ffffff;
	z-index : 99999;
	display : none;
}

textarea {
	overflow-y : hidden;
}

</style>

<script type="text/javascript">
(function($){
	$(document).ready(function() {

		$.fn.setLayer = function(data) {
			var _this = $(this);
			_this.find('.btn_close').unbind().click(function(e) {
				e.preventDefault();
				_this.closeLayer();
			});
			$('#layer_title_txt').text(data.title);
			$('#layer_button_txt').text(data.button).parent().attr('title', data.button);
			if(typeof data.func == 'function') {
				$('#layer_button_txt').parent().unbind();
				$('#layer_button_txt').parent().click(function(e) {
					e.preventDefault();
					data.func.call();
				});
				$('#layer_button_txt').parent().next().unbind();
				$('#layer_button_txt').parent().next().click(function(e) {
					e.preventDefault();
					_this.closeLayer();
				});
			}
		}

		$.fn.closeLayer = function() {
			$('[name=comment]').val('');
			$('.mask').hide();
			$(this).hide();
		}

		$.fn.showLayer = function() {
			var maskHeight = $(document).height();
	        var maskWidth = $(window).width();

	        $('.mask').css({'width':maskWidth,'height':maskHeight});
	        $('.mask').fadeIn(1000);
	        $('.mask').fadeTo("slow",0.8);

	        var left = 250;
	        var top = 150;
	        $(this).css({'left':left,'top':top, 'position':'absolute'});

	        $(this).draggable();
	        $(this).show();
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function() {
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/idp/idpList.do'/>",
					PARAM_ACTION : "IDP_AGREEMENT",
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

		$.initView = function() {
			$('#btnOrder').hide();

			$.bindButtonBackHandler();
			$.bindButtonAgreementHandler();
			$.bindButtonRefuseHandler();

 			$('#requalTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#hpqualTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function() {
			$('#btnAgreement').unbind().hide();
			$('#btnRefuse').unbind().hide();

			$('#btnOrder').show();
			$.bindButtonOrderHandler();
		};

		$.makeFormData = function(button) {
			var formData = $('#idpForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'comment', value:$('[name=comment]').val()});
			formData.push({name:'ayear', value:'${idplst.AYEAR}'});
			formData.push({name:'orgeh', value:'${idplst.ORGEH}'});
			formData.push({name:'orgtx', value:'${idplst.ORGTX}'});
			formData.push({name:'idpae', value:'${idplst.IDPAE}'});
			formData.push({name:'idpaenm', value:'${idplst.IDPAENM}'});
			formData.push({name:'persg', value:'${idplst.PERSG}'});
			formData.push({name:'stell', value:'${idplst.STELL}'});
			formData.push({name:'stltx', value:'${idplst.STLTX}'});
			formData.push({name:'zzjik', value:'${idplst.ZZJIK}'});
			formData.push({name:'cotxt', value:'${idplst.COTXT}'});
			formData.push({name:'trfgr', value:'${idplst.TRFGR}'});
			formData.push({name:'idpar', value:'${idplst.IDPAR}'});
			formData.push({name:'idparnm', value:'${idplst.IDPARNM}'});
			formData.push({name:'zzjik1', value:'${idplst.ZZJIK1}'});
			formData.push({name:'cotxt1', value:'${idplst.COTXT1}'});
			formData.push({name:'trfgr1', value:'${idplst.TRFGR1}'});
			formData.push({name:'idsts', value:'${idplst.IDSTS}'});
			formData.push({name:'idstsx', value:'${idplst.IDSTSX}'});
			formData.push({name:'idstsdl', value:'${idplst.IDSTSDL}'});
			formData.push({name:'srtky', value:'${idplst.SRTKY}'});
			formData.push({name:'modif', value:'${idplst.MODIF}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/idp/idpAgreementProc.do'/>"
				, type : "post"
				, data : $.makeFormData(params.button)
				, success : function(result) {
					if(params.isCloseDialog) $("#refuseReasonDiv").closeLayer();

					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);

						$.stopProgress();
					} else {
						alert(params.sucessMsg);
						$.CallListPage({
							SUBMIT_ACTION : "<c:url value='/portal/evaluation/idp/idpList.do'/>",
							PARAM_ACTION : "IDP_AGREEMENT",
							PARAM_PAGE_NUM : "${params.currentPage}"
						});
					}
				}
				, error : function(xhr, exMessage) {
					if(params.isCloseDialog) $("#refuseReasonDiv").closeLayer();

					AjaxError.showAlert(xhr, exMessage);

					$.stopProgress();
				}
			});
		}

		$.initView();

		<c:if test="${params.IDSTS eq '3'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">IDP (${params.AYEAR}년)</h1>

	<div class="btn_wrap left margintop_20" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnAgreement" title="합의" href="#"><span>합의</span></a>
		<a class="btn_common" id="btnRefuse" title="반려" href="#"><span>반려</span></a>
		<a class="btn_common" id="btnOrder" title="수정지시" href=""><span>수정지시</span></a>
	</div>

	<table class="list_table">
		<colgroup>
			<col style="width: 15%;">
			<col style="width: 35%;">
			<col style="width: 15%;">
			<col style="width: 35%;">
		</colgroup>
		<tbody>
			<tr>
				<th>소속</th>
				<td class="text_left">${idplst.ORGTX}</td>
				<th>사번/성명</th>
				<td class="text_left">${idplst.IDPAENM}(${idplst.IDPAE})</td>
			</tr>
			<tr>
				<th>직급</th>
				<td class="text_left">${idplst.TRFGR}</td>
				<th>직무</th>
				<td class="text_left">${idplst.STLTX}</td>
			</tr>
		</tbody>
	</table>

	<form id="idpForm" name="idpForm" method="post" action="<c:url value='/portal/evaluation/idp/idpProc.do'/>">
	<h2 class="title margintop_20">경력목표 및 희망직무</h2>
	<table class="contents_table" id="crjobTable">
		<colgroup>
		    <col style="width:10%;">
		    <col style="width:10%;">
		    <col style="width:40%;">
		    <col style="width:40%;">
		</colgroup>
		<tbody>
			<tr>
				<th>경력목표</th>
				<td colspan="3"><input type="text" name="idcgl" disabled class="width_100" value="${crjob.IDCGL}" /></td>
			</tr>
			<tr>
				<th rowspan="3">희망이동<br />(직무/부서)
				</th>
				<th>1순위</th>
				<td>
					<input type="text" name="hpjob1t" class="width_100" value="${crjob.HPJOB1T}" disabled />
				</td>
				<td>
					<input type="text" name="hporg1t" class="width_100" value="${crjob.HPORG1T}" disabled />
				</td>
			</tr>
			<tr>
				<th>2순위</th>
				<td>
					<input type="text" name="hpjob2t" class="width_100" value="${crjob.HPJOB2T}" disabled />
				</td>
				<td>
					<input type="text" name="hporg2t" class="width_100" value="${crjob.HPORG2T}" disabled />
				</td>
			</tr>
			<tr>
				<th>3순위</th>
				<td>
					<input type="text" name="hpjob3t" class="width_100" value="${crjob.HPJOB3T}" disabled />
				</td>
				<td>
					<input type="text" name="hporg3t" class="width_100" value="${crjob.HPORG3T}" disabled />
				</td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20">자기개발계획</h2>

	<h3 class="title">
		1. 필수개발역량
	</h3>
	<table class="contents_table" id="requalTable">
		<colgroup>
		    <col style="width:18%;" />
		    <col style="" />
		    <col style="width:20%;" />
		    <col style="width:80px;" />
		    <col style="width:20%;" />
		</colgroup>
		<thead>
			<tr>
				<th>개발목표(역량)</th>
				<th>개발계획 및 활동내용</th>
				<th>교육명(과정명)</th>
				<th>기간</th>
				<th>수행결과</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty requalList}">
				<c:forEach var="result" items="${requalList}">
					<tr>
						<td valign="top">
							<input type="text" name="idobjt" class="width_100" value="${result.IDOBJT}" disabled />
						</td>
						<td>
							<textarea name="iddes" class="width_100" disabled rows="4"><c:out value="${result.IDDES}" /></textarea>
						</td>
						<td class="padding_0">
							<p class="idp_edu1">
								<span class="edu_label" style="width:25%;">교육명</span>
								<span class="edu_input">
									<input type="text" name="idedut" class="width_100" value="${result.IDEDUT}" disabled />
								</span>
							</p>
							<p class="idp_edu2">
								<span class="edu_label" style="width:25%;">과정명</span>
								<span class="edu_input">
									<input type="text" name="idedux" class="width_100 margintop_10" value="${result.IDEDUX}" disabled />
								</span>
							</p>
						</td>
						<td valign="top">
							<input type="text" name="idedpd" class="width_100" value="${result.IDEDPD}월" style="text-align:center;" disabled />
						</td>
						<td>
							<textarea name="idedrt" class="width_100" disabled rows="4"><c:out value="${result.IDEDRT}" /></textarea>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h3 class="title margintop_20">
		2. 희망직무지식
	</h3>
	<table class="contents_table" id="hpqualTable">
		<colgroup>
			<col style="width: 18%;" />
			<col style="" />
			<col style="width: 20%;" />
			<col style="width: 80px;" />
			<col style="width: 20%;" />
		</colgroup>
		<thead>
			<tr>
				<th>개발목표(직무)</th>
				<th>개발계획 및 활동내용</th>
				<th>교육명(과정명)</th>
				<th>기간</th>
				<th>수행결과</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty hpqualList}">
				<c:forEach var="result" items="${hpqualList}">
					<tr>
						<td valign="top">
							<input type="text" name="idobjt" class="width_100" value="${result.IDOBJT}" disabled />
						</td>
						<td>
							<textarea name="iddes" class="width_100" disabled rows="4"><c:out value="${result.IDDES}" /></textarea>
						</td>
						<td class="padding_0">
							<p class="idp_edu1">
								<span class="edu_label" style="width:25%;">교육명</span>
								<span class="edu_input">
									<input type="text" name="idedut" class="width_100" value="${result.IDEDUT}" disabled />
								</span>
							</p>
							<p class="idp_edu2">
								<span class="edu_label" style="width:25%;">과정명</span>
								<span class="edu_input">
									<input type="text" name="idedux" class="width_100 margintop_10" value="${result.IDEDUX}" disabled />
								</span>
							</p>
						</td>
						<td valign="top">
							<input type="text" name="idedpd" class="width_100" value="${result.IDEDPD}월" style="text-align:center;" disabled />
						</td>
						<td>
							<textarea name="idedrt" class="width_100" disabled rows="4"><c:out value="${result.IDEDRT}" /></textarea>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	</form>

	<form id="ajaxForm" name="ajaxForm" method="post"></form>

</div>

<div class="mask"></div>

<div class="eva_popup" id="refuseReasonDiv">
	<div class="popup_header">
		<span class="title" id="layer_title_txt">반려내역</span>
		<a href="#" class="btn_close">닫기</a>
	</div>
	<div class="popup_contents">
		<textarea name="comment" class="width_100" rows="5"></textarea>
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