<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMessageSNS"	value="message.lightpack.externalSNS" /> 
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>


<c:if test="${'FACEBOOK_ERROR' == info}">
	<script type="text/javascript">
		if(confirm("<ikep4j:message pre='${preMessage}' key='retryFacebookAuth' />"))
		{
			authFacebookPopup();
		}
	</script>
</c:if>

	<c:if test="${not empty info}">
		<li>
			<ikep4j:message pre='${preMessageSNS}' key='dataInfo' />
		</li>
	</c:if>

<c:if test="${'FACEBOOK_ERROR' != info}">
	<c:forEach var="content" items="${info}" varStatus="loopStatus">
		<li>
			<div class="microblog_con" >
				<span class="microblog_id"><a href="${content.permalink}" target="_blank" name="strongLink"><c:out value="${content.actorId}"/></a></span>
				<span class="microblog_name"></span>
				<div class="ic_micro_ar none"></div>
				<p><c:out value="${content.message}" escapeXml="false"/></p>
				<span class="microblog_time">${ikep4j:getTimeInterval(content.createdTime, sessionUser.localeCode)} </span>
			</div>
			<div class="selected_ar"></div>
		</li>
	</c:forEach>
	
	<c:if test="${empty info}">
		<li>
			<ikep4j:message pre='${preMessageSNS}' key='facebookNoData' />
		</li>
	</c:if>
</c:if>