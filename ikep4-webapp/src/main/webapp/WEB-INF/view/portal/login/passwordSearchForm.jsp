<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="prefix" value="message.portal.login.loginForm"/>

<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<link rel="stylesheet" type="text/css" href="${contextRoot}/base/css/newpassword/pw_style.css" />
<script type="text/javascript" src="${contextRoot}/base/js/etc.plugins.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/langResource/ko.js"></script>
<scripype="text/javascript" src="${contextRoot}/base/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/common.pack.js"></script>
<script type="text/javascript" src="${contextRoot}/base/js/jquery/jquery-1.6.2.min.js"></script>
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
			$.ajax({
				url : "${contextRoot}/identityAuthCheck.do",
				data : $("#searchForm").serialize(),
				type : "post",
				success : function(data) {
					if(data != "error"){
						location.href="${contextRoot}/identityAuthForm.do?uid="+data;
					}else{
						alert("등록되지 않은 사용자입니다.");
					}
				},
				error : function() {alert("error");}
			});
			
		});
		
		$("#sapUnlocking").click(function() {  
			$.ajax({
				url : "${contextRoot}/identityAuthCheck.do",
				data : $("#searchForm").serialize(),
				type : "post",
				success : function(data) {
					if(data != "error"){
						location.href="${contextRoot}/sapUnlockingForm.do?uid="+data;
					}else{
						alert("등록되지 않은 사용자입니다.");
					}
				},
				error : function() {alert("error");}
			});
		});
		
		/* $("#searchId").keypress( function(event) {
			if(event.which == 13) {
				$.ajax({
					url : "<c:url value='/identityAuthCheck.do' />",
					data : $("#searchForm").serialize(),
					type : "post",
					success : function(data) {
						if(data != "error"){
							location.href="<c:url value='/identityAuthForm.do'/>?uid="+data;
						}else{
							alert("등록되지 않은 사용자입니다.");
						}
					},
					error : function() {alert("error");}
				});
			} 
		}); */
		
		$("#close").click(function() {  
			self.close();
		});
		
	});
})(jQuery);

//-->
</script>

<HTML>

<head>
<meta charset="utf-8">
<title>ID 입력</title>

</head>
<body>
<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false;">
<!-- title -->
<div><table width="100%" border="0" cellpadding="0" cellspacing="0" class="title_box">
  <tbody>
    <tr>
      <td width="35" class="f_right"><img src="${contextRoot}/base/images/popup/pop_icon_02.png"/></td>
      <td class="f_white f_bold" style="padding-left:5px">ID 입력</td>
      <td width="60" bgcolor="#000000"><img src="${contextRoot}/base/images/popup/btn_close.png" width="62" height="40" alt="" id="close" style="cursor: pointer;" /></td>
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
        <td style="padding-left:10px;">비밀번호 찾기 또는 SAP잠금해제 할 아이디를 입력해주세요.</td>
      </tr>
    </tbody>
  </table>
</div>

<!--contents -->
<div style="height:100px; padding:15px 0;" >
<table width="300"border="0" align="center" cellpadding="0" cellspacing="0" style="font-size:11px;">
  <tbody>
    <tr>
      <td width="10" style="vertical-align: bottom;"><img src="${contextRoot}/base/images/popup/pop_bullet_01.png" width="4" height="20" alt=""/></td>
      <td height="27" style="vertical-align: bottom;">ID</td>
      <td class="f_center" style="padding-top:10px"><span class="help_wrap">
        <input type="text" class="input" style="width:200px;ime-mode:inactive;" id="searchId" name="searchId" title="ID를 입력해주세요" />
        </span></td>
    </tr>
    <tr>
      <td height="60" valign="bottom" class="f_center" colspan="3">
      	<a href="#" class="button_img01"><span id="nextView">비밀번호 찾기</span></a>
      	<a href="#" class="button_img01"><span id="sapUnlocking">SAP잠금해제</span></a>
      </td>
      </tr>
  </tbody>
</table>

</div>
</form>

<!--페이지 사이즈 400X220-->

</body>

</HTML>

