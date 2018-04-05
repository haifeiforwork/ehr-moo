<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#btnWrite").click(function(){
			$("#ajaxForm").html("");

			$("#ajaxForm").find("input[name=dataLoad]").remove();
			$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='N'>");

			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/addressView.do'/>");
			$("#ajaxForm").submit();

		});

		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);

			$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='Y'>");

			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/addressView.do'/>");
			$("#ajaxForm").submit();

		});
	});
})(jQuery);;
</script>
<form id="addressForm" name="addressForm" method="post" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>주소사항 조회/수정</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnWrite"><span>등록</span></a>
	</div>

	<c:set var="etList" value="${result.ET_LIST}"/>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="20%">주소유형</th>
					<th scope="col" width="*">주소</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="2" class="emptyRecord center">해당 주소 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td>
									<a href="#" class="board_2" name="linkField"><c:out value="${result.ZTYPE}"/></a>
								</td>
								<td class="f_left">
									<c:out value="${result.ZADDRESS}"/>
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