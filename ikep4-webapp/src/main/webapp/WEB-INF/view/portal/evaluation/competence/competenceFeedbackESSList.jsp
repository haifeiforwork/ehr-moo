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
					ORGEH : "${result.ORGEH}",
					ORGTX : "${result.ORGTX}",
					APRSE : "${result.APRSE}",
					APRSENM : "${result.APRSENM}",
					ZZJIK : "${result.ZZJIK}",
					COTXT : "${result.COTXT}",
					TRFGR : "${result.TRFGR}",
					APRSR1 : "${result.APRSR1}",
					APRSRNM1 : "${result.APRSRNM1}",
					ZZJIK1 : "${result.ZZJIK1}",
					COTXT1 : "${result.COTXT1}",
					APSCO1 : "${result.APSCO1}",
					AQSTS1 : "${result.AQSTS1}",
					AQSTSX1 : "${result.AQSTSX1}",
					APRSR2 : "${result.APRSR2}",
					APRSRNM2 : "${result.APRSRNM2}",
					ZZJIK2 : "${result.ZZJIK2}",
					COTXT2 : "${result.COTXT2}",
					APSCO2 : "${result.APSCO2}",
					AQSTS2 : "${result.AQSTS2}",
					AQSTSX2 : "${result.AQSTSX2}",
					REVWR : "${result.REVWR}",
					REVWRNM : "${result.REVWRNM}",
					ZZJIK3 : "${result.ZZJIK3}",
					COTXT3 : "${result.COTXT3}",
					STELL : "${result.STELL}",
					STLTX : "${result.STLTX}",
					MSDAT : "${result.MSDAT}",
					APITM : "${result.APITM}",
					APITMX : "${result.APITMX}",
					APSTS : "${result.APSTS}",
					APSTSX : "${result.APSTSX}",
					QUSTS : "${result.QUSTS}",
					QUSTSX : "${result.QUSTSX}",
					QKID1 : "${result.QKID1}",
					QKID2 : "${result.QKID2}",
					EXRSN : "${result.EXRSN}",
					HIREDATE : "${result.HIREDATE}",
					PBLVE : "${result.PBLVE}",
					LVCNT : "${result.LVCNT}",
					ADJSCO1 : "${result.ADJSCO1}",
					ADJSCO2 : "${result.ADJSCO2}",
					PTSUM : "${result.PTSUM}",
					AYEAR : "${result.AYEAR}",
					EXFLG : "${result.EXFLG}",
					SEQNO : "${result.SEQNO}",
					SRTKY : "${result.SRTKY}",
					FBPER : "${result.FBPER}",
					FBPNM : "${result.FBPNM}",
					FBSTS : "${result.FBSTS}",
					FBSTSX : "${result.FBSTSX}",
					PLANS : "${result.PLANS}",
					PLSTX : "${result.PLSTX}"
		});
		</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			return formData;
		}

		$.setParam = function(data) {
			var $ajaxForm = $('#ajaxForm');

			$.each(data, function(key, value) {
				$ajaxForm.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$ajaxForm.append("<input type=\"hidden\" name=\"action\" value=\"COMPETENCE_FEEDBACK_ESS_VIEW\"/>");
			$ajaxForm.append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/competence/competenceFeedBackCheckView.do'/>"
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
							.attr("action", "<c:url value='/portal/evaluation/competence/competenceFeedBackView.do'/>")
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

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/competence/competenceFeedBackList.do'/>">
		<input type="hidden" name="action" value="COMPETENCE_FEEDBACK_ESS" />

	<h1 class="title">역량평가 Feedback</h1>

	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
			<col style="width:14%;" />
			<col style="" />
			<col style="width:14%;" />
			<col style="width:14%;" />
			<col style="width:14%;" />
			<col style="width:14%;" />
		</colgroup>
		<thead>
			<tr>
				<th>평가년도</th>
				<th>소속</th>
				<th>직급</th>
				<th>직무</th>
				<th>피드백 작성</th>
				<th>피드백 작성상태</th>
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
							<td>${result.AYEAR}</td>
							<td>${result.ORGTX}</td>
							<td>${result.TRFGR}</td>
							<td>${result.STLTX}</td>
							<td>${result.FBPNM}</td>
							<td>${result.FBSTSX}</td>
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
