<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMessageSNS"	value="message.lightpack.externalSNS" /> 
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<c:if test="${'TWITTER_ERROR' == info}">
	<script type="text/javascript">
		if(confirm("<ikep4j:message pre='${preMessage}' key='retryTwitterAuth' />"))
		{
			authTwitterPopup();
		}
	</script>
</c:if>

<c:if test="${'TWITTER_ERROR' != info}">
	<c:forEach var="content" items="${info}" varStatus="loopStatus">
		<li>
			<div class="microblog_img">
				<a href="#a" >
					<!-- //아이디에 해당하는 사진정보. -->
					<img src="<c:out value='${content.profileImage}'/>" width="50" height="50" alt="profileImage" />
				</a>
			</div>
			<div class="microblog_con">
				<span class="microblog_id"><a href="#a"><c:out value="${content.screenName}"/></a></span>
				<span class="microblog_name"><c:out value="${content.name}"/></span>
				<div class="ic_micro_ar none"></div>
				<p><c:out value="${content.text}" escapeXml="false"/></p>
				<span class="microblog_time">${ikep4j:getTimeInterval(content.createAt, sessionUser.localeCode)} </span>
			</div>
			<div class="selected_ar"></div>
		</li>
	</c:forEach>
	
	<c:if test="${empty info}">
		<li>
			<ikep4j:message pre='${preMessageSNS}' key='noData' />
		</li>
	</c:if>
</c:if>