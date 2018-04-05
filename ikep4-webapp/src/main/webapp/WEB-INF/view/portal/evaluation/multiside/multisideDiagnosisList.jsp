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
					ORGEH:"${result.ORGEH}",
					ORGTX:"${result.ORGTX}",
					MAPPE:"${result.MAPPE}",
					MAPENM:"${result.MAPENM}",
					PERSG:"${result.PERSG}",
					ZZJIK:"${result.ZZJIK}",
					COTXT:"${result.COTXT}",
					TRFGR:"${result.TRFGR}",
					MAPPR:"${result.MAPPR}",
					MAPRNM:"${result.MAPRNM}",
					ZZJIK1:"${result.ZZJIK1}",
					COTXT1:"${result.COTXT1}",
					TRFGR1:"${result.TRFGR1}",
					MSGRP:"${result.MSGRP}",
					MSGRPX:"${result.MSGRPX}",
					MSGRPDL:"${result.MSGRPDL}",
					MSSTS:"${result.MSSTS}",
					MSSTSX:"${result.MSSTSX}",
					MSSTSDL:"${result.MSSTSDL}",
					AYEAR:"${result.AYEAR}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}"
				});
			</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			formData.push({name : "rfc", value : "MULTISIDE_DETAIL"});

			return formData;
		}

		$.setParam = function(data) {
			$.each(data, function(key, value) {
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$("#ajaxForm").append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/multiside/multisideCheckView.do'/>"
				, type : "post"
				, data : $.makeFormData(boardList[index])
				, success : function(result) {
					if(result.MSGTY == 'W' || result.MSGTY == 'E') {
						alert(result.MSGTX);
						$.stopProgress();
						return false;
					} else {
						$.setParam(boardList[index]);

						$("#ajaxForm").attr("method", "post");
						$("#ajaxForm").attr("action", "<c:url value='/portal/evaluation/multiside/multisideDiagnosisView.do'/>");
						$("#ajaxForm").submit();
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);
					$.stopProgress();
				}
			});
		})
		.mouseover(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;
			$(this).addClass('line_focus');
		});

		$("a[name=page]").click(function() {
			$.callProgress();
			$("#evaluationForm").find("input[name=currentPage]").val($(this).data("page"));
			$("#evaluationForm").submit();
		});

		$.initSet = function() {
			if('${esReturn.MSGTY}' == 'W' || '${esReturn.MSGTY}' == 'E') {
				alert('${esReturn.MSGTX}');

				return false;
			}

			$("input[name=currentPage]").val("<c:out value="${params.currentPage}"/>");
		};

		$.initSet();

	});
})(jQuery);
</script>

<div id="wrap">

	<form id="multisideForm" name="multisideForm" method="post" action="<c:url value='/portal/evaluation/multiside/multisideDiagnosisList.do'/>">
		<input type="hidden" name="action" value="MULTISIDE_DIAGNOSIS" />

		<h1 class="title">다면진단</h1>

		<table class="list_table">
			<colgroup>
				<col style="width: 15%;" />
				<col style="width: 15%;" />
				<col style="width: 15%;" />
				<col style="width: 15%;" />
				<col style="width: 20%;" />
				<col style="width: 20%;" />
			</colgroup>
			<thead>
				<tr>
					<th>사번</th>
					<th>성명</th>
					<th>직책</th>
					<th>직급</th>
					<th>진단그룹</th>
					<th>진행상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="6" class="emptyRecord center">내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td>${result.MAPPE}</td>
								<td>${result.MAPENM}</td>
								<td>${result.COTXT}</td>
								<td>${result.TRFGR}</td>
								<td>${result.MSGRPX}</td>
								<td>${result.MSSTSX}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>

		<c:if test="${!empty etList }">
			<%@include file="/WEB-INF/view/portal/webess/paging.jsp"%>
		</c:if>

		<input type="hidden" name="currentPage" value=""/>
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