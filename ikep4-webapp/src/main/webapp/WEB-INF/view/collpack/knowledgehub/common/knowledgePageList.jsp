<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="pagePrefix">ui.collpack.knowledgehub.common.page</c:set>
<c:set var="searchPrefix">ui.collpack.knowledgehub.common.search</c:set>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<div class="listInfo">
		<form id="searchForm" action="">
			<spring:bind path="pageCondition.categoryId">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.totalCount">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.requestPage">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.page">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.reInit">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.viewType">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.layoutType">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.countOfPage">
				<select name="${status.expression}" title="<ikep4j:message pre="${pagePrefix}" key="countOfPage"/>" onchange="pageReflash();">
					<option <c:if test="${status.value eq 10}">selected="selected"</c:if>>10</option>
					<option <c:if test="${status.value eq 15}">selected="selected"</c:if>>15</option>
					<option <c:if test="${status.value eq 20}">selected="selected"</c:if>>20</option>
					<option <c:if test="${status.value eq 30}">selected="selected"</c:if>>30</option>
					<option <c:if test="${status.value eq 50}">selected="selected"</c:if>>50</option>
				</select>
			</spring:bind>
		</form>
		<div class="totalNum"><span><ikep4j:message pre="${pagePrefix}" key="total"/>&nbsp;<fmt:formatNumber value="${pageCondition.totalCount}"/></span></div>
	</div>
	<div class="listView_1">
		<div class="none"><ikep4j:message pre="${searchPrefix}" key="viewType.title"/></div>
		<ul>
			<li><c:if test="${0 ne pageCondition.viewType}"><a href="#a" onclick="viewSubmit(0);"></c:if><img src="<c:if test="${0 eq pageCondition.viewType}"><c:url value='/base/images/icon/ic_view_list_on.gif'/></c:if><c:if test="${0 ne pageCondition.viewType}"><c:url value='/base/images/icon/ic_view_list.gif'/></c:if>" alt="" title="<ikep4j:message pre="${searchPrefix}" key="viewType.list"/><c:if test="${0 eq pageCondition.viewType}">[on]</c:if><c:if test="${0 ne pageCondition.viewType}"></c:if>"/><c:if test="${0 ne pageCondition.viewType}"></a></c:if></li>
			<li><c:if test="${1 ne pageCondition.viewType}"><a href="#a" onclick="viewSubmit(1);"></c:if><img src="<c:if test="${1 eq pageCondition.viewType}"><c:url value='/base/images/icon/ic_view_summary_on.gif'/></c:if><c:if test="${1 ne pageCondition.viewType}"><c:url value='/base/images/icon/ic_view_summary.gif'/></c:if>" alt="" title="<ikep4j:message pre="${searchPrefix}" key="viewType.summary"/><c:if test="${1 eq pageCondition.viewType}"></c:if><c:if test="${1 ne pageCondition.viewType}"></c:if>"/><c:if test="${1 ne pageCondition.viewType}"></a></c:if></li>
		</ul>
	</div>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<!--blockListTable Start-->
<div class="blockListTable <c:if test="${1 eq pageCondition.viewType}">tag_summaryView</c:if>">
	<c:choose>
		<c:when test="${0 eq pageCondition.viewType}">
			<%@ include file="/WEB-INF/view//collpack/knowledgehub//common/knowledgeItemList.jsp"%> 
		</c:when>
		<c:when test="${1 eq pageCondition.viewType}">
			<%@ include file="/WEB-INF/view//collpack/knowledgehub//common/knowledgeItemSummary.jsp"%>
		</c:when> 				
	</c:choose>

	<!--Page Numbur Start-->
	<c:if test="${0 ne fn:length(knowledgeList)}">
	<%@ include file="/WEB-INF/view/common/page.jsp"%>
	</c:if>
	<!--//Page Numbur End-->
</div>
<!--//blockListTable End-->	
