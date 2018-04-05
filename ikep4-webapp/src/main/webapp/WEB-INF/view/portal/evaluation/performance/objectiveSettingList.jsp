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
					SEQNO:"${result.SEQNO}",
					APRSE:"${result.APRSE}",
					APRSENM:"${result.APRSENM}",
					TRFGR:"${result.TRFGR}",
					ZZJIK:"${result.ZZJIK}",
					COTXT:"${result.COTXT}",
					APRSR1:"${result.APRSR1}",
					APRSRNM1:"${result.APRSRNM1}",
					AQSTS1:"${result.AQSTS1}",
					AQSTSX1:"${result.AQSTSX1}",
					APRSR2:"${result.APRSR2}",
					APRSRNM2:"${result.APRSRNM2}",
					AQSTS2:"${result.AQSTS2}",
					AQSTSX2:"${result.AQSTSX2}",
					REVWR:"${result.REVWR}",
					REVWRNM:"${result.REVWRNM}",
					ACHVSTS:"${result.ACHVSTS}",
					ACHVSTSX:"${result.ACHVSTSX}",
					ORGTX:"${result.ORGTX}",
					STELL:"${result.STELL}",
					STLTX:"${result.STLTX}",
					APSTS:"${result.APSTS}",
					APSTSX:"${result.APSTSX}"
				});
			</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();

			$.each(data, function(key, value) { formData.push({name : key, value : value}); });
			formData.push({name : "rfc", value : "OBJECTIVE_GOAL_DETAIL"});

			return formData;
		}

		$.setParam = function(data) {
			var $ajaxForm = $('#ajaxForm');

			$.each(data, function(key, value) {
				$ajaxForm.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$ajaxForm.append("<input type=\"hidden\" name=\"action\" value=\"OBJECTIVE_SETTING_VIEW\"/>");
			$ajaxForm.append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/performance/objectiveCheckView.do'/>"
				, type : "post"
				, data : $.makeFormData(boardList[index])
				, success : function(result) {
					if(result.MSGTY == 'W') {
						alert(result.MSGTX);
						$.stopProgress();
						return false;
					} else {
						$.setParam(boardList[index]);

						$("#ajaxForm")
							.attr("method", "post")
							.attr("action", "<c:url value='/portal/evaluation/performance/objectiveView.do'/>")
							.submit();
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

			$("#evaluationForm")
				.find("input[name=currentPage]")
					.val($(this).data("page"))
				.end()
					.submit()
				.end();
		});

		$.initSet = function() {
			$("input[name=currentPage]").val("<c:out value="${params.currentPage}"/>");
		};

		$.initSet();

	});
})(jQuery);;
</script>

<div id="wrap">

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/performance/objectiveList.do'/>">
	<input type="hidden" name="action" value="OBJECTIVE_SETTING" />
	<h1 class="title">업적평가 목표수립</h1>

	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
			<col style="width: 16%;" />
			<col style="width: 22%;" />
			<col style="width: 22%;" />
			<col style="width: 22%;" />
			<col style="width: 18%;" />
		</colgroup>
		<thead>
			<tr>
				<th>년도</th>
				<th>1차평가자</th>
				<th>2차평가자</th>
				<th>Reviewer</th>
				<th>진행상태</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty etList}">
					<tr>
						<td colspan="5" class="emptyRecord center">내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${etList}">
						<tr>
							<td>${result.AYEAR}</td>
							<td><c:if test="${result.APRSR1 ne '00000000'}">${result.APRSR1} ${result.APRSRNM1}</c:if></td>
							<td><c:if test="${result.APRSR2 ne '00000000'}">${result.APRSR2} ${result.APRSRNM2}</c:if></td>
							<td><c:if test="${result.REVWR ne '00000000'}">${result.REVWR} ${result.REVWRNM}</c:if></td>
							<td>${result.ACHVSTSX}</td>
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
