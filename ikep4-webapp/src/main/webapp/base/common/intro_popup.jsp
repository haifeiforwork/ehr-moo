<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<c:set var="prefix" value="message.portal.login.loginForm"/>


<html>

<head>

<title></title>

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">



<style type="text/css">



td{font-family: "굴림"; font-size: 9pt; font-style: normal; color: #6C6C6C; line-height: 130%}



select{font-family: "굴림"; font-size: 9pt; font-style: normal}




</style>





</head>



<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">



<table width="100%" border="0" cellspacing="0" cellpadding="0">

  <tr>

    <td bgcolor="0B479D"><img src="<c:url value='/base/images/login/intro_popup_top.gif'/>" width="118" height="38"></td>

  </tr>

</table>

<table width="400" border="0" cellspacing="0" cellpadding="7">

  <tr>

    <td>

      <table width="100%" border="0" cellspacing="1" cellpadding="5" bgcolor="C9C9C9">

        <tr>

          <td bgcolor="#FFFFFF" align="center"> <br>

            <img src="<c:url value='/base/images/login/intro_popup_icon.gif'/>" width="25" height="25" align="absmiddle"> 

            <font color="D7833F"><b>로그인 전에 아래의 항목을 체크해 보시기 바랍니다.</b></font> <br>

            <br>

            <table width="350" border="0" cellspacing="0" cellpadding="0">

              <tr>

                <td height="35" valign="top"><img src="<c:url value='/base/images/login/intro_popup_title_01.gif'/>" width="350" height="28"></td>

              </tr>

              <tr>

                <td align="center">

                  <table width="300" border="0" cellspacing="0" cellpadding="0">

                    <tr>

                      <td><font color="#000000">Microsoft Internet Explorer 8.0 

                        이상</font></td>

                    </tr>

                  </table>

                  <br>

                </td>

              </tr>

              <tr>

                <td height="35" valign="top"><img src="<c:url value='/base/images/login/intro_popup_title_02.gif'/>" width="350" height="28"></td>

              </tr>

              <tr>

                <td align="center"> 

                  <table width="300" border="0" cellspacing="0" cellpadding="0">

                    <tr>

                      <td><font color="#000000">파일을 다운로드하여 설치하세요</font>. <a href="<c:url value='/Bin/TAGFREEActiveDesigner.exe'/>" target="_new"><img src="<c:url value='/base/images/login/intro_popup_btn_download.gif'/>" width="104" height="19" align="absmiddle" border="0"></a></td>

                    </tr>

                  </table>  <br>

                </td>

              </tr>

              <tr>

                <td height="35" valign="top"><img src="<c:url value='/base/images/login/intro_popup_title_04.gif'/>" width="350" height="28"></td>

              </tr>

              <tr>

                <td align="center"> 

                  <table width="300" border="0" cellspacing="0" cellpadding="0">

                    <tr> 

                      <td><font color="#000000">파일을 다운로드하여 설치하세요</font>. <a href="http://gw.moorim.co.kr/ocx/moorim_messenger.exe" target="_new"><img src="<c:url value='/base/images/login/intro_popup_btn_download.gif'/>" width="104" height="19" align="absmiddle" border=0></a></td>

                    </tr>

                  </table>

                  <br>

                </td>

              </tr>

            </table>

          </td>

        </tr>

      </table>

    </td>

  </tr>

</table>
<br/>

<table width="400" border="0" cellspacing="1" cellpadding="1">

  <tr>

    <td align="center"><img src="<c:url value='/base/images/login/popup_btn_close.gif'/>" width="82" height="22" onClick="self.close()" border="0" style="cursor:hand"></td>

  </tr>

</table>

</body>