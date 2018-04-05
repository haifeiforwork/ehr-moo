<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var imYear = "<c:out value="${params.imYear}"/>";
		var page = "CREATE";

		$("input[name=useDate]").datepicker({dateFormat: 'yy-mm-dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

		$("#btnBack").click(function(){
			$("#cafeteriaForm").attr("method", "post");
			$("#cafeteriaForm").attr("action", "<c:url value='/portal/moorimess/personalMng/cafeteriaList.do'/><c:out value='${params.searchParam}' escapeXml='false'/>");
			$("#cafeteriaForm").submit();
		});

		$("#btnRequest").click(function(){
			$.setParam("REQUEST");
		});

		$("select[name=useItem]").change(function(){
			if($(this).val() == "111" || $(this).val() == "112"){
				$("input[name=requestPoint]").val("0.0");
				$("input[name=requestPoint]").attr("readonly", "readonly");
				$("input[name=requestPoint]").addClass("disabled");
			}else{
				$("input[name=requestPoint]").removeAttr("readonly");
				$("input[name=requestPoint]").removeClass("disabled");
			}
		});

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/cafeteriaProcess.do'/>",
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

			var form = $("#cafeteriaForm");

			form.append("<span class=\"rowInfo\"></span>");

			<c:forEach items="${keySet}" var="key">
				<c:set var="selKey">SEL_<c:out value="${key}"/></c:set>
				form.find("span.rowInfo").append("<input type=\"hidden\" name=\"SEL_<c:out value='${key}'/>\" value=\"<c:out value="${params[selKey]}"/>\"/>");
			</c:forEach>

			$("input[name=useDate]").val("<c:out value="${params.toDay}"/>");
			$("input[name=requestPoint]").val("0.0");
			$("input[name=remainPoint]").val("<c:out value="${params.SEL_RENPT}"/>");
			$("input[name=remainPoint]").parents("td").append("<c:out value="${params.SEL_RENPT}"/>");
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			<%//리스트에서 선택한 원본 데이터 %>
			$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
			$("#ajaxForm").find("span.rowInfo").html($("#cafeteriaForm").find("span.rowInfo").html());

			<%//그외 추가 데이터%>
			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"imYear\" value=\""+imYear+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"useItem\" value=\""+$("#cafeteriaForm").find("select[name=useItem]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"useDate\" value=\""+$("#cafeteriaForm").find("input[name=useDate]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"requestPoint\" value=\""+$("#cafeteriaForm").find("input[name=requestPoint]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"remainPoint\" value=\""+$("#cafeteriaForm").find("input[name=remainPoint]").val()+"\"/>");

			$.callSaveRequest();
		};

		$.initSet();
	});
})(jQuery);
</script>
<form id="cafeteriaForm" name="cafeteriaForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>카페테리아 신청</h1>

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
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">사용항목</th>
					<td class="list03_td">
						<select name="useItem" style="width:150px">
							<c:forEach var="result" items="${useItemList }">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">사용 일자</th>
					<td class="list03_td">
						<input type="text" name="useDate" id="useDate" class="input datepicker" value="" readonly="readonly" />
						<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">신청 Point</th>
					<td class="list03_td">
						<input type="text" name="requestPoint" class="input f_right" value=""/>
					</td>
				</tr>

				<tr>
					<th class="list03_td_bg">잔여 Point</th>
					<td class="list03_td">
						<input type="hidden" name="remainPoint" value=""/>
					</td>
				</tr>
				<tr>
					<td class="list03_td" colspan="2">
						<p>※ 1 Point는 금액으로 1000원에 해당합니다 (10 Point = 10,000원)</p>
						<p>※ 잔여포인트(=신청가능한 포인트)는 부가세를 제외한 포인트를 의미합니다.</p>
						<p>※ 주관부서 확인여부를 확인 후 사용하시기 바랍니다.</p>
						<p>※ 1 Point는 금액으로 1000원에 해당합니다 (10 Point = 10,000원)</p>
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