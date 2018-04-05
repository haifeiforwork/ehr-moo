<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>비밀번호 변경</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/newpassword/pw_style.css"/>" />
<style>
div.ajax-container-loading {
	position:absolute;
	top:0; left:0;
	background : url(<c:url value="/base/images/common/loading_s.gif"/>) no-repeat scroll 50% 50% white;/*transparent*/
	-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=60)"; filter:alpha(opacity=60); opacity: 0.6;
}
</style>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/ko.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>
<script type="text/javascript">
<!--



(function($) {	

	
	goNewPassword = function() {
		
		
		var userId = $("#userId").val();
		var opw = $("#opw").val();
		var npw = $("#npw").val();
		var npwc = $("#npwc").val();
		
		
		
		if(userId == "") {
			alert("아이디를 입력하세요.");
			$("#userId").trigger("focus");
			return false;
		}
		
		if(opw == "") {
			alert("이전 비밀번호를 입력하세요.");
			$("#opw").trigger("focus");
			return false;
		}
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
		
		if(userId == npw) {
			alert("새 비밀번호를 아이디와 다르게 입력하세요.");
			$("#npw").trigger("focus");
			return false;
		}
		if(opw == npw) {
			alert("새 비밀번호를 이전 비밀번호와 다르게 입력하세요.");
			$("#npw").trigger("focus");
			return false;
		}
		if("a1234567" == npw) {
			alert("새 비밀번호를 초기 비밀번호[a1234567]값과 다르게 입력하세요.");
			$("#npw").trigger("focus");
			return false;
		}
		if(npwc != npw) {
			alert("새 비밀번호를 한번더 입력하세요.");
			$("#npwc").trigger("focus");
			return false;
		}
		
		var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
        if( special_pattern.test(npwc) == true ){
            alert('특수문자는 사용할 수 없습니다.');
            return false;
		}
		
		//$("#newPasswordForm").ajaxLoadStart();
		$("#wrap_pw").ajaxLoadStart();
		$("#newPasswordForm").submit();
	};
	
	selfClose =function() {
		self.close();
	};
	
	$(document).ready(function() {

		
		<c:if test="${!empty param.error}">
			<c:choose>
				<c:when test = "${param.error eq '6'}">
					alert("BW 패스워드 변경 규칙에 맞지 않습니다.");
				</c:when>
				<c:when test = "${param.error eq '5'}">
					alert("BW 패스워드를 변경 할 수 없습니다.\n비밀번호 변경은 하루에 한번만 가능합니다.\n변경내역 초기화를 위해 관리자에게 문의하십시요.");
				</c:when>
				<c:when test = "${param.error eq '4'}">
					alert("SAP 패스워드 변경 규칙에 맞지 않습니다.");
				</c:when>
				<c:when test = "${param.error eq '3'}">
					alert("SAP 패스워드를 변경 할 수 없습니다.\n비밀번호 변경은 하루에 한번만 가능합니다.\n변경내역 초기화를 위해 관리자에게 문의하십시요.");
				</c:when>
				<c:when test = "${param.error eq '2'}">
					alert("해당하는 사용자가 없습니다. 아이디를 확인해주세요.");
				</c:when>
				<c:when test = "${param.error eq '0'}">
					$("#wrap_pw").ajaxLoadStart();
					alert("패스워드 변경완료.\n확인을 누르면 로그인 창으로 이동합니다.");
					location.href = "<c:url value='/logout.do'/>";	
				</c:when>
				<c:otherwise>
					alert("사용중인 패스워드를 잘못 입력하셨습니다.\n확인후 다시 시도하십시요.");
				</c:otherwise>
			</c:choose>
		</c:if>
		
		$("#submitButton").click(function() {
			
			goNewPassword();

		});
		
		$("#searchPwButton").click(function() {
			
			var url = '<c:url value="/passwordSearchForm.do"/>';

			iKEP.popupOpen(url, {width:400, height:300}, "passwordSearchForm");

		});
		
		$("#findPwButton").click(function() {
			
			iKEP.popupOpen('<c:url value="/portal/main/findPassword.do"/>', {width:530, height:260, status:true, scrollbar:false, toolbar:false, location:false}, 'FindPassword');

		});
		
		$(document).keypress( function(event) {
			if(event.which == 13) {
				goNewPassword();
			}
		});
		
		$(".close").click(selfClose);
		
	});
	

	
	
})(jQuery);
-->
</script>
</head> 
<body>
<div  style="width: 530px; height: 260px;margin:0 auto; margin-top: 200px;">
<form name="newPasswordForm" id="newPasswordForm" action="<c:url value='/sapPasswordModify.do'/>" method="post" >
<input type="hidden" id="workType" name="workType" value="userself"/>
<input type="hidden" id="userId" name="userId" class="input_100" value="${userId}" />
<!-- title -->
<div><table width="100%" border="0" cellpadding="0" cellspacing="0" class="title_box">
  <tbody>
    <tr>
      <td width="35" class="f_right"><img src="<c:url value="/base/images/popup/pop_icon_01.png"/>"/></td>
      <td class="f_white f_bold" style="padding-left:5px">비밀번호변경</td>
    </tr>
  </tbody>
</table>
</div>
<!--contents -->
<div style="height:100px; padding:15px 0;" id="wrap_pw">
<table width="300"border="0" align="center" cellpadding="0" cellspacing="0" style="font-size:11px;">
  <tbody>
    <tr>
      <td width="10"><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
      <td height="27">기존 비밀번호</td>
      <td><span class="help_wrap">
        <input type="password" id="opw" name="opw" class="input_100"  />
      </span></td>
    </tr>
    <tr>
      <td><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
      <td height="27">새 비밀번호</td>
      <td><span class="help_wrap">
        <input type="password" id="npw" name="npw" class="input_100"  />
      </span></td>
    </tr>
    <tr>
      <td><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
      <td height="27">새 비밀번호 확인</td>
      <td><span class="help_wrap">
        <input type="password" id="npwc" name="npwc" class="input_100"  />
      </span></td>
    </tr>
    <tr>
      <td colspan="3" class="f_right" style="padding-top:10px">
      <a href="#" class="button_img01" ><span id="submitButton">확인</span></a>
      <a href="#" class="button_img01" ><span id="searchPwButton">비밀번호찾기</span></a>
      </td>
      </tr>
  </tbody>
</table>

</div>


<!-- bottom text -->
<div style="background-color:#e7e7e7; margin-top:10px; padding:15px">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="f_12">
    <tbody>
      <tr>
        <td width="3" bgcolor="#adcc85">&nbsp;</td>
        <td style="padding-left:10px;">정보보안을 위해 비밀번호는<span class="f_green"> 6개월</span> 주기로 변경해야합니다.<br>
          비밀번호는 특수문자 사용을 금지하며, 영문/숫자 조합<span class="f_red">8자리</span>로 설정바랍니다.<br>
        비밀번호 변경 시 네오넷 EP, 메신저, SAP 비밀번호가 변경됩니다.</td>
      </tr>
    </tbody>
  </table>
</div>
</form>
<!--페이지 사이즈 530X265--> 
</div>
</body>
</html>