<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<jsp:useBean id="resultStr"   class="java.lang.String"  scope="request"/>

<HTML>
 <HEAD>
  <TITLE> 멜 발송 테스트 </TITLE>
 </HEAD>
 <script language=javaScript>
<% if(resultStr != null && !"".equals(resultStr)) { %>
      alert("<%=resultStr%>");
      opener.document.searchForm.submit();
      window.close();
<% } %>
 	function send() {
 		document.abc.submit();
 	}
 </script>
 <BODY>

  * 다중 메일은 ','로 분리 입력.<br>
	<table width=780 border=1>
	<form name=abc method=post action="<c:url value='/workflow/admin/emailAdministration/testSend.do'/>">
		<tr>
			<td width=100>발신자 메일</td>
			<td>&nbsp;<input type=text name=senderEmail style="width:98%"></td>
		</tr>
		<tr>
			<td width=100>수신자 메일</td>
			<td>&nbsp;<input type=text name=receiverEmail style="width:98%"></td>
		</tr>
		<tr>
			<td width=100>수신자 ID</td>
			<td>&nbsp;<input type=text name=receiver style="width:50%"></td>
		</tr>
		<tr>
			<td width=100>참조자 메일</td>
			<td>&nbsp;<input type=text name=ccEmail style="width:98%"></td>
		</tr>		
		<tr>
			<td width=100>비밀참조자</td>
			<td>&nbsp;<input type=text name=bccEmail style="width:98%"></td>
		</tr>		
		<tr>
			<td width=100>메일제목</td>
			<td>&nbsp;<input type=text name=emailTitle style="width:98%"></td>
		</tr>
		<tr>
			<td width=100>상세내용</td>
			<td>&nbsp;<textarea name=emailContent rows=10 style="width:98%">내용들.</textarea></td>
		</tr>
		</form>
	</table>  
	<br>
	<table width=780 border=0>
		<tr>
			<td align=center><a href='javaScript:send();'>Send</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='javaScript:window.close();'>Close</a></td>
		</tr>
	</table> 

 </BODY>
</HTML>
