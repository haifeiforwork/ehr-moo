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
					RSPER:"${result.RSPER}",
					RSPERNM:"${result.RSPERNM}",
					PERSG:"${result.PERSG}",
					PERSK:"${result.PERSK}",
					STELL:"${result.STELL}",
					STLTX:"${result.STLTX}",
					ZZJIK:"${result.ZZJIK}",
					COTXT:"${result.COTXT}",
					TRFGR:"${result.TRFGR}",
					RTRFGR:"${result.RTRFGR}",
					RSDAT:"${result.RSDAT}",
					KTCDAT:"${result.KTCDAT}",
					KPTERM:"${result.KPTERM}",
					KPYEAR:"${result.KPYEAR}",
					RSSTS:"${result.RSSTS}",
					RSSTSX:"${result.RSSTSX}",
					RSSTSDL:"${result.RSSTSDL}",
					BIGO:"${result.BIGO}",
					SRTKY:"${result.SRTKY}",
					MODIF:"${result.MODIF}"
				});
			</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			formData.push({name : "action", value : "GOAL_SETTING_VIEW"});

			return formData;
		}

		$.setParam = function(data) {
			$.each(data, function(key, value) {
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
			$("#ajaxForm").append("<input type=\"hidden\" name=\"action\" value=\"GOAL_SETTING_VIEW\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"currentPage\" value=\"${params.currentPage}\"/>");
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/promotion/promotionCheckView.do'/>"
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
						$("#ajaxForm").attr("action", "<c:url value='/portal/evaluation/promotion/goalSettingView.do'/>");
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
			$("#idpForm").find("input[name=currentPage]").val($(this).data("page"));
			$("#idpForm").submit();
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

	<form id="idpForm" name="idpForm" method="post" action="<c:url value='/portal/evaluation/promotion/promotionList.do'/>">
	<input type="hidden" name="action" value="GOAL_SETTING_LIST" />
	<h1 class="title">도전과제등록</h1>

	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
			<col style="width:16%;" />
			<col style="width:17%;" />
			<col style="width:16%;" />
			<col style="width:16%;" />
			<col style="width:18%;" />
			<col style="width:17%;" />
		</colgroup>
		<thead>
			<tr>
				<th>승격년도</th>
				<th>직책</th>
				<th>현직급</th>
				<th>승격직급</th>
				<th>최종승격일</th>
				<th>작성상태</th>
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
							<td>${result.COTXT}</td>
							<td>${result.TRFGR}</td>
							<td>${result.RTRFGR}</td>
							<td>
								<fmt:parseDate value="${result.RSDAT}" var="rsdat" pattern="yyyyMMdd"/>
								<fmt:formatDate value="${rsdat}" pattern="yyyy-MM-dd"/>
							</td>
							<td>${result.RSSTSX}</td>
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
