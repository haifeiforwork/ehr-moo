<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){
		var page = "CREATE";

		$("#btnBack").click(function(){
			$("#clubForm").attr("method", "post");
			$("#clubForm").attr("action", "<c:url value='/portal/moorimess/personalMng/clubList.do'/>");
			$("#clubForm").submit();
		});

		$("#btnSave, #btnRequest").click(function(){
			if(this.id == "btnSave"){
				$.setParam("SAVE");
			}else if(this.id == "btnRequest"){
				var prefix = $("select[name=joinType] option:selected").text();
				if(confirm(prefix+"신청 하시겠습니까?")){
					$.setParam("REQUEST");
				}
			}
		});

		$("select[name=joinType]").change(function(){

			$("select[name=club] option[value=]").attr("selected", "selected");
			$("select[name=deductType] option[value=]").attr("selected", "selected");
			$("select[name=deductType]").attr("disabled", "disabled");
			$("input[name=dues]").val("");

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

						target = $("select[name=club]");
						target.html("");

						for(var i=0 ; i<data.length ; i++){
							target.append("<option value=\""+data[i].INFCD+"\">"+data[i].INTXT+"</option>");
						}
					}else{
						alert(result.EX_MESSAGE);
					}
				}
			});
		});

		$("select[name=club]").change(function(){

			var clubVal = $(this).val();

			if(clubVal == "11" || clubVal == "41"){
				$("select[name=deductType] option[value=2]").attr("selected", "selected");
				$("select[name=deductType]").removeAttr("disabled");
			}else{
				$("select[name=deductType] option[value=]").attr("selected", "selected");
				$("select[name=deductType]").attr("disabled", "disabled");
			}

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

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/clubProcess.do'/>",
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
					form.find("span.rowInfo").append("<input type=\"text\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
				</c:forEach>

				<fmt:parseDate var="dateString" value="${params.SEL_REQDT}" pattern="yyyyMMdd" />
				$("input[name=requestDate]").val("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");
				$("input[name=requestDate]").parents("td").append("<fmt:formatDate value="${dateString}" pattern="yyyy-MM-dd" />");

				$("select[name=joinType] option[value=<c:out value="${params.SEL_ZJOIN}"/>]").attr("selected", "selected");
				$("select[name=joinType]").change();

				$("select[name=club] option[value=<c:out value="${params.SEL_INFCD}"/>]").attr("selected", "selected");
				$("select[name=club]").change();

				$("select[name=deductType] option[value=<c:out value="${params.SEL_PAYGN}"/>]").attr("selected", "selected");

				$("input[name=dues]").val("<fmt:formatNumber value="${params.SEL_MBAMT}" groupingUsed="true"/>");

				page = "CHANGE";

			}else{
				$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
				$("input[name=requestDate]").parents("td").append("<c:out value="${params.toDay}"/>");

				$("select[name=joinType]").change();
			}
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#clubForm").find("span.rowInfo").html());

			<%//결재라인 데이터 처리%>
			var apprCnt = $("#clubForm").find("span.apprInfo").find("input[name=ETLINE_MANDT]").length;

			$("#ajaxForm").append("<span class=\"apprInfo\"></span>");
			$("#ajaxForm").find("span.apprInfo").append($("#clubForm").find("span.apprInfo").html());
			$("#ajaxForm").find("span.apprInfo").append("<input type=\"hidden\" name=\"apprCnt\" value=\""+apprCnt+"\"/>");

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			$.setInputParameter();

			$.callSaveRequest();
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
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnRequest"><span>신청</span></a>
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
						<input type="hidden" name="requestDate" value="" class="parameter"/>
					</td>
					<th class="list03_td_bg">가입/탈퇴</th>
					<td class="list03_td">
						<select name="joinType" style="width:150px" class="parameter">
							<c:forEach var="result" items="${joinTypeList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">동호회명</th>
					<td class="list03_td">
						<select name="club" style="width:150px" class="parameter"></select>
					</td>
					<th class="list03_td_bg">공제구분</th>
					<td class="list03_td">
						<select name="deductType" style="width:150px" class="parameter" disabled>
							<option value=""></option>
							<c:forEach var="result" items="${deductTypeList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="list03_td" colspan="2"></td>
					<th class="list03_td_bg">동호회비</th>
					<td class="list03_td">
						<input type="text" name="dues" class="input f_right parameter" value=""/>
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