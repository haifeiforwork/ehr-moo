<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMain" value="ui.portal.portlet.congratulations.normalView.main"/>
<c:set var="preList" value="ui.portal.portlet.congratulations.normalView.list"/>

<div class="tableList_1" id="divPortletCongratulations">
	<div class="pTitle_1">
		<ikep4j:message pre="${preMain}" key="birthdayTitle" />
	</div>
	<table summary="<ikep4j:message pre='${preMain}' key='birthdayTableSummary' />">
		<caption></caption>
		<tbody>
			<c:if test="${empty list}">
			<tr>
				<td class="textCenter">
					<ikep4j:message pre="${preList}" key="noData" />
				</td>
			</tr>
			</c:if>
			<c:if test="${!empty list}">
			<c:set var="birthdayCnt" value="0" />
			<c:forEach var="birthday" items="${list}" varStatus="status">			
			<c:set var="birthdayCnt" value="${birthdayCnt+1}" />
			<tr>
				<c:choose>
				<c:when test="${currentDate == birthday.solarBirthday}">
				<th class="today" scope="row" >
					<div class="ellipsis">
					<img alt="<ikep4j:message pre='${preList}' key='birthday' />" src="<c:url value='/base/images/icon/ic_birthday.gif'/>" />
				</c:when>
				<c:otherwise>
				<th scope="row" >
					<div class="ellipsis">
				</c:otherwise>
				</c:choose>
					<a href="#" onclick="iKEP.goProfilePopupMain('${birthday.userId}'); return false;">
						<c:choose>
						<c:when test="${user.localeCode == 'ko'}">
						${birthday.userName}&nbsp;${birthday.jobPositionName}(${birthday.groupName})
						</c:when>
						<c:otherwise>
						${birthday.userEnglishName}&nbsp;${birthday.jobPositionEnglishName}(${birthday.groupEnglishName})
						</c:otherwise>
						</c:choose>
					</a>
					</div>
				</th>
				<c:choose>
				<c:when test="${currentDate == birthday.solarBirthday}">
				<td class="textRight today" width="100px">
				</c:when>
				<c:otherwise>
				<td class="textRight" width="100px">
				</c:otherwise>
				</c:choose>
					<span class="date">
						<c:if test="${birthday.type == '1'}">
						${birthday.lunisolarBirthday}(음)/${birthday.solarBirthday}
						</c:if>
						<c:if test="${birthday.type == '0'}">
	                    ${birthday.solarBirthday}(양)/${birthday.solarBirthday}
	                    </c:if>						
					</span>
				</td>
			</tr>
			</c:forEach>
			<c:if test="${birthdayCnt == 0}">
			<tr>
				<td class="textCenter">
					<ikep4j:message pre="${preList}" key="noData" />
				</td>
			</tr>
			</c:if>
			</c:if>						
		</tbody>
	</table>
						
	<div class="l_b_corner"></div><div class="r_b_corner"></div>
</div>
