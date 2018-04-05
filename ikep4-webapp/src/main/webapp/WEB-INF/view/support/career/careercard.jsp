<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.profile.sub.career" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<!--blockListTable start-->
<!-- <div class="pr_career" style="margin-bottom:5px;">
	<h3><ikep4j:message pre='${preHeader}' key='histCardSearch'/></h3>
	<div class="more"></div>
</div>
-->
		
<div class="poptitle_bg"> 
<ul><li class="poptitle_text"><span><ikep4j:message pre='${preHeader}' key='histCard'/></span></li></ul>				
</div>

<!--blockDetail Start-->		
<div class="blockDetail">
	<table summary="<ikep4j:message pre='${preHeader}' key='histCardTable'/>">
		<caption></caption>
		<tbody>
			<tr>
				<td style="width: 100px" rowspan="4"><img id="mainPictureImage" src="<c:url value='${profile.profilePicturePath}' />" width="100" height="100" alt="${profile.userName}" onerror="this.src='<c:url value='/base/images/common/photo_170x170.gif'/>'" /></td>
				<th scope="row" width="17%" ><ikep4j:message pre='${preHeader}' key='title.name'/></th>

				<td width="33%" colspan="3"><strong><c:out value="${profile.userName}" /></strong> &nbsp;<span>(<c:out value="${profile.userEnglishName}" />)</span></td>
				<th width="17%" scope="row" ><ikep4j:message pre='${preHeader}' key='title.jobTitle'/></th>
				<td width="33%"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${profile.jobTitleName}"/></c:when><c:otherwise><c:out value="${profile.jobTitleEnglishName}"/></c:otherwise></c:choose></td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preHeader}' key='title.teamName'/></th>

				<td colspan="5"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${userTeamName}"/></c:when><c:otherwise><c:out value="${userTeamEnglishName}"/></c:otherwise></c:choose></td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preHeader}' key='title.phone'/></th>
				<td colspan="5"><c:out value="${profile.mobile}" /> / <c:out value="${profile.officePhoneNo}" /></td>
			</tr>
			<tr>

				<th scope="row"><ikep4j:message pre='${preHeader}' key='title.mailId'/></th>
				<td colspan="5"><c:out value="${profile.mail}" /></td>
			</tr>
		</tbody>
	</table>
</div>			

<!--//blockDetail End-->
<div class="blockBlank_10px"></div>

<!--blockDetail Start-->					
<div class="blockDetail">
	<table summary="<ikep4j:message pre='${preHeader}' key='histCardTable'/>">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="18%" class="textCenter"><ikep4j:message pre='${preHeader}' key='title.comName'/></th>
				<th scope="col" width="32%" class="textCenter"><ikep4j:message pre='${preHeader}' key='title.workdate'/></th>

				<th scope="col" width="18%" class="textCenter"><ikep4j:message pre='${preHeader}' key='title.roleName'/></th>
				<th scope="col" width="32%" class="textCenter"><ikep4j:message pre='${preHeader}' key='title.workChange'/></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${careerList != null}">
			<c:forEach var="careers" items="${careerList}" varStatus="status">
			<div id="cid:<c:out value="${careers.careerId}"/>" >
			<tr>
				<td class="textCenter break-word"><c:out value="${careers.companyName}"/></td>
				<td class="textCenter break-word"><fmt:formatDate value="${careers.workStartDate}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${careers.workEndDate}" pattern="yyyy.MM.dd"/></td>
				<td class="textCenter break-word"><c:out value="${careers.roleName}"/></td>
				<td class="textCenter break-word"><c:out value="${careers.workChange}"/></td>						
			</tr>
			</div>
			</c:forEach>
			</c:if>
							
		</tbody>
	</table>

</div>
<!--//blockDetail End-->

<!--//blockListTable End-->

