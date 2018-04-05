<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

						<div class="pr_career">
							<h3><ikep4j:message pre='${preProfileMain}' key='career.title'/></h3>
							<div class="more"><a href="#a" onclick="getCareerMorePopup()" ><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>
							
							<table style="table-layout:fixed;" summary="<ikep4j:message pre='${preProfileMain}' key='career.title'/>">
								<caption></caption>
								<tbody>
									<c:if test="${careerList != null}">
									<c:forEach var="careerList" items="${careerList}" varStatus="status">
									<tr>
										<th rowspan="2" class="date" scope="rowgroup"><span><fmt:formatDate value="${careerList.workStartDate}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${careerList.workEndDate}" pattern="yyyy.MM.dd"/></span></th>
										<td><span><c:out value="${careerList.companyName}"/> / <c:out value="${careerList.roleName}"/></span></td>
									</tr>
									<tr>

										<td class="carrer_d break-word">
											<p><c:out value="${fn:replace(careerList.workChange, lf, '<br/>')}" escapeXml="false" /></p>
										</td>
									</tr>
									</c:forEach>
									</c:if>	                                  																																																					
								</tbody>
							</table>
						</div>
							
							
							
