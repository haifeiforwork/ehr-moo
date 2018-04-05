<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="prefix" value="message.portal.login.loginForm"/>


<HTML>

<HEAD>

<TITLE> 비밀번호 분실신고 </TITLE>

	<style type="text/css">



		td{font-family: "굴림"; font-size: 9pt; font-style: normal; color: #6C6C6C; line-height: 130%}

		select{font-family: "굴림"; font-size: 9pt; font-style: normal}


	</style>

</HEAD>



<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" background="img/popup_bg2.gif">



<table width="100%" border="0" cellspacing="0" cellpadding="0" height="6" bgcolor="0B479D">

  <tr>

    <td><img src="<c:url value='/base/images/login/popup_bg.gif'/>" width="83" height="6"></td>

  </tr>

</table>

<table width="100%" border="0" cellspacing="5" cellpadding="1">

  <tr>

    <td bgcolor="C9C9C9">

      <table width="100%" border="0" cellspacing="0" cellpadding="10">

        <tr>

          <td bgcolor="#FFFFFF" align="center"> <br>

            <table border="0" cellspacing="0" cellpadding="2">

              <tr>

                <td><img src="<c:url value='/base/images/login/popup_icon.gif'/>" width="25" height="25" align="absmiddle"> 

                  <b><font color="3D9B08">비밀번호 분실 신고</font></b></td>

              </tr>

            </table>

            <p>자세한 사항은 시스템 관리자에게 문의하여 주시기 바랍니다.<br>

              <br>

            </p>

            <table border="0" cellspacing="1" cellpadding="2" width="307" bgcolor="B7D6A9">

              <tr>

                <td align="center" height="30" bgcolor="F6FAF1" width="84"><font color="3A6E22">시스템 

                  관리자</font><br>

                </td>

                <td align="center" bgcolor="#FFFFFF" width="212"><font color="#333333"><br>IT기획팀 이성화 <br><br> Tel.(02)3485-1803  <br> Mail : timelee0911@moorim.co.kr</font></td>

              </tr>

            </table>

            <br>

          </td>

        </tr>

      </table>

    </td>

  </tr>

</table>
<br/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>

    <td align="center"><img src="<c:url value='/base/images/login/popup_btn_close.gif'/>" width="82" height="22" onClick="self.close()" style="cursor:hand"></td>

  </tr>

</table>

</BODY>

</HTML>

