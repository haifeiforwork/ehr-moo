<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		<%
		/*
			exFlag
			CR = 변경 요청
			D = 삭제
			C = 취소 요청
		*/
		%>
		var exFlag = "";
		var exResult = "E";
		var exMessage = "";
		var returnFlag = "FAIL";

		$("#searchButton").click(function() {
			$.callProgress();
			$("#businessTripForm").submit();
		});

		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");
		$("select[name=imMonth] option[value=<c:out value='${params.imMonth}'/>]").attr("selected", "selected");

		$("#btnWrite").click(function(){
			exFlag = "";

			$("#ajaxForm").html("");
			$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+exFlag+"'>");
			$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripView.do'/>");
			$("#ajaxForm").submit();
		});

		$("#btnModify").click(function(){
			$.checkValidation("CHANGE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("#btnCancelRequest").click(function(){
			$.checkValidation("CANCEL");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("#btnDelete").click(function(){
			$.checkValidation("DELETE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\"\"/>");

			exFlag = "";
			exResult = "S";
			exMessage = "";
			returnFlag = "PASS";

			if(returnFlag == "PASS"){
				exFlag = "";
				$("#ajaxForm").find("input[name=exFlag]").remove();
				$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+exFlag+"'>");

				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>&imMonth=<c:out value='${params.imMonth}'/>'/>");

				$("#ajaxForm").find("input[name=dataLoad]").remove();
				$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='Y'>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/businessTripView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$.setParam = function(eventId){
			var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");
			$("#ajaxForm").html(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
		};

		$.checkValidation = function(eventId){

			if($("input:radio[name=flag]:checked").length > 0){

				$.setParam(eventId);

				exFlag = "";
				exResult = "E";
				exMessage = "";
				returnFlag = "FAIL";

				if(eventId != ""){
					$jq.ajax({
						url : "<c:url value='/portal/moorimess/diligenceMng/checkedRowValidationPT008.do'/>",
						data : $("#ajaxForm").serialize(),
						type : "post",
						async: false,
						success : function(result) {
							if($.trim(result.EX_RESULT) == "S"){
								exFlag = result.EX_FLAG;
								exResult = result.EX_RESULT;
								exMessage = result.EX_MESSAGE;

								$.setValidationAfter();
							}else{
								alert(result.EX_MESSAGE);
							}
						}
					});
				}else{
					exResult = "S";
					returnFlag = "PASS";
				}
			}else{
				alert("선택된 출장 내역이 없습니다.");
				returnFlag = "FAIL";
			}
		};

		$.setValidationAfter = function(){
			if($.trim(exResult) == "E"){
				alert($.trim(exMessage));
				returnFlag = "FAIL";
			}else{
				$("#ajaxForm").find("input[name=exFlag]").remove();
				$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+exFlag+"'>");

				$("#ajaxForm").find("input[name=dataLoad]").remove();
				$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='Y'>");
				returnFlag = "PASS";
			}
		};
	});
})(jQuery);;
</script>
<form id="businessTripForm" name="businessTripForm" method="post" action="<c:url value='/portal/moorimess/diligenceMng/businessTripList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>출장 신청/조회</h1>
	<div class="search_box">
		<table border="0">
			<tbody>
				<tr>
					<td><span class="f_333">조회년월</span></td>
					<td>
						<select name="imYear" id="imYear">
							<c:forEach var="result" items="${yearList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
						<select name="imMonth" id="imMonth">
							<c:forEach var="result" items="${monthList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>
						<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnWrite"><span>작성</span></a>
		<a href="#" class="button_img01" id="btnModify"><span>수정</span></a>
		<a href="#" class="button_img01" id="btnCancelRequest"><span>취소신청</span></a>
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
	</div>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<thead>
				<tr>
					<th width="3%">&nbsp;</th>
					<th width="10%">출장번호</th>
					<th width="15%">출장기간</th>
					<th width="10%">출장구분</th>
					<th width="15%">출장지역</th>
					<th width="*">출장사유</th>
					<th width="10%">신청상태</th>
					<th width="10%">정산상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty resultList}">
						<tr>
							<td colspan="8">해당 출장 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${resultList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td><a class="board_2" href="#" name="linkField"><c:out value="${result.REINR }"/></a></td>
								<td><c:out value="${result.ZPERIOD }"/></td>
								<td><c:out value="${result.ZCNTEXT }"/></td>
								<td><c:out value="${result.LOCATION_END }"/></td>
								<td><c:out value="${result.REQUEST_REASON }"/></td>
								<td><c:out value="${result.ZASTXT }"/></td>
								<td>
									<c:out value="${result.ZSTATXT }"/>
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