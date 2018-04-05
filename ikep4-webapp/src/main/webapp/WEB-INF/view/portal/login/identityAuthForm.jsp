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
		
		$("#passwordSearchBtn").click(function() {  
			$("#pwdSearchForm").submit();
		});
		
		$("#nextView").click(function() {  
			if($("#code").val() == ""){
				alert("인증번호를 요청하세요.");
			}else{
				$.ajax({
					url : "<c:url value='/requestCertificationCheck.do' />",
					data : $("#searchForm").serialize(),
					type : "post",
					success : function(data) {
						if(data > 0){
							$("#uid").val($("#searchId").val());
							$("#ucd").val($("#code").val());
							$("#nextForm").attr("action", "<c:url value='/passwordUpdateForm.do' />");
							$("#nextForm")[0].submit();
						}else{
							alert("인증번호가 맞지 않습니다.");
						}
					},
					error : function() {alert("error");}
				});
			}
		});
		
		$("#requestCertification").click(function() {  
			
				$.ajax({
					url : "<c:url value='/requestCertification.do' />",
					data : $("#searchForm").serialize(),
					type : "post",
					success : function(data) {
						if(data > 0){
							alert("인증번호가 요청되었습니다. 5분이내에 입력해주세요.");
							$("#code").focus();
						}else{
							alert("등록된 사용자가 아닙니다.");
						}
						/* if(data != "error"){
							location.href="<c:url value='/passwordChangeForm.do'/>?uid="+data;
						}else{
							alert("등록되지 않은 사용자입니다.");
						} */
					},
					error : function() {alert("error");}
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
<title>본인확인</title>
<link href="pw_style.css" rel="stylesheet" type="text/css">
</head>
<body>
<form id="nextForm" name="nextForm" method="post" action="">
<input type="hidden" id="uid" name="uid" value="" />
<input type="hidden" id="ucd" name="ucd" value="" />
</form>
<form id="searchForm" name="searchForm" method="post" action="">
<input type="hidden" id="searchId" name="searchId" value="${uid}" />
<!-- title -->
<div><table width="100%" border="0" cellpadding="0" cellspacing="0" class="title_box">
  <tbody>
    <tr>
      <td width="40" class="f_right"><img src="<c:url value="/base/images/popup/pop_icon_03.png"/>"/></td>
      <td class="f_white f_bold" style="padding-left:5px">본인확인</td>
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
        <td style="padding-left:10px;">사원정보에 등록된 휴대전화 번호로만 인증번호를 받으실수 있습니다.</td>
      </tr>
    </tbody>
  </table>
</div>

<!--contents -->
<div style="height:100px; padding:15px 0;" >
  <table width="310"border="0" align="center" cellpadding="0" cellspacing="0" style="font-size:11px;">
    <tbody>
      <tr>
        <td width="10"><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
        <td height="27">이름</td>
        <td class="f_right">
          <input type="text" class="input_blue" id="searchName" name="searchName" value="${uName}" readonly="readonly"/>
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
        <td height="27">휴대전화</td>
        <td class="f_right">
          <input type="text" class="input_blue" id="searchPhoneNo" name="searchPhoneNo" value="${uMobile}" readonly="readonly"/>
       </td>
        <td align="right" style="padding-left:5px;"><span class="button_img03"><a href="#" class="button_img03"><span id="requestCertification">인증번호요청</span></a></span></td>
      </tr>
      <tr>
        <td><img src="<c:url value="/base/images/popup/pop_bullet_01.png"/>" width="4" height="20" alt=""/></td>
        <td height="27">인증번호</td>
        <td class="f_right">
          <input type="text" class="input" id="code" name="code" />
        </td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="4" class="f_center" style="padding-top:10px"><a href="#" class="button_img01"><span id="nextView">다음</span></a> </td>
      </tr>
    </tbody>
  </table>
</div>

<!--페이지 사이즈 400X220-->

</form>
</body>
</html>

