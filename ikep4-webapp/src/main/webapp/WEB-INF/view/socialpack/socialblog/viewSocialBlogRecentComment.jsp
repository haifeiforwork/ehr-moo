<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preMsgCmt"  value="message.socialpack.socialblog.portlet.comment" />

<c:set var="preRecentCmtMenu" value="ui.socialpack.socialblog.management.designsetting.layout.portlet" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

						<!--sMenu Start-->
						<div class="sMenu" id="recentComt">
							<h3><ikep4j:message pre='${preRecentCmtMenu}' key='recentComment.menu'/></h3>
							<ul>
								<c:choose>
								<c:when test="${empty top5LinereplyList}">
									<li><div class="ellipsis" align="center"><ikep4j:message pre='${preMsgCmt}' key='nodata'/></div></li>
								</c:when>
								<c:otherwise>
									<c:forEach var="top5LinereplyList" items="${top5LinereplyList}" varStatus="status">
									<li><div class="ellipsis"><a href="#a" onclick="getRecentCmtBlogPosting('${top5LinereplyList.itemId}');">${top5LinereplyList.contents}</a></div></li>
									</c:forEach>				
									</c:otherwise>
								</c:choose>	
							</ul>
						</div>
						<!--//sMenu End-->
