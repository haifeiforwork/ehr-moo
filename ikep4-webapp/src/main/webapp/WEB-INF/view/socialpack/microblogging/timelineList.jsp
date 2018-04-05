<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<c:forEach var="mblog" items="${mblogList}" varStatus="loopStatus">
	<li id="timelineMblogId_${mblog.mblogId}" name="timelineThreadId_${mblog.threadId}">
		<c:if test="${'Y' == threadType && mblog.mblogId != mblog.threadId}">
			<div class="microblog_img">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</c:if>
		<div class="microblog_img">
			<a href="#a" name="userInfo" id="${mblog.registerId}">
				<!-- //아이디에 해당하는 사진정보. -->
				<img src="<c:url value='${mblog.profilePicturePath}' />" width="50" height="50" alt="" title="${mblog.registerId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/>
			</a>
		</div>
		<div class="microblog_con">
			<span class="microblog_id"><a href="#a" name="userInfo" id="${mblog.registerId}"><c:out value="${mblog.registerId}"/></a></span>
			<span class="microblog_name">
				<c:choose>
					<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
						<c:out value="${mblog.registerName}"/>
					</c:when>
					<c:otherwise>
						<c:out value="${mblog.registerEnglishName}"/>
					</c:otherwise>
				</c:choose>
			</span>
			<div class="ic_micro_ar none"></div>
			<p><c:out value="${mblog.contentsDisplay}" escapeXml="false"/></p>
			<span class="microblog_time">${ikep4j:getTimeInterval(mblog.registDate, sessionUser.localeCode)} </span>
			<span class="microblog_icon none">
				<span name="favoriteBtn_${mblog.mblogId}" >
					<c:if test="${empty mblog.isFavorite}">
						<span class=microblog_ic_favorite><a href="#a" name="strongButtons" onclick="regFavorite('${mblog.mblogId}','favoriteBtn_${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='favorite' /></a></span>
					</c:if>
					<c:if test="${not empty mblog.isFavorite}">
						<span class="microblog_ic_unfavorite"><a href="#a" name="strongButtons" onclick="deleteFavorite('${mblog.mblogId}','favoriteBtn_${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='delFavorite' /></a></span>
					</c:if>
				</span>
				<c:if test="${sessionUser.userId != mblog.registerId  && 1 == mblog.isRetweetAllowed}">
					<span class="microblog_ic_retweet"><a href="#a" name="strongButtons" onclick="retwitPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='retweet'  /></a></span>
				</c:if>
					<span class="microblog_ic_reply"><a href="#a" name="strongButtons" onclick="replyPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='reply' /></a></span>
				<c:if test="${sessionUser.userId == mblog.registerId}">
					<span class="microblog_ic_delete"><a href="#a" name="strongButtons" onclick="removeMblog('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='delete'  /></a></span>
				</c:if>
			</span>
		</div>
		<div class="selected_ar"></div>
	</li>
</c:forEach>