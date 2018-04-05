<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<style>

textarea {
	overflow-y : hidden;
}

</style>

<script type="text/javascript">
(function($){
	$(document).ready(function() {

		$.hideEduComponent = function() {
			$('#requalTable, #hpqualTable').find('tbody > tr').each(function() {
				if($(this).find('[name=idedut]').val() == '') {
					$(this).find('[name=idedut]').hide();
					$(this).find('[name=idedux]').removeClass('margintop_10');
				}
				if($(this).find('[name=idedux]').val() == '') $(this).find('[name=idedux]').hide();
			});
		}

		$.bindButtonPrintHandler = function() {
			$('#btnPrint').click(function(e) {
				e.preventDefault();

				var $inputElem = $('<input type="hidden" />');
				var $printForm = $('#ajaxForm');

				$printForm.empty();
				$printForm.append($inputElem.clone().attr('name', 'AYEAR').val('${idplst.AYEAR}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGEH').val('${idplst.ORGEH}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGTX').val('${idplst.ORGTX}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPAE').val('${idplst.IDPAE}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPAENM').val('${idplst.IDPAENM}'));
				$printForm.append($inputElem.clone().attr('name', 'PERSG').val('${idplst.PERSG}'));
				$printForm.append($inputElem.clone().attr('name', 'STELL').val('${idplst.STELL}'));
				$printForm.append($inputElem.clone().attr('name', 'STLTX').val('${idplst.STLTX}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK').val('${idplst.ZZJIK}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT').val('${idplst.COTXT}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR').val('${idplst.TRFGR}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPAR').val('${idplst.IDPAR}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPARNM').val('${idplst.IDPARNM}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK1').val('${idplst.ZZJIK1}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT1').val('${idplst.COTXT1}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR1').val('${idplst.TRFGR1}'));
				$printForm.append($inputElem.clone().attr('name', 'IDSTS').val('${idplst.IDSTS}'));
				$printForm.append($inputElem.clone().attr('name', 'IDSTSX').val('${idplst.IDSTSX}'));
				$printForm.append($inputElem.clone().attr('name', 'SRTKY').val('${idplst.SRTKY}'));
				$printForm.append($inputElem.clone().attr('name', 'MODIF').val('${idplst.MODIF}'));

				$printForm.attr("method", "post");
				$printForm.attr("action", "<c:url value='/portal/evaluation/idp/idpSettingViewPrint.do'/>");

				$.CallPrintPagePopup();

				$printForm.empty();
			});
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function() {
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/idp/idpHSS.do'/>",
					PARAM_EMPNO : "${params.empno}",
					PARAM_USERID : "${params.userId}"
				});
			});
		}

		$.initView = function() {
			$.bindButtonPrintHandler();
			$.bindButtonBackHandler();

 			$('#requalTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#hpqualTable').find('textarea').each(function() { $(this).AutoHeightResizeTextarea(); });

			$.hideEduComponent();

			$.DisabledBackgroundStyleChange();
		};

		$.initView();

	});
})(jQuery);
</script>

<div id="wrap">

	<h1 class="title">IDP (${params.AYEAR}년)</h1>

	<div class="btn_wrap left margintop_20" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnPrint" title="출력" href=""><span>출력</span></a>
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
				<td colspan="3"><input type="text" name="idcgl" class="width_100" value="${crjob.IDCGL}" disabled /></td>
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