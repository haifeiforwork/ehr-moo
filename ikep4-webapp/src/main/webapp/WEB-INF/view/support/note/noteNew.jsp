<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/common.css"/>" />
</head>



<body>

	
	<form id="noteFrm" name="noteFrm" enctype="multipart/form-data">
	
		<table border="1" cellpadding="3" cellspacing="1" width="100%" align="center" >
			<tr class="list_title">
				<th>발신자</th><td><input type="text" id="fromUserId" name="fromUserId"></input></td>
			</tr>
			<tr class="list_title">
				<th>수신자</th><td><input type="text" id="toUserId" name="toUserId"></input></td>
			</tr>
			
			<tr class="list_title" >
				<td colspan=2><textarea id="content" name="content" cols="60" rows="20"></textarea></td>
			</tr>		
			
			<tr class="list_title" >
				<td colspan=2><input type="file" name="file" size="60"/></textarea></td>
			</tr>
			
		
		</table>
		
		<center>
		<input type="button" value="보내기" onclick="javascript:sendNote2()"></input>
		</center>
	</form>
	
</body>
</html>
