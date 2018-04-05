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

<!-- //TODO 프로파일 화면과 연결이 필요하다. -->
	<div>
		<div class="microblog_img">
			<a href="<c:url value='/socialpack/microblogging/privateHome.do?ownerId=${userInfo.userId}'/>">
				<!-- //아이디에 해당하는 사진정보. -->
				<img src="<c:url value='${userInfo.profilePicturePath}' />" width="50" height="50" alt="" title="${userInfo.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
			</a>
		</div>
		<div class="microblog_con">
			<div class="microblog_me">
				<c:choose>
					<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
						${userInfo.teamName}  
						&nbsp;<strong>${userInfo.userName}</strong> 
					</c:when>
					<c:otherwise>
						${userInfo.teamEnglishName}  
						&nbsp;<strong>${userInfo.userEnglishName}</strong> 
					</c:otherwise>
				</c:choose>
				&nbsp; @${userInfo.userId} 
			</div>
			<div class="more"><img src="<c:url value='/base/images/icon/ic_plus_s.gif'/>" title="" /> <a href="#a" onclick="javascript:viewFullProfile('${userInfo.userId}');"><ikep4j:message pre='${preLabel}' key='viewfullProfile' /></a></div>
			<div class="more">
				<div id="followBtnDetail_${userInfo.userId}" >
					<c:if test="${sessionuserInfo.userId == userInfo.userId}">
						&nbsp;
					</c:if>
					<c:if test="${sessionuserInfo.userId != userInfo.userId}">
						<c:if test="${not empty userInfo.isFollowing}">
							<a class="button_follow" href="#a" onclick="unfollow('${userInfo.userId}','followBtnDetail_${userInfo.userId}');"><span><ikep4j:message pre='${preButton}' key='unfollow' /></span></a>
						</c:if>
						<c:if test="${empty userInfo.isFollowing}">
							<a class="button_pr" href="#a" onclick="follow('${userInfo.userId}','followBtnDetail_${userInfo.userId}');"><span><img src="<c:url value='/base/images/icon/ic_plus.gif'/>" title="" /><ikep4j:message pre='${preButton}' key='follow' /></span></a>
						</c:if>
					</c:if>
				</div>
			</div>
		</div>
		<div class="clear"><c:out value="${userInfo.expertField}" escapeXml="false"/></div>
	</div>
	
	<div class="microblog_numtable">
		<table summary="Tweets, Following, Followers, Groups">
			<caption></caption>
			<tbody>
				<tr>
					<td class="microblog_num">${myTweetCount}</td>
					<td class="microblog_num">${myFollowingCount}</td>
					<td class="microblog_num">${myFollowerCount}</td>
					<td class="microblog_num">${myGroupListSize}</td>
				</tr>
				<tr>
					<td><ikep4j:message pre='${preLabel}' key='tweets'  /></td>
					<td><ikep4j:message pre='${preLabel}' key='following'  /></td>
					<td><ikep4j:message pre='${preLabel}' key='followers'  /></td>
					<td><ikep4j:message pre='${preLabel}' key='groups'  /></td>
				</tr>
			</tbody>
		</table>
	</div>

	<h3><ikep4j:message pre='${preLabel}' key='recentTweets' /></h3>
		
	<ul class="microblog_li">
	<c:forEach var="mblog" items="${mblogList}" varStatus="loopStatus">
		<li id="timelineMblogId_${mblog.mblogId}" name="timelineThreadId_${mblog.threadId}">
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
				<span class="microblog_time">${ikep4j:getTimeInterval(mblog.registDate, sessionUser.localeCode)} <ikep4j:message pre='${preLabel}' key='ago'  /></span>
				<span class="microblog_icon none">
						<span class="microblog_ic_favorite"><a href="#a" onclick="regFavorite('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='favorite' /></a></span>
					<c:if test="${sessionuserInfo.userId != mblog.registerId && 1 == mblog.isRetweetAllowed}">
						<span class="microblog_ic_retweet"><a href="#a" onclick="retwitPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='retweet'  /></a></span>
					</c:if>
						<span class="microblog_ic_reply"><a href="#a" onclick="replyPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='reply'  /></a></span>
					<c:if test="${sessionuserInfo.userId == mblog.registerId}">
						<span class="microblog_ic_delete"><a href="#a" onclick="removeMblog('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='delete'  /></a></span>
					</c:if>
				</span>
			</div>
			<div class="selected_ar"></div>
		</li>
	</c:forEach>
	</ul>	
	
	<div class="retweetlist">
		<h3><a href="<c:url value='/socialpack/microblogging/privateHome.do?ownerId=${userInfo.userId}'/>" >More Tweets from @<c:out value="${userInfo.userId}"/></a></h3>
	</div>	
