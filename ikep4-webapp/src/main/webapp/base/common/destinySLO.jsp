<%--
 해당 페이지는 SLO 연동 시 사용되는 JSP 입니다.
 해당 JSP 페이지를 DestinySLO.jar와 함께 연동을 수행하려는 사이트에 배포하여 주세요.
 --%>
<%@page import="sun.misc.BASE64Encoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="destiny.link.slo.service.DestinySLO"%>
<%!
    public String buildExtParameter( HttpServletRequest request, HttpServletResponse response, String params) throws Exception {

        Enumeration en = request.getParameterNames();
        while( en.hasMoreElements() ) {

            String key = ( String)en.nextElement();

            if ( DestinySLO.TARGET_URL.equals( key)) continue;

                String value = request.getParameter(key);
            if ( value != null ) {
                value = URLEncoder.encode( value, "UTF-8");
                params += "&" + key + "=" + value;
            }
        }

        return params;
    }

    public  String ScriptEscape( String url) throws Exception {
        String returnURL = new BASE64Encoder().encode( url.getBytes());
         returnURL = returnURL.replaceAll( "[\r\n]", "");
         return "xss.decode('"+ returnURL + "')";
    }
%>
<%
    String address = System.getProperty( "common.SloAddrKey");
%>

<!DOCTYPE html>
<html>
<head>
    <title>DestinySLO</title>
    <meta charset="UTF-8" />
    <script type="text/javascript" src="<%= address + "/scripts/easyXDM/easyXDM.js" %>"></script>
    <script type="text/javascript">
        /**
         * Request the use of the JSON object
         */
        easyXDM.DomHelper.requiresJSON("<%=address + "/scripts/easyXDM/json2.js" %>");
    </script>
</head>
<body>
<%--
 해당 script 태그는 body 안에 두어야 합니다.
 gotoPage 함수를 수행 시 body 태그가 load 된 이후에 처리하는 부분이 존재하기 때문입니다.
--%>
<script>

/*
 * url 이동하는 스크립트
 * 크로스 도메인 처리를 위하여 사용됨
 */
function gotoPage( url) {
    if( url.indexOf( "TARGET_URL=exec")>-1 || url.indexOf( "TARGET_URL=popup")>-1) {
        var params = getParam( url);
        RemoteRpc({
            settings : {
                remote : url
            },
            callBack : params.callBack
        });
        return;
    }

    if( navigator.appName.indexOf("Microsoft") > -1 ) {

        var anchor = document.createElement("a");
        anchor.setAttribute("href", url);
        anchor.style.display = "none";
        document.body.appendChild(anchor);
        anchor.click();
    } else {
        window.location = url;
        return;
    }
}

var getParam = function( params) {
    var paramObj = {};

    if( typeof params == 'string'){
        params = decodeURIComponent( params);
        var s = params.split('&');
        for( var i=0;i<s.length; i++) {
            var p = s[i].split('=');
            if( p.length==2) paramObj[p[0]] = p[1];
        }
    } else paramObj = params;
    return paramObj;
}

var extend = function( srcObj, extendObj) {
   var target = {};

    for( var i=0; i<arguments.length; i++) {
        var obj = arguments[i];
        for( var property in obj) {
            if( typeof obj[property] == 'object') {
                target[property] = extend( obj[property])
            } else {
                target[property] = obj[property];
            }
        }
    }
    return target;
}
var xss = {
    // private property
    _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

    // public method for decoding
    decode : function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;

        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

        while (i < input.length) {

            enc1 = this._keyStr.indexOf(input.charAt(i++));
            enc2 = this._keyStr.indexOf(input.charAt(i++));
            enc3 = this._keyStr.indexOf(input.charAt(i++));
            enc4 = this._keyStr.indexOf(input.charAt(i++));

            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;

            output = output + String.fromCharCode(chr1);

            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }

        }

        output = xss._utf8_decode(output);

        return output;

    },

    // private method for UTF-8 decoding
    _utf8_decode : function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;

        while ( i < utftext.length ) {

            c = utftext.charCodeAt(i);

            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            }
            else if((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i+1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            }
            else {
                c2 = utftext.charCodeAt(i+1);
                c3 = utftext.charCodeAt(i+2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }

        }

        return string;
    }
}

var RemoteRpc = function( config) {
    var settings = extend({
        local: "<%=address%>/scripts/easyXDM/name.html",
        swf: "<%=address%>/scripts/easyXDM/easyxdm.swf",
        remote : "./destinySLO.jsp?TARGET_URL=messageMainView",
        remoteHelper: "<%=address%>/scripts/easyXDM/name.html",

        //container: "container", //대상 ID
        props: {
              style: {
                  border: "none",
                  height: "100%",
                  width : "100%",
                  left : "0px",
                  top: "0px"
              }
        },
        onReady: function(){
            remote.show();
        }
    }, config.settings);

    var fn = {
        remote: {
            show: {}
        },
        local: {
            callBack : function( _p, type) {
                if( config.callBack) {
                    var parentFuction = window.parent;
                    if( config.callBack.indexOf(".")>-1) {
                        var names = config.callBack.split(".");
                        var length = names.length;
                        for( var i=0;i<length; i++) {
                            parentFuction = parentFuction[names[i]];
                        }
                    } else {
                        parentFuction = parentFuction[config.callBack];
                    }
                    parentFuction( decodeURIComponent(_p), type);
                }
            }
        }
    };
    var remote = new easyXDM.Rpc( settings, fn);
}


<%

// 파라미터로 TargetUrl 을 받는다.
String url = request.getParameter( DestinySLO.TARGET_URL);

/*
 * [인스턴스 생성]
 * 초기화를 수행합니다.
 */
DestinySLO destinySLO = new DestinySLO( request, response);
/*
 * [인증]
 * 인증만을 수행하며 인증 결과는 쿠키에 저장됩니다.
 * 인증이 정상적으로 수행되면 true 값을 리턴합니다. 만일 false 값을 리턴받았다면 연동 실패에 따른 로직을 구현합니다.
 * 이후 수행되는 ECM 페이지 요청은 별도의 인증이 없이 바로 호출할 수 있습니다.
 */
if ( destinySLO.auth()) {
    if( url != null && url.length() >0) {
        url = buildExtParameter( request, response, url);
        url = destinySLO.getTargetUrl( url);
    } else {
        String redirectUrl = request.getParameter( DestinySLO.REDIRECT_URL);
        url = destinySLO.getRedirectUrl( redirectUrl);
    }
%>
/**
 * 페이지 이동
 *
 * 기본 페이지 이동 KEY
 * Agent 설치 : install
 * ECM 메인 : main
 * 문서 조회 : documentView&documentID={문서 OID}
 * 쪽지창 : messageMainView
 */
gotoPage( <%= ScriptEscape(url) %>);
<%
} else {
%>
alert( "연동에 실패하였습니다.");
<% } %>
</script>
<div id="treePanel" style="width:100%; height:100%"></div>
</body>
</html>