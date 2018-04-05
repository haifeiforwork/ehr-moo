<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.expertnetwork.common.message</c:set>

<c:forEach var="item" items="${interestList}">
<li>
	<div class="expert_photo">
	<c:if test="${0 lt item.expertCount}">
		<div class="expert_best_s"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.expertUserId}');"><img src="<c:url value='/base/images/common/img_best_s.png'/>" alt="best"/></a></div>
	</c:if>
		<span><a href="#a" onclick="iKEP.goProfilePopupMain('${item.expertUserId}');"><img src="<c:url value='${item.profilePicturePath}'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></span>
	</div>
	<div class="expert_Photo_info">
		<span class="expert_photo_name"><a href="#a" onclick="iKEP.goProfilePopupMain('${item.expertUserId}');">${item.userName}&nbsp;${item.jobTitleName}</a></span>
		<span class="expert_photo_team">${item.teamName}</span>
		<span class="expert_photo_num">${item.matchingScore}%</span>
		<div class="expert_photo_con">${item.expertField}</div>
		<div class="expert_photo_tag"><img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="<ikep4j:message pre="${messagePrefix}" key="tag"/>"/>
		<c:forEach var="tagItem" items="${item.tagList}" varStatus="tagItemStatus">
			<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tagItem.tagName}','${tagItem.tagItemType}','${tagItem.tagItemSubType}');">${tagItem.tagName}</a><c:if test="${!tagItemStatus.last}">,</c:if>
		</c:forEach>
		</div>
	</div>
</li>
</c:forEach>
<c:if test="${0 eq fn:length(interestList)}"><li><div class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></div></li></c:if>
