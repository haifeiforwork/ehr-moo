<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="prefix" value="message.portal.login.loginForm"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/newpassword/pw_style.css"/>" />
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/ko.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript">
<!--   
(function($){	 
	$(document).ready(function() {   
		
		<c:if test="${!empty param.error}">
			<c:choose>
				<c:when test = "${param.error eq '1'}">
					alert("등록되지 않은 사용자입니다.");
				</c:when>
				<c:when test = "${param.error eq '2'}">
					alert("입력하신 사번이 맞지 않습니다.");
				</c:when>
				<c:when test = "${param.error eq '3'}">
					alert("입력하신 휴대폰 번호가 맞지 않습니다.");
				</c:when>
				<c:when test = "${param.error eq '4'}">
					alert("휴대폰 번호가 등록되지 않은 사용자입니다. 관리자에게 문의하시기 바랍니다.");
				</c:when>
				<c:otherwise>
					alert("비밀번호가 휴대폰으로 발송되었습니다.");
					self.close();
				</c:otherwise>
			</c:choose>
		</c:if>
		
		$("#passwordUpdateBtn").click(function() {  
			
			var npw = $("#npw").val();
			var npwr = $("#npwr").val();
			
			if(npw == "") {
				alert("새 비밀번호를 입력하세요.");
				$("#npw").trigger("focus");
				return false;
			}
			
			var chk_num = npw.search(/[0-9]/g);
			var chk_eng = npw.search(/[a-z]/ig);
			if(chk_num < 0 || chk_eng < 0){
				alert("비밀번호는 영문+숫자 조합으로 하셔야합니다.");
				return;
			}
			
			if(npw.length != 8) {
				alert("비밀번호를 8자리로 입력하세요.");
				$("#npw").trigger("focus");
				return false;
			}
			if("${uid}" == npw) {
				alert("새 비밀번호를 아이디와 다르게 입력하세요.");
				$("#npw").trigger("focus");
				return false;
			}
			if("a1234567" == npw) {
				alert("새 비밀번호를 초기 비밀번호[a1234567]값과 다르게 입력하세요.");
				$("#npw").trigger("focus");
				return false;
			}
			if(npwr != npw) {
				alert("새 비밀번호를 한번더 입력하세요.");
				$("#npwr").trigger("focus");
				return false;
			}
			
			var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
	        if( special_pattern.test(npwr) == true ){
	            alert('특수문자는 사용할 수 없습니다.');
	            return false;
			}
			
			$.ajax({
				url : "<c:url value='/passwordUpdate.do' />",
				data : $("#searchForm").serialize(),
				type : "post",
				success : function(data) { 
					if(data == "error7"){
						alert("패스워드 변경 시간이 초과 되었습니다.");
					}else{
						alert("패스워드 변경이 완료되었습니다.");
						//opener.location.href="<c:url value='/portal/main/portalMain.do'/>";
						window.close();
					}
				},
				error : function() {alert("패스워드 변경 시간이 초과 되었습니다.");}
			});
		});
		
		$("#close").click(function() {  
			self.close();
		});
		
	});
})(jQuery);

//-->
</script>

<html>
<head>
<meta charset="utf-8">
<title>비밀번호 재설정</title>
</head>
<body>
<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false;">
<input type="hidden" id="uid" name="uid" value="${uid}" />
<input type="hidden" id="ucd" name="ucd" value="${ucd}" />
<!-- title -->
<div><table width="100%" border="0" cellpadding="0" cellspacing="0" class="title_box">
  <tbody>
    <tr>
      <td width="40" class="f_right"><img src="<c:url value="/base/images/popup/pop_icon_04.png"/>"/></td>
      <td class="f_white f_bold" style="padding-left:5px">비밀번호 재설정</td>
      <td width="60" bgcolor="#000000"><img src="<c:url value="/base/images/popup/btn_close.png"/>" width="62" height="40" alt="" id="close" style="cursor: pointer;"/></td>
    </tr>
  </tbody>
</table>
</div>
<!--  text area -->
<div style="padding:15px">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="f_12">
    <tbody>
      <tr>
        <td width="3" bgcolor="#adcc85">&nbsp;</td>
        <td style="padding-left:10px;">새 비밀번호를 입력해주시기 바랍니다.</td>
      </tr>
    </tbody>
  </table>
</div>

<!--contents -->
<div style="height:100px; padding:15px 0;" >
  <table width="300"border="0" align="center" cellpadding="0" cellspacing="0" style="font-size:11px;">
    <tbody>
      <tr>
      	<td width="10"><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
        <td height="27">새 비밀번호</td>
        <td height="27" class="f_right">
          <input type="password" class="input_100" value="" id="npw" name="npw" />
        </td>
      </tr>
      <tr>
      	<td width="10"><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
        <td height="27">새 비밀번호 확인</td>
        <td height="27" class="f_right">
          <input type="password" class="input_100" value="" id="npwr" name="npwr" />
       </td>
      </tr>
      <tr>
        <td class="f_center" style="padding-top:15px" colspan="3"><a href="#" class="button_img01"><span id="passwordUpdateBtn">확인</span></a> </td>
      </tr>
    </tbody>
  </table>
</div>
</form>

<!--페이지 사이즈 400X220-->

</body>
</html>

