<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("select[name=imYear] option[value=<c:out value='${params.imYear}'/>]").attr("selected", "selected");

		$("#searchButton").click(function() {
			$.callProgress();
			$("#cafeteriaForm").submit();
		});

		$("#btnWrite").click(function(){

			$("#ajaxForm").find("input[name=imYear]").remove();
			$("#ajaxForm").append("<input type='hidden' name='imYear' value='"+$("select[name=imYear]").val()+"'>");
			
			$("span.rowInfo").each(function(index, value){
				$("#ajaxForm").append($(this).html());
			});

			$("#ajaxForm").append("<input type='hidden' name='rowInfoCnt' value='"+$("span.rowInfo").length+"'>");
			
			$("#ajaxForm").append("<input type='hidden' name='searchParam' value='?imYear=<c:out value='${params.imYear}'/>'/>");
			
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/cafeteriaRegView.do'/>");
			$("#ajaxForm").submit();
		});

		$("#btnDelete").click(function(){
			$.checkValidation("DELETE");
		});

		$.setParam = function(eventId){
			var index = $("input:radio[name=flag]").index($("input:radio[name=flag]:checked"));
			var rowInfo = $("span.rowInfo").eq(index).html();

			$("#ajaxForm").html("");

			$("#ajaxForm").html(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").append("<input type='hidden' name='imYear' value='"+$("select[name=imYear]").val()+"'>");
		};

		$.checkValidation = function(eventId){

			if($("input:radio[name=flag]:checked").length > 0){

				$.setParam(eventId);

				$jq.ajax({
					url : "<c:url value='/portal/moorimess/personalMng/checkedRowValidationPA042.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async: false,
					success : function(result) {
						alert(result.EX_MESSAGE);
						$("#searchButton").click();
					}
				});
			}else{
				alert("선택된 카페테리아 사용 신청 내역이 없습니다.");
				returnFlag = "FAIL";
			}
		};
	});
})(jQuery);;
</script>

<form id="cafeteriaForm" name="cafeteriaForm" method="post" action="<c:url value='/portal/moorimess/personalMng/cafeteriaList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>카페테리아 신청</h1>

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

	<c:if test="${params.imYear eq params.thisYear }">
		<!-- 상단버튼-->
		<div class="button_box">
			<a href="#" class="button_img01" id="btnWrite"><span>작성</span></a>
			<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</div>
	</c:if>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="4%"></th>
					<th scope="col" width="*">일자</th>
					<th scope="col" width="12%">항목</th>
					<th scope="col" width="12%">적치Point</th>
					<th scope="col" width="12%">신청Point</th>
					<th scope="col" width="12%">사용Point</th>
					<th scope="col" width="12%">잔여Point</th>
					<th scope="col" width="12%">주관부서확인</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty resultList}">
						<tr>
							<td colspan="8">해당 카페테리아 사용 신청 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${resultList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td>
									<fmt:parseDate var="dateString" value="${result.USEDT}" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
								</td>
								<td><c:out value="${result.CAFTX}"/></td>
								<td class="f_right"><c:out value="${result.CREPT}"/></td>
								<td class="f_right"><c:out value="${result.PROPT}"/></td>
								<td class="f_right"><c:out value="${result.USEPTT}"/></td>
								<td class="f_right"><c:out value="${result.RENPTT}"/></td>
								<td>
									<c:if test="${result.CONCK eq 'X'}">
										<img src="<c:url value='/base/images/common/icon_v.png'/>"/>
									</c:if>
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