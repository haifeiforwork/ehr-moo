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
String userId =((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId();//"hwangdw"

if(userId.startsWith("0")){//0으로 시작하면 0뗀다.
	userId=userId.substring(1);
}

/*
1. company ID --> moorim
2. 로그인 DI --> EP ID(소문자)
3. password : a1234567
4. secret key : anflapaper1114moorim2011
5. tklogin_key : xlzpdlkey2014
*/

SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
Calendar cal = Calendar.getInstance();
String today = null;
today = formatter.format(cal.getTime());


String userId3DES = EncryptUtil.encryptTripleDES(userId);
String password3DES = EncryptUtil.encryptTripleDES("a1234567");
String time3DES  =EncryptUtil.encryptTripleDES(""+today+"-");//"0NLL-AFS7-eKwL-xema-9DH5-F3lS-Z6cH-EPS6-"
%>
<html>
<body>
	<script type="text/javascript">
	//alert("<%=userId%>\n<%=userId3DES%>");
	//alert("a1234567\n<%=password3DES%>");
	//alert("<%=today%>\n<%=time3DES%>");
	location.href="https://performancemanager41.successfactors.com/login?company=moorim&username=<%=userId3DES%>&password=<%=password3DES%>&expire=<%=time3DES%>&tklogin_key=xlzpdlkey2014&tz=KST";
	</script>
</body>
</html>
