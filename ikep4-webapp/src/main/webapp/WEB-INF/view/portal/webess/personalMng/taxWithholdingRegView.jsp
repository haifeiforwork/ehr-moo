<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		$("#btnBack").click(function(){

			if(!confirm("저장하지 않은 데이터가 손실됩니다.\n이전화면으로 이동하시겠습니까?")){
				return;
			}

			$("#taxWithholdingForm").attr("method", "post");
			$("#taxWithholdingForm").attr("action", "<c:url value='/portal/moorimess/personalMng/taxWithholdingList.do'/>");
			$("#taxWithholdingForm").submit();
		});

		$("#btnRequest").click(function(){
			$.setParam("REQUEST");
		});

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/taxWithholdingProcess.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				success : function(result) {
					alert(result.EX_MESSAGE);
					if(result.EX_RESULT == "S"){
						$("#taxWithholdingForm").attr("method", "post");
						$("#taxWithholdingForm").attr("action", "<c:url value='/portal/moorimess/personalMng/taxWithholdingList.do'/>");
						$("#taxWithholdingForm").submit();
					}else{
						//에러처리
					}
				}
			});
		};

		$.initSet = function(){

			var form = $("#taxWithholdingForm");

			form.append("<span class=\"rowInfo\"></span>");

			<c:forEach items="${keySet}" var="key">
				form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[key]}"/>\"/>");
			</c:forEach>

			$("input[name=requestDate]").val("<c:out value="${params.toDay}"/>");
			$("input[name=requestDate]").parents("td").append("<c:out value="${params.toDay}"/>");

			$("input[name=address]").val("<c:out value="${params.ADDRESS}"/>");
			$("input[name=address]").parents("td").append("<c:out value="${params.ADDRESS}"/>");

			$("input[name=staff]").val("<c:out value="${params.ZDAMG}"/>");
			$("input[name=staff]").parents("td").append("<c:out value="${params.ZDAMG}"/>");
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#taxWithholdingForm").find("span.rowInfo").html());

			$.setInputParameter();

			$.callSaveRequest();
		};

		$.setInputParameter = function(){
			var name, val;

			if($("#ajaxForm").find("span.etcInfo").length == 0){
				$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			}

			$("#taxWithholdingForm").find(".parameter").each(function(){
				name = $(this).attr("name");
				val = $(this).val();

				$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\""+name+"\" value=\""+val+"\"/>");
			});
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="taxWithholdingForm" name="taxWithholdingForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>근로소득 원천징수영수증 신청</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
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
						<input type="hidden" name="requestDate" class="parameter" value=""/>
					</td>
					<th class="list03_td_bg">신청부수</th>
					<td class="list03_td">
						<select name="copy" class="parameter w150px">
							<c:forEach var="result" items="${copyList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">제출처</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="submission" class="input w90per parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">용도</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="purpose" class="input w90per parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">특기사항</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="specialNote" class="input w90per parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">주소</th>
					<td class="list03_td" colspan="3">
						<input type="hidden" name="address" class="parameter" value=""/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">담당자</th>
					<td class="list03_td" colspan="3">
						<input type="hidden" name="staff" class="parameter" value=""/>
					</td>
				</tr>
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