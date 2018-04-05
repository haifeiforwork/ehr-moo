<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<title>관리자 연말정산</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_style.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/ep_button.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/validation/validation.js"/>"></script>

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		
		$("#btnClose").click(function(){
			self.close();
		});
		
		$("#btnSave").click(function(){
			
			$("#ajaxForm").html("");
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"imZyear\" value=\"<c:out value="${params.EX_YEAR}"/>\"/>");
			
			$(".parameter").each(function(){
				$("#ajaxForm").append("<input type=\"hidden\" name=\""+$(this).attr("name")+"\" value=\""+$(this).val()+"\"/>");
			});
			
			$("#ajaxForm").append($("span.rowInfo").html());
			
			$.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003OninputPop14.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					alert(result.EX_MESSAGE);
					if($.trim(result.EX_RESULT) == "S"){
						opener.$.refresh(0);
						self.close();
					}else{
					}
				},complete : function(){
					
				}
			});
		});
		
		$("#btnSearch").click(function(){
			var target = "zipCodePop";
			var param = "menubar=no, toolbar=no, location=no, status=no, scrollbars=yes, width=800px, height=500px, top=200px, left=500px, resizable=no";

			var popup = window.open("", target, param);

			$("#popupForm").find("input[name=initFlag]").remove();
			$("#popupForm").append("<input type=\"hidden\" name=\"initFlag\" value=\"Y\"/>");
			$("#popupForm").append("<input type=\"hidden\" name=\"imFlag\" value=\"A\"/>");

			$("#popupForm").attr("action", "<c:url value='/portal/moorimess/personalMng/zipCodePop.do'/>");
			$("#popupForm").attr("target", target);
			$("#popupForm").submit();

			popup.focus();
		});
		
		$.initSet = function(){
			$("select[name=type] option[value=<c:out value='${params.SUBTY}'/>]").attr("selected", "selected");
			$("select[name=state] option[value=<c:out value='${params.STATE}'/>]").attr("selected", "selected");
			
			$("input[name=zipCode]").val("<c:out value="${params.PSTLZ}"/>");
			$("input[name=stateText]").val("<c:out value="${params.ORT01}"/>");
			$("input[name=town]").val("<c:out value="${params.ORT02}"/>");
			$("input[name=addAddress]").val("<c:out value="${params.LOCAT}"/>");
			$("input[name=detailAddress]").val("<c:out value="${params.STRAS}"/>");
			$("input[name=telNumber]").val("<c:out value="${params.TELNB}"/>");
		};
		
		$.initSet();
	});
})(jQuery);
</script>
</head>
<body>
<form id="addressForm" name="addressForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>주소/연락처 수정</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>

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
						<select name="type" style="width:150px" disabled class="parameter">
							<c:forEach var="result" items="${resultMap.ET_ZTYPE}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">구역</th>
					<td class="list03_td">
						<select name="state" class="STATE parameter" style="width:150px">
							<c:forEach var="result" items="${resultMap.ET_ZSTATE}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
					<th class="list03_td_bg">우편번호</th>
					<td class="list03_td">
						<input type="text" name="zipCode" value="" class="input PSTLZ parameter"/>&nbsp;
						<a href="#" class="button_img03" id="btnSearch">
							<span><img src="/base/images/ess/icon01.png" alt=""/></span>
						</a>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">시/구/군/읍/면</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="stateText" value="" class="input w90per ORT01 parameter"/>
					</td>
				</tr>
				
				<tr>
					<th class="list03_td_bg">도로/건물번호</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="town" value="" class="input w90per ORT02 parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">추가주소</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="addAddress" value="" class="input w90per LOCAT parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">상세주소(동/호)</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="detailAddress" value="" class="input w90per STRAS parameter"/>
					</td>
				</tr>
				<tr>
					<td class="list03_td" colspan="4">
						<p><font color="red">※ 반드시 우편번호 검색 버튼을 이용하여 주소를 입력해 주시기 바랍니다.</font></p>
						<p>※ 공동주택(아파트/빌라 등)에 거주하는 경우 상세주소를 반드시 입력해주세요.</p>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">연락처</th>
					<td class="list03_td" colspan="3">
						<input type="text" name="telNumber" value="" class="input w90per TELNB parameter"/>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<span class="rowInfo">
		<c:forEach items="${addressKeySet}" var="key">
			<input type="hidden" name="<c:out value='${key}'/>" value="<c:out value="${params[key]}"/>"/>
		</c:forEach>
	</span>
	
</div>
</form>
<form name="ajaxForm" id="ajaxForm" method="post">
</form>
<form name="popupForm" id="popupForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>
</body>
</html>