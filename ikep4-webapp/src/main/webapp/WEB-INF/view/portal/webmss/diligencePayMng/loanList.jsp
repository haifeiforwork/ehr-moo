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
			var endda = $("input[name=ENDDA]").eq(index).val();

			if(endda <= '20060630'){
				alert('2006년 7월 이전의 세부상환 내역은 조회할 수 없습니다.');
				return;
			}

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);

			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimmss/diligencePayMng/loanView.do'/>");
			$("#ajaxForm").submit();
		});

	});
})(jQuery);;
</script>

<c:set var="etList" value="${resultMapList.ET_LIST}"/>


<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>대출내역 조회</h1>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<colgroup>
				<col width="16%"/>
				<col width="16%"/>
				<col width="16%"/>
				<col width="16%"/>
				<col width="16%"/>
				<col width="*"/>
			</colgroup>
			<thead>
				<tr>
					<th>대출금 유형</th>
					<th>대출 순번</th>
					<th>대출 일자</th>
					<th>대출 종료일</th>
					<th>대출금</th>
					<th>대출잔액</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${etList }" var="result">
					<tr>
						<td><c:out value="${result.ZDLART}"/></td>
						<td><c:out value="${result.OBJPS}"/></td>
						<td>
							<fmt:parseDate var="dateString" value="${result.DATBW}" pattern="yyyyMMdd" />
							<a href="#" class="board_2" name="linkField"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
						</td>
						<td>
							<fmt:parseDate var="dateString" value="${result.ENDDA}" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						</td>
						<td class="f_right">
							<fmt:formatNumber value="${result.DARBT}" groupingUsed="true"/>
						</td>
						<td class="f_right">
							<fmt:formatNumber value="${result.ZREST}" groupingUsed="true"/>
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