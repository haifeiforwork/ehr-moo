<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,java.util.*,javax.servlet.http.*,com.lgcns.ikep4.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<html>
<head>
<title>암호변경쿼리만들기</title>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript">
(function($) {
	$(document).ready(function () {

<%
	
String updateSqlStr1 ="UPDATE IKEP4_EV_USER SET user_password=";
String updateSqlStr2 ="WHERE user_id=";

String ids = com.lgcns.ikep4.util.lang.StringUtil.nvl(request.getParameter("userIds"),"");
String[] idArray = ids.split("\n");
      
String passwds = com.lgcns.ikep4.util.lang.StringUtil.nvl(request.getParameter("userPasswords"),"");
String[] pwdArray = passwds.split("\n");

String encryptPasswds = com.lgcns.ikep4.util.lang.StringUtil.nvl(request.getParameter("encryptPasswords"),"");
String[] epwdArray = encryptPasswds.split("\n");

String resultIds = "";
String resultPwds = "";
String resultEPwds = "";
String resultQuery ="";

if(!com.lgcns.ikep4.util.lang.StringUtil.isEmpty(ids)){
	for(int i=0;i<idArray.length;i++){
		
		resultIds = resultIds+"'"+idArray[i].trim()+"\\n'+";
		String queryLine ="";
		if("".equals(encryptPasswds)){
			resultPwds = resultPwds+"'"+pwdArray[i].trim()+"\\n'+"; 
			resultEPwds = resultEPwds+"'"+com.lgcns.ikep4.util.encrypt.EncryptUtil.encryptText(pwdArray[i].trim())+"\\n'+";
			queryLine = updateSqlStr1+"'"+com.lgcns.ikep4.util.encrypt.EncryptUtil.encryptText(pwdArray[i].trim())+"' "+updateSqlStr2+"'"+idArray[i].trim()+"';\\n";
			resultQuery=resultQuery+queryLine;
		}else if("".equals(passwds)){
			resultPwds = resultPwds+"'"+com.lgcns.ikep4.util.encrypt.EncryptUtil.decryptText(epwdArray[i].trim())+"\\n'+";
			resultEPwds = resultEPwds+"'"+epwdArray[i].trim()+"\\n'+"; 
		}
	}
	resultIds=resultIds.substring(0,resultIds.length()-1);
	resultPwds=resultPwds.substring(0,resultPwds.length()-1);
	resultEPwds=resultEPwds.substring(0,resultEPwds.length()-1);
	%>
	$("#userIds").val(<%=resultIds%>);
	$("#userPasswords").val(<%=resultPwds%>);
	$("#encryptPasswords").val(<%=resultEPwds%>);
	$("#updateSqlString").val("<%=resultQuery%>");
	<%
}

%>
	});

})(jQuery);
</script>
</head>
<body>
<form name="pwdUtilForm" ACTION="./encryptPassWordUtil.jsp" method="post">
<table border="1" width="900px">
	<tr>
		<td>아이디</td>
		<td>패스워드</td>
		<td>암호화된패스워드</td>
	</tr>
	<tr>
		<td width="300px"><textarea NAME="userIds" id="userIds" style="width:100%;height:400px"></textarea></td>
		<td width="300px"><textarea NAME="userPasswords" id="userPasswords" style="width:100%;height:400px"></textarea></td>
		<td width="300px"><textarea NAME="encryptPasswords" id="encryptPasswords" style="width:100%;height:400px"></textarea></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input TYPE="SUBMIT" value="암호변환^^"/></td>
	</tr>
	<tr>
		<td colspan="3" align="center">결과</td>
	</tr>
	<tr>
		<td colspan="3" align="center"><textarea NAME="updateSqlString" id="updateSqlString" style="width:900px;height:400px"></textarea></td>
	</tr>
</table>

</form>
</body>
</html>