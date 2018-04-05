<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,java.util.*,javax.servlet.http.*,com.lgcns.ikep4.util.*,java.io.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<html>
<head>
<title>파일사이즈 업데이트 쿼리만들기</title>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript">
(function($) {
	$(document).ready(function () {

<%


String root = com.lgcns.ikep4.util.lang.StringUtil.nvl(request.getParameter("root"),"");   
String where = com.lgcns.ikep4.util.lang.StringUtil.nvl(request.getParameter("where"),"");

String updateSqlStr1 ="UPDATE IKEP4_DM_FILE SET file_size=";
String updateSqlStr2 ="WHERE file_path='"+where+"' and file_name=";

String resultQuery ="";

if(!com.lgcns.ikep4.util.lang.StringUtil.isEmpty(root) && !com.lgcns.ikep4.util.lang.StringUtil.isEmpty(where)){
	
	
	File dir = new File(root+where); 
	File[] list = dir.listFiles(); 
	
	if(list!=null){
		for(int i=0; i < list.length;i++) { 
		    String fileName = list[i].getName(); 
		    long fileSize = list[i].length();
	
			String queryLine =updateSqlStr1+"'"+fileSize+"' "+updateSqlStr2+"'"+fileName+"';\\n";
			resultQuery=resultQuery+queryLine;
		 
		
		} 
	}

%>
	$("#root").val("<%=root%>");
	$("#where").val("<%=where%>");
	$("#updateSqlString").val("<%=resultQuery%>");
<%
}
%>
	});

})(jQuery);
</script>
</head>
<body>
<form name="pwdUtilForm" ACTION="./fileSizeUpdateUtil.jsp" method="post">
<table border="1" width="900px">
	<tr>
		<td  align="center">fileroot : <input id="root" name="root" type="text" style="width:600px" value="F:/ikep4/fileupload"></td>
	</tr>
	<tr>
		<td  align="center">folder : <input id="where" name="where" type="text" style="width:600px" value="/ikep4jfile/kms/sapmnt/MPP/global/common/Namo/editor/upload"></td>
	</tr>
	<tr>
		<td  align="center"><input TYPE="SUBMIT" value="사이즈가져오기^^"/></td>
	</tr>
	<tr>
		<td  align="center">result</td>
	</tr>
	<tr>
		<td colspan="3" align="center"><textarea NAME="updateSqlString" id="updateSqlString" style="width:900px;height:400px"></textarea></td>
	</tr>
</table>

</form>
</body>
</html>