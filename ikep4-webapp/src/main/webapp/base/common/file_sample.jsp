<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="destiny.link.slo.service.DestinySLO"%>
<%!
public void setSystemProperty( String key, String value) {
    System.setProperty( key, value);
}

public void setCookie( HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie( key, value);
    cookie.setPath( "/");
    cookie.setMaxAge( -1);
    cookie.setVersion( 0);
    //cookie.setComment( "destiny slo test");
    cookie.setSecure( false);
    response.addCookie( cookie);
}
%>

<%
// ECM Server Address
String sloServerAddress = "http://ecm.moorim.co.kr";
String sloAPIKey = "0VbXsZYobdOnciJmv4GQ3h16EvOjAoF0icK5sHMSvX4=";

// GW Login User Account
//String userAccount = "admin"; // 로그인 사용자ID

// GW Login User's GroupCode
//String userGroupCode = "E000"; // 로그인 사용자 부서코드
String userAccount = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId(); // 로그인 사용자ID
String userGroupCode = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getGroupId(); // 로그인 사용자 부서코드

// ECM Settings
setSystemProperty( "common.SloAddrKey", sloServerAddress);
setSystemProperty( "common.SloAPIKey", sloAPIKey);
setCookie( response, "ACCOUNT", userAccount);
setCookie( response, "GROUP_CODE", userGroupCode);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>DestinyECM™V File Dialog Page</title>
<script type="text/javascript">

var Cyberdigm = {

    popupSelect : function( action) {
        var iframe = document.getElementById( "select_dialog");
        var callbackFn = 'Cyberdigm.selectItem';

        //팝업 설정( 해당 다이얼로그에 맞도록 수정)
        var settings = "&settings=width:665,height:480,location:0,menubar:0,resizable:0,scrollbars:0,status:0,toolbar:0";

        iframe.src = "./destinySLO.jsp?TARGET_URL=popup&action=" + action + "&callBack=" + callbackFn + settings;
    },

    selectItem : function( _p, type) {
        var data = eval( "(" + decodeURIComponent( _p) + ")");
        for( var i = 0; i < data.length; i++) {
        	var meta = "파일명 : " + data[ i].name;
        	var fileurl = "http://127.0.0.1:36482/viewFile?fileName=" + encodeURIComponent( data[i].fileName) + "&docID=" + data[ i].docOID + "&fileID_=" + data[ i].fileOID + "&history=true&overWrite=true&recently=true&clientType=I";

        	alert( meta + '\n' + fileurl);
        }
    }
};

</script>
<style type="text/css">
<!--
h4 {
    font-family: Dotum, 돋음, seoul, arial, helvetica
}
a {
    cursor: pointer;
    text-decoration: none;
    font-size: 12px
}
-->
</style>
</head>
<body>
<!-- IFrame 으로 Agent 인스톨 여부 및 실행을 담당하는 URL 호출, 연동대상 시스템 실행 시 Agent 인스톨 여부 판별 및 실행 시 사용합니다. -->
<iframe width="0" height="0" src="./destinySLO.jsp?TARGET_URL=install"></iframe>
<!-- Iframe을 이용한 파일선택 다이얼로그( 클릭시 이벤트 처리 포함) -->
<a onClick="javascript:Cyberdigm.popupSelect('selectAllFiles');">[Click] ECM 파일 다이얼로그.</a>
<iframe id="select_dialog" src="" style="display:none"></iframe>
</body>
</html>
