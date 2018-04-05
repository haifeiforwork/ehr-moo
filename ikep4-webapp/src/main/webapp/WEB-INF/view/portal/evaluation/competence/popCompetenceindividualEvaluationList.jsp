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
					QUSTS:"${result.QUSTS}",
					QUSTSX:"${result.QUSTSX}",
					APSTS:"${result.APSTS}",
					APSTSX:"${result.APSTSX}",
					ORGTX:"${result.ORGTX}",
					STELL:"${result.STELL}",
					STLTX:"${result.STLTX}",
					APITM:"${result.APITM}",
					MSDAT:"${result.MSDAT}"
		});
		</c:forEach>
		</c:if>

		$.makeFormData = function(data) {
			var formData = $("#ajaxForm").serializeArray();
			$.each(data, function(key, value) { formData.push({name : key, value : value}); });

			formData.push({name : "rfc", value : "COMPETENCE_EVALUATION_DETAIL"});

			return formData;
		}

		$.setParam = function(data) {
			$.each(data, function(key, value) {
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
		};

		$(".list_table > tbody > tr").click(function() {
			if($(this).find('.emptyRecord').size() > 0) return false;

			$.callProgress();

			var index = $(".list_table > tbody > tr").index(this);

			$.ajax({
				url : "<c:url value='/portal/evaluation/competence/competenceCheckView.do'/>"
				, type : "post"
				, data : $.makeFormData(boardList[index])
				, success : function(result) {
					if(result.MSGTY == 'W') {
						alert(result.MSGTX);
						$.stopProgress();
						return false;
					} else {
						$.setParam(boardList[index]);

						$("#ajaxForm").attr("method", "post");
						$("#ajaxForm").attr("action", "<c:url value='/portal/evaluation/competence/popCompetenceindividualEvaluationView.do'/>");
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

		$('#btnClose').click(function(e) {
			e.preventDefault();

			window.close();
		});

	});
})(jQuery);;
</script>

<div id="wrap">

	<h1 class="title">역량평가</h1>

	<div class="btn_wrap left">
		<a class="btn_common" id="btnClose" title="닫기" href="#"><span>닫기</span></a>
	</div>

	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
		    <col style="width:10%;" />
		    <col style="width:10%;" />
		    <col style="width:10%;" />
		    <col style="width:10%;" />
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		    <col style=";" />
		</colgroup>
		<thead>
			<tr>
				<th>사번</th>
				<th>성명</th>
				<th>직급</th>
				<th>직무</th>
				<th>1차평가자</th>
				<th>2차평가자</th>
				<th>Reviwer</th>
				<th>진행상태</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty etList}">
					<tr>
						<td colspan="8" class="emptyRecord center">내역이 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="result" items="${etList}">
						<tr>
							<td>${result.APRSE}</td>
							<td>${result.APRSENM}</td>
							<td>${result.TRFGR}</td>
							<td>${result.STLTX}</td>
							<td>${result.APRSRNM1}</td>
							<td>${result.APRSRNM2}</td>
							<td>${result.REVWRNM}</td>
							<td>${result.APSTSX}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
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
