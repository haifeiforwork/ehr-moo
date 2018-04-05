<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.lgcns.ikep4.util.encrypt.EncryptUtil"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- Tab Lib End --%> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />

<%
String empNo =((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getEmpNo();//
String bwId =((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getBwId();//
%>

<script type="text/javascript">
var userEmpNo = "<%=empNo%>";
var userBwId = "<%=bwId%>";
var BWserverDomain="";


	if((location.hostname).indexOf("eptest")>-1){
		BWserverDomain="http://smrmbp02.moorim.co.kr:8000/sap/bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_O02_WT001&P_SABUN=";
	}else{
		if((userBwId.toUpperCase()).indexOf("EPBWIF")>-1){//공용아이디는 다르게.
			BWserverDomain="http://smrmbp02.moorim.co.kr:8000/sap/bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_O02_WT001&P_SABUN=";
		}else{
			BWserverDomain="http://smrmbp01.moorim.co.kr:8000/sap/bw/BEx?sap-language=KO&bsplanguage=KO&CMD=LDOC&TEMPLATE_ID=ZEIS_O02_WT001&P_SABUN=";
		}
	}
BWserverDomain = BWserverDomain+userEmpNo;


function createHttpRequest()
{


	if(window.XMLHttpRequest){
		 //Win Mac Linux m1,f1,o8 Mac s1 Linux k3용
		//alert("XMLHttpRequest");
		return new XMLHttpRequest() ;
	}else if(window.ActiveXObject){
		try {
			//alert("MSXML2.XMLHTTP.3.0");
			return new ActiveXObject("MSXML2.XMLHTTP.3.0") ;
		} catch (e) {
			try {
				//alert("MSXML2.XMLHTTP");
				return new ActiveXObject("MSXML2.XMLHTTP") ;
			} catch (e2) {
				try {
					//alert("Microsoft.XMLHTTP");
					return new ActiveXObject("Microsoft.XMLHTTP") ;
				} catch (e3) {
					//alert("catch");
					return false;
	 			}
 			}
 		}
	} else if (window.XDomainRequest){	
		//alert("XDomainRequest");
		return new XDomainRequest() ;
		
	} else {
		//alert("else");
		return false ;
	}
	
}




(function($) {
    $(document).ready(function() {  
    	//goBW();
        $jq("#tabDiv").append('<iframe id="tab1" src="http://smrmbp01.moorim.co.kr:8000/sap(bD1rbyZjPTMwMA==)/bc/bsp/sap/zmon/eis/zmain_bw.htm" width="330" height="440" border="0" frameborder="no"  scrolling="no" marginwidth="0"></iframe><iframe id="tab2" src="http://smrmbp01.moorim.co.kr:8000/sap(bD1rbyZjPTMwMA==)/bc/bsp/sap/zmon/eis/zmain_erp.htm" width="330" height="440" border="0" frameborder="no"  scrolling="no" marginwidth="0"></iframe>');
        loadTabs('tabs-1');
        tab = $("#divTab_s").tabs({
            select : function(event, ui) {
                switch($(ui.panel).attr("id")) {
                    case "tabs-1" :                    	
                    	loadTabs('tabs-1');
                        break;
                    case "tabs-2" :
                    	loadTabs('tabs-2');
                        break;
                }                
            }
        }); 
    });
})(jQuery);

function loadTabs(tab) {
	if(tab == "tabs-2"){
		$jq("#tab1").hide();
		$jq("#tab2").show();
	}else{
		$jq("#tab2").hide();
		$jq("#tab1").show();
	}
}   
/*
function goBW(){
	var xhr = false;

	xhr = createHttpRequest();
	//alert(BWserverDomain);
	if( xhr==false){
		//alert("\n도구 > 인터넷 옵션 > 보안 > 사용자 지정 수준 클릭> [다른 도메인사이에서 창과 프레임 탐색]와 [도메인간의 데이터 원본 엑세스]를  [사용]으로 변경하세요!\n");
	}else{
		xhr.open("GET", BWserverDomain, true, "${bwId}" , "${bwPwd}");//ok
		
		xhr.onreadystatechange = function() { 
	         if(xhr.readyState == 4) {//COMPLETE 
	             if(xhr.status == 200){ //200 = 정상처리
	            	 
	            	 $jq("#tabDiv").append('<iframe id="tab1" src="http://smrmbdev.moorim.co.kr:8000/sap(bD1rbyZjPTMwMA==)/bc/bsp/sap/zmon/eis/zmain_bw.htm" width="330" height="440" border="0" frameborder="no"  scrolling="no" marginwidth="0"></iframe><iframe id="tab2" src="http://smrmbdev.moorim.co.kr:8000/sap/bc/bsp/sap/zmon/eis/zmain_erp.htm" width="330" height="440" border="0" frameborder="no"  scrolling="no" marginwidth="0"></iframe>');
	            	 loadTabs('tabs-1');
	             }else{
	        		 //alert("SAP 인증을 실패했습니다.\n SAP패스워드를 변경하지 않으신분은 확인을 눌러 주십시요.\n변경후에 자동로그아웃후 재접속시에도 SAP인증이 실패하면 관리자에게 문의하십시요.");
					 //self.close();
	             }
	         }
		};
		
		xhr.send(null);//ok
	}
}
*/
</script>
<div id="divTab_s" class="iKEP_tab_s">
		<ul>          
            <li><a href="#tabs-1">사업계획 달성율</a></li>
            <li><a href="#tabs-2">일일 현황</a></li>
        </ul>
        <div>
            <div id="tabs-1"></div>
            <div id="tabs-2"></div>
        </div> 
	
<div style="width:100%;height:440px;position:relative;left:0;">
   <div id="tabDiv" style="width:100%; height:440px; left: 0px; top: 0px; position: absolute;text-align:center "> 
   </div>  
</div>

</div>