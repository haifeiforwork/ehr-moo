<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);

			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/eduExpenseView.do'/>");
			$("#ajaxForm").submit();
		});

	});
})(jQuery);;
</script>

<c:set var="etList" value="${resultMap.ET_LIST}"/>

<form id="eduExpenseForm" name="eduExpenseForm" method="post" action="<c:url value='/portal/moorimess/personalMng/eduExpenseList.do'/>">

<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>학자금 신청/조회</h1>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<colgroup>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
			</colgroup>
			<thead>
				<tr>
					<th>성명</th>
					<th>학교유형</th>
					<th>지급횟수</th>
					<th>학자금</th>
					<th>결제상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${etList }" var="result">
					<tr>
						<td><a href="#" class="board_2" name="linkField"><c:out value="${result.FAMNM}"/></a></td>
						<td><c:out value="${result.SUBNM}"/></td>
						<td><c:out value="${result.ABWTGT}"/></td>
						<td class="f_right">
							<fmt:formatNumber value="${result.TOFEE}" groupingUsed="true"/>
						</td>
						<td>
							<c:out value="${result.ASTATT}"/>
							<span class="rowInfo">
								<c:forEach items="${keySet}" var="key">
									<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
								</c:forEach>
							</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>