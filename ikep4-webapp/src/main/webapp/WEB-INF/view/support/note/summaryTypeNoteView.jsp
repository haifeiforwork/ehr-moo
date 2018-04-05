<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.note.listNoteView.header" /> 
<c:set var="preList"    value="ui.support.note.listNoteView.list" />
<c:set var="preButton"  value="ui.support.common.button" /> 
<c:set var="preMessage" value="message.support.common.note" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%> 

<c:forEach var="note" items="${searchResult.entity}" varStatus="noteStatus"> 
<tr class="bgWhite">
	<c:choose>
		<c:when test="${not empty note.imageFileId}">
		<td class="note_box_img">
			<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${note.imageFileId}&amp;thumbnailYn=Y" alt="Image"/>
		</td>
		<td class="note_box_con">		
			<div class="note_box_con_t">
				<div class="note_box_con_tl img">
		</c:when>
		<c:otherwise>
		<td colspan="2" class="note_box_con">    		
			<div class="note_box_con_t">
				<div class="note_box_con_tl noImg">
		</c:otherwise>
	</c:choose>								
				<a class="note" value="${note.noteId}" href="<c:url value='/support/note/readNoteView.do?folderId=${noteFolder.folderId}&amp;noteId=${note.noteId}&amp;searchConditionString=${searchConditionString}'/>">
					<h3>${note.title}</h3>
				</a>
				<p><ikep4j:extractText text="${note.contents}" length="100"/></p>
			</div>
		</div>
		<div class="note_box_con_info">
			<span class="note_box_con_date"><ikep4j:timezone date="${note.registDate}"/></span>
		</div>
	</td>
</tr>				
</c:forEach>