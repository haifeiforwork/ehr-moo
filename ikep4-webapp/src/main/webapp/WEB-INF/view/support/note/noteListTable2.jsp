<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

		
		<table border="1" cellpadding="3" cellspacing="1" width="100%" align="center" >
			
			<tr class="list_title">
				<th>수신여부</th>
				<th>수신자</th>
				<th>제목</th>
				<th>수신날짜</th>
			</tr>

			<c:forEach var="note" items="${noteList}">
				<tr class="list_content">
					<td style="text-align:center">${note.readYn}</td>			
					<td style="text-align:center">${note.toUserName}</td>
					<td style="text-align:center"><a href="javascript:getNoteInfo('${note.noteId}')"><c:out value="${note.title}" escapeXml="true"/></a></td>
					<td style="text-align:center"><fmt:formatDate value="${note.receiveDate}" pattern="yyyy-MM-dd hh:mm" /></td>
				</tr>
			</c:forEach>
			
		</table>
