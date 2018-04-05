<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnBack").click(function(){
			$("#clubForm").attr("method", "post");
			$("#clubForm").attr("action", "<c:url value='/portal/moorimess/personalMng/clubList.do'/>");
			$("#clubForm").submit();
		});

		$("#btnDelete").click(function(){
			if(confirm("정말로 삭제하시겠습니까?")){
				$.callDeleteRequest();
			}
		});

		$("input[name=joinType]").change(function(){

			$("#ajaxForm").html("");

			$.setInputParameter();

			var infcdCnt = $("#clubForm").find("span.infcdListInfo").find("input[name=INFCD_MANDT]").length;

			$("#ajaxForm").append("<span class=\"infcdListInfo\"></span>");

			$("#ajaxForm").find("span.infcdListInfo").append($("#clubForm").find("span.infcdListInfo").html());
			$("#ajaxForm").find("span.infcdListInfo").append("<input type=\"hidden\" name=\"infcdCnt\" value=\""+infcdCnt+"\"/>");

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/retrieveGetJoinCircle.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async : false,
				success : function(result) {
					if($.trim(result.EX_RESULT == "S")){
						data = result.ET_INFCD;
						for(var i=0 ; i<data.length ; i++){
							if(data[i].INFCD == "<c:out value="${params.SEL_INFCD}"/>"){
								$("input[name=club]").parents("td").append(data[i].INTXT);
							}
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});

		$("input[name=club]").change(function(){

			$("input[name=dues]").val("");

			$("#ajaxForm").html("");

			$.setInputParameter();

			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#clubForm").find("span.rowInfo").html());

			var infcdCnt = $("#clubForm").find("span.infcdListInfo").find("input[name=INFCD_MANDT]").length;

			$("#ajaxForm").append("<span class=\"infcdListInfo\"></span>");

			$("#ajaxForm").find("span.infcdListInfo").append($("#clubForm").find("span.infcdListInfo").html());
			$("#ajaxForm").find("span.infcdListInfo").append("<input type=\"hidden\" name=\"infcdCnt\" value=\""+infcdCnt+"\"/>");

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/retrieveGetMbamt.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async : false,
				success : function(result) {
					if($.trim(result.EX_RESULT == "S")){
						var dues = result.EX_LIST.MBAMT.toString();
						$("input[name=dues]").val(dues.addComma());

						var etLine = result.ET_LINE;
						var target = $("span.apprInfo");

						target.html("");

						for(var i=0 ; i<etLine.length ; i++){
							<c:forEach items="${apprKeySet}" var="key">
							target.append("<input type=\"hidden\" name=\"ETLINE_"+"<c:out value="${key}"/>"+"\" value=\""+etLine[i]["<c:out value="${key}"/>"]+"\"/>");
							</c:forEach>
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});

		$("input[name=dues]").blur(function(){
			var tmp = $(this).val();
			tmp = tmp.clearComma();
			$(this).val(tmp.addComma());
		});

		$.callDeleteRequest = function(){

			$("#ajaxForm").html("");

			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#clubForm").find("span.rowInfo").html());

			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/clubDelete.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#btnBack").click();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){
			if("<c:out value="${params.dataLoad}"/>" == "Y"){

				var form = $("#clubForm");

				form.append("<span class=\"rowInfo\"></span>");

				<c:forEach items="${keySet}" var="key">
					<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
					form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>

				<fmt:parseDate var="dateString" value="${params.SEL_REQDT}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("input[name=joinType]").val("<c:out value="${params.SEL_ZJOIN}"/>");
				$("input[name=joinType]").change();

				$("input[name=club]").val("<c:out value="${params.SEL_INFCD}"/>");
				$("input[name=club]").change();

				$("input[name=deductType]").val("<c:out value="${params.SEL_PAYGN}"/>");

				$("input[name=dues]").val("<fmt:formatNumber value="${params.SEL_MBAMT}" groupingUsed="true"/>");
			}else{
				$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
			}
		};

		$.setInputParameter = function(){
			var name, val;

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#clubForm").find(".parameter").each(function(){
				name = $(this).attr("name");
				val = $(this).val();

				if(name == "dues"){
					val = val.clearComma();
				}

				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\""+name+"\" value=\""+val+"\"/>");

			});
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="clubForm" name="clubForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>동호회 신청/조회</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<c:if test="${params.exFlag eq 'D' }">
			<a href="#" class="button_img01" id="btnDelete"><span>삭제</span></a>
		</c:if>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청일자</th>
					<td class="list03_td">
						<fmt:parseDate var="dateString" value="${params.SEL_REQDT}" pattern="yyyyMMdd" />
						<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />
						<input type="hidden" name="requestDate" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">가입/탈퇴</th>
					<td class="list03_td">
						<c:forEach var="result" items="${joinTypeList }">
							<c:if test="${result.KEY eq params.SEL_ZJOIN}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="joinType" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">동호회명</th>
					<td class="list03_td">
						<input type="hidden" name="club" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">공제구분</th>
					<td class="list03_td">
						<c:forEach var="result" items="${deductTypeList }">
							<c:if test="${result.KEY eq params.SEL_PAYGN}">
								<c:out value="${result.VALUE }"/>
							</c:if>
						</c:forEach>
						<input type="hidden" name="deductType" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<td class="list03_td" colspan="2"></td>
					<th class="list03_td_bg">동호회비</th>
					<td class="list03_td">
						<fmt:formatNumber value="${params.SEL_MBAMT}" groupingUsed="true"/>
						<input type="hidden" name="dues" class="parameter" value=""/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<span class="infcdListInfo">
	<c:forEach items="${clubList }" var="result">
		<c:forEach items="${infcdKeySet}" var="key">
			<input type="hidden" name="INFCD_<c:out value='${key}'/>" value="<c:out value="${result[key]}"/>"/>
		</c:forEach>
	</c:forEach>
</span>

<span class="apprInfo">
</span>

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