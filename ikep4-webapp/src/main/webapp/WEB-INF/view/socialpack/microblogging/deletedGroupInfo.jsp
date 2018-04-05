<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="sessionPortal" 	value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" 	value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preLink"  		value="ui.socialpack.microblogging.link" /> 
<c:set var="preGroupLabel" 	value="ui.socialpack.microblogging.group.label" />
<c:set var="preLabel" 		value="ui.socialpack.microblogging.label" />
<%-- 메시지 관련 Prefix 선언 End --%>

	<div class="nopagewrap">
		<div class="nopage nopage_border">
			<img src="<c:url value='/base/images/icon/ic_nopage2.gif'/>" alt="" />
			<span><ikep4j:message pre='${preGroupLabel}' key='deletedGroup' /></span>
			<div class="nopage_info">
				<ikep4j:message pre='${preLabel}' key='deletedBy'  /> 
					<c:choose>
						<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
							${nowMbgroup.updaterName} ${updaterInfo.jobTitleName}
						</c:when>
						<c:otherwise>
							${nowMbgroup.updaterEnglishName} ${updaterInfo.jobTitleEnglishName}
						</c:otherwise>
					</c:choose>
				<ikep4j:timezone date="${nowMbgroup.updateDate}" pattern="yyyy.MM.dd HH:mm"/>
			</div>						
			<div class="clear"></div>	
			<div class="nopage_list1">
				<!--blockButton_2 Start-->
				<div class="blockButton_2"> 
					<a class="button_2" href="<c:url value='/socialpack/microblogging/privateHome.do?ownerId=${sessionUser.userId}'/>"><span><ikep4j:message pre='${preLink}' key='microblogHome'  /></span></a>				
				</div>
				<!--//blockButton_2 End-->
			</div>
		</div>
	</div>
