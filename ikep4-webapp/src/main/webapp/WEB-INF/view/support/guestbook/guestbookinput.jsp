<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<form name="guestbookForm" action="" method="post" onsubmit="return false">
	<fieldset>
		<input type="hidden" name="targetUserId" value="${guestbook.targetUserId}" />
		<input type="hidden" name="registerId" value="${guestbook.registerId}" />
		<input type="hidden" name="registerName" value="${guestbook.registerName}" />
	<div class="guestbook_write">
	<table summary="<ikep4j:message pre='${preGuestbook}' key='inputTable'/>">
		<caption></caption>
		<tbody><tr>
			<td>
				<div><textarea id="contents" name="contents" rows="3" cols="" title="<ikep4j:message pre='${preGuestbook}' key='input'/>" name=""><c:out value="${guestbook.contents}"/></textarea></div>
			</td>
			<td width="74" class="textRight">
				<c:choose>
					<c:when test="${viewType == 'PF'}">
						<a href="#a" class="button_re"><span><ikep4j:message pre='${preButton}' key='create'/></span></a>
					</c:when>
					<c:otherwise>
						<a href="#a" class="button_re"><span><ikep4j:message pre='${preButton}' key='create'/></span></a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</tbody></table>
	<div class="guestbook_write_num"><p style="display:inline"><span id="textcount">0</span>/300</p></div>
	</div>
	</fieldset>
</form>							