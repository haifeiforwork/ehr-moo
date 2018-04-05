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
					MAPPE:"${result.MAPPE}",
					MAPENM:"${result.MAPENM}",
					LOCAT:"${result.LOCAT}",
					LOCATX:"${result.LOCATX}",
					ORGEH:"${result.ORGEH}",
					ORGTX:"${result.ORGTX}",
					ZZJIK:"${result.ZZJIK}",
					COTXT:"${result.COTXT}",
					TRFGR:"${result.TRFGR}",
					PLANS:"${result.PLANS}",
					PLSTX:"${result.PLSTX}",
					PERSG:"${result.PERSG}",
					PGTXT:"${result.PGTXT}",
					FBEGDA:"${result.FBEGDA}",
					FENDDA:"${result.FENDDA}",
					FDATUM:"${result.FDATUM}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}"
				});
			</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			return formData;
		}

		$.setParam = function(data) {
			$.each(data, function(key, value) {
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$("#ajaxForm").append("<input type=\"hidden\" name=\"action\" value=\"FEEDBACK_DETAIL\"/>");
//			$("#ajaxForm").append("<input type=\"hidden\" name=\"action\" value=\"FEEDBACK_PRINT\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/multiside/multisideFeedbackCheckView.do'/>"
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
						$("#ajaxForm").attr("action", "<c:url value='/portal/evaluation/multiside/multisideFeedbackView.do'/>");
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

	<form id="multisideForm" name="multisideForm" method="post" action="<c:url value='/portal/evaluation/multiside/multisideFeedbackList.do'/>">
	<h1 class="title">다면진단 Feedback	</h1>

	<table class="list_table">
		<colgroup>
			<col style="width:15%;" />
			<col style="width:20%;" />
			<col style="" />
			<col style="width:20%;" />
		</colgroup>
		<thead>
			<tr>
				<th>년도</th>
				<th>사업장</th>
				<th>직책</th>
				<th>포지션</th>
				<th>구분</th>
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
							<td>${result.LOCATX}</td>
							<td>${result.COTXT}</td>
							<td>${result.TRFGR}</td>
							<td>${result.PGTXT}</td>
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