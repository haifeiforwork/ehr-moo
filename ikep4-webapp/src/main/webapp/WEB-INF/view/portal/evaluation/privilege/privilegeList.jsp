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
					LOCAT:"${result.LOCAT}",
					LOCATX:"${result.LOCATX}",
					ORGEH:"${result.ORGEH}",
					ORGTX:"${result.ORGTX}",
					PERNR:"${result.PERNR}",
					APRSENM:"${result.APRSENM}",
					ZZJIK:"${result.ZZJIK}",
					COTXT:"${result.COTXT}",
					PERSK:"${result.PERSK}",
					PKTXT:"${result.PKTXT}",
					TRFGR:"${result.TRFGR}",
					PPERNR:"${result.PPERNR}",
					APRSRNM:"${result.APRSRNM}",
					ZZJIK1:"${result.ZZJIK1}",
					COTXT1:"${result.COTXT1}",
					PORGEH:"${result.PORGEH}",
					PORGTX:"${result.PORGTX}",
					REVIEWER:"${result.REVIEWER}",
					REVWRNM:"${result.REVWRNM}",
					ZZJIK2:"${result.ZZJIK2}",
					COTXT2:"${result.COTXT2}",
					REASN:"${result.REASN}",
					REASNX:"${result.REASNX}",
					LRATING:"${result.LRATING}",
					MARK:"${result.MARK}",
					APSTS:"${result.APSTS}",
					APSTSX:"${result.APSTSX}",
					ZYEAR:"${result.ZYEAR}",
					EXFLG:"${result.EXFLG}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}",
					ORGEH2:"${result.ORGEH2}",
					ORGTX2:"${result.ORGTX2}"
				});
			</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			formData.push({name : "action", value : "EVALUATION_VIEW"});

			return formData;
		}

		$.setParam = function(data) {
			var $ajaxForm = $("#ajaxForm");
			$.each(data, function(key, value) {
				$ajaxForm.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$ajaxForm.append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/privilege/privilegeCheckView.do'/>"
				, type : "post"
				, data : $.makeFormData(boardList[index])
				, success : function(result) {
					if(result.MSGTY == 'W' || result.MSGTY == 'E') {
						alert(result.MSGTX);
						$.stopProgress();
						return false;
					} else {
						$.setParam(boardList[index]);

						$("#ajaxForm")
							.attr("method", "post")
							.attr("action", "<c:url value='/portal/evaluation/privilege/privilegeView.do'/>")
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
			$("#privilegeForm").find("input[name=currentPage]").val($(this).data("page"));
			$("#privilegeForm").submit();
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

	<form id="privilegeForm" name="privilegeForm" method="post" action="<c:url value='/portal/evaluation/privilege/privilegeList.do'/>">
	<h1 class="title">촉탁/별정직 평가자평가</h1>

	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
			<col style="width:25%;" />
			<col style="width:15%;" />
			<col style="width:15%;" />
			<col style="width:15%;" />
			<col style="width:15%;" />
		</colgroup>
		<thead>
			<tr>
				<th>소속</th>
				<th>사번</th>
				<th>성명</th>
				<th>직급</th>
				<th>평가자</th>
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
							<td>${result.ORGTX2}</td>
							<td>${result.PERNR}</td>
							<td>${result.APRSENM}</td>
							<td>${result.TRFGR}</td>
							<td>${result.APRSRNM}</td>
							<td>${result.APSTSX}</td>
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
