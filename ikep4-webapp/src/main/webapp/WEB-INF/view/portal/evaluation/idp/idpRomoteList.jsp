<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="prefix" value="message.portal.main.listUser"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>

<script type="text/javascript">
var boardList = [];

(function($){

	$(document).ready(function() {

		$.initView = function() {
			$.getAndDrawHSSList($('[name=empno]').val());
		}

		$.setParam = function(data) {
			var $ajaxForm = $('#ajaxForm');

			$.each(data, function(key, value) {
				$ajaxForm.append("<input type=\"hidden\" name=\""+key+"\" value=\""+value+"\"/>");
			});
		};

		$.bindHssRowClickHandler = function() {
			$(".list_table > tbody> tr").click(function() {
				if($(this).find('.emptyRecord').size() > 0) return false;

				$.callProgress();

				var index = $(".list_table > tbody > tr").index(this);
				$.setParam(boardList[index]);

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/evaluation/idp/idpRemoteView.do'/>");
				$("#ajaxForm").submit();
			})
			.mouseover(function() {
				if($(this).find('.emptyRecord').size() > 0) return false;
				$(this).addClass('line_focus');
			});
		}

		$.drowTable = function(objList) {
			$('#tmpHSSRow').tmpl(objList).appendTo('.list_table > tbody');

			$.bindHssRowClickHandler();
		}

		$.removeTable = function() {
			$('#selectedUserInfo').text('');
			$('.list_table > tbody > tr').remove();
		}

		$.getAndDrawHSSList = function(empno) {
			$.callProgress();

			$.removeTable();

			$.ajax({
				url : "<c:url value='/portal/evaluation/idp/idpHSSList.do'/>"
				, type : "get"
				, data : {"emp_no" : empno}
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'W') {
						alert(result.ES_RETURN.MSGTX);
						$.stopProgress();
						return false;
					} else {
						$('#selectedUserInfo').text(result.EMP_NO + " " + result.USER_NAME);

						boardList = result.ET_LIST;
						$.drowTable(boardList);
						$.stopProgress();
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);
					$.stopProgress();
				}
			});
		}

		$.initView();

	});

})(jQuery);

</script>

<script id="tmpHSSRow" type="text/x-jquery-tmpl">
	<tr>
		<td>\${AYEAR}</td>
		<td>\${STLTX}</td>
		<td>\${COTXT}</td>
		<td>\${TRFGR}</td>
		<td>\${IDPARNM}</td>
		<td>\${IDSTSX}</td>
	</tr>
</script>

<div id="wrap">

	<h1 class="title">IDP 조회</h1>

	<h2 id="selectedUserInfo" class="title"></h2>
	<!-- 리스트 -->
	<table class="list_table">
		<colgroup>
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		    <col style="width:20%;" />
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		    <col style="width:15%;" />
		</colgroup>
		<thead>
			<tr>
				<th>년도</th>
				<th>직무</th>
				<th>직책</th>
				<th>직급</th>
				<th>합의자</th>
				<th>작성상태</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="6" class="emptyRecord center">내역이 없습니다.</td>
			</tr>
		</tbody>
	</table>
</div>

<form id="ajaxForm" name="ajaxForm" method="post">
	<input type="hidden" name="empno" value="${empno}" />
</form>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
