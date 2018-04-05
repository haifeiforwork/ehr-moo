<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript">
(function($){
	$(document).ready(function(){

		var page = "CREATE";

		$("#btnBack").click(function(){
			$("#addressForm").attr("method", "post");
			$("#addressForm").attr("action", "<c:url value='/portal/moorimess/personalMng/addressList.do'/>");
			$("#addressForm").submit();
		});

		$("#btnSave").click(function(){
			$.setParam("SAVE");
		});

		$("#btnSearch").click(function(){
			var target = "zipCodePop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=800px, height=500px, top=200px, left=300px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").find("input[name=initFlag]").remove();
			$("#popupForm").append("<input type=\"hidden\" name=\"initFlag\" value=\"Y\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"B\"/>");

			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/personalMng/zipCodePop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();
		});

		$.initSet = function(){
			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				var form = $("#addressForm");
				form.append("<span class=\"rowInfo\"></span>");

				<c:forEach items="${keySet}" var="key">
					form.find("span.rowInfo").append("<input type=\"hidden\" name=\"<c:out value='${key}'/>\" value=\"<c:out value="${params[key]}"/>\"/>");
				</c:forEach>

				$("select[name=type] option[value=<c:out value='${params.SUBTY}'/>]").attr("selected", "selected");
				$("select[name=state] option[value=<c:out value='${params.STATE}'/>]").attr("selected", "selected");
				$("input[name=zipCode]").val("<c:out value="${params.PSTLZ}"/>");
				$("input[name=stateText]").val("<c:out value="${params.ORT01}"/>");

				<c:if test="${!empty params.ORT02}">
				$("input[name=town]").val("<c:out value="${params.ORT02}"/>");
				</c:if>

				$("input[name=address]").val("<c:out value="${params.STRAS}"/>");
				$("input[name=addressDetail]").val("<c:out value="${params.LOCAT}"/>");

				page = "CHANGE";
			}
		};

		$.setParam = function(eventId){

			$("#ajaxForm").html("");

			if("<c:out value="${params.dataLoad}"/>" == "Y"){
				$("#ajaxForm").append("<span class=\"rowInfo\"></span>");
				$("#ajaxForm").find("span.rowInfo").html($("#addressForm").find("span.rowInfo").html());
			}

			$("#ajaxForm").append("<span class=\"etcInfo\"></span>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"eventId\" value=\""+eventId+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"page\" value=\""+page+"\"/>");

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"type\" value=\""+$("#addressForm").find("select[name=type]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"typeName\" value=\""+$("#addressForm").find("select[name=type] option:selected").text()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"state\" value=\""+$("#addressForm").find("select[name=state]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"stateName\" value=\""+$("#addressForm").find("select[name=state] option:selected").text()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"zipCode\" value=\""+$("#addressForm").find("input[name=zipCode]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"stateText\" value=\""+$("#addressForm").find("input[name=stateText]").val()+"\"/>");

			<c:if test="${!empty params.ORT02}">
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"town\" value=\""+$("#addressForm").find("input[name=town]").val()+"\"/>");
			</c:if>

			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"address\" value=\""+$("#addressForm").find("input[name=address]").val()+"\"/>");
			$("#ajaxForm").find("span.etcInfo").append("<input type=\"hidden\" name=\"addressDetail\" value=\""+$("#addressForm").find("input[name=addressDetail]").val()+"\"/>");

			$.callSaveRequest();
		};

		$.callSaveRequest = function(){
			$jq.ajax({
				url : "<c:url value='/portal/moorimess/personalMng/addressProcess.do'/>",
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

		$.initSet();
	});
})(jQuery);
</script>
<form id="addressForm" name="addressForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>주소사항 조회/수정</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnBack"><span>뒤로</span></a>
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
	</div>

	<c:set var="etZtype" value="${resultCode.ET_ZTYPE }"/>
	<c:set var="etZstate" value="${resultCode.ET_ZSTATE }"/>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="25%"/>
				<col width="15%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">신청구분</th>
					<td class="list03_td" colspan="3">
						<select name="type" style="width:150px">
							<c:forEach var="result" items="${etZtype}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">구역</th>
					<td class="list03_td">
						<select name="state" class="STATE" style="width:150px">
							<c:forEach var="result" items="${etZstate}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">우편번호</th>
					<td class="list03_td">
						<input type="text" name="zipCode" value="" class="input PSTLZ"/>&nbsp;
						<a href="#" class="button_img03" id="btnSearch">
							<span><img src="/base/images/ess/icon01.png" alt=""/></span>
						</a>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">시/군/구</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="stateText" value="" class="input w90per ORT01"/>
					</td>
				</tr>

				<c:choose>
					<c:when test="${!empty params.ORT02}">
						<tr>
							<th class="list03_td_bg">동/읍/면</th>
							<td class="list03_td" colspan="3">
								<input type="text" name="town" value="" class="input w90per ORT02"/>
							</td>
						</tr>
						<tr>
							<th class="list03_td_bg">주소</th>
							<td class="list03_td" colspan="3">
								<input type="text" name="address" value="" class="input w90per STRAS"/>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
					<tr>
						<th class="list03_td_bg">주소(도로명)</th>
						<td class="list03_td" colspan="3">
							<input type="text" name="address" value="" class="input w90per STRAS"/>
						</td>
					</tr>
					</c:otherwise>
				</c:choose>

				<tr>
					<th class="list03_td_bg">상세주소</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="addressDetail" value="" class="input w90per LOCAT"/>
					</td>
				</tr>
				<tr>
					<td class="list03_td" colspan="4">
						<p><font color="red">※ 반드시 우편번호 검색 버튼을 이용하여 주소를 입력해 주시기 바랍니다.</font></p>
						<p>※ 공동주택(아파트/빌라 등)에 거주하는 경우 상세주소를 반드시 입력해주세요.</p>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="popupForm" name="popupForm" method="post">
	<input type="hidden" name="IM_FLAG" value=""/>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>