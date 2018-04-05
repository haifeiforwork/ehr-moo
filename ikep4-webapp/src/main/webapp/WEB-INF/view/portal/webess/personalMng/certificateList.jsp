<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		if($jq.cookie("IKEP_POPUP_NOTICE") != 'none') {
			window.open("<c:url value='/support/popup/popupNotice.do'/>",'notice','width=580,height=195,location=0,menubar=0,scrollbars=0,toolbar=0,resizable=0');
		}
		
		$("#btnWrite").click(function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/checkedRowBe002SetEntryCheck.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					//if($.trim(result.EX_RESULT) == "S"){
						$("#ajaxForm").find("input[name=jspType]").remove();
						$("#ajaxForm").append("<input type='hidden' name='jspType' value='regView'>");
						$("#ajaxForm").attr("method", "post");
						$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/certificateView.do'/>");
						$("#ajaxForm").submit();
					//}else{
					//	alert($.trim(result.EX_MESSAGE));
					//}
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
					url : "<c:url value='/portal/moorimess/personalMng/certificateDelete.do'/>",
					data : $("#ajaxForm").serialize(),
					type : "post",
					async: false,
					success : function(result) {
						alert(result.EX_MESSAGE);
						if($.trim(result.EX_RESULT) == "S"){
							$("#certificateForm").submit();
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
		
		$("a[name=linkField]").click(function(){
			$("#ajaxForm").find("input[name=jspType]").remove();
			$("#ajaxForm").html("");
			var tr = $(this).parents("tr");
			var allTr = $("#blktable").find("tbody").find("tr");
			var index = Number(allTr.index(tr));
			var rowInfo = $("span.rowInfo").eq(index).html();
			$("#ajaxForm").html(rowInfo);
			$("#ajaxForm").append("<input type=\"hidden\" name=\"eventId\" value=\"\"/>");
			$("#ajaxForm").append("<input type='hidden' name='jspType' value='view'>");
			$("#ajaxForm").attr("method", "post");
			$("#ajaxForm").attr("action", "<c:url value='/portal/moorimess/personalMng/certificateView.do'/>");
			$("#ajaxForm").submit();
		});
		
	});
	
	printFnc = function(zecerti,appid,begda,werks,certi){
		
		$("#IM_ZECERTI").val(zecerti);
		$("#IM_APPID").val(appid);
		$("#IM_BEGDA").val(begda);
		$("#IM_WERKS").val(werks);
		$("#IM_CERTI").val(certi);
		var target = "printPopup";
		var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=750px, height=800px, resizable=yes";

		var popup = window.open("", target, param);
		if(certi == "1"){
			$("#printForm").attr("action", "<c:url value='/portal/moorimess/personalMng/certificatePrint.do'/>");
		}else{
			$("#printForm").attr("action", "<c:url value='/portal/moorimess/personalMng/certificatePrintPDF.do'/>");
		}
		
		$("#printForm").attr("target", target);
		$("#printForm").submit();
		
		popup.focus();
	};
	

	printComplete = function(){
		location.href="<c:url value='/portal/moorimess/personalMng/certificateList.do'/>";
	};
})(jQuery);
</script>

<form id="certificateForm" name="certificateForm" method="post" action="<c:url value='/portal/moorimess/personalMng/certificateList.do'/>">
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>제증명서 신청/조회</h1>

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
					<th scope="col" width="8%">신청일</th>
					<th scope="col" width="15%">신청구분</th>
					<th scope="col" width="8%">신청부수</th>
					<th scope="col" width="*">제출처</th>
					<th scope="col" width="12%">용도</th>
					<th scope="col" width="10%">수신방법</th>
					<th scope="col" width="8%">담당자</th>
					<th scope="col" width="6%">진행상태</th>
					<th scope="col" width="6%">결재상태</th>
					<th scope="col" width="14%">출력</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty resultList}">
						<tr>
							<td colspan="11">제증명서 신청 내역이 없습니다.</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="result" items="${resultList}">
							<tr>
								<td><input type="radio" name="flag" value=""/></td>
								<td>
									<a href="#" class="board_2" name="linkField">
										<fmt:parseDate var="dateString" value="${result.BEGDA}" pattern="yyyyMMdd" />
										<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
									</a>
								</td>
								<td><c:out value="${result.ZCERTI}"/></td>
								<td><c:out value="${result.ZCOPIES}"/></td>
								<td><c:out value="${result.SUBMIT}"/></td>
								<td><c:out value="${result.CPURPST}"/></td>
								<td><c:out value="${result.RECEIVET}"/></td>
								<td><c:out value="${result.ZDAMG}"/></td>
								<td>
									<c:out value="${result.STATST}"/>
									<span class="rowInfo">
										<c:forEach items="${keySet}" var="key">
											<input type="hidden" name="<c:out value="${key}"/>" value="<c:out value="${result[key]}"/>"/>
										</c:forEach>
										<input type="hidden" name="CPURPST" value="<c:out value="${result.CPURPST}"/>"/>
										<input type="hidden" name="RECEIVET" value="<c:out value="${result.RECEIVET}"/>"/>
										<input type="hidden" name="APPID" value="<c:out value="${result.APPID}"/>"/>
									</span>
								</td>
								<td><c:out value="${result.ASTATT}"/></td>
								<td>
									<c:choose>
										<c:when test="${result.PRINTBT == 'X'}">
											<div class="button_box3 print"  style="text-align: center;">
												<a href="javaScript:printFnc('${result.ZCERTI}','${result.APPID}','${result.BEGDA}','${result.WERKS}','${result.CERTI}');" class="button_img01" id="btnPrint"><span>인쇄</span></a>
											</div>
										</c:when>
										<c:otherwise>
											<fmt:parseDate var="dateString1" value="${result.PDATE}" pattern="yyyyMMdd" />
											<c:if test="${result.PDATE != ''}">
												<fmt:formatDate value="${dateString1}" pattern="yyyy-MM-dd" /> <c:out value="${result.PTIME}"/>
											</c:if>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			 </tbody>
		</table>
	</div>
	 <b>□ 제증명서 출력 시, 페이지 설정 필요(아래 글 참고)</b><br>
  &nbsp;(※위치 : 인터넷익스플로러 좌측상단 「파일>페이지설정」)<br>
  &nbsp;&nbsp;①머리글(H) : -비어 있음-(상, 중, 하 전부)<br>
   &nbsp;&nbsp;②바닥글(F) : -비어 있음-(상, 중, 하 전부)<br>
   &nbsp;&nbsp;③여백(밀리미터) : 왼쪽(L) → 8mm, 오른쪽(R) → 8mm, 위쪽(T) → 19mm, 아래쪽(B) → 19mm
</div>


</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="printForm" name="printForm" method="post">
	<input type="hidden" name="IM_ZECERTI" id="IM_ZECERTI" value=""/>
	<input type="hidden" name="IM_APPID" id="IM_APPID" value=""/>
	<input type="hidden" name="IM_BEGDA" id="IM_BEGDA" value=""/>
	<input type="hidden" name="IM_WERKS" id="IM_WERKS" value=""/>
	<input type="hidden" name="IM_CERTI" id="IM_CERTI" value=""/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>