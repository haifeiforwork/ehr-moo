<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.profile.sub.career" /> 
<c:set var="preButton"  value="ui.support.profile.sub.career.button" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

							<c:if test="${career != null}">
							<c:if test="${type == 'CREATE'}">
							<div class="container" id="cid_<c:out value="${career.careerId}"/>" >
							</c:if>
							<table>
								<input id="careerId" name="careerId" value="<c:out value="${career.careerId}"/>" type="hidden" />

								<tr>
									<th rowspan="2" class="date" scope="rowgroup"><fmt:formatDate value="${career.workStartDate}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${career.workEndDate}" pattern="yyyy.MM.dd"/></th>

									<td><c:out value="${career.companyName}"/> / <c:out value="${career.roleName}"/></td>
									<c:if test="${user.userId == targetUserId}">
									<td rowspan="2" class="textRight" width="100">
										<a onclick="updateCareer('${career.careerId}', this)" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a>
										<a onclick="deleteCareer('${career.careerId}', this)" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
									</td>
									</c:if>
								</tr>
								<tr>
									<td class="carrer_d">
									<p class="break-word"><c:out value="${fn:replace(career.workChange, lf, '<br/>')}"  escapeXml="false" /></p>
									</td>
								</tr>
							</table>
							<c:if test="${type == 'CREATE'}">
							</div>
							</c:if>
							</c:if>

