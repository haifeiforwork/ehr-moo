<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnWrite").click(function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/checkedRowBe005SetEntryCheck.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						$("#ajaxForm").attr("method", "post");
						$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/taxWithholdingRegView.do'/>");
						$("#ajaxForm").submit();
					}else{
						alert($.trim(result.EX_MESSAGE));
					}
				}
			});

		});

		$("#btnDelete").click(function(){

			if($("input:radio[name=flag]:checked").length == 0){
				alert("선택된 데이터가 없습니다.");
				return;
			}

			if(confirm("정말로 삭제하시겠습니까?")){

				$.setParam();

				$jq.ajax({
					url : "<c:url value='/portal/moorimess/personalMng/taxWithholdingDelete.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async: false,
					success : function(result) {
						alert(result.EX_MESSAGE);
						if($.trim(result.EX_RESULT) == "S"){
							$("#taxWithholdingForm").submit();
						}
					}
				});
			}
		});

		$.setParam = function(){
			var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);
		};
	});
})(jQuery);;
</script>

<form id="taxWithholdingForm" name="taxWithholdingForm" method="post" action="<c:url value='/portal/moorimess/personalMng/taxWithholdingList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근로소득 원천징수영수증 신청</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnWrite"><span>작성</span></a>
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
	</div>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="3%"></th>
					<th scope="col" width="80px">신청일</th>
					<th scope="col" width="15%">신청구분</th>
					<th scope="col" width="6%">신청부수</th>
					<th scope="col" width="9%">제출처</th>
					<th scope="col" width="10%">용도</th>
					<th scope="col" width="*">주소</th>
					<th scope="col" width="7%">특기사항</th>
					<th scope="col" width="7%">담당자</th>
					<th scope="col" width="6%">상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty resultList}">
						<tr>
							<td colspan="10">근로소득 원청징수영수증 신청 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${resultList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.ZCERTI}"/></td>
								<td><c:out value="${result.ZCOPIES}"/></td>
								<td><c:out value="${result.SUBMIT}"/></td>
								<td><c:out value="${result.PURPS}"/></td>
								<td class="f_left"><c:out value="${result.ADDRESS}"/></td>
								<td><c:out value="${result.DETAIL}"/></td>
								<td><c:out value="${result.ZDAMG}"/></td>
								<td>
									<c:out value="${result.STATST}"/>
									<span class="rowInfo">
										<c:forEach items="${keySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
										</c:forEach>
									</span>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
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