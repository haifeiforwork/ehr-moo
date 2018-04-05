<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.expertnetwork.common.message</c:set>
<c:set var="pagePrefix">ui.collpack.expertnetwork.common.page</c:set>

<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>${pageCondition.categoryName}&nbsp;Category</h2>
	<div class="listInfo">
		<form id="searchForm" action="">
			<spring:bind path="pageCondition.categoryId">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.categoryName">
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
			<spring:bind path="pageCondition.expertAdmin">
				<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>
			<spring:bind path="pageCondition.countOfPage">
				<select id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre="${pagePrefix}" key="countOfPage"/>" onchange="pageReflash();">
					<option <c:if test="${status.value eq 10}">selected="selected"</c:if>>10</option>
					<option <c:if test="${status.value eq 15}">selected="selected"</c:if>>15</option>
					<option <c:if test="${status.value eq 20}">selected="selected"</c:if>>20</option>
					<option <c:if test="${status.value eq 30}">selected="selected"</c:if>>30</option>
					<option <c:if test="${status.value eq 50}">selected="selected"</c:if>>50</option>
				</select>
			</spring:bind>
		</form>
		<div class="totalNum"><ikep4j:message pre="${pagePrefix}" key="total"/>&nbsp;<span><fmt:formatNumber value="${pageCondition.totalCount}"/></span></div>
	</div>
	<div class="clear"></div>
</div>

<div class="expert_bl block">
	<form id="bodyForm" action="">
	<spring:bind path="pageCondition.categoryId">
		<input type="hidden" name="${status.expression}" value="${status.value}"/>
	</spring:bind>
	<ul>
	<c:forEach var="item" items="${expertItemList}">
	<li>
		<c:if test="${pageCondition.expertAdmin}">
		<div class="expert_checkbox">
			<span><input name="selectItem" class="checkbox" title="<ikep4j:message pre="${messagePrefix}" key="select"/>" type="checkbox" value="${item.userId}" /></span>
		</div>
		</c:if>
		<div class="expert_photo">
			<c:if test="${100 le item.matchingScore and 2 ne item.isAuthorized}">
			<div class="expert_best_s"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.userId}');"><img src="<c:url value='/base/images/common/img_best_s.png'/>" alt="best" /></a></div>
			</c:if>
			<span><a href="#a" onclick="iKEP.goProfilePopupMain('${item.userId}');"><img src="<c:url value='${item.profilePicturePath}'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></span>
		</div>
		<div class="expert_Photo_info">
			<span class="expert_photo_name"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.userId}');">${item.userName} ${item.jobTitleName}</a></span>
			<span class="expert_photo_team">${item.teamName}</span>
			<span class="expert_photo_num">${item.matchingScore}%</span>
			<div class="expert_photo_con"><p>${item.expertField}</p></div>
			<div class="expert_photo_tag"><img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="<ikep4j:message pre="${messagePrefix}" key="tag"/>"/>
			<c:forEach var="tagItem" items="${item.tagList}" varStatus="tagItemStatus">
				<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tagItem.tagName}','${tagItem.tagItemType}','${tagItem.tagItemSubType}');">${tagItem.tagName}</a><c:if test="${!tagItemStatus.last}">,</c:if>
			</c:forEach>
			</div>
		</div>
	</li>
	</c:forEach>
	<c:if test="${0 eq fn:length(expertItemList)}">
	<li>
		<div class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></div>
	</li>
	</c:if>
	</ul>
	</form>
	
	<!--Page Numbur Start-->
	<c:if test="${0 ne fn:length(expertItemList)}">
	<%@ include file="/WEB-INF/view/common/page.jsp"%>
	</c:if>
	<!--//Page Numbur End-->
</div>