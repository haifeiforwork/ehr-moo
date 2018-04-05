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
<script type="text/javascript" src="<c:url value="/base/js/jquery/progress/progress.js"/>"></script>

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
			
			$("#ajaxForm").append("<input type=\"hidden\" name=\"MANDT\" value=\"<c:out value="${resultMap.EX_FAM.MANDT}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"ZYEAR\" value=\"<c:out value="${resultMap.EX_FAM.ZYEAR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"PERNR\" value=\"<c:out value="${resultMap.EX_FAM.PERNR}"/>\"/>");
			$("#ajaxForm").append("<input type=\"hidden\" name=\"BEGDA\" value=\"<c:out value="${resultMap.EX_FAM.BEGDA}"/>\"/>");
			
			$("#ajaxForm").append($("span.rowInfo").html());
			
			$.ajax({
				url : "<c:url value='/portal/moorimmss/diligencePayMng/callPY003OninputPop13.do'/>",
				data : $("#ajaxForm").serialize(),
				type : "post",
				async: false,
				success : function(result) {
					if($.trim(result.EX_RESULT) == "S"){
						opener.$.refresh(0);
						self.close();
					}else{
						alert(result.EX_MESSAGE);
					}
				},complete : function(){}
			});
		});
		
		$.initSet = function(){
			if("<c:out value="${resultMap.EX_FLAG}"/>" == "X"){
				alert("<c:out value="${resultMap.EX_MESSAGE}"/>");
				opener.$.refresh(0);
				self.close();
			}else{
				$("input[name=lastName]").val("<c:out value="${resultMap.EX_FAM.FNMHG}"/>");
				$("input[name=firstName]").val("<c:out value="${resultMap.EX_FAM.LNMHG}"/>");
				
				$("input[name=regNo1]").val("<c:out value="${resultMap.EX_FAM.REGNO1}"/>");
				$("input[name=regNo2]").val("<c:out value="${resultMap.EX_FAM.REGNO2}"/>");
				
				$("input[name=relations]").val("<c:out value="${resultMap.EX_FAM.DPTYP}"/>");
			}
		};
		
		$.initSet();
	});
})(jQuery);
</script>
</head>
<body>
<form id="familyAddForm" name="familyAddForm" method="post" action="" >
<div id="wrap">
	<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>가족사항 삭제</h1>

	<!-- 상단버튼-->
	<div class="button_box">
		<a href="#" class="button_img01" id="btnSave"><span>저장</span></a>
		<a href="#" class="button_img01" id="btnClose"><span>닫기</span></a>
	</div>

	<div class="list03">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<caption></caption>
			<colgroup>
				<col width="30%"/>
				<col width="*"/>
			</colgroup>
			<tbody>
				<tr>
					<th class="list03_td_bg">성명</th>
					<td class="list03_td">
						<c:out value="${resultMap.EX_FAM.LNMHG}"/><c:out value="${resultMap.EX_FAM.FNMHG}"/>
						<input type="hidden" name="lastName" value="" class="parameter"/>
						<input type="hidden" name="firstName" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">주민번호</th>
					<td class="list03_td">
						<c:out value="${resultMap.EX_FAM.REGNO1}"/>&nbsp;-&nbsp;<c:out value="${resultMap.EX_FAM.REGNO2}"/>
						<input type="hidden" name="regNo1" maxlength="6" value="" class="parameter"/>
						<input type="hidden" name="regNo2" maxlength="7" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">관계</th>
					<td class="list03_td">
						<c:forEach var="result" items="${ET_FAM}">
							<c:if test="${result.KEY eq resultMap.EX_FAM.DPTYP}"><c:out value="${result.VALUE }"/></c:if>
						</c:forEach>
						<input type="hidden" name="relations" value="" class="parameter"/>
					</td>
				</tr>
				<tr>
					<th class="list03_td_bg">종료일자</th>
					<td class="list03_td">
						<select name="endDate" class="parameter w150px">
							<c:forEach var="result" items="${ET_ENDDA}">
								<option value="<c:out value="${result.KEY }"/>"><c:out value="${result.VALUE }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<table width="100%" border="0" class="margin_delimiter">
		<tbody>
			<tr>
				<td width="10"><img src="<c:url value="/base/images/ess/dot_01.png"/>" width="10" height="3" alt=""/></td>
				<td>※ 종료일자 입력</td>
			</tr>
			<tr>
				<td width="10"></td>
				<td>
					<c:forEach var="result" items="${ET_ENDDA}">
						<p><c:out value="${result.VALUE }"/> : <c:out value="${fn:substring(result.KEY, 0, 4) }"/>년 중 사망자 선택</p>
					</c:forEach>
				</td>
			</tr>
		</tbody>
	</table>
		
	<span class="rowInfo">
		<c:forEach items="${perFuncKeySet}" var="key">
			<input type="hidden" name="perFunc_<c:out value='${key}'/>" value="<c:out value="${params[key]}"/>"/>
		</c:forEach>
	</span>
	
</div>
</form>
<form name="ajaxForm" id="ajaxForm" method="post">
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