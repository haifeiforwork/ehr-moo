<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.assess.common.message</c:set>

<div class="imgList_3" id="${portletConfigId}">
	<ul>
	<c:forEach var="item" items="${assessmentManagementUserPviList}" varStatus="itemStatus">
		<c:if test="${4 gt itemStatus.count}">
			<li class="portNo0${itemStatus.count} position_0">
				<ul>
					<li><span><a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.userId}', 'bottom');"><img src="<c:url value='${item.profilePicturePath}'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></span></li>
					<li><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.userId}', 'bottom');">${item.userName}&nbsp;${item.jobTitleName}</a></span>&nbsp;<span class="team">${item.teamName}</span></li>
					<li><span class="index">PVI&nbsp;:&nbsp;<span><fmt:formatNumber value="${item.pvi}"/></span></span></li>
				</ul>
				<div class="clear"></div>
			</li>
		</c:if>
		<c:if test="${3 lt itemStatus.count and itemStatus.count < 10}">
			<li class="portNo0${itemStatus.count} position_1"><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.userId}', 'bottom');">${item.userName}&nbsp;${item.jobTitleName}</a></span>&nbsp;<span class="team">${item.teamName}</span>&nbsp;<span class="index">PVI&nbsp;:&nbsp;<span><fmt:formatNumber value="${item.pvi}"/></span></span></li>
		</c:if>
		<c:if test="${10 eq itemStatus.count}">
			<li class="portNo10 position_1"><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${item.userId}', 'bottom');">${item.userName}&nbsp;${item.jobTitleName}</a></span>&nbsp;<span class="team">${item.teamName}</span>&nbsp;<span class="index">PVI&nbsp;:&nbsp;<span><fmt:formatNumber value="${item.pvi}"/></span></span></li>
		</c:if>
	</c:forEach>
	<c:if test="${0 eq fn:length(assessmentManagementUserPviList)}">
		<li class="emptyRecord"><ikep4j:message pre="${messagePrefix}" key="emptyRecord"/></li>
	</c:if>
	</ul>
</div>
