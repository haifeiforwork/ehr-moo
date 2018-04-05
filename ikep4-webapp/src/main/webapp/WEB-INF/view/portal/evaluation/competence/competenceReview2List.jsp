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
					RVWSC:"${result.RVWSC}",
					REVWR:"${result.REVWR}",
					REVWRNM:"${result.REVWRNM}",
					RVSTS:"${result.RVSTS}",
					RVSTSX:"${result.RVSTSX}",
					REVWR2:"${result.REVWR2}",
					REVWRNM2:"${result.REVWRNM2}",
					RVSC2:"${result.RVSC2}",
					RVWSC2:"${result.RVWSC2}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}"
				});
			</c:forEach>
		</c:if>

		$.setParam = function(data) {
			var $ajaxForm = $('#ajaxForm');

			$.each(data, function(key, value) {
				$ajaxForm.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$ajaxForm.append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);
			if(boardList[index].RVSTS == '' || boardList[index].RVSTS == '1') {
				alert("역량평가 Review 기간이 아닙니다");
				$.stopProgress();
				return false;
			} else {
				$.setParam(boardList[index]);

				$("#ajaxForm")
					.attr("method", "post")
					.attr("action", "<c:url value='/portal/evaluation/competence/competenceReview2View.do'/>")
					.submit();
			}
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
			if('${esReturn.MSGTY}' == 'W') {
				alert('${esReturn.MSGTX}');

				return false;
			}

			$("input[name=currentPage]").val("<c:out value="${params.currentPage}"/>");
		};

		$.initSet();

	});
})(jQuery);;
</script>

<div id="wrap">

	<form id="evaluationForm" name="evaluationForm" method="post" action="<c:url value='/portal/evaluation/competence/competenceReview2List.do'/>">
	<h1 class="title">역량평가 2차 Review</h1>

	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
			<col style="width:60%;">
			<col style="width:40%;">
		</colgroup>
		<thead>
			<tr>
				<th>1차 Reviewer</th>
				<th>진행상태</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty etList}">
					<tr>
						<td colspan="2" class="emptyRecord center">내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${etList}">
						<tr>
							<td>${result.REVWR} ${result.REVWRNM}</td>
							<td>${result.RVSTSX}</td>
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
