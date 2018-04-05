<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preActivityMenu" value="ui.socialpack.socialblog.management.designsetting.layout.portlet" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

						<div class="blockList">
							<h2><ikep4j:message pre='${preActivityMenu}' key='activity'/></h2>
							<ul>
								<li><ikep4j:message pre='${preActivityMenu}' arguments='${followerCount}' key='activity.follow'/></li>
								<li><ikep4j:message pre='${preActivityMenu}' arguments='${followingCount}' key='activity.following'/></li>
								<li><ikep4j:message pre='${preActivityMenu}' arguments='${totalPostingCount}' key='activity.totalPosting'/></li>

							</ul>
						</div>
