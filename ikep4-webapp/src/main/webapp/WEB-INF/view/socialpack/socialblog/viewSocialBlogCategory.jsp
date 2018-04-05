<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="preCategoryMenu" value="ui.socialpack.socialblog.management.designsetting.layout.portlet" />

						<!--sMenu Start-->
						<div class="sMenu">
							<h3><ikep4j:message pre='${preCategoryMenu}' key='category'/></h3>
							<ul>
								<li class="viewAll"><a onclick="getCategoryBlogPosting('')" href="#a"><ikep4j:message pre='${preCategoryMenu}' key='category.allList'/>(${totalPostingCount})</a></li>
								<c:if test="${socialCategoryServiceList != null}">
								<c:forEach var="socialCategoryServiceList" items="${socialCategoryServiceList}" varStatus="status">
								<li><div class="ellipsis"><a href="#a" onclick="getCategoryBlogPosting('${socialCategoryServiceList.categoryId}')" ><c:out value="${socialCategoryServiceList.categoryName}"/></a></div></li>
								</c:forEach>				
								</c:if>
							</ul>
						</div>
						<!--//sMenu End-->					
