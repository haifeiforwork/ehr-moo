<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.note.listNoteView.header" /> 
<c:set var="preList"    value="ui.support.note.listNoteView.list" />
<c:set var="preButton"  value="ui.support.common.button" /> 
<c:set var="preMessage" value="message.support.common.note" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" />  
<%-- 메시지 관련 Prefix 선언 End --%>

<c:forEach var="note" items="${searchResult.entity}" varStatus="status">
<tr class="bgWhite">
	<td class="note_box_con">
		<div class="note_box_con_t">
			<div class="note_box_con_tl">
				<h3><a class="note" value="${note.noteId}" href="<c:url value='/support/note/readNoteView.do?folderId=${noteFolder.folderId}&amp;noteId=${note.noteId}&amp;searchConditionString=${searchConditionString}'/>">${note.title}</a></h3>
			</div>
		</div>
	</td>
	<td width="100px">
		<div class="note_box_con_info">
			<span class="note_box_con_date"><ikep4j:timezone date="${note.registDate}"/></span>
		</div>
	</td>
</tr>
</c:forEach>	