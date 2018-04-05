<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.lgcns.ikep4.util.*"%>
<%@page import="com.lgcns.ikep4.util.encrypt.EncryptUtil"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%
String empNo =((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getEmpNo();
%>
<html>
<body>
	<script type="text/javascript">
	var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	var sabun1 = <%=empNo%>;
	var sabun2 = encode64(sabun1);
	
	function encode64(str){
	 return encode(escape(str));
	}

	
	
	function encode(input){
	 var output = "";
	 var chr1, chr2, chr3;
	 var enc1, enc2, enc3, enc4;
	 var i =0;
	 do{
	  chr1 = input.charCodeAt(i++);
	  chr2 = input.charCodeAt(i++);
	  chr3 = input.charCodeAt(i++);
	  enc1 = chr1 >> 2;
	  enc2 = ((chr1 & 3) << 4) | (chr2 >> 4 );
	  enc3 = ((chr2 & 15) << 2) | (chr3 >> 6 );
	  enc4 = chr3 & 63;
	  if(isNaN(chr2)){
	   enc3 = enc4 =64;
	  }else if(isNaN(chr3)){
	   enc4 = 64;
	  }
	  output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
	 }while(i<input.length);
	 
	 return output;
	}

	location.href="http://10.3.10.117/MoorimSH/group/login?sabun="+sabun1+"&ssokey="+sabun2;
	</script>
</body>
</html>
