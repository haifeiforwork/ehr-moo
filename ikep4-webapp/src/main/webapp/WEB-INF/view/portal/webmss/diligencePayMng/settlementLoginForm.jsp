<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<title>관리자 연말정산</title>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/mss_settlement.css"/>" />

<script type="text/javascript">
(function($){
	$(document).ready(function(){
		$("#goWin").click(function(){
			window.open('http://www.yesone.go.kr/index.jsp');
		});
		
		$("#goMain").click(function(){
			if($.trim($("input[name=imPernr]").val()) == ""){
				alert("조회할 사번을 입력하세요.");
				$("input[name=imPernr]").focus();
				return;
			}
			
			if($.trim($("input[name=imAdminid]").val()) == ""){
				alert("담당자ID를 입력하세요.");
				$("input[name=ADMIDID]").focus();
				return;
			}
			
			if($.trim($("input[name=imPasswd]").val()) == ""){
				alert("비밀번호를 입력하세요.");
				$("input[name=imPasswd]").focus();
				return;
			}
			
			$("#form").submit();
			
		});
		
		$("input").keyup(function(e){
			if(e.which == 13){
				$("#goMain").click();
			}
		});
		
		<c:if test="${resultMap.EX_RESULT eq 'E'}">
		alert("<c:out value="${resultMap.EX_MESSAGE}"/>");
		</c:if>
		
		$("input[name=imPernr]").focus();
	});
})(jQuery);
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form name="form" id="form" method="post" action="<c:url value='/portal/moorimmss/diligencePayMng/settlementLogin.do'/>">
	<input type="hidden" name="OnInputProcessing">
	<table width="100%" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td height="200"></td>
		</tr>
		<tr>
			<td align="center">
				<font color="blue"> * <B> 연말정산 입력 SYSTEM 입니다.</B> </font> <BR>
			</td>
		</tr>
		<tr>
			<td class="TR_pad10" align="center">
				<table width="40%" border="0" cellpadding="0" cellspacing="1" bgcolor="A1CAD4">
					<tr>
						<td width="50%" align="center" class="T01_2">사원번호</td>
						<td class="T01_4" align="left">
							<input type="text" name="imPernr" class="input" size="10" maxlength="8">
						</td>
					</tr>
					<tr>
						<td width="50%" align="center" class="T01_2">담당자ID</td>
						<td class="T01_4" align="left">
							<input type="text" name="imAdminid" class="input" size="10" maxlength="8">
						</td>
					</tr>
					<tr>
						<td width="50%" align="center" class="T01_2">비밀번호</td>
						<td class="T01_4" align="left">
							<input type="password" name="imPasswd" class="input" size="10" maxlength="8">
							<img src="<c:url value="/base/images/ess/yearEndSettlement/butt_ok.gif"/>" id="goMain" style="cursor:hand;vertical-align:top"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="<c:url value="/base/images/ess/yearEndSettlement/ntslink.gif"/>" style="cursor:hand;vertical-align:top" id="goWin"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>