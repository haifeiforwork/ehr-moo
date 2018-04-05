<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<c:forEach var="mblog" items="${mblogList}" varStatus="loopStatus">
	<li id="timelineMblogId_${mblog.mblogId}" name="timelineAddonCode_${mblog.addonCode}">
		<div class="microblog_img">
			<a href="#a" name="userInfo" id="${mblog.registerId}">
				<!-- //아이디에 해당하는 사진정보. -->
				<img src="<c:url value='${mblog.profilePicturePath}' />" width="50" height="50" alt="" title="${mblog.registerId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
			</a>
		</div>
		<div class="microblog_con">
			<span class="microblog_id"><a href="#a" name="userInfo" id="${mblog.registerId}"><c:out value="${mblog.registerId}"/></a></span>
			<span class="microblog_name"><a href="${mblog.sourceLink}" name='addonLink' target='_blank' ><c:out value="${mblog.displayCode}"/></a></span>
			<span class="microblog_name"><c:out value="${mblog.sourceLink}"/></span>
			<div class="ic_micro_ar none"></div>
			<p class="ellipsis"> ${mblog.contents }</p>
			<span class="microblog_time">${ikep4j:getTimeInterval(mblog.registDate, sessionUser.localeCode)} </span>
		</div>
		<div class="selected_ar"></div>
	</li>
</c:forEach>