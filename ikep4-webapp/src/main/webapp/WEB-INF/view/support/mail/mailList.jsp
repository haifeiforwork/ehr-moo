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

	
	<a href="listMail.do?folderName=inbox">받은편지함</a>&nbsp;&nbsp;&nbsp;
	<a href="listMail.do?folderName=Sent Messages">보낸편지함</a>&nbsp;&nbsp;&nbsp;
	<a href="listMail.do?folderName=Deleted Messages">휴지통</a>

	<div>
		<table border="1" cellpadding="3" cellspacing="1" width="100%" align="center" >
			<tr class="list_title">
				<th>읽기여부</th>
				<th>발신자</th>
				<th>제목</th>
				<th>날짜</th>
				<th>삭제여부</th>
			</tr>
			
			<c:forEach var="mail" items="${mailList}">
				<tr class="list_content">
					<td style="text-align:center">${mail.readYn}</td>			
					<td style="text-align:center">${mail.fromName}</td>
					<td style="text-align:center"><a href="viewMail.do?folderName=${mail.folderName}&mailId=${mail.mailId}" target="_blank"><c:out value="${mail.title}" escapeXml="true"/></a></td>
					<td style="text-align:center"><fmt:formatDate value="${mail.sendDate}" pattern="yyyy-MM-dd hh:mm" /></td>
					<td style="text-align:center"><a href="moveMail.do?folderName=${mail.folderName}&mailId=${mail.mailId}&targetFolderName=Deleted Messages">휴지통</a></td>				
				</tr>
			</c:forEach>
			
		</table>
	</div>
	
</body>
</html>
