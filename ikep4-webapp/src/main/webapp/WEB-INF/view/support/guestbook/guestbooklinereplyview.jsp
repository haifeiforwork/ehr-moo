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

							<c:if test="${guestbook != null}">
								<!--blockComment_re Start-->
								<div class="blockComment_re">
								<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar.gif' />" alt="" /></div>
								<div class="blockCommentInfo">
									<span class="blockCommentInfo_name"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbook.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbook.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbook.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbook.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>
									<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />						
									<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbook.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbook.registerTeamEngName}"/></c:otherwise></c:choose></span>
										<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif' />" alt="" />
									<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbook.registDate}"/></span>
									<span class="rebtn">
										<c:if test="${user.userId == guestbook.registerId  or user.userId == targetUserId }">
											<a onclick="javascript:deleteGuestbookLineReply('${guestbook.guestbookId}',this);" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
										</c:if>
										${guestbook.indentation}
										<c:if test="${guestbook.indentation <= '2'}">
											<a onclick="inputGuestbookLineReplyValid('${guestbook.guestbookId}',this,${guestbook.indentation+1});" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
										</c:if>
									</span>
								</div>
								<div class="summaryViewCon">
									<p>${fn:replace(guestbook.contents, lf, '<br/>')}</p>
									
									<div class="gbLineReplyInputView"></div>
									<div class="prependGbLineReplyview"></div>
								</div>
								</div>
								<!--//blockComment_re End-->
							</c:if>							
