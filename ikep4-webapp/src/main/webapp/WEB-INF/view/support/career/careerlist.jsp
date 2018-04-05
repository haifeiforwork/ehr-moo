<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.profile.sub.career" /> 
<c:set var="preButton"  value="ui.support.profile.sub.career.button" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<% 
	pageContext.setAttribute("lf", "\n"); 
%>
<script type="text/javascript">
<!--

(function($) {
	
	cancelCareer = function() {
		$jq("#addCareerDiv").empty();
	};
	
})(jQuery);  
//-->
</script>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

					<c:if test="${user.userId == targetUserId}">
					<span class="btn_profile mt10"><a onclick="javascript:addCareer();" href="#a"><ikep4j:message pre='${preHeader}' key='creatCareer'/></a></span>
					</c:if>
					<div id="addCareerDiv"></div>
					<div class="pr_career" style="margin-bottom:-5px;" >
						<div class="more"></div>
					</div>
					<div id="careerListView">

					<!--blockListTable start-->
					<!--blockDetail Start-->
					<c:if test="${careerList != null}">
					<div class="pr_career">
					
						<table summary="<ikep4j:message pre='${preHeader}' key='careerByYear'/>">
							<caption></caption>
							<tbody>
								<tr><td>
								<div id="careerListPreView"></div>
								<c:forEach var="careers" items="${careerList}" varStatus="status">
								<div class="container" id="cid_<c:out value="${careers.careerId}"/>" >
								<table style="table-layout:fixed;">
									<input id="careerId" name="careerId" value="<c:out value="${careers.careerId}"/>" type="hidden" />
									<tr>
										<th rowspan="2" class="date" scope="rowgroup"><fmt:formatDate value="${careers.workStartDate}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${careers.workEndDate}" pattern="yyyy.MM.dd"/></th>
	
										<td><c:out value="${careers.companyName}"/> / <c:out value="${careers.roleName}"/></td>
										<c:if test="${user.userId == targetUserId}">
										<td rowspan="2" class="textRight" width="100">
											<a onclick="updateCareer('${careers.careerId}', this)" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a>
											<a onclick="deleteCareer('${careers.careerId}', this)" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
										</td>
										</c:if>
									</tr>
									<tr>
										<td class="carrer_d break-word"><c:out value="${fn:replace(careers.workChange, lf, '<br/>')}" escapeXml="false" /></td> <!-- escapeXml="false"  -->
									</tr>
								</table>
								</div>
								</c:forEach>
								</td></tr>             																																																					
							</tbody>
						</table>
					</div>
					</c:if>
					<!--//blockDetail End-->
					</div>
					<!--//blockListTable End-->

