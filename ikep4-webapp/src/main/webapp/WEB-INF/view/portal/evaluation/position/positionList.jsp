<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<style>
	.mask {
		 position : absolute;
		 opacity : 0.1;
		 left : 0;
		 top : 0;
		 z-index : 9999;
		 background-color : #000000;
		 display : none;
	}

	.eva_popup {
		width : 300px;
		background-color : #ffffff;
		z-index : 99999;
		display : none;
	}
</style>

<script type="text/javascript">
var boardList = [];
(function($){

	Date.prototype.yyyymmdd = function() {
		var mm = this.getMonth() + 1;
		var dd = this.getDate();

		return [this.getFullYear(),
				(mm>9 ? '' : '0') + mm,
				(dd>9 ? '' : '0') + dd
		].join('');
	};

	$(document).ready(function() {

		<c:if test="${not empty etList}">
			<c:forEach var="result" varStatus="varStatus" items="${etList}">
				boardList.push({
					ORGEH:"${result.ORGEH}",
					ORGTX:"${result.ORGTX}",
					PERNR:"${result.PERNR}",
					ENAME:"${result.ENAME}",
					PERSG:"${result.PERSG}",
					PERSK:"${result.PERSK}",
					BTRTL:"${result.BTRTL}",
					ZZJIK:"${result.ZZJIK}",
					COTXT:"${result.COTXT}",
					TRFGR:"${result.TRFGR}",
					STELL:"${result.STELL}",
					STLTX:"${result.STLTX}",
					PLANS:"${result.PLANS}",
					PLSTX:"${result.PLSTX}",
					CPLSTX:"${result.CPLSTX}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}",
					MSGTY:"${result.MSGTY}",
					MSGTX:"${result.MSGTX}",
					NUMBR:"${result.NUMBR}"
				});
			</c:forEach>
		</c:if>

		$.initSet = function() {
			if('${esReturn.MSGTY}' == 'W') {
				$.viewMode();
				alert('${esReturn.MSGTX}');

				return false;
			}

			$.bindButtionSaveHandler();
			$.bindButtonChangeHandler();
			$.bindButtonProcessHandler();
			$.bindButtonCloseHandler();
			$.bindButtonCloseIconHandler();
			$('.btnRetrieveAppoint').each(function() { $(this).bindButtonAppointPopupHandler(); });

			$.bindChangeOrgehHandler();

			$('[name=iAcdat]').datepicker({minDate : 0});
		};

		$.viewMode = function() {
			$('#btnSave').unbind().hide();
			$('#btnComplete').unbind().hide();
		};

		$.fn.closeLayer = function() {
			$('[name=iAcdat]').val('');
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

		$.pageReload = function() {
			$("#ajaxForm").append("<input type=\"hidden\" name=\"orgeh\" value=\"" + $('[name=iOrgeh]').val() + "\"/>");
			$("#ajaxForm")
				.attr("method", "post")
				.attr("action", "<c:url value='/portal/evaluation/position/positionList.do'/>")
				.submit();
		}

		$.makeFormData = function(button) {
			var formData = [];

			formData.push({name:'iOrgeh', value:$('[name=iOrgeh]').val()});
			if($('[name=iAcdat]').val()) formData.push({name:'iAcdat', value:$('[name=iAcdat]').datepicker("getDate").yyyymmdd()});
			formData.push({name:'button', value:button});
			$.each(boardList, function(index, item) {
				var cplstx = $('.list_table > tbody > tr').eq(index).find('[name=cplstx]').val();

				formData.push({name:'positions['+index+'].orgeh', value:item.ORGEH});
				formData.push({name:'positions['+index+'].orgtx', value:item.ORGTX});
				formData.push({name:'positions['+index+'].pernr', value:item.PERNR});
				formData.push({name:'positions['+index+'].ename', value:item.ENAME});
				formData.push({name:'positions['+index+'].persg', value:item.PERSG});
				formData.push({name:'positions['+index+'].persk', value:item.PERSK});
				formData.push({name:'positions['+index+'].btrtl', value:item.BTRTL});
				formData.push({name:'positions['+index+'].zzjik', value:item.ZZJIK});
				formData.push({name:'positions['+index+'].cotxt', value:item.COTXT});
				formData.push({name:'positions['+index+'].trfgr', value:item.TRFGR});
				formData.push({name:'positions['+index+'].stell', value:item.STELL});
				formData.push({name:'positions['+index+'].stltx', value:item.STLTX});
				formData.push({name:'positions['+index+'].plans', value:item.PLANS});
				formData.push({name:'positions['+index+'].plstx', value:item.PLSTX});
				formData.push({name:'positions['+index+'].cplstx', value:$.trim(cplstx)});
				formData.push({name:'positions['+index+'].srtky', value:item.SRTKY});
				formData.push({name:'positions['+index+'].modif', value:item.MODIF});
				formData.push({name:'positions['+index+'].msgty', value:item.MSGTY});
				formData.push({name:'positions['+index+'].msgtx', value:item.MSGTX});
				formData.push({name:'positions['+index+'].numbr', value:item.NUMBR});
			});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/position/positionProc.do'/>"
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

		$.bindChangeOrgehHandler = function() {
			$('[name=iOrgeh]').change(function() {
				$.callProgress();

				$.pageReload();
			});
		}

		$.bindButtionSaveHandler = function() {
			$('#btnSave').click(function(e) {
				e.preventDefault();

				if($('.list_table').find('.emptyRecord').length) {
					return false;
				}

				if(confirm("저장하시겠습니까?")) {
					$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : true});
				}
			});
		}

		$.isAllInputWrite = function() {
			var isAllWrite = true;

			$('[name=cplstx]').each(function() { if(!$(this).val()) isAllWrite = false; });

			return isAllWrite;
		}

		$.isInputChange = function() {
			var isChange = false;

			var curText, changeText;
			$('[name=cplstx]').each(function() {
				curText = $(this).parent().prev().text();
				changeText = $(this).val();
				if($.trim(curText) != $.trim(changeText)) isChange = true;
			});

			return isChange;
		}

		$.bindButtonChangeHandler = function() {
			$('#btnChange').click(function(e) {
				e.preventDefault();

				if($('.list_table').find('.emptyRecord').length) {
					return false;
				}

				if(!$.isAllInputWrite()) {
					alert("전체 Column에 대해 값을 입력하십시오.");
					return false;
				}

				if(!$.isInputChange()) {
					alert("변경된 내역이 없습니다.");
					return false;
				}

				$('#changeDiv').showLayer();
			});
		}

		$.bindButtonProcessHandler = function() {
			$('#btnProc').click(function(e) {
				e.preventDefault();

				if($('[name=iAcdat]').val() == '') {
					alert("변경일자를 선택하세요.");
					return false;
				}

				if(confirm("입력된 날자 기준으로 포지션 변경 발령과 함께 \n구성원의 포지션 명칭이 변경됩니다.\n계속하시겠습니까?")) {
					$.businessProcess({button : 'COMP', sucessMsg : '완료되었습니다.', isCallList : true});
				}
			});
		}

		$.fn.bindButtonAppointPopupHandler = function() {
			$(this).click(function(e) {
				e.preventDefault();

				var url = iKEP.getContextRoot() + "/portal/evaluation/position/popAppointList.do?empno=" + $(this).data('eno');
				iKEP.popupOpen(url, {width:900, height:700, scrollbar:true}, "appointListPopup");
			});
		}

		$.bindButtonCloseHandler = function() {
			$('#btnClose').click(function(e) {
				e.preventDefault();

				$('#changeDiv').closeLayer();
			});
		}

		$.bindButtonCloseIconHandler = function() {
			$('#btnCloseIcon').click(function(e) {
				e.preventDefault();

				$('#changeDiv').closeLayer();
			});
		}

		$.initSet();

		<c:if test="${evRvsts eq '2'}">
		$.viewMode();
		</c:if>

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">팀원 포지션 변경</h1>

	<div class="btn_wrap left">
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnChange" title="포지션 변경 발령" href="#"><span>포지션 변경 발령</span></a>
	</div>

	<hr />

	<h2 class="title margintop_20">
		<select name="iOrgeh">
			<c:if test="${not empty etOrgeh}">
				<c:forEach var="result" items="${etOrgeh}">
					<option value="${result.OBJID}" <c:if test="${result.OBJID eq paramsOrgeh}">selected</c:if>>${result.STEXT}</option>
				</c:forEach>
			</c:if>
		</select>
	</h2>
	<table class="list_table">
		<colgroup>
			<col style="width:;">
			<col style="width:10%;">
			<col style="width:10%;">
			<col style="width:10%;">
			<col style="width:22%;">
			<col style="width:22%;">
			<col style="width:8%;">
		</colgroup>
		<thead>
			<tr>
				<th rowspan="2">소속</th>
				<th rowspan="2">성명</th>
				<th rowspan="2">직급</th>
				<th rowspan="2">직무</th>
				<th colspan="2">포지션</th>
				<th rowspan="2">발령사항</th>
			</tr>
			<tr>
				<th>현재</th>
				<th>변경</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty etList}">
					<tr>
						<td colspan="7" class="emptyRecord center">내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${etList}">
						<tr>
							<td>${result.ORGTX}</td>
							<td>${result.ENAME}</td>
							<td>${result.TRFGR}</td>
							<td>${result.STLTX}</td>
							<td>${result.PLSTX}</td>
							<td><input type="text" name="cplstx" class="width_100" value="${result.CPLSTX}" maxlength="20" /></td>
							<td><a class="btn_common2 btnRetrieveAppoint" data-eno="${result.PERNR}" href="#"><span>조회</span></a></td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>

	<div class="mask"></div>
	<div class="eva_popup" id="changeDiv"> <!-- 팝업 띄울때 width:300px -->
		<div class="popup_header">
			<span class="title">포지션 변경 발령일</span>
			<a href="#" id="btnCloseIcon" class="btn_close">닫기</a>
		</div>
		<div class="popup_contents">
			<table class="contents_table">
				<colgroup>
					<col style="width:30%;">
					<col style="width:70%;">
				</colgroup>
				<tbody>
					<tr>
						<th>발령일</th>
						<td>
							<input type="text" name="iAcdat" class="width_100" />
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btn_wrap right">
				<a class="btn_common" id="btnProc" title="실행" href="#"><span>실행</span></a>
				<a class="btn_common" id="btnClose" title="닫기" href="#"><span>닫기</span></a>
			</div>
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