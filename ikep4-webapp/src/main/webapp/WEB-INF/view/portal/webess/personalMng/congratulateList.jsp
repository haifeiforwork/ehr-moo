<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var exFlag = "";
		var exResult = "E";
		var exMessage = "";
		var returnFlag = "FAIL";

		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");

		$("#searchButton").click(function() {
			$.callProgress();
			$("#congratulateForm").submit();
		});

		$("#btnWrite").click(function(){

			exFlag = "";
			$("#ajaxForm").find("input[name=exFlag]").remove();
			$("#ajaxForm").append("<input type='hidden' name='exFlag' value='"+exFlag+"'>");

			$("#ajaxForm").find("input[name=jspType]").remove();
			$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>'/>");

			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/congratulateView.do'/>");
			$("#ajaxForm").submit();
		});

		$("#btnModify").click(function(){
			$.checkValidation("CHANGE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>'/>");
				
				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/congratulateView.do'/>");
				$("#ajaxForm").submit();
			}
		});

		$("#btnDelete").click(function(){
			$.checkValidation("DELETE");

			if(returnFlag == "PASS"){
				$("#ajaxForm").find("input[name=jspType]").remove();
				$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>'/>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/congratulateView.do'/>");
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
				
				$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>'/>");

				$("#ajaxForm").find("input[name=dataLoad]").remove();
				$("#ajaxForm").append("<input type='hidden' name='dataLoad' value='Y'>");

				$("#ajaxForm").attr("method", "post");
				$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/congratulateView.do'/>");
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
						url : "<c:url value='/portal/moorimess/personalMng/checkedRowValidationBE004.do'/>",
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
				alert("선택된 경조금 신청 내역이 없습니다.");
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

<form id="congratulateForm" name="congratulateForm" method="post" action="<c:url value='/portal/moorimess/personalMng/congratulateList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>경조금 신청 조회</h1>

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
		<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
	</div>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="4%"></th>
					<th scope="col" width="*">경조기간</th>
					<th scope="col" width="12%">경조사 유형</th>
					<th scope="col" width="12%">대상</th>
					<th scope="col" width="12%">대상자명</th>
					<th scope="col" width="12%">휴가일수(일)</th>
					<th scope="col" width="12%">금액(원)</th>
					<th scope="col" width="12%">화환지급여부</th>
					<th scope="col" width="8%">상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty resultList}">
						<tr>
							<td colspan="9">경조금 신청내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${resultList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td><a href="#" class="board_2" name="linkField"><c:out value="${result.PERIOD}"/></a></td>
								<td><c:out value="${result.ZCACCD}"/></td>
								<td><c:out value="${result.ZCACRN}"/></td>
								<td><c:out value="${result.ENAME}"/></td>
								<td><c:out value="${result.ZVACDT}"/></td>
								<td class="f_right"><fmt:formatNumber value="${result.CACMT}" groupingUsed="true"/></td>
								<td><c:out value="${result.ZGARLD}"/></td>
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