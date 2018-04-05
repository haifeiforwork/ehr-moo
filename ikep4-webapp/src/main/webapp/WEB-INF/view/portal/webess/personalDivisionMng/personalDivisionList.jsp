<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="etList" value="${resultMap.ET_LIST }"/>
<c:set var="etRagPernr" value="${resultMap.ET_RAG_PERNR }"/>

<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnWrite").click(function(){
			$.callChecked("CREATE");
		});

		$("a[name=linkField]").click(function(){
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));

			$.callChecked("DISPLAY", index);
		});

		$.callChecked = function(eventId, index){

			$("#ajaxForm").html("");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\" />");

			if(eventId == "CREATE"){

				$("#ajaxForm").append("<span class=\"etListInfo\"></span>");

				<c:forEach var="result" items="${etList}">
					<c:forEach items="${keySet}" var="key">
						$("#ajaxForm").find("span.etListInfo").append("<input type=\"hidden\" name=\"etList_<c:out value='${key}'/>\" value=\"<c:out value='${result[key]}'/>\" />");
					</c:forEach>
				</c:forEach>

				$("#ajaxForm").find("span.etListInfo").append("<input type=\"hidden\" name=\"etListCnt\" value=\"<c:out value='${fn:length(etList)}'/>\"/>");
			}else if(eventId == "DISPLAY"){

				$("#ajaxForm").append("<span class=\"rowInfo\"></span>");

				var rowInfo = $("span.rowInfo").eq(index).html();
				$("#ajaxForm").find("span.rowInfo").html(rowInfo);
			}

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalDivisionMng/checkedPD033OnInput.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){

						$("#ajaxForm").append("<input type=\"hidden\" name=\"exDisabled\" value=\""+result.EX_DISABLED+"\"/>");
						$("#ajaxForm").append("<input type=\"hidden\" name=\"exOpera\" value=\""+result.EX_OPERA+"\"/>");

						var exList = result.EX_LIST;
						var value;

						if(eventId == "CREATE"){

							$("#ajaxForm").find("span.etListInfo").remove();

							$("#ajaxForm").append("<span class=\"rowInfo\"></span>");

							<c:forEach items="${keySet}" var="key">
								value = exList["<c:out value="${key}"/>"];
								$("#ajaxForm").find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\""+value+"\"/>");
							</c:forEach>

						}else if(eventId == "DISPLAY"){

							<c:forEach items="${keySet}" var="key">
								value = exList["<c:out value="${key}"/>"];
								$("#ajaxForm").find("span.rowInfo").find("input[name=<c:out value='${key}'/>]").val(value);
							</c:forEach>
						}

						$("#ajaxForm").append("<span class=\"ragPernrInfo\"></span>");

						<c:forEach items="${etRagPernr}" var="result">
							$("#ajaxForm").find("span.ragPernrInfo").append("<input type=\"hidden\" name=\"etRagPernr_SIGN\" value=\"<c:out value='${result.SIGN}'/>\"/>");
							$("#ajaxForm").find("span.ragPernrInfo").append("<input type=\"hidden\" name=\"etRagPernr_OPTION\" value=\"<c:out value='${result.OPTION}'/>\"/>");
							$("#ajaxForm").find("span.ragPernrInfo").append("<input type=\"hidden\" name=\"etRagPernr_LOW\" value=\"<c:out value='${result.LOW}'/>\"/>");
							$("#ajaxForm").find("span.ragPernrInfo").append("<input type=\"hidden\" name=\"etRagPernr_HIGH\" value=\"<c:out value='${result.HIGH}'/>\"/>");
						</c:forEach>

						$("#ajaxForm").find("span.ragPernrInfo").append("<input type=\"hidden\" name=\"etRagPernrCnt\" value=\"<c:out value='${fn:length(etRagPernr)}'/>\"/>");

						$("#ajaxForm").attr("method", "post");
						$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionView.do'/>");
						$("#ajaxForm").submit();

					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		}
	});
})(jQuery);;
</script>
<form id="personalDivisionForm" name="personalDivisionForm" method="post" action="<c:url value='/portal/moorimess/personalDivisionMng/personalDivisionList.do'/>" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>개인별 업무분장 작성</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<c:if test="${resultMap.EX_CREATE eq 'X' }">
			<a href="#" class="button_img01" id="btnWrite"><span>작성</span></a>
		</c:if>
	</div>

	<div class="list01">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="blktable">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="120px">작성일</th>
					<th scope="col" width="*">소속</th>
					<th scope="col" width="100px">직무</th>
					<th scope="col" width="100px">직급</th>
					<th scope="col" width="120px">작성상태</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty etList}">
						<tr>
							<td colspan="5">개인별 업무분장 작성 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${etList}">
							<tr>
								<td>
									<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
									<a href="#" class="board_2" name="linkField"><fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" /></a>
								</td>
								<td class="f_left"><a href="#" class="board_2" name="linkField"><c:out value="${result.ORGTX}"/></a></td>
								<td><a href="#" class="board_2" name="linkField"><c:out value="${result.STLTX}"/></a></td>
								<td><a href="#" class="board_2" name="linkField"><c:out value="${result.TRFGR}"/></a></td>
								<td>
									<a href="#" class="board_2" name="linkField"><c:out value="${result.APSTS_T}"/></a>
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